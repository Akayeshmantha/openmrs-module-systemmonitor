package org.openmrs.module.systemmonitor.api;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.systemmonitor.DHISPeriodTypes;

import junit.framework.Assert;

/**
 * Unit tests {@link DHISPeriodTypes}
 * 
 * @author k-joseph
 */
public class DHISPeriodTypesTest {
	private Date date;
	DateFormat sdf;
	Calendar calendar; 
	

	@Before
	public void init() throws ParseException {
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		calendar = Calendar.getInstance();
		
		date = sdf.parse("2016-06-19");
	}
	
	@Test
	public void testPeriodTypesOrFormats() throws ParseException {
		Assert.assertEquals(new SimpleDateFormat(DHISPeriodTypes.FORMAT_DAILY).format(date), "20160619");
		Assert.assertEquals(new SimpleDateFormat(DHISPeriodTypes.FORMAT_MONTHLY).format(date), "201606");
		Assert.assertEquals(DHISPeriodTypes.getQuaterlyDatePeriod(date), "2016Q2");

		calendar.setTime(date);
		Assert.assertEquals(new SimpleDateFormat(DHISPeriodTypes.FORMAT_WEEKLY).format(date), "2016" + calendar.get(Calendar.WEEK_OF_YEAR));
		Assert.assertEquals(DHISPeriodTypes.getSixMonthsDatePeriod(date), "2016S1");
		
		date = sdf.parse("2016-07-19");
		calendar.setTime(date);
		
		Assert.assertEquals(DHISPeriodTypes.getSixMonthsDatePeriod(date), "2016S2");
		Assert.assertEquals(new SimpleDateFormat(DHISPeriodTypes.FORMAT_WEEKLY).format(date), "2016" + calendar.get(Calendar.WEEK_OF_YEAR));
	}
}
