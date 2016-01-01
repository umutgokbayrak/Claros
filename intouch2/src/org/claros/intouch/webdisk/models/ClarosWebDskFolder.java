package org.claros.intouch.webdisk.models;

import java.io.File;
import java.util.TreeSet;

public class ClarosWebDskFolder extends ClarosWebDskObject {
	private TreeSet contents = new TreeSet();
	
	public ClarosWebDskFolder(File tmp) {
		super(tmp);
	}

	public TreeSet getContents() {
		return contents;
	}

	public void setContents(TreeSet contents) {
		this.contents = contents;
	}

}
