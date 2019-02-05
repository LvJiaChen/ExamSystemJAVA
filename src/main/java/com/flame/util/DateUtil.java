package com.flame.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/*
	 * 日期差
	 * 
	 * interval： yyyy - 年 m - 月 d - 日 h - 小时 n - 分钟 s - 秒
	 */
	public static int DateDiff(String interval, Date from, Date to) {
		Calendar fromCalendar = Calendar.getInstance();
		Calendar toCalendar = Calendar.getInstance();
		fromCalendar.setTime(from);
		toCalendar.setTime(to);
		int diff = 0;

		if ("d".equals(interval)) {
			int fromDay = fromCalendar.get(Calendar.DAY_OF_YEAR);
			int toDay = toCalendar.get(Calendar.DAY_OF_YEAR);
			diff = toDay - fromDay;
		}

		return diff;
	}

}
