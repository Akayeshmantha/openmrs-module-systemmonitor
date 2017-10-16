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
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Concept;
import org.openmrs.GlobalProperty;
import org.openmrs.Person;
import org.openmrs.Program;
import org.openmrs.api.APIAuthenticationException;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.DAOException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.Module;
import org.openmrs.module.systemmonitor.ConfigurableGlobalProperties;
import org.openmrs.module.systemmonitor.SystemMonitorConstants;
import org.openmrs.module.systemmonitor.api.SystemMonitorService;
import org.openmrs.module.systemmonitor.api.db.SystemMonitorDAO;
import org.openmrs.module.systemmonitor.curl.CurlEmulator;
import org.openmrs.module.systemmonitor.hacks.WindowsResourceFileNotFoundHack;
import org.openmrs.module.systemmonitor.mapping.DHISMapping;
import org.openmrs.scheduler.SchedulerException;
import org.openmrs.scheduler.TaskDefinition;
import org.openmrs.util.OpenmrsUtil;

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
	public String getYesterdayStartDate() {
		return getDao().getYesterdayStartDate();
	}

	@Override
	public String getYesterdayEndDate() {
		return getDao().getYesterdayEndDate();
	}

	@Override
	public String getThisWeekEndDate() {
		return getDao().getThisWeekEndDate();
	}

	@Override
	public String getThisWeekStartDate() {
		return getDao().getThisWeekStartDate();
	}

	@Override
	public String getToday() {
		return getDao().getToday();
	}

	@Override
	public String getLastWeekStartDate() {
		return getDao().getLastWeekStartDate();
	}

	@Override
	public String getLastYearEndDate() {
		return getDao().getLastYearEndDate();
	}

	@Override
	public String getLastYearStartDate() {
		return getDao().getLastYearStartDate();
	}

	@Override
	public String getThisYearEndDate() {
		return getDao().getThisYearEndDate();
	}

	@Override
	public String getThisYearStartDate() {
		return getDao().getThisYearStartDate();
	}

	@Override
	public String getLastMonthEndDate() {
		return getDao().getLastMonthEndDate();
	}

	@Override
	public String getLastMonthStartDate() {
		return getDao().getLastMonthStartDate();
	}

	@Override
	public String getThisMonthEndDate() {
		return getDao().getThisMonthEndDate();
	}

	@Override
	public String getThisMonthStartDate() {
		return getDao().getThisMonthStartDate();
	}

	@Override
	public String getLastWeekEndDate() {
		return getDao().getLastWeekEndDate();
	}

	@Override
	public Integer getTotalPatientsToday(Boolean includeRetired) {
		return getDao().getTotalPatientsToday(includeRetired);
	}

	@Override
	public Integer getTotalVisitsToday(Boolean includeRetired) {
		return getDao().getTotalVisitsToday(includeRetired);
	}

	@Override
	public Integer getTotalObservationsToday(Boolean includeRetired) {
		return getDao().getTotalObservationsToday(includeRetired);
	}

	@Override
	public Integer getTotalUsersToday(Boolean includeRetired) {
		return getDao().getTotalUsersToday(includeRetired);
	}

	@Override
	public Integer getTotalEncountersToday(Boolean includeRetired) {
		return getDao().getTotalEncountersToday(includeRetired);
	}

	@Override
	public Integer getTotalEncountersThisWeek(Boolean includeRetired) {
		return getDao().getTotalEncountersThisWeek(includeRetired);
	}

	@Override
	public Integer getTotalEncountersLastWeek(Boolean includeRetired) {
		return getDao().getTotalEncountersLastWeek(includeRetired);
	}

	@Override
	public Integer getTotalEncountersLastMonth(Boolean includeRetired) {
		return getDao().getTotalEncountersLastMonth(includeRetired);
	}

	@Override
	public Integer getTotalEncountersThisMonth(Boolean includeRetired) {
		return getDao().getTotalEncountersThisMonth(includeRetired);
	}

	@Override
	public Integer getTotalEncountersThisYear(Boolean includeRetired) {
		return getDao().getTotalEncountersThisYear(includeRetired);
	}

	@Override
	public Integer getTotalEncountersLastYear(Boolean includeRetired) {
		return getDao().getTotalEncountersLastYear(includeRetired);
	}

	@Override
	public Integer getTotalUsersThisWeek(Boolean includeRetired) {
		return getDao().getTotalUsersThisWeek(includeRetired);
	}

	@Override
	public Integer getTotalUsersLastWeek(Boolean includeRetired) {
		return getDao().getTotalUsersLastWeek(includeRetired);
	}

	@Override
	public Integer getTotalUsersLastMonth(Boolean includeRetired) {
		return getDao().getTotalUsersLastMonth(includeRetired);
	}

	@Override
	public Integer getTotalUsersThisMonth(Boolean includeRetired) {
		return getDao().getTotalUsersThisMonth(includeRetired);
	}

	@Override
	public Integer getTotalUsersThisYear(Boolean includeRetired) {
		return getDao().getTotalUsersThisYear(includeRetired);
	}

	@Override
	public Integer getTotalUsersLastYear(Boolean includeRetired) {
		return getDao().getTotalUsersLastYear(includeRetired);
	}

	@Override
	public Integer getTotalObservationsThisWeek(Boolean includeRetired) {
		return getDao().getTotalObservationsThisWeek(includeRetired);
	}

	@Override
	public Integer getTotalObservationsLastWeek(Boolean includeRetired) {
		return getDao().getTotalObservationsLastWeek(includeRetired);
	}

	@Override
	public Integer getTotalObservationsLastMonth(Boolean includeRetired) {
		return getDao().getTotalObservationsLastMonth(includeRetired);
	}

	@Override
	public Integer getTotalObservationsThisMonth(Boolean includeRetired) {
		return getDao().getTotalObservationsThisMonth(includeRetired);
	}

	@Override
	public Integer getTotalObservationsThisYear(Boolean includeRetired) {
		return getDao().getTotalObservationsThisYear(includeRetired);
	}

	@Override
	public Integer getTotalObservationsLastYear(Boolean includeRetired) {
		return getDao().getTotalObservationsLastYear(includeRetired);
	}

	@Override
	public Integer getTotalVisitsThisWeek(Boolean includeRetired) {
		return getDao().getTotalVisitsThisWeek(includeRetired);
	}

	@Override
	public Integer getTotalVisitsLastWeek(Boolean includeRetired) {
		return getDao().getTotalVisitsLastWeek(includeRetired);
	}

	@Override
	public Integer getTotalVisitsThisMonth(Boolean includeRetired) {
		return getDao().getTotalVisitsThisMonth(includeRetired);
	}

	@Override
	public Integer getTotalVisitsLastMonth(Boolean includeRetired) {
		return getDao().getTotalVisitsLastMonth(includeRetired);
	}

	@Override
	public Integer getTotalVisitsThisYear(Boolean includeRetired) {
		return getDao().getTotalVisitsThisYear(includeRetired);
	}

	@Override
	public Integer getTotalVisitsLastYear(Boolean includeRetired) {
		return getDao().getTotalVisitsLastYear(includeRetired);
	}

	@Override
	public Integer getTotalPatientsThisWeek(Boolean includeRetired) {
		return getDao().getTotalPatientsThisWeek(includeRetired);
	}

	@Override
	public Integer getTotalPatientsLastWeek(Boolean includeRetired) {
		return getDao().getTotalPatientsLastWeek(includeRetired);
	}

	@Override
	public Integer getTotalPatientsLastMonth(Boolean includeRetired) {
		return getDao().getTotalPatientsLastMonth(includeRetired);
	}

	@Override
	public Integer getTotalPatientsThisMonth(Boolean includeRetired) {
		return getDao().getTotalPatientsThisMonth(includeRetired);
	}

	@Override
	public Integer getTotalPatientsThisYear(Boolean includeRetired) {
		return getDao().getTotalPatientsThisYear(includeRetired);
	}

	@Override
	public Integer getTotalPatientsLastYear(Boolean includeRetired) {
		return getDao().getTotalPatientsLastYear(includeRetired);
	}

	@Override
	public Integer getTotalLocationsToday(Boolean includeRetired) {
		return getDao().getTotalLocationsToday(includeRetired);
	}

	@Override
	public Integer getTotalLocationsThisWeek(Boolean includeRetired) {
		return getDao().getTotalLocationsThisWeek(includeRetired);
	}

	@Override
	public Integer getTotalLocationsLastWeek(Boolean includeRetired) {
		return getDao().getTotalLocationsLastWeek(includeRetired);
	}

	@Override
	public Integer getTotalLocationsLastMonth(Boolean includeRetired) {
		return getDao().getTotalLocationsLastMonth(includeRetired);
	}

	@Override
	public Integer getTotalLocationsThisMonth(Boolean includeRetired) {
		return getDao().getTotalLocationsThisMonth(includeRetired);
	}

	@Override
	public Integer getTotalLocationsThisYear(Boolean includeRetired) {
		return getDao().getTotalLocationsThisYear(includeRetired);
	}

	@Override
	public Integer getTotalLocationsLastYear(Boolean includeRetired) {
		return getDao().getTotalLocationsLastYear(includeRetired);
	}

	@Override
	public Integer getTotalConceptsLastYear(Boolean includeRetired) {
		return getDao().getTotalConceptsLastYear(includeRetired);
	}

	@Override
	public Integer getTotalConceptsToday(Boolean includeRetired) {
		return getDao().getTotalConceptsToday(includeRetired);
	}

	@Override
	public Integer getTotalConceptsThisWeek(Boolean includeRetired) {
		return getDao().getTotalConceptsThisWeek(includeRetired);
	}

	@Override
	public Integer getTotalConceptsLastWeek(Boolean includeRetired) {
		return getDao().getTotalConceptsLastWeek(includeRetired);
	}

	@Override
	public Integer getTotalConceptsLastMonth(Boolean includeRetired) {
		return getDao().getTotalConceptsLastMonth(includeRetired);
	}

	@Override
	public Integer getTotalConceptsThisMonth(Boolean includeRetired) {
		return getDao().getTotalConceptsThisMonth(includeRetired);
	}

	@Override
	public Integer getTotalConceptsThisYear(Boolean includeRetired) {
		return getDao().getTotalConceptsThisYear(includeRetired);
	}

	@Override
	public Integer getTotalFormsLastYear(Boolean includeRetired) {
		return getDao().getTotalFormsLastYear(includeRetired);
	}

	@Override
	public Integer getTotalFormsToday(Boolean includeRetired) {
		return getDao().getTotalFormsToday(includeRetired);
	}

	@Override
	public Integer getTotalFormsThisWeek(Boolean includeRetired) {
		return getDao().getTotalFormsThisWeek(includeRetired);
	}

	@Override
	public Integer getTotalFormsLastWeek(Boolean includeRetired) {
		return getDao().getTotalFormsLastWeek(includeRetired);
	}

	@Override
	public Integer getTotalFormsLastMonth(Boolean includeRetired) {
		return getDao().getTotalFormsLastMonth(includeRetired);
	}

	@Override
	public Integer getTotalFormsThisMonth(Boolean includeRetired) {
		return getDao().getTotalFormsThisMonth(includeRetired);
	}

	@Override
	public Integer getTotalFormsThisYear(Boolean includeRetired) {
		return getDao().getTotalFormsThisYear(includeRetired);
	}

	@Override
	public Integer getTotalOrdersLastYear(Boolean includeRetired) {
		return getDao().getTotalOrdersLastYear(includeRetired);
	}

	@Override
	public Integer getTotalOrdersToday(Boolean includeRetired) {
		return getDao().getTotalOrdersToday(includeRetired);
	}

	@Override
	public Integer getTotalOrdersThisWeek(Boolean includeRetired) {
		return getDao().getTotalOrdersThisWeek(includeRetired);
	}

	@Override
	public Integer getTotalOrdersLastWeek(Boolean includeRetired) {
		return getDao().getTotalOrdersLastWeek(includeRetired);
	}

	@Override
	public Integer getTotalOrdersLastMonth(Boolean includeRetired) {
		return getDao().getTotalOrdersLastMonth(includeRetired);
	}

	@Override
	public Integer getTotalOrdersThisMonth(Boolean includeRetired) {
		return getDao().getTotalOrdersThisMonth(includeRetired);
	}

	@Override
	public Integer getTotalProvidersLastYear(Boolean includeRetired) {
		return getDao().getTotalProvidersLastYear(includeRetired);
	}

	@Override
	public Integer getTotalProvidersToday(Boolean includeRetired) {
		return getDao().getTotalProvidersToday(includeRetired);
	}

	@Override
	public Integer getTotalProvidersThisWeek(Boolean includeRetired) {
		return getDao().getTotalProvidersThisWeek(includeRetired);
	}

	@Override
	public Integer getTotalProvidersLastWeek(Boolean includeRetired) {
		return getDao().getTotalProvidersLastWeek(includeRetired);
	}

	@Override
	public Integer getTotalProvidersLastMonth(Boolean includeRetired) {
		return getDao().getTotalProvidersLastMonth(includeRetired);
	}

	@Override
	public Integer getTotalProvidersThisMonth(Boolean includeRetired) {
		return getDao().getTotalProvidersThisMonth(includeRetired);
	}

	@Override
	public Integer getTotalProvidersThisYear(Boolean includeRetired) {
		return getDao().getTotalProvidersThisYear(includeRetired);
	}

	@Override
	public Integer getTotalOrdersThisYear(Boolean includeRetired) {
		return getDao().getTotalOrdersThisYear(includeRetired);
	}

	@Override
	public Integer getTotalEncounters(Boolean includeRetired) {
		return getDao().getTotalEncounters(includeRetired);
	}

	@Override
	public Integer getTotalUsers(Boolean includeRetired) {
		return getDao().getTotalUsers(includeRetired);
	}

	@Override
	public Integer getTotalObservations(Boolean includeRetired) {
		return getDao().getTotalObservations(includeRetired);
	}

	@Override
	public Integer getTotalVisits(Boolean includeRetired) {
		return getDao().getTotalVisits(includeRetired);
	}

	@Override
	public Integer getTotalPatients(Boolean includeRetired) {
		return getDao().getTotalPatients(includeRetired);
	}

	@Override
	public Integer getTotalLocations(Boolean includeRetired) {
		return getDao().getTotalLocations(includeRetired);
	}

	@Override
	public Integer getTotalConcepts(Boolean includeRetired) {
		return getDao().getTotalConcepts(includeRetired);
	}

	@Override
	public Integer getTotalForms(Boolean includeRetired) {
		return getDao().getTotalForms(includeRetired);
	}

	@Override
	public Integer getTotalOrders(Boolean includeRetired) {
		return getDao().getTotalOrders(includeRetired);
	}

	@Override
	public Integer getTotalProviders(Boolean includeRetired) {
		return getDao().getTotalProviders(includeRetired);
	}

	@Override
	public Integer getTotalViralLoadTestsEver() {
		return getDao().getTotalViralLoadTestsEver();
	}

	@Override
	public Integer getTotalViralLoadTestsLastSixMonths() {
		return getDao().getTotalViralLoadTestsLastSixMonths();
	}

	@Override
	public Integer getTotalViralLoadTestsLastYear() {
		return getDao().getTotalViralLoadTestsLastYear();
	}

	@Override
	public Integer rwandaPIHEMTGetTotalVisits() {
		return getDao().rwandaPIHEMTGetTotalVisits();
	}

	@Override
	public Integer rwandaPIHEMTGetTotalActivePatients() {
		return getDao().rwandaPIHEMTGetTotalActivePatients();
	}

	@Override
	public Integer rwandaPIHEMTGetTotalNewPatients() {
		return getDao().rwandaPIHEMTGetTotalNewPatients();
	}

	@Override
	public Integer rwandaPIHEMTGetTotalEncounters() {
		return getDao().rwandaPIHEMTGetTotalEncounters();
	}

	@Override
	public Integer rwandaPIHEMTGetTotalEncountersForYesterday() {
		return getDao().rwandaPIHEMTGetTotalEncountersForYesterday();
	}

	@Override
	public Integer rwandaPIHEMTGetTotalObservations() {
		return getDao().rwandaPIHEMTGetTotalObservations();
	}

	@Override
	public String getOneYearBackDate() {
		return getDao().getOneYearBackDate();
	}

	@Override
	public Integer rwandaPIHEMTGetTotalUsers() {
		return getDao().rwandaPIHEMTGetTotalUsers();
	}

	@Override
	public Person[] getAllPersonsWhoArePatients() {
		return getDao().getAllPersonsWhoArePatients();
	}

	@Override
	public String getOneHalfYearBackDate() {
		return getDao().getOneHalfYearBackDate();
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
			if (!SystemMonitorConstants.SYSTEMMONITOR_FINAL_MAPPINGFILE.exists()
					|| (SystemMonitorConstants.SYSTEMMONITOR_FINAL_MAPPINGFILE.exists() && !addedLocalDHISMappings())) {
				try {
					FileUtils.copyFile(mappingsFile, SystemMonitorConstants.SYSTEMMONITOR_FINAL_MAPPINGFILE);
				} catch (FileNotFoundException e) {
					if (e.getMessage().indexOf("Source") > -1
							|| e.getMessage().indexOf(SystemMonitorConstants.SYSTEMMONITOR_MAPPING_FILENAME) > -1)
						WindowsResourceFileNotFoundHack.addMappingsFileToSystemMonitorDataDirectory();
					if (e.getMessage().indexOf("Source") > -1 || e.getMessage()
							.indexOf(SystemMonitorConstants.SYSTEMMONITOR_DATAELEMENTSMETADATA_FILENAME) > -1)
						WindowsResourceFileNotFoundHack.addDHISDataElementsMetadataToSystemMonitorDataDirectory();
				}
			}
			FileUtils.copyFile(dataElementsFile,
					SystemMonitorConstants.SYSTEMMONITOR_DATAELEMENTSMETADATA_FILE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean addedLocalDHISMappings() {
		String addedString = Context.getAdministrationService()
				.getGlobalProperty(ConfigurableGlobalProperties.ADDED_LOCAL_DHISMAPPINGS);

		if (StringUtils.isNotBlank(addedString) && addedString.equals("true")) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public GlobalProperty getCurrentConfiguredDHISOrgUnit() {
		return Context.getAdministrationService().getGlobalPropertyObject(ConfigurableGlobalProperties.SITE_ID);
	}

	@Override
	public GlobalProperty getCurrentConfiguredDHISUsername() {
		return Context.getAdministrationService().getGlobalPropertyObject(ConfigurableGlobalProperties.DHIS_USERNAME);
	}

	@Override
	public GlobalProperty getCurrentConfiguredDHISPassword() {
		return Context.getAdministrationService().getGlobalPropertyObject(ConfigurableGlobalProperties.DHIS_PASSWORD);
	}

	@Override
	public GlobalProperty getConfiguredOpeningHour() {
		return Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.CONFIGS_OPENNINGHOUR);
	}

	@Override
	public GlobalProperty getConfiguredClosingHour() {
		return Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.CONFIGS_CLOSINGHOUR);
	}

	@Override
	public GlobalProperty getConfiguredOpeningDays() {
		return Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.CONFIGS_OPENNINGDAYS);
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
		return getDao().unitTestingTheseMetrics();
	}

	@Override
	public JSONObject getDataToPushToDHIS() {
		return getDao().getDataToPushToDHIS();
	}

	@Override
	public String pushMonitoredDataToDHIS() {
		String r1 = updatePreviouslySubmittedOrMissedSMTData();
		String resp = getDao().pushPreviouslyFailedDataWhenOutOfInternet();
		JSONObject r2 = getDao().runSMTEvaluatorAndLogOrPushData();

		return (StringUtils.isNotBlank(r1) ? r1 : "") + (StringUtils.isNotBlank(resp) ? resp : "")
				+ (r2 != null ? r2.toString() : "");
	}

	@Override
	public void backupSystemMonitorDataToPush(JSONObject dhisValues) {
		getDao().backupSystemMonitorDataToPush(dhisValues);
	}

	@Override
	public JSONObject getIndicatorOrMetricOrDataElement(String indicatorUid) {
		JSONObject metric = null;
		File dataElementsFile = SystemMonitorConstants.SYSTEMMONITOR_DATAELEMENTSMETADATA_FILE;
		String jsonDataElementsString = "";
		JSONObject jsonDataElements;

		if (StringUtils.isNotBlank(indicatorUid) && dataElementsFile != null && dataElementsFile.exists()) {
			jsonDataElementsString = readFileToString(dataElementsFile);
			if (StringUtils.isNotBlank(jsonDataElementsString)) {
				jsonDataElements = new JSONObject(jsonDataElementsString);
				if (jsonDataElements != null && jsonDataElements.getJSONArray("dataElements") != null) {
					for (int i = 0; i < jsonDataElements.getJSONArray("dataElements").length(); i++) {
						JSONObject json = jsonDataElements.getJSONArray("dataElements").getJSONObject(i);

						if (json != null && StringUtils.isNotBlank(json.getString("id"))
								&& indicatorUid.equals(json.getString("id"))) {
							metric = json;
							break;
						}
					}
				}
			}
		}
		return metric;
	}

	@Override
	public JSONObject getDHISOrgUnit(String orgUnitId) {
		JSONObject orgUnitObj = null;
		File orgUnitsMetadataFile = SystemMonitorConstants.SYSTEMMONITOR_ORGUNIT_FILE;
		String jsonOrgUnitsString = "";
		JSONObject jsonOrgUnits;

		if (StringUtils.isNotBlank(orgUnitId) && orgUnitsMetadataFile != null && orgUnitsMetadataFile.exists()) {
			jsonOrgUnitsString = readFileToString(orgUnitsMetadataFile);
			if (StringUtils.isNotBlank(jsonOrgUnitsString)) {
				jsonOrgUnits = new JSONObject(jsonOrgUnitsString);

				if (jsonOrgUnits != null && orgUnitId.equals(jsonOrgUnits.getString("id"))) {
					orgUnitObj = jsonOrgUnits;
				}
			}
		}
		return orgUnitObj;
	}

	private String readFileToString(File file) {
		return getDao().readFileToString(file);
	}

	@Override
	public void updateLocallyStoredDHISMetadata() {
		try {
			updateDataElementsFromDHISConfiguredRemoteInstance();
			updateOrgUnitsFromDHISConfiguredRemoteInstance();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	private void updateDataElementsFromDHISConfiguredRemoteInstance() throws UnknownHostException {
		updateDHISDataElementsOrOrgUnit(true);
	}

	private void updateOrgUnitsFromDHISConfiguredRemoteInstance() throws UnknownHostException {
		updateDHISDataElementsOrOrgUnit(false);
	}

	private void writeToFile(String textToOverwriteTheFile, File file) {
		if (StringUtils.isNotBlank(textToOverwriteTheFile) && file != null) {
			try {
				if (!file.exists()) {
					file.createNewFile();
				}

				FileWriter fileWriter = new FileWriter(file);
				fileWriter.write(textToOverwriteTheFile);
				fileWriter.flush();
				fileWriter.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void updateDHISDataElementsOrOrgUnit(boolean isDataelementUpdateIfTrueOrOrgUnitUpdateIfFalse)
			throws UnknownHostException {
		String dhisUserName = Context.getAdministrationService()
				.getGlobalProperty(ConfigurableGlobalProperties.DHIS_USERNAME);
		String dhisPassword = Context.getAdministrationService()
				.getGlobalProperty(ConfigurableGlobalProperties.DHIS_PASSWORD);
		String dhisGetUrl;
		String configuredOrgUnitID = DHISMapping
				.getDHISMappedObjectValue(getCurrentConfiguredDHISOrgUnit().getPropertyValue());
		File dhisDataFile;

		if (isDataelementUpdateIfTrueOrOrgUnitUpdateIfFalse) {
			dhisGetUrl = (Context.getAdministrationService()
					.getGlobalProperty(ConfigurableGlobalProperties.DHISAPI_URL)) != null
							? Context.getAdministrationService()
									.getGlobalProperty(ConfigurableGlobalProperties.DHISAPI_URL)
									+ SystemMonitorConstants.DHIS_API_DATAELEMENTS_URL + "?pageSize=1000"
							: null;
			dhisDataFile = SystemMonitorConstants.SYSTEMMONITOR_DATAELEMENTSMETADATA_FILE;
		} else {
			dhisGetUrl = (StringUtils
					.isNotBlank(Context.getAdministrationService()
							.getGlobalProperty(ConfigurableGlobalProperties.DHISAPI_URL))
					&& StringUtils.isNotBlank(configuredOrgUnitID))
							? Context.getAdministrationService()
									.getGlobalProperty(ConfigurableGlobalProperties.DHISAPI_URL)
									+ SystemMonitorConstants.DHIS_API_ORGUNITS_URL + configuredOrgUnitID
							: null;
			dhisDataFile = SystemMonitorConstants.SYSTEMMONITOR_ORGUNIT_FILE;
		}
		if (StringUtils.isNotBlank(dhisGetUrl) && StringUtils.isNotBlank(dhisPassword)
				&& StringUtils.isNotBlank(dhisUserName)) {
			JSONObject returnedJSON;
			try {
				returnedJSON = CurlEmulator.get(dhisGetUrl, dhisUserName, dhisPassword);

				if (returnedJSON != null) {
					writeToFile(returnedJSON.toString(), dhisDataFile);
				}
			} catch (UnknownHostException e) {
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}
	}

	private Long getNumberOfSecondsAtOpenMRSStartup() {
		return Long.parseLong(Context.getAdministrationService()
				.getGlobalProperty(ConfigurableGlobalProperties.NUMBER_OF_SECS_AT_STARTUP));
	}

	private Long getNumberOfSecondsAtOpenMRSOpenningHours() {
		Integer openingHour = Integer.parseInt(getConfiguredOpeningHour().getPropertyValue().substring(0, 2));
		Calendar evalStart = Calendar.getInstance();

		evalStart.setTime(getEvaluationAndReportingDate());
		evalStart.set(Calendar.HOUR_OF_DAY, openingHour);
		evalStart.clear(Calendar.MINUTE);
		evalStart.clear(Calendar.SECOND);
		evalStart.clear(Calendar.MILLISECOND);

		return evalStart.getTimeInMillis();
	}

	@Override
	public Long getOpenMRSSystemUpTime() {
		return ((System.currentTimeMillis() / 1000) - getNumberOfSecondsAtOpenMRSOpenningHours()) / 60;
	}

	@Override
	public void updateNumberOfSecondsAtOpenMRSStartup(Long startTime) {
		GlobalProperty numberOfSecondsAtOpenMRSStartupGp = Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.NUMBER_OF_SECS_AT_STARTUP);

		numberOfSecondsAtOpenMRSStartupGp.setPropertyValue(Long.toString(startTime));
		Context.getAdministrationService().saveGlobalProperty(numberOfSecondsAtOpenMRSStartupGp);
	}

	@Override
	public Integer getTotalCD4CountTestsEver() {
		return getDao().getTotalCD4CountTestsEver();
	}

	@Override
	public Integer getTotalCD4CountTestsLastSixMonths() {
		return getDao().getTotalCD4CountTestsLastSixMonths();
	}

	@Override
	public Integer getTotalCD4CountTestsLastYear() {
		return getDao().getTotalCD4CountTestsLastYear();
	}

	@Override
	public Program getHIVProgram() {
		return getDao().getHIVProgram();
	}

	@Override
	public Concept getReasonForExitingCareConcept() {
		return getDao().getReasonForExitingCareConcept();
	}

	@Override
	public Concept getCD4CountConcept() {
		return getDao().getCD4CountConcept();
	}

	@Override
	public Concept getViralLoadsConcept() {
		return getDao().getViralLoadsConcept();
	}

	@Override
	public Concept getARVDrugsConceptSet() {
		return getDao().getARVDrugsConceptSet();
	}

	@Override
	public Integer rwandaPIHEMTGetTotalObservationsForYesterday() {
		return getDao().rwandaPIHEMTGetTotalObservationsForYesterday();
	}

	@Override
	public Integer rwandaPIHEMTGetTotalUsersForYesterday() {
		return getDao().rwandaPIHEMTGetTotalUsersForYesterday();
	}

	@Override
	public Integer getTotalViralLoadTestsForYesterday() {
		return getDao().getTotalViralLoadTestsForYesterday();
	}

	@Override
	public Integer getTotalCD4CountTestsForYesterday() {
		return getDao().getTotalCD4CountTestsForYesterday();
	}

	@Override
	public Integer rwandaPIHEMTGetTotalAdultInitialEncountersForYesterday() {
		return getDao().rwandaPIHEMTGetTotalAdultInitialEncountersForYesterday();
	}

	@Override
	public Integer rwandaPIHEMTGetTotalAdultReturnEncountersForYesterday() {
		return getDao().rwandaPIHEMTGetTotalAdultReturnEncountersForYesterday();
	}

	@Override
	public Integer rwandaPIHEMTGetTotalPedsInitialEncountersForYesterday() {
		return getDao().rwandaPIHEMTGetTotalPedsInitialEncountersForYesterday();
	}

	@Override
	public Integer rwandaPIHEMTGetTotalPedsReturnEncountersForYesterday() {
		return getDao().rwandaPIHEMTGetTotalPedsReturnEncountersForYesterday();
	}

	@Override
	public Date getLastBackUpDate() {
		Date lastBackUpDate = null;
		String backupFolderPathOrName = Context.getAdministrationService()
				.getGlobalProperty(ConfigurableGlobalProperties.BACKUP_FOLDERPATHORNAME);
		File backUpDirectory = new File(backupFolderPathOrName);

		if (!backUpDirectory.isAbsolute()) {
			backUpDirectory = OpenmrsUtil.getDirectoryInApplicationDataDirectory(backupFolderPathOrName);
		}
		if (!backUpDirectory.exists())
			backUpDirectory.mkdirs();
		if (backUpDirectory.isDirectory() && backUpDirectory.listFiles().length > 0) {
			File dbBackup = getLatestModifiedFile(filterOnlySqlFilesInDirectory(backUpDirectory));

			if (dbBackup != null) {
				lastBackUpDate = new Date(dbBackup.lastModified());
			}
		}

		return lastBackUpDate;
	}

	/* TODO this in the future should use CustomFileExtensionFilter */
	private File[] filterOnlySqlFilesInDirectory(File directory) {
		List<File> filteredFiles = new ArrayList<File>();

		for (File file : directory.listFiles()) {
			if (file.getName().endsWith(".sql")) {
				filteredFiles.add(file);
			}
		}

		return filteredFiles.toArray(new File[filteredFiles.size()]);
	}

	/**
	 * @param files,
	 *            array of files to compare to retrieve last modified among them
	 * @return lastModfied file
	 */
	@Override
	public File getLatestModifiedFile(File[] files) {
		if (files == null || files.length == 0) {
			return null;
		}

		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				lastModifiedFile = files[i];
			}
		}
		return lastModifiedFile;
	}

	@Override
	public void cleanOldLocallyStoredLogsAndDHISData() {
		Integer monthsToStoreLogsAndData = Integer.parseInt(Context.getAdministrationService()
				.getGlobalProperty(ConfigurableGlobalProperties.MONTHS_TO_STORE_LOGSANDDATA));

		deleteFileWhoseLastModifedRangesOutsideNMonths(monthsToStoreLogsAndData,
				SystemMonitorConstants.SYSTEMMONITOR_LOGSFOLDER.listFiles());
		deleteFileWhoseLastModifedRangesOutsideNMonths(monthsToStoreLogsAndData,
				SystemMonitorConstants.SYSTEMMONITOR_BACKUPFOLDER.listFiles());
	}

	private void deleteFileWhoseLastModifedRangesOutsideNMonths(Integer numberOfMonths, File[] files) {
		if (files != null && numberOfMonths != null) {
			for (File file : files) {
				if (!checkIfDateIsInNMonthsRange(numberOfMonths, new Date(file.lastModified()))) {
					file.delete();
				}
			}
		}
	}

	private boolean checkIfDateIsInNMonthsRange(Integer nMonths, Date date) {
		if (nMonths != null && nMonths > 0) {
			Calendar today = Calendar.getInstance(Context.getLocale());
			Calendar nMonthsAgo = Calendar.getInstance(Context.getLocale());

			nMonthsAgo.add(Calendar.YEAR, -nMonths / 12);

			return date.after(nMonthsAgo.getTime()) && date.before(today.getTime());
		}

		return true;
	}

	@Override
	public Integer rwandaPIHEMTGetTotalNewPatientsForYesterday() {
		return getDao().rwandaPIHEMTGetTotalNewPatientsForYesterday();
	}

	@Override
	public Integer rwandaPIHEMTGetTotalActivePatientsForYesterday() {
		return getDao().rwandaPIHEMTGetTotalActivePatientsForYesterday();
	}

	@Override
	public String getDHISTodayPeriod() {
		Calendar calendar = Calendar.getInstance(Context.getLocale());

		calendar.setTime(getEvaluationAndReportingDate());
		return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
	}

	@Override
	public String getDHISYesterdayPeriod() {
		Calendar yesterdayFromToday = Calendar.getInstance(Context.getLocale());

		yesterdayFromToday.setTime(getEvaluationAndReportingDate());
		yesterdayFromToday.add(Calendar.DAY_OF_YEAR, -1);
		return new SimpleDateFormat("yyyyMMdd").format(yesterdayFromToday.getTime());
	}

	@Override
	public String getDHISCurrentMonthPeriod() {
		Calendar lastMonthFromToday = Calendar.getInstance(Context.getLocale());

		lastMonthFromToday.setTime(getEvaluationAndReportingDate());
		return new SimpleDateFormat("yyyyMM").format(lastMonthFromToday.getTime());
	}

	@Override
	public Date getEvaluationAndReportingDate() {
		return getDao().getEvaluationAndReportingDate();
	}

	@Override
	public String getDHISConfiguredOrgUnitName() {
		String orgUnitId = DHISMapping.getDHISMappedObjectValue(getCurrentConfiguredDHISOrgUnit().getPropertyValue());

		if (StringUtils.isNotBlank(orgUnitId)) {
			JSONObject orgUnit = getDHISOrgUnit(orgUnitId);

			if (orgUnit != null) {
				return orgUnit.getString("name");
			}
		}
		return null;
	}

	@Override
	public void rebootSystemMonitorTasks() {
		Collection<TaskDefinition> tasks = Context.getSchedulerService().getRegisteredTasks();

		try {
			if (tasks != null) {
				for (TaskDefinition task : tasks) {
					if (task != null && StringUtils.isNotBlank(task.getTaskClass())
							&& task.getTaskClass().startsWith("org.openmrs.module.systemmonitor.scheduler.")) {
						Context.getSchedulerService().scheduleTask(task);
					}
				}
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (APIAuthenticationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public TaskDefinition getTaskByClass(String taskClass) throws DAOException {
		return getDao().getTaskByClass(taskClass);
	}

	@Override
	public JSONArray fetchDataToBePushedAtClientLevelOrExported() {
		JSONArray json = new JSONArray();

		JSONArray currentDataValues = reArrangeDataValuesJSONArrayToIncludeOrgUnitInEachEntry(getDataToPushToDHIS());
		JSONArray storedDataValues = new JSONArray();
		File dataDir = SystemMonitorConstants.SYSTEMMONITOR_BACKUPFOLDER;

		if (dataDir.exists() && dataDir.isDirectory() && dataDir.listFiles().length > 0) {
			for (int i = 0; i < dataDir.listFiles().length; i++) {
				File backup = dataDir.listFiles()[i];

				if (backup.getPath().endsWith(".json")) {
					JSONArray data = reArrangeDataValuesJSONArrayToIncludeOrgUnitInEachEntry(
							new JSONObject(readFileToString(backup)));
					for (int j = 0; j < data.length(); j++) {
						storedDataValues.put(data.getJSONObject(j));
					}
				}
			}
		}
		if (currentDataValues.length() > 0) {
			for (int i = 0; i < currentDataValues.length(); i++) {
				json.put(currentDataValues.getJSONObject(i));
			}
		}
		if (storedDataValues.length() > 0) {
			for (int i = 0; i < storedDataValues.length(); i++) {
				json.put(storedDataValues.getJSONObject(i));
			}
		}

		return json;
	}

	private JSONArray reArrangeDataValuesJSONArrayToIncludeOrgUnitInEachEntry(JSONObject dataValuesJson) {
		JSONArray dataValues = dataValuesJson.getJSONArray("dataValues");
		JSONArray newDataValues = dataValues;
		String dhisOrgUnitUid = null;
		if (dataValuesJson.has("orgUnit"))
			dhisOrgUnitUid = dataValuesJson.getString("orgUnit");

		if (StringUtils.isNotBlank(dhisOrgUnitUid)) {
			if (dataValues != null) {
				newDataValues = new JSONArray();
				for (int i = 0; i < dataValues.length(); i++) {
					JSONObject json = new JSONObject();

					json.put("orgUnit", dhisOrgUnitUid);
					json.put("dataElement", dataValues.getJSONObject(i).getString("dataElement"));
					json.put("period", dataValues.getJSONObject(i).getString("period"));
					json.put("value", dataValues.getJSONObject(i).get("value"));
					newDataValues.put(json);
				}
			}
		}

		return newDataValues;
	}

	@Override
	public Integer rwandaGetTotalActivePatients_AtleastEightMonthsARVTreatment() {
		return getDao().rwandaGetTotalActivePatients_AtleastEightMonthsARVTreatment();
	}

	@Override
	public Integer rwandaGetTotalActivePatients_AtleastTwentyMonthsARVTreatment() {
		return getDao().rwandaGetTotalActivePatients_AtleastTwentyMonthsARVTreatment();
	}

	@Override
	public Integer rwandaGetTotalActivePatients_AtleastEightMonthsARVTreatment_AtleastOneViralLoad_InEMR() {
		return getDao().rwandaGetTotalActivePatients_AtleastEightMonthsARVTreatment_AtleastOneViralLoad_InEMR();
	}

	@Override
	public Integer rwandaGetTotalActivePatients_AtleastEightMonthsARVTreatment_AtleastOneCD4Count_InEMR() {
		return getDao().rwandaGetTotalActivePatients_AtleastEightMonthsARVTreatment_AtleastOneCD4Count_InEMR();
	}

	@Override
	public Integer rwandaGetTotalActivePatients_AtleastTwentyMonthsARVTreatment_AtleastOneViralLoad_LastYear() {
		return getDao().rwandaGetTotalActivePatients_AtleastTwentyMonthsARVTreatment_AtleastOneViralLoad_LastYear();
	}

	@Override
	public Integer rwandaGetTotalActivePatients_AtleastTwentyMonthsARVTreatment_AtleastOneCD4Count_LastYear() {
		return getDao().rwandaGetTotalActivePatients_AtleastTwentyMonthsARVTreatment_AtleastOneCD4Count_LastYear();
	}

	@Override
	public Integer fetchTotalEncountersCountPreviousWeek() {
		return getDao().fetchTotalEncountersCountPreviousWeek();
	}

	@Override
	public Integer fetchTotalEncountersCountPreviousMonth() {
		return getDao().fetchTotalEncountersCountPreviousMonth();
	}

	@Override
	public Integer fetchTotalObservationsCountPreviousWeek() {
		return getDao().fetchTotalObservationsCountPreviousWeek();
	}

	@Override
	public Integer fetchTotalObservationsCountPreviousMonth() {
		return getDao().fetchTotalObservationsCountPreviousMonth();
	}

	public Integer fetchTotalPatientsCountPreviousWeek() {
		return getDao().fetchTotalPatientsCountPreviousWeek();
	}

	public Integer fetchTotalPatientsCountPreviousMonth() {
		return getDao().fetchTotalPatientsCountPreviousMonth();
	}

	@Override
	public String updatePreviouslySubmittedOrMissedSMTData() {
		return getDao().updatePreviouslySubmittedOrMissedSMTData();
	}

	@Override
	public Integer numberofBackedUpDataFiles() {
		return getDao().numberofBackedUpDataFiles();
	}

	@Override
	public Integer basicOpenMRSObjectCount(@SuppressWarnings("rawtypes") Class clazz) {
		return getDao().basicOpenMRSObjectCount(clazz);
	}

	@Override
	public Integer basicOpenMRSObjectCountCreatedLast24Hours(@SuppressWarnings("rawtypes") Class clazz) {
		return getDao().basicOpenMRSObjectCountCreatedLast24Hours(clazz);
	}
}