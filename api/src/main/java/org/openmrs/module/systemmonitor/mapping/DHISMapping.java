package org.openmrs.module.systemmonitor.mapping;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;
import org.openmrs.module.systemmonitor.SystemMonitorConstants;

/**
 * Extracts stuff out of the systemmonitor mappings file into exposable methods
 * 
 * @author k-joseph
 *
 */
public class DHISMapping {
	private static File mappingFile = SystemMonitorConstants.SYSTEMMONITOR_FINAL_MAPPINGFILE;

	public static String getDHISMappedObjectValue(String objectCode) {
		String objectMappedValue = null;

		if (mappingFile.exists() && mappingFile.isFile() && StringUtils.isNotBlank(objectCode)) {
			objectMappedValue = readMappingsFileLineByLineAndOnbtainCodeValue(objectCode, objectMappedValue);
		}
		return objectMappedValue;
	}

	private static String readMappingsFileLineByLineAndOnbtainCodeValue(String objectCode, String objectMappedValue) {
		FileInputStream fis;
		BufferedReader br;
		try {
			fis = new FileInputStream(mappingFile);
			br = new BufferedReader(new InputStreamReader(fis));
			String line = null;

			while ((line = br.readLine()) != null) {
				if (line.startsWith(objectCode + "=")) {
					objectMappedValue = line.replace(objectCode + "=", "");
				}
			}

			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return objectMappedValue;
	}

}
