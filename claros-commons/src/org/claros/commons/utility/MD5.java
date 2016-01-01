package org.claros.commons.utility;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * MD5 Hash Generator
 * 
 * @author Umut Gokbayrak
 *
 */
public interface MD5 {

	/**
	 * Gets this hash sum as an array of 16 bytes.
	 *
	 * @return Array of 16 bytes, the hash of all updated bytes.
	 *
	 * @since ostermillerutils 1.00.00
	 */
	public byte[] getHash();

	/**
	 * Returns 32-character hex representation of this hash.
	 *
	 * @return String representation of this object's hash.
	 *
	 * @since ostermillerutils 1.00.00
	 */
	public String getHashString();

	/**
	 * Gets the MD5 hash of the given byte array.
	 *
	 * @param b byte array for which an MD5 hash is desired.
	 * @return Array of 16 bytes, the hash of all updated bytes.
	 *
	 * @since ostermillerutils 1.00.00
	 */
	public byte[] getHash(byte[] b);

	/**
	 * Gets the MD5 hash of the given byte array.
	 *
	 * @param b byte array for which an MD5 hash is desired.
	 * @return 32-character hex representation the data's MD5 hash.
	 *
	 * @since ostermillerutils 1.00.00
	 */
	public String getHashString(byte[] b);

	/**
	 * Gets the MD5 hash the data on the given InputStream.
	 *
	 * @param in byte array for which an MD5 hash is desired.
	 * @return Array of 16 bytes, the hash of all updated bytes.
	 * @throws IOException if an I/O error occurs.
	 *
	 * @since ostermillerutils 1.00.00
	 */
	public byte[] getHash(InputStream in) throws IOException;

	/**
	 * Gets the MD5 hash the data on the given InputStream.
	 *
	 * @param in byte array for which an MD5 hash is desired.
	 * @return 32-character hex representation the data's MD5 hash.
	 * @throws IOException if an I/O error occurs.
	 *
	 * @since ostermillerutils 1.00.00
	 */
	public String getHashString(InputStream in) throws IOException;

	/**
	 * Gets the MD5 hash of the given file.
	 *
	 * @param f file for which an MD5 hash is desired.
	 * @return Array of 16 bytes, the hash of all updated bytes.
	 * @throws IOException if an I/O error occurs.
	 *
	 * @since ostermillerutils 1.00.00
	 */
	public byte[] getHash(File f) throws IOException;

	/**
	 * Gets the MD5 hash of the given file.
	 *
	 * @param f file array for which an MD5 hash is desired.
	 * @return 32-character hex representation the data's MD5 hash.
	 * @throws IOException if an I/O error occurs.
	 *
	 * @since ostermillerutils 1.00.00
	 */
	public String getHashString(File f) throws IOException;

	/**
	 * Gets the MD5 hash of the given String.
	 * The string is converted to bytes using the current
	 * platform's default character encoding.
	 *
	 * @param s String for which an MD5 hash is desired.
	 * @return Array of 16 bytes, the hash of all updated bytes.
	 *
	 * @since ostermillerutils 1.00.00
	 */
	public byte[] getHash(String s);

	/**
	 * Gets the MD5 hash of the given String.
	 * The string is converted to bytes using the current
	 * platform's default character encoding.
	 *
	 * @param s String for which an MD5 hash is desired.
	 * @return 32-character hex representation the data's MD5 hash.
	 *
	 * @since ostermillerutils 1.00.00
	 */
	public String getHashString(String s);

	/**
	 * Gets the MD5 hash of the given String.
	 *
	 * @param s String for which an MD5 hash is desired.
	 * @param enc The name of a supported character encoding.
	 * @return Array of 16 bytes, the hash of all updated bytes.
	 * @throws UnsupportedEncodingException If the named encoding is not supported.
	 *
	 * @since ostermillerutils 1.00.00
	 */
	public byte[] getHash(String s, String enc) throws UnsupportedEncodingException;

	/**
	 * Gets the MD5 hash of the given String.
	 *
	 * @param s String for which an MD5 hash is desired.
	 * @param enc The name of a supported character encoding.
	 * @return 32-character hex representation the data's MD5 hash.
	 * @throws UnsupportedEncodingException If the named encoding is not supported.
	 *
	 * @since ostermillerutils 1.00.00
	 */
	public String getHashString(String s, String enc) throws UnsupportedEncodingException;

	/**
	 * Reset the MD5 sum to its initial state.
	 *
	 * @since ostermillerutils 1.00.00
	 */
	public void reset();

	/**
	 * Update this hash with the given data.
	 * <p>
	 * If length bytes are not available to be hashed, as many bytes as
	 * possible will be hashed.
	 *
	 * @param buffer Array of bytes to be hashed.
	 * @param offset Offset to buffer array.
	 * @param length number of bytes to hash.
	 *
	 * @since ostermillerutils 1.00.00
	 */
	public void update(byte buffer[], int offset, int length);

	/**
	 * Update this hash with the given data.
	 * <p>
	 * If length bytes are not available to be hashed, as many bytes as
	 * possible will be hashed.
	 *
	 * @param buffer Array of bytes to be hashed.
	 * @param length number of bytes to hash.
	 *
	 * @since ostermillerutils 1.00.00
	 */
	public void update(byte buffer[], int length);

	/**
	 * Update this hash with the given data.
	 *
	 * @param buffer Array of bytes to be hashed.
	 *
	 * @since ostermillerutils 1.00.00
	 */
	public void update(byte buffer[]);

	/**
	 * Updates this hash with a single byte.
	 *
	 * @param b byte to be hashed.
	 *
	 * @since ostermillerutils 1.00.00
	 */
	public void update(byte b);

	/**
	 * Update this hash with a String.
	 * The string is converted to bytes using the current
	 * platform's default character encoding.
	 *
	 * @param s String to be hashed.
	 *
	 * @since ostermillerutils 1.00.00
	 */
	public void update(String s);

	/**
	 * Update this hash with a String.
	 *
	 * @param s String to be hashed.
	 * @param enc The name of a supported character encoding.
	 * @throws UnsupportedEncodingException If the named encoding is not supported.
	 *
	 * @since ostermillerutils 1.00.00
	 */
	public void update(String s, String enc) throws UnsupportedEncodingException;

}