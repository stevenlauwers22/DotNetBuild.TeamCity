package dotNetBuild.teamCity;

import jetbrains.buildServer.serverSide.InvalidProperty;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import jetbrains.buildServer.serverSide.RunType;
import jetbrains.buildServer.serverSide.RunTypeRegistry;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DotNetBuildRunType extends RunType {
    private final PluginDescriptor pluginDescriptor;

    public DotNetBuildRunType(final RunTypeRegistry runTypeRegistry, final PluginDescriptor pluginDescriptor) {
        this.pluginDescriptor = pluginDescriptor;
        runTypeRegistry.registerRunType(this);
    }

    @NotNull
    @Override
    public String getType() {
        return DotNetBuildConstants.BUILD_RUNNER_TYPE;
    }

    @Override
    public String getDisplayName() {
        return "DotNetBuild";
    }

    @Override
    public String getDescription() {
        return "Executes DotNetBuild scripts.";
    }

    @Nullable
    @Override
    public PropertiesProcessor getRunnerPropertiesProcessor() {
        return new PropertiesProcessor() {
            private void checkNotEmpty(@NotNull final Map<String, String> properties,
                                       @NotNull final String key,
                                       @NotNull final String message,
                                       @NotNull final Collection<InvalidProperty> res) {
                if (jetbrains.buildServer.util.StringUtil.isEmptyOrSpaces(properties.get(key))) {
                    res.add(new InvalidProperty(key, message));
                }
            }

            @NotNull
            public Collection<InvalidProperty> process(@Nullable final Map<String, String> p) {
                final Collection<InvalidProperty> result = new ArrayList<InvalidProperty>();
                if (p == null) return result;

                checkNotEmpty(p, DotNetBuildConstants.Instance.getAssemblyKey(), "Assembly must be specified", result);
                checkNotEmpty(p, DotNetBuildConstants.Instance.getTargetKey(), "Target must be specified", result);

                return result;
            }
        };
    }

    @Nullable
    @Override
    public String getEditRunnerParamsJspFilePath() {
        return pluginDescriptor.getPluginResourcesPath("editDotNetBuild.jsp");
    }

    @Nullable
    @Override
    public String getViewRunnerParamsJspFilePath() {
        return pluginDescriptor.getPluginResourcesPath("viewDotNetBuild.jsp");
    }

    @Nullable
    @Override
    public Map<String, String> getDefaultRunnerProperties() {
        final Map<String, String> map = new HashMap<String, String>();
        return map;
    }
}