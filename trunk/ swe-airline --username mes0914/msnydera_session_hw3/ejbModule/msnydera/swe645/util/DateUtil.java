/*
 * Created by: Matt Snyder
 */
package msnydera.swe645.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Date utility to extract common code.
 * 
 */
public class DateUtil {
	/**
	 * Returns True if the provided date is today or later. This method only
	 * compares the day, month, and year (i.e. it ignores time).
	 * 
	 * @param checkDate
	 *            Date to check.
	 * @return True if the date is today or later, otherwise returns false.
	 * @throws NullPointerException
	 *             Thrown if the provided date is null.
	 */
	public static boolean isTodayOrLater(Date checkDate) throws NullPointerException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(checkDate);
		if (cal2.get(Calendar.YEAR) == cal.get(Calendar.YEAR) && cal2.get(Calendar.MONTH) == cal.get(Calendar.MONTH)
				&& cal2.get(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH)) {
			return true;
		} else if (checkDate.after(new Date())) {
			return true;
		}
		return false;
	}
}
