package org.openmrs.module.systemmonitor.memory;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.module.systemmonitor.indicators.OSAndHardwareIndicators;

/**
 * TODO very incomplete, a file json db was an initial thought but writing and
 * reading it every one/n minutes may require lots of memory maybe
 * 
 * @author k-joseph
 *
 */
public class MemoryAggregation {

	private static SimpleDateFormat hhmm = new SimpleDateFormat("HHmm");
	private static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");

	/**
	 * Generates something like;
	 * {"memory":{"aggregated":[{"date":"20150816","total":2300,"used":2223},{
	 * "date":"20160816","total":2300,"used":2223}], "todayTemp":
	 * {"date":"20150817", "values":[{"time":"0923","total":2300,"used":2223},
	 * {"time":"0924","total":2300,"used":2223}]}}}
	 * 
	 * @param aggregated
	 * @param todayTemp
	 * @return
	 */
	public static JSONObject generateMemoryDBEntry(JSONArray aggregated, JSONArray todayTemp) {
		JSONObject memory = new JSONObject();
		JSONObject json = new JSONObject();
		JSONObject tempJSON = new JSONObject();

		tempJSON.put("date", yyyyMMdd.format(new Date()));
		tempJSON.put("values", todayTemp);
		json.put("aggregated", aggregated);
		json.put("todayTemp", tempJSON);
		memory.put("memory", json);

		return memory;
	}

	private static JSONObject generateInArrayAggregatedMemoryDBEntry(Date date, Integer totalMemory,
			Integer usedMemory) {
		JSONObject json = new JSONObject();

		json.put("date", yyyyMMdd.format(date));
		json.put("total", totalMemory);
		json.put("used", usedMemory);

		return json;
	}

	private static JSONObject generateInArrayTodayAtMinuteTempMemoryDBEntry() {
		JSONObject json = new JSONObject();

		json.put("time", hhmm.format(new Date()));
		json.put("total", OSAndHardwareIndicators.MEMORY_TOTAL);
		json.put("used", OSAndHardwareIndicators.MEMORY_USED);

		return json;
	}
}
