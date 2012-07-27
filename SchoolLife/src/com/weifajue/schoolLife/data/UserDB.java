/** 
 * com.weifajue.schoolLife.data.UserDB.java
 * WeiFaJue.com
 */
package com.weifajue.schoolLife.data;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.weifajue.schoolLife.model.User;

/**
 * �û����ݲ���
 * 
 * @Lemon create on 2012-7-14 ����12:28:15
 */
public class UserDB {

	public static final String USER_TABLE_CREATE_VER1 = "CREATE TABLE IF NOT EXISTS user_table ("
			+ "id  INTEGER PRIMARY KEY AUTOINCREMENT, "  /** �û�id **/
			+ "email NVARCHAR(60), " /** ���� **/
			+ "password NVARCHAR(30)," /** ���� **/
			+ "mac_address NVARCHAR(40), " /** mac��ַ **/
			+ "weibo_token  NVARCHAR(60), "
			+ "logon INTEGER," /** �ѵ�¼�˻� **/
			+ "gender NVARCHAR(20)," /** �Ա� **/
			+ "school_id_list NVARCHAR(360)," /** ѧУ�б� **/
			+ "sharing_id_list  NVARCHAR(360)," /** ���������б� **/
			+ "comment_id_list NVARCHAR(360)," /** ���������б� **/
			+ "school_name NVARCHAR(60)," /** ѧУ **/
			+ "province_name NVARCHAR(20)," /** ʡ **/
			+ "city_name NVARCHAR(20)" /** �� **/
			+ ")";
	
	private static final String USER_TABLE_INSERT_VER1 = "INSERT INTO user_table VALUES(null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String USER_TABLE_QUERY_ALL = "SELECT * FROM user_table";

	private SQLiteDatabase db;

	private BasicOpenHelper helper;

	public UserDB(Context context) {

		helper = new BasicOpenHelper(context);
		db = helper.getWritableDatabase();
	}

	/**
	 * �����û�
	 * 
	 * @param user
	 */
	public void insert(User user) {

		db.beginTransaction();

		try {

			db.execSQL(
					USER_TABLE_INSERT_VER1,
					new Object[] { user.getEmail(), user.getPassword(),
							user.getMacAddress(), user.getWeiboToken(),
							user.isLogon(), user.getGender(),
							user.getSchoolIdList(), user.getSharingIdList(),
							user.getCommentIdList(), user.getSchoolName(),
							user.getProvinceName(), user.getCityName() });
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}

	/**
	 * �����û�
	 * 
	 * @param user
	 */
	public void update(User user) {

	}

	public List<User> queryAll() {
		ArrayList<User> users = new ArrayList<User>();
		Cursor c = db.rawQuery(USER_TABLE_QUERY_ALL, null);
		while (c.moveToNext()) {

			User user = new User();
			Log.d("�û�id", String.valueOf(c.getInt(c.getColumnIndex("id"))));
			Log.d("����", String.valueOf(c.getString(c.getColumnIndex("email"))));
			Log.d("����",
					String.valueOf(c.getString(c.getColumnIndex("password"))));
			Log.d("mac��ַ", String.valueOf(c.getString(c
					.getColumnIndex("mac_address"))));
			Log.d("�ѵ�¼�˻�", String.valueOf(c.getString(c
					.getColumnIndex("weibo_token"))));
			Log.d("�Ա�", String.valueOf(c.getString(c.getColumnIndex("gender"))));
			Log.d("ѧУ�б�", String.valueOf(c.getString(c
					.getColumnIndex("school_id_list"))));
			Log.d("���������б�", String.valueOf(c.getString(c
					.getColumnIndex("sharing_id_list"))));
			Log.d("���������б�", String.valueOf(c.getString(c
					.getColumnIndex("comment_id_list"))));
			Log.d("ѧУ", String.valueOf(c.getString(c
					.getColumnIndex("school_name"))));
			Log.d("��",
					String.valueOf(c.getString(c.getColumnIndex("city_name"))));
			users.add(user);
		}
		return users;
	}

}
