package dotNetBuild.teamCity;

public class DotNetBuildConstants {
    public final static DotNetBuildConstants Instance = new DotNetBuildConstants();
    public final static String BUILD_RUNNER_TYPE = "dotnetbuild";

    public String getAssemblyKey() {
        return "dotnetbuild_assembly";
    }

    public String getTargetKey() {
        return "dotnetbuild_target";
    }

    public String getConfigurationKey() {
        return "dotnetbuild_configuration";
    }

    public String getCommandLineRunnerPathKey() {
        return "dotnetbuild_commandLineRunnerPathKey";
    }

    public String getCommandLineArgumentsKey() {
        return "dotnetbuild_commandLineArgumentsKey";
    }

    public String getWorkingDirectoryKey() {
        return "dotnetbuild_workingDirectoryKey";
    }
}