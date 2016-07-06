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
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.openmrs.Form;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.PatientProgram;
import org.openmrs.Person;
import org.openmrs.Program;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.systemmonitor.ConfigurableGlobalProperties;
import org.openmrs.module.systemmonitor.api.db.SystemMonitorDAO;

/**
 * It is a default implementation of {@link SystemMonitorDAO}.
 */
public class HibernateSystemMonitorDAO implements SystemMonitorDAO {
	protected final Log log = LogFactory.getLog(HibernateSystemMonitorDAO.class);

	private SessionFactory sessionFactory;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

	private Concept getConceptByGpCode(String globalPropertyCode) {
		String concept = Context.getAdministrationService().getGlobalProperty(globalPropertyCode);
		Integer conceptId = concept != null ? Integer.parseInt(concept) : null;

		return conceptId != null ? Context.getConceptService().getConcept(conceptId) : null;
	}

	@Override
	public Program getHIVProgram() {
		String program = Context.getAdministrationService()
				.getGlobalProperty(ConfigurableGlobalProperties.HIV_PROGRAMID);
		Integer programId = program != null ? Integer.parseInt(program) : null;

		return programId != null ? Context.getProgramWorkflowService().getProgram(programId) : null;
	}

	@Override
	public Concept getViralLoadsConcept() {
		return getConceptByGpCode(ConfigurableGlobalProperties.VIRALLOAD_CONCEPTID);
	}

	@Override
	public Concept getCD4CountConcept() {
		return getConceptByGpCode(ConfigurableGlobalProperties.CD4COUNT_CONCEPTID);
	}

	@Override
	public Concept getReasonForExitingCareConcept() {
		return getConceptByGpCode(ConfigurableGlobalProperties.CAREEXITREASON_CONCEPTID);
	}

	@Override
	public Concept getARVDrugsConceptSet() {
		return getConceptByGpCode(ConfigurableGlobalProperties.ARVDRUGS_CONCEPTSETID);
	}

	@Override
	public String getToday() {
		Calendar calendar = Calendar.getInstance(Context.getLocale());

		resetDateTimes(calendar);

		return sdf.format(calendar.getTime());
	}

	@Override
	public String getThisWeekStartDate() {
		Calendar calendar = Calendar.getInstance(Context.getLocale());

		resetDateTimes(calendar);
		// get start of this week in milliseconds
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
		return sdf.format(calendar.getTime());
	}

