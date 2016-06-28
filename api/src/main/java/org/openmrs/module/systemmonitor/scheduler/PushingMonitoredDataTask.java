package org.openmrs.module.systemmonitor.scheduler;

public class PushingMonitoredDataTask extends SystemMonitoringTask {

	@Override
	protected void executeTask() {
		getSystemMonitorService().pushMonitoredDataToDHIS();
	}

}
