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
package org.openmrs.module.systemmonitor.api;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Concept;
import org.openmrs.GlobalProperty;
import org.openmrs.Person;
import org.openmrs.Program;
import org.openmrs.api.OpenmrsService;
import org.openmrs.api.db.DAOException;
import org.openmrs.scheduler.TaskDefinition;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;

/**
 * This service exposes module's core functionality. It is a Spring managed bean
 * which is configured in moduleApplicationContext.xml.
 * <p>
 * It can be accessed only via Context:<br>
 * <code>
 * Context.getService(SystemMonitorService.class).someMethod();
 * </code>
 * 
 * @see org.openmrs.api.context.Context
 */
@Transactional
public interface SystemMonitorService extends OpenmrsService {

	String getYesterdayStartDate();

	String getYesterdayEndDate();

	String getThisWeekEndDate();

	String getThisWeekStartDate();

	String getToday();

	String getLastWeekStartDate();

	String getLastYearEndDate();

	String getLastYearStartDate();

	String getThisYearEndDate();

	String getThisYearStartDate();

	String getLastMonthEndDate();

	String getLastMonthStartDate();

	String getThisMonthEndDate();

	String getThisMonthStartDate();

	String getLastWeekEndDate();

	Integer getTotalPatientsToday(Boolean includeRetired);

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

	Integer getTotalUsersThisWeek(Boolean includeRetired);

	Integer getTotalUsersLastWeek(Boolean includeRetired);

	Integer getTotalUsersLastMonth(Boolean includeRetired);

	Integer getTotalUsersThisMonth(Boolean includeRetired);

	Integer getTotalUsersThisYear(Boolean includeRetired);

	Integer getTotalUsersLastYear(Boolean includeRetired);

	Integer getTotalObservationsThisWeek(Boolean includeRetired);

	Integer getTotalObservationsLastWeek(Boolean includeRetired);

	Integer getTotalObservationsLastMonth(Boolean includeRetired);

	Integer getTotalObservationsThisMonth(Boolean includeRetired);

	Integer getTotalObservationsThisYear(Boolean includeRetired);

	Integer getTotalObservationsLastYear(Boolean includeRetired);

	Integer getTotalVisitsThisWeek(Boolean includeRetired);

	Integer getTotalVisitsLastWeek(Boolean includeRetired);

	Integer getTotalVisitsThisMonth(Boolean includeRetired);

	Integer getTotalVisitsLastMonth(Boolean includeRetired);

	Integer getTotalVisitsThisYear(Boolean includeRetired);

	Integer getTotalVisitsLastYear(Boolean includeRetired);

	Integer getTotalPatientsThisWeek(Boolean includeRetired);

	Integer getTotalPatientsLastWeek(Boolean includeRetired);

	Integer getTotalPatientsLastMonth(Boolean includeRetired);

	Integer getTotalPatientsThisMonth(Boolean includeRetired);

	Integer getTotalPatientsThisYear(Boolean includeRetired);

	Integer getTotalPatientsLastYear(Boolean includeRetired);

	Integer getTotalLocationsToday(Boolean includeRetired);

	Integer getTotalLocationsThisWeek(Boolean includeRetired);

	Integer getTotalLocationsLastWeek(Boolean includeRetired);

	Integer getTotalLocationsLastMonth(Boolean includeRetired);

	Integer getTotalLocationsThisMonth(Boolean includeRetired);

	Integer getTotalLocationsThisYear(Boolean includeRetired);

	Integer getTotalLocationsLastYear(Boolean includeRetired);

	Integer getTotalConceptsLastYear(Boolean includeRetired);

	Integer getTotalConceptsToday(Boolean includeRetired);

	Integer getTotalConceptsThisWeek(Boolean includeRetired);

	Integer getTotalConceptsLastWeek(Boolean includeRetired);

	Integer getTotalConceptsLastMonth(Boolean includeRetired);

	Integer getTotalConceptsThisMonth(Boolean includeRetired);

	Integer getTotalConceptsThisYear(Boolean includeRetired);

	Integer getTotalFormsLastYear(Boolean includeRetired);

	Integer getTotalFormsToday(Boolean includeRetired);

	Integer getTotalFormsThisWeek(Boolean includeRetired);

	Integer getTotalFormsLastWeek(Boolean includeRetired);

	Integer getTotalFormsLastMonth(Boolean includeRetired);

	Integer getTotalFormsThisMonth(Boolean includeRetired);

	Integer getTotalFormsThisYear(Boolean includeRetired);

	Integer getTotalOrdersLastYear(Boolean includeRetired);

	Integer getTotalOrdersToday(Boolean includeRetired);

	Integer getTotalOrdersThisWeek(Boolean includeRetired);

	Integer getTotalOrdersLastWeek(Boolean includeRetired);

	Integer getTotalOrdersLastMonth(Boolean includeRetired);

	Integer getTotalOrdersThisMonth(Boolean includeRetired);

	Integer getTotalProvidersLastYear(Boolean includeRetired);

	Integer getTotalProvidersToday(Boolean includeRetired);

	Integer getTotalProvidersThisWeek(Boolean includeRetired);

	Integer getTotalProvidersLastWeek(Boolean includeRetired);

	Integer getTotalProvidersLastMonth(Boolean includeRetired);

	Integer getTotalProvidersThisMonth(Boolean includeRetired);

	Integer getTotalProvidersThisYear(Boolean includeRetired);

	Integer getTotalOrdersThisYear(Boolean includeRetired);

	Integer getTotalEncounters(Boolean includeRetired);

	Integer getTotalUsers(Boolean includeRetired);

	Integer getTotalObservations(Boolean includeRetired);

