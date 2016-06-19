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

	public File MODULEREPOSITORY = ModuleUtil.getModuleRepository();

	public File EMTDIRECTORY = OpenmrsUtil.getDirectoryInApplicationDataDirectory("EmrMonitoringTool");

	public String OPENMRSDATADIRECTORYPATH = OpenmrsUtil.getApplicationDataDirectory();

	public Collection<Module> LOADEDMODULES = ModuleFactory.getLoadedModules();
}
