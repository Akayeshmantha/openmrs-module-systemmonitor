<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="template/localHeader.jsp"%>
	
<openmrs:require privilege="View Administration Functions" otherwise="/login.htm" redirect="transferDHISData.form" />

<h3>
	<spring:message code="systemmonitor.transferDHISData" />
</h3>

<p>
	<spring:message code="systemmonitor.transferDHISData.description" />
</p>

<form method="post">
	<input type="hidden" id="form-action" name="formAction" />
	<input type="submit" id="dhis-export" value='<spring:message code="systemmonitor.transferDHISData.export" />'/>
	<br />
	<input type="file" name="dhisFile"/><input type="submit" id="dhis-import" value='<spring:message code="systemmonitor.transferDHISData.import"/>'>
</form>


<%@ include file="/WEB-INF/template/footer.jsp"%>