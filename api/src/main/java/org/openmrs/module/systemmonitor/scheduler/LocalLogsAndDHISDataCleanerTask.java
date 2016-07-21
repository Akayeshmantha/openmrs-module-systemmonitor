package org.openmrs.module.systemmonitor.scheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.ModuleUtil;
import org.openmrs.module.systemmonitor.SystemMonitorConstants;
import org.openmrs.module.systemmonitor.api.SystemMonitorService;
import org.openmrs.scheduler.tasks.AbstractTask;

public class LocalLogsAndDHISDataCleanerTask extends AbstractTask {

	private Log log = LogFactory.getLog(getClass());

	@SuppressWarnings("deprecation")
	@Override
	public void execute() {
		try {
			if (!ModuleUtil.matchRequiredVersions(SystemMonitorConstants.OPENMRS_VERSION, "1.9")
					&& !Context.isAuthenticated()) {
				authenticate();
			}
		} catch (Exception e) {
			log.error(e);
		}
		Context.getService(SystemMonitorService.class).cleanOldLocallyStoredLogsAndDHISData();
	}
}
