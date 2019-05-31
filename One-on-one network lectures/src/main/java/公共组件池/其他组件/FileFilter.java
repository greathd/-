package 公共组件池.其他组件;
/**
*
* @author 不识不知 wx:hbhdlhd96
* @version 创建时间：2019年4月28日 下午7:10:21
*/

import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;

public class FileFilter extends javax.swing.filechooser.FileFilter {
	@SuppressWarnings("unused")
	private String TYPE_UNKNOWN = "Type Unknown";
	@SuppressWarnings("unused")
	private String HIDDEN_FILE = "Hidden File";
	private Hashtable<String, FileFilter> filters = null;
	private String description = null;
	private String fullDescription = null;
	private boolean useExtensionsInDescription = true;

	public FileFilter() {
		this.filters = new Hashtable<String, FileFilter>();
	}

	@SuppressWarnings("unused")
	public FileFilter(String extension) {
		this(extension, null);
	}

	public FileFilter(String extension, String description) {
		this();
		if (extension != null)
			addExtension(extension);
		if (description != null)
			setDescription(description);
	}

	@SuppressWarnings("unused")
	public FileFilter(String[] filters) {
		this(filters, null);
	}

	public FileFilter(String[] filters, String description) {
		this();
		for (int i = 0; i < filters.length; i++) {
			// add filters one by one
			addExtension(filters[i]);
		}
		if (description != null)
			setDescription(description);
	}

	public boolean accept(File f) {
		if (f != null) {
			if (f.isDirectory()) {
				return true;
			}
			String extension = getExtension(f);
			if (extension != null && filters.get(getExtension(f)) != null) {
				return true;
			}
			;
		}
		return false;
	}

	public String getExtension(File f) {
		if (f != null) {
			String filename = f.getName();
			int i = filename.lastIndexOf('.');
			if (i > 0 && i < filename.length() - 1) {
				return filename.substring(i + 1).toLowerCase();
			}
			;
		}
		return null;
	}

	public void addExtension(String extension) {
		if (filters == null) {
			filters = new Hashtable<String, FileFilter>(5);
		}
		filters.put(extension.toLowerCase(), this);
		fullDescription = null;
	}

	public String getDescription() {
		if (fullDescription == null) {
			if (description == null || isExtensionListInDescription()) {
				fullDescription = description == null ? "(" : description + " (";
				// build the description from the extension list
				Enumeration<String> extensions = filters.keys();
				if (extensions != null) {
					fullDescription += "." + (String) extensions.nextElement();
					while (extensions.hasMoreElements()) {
						fullDescription += ", ." + (String) extensions.nextElement();
					}
				}
				fullDescription += ")";
			} else {
				fullDescription = description;
			}
		}
		return fullDescription;
	}

	public void setDescription(String description) {
		this.description = description;
		fullDescription = null;
	}

	@SuppressWarnings("unused")
	public void setExtensionListInDescription(boolean b) {
		useExtensionsInDescription = b;
		fullDescription = null;
	}

	public boolean isExtensionListInDescription() {
		return useExtensionsInDescription;
	}

}