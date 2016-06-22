package org.openmrs.module.systemmonitor.indicators;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.json.JSONObject;
import org.openmrs.module.systemmonitor.SystemMonitorConstants;
import org.openmrs.module.systemmonitor.curl.CurlEmulator;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.Sensors;
import oshi.software.os.OperatingSystem;
import oshi.software.os.OperatingSystemVersion;

public class OSAndHardwareIndicators {
	private static SystemInfo si = new SystemInfo();

	private static CentralProcessor p = si.getHardware().getProcessor();

	private static HardwareAbstractionLayer hal = si.getHardware();

	private static GlobalMemory memory = hal.getMemory();

	private static OperatingSystem os = si.getOperatingSystem();

	private static Sensors s = si.getHardware().getSensors();

	private static OperatingSystemVersion version = os.getVersion();

	public static String PROCESSOR_NAME = p.getName();

	public static String PROCESSOR_VENDOR = p.getVendor();

	public static Double PROCESSOR_SYSTEM_LOAD = p.getSystemLoadAverage();

	public static String PROCESSOR_SERIAL_NUMBER = p.getSystemSerialNumber();

	public static Integer PROCESSOR_LOGICAL_COUNT = p.getLogicalProcessorCount();

	public static Integer PROCESSOR_PHYSICAL_COUNT = p.getPhysicalProcessorCount();

	public static Integer PROCESSOR_THREAD_COUNT = p.getThreadCount();

	public static Long MEMORY_TOTAL = memory.getTotal();

	public static Long MEMORY_AVAILABLE = memory.getAvailable();

	public static Long MEMORY_USED = memory.getTotal() - memory.getAvailable();

	public static Long MEMORY_SWAP_TOTAL = memory.getSwapTotal();

	public static Long MEMORY_SWAP_USED = memory.getSwapUsed();

	public static Long MEMORY_SWAP_FREE = memory.getSwapTotal() - memory.getSwapUsed();

	/**
	 * CPU Voltage in Volts (V)
	 */
	public static Double CPU_VOLTAGE = s.getCpuVoltage();

	/**
	 * Temperature in Degrees celsius (°C)
	 */
	public static Double CPU_TEMPERATURE = s.getCpuTemperature();

	public static int[] FAN_SPEED = s.getFanSpeeds();

	public static String OS_FAMILY = os.getFamily();

	public static String OS_MANUFACTURER = os.getManufacturer();

	public static String OS_VERSION_NAME = version.getCodeName();

	public static String OS_VERSION_BUILDNUMBER = version.getBuildNumber();

	public static String OS_VERSION_NUMBER = version.getVersion();

	public static JSONObject IP_INFO = CurlEmulator.get(SystemMonitorConstants.IP_INFO_URL + getIpAddress());

	/**
	 * Time from when System started in seconds
	 */
	public static Long PROCESSOR_SYSTEM_UPTIME = p.getSystemUptime();

	public static String getHostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getIpAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}
}
