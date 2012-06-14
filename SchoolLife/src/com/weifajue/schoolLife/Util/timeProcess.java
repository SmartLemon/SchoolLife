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

	//������������֮����������
	public int dayOffset(Date beginDate,Date endDate)
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
		return (int)diff/86400000;
	}
	//������������֮����ܲ�
	public int weekOffset(Date beginDate,Date endDate)
	{
		int dayOffset=dayOffset(beginDate,endDate);
		//��beginDate��ʼ�������һ��
		return ((dayOffset+beginDate.getDay()-1)/7);
		//getDay����DAYS_OF_WEEK,��1��7�ֱ�����:
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
	//���ݿ�ʼʱ��͵ڼ��죬������������
	public Date dayNumToDate(Date beginDate,int DN)
	{
		long millionSecond;
		millionSecond=(long)beginDate.getTime()+((long)(DN-1))*86400000;
		return new Date(millionSecond);
	}
	//����weekNum��weekWD����dayNum
	//WD��DAYS_OF_WEEK����,��1��7�ֱ�����:
	//SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, and SATURDAY.
	public int weekAndWeekDayToDayNum(int WN,int WD)
	{
		return (WN-1)*7+WD;
	}

}
