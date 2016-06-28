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

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.Module;
import org.openmrs.module.systemmonitor.ConfigurableGlobalProperties;
import org.openmrs.module.systemmonitor.SystemMonitorConstants;
import org.openmrs.module.systemmonitor.api.SystemMonitorService;
import org.openmrs.module.systemmonitor.api.db.SystemMonitorDAO;
import org.openmrs.module.systemmonitor.curl.CurlEmulator;
import org.openmrs.module.systemmonitor.export.DHISGenerateDataValueSetSchemas;
import org.openmrs.module.systemmonitor.scheduler.SystemMonitorTimerTask;

/**
 * It is a default implementation of {@link SystemMonitorService}.
 */
public class SystemMonitorServiceImpl extends BaseOpenmrsService implements SystemMonitorService {

	protected final Log log = LogFactory.getLog(this.getClass());

	private SystemMonitorDAO dao;

	private SystemMonitorTimerTask systemMonitoringTask;

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
	public String getThisWeekEndDate() {
		return dao.getThisWeekEndDate();
	}

	@Override
	public String getThisWeekStartDate() {
		return dao.getThisWeekStartDate();
	}

	@Override
	public String getToday() {
		return dao.getToday();
	}

	@Override
	public String getLastWeekStartDate() {
		return dao.getLastWeekStartDate();
	}

	@Override
	public String getLastYearEndDate() {
		return dao.getLastYearEndDate();
	}

	@Override
	public String getLastYearStartDate() {
		return dao.getLastYearStartDate();
	}

	@Override
	public String getThisYearEndDate() {
		return dao.getThisYearEndDate();
	}

	@Override
	public String getThisYearStartDate() {
		return dao.getThisYearStartDate();
	}

	@Override
	public String getLastMonthEndDate() {
		return dao.getLastMonthEndDate();
	}

	@Override
	public String getLastMonthStartDate() {
		return dao.getLastMonthStartDate();
	}

	@Override
	public String getThisMonthEndDate() {
		return dao.getThisMonthEndDate();
	}

	@Override
	public String getThisMonthStartDate() {
		return dao.getThisMonthStartDate();
	}

	@Override
	public String getLastWeekEndDate() {
		return dao.getLastWeekEndDate();
	}

