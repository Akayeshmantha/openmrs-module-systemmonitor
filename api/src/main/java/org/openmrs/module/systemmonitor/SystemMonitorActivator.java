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
import org.openmrs.module.Activator;
import org.openmrs.module.systemmonitor.api.impl.SystemMonitorServiceImpl;

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
		log.info("Starting up System Monitor Module");

		// crazy but can't reach service before it's loaded
		SystemMonitorServiceImpl service = new SystemMonitorServiceImpl();

		service.transferDHISMappingsAndMetadataFileToDataDirectory();
		service.updateLocallyStoredDHISMetadata();
		service.updateNumberOfSecondsAtOpenMRSStartup(System.currentTimeMillis() / 1000);
	}
}
