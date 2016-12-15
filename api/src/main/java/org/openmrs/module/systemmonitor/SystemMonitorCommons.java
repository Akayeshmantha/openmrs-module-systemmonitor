package org.openmrs.module.systemmonitor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;

public class SystemMonitorCommons {
	public static Calendar resetDateTimes(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the
												// hour of day !
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.MILLISECOND);

		return calendar;
	}

	public static Calendar resetSecsAndMilliSecs(Calendar calendar) {
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.MILLISECOND);

		return calendar;
	}

	public static Calendar resetMinsSecsAndMilliSecs(Calendar calendar) {
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.MILLISECOND);

		return calendar;
	}

	public static void writeToFile(File file, String content, boolean append) {
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
}
