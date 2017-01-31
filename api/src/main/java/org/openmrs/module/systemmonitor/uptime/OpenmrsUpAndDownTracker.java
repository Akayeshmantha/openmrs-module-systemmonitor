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

		try {
			if (!upTimelogFile.exists()) {
				if (!upTimelogFile.getParentFile().exists())
					upTimelogFile.getParentFile().mkdirs();
				upTimelogFile.createNewFile();
			} else if (upTimelogFile.exists() && updatedOn.before(todate)) {
				upTimelogFile.delete();
				upTimelogFile.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			if (minutesDiff != 0) {
				/*
				 * runs every 5 minutes, cater for delays upto 1 extra minute on
				 * slow machines. TODO 5 - 6 minutes downtime is counted as
				 * uptime
				 */
				if (minutesDiff >= 5 && minutesDiff <= 6) {
					content += Long.toString(today.getTimeInMillis()) + ":up;" + minutesDiff;
				} else {
					content += Long.toString(today.getTimeInMillis()) + ":down;" + minutesDiff;
				}
			}
		}
		resetDateTimes(updatedOn);
		resetDateTimes(todate);
		writeNow(upTimelogFile, updatedOn, todate, content);
	}

	private static void writeNow(File logFile, Calendar updatedOn, Calendar todate, String content) {
		if (StringUtils.isNotBlank(content)) {
			if (logFile.length() > 0)
				writeToFile(logFile, "," + content, true);
			else
				writeToFile(logFile, content, false);
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
	public static Object[] calculateOpenMRSUpAndDowntimeBy(Date date, String upAndDownTimeContent) {
		Object[] upAndDown = new Object[4];
		try {
			String str = StringUtils.isBlank(upAndDownTimeContent)
					? FileUtils.readFileToString(SystemMonitorConstants.OPENMRS_UPANDDOWNTIME_FILE)
					: upAndDownTimeContent;
			String[] content = StringUtils.isNotBlank(str) ? str.split(",") : null;
			Calendar dateTime = Calendar.getInstance();
			Calendar dateTimeOpenning = Calendar.getInstance();
			Calendar dateTimeClosing = Calendar.getInstance();
			Integer openingHour = Integer.parseInt(Context.getService(SystemMonitorService.class)
					.getConfiguredOpeningHour().getPropertyValue().substring(0, 2));
			Integer closingHour = Integer.parseInt(Context.getService(SystemMonitorService.class)
					.getConfiguredClosingHour().getPropertyValue().substring(0, 2));
			List<Date> dates = new ArrayList<Date>();
			Integer totalUp = 0, totalDown = 0;
			List<UpOrDownTimeInterval> upIntervals = new ArrayList<UpOrDownTimeInterval>();
			List<UpOrDownTimeInterval> downIntervals = new ArrayList<UpOrDownTimeInterval>();

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
						Date lastStartingInterval = null;

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
								String lastString = i - 1 >= 0 && i - 1 < rangedContent.split(",").length - 1
										? rangedContent.split(",")[i - 1] : null;
								String nextString = i + 1 < rangedContent.split(",").length
										? rangedContent.split(",")[i + 1] : null;
								Integer up = 0;
								Integer down = 0;

								extractUpDownIntervals(string, lastString, nextString, upIntervals, downIntervals,
										lastStartingInterval);
								if (upOrDown.equals("up")) {
									up = Integer.parseInt(string.split(":up;")[1]);
									totalUp += up;
								} else if (upOrDown.equals("down")) {
									down = Integer.parseInt(string.split(":down;")[1]);
									totalDown += down;
								}
							}
						} else
							totalUp = (int) (long) Context.getService(SystemMonitorService.class)
									.getOpenMRSSystemUpTime();
					}
				} else
					totalUp = (int) (long) Context.getService(SystemMonitorService.class).getOpenMRSSystemUpTime();
			}
			upAndDown[0] = totalUp;
			upAndDown[1] = totalDown;
			upAndDown[2] = upIntervals;
			upAndDown[3] = downIntervals;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return upAndDown;
	}

	private static void extractUpDownIntervals(String currentUpOrDownEntry, String lastUpOrDownEntry,
			String nextUpOrDownEntry, List<UpOrDownTimeInterval> upIntervals, List<UpOrDownTimeInterval> downIntervals,
			Date lastStartingInterval) {
		String currentUpOrDown = StringUtils.isNotBlank(currentUpOrDownEntry) ? currentUpOrDownEntry
				.substring(currentUpOrDownEntry.indexOf(":") + 1, currentUpOrDownEntry.indexOf(";")) : null;
		String lastUpOrDown = StringUtils.isNotBlank(lastUpOrDownEntry)
				? lastUpOrDownEntry.substring(lastUpOrDownEntry.indexOf(":") + 1, lastUpOrDownEntry.indexOf(";"))
				: null;
		String nextUpOrDown = StringUtils.isNotBlank(nextUpOrDownEntry)
				? nextUpOrDownEntry.substring(nextUpOrDownEntry.indexOf(":") + 1, nextUpOrDownEntry.indexOf(";"))
				: null;
		Integer currentUpOrDownValue = StringUtils.isNotBlank(currentUpOrDown)
				? Integer.parseInt(currentUpOrDownEntry.split(":" + currentUpOrDown + ";")[1]) : null;
		Date currentUpOrDownDate = StringUtils.isNotBlank(currentUpOrDown)
				? new Date(Long.parseLong(currentUpOrDownEntry.split(":")[0])) : null;
		Date lastUpOrDownDate = StringUtils.isNotBlank(lastUpOrDown)
				? new Date(Long.parseLong(lastUpOrDownEntry.split(":")[0])) : null;
		UpOrDownTimeInterval interval = new UpOrDownTimeInterval();

		interval.setUpOrDown(currentUpOrDown);
		interval.setIntervalStoppingAt(currentUpOrDownDate);
		if ((!currentUpOrDown.equals(lastUpOrDown) && !currentUpOrDown.equals(nextUpOrDown))) {
			interval.setTotalUpOrDownTime(currentUpOrDownValue);
			interval.setIntervalStartingAt(lastUpOrDownDate);

			if ("up".equals(currentUpOrDown)) {
				upIntervals.add(interval);
			} else if ("down".equals(currentUpOrDown)) {
				downIntervals.add(interval);
			}
			lastStartingInterval = currentUpOrDownDate;
		} else {
			interval.setTotalUpOrDownTime(currentUpOrDownValue);
			interval.setIntervalStartingAt((lastStartingInterval != null) ? lastStartingInterval : lastUpOrDownDate);

			if ("up".equals(currentUpOrDown)) {
				addOrSetLastUpOrDownInterval(upIntervals, interval);
			} else if ("down".equals(currentUpOrDown)) {
				addOrSetLastUpOrDownInterval(downIntervals, interval);
			}
		}

	}

	private static void addOrSetLastUpOrDownInterval(List<UpOrDownTimeInterval> intervals,
			UpOrDownTimeInterval upOrDownInterval) {
		UpOrDownTimeInterval lastUpOrDownInterval = intervals.size() > 0 ? intervals.get(intervals.size() - 1) : null;

		if (lastUpOrDownInterval != null
				&& lastUpOrDownInterval.getIntervalStoppingAt().equals(upOrDownInterval.getIntervalStartingAt())) {
			upOrDownInterval.setIntervalStartingAt(lastUpOrDownInterval.getIntervalStartingAt());
			upOrDownInterval.setTotalUpOrDownTime(
					lastUpOrDownInterval.getTotalUpOrDownTime() + upOrDownInterval.getTotalUpOrDownTime());
			intervals.set(intervals.size() - 1, upOrDownInterval);
		} else {
			intervals.add(upOrDownInterval);
		}
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
