package com.weifajue.schoolLife.model;

/**
 * 评论
 * 
 * @author Lemon
 */
public class Comment {

	/** id */
	private int id;
	
	/**  远程id  **/
	private long remoteId;
	
	/** 内容 */
	private String content;
	
	/** 作者 */
	private String author;
	
	/** 分享id  */
	private long shareingId;
	
	/** like or hate  */
	private long like;

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

	public long getShareingId() {
		return shareingId;
	}

	public void setShareingId(long shareingId) {
		this.shareingId = shareingId;
	}

	public long getLike() {
		return like;
	}

	public void setLike(long like) {
		this.like = like;
	}

	
}
