/**
 * 
 */
package com.weifajue.schoolLife;

/**
 * @author SmartGang
 *
 */
public class Class {

	private int mWeekDay;
	private int mClassNum;
	private String mClassName;
	private String mTeacherName;
	private int mContinuumClass;
	private int mTimeHour;
	private int mTimeMinute;
	public Class(int CN,int WD,String CNa,String TN, int CC)
	{
		mWeekDay=WD;
		mClassNum=CN;
		mClassName=CNa;
		mTeacherName=TN;
		mContinuumClass=CC;
		mTimeHour=9;
		mTimeMinute=0;
	}
	public Class(int CN,int WD,String CNa,String TN, int CC,int TH,int TM)
	{
		mWeekDay=WD;
		mClassNum=CN;
		mClassName=CNa;
		mTeacherName=TN;
		mContinuumClass=CC;
		mTimeHour=TH;
		mTimeMinute=TM;
	}
	
	public void setWeekDay(int WD)
	{
		if(WD>7 || WD<0)
		{
			WD=0;
		}
		mWeekDay=WD;
	}
	
	public void setClassNum(int CN)
	{
		if(CN<0)
		{
			CN=0;
		}
		mClassNum=CN;
	}
	
	public void setClassName(String CN)
	{
		mClassName=CN;
	}
	
	public void setTeacherName(String TN)
	{
		 mTeacherName=TN;
	}
	
	public void setClassTime(int TH,int TM)
	{
		mTimeHour=TH;
		mTimeMinute=TM;
	}

	public void setContinuumClass(int CC)
	{
		if(CC<0)CC=0;
		mContinuumClass=CC;
	}
	
	public int getWeekDay()
	{
		return mWeekDay;
	}
	
	public int getClassNum()
	{
		return mClassNum;
	}
	
	public String getClassName()
	{
		return mClassName;
	}
	
	public String getTeacherName()
	{
		return mTeacherName;
	}
	
	public int getContinuumClass()
	{
		return mContinuumClass;
	}
	
	public int getClassTime()
	{
		return mTimeHour*100+mTimeMinute;
	}
}
