package org.claros.commons.utility.impl;

import java.util.Random;

/**
 * Random Number & Text Generators
 * 
 * @author Umut Gokbayrak
 *
 */
public class RandomGenImpl {

	/**
	 * Generates a random text with the specified length.
	 * 
	 * @param n 	length 
	 * @return
	 */
	public static String getRandomText(int n) {
		String chars[] = new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "w", "x", "y", "z",
									   "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "W", "X", "Y", "Z"};

		String res = "";
		for (int i = 0; i < n; i++) {
			int s = (int)(Math.random() * chars.length);
			res += chars[s];
		}
		return new String(res);
	}

	/**
	 * Generates a random int is equal to or less than the number specified as rangeEnd
	 * 
	 * @param rangeEnd	The number will be generates is equal to or less then this number
	 * @return
	 */
	public static int getRandomInt(int rangeEnd) {
		return new Random().nextInt(rangeEnd);
	}
	
	
}
