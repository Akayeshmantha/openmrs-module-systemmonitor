package org.openmrs.module.systemmonitor.distributions;

import org.json.JSONObject;
import org.openmrs.module.systemmonitor.export.DHISGenerateDataValueSetSchemas;

/**
 * This interfaces SystemMonitor API and only retrieves indicators for
 * https://github.com/jembi/openmrs-emt-ubuntu (Rwanda SPH Study Monitoring
 * Tool)
 * 
 * @author k-joseph
 *
 */
public class RwandaSPHStudyEMT {

	public JSONObject generatedDHISDataValueSetJSONString() {
		return DHISGenerateDataValueSetSchemas.generateRwandaSPHEMTDHISDataValueSets();
	}
}
