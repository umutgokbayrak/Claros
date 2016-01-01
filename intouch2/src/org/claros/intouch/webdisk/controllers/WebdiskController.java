package org.claros.intouch.webdisk.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.commons.configuration.Paths;
import org.claros.commons.configuration.PropertyFile;
import org.claros.commons.exception.NoPermissionException;
import org.claros.commons.utility.Utility;
import org.claros.intouch.webdisk.models.ClarosWebDskFile;
import org.claros.intouch.webdisk.models.ClarosWebDskFolder;

public class WebdiskController {
	private static String homeBase;
	private static Log log = LogFactory.getLog(WebdiskController.class);

	/**
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public static File getUserHome(String username) throws Exception {
		String domain = "localhost";
		if (username.indexOf("@") > 0) {
			domain = username.substring(username.indexOf("@") + 1);
			username = username.substring(0, username.indexOf("@"));
		}
		
		// make sure domain dir exists at the file system
		File fDomain = new File(homeBase + "/" + domain);
		if (!fDomain.exists()) {
			fDomain.mkdir();
		}
		
		// access the directory.
		String firstLetter = username.substring(0, 1);

		File f = new File(homeBase + "/" + domain + "/" + firstLetter);
		if (!f.exists()) {
			f.mkdir();
		}
		f = new File(homeBase + "/" + domain + "/" + firstLetter + "/" + username);
		if (!f.exists()) {
			f.mkdir();
		}
		
		String uploadStr = PropertyFile.getConfiguration("/config/config.xml").getString("webdisk.upload-dir-name");
		File uploadDir = new File(homeBase + "/" + domain + "/" + firstLetter + "/" + username + "/" + uploadStr);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
			
			String tmp = Paths.getCfgFolder() + "upload-readme.txt";
			FileOutputStream fo = new FileOutputStream(uploadDir + "/readme.txt");
			FileInputStream fis = new FileInputStream(tmp);
			int i = -1;
			while ((i = fis.read()) != -1) {
				fo.write(i);
			}
			fo.close();
			fis.close();
		}
		
		return f;
	}

	/**
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public static TreeSet getUserFiles(String username) throws Exception {
		File home = getUserHome(username);
		
		TreeSet set = new TreeSet();
		getFolderContents(username, home, set);
		return set;
	}
	
	public static File getUploadDir(String username) throws Exception {
		String domain = "localhost";
		if (username.indexOf("@") > 0) {
			domain = username.substring(username.indexOf("@") + 1);
			username = username.substring(0, username.indexOf("@"));
		}
		
		// make sure domain dir exists at the file system
		File fDomain = new File(homeBase + "/" + domain);
		if (!fDomain.exists()) {
			fDomain.mkdir();
		}
		
		// access the directory.
		String firstLetter = username.substring(0, 1);

		File f = new File(homeBase + "/" + domain + "/" + firstLetter);
		if (!f.exists()) {
			f.mkdir();
		}
		f = new File(homeBase + "/" + domain + "/" + firstLetter + "/" + username);
		if (!f.exists()) {
			f.mkdir();
		}

		String uploadStr = PropertyFile.getConfiguration("/config/config.xml").getString("webdisk.upload-dir-name");
		File uploadDir = new File(homeBase + "/" + domain + "/" + firstLetter + "/" + username + "/" + uploadStr);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		return uploadDir;
	}
	
	/**
	 * 
	 * @param username
	 * @param path
	 * @return
	 * @throws NoPermissionException
	 * @throws Exception
	 */
	public static void getFolderContents(String username, File obj, TreeSet out) throws NoPermissionException, Exception {
		checkPathPermissions(username, obj.getAbsolutePath());

		if (obj.isDirectory()) {
			File allFiles[] = obj.listFiles();
			
			ClarosWebDskFolder fold = new ClarosWebDskFolder(obj);
			
			TreeSet contents = fold.getContents();
			for(File aFile : allFiles){
				getFolderContents(username, aFile, contents);
			}
			fold.setContents(contents);
			out.add(fold);
		} else if (obj.isFile()) {
			out.add(new ClarosWebDskFile(obj));
		}
	}

	/**
	 * 
	 * @param username
	 * @param path
	 * @throws NoPermissionException
	 * @throws Exception
	 */
	public static void checkPathPermissions(String username, String path) throws NoPermissionException, Exception {
		if (path.indexOf("../") >= 0) {
			throw new NoPermissionException();
		}
		if (!correctPath(path).startsWith(homeBase)) {
			throw new NoPermissionException();
		}
		
		File home = getUserHome(username);
		String homePath = home.getAbsolutePath();
		if (!correctPath(path).startsWith(correctPath(homePath))) {
			throw new NoPermissionException();
		}
	}
	
	static {
		try {
			homeBase = PropertyFile.getConfiguration("/config/config.xml").getString("webdisk.home-base");
			homeBase = correctPath(homeBase);
			
			if (homeBase != null) {
				if (homeBase.endsWith("/") || homeBase.endsWith("\\")) {
					homeBase = homeBase.substring(0, homeBase.length() - 1);
				}
				File f = new File(homeBase); 
				if (!f.exists()) {
					log.fatal("home-base does not exist. Webdisk will not be functional");
				}
				if (!f.isDirectory()) {
					log.fatal("home-base is not a directory. Webdisk will not be functional");
				}
				if (!f.canRead() || !f.canWrite()) {
					log.fatal("home-base is not readable/writable. Make sure file system permissions are right. Webdisk will not be functional");
				}
			} else {
				log.fatal("home-base parameter at config.xml is null. Webdisk will not be functional");
			}
		} catch (Exception e) {
			log.fatal("home-base can not be determined. Webdisk will not be functional");
		}
	}

	public static String correctPath(String path) {
		path = Utility.replaceAllOccurances(path, "\\\\\\\\\\", "/");
		path = Utility.replaceAllOccurances(path, "\\\\\\\\", "/");
		path = Utility.replaceAllOccurances(path, "\\\\\\", "/");
		path = Utility.replaceAllOccurances(path, "\\\\", "/");
		path = Utility.replaceAllOccurances(path, "\\", "/");
		return path;
	}

	/**
	 * 
	 * @param username
	 * @param path
	 * @return
	 * @throws Exception 
	 */
	public static File getUserFile(String username, String path) throws Exception {
		if (path.indexOf("../") >= 0) {
			throw new NoPermissionException();
		}

		File userHome = getUserHome(username);
		String homeDir = correctPath(userHome.getAbsolutePath());
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		String pt = homeDir + path;
		
		File f = new File(pt);
		String corrPath = correctPath(f.getAbsolutePath());
		
		checkPathPermissions(username, corrPath);
		
		return f;
	}
}
