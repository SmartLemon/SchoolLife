package com.weifajue.schoolLife.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 分享
 * 
 * @author Lemon
 *
 */
public class Shareing {

	/** id **/
	private int id;
	
	/** 远程id  **/
	private long remoteId;
	
	/** 文章 **/
	private String content;
	
	/** 作者  **/
	private String author;
	
	/** 类型  **/
	private String type;
	
	/** 喜欢数  **/
	private int likeCount;
	
	/** 讨厌数  **/
	private int hateCount;
	
	/** 缓存评论  **/
	private List<String> cacheCommentList = new ArrayList<String>();
	
	/** 评论列表  **/
	private List<Long> commentList = new ArrayList<Long>();
	
	/** 喜欢的用户列表  **/
	private List<Long> likeUserList = new ArrayList<Long>();
	
	/** 讨厌的用户列表  **/
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
