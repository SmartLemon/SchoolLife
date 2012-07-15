package com.weifajue.schoolLife.model;

import java.util.ArrayList;
import java.util.List;

/**
 * ����
 * 
 * @author Lemon
 *
 */
public class Shareing {

	/** id **/
	private int id;
	
	/** Զ��id  **/
	private long remoteId;
	
	/** ���� **/
	private String content;
	
	/** ����  **/
	private String author;
	
	/** ����  **/
	private String type;
	
	/** ϲ����  **/
	private int likeCount;
	
	/** ������  **/
	private int hateCount;
	
	/** ��������  **/
	private List<String> cacheCommentList = new ArrayList<String>();
	
	/** �����б�  **/
	private List<Long> commentList = new ArrayList<Long>();
	
	/** ϲ�����û��б�  **/
	private List<Long> likeUserList = new ArrayList<Long>();
	
	/** ������û��б�  **/
	private List<Long> hateUserList = new ArrayList<Long>();

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public int getHateCount() {
		return hateCount;
	}

	public void setHateCount(int hateCount) {
		this.hateCount = hateCount;
	}

	public List<String> getCacheCommentList() {
		return cacheCommentList;
	}

	public void setCacheCommentList(List<String> cacheCommentList) {
		this.cacheCommentList = cacheCommentList;
	}

	public List<Long> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Long> commentList) {
		this.commentList = commentList;
	}

	public List<Long> getLikeUserList() {
		return likeUserList;
	}

	public void setLikeUserList(List<Long> likeUserList) {
		this.likeUserList = likeUserList;
	}

	public List<Long> getHateUserList() {
		return hateUserList;
	}

	public void setHateUserList(List<Long> hateUserList) {
		this.hateUserList = hateUserList;
	}
	
}
