package dotNetBuild.teamCity;

import com.intellij.openapi.diagnostic.Logger;
import jetbrains.buildServer.agent.*;
import jetbrains.buildServer.agent.runner.CommandLineBuildService;
import jetbrains.buildServer.agent.runner.CommandLineBuildServiceFactory;
import jetbrains.buildServer.log.Loggers;
import org.jetbrains.annotations.NotNull;

public class DotNetBuildBuildServiceFactory implements CommandLineBuildServiceFactory {
    @NotNull
    public CommandLineBuildService createService() {
        return new DotNetBuildBuildService();
    }

    @NotNull
    public AgentBuildRunnerInfo getBuildRunnerInfo() {
        return new AgentBuildRunnerInfo() {
            @NotNull
            public String getType() {
                return DotNetBuildConstants.BUILD_RUNNER_TYPE;
            }

            public boolean canRun(@NotNull BuildAgentConfiguration buildAgentConfiguration) {
                if (!buildAgentConfiguration.getSystemInfo().isWindows()) {
                    Loggers.SERVER.debug(getType() + " runner is supported only under Windows platform");
                    return false;
                }

                return true;
            }
        };
    }
}