<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="template/localHeader.jsp"%>

<openmrs:require privilege="View Administration Functions"
	otherwise="/login.htm" redirect="activityMonitor.form" />

<openmrs:htmlInclude
	file="/moduleResources/systemmonitor/smoothie.js" />

<h3>
	<spring:message code="systemmonitor.activityMonitor" />
</h3>

<script type="text/javascript">
	var activityMonitorInfo,
	systemLoad = new TimeSeries(),
	cpuTemperature = new TimeSeries(),
	cpuVoltage = new TimeSeries(),
	processorUpTime = new TimeSeries(),
	openmrsUpTime = new TimeSeries(),
	openmrsDownTime = new TimeSeries(),
	usedMemory = new TimeSeries();
	
	jQuery(function() {
		activityMonitorInfo = ${activityMonitorInfo};
		jQuery("#activity-monitors").tabs();
	});
	
	function renderActivityMonitors() {
		var smoothieCpu = new SmoothieChart();
		var smoothieMemory = new SmoothieChart();
		var smoothieUptime = new SmoothieChart();
		smoothieCpu.addTimeSeries(systemLoad, { strokeStyle:'cyan', lineWidth:2 });
		smoothieCpu.addTimeSeries(cpuTemperature, { strokeStyle:'#009384', lineWidth:2 });
		smoothieCpu.addTimeSeries(cpuVoltage);
		smoothieCpu.streamTo(document.getElementById("cpu-canvas"), 1000);
		
		smoothieMemory.addTimeSeries(usedMemory, { strokeStyle:'cyan', lineWidth:2 });
		smoothieMemory.streamTo(document.getElementById("memory-canvas"), 1000);
		
		//smoothieUptime.addTimeSeries(processorUpTime);
		smoothieUptime.addTimeSeries(openmrsDownTime, { strokeStyle:'gray', lineWidth:2 });
		smoothieUptime.addTimeSeries(openmrsUpTime, { strokeStyle:'cyan', lineWidth:2 });
		smoothieUptime.streamTo(document.getElementById("uptime-canvas"), 1000);
	}
	
	setInterval(function(){
		fetchAndRenderActivityMonitorInfo()
	}, 1000);
	
	function fetchAndRenderActivityMonitorInfo() {
		jQuery.ajax({
			type : "GET",
			url : "activityMonitorInfo.form",
			datatype: "json",
			success : function(activityMonitorData) {
				activityMonitorInfo = JSON.parse(activityMonitorData);
				systemLoad.append(new Date().getTime(), activityMonitorInfo.systemLoad);
				cpuTemperature.append(new Date().getTime(), activityMonitorInfo.cpuTemperature);
				cpuVoltage.append(new Date().getTime(), activityMonitorInfo.cpuVoltage);
				//processorUpTime.append(new Date().getTime(), activityMonitorInfo.processorUpTime);
				openmrsUpTime.append(new Date().getTime(), activityMonitorInfo.openMRsUpTimePercentage);
				openmrsDownTime.append(new Date().getTime(), activityMonitorInfo.openMRsDownTimePercentage);
				usedMemory.append(new Date().getTime(), activityMonitorInfo.usedMemory/activityMonitorInfo.totalMemory * 100);//percentage
				
				usedMemory.minValue = 0;
				usedMemory.maxValue = 100;
				openmrsUpTime.minValue = 0;
				openmrsUpTime.maxValue = 100;
				openmrsDownTime.minValue = 0;
				openmrsDownTime.maxValue = 100;
				systemLoad.maxValue = activityMonitorInfo.systemLoad + 1;
				
				jQuery("#cpu-label").text("System Load: " + activityMonitorInfo.systemLoad);
				jQuery("#memory-label").text("Used Memory (MB): " + activityMonitorInfo.usedMemory + "/" + activityMonitorInfo.totalMemory + " = " + activityMonitorInfo.usedMemory/activityMonitorInfo.totalMemory * 100 + " %");
				jQuery("#uptime-label").text("Uptime: " + activityMonitorInfo.openMRsUpTimePercentage + "%, Downtime: " + activityMonitorInfo.openMRsDownTimePercentage + "%");
			}
		});
	}
</script>

<body onload="renderActivityMonitors()">
	<div id="activity-monitors">
		<ul>
			<li><a href="#cpu"><spring:message
						code="systemmonitor.activityMonitor.cpu" /></a></li>
			<li><a href="#memory"><spring:message
						code="systemmonitor.activityMonitor.memory" /></a></li>
			<li><a href="#uptime"><spring:message
						code="systemmonitor.activityMonitor.uptime" /></a></li>
		</ul>

		<div id="cpu">
			<canvas id="cpu-canvas" height="200" width="800"></canvas>
			<label id="cpu-label"></label>
		</div>
		<div id="memory">
			<canvas id="memory-canvas" height="200" width="800"></canvas>
			<label id="memory-label"></label>
		</div>
		<div id="uptime">
			<canvas id="uptime-canvas" height="200" width="800"></canvas>
			<label id="uptime-label"></label>
		</div>
	</div>
</body>

<%@ include file="/WEB-INF/template/footer.jsp"%>
