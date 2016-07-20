package org.openmrs.module.systemmonitor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.GlobalProperty;
import org.openmrs.api.context.Context;
import org.openmrs.module.ModuleUtil;

/**
 * Reads and persists configurations which are supported as global properties,
 * should only be used at: configurations controller layer
 */
public class ConfigureGPs {
	private GlobalProperty viralLoadConceptIdGp;

	private GlobalProperty cd4CountConceptIdGp;

	private GlobalProperty reasonForExistingCareConceptIdGp;

	private GlobalProperty hivProgramIdGp;

	private GlobalProperty siteIdGp;

	private GlobalProperty dhisAPIRootUrlGp;

	private GlobalProperty dhisUsernameGp;

	private GlobalProperty dhisPasswordGp;

	private GlobalProperty openingHourGp;

	private GlobalProperty openingDaysGp;

	private GlobalProperty closingHourGp;

	private GlobalProperty schedulerUsernameGp;

	private GlobalProperty schedulerPasswordGp;

	private GlobalProperty adultInitialEncounterTypeIdGp;

	private GlobalProperty adultReturnEncounterTypeIdGp;

	private GlobalProperty pedsInitialEncounterTypeIdGp;

	private GlobalProperty pedsReturnEncounterTypeIdGp;

	private GlobalProperty backupFolderPathOrNameGp;

	private GlobalProperty numberOfMonthsBeforeDeletingLogsAndDataGp;

	/* DON'T DELETE this field, it's used at client side */
	private boolean mustSetScheduler;

	private GlobalProperty arvDrugsConceptSetGp;

	private GlobalProperty numberOfVisitsMetricEncounterTypeIdsGp;

	private GlobalProperty numberOfTotalNewPatientsMetricEncounterTypeIdsGp;

	private GlobalProperty addedLocalDHISMappingsGp;

	public ConfigureGPs() {
		initialiseGPsWithTheirCurrentValues();
	}

