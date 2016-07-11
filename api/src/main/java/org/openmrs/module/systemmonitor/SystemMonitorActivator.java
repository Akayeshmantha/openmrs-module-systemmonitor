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
import org.openmrs.api.context.Context;
import org.openmrs.module.ModuleActivator;
import org.openmrs.module.systemmonitor.api.SystemMonitorService;

/**
 * This class contains the logic that is run every time this module is either
 * started or stopped.
 */
public class SystemMonitorActivator implements ModuleActivator {

	protected Log log = LogFactory.getLog(getClass());

	@Override
	public void contextRefreshed() {
	}

	@Override
	public void started() {
		Context.getService(SystemMonitorService.class).transferDHISMappingsAndMetadataFileToDataDirectory();
		Context.getService(SystemMonitorService.class).updateLocallyStoredDHISMetadata();
		Context.getService(SystemMonitorService.class)
				.updateNumberOfSecondsAtOpenMRSStartup(System.currentTimeMillis() / 1000);
	}

	@Override
	public void stopped() {
	}

	@Override
	public void willRefreshContext() {
	}

	@Override
	public void willStart() {
		log.info("Starting System Monitor Module");
	}

	@Override
	public void willStop() {
		log.info("Shutting down System Monitor Module");
	}
}
