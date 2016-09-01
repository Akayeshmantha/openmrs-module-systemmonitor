package org.openmrs.module.systemmonitor.export;

import java.io.File;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
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
		File mappingsFile = SystemMonitorConstants.SYSTEMMONITOR_FINAL_MAPPINGFILE;
		JSONObject jsonObj = new JSONObject();
		JSONObject jsonObj2 = new JSONObject();
		JSONObject finalJSON = new JSONObject();
		JSONArray jsonToBePushed;
		JSONArray jsonDataValueSets = new JSONArray();
		SystemMonitorService systemMonitorService = Context.getService(SystemMonitorService.class);

		String systemId = OSAndHardwareIndicators.getHostName() + "-" + (OSAndHardwareIndicators.getMacAddress() != null
				? OSAndHardwareIndicators.getMacAddress().replace(":", "") : "");

		String dhisOrgUnitUid = DHISMapping
				.getDHISMappedObjectValue(systemMonitorService.getCurrentConfiguredDHISOrgUnit().getPropertyValue());

		String clinicDays = SystemMonitorConstants.CLINIC_OPENING_DAYS;

		String clinicHours = SystemMonitorConstants.CLINIC_OPENING_HOUR + " - "
				+ SystemMonitorConstants.CLINIC_CLOSING_HOUR;

		String openmrsAPPName = WebConstants.WEBAPP_NAME;

		Integer uptime = OSAndHardwareIndicators.PROCESSOR_SYSTEM_UPTIME.intValue();

		String processor = OSAndHardwareIndicators.getLinuxProcessorName();

		Integer openmrsUptime = systemMonitorService.getOpenMRSSystemUpTime().intValue();

		Long freeMemory = OSAndHardwareIndicators.MEMORY_AVAILABLE;

		Long usedMemory = OSAndHardwareIndicators.MEMORY_USED;

		Long totalMemory = OSAndHardwareIndicators.MEMORY_TOTAL;

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

		JSONObject serverRealLocation = null;
		try {
			serverRealLocation = CurlEmulator
					.get(SystemMonitorConstants.IP_INFO_URL + OSAndHardwareIndicators.getIpAddress(), null, null);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}

		Integer encounterTotal = systemMonitorService.rwandaPIHEMTGetTotalEncounters();

		Integer encountersYesterday = systemMonitorService.rwandaPIHEMTGetTotalEncountersForYesterday();

		Integer obsTotal = systemMonitorService.rwandaPIHEMTGetTotalObservations();

		Integer totalUsers = systemMonitorService.rwandaPIHEMTGetTotalUsers();

		Integer totalPatientActive = systemMonitorService.rwandaPIHEMTGetTotalActivePatients();

		Integer totalPatientNew = systemMonitorService.rwandaPIHEMTGetTotalNewPatients();

		Integer totalVisits = systemMonitorService.rwandaPIHEMTGetTotalVisits();

		Integer viralLoadTestResultsEver = systemMonitorService.getTotalViralLoadTestsEver();

		Integer viralLoadTestResultsLastSixMonths = systemMonitorService.getTotalViralLoadTestsLastSixMonths();

		Integer viralLoadTestResultsLastYear = systemMonitorService.getTotalViralLoadTestsLastYear();

		Integer cd4CountTestResultsEver = systemMonitorService.getTotalCD4CountTestsEver();

		Integer cd4CountTestResultsLastSixMonths = systemMonitorService.getTotalCD4CountTestsLastSixMonths();

		Integer cd4CountTestResultsLastYear = systemMonitorService.getTotalCD4CountTestsLastYear();

		Integer newObs = systemMonitorService.rwandaPIHEMTGetTotalObservationsForYesterday();

		Integer newUsers = systemMonitorService.rwandaPIHEMTGetTotalUsersForYesterday();

		Integer newAdultInitialEncounters = systemMonitorService
				.rwandaPIHEMTGetTotalAdultInitialEncountersForYesterday();

		Integer newAdultReturnEncounters = systemMonitorService.rwandaPIHEMTGetTotalAdultReturnEncountersForYesterday();

		Integer newPedsInitialEncounters = systemMonitorService.rwandaPIHEMTGetTotalPedsInitialEncountersForYesterday();

		Integer newPedsReturnEncounters = systemMonitorService.rwandaPIHEMTGetTotalPedsReturnEncountersForYesterday();

		Integer newPatientsCD4CountsTestResults = systemMonitorService.getTotalCD4CountTestsForYesterday();

		Integer newPatientViralLoadTestResults = systemMonitorService.getTotalViralLoadTestsForYesterday();

		Integer newTotalPatientNew = systemMonitorService.rwandaPIHEMTGetTotalNewPatientsForYesterday();

		Integer newTotalPatientActive = systemMonitorService.rwandaPIHEMTGetTotalActivePatientsForYesterday();

		String dateForLastBackUp = systemMonitorService.getLastBackUpDate() != null
				? systemMonitorService.getLastBackUpDate().toString() : "";

		if (StringUtils.isBlank(dhisOrgUnitUid)) {
			dhisOrgUnitUid = "";
		}
		jsonObj2.put("orgUnit", dhisOrgUnitUid);
		jsonObj.put("orgUnit",
				StringUtils.isNotBlank(systemMonitorService.getDHISConfiguredOrgUnitName())
						? systemMonitorService.getDHISConfiguredOrgUnitName()
						: "Org Unit DHIS Id: " + dhisOrgUnitUid + " FosID: "
								+ systemMonitorService.getCurrentConfiguredDHISOrgUnit().getPropertyValue());
		if (mappingsFile.exists() && mappingsFile.isFile()) {
			JSONObject systemRealLocationDataElementJSON = new JSONObject();
			JSONObject installedModulesDataElementJSON = new JSONObject();
			JSONObject systemRealLocationDataElementJSON2 = new JSONObject();
			JSONObject installedModulesDataElementJSON2 = new JSONObject();

			systemRealLocationDataElementJSON.put("dataElement",
					DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemRealLocation"));
			systemRealLocationDataElementJSON.put("period", systemMonitorService.getDHISLastMonthPeriod());
			systemRealLocationDataElementJSON.put("value",
					serverRealLocation != null ? serverRealLocation : "No Internet Connection");
			installedModulesDataElementJSON.put("dataElement",
					DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemInfo_installedModules"));
			installedModulesDataElementJSON.put("period", systemMonitorService.getDHISLastMonthPeriod());
			installedModulesDataElementJSON.put("value", systemMonitorService.getInstalledModules());
			systemRealLocationDataElementJSON2.put("dataElement",
					DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemRealLocation"));
			systemRealLocationDataElementJSON2.put("period", systemMonitorService.getDHISLastMonthPeriod());
			systemRealLocationDataElementJSON2.put("value", serverRealLocation != null
					? convertJSONToCleanString(serverRealLocation, null) : "No Internet Connection");
			installedModulesDataElementJSON2.put("dataElement",
					DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemInfo_installedModules"));
			installedModulesDataElementJSON2.put("period", systemMonitorService.getDHISLastMonthPeriod());
			installedModulesDataElementJSON2.put("value",
					convertJSONToCleanString(null, systemMonitorService.getInstalledModules()));

			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_newEncounters", encountersYesterday,
					systemMonitorService.getDHISYesterdayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_newObservations", newObs,
					systemMonitorService.getDHISYesterdayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_newUsers", newUsers,
					systemMonitorService.getDHISYesterdayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_newEncounters_adultinitial",
					newAdultInitialEncounters, systemMonitorService.getDHISYesterdayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_newEncounters_adultreturn",
					newAdultReturnEncounters, systemMonitorService.getDHISYesterdayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_newEncounters_predsinitial",
					newPedsInitialEncounters, systemMonitorService.getDHISYesterdayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_newEncounters_pedsreturn",
					newPedsReturnEncounters, systemMonitorService.getDHISYesterdayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_newTotalPatientsNew", newTotalPatientNew,
					systemMonitorService.getDHISYesterdayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_newTotalPatientsActive",
					newTotalPatientActive, systemMonitorService.getDHISYesterdayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_patientsCD4CountTestResults_new",
					newPatientsCD4CountsTestResults, systemMonitorService.getDHISYesterdayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_patientsViralLoadTestResults_new",
					newPatientViralLoadTestResults, systemMonitorService.getDHISYesterdayPeriod()));
			jsonDataValueSets
					.put(createBasicIndicatorJSONObject("DATA-ELEMENT_patientsViralLoadTestResults_last6Months",
							viralLoadTestResultsLastSixMonths, systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_patientsCD4CountTestResults_last6Months",
					cd4CountTestResultsLastSixMonths, systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_patientsViralLoadTestResults_LastYear",
					viralLoadTestResultsLastYear, systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_patientsCD4CountTestResults_LastYear",
					cd4CountTestResultsLastYear, systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_patientsCD4CountTestResults_ever",
					cd4CountTestResultsEver, systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_patientsViralLoadTestResults_ever",
					viralLoadTestResultsEver, systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalEncounters", encounterTotal,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalObservations", obsTotal,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalUsers", totalUsers,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalPatientsActive", totalPatientActive,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalPatientsNew", totalPatientNew,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalVisits", totalVisits,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_dataForLastBackup", dateForLastBackUp,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemId", systemId,
					systemMonitorService.getDHISLastMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_processor", processor,
					systemMonitorService.getDHISLastMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_serverUptimeSecs", uptime,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalMemory", totalMemory,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_usedMemory", usedMemory,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_freeMemory", freeMemory,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_openmrsAppName", openmrsAPPName,
					systemMonitorService.getDHISLastMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_openmrsUptimeSecs", openmrsUptime,
					systemMonitorService.getDHISLastMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_primaryCareDays", clinicDays,
					systemMonitorService.getDHISLastMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_primaryCareHours", clinicHours,
					systemMonitorService.getDHISLastMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_operatingSystemName",
					operatingSystem, systemMonitorService.getDHISLastMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_operatingSystemArch",
					operatingSystemArch, systemMonitorService.getDHISLastMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_operatingSystemVersion",
					operatingSystemVersion, systemMonitorService.getDHISLastMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_javaVersion", javaVersion,
					systemMonitorService.getDHISLastMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_javaVendor", javaVendor,
					systemMonitorService.getDHISLastMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_jvmVersion", jvmVersion,
					systemMonitorService.getDHISLastMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_jvmVendor", jvmVendor,
					systemMonitorService.getDHISLastMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_userName", userName,
					systemMonitorService.getDHISLastMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_systemLanguage",
					systemLanguage, systemMonitorService.getDHISLastMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_systemTimezone",
					systemTimezone, systemMonitorService.getDHISLastMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_userDirectory", userDirectory,
					systemMonitorService.getDHISLastMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_tempDirectory", tempDirectory,
					systemMonitorService.getDHISLastMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_javaRuntimeName",
					javaRuntimeName, systemMonitorService.getDHISLastMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_javaRuntimeVersion",
					javaRuntimeVersion, systemMonitorService.getDHISLastMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_systemDateTime",
					systemDateTime, systemMonitorService.getDHISLastMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_fileSystemEncoding",
					fileSystemEncoding, systemMonitorService.getDHISLastMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_openMRSVersion",
					openmrsVersion, systemMonitorService.getDHISLastMonthPeriod()));

			// jsonDataValueSets.put(new JSONObject(systemStartupsDataElement));
			// jsonDataValueSets.put(new JSONObject(upTimeThisWeekDataElement));
			// jsonDataValueSets.put(new JSONObject(upTimeLastWeekDataElement));
			// jsonDataValueSets.put(new
			// JSONObject(upTimeLastMonthDataElement));
			// jsonDataValueSets.put(new
			// JSONObject(totalOpenMRSUptimeDataElement));

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
		String region = "";

		if (locationsJson != null && modulesJson == null) {// is Location
			try {
				region = "Region: " + locationsJson.getString("region") + "; ";
			} catch (JSONException e) {
				if (e.getMessage().indexOf("region") > 0) {
					region = "";
				}
			}
			string = region + "Hostname: " + locationsJson.getString("hostname") + "; ISP: "
					+ locationsJson.getString("org") + "; Country: " + locationsJson.getString("country") + "; City: "
					+ locationsJson.getString("city") + "; IP Address: " + locationsJson.getString("ip")
					+ "; Location(Latitude,Longitude): " + locationsJson.getString("loc");
		} else if (modulesJson != null && locationsJson == null) {
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

	private static JSONObject createBasicIndicatorJSONObject(String dhisDataElementMappingCode, Object value,
			String period) {
		JSONObject json = new JSONObject();

		if (StringUtils.isNotBlank(dhisDataElementMappingCode) && StringUtils.isNotBlank(period)) {

			if (value == null)
				value = "";
			json.put("dataElement", DHISMapping.getDHISMappedObjectValue(dhisDataElementMappingCode));
			json.put("period", period);
			json.put("value", value);
		}
		return json;
	}
}
