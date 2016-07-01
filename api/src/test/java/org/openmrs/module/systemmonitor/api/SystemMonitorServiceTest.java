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

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.Encounter;
import org.openmrs.api.EncounterService;
import org.openmrs.api.LocationService;
import org.openmrs.api.PatientService;
import org.openmrs.api.VisitService;
import org.openmrs.api.context.Context;
import org.openmrs.module.ModuleConstants;
import org.openmrs.module.ModuleUtil;
import org.openmrs.module.systemmonitor.SystemMonitorConstants;
import org.openmrs.module.systemmonitor.distributions.RwandaSPHStudyEMT;
import org.openmrs.module.systemmonitor.indicators.OSAndHardwareIndicators;
import org.openmrs.test.BaseModuleContextSensitiveTest;

import junit.framework.Assert;

/**
 * Tests {@link ${SystemMonitorService}}.
 */

public class SystemMonitorServiceTest extends BaseModuleContextSensitiveTest {

	EncounterService encounterService;
	PatientService patientService;
	VisitService visitService;
	LocationService locationService;
	Calendar today;
	private SystemMonitorService systemMonitorService;

	@Override
	public Properties getRuntimeProperties() {
		Properties props = super.getRuntimeProperties();

		props.setProperty(ModuleConstants.RUNTIMEPROPERTY_MODULE_LIST_TO_LOAD,
				"org/openmrs/module/include/logic-0.2.omod org/openmrs/module/include/dssmodule-1.44.omod systemmonitor-1.0-SNAPSHOT.omod");

		return props;
	}

	@Before
	public void init() {
		encounterService = Context.getEncounterService();
		patientService = Context.getPatientService();
		visitService = Context.getVisitService();
		locationService = Context.getLocationService();
		today = Calendar.getInstance(Context.getLocale());
		systemMonitorService = Context.getService(SystemMonitorService.class);

		ModuleUtil.startup(getRuntimeProperties());
	}

	@Test
	public void shouldSetupContext() {
		assertNotNull(systemMonitorService);
	}

	@Test
	public void testThisAndLastDayWeekMonthYearDateCalculations() {
		System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		System.out.println("Today: " + (systemMonitorService.getToday()));
		System.out.println("Start of this Week: " + (systemMonitorService.getThisWeekStartDate()));
		System.out.println("End of this week: " + (systemMonitorService.getThisWeekEndDate()));
		System.out.println("Start of last week: " + (systemMonitorService.getLastWeekStartDate()));
		System.out.println("End of last week: " + (systemMonitorService.getLastWeekEndDate()));
		System.out.println("Start of This Month: " + (systemMonitorService.getThisMonthStartDate()));
		System.out.println("End of This Month: " + (systemMonitorService.getThisMonthEndDate()));
		System.out.println("Start of Last Month: " + (systemMonitorService.getLastMonthStartDate()));
		System.out.println("End of Last Month: " + (systemMonitorService.getLastMonthEndDate()));
		System.out.println("Start of This Year: " + (systemMonitorService.getThisYearStartDate()));
		System.out.println("End of This Year: " + (systemMonitorService.getThisYearEndDate()));
		System.out.println("Start of Last Year: " + (systemMonitorService.getLastYearStartDate()));
		System.out.println("End of Last Year: " + (systemMonitorService.getLastYearEndDate()));
		System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	}

	private Encounter buildEncounter(Date dateCreated, Date dateUpdated) {
		Encounter enc = new Encounter();

		enc.setLocation(locationService.getLocation(1));

		enc.setEncounterType(encounterService.getEncounterType(1));
		enc.setEncounterDatetime(new Date());
		enc.setPatient(patientService.getAllPatients().get(patientService.getAllPatients().size() - 1));
		enc.addProvider(encounterService.getEncounterRole(1), Context.getProviderService().getProvider(1));
		if (dateCreated != null)
			enc.setDateCreated(dateCreated);
		if (dateUpdated != null)
			enc.setDateChanged(dateUpdated);

		return enc;
	}

