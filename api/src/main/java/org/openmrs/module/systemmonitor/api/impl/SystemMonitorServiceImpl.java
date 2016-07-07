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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Concept;
import org.openmrs.GlobalProperty;
import org.openmrs.Person;
import org.openmrs.Program;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.Module;
import org.openmrs.module.systemmonitor.ConfigurableGlobalProperties;
import org.openmrs.module.systemmonitor.SystemMonitorConstants;
import org.openmrs.module.systemmonitor.api.SystemMonitorService;
import org.openmrs.module.systemmonitor.api.db.SystemMonitorDAO;
import org.openmrs.module.systemmonitor.curl.CurlEmulator;
import org.openmrs.module.systemmonitor.export.DHISGenerateDataValueSetSchemas;
import org.openmrs.module.systemmonitor.mapping.DHISMapping;

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
		return dao.getYesterdayStartDate();
	}

	@Override
	public String getYesterdayEndDate() {
		return dao.getYesterdayEndDate();
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
	public Integer rwandaPIHEMTGetTotalEncountersForYesterday() {
		return dao.rwandaPIHEMTGetTotalEncountersForYesterday();
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
		return dao.unitTestingTheseMetrics();
	}

	@Override
	public JSONObject getDataToPushToDHIS() {
		return DHISGenerateDataValueSetSchemas.generateRwandaSPHEMTDHISDataValueSets().length() > 0
				? DHISGenerateDataValueSetSchemas.generateRwandaSPHEMTDHISDataValueSets().getJSONObject("toBePushed")
				: null;
	}

	@Override
	public JSONObject pushMonitoredDataToDHIS() {
		JSONObject dataToBePushed = getDataToPushToDHIS();

		String dhisUserName = Context.getAdministrationService()
				.getGlobalProperty(ConfigurableGlobalProperties.DHIS_USERNAME);
		String dhisPassword = Context.getAdministrationService()
				.getGlobalProperty(ConfigurableGlobalProperties.DHIS_PASSWORD);
		String dhisPostUrl = (Context.getAdministrationService()
				.getGlobalProperty(ConfigurableGlobalProperties.DHISAPI_URL)) != null
						? Context.getAdministrationService().getGlobalProperty(ConfigurableGlobalProperties.DHISAPI_URL)
								+ SystemMonitorConstants.DHIS_API_DATAVALUES_URL
						: null;
		JSONObject response = null;
		File backupFile = SystemMonitorConstants.SYSTEMMONITOR_BACKUPFILE;

		if (StringUtils.isNotBlank(dhisPostUrl) && StringUtils.isNotBlank(dhisPassword)
				&& StringUtils.isNotBlank(dhisUserName) && dataToBePushed != null) {
			try {
				response = CurlEmulator.post(dhisPostUrl, dataToBePushed, dhisUserName, dhisPassword);
				if (response != null) {
					logCurlEmulatorPostResponse(response.toString());
					if (backupFile.exists()) {
						backupFile.delete();
					}
					pushPreviouslyFailedDataWhenOutOfInternet();
				} else {
					backupSystemMonitorDataToPush(dataToBePushed);
				}

			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	private void pushPreviouslyFailedDataWhenOutOfInternet() {
		File dataDir = SystemMonitorConstants.SYSTEMMONITOR_BACKUPFOLDER;
		String dhisUserName = Context.getAdministrationService()
				.getGlobalProperty(ConfigurableGlobalProperties.DHIS_USERNAME);
		String dhisPassword = Context.getAdministrationService()
				.getGlobalProperty(ConfigurableGlobalProperties.DHIS_PASSWORD);
		String dhisPostUrl = (Context.getAdministrationService()
				.getGlobalProperty(ConfigurableGlobalProperties.DHISAPI_URL)) != null
						? Context.getAdministrationService().getGlobalProperty(ConfigurableGlobalProperties.DHISAPI_URL)
								+ SystemMonitorConstants.DHIS_API_DATAVALUES_URL
						: null;
		JSONObject response = null;
		if (dataDir.exists() && dataDir.isDirectory() && dataDir.listFiles().length > 0) {
			for (int i = 0; i < dataDir.listFiles().length; i++) {
				File backup = dataDir.listFiles()[i];

				if (backup.getPath().endsWith(".json")) {
					JSONObject data = new JSONObject(readFileToString(backup));

					try {
						response = CurlEmulator.post(dhisPostUrl, data, dhisUserName, dhisPassword);
						if (response != null) {
							logCurlEmulatorPostResponse(response.toString());
							backup.delete();
						}

					} catch (UnknownHostException e) {
						e.printStackTrace();
					} catch (SocketException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public void backupSystemMonitorDataToPush(JSONObject dhisValues) {
		if (dhisValues != null && dhisValues.length() > 0) {
			File backupFolder = SystemMonitorConstants.SYSTEMMONITOR_BACKUPFOLDER;
			File backupFile = SystemMonitorConstants.SYSTEMMONITOR_BACKUPFILE;

			try {
				if (!backupFolder.exists()) {
					backupFolder.mkdirs();
				}
				if (!backupFile.exists()) {
					backupFile.createNewFile();
				}

				FileWriter fileWritter = new FileWriter(backupFile);
				BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
				bufferWritter.write(dhisValues.toString());
				bufferWritter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void logCurlEmulatorPostResponse(String response) {
		if (response != null && response.length() > 0) {
			File logsFolder = SystemMonitorConstants.SYSTEMMONITOR_LOGSFOLDER;
			File logsFile = SystemMonitorConstants.SYSTEMMONITOR_LOGSFILE;

			try {
				if (!logsFolder.exists()) {
					logsFolder.mkdirs();
				}
				if (!logsFile.exists()) {
					logsFile.createNewFile();
				}

				FileWriter fileWritter = new FileWriter(logsFile, true);
				BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
				bufferWritter.write(new Date().toString() + "\n" + response + "\n\n");
				bufferWritter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
		String string = "";
		if (file != null && file.isFile()) {
			BufferedReader br = null;

			try {
				String line;
				br = new BufferedReader(new FileReader(file));
				while ((line = br.readLine()) != null) {
					string += line + "\n";
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (br != null)
						br.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return string;
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
		updateDHISDataElementsOrOrgUnits(true);
	}

	private void updateOrgUnitsFromDHISConfiguredRemoteInstance() throws UnknownHostException {
		updateDHISDataElementsOrOrgUnits(false);
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

	private void updateDHISDataElementsOrOrgUnits(boolean isDataelementUpdateIfTrueOrOrgUnitUpdateIfFalse)
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
									+ SystemMonitorConstants.DHIS_API_DATAELEMENTS_URL
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

	@Override
	public Long getOpenMRSSystemUpTime() {
		return ((System.currentTimeMillis() / 1000) - getNumberOfSecondsAtOpenMRSStartup()) / 60;
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
		return dao.getTotalCD4CountTestsEver();
	}

	@Override
	public Integer getTotalCD4CountTestsLastSixMonths() {
		return dao.getTotalCD4CountTestsLastSixMonths();
	}

	@Override
	public Integer getTotalCD4CountTestsLastYear() {
		return dao.getTotalCD4CountTestsLastYear();
	}

	@Override
	public Program getHIVProgram() {
		return dao.getHIVProgram();
	}

	@Override
	public Concept getReasonForExitingCareConcept() {
		return dao.getReasonForExitingCareConcept();
	}

	@Override
	public Concept getCD4CountConcept() {
		return dao.getCD4CountConcept();
	}

	@Override
	public Concept getViralLoadsConcept() {
		return dao.getViralLoadsConcept();
	}

	@Override
	public Concept getARVDrugsConceptSet() {
		return dao.getARVDrugsConceptSet();
	}
}