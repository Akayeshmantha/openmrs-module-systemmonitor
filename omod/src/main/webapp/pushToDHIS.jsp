<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="template/localHeader.jsp"%>
	
<openmrs:require privilege="View Administration Functions"
	otherwise="/login.htm" redirect="pushToDHIS.form" />

<h3>
	<spring:message code="systemmonitor.pushToDHIS" />
</h3>

<form method="post">
	<input type="submit"
		value='<spring:message code="systemmonitor.pushOrSendData"/>'>
</form>
<br />
<br />
<a id="downloadAnchorElem" style="display:none"></a>
<input type="button" id="dhis-push-from-client"
	value='<spring:message code="systemmonitor.clientPushing.pushFromClient" />' />
<p>
	<spring:message code="systemmonitor.clientPushing.description" />
</p>
<br />
<br />
<div id="dhis2ServerResponse">${response}</div>

<script type="text/javascript">
	jQuery(function() {
		jQuery("#dhis-push-from-client").click(function(event) {
			jQuery.ajax({
				type : "GET",
				url : "dataForClientPushing.form",
				success : function(data) {
					if (data != "" && data != undefined) {
						data = JSON.parse(data);
						
						//Download the data before pushing
						var auth = btoa(data.dhisUserName + ":" + data.dhisPassword);
						var url = data.dhisPostUrl;
						var dataStr = JSON.stringify(data.data);
						
						jQuery.ajax({
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
								jQuery("#dhis2ServerResponse").text(dhisResponse);
							},
							error: function(e) {
								var dataStr2 = "data:text/json;charset=utf-8," + encodeURIComponent(dataStr);
								var dlAnchorElem = document.getElementById('downloadAnchorElem');
								
								dlAnchorElem.setAttribute("href", dataStr2);
								dlAnchorElem.setAttribute("download", data.fileName);
								dlAnchorElem.click();
								jQuery("#dhis2ServerResponse").text(data.failureMessage);
							}
						});
					}
				}
			});
		});

	});
</script>

<%@ include file="/WEB-INF/template/footer.jsp"%>