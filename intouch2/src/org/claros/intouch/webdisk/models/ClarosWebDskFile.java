package org.claros.intouch.webdisk.models;

import java.io.File;

import org.claros.intouch.webmail.controllers.IconController;

public class ClarosWebDskFile extends ClarosWebDskObject {
	private String icon;
	private String mimeType;
	
	public ClarosWebDskFile(File tmp) {
		super(tmp);
		this.mimeType = IconController.findMimeByName(tmp.getName());
		this.icon = IconController.findIconByMime(mimeType);
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

}
