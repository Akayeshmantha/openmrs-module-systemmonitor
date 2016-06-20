package org.openmrs.module.systemmonitor;

import java.util.Properties;

public class SystemPropertiesIndicators {
	private static Properties properties = System.getProperties();

	public static String OSNAME = properties.getProperty("os.name");

	public static String OSARCH = properties.getProperty("os.arch");

	public static String OSVERSION = properties.getProperty("os.version");

	public static String JAVAVERSION = properties.getProperty("java.version");

	public static String JAVAVENDOR = properties.getProperty("java.vendor");

	public static String JVMVERSION = properties.getProperty("java.vm.version");

	public static String JVMVENDOR = properties.getProperty("java.vm.vendor");

	public static String JAVARUNTIMENAME = properties.getProperty("java.runtime.name");

	public static String JAVARUNTIMEVERSION = properties.getProperty("java.runtime.version");

	public static String USERNAME = properties.getProperty("user.name");

	public static String SYSTEMLANGUAGE = properties.getProperty("user.language");

	public static String SYSTEMTIMEZONE = properties.getProperty("user.timezone");

	public static String FILESYSTEMENCODING = properties.getProperty("sun.jnu.encoding");

	public static String USERDIRECTORY = properties.getProperty("user.dir");

	public static String TEMPFOLDER = properties.getProperty("java.io.tmpdir");
}
