package org.openmrs.module.systemmonitor.uptime;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.systemmonitor.SystemMonitorCommons;
import org.openmrs.module.systemmonitor.SystemMonitorConstants;
import org.openmrs.module.systemmonitor.api.SystemMonitorService;

public class OpenmrsUpAndDownTracker extends SystemMonitorCommons {

	public static void logCurrentOpenMRSUptime() {
		File upTimelogFile = SystemMonitorConstants.OPENMRS_UPANDDOWNTIME_FILE;
		Calendar updatedOn = Calendar.getInstance();
		Calendar todate = Calendar.getInstance();

		updatedOn.setTimeInMillis(upTimelogFile.lastModified());

		Calendar updatedAt = resetSecsAndMilliSecs(updatedOn);
		Calendar today = resetSecsAndMilliSecs(todate);
		long minutesDiff = TimeUnit.MILLISECONDS.toMinutes(today.getTimeInMillis() - updatedAt.getTimeInMillis());
		String content = "";
		Integer openingHour = Integer.parseInt(Context.getService(SystemMonitorService.class).getConfiguredOpeningHour()
				.getPropertyValue().substring(0, 2));
		Integer closingHour = Integer.parseInt(Context.getService(SystemMonitorService.class).getConfiguredClosingHour()
				.getPropertyValue().substring(0, 2));
		Integer workingMinutesDifference = ((closingHour - openingHour) - 1) * 60;

		if (minutesDiff <= workingMinutesDifference && today.get(Calendar.HOUR_OF_DAY) >= openingHour
				&& today.get(Calendar.HOUR_OF_DAY) < closingHour) {
			/*
			 * runs every 5 minutes, cater for delays upto 2 extra minutes on
			 * slow machines
			 */
			if (minutesDiff >= 5 && minutesDiff <= 7) {
				content += Long.toString(today.getTimeInMillis()) + ":up;" + minutesDiff;
			} else {
				content += Long.toString(today.getTimeInMillis()) + ":down;" + minutesDiff;
			}
		}
		resetDateTimes(updatedOn);
		resetDateTimes(todate);
		writeNow(upTimelogFile, updatedOn, todate, content);
	}

	private static void writeNow(File logFile, Calendar updatedOn, Calendar todate, String content) {
		try {
			if (!logFile.exists()) {
				logFile.createNewFile();
			} else if (logFile.exists() && updatedOn.before(todate)) {
				logFile.delete();
				logFile.createNewFile();
			}
			if (StringUtils.isNotBlank(content)) {
				if (logFile.length() > 0)
					writeToFile(logFile, "," + content, true);
				else
					writeToFile(logFile, content, false);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Integer getNumberOfOpenMRSDownTimesToday() {
		File upTimelogFile = SystemMonitorConstants.OPENMRS_UPANDDOWNTIME_FILE;
		Integer numberOfReboots = new Integer(0);
		try {
			numberOfReboots = StringUtils.countMatches(FileUtils.readFileToString(upTimelogFile), ":down;");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return numberOfReboots;
	}

	/**
	 * @param date,
	 *            by this date
	 * @return
	 */
	public static Integer[] calculateOpenMRSUpAndtimeBy(Date date) {
		Integer[] upAndDown = new Integer[2];
		try {
			String str = FileUtils.readFileToString(SystemMonitorConstants.OPENMRS_UPANDDOWNTIME_FILE);
			String[] content = StringUtils.isNotBlank(str) ? str.split(",") : null;
			Calendar dateTime = Calendar.getInstance();
			Calendar dateTimeOpenning = Calendar.getInstance();
			Calendar dateTimeClosing = Calendar.getInstance();
			Integer openingHour = Integer.parseInt(Context.getService(SystemMonitorService.class)
					.getConfiguredOpeningHour().getPropertyValue().substring(0, 2));
			Integer closingHour = Integer.parseInt(Context.getService(SystemMonitorService.class)
					.getConfiguredClosingHour().getPropertyValue().substring(0, 2));
			List<Date> dates = new ArrayList<Date>();
			Integer lastUp = 0, lastDown = 0, totalUp = 0, totalDown = 0;

			dateTime.setTime(date);
			if (content != null) {
				dateTime = resetSecsAndMilliSecs(dateTime);
				dateTimeOpenning.setTime(dateTime.getTime());
				dateTimeClosing.setTime(dateTime.getTime());
				dateTimeOpenning.set(Calendar.HOUR_OF_DAY, openingHour);
				dateTimeClosing.set(Calendar.HOUR_OF_DAY, closingHour);
				dateTimeOpenning = resetMinsSecsAndMilliSecs(dateTimeOpenning);
				dateTimeClosing = resetMinsSecsAndMilliSecs(dateTimeClosing);
				for (int i = 0; i < content.length; i++) {
					Calendar d = Calendar.getInstance();
					d.setTimeInMillis(Long.parseLong(content[i].split(":")[0]));
					dates.add(d.getTime());
				}

				if (dates.size() > 1) {
					Date nearestOpening = getNearestDate(dates, dateTimeOpenning.getTime());
					Date nearestClosing = getNearestDate(dates, dateTimeClosing.getTime());
					if (nearestOpening != null && nearestClosing != null) {
						String open = Long.toString(nearestOpening.getTime());
						String close = Long.toString(nearestClosing.getTime());
						String[] ranged = str.split(open)[1].split(close);

						if (ranged.length > 1) {
							String rangedContent = open + ranged[0]
									+ ((ranged[1].indexOf(",") > 0)
											? close + ranged[1].substring(0, ranged[1].indexOf(","))
											: close + ranged[1]);
							for (int i = 0; i < rangedContent.split(",").length; i++) {
								Calendar logTime = Calendar.getInstance();
								logTime.setTimeInMillis(Long.parseLong(content[i].split(":")[0]));
								String string = rangedContent.split(",")[i];
								String upOrDown = string.substring(string.indexOf(":") + 1, string.indexOf(";"));

								if (upOrDown.equals("up")) {
									Integer up = Integer.parseInt(string.split(":up;")[1]);
									totalUp += up;
									lastUp = up;
								} else if (upOrDown.equals("down")) {
									Integer down = Integer.parseInt(string.split(":down;")[1]);
									totalDown += down;
									lastDown = down;
								}
							}
						} else
							totalUp = (int) (long) Context.getService(SystemMonitorService.class)
									.getOpenMRSSystemUpTime();
					}
				} else
					totalUp = (int) (long) Context.getService(SystemMonitorService.class).getOpenMRSSystemUpTime();
			} else
				totalUp = totalUp = (int) (long) Context.getService(SystemMonitorService.class)
						.getOpenMRSSystemUpTime();
			upAndDown[1] = totalDown;
			upAndDown[0] = totalUp;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return upAndDown;
	}

	private static Date getNearestDate(List<Date> dates, Date currentDate) {
		long minDiff = -1, currentTime = currentDate.getTime();
		Date minDate = null;
		for (Date date : dates) {
			long diff = Math.abs(currentTime - date.getTime());
			if ((minDiff == -1) || (diff < minDiff)) {
				minDiff = diff;
				minDate = date;
			}
		}
		return minDate;
	}

}
