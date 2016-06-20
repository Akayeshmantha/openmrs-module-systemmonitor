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
package org.openmrs.module.systemmonitor.api.db.hibernate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.module.systemmonitor.api.db.SystemMonitorDAO;

/**
 * It is a default implementation of {@link SystemMonitorDAO}.
 */
public class HibernateSystemMonitorDAO implements SystemMonitorDAO {
	protected final Log log = LogFactory.getLog(HibernateSystemMonitorDAO.class);

	private SessionFactory sessionFactory;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * @param sessionFactory
	 *            the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	public Date getToday() {
		Calendar calendar = Calendar.getInstance(Context.getLocale());

		resetDateTimes(calendar);
		try {
			return sdf.parse(sdf.format(calendar.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Date getThisWeekStartDate() {
		Calendar calendar = Calendar.getInstance(Context.getLocale());

		resetDateTimes(calendar);
		// get start of this week in milliseconds
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
		return calendar.getTime();
	}

	private void resetDateTimes(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the
												// hour of day !
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.MILLISECOND);
	}

	@Override
	public Date getThisWeekEndDate() {
		Calendar calendar = Calendar.getInstance(Context.getLocale());
		resetDateTimes(calendar);
		Calendar first = (Calendar) calendar.clone();
		first.add(Calendar.DAY_OF_WEEK, first.getFirstDayOfWeek() - first.get(Calendar.DAY_OF_WEEK));

		Calendar last = (Calendar) first.clone();
		last.add(Calendar.DAY_OF_YEAR, 6);

		return last.getTime();
	}

	@Override
	public Date getLastWeekStartDate() {
		Calendar calendar = Calendar.getInstance(Context.getLocale());
		resetDateTimes(calendar);
		calendar.set(Calendar.WEEK_OF_YEAR, calendar.get(Calendar.WEEK_OF_YEAR) - 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the
		// hour of day !
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.MILLISECOND);

		// get start of this week in milliseconds
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
		return calendar.getTime();
	}

	@Override
	public Date getLastWeekEndDate() {
		Calendar calendar = Calendar.getInstance(Context.getLocale());
		resetDateTimes(calendar);
		Calendar first = (Calendar) calendar.clone();
		first.add(Calendar.DAY_OF_WEEK, first.getFirstDayOfWeek() - first.get(Calendar.DAY_OF_WEEK));
		first.set(Calendar.WEEK_OF_YEAR, calendar.get(Calendar.WEEK_OF_YEAR) - 1);

		Calendar last = (Calendar) first.clone();
		last.add(Calendar.DAY_OF_YEAR, 6);

		return last.getTime();
	}

	@Override
	public Date getThisMonthStartDate() {
		Calendar calendar = Calendar.getInstance(Context.getLocale());
		resetDateTimes(calendar);
		calendar.set(Calendar.DAY_OF_MONTH, 1);

		return calendar.getTime();
	}

	@Override
	public Date getThisMonthEndDate() {
		Calendar calendar = Calendar.getInstance(Context.getLocale());
		resetDateTimes(calendar);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

		return calendar.getTime();
	}

	@Override
	public Date getLastMonthStartDate() {
		Calendar calendar = Calendar.getInstance(Context.getLocale());
		resetDateTimes(calendar);
		calendar.add(Calendar.MONTH, -1);
		// set DATE to 1, so first date of previous month
		calendar.set(Calendar.DATE, 1);
		return calendar.getTime();
	}

	@Override
	public Date getLastMonthEndDate() {
		Calendar calendar = Calendar.getInstance(Context.getLocale());
		resetDateTimes(calendar);
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

		return calendar.getTime();
	}

	@Override
	public Date getThisYearStartDate() {
		Calendar calendar = Calendar.getInstance(Context.getLocale());
		resetDateTimes(calendar);
		calendar.set(Calendar.DAY_OF_YEAR, 1);

		return calendar.getTime();
	}

	@Override
	public Date getThisYearEndDate() {
		Calendar calendar = Calendar.getInstance(Context.getLocale());
		resetDateTimes(calendar);
		calendar.set(Calendar.DAY_OF_YEAR, isLeapYear(calendar.get(Calendar.YEAR)) ? 366 : 365);

		return calendar.getTime();
	}

	@Override
	public Date getLastYearStartDate() {
		Calendar calendar = Calendar.getInstance(Context.getLocale());
		resetDateTimes(calendar);
		calendar.add(Calendar.YEAR, -1);
		calendar.set(Calendar.DAY_OF_YEAR, 1);

		return calendar.getTime();
	}

	@Override
	public Date getLastYearEndDate() {
		Calendar calendar = Calendar.getInstance(Context.getLocale());
		resetDateTimes(calendar);
		calendar.add(Calendar.YEAR, -1);
		calendar.set(Calendar.DAY_OF_YEAR, isLeapYear(calendar.get(Calendar.YEAR)) ? 366 : 365);

		return calendar.getTime();
	}

	private boolean isLeapYear(int year) {
		if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Integer getTotalEncountersToday(Boolean includeRetired) {
		return getSessionFactory().getCurrentSession().createCriteria(Encounter.class)
				.add(Restrictions.eq("voided", includeRetired)).add(Restrictions
						.or(Restrictions.ge("dateChanged", getToday()), Restrictions.ge("dateCreated", getToday())))
				.list().size();
	}

	@Override
	public Integer getTotalEncountersThisWeek(Boolean includeRetired) {
		return getSessionFactory().getCurrentSession().createCriteria(Encounter.class)
				.add(Restrictions.eq("voided", includeRetired))
				.add(Restrictions.or(Restrictions.ge("dateChanged", getThisWeekStartDate()),
						Restrictions.ge("dateCreated", getThisWeekStartDate())))
				.add(Restrictions.or(Restrictions.le("dateChanged", getThisWeekEndDate()),
						Restrictions.le("dateCreated", getThisWeekEndDate())))
				.list().size();
	}

	@Override
	public Integer getTotalEncountersLastWeek(Boolean includeRetired) {
		return getSessionFactory().getCurrentSession().createCriteria(Encounter.class)
				.add(Restrictions.eq("voided", includeRetired))
				.add(Restrictions.or(Restrictions.ge("dateChanged", getLastWeekStartDate()),
						Restrictions.ge("dateCreated", getLastWeekStartDate())))
				.add(Restrictions.or(Restrictions.le("dateChanged", getLastWeekEndDate()),
						Restrictions.le("dateCreated", getLastWeekEndDate())))
				.list().size();
	}

	@Override
	public Integer getTotalEncountersLastMonth(Boolean includeRetired) {
		return getSessionFactory().getCurrentSession().createCriteria(Encounter.class)
				.add(Restrictions.eq("voided", includeRetired))
				.add(Restrictions.or(Restrictions.ge("dateChanged", getLastMonthStartDate()),
						Restrictions.ge("dateCreated", getLastMonthStartDate())))
				.add(Restrictions.or(Restrictions.le("dateChanged", getLastMonthEndDate()),
						Restrictions.le("dateCreated", getLastMonthEndDate())))
				.list().size();
	}

	@Override
	public Integer getTotalEncountersThisMonth(Boolean includeRetired) {
		return getSessionFactory().getCurrentSession().createCriteria(Encounter.class)
				.add(Restrictions.eq("voided", includeRetired))
				.add(Restrictions.or(Restrictions.ge("dateChanged", getThisMonthStartDate()),
						Restrictions.ge("dateCreated", getThisMonthStartDate())))
				.add(Restrictions.or(Restrictions.le("dateChanged", getThisMonthEndDate()),
						Restrictions.le("dateCreated", getThisMonthEndDate())))
				.list().size();
	}

	@Override
	public Integer getTotalEncountersThisYear(Boolean includeRetired) {
		return getSessionFactory().getCurrentSession().createCriteria(Encounter.class)
				.add(Restrictions.eq("voided", includeRetired))
				.add(Restrictions.or(Restrictions.ge("dateChanged", getThisYearStartDate()),
						Restrictions.ge("dateCreated", getThisYearStartDate())))
				.add(Restrictions.or(Restrictions.le("dateChanged", getThisYearEndDate()),
						Restrictions.le("dateCreated", getThisYearEndDate())))
				.list().size();
	}

	@Override
	public Integer getTotalEncountersLastYear(Boolean includeRetired) {
		return getSessionFactory().getCurrentSession().createCriteria(Encounter.class)
				.add(Restrictions.eq("voided", includeRetired))
				.add(Restrictions.or(Restrictions.ge("dateChanged", getLastYearStartDate()),
						Restrictions.ge("dateCreated", getLastYearStartDate())))
				.add(Restrictions.or(Restrictions.le("dateChanged", getLastYearEndDate()),
						Restrictions.le("dateCreated", getLastYearEndDate())))
				.list().size();
	}

	@Override
	public Integer getTotalUsersToday(Boolean includeRetired) {
		return getSessionFactory().getCurrentSession().createCriteria(User.class)
				.add(Restrictions.eq("voided", includeRetired)).add(Restrictions
						.or(Restrictions.ge("dateChanged", getToday()), Restrictions.ge("dateCreated", getToday())))
				.list().size();
	}

	public Integer getTotalUsersThisWeek(Boolean includeRetired) {
		return null;
	}

	public Integer getTotalUsersLastWeek(Boolean includeRetired) {
		return null;
	}

	public Integer getTotalUsersLastMonth(Boolean includeRetired) {
		return null;
	}

	public Integer getTotalUsersThisMonth(Boolean includeRetired) {
		return null;
	}

	public Integer getTotalUsersThisYear(Boolean includeRetired) {
		return null;
	}

	public Integer getTotalUsersLastYear(Boolean includeRetired) {
		return null;
	}

	@Override
	public Integer getTotalObservationsToday(Boolean includeRetired) {
		return getSessionFactory().getCurrentSession().createCriteria(Obs.class)
				.add(Restrictions.eq("voided", includeRetired)).add(Restrictions
						.or(Restrictions.ge("dateChanged", getToday()), Restrictions.ge("dateCreated", getToday())))
				.list().size();
	}

	public Integer getTotalObservationsThisWeek(Boolean includeRetired) {
		return null;
	}

	public Integer getTotalObservationsLastWeek(Boolean includeRetired) {
		return null;
	}

	public Integer getTotalObservationsLastMonth(Boolean includeRetired) {
		return null;
	}

	public Integer getTotalObservationsThisMonth(Boolean includeRetired) {
		return null;
	}

	public Integer getTotalObservationsThisYear(Boolean includeRetired) {
		return null;
	}

	public Integer getTotalObservationsLastYear(Boolean includeRetired) {
		return null;
	}

	@Override
	public Integer getTotalVisitsToday(Boolean includeRetired) {
		return getSessionFactory().getCurrentSession().createCriteria(Visit.class)
				.add(Restrictions.eq("voided", includeRetired)).add(Restrictions
						.or(Restrictions.ge("dateChanged", getToday()), Restrictions.ge("dateCreated", getToday())))
				.list().size();
	}

	public Integer getTotalVisitsThisWeek(Boolean includeRetired) {
		return null;
	}

	public Integer getTotalVisitsLastWeek(Boolean includeRetired) {
		return null;
	}

	public Integer getTotalVisitsLastMonth(Boolean includeRetired) {
		return null;
	}

	public Integer getTotalVisitsThisMonth(Boolean includeRetired) {
		return null;
	}

	public Integer getTotalVisitsThisYear(Boolean includeRetired) {
		return null;
	}

	public Integer getTotalVisitsLastYear(Boolean includeRetired) {
		return null;
	}

	public Integer getTotalPatientsActiveToday(Boolean includeRetired) {
		return null;
	}

	public Integer getTotalPatientsActiveThisWeek(Boolean includeRetired) {
		return null;
	}

	public Integer getTotalPatientsActiveLastWeek(Boolean includeRetired) {
		return null;
	}

	public Integer getTotalPatientsActiveLastMonth(Boolean includeRetired) {
		return null;
	}

	public Integer getTotalPatientsActiveThisMonth(Boolean includeRetired) {
		return null;
	}

	public Integer getTotalPatientsActiveThisYear(Boolean includeRetired) {
		return null;
	}

	@Override
	public Integer getTotalPatientsNewToday(Boolean includeRetired) {
		return getSessionFactory().getCurrentSession().createCriteria(Patient.class)
				.add(Restrictions.eq("voided", includeRetired)).add(Restrictions.ge("dateCreated", getToday())).list()
				.size();
	}

	public Integer getTotalPatientsNewThisWeek(Boolean includeRetired) {
		return null;
	}

	public Integer getTotalPatientsNewLastWeek(Boolean includeRetired) {
		return null;
	}

	public Integer getTotalPatientsNewLastMonth(Boolean includeRetired) {
		return null;
	}

	public Integer getTotalPatientsNewThisMonth(Boolean includeRetired) {
		return null;
	}

	public Integer getTotalPatientsNewThisYear(Boolean includeRetired) {

		return null;
	}
}