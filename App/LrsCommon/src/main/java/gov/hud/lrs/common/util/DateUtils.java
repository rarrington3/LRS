package gov.hud.lrs.common.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

	public static int daysBetween(Date date1, Date date2) {
		return Math.abs(
			(int)ChronoUnit.DAYS.between(
				date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
				date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
			)
		);
	}
	
	public static long minutesBetween(Date date1, Date date2) {
		LocalDateTime ldt1 = LocalDateTime.ofInstant(date1.toInstant(), ZoneId.systemDefault()) ;
		LocalDateTime ldt2 = LocalDateTime.ofInstant(date2.toInstant(), ZoneId.systemDefault()) ;
		return Math.abs(Duration.between(ldt1, ldt2).toMinutes());
	}
	
	public static Date getCurrentDateWithZeroHour () {
		return getDateWithZeroHour (null);
	}

	public static Date getCurrentDateWithLastHour () {
		return getDateWithLastHour(null);
	}

	public static Date getDateWithZeroHour (Date date) {
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date getDateWithLastHour (Date date) {
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date convertDateToNoonUtcDate(Date date) {
		if (date == null) {
			return null;
		}

		Calendar currentCalDate = Calendar.getInstance();
		currentCalDate.setTime(date);
		Calendar utcCalendar = Calendar.getInstance();
		utcCalendar.setTimeZone(TimeZone.getTimeZone("UTC"));
		utcCalendar.set(Calendar.YEAR, currentCalDate.get(Calendar.YEAR));
		utcCalendar.set(Calendar.MONTH, currentCalDate.get(Calendar.MONTH));
		utcCalendar.set(Calendar.DAY_OF_MONTH, currentCalDate.get(Calendar.DAY_OF_MONTH));
		utcCalendar.set(Calendar.HOUR, 0);
		utcCalendar.set(Calendar.MINUTE, 0);
		utcCalendar.set(Calendar.SECOND, 0);
		utcCalendar.set(Calendar.MILLISECOND, 0);
		utcCalendar.set(Calendar.AM_PM, Calendar.PM);
		return utcCalendar.getTime();
	}
	
	public static Date convertDateToNoonUtcDate(int year, int month, int day) {
		Calendar utcCalendar = Calendar.getInstance();
		utcCalendar.setTimeZone(TimeZone.getTimeZone("UTC"));
		utcCalendar.set(Calendar.YEAR, year);
		utcCalendar.set(Calendar.MONTH, month - 1);
		utcCalendar.set(Calendar.DAY_OF_MONTH, day);
		utcCalendar.set(Calendar.HOUR, 0);
		utcCalendar.set(Calendar.MINUTE, 0);
		utcCalendar.set(Calendar.SECOND, 0);
		utcCalendar.set(Calendar.MILLISECOND, 0);
		utcCalendar.set(Calendar.AM_PM, Calendar.PM);
		return utcCalendar.getTime();
	}
	
	/**
	 * When you call this method in month of June the result will be
	 * Last month last day : Wed May 31 23:59:59 EDT 2017
	 */
	public static Date getLastMonthLastDayLastHour () {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	/**
	 * get the current month
	 * @return current month value
	 */
	public static int getCurrentMonth () {
		// Add 1 because Calendar.MONTH starts from 0-11  
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}
}
