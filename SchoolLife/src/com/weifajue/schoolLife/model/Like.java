package com.weifajue.schoolLife.model;

/**
 * œ≤ª∂
 * 
 * @author Lemon
 *
 */
public class Like {

	private int id;
	
	/** œ≤ª∂ Ã÷—· */
	private boolean like;
	
	/** ”√ªßid */
	private long remoteUserId;
	
	/** π≤œÌid */
	private long remmoteSharingId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isLike() {
		return like;
	}

	public void setLike(boolean like) {
		this.like = like;
	}

	public long getRemoteUserId() {
		return remoteUserId;
	}

	public void setRemoteUserId(long remoteUserId) {
		this.remoteUserId = remoteUserId;
	}

	public long getRemmoteSharingId() {
		return remmoteSharingId;
	}

	public void setRemmoteSharingId(long remmoteSharingId) {
		this.remmoteSharingId = remmoteSharingId;
	}


	
	
}
