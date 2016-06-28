package org.openmrs.module.systemmonitor.scheduler;

import org.openmrs.api.context.Context;
import org.openmrs.module.systemmonitor.api.SystemMonitorService;
import org.openmrs.scheduler.tasks.AbstractTask;

/**
 * This Task sends data to a configured remote task and is represented by
 * scheduler_task_config entry named: 'Push System Monitored Data to DHIS' meant
 * to run within a range of 4 hours starting from 08:00 everyday
 * 
 * @author k-joseph
 *
 */
public class PushMonitoredDataTask extends AbstractTask {

	@Override
	public void execute() {
		Context.getService(SystemMonitorService.class).pushMonitoredDataToDHIS();
	}

}
