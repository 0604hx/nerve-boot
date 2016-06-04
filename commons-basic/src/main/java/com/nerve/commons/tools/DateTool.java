package com.nerve.commons.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * com.zeus.dpos.commons.tools
 * Created by zengxm on 2016/4/26.
 */
public class DateTool {
	public static final String yyyyMMdd="yyyy-MM-dd";
	public static final String yyyyMMdd_HHmm="yyyy-MM-dd HH:mm";
	public static final String yyyyMMdd_HHmmss="yyyy-MM-dd HH:mm:ss";

	/**
	 * @method name: toDate
	 * @description: 将指定的时间字符串转换为Date对象，默认的时间格式：yyyy-MM-dd hh:mm
	 * @return type: Date
	 *	@param timeString
	 *	@return
	 *	@throws Exception
	 */
	public static Date toDate(String timeString) throws Exception{
		return new SimpleDateFormat(yyyyMMdd_HHmm).parse(timeString);
	}


	public static Date toDate(String timeString, String format) throws Exception{
		return new SimpleDateFormat(format).parse(timeString);
	}

	/**
	 * 获取指定当前时间
	 *	@param timeType 时间串格式
	 *	@return
	 *  @date: 2013-4-9
	 */
	public static String getDate(String timeType) {
		String strTime = null;
		try {
			SimpleDateFormat simpledateformat = new SimpleDateFormat(timeType);
			strTime = simpledateformat.format(new Date());
		} catch (Exception ex) {
			System.err.println("格式化日期错误 : " + ex.getMessage());
		}
		return strTime;
	}

	/**
	 *
	 * @method name: toString
	 * @return type: String
	 *	@param date
	 *	@param format
	 *	@return
	 */
	public static String toString(Date date, String format){
		String strTime = null;
		try {
			SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
			strTime = simpledateformat.format(date);
		} catch (Exception ex) {
			System.err.println("格式化日期错误 : " + ex.getMessage());
		}
		return strTime;
	}

	/**
	 *
	 * @param date
	 * @return
	 */
	public static String toString(Date date){
		String strTime = null;
		try {
			SimpleDateFormat simpledateformat = new SimpleDateFormat(yyyyMMdd_HHmm);
			strTime = simpledateformat.format(date);
		} catch (Exception ex) {
			System.err.println("格式化日期错误 : " + ex.getMessage());
		}
		return strTime;
	}

	/**
	 * 获取当前时间，格式为：yyyy-MM-dd HH:mm:ss
	 *	@return
	 *  @date: 2013-4-9
	 */
	public static String getDate() {
		return getDate(yyyyMMdd_HHmmss);
	}

	/**
	 * 判断是否为当天时间
	 *	@param comparedDate	格式为：yyyy-MM-dd
	 *	@return
	 *  @date: 2013-4-9
	 */
	public static boolean isToday(String comparedDate) {
		return getDate(yyyyMMdd).equals(comparedDate);
	}

	public static String getBeforeDate(String timePos, int day) {
		long dateLong = 0L;
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		if ((timePos == null) || ("".equals(timePos)))
			dateLong = new Date().getTime();
		else {
			try {
				dateLong = dateFormat.parse(timePos).getTime();
			} catch (ParseException e) {
				System.err.println("输入时间\"" + timePos + "\"不合法,parse时间错误 : "
						+ e.getMessage());
				return "0000:00:00 00:00:00";
			}
		}
		dateLong -= day * 24 * 60 * 60 * 1000;
		return dateFormat.format(new Date(dateLong));
	}

	public static String getBeforeDate(String timeType, String timePos, int day) {
		long dateLong = 0L;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeType);

		if ((timePos == null) || ("".equals(timePos)))
			dateLong = new Date().getTime();
		else {
			try {
				dateLong = simpleDateFormat.parse(timePos).getTime();
			} catch (ParseException e) {
				System.err.println("请检查\"" + timePos + "\"和\"" + timeType
						+ "\"的时间格式是否一样,parse时间错误 : " + e.getMessage());
				return "0000:00:00 00:00:00";
			}
		}
		dateLong -= day * 24 * 60 * 60 * 1000;
		return simpleDateFormat.format(new Date(dateLong));
	}

	public static String getAfterDate(String timeType, String timePos, int day) {
		long dateLong = 0L;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeType);

		if ((timePos == null) || ("".equals(timePos)))
			dateLong = new Date().getTime();
		else {
			try {
				dateLong = simpleDateFormat.parse(timePos).getTime();
			} catch (ParseException e) {
				System.err.println("请检查\"" + timePos + "\"和\"" + timeType
						+ "\"的时间格式是否一样,parse时间错误 : " + e.getMessage());
				return "0000:00:00 00:00:00";
			}
		}
		dateLong += day * 24 * 60 * 60 * 1000;
		return simpleDateFormat.format(new Date(dateLong));
	}
}
