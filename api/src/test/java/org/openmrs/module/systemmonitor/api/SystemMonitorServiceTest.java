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

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.Encounter;
import org.openmrs.api.EncounterService;
import org.openmrs.api.LocationService;
import org.openmrs.api.PatientService;
import org.openmrs.api.VisitService;
import org.openmrs.api.context.Context;
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

	@Before
	public void init() {
		encounterService = Context.getEncounterService();
		patientService = Context.getPatientService();
		visitService = Context.getVisitService();
		locationService = Context.getLocationService();
		today = Calendar.getInstance(Context.getLocale());
	}

	@Test
	public void shouldSetupContext() {
		assertNotNull(Context.getService(SystemMonitorService.class));
	}

	@Test
	public void testThisAndLastDayWeekMonthYearDateCalculations() {
		System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		System.out.println("Today: " + (Context.getService(SystemMonitorService.class).getToday()));
		System.out.println(
				"Start of this Week: " + (Context.getService(SystemMonitorService.class).getThisWeekStartDate()));
		System.out
				.println("End of this week: " + (Context.getService(SystemMonitorService.class).getThisWeekEndDate()));
		System.out.println(
				"Start of last week: " + (Context.getService(SystemMonitorService.class).getLastWeekStartDate()));
		System.out
				.println("End of last week: " + (Context.getService(SystemMonitorService.class).getLastWeekEndDate()));
		System.out.println(
				"Start of This Month: " + (Context.getService(SystemMonitorService.class).getThisMonthStartDate()));
		System.out.println(
				"End of This Month: " + (Context.getService(SystemMonitorService.class).getThisMonthEndDate()));
		System.out.println(
				"Start of Last Month: " + (Context.getService(SystemMonitorService.class).getLastMonthStartDate()));
		System.out.println(
				"End of Last Month: " + (Context.getService(SystemMonitorService.class).getLastMonthEndDate()));
		System.out.println(
				"Start of This Year: " + (Context.getService(SystemMonitorService.class).getThisYearStartDate()));
		System.out
				.println("End of This Year: " + (Context.getService(SystemMonitorService.class).getThisYearEndDate()));
		System.out.println(
				"Start of Last Year: " + (Context.getService(SystemMonitorService.class).getLastYearStartDate()));
		System.out
				.println("End of Last Year: " + (Context.getService(SystemMonitorService.class).getLastYearEndDate()));
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

	@Test
	public void test_totalOpenMRSObjectsCountingOrIndicators() {
		Assert.assertEquals(Integer.valueOf(0),
				Context.getService(SystemMonitorService.class).getTotalEncountersToday(false));
		Assert.assertEquals(Integer.valueOf(0),
				Context.getService(SystemMonitorService.class).getTotalEncountersThisMonth(false));

		Encounter savedEnc = encounterService.saveEncounter(buildEncounter(null, null));
		Calendar lastWeek = (Calendar) today.clone();
		Calendar lastMonth = (Calendar) today.clone();
		Calendar lastYear = (Calendar) today.clone();

		lastWeek.add(Calendar.WEEK_OF_MONTH, -1);
		lastMonth.add(Calendar.MONTH, -1);
		lastYear.add(Calendar.YEAR, -1);

		Assert.assertNotNull(savedEnc);
		Assert.assertEquals(Integer.valueOf(1),
				Context.getService(SystemMonitorService.class).getTotalEncountersToday(false));
		Assert.assertEquals(Integer.valueOf(0),
				Context.getService(SystemMonitorService.class).getTotalEncountersLastYear(false));

		encounterService.saveEncounter(buildEncounter(lastWeek.getTime(), null));// adds
																					// to
																					// this/current
																					// month
		encounterService.saveEncounter(buildEncounter(lastMonth.getTime(), null));
		encounterService.saveEncounter(buildEncounter(lastMonth.getTime(), null));
		encounterService.saveEncounter(buildEncounter(lastYear.getTime(), null));

		Assert.assertEquals(Integer.valueOf(1),
				Context.getService(SystemMonitorService.class).getTotalEncountersThisWeek(false));
		Assert.assertEquals(Integer.valueOf(1),
				Context.getService(SystemMonitorService.class).getTotalEncountersToday(false));
		Assert.assertEquals(Integer.valueOf(1),
				Context.getService(SystemMonitorService.class).getTotalEncountersLastWeek(false));
		Assert.assertEquals(Integer.valueOf(2),
				Context.getService(SystemMonitorService.class).getTotalEncountersLastMonth(false));
		Assert.assertEquals(Integer.valueOf(1),
				Context.getService(SystemMonitorService.class).getTotalEncountersLastWeek(false));
		Assert.assertEquals(Integer.valueOf(1),
				Context.getService(SystemMonitorService.class).getTotalEncountersLastYear(false));
		Assert.assertEquals(Integer.valueOf(2),
				Context.getService(SystemMonitorService.class).getTotalEncountersThisMonth(false));
		Assert.assertNull(encounterService.getEncounterByUuid(savedEnc.getUuid()).getDateChanged());
		int updatedThisYear = Context.getService(SystemMonitorService.class).getTotalEncountersThisYear(false);

		// updating savedEnc to update last updated
		Encounter updatedEnc = encounterService.getEncounter(3);
		encounterService.updateEncounter(updatedEnc);// adds to today, this
														// week, month and year
		updatedEnc.setLocation(locationService.getLocation(2));

		Assert.assertNotNull(encounterService.getEncounterByUuid(updatedEnc.getUuid()).getDateChanged());
		Assert.assertEquals(Integer.valueOf(2),
				Context.getService(SystemMonitorService.class).getTotalEncountersThisWeek(false));
		Assert.assertEquals(Integer.valueOf(2),
				Context.getService(SystemMonitorService.class).getTotalEncountersToday(false));
		Assert.assertEquals(Integer.valueOf(3),
				Context.getService(SystemMonitorService.class).getTotalEncountersThisMonth(false));
		Assert.assertEquals(Integer.valueOf(updatedThisYear + 1),
				Context.getService(SystemMonitorService.class).getTotalEncountersThisYear(false));

		System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		System.out.println("Today: Total Encounters Retired: "
				+ Context.getService(SystemMonitorService.class).getTotalEncountersToday(true));
		System.out.println("Today: Total Encounters Non Retired: "
				+ Context.getService(SystemMonitorService.class).getTotalEncountersToday(false));

		System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	}
	
	@Ignore
	public void test_transferMappingsFileToDataDirectory() {
		Context.getService(SystemMonitorService.class).transferMappingsFileToDataDirectory();
	}
}
