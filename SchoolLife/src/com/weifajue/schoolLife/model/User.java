package com.weifajue.schoolLife.model;

import java.util.List;

/**
 * �û�
 * 
 * @author Lemon
 * 
 */
public class User {

	/** �û�id  **/
	private long id;
	
	/** ����  **/
	private String email;
	
	/** ����   **/
	private String password;
	
	/** mac��ַ   **/
	private String macAddress;
	
	/** ΢������   **/
	private String weiboToken;
	
	/** ��¼�˻�   **/
	private boolean logon;
	
	/** �Ա���Ů����   **/
	private String gender;
	
	/** ѧУ�б�   **/
	private List<Long> schoolIdList;

	/** ���������б�   **/
	private List<Long> sharingIdList;
	
	/** ���������б�   **/
	private List<Long> commentIdList;
	
	/** ѧУ   **/
	private String schoolName;
	
	/** ʡ   **/
	private String provinceName;
	
	/** ��   **/
	private String cityName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public boolean isLogon() {
		return logon;
	}

	public void setLogon(boolean logon) {
		this.logon = logon;
	}

	public String getWeiboToken() {
		return weiboToken;
	}

	public void setWeiboToken(String weiboToken) {
		this.weiboToken = weiboToken;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<Long> getSchoolIdList() {
		return schoolIdList;
	}

	public void setSchoolIdList(List<Long> schoolIdList) {
		this.schoolIdList = schoolIdList;
	}

	public List<Long> getSharingIdList() {
		return sharingIdList;
	}

	public void setSharingIdList(List<Long> sharingIdList) {
		this.sharingIdList = sharingIdList;
	}

	public List<Long> getCommentIdList() {
		return commentIdList;
	}

	public void setCommentIdList(List<Long> commentIdList) {
		this.commentIdList = commentIdList;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

}
