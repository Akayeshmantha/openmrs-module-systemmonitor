package org.openmrs.module.systemmonitor.indicators;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang3.StringUtils;
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

	private static CentralProcessor p = si.getHardware().getProcessor();

	private static HardwareAbstractionLayer hal = si.getHardware();

	private static GlobalMemory memory = hal.getMemory();

	private static OperatingSystem os = si.getOperatingSystem();

	private static Sensors s = si.getHardware().getSensors();

	private static OperatingSystemVersion version = os.getVersion();

	private static PowerSource[] psArr = si.getHardware().getPowerSources();

	public static String PROCESSOR_NAME = p.getName();

	public static String PROCESSOR_VENDOR = p.getVendor();

	public static Double PROCESSOR_SYSTEM_LOAD = p.getSystemLoadAverage();

	public static String PROCESSOR_SERIAL_NUMBER = p.getSystemSerialNumber();

	public static Integer PROCESSOR_LOGICAL_COUNT = p.getLogicalProcessorCount();

	public static Integer PROCESSOR_PHYSICAL_COUNT = p.getPhysicalProcessorCount();

	public static Integer PROCESSOR_THREAD_COUNT = p.getThreadCount();

	/**
	 * in Megabytes(MB)
	 */
	public static Long MEMORY_TOTAL = memory.getTotal() / 1048576;

	/**
	 * in Megabytes(MB)
	 */
	public static Long MEMORY_AVAILABLE = memory.getAvailable() / 1048576;

	/**
	 * in Megabytes(MB)
	 */
	public static Long MEMORY_USED = (memory.getTotal() - memory.getAvailable()) / 1048576;

	/**
	 * in Megabytes(MB)
	 */
	public static Long MEMORY_SWAP_TOTAL = memory.getSwapTotal() / 1048576;

	/**
	 * in Megabytes(MB)
	 */
	public static Long MEMORY_SWAP_USED = memory.getSwapUsed() / 1048576;

	/**
	 * in Megabytes(MB)
	 */
	public static Long MEMORY_SWAP_FREE = (memory.getSwapTotal() - memory.getSwapUsed()) / 1048576;

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

	/**
	 * Time from when System started in minutes
	 */
	public static Long PROCESSOR_SYSTEM_UPTIME = p.getSystemUptime() / 60;

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
			@SuppressWarnings("static-access")
			String publicIp = new CurlEmulator().sendNormalHtmlGET("http://ipinfo.io/ip");
			String finalIP = StringUtils.isNotBlank(publicIp) && publicIp.split("\\.").length == 4 ? publicIp
					: InetAddress.getLocalHost().getHostAddress();

			return finalIP;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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
}
