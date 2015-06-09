<%@ include file="/include-internal.jsp"%>
<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="forms" tagdir="/WEB-INF/tags/forms" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="keys" class="dotNetBuild.teamCity.DotNetBuildConstants"/>
<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>

<l:settingsGroup title="DotNetBuild">
<tr>
  <th>Assembly:<l:star/></th>
  <td>
    <props:textProperty name="${keys.assemblyKey}" className="longField"/>
    <span class="error" id="error_${keys.assemblyKey}"></span>
    <span class="smallNote">The assembly containing your build script, relative from the checkout directory<./span>
  </td>
</tr>
<tr>
  <th>Target:<l:star/></th>
  <td>
    <props:textProperty name="${keys.targetKey}" className="longField"/>
    <span class="error" id="error_${keys.targetKey}"></span>
    <span class="smallNote">The target to run.</span>
  </td>
</tr>
<tr>
  <th>Configuration:</th>
  <td>
    <props:textProperty name="${keys.configurationKey}" className="longField"/>
    <span class="error" id="error_${keys.configurationKey}"></span>
    <span class="smallNote">The configuration to apply.</span>
  </td>
</tr>
</l:settingsGroup>

<l:settingsGroup title="DotNetBuild - advanced">
<tr>
  <th>Commandline arguments:</th>
  <td>
    <props:multilineProperty name="${keys.commandLineArgumentsKey}" className="longField" cols="30" rows="4" expanded="true" linkTitle="Additional commandline arguments"/>
    <span class="error" id="error_${keys.commandLineArgumentsKey}"></span>
    <span class="smallNote">
        Additional commandline arguments that will be passed to the DotNetBuild runner.<br />
        These arguments are key-value pairs separated by a semicolon (e.g. myParam:someValue).
    </span>
  </td>
</tr>
<tr>
  <th>Commandline runner:</th>
  <td>
    <props:textProperty name="${keys.commandLineRunnerPathKey}" className="longField"/>
    <span class="error" id="error_${keys.commandLineRunnerPathKey}"></span>
    <span class="smallNote">
        The path to the directory that contains your DotNetBuild commandline runner.<br />
        This can be an absolute path or a path relative from the checkout directory.<br />
        Leave empty if you want to use the built in TeamCity runner (version 1.1.0.0).
    </span>
  </td>
</tr>
<tr>
  <th>Working directory:</th>
  <td>
    <props:textProperty name="${keys.workingDirectoryKey}" className="longField"/>
    <span class="error" id="error_${keys.workingDirectoryKey}"></span>
    <span class="smallNote">
        The working directory your DotNetBuild script will run from, this will be the base directory of any relative paths you have in your DotNetBuild script.<br />
        This can be an absolute path or a path relative from the checkout directory.<br />
        Leave empty if you want to use the default working directory (which is the checkout directory).
    </span>
  </td>
</tr>
</l:settingsGroup>