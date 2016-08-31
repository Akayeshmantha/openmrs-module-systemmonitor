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
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.systemmonitor.ConfigurableGlobalProperties;
import org.openmrs.module.systemmonitor.ConfigureGPs;
import org.openmrs.module.systemmonitor.SystemMonitorConstants;
import org.openmrs.module.systemmonitor.api.SystemMonitorService;
import org.openmrs.module.systemmonitor.distributions.RwandaSPHStudyEMT;
import org.openmrs.module.systemmonitor.indicators.OSAndHardwareIndicators;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
	}

	@RequestMapping(value = "/module/systemmonitor/pushToDHIS", method = RequestMethod.POST)
	public void pushToDHIS(ModelMap model) {
		JSONObject response = Context.getService(SystemMonitorService.class).pushMonitoredDataToDHIS();

		model.addAttribute("response", response);
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
		JSONObject generatedDHISDataValueSetJSON = emt.generatedDHISDataValueSetJSON();

		model.addAttribute("orgUnit", Context.getService(SystemMonitorService.class).getDHISConfiguredOrgUnitName());
		model.addAttribute("reportData", generatedDHISDataValueSetJSON != null
				? generatedDHISDataValueSetJSON.getJSONObject("allData").getJSONArray("dataValues") : "undefined");
	}

	@RequestMapping(value = "/module/systemmonitor/activityMonitorInfo", method = RequestMethod.GET)
	public @ResponseBody String getActiveMonitorInfo() {
		JSONObject json = createClientActivityMonitorInfor();

		return json.toString();
	}

	private JSONObject createClientActivityMonitorInfor() {
		JSONObject json = new JSONObject();
		Double systemLoad = ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage();
		Double cpuTemperature = OSAndHardwareIndicators.CPU_TEMPERATURE;
		Double cpuVoltage = OSAndHardwareIndicators.CPU_VOLTAGE;
		Long processorUpTime = OSAndHardwareIndicators.PROCESSOR_SYSTEM_UPTIME;
		Integer openmrsUpTime = Context.getService(SystemMonitorService.class).getOpenMRSSystemUpTime().intValue();
		Long totalMemory = OSAndHardwareIndicators.MEMORY_TOTAL;
		Long usedMemory = OSAndHardwareIndicators.MEMORY_USED;
		Long availableMemory = OSAndHardwareIndicators.MEMORY_AVAILABLE;

		json.put("systemLoad", systemLoad);
		json.put("cpuTemperature", cpuTemperature);
		json.put("cpuVoltage", cpuVoltage);
		json.put("processorUpTime", processorUpTime);
		json.put("openmrsUpTime", openmrsUpTime);
		json.put("totalMemory", totalMemory);
		json.put("usedMemory", usedMemory);
		json.put("availableMemory", availableMemory);

		return json;
	}

	@RequestMapping(value = "/module/systemmonitor/activityMonitor", method = RequestMethod.GET)
	public void getActiveMonitor(ModelMap model) {
		model.put("activityMonitorInfo", createClientActivityMonitorInfor());
	}

	@RequestMapping(value = "/module/systemmonitor/transferDHISData", method = RequestMethod.GET)
	public void renderTransferDHISData(ModelMap model) {

	}

	@RequestMapping(value = "/module/systemmonitor/transferDHISData", method = RequestMethod.POST)
	public String postTransferDHISData(ModelMap model,
			@RequestParam(value = "dhisFile", required = false) MultipartFile mapping,
			@RequestParam(value = "formAction") String formAction) {
		String data = "";

		if (StringUtils.isNotBlank(formAction)) {
			// supplies data to be pushed at client level
			if (formAction.equals("Push")) {
				JSONObject allDHISValuesJSON = prepareClientMonitoredData();

				data = allDHISValuesJSON.toString();
			}
		}

		return data;
	}

	@RequestMapping(value = "/module/systemmonitor/dataForClientPushing", method = RequestMethod.GET)
	public @ResponseBody String getDataForClientPushing() {
		String dhisUserName = Context.getAdministrationService()
				.getGlobalProperty(ConfigurableGlobalProperties.DHIS_USERNAME);
		String dhisPassword = Context.getAdministrationService()
				.getGlobalProperty(ConfigurableGlobalProperties.DHIS_PASSWORD);
		String dhisPostUrl = (Context.getAdministrationService()
				.getGlobalProperty(ConfigurableGlobalProperties.DHISAPI_URL)) != null
						? Context.getAdministrationService().getGlobalProperty(ConfigurableGlobalProperties.DHISAPI_URL)
								+ SystemMonitorConstants.DHIS_API_DATAVALUES_URL
						: "";
		String systemId = OSAndHardwareIndicators.getHostName() + "-" + (OSAndHardwareIndicators.getMacAddress() != null
				? OSAndHardwareIndicators.getMacAddress().replace(":", "") : "");
		JSONObject allDataValues = prepareClientMonitoredData();
		JSONObject allDHISValuesJSON = new JSONObject();

		allDHISValuesJSON.put("data", allDataValues);
		allDHISValuesJSON.put("dhisUserName", dhisUserName);
		allDHISValuesJSON.put("dhisPassword", dhisPassword);
		allDHISValuesJSON.put("dhisPostUrl", dhisPostUrl);
		allDHISValuesJSON.put("fileName",
				systemId + "-dhisData-" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".json");
		allDHISValuesJSON.put("failureMessage",
				Context.getMessageSourceService().getMessage("systemmonitor.clientPushing.failureMessage"));

		return allDataValues.length() > 0 ? allDHISValuesJSON.toString() : "";
	}

	private JSONObject prepareClientMonitoredData() {
		JSONObject allDataValues = new JSONObject();
		JSONArray dataToBePushedOrExported = Context.getService(SystemMonitorService.class)
				.fetchDataToBePushedAtClientLevelOrExported();

		if (dataToBePushedOrExported.length() > 0)
			allDataValues.put("dataValues", dataToBePushedOrExported);

		return allDataValues;
	}
}
