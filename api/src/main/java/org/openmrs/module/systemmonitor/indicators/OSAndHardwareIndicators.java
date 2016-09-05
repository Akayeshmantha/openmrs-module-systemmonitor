package org.openmrs.module.systemmonitor.indicators;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.module.systemmonitor.curl.CurlEmulator;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.hardware.PowerSource;
import oshi.hardware.Sensors;
import oshi.software.os.OperatingSystem;
import oshi.software.os.OperatingSystemVersion;

public class OSAndHardwareIndicators {
	private static SystemInfo si = new SystemInfo();

	private static CentralProcessor p = getCentralProcessor();

	private static HardwareAbstractionLayer hal = si.getHardware();

	private static GlobalMemory memory = hal.getMemory();

	private static OperatingSystem os = si.getOperatingSystem();

	private static Sensors s = si.getHardware().getSensors();

	private static OperatingSystemVersion version = os.getVersion();

	private static PowerSource[] psArr = si.getHardware().getPowerSources();

	public static String PROCESSOR_NAME = getLinuxProcessorName();

	public static String PROCESSOR_VENDOR = p != null ? p.getVendor() : null;

	public static Double PROCESSOR_SYSTEM_LOAD = p != null ? p.getSystemLoadAverage() : null;

	public static String PROCESSOR_SERIAL_NUMBER = p != null ? p.getSystemSerialNumber() : null;

	public static Integer PROCESSOR_LOGICAL_COUNT = p != null ? p.getLogicalProcessorCount() : null;

	public static Integer PROCESSOR_PHYSICAL_COUNT = p != null ? p.getPhysicalProcessorCount() : null;

	public static Integer PROCESSOR_THREAD_COUNT = p != null ? p.getThreadCount() : null;

	/**
	 * Total Physical Memory (RAM) in Megabytes(MB)
	 */
	public static Long MEMORY_TOTAL = memory.getTotal() / 1048576;

	/**
	 * Available Physical Memory (RAM) in Megabytes(MB)
	 */
	public static Long MEMORY_AVAILABLE = memory.getAvailable() / 1048576;

	/**
	 * Used Physical Memory (RAM) in Megabytes(MB)
	 */
	public static Long MEMORY_USED = (memory.getTotal() - memory.getAvailable()) / 1048576;

	/**
	 * Total Swap memory in Megabytes(MB)
	 */
	public static Long MEMORY_SWAP_TOTAL = memory.getSwapTotal() / 1048576;

	/**
	 * Used Swap Memory in Megabytes(MB)
	 */
	public static Long MEMORY_SWAP_USED = memory.getSwapUsed() / 1048576;

	/**
	 * Free Swap Memory in Megabytes(MB)
	 */
	public static Long MEMORY_SWAP_FREE = (memory.getSwapTotal() - memory.getSwapUsed()) / 1048576;

	/**
	 * CPU Voltage in Volts (V)
	 */
	public static Double CPU_VOLTAGE = s.getCpuVoltage();

	/**
	 * CPU Temperature in Degrees celsius (°C)
	 */
	public static Double CPU_TEMPERATURE = s.getCpuTemperature();

	public static int[] FAN_SPEED = s.getFanSpeeds();

	public static String OS_FAMILY = os.getFamily();

	public static String OS_MANUFACTURER = os.getManufacturer();

	public static String OS_VERSION_NAME = version.getCodeName();

	public static String OS_VERSION_BUILDNUMBER = version.getBuildNumber();

	public static String OS_VERSION_NUMBER = version.getVersion();

	/**
	 * Time from when System started in minutes
	 */
	public static Long PROCESSOR_SYSTEM_UPTIME = p != null ? p.getSystemUptime() / 60
			: ManagementFactory.getRuntimeMXBean().getUptime() / 60;

	public static String getHostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getIpAddress() throws SocketException, UnknownHostException {
		try {
			@SuppressWarnings("static-access")
			String publicIp = new CurlEmulator().sendNormalHtmlGET("http://ipinfo.io/ip");
			String finalIP = StringUtils.isNotBlank(publicIp) && publicIp.split("\\.").length == 4 ? publicIp
					: InetAddress.getLocalHost().getHostAddress();

			return finalIP;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return InetAddress.getLocalHost().getHostAddress();
	}

	public static JSONArray getNetworkInformation() {
		JSONArray json = new JSONArray();

		for (NetworkIF net : si.getHardware().getNetworkIFs()) {
			JSONObject js = new JSONObject();

			if (net != null) {
				js.put("name", net.getDisplayName());
				js.put("macAddress", net.getMacaddr());
				js.put("speed", net.getSpeed());
				js.put("packetsReceived", net.getPacketsRecv());
				js.put("packetsSent", net.getPacketsSent());
				js.put("mtu", net.getMTU());
				json.put(js);
			}
		}
		return json;
	}

	public static String getMacAddress() {
		String macAdd = null;

		for (int i = 0; i < getNetworkInformation().length(); i++) {
			if (getNetworkInformation().getJSONObject(i) != null
					&& StringUtils.isNotBlank(getNetworkInformation().getJSONObject(i).getString("macAddress"))) {
				macAdd = getNetworkInformation().getJSONObject(i).getString("macAddress");
				break;
			}
		}
		return macAdd;
	}

	public static JSONArray getDisksInformation() {
		JSONArray json = new JSONArray();

		for (HWDiskStore disk : si.getHardware().getDiskStores()) {
			JSONObject js = new JSONObject();

			if (disk != null) {
				js.put("name", disk.getName());
				js.put("model", disk.getModel());
				js.put("serial", disk.getSerial());
				js.put("size", disk.getSize());
				json.put(js);
			}
		}
		return json;
	}

	public static JSONArray getPowerInformation() {
		JSONArray json = new JSONArray();

		for (PowerSource ps : psArr) {
			JSONObject js = new JSONObject();

			if (ps != null) {
				js.put("name", ps.getName());
				js.put("remainingCapacity", ps.getRemainingCapacity());
				js.put("remainingTime", ps.getTimeRemaining());
				json.put(js);
			}
		}
		return json;
	}

	public static String getLinuxProcessorName() {
		if (p != null && "Linux".equals(System.getProperties().getProperty("os.name"))
				&& StringUtils.isBlank(p.getName())) {
			String[] cmds = { "/bin/sh", "-c", "cat /proc/cpuinfo | grep 'name' | uniq" };

			return executeCommand(cmds).replace("model name", "").replace(": ", "").replace(">", "");
		} else {
			return p != null ? p.getName() : "";
		}
	}

	private static CentralProcessor getCentralProcessor() {
		CentralProcessor p = null;
		try {
			p = si.getHardware().getProcessor();
		} catch (UnsatisfiedLinkError e) {
			// handles java.lang.UnsatisfiedLinkError: C:\Program Files
			// (x86)\Apache Software Foundation\Tomcat
			// 6.0\temp\1472550968944.openmrs-lib-cache\systemmonitor\com\sun\jna\win32-x86\jnidispatch.dll:
			// Can't find dependent libraries thrown on some Windows servers
			e.printStackTrace();
			p = null;
		}
		return p;
	}

	private static String executeCommand(String[] commands) {
		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(commands);
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + ">");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString().replace("\n", "").replace("\t", "");

	}
}
