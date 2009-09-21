package gmu.swe.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static boolean isTodayOrLater(Date checkDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(checkDate);
		if (cal2.get(Calendar.YEAR) == cal.get(Calendar.YEAR) && cal2.get(Calendar.MONTH) == cal.get(Calendar.MONTH)
				&& cal2.get(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH)) {
			return true;
		}else if(checkDate.after(new Date())){
			return true;
		}
		return false;
	}
}
