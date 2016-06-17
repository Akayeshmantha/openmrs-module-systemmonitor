package org.openmrs.module.systemmonitor.scheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.scheduler.tasks.AbstractTask;

/**
 * Sample class of how a scheduled task may look like, this executes nothing for now
 * 
 * @author k-joseph
 *
 */
public class SystemMonitoringTask extends AbstractTask {
	private static final Log log = LogFactory.getLog(SystemMonitoringTask.class);

	/**
	 * @see org.openmrs.scheduler.tasks.AbstractTask#execute()
	 */
	@Override
	public void execute() {
		if (!isExecuting) {
			if (log.isDebugEnabled()) {
				log.debug("Starting Auto Close Visits Task...");
			}

			startExecuting();
			try {
				// TODO run task here such as pushing to DHIS instance, monitor
				// data as what EMT does through cronejobs
			} catch (Exception e) {
				log.error("Error while auto closing visits:", e);
			} finally {
				stopExecuting();
			}
		}
	}
}
