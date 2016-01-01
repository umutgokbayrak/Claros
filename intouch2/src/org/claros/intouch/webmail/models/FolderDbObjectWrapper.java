package org.claros.intouch.webmail.models;

/**
 * @author Umut Gokbayrak
 */
public class FolderDbObjectWrapper extends FolderDbObject {
	private Integer unreadItemCount;
	private Integer totalItemCount;

	public FolderDbObjectWrapper(FolderDbObject f) {
		this.folderName = f.getFolderName();
		this.id = f.getId();
		this.parentId = f.getParentId();
		this.folderType = f.getFolderType();
		this.username = f.getUsername();
	}

	public Integer getTotalItemCount() {
		return totalItemCount;
	}

	public void setTotalItemCount(Integer totalItemCount) {
		this.totalItemCount = totalItemCount;
	}

	public Integer getUnreadItemCount() {
		return unreadItemCount;
	}

	public void setUnreadItemCount(Integer unreadItemCount) {
		this.unreadItemCount = unreadItemCount;
	}

}
