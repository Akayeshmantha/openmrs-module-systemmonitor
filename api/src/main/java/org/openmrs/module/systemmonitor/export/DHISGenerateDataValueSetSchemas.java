package org.openmrs.module.systemmonitor.export;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.module.systemmonitor.SystemMonitorConstants;
import org.openmrs.module.systemmonitor.mapping.DHISDataElementMapping;

public class DHISGenerateDataValueSetSchemas {

	public static JSONObject generateRwandaSPHEMTDHISDataValueSets(String systemId, String dhisOrganizationUnitUid,
			String clinicDays, String clinicHours, Integer encounterTotal, Integer obsTotal, Integer totalUsers,
			String openmrsAPPName, Integer totalPatientActive, Integer totalPatientNew, Integer totalVisits,
			Integer startupCounts, Long thisWeekUptime, Integer previousWeekUptime, Integer previousMonthUptime,
			Long freeMemory, Long usedMemory, Long totalMemory, Integer openmrsUptimePercentage,
			Integer viralLoadTestResultsEver, Integer viralLoadTestResultsLastSixMonths,
			Integer viralLoadTestResultsLastYear, String operatingSystem, String operatingSystemArch,
			String operatingSystemVersion, String javaVersion, String javaVendor, String jvmVersion, String jvmVendor,
			String javaRuntimeName, String javaRuntimeVersion, String userName, String systemLanguage,
			String systemTimezone, String userDirectory, String fileSystemEncoding, String systemDateTime,
			String openmrsVersion, JSONObject installedModules, String tempDirectory, JSONObject serverRealLocation) {
		Calendar cal = Calendar.getInstance();
		Date today = new Date();
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd");
		File mappingsFile = SystemMonitorConstants.SYSTEMMONITOR_FINAL_MAPPINGFILE;
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonDataValueSets = new JSONArray();

		if(StringUtils.isBlank(dhisOrganizationUnitUid)) {
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

			systemRealLocationDataElementJSON.put("dataElement", DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_systemRealLocation"));
			systemRealLocationDataElementJSON.put("period", dFormat.format(today));
			systemRealLocationDataElementJSON.put("value", serverRealLocation);
			installedModulesDataElementJSON.put("dataElement", DHISDataElementMapping.getDHISMappedObject("DATA-ELEMENT_systemInfo_installedModules"));
			installedModulesDataElementJSON.put("period", dFormat.format(today));
			installedModulesDataElementJSON.put("value", installedModules);
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
			jsonObj.put("dataValues", jsonDataValueSets);

		}
		return jsonObj;
	}

}
