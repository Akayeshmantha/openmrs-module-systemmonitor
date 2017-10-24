/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.systemmonitor.web.controller;

import java.lang.management.ManagementFactory;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.systemmonitor.ConfigureGPs;
import org.openmrs.module.systemmonitor.api.SystemMonitorService;
import org.openmrs.module.systemmonitor.distributions.RwandaSPHStudyEMT;
import org.openmrs.module.systemmonitor.indicators.OSAndHardwareIndicators;
import org.openmrs.module.systemmonitor.memory.MemoryAggregation;
import org.openmrs.module.systemmonitor.uptime.OpenmrsUpAndDownTracker;
import org.openmrs.module.systemmonitor.uptime.UpOrDownTimeInterval;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The main controller.
 */
@Controller
public class SystemMonitorMainController {
	@RequestMapping(value = "/module/systemmonitor/dhisDataValues", method = RequestMethod.GET)
	public void dhisDataValuesViewer(ModelMap model) {
		RwandaSPHStudyEMT emt = new RwandaSPHStudyEMT();

		model.addAttribute("data", emt.generatedDHISDataValueSetJSON());
	}

	@RequestMapping(value = "/module/systemmonitor/pushToDHIS", method = RequestMethod.GET)
	public void renderPushToDHIS(ModelMap model) {
		model.addAttribute("response", "");
		model.addAttribute("numberofBackedUps", Context.getService(SystemMonitorService.class).numberofBackedUpDataFiles());
	}

	@RequestMapping(value = "/module/systemmonitor/pushToDHIS", method = RequestMethod.POST)
	public void pushToDHIS(ModelMap model) {
		String response = Context.getService(SystemMonitorService.class).pushMonitoredDataToDHIS();

		model.addAttribute("numberofBackedUps", Context.getService(SystemMonitorService.class).numberofBackedUpDataFiles());
		model.addAttribute("response", response != null ? response
				: Context.getMessageSourceService().getMessage("systemmonitor.pushToDHIS.serverInternetFailure"));
	}

	@RequestMapping(value = "/module/systemmonitor/configurations", method = RequestMethod.GET)
	public void renderConfigurations(ModelMap model) {
		model.addAttribute("configurations", new ConfigureGPs());
	}

	@RequestMapping(value = "/module/systemmonitor/configurations", method = RequestMethod.POST)
	public void postConfigurations(ModelMap model, HttpServletRequest request) {
		ConfigureGPs configs = new ConfigureGPs();

		try {
			configs.updateAndPersistConfigurableGPs(request);
			Context.getService(SystemMonitorService.class).updateLocallyStoredDHISMetadata();
			Context.getService(SystemMonitorService.class).cleanOldLocallyStoredLogsAndDHISData();
			request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR,
					"systemmonitor.configurations.save.success");
		} catch (Exception e) {
			request.getSession().setAttribute(WebConstants.OPENMRS_ERROR_ATTR, e);
		}
		model.addAttribute("configurations", configs);
	}

	@RequestMapping(value = "/module/systemmonitor/localReport", method = RequestMethod.GET)
	public void renderLocalReport(ModelMap model) {
		RwandaSPHStudyEMT emt = new RwandaSPHStudyEMT();

		model.addAttribute("orgUnit", Context.getService(SystemMonitorService.class).getDHISConfiguredOrgUnitName());
		model.addAttribute("reportData",
				emt.generatedDHISDataValueSetJSON() != null
						? emt.generatedDHISDataValueSetJSON().getJSONObject("allData").getJSONArray("dataValues")
						: "undefined");
	}

	@RequestMapping(value = "/module/systemmonitor/runAsSoonAsStarted", method = RequestMethod.GET)
	public void renderAutoRun(ModelMap model) {
	}

	@RequestMapping(value = "/module/systemmonitor/runAsSoonAsStarted", method = RequestMethod.POST)
	public void postAutoRun(ModelMap model, HttpServletRequest request) {
		Context.getService(SystemMonitorService.class).transferDHISMappingsAndMetadataFileToDataDirectory();
		//Context.getService(SystemMonitorService.class).updateLocallyStoredDHISMetadata();
		Context.getService(SystemMonitorService.class).rebootSystemMonitorTasks();

		request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "systemmonitor.runAsSoonAsStarted.success");
	}

	/*@RequestMapping(value = "/module/systemmonitor/activityMonitorInfo", method = RequestMethod.GET)
	public String String getActiveMonitorInfo() {
		JSONObject json = createClientActivityMonitorInfor();

		return json.toString();
	}*/

	private JSONObject createClientActivityMonitorInfor() {
		JSONObject json = new JSONObject();
		OSAndHardwareIndicators oshi = new OSAndHardwareIndicators();
		Double systemLoad = ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage();
		Double cpuTemperature = oshi.CPU_TEMPERATURE;
		Double cpuVoltage = oshi.CPU_VOLTAGE;
		Long processorUpTime = oshi.PROCESSOR_SYSTEM_UPTIME;
		Integer openmrsUpTime = Context.getService(SystemMonitorService.class).getOpenMRSSystemUpTime().intValue();
		Long totalMemory = oshi.MEMORY_TOTAL;
		Long usedMemory = MemoryAggregation.getAggregatedUsedMemory();
		Long availableMemory = oshi.MEMORY_AVAILABLE;
		Integer openingHour = Integer.parseInt(Context.getService(SystemMonitorService.class).getConfiguredOpeningHour()
				.getPropertyValue().substring(0, 2));
		Integer closingHour = Integer.parseInt(Context.getService(SystemMonitorService.class).getConfiguredClosingHour()
				.getPropertyValue().substring(0, 2));
		Calendar date = Calendar.getInstance();
		date.setTime(Context.getService(SystemMonitorService.class).getEvaluationAndReportingDate());
		Integer workingMinutesDifference = ((closingHour - openingHour) - 1) * 60;
		Object[] openmrsUpAndDownTime = OpenmrsUpAndDownTracker.calculateOpenMRSUpAndDowntimeBy(date.getTime(), null);
		Integer openmrsUptime = (Integer) openmrsUpAndDownTime[0];
		Integer openmrsDowntime = (Integer) openmrsUpAndDownTime[1];
		List<UpOrDownTimeInterval> upIntervals = (List<UpOrDownTimeInterval>) openmrsUpAndDownTime[2];
		List<UpOrDownTimeInterval> downIntervals = (List<UpOrDownTimeInterval>) openmrsUpAndDownTime[3];
		Integer openMRsUpTimePercentage = openmrsUptime != null ? (openmrsUptime * 100) / workingMinutesDifference
				: null;
		Integer openMRsDownTimePercentage = openmrsDowntime != null ? (openmrsDowntime * 100) / workingMinutesDifference
				: null;

		json.put("systemLoad", systemLoad);
		json.put("cpuTemperature", cpuTemperature);
		json.put("cpuVoltage", cpuVoltage);
		json.put("processorUpTime", processorUpTime);
		json.put("openmrsUpTime", openmrsUpTime);
		json.put("totalMemory", totalMemory);
		json.put("usedMemory", usedMemory);
		json.put("availableMemory", availableMemory);
		json.put("openMRsUpTimePercentage", openMRsUpTimePercentage);
		json.put("openMRsDownTimePercentage", openMRsDownTimePercentage);

		return json;
	}

	@RequestMapping(value = "/module/systemmonitor/activityMonitor", method = RequestMethod.GET)
	public void getActiveMonitor(ModelMap model) {
		model.put("activityMonitorInfo", createClientActivityMonitorInfor());
	}

}
