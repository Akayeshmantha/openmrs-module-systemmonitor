package org.openmrs.module.systemmonitor;

import java.util.Calendar;
import java.util.Date;

/**
 * Implements some required DHIS periods by this System Monitor: <br />
 * http://dhis2.github.io/dhis2-docs/master/en/developer/html/ch01s04.html
 * http://dhis2.github.io/dhis2-docs/master/en/user/html/ch03s04.html <br />
 * Throughout the DHIS Web API we refer to dates and periods. The date format
 * is: yyyy-MM-dd <br />
 * <table class="table" summary="Period format" border="1">
 * <colgroup><col width="60pt" class="c1"><col width="80pt" class="c2"><col
 * width="80pt" class="c3"><col width="200pt" class="c4"></colgroup><thead>
 * <tr>
 * <th>Interval</th>
 * <th>Format</th>
 * <th>Example</th>
 * <th>Description</th>
 * </tr>
 * </thead><tbody>
 * <tr>
 * <td>Day</td>
 * <td><span class="emphasis"><em>yyyyMMdd</em></span></td>
 * <td>20040315</td>
 * <td>March 15 2004</td>
 * </tr>
 * <tr>
 * <td>Week</td>
 * <td><span class="emphasis"><em>yyyy</em></span>W<span class="emphasis">
 * <em>n</em></span></td>
 * <td>2004W10</td>
 * <td>Week 10 2004</td>
 * </tr>
 * <tr>
 * <td>Month</td>
 * <td><span class="emphasis"><em>yyyyMM</em></span></td>
 * <td>200403</td>
 * <td>March 2004</td>
 * </tr>
 * <tr>
 * <td>Quarter</td>
 * <td><span class="emphasis"><em>yyyy</em></span>Q<span class="emphasis">
 * <em>n</em></span></td>
 * <td>2004Q1</td>
 * <td>January-March 2004</td>
 * </tr>
 * <tr>
 * <td>Six-month</td>
 * <td><span class="emphasis"><em>yyyy</em></span>S<span class="emphasis">
 * <em>n</em></span></td>
 * <td>2004S1</td>
 * <td>January-June 2004</td>
 * </tr>
 * <tr>
 * <td>Six-month April</td>
 * <td><span class="emphasis"><em>yyyy</em></span>AprilSn</td>
 * <td>2004AprilS1</td>
 * <td>April-September 2004</td>
 * </tr>
 * <tr>
 * <td>Year</td>
 * <td>yyyy</td>
 * <td>2004</td>
 * <td>2004</td>
 * </tr>
 * <tr>
 * <td>Financial Year April</td>
 * <td>yyyyApril</td>
 * <td>2004April</td>
 * <td>Apr 2004-Mar 2005</td>
 * </tr>
 * <tr>
 * <td>Financial Year July</td>
 * <td>yyyyJuly</td>
 * <td>2004July</td>
 * <td>July 2004-June 2005</td>
 * </tr>
 * <tr>
 * <td>Financial Year Oct</td>
 * <td>yyyyOct</td>
 * <td>2004Oct</td>
 * <td>Oct 2004-Sep 2005</td>
 * </tr>
 * </tbody>
 * </table>
 */
public class DHISPeriodTypes {
	
	public static String FORMAT_DAILY = "yyyyMMdd";

	public static String FORMAT_WEEKLY = "yyyyww";

	public static String FORMAT_MONTHLY = "yyyyMM";

	public static String FORMAT_YEARLY = "yyyy";

	private static Calendar calendar = Calendar.getInstance();

	/**
	 * Quartery period looks like; yyyyQn e.g: 2016Q2
	 * 
	 * @param date
	 * @return
	 */
	public static String getQuaterlyDatePeriod(Date date) {
		calendar.setTime(date);

		int quarterCount = (calendar.get(Calendar.MONTH) / 3) + 1;

		return calendar.get(Calendar.YEAR) + "Q" + quarterCount;
	}
	
	/**
	 * Half period looks like; yyyySn e.g: 2016S1 or 2016S2
	 * 
	 * @param date
	 * @return
	 */
	public static String getSixMonthsDatePeriod(Date date) {
		calendar.setTime(date);

		int halfCount = calendar.get(Calendar.MONTH) <= 5 ? 1 : 2;

		return calendar.get(Calendar.YEAR) + "S" + halfCount;
	}
}
