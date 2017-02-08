package org.openmrs.module.systemmonitor.scheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.systemmonitor.memory.MemoryAggregation;
import org.openmrs.scheduler.tasks.AbstractTask;

public class MemoryAggregationTask extends AbstractTask {
	private Log log = LogFactory.getLog(getClass());

	@Override
	public void execute() {
		log.info("Executing " + getClass() + " background task");
		SystemMonitorTask.authenticateHack();
		MemoryAggregation.logCurrentUsedMemory();
	}

}
