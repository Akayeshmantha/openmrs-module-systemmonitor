<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="template/localHeader.jsp"%>

<openmrs:require privilege="View Administration Functions" otherwise="/login.htm" redirect="runAsSoonAsStarted.form" />

<h3>
	<spring:message code="systemmonitor.runAsSoonAsStarted" />
</h3>

<p><spring:message code="systemmonitor.runAsSoonAsStarted.info" /></p>

<form method="post">
	<input type="submit" value='<spring:message code="systemmonitor.runAsSoonAsStarted.run" />'/>
</form>

<%@ include file="/WEB-INF/template/footer.jsp"%>