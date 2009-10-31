/*
 * Created by: Matt Snyder
 */
package msnydera.swe645.util;

import java.util.ArrayList;

/**
 * Utility class created to extract common code. This class contains utiliy
 * methods for Strings.
 */
public class StringUtils {
	/**
	 * Given a list of messages, this method will return each message on a new
	 * line.
	 * 
	 * @param errors
	 *            List of error messages
	 * @return String of each message on a new line.
	 */
	public static String getFormattedMessages(ArrayList<String> errors) {
		String errorMessage = "";

		for (String error : errors) {
			if (errorMessage.length() > 0) {
				errorMessage += "<br />";
			}
			errorMessage += error;
		}

		return errorMessage;
	}
}
