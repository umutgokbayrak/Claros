package org.claros.intouch.webmail.controllers;

import java.util.ArrayList;
import java.util.List;

import org.claros.intouch.webmail.models.FolderDbObject;

/**
 * @author Umut Gokbayrak
 */
public interface FolderController {
	public List getFolders() throws Exception;
	public FolderDbObject getFolder(String folder) throws Exception;
	public List getMailsByFolder(String folder) throws Exception;
	public void createFolder(FolderDbObject item) throws Exception;
	public Integer countUnreadMessages(String folder) throws Exception;
	public Integer countTotalMessages(String folder) throws Exception;
	public void emptyFolder(String folder) throws Exception;
	public void deleteFolder(String folder) throws Exception;
	public void renameFolder(String oldName, String newName) throws Exception;
	public ArrayList getHeadersByFolder(String folder) throws Exception;
	public void createDefaultFolders() throws Exception;
	public FolderDbObject getJunkFolder() throws Exception;
	public FolderDbObject getInboxFolder() throws Exception;
	public FolderDbObject getSentItems() throws Exception;
	public FolderDbObject getTrashFolder() throws Exception;
	public FolderDbObject getDraftsFolder() throws Exception;
	public List getHeadersByFolder(String currFolder, int[] msgs) throws Exception;
}
