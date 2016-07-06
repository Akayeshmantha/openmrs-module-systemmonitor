<%@ include file="/WEB-INF/template/include.jsp"%>
<openmrs:htmlInclude file="/moduleResources/systemmonitor/jquery.js" />
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="template/localHeader.jsp"%>

<div id="renderedReport">
	
<h3>
	<spring:message code="systemmonitor.localReport" />
</h3>

<br />
<div id="date"></div>

<style type="text/css">
	#renderReport, td {
    	border: 1px solid #15719a;
	}
</style>

<script type="text/javascript">
	var jsonData = ${reportData};
	var reportTableData = "";

	$(function() {
		for ( var i in jsonData) {
			if (jsonData[i].dataElementName === "Installed Modules") {
				var insiderTableData = "<table><tr><th>Name</th><th>Version</th><th>Author</th></tr>";
				var modules = jsonData[i].value;
	
				for ( var j in modules) {
					insiderTableData += "<tr><td>" + modules[j].name + "</td><td>"
							+ modules[j].version + "</td><td>" + modules[j].author + "</td></tr>";
				}
	
				insiderTableData += "</table>";
				reportTableData += "<tr><td>" + jsonData[i].orgUnitName + "</td><td>"
						+ jsonData[i].period + "</td><td>" + jsonData[i].dataElementName
						+ "</td><td>" + insiderTableData + "</td></tr>";
			} else if (jsonData[i].dataElementName === "Server's Real Location") {
				var insiderTableData = "<table><tr><th>Country</th><td>"
						+ jsonData[i].value.country
						+ "</tr><tr><th>Region</th><td>" + jsonData[i].value.region
						+ "</tr><tr><th>City</th><td>" + jsonData[i].value.city
						+ "</tr><tr><th>Location(Latitude,Longitude)</th><td>"
						+ jsonData[i].value.loc + "</tr><tr><th>HostName</th><td>"
						+ jsonData[i].value.hostname
						+ "</tr><tr><th>IP Address</th><td>" + jsonData[i].value.ip
						+ "</tr><tr><th>ISP</th><td>" + jsonData[i].value.org
						+ "</td></tr></table>";
	
				reportTableData += "<tr><td>" + jsonData[i].orgUnitName + "</td><td>"
						+ jsonData[i].period + "</td><td>" + jsonData[i].dataElementName
						+ "</td><td>" + insiderTableData + "</td></tr>";
			} else {
				reportTableData += "<tr><td>" + jsonData[i].orgUnitName + "</td><td>"
						+ jsonData[i].period + "</td><td>" + jsonData[i].dataElementName
						+ "</td><td>" + jsonData[i].value
						+ "</td></tr>";
			}
		}
	
		$('#renderReport tr:last').after(reportTableData);
		$("#date").html("Date: <b>" + new Date() + "</b>");
	});
	
	function printLatestReport(){
		var reActivateTableCss = '<style type="text/css">#renderReport, td {border: 1px solid #15719a;}</style>'
		var restorepage = document.body.innerHTML;
		var printcontent = document.getElementById("renderedReport").innerHTML;
		document.body.innerHTML = reActivateTableCss + printcontent;
		$("#print-report").hide();
		window.print();
		document.body.innerHTML = restorepage;
		$("#print-report").show();
	}
</script>


<input id="print-report" type="button" value='<spring:message code="systemmonitor.report.print" />' style="float:right" onclick="printLatestReport();"/>
<br /><br />

<table id="renderReport">
	<tr>
		<th><spring:message code="systemmonitor.report.site" /></th>
		<th><spring:message code="systemmonitor.report.period" /></th>
		<th><spring:message code="systemmonitor.report.indicator" /></th>
		<th><spring:message code="systemmonitor.report.value" /></th>
	</tr>
</table>
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>