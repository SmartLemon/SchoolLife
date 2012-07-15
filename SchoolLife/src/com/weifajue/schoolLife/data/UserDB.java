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
 * 用户数据操作
 * 
 * @Lemon create on 2012-7-14 下午12:28:15
 */
public class UserDB {

	public static final String USER_TABLE_CREATE_VER1 = "CREATE TABLE IF NOT EXISTS user_table ("
			+ "id  INTEGER PRIMARY KEY AUTOINCREMENT, "  /** 用户id **/
			+ "email NVARCHAR(60), " /** 邮箱 **/
			+ "password NVARCHAR(30)," /** 密码 **/
			+ "mac_address NVARCHAR(40), " /** mac地址 **/
			+ "weibo_token  NVARCHAR(60), "
			+ "logon INTEGER," /** 已登录账户 **/
			+ "gender NVARCHAR(20)," /** 性别 **/
			+ "school_id_list NVARCHAR(360)," /** 学校列表 **/
			+ "sharing_id_list  NVARCHAR(360)," /** 分享文章列表 **/
			+ "comment_id_list NVARCHAR(360)," /** 评论文章列表 **/
			+ "school_name NVARCHAR(60)," /** 学校 **/
			+ "province_name NVARCHAR(20)," /** 省 **/
			+ "city_name NVARCHAR(20)" /** 市 **/
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
	 * 插入用户
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
	 * 更新用户
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
			Log.d("用户id", String.valueOf(c.getInt(c.getColumnIndex("id"))));
			Log.d("邮箱", String.valueOf(c.getString(c.getColumnIndex("email"))));
			Log.d("密码",
					String.valueOf(c.getString(c.getColumnIndex("password"))));
			Log.d("mac地址", String.valueOf(c.getString(c
					.getColumnIndex("mac_address"))));
			Log.d("已登录账户", String.valueOf(c.getString(c
					.getColumnIndex("weibo_token"))));
			Log.d("性别", String.valueOf(c.getString(c.getColumnIndex("gender"))));
			Log.d("学校列表", String.valueOf(c.getString(c
					.getColumnIndex("school_id_list"))));
			Log.d("分享文章列表", String.valueOf(c.getString(c
					.getColumnIndex("sharing_id_list"))));
			Log.d("评论文章列表", String.valueOf(c.getString(c
					.getColumnIndex("comment_id_list"))));
			Log.d("学校", String.valueOf(c.getString(c
					.getColumnIndex("school_name"))));
			Log.d("市",
					String.valueOf(c.getString(c.getColumnIndex("city_name"))));
			users.add(user);
		}
		return users;
	}

}
