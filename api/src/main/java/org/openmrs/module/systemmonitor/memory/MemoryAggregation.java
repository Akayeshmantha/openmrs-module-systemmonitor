package org.openmrs.module.systemmonitor.memory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openmrs.module.systemmonitor.SystemMonitorConstants;
import org.openmrs.module.systemmonitor.indicators.OSAndHardwareIndicators;

/**
 * @author k-joseph
 *
 */
public class MemoryAggregation {

	/**
	 * Memory in Mega Bytes (MB)
	 * 
	 * @return
	 */
	public static Long logCurrentUsedMemory() {
		Long usedMemo = OSAndHardwareIndicators.MEMORY_USED;
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

	private static void resetDateTimes(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the
												// hour of day !
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.MILLISECOND);
	}

	private static void writeToFile(File file, String content, boolean append) {
		try {

			FileWriter fstream = new FileWriter(file, true);
			BufferedWriter fbw = new BufferedWriter(fstream);
			if (append)
				fbw.append(content);
			else
				fbw.write(content);
			fbw.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static Long getAggregatedUsedMemory() {
		Long usedMemo = OSAndHardwareIndicators.MEMORY_USED;
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
