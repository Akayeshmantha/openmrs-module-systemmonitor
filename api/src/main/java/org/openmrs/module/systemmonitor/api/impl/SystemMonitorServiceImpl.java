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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.openmrs.module.systemmonitor.export.DHISGenerateDataValueSetSchemas;
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
			if (!SystemMonitorConstants.SYSTEMMONITOR_FINAL_MAPPINGFILE.exists()
					|| (SystemMonitorConstants.SYSTEMMONITOR_FINAL_MAPPINGFILE.exists() && !addedLocalDHISMappings())) {
				try {
					FileUtils.copyFile(mappingsFile, SystemMonitorConstants.SYSTEMMONITOR_FINAL_MAPPINGFILE);
					FileUtils.copyFile(dataElementsFile,
							SystemMonitorConstants.SYSTEMMONITOR_DATAELEMENTSMETADATA_FILE);
				} catch (FileNotFoundException e) {
					if (e.getMessage().indexOf("Source") > -1
							|| e.getMessage().indexOf(SystemMonitorConstants.SYSTEMMONITOR_MAPPING_FILENAME) > -1)
						WindowsResourceFileNotFoundHack.addMappingsFileToSystemMonitorDataDirectory();
					if (e.getMessage().indexOf("Source") > -1 || e.getMessage()
							.indexOf(SystemMonitorConstants.SYSTEMMONITOR_DATAELEMENTSMETADATA_FILENAME) > -1)
						WindowsResourceFileNotFoundHack.addDHISDataElementsMetadataToSystemMonitorDataDirectory();
				}
			}
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
		updatePreviouslySubmittedSMTData();
		return runSMTEvaluatorAndLogOrPushData();
	}

	private JSONObject runSMTEvaluatorAndLogOrPushData() {
		JSONObject response = null;
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
		File backupFile = getSystemBackUpFile();

		if (StringUtils.isNotBlank(dhisPostUrl) && StringUtils.isNotBlank(dhisPassword)
				&& StringUtils.isNotBlank(dhisUserName) && dataToBePushed != null && dataToBePushed.length() > 0
				&& dataToBePushed.getJSONArray("dataValues") != null
				&& dataToBePushed.getJSONArray("dataValues").length() > 0) {
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

				if (backup.getPath().startsWith(SystemMonitorConstants.SYSTEMMONITOR_DATA_PREFIX)
						&& backup.getPath().endsWith(".json") && backup.length() > 0) {
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
				if (backup.length() == 0)
					backup.delete();
			}
		}
	}

	private File getSystemLogFile() {
		return new File(SystemMonitorConstants.SYSTEMMONITOR_LOGS_DIRECTORYPATH + File.separator
				+ SystemMonitorConstants.SYSTEMMONITOR_LOGS_PREFIX
				+ new SimpleDateFormat("yyyyMMdd").format(getEvaluationAndReportingDate()) + ".log");
	}

	private File getSystemBackUpFile() {
		return new File(SystemMonitorConstants.SYSTEMMONITOR_DATA_DIRECTORYPATH + File.separator
				+ SystemMonitorConstants.SYSTEMMONITOR_DATA_PREFIX
				+ new SimpleDateFormat("yyyyMMdd").format(getEvaluationAndReportingDate()) + ".json");
	}

	@Override
	public void backupSystemMonitorDataToPush(JSONObject dhisValues) {
		if (dhisValues != null && dhisValues.length() > 0) {
			File backupFolder = SystemMonitorConstants.SYSTEMMONITOR_BACKUPFOLDER;
			File backupFile = getSystemBackUpFile();

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
			File logsFile = getSystemLogFile();

			try {
				if (!logsFolder.exists()) {
					logsFolder.mkdirs();
				}
				if (!logsFile.exists()) {
					logsFile.createNewFile();
				}

				FileWriter fileWritter = new FileWriter(logsFile, true);
				BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
				bufferWritter.write(getEvaluationAndReportingDate().toString() + "\n" + response + "\n\n");
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

	@Override
	public Integer rwandaPIHEMTGetTotalObservationsForYesterday() {
		return dao.rwandaPIHEMTGetTotalObservationsForYesterday();
	}

	@Override
	public Integer rwandaPIHEMTGetTotalUsersForYesterday() {
		return dao.rwandaPIHEMTGetTotalUsersForYesterday();
	}

	@Override
	public Integer getTotalViralLoadTestsForYesterday() {
		return dao.getTotalViralLoadTestsForYesterday();
	}

	@Override
	public Integer getTotalCD4CountTestsForYesterday() {
		return dao.getTotalCD4CountTestsForYesterday();
	}

	@Override
	public Integer rwandaPIHEMTGetTotalAdultInitialEncountersForYesterday() {
		return dao.rwandaPIHEMTGetTotalAdultInitialEncountersForYesterday();
	}

	@Override
	public Integer rwandaPIHEMTGetTotalAdultReturnEncountersForYesterday() {
		return dao.rwandaPIHEMTGetTotalAdultReturnEncountersForYesterday();
	}

	@Override
	public Integer rwandaPIHEMTGetTotalPedsInitialEncountersForYesterday() {
		return dao.rwandaPIHEMTGetTotalPedsInitialEncountersForYesterday();
	}

	@Override
	public Integer rwandaPIHEMTGetTotalPedsReturnEncountersForYesterday() {
		return dao.rwandaPIHEMTGetTotalPedsReturnEncountersForYesterday();
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
		return dao.rwandaPIHEMTGetTotalNewPatientsForYesterday();
	}

	@Override
	public Integer rwandaPIHEMTGetTotalActivePatientsForYesterday() {
		return dao.rwandaPIHEMTGetTotalActivePatientsForYesterday();
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
		return dao.getEvaluationAndReportingDate();
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
		TaskDefinition localCleanerTask = getTaskByClass(SystemMonitorConstants.SCHEDULER_TASKCLASS_LOCALCLEANER);
		TaskDefinition pushToDHISTask = getTaskByClass(SystemMonitorConstants.SCHEDULER_TASKCLASS_PUSH);
		TaskDefinition updateLocalDHISMetadataTask = getTaskByClass(
				SystemMonitorConstants.SCHEDULER_TASKCLASS_UPDATESHISMETADATA);
		try {
			if (localCleanerTask != null)
				Context.getSchedulerService().scheduleTask(localCleanerTask);
			if (pushToDHISTask != null)
				Context.getSchedulerService().scheduleTask(pushToDHISTask);
			if (updateLocalDHISMetadataTask != null)
				Context.getSchedulerService().scheduleTask(updateLocalDHISMetadataTask);
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (APIAuthenticationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public TaskDefinition getTaskByClass(String taskClass) throws DAOException {
		return dao.getTaskByClass(taskClass);
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

	/**
	 * this must only run once for Sites where SMT had been installed already,
	 * this functionality can only run until 31st/march/2016 which means by this
	 * date all Sites must have been upgraded
	 */
	private void updatePreviouslySubmittedSMTData() {
		try {
			SimpleDateFormat sdf = dao.getSdf() != null ? dao.getSdf() : new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date supportedUntil = sdf.parse(Context.getAdministrationService().getGlobalProperty("systemmonitor.evaluationAndReportingSToday_supportedUntil"));
			Calendar date = Calendar.getInstance(Context.getLocale());
			Calendar today = Calendar.getInstance(Context.getLocale());
			GlobalProperty evalDateGp = Context.getAdministrationService()
					.getGlobalPropertyObject(ConfigurableGlobalProperties.EVALUATION_AND_REPORTING_DATE);
			File smtBackUpDirectory = SystemMonitorConstants.SYSTEMMONITOR_DIRECTORY;
			if (evalDateGp != null) {
				String dateStr = evalDateGp.getPropertyValue();

				if (supportedUntil != null && smtBackUpDirectory != null && StringUtils.isNotBlank(dateStr)
						&& smtBackUpDirectory.exists() && smtBackUpDirectory.isDirectory()
						&& supportedUntil.after(today.getTime())) {

					date.setTime(sdf.parse(dateStr));
					while (today.after(date)) {
						// eliminate weekend days
						if (date.get(Calendar.DAY_OF_WEEK) != 1 && date.get(Calendar.DAY_OF_WEEK) != 7) {
							runSMTEvaluatorAndLogOrPushData();
						}
						date.add(Calendar.DAY_OF_YEAR, 1);
						dateStr = sdf.format(date.getTime());
						evalDateGp.setPropertyValue(dateStr);
						Context.getAdministrationService().saveGlobalProperty(evalDateGp);
					}
					// finally
					evalDateGp.setPropertyValue("");
					Context.getAdministrationService().saveGlobalProperty(evalDateGp);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Integer rwandaGetTotalActivePatients_AtleastEightMonthsARVTreatment() {
		return dao.rwandaGetTotalActivePatients_AtleastEightMonthsARVTreatment();
	}

	@Override
	public Integer rwandaGetTotalActivePatients_AtleastTwentyMonthsARVTreatment() {
		return dao.rwandaGetTotalActivePatients_AtleastTwentyMonthsARVTreatment();
	}

	@Override
	public Integer rwandaGetTotalActivePatients_AtleastEightMonthsARVTreatment_AtleastOneViralLoad_InEMR() {
		return dao.rwandaGetTotalActivePatients_AtleastEightMonthsARVTreatment_AtleastOneViralLoad_InEMR();
	}

	@Override
	public Integer rwandaGetTotalActivePatients_AtleastEightMonthsARVTreatment_AtleastOneCD4Count_InEMR() {
		return dao.rwandaGetTotalActivePatients_AtleastEightMonthsARVTreatment_AtleastOneCD4Count_InEMR();
	}

	@Override
	public Integer rwandaGetTotalActivePatients_AtleastTwentyMonthsARVTreatment_AtleastOneViralLoad_LastYear() {
		return dao.rwandaGetTotalActivePatients_AtleastTwentyMonthsARVTreatment_AtleastOneViralLoad_LastYear();
	}

	@Override
	public Integer rwandaGetTotalActivePatients_AtleastTwentyMonthsARVTreatment_AtleastOneCD4Count_LastYear() {
		return dao.rwandaGetTotalActivePatients_AtleastTwentyMonthsARVTreatment_AtleastOneCD4Count_LastYear();
	}

	@Override
	public Integer fetchTotalEncountersCountPreviousWeek() {
		return dao.fetchTotalEncountersCountPreviousWeek();
	}

	@Override
	public Integer fetchTotalEncountersCountPreviousMonth() {
		return dao.fetchTotalEncountersCountPreviousMonth();
	}

	@Override
	public Integer fetchTotalObservationsCountPreviousWeek() {
		return dao.fetchTotalObservationsCountPreviousWeek();
	}

	@Override
	public Integer fetchTotalObservationsCountPreviousMonth() {
		return dao.fetchTotalObservationsCountPreviousMonth();
	}

	public Integer fetchTotalPatientsCountPreviousWeek() {
		return dao.fetchTotalPatientsCountPreviousWeek();
	}

	public Integer fetchTotalPatientsCountPreviousMonth() {
		return dao.fetchTotalPatientsCountPreviousMonth();
	}
}