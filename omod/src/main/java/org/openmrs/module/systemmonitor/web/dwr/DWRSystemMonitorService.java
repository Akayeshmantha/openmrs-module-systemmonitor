package org.openmrs.module.systemmonitor.web.dwr;

import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.systemmonitor.ConfigurableGlobalProperties;
import org.openmrs.module.systemmonitor.SystemMonitorConstants;
import org.openmrs.module.systemmonitor.api.SystemMonitorService;
import org.openmrs.module.systemmonitor.indicators.OSAndHardwareIndicators;

public class DWRSystemMonitorService {

	public String getDataForClientPushing() {
		String dhisUserName = Context.getAdministrationService()
				.getGlobalProperty(ConfigurableGlobalProperties.DHIS_USERNAME);
		String dhisPassword = Context.getAdministrationService()
				.getGlobalProperty(ConfigurableGlobalProperties.DHIS_PASSWORD);
		String dhisPostUrl = (Context.getAdministrationService()
				.getGlobalProperty(ConfigurableGlobalProperties.DHISAPI_URL)) != null
						? Context.getAdministrationService().getGlobalProperty(ConfigurableGlobalProperties.DHISAPI_URL)
								+ SystemMonitorConstants.DHIS_API_DATAVALUES_URL
						: "";
		OSAndHardwareIndicators osshi = new OSAndHardwareIndicators();

		String systemId = osshi.getHostName() + "-"
				+ (osshi.getMacAddress() != null ? osshi.getMacAddress().replace(":", "") : "");
		JSONObject allDataValues = prepareClientMonitoredData();
		JSONObject allDHISValuesJSON = new JSONObject();

		allDHISValuesJSON.put("data", allDataValues);
		allDHISValuesJSON.put("dhisUserName", dhisUserName);
		allDHISValuesJSON.put("dhisPassword", dhisPassword);
		allDHISValuesJSON.put("dhisPostUrl", dhisPostUrl);
		allDHISValuesJSON.put("fileName",
				systemId + "-dhisData-"
						+ new SimpleDateFormat("yyyyMMdd")
								.format(Context.getService(SystemMonitorService.class).getEvaluationAndReportingDate())
						+ ".json");
		allDHISValuesJSON.put("failureMessage",
				Context.getMessageSourceService().getMessage("systemmonitor.clientPushing.failureMessage"));

		return allDataValues.length() > 0 ? allDHISValuesJSON.toString() : "";
	}

	private JSONObject prepareClientMonitoredData() {
		Context.getService(SystemMonitorService.class).updatePreviouslySubmittedOrMissedSMTData();
		
		JSONObject allDataValues = new JSONObject();
		JSONArray dataToBePushedOrExported = Context.getService(SystemMonitorService.class)
				.fetchDataToBePushedAtClientLevelOrExported();

		if (dataToBePushedOrExported.length() > 0)
			allDataValues.put("dataValues", dataToBePushedOrExported);

		return allDataValues;
	}
}
