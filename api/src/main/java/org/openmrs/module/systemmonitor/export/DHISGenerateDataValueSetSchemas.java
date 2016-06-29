package org.openmrs.module.systemmonitor.export;

import java.io.File;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.systemmonitor.SystemMonitorConstants;
import org.openmrs.module.systemmonitor.api.SystemMonitorService;
import org.openmrs.module.systemmonitor.curl.CurlEmulator;
import org.openmrs.module.systemmonitor.indicators.OSAndHardwareIndicators;
import org.openmrs.module.systemmonitor.indicators.SystemPropertiesIndicators;
import org.openmrs.module.systemmonitor.mapping.DHISMapping;
import org.openmrs.web.WebConstants;

public class DHISGenerateDataValueSetSchemas {

	/**
	 * @TODO re-write this from a string-json parse approach to json itself
	 * 
	 * @return dataValueSets json object
	 * @throws UnknownHostException
	 */
	public static JSONObject generateRwandaSPHEMTDHISDataValueSets() {
		Calendar cal = Calendar.getInstance();
		Date today = new Date();
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd");
		File mappingsFile = SystemMonitorConstants.SYSTEMMONITOR_FINAL_MAPPINGFILE;
		JSONObject jsonObj = new JSONObject();
		JSONObject jsonObj2 = new JSONObject();
		JSONObject finalJSON = new JSONObject();
		JSONArray jsonToBePushed;
		JSONArray jsonDataValueSets = new JSONArray();
		SystemMonitorService systemMonitorService = Context.getService(SystemMonitorService.class);

		String systemId = OSAndHardwareIndicators.getHostName() + "-" + (OSAndHardwareIndicators.getMacAddress() != null
				? OSAndHardwareIndicators.getMacAddress().replace(":", "") : "");

		String dhisOrganizationUnitUid = DHISMapping
				.getDHISMappedObjectValue(systemMonitorService.getCurrentConfiguredDHISOrgUnit());

		String clinicDays = null;

		String clinicHours = null;

		String openmrsAPPName = WebConstants.WEBAPP_NAME;

		Integer startupCounts = null;

		/*
		 * TODO evaluate this for a week and respectively
		 */
		Long thisWeekUptime = OSAndHardwareIndicators.PROCESSOR_SYSTEM_UPTIME;

		Integer previousWeekUptime = null;

		Integer previousMonthUptime = null;

		Long freeMemory = OSAndHardwareIndicators.MEMORY_AVAILABLE;

		Long usedMemory = OSAndHardwareIndicators.MEMORY_USED;

		Long totalMemory = OSAndHardwareIndicators.MEMORY_TOTAL;

		Integer openmrsUptimePercentage = null;

		String operatingSystem = SystemPropertiesIndicators.OS_NAME + ", Family: " + OSAndHardwareIndicators.OS_FAMILY
				+ ", Manufacturer: " + OSAndHardwareIndicators.OS_MANUFACTURER + ", Version Name: "
				+ OSAndHardwareIndicators.OS_VERSION_NAME + ", Version Number: "
				+ OSAndHardwareIndicators.OS_VERSION_NUMBER + ", Build Number: "
				+ OSAndHardwareIndicators.OS_VERSION_BUILDNUMBER;

		String operatingSystemArch = SystemPropertiesIndicators.OS_ARCH;

		String operatingSystemVersion = SystemPropertiesIndicators.OS_VERSION;

		String javaVersion = SystemPropertiesIndicators.JAVA_VERSION;

		String javaVendor = SystemPropertiesIndicators.JAVA_VENDOR;

		String jvmVersion = SystemPropertiesIndicators.JVM_VERSION;

		String jvmVendor = SystemPropertiesIndicators.JVM_VENDOR;

		String javaRuntimeName = SystemPropertiesIndicators.JAVA_RUNTIMENAME;

		String javaRuntimeVersion = SystemPropertiesIndicators.JAVA_RUNTIMEVERSION;

		String userName = SystemPropertiesIndicators.USERNAME;

		String systemLanguage = SystemPropertiesIndicators.SYSTEM_LANGUAGE;

		String systemTimezone = SystemPropertiesIndicators.SYSTEM_TIMEZONE;

		String userDirectory = SystemPropertiesIndicators.USER_DIRECTORY;

		String fileSystemEncoding = SystemPropertiesIndicators.FILESYSTEM_ENCODING;

		String systemDateTime = Calendar.getInstance(Context.getLocale()).getTime().toString();

		String openmrsVersion = SystemMonitorConstants.OPENMRS_VERSION;

		String tempDirectory = SystemPropertiesIndicators.TEMP_FOLDER;

		JSONObject serverRealLocation = CurlEmulator
				.get(SystemMonitorConstants.IP_INFO_URL + OSAndHardwareIndicators.getIpAddress(), null, null);

		Integer encounterTotal = systemMonitorService.rwandaPIHEMTGetTotalEncounters();

		Integer obsTotal = systemMonitorService.rwandaPIHEMTGetTotalObservations();

		Integer totalUsers = systemMonitorService.rwandaPIHEMTGetTotalUsers();

		Integer totalPatientActive = systemMonitorService.rwandaPIHEMTGetTotalActivePatients();

		Integer totalPatientNew = systemMonitorService.rwandaPIHEMTGetTotalNewPatients();

		Integer totalVisits = systemMonitorService.rwandaPIHEMTGetTotalVisits();

		Integer viralLoadTestResultsEver = systemMonitorService.getTotalViralLoadTestsEver();

		Integer viralLoadTestResultsLastSixMonths = systemMonitorService.getTotalViralLoadTestsLastSixMonths();

		Integer viralLoadTestResultsLastYear = systemMonitorService.getTotalViralLoadTestsLastYear();

		if (StringUtils.isBlank(dhisOrganizationUnitUid)) {
			dhisOrganizationUnitUid = "";
		}
		// TODO restructure or refactor this file as;
		// http://dhis2.github.io/dhis2-docs/master/en/developer/html/dhis2_developer_manual_full.html#d6543e3472
		if (mappingsFile.exists() && mappingsFile.isFile()) {
			cal.setTime(today);// TODO must it be hard coded to one day range
								// alone!!! or we use start and end dates
			String systemIdDataElement = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemId") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": \""
					+ systemId + "\"}";
			String primaryClinicDaysDataElement = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_primaryCareDays") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": \""
					+ clinicDays + "\"}";
			String primaryClinicHoursDataElement = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_primaryCareHours") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": \""
					+ clinicHours + "\"}";
			String openMRSAppNameDataElement = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_openmrsAppName") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": \""
					+ openmrsAPPName + "\"}";
			String encounterDataElement = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_totalEncounters") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": "
					+ encounterTotal + "}";
			String obsDataElement = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_totalObservations") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": "
					+ obsTotal + "}";
			String userDataElement = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_totalUsers") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": "
					+ totalUsers + "}";
			String patientActiveDataElement = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_totalPatientsActive") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": "
					+ totalPatientActive + "}";
			String patientNewDataElement = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_totalPatientsNew") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": "
					+ totalPatientNew + "}";
			String visitsDataElement = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_totalVisits") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": "
					+ totalVisits + "}";
			String systemStartupsDataElement = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemStartupCounts") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": "
					+ startupCounts + "}";
			String upTimeThisWeekDataElement = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemUptime-thisWeek") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": "
					+ thisWeekUptime + "}";
			String upTimeLastWeekDataElement = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemUptime-lastWeek") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": "
					+ previousWeekUptime + "}";
			String upTimeLastMonthDataElement = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemUptime-lastMonth") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": "
					+ previousMonthUptime + "}";
			String freeMemoryDataElement = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_freeMemory") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": "
					+ freeMemory + "}";
			String totalMemoryDataElement = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_totalMemory") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": "
					+ totalMemory + "}";
			String usedMemoryDataElement = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_usedMemory") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": "
					+ usedMemory + "}";
			String totalOpenMRSUptimeDataElement = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_openmrsUptime") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": "
					+ openmrsUptimePercentage + "}";
			String viralLoadTestResults_everDataElement = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_patientsViralLoadTestResults_ever")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": " + viralLoadTestResultsEver + "}";
			String viralLoadTestResults_last6MonthsDataElement = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_patientsViralLoadTestResults_last6Months")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": " + viralLoadTestResultsLastSixMonths + "}";
			String viralLoadTestResults_lastYearDataElement = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_patientsViralLoadTestResults_LastYear")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": " + viralLoadTestResultsLastYear + "}";
			String systemInfo_operatingSystem = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemInfo_operatingSystemName")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": \"" + operatingSystem + "\"}";
			String systemInfo_operatingSystemArch = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemInfo_operatingSystemArch")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": \"" + operatingSystemArch + "\"}";
			String systemInfo_operatingSystemVersion = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemInfo_operatingSystemVersion")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": \"" + operatingSystemVersion + "\"}";
			String systemInfo_javaVersion = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemInfo_javaVersion") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": \""
					+ javaVersion + "\"}";
			String systemInfo_javaVendor = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemInfo_javaVendor") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": \""
					+ javaVendor + "\"}";
			String systemInfo_jvmVersion = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemInfo_jvmVersion") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": \""
					+ jvmVersion + "\"}";
			String systemInfo_jvmVendor = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemInfo_jvmVendor") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": \""
					+ jvmVendor + "\"}";
			String systemInfo_javaRuntimeName = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemInfo_javaRuntimeName")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": \"" + javaRuntimeName + "\"}";
			String systemInfo_javaRuntimeVersion = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemInfo_javaRuntimeVersion")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": \"" + javaRuntimeVersion + "\"}";
			String systemInfo_userName = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemInfo_userName") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": \""
					+ userName + "\"}";
			String systemInfo_systemLanguage = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemInfo_systemLanguage")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": \"" + systemLanguage + "\"}";
			String systemInfo_systemTimezone = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemInfo_systemTimezone")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": \"" + systemTimezone + "\"}";
			String systemInfo_systemDateTime = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemInfo_systemDateTime")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": \"" + systemDateTime + "\"}";
			String systemInfo_fileSystemEncoding = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemInfo_fileSystemEncoding")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": \"" + fileSystemEncoding + "\"}";
			String systemInfo_userDirectory = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemInfo_userDirectory")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": \"" + userDirectory + "\"}";
			String systemInfo_tempDirectory = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemInfo_tempDirectory")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": \"" + tempDirectory + "\"}";
			String systemInfo_openMRSVersion = "{ \"dataElement\": \""
					+ DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemInfo_openMRSVersion")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": \"" + openmrsVersion + "\"}";

			JSONObject systemRealLocationDataElementJSON = new JSONObject();
			JSONObject installedModulesDataElementJSON = new JSONObject();
			JSONObject systemRealLocationDataElementJSON2 = new JSONObject();
			JSONObject installedModulesDataElementJSON2 = new JSONObject();

			systemRealLocationDataElementJSON.put("dataElement",
					DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemRealLocation"));
			systemRealLocationDataElementJSON.put("period", dFormat.format(today));
			systemRealLocationDataElementJSON.put("value", serverRealLocation);
			systemRealLocationDataElementJSON.put("orgUnit", dhisOrganizationUnitUid);
			installedModulesDataElementJSON.put("dataElement",
					DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemInfo_installedModules"));
			installedModulesDataElementJSON.put("period", dFormat.format(today));
			installedModulesDataElementJSON.put("value", systemMonitorService.getInstalledModules());
			installedModulesDataElementJSON.put("orgUnit", dhisOrganizationUnitUid);
			systemRealLocationDataElementJSON2.put("dataElement",
					DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemRealLocation"));
			systemRealLocationDataElementJSON2.put("period", dFormat.format(today));
			systemRealLocationDataElementJSON2.put("value", convertJSONToCleanString(serverRealLocation, null));
			systemRealLocationDataElementJSON2.put("orgUnit", dhisOrganizationUnitUid);
			installedModulesDataElementJSON2.put("orgUnit", dhisOrganizationUnitUid);
			installedModulesDataElementJSON2.put("dataElement",
					DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemInfo_installedModules"));
			installedModulesDataElementJSON2.put("period", dFormat.format(today));
			installedModulesDataElementJSON2.put("value",
					convertJSONToCleanString(null, systemMonitorService.getInstalledModules()));
			jsonDataValueSets.put(new JSONObject(systemIdDataElement));
			jsonDataValueSets.put(new JSONObject(openMRSAppNameDataElement));
			jsonDataValueSets.put(new JSONObject(primaryClinicDaysDataElement));
			jsonDataValueSets.put(new JSONObject(primaryClinicHoursDataElement));
			jsonDataValueSets.put(new JSONObject(encounterDataElement));
			jsonDataValueSets.put(new JSONObject(obsDataElement));
			jsonDataValueSets.put(new JSONObject(userDataElement));
			jsonDataValueSets.put(new JSONObject(patientActiveDataElement));
			jsonDataValueSets.put(new JSONObject(patientNewDataElement));
			jsonDataValueSets.put(new JSONObject(visitsDataElement));
			jsonDataValueSets.put(new JSONObject(viralLoadTestResults_everDataElement));
			jsonDataValueSets.put(new JSONObject(viralLoadTestResults_last6MonthsDataElement));
			jsonDataValueSets.put(new JSONObject(viralLoadTestResults_lastYearDataElement));
			jsonDataValueSets.put(new JSONObject(systemStartupsDataElement));
			jsonDataValueSets.put(new JSONObject(upTimeThisWeekDataElement));
			jsonDataValueSets.put(new JSONObject(upTimeLastWeekDataElement));
			jsonDataValueSets.put(new JSONObject(upTimeLastMonthDataElement));
			jsonDataValueSets.put(new JSONObject(freeMemoryDataElement));
			jsonDataValueSets.put(new JSONObject(totalMemoryDataElement));
			jsonDataValueSets.put(new JSONObject(totalOpenMRSUptimeDataElement));
			jsonDataValueSets.put(new JSONObject(usedMemoryDataElement));
			jsonDataValueSets.put(new JSONObject(systemInfo_operatingSystem));
			jsonDataValueSets.put(new JSONObject(systemInfo_operatingSystemArch));
			jsonDataValueSets.put(new JSONObject(systemInfo_operatingSystemVersion));
			jsonDataValueSets.put(new JSONObject(systemInfo_javaVersion));
			jsonDataValueSets.put(new JSONObject(systemInfo_javaVendor));
			jsonDataValueSets.put(new JSONObject(systemInfo_jvmVersion));
			jsonDataValueSets.put(new JSONObject(systemInfo_jvmVendor));
			jsonDataValueSets.put(new JSONObject(systemInfo_userName));
			jsonDataValueSets.put(new JSONObject(systemInfo_systemLanguage));
			jsonDataValueSets.put(new JSONObject(systemInfo_systemTimezone));
			jsonDataValueSets.put(new JSONObject(systemInfo_userDirectory));
			jsonDataValueSets.put(new JSONObject(systemInfo_tempDirectory));
			jsonDataValueSets.put(new JSONObject(systemInfo_javaRuntimeName));
			jsonDataValueSets.put(new JSONObject(systemInfo_javaRuntimeVersion));
			jsonDataValueSets.put(new JSONObject(systemInfo_systemDateTime));
			jsonDataValueSets.put(new JSONObject(systemInfo_fileSystemEncoding));
			jsonDataValueSets.put(new JSONObject(systemInfo_openMRSVersion));
			jsonToBePushed = new JSONArray(jsonDataValueSets.toString());
			jsonToBePushed.put(systemRealLocationDataElementJSON2);
			jsonToBePushed.put(installedModulesDataElementJSON2);
			jsonDataValueSets.put(systemRealLocationDataElementJSON);
			jsonDataValueSets.put(installedModulesDataElementJSON);
			jsonObj.put("dataValues", addMetricDetailsLikeNamesToAllDataJSON(jsonDataValueSets));
			jsonObj2.put("dataValues", jsonToBePushed);
			finalJSON.put("allData", jsonObj);
			finalJSON.put("toBePushed", jsonObj2);
		}
		return finalJSON;
	}

	private static JSONArray addMetricDetailsLikeNamesToAllDataJSON(JSONArray allData) {
		JSONArray newJson = new JSONArray();

		if (allData != null && allData.length() > 0) {
			for (int i = 0; i < allData.length(); i++) {
				if (allData.getJSONObject(i) != null) {
					JSONObject json = new JSONObject();

					json.put("value", allData.getJSONObject(i).get("value"));
					json.put("period", allData.getJSONObject(i).getString("period"));
					json.put("orgUnitId", allData.getJSONObject(i).getString("orgUnit"));
					json.put("orgUnitName", getOrgUnitName(allData.getJSONObject(i).getString("orgUnit")));
					json.put("dataElementId", allData.getJSONObject(i).getString("dataElement"));
					json.put("dataElementName",
							getMetricOrIndicatorName(allData.getJSONObject(i).getString("dataElement")));
					newJson.put(json);
				}
			}
			return newJson;
		}
		return allData;
	}

	private static String getOrgUnitName(String orgUnitId) {
		if (StringUtils.isNotBlank(orgUnitId)) {
			JSONObject orgUnit = Context.getService(SystemMonitorService.class).getDHISOrgUnit(orgUnitId);

			if (orgUnit != null) {
				return orgUnit.getString("name");
			}
		}
		return null;
	}

	private static String getMetricOrIndicatorName(String metricId) {
		if (StringUtils.isNotBlank(metricId)) {
			JSONObject metric = Context.getService(SystemMonitorService.class)
					.getIndicatorOrMetricOrDataElement(metricId);
			if (metric != null) {
				return metric.getString("name");
			}
		}
		return null;
	}

	private static String convertJSONToCleanString(JSONObject locationsJson, JSONArray modulesJson) {
		String string = null;

		if (locationsJson != null && modulesJson == null) {// is Location
			string = "Region: " + locationsJson.getString("region") + "; Hostname: "
					+ locationsJson.getString("hostname") + "; ISP: " + locationsJson.getString("org") + "; Country: "
					+ locationsJson.getString("country") + "; City: " + locationsJson.getString("city")
					+ "; IP Address: " + locationsJson.getString("ip") + "; Location(Latitude,Longitude): "
					+ locationsJson.getString("loc");
		} else if (modulesJson != null && locationsJson == null) {// is
																	// modules
			string = "";

			for (int i = 0; i < modulesJson.length(); i++) {
				string += modulesJson.getJSONObject(i).getString("name") + "("
						+ modulesJson.getJSONObject(i).getString("version") + ")";
				if (i != modulesJson.length() - 1) {
					string += ", ";
				}
			}
		}
		return string;
	}
}
