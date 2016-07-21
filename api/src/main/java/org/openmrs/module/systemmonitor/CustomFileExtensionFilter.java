package org.openmrs.module.systemmonitor;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import org.apache.commons.lang.StringUtils;

/**
 * TODO extend java.io.FileFilter instead when upgraded to java 7+ and use this
 * filter until which it should be UN-USED
 * 
 * <ul>
 * <li><b><code>javax.swing.filechooser.FileFilter</code></b> - Use with
 * JFileChooser. An abstract class that you must extend, defining
 * <i>accept(f)</i> and <i>getDescription()</i>.</li>
 * <li><b><code>java.io.FileFilter</code></b> - Pass to the File
 * <i>listFiles(ff)</i> method. An interface for which you must define
 * <i>accept(f)</i>.</li>
 * <li><b><code>java.io.FilenameFilter</code></b> - Similar to
 * java.io.FileFilter, in that your can call the File <i>list(fnf)</i> method,
 * but apparently restricts the selection further to one name. Implementing this
 * interface requires defining <i>accept(dir, name)</i>. Doesn't seem to be as
 * generally useful.</li>
 * </ul>
 * 
 * @author k-joseph
 *
 */
public class CustomFileExtensionFilter extends FileFilter {

	private String[] acceptedFileExtensions;

	private String description;

	public CustomFileExtensionFilter(String[] acceptedFileExtensions, String description) {
		this.acceptedFileExtensions = acceptedFileExtensions;
		this.description = description;
	}

	@Override
	public boolean accept(File file) {
		if (acceptedFileExtensions != null && acceptedFileExtensions.length > 0) {
			for (String extension : acceptedFileExtensions) {
				if (file.getName().toLowerCase().endsWith("." + extension)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String getDescription() {
		return StringUtils.isBlank(description) && acceptedFileExtensions != null
				? acceptedFileExtensions.toString() + " files" : description;
	}
}
