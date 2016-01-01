package org.claros.intouch.webdisk.controllers;

import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.StringTokenizer;

import org.claros.intouch.webdisk.models.Thumbnail;

public class ImageController {

	/**
	 * Herhangi bir url'deki resmi okur ve image objesi olarak döner.
	 * 
	 * @return
	 * @throws Exception
	 */
	private static BufferedImage getImage(String path) throws Exception {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.createImage(path);
		MediaTracker mediaTracker = new MediaTracker(new Container());
		mediaTracker.addImage(image, 0);
		mediaTracker.waitForID(0);

		BufferedImage img = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = img.createGraphics();
		graphics2D.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), null);
		try {
			graphics2D.dispose();
		} catch (Exception e) {
			// Do nothing sier
		}
		return img;
	}

	/**
	 * 
	 * @param newWidth
	 * @param newHeight
	 * @param myObjWidth
	 * @param myObjHeight
	 * @param destObjWidth
	 * @param destObjHeight
	 */
	private static String calculateRatio(int myObjWidth, int myObjHeight, int destObjWidth, int destObjHeight) {
		double widthRatio = (double)myObjWidth / (double)destObjWidth;   // 2.2846
		double heightRatio = (double)myObjHeight / (double)destObjHeight;  // 2.02
		
		double ratio = widthRatio;
		if (heightRatio > widthRatio) {
			ratio = heightRatio;
		}
		int newWidth = (int)(myObjWidth / ratio);	// 151
		int newHeight = (int)(myObjHeight / ratio);  // 67
		return newWidth + "_" + newHeight;
	}

	/**
	 * 
	 * @return
	 */
	private static byte[] getCustomThumbImgBytes(String fileName, int width, int height) {
		byte[] bytesOut = null;
		try {
			Thumbnail th = new Thumbnail();
			bytesOut = th.getThumb(fileName, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bytesOut;
	}


	/**
	 * 
	 * @return
	 */
	public static byte[] getImgBytes(String path) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			BufferedInputStream is = new BufferedInputStream(new FileInputStream(path));
			int byte_;
			while ((byte_ = is.read()) != -1) {
				baos.write (byte_);
			}
			is.close();
			baos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
	}
	/**
	 * 
	 * @param width
	 * @param height
	 * @return
	 */
	public static byte[] getCustomImgBytes(String path, int width, int height) {
		byte[] bytesOut = null;
		try {
			BufferedImage img = getImage(path);
			int w = img.getWidth();
			int h = img.getHeight();
			StringTokenizer token = new StringTokenizer(ImageController.calculateRatio(w, h, width, height), "_");
			bytesOut = ImageController.getCustomThumbImgBytes(path, Integer.parseInt(token.nextToken()), Integer.parseInt(token.nextToken()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bytesOut;
	}
}
