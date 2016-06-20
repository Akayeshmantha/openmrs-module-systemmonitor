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
package org.openmrs.module.systemmonitor.api.db;

import java.util.Date;

import org.openmrs.module.systemmonitor.api.SystemMonitorService;

/**
 *  Database methods for {@link SystemMonitorService}.
 */
public interface SystemMonitorDAO {

	Date getThisWeekEndDate();

	Date getThisWeekStartDate();

	Date getToday();

	Date getLastWeekStartDate();

	Date getLastYearEndDate();

	Date getLastYearStartDate();

	Date getThisYearEndDate();

	Date getThisYearStartDate();

	Date getLastMonthEndDate();

	Date getLastMonthStartDate();

	Date getThisMonthEndDate();

	Date getThisMonthStartDate();

	Date getLastWeekEndDate();

	Integer getTotalPatientsNewToday(Boolean includeRetired);

	Integer getTotalVisitsToday(Boolean includeRetired);

	Integer getTotalObservationsToday(Boolean includeRetired);

	Integer getTotalUsersToday(Boolean includeRetired);

	Integer getTotalEncountersToday(Boolean includeRetired);

	Integer getTotalEncountersThisWeek(Boolean includeRetired);

	Integer getTotalEncountersLastWeek(Boolean includeRetired);

	Integer getTotalEncountersLastMonth(Boolean includeRetired);

	Integer getTotalEncountersThisMonth(Boolean includeRetired);

	Integer getTotalEncountersThisYear(Boolean includeRetired);

	Integer getTotalEncountersLastYear(Boolean includeRetired);
	
	/*
	 * Add DAO methods here
	 */
}