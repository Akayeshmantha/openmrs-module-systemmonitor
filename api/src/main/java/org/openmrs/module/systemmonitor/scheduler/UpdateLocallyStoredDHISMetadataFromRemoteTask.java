package org.openmrs.module.systemmonitor.scheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.ContextAuthenticationException;
import org.openmrs.module.systemmonitor.api.SystemMonitorService;
import org.openmrs.scheduler.SchedulerConstants;
import org.openmrs.scheduler.tasks.AbstractTask;

/**
 * This Task sends data to a configured remote task and is represented by
 * scheduler_task_config entry named: 'Update Locally Stored DHIS Metadata'
 * meant to run once a day
 * 
 * @author k-joseph
 *
 */
public class UpdateLocallyStoredDHISMetadataFromRemoteTask extends AbstractTask {
	private Log log = LogFactory.getLog(getClass());

	@Override
	public void execute() {
		authenticateHack();
		Context.getService(SystemMonitorService.class).updateLocallyStoredDHISMetadata();
		Context.closeSession();
	}

	protected void authenticateHack() {
		try {
			try {
				Context.openSession();

				AdministrationService adminService = Context.getAdministrationService();
				Context.authenticate(adminService.getGlobalProperty(SchedulerConstants.SCHEDULER_USERNAME_PROPERTY),
						adminService.getGlobalProperty(SchedulerConstants.SCHEDULER_PASSWORD_PROPERTY));
			} catch (ContextAuthenticationException e) {
				log.error("Error authenticating user", e);
			}

		} catch (ContextAuthenticationException e) {
			log.error("Error authenticating user", e);
		}
	}
}