	private void resetDateTimes(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the
												// hour of day !
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.MILLISECOND);
	}

	@Override
	public String getThisWeekEndDate() {
		Calendar calendar = Calendar.getInstance(Context.getLocale());
		resetDateTimes(calendar);
		Calendar first = (Calendar) calendar.clone();
		first.add(Calendar.DAY_OF_WEEK, first.getFirstDayOfWeek() - first.get(Calendar.DAY_OF_WEEK));

		Calendar last = (Calendar) first.clone();
		last.add(Calendar.DAY_OF_YEAR, 6);

		return sdf.format(last.getTime());
	}

	@Override
	public String getLastWeekStartDate() {
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
		return sdf.format(calendar.getTime());
	}

	@Override
	public String getLastWeekEndDate() {
		Calendar calendar = Calendar.getInstance(Context.getLocale());
		resetDateTimes(calendar);
		Calendar first = (Calendar) calendar.clone();
		first.add(Calendar.DAY_OF_WEEK, first.getFirstDayOfWeek() - first.get(Calendar.DAY_OF_WEEK));
		first.set(Calendar.WEEK_OF_YEAR, calendar.get(Calendar.WEEK_OF_YEAR) - 1);

		Calendar last = (Calendar) first.clone();
		last.add(Calendar.DAY_OF_YEAR, 6);

		return sdf.format(last.getTime());
	}

	@Override
	public String getThisMonthStartDate() {
		Calendar calendar = Calendar.getInstance(Context.getLocale());
		resetDateTimes(calendar);
		calendar.set(Calendar.DAY_OF_MONTH, 1);

		return sdf.format(calendar.getTime());
	}

	@Override
	public String getThisMonthEndDate() {
		Calendar calendar = Calendar.getInstance(Context.getLocale());
		resetDateTimes(calendar);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

		return sdf.format(calendar.getTime());
	}

	@Override
	public String getLastMonthStartDate() {
		Calendar calendar = Calendar.getInstance(Context.getLocale());
		resetDateTimes(calendar);
		calendar.add(Calendar.MONTH, -1);
		// set DATE to 1, so first date of previous month
		calendar.set(Calendar.DATE, 1);
		return sdf.format(calendar.getTime());
	}

	@Override
	public String getLastMonthEndDate() {
		Calendar calendar = Calendar.getInstance(Context.getLocale());
		resetDateTimes(calendar);
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

		return sdf.format(calendar.getTime());
	}

	@Override
	public String getThisYearStartDate() {
		Calendar calendar = Calendar.getInstance(Context.getLocale());
		resetDateTimes(calendar);
		calendar.set(Calendar.DAY_OF_YEAR, 1);

		return sdf.format(calendar.getTime());
	}

	@Override
	public String getThisYearEndDate() {
		Calendar calendar = Calendar.getInstance(Context.getLocale());
		resetDateTimes(calendar);
		calendar.set(Calendar.DAY_OF_YEAR, isLeapYear(calendar.get(Calendar.YEAR)) ? 366 : 365);

		return sdf.format(calendar.getTime());
	}

	@Override
	public String getLastYearStartDate() {
		Calendar calendar = Calendar.getInstance(Context.getLocale());
		resetDateTimes(calendar);
		calendar.add(Calendar.YEAR, -1);
		calendar.set(Calendar.DAY_OF_YEAR, 1);

		return sdf.format(calendar.getTime());
	}

	@Override
	public String getLastYearEndDate() {
		Calendar calendar = Calendar.getInstance(Context.getLocale());
		resetDateTimes(calendar);
		calendar.add(Calendar.YEAR, -1);
		calendar.set(Calendar.DAY_OF_YEAR, isLeapYear(calendar.get(Calendar.YEAR)) ? 366 : 365);

		return sdf.format(calendar.getTime());
	}

	@Override
	public String getOneHalfYearBackDate() {
		Calendar prevHalfYear = Calendar.getInstance();
		prevHalfYear.add(Calendar.MONTH, -6);

		return sdf.format(prevHalfYear.getTime());
	}

	@Override
	public String getOneYearBackDate() {
		Calendar prevYear = Calendar.getInstance();
		prevYear.add(Calendar.YEAR, -1);

		return sdf.format(prevYear.getTime());
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
		return null;//fetchTotalOpenMRSObjectCountToday(includeRetired, Visit.class);
	}

	@Override
	public Integer getTotalVisitsThisWeek(Boolean includeRetired) {
		return null;//fetchTotalOpenMRSObjectCountThisWeek(includeRetired, Visit.class);
	}

	@Override
	public Integer getTotalVisitsLastWeek(Boolean includeRetired) {
		return null;//fetchTotalOpenMRSObjectCountLastWeek(includeRetired, Visit.class);
	}

	@Override
	public Integer getTotalVisitsThisMonth(Boolean includeRetired) {
		return null;//fetchTotalOpenMRSObjectCountThisMonth(includeRetired, Visit.class);
	}

	@Override
	public Integer getTotalVisitsLastMonth(Boolean includeRetired) {
		return null;//fetchTotalOpenMRSObjectCountLastMonth(includeRetired, Visit.class);
	}

	@Override
	public Integer getTotalVisitsThisYear(Boolean includeRetired) {
		return null;//fetchTotalOpenMRSObjectCountThisYear(includeRetired, Visit.class);
	}

	@Override
	public Integer getTotalVisitsLastYear(Boolean includeRetired) {
		return null;//fetchTotalOpenMRSObjectCountLastYear(includeRetired, Visit.class);
	}

	@Override
	public Integer getTotalVisits(Boolean includeRetired) {
		return null;//fetchTotalOpenMRSObject(includeRetired, Visit.class);
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
		return null;//fetchTotalOpenMRSObjectCountLastYear(includeRetired, Provider.class);
	}

	@Override
	public Integer getTotalProvidersToday(Boolean includeRetired) {
		return null;//fetchTotalOpenMRSObjectCountToday(includeRetired, Provider.class);
	}

	@Override
	public Integer getTotalProvidersThisWeek(Boolean includeRetired) {
		return null;//fetchTotalOpenMRSObjectCountThisWeek(includeRetired, Provider.class);
	}

	@Override
	public Integer getTotalProvidersLastWeek(Boolean includeRetired) {
		return null;//fetchTotalOpenMRSObjectCountLastWeek(includeRetired, Provider.class);
	}

	@Override
	public Integer getTotalProvidersLastMonth(Boolean includeRetired) {
		return null;//fetchTotalOpenMRSObjectCountLastMonth(includeRetired, Provider.class);
	}

	@Override
	public Integer getTotalProviders(Boolean includeRetired) {
		return null;//fetchTotalOpenMRSObject(includeRetired, Provider.class);
	}

	@Override
	public Integer getTotalProvidersThisMonth(Boolean includeRetired) {
		return null;//fetchTotalOpenMRSObjectCountThisMonth(includeRetired, Provider.class);
	}

	@Override
	public Integer getTotalProvidersThisYear(Boolean includeRetired) {
		return null;//fetchTotalOpenMRSObjectCountThisYear(includeRetired, Provider.class);
	}

	private String getObjectTableNameFromClass(@SuppressWarnings("rawtypes") Class clazz) {
		String tableName = "";

		/*if (clazz.equals(Provider.class)) {
			tableName = "provider";
		} else*/ if (clazz.equals(User.class)) {
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
		} /*else if (clazz.equals(Visit.class)) {
			tableName = "visit";
		}*/ else if (clazz.equals(Obs.class)) {
			tableName = "obs";
		} else if (clazz.equals(Encounter.class)) {
			tableName = "encounter";
		}
		return tableName;
	}

	private Integer fetchTotalOpenMRSObjectCountToday(Boolean includeRetired,
			@SuppressWarnings("rawtypes") Class clazz) {
		String voidedOrRetiredParameterName = isPropertyCalledRetiredOrVoided(clazz);
		String sql = "select count(*) from " + getObjectTableNameFromClass(clazz) + " where "
				+ voidedOrRetiredParameterName + "=" + includeRetired + " and (date_changed >= '" + getToday()
				+ "' or date_created >= '" + getToday() + "')";
		Integer count = ((BigInteger) getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult())
				.intValue();

		return count;
	}

	private Integer fetchTotalOpenMRSObjectCountThisWeek(Boolean includeRetired,
			@SuppressWarnings("rawtypes") Class clazz) {
		String voidedOrRetiredParameterName = isPropertyCalledRetiredOrVoided(clazz);
		String sql = "select count(*) from " + getObjectTableNameFromClass(clazz) + " where "
				+ voidedOrRetiredParameterName + "=" + includeRetired + " and ((date_changed between '"
				+ getThisWeekStartDate() + "' and '" + getThisWeekEndDate() + "') or (date_created between '"
				+ getThisWeekStartDate() + "' and '" + getThisWeekEndDate() + "'))";
		Integer count = ((BigInteger) getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult())
				.intValue();

		return count;
	}

	private Integer fetchTotalOpenMRSObjectCountThisMonth(Boolean includeRetired,
			@SuppressWarnings("rawtypes") Class clazz) {
		String voidedOrRetiredParameterName = isPropertyCalledRetiredOrVoided(clazz);
		String sql = "select count(*) from " + getObjectTableNameFromClass(clazz) + " where "
				+ voidedOrRetiredParameterName + "=" + includeRetired + " and ((date_changed between '"
				+ getThisMonthStartDate() + "' and '" + getThisMonthEndDate() + "') or (date_created between '"
				+ getThisMonthStartDate() + "' and '" + getThisMonthEndDate() + "'))";
		Integer count = ((BigInteger) getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult())
				.intValue();

		return count;
	}

	private String isPropertyCalledRetiredOrVoided(@SuppressWarnings("rawtypes") Class clazz) {
		String voidedOrRetiredParameterName = "voided";

		// metadata voided is named retired instead
		if (/*clazz.equals(Provider.class) || */clazz.equals(User.class) || clazz.equals(Location.class)
				|| clazz.equals(Form.class) || clazz.equals(Concept.class)) {
			voidedOrRetiredParameterName = "retired";
		}
		return voidedOrRetiredParameterName;
	}

	private Integer fetchTotalOpenMRSObjectCountThisYear(Boolean includeRetired,
			@SuppressWarnings("rawtypes") Class clazz) {
		String voidedOrRetiredParameterName = isPropertyCalledRetiredOrVoided(clazz);
		String sql = "select count(*) from " + getObjectTableNameFromClass(clazz) + " where "
				+ voidedOrRetiredParameterName + "=" + includeRetired + " and ((date_changed between '"
				+ getThisYearStartDate() + "' and '" + getThisYearEndDate() + "') or (date_created between '"
				+ getThisYearStartDate() + "' and '" + getThisYearEndDate() + "'))";
		Integer count = ((BigInteger) getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult())
				.intValue();

		return count;
	}

	private Integer fetchTotalOpenMRSObjectCountLastWeek(Boolean includeRetired,
			@SuppressWarnings("rawtypes") Class clazz) {
		String voidedOrRetiredParameterName = isPropertyCalledRetiredOrVoided(clazz);
		String sql = "select count(*) from " + getObjectTableNameFromClass(clazz) + " where "
				+ voidedOrRetiredParameterName + "=" + includeRetired + " and ((date_changed between '"
				+ getLastWeekStartDate() + "' and '" + getLastWeekEndDate() + "') or (date_created between '"
				+ getLastWeekStartDate() + "' and '" + getLastWeekEndDate() + "'))";
		Integer count = ((BigInteger) getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult())
				.intValue();

		return count;
	}

	private Integer fetchTotalOpenMRSObjectCountLastMonth(Boolean includeRetired,
			@SuppressWarnings("rawtypes") Class clazz) {
		String voidedOrRetiredParameterName = isPropertyCalledRetiredOrVoided(clazz);
		String sql = "select count(*) from " + getObjectTableNameFromClass(clazz) + " where "
				+ voidedOrRetiredParameterName + "=" + includeRetired + " and ((date_changed between '"
				+ getLastMonthStartDate() + "' and '" + getLastMonthEndDate() + "') or (date_created between '"
				+ getLastMonthStartDate() + "' and '" + getLastMonthEndDate() + "'))";
		Integer count = ((BigInteger) getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult())
				.intValue();

		return count;
	}

	private Integer fetchTotalOpenMRSObject(Boolean includeRetired, @SuppressWarnings("rawtypes") Class clazz) {
		String voidedOrRetiredParameterName = isPropertyCalledRetiredOrVoided(clazz);
		String sql = "select count(*) from " + getObjectTableNameFromClass(clazz) + " where "
				+ voidedOrRetiredParameterName + "=" + includeRetired;
		Integer count = ((BigInteger) getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult())
				.intValue();

		return count;
	}

	private Integer fetchTotalOpenMRSObjectCountLastYear(Boolean includeRetired,
			@SuppressWarnings("rawtypes") Class clazz) {
		String voidedOrRetiredParameterName = isPropertyCalledRetiredOrVoided(clazz);
		String sql = "select count(*) from " + getObjectTableNameFromClass(clazz) + " where "
				+ voidedOrRetiredParameterName + "=" + includeRetired + " and ((date_changed between '"
				+ getLastYearStartDate() + "' and '" + getLastYearEndDate() + "') or (date_created between '"
				+ getLastYearStartDate() + "' and '" + getLastYearEndDate() + "'))";
		Integer count = ((BigInteger) getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult())
				.intValue();

		return count;
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

	@SuppressWarnings({ "unchecked", "unused" })
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

	@SuppressWarnings("unchecked")
	private Person[] getAllPersonsWithOrders() {
		List<Person> allPersonsWithOrders = new ArrayList<Person>();
		List<Order> allOrders = getSessionFactory().getCurrentSession().createCriteria(Order.class).list();
		//List<Provider> allProviders = Context.getProviderService().getAllProviders();
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
			} /*else if (o != null && o.getOrderer().getClass().equals(Provider.class)) {
				for (int j = 0; j < allProviders.size(); j++) {
					Provider p = allProviders.get(j);

					if (p != null && o.getOrderId().equals(p.getProviderId())) {
						Person person = Context.getPersonService().getPerson(p.getProviderId());

						if (person != null)
							allPersonsWithOrders.add(person);
					}
				}
			}*/
		}

		return allPersonsWithOrders.toArray(new Person[allPersonsWithOrders.size()]);
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private Object[] getAllOrderers() {
		//List<Provider> allProviders = Context.getProviderService().getAllProviders();
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
			} /*else if (o != null && o.getOrderer().getClass().equals(Provider.class)) {
				for (int j = 0; j < allProviders.size(); j++) {
					Provider p = allProviders.get(j);

					if (p != null && o.getOrderId().equals(p.getProviderId())) {
						orderers.add(p);
					}
				}
			}*/
		}
		return orderers.toArray(new Object[orderers.size()]);
	}

	private Integer getConceptObsEverCount(Concept concept) {
		String sql;
		Integer count = 0;

		if (concept != null) {
			sql = "select count(*) from " + getObjectTableNameFromClass(Obs.class)
					+ " where voided=false and concept_id = " + concept.getConceptId()
					+ " and person_id in(select distinct patient_id from patient)";
			count = ((BigInteger) getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult())
					.intValue();
		}
		return count;
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
			//CriteriaLoader loader = new CriteriaLoader((OuterJoinLoadable) factory.getEntityPersister(implementors[0]),
					//factory, c, implementors[0], s.getLoadQueryInfluencers());
			//Field f = OuterJoinLoader.class.getDeclaredField("sql");
			//f.setAccessible(true);
			return null;//(String) f.get(loader);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Integer getConceptObsLastSixMonthsCount(Concept concept) {
		String sql;
		Integer count = 0;

		if (concept != null) {
			sql = "select count(*) from " + getObjectTableNameFromClass(Obs.class)
					+ " where voided=false and concept_id = " + concept.getConceptId()
					+ " and person_id in(select distinct patient_id from patient) and obs_datetime > DATE_SUB(now(), INTERVAL 6 MONTH)";
			count = ((BigInteger) getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult())
					.intValue();
		}
		return count;
	}

	@Override
	public Integer getTotalViralLoadTestsEver() {
		return getConceptObsEverCount(getViralLoadsConcept());
	}

	@Override
	public Integer getTotalViralLoadTestsLastSixMonths() {
		return getConceptObsLastSixMonthsCount(getViralLoadsConcept());
	}

	@Override
	public Integer getTotalViralLoadTestsLastYear() {
		return getConceptObsLastYearCount(getViralLoadsConcept());
	}

	@Override
	public Integer getTotalCD4CountTestsEver() {
		return getConceptObsEverCount(getCD4CountConcept());
	}

	@Override
	public Integer getTotalCD4CountTestsLastSixMonths() {
		return getConceptObsLastSixMonthsCount(getCD4CountConcept());
	}

	@Override
	public Integer getTotalCD4CountTestsLastYear() {
		return getConceptObsLastYearCount(getCD4CountConcept());
	}

	private Integer getConceptObsLastYearCount(Concept concept) {
		String sql;
		Integer count = 0;

		if (concept != null) {
			sql = "select count(*) from " + getObjectTableNameFromClass(Obs.class)
					+ " where voided=false and concept_id = " + concept.getConceptId()
					+ " and person_id in(select distinct patient_id from patient) and obs_datetime > DATE_SUB(now(), INTERVAL 12 MONTH)";
			count = ((BigInteger) getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult())
					.intValue();
		}
		return count;
	}

	@Override
	public Integer rwandaPIHEMTGetTotalVisits() {
		String sql = "select count(*) from encounter where encounter_type in (" + Context.getAdministrationService()
				.getGlobalProperty(ConfigurableGlobalProperties.METRIC_ENC_TYPEIDS_NUMBEROFVISITS) + ")";
		Integer count = ((BigInteger) getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult())
				.intValue();

		return count;
	}

	@Override
	public Integer rwandaPIHEMTGetTotalActivePatients() {
		String sql = "select count(distinct person_id) from obs o inner join patient_program pp on o.person_id = pp.patient_id inner join orders ord on o.person_id = ord.patient_id where o.concept_id = "
				+ getReasonForExitingCareConcept().getConceptId() + "  and program_id = "
				+ getHIVProgram().getProgramId()
				+ " and ord.concept_id in (select distinct concept_id from concept_set where concept_set = "
				+ getARVDrugsConceptSet().getConceptId() + ")";
		Integer count = ((BigInteger) getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult())
				.intValue();

		return count;
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
		String sql = "select count(*) from encounter where encounter_type in (" + Context.getAdministrationService()
				.getGlobalProperty(ConfigurableGlobalProperties.METRIC_ENC_TYPEIDS_NUMBEROFPATIENTSNEW) + ")";
		Integer count = ((BigInteger) getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult())
				.intValue();

		return count;
	}
}