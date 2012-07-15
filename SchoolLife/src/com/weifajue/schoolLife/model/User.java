package com.weifajue.schoolLife.model;

import java.util.List;

/**
 * 用户
 * 
 * @author Lemon
 * 
 */
public class User {

	/** 用户id  **/
	private long id;
	
	/** 邮箱  **/
	private String email;
	
	/** 密码   **/
	private String password;
	
	/** mac地址   **/
	private String macAddress;
	
	/** 微博密令   **/
	private String weiboToken;
	
	/** 登录账户   **/
	private boolean logon;
	
	/** 性别：男女其他   **/
	private String gender;
	
	/** 学校列表   **/
	private List<Long> schoolIdList;

	/** 分享文章列表   **/
	private List<Long> sharingIdList;
	
	/** 评论文章列表   **/
	private List<Long> commentIdList;
	
	/** 学校   **/
	private String schoolName;
	
	/** 省   **/
	private String provinceName;
	
	/** 市   **/
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
