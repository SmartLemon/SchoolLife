package com.weifajue.schoolLife.model;

/**
 * 学校
 * 
 * @author Lemon
 */
public class School {

	/** id  */
	private int id;

	/** 远程id   **/
	private long remoteId;
	
	/**  学校名  */
	private String name;

	/**  学校英文名  */
	private String englishName;

	/** 学校短名   */
	private String shortName;

	/** 学校英文短名  **/
	private String shortEnglishName;

	/** 学校地址  **/
	private String local;

	/** 学校简介  **/
	private String brief;

	/** 学校百度网址  **/
	private String baiduUrl;

	/** 学校人人网址  **/
	private String renrenUrl;

	/** 学校朋友网址  **/
	private String pengyouUrl;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getRemoteId() {
		return remoteId;
	}

	public void setRemoteId(long remoteId) {
		this.remoteId = remoteId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getShortEnglishName() {
		return shortEnglishName;
	}

	public void setShortEnglishName(String shortEnglishName) {
		this.shortEnglishName = shortEnglishName;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getBaiduUrl() {
		return baiduUrl;
	}

	public void setBaiduUrl(String baiduUrl) {
		this.baiduUrl = baiduUrl;
	}

	public String getRenrenUrl() {
		return renrenUrl;
	}

	public void setRenrenUrl(String renrenUrl) {
		this.renrenUrl = renrenUrl;
	}

	public String getPengyouUrl() {
		return pengyouUrl;
	}

	public void setPengyouUrl(String pengyouUrl) {
		this.pengyouUrl = pengyouUrl;
	}

	
	
}
