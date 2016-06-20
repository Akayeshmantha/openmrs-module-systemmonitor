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
package org.openmrs.module.systemmonitor.api.impl;

import org.openmrs.api.impl.BaseOpenmrsService;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.systemmonitor.api.SystemMonitorService;
import org.openmrs.module.systemmonitor.api.db.SystemMonitorDAO;

/**
 * It is a default implementation of {@link SystemMonitorService}.
 */
public class SystemMonitorServiceImpl extends BaseOpenmrsService implements SystemMonitorService {

	protected final Log log = LogFactory.getLog(this.getClass());

	private SystemMonitorDAO dao;

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(SystemMonitorDAO dao) {
		this.dao = dao;
	}

	/**
	 * @return the dao
	 */
	public SystemMonitorDAO getDao() {
		return dao;
	}

	@Override
	public Date getThisWeekEndDate() {
		return dao.getThisWeekEndDate();
	}

	@Override
	public Date getThisWeekStartDate() {
		return dao.getThisWeekStartDate();
	}

	@Override
	public Date getToday() {
		return dao.getToday();
	}

	@Override
	public Date getLastWeekStartDate() {
		return dao.getLastWeekStartDate();
	}

	@Override
	public Date getLastYearEndDate() {
		return dao.getLastYearEndDate();
	}

	@Override
	public Date getLastYearStartDate() {
		return dao.getLastYearStartDate();
	}

	@Override
	public Date getThisYearEndDate() {
		return dao.getThisYearEndDate();
	}

	@Override
	public Date getThisYearStartDate() {
		return dao.getThisYearStartDate();
	}

	@Override
	public Date getLastMonthEndDate() {
		return dao.getLastMonthEndDate();
	}

	@Override
	public Date getLastMonthStartDate() {
		return dao.getLastMonthStartDate();
	}

	@Override
	public Date getThisMonthEndDate() {
		return dao.getThisMonthEndDate();
	}

	@Override
	public Date getThisMonthStartDate() {
		return dao.getThisMonthStartDate();
	}

	@Override
	public Date getLastWeekEndDate() {
		return dao.getLastWeekEndDate();
	}

	@Override
	public Integer getTotalPatientsNewToday(Boolean includeRetired) {
		return dao.getTotalPatientsNewToday(includeRetired);
	}

	@Override
	public Integer getTotalVisitsToday(Boolean includeRetired) {
		return dao.getTotalVisitsToday(includeRetired);
	}

	@Override
	public Integer getTotalObservationsToday(Boolean includeRetired) {
		return dao.getTotalObservationsToday(includeRetired);
	}

	@Override
	public Integer getTotalUsersToday(Boolean includeRetired) {
		return dao.getTotalUsersToday(includeRetired);
	}

	@Override
	public Integer getTotalEncountersToday(Boolean includeRetired) {
		return dao.getTotalEncountersToday(includeRetired);
	}

	@Override
	public Integer getTotalEncountersThisWeek(Boolean includeRetired) {
		return dao.getTotalEncountersThisWeek(includeRetired);
	}

	@Override
	public Integer getTotalEncountersLastWeek(Boolean includeRetired) {
		return dao.getTotalEncountersLastWeek(includeRetired);
	}

	@Override
	public Integer getTotalEncountersLastMonth(Boolean includeRetired) {
		return dao.getTotalEncountersLastMonth(includeRetired);
	}

	@Override
	public Integer getTotalEncountersThisMonth(Boolean includeRetired) {
		return dao.getTotalEncountersThisMonth(includeRetired);
	}

	@Override
	public Integer getTotalEncountersThisYear(Boolean includeRetired) {
		return dao.getTotalEncountersThisYear(includeRetired);
	}

	@Override
	public Integer getTotalEncountersLastYear(Boolean includeRetired) {
		return dao.getTotalEncountersLastYear(includeRetired);
	}
}