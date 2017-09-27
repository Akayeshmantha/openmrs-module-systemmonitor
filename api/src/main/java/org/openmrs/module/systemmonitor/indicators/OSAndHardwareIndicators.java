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
import oshi.software.os.OperatingSystem;
import oshi.software.os.OperatingSystemVersion;

public class OSAndHardwareIndicators {
	private SystemInfo si = new SystemInfo();

	private CentralProcessor p = getCentralProcessor();

	private HardwareAbstractionLayer hal = si.getHardware();

	private GlobalMemory memory = getMemory();

	private OperatingSystem os = getOperatingSystem();

	private OperatingSystemVersion version = os != null ? os.getVersion() : null;

	private PowerSource[] psArr = /*
									 * si.getHardware().getPowerSources( )
									 */null;;

	public String PROCESSOR_NAME = getLinuxProcessorName();

	/**
	 * Total Physical Memory (RAM) in Megabytes(MB)
	 */
	public Long MEMORY_TOTAL = memory != null ? memory.getTotal() / 1048576
			: Runtime.getRuntime().maxMemory() / 1048576;

	/**
	 * Used Physical Memory (RAM) in Megabytes(MB)
	 */
	public Long MEMORY_USED = memory != null ? (memory.getTotal() - memory.getAvailable()) / 1048576
			: Runtime.getRuntime().totalMemory() / 1048576;

	/**
	 * Available Physical Memory (RAM) in Megabytes(MB)
	 */
	public Long MEMORY_AVAILABLE = memory != null ? memory.getAvailable() / 1048576
			: Runtime.getRuntime().freeMemory() / 1048576;

	/**
	 * Total Swap memory in Megabytes(MB)
	 */
	public Long MEMORY_SWAP_TOTAL = memory != null ? memory.getSwapTotal() / 1048576 : null;

	/**
	 * Used Swap Memory in Megabytes(MB)
	 */
	public Long MEMORY_SWAP_USED = memory != null ? memory.getSwapUsed() / 1048576 : null;

	/**
	 * Free Swap Memory in Megabytes(MB)
	 */
	public Long MEMORY_SWAP_FREE = memory != null ? (memory.getSwapTotal() - memory.getSwapUsed()) / 1048576 : null;

	/**
	 * CPU Voltage in Volts (V)
	 */
	public Double CPU_VOLTAGE = /* s.getCpuVoltage() */null;

	/**
	 * CPU Temperature in Degrees celsius (°C)
	 */
	public Double CPU_TEMPERATURE = /* s.getCpuTemperature() */null;

	public int[] FAN_SPEED = /* s.getFanSpeeds() */null;

	public String OS_FAMILY = os != null ? os.getFamily() : "";

	public String OS_MANUFACTURER = os != null ? os.getManufacturer() : "";

	public String OS_VERSION_NAME = version != null ? version.getCodeName() : System.getProperty("os.version");

	public String OS_VERSION_BUILDNUMBER = version != null ? version.getBuildNumber() : "";

	public String OS_VERSION_NUMBER = version != null ? version.getVersion() : System.getProperty("os.name");

	/**
	 * Time from when System started in minutes
	 */
	public Long PROCESSOR_SYSTEM_UPTIME = p != null ? p.getSystemUptime() / 60
			: ManagementFactory.getRuntimeMXBean().getUptime() / 60;

	public String getHostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}

	private OperatingSystem getOperatingSystem() {
		OperatingSystem os = null;

		try {
			os = si.getOperatingSystem();
		} catch (NoClassDefFoundError e) {
			// fake Doors OS, TODO do what now?
		} catch (UnsatisfiedLinkError e) {
			e.printStackTrace();
		}
		return os;
	}

	private GlobalMemory getMemory() {
		GlobalMemory memory = null;
		try {
			memory = hal.getMemory();
		} catch (NoClassDefFoundError e) {
			// fake Doors OS, TODO do what now?
		} catch (UnsatisfiedLinkError e) {
			e.printStackTrace();
		}
		return memory;
	}

	public String getIpAddress() throws SocketException, UnknownHostException {
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

	public JSONArray getNetworkInformation() {
		JSONArray json = null;

		try {
			for (NetworkIF net : si.getHardware().getNetworkIFs()) {
				json = new JSONArray();
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
		} catch (NoClassDefFoundError e) {
			e.printStackTrace();
		} catch (UnsatisfiedLinkError e) {
			e.printStackTrace();
		}
		return json;
	}

	public String getMacAddress() {
		String macAdd = "";

		JSONArray networkInformation = getNetworkInformation();

		if (networkInformation != null) {
			for (int i = 0; i < networkInformation.length(); i++) {
				if (networkInformation.getJSONObject(i) != null
						&& StringUtils.isNotBlank(networkInformation.getJSONObject(i).getString("macAddress"))) {
					macAdd = networkInformation.getJSONObject(i).getString("macAddress");
					break;
				}
			}
		}
		return macAdd;
	}

	public JSONArray getDisksInformation() {
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

	public JSONArray getPowerInformation() {
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

	public String getLinuxProcessorName() {
		if (p != null && "Linux".equals(System.getProperties().getProperty("os.name"))
				&& StringUtils.isBlank(p.getName())) {
			String[] cmds = { "/bin/sh", "-c", "cat /proc/cpuinfo | grep 'name' | uniq" };

			try {
				return executeCommand(cmds).replace("model name", "").replace(": ", "").replace(">", "");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return p != null ? p.getName() : "";
		}
		return "";
	}

	private CentralProcessor getCentralProcessor() {
		CentralProcessor p = null;
		try {
			p = si.getHardware().getProcessor();
		} catch (UnsatisfiedLinkError e) {
			// handles java.lang.UnsatisfiedLinkError: C:\Program Files
			// (x86)\Apache Software Foundation\Tomcat
			// 6.0\temp\1472550968944.openmrs-lib-cache\systemmonitor\com\sun\jna\win32-x86\jnidispatch.dll:
			// Can't find dependent libraries thrown on some Windows servers
			// e.printStackTrace();
			p = null;
		} catch (NoClassDefFoundError e) {
			p = null;
		}
		return p;
	}

	private String executeCommand(String[] commands) {
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
