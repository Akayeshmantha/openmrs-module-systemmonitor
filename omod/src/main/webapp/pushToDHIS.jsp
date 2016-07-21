<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="template/localHeader.jsp"%>

<openmrs:require privilege="View Administration Functions" otherwise="/login.htm" redirect="pushToDHIS.form" />

<h3>
	<spring:message code="systemmonitor.pushToDHIS" />
</h3>

<form method="post">
<input type="submit" value='<spring:message code="systemmonitor.pushOrSendData"/>'>
</form>
<br /><br />
<div id="dhis2ServerResponse">${response}</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>