	@Ignore
	public void test_totalOpenMRSObjectsCountingOrIndicators() {
		Assert.assertEquals(Integer.valueOf(0), systemMonitorService.getTotalEncountersToday(false));
		Assert.assertEquals(Integer.valueOf(0), systemMonitorService.getTotalEncountersThisWeek(false));
		Assert.assertEquals(Integer.valueOf(0), systemMonitorService.getTotalEncountersThisMonth(false));

		Encounter savedEnc = encounterService.saveEncounter(buildEncounter(null, null));
		Calendar lastWeek = (Calendar) today.clone();
		Calendar lastMonth = (Calendar) today.clone();
		Calendar lastYear = (Calendar) today.clone();

		lastWeek.add(Calendar.WEEK_OF_MONTH, -1);
		lastMonth.add(Calendar.MONTH, -1);
		lastYear.add(Calendar.YEAR, -1);

		Assert.assertNotNull(savedEnc);
		Assert.assertEquals(Integer.valueOf(1), systemMonitorService.getTotalEncountersToday(false));
		Assert.assertEquals(Integer.valueOf(0), systemMonitorService.getTotalEncountersLastYear(false));

		encounterService.saveEncounter(buildEncounter(lastWeek.getTime(), null));// adds
																					// to
																					// this/current
																					// //
																					// month
		encounterService.saveEncounter(buildEncounter(lastMonth.getTime(), null));
		encounterService.saveEncounter(buildEncounter(lastMonth.getTime(), null));
		encounterService.saveEncounter(buildEncounter(lastYear.getTime(), null));

		Assert.assertEquals(Integer.valueOf(1), systemMonitorService.getTotalEncountersThisWeek(false));
		Assert.assertEquals(Integer.valueOf(1), systemMonitorService.getTotalEncountersToday(false));
		Assert.assertEquals(Integer.valueOf(1), systemMonitorService.getTotalEncountersLastWeek(false));
		Assert.assertEquals(Integer.valueOf(2), systemMonitorService.getTotalEncountersLastMonth(false));
		Assert.assertEquals(Integer.valueOf(1), systemMonitorService.getTotalEncountersLastWeek(false));
		Assert.assertEquals(Integer.valueOf(1), systemMonitorService.getTotalEncountersLastYear(false));
		Assert.assertEquals(Integer.valueOf(2), systemMonitorService.getTotalEncountersThisMonth(false));
		Assert.assertNull(encounterService.getEncounterByUuid(savedEnc.getUuid()).getDateChanged());
		int updatedThisYear = systemMonitorService.getTotalEncountersThisYear(false);

		// updating savedEnc to update last updated
		Encounter updatedEnc = encounterService.getEncounter(3);
		encounterService.updateEncounter(updatedEnc);// adds to today, this
														// week, month and year
		updatedEnc.setLocation(locationService.getLocation(2));

		Assert.assertNotNull(encounterService.getEncounterByUuid(updatedEnc.getUuid()).getDateChanged());
		Assert.assertEquals(Integer.valueOf(2), systemMonitorService.getTotalEncountersThisWeek(false));
		Assert.assertEquals(Integer.valueOf(2), systemMonitorService.getTotalEncountersToday(false));
		Assert.assertEquals(Integer.valueOf(3), systemMonitorService.getTotalEncountersThisMonth(false));
		Assert.assertEquals(Integer.valueOf(updatedThisYear + 1),
				systemMonitorService.getTotalEncountersThisYear(false));

		System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		System.out.println("Today: Total Encounters Retired: " + systemMonitorService.getTotalEncountersToday(true));
		System.out
				.println("Today: Total Encounters Non Retired: " + systemMonitorService.getTotalEncountersToday(false));

		System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	}

	@Ignore
	public void test_transferMappingsFileToDataDirectory() {
		systemMonitorService.transferDHISMappingsAndMetadataFileToDataDirectory();
	}

	@Ignore
	public void test_generatingDHISJsonAndInstallationOfSystemMonitorModule() {
		RwandaSPHStudyEMT emt = new RwandaSPHStudyEMT();

		// systemMonitorService.transferDHISMappingsAndMetadataFileToDataDirectory();

		System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		System.out.println("Installed Modules: " + systemMonitorService.getInstalledModules().toString());
		System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		System.out.println("DHIS Generated ValueSet :\n" + emt.generatedDHISDataValueSetJSONString().toString());
		System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	}

	@Ignore
	public void test_hibernateCriteriaRestrictions() throws IOException {
		System.out.println("Total Count: " + systemMonitorService.unitTestingTheseMetrics());
	}

	@Test
	public void testGetIp() {
		System.out.println("IP ADDR: " + OSAndHardwareIndicators.getIpAddress());
	}

	@Ignore
	public void testPushingDataToDHIS() {
		JSONObject serverResponse = Context.getService(SystemMonitorService.class).pushMonitoredDataToDHIS();
		System.out.print("serverResponseString::::::::::" + serverResponse);

		if (serverResponse != null) {
			System.out.println("SERVER STATUS: " + serverResponse.getString("status"));
		}
	}
	
	@Test
	public void test_matchRequiredVersions() {
		System.out.println("Current OpenMRS Version: " + SystemMonitorConstants.OPENMRS_VERSION + ", is it greater than 1.9? " + ModuleUtil.matchRequiredVersions(SystemMonitorConstants.OPENMRS_VERSION, "1.9"));
	}
}
