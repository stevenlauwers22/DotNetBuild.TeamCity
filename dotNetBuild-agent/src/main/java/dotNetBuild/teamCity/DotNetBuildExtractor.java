package dotNetBuild.teamCity;

import java.io.*;

public class DotNetBuildExtractor {
    public void extractTo(String destinationPath) throws IOException {
        extractFile("/DotNetBuild.Core.dll", destinationPath + "\\DotNetBuild.Core.dll");
        extractFile("/DotNetBuild.Runner.dll", destinationPath + "\\DotNetBuild.Runner.dll");
        extractFile("/DotNetBuild.Runner.Assembly.exe", destinationPath + "\\DotNetBuild.Runner.Assembly.exe");
        extractFile("/DotNetBuild.Tasks.dll", destinationPath + "\\DotNetBuild.Tasks.dll");
    }

    private void extractFile(String resourceName, String destinationName) throws IOException {
        File destinationFile = new File(destinationName);
        if (destinationFile.exists())
            return;

        InputStream inputStream = getClass().getResourceAsStream(resourceName);
        OutputStream outputStream = new FileOutputStream(destinationName, false);

        byte[] buffer = new byte[4096];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        outputStream.close();
        inputStream.close();
    }
}
