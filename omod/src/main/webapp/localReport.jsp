<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="template/localHeader.jsp"%>

<h3>
	<spring:message code="systemmonitor.localReport" />
</h3>

<script type="text/javascript">
	var jsonData = ${reportData};
	var reportTableData = "";

	for ( var i in jsonData) {
		if (jsonData[i].dataElementName === "Installed Modules") {
			var insiderTableData = "<table><tr><th>Name</th><th>Version</th><th>Author</th></tr>";
			var modules = jsonData[i].value;

			for ( var j in modules) {
				insiderTableData += "<tr><td>" + modules[j].name + "</td><td>"
						+ modules[j].version + "</td><td>" + modules[j].author
						+ "</td></tr>";
			}

			insiderTableData += "</table>";
			reportTableData += "<tr><td>" + jsonData[i].dataElementName
					+ "</td><td>" + jsonData[i].orgUnitName + "</td><td>"
					+ jsonData[i].period + "</td><td>" + insiderTableData
					+ "</td></tr>";
		} else if (jsonData[i].dataElementName === "Server's Real Location") {
			var insiderTableData = "<table><tr><td>Country</td><td>"
					+ jsonData[i].value.country
					+ "</tr><tr><td>Region</td><td>" + jsonData[i].value.region
					+ "</tr><tr><td>City</td><td>" + jsonData[i].value.city
					+ "</tr><tr><td>Location(Latitude,Longitude)</td><td>"
					+ jsonData[i].value.loc + "</tr><tr><td>HostName</td><td>"
					+ jsonData[i].value.hostname
					+ "</tr><tr><td>IP Address</td><td>" + jsonData[i].value.ip
					+ "</tr><tr><td>ISP</td><td>" + jsonData[i].value.org
					+ "</td></tr></table>";

			reportTableData += "<tr><td>" + jsonData[i].dataElementName
					+ "</td><td>" + jsonData[i].orgUnitName + "</td><td>"
					+ jsonData[i].period + "</td><td>" + insiderTableData
					+ "</td></tr>";
		} else {
			reportTableData += "<tr><td>" + jsonData[i].dataElementName
					+ "</td><td>" + jsonData[i].orgUnitName + "</td><td>"
					+ jsonData[i].period + "</td><td>" + jsonData[i].value
					+ "</td></tr>";
		}
	}

	jQuery("#renderReport").html(reportTableData);
</script>

<table>
	<tr>
		<th><spring:message code="systemmonitor.report.indicator" /></th>
		<th><spring:message code="systemmonitor.report.site" /></th>
		<th><spring:message code="general.report.period" /></th>
		<th><spring:message code="general.report.value" /></th>
	</tr>
	<div id="renderReport"></div>

</table>

<%@ include file="/WEB-INF/template/footer.jsp"%>