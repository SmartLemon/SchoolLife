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
	
	public Class(int CN,int WD,String CNa,String TN, int CC)
	{
		mWeekDay=WD;
		mClassNum=CN;
		mClassName=CNa;
		mTeacherName=TN;
		mContinuumClass=CC;
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
		mWeekDay=CN;
	}
	
	public void setClassName(String CN)
	{
		mClassName=CN;
	}
	
	public void setTeacherName(String TN)
	{
		 mTeacherName=TN;
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
}
