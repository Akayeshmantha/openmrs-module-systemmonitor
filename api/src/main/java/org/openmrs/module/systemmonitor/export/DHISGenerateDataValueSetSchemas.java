package org.openmrs.module.systemmonitor.export;

import java.io.File;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.systemmonitor.ConfigurableGlobalProperties;
import org.openmrs.module.systemmonitor.SystemMonitorConstants;
import org.openmrs.module.systemmonitor.api.SystemMonitorService;
import org.openmrs.module.systemmonitor.curl.CurlEmulator;
import org.openmrs.module.systemmonitor.indicators.OSAndHardwareIndicators;
import org.openmrs.module.systemmonitor.indicators.SystemPropertiesIndicators;
import org.openmrs.module.systemmonitor.mapping.DHISMapping;
import org.openmrs.module.systemmonitor.uptime.OpenmrsUpAndDownTracker;
import org.openmrs.module.systemmonitor.uptime.UpOrDownTimeInterval;
import org.openmrs.web.WebConstants;

import com.sun.jersey.api.representation.Form;

public class DHISGenerateDataValueSetSchemas {

	/**
	 * @TODO re-write this from a string-json parse approach to json itself
	 * 
	 * @return dataValueSets json object
	 * @throws UnknownHostException
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject generateRwandaSPHEMTDHISDataValueSets() {
		JSONObject jsonObj = new JSONObject();
		JSONObject jsonObj2 = new JSONObject();
		JSONObject finalJSON = new JSONObject();
		JSONArray jsonToBePushed = new JSONArray();
		JSONArray jsonDataValueSets = new JSONArray();
		Integer openingHour = Integer.parseInt(Context.getService(SystemMonitorService.class).getConfiguredOpeningHour()
				.getPropertyValue().substring(0, 2));
		Integer closingHour = Integer.parseInt(Context.getService(SystemMonitorService.class).getConfiguredClosingHour()
				.getPropertyValue().substring(0, 2));
		Integer workingMinutesDifference = ((closingHour - openingHour) - 1) * 60;
		File mappingsFile = SystemMonitorConstants.SYSTEMMONITOR_FINAL_MAPPINGFILE;
		Calendar date = Calendar.getInstance();
		String evaluationAndReportingIsAllowed = Context.getAdministrationService()
				.getGlobalProperty(ConfigurableGlobalProperties.TOGGLE_SMT_EVALUATIONANDREPORTING_ONOROFF);
		JSONArray metricDetails = new JSONArray();
		SystemMonitorService systemMonitorService = Context.getService(SystemMonitorService.class);
		OSAndHardwareIndicators osshi = new OSAndHardwareIndicators();

		date.setTime(systemMonitorService.getEvaluationAndReportingDate());
		if (mappingsFile.exists() && mappingsFile.isFile() && date.get(Calendar.HOUR_OF_DAY) >= openingHour
				&& date.get(Calendar.HOUR_OF_DAY) < closingHour && (StringUtils.isBlank(evaluationAndReportingIsAllowed) || "on".equals(evaluationAndReportingIsAllowed))) {
			String systemId = osshi.getHostName() + "-"
					+ (osshi.getMacAddress() != null ? osshi.getMacAddress().replace(":", "") : "");

			String dhisOrgUnitUid = DHISMapping
					.getDHISMappedObjectValue(systemMonitorService.getCurrentConfiguredDHISOrgUnit().getPropertyValue());

			String clinicDays = SystemMonitorConstants.CLINIC_OPENING_DAYS;

			String clinicHours = SystemMonitorConstants.CLINIC_OPENING_HOUR + " - "
					+ SystemMonitorConstants.CLINIC_CLOSING_HOUR;

			String openmrsAPPName = WebConstants.WEBAPP_NAME;

			Integer uptime = osshi.PROCESSOR_SYSTEM_UPTIME.intValue();

			String processor = osshi.getLinuxProcessorName();

			Integer numberOfDownTimes = OpenmrsUpAndDownTracker.getNumberOfOpenMRSDownTimesToday();

			Object[] openmrsUpAndDownTime = OpenmrsUpAndDownTracker.calculateOpenMRSUpAndDowntimeBy(date.getTime(), null);

			Integer openmrsUptime = (Integer) openmrsUpAndDownTime[0];

			Integer openmrsDowntime = (Integer) openmrsUpAndDownTime[1];

			List<UpOrDownTimeInterval> upIntervals = (List<UpOrDownTimeInterval>) openmrsUpAndDownTime[2];

			List<UpOrDownTimeInterval> downIntervals = (List<UpOrDownTimeInterval>) openmrsUpAndDownTime[3];

			//down & up time minutes can only be recovered on a daily basis
			openmrsUptime = openmrsUptime != null && openmrsUptime >= 0 && openmrsUptime <= 1440 ? openmrsUptime : 0;
			openmrsDowntime = openmrsDowntime != null && openmrsDowntime >= 0 && openmrsDowntime <= 1440 ? openmrsDowntime : 0;

			Integer openMRsUpTimePercentage = openmrsUptime != null && openmrsUptime >= 0 ? (openmrsUptime * 100) / workingMinutesDifference
					: 0;

			Integer openMRsDownTimePercentage = openmrsDowntime != null && openmrsDowntime >= 0 ? (openmrsDowntime * 100) / workingMinutesDifference
					: 0;

			String upIntervalString = convertUpOrDownTimeIntervalsToString(upIntervals);

			String downIntervalString = convertUpOrDownTimeIntervalsToString(downIntervals);

			Integer totalActivePatients_AtleastEightMonthsARVTreatment = systemMonitorService
					.rwandaGetTotalActivePatients_AtleastEightMonthsARVTreatment();

			Integer totalActivePatients_AtleastTwentyMonthsARVTreatment = systemMonitorService
					.rwandaGetTotalActivePatients_AtleastTwentyMonthsARVTreatment();

			Integer totalActivePatients_AtleastEightMonthsARVTreatment_AtleastOneViralLoad_InEMR = systemMonitorService
					.rwandaGetTotalActivePatients_AtleastEightMonthsARVTreatment_AtleastOneViralLoad_InEMR();

			Integer totalActivePatients_AtleastEightMonthsARVTreatment_AtleastOneCD4Count_InEMR = systemMonitorService
					.rwandaGetTotalActivePatients_AtleastEightMonthsARVTreatment_AtleastOneCD4Count_InEMR();

			Integer totalActivePatients_AtleastTwentyMonthsARVTreatment_AtleastOneViralLoad_LastYear = systemMonitorService
					.rwandaGetTotalActivePatients_AtleastTwentyMonthsARVTreatment_AtleastOneViralLoad_LastYear();

			Integer totalActivePatients_AtleastTwentyMonthsARVTreatment_AtleastOneCD4Count_LastYear = systemMonitorService
					.rwandaGetTotalActivePatients_AtleastTwentyMonthsARVTreatment_AtleastOneCD4Count_LastYear();

			Integer initialCD4Count = totalActivePatients_AtleastEightMonthsARVTreatment == 0 ? 0
					: (totalActivePatients_AtleastEightMonthsARVTreatment_AtleastOneCD4Count_InEMR * 100)
							/ totalActivePatients_AtleastEightMonthsARVTreatment;

			Integer initialViralLoad = totalActivePatients_AtleastEightMonthsARVTreatment == 0 ? 0
					: (totalActivePatients_AtleastEightMonthsARVTreatment_AtleastOneViralLoad_InEMR * 100)
							/ totalActivePatients_AtleastEightMonthsARVTreatment;

			Integer followupViralLoad = totalActivePatients_AtleastTwentyMonthsARVTreatment == 0 ? 0
					: (totalActivePatients_AtleastTwentyMonthsARVTreatment_AtleastOneCD4Count_LastYear * 100)
							/ totalActivePatients_AtleastTwentyMonthsARVTreatment;

			Integer followupCD4Count = totalActivePatients_AtleastTwentyMonthsARVTreatment == 0 ? 0
					: (totalActivePatients_AtleastTwentyMonthsARVTreatment_AtleastOneViralLoad_LastYear * 100)
							/ totalActivePatients_AtleastTwentyMonthsARVTreatment;

			//TODO fall back to memory aggregation usage and fix
			Long totalMemory = Runtime.getRuntime().maxMemory() / 1024;

			Long freeMemory = Runtime.getRuntime().freeMemory()/ 1024;

			Long usedMemory = Runtime.getRuntime().totalMemory()/ 1024;

			String operatingSystem = SystemPropertiesIndicators.OS_NAME + ", Family: " + osshi.OS_FAMILY
					+ ", Manufacturer: " + osshi.OS_MANUFACTURER + ", Version Name: " + osshi.OS_VERSION_NAME
					+ ", Version Number: " + osshi.OS_VERSION_NUMBER + ", Build Number: " + osshi.OS_VERSION_BUILDNUMBER;

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
				serverRealLocation = CurlEmulator.get(SystemMonitorConstants.IP_INFO_URL + osshi.getIpAddress(), null,
						null);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (Exception e) {
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
			JSONObject systemRealLocationDataElementJSON = new JSONObject();
			JSONObject installedModulesDataElementJSON = new JSONObject();
			JSONObject systemRealLocationDataElementJSON2 = new JSONObject();
			JSONObject installedModulesDataElementJSON2 = new JSONObject();

			systemRealLocationDataElementJSON.put("dataElement",
					DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemRealLocation"));
			systemRealLocationDataElementJSON.put("period", systemMonitorService.getDHISCurrentMonthPeriod());
			systemRealLocationDataElementJSON.put("value",
					serverRealLocation != null ? serverRealLocation : "No Internet Connection");
			installedModulesDataElementJSON.put("dataElement",
					DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemInfo_installedModules"));
			installedModulesDataElementJSON.put("period", systemMonitorService.getDHISCurrentMonthPeriod());
			installedModulesDataElementJSON.put("value", systemMonitorService.getInstalledModules());
			systemRealLocationDataElementJSON2.put("dataElement",
					DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemRealLocation"));
			systemRealLocationDataElementJSON2.put("period", systemMonitorService.getDHISCurrentMonthPeriod());
			systemRealLocationDataElementJSON2.put("value", serverRealLocation != null
					? convertLocationJSONToCleanString(serverRealLocation, null) : "No Internet Connection");
			installedModulesDataElementJSON2.put("dataElement",
					DHISMapping.getDHISMappedObjectValue("DATA-ELEMENT_systemInfo_installedModules"));
			installedModulesDataElementJSON2.put("period", systemMonitorService.getDHISCurrentMonthPeriod());
			installedModulesDataElementJSON2.put("value",
					convertLocationJSONToCleanString(null, systemMonitorService.getInstalledModules()));

			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_newEncounters", encountersYesterday,
					systemMonitorService.getDHISYesterdayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_newObservations", newObs,
					systemMonitorService.getDHISYesterdayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_newUsers", newUsers,
					systemMonitorService.getDHISYesterdayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalEncounters",
					systemMonitorService.basicOpenMRSObjectCount(Encounter.class), systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalObservations",
					systemMonitorService.basicOpenMRSObjectCount(Obs.class), systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalUsers",
					systemMonitorService.basicOpenMRSObjectCount(User.class), systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalPatients",
					systemMonitorService.basicOpenMRSObjectCount(Patient.class), systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalOrders",
					systemMonitorService.basicOpenMRSObjectCount(Order.class), systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalConcepts",
					systemMonitorService.basicOpenMRSObjectCount(Concept.class), systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalLocations",
					systemMonitorService.basicOpenMRSObjectCount(Location.class), systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalForms",
					systemMonitorService.basicOpenMRSObjectCount(Form.class), systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalEncounters_new",
					systemMonitorService.basicOpenMRSObjectCountCreatedLast24Hours(Encounter.class), systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalObservations_new",
					systemMonitorService.basicOpenMRSObjectCountCreatedLast24Hours(Obs.class), systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalObservations_lastWeek",
					systemMonitorService.basicOpenMRSObjectCountCreatedLastWeek(Obs.class), systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalObservations_lastMonth",
					systemMonitorService.basicOpenMRSObjectCountCreatedLastMonth(Obs.class), systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalEncounters_lastWeek",
					systemMonitorService.basicOpenMRSObjectCountCreatedLastWeek(Encounter.class), systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalEncounters_lastMonth",
					systemMonitorService.basicOpenMRSObjectCountCreatedLastMonth(Encounter.class), systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalUsers_new",
					systemMonitorService.basicOpenMRSObjectCountCreatedLast24Hours(User.class), systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalPatients_new",
					systemMonitorService.basicOpenMRSObjectCountCreatedLast24Hours(Patient.class), systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalOrders_new",
					systemMonitorService.basicOpenMRSObjectCountCreatedLast24Hours(Order.class), systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalConcepts_new",
					systemMonitorService.basicOpenMRSObjectCountCreatedLast24Hours(Concept.class), systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalLocations_new",
					systemMonitorService.basicOpenMRSObjectCountCreatedLast24Hours(Location.class), systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalForms_new",
					systemMonitorService.basicOpenMRSObjectCountCreatedLast24Hours(Form.class), systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_previousWeekEncounters", systemMonitorService.fetchTotalEncountersCountPreviousWeek(),
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_previousMonthEncounters", systemMonitorService.fetchTotalEncountersCountPreviousMonth(),
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_previousWeekObservations", systemMonitorService.fetchTotalObservationsCountPreviousWeek(),
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_previousMonthObservations", systemMonitorService.fetchTotalObservationsCountPreviousMonth(),
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_previousWeekPatients", systemMonitorService.fetchTotalPatientsCountPreviousWeek(),
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_previousMonthPatients", systemMonitorService.fetchTotalPatientsCountPreviousMonth(),
					systemMonitorService.getDHISTodayPeriod()));
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

			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_activePatient8",
					totalActivePatients_AtleastEightMonthsARVTreatment, systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_activePatient20",
					totalActivePatients_AtleastTwentyMonthsARVTreatment, systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_activePatient8_Vl_EMR",
					totalActivePatients_AtleastEightMonthsARVTreatment_AtleastOneViralLoad_InEMR,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_activePatient8_CD4_EMR",
					totalActivePatients_AtleastEightMonthsARVTreatment_AtleastOneCD4Count_InEMR,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_activePatient20_CD4_lastYear",
					totalActivePatients_AtleastTwentyMonthsARVTreatment_AtleastOneCD4Count_LastYear,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_activePatient20_Vl_lastYear",
					totalActivePatients_AtleastTwentyMonthsARVTreatment_AtleastOneViralLoad_LastYear,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_initialVL", initialViralLoad,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_initialCD4", initialCD4Count,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_followUpCD4", followupCD4Count,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_followUpVL", followupViralLoad,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemStartupCounts", numberOfDownTimes,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_openmrsUptimeSecs", openmrsUptime,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_openmrsUptime", openMRsUpTimePercentage,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_downTimeMinutes", openmrsDowntime,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_downTimePercentage",
					openMRsDownTimePercentage, systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_downTimeIntervals", downIntervalString,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_upTimeIntervals", upIntervalString,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_serverUptimeSecs", uptime,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalEncounters_rwanda", encounterTotal,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalObservations_rwanda", obsTotal,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalUsers_rwanda", totalUsers,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalPatientsActive", totalPatientActive,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalPatientsNew", totalPatientNew,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalVisits_rwanda", totalVisits,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_dataForLastBackup", dateForLastBackUp,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemId", systemId,
					systemMonitorService.getDHISCurrentMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_processor", processor,
					systemMonitorService.getDHISCurrentMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_totalMemory", totalMemory,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_usedMemory", usedMemory,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_freeMemory", freeMemory,
					systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_openmrsAppName", openmrsAPPName,
					systemMonitorService.getDHISCurrentMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_primaryCareDays", clinicDays,
					systemMonitorService.getDHISCurrentMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_primaryCareHours", clinicHours,
					systemMonitorService.getDHISCurrentMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_operatingSystemName",
					operatingSystem, systemMonitorService.getDHISCurrentMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_operatingSystemArch",
					operatingSystemArch, systemMonitorService.getDHISCurrentMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_operatingSystemVersion",
					operatingSystemVersion, systemMonitorService.getDHISCurrentMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_javaVersion", javaVersion,
					systemMonitorService.getDHISCurrentMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_javaVendor", javaVendor,
					systemMonitorService.getDHISCurrentMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_jvmVersion", jvmVersion,
					systemMonitorService.getDHISCurrentMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_jvmVendor", jvmVendor,
					systemMonitorService.getDHISCurrentMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_userName", userName,
					systemMonitorService.getDHISCurrentMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_systemLanguage",
					systemLanguage, systemMonitorService.getDHISCurrentMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_systemTimezone",
					systemTimezone, systemMonitorService.getDHISCurrentMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_userDirectory", userDirectory,
					systemMonitorService.getDHISCurrentMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_tempDirectory", tempDirectory,
					systemMonitorService.getDHISCurrentMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_javaRuntimeName",
					javaRuntimeName, systemMonitorService.getDHISCurrentMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_javaRuntimeVersion",
					javaRuntimeVersion, systemMonitorService.getDHISCurrentMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_systemDateTime",
					systemDateTime, systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_executionDateTime",
					date.getTime().toString(), systemMonitorService.getDHISTodayPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_fileSystemEncoding",
					fileSystemEncoding, systemMonitorService.getDHISCurrentMonthPeriod()));
			jsonDataValueSets.put(createBasicIndicatorJSONObject("DATA-ELEMENT_systemInfo_openMRSVersion",
					openmrsVersion, systemMonitorService.getDHISCurrentMonthPeriod()));

			jsonToBePushed = new JSONArray(jsonDataValueSets.toString());
			jsonToBePushed.put(systemRealLocationDataElementJSON2);
			jsonToBePushed.put(installedModulesDataElementJSON2);
			jsonDataValueSets.put(systemRealLocationDataElementJSON);
			jsonDataValueSets.put(installedModulesDataElementJSON);
			metricDetails = addMetricDetailsLikeNamesToAllDataJSON(jsonDataValueSets);
		}
		jsonObj.put("dataValues", metricDetails);
		jsonObj2.put("dataValues", jsonToBePushed);
		finalJSON.put("allData", jsonObj);
		finalJSON.put("toBePushed", jsonObj2);

		return finalJSON;
	}

	private static JSONArray addMetricDetailsLikeNamesToAllDataJSON(JSONArray allData) {
		JSONArray newJson = new JSONArray();

		if (allData != null && allData.length() > 0) {
			for (int i = 0; i < allData.length(); i++) {
				if (allData.getJSONObject(i) != null && allData.getJSONObject(i).has("dataElement")) {
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
				if(metric.has("name"))
					return metric.getString("name");
				else
					return metric.getString("displayName");
			}
		}
		return null;
	}

	private static String convertLocationJSONToCleanString(JSONObject locationsJson, JSONArray modulesJson) {
		String string = null;
		String region = "";
		String hostname = "";
		String country = "";
		String city = "";
		String org = "";
		String ip = "";
		String loc = "";
		
		if (locationsJson != null && modulesJson == null) {// is Location
			try {
				region = "Region: " + locationsJson.getString("region") + "; ";
				hostname = "Hostname: " + locationsJson.getString("hostname");
				city = locationsJson.getString("city");
				ip = locationsJson.getString("ip");
				country = locationsJson.getString("country");
				org = locationsJson.getString("org");
				loc = locationsJson.getString("loc");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			string = region + hostname + "; ISP: " + org
					+ "; Country: " + country + "; City: "
					+ city + "; IP Address: " + ip
					+ "; Location(Latitude,Longitude): " + loc;
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
			if(StringUtils.isNotBlank(dhisDataElementMappingCode))
				json.put("comment", dhisDataElementMappingCode.replace("DATA-ELEMENT_", ""));
		}
		return json;
	}

	private static String convertUpOrDownTimeIntervalsToString(List<UpOrDownTimeInterval> upOrDownIntervals) {
		String upOrDownIntervalsString = "";

		if (upOrDownIntervals != null && upOrDownIntervals.size() > 0) {
			upOrDownIntervalsString = "On: "
					+ new SimpleDateFormat("EEE, d MMM yyyy z").format(upOrDownIntervals.get(0).getIntervalStoppingAt())
					+ ", EMR was " + upOrDownIntervals.get(0).getUpOrDown() + " during intervals: ";
			for (int i = 0; i < upOrDownIntervals.size(); i++) {
				UpOrDownTimeInterval upOrDown = upOrDownIntervals.get(i);
				String startWith = "";
				SimpleDateFormat hhmm = new SimpleDateFormat("HH:mm");

				if (i != 0)
					startWith = ", ";
				upOrDownIntervalsString += startWith + "("
						+ (upOrDown.getIntervalStartingAt() != null
								? (hhmm.format(upOrDown.getIntervalStartingAt()) + " to ") : "by ")
						+ hhmm.format(upOrDown.getIntervalStoppingAt()) + ")" + " for: "
						+ upOrDown.getTotalUpOrDownTime() + " minutes";
			}
		}

		return upOrDownIntervalsString;
	}
}