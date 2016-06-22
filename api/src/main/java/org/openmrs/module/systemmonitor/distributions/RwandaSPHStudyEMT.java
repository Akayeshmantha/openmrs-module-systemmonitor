package org.openmrs.module.systemmonitor.distributions;

import java.util.Calendar;

import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.systemmonitor.SystemMonitorConstants;
import org.openmrs.module.systemmonitor.api.SystemMonitorService;
import org.openmrs.module.systemmonitor.curl.CurlEmulator;
import org.openmrs.module.systemmonitor.export.DHISGenerateDataValueSetSchemas;
import org.openmrs.module.systemmonitor.indicators.OSAndHardwareIndicators;
import org.openmrs.module.systemmonitor.indicators.SystemPropertiesIndicators;

/**
 * This interfaces SystemMonitor API and only retrieves indicators for
 * https://github.com/jembi/openmrs-emt-ubuntu (Rwanda SPH Study Monitoring
 * Tool)
 * 
 * @author k-joseph
 *
 */
public class RwandaSPHStudyEMT {

	private SystemMonitorService systemMonitorService = Context.getService(SystemMonitorService.class);

	private String systemId = OSAndHardwareIndicators.getHostName();

	private String dhisOrganizationUnitUid = systemMonitorService.getCurrentConfiguredDHISOrgUnit();

	private String clinicDays = null;

	private String clinicHours = null;

	private Integer encounterTotal = systemMonitorService.rwandaPIHEMTGetTotalEncounters();

	private Integer obsTotal = systemMonitorService.rwandaPIHEMTGetTotalObservations();

	private Integer totalUsers = systemMonitorService.rwandaPIHEMTGetTotalUsers();

	private String openmrsAPPName = SystemMonitorConstants.OPENMRS_WEBAPPNAME;

	private Integer totalPatientActive = systemMonitorService.rwandaPIHEMTGetTotalActivePatients();

	private Integer totalPatientNew = systemMonitorService.rwandaPIHEMTGetTotalNewPatients();

	private Integer totalVisits = systemMonitorService.rwandaPIHEMTGetTotalVisits();

	private Integer startupCounts = null;

	/*
	 * TODO evaluate this for a week and respectively
	 */
	private Long thisWeekUptime = OSAndHardwareIndicators.PROCESSOR_SYSTEM_UPTIME;

	private Integer previousWeekUptime = null;

	private Integer previousMonthUptime = null;

	private Long freeMemory = OSAndHardwareIndicators.MEMORY_AVAILABLE;

	private Long usedMemory = OSAndHardwareIndicators.MEMORY_USED;

	private Long totalMemory = OSAndHardwareIndicators.MEMORY_TOTAL;

	private Integer openmrsUptimePercentage = null;

	private Integer viralLoadTestResultsEver = systemMonitorService.getTotalViralLoadTestsEver();

	private Integer viralLoadTestResultsLastSixMonths = systemMonitorService.getTotalViralLoadTestsLastSixMonths();

	private Integer viralLoadTestResultsLastYear = systemMonitorService.getTotalViralLoadTestsLastYear();

	private String operatingSystem = SystemPropertiesIndicators.OS_NAME + ", Family: "
			+ OSAndHardwareIndicators.OS_FAMILY + ", Manufacturer: " + OSAndHardwareIndicators.OS_MANUFACTURER
			+ ", Version Name: " + OSAndHardwareIndicators.OS_VERSION_NAME + ", Version Number: "
			+ OSAndHardwareIndicators.OS_VERSION_NUMBER + ", Build Number: "
			+ OSAndHardwareIndicators.OS_VERSION_BUILDNUMBER;

	private String operatingSystemArch = SystemPropertiesIndicators.OS_ARCH;

	private String operatingSystemVersion = SystemPropertiesIndicators.OS_VERSION;

	private String javaVersion = SystemPropertiesIndicators.JAVA_VERSION;

	private String javaVendor = SystemPropertiesIndicators.JAVA_VENDOR;

	private String jvmVersion = SystemPropertiesIndicators.JVM_VERSION;

	private String jvmVendor = SystemPropertiesIndicators.JVM_VENDOR;

	private String javaRuntimeName = SystemPropertiesIndicators.JAVA_RUNTIMENAME;

	private String javaRuntimeVersion = SystemPropertiesIndicators.JAVA_RUNTIMEVERSION;

	private String userName = SystemPropertiesIndicators.USERNAME;

	private String systemLanguage = SystemPropertiesIndicators.SYSTEM_LANGUAGE;

	private String systemTimezone = SystemPropertiesIndicators.SYSTEM_TIMEZONE;

	private String userDirectory = SystemPropertiesIndicators.USER_DIRECTORY;

	private String fileSystemEncoding = SystemPropertiesIndicators.FILESYSTEM_ENCODING;

	private String systemDateTime = Calendar.getInstance(Context.getLocale()).getTime().toString();

	private String openmrsVersion = SystemMonitorConstants.OPENMRS_VERSION;

	private String tempDirectory = SystemPropertiesIndicators.TEMP_FOLDER;

	private JSONObject serverRealLocation = CurlEmulator
			.get(SystemMonitorConstants.IP_INFO_URL + OSAndHardwareIndicators.getIpAddress());

	private JSONObject getInstalledModules() {
		return systemMonitorService.getInstalledModules();
	}

	public JSONObject generatedDHISDataValueSetJSONString() {
		return DHISGenerateDataValueSetSchemas.generateRwandaSPHEMTDHISDataValueSets(systemId, dhisOrganizationUnitUid,
				clinicDays, clinicHours, encounterTotal, obsTotal, totalUsers, openmrsAPPName, totalPatientActive,
				totalPatientNew, totalVisits, startupCounts, thisWeekUptime, previousWeekUptime, previousMonthUptime,
				freeMemory, usedMemory, totalMemory, openmrsUptimePercentage, viralLoadTestResultsEver,
				viralLoadTestResultsLastSixMonths, viralLoadTestResultsLastYear, operatingSystem, operatingSystemArch,
				operatingSystemVersion, javaVersion, javaVendor, jvmVersion, jvmVendor, javaRuntimeName,
				javaRuntimeVersion, userName, systemLanguage, systemTimezone, userDirectory, fileSystemEncoding,
				systemDateTime, openmrsVersion, getInstalledModules(), tempDirectory, serverRealLocation);
	};

}
