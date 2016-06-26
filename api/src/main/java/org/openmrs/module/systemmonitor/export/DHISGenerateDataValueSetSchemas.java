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
import org.openmrs.module.systemmonitor.mapping.DHISDataElementMapping;
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
		JSONArray jsonToBePushed = new JSONArray();
		JSONArray jsonDataValueSets = new JSONArray();
		SystemMonitorService systemMonitorService = Context.getService(SystemMonitorService.class);

		String systemId = OSAndHardwareIndicators.getHostName();

		String dhisOrganizationUnitUid = DHISDataElementMapping
				.getDHISMappedObject(systemMonitorService.getCurrentConfiguredDHISOrgUnit());

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

		System.out.println("OSAndHardwareIndicators.getIpAddress())" + OSAndHardwareIndicators.getIpAddress());

		JSONObject serverRealLocation = CurlEmulator
				.get(SystemMonitorConstants.IP_INFO_URL + OSAndHardwareIndicators.getIpAddress());

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
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_systemId") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": \""
					+ systemId + "\"}";
			String primaryClinicDaysDataElement = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_primaryCareDays") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": \""
					+ clinicDays + "\"}";
			String primaryClinicHoursDataElement = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_primaryCareHours") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": \""
					+ clinicHours + "\"}";
			String openMRSAppNameDataElement = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_openmrsAppName") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": \""
					+ openmrsAPPName + "\"}";
			String encounterDataElement = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_totalEncounters") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": "
					+ encounterTotal + "}";
			String obsDataElement = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_totalObservations")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": " + obsTotal + "}";
			String userDataElement = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_totalUsers") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": "
					+ totalUsers + "}";
			String patientActiveDataElement = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_totalPatientsActive")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": " + totalPatientActive + "}";
			String patientNewDataElement = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_totalPatientsNew") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": "
					+ totalPatientNew + "}";
			String visitsDataElement = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_totalVisits") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": "
					+ totalVisits + "}";
			String systemStartupsDataElement = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_systemStartupCounts")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": " + startupCounts + "}";
			String upTimeThisWeekDataElement = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_systemUptime-thisWeek")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": " + thisWeekUptime + "}";
			String upTimeLastWeekDataElement = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_systemUptime-lastWeek")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": " + previousWeekUptime + "}";
			String upTimeLastMonthDataElement = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_systemUptime-lastMonth")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": " + previousMonthUptime + "}";
			String freeMemoryDataElement = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_freeMemory") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": "
					+ freeMemory + "}";
			String totalMemoryDataElement = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_totalMemory") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": "
					+ totalMemory + "}";
			String usedMemoryDataElement = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_usedMemory") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": "
					+ usedMemory + "}";
			String totalOpenMRSUptimeDataElement = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_openmrsUptime") + "\", \"period\": \""
					+ dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid + "\", \"value\": "
					+ openmrsUptimePercentage + "}";
			String viralLoadTestResults_everDataElement = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_patientsViralLoadTestResults_ever")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": " + viralLoadTestResultsEver + "}";
			String viralLoadTestResults_last6MonthsDataElement = "{ \"dataElement\": \""
					+ DHISDataElementMapping
							.getDHISMappedObject("DATA-ELEMENT_patientsViralLoadTestResults_last6Months")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": " + viralLoadTestResultsLastSixMonths + "}";
			String viralLoadTestResults_lastYearDataElement = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_patientsViralLoadTestResults_LastYear")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": " + viralLoadTestResultsLastYear + "}";
			String systemInfo_operatingSystem = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_systemInfo_operatingSystemName")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": \"" + operatingSystem + "\"}";
			String systemInfo_operatingSystemArch = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_systemInfo_operatingSystemArch")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": \"" + operatingSystemArch + "\"}";
			String systemInfo_operatingSystemVersion = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_systemInfo_operatingSystemVersion")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": \"" + operatingSystemVersion + "\"}";
			String systemInfo_javaVersion = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_systemInfo_javaVersion")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": \"" + javaVersion + "\"}";
			String systemInfo_javaVendor = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_systemInfo_javaVendor")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": \"" + javaVendor + "\"}";
			String systemInfo_jvmVersion = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_systemInfo_jvmVersion")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": \"" + jvmVersion + "\"}";
			String systemInfo_jvmVendor = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_systemInfo_jvmVendor")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": \"" + jvmVendor + "\"}";
			String systemInfo_javaRuntimeName = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_systemInfo_javaRuntimeName")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": \"" + javaRuntimeName + "\"}";
			String systemInfo_javaRuntimeVersion = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_systemInfo_javaRuntimeVersion")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": \"" + javaRuntimeVersion + "\"}";
			String systemInfo_userName = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_systemInfo_userName")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": \"" + userName + "\"}";
			String systemInfo_systemLanguage = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_systemInfo_systemLanguage")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": \"" + systemLanguage + "\"}";
			String systemInfo_systemTimezone = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_systemInfo_systemTimezone")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": \"" + systemTimezone + "\"}";
			String systemInfo_systemDateTime = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_systemInfo_systemDateTime")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": \"" + systemDateTime + "\"}";
			String systemInfo_fileSystemEncoding = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_systemInfo_fileSystemEncoding")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": \"" + fileSystemEncoding + "\"}";
			String systemInfo_userDirectory = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_systemInfo_userDirectory")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": \"" + userDirectory + "\"}";
			String systemInfo_tempDirectory = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_systemInfo_tempDirectory")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": \"" + tempDirectory + "\"}";
			String systemInfo_openMRSVersion = "{ \"dataElement\": \""
					+ DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_systemInfo_openMRSVersion")
					+ "\", \"period\": \"" + dFormat.format(today) + "\", \"orgUnit\": \"" + dhisOrganizationUnitUid
					+ "\", \"value\": \"" + openmrsVersion + "\"}";

			JSONObject systemRealLocationDataElementJSON = new JSONObject();
			JSONObject installedModulesDataElementJSON = new JSONObject();
			JSONObject systemRealLocationDataElementJSON2 = new JSONObject();
			JSONObject installedModulesDataElementJSON2 = new JSONObject();

			systemRealLocationDataElementJSON.put("dataElement",
					DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_systemRealLocation"));
			systemRealLocationDataElementJSON.put("period", dFormat.format(today));
			systemRealLocationDataElementJSON.put("value", serverRealLocation);
			installedModulesDataElementJSON.put("dataElement",
					DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_systemInfo_installedModules"));
			installedModulesDataElementJSON.put("period", dFormat.format(today));
			installedModulesDataElementJSON.put("value", systemMonitorService.getInstalledModules());
			systemRealLocationDataElementJSON2.put("dataElement",
					DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_systemRealLocation"));
			systemRealLocationDataElementJSON2.put("period", dFormat.format(today));
			systemRealLocationDataElementJSON2.put("value", convertJSONToCleanString(serverRealLocation, null));
			installedModulesDataElementJSON2.put("dataElement",
					DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_systemInfo_installedModules"));
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
			jsonDataValueSets.put(systemRealLocationDataElementJSON);
			jsonDataValueSets.put(installedModulesDataElementJSON);
			jsonToBePushed = jsonDataValueSets;
			jsonToBePushed.put(systemRealLocationDataElementJSON2);
			jsonToBePushed.put(installedModulesDataElementJSON2);
			jsonObj.put("dataValues", jsonDataValueSets);
			jsonObj2.put("dataValues", jsonToBePushed);
			finalJSON.put("allData", jsonObj);
			finalJSON.put("toBePushed", jsonObj2);
		}
		return finalJSON;
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
				string += modulesJson.getJSONObject(i).getString("name") + "-"
						+ modulesJson.getJSONObject(i).getString("version");
				if (i != modulesJson.length() - 1) {
					string += ", ";
				}
			}
		}
		return string;
	}
}
