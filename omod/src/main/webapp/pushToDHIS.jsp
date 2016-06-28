<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<openmrs:htmlInclude file="/moduleResources/systemmonitor/renderjson.js" />

<h3>
	<spring:message code="systemmonitor.pusToDHIS" />
</h3>

<form method="post">
<input type="submit" value="Push/Send Data">
</form>
<div id="dhis2ServerResponse">${response}</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>