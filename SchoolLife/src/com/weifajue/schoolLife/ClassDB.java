/**
 * 
 */
package com.weifajue.schoolLife;

import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
/**
 * @author SmartGang
 *
 */
public class ClassDB extends SQLiteOpenHelper {

	//数据库操作语句
	/*数据库名称*/
	private final static String DATABASE_NAME="ClassDataBase2";
	/*表名称*/	
	private final static String TABLE_NAME ="ClassTable";
	
	private final static int VERSION=2;
	/*字段名称*/
	//classId根据使用ClassNum*7+WeekDay来计算，只保存classId;
	private final static String ID="_id";
	private final static String CLASSID="classID";
	private final static String CLASSNAME="className";
	private final static String TEACHERNAME="teacherName";
	private final static String CONTINUUMCLASS="continuumClass";
	private final static String CLASSTIME="classTime";
	
	//创建表
	private final static String CREATE_TABLE =
		"CREATE TABLE "
		+TABLE_NAME
		+" ("
		+ID+" INTEGER PRIMARY KEY,"
		+CLASSID+" INTERGER,"
		+CLASSNAME+" TEXT,"
		+TEACHERNAME+" TEXT,"
		+CONTINUUMCLASS+" INTERGER,"
		+CLASSTIME+" INTERGER)";
	
	public ClassDB(Context context)
	{
		super(context, DATABASE_NAME, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.d("Database debug", "Creating Table:"+TABLE_NAME);
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String drop="DROP TABLE IF EXISTS "+TABLE_NAME;
		db.execSQL(drop);
		onCreate(db);
	}
/*	private static enum CLASS_TABLE_COLUMM
	{
		ID,
		CLASSID,
		CLASSNAME,
		TEACHERNAME,
		CONTINUUMCLASS
	}
*/
	//根据weekDay和classNum，从数据库中读取该条记录的内容
//	@SuppressWarnings("finally")
	public Class readClass(int CN,int WD)
	{
		SQLiteDatabase pClassDB=null;
		Cursor cursor = null;
		Class C= null;//此处暂未做保护，需要上层调用函数进行判断该返回值是否为空;
		int cid=CN*7+WD;
		Log.d("Database debug", "reading Class"+String.valueOf(cid));
		try
		{
			String[] cidString=new String[1];
			cidString[0]=String.valueOf(cid);
//			pClassDB=SQLiteDatabase.openDatabase(DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.CREATE_IF_NECESSARY);
			pClassDB=this.getReadableDatabase();
//			cursor=pClassDB.query(TABLE_NAME, null, CLASSID+"=?", cidString, null, null, CLASSID);
			cursor=pClassDB.query(TABLE_NAME, null, null, null, null, null, CLASSID);
			if(cursor.getCount()!=0)
			{
				cursor.moveToFirst();
				int columnIndex;
				do//cursor初始应该是指向队头，所以要先do
				{
					columnIndex=cursor.getColumnIndex(CLASSID);
					Log.e("Database debug"," the ColumnIndex is "+String.valueOf(columnIndex));
					Log.e("Database debug"," the Column 2 is "+cursor.getString(2));
					Log.e("Database debug"," the cid is "+String.valueOf(cursor.getInt(columnIndex)));
					if(cursor.getInt(columnIndex)==cid)
					{
						C=new Class(cursor.getInt(1)/7,cursor.getInt(1)%7,cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getInt(5)/100,cursor.getInt(5)%100);
						break;//找到后跳出循环
					}
				}while(cursor.moveToNext());
			}
		}catch(Exception e)
		{
			Log.e("Database debut","Creating database Error while reading");
		}
		finally
		{
			if(cursor!=null)cursor.close();
			pClassDB.close();
		}
		return C;
	}
	//将课程内容写入到数据库中,返回true表示成功,返回false表示失败
//	@SuppressWarnings("finally")
	public boolean writeClass(Class C)
	{
		//INSERT INTO TABLSE_NAME value(cId,'Classname','teacherName',continuumClass);
/*		String insertData="INSERT INTO "
						+TABLE_NAME
						+" values("
						+(C.getClassNum()*7+C.getWeekDay())
						+",'"+C.getClassName()+"','"
						+C.getTeacherName()+"',"
						+C.getContinuumClass()+")";
*/		SQLiteDatabase pClassDB=this.getWritableDatabase();
		Cursor cursor = null;
		int cid=C.getClassNum()*7+C.getWeekDay();
		int ct=C.getClassTime();
		//使用contentValues的方法来插入和更新数据库
		ContentValues cv=new ContentValues();
		cv.put(CLASSID, cid);
		cv.put(CLASSNAME, C.getClassName());
		cv.put(TEACHERNAME, C.getTeacherName());
		cv.put(CONTINUUMCLASS, C.getContinuumClass());
		cv.put(CLASSTIME, ct);
		
		Log.d("Database debug", "writing Class"+String.valueOf(cid));
		try
		{
			String[] cidString=new String[1];
			cidString[0]=String.valueOf(cid);
			cursor=pClassDB.query(TABLE_NAME, null, CLASSID+"=?", cidString, null, null, CLASSID);
			//判断是更新还是新插入
			if(cursor.getCount()!=0)
			{
				Log.d("Database debug", "update Class"+String.valueOf(cid)+"in writing process");
//				pClassDB.update(TABLE_NAME, cv, CLASSID+"="+String.valueOf(cid), null);
				pClassDB.update(TABLE_NAME, cv, CLASSID+"=?", cidString);
			}
			else
			{
				Log.d("Database debug", "insert Class"+String.valueOf(cid)+"in writing process");
				pClassDB.insert(TABLE_NAME, null, cv);
			}
		}catch(Exception e)
		{
			Log.e("Database debut","writing database Error");
		}
		finally
		{
			if(cursor!=null)cursor.close();
			pClassDB.close();
		}
		return true;
	}
/*
	//更新指定课程的内容，返回true表示成功,返回false表示失败
	public boolean updateClass(Class C)
	{
		return true;
	}
*/
	//删除指定的课程，返回true表示成功,返回false表示失败
	public boolean deleteClass(int CN,int WD)
	{
		int cid=CN*7+WD;
		SQLiteDatabase pClassDB=this.getWritableDatabase();
		Log.d("Database debug", "delete Class"+String.valueOf(cid));
		try
		{
			String[] cidString=new String[1];
			cidString[0]=String.valueOf(cid);
//			pClassDB=SQLiteDatabase.openDatabase(DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.CREATE_IF_NECESSARY);
			//直接删除，不做保护
			pClassDB.delete(TABLE_NAME, CLASSID+" =?", cidString);
//			pClassDB.delete(TABLE_NAME, null, null);
		}catch(Exception e)
		{
			Log.e("Database debut","deleting class Error");
		}
		finally
		{
			pClassDB.close();
		}
		return true;
	}

	public Cursor getClassCursor()
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cursor=db.query(TABLE_NAME, null, null, null, null, null, CLASSID);
		return cursor;
	}
}
