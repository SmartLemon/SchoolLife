/** 
 * com.weifajue.schoolLife.data.BasicOpenHelper.java
 * WeiFaJue.com
 */
package com.weifajue.schoolLife.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/** 
 * �������ݿ����
 * 
 * @Lemon
 * create on 2012-7-14 ����9:09:25 
 */
public class BasicOpenHelper extends SQLiteOpenHelper {

	/** weifajue���ݿ��ļ�   **/
	private static final String WEI_FA_JUE_DB = "weifajue.db";
	
	/** �汾   **/
	private static final int VERSION = 1;
	
	public BasicOpenHelper(Context context) {
		super(context, WEI_FA_JUE_DB, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		try {
			db.execSQL(LikeDB.LIKE_TABLE_CREATE_VER1);
			db.execSQL(UserDB.USER_TABLE_CREATE_VER1);
		} catch (Exception e) {
			Log.e("weifajue-exception", e.getStackTrace().toString());
		}
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
