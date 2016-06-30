<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="template/localHeader.jsp"%>

<h3>
	<spring:message code="systemmonitor.pusToDHIS" />
</h3>

<form method="post">
<input type="submit" value='<spring:message code="systemmonitor.pushOrSendData"/>'>
</form>
<br /><br />
<div id="dhis2ServerResponse">${response}</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>