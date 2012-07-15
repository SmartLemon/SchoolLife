/** 
 * com.weifajue.schoolLife.data.LikeDB.java
 * WeiFaJue.com
 */
package com.weifajue.schoolLife.data;

import java.util.ArrayList;
import java.util.List;

import com.weifajue.schoolLife.model.Like;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * ϲ�� ���ݲ���
 * 
 * @Lemon create on 2012-7-14 ����12:24:43
 */
public class LikeDB {

	/** ���� ϲ�� ���ݿ� **/
	public static final String LIKE_TABLE_CREATE_VER1 = "CREATE TABLE IF NOT EXISTS like_table " +
			"( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"  like INTEGER, " +
			"  remote_user_id INTEGER, " +
			"  remote_shareing_id INTEGER)";

	/** ���� ϲ�� ���� **/
	private static final String LIKE_TABLE_INSERT_VER1 = "INSERT INTO like_table VALUES(null, ?, ?, ?)";

	/** ��ѯ��������   **/
	private static final String LIKE_SELECT_VER1 = "SELECT * FROM like_table";
	
	/** ���ݲ���   **/
	private BasicOpenHelper helper;

	/** ���ݿ�   **/
	private SQLiteDatabase db;

	public LikeDB(Context context) {

		helper = new BasicOpenHelper(context);
		db = helper.getWritableDatabase();
	}

	/**
	 * ��������
	 * 
	 * @param like
	 */
	public void insert(Like like) {

		db.beginTransaction();

		try {

			db.execSQL(
					LIKE_TABLE_INSERT_VER1,
					new Object[] { like.isLike(), like.getRemoteUserId(),
							like.getRemmoteSharingId() });
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}

	/**
	 * ��ѯ��������
	 * @return ��������
	 */
	public List<Like> queryAll() {
		ArrayList<Like> likes = new ArrayList<Like>();
		Cursor c = db.rawQuery(LIKE_SELECT_VER1, null);
		while (c.moveToNext()) {

			Like like = new Like();
			like.setId(c.getInt(c.getColumnIndex("id")));
			
			like.setLike(true);
			if(0 == c.getInt(c.getColumnIndex("like"))) {
				like.setLike(false);
			}

			like.setRemmoteSharingId(c.getLong(c.getColumnIndex("remte_sharing_id")));
			like.setRemoteUserId(c.getLong(c.getColumnIndex("remte_user_id")));
			
			likes.add(like);
		}
		return likes;
	}

	/**
	 * �ر����ݿ�
	 */
	public void closeDB() {
		//TODO �������õĵط��д���ȶ
		try {
			db.close();
		} catch (Exception e) {
			Log.e("weifajue-exception", e.getStackTrace().toString());
		}
	}
	
}
