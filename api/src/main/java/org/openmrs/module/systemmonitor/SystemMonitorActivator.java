/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.systemmonitor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.GlobalProperty;
import org.openmrs.api.context.Context;
import org.openmrs.module.Activator;

/**
 * This class contains the logic that is run every time this module is either
 * started or stopped.
 */
public class SystemMonitorActivator implements Activator {

	protected Log log = LogFactory.getLog(getClass());

	@Override
	public void shutdown() {
		log.info("Shutting down System Monitor Module");
	}

	@Override
	public void startup() {
		Context.addProxyPrivilege("Manage Global Properties");
		Context.addProxyPrivilege("Manage Scheduler");

		GlobalProperty gp = Context.getAdministrationService()
				.getGlobalPropertyObject("systemmonitor.database_version");
		GlobalProperty numberOfSecondsAtOpenMRSStartupGp = Context.getAdministrationService()
				.getGlobalPropertyObject("systemmonitor.numberOfSecondsAtOpenMRSStartup");
		GlobalProperty dhisUser = Context.getAdministrationService()
				.getGlobalPropertyObject("systemmonitor.DHISUSERNAME");
		GlobalProperty dhisPass = Context.getAdministrationService()
				.getGlobalPropertyObject("systemmonitor.DHISPASSWORD");

		log.info("Starting System Monitor Module");

		if (numberOfSecondsAtOpenMRSStartupGp != null) {
			numberOfSecondsAtOpenMRSStartupGp.setPropertyValue(Long.toString(System.currentTimeMillis() / 1000));
			Context.getAdministrationService().saveGlobalProperty(numberOfSecondsAtOpenMRSStartupGp);
		}
		if (gp != null && "1.0-SNAPSHOT".equals(gp.getPropertyValue())) {
			gp.setPropertyValue("1.0");
			Context.getAdministrationService().saveGlobalProperty(gp);
		}
		if (dhisUser != null && "admin".equals(dhisUser.getPropertyValue())) {
			dhisUser.setPropertyValue("sphentry");
			Context.getAdministrationService().saveGlobalProperty(dhisUser);
		}
		if (dhisPass != null && "district".equals(dhisPass.getPropertyValue())) {
			dhisPass.setPropertyValue("SphDataEntry123");
			Context.getAdministrationService().saveGlobalProperty(dhisPass);
		}
/*
		TaskDefinition pushTask = Context.getSchedulerService().getTaskByName("Push System Monitored Data to DHIS");
		TaskDefinition metadataTask = Context.getSchedulerService()
				.getTaskByName("Update Locally Stored DHIS Metadata");
		TaskDefinition cleanTask = Context.getSchedulerService()
				.getTaskByName("Clean/Delete Old Local Logs and DHIS Data");
		TaskDefinition memoTask = Context.getSchedulerService().getTaskByName("Used Memory Aggregation");
		TaskDefinition uptimeTask = Context.getSchedulerService().getTaskByName("OpenMRS Uptime Evaluation");
		try {
			if (pushTask != null) {
				pushTask.setStartOnStartup(false);
				Context.getSchedulerService().scheduleTask(pushTask);
			}
			if (metadataTask != null) {
				metadataTask.setStartOnStartup(false);
				Context.getSchedulerService().scheduleTask(metadataTask);
			}
			if (cleanTask != null) {
				cleanTask.setStartOnStartup(false);
				Context.getSchedulerService().scheduleTask(cleanTask);
			}
			if (memoTask != null) {
				memoTask.setStartOnStartup(false);
				Context.getSchedulerService().scheduleTask(memoTask);
			}
			if (uptimeTask != null) {
				uptimeTask.setStartOnStartup(false);
				Context.getSchedulerService().scheduleTask(uptimeTask);
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
*/
	}
}
