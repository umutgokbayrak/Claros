package org.claros.commons.utility;

public interface CommonUtility {

	/**
	 * General purpose method for replacing all sorts for strings
	 * 
	 * @param str
	 * @param from
	 * @param to
	 * @return Mofied String
	 */
	public String replaceAllOccurances(String str, String from, String to);

	/**
	 * Trims any kind of string from left and right sides. For example
	 * <pre>
	 * 	extendedTrim('...hebelek..' , '.');
	 * </pre>
	 * Returns hebelek
	 * 
	 * @param str
	 * @param trimStr
	 * @return trimmed string
	 */
	public String extendedTrim(String str, String trimStr);

	/**
	 * Sometimes decimal number are malformed and can'not be used for converting to double automatically. 
	 * This will make the number formatted for valid double conversion. 
	 * 
	 * @param number
	 * @return String
	 */
	public Object checkDecimalFormat(Object number);

	/**
	 * <strong>For Turkish Users:</strong> Some Turkish characters are problemmatic and must be replaced by 
	 * their valid equivalents. This is the method doing this. 
	 * 
	 * <strong>For Czech Users:</strong> For this method is used some Czech characters like Yacuate will be 
	 * evaluated wrong. So disable this.
	 * 
	 * @param str
	 * @return
	 */
	public String doCharsetCorrections(String str);

	/**
	 * This will replace HTML special characters with their safe 
	 * unicode replacements.
	 * 
	 * @param input
	 * @return HTML Unicode Compliant HTML String
	 */
	public String htmlSpecialChars(String str);

	/**
	 * This will replace special characters within a regular expression
	 * with their safe unicode replacements.
	 * 
	 * @param str
	 * @return Safe Unicode Text Which can be embedded in an XML
	 */
	public String regExpSpecialChars(String str);

	/**
	 * This will replace XML special characters with their safe 
	 * unicode replacements.
	 * 
	 * @param str
	 * @return
	 */
	public String xmlSpecialChars(String str);

	/**
	 * <strong>For Turkish Users:</strong> Some Turkish characters are problemmatic and must be replaced by 
	 * their valid HTML equivalents. This is the method doing it. 
	 * 
	 * <strong>For Czech Users:</strong> For this method is used some Czech characters like Yacuate will be 
	 * evaluated wrong. So disable this.
	 * 
	 * @param a
	 * @return
	 */
	public String convertTRCharsToHtmlSafe(String str);

	/**
	 * <strong>For Turkish Users:</strong> Some Turkish characters are problemmatic and must be replaced by 
	 * their valid equivalents. This is the reverse method for it. Unicode HTML text is converted into 
	 * Turkish characters.
	 * 
	 * @param a
	 * @return
	 */
	public String convertHtmlSafeToTRChars(String str);
	
	/**
	 * <strong>For Turkish Users:</strong> Turkish only characters are beeing replaced with their
	 * English equivalants. Case is ignored. 
	 * 
	 * @param a
	 * @return
	 */
	public String convertTRCharsToENChars(String str);
	
	/**
	 * <strong>For Turkish Users:</strong> Turkish only characters are beeing replaced with their
	 * English equivalants but Lowercase..
	 * 
	 * @param a
	 * @return Lowercase text with no Turkish characters.
	 */
	public String convertTRCharsToENLowerCaseChars(String str);

}
