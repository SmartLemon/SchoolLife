/**
 * 
 */
package com.weifajue.schoolLife.model;

/**
 * @author SmartGang
 *
 */
public class Class {

	private int weekNum;
	private int weekDay;//星期
	private int classNum;//节序号
	private String className;//课程名称
	private String teacherName;//教师名字
	private int minutesForClass;//上课时间的分钟数(从0点开始的分钟数)
	private String classRoom;//上课地点
	private int homeworkIndex;//对应作业在作业的数据库表中的序号
	private int notesIndex;//对应笔记在笔记数据库中的序号
	private boolean isSingin;//判断该课程是否已经签到
	private int rate;//评分
	private String comment;//评价

	public Class()
	{
		weekNum=1;
	}
	
	public Class(int WN,int WD,int CN,String CNa,String TN)
	{
		weekNum=WN;
		weekDay=WD;
		classNum=CN;
		className=CNa;
		teacherName=TN;
	}

	/**
	 * @return the weekDay
	 */
	public int getWeekDay() {
		return weekDay;
	}

	/**
	 * @param weekDay the weekDay to set
	 */
	public void setWeekDay(int weekDay) {
		this.weekDay = weekDay;
	}

	/**
	 * @return the classNum
	 */
	public int getClassNum() {
		return classNum;
	}

	/**
	 * @param classNum the classNum to set
	 */
	public void setClassNum(int classNum) {
		this.classNum = classNum;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return the teacherName
	 */
	public String getTeacherName() {
		return teacherName;
	}

	/**
	 * @param teacherName the teacherName to set
	 */
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	/**
	 * @return the minutesForClass
	 */
	public int getMinutesForClass() {
		return minutesForClass;
	}

	/**
	 * @param minutesForClass the minutesForClass to set
	 */
	public void setMinutesForClass(int minutesForClass) {
		this.minutesForClass = minutesForClass;
	}

	/**
	 * @return the classRoom
	 */
	public String getClassRoom() {
		return classRoom;
	}

	/**
	 * @param classRoom the classRoom to set
	 */
	public void setClassRoom(String classRoom) {
		this.classRoom = classRoom;
	}

	/**
	 * @return the homeworkIndex
	 */
	public int getHomeworkIndex() {
		return homeworkIndex;
	}

	/**
	 * @param homeworkIndex the homeworkIndex to set
	 */
	public void setHomeworkIndex(int homeworkIndex) {
		this.homeworkIndex = homeworkIndex;
	}

	/**
	 * @return the notesIndex
	 */
	public int getNotesIndex() {
		return notesIndex;
	}

	/**
	 * @param notesIndex the notesIndex to set
	 */
	public void setNotesIndex(int notesIndex) {
		this.notesIndex = notesIndex;
	}

	/**
	 * @return the isSingin
	 */
	public boolean isSingin() {
		return isSingin;
	}

	/**
	 * @param isSingin the isSingin to set
	 */
	public void setSingin(boolean isSingin) {
		this.isSingin = isSingin;
	}

	/**
	 * @return the rate
	 */
	public int getRate() {
		return rate;
	}

	/**
	 * @param rate the rate to set
	 */
	public void setRate(int rate) {
		this.rate = rate;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @param weekNum the weekNum to set
	 */
	public void setWeekNum(int weekNum) {
		this.weekNum = weekNum;
	}

	/**
	 * @return the weekNum
	 */
	public int getWeekNum() {
		return weekNum;
	}
	
}