	private void initialiseGPsWithTheirCurrentValues() {
		viralLoadConceptIdGp = Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.VIRALLOAD_CONCEPTID);
		cd4CountConceptIdGp = Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.CD4COUNT_CONCEPTID);
		reasonForExistingCareConceptIdGp = Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.CAREEXITREASON_CONCEPTID);
		hivProgramIdGp = Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.HIV_PROGRAMID);
		siteIdGp = Context.getAdministrationService().getGlobalPropertyObject(ConfigurableGlobalProperties.SITE_ID);
		dhisAPIRootUrlGp = Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.DHISAPI_URL);
		dhisUsernameGp = Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.DHIS_USERNAME);
		dhisPasswordGp = Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.DHIS_PASSWORD);
		openingHourGp = Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.CONFIGS_OPENNINGHOUR);
		openingDaysGp = Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.CONFIGS_OPENNINGDAYS);
		closingHourGp = Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.CONFIGS_CLOSINGHOUR);
		arvDrugsConceptSetGp = Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.ARVDRUGS_CONCEPTSETID);
		numberOfVisitsMetricEncounterTypeIdsGp = Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.METRIC_ENC_TYPEIDS_NUMBEROFVISITS);
		numberOfTotalNewPatientsMetricEncounterTypeIdsGp = Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.METRIC_ENC_TYPEIDS_NUMBEROFPATIENTSNEW);
		adultInitialEncounterTypeIdGp = Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.ENC_TYPE_ADULTINITIAL_TYPEID);
		adultReturnEncounterTypeIdGp = Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.ENC_TYPE_ADULTRETURN_TYPEID);
		pedsInitialEncounterTypeIdGp = Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.ENC_TYPE_PEDSINITIAL_TYPEID);
		pedsReturnEncounterTypeIdGp = Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.ENC_TYPE_PEDSRETURN_TYPEID);
		backupFolderPathOrNameGp = Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.BACKUP_FOLDERPATHORNAME);
		numberOfMonthsBeforeDeletingLogsAndDataGp = Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.MONTHS_TO_STORE_LOGSANDDATA);
		addedLocalDHISMappingsGp = Context.getAdministrationService()
				.getGlobalPropertyObject(ConfigurableGlobalProperties.ADDED_LOCAL_DHISMAPPINGS);

		if (mustConfigureSchedulerAccount()) {
			schedulerUsernameGp = Context.getAdministrationService().getGlobalPropertyObject("scheduler.username");
			schedulerPasswordGp = Context.getAdministrationService().getGlobalPropertyObject("scheduler.password");
		}
	}

	private boolean mustConfigureSchedulerAccount() {
		return !ModuleUtil.matchRequiredVersions(SystemMonitorConstants.OPENMRS_VERSION, "1.9");
	}

	public void updateAndPersistConfigurableGPs(HttpServletRequest request) {
		List<GlobalProperty> gps = new ArrayList<GlobalProperty>();

		if (mustConfigureSchedulerAccount()) {
			schedulerUsernameGp.setPropertyValue(request.getParameter("schedulerUsername"));
			schedulerPasswordGp.setPropertyValue(request.getParameter("schedulerPassword"));
		}
		viralLoadConceptIdGp.setPropertyValue(request.getParameter("viralLoadConceptId"));
		cd4CountConceptIdGp.setPropertyValue(request.getParameter("cd4CountConceptId"));
		reasonForExistingCareConceptIdGp.setPropertyValue(request.getParameter("reasonForExistingCareConceptId"));
		hivProgramIdGp.setPropertyValue(request.getParameter("hivProgramId"));
		siteIdGp.setPropertyValue(request.getParameter("siteId"));
		dhisAPIRootUrlGp.setPropertyValue(request.getParameter("dhisAPIRootUrl"));
		dhisUsernameGp.setPropertyValue(request.getParameter("dhisUsername"));
		dhisPasswordGp.setPropertyValue(request.getParameter("dhisPassword"));
		openingDaysGp.setPropertyValue(request.getParameter("openingDays"));
		openingHourGp.setPropertyValue(request.getParameter("openingHour"));
		closingHourGp.setPropertyValue(request.getParameter("closingHour"));
		arvDrugsConceptSetGp.setPropertyValue(request.getParameter("arvDrugsConceptSetId"));
		numberOfVisitsMetricEncounterTypeIdsGp
				.setPropertyValue(request.getParameter("numberOfVisitsMetricEncounterTypeIds"));
		numberOfTotalNewPatientsMetricEncounterTypeIdsGp
				.setPropertyValue(request.getParameter("numberOfTotalNewPatientsMetricEncounterTypeIds"));
		adultInitialEncounterTypeIdGp.setPropertyValue(request.getParameter("adultInitialEncounterTypeId"));
		adultReturnEncounterTypeIdGp.setPropertyValue(request.getParameter("adultReturnEncounterTypeId"));
		pedsInitialEncounterTypeIdGp.setPropertyValue(request.getParameter("pedsInitialEncounterTypeId"));
		pedsReturnEncounterTypeIdGp.setPropertyValue(request.getParameter("pedsReturnEncounterTypeId"));
		backupFolderPathOrNameGp.setPropertyValue(request.getParameter("backupFolderPathOrName"));
		numberOfMonthsBeforeDeletingLogsAndDataGp
				.setPropertyValue(request.getParameter("numberOfMonthsBeforeDeletingLogsAndData"));
		addedLocalDHISMappingsGp.setPropertyValue(request.getParameter("addedLocalDHISMappings"));

		if (mustConfigureSchedulerAccount()) {
			gps.add(schedulerUsernameGp);
			gps.add(schedulerPasswordGp);
		}
		gps.add(viralLoadConceptIdGp);
		gps.add(cd4CountConceptIdGp);
		gps.add(reasonForExistingCareConceptIdGp);
		gps.add(hivProgramIdGp);
		gps.add(siteIdGp);
		gps.add(dhisAPIRootUrlGp);
		gps.add(dhisUsernameGp);
		gps.add(dhisPasswordGp);
		gps.add(openingDaysGp);
		gps.add(openingHourGp);
		gps.add(closingHourGp);
		gps.add(arvDrugsConceptSetGp);
		gps.add(numberOfVisitsMetricEncounterTypeIdsGp);
		gps.add(numberOfTotalNewPatientsMetricEncounterTypeIdsGp);
		gps.add(adultInitialEncounterTypeIdGp);
		gps.add(adultReturnEncounterTypeIdGp);
		gps.add(pedsInitialEncounterTypeIdGp);
		gps.add(pedsReturnEncounterTypeIdGp);
		gps.add(backupFolderPathOrNameGp);
		gps.add(numberOfMonthsBeforeDeletingLogsAndDataGp);
		gps.add(addedLocalDHISMappingsGp);

		Context.getAdministrationService().saveGlobalProperties(gps);
	}

	public GlobalProperty getViralLoadConceptIdGp() {
		return viralLoadConceptIdGp;
	}

	public GlobalProperty getReasonForExistingCareConceptIdGp() {
		return reasonForExistingCareConceptIdGp;
	}

	public GlobalProperty getHivProgramIdGp() {
		return hivProgramIdGp;
	}

	public GlobalProperty getSiteIdGp() {
		return siteIdGp;
	}

	public GlobalProperty getDhisAPIRootUrlGp() {
		return dhisAPIRootUrlGp;
	}

	public GlobalProperty getDhisUsernameGp() {
		return dhisUsernameGp;
	}

	public GlobalProperty getDhisPasswordGp() {
		return dhisPasswordGp;
	}

	public GlobalProperty getOpeningHourGp() {
		return openingHourGp;
	}

	public GlobalProperty getOpeningDaysGp() {
		return openingDaysGp;
	}

	public GlobalProperty getClosingHourGp() {
		return closingHourGp;
	}

	public GlobalProperty getSchedulerUsernameGp() {
		return schedulerUsernameGp;
	}

	public GlobalProperty getSchedulerPasswordGp() {
		return schedulerPasswordGp;
	}

	public boolean getMustSetScheduler() {
		return mustConfigureSchedulerAccount();
	}

	public GlobalProperty getNumberOfTotalNewPatientsMetricEncounterTypeIdsGp() {
		return numberOfTotalNewPatientsMetricEncounterTypeIdsGp;
	}

	public GlobalProperty getArvDrugsConceptSetGp() {
		return arvDrugsConceptSetGp;
	}

	public GlobalProperty getCd4CountConceptIdGp() {
		return cd4CountConceptIdGp;
	}

	public GlobalProperty getNumberOfVisitsMetricEncounterTypeIdsGp() {
		return numberOfVisitsMetricEncounterTypeIdsGp;
	}

	public GlobalProperty getAdultInitialEncounterTypeIdGp() {
		return adultInitialEncounterTypeIdGp;
	}

	public GlobalProperty getAdultReturnEncounterTypeIdGp() {
		return adultReturnEncounterTypeIdGp;
	}

	public GlobalProperty getPedsInitialEncounterTypeIdGp() {
		return pedsInitialEncounterTypeIdGp;
	}

	public GlobalProperty getPedsReturnEncounterTypeIdGp() {
		return pedsReturnEncounterTypeIdGp;
	}

	public GlobalProperty getBackupFolderPathOrNameGp() {
		return backupFolderPathOrNameGp;
	}

	public GlobalProperty getNumberOfMonthsBeforeDeletingLogsAndDataGp() {
		return numberOfMonthsBeforeDeletingLogsAndDataGp;
	}

	public GlobalProperty getAddedLocalDHISMappingsGp() {
		return addedLocalDHISMappingsGp;
	}

}