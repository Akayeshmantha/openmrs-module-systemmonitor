package org.openmrs.module.systemmonitor.scheduler;

import org.openmrs.api.context.Context;
import org.openmrs.module.systemmonitor.api.SystemMonitorService;
import org.openmrs.scheduler.tasks.AbstractTask;

public class LocalLogsAndDHISDataCleanerTask extends AbstractTask {

	@Override
	public void execute() {
		Context.getService(SystemMonitorService.class).cleanOldLocallyStoredLogsAndDHISData();
	}
}
