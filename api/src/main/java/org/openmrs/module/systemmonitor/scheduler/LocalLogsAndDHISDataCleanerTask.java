package org.openmrs.module.systemmonitor.scheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.systemmonitor.api.SystemMonitorService;
import org.openmrs.scheduler.tasks.AbstractTask;

public class LocalLogsAndDHISDataCleanerTask extends AbstractTask {

	private Log log = LogFactory.getLog(getClass());

	@Override
	public void execute() {
		log.info("Executing " + getClass() + " background task");
		SystemMonitorTask.authenticateHack();
		Context.getService(SystemMonitorService.class).cleanOldLocallyStoredLogsAndDHISData();
	}
}
