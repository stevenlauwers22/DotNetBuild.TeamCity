<%@ include file="/include-internal.jsp"%>
<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="forms" tagdir="/WEB-INF/tags/forms" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="keys" class="dotNetBuild.teamCity.DotNetBuildConstants"/>
<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>

<div class="parameter">
    Assembly:
    <strong><props:displayValue name="${keys.assemblyKey}" emptyValue="not specified"/></strong>
</div>

<div class="parameter">
    Target:
    <strong><props:displayValue name="${keys.targetKey}" emptyValue="not specified"/></strong>
</div>

<div class="parameter">
    Configuration:
    <strong><props:displayValue name="${keys.configurationKey}" emptyValue="not specified"/></strong>
</div>

<div class="parameter">
    Commandline runner:
    <strong><props:displayValue name="${keys.commandLineRunnerPathKey}" emptyValue="not specified"/></strong>
</div>

<div class="parameter">
    Commandline arguments:
    <strong><props:displayValue name="${keys.commandLineArgumentKey}" emptyValue="not specified"/></strong>
</div>

<div class="parameter">
    Working directory:
    <strong><props:displayValue name="${keys.workingDirectoryKey}" emptyValue="not specified"/></strong>
</div>