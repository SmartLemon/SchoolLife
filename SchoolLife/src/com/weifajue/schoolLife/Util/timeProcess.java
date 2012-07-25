/**
 * 
 */
package com.weifajue.schoolLife.Util;

import java.util.Date;

import android.text.format.Time;

/**
 * @author SmartGang
 *ʱ�䴦���࣬����֧��ϵͳ�õ��ĸ�����ʱ���йصļ���
 */
public class timeProcess {

	//����Ĭ�Ͽ�ʼʱ�䣬Ϊ2000��1��1��(Date������Ǵ�1900��ʼ��ģ�����2000-1900=100
	final static Date DEFAULT_BEGIN_DATE=new Date(100,0,1);
	//������������֮����������
	private static int dayOffset(Date beginDate,Date endDate)
	{
		long diff;
		//��0��0��0�뿪ʼ�Ƚ�
		beginDate.setHours(0);
		beginDate.setMinutes(0);
		beginDate.setSeconds(0);
		endDate.setHours(0);
		endDate.setMinutes(0);
		endDate.setSeconds(0);
		//���ж���һ����ǰ
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
	//������������֮����ܲ�
	public static int weekOffset(Date beginDate,Date endDate)
	{
		int dayOffset=dayOffset(beginDate,endDate);
		//��beginDate��ʼ�������һ��
		return ((dayOffset+beginDate.getDay()-1)/7);
		//getDay����DAYS_OF_WEEK,��0��6�ֱ�����:
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
	//���������ʱ������ڿ�ʼʱ��Ϊ�ڼ���
	public int dateToWeekNum(Date beginDate,Date endDate)
	{
		return 1+weekOffset(beginDate,endDate);
	}
	public int dateToDayNum(Date beginDate,Date endDate)
	{
		return 1+dayOffset(beginDate,endDate);
	}
	public static int dateToDefaultWeekNum(Date endDate)
	{
		return 1+weekOffset(DEFAULT_BEGIN_DATE,endDate);
	}
	public int dateToDefaultDayNum(Date endDate)
	{
		return 1+dayOffset(DEFAULT_BEGIN_DATE,endDate);
	}	
	//���ݿ�ʼʱ��͵ڼ��죬������������
	public Date dayNumToDate(Date beginDate,int DN)
	{
		long millionSecond;
		millionSecond=(long)beginDate.getTime()+((long)(DN-1))*86400000;
		return new Date(millionSecond);
	}
	//����weekNum��weekWD����dayNum
	//WD��DAYS_OF_WEEK����,��0��6�ֱ�����:
	//SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, and SATURDAY.
	public static int weekAndWeekDayToDayNum(int WN,int WD)
	{
		return WN*7+WD;
	}

	/**����weekNum��dayNum�����dayNum��Ӧ��weekDay
	 * @param WN
	 * @param DN
	 * @return ��0��6�ֱ�����:SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, and SATURDAY.
	 */
	public static int weekAndDayNumToWeekDay(int WN, int DN)
	{
		return DN-WN*7;
	}
	/**
	 * ���ݿ�ʼ���ں��������������DN��Ӧ�����ܼ�
	 * @param beginDate
	 * @param DN
	 * @return WD��DAYS_OF_WEEK����,��1��7�ֱ�����:
	 * SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, and SATURDAY.
	 */
	public int dayNumToWeekDay(Date beginDate, int DN)
	{
		return dayNumToDate(beginDate,DN).getDay();
	}
}
