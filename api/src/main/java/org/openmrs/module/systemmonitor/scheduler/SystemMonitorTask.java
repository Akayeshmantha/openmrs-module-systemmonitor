package org.openmrs.module.systemmonitor.scheduler;

import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.ContextAuthenticationException;
import org.openmrs.module.ModuleUtil;
import org.openmrs.module.systemmonitor.SystemMonitorConstants;
import org.openmrs.scheduler.SchedulerConstants;

public class SystemMonitorTask {
	public static void authenticateHack() {
		try {
			try {
				if (!ModuleUtil.matchRequiredVersions(SystemMonitorConstants.OPENMRS_VERSION, "1.9")
						&& !Context.isAuthenticated()) {
					AdministrationService adminService = Context.getAdministrationService();
					Context.authenticate(adminService.getGlobalProperty(SchedulerConstants.SCHEDULER_USERNAME_PROPERTY),
							adminService.getGlobalProperty(SchedulerConstants.SCHEDULER_PASSWORD_PROPERTY));
				}
			} catch (ContextAuthenticationException e) {
				System.out.println("Error authenticating user: " + e);
			}

		} catch (ContextAuthenticationException e) {
			System.out.println("Error authenticating user: " + e);
		}
	}
}