	Integer getTotalVisits(Boolean includeRetired);

	Integer getTotalPatients(Boolean includeRetired);

	Integer getTotalLocations(Boolean includeRetired);

	Integer getTotalConcepts(Boolean includeRetired);

	Integer getTotalForms(Boolean includeRetired);

	Integer getTotalOrders(Boolean includeRetired);

	Integer getTotalProviders(Boolean includeRetired);

	Integer getTotalViralLoadTestsEver();

	Integer getTotalViralLoadTestsLastSixMonths();

	Integer getTotalViralLoadTestsLastYear();

	Integer rwandaPIHEMTGetTotalVisits();

	Integer rwandaPIHEMTGetTotalActivePatients();

	Integer rwandaPIHEMTGetTotalNewPatients();

	Integer rwandaPIHEMTGetTotalEncounters();

	Integer rwandaPIHEMTGetTotalEncountersForYesterday();

	Integer rwandaPIHEMTGetTotalObservations();

	String getOneYearBackDate();

	Integer rwandaPIHEMTGetTotalUsers();

	Person[] getAllPersonsWhoArePatients();

	String getOneHalfYearBackDate();

	void transferDHISMappingsAndMetadataFileToDataDirectory();

	GlobalProperty getCurrentConfiguredDHISOrgUnit();

	GlobalProperty getCurrentConfiguredDHISUsername();

	GlobalProperty getCurrentConfiguredDHISPassword();

	JSONArray getInstalledModules();

	Integer unitTestingTheseMetrics();

	JSONObject pushMonitoredDataToDHIS();

	JSONObject getDataToPushToDHIS();

	/**
	 * Fetches the whole dhis dataelement/indicator/metric json object to get
	 * its id use; getIndicatorOrMetricOrDataElement("id").getString("id") and
	 * its name use; getIndicatorOrMetricOrDataElement("id").getString("name")
	 * 
	 * @param indicatorUid
	 * @return
	 */
	JSONObject getIndicatorOrMetricOrDataElement(String indicatorUid);

	void updateLocallyStoredDHISMetadata();

	JSONObject getDHISOrgUnit(String orgUnitId);

	GlobalProperty getConfiguredOpeningHour();

	GlobalProperty getConfiguredClosingHour();

	GlobalProperty getConfiguredOpeningDays();

	/**
	 * Calculates and fetches OpenMRS Server uptime in minutes
	 * 
	 * @return
	 */
	Long getOpenMRSSystemUpTime();

	void updateNumberOfSecondsAtOpenMRSStartup(Long startTime);

	Integer getTotalCD4CountTestsEver();

	Integer getTotalCD4CountTestsLastSixMonths();

	Integer getTotalCD4CountTestsLastYear();

	Program getHIVProgram();

	Concept getReasonForExitingCareConcept();

	Concept getCD4CountConcept();

	Concept getViralLoadsConcept();

	Concept getARVDrugsConceptSet();

	void backupSystemMonitorDataToPush(JSONObject dhisValues);

	Integer rwandaPIHEMTGetTotalObservationsForYesterday();

	Integer rwandaPIHEMTGetTotalUsersForYesterday();

	Integer getTotalViralLoadTestsForYesterday();

	Integer getTotalCD4CountTestsForYesterday();

	Integer rwandaPIHEMTGetTotalAdultInitialEncountersForYesterday();

	Integer rwandaPIHEMTGetTotalAdultReturnEncountersForYesterday();

	Integer rwandaPIHEMTGetTotalPedsInitialEncountersForYesterday();

	Integer rwandaPIHEMTGetTotalPedsReturnEncountersForYesterday();

	Date getLastBackUpDate();

	File getLatestModifiedFile(File[] files);

	void cleanOldLocallyStoredLogsAndDHISData();

	Integer rwandaPIHEMTGetTotalNewPatientsForYesterday();

	Integer rwandaPIHEMTGetTotalActivePatientsForYesterday();

	String getDHISTodayPeriod();

	String getDHISYesterdayPeriod();

	/**
	 * TODO think of a better way of using this to may be capture current month
	 * when monitoring and maybe push to dhis as last month
	 * 
	 * @return
	 */
	String getDHISCurrentMonthPeriod();

	String getDHISConfiguredOrgUnitName();

	TaskDefinition getTaskByClass(String taskClass) throws DAOException;

	void rebootSystemMonitorTasks();

	JSONArray fetchDataToBePushedAtClientLevelOrExported();

	Date getEvaluationAndReportingDate();

	Integer rwandaGetTotalActivePatients_AtleastEightMonthsARVTreatment();

	Integer rwandaGetTotalActivePatients_AtleastTwentyMonthsARVTreatment();

	Integer rwandaGetTotalActivePatients_AtleastEightMonthsARVTreatment_AtleastOneViralLoad_InEMR();

	Integer rwandaGetTotalActivePatients_AtleastEightMonthsARVTreatment_AtleastOneCD4Count_InEMR();

	Integer rwandaGetTotalActivePatients_AtleastTwentyMonthsARVTreatment_AtleastOneViralLoad_LastYear();

	Integer rwandaGetTotalActivePatients_AtleastTwentyMonthsARVTreatment_AtleastOneCD4Count_LastYear();

	public Integer fetchTotalEncountersCountPreviousWeek();

	public Integer fetchTotalEncountersCountPreviousMonth();

	public Integer fetchTotalObservationsCountPreviousWeek();

	public Integer fetchTotalObservationsCountPreviousMonth();

	public Integer fetchTotalPatientsCountPreviousWeek();

	public Integer fetchTotalPatientsCountPreviousMonth();
}