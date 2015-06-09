package dotNetBuild.teamCity;

import com.intellij.openapi.diagnostic.Logger;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.BuildProgressLogger;
import jetbrains.buildServer.agent.BuildRunnerContext;
import jetbrains.buildServer.agent.runner.CommandLineBuildService;
import jetbrains.buildServer.agent.runner.ProgramCommandLine;
import jetbrains.buildServer.agent.runner.SimpleProgramCommandLine;
import jetbrains.buildServer.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DotNetBuildBuildService extends CommandLineBuildService {
    @NotNull
    @Override
    public ProgramCommandLine makeProgramCommandLine() throws RunBuildException {
        BuildProgressLogger buildLogger = getLogger();
        BuildRunnerContext buildRunnerContext = getRunnerContext();
        Map<String, String> programEnvironmentVariables = buildRunnerContext.getBuildParameters().getEnvironmentVariables();
        String programWorkingDirectory = getProgramWorkingDirectory();
        String programPath = getProgramPath();
        List<String> programArgs = getProgramArgs();

        buildLogger.message("Program environment variables: " + programEnvironmentVariables.toString());
        buildLogger.message("Program working directory: " + programWorkingDirectory);
        buildLogger.message("Program path: " + programPath);
        buildLogger.message("Program args: " + programArgs.toString());

        return new SimpleProgramCommandLine(buildRunnerContext.getBuildParameters().getEnvironmentVariables(), programWorkingDirectory, programPath, programArgs);
    }

    private String getProgramWorkingDirectory() {
        Map<String,String> parameters = getRunnerContext().getRunnerParameters();
        String workingDirectory = parameters.get(DotNetBuildConstants.Instance.getWorkingDirectoryKey());
        String checkoutDirectory = getBuild().getCheckoutDirectory().getAbsolutePath();

        if (workingDirectory != null && !workingDirectory.isEmpty()) {
            if (new File(workingDirectory).isAbsolute() == false){
                workingDirectory = Paths.get(checkoutDirectory, workingDirectory).toAbsolutePath().toString();
            }
        }
        else {
            workingDirectory = checkoutDirectory;
        }

        return workingDirectory;
    }

    private String getProgramPath() throws RunBuildException {
        Map<String,String> parameters = getRunnerContext().getRunnerParameters();
        String commandLineRunnerPath = parameters.get(DotNetBuildConstants.Instance.getCommandLineRunnerPathKey());

        if (commandLineRunnerPath != null && !commandLineRunnerPath.isEmpty()) {
            // Parameter commandLineRunnerPath is not empty, check if it's a relative path or not
            if (new File(commandLineRunnerPath).isAbsolute() == false){
                // It's a relative path from the checkout directory, the DotNetBuild command line runner has to be checked in in the VCS
                commandLineRunnerPath = new File(getBuild().getCheckoutDirectory(), commandLineRunnerPath).getAbsolutePath();
            }

            // It's an absolute path, the DotNetBuild command line runner must be at the exact location of this file system path
        } else {
            // Parameter commandLineRunnerPath is empty, we'll use the built-in DotNetBuild command line runner
            File builtInCommandLineRunnerPath = new File(getBuild().getBuildTempDirectory(), "dotNetBuild");
            extractDotNetBuild(builtInCommandLineRunnerPath);
            commandLineRunnerPath = builtInCommandLineRunnerPath.getAbsolutePath();
        }

        commandLineRunnerPath = new File(commandLineRunnerPath, "DotNetBuild.Runner.Assembly.exe").getAbsolutePath();

        return commandLineRunnerPath;
    }

    private void extractDotNetBuild(File builtInCommandLineRunnerPath) throws RunBuildException {
        try {
            if (builtInCommandLineRunnerPath.exists())
                return;

            if (!builtInCommandLineRunnerPath.mkdirs())
                throw new RuntimeException("Unable to create temp output directory " + builtInCommandLineRunnerPath);

            DotNetBuildExtractor extractor = new DotNetBuildExtractor();
            extractor.extractTo(builtInCommandLineRunnerPath.getAbsolutePath());
        } catch (IOException e) {
            final String message = "Unable to create temporary file in " + builtInCommandLineRunnerPath + " for DotNetBuild: " + e.getMessage();
            Logger.getInstance(getClass().getName()).error(message, e);
            throw new RunBuildException(message);
        }
    }

    private List<String> getProgramArgs() {
        List<String> result = new ArrayList<String>();
        Map<String,String> parameters = getRunnerContext().getRunnerParameters();

        String assembly = parameters.get(DotNetBuildConstants.Instance.getAssemblyKey());
        String target = parameters.get(DotNetBuildConstants.Instance.getTargetKey());
        String configuration = parameters.get(DotNetBuildConstants.Instance.getConfigurationKey());
        String additionalArguments = parameters.get(DotNetBuildConstants.Instance.getCommandLineArgumentsKey());

        if (assembly != null && !assembly.isEmpty()) {
            File assemblyPath = new File(getBuild().getCheckoutDirectory(), assembly);
            result.add("assembly:" + quote(assemblyPath.getAbsolutePath()));
        }

        if (target != null && !target.isEmpty()) {
            result.add("target:" + quote(target));
        }

        if (configuration != null && !configuration.isEmpty()) {
            result.add("configuration:" + quote(configuration));
        }

        if (additionalArguments != null && !additionalArguments.isEmpty()) {
            List<String> additionalArgumentsList = splitCommaSeparatedValues(additionalArguments);
            for (String additionalArgument : additionalArgumentsList) {
                result.add(additionalArgument);
            }
        }

        return result;
    }

    private String quote(String value) {
        return "\"" + value + "\"";
    }

    private List<String> splitCommaSeparatedValues(String values) {
        List<String> result = new ArrayList<String>();
        if (values == null || StringUtil.isEmptyOrSpaces(values)) {
            return result;
        }

        String[] valueList = values.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        for (String value : valueList) {
            String valueTrimmed = value.trim();
            if (valueTrimmed.length() > 0) {
                result.add(valueTrimmed);
            }
        }

        return result;
    }
}