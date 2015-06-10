# DotNetBuild.TeamCity

DotNetBuild.TeamCity is a TeamCity plugin which allows you run your [DotNetBuild](https://github.com/stevenlauwers22/DotNetBuild) scripts from within TeamCity. When you install DotNetBuild, you'll get a new build runner type called 'DotNetBuild'.

## How can I install it?

1. Download the latest version of the plugin from the [releases page](https://github.com/stevenlauwers22/DotNetBuild.TeamCity/releases).
2. Put the dotNetBuild.zip file in the 'plugins' directory under the [TeamCity data directory](https://confluence.jetbrains.com/display/TCD8/TeamCity+Data+Directory).
3. Restart TeamCity.

You should now be able to see the 'DotNetBuild' runner type when you add a new build step.

![New build runner](https://raw.githubusercontent.com/stevenlauwers22/DotNetBuild.TeamCity/master/documents/Screen1.png)

## What does it look like?

When adding a new DotNetBuild build step, you'll get the following option page where you can specify which parameters should be passed to the DotNetBuild runner.

![Option page](https://raw.githubusercontent.com/stevenlauwers22/DotNetBuild.TeamCity/master/documents/Screen2.png)