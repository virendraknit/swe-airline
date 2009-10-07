/*
 * Created by: Matt Snyder
 */
package gmu.swe.util;

/**
 * Utility class that extract common functionality for reuse.
 *
 */
public class NumberUtils {
	/**
	 * Utility method that determines if the provided string is a whole number.
	 * Returns true if it is, false if it isn't (this could be because it is
	 * text or a floating number.)
	 * 
	 * @param sNumber
	 *            The String to check
	 * @return True of sNumber is a whole number, otherwise returns false.
	 */
	public static boolean isWholeNumber(String sNumber) {
		try {
			// Test if the value is actually a number
			new Integer(sNumber);
			return true;
		} catch (NumberFormatException e) {
			// Ignore, this means it isn't a whole number.
		} catch (NullPointerException e){
			// Ignore, this means it isn't a whole number.
		}
		return false;
	}
	
	/**
	 * Utility method to determine if the provided currencyAmount is a
	 * {@link Double}. Returns true if it is, false if it isn't.
	 * 
	 * @param currencyAmount
	 *            Value to check.
	 * @return True if currenctyAmount is a {@link Double}, false if it isn't.
	 */
	public static boolean isValidCurrency(String currencyAmount) {
		try {
			// Test if the value is actually a number
			new Double(currencyAmount);
			return true;
		} catch (NumberFormatException e) {
			// Ignore, this meants it isn't a double.
		}
		return false;
	}
}
