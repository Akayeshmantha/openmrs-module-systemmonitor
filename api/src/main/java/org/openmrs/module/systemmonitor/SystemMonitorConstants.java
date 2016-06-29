package org.openmrs.module.systemmonitor;

import java.io.File;
import java.util.Collection;

import org.openmrs.module.Module;
import org.openmrs.module.ModuleFactory;
import org.openmrs.module.ModuleUtil;
import org.openmrs.util.OpenmrsConstants;
import org.openmrs.util.OpenmrsUtil;
import org.openmrs.web.WebConstants;

public class SystemMonitorConstants {

	public static String SYSTEMMONITOR_DIRECTORYNAME = "SystemMonitor";

	public static String SYSTEMMONITOR_MAPPING_FILENAME = "mapping-to-dhis.txt";

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
}
