package org.openmrs.module.systemmonitor.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.openmrs.GlobalProperty;
import org.openmrs.api.context.Context;
import org.openmrs.module.systemmonitor.ConfigurableGlobalProperties;
import org.openmrs.module.systemmonitor.uptime.OpenmrsUpAndDownTracker;
import org.openmrs.module.systemmonitor.uptime.UpOrDownTimeInterval;
import org.openmrs.test.BaseModuleContextSensitiveTest;

import junit.framework.Assert;

public class OpenmrsUpAndDownTrackerTest extends BaseModuleContextSensitiveTest {
	String to = Long.toString(System.currentTimeMillis()).substring(0,
			Math.min(Long.toString(System.currentTimeMillis()).length(), 5));;

	String upAndownContent = to + "54500000:down;523," + to + "54800000:up;5," + to + "55100000:up;5," + to
			+ "55400000:up;5," + to + "55700000:up;5," + to + "56000000:up;5" + "," + to + "56300000:down;5," + to
			+ "57500000:down;20" + "," + to + "57800000:up;5" + "," + to + "58100000:down;1" + "," + to
			+ "58400000:up;4" + "," + to + "58700000:down;3" + "," + to + "58780000:down;3";
	Date date = new Date(Long.parseLong(to + "58700000"));

	@SuppressWarnings("unchecked")
	@Test
	public void test_calculateOpenMRSUpAndDowntimeBy() {
		GlobalProperty o = new GlobalProperty(ConfigurableGlobalProperties.CONFIGS_OPENNINGHOUR);
		GlobalProperty c = new GlobalProperty(ConfigurableGlobalProperties.CONFIGS_CLOSINGHOUR);

		o.setPropertyValue("0000");
		c.setPropertyValue("2359");
		Context.getAdministrationService().saveGlobalProperty(o);
		Context.getAdministrationService().saveGlobalProperty(c);

		Object[] upsAndDowns = OpenmrsUpAndDownTracker.calculateOpenMRSUpAndDowntimeBy(date, upAndownContent);
		Integer openmrsUptime = (Integer) upsAndDowns[0];
		Integer openmrsDowntime = (Integer) upsAndDowns[1];
		List<UpOrDownTimeInterval> upIntervals = (List<UpOrDownTimeInterval>) upsAndDowns[2];
		List<UpOrDownTimeInterval> downIntervals = (List<UpOrDownTimeInterval>) upsAndDowns[3];

		Assert.assertEquals(555, openmrsDowntime.intValue());
		Assert.assertEquals(34, openmrsUptime.intValue());

		Assert.assertEquals(4, downIntervals.size());
		Assert.assertEquals(3, upIntervals.size());

		Assert.assertEquals(downIntervals.get(0).getTotalUpOrDownTime().intValue(), 523);
		Assert.assertNull(downIntervals.get(0).getIntervalStartingAt());
		Assert.assertEquals("down", downIntervals.get(0).getUpOrDown());
		Assert.assertEquals(new Date(Long.parseLong(to + "54500000")), downIntervals.get(0).getIntervalStoppingAt());

		Assert.assertEquals(new Date(Long.parseLong(to + "56000000")), downIntervals.get(1).getIntervalStartingAt());
		Assert.assertEquals(new Date(Long.parseLong(to + "57500000")), downIntervals.get(1).getIntervalStoppingAt());
		Assert.assertEquals("down", downIntervals.get(1).getUpOrDown());
		Assert.assertEquals(25, downIntervals.get(1).getTotalUpOrDownTime().intValue());

		Assert.assertEquals(downIntervals.get(2).getTotalUpOrDownTime().intValue(), 1);
		Assert.assertEquals(downIntervals.get(2).getIntervalStartingAt(), new Date(Long.parseLong(to + "57800000")));
		Assert.assertEquals(downIntervals.get(2).getIntervalStoppingAt(), new Date(Long.parseLong(to + "58100000")));
		Assert.assertEquals("down", downIntervals.get(2).getUpOrDown());

		Assert.assertEquals(downIntervals.get(3).getTotalUpOrDownTime().intValue(), 6);
		Assert.assertEquals(downIntervals.get(3).getIntervalStartingAt(), new Date(Long.parseLong(to + "58400000")));
		Assert.assertEquals(downIntervals.get(3).getIntervalStoppingAt(), new Date(Long.parseLong(to + "58780000")));
		Assert.assertEquals("down", downIntervals.get(3).getUpOrDown());

		Assert.assertEquals(upIntervals.get(0).getTotalUpOrDownTime().intValue(), 25);
		Assert.assertEquals(upIntervals.get(0).getIntervalStartingAt(), new Date(Long.parseLong(to + "54500000")));
		Assert.assertEquals(upIntervals.get(0).getIntervalStoppingAt(), new Date(Long.parseLong(to + "56000000")));
		Assert.assertEquals("up", upIntervals.get(0).getUpOrDown());

		Assert.assertEquals(upIntervals.get(1).getTotalUpOrDownTime().intValue(), 5);
		Assert.assertEquals(upIntervals.get(1).getIntervalStartingAt(), new Date(Long.parseLong(to + "57500000")));
		Assert.assertEquals(upIntervals.get(1).getIntervalStoppingAt(), new Date(Long.parseLong(to + "57800000")));
		Assert.assertEquals("up", upIntervals.get(1).getUpOrDown());

		Assert.assertEquals(4, upIntervals.get(2).getTotalUpOrDownTime().intValue());
		Assert.assertEquals(upIntervals.get(2).getIntervalStartingAt(), new Date(Long.parseLong(to + "58100000")));
		Assert.assertEquals(upIntervals.get(2).getIntervalStoppingAt(), new Date(Long.parseLong(to + "58400000")));
		Assert.assertEquals("up", upIntervals.get(2).getUpOrDown());
	}
}
