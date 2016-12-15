package org.openmrs.module.systemmonitor;

import java.io.File;
import java.util.Collection;

import org.openmrs.api.context.Context;
import org.openmrs.module.Module;
import org.openmrs.module.ModuleFactory;
import org.openmrs.module.ModuleUtil;
import org.openmrs.util.OpenmrsConstants;
import org.openmrs.util.OpenmrsUtil;
import org.openmrs.web.WebConstants;

public class SystemMonitorConstants {

	public static String SYSTEMMONITOR_DIRECTORYNAME = "SystemMonitor";

	public static String SYSTEMMONITOR_DATA_DIRECTORYNAME = "Data";

	public static String SYSTEMMONITOR_LOGS_DIRECTORYNAME = "Logs";

	public static String SYSTEMMONITOR_DATA_PREFIX = "dhis-data-";

	public static String SYSTEMMONITOR_LOGS_PREFIX = "dhis-push-";

	public static String SYSTEMMONITOR_MAPPING_FILENAME = "dhis-mappings.properties";

	public static String SYSTEMMONITOR_DATAELEMENTSMETADATA_FILENAME = "dhis-dataelementsMetadata.json";

	public static String SYSTEMMONITOR_ORGUNIT_FILENAME = "dhis-configuredOrgUnit.json";

	public static String IP_INFO_URL = "http://ipinfo.io/";

	public static String DHIS_API_DATAVALUES_URL = "/dataValueSets/";

	public static String DHIS_API_ORGUNITS_URL = "/organisationUnits/";

	public static String DHIS_API_DATAELEMENTS_URL = "/dataElements/";

	public static String OPENMRS_WEBAPPNAME = WebConstants.WEBAPP_NAME;

	public static String OPENMRS_VERSION = OpenmrsConstants.OPENMRS_VERSION;

	public static String SYSTEMMONITOR_DIRECTORYPATH = OpenmrsUtil.getApplicationDataDirectory() + File.separator
			+ SYSTEMMONITOR_DIRECTORYNAME;

	public static String SYSTEMMONITOR_DATA_DIRECTORYPATH = OpenmrsUtil.getApplicationDataDirectory() + File.separator
			+ SYSTEMMONITOR_DIRECTORYNAME + File.separator + SYSTEMMONITOR_DATA_DIRECTORYNAME;

	public static String SYSTEMMONITOR_LOGS_DIRECTORYPATH = OpenmrsUtil.getApplicationDataDirectory() + File.separator
			+ SYSTEMMONITOR_DIRECTORYNAME + File.separator + SYSTEMMONITOR_LOGS_DIRECTORYNAME;

	public static File MODULE_REPOSITORY = ModuleUtil.getModuleRepository();

	public static File SYSTEMMONITOR_DIRECTORY = OpenmrsUtil
			.getDirectoryInApplicationDataDirectory(SYSTEMMONITOR_DIRECTORYNAME);

	public static File OPENMRSDATA_DIRECTORY = new File(OpenmrsUtil.getApplicationDataDirectory());

	public static File SYSTEMMONITOR_FINAL_MAPPINGFILE = new File(
			SYSTEMMONITOR_DIRECTORYPATH + File.separator + SYSTEMMONITOR_MAPPING_FILENAME);

	public static File SYSTEMMONITOR_DATAELEMENTSMETADATA_FILE = new File(
			SYSTEMMONITOR_DIRECTORYPATH + File.separator + SYSTEMMONITOR_DATAELEMENTSMETADATA_FILENAME);

	public static File SYSTEMMONITOR_ORGUNIT_FILE = new File(
			SYSTEMMONITOR_DIRECTORYPATH + File.separator + SYSTEMMONITOR_ORGUNIT_FILENAME);

	public static Collection<Module> LOADED_MODULES = ModuleFactory.getLoadedModules();

	public static String CLINIC_OPENING_DAYS = Context.getAdministrationService()
			.getGlobalProperty(ConfigurableGlobalProperties.CONFIGS_OPENNINGDAYS);

	public static String CLINIC_OPENING_HOUR = Context.getAdministrationService()
			.getGlobalProperty(ConfigurableGlobalProperties.CONFIGS_OPENNINGHOUR);

	public static String CLINIC_CLOSING_HOUR = Context.getAdministrationService()
			.getGlobalProperty(ConfigurableGlobalProperties.CONFIGS_CLOSINGHOUR);

	public static File SYSTEMMONITOR_LOGSFOLDER = new File(SystemMonitorConstants.SYSTEMMONITOR_LOGS_DIRECTORYPATH);

	public static File SYSTEMMONITOR_BACKUPFOLDER = new File(SystemMonitorConstants.SYSTEMMONITOR_DATA_DIRECTORYPATH);

	public static String SCHEDULER_TASKCLASS_LOCALCLEANER = "org.openmrs.module.systemmonitor.scheduler.LocalLogsAndDHISDataCleanerTask";

	public static String SCHEDULER_TASKCLASS_PUSH = "org.openmrs.module.systemmonitor.scheduler.PushMonitoredDataTask";

	public static String SCHEDULER_TASKCLASS_UPDATESHISMETADATA = "org.openmrs.module.systemmonitor.scheduler.UpdateLocallyStoredDHISMetadataFromRemoteTask";

	public static String USED_MEMORY_FILE_NAME = "used-memory.txt";

	public static File SYSTEMMONITOR_MEMORYDATAFILE = new File(
			SYSTEMMONITOR_DATA_DIRECTORYPATH + File.separator + USED_MEMORY_FILE_NAME);

	public static String OPENMRS_UPANDDOWNTIME_FILE_NAME = "openmrs-upanddowntime.txt";

	public static File OPENMRS_UPANDDOWNTIME_FILE = new File(
			SYSTEMMONITOR_DATA_DIRECTORYPATH + File.separator + OPENMRS_UPANDDOWNTIME_FILE_NAME);

}
