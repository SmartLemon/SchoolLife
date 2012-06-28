/**
 * 
 */
package com.weifajue.schoolLife.Util;

import java.util.Date;

import android.text.format.Time;

/**
 * @author SmartGang
 *时间处理类，用于支持系统用到的各个与时间有关的计算
 */
public class timeProcess {

	//设置默认开始时间，为2000年1月1日(Date的年份是从1900开始算的，所以2000-1900=100
	final static Date DEFAULT_BEGIN_DATE=new Date(100,0,1);
	//计算两个日期之间相差的天数
	private int dayOffset(Date beginDate,Date endDate)
	{
		long diff;
		//从0点0分0秒开始比较
		beginDate.setHours(0);
		beginDate.setMinutes(0);
		beginDate.setSeconds(0);
		endDate.setHours(0);
		endDate.setMinutes(0);
		endDate.setSeconds(0);
		//先判断哪一天在前
		if(beginDate.after(endDate))
		{
			diff=beginDate.getTime()-endDate.getTime();
		}
		else
		{
			diff=endDate.getTime()-beginDate.getTime();
		}
						//1000*60*60*24
		return Integer.valueOf(String.valueOf(diff/86400000));
	}
	//计算两个日期之间的周差
	public int weekOffset(Date beginDate,Date endDate)
	{
		int dayOffset=dayOffset(beginDate,endDate);
		//从beginDate开始那周算第一周
		return ((dayOffset+beginDate.getDay()-1)/7);
		//getDay返回DAYS_OF_WEEK,从1到7分别如下:
		//SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, and SATURDAY.
	}
	public Time minutesToTime(int minutes)
	{
		Time time=new Time();
		time.minute=minutes%60;
		time.hour=minutes/60;
		return time;
	}
	public int timeToMinutes(Time time)
	{
		return time.hour*60+time.minute;
	}
	//计算出结束时间相对于开始时间为第几周
	public int dateToWeekNum(Date beginDate,Date endDate)
	{
		return 1+weekOffset(beginDate,endDate);
	}
	public int dateToDayNum(Date beginDate,Date endDate)
	{
		return 1+dayOffset(beginDate,endDate);
	}
	public int dateToDefaultWeekNum(Date endDate)
	{
		return 1+weekOffset(DEFAULT_BEGIN_DATE,endDate);
	}
	public int dateToDefaultDayNum(Date endDate)
	{
		return 1+dayOffset(DEFAULT_BEGIN_DATE,endDate);
	}	
	//根据开始时间和第几天，算出当天的日期
	public Date dayNumToDate(Date beginDate,int DN)
	{
		long millionSecond;
		millionSecond=(long)beginDate.getTime()+((long)(DN-1))*86400000;
		return new Date(millionSecond);
	}
	//根据weekNum和weekWD计算dayNum
	//WD按DAYS_OF_WEEK定义,从1到7分别如下:
	//SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, and SATURDAY.
	public int weekAndWeekDayToDayNum(int WN,int WD)
	{
		return (WN-1)*7+WD;
	}

}
