<%@ include file="/WEB-INF/template/include.jsp"%>
<openmrs:htmlInclude file="/moduleResources/systemmonitor/jquery.js" />
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="template/localHeader.jsp"%>

<script src="<%= request.getContextPath()%>/dwr/interface/DWRSystemMonitorService.js"></script>

<openmrs:require privilege="View Administration Functions" otherwise="/login.htm" redirect="pushToDHIS.form" />

<h3>
	<spring:message code="systemmonitor.pushToDHIS" />
</h3>

<form method="post">
<input type="submit" value='<spring:message code="systemmonitor.pushOrSendData"/>'>
</form>
<br /><br />
<a id="downloadAnchorElem" style="display:none"></a>
<input type="button" id="dhis-push-from-client" value='<spring:message code="systemmonitor.clientPushing.pushFromClient" />' />
<p>
 	<spring:message code="systemmonitor.clientPushing.description" />
</p>
<br /><br />
<div id="dhis2ServerResponse" style="font-weight:bold;font-size:1.15em;">${response}</div>

<script type="text/javascript">
	function pushToDHISOrExportOnFailure(data) {
		if (data != "" && data != undefined) {
				data = JSON.parse(data);
				
				var auth = btoa(data.dhisUserName + ":" + data.dhisPassword);
				var url = data.dhisPostUrl;
				var dataStr = JSON.stringify(data.data);
				
				$.ajax({
					headers: {
						"content-type": "application/json",
						"authorization": "Basic " + auth,
						"cache-control": "no-cache"
					},
					crossDomain: true,
					processData: false,
					url : url,
				    method : "POST",
				    data: dataStr,
					success : function(dhisResponse) {
						$("#dhis2ServerResponse").text(dhisResponse);
					},
					error: function(e) {
						//download the data if pushing failed because of no TODO (OpenHIM interoperability layer) to wire the request through
						var dataStr2 = "data:text/json;charset=utf-8," + encodeURIComponent(dataStr);
						var dlAnchorElem = document.getElementById('downloadAnchorElem');
						
						dlAnchorElem.setAttribute("href", dataStr2);
						dlAnchorElem.setAttribute("download", data.fileName);
						dlAnchorElem.click();
						$("#dhis2ServerResponse").text(data.failureMessage);
					}
				});
			}
	}
	
	$(function() {
		$("#dhis-push-from-client").click(function(event) {
			DWRSystemMonitorService.getDataForClientPushing(pushToDHISOrExportOnFailure);
		});

	});
</script>

<%@ include file="/WEB-INF/template/footer.jsp"%>