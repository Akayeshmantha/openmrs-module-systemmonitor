package org.openmrs.module.systemmonitor;

import java.io.File;
import java.util.Collection;

import org.openmrs.module.Module;
import org.openmrs.module.ModuleFactory;
import org.openmrs.module.ModuleUtil;
import org.openmrs.util.OpenmrsUtil;
import org.openmrs.web.WebConstants;

public class OpenMRSPropertiesIndicators {
	public String OPENMRSWEBAPPNAME = WebConstants.WEBAPP_NAME;

	public static File MODULEREPOSITORY = ModuleUtil.getModuleRepository();

	public static File SYSTEMMONITORDIRECTORY = OpenmrsUtil.getDirectoryInApplicationDataDirectory("SystemMonitor");

	public static File OPENMRSDATADIRECTORY = new File(OpenmrsUtil.getApplicationDataDirectory());

	public static File OPENMRSFINALMAPPINGFILE = new File(
			OpenmrsUtil.getApplicationDataDirectory() + File.separator + "mapping-to-dhis.txt");

	public static Collection<Module> LOADEDMODULES = ModuleFactory.getLoadedModules();
}