	@Override
	public Integer getTotalPatientsToday(Boolean includeRetired) {
		return dao.getTotalPatientsToday(includeRetired);
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

	@Override
	public Integer getTotalUsersThisWeek(Boolean includeRetired) {
		return dao.getTotalUsersThisWeek(includeRetired);
	}

	@Override
	public Integer getTotalUsersLastWeek(Boolean includeRetired) {
		return dao.getTotalUsersLastWeek(includeRetired);
	}

	@Override
	public Integer getTotalUsersLastMonth(Boolean includeRetired) {
		return dao.getTotalUsersLastMonth(includeRetired);
	}

	@Override
	public Integer getTotalUsersThisMonth(Boolean includeRetired) {
		return dao.getTotalUsersThisMonth(includeRetired);
	}

	@Override
	public Integer getTotalUsersThisYear(Boolean includeRetired) {
		return dao.getTotalUsersThisYear(includeRetired);
	}

	@Override
	public Integer getTotalUsersLastYear(Boolean includeRetired) {
		return dao.getTotalUsersLastYear(includeRetired);
	}

	@Override
	public Integer getTotalObservationsThisWeek(Boolean includeRetired) {
		return dao.getTotalObservationsThisWeek(includeRetired);
	}

	@Override
	public Integer getTotalObservationsLastWeek(Boolean includeRetired) {
		return dao.getTotalObservationsLastWeek(includeRetired);
	}

	@Override
	public Integer getTotalObservationsLastMonth(Boolean includeRetired) {
		return dao.getTotalObservationsLastMonth(includeRetired);
	}

	@Override
	public Integer getTotalObservationsThisMonth(Boolean includeRetired) {
		return dao.getTotalObservationsThisMonth(includeRetired);
	}

	@Override
	public Integer getTotalObservationsThisYear(Boolean includeRetired) {
		return dao.getTotalObservationsThisYear(includeRetired);
	}

	@Override
	public Integer getTotalObservationsLastYear(Boolean includeRetired) {
		return dao.getTotalObservationsLastYear(includeRetired);
	}

	@Override
	public Integer getTotalVisitsThisWeek(Boolean includeRetired) {
		return dao.getTotalVisitsThisWeek(includeRetired);
	}

	@Override
	public Integer getTotalVisitsLastWeek(Boolean includeRetired) {
		return dao.getTotalVisitsLastWeek(includeRetired);
	}

	@Override
	public Integer getTotalVisitsThisMonth(Boolean includeRetired) {
		return dao.getTotalVisitsThisMonth(includeRetired);
	}

	@Override
	public Integer getTotalVisitsLastMonth(Boolean includeRetired) {
		return dao.getTotalVisitsLastMonth(includeRetired);
	}

	@Override
	public Integer getTotalVisitsThisYear(Boolean includeRetired) {
		return dao.getTotalVisitsThisYear(includeRetired);
	}

	@Override
	public Integer getTotalVisitsLastYear(Boolean includeRetired) {
		return dao.getTotalVisitsLastYear(includeRetired);
	}

	@Override
	public Integer getTotalPatientsThisWeek(Boolean includeRetired) {
		return dao.getTotalPatientsThisWeek(includeRetired);
	}

	@Override
	public Integer getTotalPatientsLastWeek(Boolean includeRetired) {
		return dao.getTotalPatientsLastWeek(includeRetired);
	}

	@Override
	public Integer getTotalPatientsLastMonth(Boolean includeRetired) {
		return dao.getTotalPatientsLastMonth(includeRetired);
	}

	@Override
	public Integer getTotalPatientsThisMonth(Boolean includeRetired) {
		return dao.getTotalPatientsThisMonth(includeRetired);
	}

	@Override
	public Integer getTotalPatientsThisYear(Boolean includeRetired) {
		return dao.getTotalPatientsThisYear(includeRetired);
	}

	@Override
	public Integer getTotalPatientsLastYear(Boolean includeRetired) {
		return dao.getTotalPatientsLastYear(includeRetired);
	}

	@Override
	public Integer getTotalLocationsToday(Boolean includeRetired) {
		return dao.getTotalLocationsToday(includeRetired);
	}

	@Override
	public Integer getTotalLocationsThisWeek(Boolean includeRetired) {
		return dao.getTotalLocationsThisWeek(includeRetired);
	}

	@Override
	public Integer getTotalLocationsLastWeek(Boolean includeRetired) {
		return dao.getTotalLocationsLastWeek(includeRetired);
	}

	@Override
	public Integer getTotalLocationsLastMonth(Boolean includeRetired) {
		return dao.getTotalLocationsLastMonth(includeRetired);
	}

	@Override
	public Integer getTotalLocationsThisMonth(Boolean includeRetired) {
		return dao.getTotalLocationsThisMonth(includeRetired);
	}

	@Override
	public Integer getTotalLocationsThisYear(Boolean includeRetired) {
		return dao.getTotalLocationsThisYear(includeRetired);
	}

	@Override
	public Integer getTotalLocationsLastYear(Boolean includeRetired) {
		return dao.getTotalLocationsLastYear(includeRetired);
	}

	@Override
	public Integer getTotalConceptsLastYear(Boolean includeRetired) {
		return dao.getTotalConceptsLastYear(includeRetired);
	}

	@Override
	public Integer getTotalConceptsToday(Boolean includeRetired) {
		return dao.getTotalConceptsToday(includeRetired);
	}

	@Override
	public Integer getTotalConceptsThisWeek(Boolean includeRetired) {
		return dao.getTotalConceptsThisWeek(includeRetired);
	}

	@Override
	public Integer getTotalConceptsLastWeek(Boolean includeRetired) {
		return dao.getTotalConceptsLastWeek(includeRetired);
	}

	@Override
	public Integer getTotalConceptsLastMonth(Boolean includeRetired) {
		return dao.getTotalConceptsLastMonth(includeRetired);
	}

	@Override
	public Integer getTotalConceptsThisMonth(Boolean includeRetired) {
		return dao.getTotalConceptsThisMonth(includeRetired);
	}

	@Override
	public Integer getTotalConceptsThisYear(Boolean includeRetired) {
		return dao.getTotalConceptsThisYear(includeRetired);
	}

	@Override
	public Integer getTotalFormsLastYear(Boolean includeRetired) {
		return dao.getTotalFormsLastYear(includeRetired);
	}

	@Override
	public Integer getTotalFormsToday(Boolean includeRetired) {
		return dao.getTotalFormsToday(includeRetired);
	}

	@Override
	public Integer getTotalFormsThisWeek(Boolean includeRetired) {
		return dao.getTotalFormsThisWeek(includeRetired);
	}

	@Override
	public Integer getTotalFormsLastWeek(Boolean includeRetired) {
		return dao.getTotalFormsLastWeek(includeRetired);
	}

	@Override
	public Integer getTotalFormsLastMonth(Boolean includeRetired) {
		return dao.getTotalFormsLastMonth(includeRetired);
	}

	@Override
	public Integer getTotalFormsThisMonth(Boolean includeRetired) {
		return dao.getTotalFormsThisMonth(includeRetired);
	}

	@Override
	public Integer getTotalFormsThisYear(Boolean includeRetired) {
		return dao.getTotalFormsThisYear(includeRetired);
	}

	@Override
	public Integer getTotalOrdersLastYear(Boolean includeRetired) {
		return dao.getTotalOrdersLastYear(includeRetired);
	}

	@Override
	public Integer getTotalOrdersToday(Boolean includeRetired) {
		return dao.getTotalOrdersToday(includeRetired);
	}

	@Override
	public Integer getTotalOrdersThisWeek(Boolean includeRetired) {
		return dao.getTotalOrdersThisWeek(includeRetired);
	}

	@Override
	public Integer getTotalOrdersLastWeek(Boolean includeRetired) {
		return dao.getTotalOrdersLastWeek(includeRetired);
	}

	@Override
	public Integer getTotalOrdersLastMonth(Boolean includeRetired) {
		return dao.getTotalOrdersLastMonth(includeRetired);
	}

	@Override
	public Integer getTotalOrdersThisMonth(Boolean includeRetired) {
		return dao.getTotalOrdersThisMonth(includeRetired);
	}

	@Override
	public Integer getTotalProvidersLastYear(Boolean includeRetired) {
		return dao.getTotalProvidersLastYear(includeRetired);
	}

	@Override
	public Integer getTotalProvidersToday(Boolean includeRetired) {
		return dao.getTotalProvidersToday(includeRetired);
	}

	@Override
	public Integer getTotalProvidersThisWeek(Boolean includeRetired) {
		return dao.getTotalProvidersThisWeek(includeRetired);
	}

	@Override
	public Integer getTotalProvidersLastWeek(Boolean includeRetired) {
		return dao.getTotalProvidersLastWeek(includeRetired);
	}

	@Override
	public Integer getTotalProvidersLastMonth(Boolean includeRetired) {
		return dao.getTotalProvidersLastMonth(includeRetired);
	}

	@Override
	public Integer getTotalProvidersThisMonth(Boolean includeRetired) {
		return dao.getTotalProvidersThisMonth(includeRetired);
	}

	@Override
	public Integer getTotalProvidersThisYear(Boolean includeRetired) {
		return dao.getTotalProvidersThisYear(includeRetired);
	}

	@Override
	public Integer getTotalOrdersThisYear(Boolean includeRetired) {
		return dao.getTotalOrdersThisYear(includeRetired);
	}

	@Override
	public Integer getTotalEncounters(Boolean includeRetired) {
		return dao.getTotalEncounters(includeRetired);
	}

	@Override
	public Integer getTotalUsers(Boolean includeRetired) {
		return dao.getTotalUsers(includeRetired);
	}

	@Override
	public Integer getTotalObservations(Boolean includeRetired) {
		return dao.getTotalObservations(includeRetired);
	}

	@Override
	public Integer getTotalVisits(Boolean includeRetired) {
		return dao.getTotalVisits(includeRetired);
	}

	@Override
	public Integer getTotalPatients(Boolean includeRetired) {
		return dao.getTotalPatients(includeRetired);
	}

	@Override
	public Integer getTotalLocations(Boolean includeRetired) {
		return dao.getTotalLocations(includeRetired);
	}

	@Override
	public Integer getTotalConcepts(Boolean includeRetired) {
		return dao.getTotalConcepts(includeRetired);
	}

	@Override
	public Integer getTotalForms(Boolean includeRetired) {
		return dao.getTotalForms(includeRetired);
	}

	@Override
	public Integer getTotalOrders(Boolean includeRetired) {
		return dao.getTotalOrders(includeRetired);
	}

	@Override
	public Integer getTotalProviders(Boolean includeRetired) {
		return dao.getTotalProviders(includeRetired);
	}

	@Override
	public Integer getTotalViralLoadTestsEver() {
		return dao.getTotalViralLoadTestsEver();
	}

	@Override
	public Integer getTotalViralLoadTestsLastSixMonths() {
		return dao.getTotalViralLoadTestsLastSixMonths();
	}

	@Override
	public Integer getTotalViralLoadTestsLastYear() {
		return dao.getTotalViralLoadTestsLastYear();
	}

	@Override
	public Integer rwandaPIHEMTGetTotalVisits() {
		return dao.rwandaPIHEMTGetTotalVisits();
	}

	@Override
	public Integer rwandaPIHEMTGetTotalActivePatients() {
		return dao.rwandaPIHEMTGetTotalActivePatients();
	}

	@Override
	public Integer rwandaPIHEMTGetTotalNewPatients() {
		return dao.rwandaPIHEMTGetTotalNewPatients();
	}

	@Override
	public Integer rwandaPIHEMTGetTotalEncounters() {
		return dao.rwandaPIHEMTGetTotalEncounters();
	}

	@Override
	public Integer rwandaPIHEMTGetTotalObservations() {
		return dao.rwandaPIHEMTGetTotalObservations();
	}

	@Override
	public String getOneYearBackDate() {
		return dao.getOneYearBackDate();
	}

	@Override
	public Integer rwandaPIHEMTGetTotalUsers() {
		return dao.rwandaPIHEMTGetTotalUsers();
	}

	@Override
	public Person[] getAllPersonsWhoArePatients() {
		return dao.getAllPersonsWhoArePatients();
	}

	@Override
	public String getOneHalfYearBackDate() {
		return dao.getOneHalfYearBackDate();
	}

	@Override
	public void transferDHISMappingsAndMetadataFileToDataDirectory() {
		if (!SystemMonitorConstants.SYSTEMMONITOR_DIRECTORY.exists()) {
			SystemMonitorConstants.SYSTEMMONITOR_DIRECTORY.mkdirs();
		}

		File mappingsFile = new File(getClass().getClassLoader()
				.getResource(SystemMonitorConstants.SYSTEMMONITOR_MAPPING_FILENAME).getFile());
		File dataElementsFile = new File(getClass().getClassLoader()
				.getResource(SystemMonitorConstants.SYSTEMMONITOR_DATAELEMENTSMETADATA_FILENAME).getFile());

		try {
			FileUtils.copyFile(mappingsFile, SystemMonitorConstants.SYSTEMMONITOR_FINAL_MAPPINGFILE);
			FileUtils.copyFile(dataElementsFile, SystemMonitorConstants.SYSTEMMONITOR_DATAELEMENTSMETADATA_FILE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getCurrentConfiguredDHISOrgUnit() {
		return Context.getAdministrationService().getGlobalProperty(ConfigurableGlobalProperties.SITE_ID);
	}

	@Override
	public String getCurrentConfiguredDHISUsername() {
		return Context.getAdministrationService().getGlobalProperty(ConfigurableGlobalProperties.DHIS_USERNAME);
	}

	@Override
	public String getCurrentConfiguredDHISPassword() {
		return Context.getAdministrationService().getGlobalProperty(ConfigurableGlobalProperties.DHIS_PASSWORD);
	}

	@Override
	public JSONArray getInstalledModules() {
		JSONArray modulesArray = new JSONArray();

		for (Module module : SystemMonitorConstants.LOADED_MODULES) {
			if (module != null) {
				JSONObject moduleJSON = new JSONObject();

				moduleJSON.put("id", module.getModuleId());
				moduleJSON.put("name", module.getName());
				moduleJSON.put("version", module.getVersion());
				moduleJSON.put("author", module.getAuthor());
				moduleJSON.put("description", module.getDescription());

				modulesArray.put(moduleJSON);
			}
		}

		return modulesArray;
	}

	@Override
	public Integer unitTestingTheseMetrics() {
		return dao.unitTestingTheseMetrics();
	}

	@Override
	public JSONObject getDataToPushToDHIS() {
		return DHISGenerateDataValueSetSchemas.generateRwandaSPHEMTDHISDataValueSets().length() > 0
				? DHISGenerateDataValueSetSchemas.generateRwandaSPHEMTDHISDataValueSets().getJSONObject("toBePushed")
				: null;
	}

	@Override
	public String pushMonitoredDataToDHIS() {
		JSONObject dataToBePushed = getDataToPushToDHIS();
		
		String dhisUserName = ConfigurableGlobalProperties.DHIS_USERNAME;
		String dhisPassword = ConfigurableGlobalProperties.DHIS_PASSWORD;
		String dhisPostUrl = ConfigurableGlobalProperties.DHISAPI_URL;

		System.out.println("dataToBePushed: " + dataToBePushed);

		if (StringUtils.isNotBlank(dhisPostUrl) && StringUtils.isNotBlank(dhisPassword)
				&& StringUtils.isNotBlank(dhisUserName) && dataToBePushed != null) {
			try {
				return CurlEmulator.post(dhisPostUrl, dataToBePushed, dhisUserName, dhisPassword);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}