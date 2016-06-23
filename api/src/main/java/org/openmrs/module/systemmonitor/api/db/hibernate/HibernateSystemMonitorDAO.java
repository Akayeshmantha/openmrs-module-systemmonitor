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

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.impl.SessionImpl;
import org.hibernate.loader.OuterJoinLoader;
import org.hibernate.loader.criteria.CriteriaLoader;
import org.hibernate.persister.entity.OuterJoinLoadable;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Form;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.PatientProgram;
import org.openmrs.Person;
import org.openmrs.Provider;
import org.openmrs.User;
import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.module.systemmonitor.ConfigurableGlobalProperties;
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

	private Concept getViralLoadsConcept() {
		String viralLoadConcept = Context.getAdministrationService()
				.getGlobalProperty(ConfigurableGlobalProperties.VIRALLOAD_CONCEPTID);
		Integer viralLoadConceptId = viralLoadConcept != null ? Integer.parseInt(viralLoadConcept) : null;

		return viralLoadConceptId != null ? Context.getConceptService().getConcept(viralLoadConceptId) : null;
	}

	private Concept getReasonForExitingCareConcept() {
		String exitCareConcept = Context.getAdministrationService()
				.getGlobalProperty(ConfigurableGlobalProperties.CAREEXITREASON_CONCEPTID);
		Integer exitCareConceptId = exitCareConcept != null ? Integer.parseInt(exitCareConcept) : null;

		return exitCareConceptId != null ? Context.getConceptService().getConcept(exitCareConceptId) : null;
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
	public Integer getTotalEncounters(Boolean includeRetired) {
		return fetchTotalOpenMRSObject(includeRetired, Encounter.class);
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
	public Integer getTotalUsers(Boolean includeRetired) {
		return fetchTotalOpenMRSObject(includeRetired, User.class);
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
	public Integer getTotalObservations(Boolean includeRetired) {
		return fetchTotalOpenMRSObject(includeRetired, Obs.class);
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
	public Integer getTotalVisits(Boolean includeRetired) {
		return fetchTotalOpenMRSObject(includeRetired, Visit.class);
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
	public Integer getTotalPatients(Boolean includeRetired) {
		return fetchTotalOpenMRSObject(includeRetired, Patient.class);
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
	public Integer getTotalLocations(Boolean includeRetired) {
		return fetchTotalOpenMRSObject(includeRetired, Location.class);
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
	public Integer getTotalConcepts(Boolean includeRetired) {
		return fetchTotalOpenMRSObject(includeRetired, Concept.class);
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
	public Integer getTotalForms(Boolean includeRetired) {
		return fetchTotalOpenMRSObject(includeRetired, Form.class);
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
	public Integer getTotalOrders(Boolean includeRetired) {
		return fetchTotalOpenMRSObject(includeRetired, Order.class);
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
	public Integer getTotalProviders(Boolean includeRetired) {
		return fetchTotalOpenMRSObject(includeRetired, Provider.class);
	}

	@Override
	public Integer getTotalProvidersThisMonth(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisMonth(includeRetired, Provider.class);
	}

	@Override
	public Integer getTotalProvidersThisYear(Boolean includeRetired) {
		return fetchTotalOpenMRSObjectCountThisYear(includeRetired, Provider.class);
	}

	private String getObjectTableNameFromClass(Class clazz) {
		String tableName = "";

		if (clazz.equals(Provider.class)) {
			tableName = "provider";
		} else if (clazz.equals(User.class)) {
			tableName = "users";
		} else if (clazz.equals(Location.class)) {
			tableName = "location";
		} else if (clazz.equals(Concept.class)) {
			tableName = "concept";
		} else if (clazz.equals(Form.class)) {
			tableName = "form";
		} else if (clazz.equals(Patient.class)) {
			tableName = "patient";
		} else if (clazz.equals(Order.class)) {
			tableName = "orders";
		} else if (clazz.equals(Visit.class)) {
			tableName = "visit";
		} else if (clazz.equals(Obs.class)) {
			tableName = "obs";
		} else if (clazz.equals(Encounter.class)) {
			tableName = "encounter";
		}
		return tableName;
	}

	private Integer fetchTotalOpenMRSObjectCountToday(Boolean includeRetired,
			@SuppressWarnings("rawtypes") Class clazz) {
		String voidedOrRetiredParameterName = isPropertyCalledRetiredOrVoided(clazz);
		String sql = "";

		return getSessionFactory().getCurrentSession().createCriteria(clazz)
				.add(Restrictions.eq(voidedOrRetiredParameterName, includeRetired)).add(Restrictions
						.or(Restrictions.ge("dateChanged", getToday()), Restrictions.ge("dateCreated", getToday())))
				.list().size();
	}

	private Integer fetchTotalOpenMRSObjectCountThisWeek(Boolean includeRetired,
			@SuppressWarnings("rawtypes") Class clazz) {
		String voidedOrRetiredParameterName = isPropertyCalledRetiredOrVoided(clazz);

		return getSessionFactory().getCurrentSession().createCriteria(clazz)
				.add(Restrictions.eq(voidedOrRetiredParameterName, includeRetired))
				.add(Restrictions.or(Restrictions.between("dateChanged", getThisWeekStartDate(), getThisWeekEndDate()),
						Restrictions.between("dateCreated", getThisWeekStartDate(), getThisWeekEndDate())))
				.list().size();
	}

	private Integer fetchTotalOpenMRSObjectCountThisMonth(Boolean includeRetired,
			@SuppressWarnings("rawtypes") Class clazz) {
		String voidedOrRetiredParameterName = isPropertyCalledRetiredOrVoided(clazz);

		return getSessionFactory().getCurrentSession().createCriteria(clazz)
				.add(Restrictions.eq(voidedOrRetiredParameterName, includeRetired))
				.add(Restrictions.or(
						Restrictions.between("dateChanged", getThisMonthStartDate(), getThisMonthEndDate()),
						Restrictions.between("dateCreated", getThisMonthStartDate(), getThisMonthEndDate())))
				.list().size();
	}

	private String isPropertyCalledRetiredOrVoided(@SuppressWarnings("rawtypes") Class clazz) {
		String voidedOrRetiredParameterName = "voided";

		// metadata voided is named retired instead
		if (clazz.equals(Provider.class) || clazz.equals(User.class) || clazz.equals(Location.class)
				|| clazz.equals(Form.class) || clazz.equals(Concept.class)) {
			voidedOrRetiredParameterName = "retired";
		}
		return voidedOrRetiredParameterName;
	}

	private Integer fetchTotalOpenMRSObjectCountThisYear(Boolean includeRetired,
			@SuppressWarnings("rawtypes") Class clazz) {
		String voidedOrRetiredParameterName = isPropertyCalledRetiredOrVoided(clazz);

		return getSessionFactory().getCurrentSession().createCriteria(clazz)
				.add(Restrictions.eq(voidedOrRetiredParameterName, includeRetired))
				.add(Restrictions.or(Restrictions.between("dateChanged", getThisYearStartDate(), getThisYearEndDate()),
						Restrictions.between("dateCreated", getThisYearStartDate(), getThisYearEndDate())))
				.list().size();
	}

	private Integer fetchTotalOpenMRSObjectCountLastWeek(Boolean includeRetired,
			@SuppressWarnings("rawtypes") Class clazz) {
		String voidedOrRetiredParameterName = isPropertyCalledRetiredOrVoided(clazz);

		return getSessionFactory().getCurrentSession().createCriteria(clazz)
				.add(Restrictions.eq(voidedOrRetiredParameterName, includeRetired))
				.add(Restrictions.or(Restrictions.between("dateChanged", getLastWeekStartDate(), getLastWeekEndDate()),
						Restrictions.between("dateCreated", getLastWeekStartDate(), getLastWeekEndDate())))
				.list().size();
	}

	private Integer fetchTotalOpenMRSObjectCountLastMonth(Boolean includeRetired,
			@SuppressWarnings("rawtypes") Class clazz) {
		String voidedOrRetiredParameterName = isPropertyCalledRetiredOrVoided(clazz);

		return getSessionFactory().getCurrentSession().createCriteria(clazz)
				.add(Restrictions.eq(voidedOrRetiredParameterName, includeRetired))
				.add(Restrictions.or(
						Restrictions.between("dateChanged", getLastMonthStartDate(), getLastMonthEndDate()),
						Restrictions.between("dateCreated", getLastMonthStartDate(), getLastMonthEndDate())))
				.list().size();
	}

	private Integer fetchTotalOpenMRSObject(Boolean includeRetired, @SuppressWarnings("rawtypes") Class clazz) {
		String voidedOrRetiredParameterName = isPropertyCalledRetiredOrVoided(clazz);

		return getSessionFactory().getCurrentSession().createCriteria(clazz)
				.add(Restrictions.eq(voidedOrRetiredParameterName, includeRetired)).list().size();
	}

	private Integer fetchTotalOpenMRSObjectCountLastYear(Boolean includeRetired,
			@SuppressWarnings("rawtypes") Class clazz) {
		String voidedOrRetiredParameterName = isPropertyCalledRetiredOrVoided(clazz);

		return getSessionFactory().getCurrentSession().createCriteria(clazz)
				.add(Restrictions.eq(voidedOrRetiredParameterName, includeRetired))
				.add(Restrictions.or(Restrictions.between("dateChanged", getLastYearStartDate(), getLastYearEndDate()),
						Restrictions.between("dateCreated", getLastYearStartDate(), getLastYearEndDate())))
				.list().size();
	}

	@Override
	public Date getOneHalfYearBackDate() {
		Calendar prevHalfYear = Calendar.getInstance();
		prevHalfYear.add(Calendar.MONTH, -6);

		return prevHalfYear.getTime();
	}

	@Override
	public Date getOneYearBackDate() {
		Calendar prevYear = Calendar.getInstance();
		prevYear.add(Calendar.YEAR, -1);

		return prevYear.getTime();
	}

	@Override
	public Integer rwandaPIHEMTGetTotalEncounters() {
		return getTotalEncounters(false);
	}

	@Override
	public Integer rwandaPIHEMTGetTotalObservations() {
		return getTotalObservations(false);
	}

	@Override
	public Integer rwandaPIHEMTGetTotalUsers() {
		return getTotalUsers(false);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Person[] getAllPersonsWhoArePatients() {
		List<Patient> allPatients = Context.getPatientService().getAllPatients();
		List<Person> allPersons = getSessionFactory().getCurrentSession().createCriteria(Person.class).list();
		List<Person> persons = new ArrayList<Person>();

		for (int i = 0; i < allPersons.size(); i++) {
			Person person = allPersons.get(i);

			for (int j = 0; j < allPatients.size(); j++) {
				Patient patient = allPatients.get(j);

				if (person != null && patient != null && person.getPersonId().equals(patient.getPatientId())) {
					persons.add(patient);
				}
			}
		}

		return persons.toArray(new Person[persons.size()]);
	}

	@SuppressWarnings("unchecked")
	private Patient[] getAllPatientEnrolledIntoPrograms() {
		List<Patient> patientsInPrograms = new ArrayList<Patient>();
		List<PatientProgram> patientPrograms = getSessionFactory().getCurrentSession()
				.createCriteria(PatientProgram.class).list();

		for (int i = 0; i < patientPrograms.size(); i++) {
			patientsInPrograms.add(patientPrograms.get(i).getPatient());
		}
		return patientsInPrograms.toArray(new Patient[patientsInPrograms.size()]);
	}

	@SuppressWarnings("unchecked")
	private Person[] getAllPersonsEnrolledIntoPrograms() {
		List<Person> personsInPrograms = new ArrayList<Person>();
		List<PatientProgram> patientPrograms = getSessionFactory().getCurrentSession()
				.createCriteria(PatientProgram.class).list();

		for (int i = 0; i < patientPrograms.size(); i++) {
			personsInPrograms
					.add(Context.getPersonService().getPerson(patientPrograms.get(i).getPatient().getPersonId()));
		}
		return personsInPrograms.toArray(new Patient[personsInPrograms.size()]);
	}

	private Person[] getAllPersonsWithOrders() {
		List<Person> allPersonsWithOrders = new ArrayList<Person>();
		List<Order> allOrders = getSessionFactory().getCurrentSession().createCriteria(Order.class).list();
		List<Provider> allProviders = Context.getProviderService().getAllProviders();
		List<User> allUsers = getSessionFactory().getCurrentSession().createCriteria(User.class).list();

		for (int i = 0; i < allOrders.size(); i++) {
			Order o = allOrders.get(i);

			if (o != null && o.getOrderer() instanceof User) {
				for (int j = 0; j < allUsers.size(); j++) {
					User u = allUsers.get(j);

					if (u != null && o.getOrderId().equals(u.getUserId())) {
						Person person = Context.getPersonService().getPerson(u.getUserId());

						if (person != null)
							allPersonsWithOrders.add(person);
					}
				}
			} else if (o != null && o.getOrderer().getClass().equals(Provider.class)) {
				for (int j = 0; j < allProviders.size(); j++) {
					Provider p = allProviders.get(j);

					if (p != null && o.getOrderId().equals(p.getProviderId())) {
						Person person = Context.getPersonService().getPerson(p.getProviderId());

						if (person != null)
							allPersonsWithOrders.add(person);
					}
				}
			}
		}

		return allPersonsWithOrders.toArray(new Person[allPersonsWithOrders.size()]);
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private Object[] getAllOrderers() {
		List<Provider> allProviders = Context.getProviderService().getAllProviders();
		List<Order> allOrders = getSessionFactory().getCurrentSession().createCriteria(Order.class).list();
		List<User> allUsers = getSessionFactory().getCurrentSession().createCriteria(User.class).list();
		List<Object> orderers = new ArrayList<Object>();

		for (int i = 0; i < allOrders.size(); i++) {
			Order o = allOrders.get(i);

			if (o != null && o.getOrderer() instanceof User) {
				for (int j = 0; j < allUsers.size(); j++) {
					User u = allUsers.get(j);

					if (u != null && o.getOrderId().equals(u.getUserId())) {
						orderers.add(u);
					}
				}
			} else if (o != null && o.getOrderer().getClass().equals(Provider.class)) {
				for (int j = 0; j < allProviders.size(); j++) {
					Provider p = allProviders.get(j);

					if (p != null && o.getOrderId().equals(p.getProviderId())) {
						orderers.add(p);
					}
				}
			}
		}
		return orderers.toArray(new Object[orderers.size()]);
	}

	@Override
	public Integer getTotalViralLoadTestsEver() {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Obs.class)
				.add(Restrictions.eq("voided", false)).add(Restrictions.eq("concept", getViralLoadsConcept()))
				.add(Restrictions.in("person", getAllPersonsWhoArePatients()));

		System.out.println("SQL:::::::::::::: " + toSql(criteria) + " ::::::::::::::LQS");

		return criteria.list().size();
	}

	@Override
	public Integer unitTestingTheseMetrics() {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Obs.class)
				.add(Restrictions.eq("voided", false))
				.add(Restrictions.eq("concept", Context.getConceptService().getConcept(5089)))
				.add(Restrictions.in("person", getAllPersonsWhoArePatients()));

		System.out.println("Total Encounters, Visits, Patients, Obs, Forms, Orders, Providers, Concepts: "
				+ rwandaPIHEMTGetTotalEncounters() + ", " + getTotalObservations(false) + ", " + getTotalVisits(false)
				+ ", " + getTotalVisits(false) + ", " + getTotalForms(false) + ", " + getTotalOrders(false) + ", "
				+ getTotalProviders(false) + ", " + getTotalConcepts(false));
		System.out.println("Matched persons length: " + getAllPersonsWhoArePatients().length);

		System.out.println("rwandaPIHEMTGetTotalActivePatients: " + rwandaPIHEMTGetTotalActivePatients());

		System.out.println("SQL:::::::::::::: " + toSql(criteria) + " ::::::::::::::LQS");

		System.out.println("rwandaPIHEMTGetTotalActivePatients: " + rwandaPIHEMTGetTotalActivePatients());
		System.out.println("getAllPersonsEnrolledIntoPrograms: " + getAllPersonsEnrolledIntoPrograms().length);
		System.out.println("getAllPersonsWithOrders: " + getAllPersonsWithOrders().length);
		System.out.println("mergeGetAllPersonsEnrolledIntoProgramsAndGetAllPersonsWithOrders: "
				+ mergeGetAllPersonsEnrolledIntoProgramsAndGetAllPersonsWithOrders().length);

		for (Object o : criteria.list()) {
			Obs obs = (Obs) o;
			System.out.println("Concept: " + obs.getConcept().getConceptId());
			System.out.println("Person: " + obs.getPerson().getPersonId());
		}

		return criteria.list().size();
	}

	private String toSql(Criteria criteria) {
		try {
			CriteriaImpl c = (CriteriaImpl) criteria;
			SessionImpl s = (SessionImpl) c.getSession();
			SessionFactoryImplementor factory = (SessionFactoryImplementor) s.getSessionFactory();
			String[] implementors = factory.getImplementors(c.getEntityOrClassName());
			CriteriaLoader loader = new CriteriaLoader((OuterJoinLoadable) factory.getEntityPersister(implementors[0]),
					factory, c, implementors[0], s.getLoadQueryInfluencers());
			Field f = OuterJoinLoader.class.getDeclaredField("sql");
			f.setAccessible(true);
			return (String) f.get(loader);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Integer getTotalViralLoadTestsLastSixMonths() {
		return getSessionFactory().getCurrentSession().createCriteria(Obs.class).add(Restrictions.eq("voided", false))
				.add(Restrictions.eq("concept", getViralLoadsConcept()))
				.add(Restrictions.in("person", getAllPersonsWhoArePatients()))
				.add(Restrictions.ge("obsDatetime", getOneHalfYearBackDate())).list().size();
	}

	@Override
	public Integer getTotalViralLoadTestsLastYear() {
		return getSessionFactory().getCurrentSession().createCriteria(Obs.class).add(Restrictions.eq("voided", false))
				.add(Restrictions.eq("concept", getViralLoadsConcept()))
				.add(Restrictions.in("person", getAllPersonsWhoArePatients()))
				.add(Restrictions.ge("obsDatetime", getOneYearBackDate())).list().size();
	}

	@Override
	public Integer rwandaPIHEMTGetTotalVisits() {
		return getSessionFactory().getCurrentSession()
				.createCriteria(
						Encounter.class)
				.add(Restrictions.in("encounterType",
						new EncounterType[] { Context.getEncounterService().getEncounterType(2),
								Context.getEncounterService().getEncounterType(4) }))
				.list().size();

	}

	@Override
	public Integer rwandaPIHEMTGetTotalActivePatients() {
		// TODO person merge should be intersect instead
		/*
		 * return
		 * getSessionFactory().getCurrentSession().createCriteria(Obs.class)
		 * .add(Restrictions.eq("concept", getReasonForExitingCareConcept()))
		 * .add(Restrictions.in("person",
		 * mergeGetAllPersonsEnrolledIntoProgramsAndGetAllPersonsWithOrders()))
		 * .list().size()
		 */return null;
	}

	private Person[] mergeGetAllPersonsEnrolledIntoProgramsAndGetAllPersonsWithOrders() {
		List<Person> persons = new ArrayList<Person>();

		for (int i = 0; i < getAllPersonsEnrolledIntoPrograms().length; i++) {
			Person p1 = getAllPersonsEnrolledIntoPrograms()[i];

			if (p1 != null)
				persons.add(p1);
		}
		for (int j = 0; j < getAllPersonsWithOrders().length; j++) {
			Person p2 = getAllPersonsWithOrders()[j];

			if (p2 != null && !personListContains(persons, p2)) {
				persons.add(p2);
			}
		}

		return persons.toArray(new Person[persons.size()]);
	}

	private boolean personListContains(List<Person> list, Person person) {
		boolean contains = false;

		for (Person p : list) {
			if (p.getPersonId().equals(person.getPersonId())) {
				contains = true;
				break;
			}
		}

		return contains;
	}

	@Override
	public Integer rwandaPIHEMTGetTotalNewPatients() {
		/*
		 * String sql =
		 * "select count(*) from encounter where encounter_type in (1,3)";
		 * 
		 * return
		 * getSessionFactory().getCurrentSession().createQuery(sql).list().size(
		 * );
		 */return null;
	}
}