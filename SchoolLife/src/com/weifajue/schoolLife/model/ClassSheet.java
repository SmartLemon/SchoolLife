/**
 * 
 */
package com.weifajue.schoolLife.model;

import java.util.Date;
/**
 * @author SmartGang
 *
 */
public class ClassSheet {
	
	private String classSheetName; //�α�����
	private int classSheetID;
	private Date dateStart;//�α���Ч����
	private Date dateEnd;//�α�ʧЧ����
	private int termWeekNum;//ѧ������������ʱ���ã�
	private int maxClassNumPerDay;//����������
	private int minuteForPerClass;//ÿ�ڿζ��ٷ���
	private int classTimeHour[];//ÿ�ڿο�ʼʱ��,Сʱ����
	private int classTimeMinuter[];//ÿ�ڿο�ʼʱ�䣬���Ӳ���
		
	/**
	 * @param classSheetName
	 *���ݿα����ƽ����α�
	 */
	public ClassSheet(String classSheetName) {
		super();
		this.classSheetName = classSheetName;
		dateStart=new Date();
		maxClassNumPerDay=8;
		minuteForPerClass=40;
		classTimeHour=new int[20];
		classTimeMinuter=new int[20];
	}
	public ClassSheet()
	{
		
	}
	
	public String getClassSheetName() {
		return classSheetName;
	}
	public void setClassSheetName(String classSheetName) {
		this.classSheetName = classSheetName;
	}
	public Date getdateStart() {
		return dateStart;
	}
	public void setdateStart(Date dateStart) {
		this.dateStart = dateStart;
	}
	public Date getdateEnd() {
		return dateEnd;
	}
	public void setdateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
	public int getTermWeekNum() {
		return termWeekNum;
	}
	public void setTermWeekNum(int termWeekNum) {
		this.termWeekNum = termWeekNum;
	}
	public int getMaxClassNumPerDay() {
		return maxClassNumPerDay;
	}
	public void setMaxClassNumPerDay(int maxClassNumPerDay) {
		this.maxClassNumPerDay = maxClassNumPerDay;
	}
	public int getMinuteForPerClass() {
		return minuteForPerClass;
	}
	public void setMinuteForPerClass(int minuteForPerClass) {
		this.minuteForPerClass = minuteForPerClass;
	}

	/**
	 * @param classTimeHour the classTimeHour to set
	 */
	public void setClassTimeHour(int classTimeHour[]) {
		this.classTimeHour = classTimeHour;
	}

	/**
	 * @return the classTimeHour
	 */
	public int[] getClassTimeHour() {
		return classTimeHour;
	}

	/**
	 * @param classTimeMinuter the classTimeMinuter to set
	 */
	public void setClassTimeMinuter(int classTimeMinuter[]) {
		for(int i=0;i<classTimeMinuter.length;i++)
		{
			this.classTimeMinuter[i] = classTimeMinuter[i];
		}
	}
	
	public void setClassTimeMinuter(int timeMinuter,int CN)
	{
		if(CN<maxClassNumPerDay)
		{
			classTimeMinuter[CN]=timeMinuter;
		}
	}

	/**
	 * @return the classTimeMinuter
	 */
	public int[] getClassTimeMinuter() {
		return classTimeMinuter;
	}
	
	public int getClassTimeMinuter(int CN)
	{
		if(CN<maxClassNumPerDay)
		{
			return classTimeMinuter[CN];
		}
		else 
		{
			return 40;
		}
	}
	/**
	 * @param classSheetID the classSheetID to set
	 */
	public void setClassSheetID(int classSheetID) {
		this.classSheetID = classSheetID;
	}
	/**
	 * @return the classSheetID
	 */
	public int getClassSheetID() {
		return classSheetID;
	}

}
