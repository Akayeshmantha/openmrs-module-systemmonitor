package org.openmrs.module.systemmonitor;

import java.util.Properties;

public class SystemPropertiesIndicators {
	private Properties properties = System.getProperties();

	public String OSNAME = properties.getProperty("os.name");

	public String OSARCH = properties.getProperty("os.arch");

	public String OSVERSION = properties.getProperty("os.version");

	public String JAVAVERSION = properties.getProperty("java.version");

	public String JAVAVENDOR = properties.getProperty("java.vendor");

	public String JVMVERSION = properties.getProperty("java.vm.version");

	public String JVMVENDOR = properties.getProperty("java.vm.vendor");

	public String JAVARUNTIMENAME = properties.getProperty("java.runtime.name");

	public String JAVARUNTIMEVERSION = properties.getProperty("java.runtime.version");

	public String USERNAME = properties.getProperty("user.name");

	public String SYSTEMLANGUAGE = properties.getProperty("user.language");

	public String SYSTEMTIMEZONE = properties.getProperty("user.timezone");

	public String FILESYSTEMENCODING = properties.getProperty("sun.jnu.encoding");

	public String USERDIRECTORY = properties.getProperty("user.dir");

	public String TEMPFOLDER = properties.getProperty("java.io.tmpdir");
}
