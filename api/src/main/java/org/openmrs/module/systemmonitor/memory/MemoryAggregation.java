package org.openmrs.module.systemmonitor.memory;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.openmrs.module.systemmonitor.SystemMonitorCommons;
import org.openmrs.module.systemmonitor.SystemMonitorConstants;
import org.openmrs.module.systemmonitor.indicators.OSAndHardwareIndicators;

public class MemoryAggregation extends SystemMonitorCommons {

	/**
	 * Memory in Mega Bytes (MB)
	 * 
	 * @return
	 */
	public static Long logCurrentUsedMemory() {
		Long usedMemo = new OSAndHardwareIndicators().MEMORY_USED;
		File logFile = SystemMonitorConstants.SYSTEMMONITOR_MEMORYDATAFILE;
		Calendar updatedOn = Calendar.getInstance();
		Calendar today = Calendar.getInstance();

		updatedOn.setTimeInMillis(logFile.lastModified());
		resetDateTimes(updatedOn);
		resetDateTimes(today);
		try {
			if (!logFile.exists()) {
				logFile.createNewFile();
			} else if (logFile.exists() && updatedOn.before(today)) {
				logFile.delete();
				logFile.createNewFile();
			}
			if (logFile.length() > 0)
				writeToFile(logFile, "," + usedMemo, true);
			else
				writeToFile(logFile, String.valueOf(usedMemo), false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return usedMemo;
	}

	public static Long getAggregatedUsedMemory() {
		Long usedMemo = new OSAndHardwareIndicators().MEMORY_USED;
		File logFile = SystemMonitorConstants.SYSTEMMONITOR_MEMORYDATAFILE;

		if (logFile.exists() && logFile.length() > 0) {
			try {
				String usedMemos = FileUtils.readFileToString(logFile);

				if (StringUtils.isNotBlank(usedMemos)) {
					String[] usedMemosArr = usedMemos.split(",");
					Long[] usedMemoRight = new Long[usedMemosArr.length];

					for (int i = 0; i < usedMemosArr.length; i++) {
						if (StringUtils.isNotBlank(usedMemosArr[i])) {
							usedMemoRight[i] = Long.parseLong(usedMemosArr[i]);
						}
					}
					usedMemo = calculateAverage(usedMemoRight);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return usedMemo;
	}

	/**
	 * This is where the real aggregation actually happens
	 * 
	 * @param marks
	 * @return
	 */
	private static Long calculateAverage(Long[] marks) {
		Long sum = new Long(0);

		if (marks.length > 0) {
			for (Long mark : marks) {
				sum += mark;
			}
			return sum.longValue() / marks.length;
		}
		return sum;
	}
}
