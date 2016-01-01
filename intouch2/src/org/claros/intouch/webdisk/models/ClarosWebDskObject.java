package org.claros.intouch.webdisk.models;

import java.io.File;

public abstract class ClarosWebDskObject implements Comparable {
	protected File file;
	protected String name;
	protected String path;
	
	/**
	 * @param tmp
	 */
	public ClarosWebDskObject(File tmp) {
		name = tmp.getName();
		file = tmp;
		path = tmp.getAbsolutePath();
	}

	/**
	 * @return Returns the file.
	 */
	public File getFile() {
		return file;
	}
	/**
	 * @param file The file to set.
	 */
	public void setFile(File file) {
		this.file = file;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object arg0) {
		if (this instanceof ClarosWebDskFolder && arg0 instanceof ClarosWebDskFile) {
			return -1;
		} else if (this instanceof ClarosWebDskFolder && arg0 instanceof ClarosWebDskFolder) {
			return this.path.compareTo(((ClarosWebDskFolder)arg0).getPath());
		} else if (this instanceof ClarosWebDskFile && arg0 instanceof ClarosWebDskFolder) {
			return 1;
		} else if (this instanceof ClarosWebDskFile && arg0 instanceof ClarosWebDskFile) {
			return this.path.compareTo(((ClarosWebDskFile)arg0).getPath());
		}
		return 0;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String toString() {
		if (this instanceof ClarosWebDskFolder) {
			return "folder:" + path;
		} else if (this instanceof ClarosWebDskFile) {
			return "file:" + path;
		}
		return "";
	}
}
