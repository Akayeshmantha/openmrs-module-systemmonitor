package org.openmrs.module.systemmonitor.uptime;

import java.util.Date;

import org.openmrs.module.systemmonitor.SystemMonitorConstants;

/**
 * {@link SystemMonitorConstants#OPENMRS_UPANDDOWNTIME_FILE} contains both up
 * and down time logging, this Class helps to model these as an object for
 * better usage
 */
public class UpOrDownTimeInterval {
	private String upOrDown;

	private Integer totalUpOrDownTime;

	public String getUpOrDown() {
		return upOrDown;
	}
	
	private Date intervalStartingAt;
	
	private Date intervalStoppingAt;

	public Date getIntervalStartingAt() {
		return intervalStartingAt;
	}

	public void setIntervalStartingAt(Date intervalStartingAt) {
		this.intervalStartingAt = intervalStartingAt;
	}

	public Date getIntervalStoppingAt() {
		return intervalStoppingAt;
	}

	public void setIntervalStoppingAt(Date intervalStoppingAt) {
		this.intervalStoppingAt = intervalStoppingAt;
	}

	public void setUpOrDown(String upOrDown) {
		this.upOrDown = upOrDown;
	}

	public Integer getTotalUpOrDownTime() {
		return totalUpOrDownTime;
	}

	public void setTotalUpOrDownTime(Integer totalUpOrDownTime) {
		this.totalUpOrDownTime = totalUpOrDownTime;
	}


}
