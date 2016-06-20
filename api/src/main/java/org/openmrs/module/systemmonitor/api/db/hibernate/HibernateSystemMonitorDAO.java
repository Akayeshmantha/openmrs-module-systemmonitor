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
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Form;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.Provider;
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
		return fetchTotalOpenMRSObjectCountToday(includeRetired, Encounter.class);
	}

	@Override
	public Integer getTotalEncountersThisWeek(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisWeek(includeRetired, Encounter.class);
	}

	@Override
	public Integer getTotalEncountersLastWeek(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountLastWeek(includeRetired, Encounter.class);
	}

	@Override
	public Integer getTotalEncountersLastMonth(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountLastMonth(includeRetired, Encounter.class);
	}

	@Override
	public Integer getTotalEncountersThisMonth(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisMonth(includeRetired, Encounter.class);
	}

	@Override
	public Integer getTotalEncountersThisYear(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisYear(includeRetired, Encounter.class);
	}

	@Override
	public Integer getTotalEncountersLastYear(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountLastYear(includeRetired, Encounter.class);
	}

	@Override
	public Integer getTotalUsersToday(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountToday(includeRetired, User.class);
	}

	@Override
	public Integer getTotalUsersThisWeek(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisWeek(includeRetired, User.class);
	}

	@Override
	public Integer getTotalUsersLastWeek(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountLastWeek(includeRetired, User.class);
	}

	@Override
	public Integer getTotalUsersLastMonth(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountLastMonth(includeRetired, User.class);
	}

	@Override
	public Integer getTotalUsersThisMonth(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisMonth(includeRetired, User.class);
	}

	@Override
	public Integer getTotalUsersThisYear(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisYear(includeRetired, User.class);
	}

	@Override
	public Integer getTotalUsersLastYear(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountLastYear(includeRetired, User.class);
	}

	@Override
	public Integer getTotalObservationsToday(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountToday(includeRetired, Obs.class);
	}

	@Override
	public Integer getTotalObservationsThisWeek(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisWeek(includeRetired, Obs.class);
	}

	@Override
	public Integer getTotalObservationsLastWeek(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountLastWeek(includeRetired, Obs.class);
	}

	@Override
	public Integer getTotalObservationsLastMonth(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountLastMonth(includeRetired, Obs.class);
	}

	@Override
	public Integer getTotalObservationsThisMonth(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisMonth(includeRetired, Obs.class);
	}

	@Override
	public Integer getTotalObservationsThisYear(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisYear(includeRetired, Obs.class);
	}

	@Override
	public Integer getTotalObservationsLastYear(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountLastYear(includeRetired, Obs.class);
	}

	@Override
	public Integer getTotalVisitsToday(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountToday(includeRetired, Visit.class);
	}

	@Override
	public Integer getTotalVisitsThisWeek(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisWeek(includeRetired, Visit.class);
	}

	@Override
	public Integer getTotalVisitsLastWeek(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountLastWeek(includeRetired, Visit.class);
	}

	@Override
	public Integer getTotalVisitsThisMonth(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisMonth(includeRetired, Visit.class);
	}

	@Override
	public Integer getTotalVisitsLastMonth(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountLastMonth(includeRetired, Visit.class);
	}

	@Override
	public Integer getTotalVisitsThisYear(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisYear(includeRetired, Visit.class);
	}

	@Override
	public Integer getTotalVisitsLastYear(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountLastYear(includeRetired, Visit.class);
	}

	@Override
	public Integer getTotalPatientsToday(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountToday(includeRetired, Patient.class);
	}

	@Override
	public Integer getTotalPatientsThisWeek(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisWeek(includeRetired, Patient.class);
	}

	@Override
	public Integer getTotalPatientsLastWeek(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountLastWeek(includeRetired, Patient.class);
	}

	@Override
	public Integer getTotalPatientsLastMonth(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountLastMonth(includeRetired, Patient.class);
	}

	@Override
	public Integer getTotalPatientsThisMonth(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisMonth(includeRetired, Patient.class);
	}

	@Override
	public Integer getTotalPatientsThisYear(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisYear(includeRetired, Patient.class);
	}

	@Override
	public Integer getTotalPatientsLastYear(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountLastYear(includeRetired, Patient.class);
	}

	@Override
	public Integer getTotalLocationsToday(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountToday(includeRetired, Location.class);
	}

	@Override
	public Integer getTotalLocationsThisWeek(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisWeek(includeRetired, Location.class);
	}

	@Override
	public Integer getTotalLocationsLastWeek(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountLastWeek(includeRetired, Location.class);
	}

	@Override
	public Integer getTotalLocationsLastMonth(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountLastMonth(includeRetired, Location.class);
	}

	@Override
	public Integer getTotalLocationsThisMonth(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisMonth(includeRetired, Location.class);
	}

	@Override
	public Integer getTotalLocationsThisYear(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisYear(includeRetired, Location.class);
	}

	@Override
	public Integer getTotalLocationsLastYear(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountLastYear(includeRetired, Location.class);
	}

	@Override
	public Integer getTotalConceptsLastYear(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountLastYear(includeRetired, Concept.class);
	}

	@Override
	public Integer getTotalConceptsToday(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountToday(includeRetired, Concept.class);
	}

	@Override
	public Integer getTotalConceptsThisWeek(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisWeek(includeRetired, Concept.class);
	}

	@Override
	public Integer getTotalConceptsLastWeek(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountLastWeek(includeRetired, Concept.class);
	}

	@Override
	public Integer getTotalConceptsLastMonth(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountLastMonth(includeRetired, Concept.class);
	}

	@Override
	public Integer getTotalConceptsThisMonth(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisMonth(includeRetired, Concept.class);
	}

	@Override
	public Integer getTotalConceptsThisYear(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisYear(includeRetired, Concept.class);
	}

	@Override
	public Integer getTotalFormsLastYear(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountLastYear(includeRetired, Form.class);
	}

	@Override
	public Integer getTotalFormsToday(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountToday(includeRetired, Form.class);
	}

	@Override
	public Integer getTotalFormsThisWeek(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisWeek(includeRetired, Form.class);
	}

	@Override
	public Integer getTotalFormsLastWeek(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountLastWeek(includeRetired, Form.class);
	}

	@Override
	public Integer getTotalFormsLastMonth(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountLastMonth(includeRetired, Form.class);
	}

	@Override
	public Integer getTotalFormsThisMonth(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisMonth(includeRetired, Form.class);
	}

	@Override
	public Integer getTotalFormsThisYear(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisYear(includeRetired, Form.class);
	}

	@Override
	public Integer getTotalOrdersLastYear(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountLastYear(includeRetired, Order.class);
	}

	@Override
	public Integer getTotalOrdersToday(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountToday(includeRetired, Order.class);
	}

	@Override
	public Integer getTotalOrdersThisWeek(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisWeek(includeRetired, Order.class);
	}

	@Override
	public Integer getTotalOrdersLastWeek(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountLastWeek(includeRetired, Order.class);
	}

	@Override
	public Integer getTotalOrdersLastMonth(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountLastMonth(includeRetired, Order.class);
	}

	@Override
	public Integer getTotalOrdersThisMonth(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisMonth(includeRetired, Order.class);
	}

	@Override
	public Integer getTotalOrdersThisYear(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisYear(includeRetired, Order.class);
	}

	@Override
	public Integer getTotalProvidersLastYear(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountLastYear(includeRetired, Provider.class);
	}

	@Override
	public Integer getTotalProvidersToday(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountToday(includeRetired, Provider.class);
	}

	@Override
	public Integer getTotalProvidersThisWeek(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisWeek(includeRetired, Provider.class);
	}

	@Override
	public Integer getTotalProvidersLastWeek(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountLastWeek(includeRetired, Provider.class);
	}

	@Override
	public Integer getTotalProvidersLastMonth(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountLastMonth(includeRetired, Provider.class);
	}

	@Override
	public Integer getTotalProvidersThisMonth(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisMonth(includeRetired, Provider.class);
	}

	@Override
	public Integer getTotalProvidersThisYear(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisYear(includeRetired, Provider.class);
	}

	private Integer fetchTotalOpenMRSObjectCountToday(Boolean includeRetired, Class clazz) {
		return getSessionFactory().getCurrentSession().createCriteria(clazz)
				.add(Restrictions.eq("voided", includeRetired)).add(Restrictions
						.or(Restrictions.ge("dateChanged", getToday()), Restrictions.ge("dateCreated", getToday())))
				.list().size();
	}

	private Integer fetchTotalOpenMRSObjectCountThisWeek(Boolean includeRetired, Class clazz) {
		return getSessionFactory().getCurrentSession().createCriteria(clazz)
				.add(Restrictions.eq("voided", includeRetired))
				.add(Restrictions.or(Restrictions.ge("dateChanged", getThisWeekStartDate()),
						Restrictions.ge("dateCreated", getThisWeekStartDate())))
				.add(Restrictions.or(Restrictions.le("dateChanged", getThisWeekEndDate()),
						Restrictions.le("dateCreated", getThisWeekEndDate())))
				.list().size();
	}

	private Integer fetchTotalOpenMRSObjectCountThisMonth(Boolean includeRetired, Class clazz) {
		return getSessionFactory().getCurrentSession().createCriteria(clazz)
				.add(Restrictions.eq("voided", includeRetired))
				.add(Restrictions.or(Restrictions.ge("dateChanged", getThisMonthStartDate()),
						Restrictions.ge("dateCreated", getThisMonthStartDate())))
				.add(Restrictions.or(Restrictions.le("dateChanged", getThisMonthEndDate()),
						Restrictions.le("dateCreated", getThisMonthEndDate())))
				.list().size();
	}

	private Integer fetchTotalOpenMRSObjectCountThisYear(Boolean includeRetired, Class clazz) {
		return getSessionFactory().getCurrentSession().createCriteria(clazz)
				.add(Restrictions.eq("voided", includeRetired))
				.add(Restrictions.or(Restrictions.ge("dateChanged", getThisYearStartDate()),
						Restrictions.ge("dateCreated", getThisYearStartDate())))
				.add(Restrictions.or(Restrictions.le("dateChanged", getThisYearEndDate()),
						Restrictions.le("dateCreated", getThisYearEndDate())))
				.list().size();
	}

	private Integer fetchTotalOpenMRSObjectCountLastWeek(Boolean includeRetired, Class clazz) {
		return getSessionFactory().getCurrentSession().createCriteria(clazz)
				.add(Restrictions.eq("voided", includeRetired))
				.add(Restrictions.or(Restrictions.ge("dateChanged", getLastWeekStartDate()),
						Restrictions.ge("dateCreated", getLastWeekStartDate())))
				.add(Restrictions.or(Restrictions.le("dateChanged", getLastWeekEndDate()),
						Restrictions.le("dateCreated", getLastWeekEndDate())))
				.list().size();
	}

	private Integer fetchTotalOpenMRSObjectCountLastMonth(Boolean includeRetired, Class clazz) {
		return getSessionFactory().getCurrentSession().createCriteria(clazz)
				.add(Restrictions.eq("voided", includeRetired))
				.add(Restrictions.or(Restrictions.ge("dateChanged", getLastMonthStartDate()),
						Restrictions.ge("dateCreated", getLastMonthStartDate())))
				.add(Restrictions.or(Restrictions.le("dateChanged", getLastMonthEndDate()),
						Restrictions.le("dateCreated", getLastMonthEndDate())))
				.list().size();
	}

	private Integer fetchTotalOpenMRSObjectCountLastYear(Boolean includeRetired, Class clazz) {
		return getSessionFactory().getCurrentSession().createCriteria(clazz)
				.add(Restrictions.eq("voided", includeRetired))
				.add(Restrictions.or(Restrictions.ge("dateChanged", getLastYearStartDate()),
						Restrictions.ge("dateCreated", getLastYearStartDate())))
				.add(Restrictions.or(Restrictions.le("dateChanged", getLastYearEndDate()),
						Restrictions.le("dateCreated", getLastYearEndDate())))
				.list().size();
	}
}