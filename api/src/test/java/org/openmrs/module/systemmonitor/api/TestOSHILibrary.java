package org.openmrs.module.systemmonitor.api;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.InetAddress;

import org.junit.Test;
import org.openmrs.module.systemmonitor.extension.curl.CurlEmulator;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.Display;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.hardware.PowerSource;
import oshi.hardware.Sensors;
import oshi.software.os.OperatingSystem;
import oshi.software.os.OperatingSystemVersion;

/**
 * @author k-joseph
 * 
 *         Tests Library: https://github.com/dblock/ (Operating System and
 *         Hardware Information): A JNA-based (native) operating system
 *         information library for Java that aims to provide a cross-platform
 *         implementation to retrieve system information, such as version,
 *         memory, CPU, disk, battery, etc.
 * 
 * @TODO Re-write a version of this library that supports lower java version
 *       instead of requiring 1.8
 */
public class TestOSHILibrary {

	@Test
	public void test_systemProcessor() {
		SystemInfo si = new SystemInfo();
		CentralProcessor p = si.getHardware().getProcessor();

		System.out.println(
				"\n::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\nPROCESSOR NAME: " + p.getName());
		System.out.println("PROCESSOR VENDOR: " + p.getVendor());
		// System.out.println("CPU LOAD: " + p.getSystemCpuLoad());
		System.out.println("SYSTEM LOAD: " + p.getSystemLoadAverage());
		System.out.println("SYSTEM SERIAL NUMBER: " + p.getSystemSerialNumber());
		System.out.println("LOGICAL PROCESSOR COUNT: " + p.getLogicalProcessorCount());
		System.out.println("PHYSICAL PROCESSOR COUNT: " + p.getPhysicalProcessorCount());
		System.out.println("THREAD COUNT: " + p.getThreadCount());
		System.out.println("SYSTEM UPTIME (Seconds): " + p.getSystemUptime()
				+ "\n::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
	}

	@Test
	public void test_systemMemoryUsage() {
		SystemInfo si = new SystemInfo();
		HardwareAbstractionLayer hal = si.getHardware();
		GlobalMemory memory = hal.getMemory();

		System.out.println("\n::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\nTOTAL MEMORY (Bytes): "
				+ memory.getTotal());
		System.out.println("AVAILABLE MEMORY: " + memory.getAvailable());
		System.out.println("USED MEMORY: " + (memory.getTotal() - memory.getAvailable()));
		System.out.println("TOTAL SWAP MEMORY: " + memory.getSwapTotal());
		System.out.println("TOTAL SWAP USED MEMORY: " + memory.getSwapUsed());
		System.out.println("TOTAL SWAP FREE MEMORY: " + (memory.getSwapTotal() - memory.getSwapUsed())
				+ "\n::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
	}

	@Test
	public void test_systemNetwork() throws IOException {
		SystemInfo si = new SystemInfo();

		for (NetworkIF net : si.getHardware().getNetworkIFs()) {
			System.out.println(
					"\n::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\nNETWORK NAME: " + net.getName());
			System.out.println("NETWORK DISPLAY NAME: " + net.getDisplayName());
			System.out.println("NETWORK MAC ADDRESS: " + net.getMacaddr());
			System.out.println("NETWORK IP ADDRESS -4: " + net.getIPv4addr());
			System.out.println("NETWORK IP ADDRESS -6: " + net.getIPv6addr());
			System.out.println("HOSTNAME: " + InetAddress.getLocalHost().getHostName());
			System.out.println("IP ADDRESS: " + InetAddress.getLocalHost().getHostAddress());
			System.out.println("NETWORK SPEED: " + net.getSpeed());

			String ipInfoUrl = "http://ipinfo.io/" + InetAddress.getLocalHost().getHostAddress();
			String googleIpInfoUrl = "http://ipinfo.io/8.8.8.8";
			CurlEmulator curlEmulator = new CurlEmulator();

			System.out.println("ipinfo.io's response about Google.com: " + curlEmulator.get(googleIpInfoUrl).toString());
			System.out.println("ipinfo.io's response for This PC: " + curlEmulator.get(ipInfoUrl).toString());

			System.out.println("NETWORK MTU: " + net.getMTU()
					+ "\n::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
		}
	}

	/**
	 * Test displays
	 */
	@Test
	public void testDisplay() {
		SystemInfo si = new SystemInfo();
		Display[] displays = si.getHardware().getDisplays();
		for (Display d : displays) {
			System.out.println("\n::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\nSYSYTEM INFO DISPLAY: "
					+ d + "\n::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
			assertTrue(d.getEdid().length >= 128);
		}
	}

	public void test_systemPowerUsage() {
		SystemInfo si = new SystemInfo();
		PowerSource[] psArr = si.getHardware().getPowerSources();

		for (PowerSource ps : psArr) {
			assertTrue(ps.getRemainingCapacity() >= 0 && ps.getRemainingCapacity() <= 1);
			System.out.println(
					"\n::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\nPOWER-REMAINING CAPACITY: "
							+ ps.getRemainingCapacity());
			System.out.println("POWER REMAINING TIME: " + ps.getTimeRemaining()
					+ "\n::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
		}
	}

	@Test
	public void test_systemSensors() {
		SystemInfo si = new SystemInfo();
		Sensors s = si.getHardware().getSensors();

		System.out.println(
				"\n::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\nFAN SPEED(rpm): " + s.getFanSpeeds());
		System.out.println("CPU TEMPERATURE (degrees celcius): " + s.getCpuTemperature());
		System.out.println("CPU VOLTAGE(Volts): " + s.getCpuVoltage()
				+ "\n::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
	}

	@Test
	public void test_systemOS() {
		SystemInfo si = new SystemInfo();
		OperatingSystem os = si.getOperatingSystem();
		OperatingSystemVersion version = os.getVersion();

		System.out.println(
				"\n::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\nOS FAMILY: " + os.getFamily());
		System.out.println("OS MANUFACTURER: " + os.getManufacturer());
		System.out.println("OS VERSION NAME: " + version.getCodeName());
		System.out.println("OS VERSION BUILD NUMBER: " + version.getBuildNumber());
		System.out.println("OS VERSION: " + version.getVersion()
				+ "\n::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
	}

	@Test
	public void test_systemDisks() {
		SystemInfo si = new SystemInfo();

		for (HWDiskStore disk : si.getHardware().getDiskStores()) {
			// NOTE: for now, only tests are for getting disk informations
			assertNotNull(disk.getName());
			assertNotNull(disk.getModel());
			assertNotNull(disk.getSerial());
			assertNotNull(disk.getSize());
			assertNotNull(disk.getReads());
			assertNotNull(disk.getWrites());

			System.out.println(
					"\n::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\nDISK NAME: " + disk.getName());
			System.out.println("DISK MODEL: " + disk.getModel());
			System.out.println("DISK SERIAL: " + disk.getSerial());
			System.out.println("DISK SIZE(Bytes): " + disk.getSize()
					+ "\n::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
		}
	}
}
