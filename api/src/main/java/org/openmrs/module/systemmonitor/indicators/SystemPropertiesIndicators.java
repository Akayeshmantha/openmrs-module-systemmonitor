package org.openmrs.module.systemmonitor.indicators;

import java.util.Properties;

public class SystemPropertiesIndicators {
	private static Properties properties = System.getProperties();

	public static String OS_NAME = properties.getProperty("os.name");

	public static String OS_ARCH = properties.getProperty("os.arch");

	public static String OS_VERSION = properties.getProperty("os.version");

	public static String JAVA_VERSION = properties.getProperty("java.version");

	public static String JAVA_VENDOR = properties.getProperty("java.vendor");

	public static String JVM_VERSION = properties.getProperty("java.vm.version");

	public static String JVM_VENDOR = properties.getProperty("java.vm.vendor");

	public static String JAVA_RUNTIMENAME = properties.getProperty("java.runtime.name");

	public static String JAVA_RUNTIMEVERSION = properties.getProperty("java.runtime.version");

	public static String USERNAME = properties.getProperty("user.name");

	public static String SYSTEM_LANGUAGE = properties.getProperty("user.language");

	public static String SYSTEM_TIMEZONE = properties.getProperty("user.timezone");

	public static String FILESYSTEM_ENCODING = properties.getProperty("sun.jnu.encoding");

	public static String USER_DIRECTORY = properties.getProperty("user.dir");

	public static String TEMP_FOLDER = properties.getProperty("java.io.tmpdir");
}
