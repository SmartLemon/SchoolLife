/**
 * 
 */
package com.weifajue.schoolLife.data;

import java.util.Date;

import com.weifajue.schoolLife.model.Class;
import com.weifajue.schoolLife.model.ClassSheet;
import com.weifajue.schoolLife.Util.timeProcess;
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

	private final static String DEBUG_TAG="ClassDB";
	
	//数据库操作语句
	/*数据库名称*/
	private final static String DATABASE_NAME="ClassDataBase";
	/*表名称*/	
	private final static String TABLE_NAME ="ClassTable";
	
	private final static int VERSION=1;
	
	private final static String CLASS_SHEET_NAME="ClassSheet";
	
 	private final static String CREATE_CLASS_DETAIL_TABLE_HEAD =
			"CREATE TABLE [";
	private final static String CREATE_CLASS_DETAIL_TABLE_TAIL =
			"] ("
		  +"[ID] INTEGER PRIMARY KEY,"	//0
		  +"[WeekNum] INT,"				//1，本学期第几周
		  +"[DayNum] INT," 				//2，本学期第几天
		  +"[ClassNum] INT," 			//3，本日第几节
		  +"[MinutesForClass] INT," 	//4，本节课开始时间，以6点为起点
		  +"[ClassName] CHAR," 			//5，课名
		  +"[TeacherName] CHAR," 		//6，老师名
		  +"[ClassRoom] CHAR," 			//7，教室
		  +"[isSingIn] BOOLEAN," 		//8，是否已签到，扩展签到功能
		  +"[HomeWorkIndex] INT," 		//9，对应作业的ID，0表示没有作业，用来在Homework表中查找使用
		  +"[NotesIndex] INT," 			//10，对应笔记的ID，查找使用
		  +"[Rating] INT," 				//11，本节课评分
		  +"[Comment] CHAR)"; 			//12，本节课的评论

	private final static String CREATE_CLASS_SHEET_TABLE_HEAD =
			"CREATE TABLE ";
	private final static String CREATE_CLASS_SHEET_TABLE_TAIL =	
			" ("
			  +"SheetID INTEGER PRIMARY KEY," //0
			  +"SheetName TEXT," 				//1
			  +"YearOfStart INTERGER," 			//2
			  +"MonthOfStart INTERGER," 			//3
			  +"DateOfStart INTERGER," 			//4
			  +"MaxClassPerDay INTERGER," 			//5
			  +"MinutersForPerClass INTERGER,"		//6
			  +"MinutesForClass1 INTERGER," 
			  +"MinutesForClass2 INTERGER," 
			  +"MinutesForClass3 INTERGER," 
			  +"MinutesForClass4 INTERGER," 
			  +"MinutesForClass5 INTERGER," 
			  +"MinutesForClass6 INTERGER," 
			  +"MinutesForClass7 INTERGER," 
			  +"MinutesForClass8 INTERGER," 
			  +"MinutesForClass9 INTERGER," 
			  +"MinutesForClass10 INTERGER," 
			  +"MinutesForClass11 INTERGER," 
			  +"MinutesForClass12 INTERGER," 
			  +"MinutesForClass13 INTERGER," 
			  +"MinutesForClass14 INTERGER," 
			  +"MinutesForClass15 INTERGER," 
			  +"MinutesForClass16 INTERGER," 
			  +"MinutesForClass17 INTERGER," 
			  +"MinutesForClass18 INTERGER," 
			  +"MinutesForClass19 INTERGER," 
			  +"MinutesForClass20 INTERGER)"; 
	private final static String CREATE_HOMEWORK_TABLE_HEAD =
			"CREATE TABLE [";
	private final static String CREATE_HOMEWORK_TABLE_TAIL=
			"] ("
			  +"[HomeWorkID] INT," 
			  +"[CorrespondSheetID] INT," 
			  +"[CorrespondClassID] INT," 
			  +"[PictureName] CHAR," 
			  +"[RecordName] CHAR," 
			  +"[VideoName] CHAR)"; 
	private final static String CREATE_NOTES_TABLE_HEAD=
			"CREATE TABLE [";
	private final static String CREATE_NOTES_TABLE_TAIL=
			"] ("
			  +"[NotesID] INT," 
			  +"[CorrespondSheetID] INT," 
			  +"[CorrespondClassID] INT," 
			  +"[PictureName] CHAR," 
			  +"[RecordName] CHAR," 
			  +"[VideoName] CHAR)";

//	private String currentClassSheetName;
//	private int currentClassSheetID;
//	private String currentClassDetailTableName;
//	private String currentHomeworkTableName;
//	private String currentNotesTableName;
/*	
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
*/	
	public ClassDB(Context context)
	{
		super(context, DATABASE_NAME, null, VERSION);
		// TODO Auto-generated constructor stub
//		LocalFile localfile=new LocalFile();
//		loclfile.setCurrentClassSheetName("hello123");
//		currentClassSheetName=loclfile.getCurrentClassSheetName();
		Log.e(DEBUG_TAG, "Running in ClassDB()");
//		currentClassSheetName="hello123";
//		setTableName();
	}
	//onCreate函数只在数据库第一次被创建时调用，后面将不会再调用
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.e(DEBUG_TAG, "Running in onCreate");
		Log.d(DEBUG_TAG, "Creating Table");
		db.execSQL(CREATE_CLASS_SHEET_TABLE_HEAD+CLASS_SHEET_NAME+CREATE_CLASS_SHEET_TABLE_TAIL);
		String[] string=new String[1];
		string[0]="default";
//		Cursor cursor1=db.query(CLASS_SHEET_NAME, null, "SheetName"+"=?", string, null, null, null);
 		Cursor cursor1=db.query(CLASS_SHEET_NAME, null, null, null, null, null, null);
 		if(cursor1.getCount()==0)//没有找到，表示是第一次创建
		{	//在ClassSheet列表中新建一个列表
			ClassSheet cs=new ClassSheet("default");
			writeClassSheet(db,cs);
		}
	}                  

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String drop="DROP TABLE IF EXISTS "+TABLE_NAME;
		db.execSQL(drop);
		onCreate(db);
	}
	
	//提供按天数、节读取功能，读取某天某节课
	public Class readClass(String currentClassSheetName,int WN,int WD,int CN)
	{
		SQLiteDatabase pClassDB=null;
		Cursor cursor=null;
		Class C= null;//此处暂未做保护，需要上层调用函数进行判断该返回值是否为空;
//		int cid=CN*7+WD;
		int daynum=timeProcess.weekAndWeekDayToDayNum(WN, WD);
		String sel="DayNum=? and ClassNum=?";
		String[] selArgs=new String[2];
		selArgs[0]=String.valueOf(daynum);
		selArgs[1]=String.valueOf(CN);
		Log.d(DEBUG_TAG, "reading Class");
		try
		{
			pClassDB=this.getReadableDatabase();
			String[] selArgsShhetName=new String[1];
			selArgsShhetName[0]=currentClassSheetName;
			cursor=pClassDB.query(CLASS_SHEET_NAME, null,"SheetName=?", selArgsShhetName, null, null, null);
			cursor.moveToFirst();
			int sheetIDColumnIndex=cursor.getColumnIndex("SheetID");
			int classSheetID=cursor.getInt(sheetIDColumnIndex);
			String tableName="CDT"+String.valueOf(classSheetID);
			cursor=pClassDB.query(tableName, null, sel, selArgs, null, null, "ID");
			if(cursor.getCount()!=0)
			{
				cursor.moveToFirst();
				Log.e(DEBUG_TAG,"reading class success:"+selArgs[0]+" "+selArgs[1]);
				C=new Class(WN,WD,CN,cursor.getString(5),cursor.getString(6));
				C.setMinutesForClass(cursor.getInt(4));
				C.setClassRoom(cursor.getString(7));
				if(cursor.getInt(8)==1)C.setSingin(true);
				else C.setSingin(false);
				C.setHomeworkIndex(cursor.getInt(9));
				C.setNotesIndex(cursor.getInt(10));
				C.setRate(cursor.getInt(11));
				C.setComment(cursor.getString(12));
			}
		}catch(Exception e)
		{
			Log.e(DEBUG_TAG,"Creating database Error while reading");
		}
		finally
		{
			if(cursor!=null)cursor.close();
			pClassDB.close();
		}
		return C;
	}
	
	public Class readClass(String currentClassSheetName,int WD,int CN)
	{
		SQLiteDatabase pClassDB=null;
		Cursor cursor=null;
		Class C= null;//此处暂未做保护，需要上层调用函数进行判断该返回值是否为空;
		int WN=timeProcess.dateToDefaultWeekNum(new Date());
		int daynum=timeProcess.weekAndWeekDayToDayNum(WN, WD);
		String sel="DayNum=? and ClassNum=?";
		String[] selArgs=new String[2];
		selArgs[0]=String.valueOf(daynum);
		selArgs[1]=String.valueOf(CN);
		Log.d(DEBUG_TAG, "reading Class");
		try
		{
			pClassDB=this.getReadableDatabase();
			String[] selArgsShhetName=new String[1];
			selArgsShhetName[0]=currentClassSheetName;
			cursor=pClassDB.query(CLASS_SHEET_NAME, null,"SheetName=?", selArgsShhetName, null, null, null);
			cursor.moveToFirst();
			int sheetIDColumnIndex=cursor.getColumnIndex("SheetID");
			int classSheetID=cursor.getInt(sheetIDColumnIndex);
			String tableName="CDT"+String.valueOf(classSheetID);
			cursor=pClassDB.query(tableName, null, sel, selArgs, null, null, "ID");
			if(cursor.getCount()!=0)
			{
				cursor.moveToFirst();
				Log.e(DEBUG_TAG,"reading class success:"+selArgs[0]+" "+selArgs[1]);
				C=new Class(WN,WD,CN,cursor.getString(5),cursor.getString(6));
				C.setMinutesForClass(cursor.getInt(4));
				C.setClassRoom(cursor.getString(7));
				if(cursor.getInt(8)==1)C.setSingin(true);
				else C.setSingin(false);
				C.setHomeworkIndex(cursor.getInt(9));
				C.setNotesIndex(cursor.getInt(10));
				C.setRate(cursor.getInt(11));
				C.setComment(cursor.getString(12));
			}
		}catch(Exception e)
		{
			Log.e(DEBUG_TAG,"Creating database Error while reading");
		}
		finally
		{
			if(cursor!=null)cursor.close();
			pClassDB.close();
		}
		return C;
	}
	
	//
	
	/**
	 * @param currentClassSheetName
	 * @param WN
	 * @param WD
	 * @return 读取到的列表句柄
	 */
	public Class[] readClassDayList(String currentClassSheetName,int WN,int WD)
	{
		SQLiteDatabase pClassDB=null;
		Cursor cursor=null;
		Class[] classList=null;
		String sel="DayNum=?";
		String[] selArgs=new String[1];
		int DN=timeProcess.weekAndWeekDayToDayNum(WN, WD);
		selArgs[0]=String.valueOf(DN);
		Log.d(DEBUG_TAG, "reading Class Day List"+selArgs[0]);
		try
		{
			pClassDB=this.getReadableDatabase();
			pClassDB.beginTransaction();
			String[] selArgsShhetName=new String[1];
			selArgsShhetName[0]=currentClassSheetName;
			cursor=pClassDB.query(CLASS_SHEET_NAME, null,"SheetName=?", selArgsShhetName, null, null, null);
			cursor.moveToFirst();
			int sheetIDColumnIndex=cursor.getColumnIndex("SheetID");
			int classSheetID=cursor.getInt(sheetIDColumnIndex);
			String tableName="CDT"+String.valueOf(classSheetID);
			int maxClassNumPerDay=cursor.getInt(5);//读每天最多节数
			cursor=pClassDB.query(tableName, null, sel, selArgs, null, null, "ClassNum");
//			cursor=pClassDB.query(tableName, null, null, null, null, null, "ClassNum");
			cursor.moveToFirst();
			int count=cursor.getCount();
			classList=new Class[maxClassNumPerDay];
			if(count>0)
			{
				
				Log.e(DEBUG_TAG,"reading class success:"+selArgs[0]);
				for(int i=0;i<maxClassNumPerDay;i++)
				{
					int classNum=cursor.getInt(3);
					if(i==classNum)
					{
						classList[i]=new Class(WN,WD,classNum,cursor.getString(5),cursor.getString(6));
						classList[i].setMinutesForClass(cursor.getInt(4));
						classList[i].setClassRoom(cursor.getString(7));
						if(cursor.getInt(8)==1)classList[i].setSingin(true);
						else classList[i].setSingin(false);
						classList[i].setHomeworkIndex(cursor.getInt(9));
						classList[i].setNotesIndex(cursor.getInt(10));
						classList[i].setRate(cursor.getInt(11));
						classList[i].setComment(cursor.getString(12));
						if(cursor.moveToNext()==false)break;
					}
				}
				pClassDB.setTransactionSuccessful();
				pClassDB.endTransaction(); 
			}
		}catch(Exception e)
		{
			Log.e(DEBUG_TAG,"Creating database Error while reading");
		}
		finally
		{
			if(cursor!=null)cursor.close();
			pClassDB.close();
		}
		return classList;
	}
	//
	
	/**
	 * 根据给定的表名和节数，读出该节在模板中一周7天的内容(相当于横向读出）
	 * @param sheetName 
	 * @param CN
	 * @param WN
	 * @return 读出列表的句柄
	 */
	public Class[] readClassByNumList(String sheetName,int WN,int CN)
	{
		SQLiteDatabase pClassDB=null;
		Cursor cursor=null;
		Class[] classList=null;
		String sel="WeekNum=? and ClassNum=?";
		String[] selArgs=new String[2];
		selArgs[0]=String.valueOf(WN);
		selArgs[1]=String.valueOf(CN);
		Log.d(DEBUG_TAG, "reading Class Num List"+selArgs[1]);
		try
		{
			pClassDB=this.getReadableDatabase();
			//以下代码读取课表ID，并生成detail表名
			String[] selArgsSheetName=new String[1];
			selArgsSheetName[0]=sheetName;
			cursor=pClassDB.query(CLASS_SHEET_NAME, null,"SheetName=?", selArgsSheetName, null, null, null);
			cursor.moveToFirst();
			int sheetIDColumnIndex=cursor.getColumnIndex("SheetID");
			int classSheetID=cursor.getInt(sheetIDColumnIndex);
			String tableName="CDT"+String.valueOf(classSheetID);//生成detail表名
			cursor=pClassDB.query(tableName, null, sel, selArgs, null, null, "DayNum");
			cursor.moveToFirst();
			int count=cursor.getCount();
			classList=new Class[7];
			if(count>0)
			{
				Log.e(DEBUG_TAG,"reading class success:"+selArgs[0]);
				for(int i=0;i<7&&cursor!=null;i++)
				{	//对classList进行填充，如果该天没有课，则空过
					int DN=cursor.getInt(2);
					int WD=timeProcess.weekAndDayNumToWeekDay(WN, DN);
					if(WD==i)
					{
						classList[i]=new Class(WN,WD,cursor.getInt(3),cursor.getString(5),cursor.getString(6));
						classList[i].setMinutesForClass(cursor.getInt(4));
						classList[i].setClassRoom(cursor.getString(7));
						if(cursor.getInt(8)==1)classList[i].setSingin(true);
						else classList[i].setSingin(false);
						classList[i].setHomeworkIndex(cursor.getInt(9));
						classList[i].setNotesIndex(cursor.getInt(10));
						classList[i].setRate(cursor.getInt(11));
						classList[i].setComment(cursor.getString(12));
						if(cursor.moveToNext()==false)break;
					}
				}
			}
		}catch(Exception e)
		{
			Log.e(DEBUG_TAG,"Creating database Error while reading");
		}
		finally
		{
			if(cursor!=null)cursor.close();
			pClassDB.close();
		}
		return classList;
	}
	//将课程内容写入到数据库中,返回true表示成功,返回false表示失败
	public boolean writeClass(String currentClassSheetName,Class C)
	{
		SQLiteDatabase pClassDB=this.getWritableDatabase();
		Cursor cursor = null;
		int daynum=timeProcess.weekAndWeekDayToDayNum(C.getWeekNum(), C.getWeekDay());
		//使用contentValues的方法来插入和更新数据库
		ContentValues cv=new ContentValues();
		cv.put("WeekNum", C.getWeekNum());
		cv.put("DayNum", daynum);
		cv.put("ClassNum", C.getClassNum());
		cv.put("MinutesForClass", C.getMinutesForClass());
		cv.put("ClassName", C.getClassName());
		cv.put("TeacherName", C.getTeacherName());
		cv.put("ClassRoom", C.getClassRoom());
		if(C.isSingin()==true)cv.put("isSingIn", 1);
		else cv.put("isSingIn", 0);
		cv.put("HomeWorkIndex",C.getHomeworkIndex());
		cv.put("NotesIndex",C.getNotesIndex());
		cv.put("Rating", C.getRate());
		cv.put("Comment", C.getComment());
		Log.d(DEBUG_TAG, "writing Class"+C.getClassName());
		try
		{
			//通地danyNum和ClassNum来唯一定位一节课
			String sel="DayNum=? and ClassNum=?";
			String[] selArgs=new String[2];
			selArgs[0]=String.valueOf(daynum);
			selArgs[1]=String.valueOf(C.getClassNum());
			String[] selArgsShhetName=new String[1];
			selArgsShhetName[0]=currentClassSheetName;
			cursor=pClassDB.query(CLASS_SHEET_NAME, null,"SheetName=?", selArgsShhetName, null, null, null);
			cursor.moveToFirst();
			int sheetIDColumnIndex=cursor.getColumnIndex("SheetID");
			int classSheetID=cursor.getInt(sheetIDColumnIndex);
			String tableName="CDT"+String.valueOf(classSheetID);			
			cursor=pClassDB.query(tableName, null, sel, selArgs, null, null, "ID");
			//判断是更新还是新插入
			if(cursor.getCount()!=0)
			{
				Log.d(DEBUG_TAG , "update Class in writing process");
				pClassDB.update(tableName, cv, sel, selArgs);
			}
			else
			{
				Log.d(DEBUG_TAG, "insert Class in writing process");
				pClassDB.insert(tableName, null, cv);
			}
		}catch(Exception e)
		{
			Log.e(DEBUG_TAG,"writing database Error");
		}
		finally
		{
			if(cursor!=null)cursor.close();
			pClassDB.close();
		}
		return true;
	}

	//
	
	/**根据给定的表名、周和节数，横向写入一周的课表内容
	 * @param sheetName
	 * @param WN
	 * @param CN
	 * 首先获取到该detail表的表名
	 * 遍历classList，判断是否为空，如不为空则执行插入操作
	 * 使用如下方法来提高批量插入的效率：
	 * dataBase.beginTransaction();        //手动设置开始事务
	 * --------数据插入操作循环--------
	 * dataBase.setTransactionSuccessful();        //设置事务处理成功，不设置会自动回滚不提交
	 * dataBase.endTransaction();        //处理完
	 */
	public void writeClassByNumList(String sheetName,int WN, int CN, Class[] classList)
	{
		SQLiteDatabase pClassDB=null;
		Cursor cursor=null;

		Log.d(DEBUG_TAG, "writing Class Num List"+WN+" "+CN);
		try
		{
			pClassDB=this.getReadableDatabase();
			//以下代码读取课表ID，并生成detail表名
			String[] selArgsSheetName=new String[1];
			selArgsSheetName[0]=sheetName;
			cursor=pClassDB.query(CLASS_SHEET_NAME, null,"SheetName=?", selArgsSheetName, null, null, null);
			cursor.moveToFirst();
			int sheetIDColumnIndex=cursor.getColumnIndex("SheetID");
			int classSheetID=cursor.getInt(sheetIDColumnIndex);
			String tableName="CDT"+String.valueOf(classSheetID);//生成detail表名
			//通过dayNum和ClassNum来唯一定位一节课，dayNum在for循环中赋值
			String[] selArgs=new String[2];	
			selArgs[0]=String.valueOf(WN);
			selArgs[1]=String.valueOf(CN);
			pClassDB.beginTransaction();
			//先册除，再添加
			pClassDB.delete(tableName, "WeekNum=? and ClassNum=?", selArgs);
			for(int i=0;i<7;i++)
			{
				if(classList[i]!=null)
				{	
					//生成dayNum
					int daynum=timeProcess.weekAndWeekDayToDayNum(WN, i);
					selArgs[0]=String.valueOf(daynum);//设计检索条件的dayNum
					ContentValues cv=new ContentValues();
					cv.put("WeekNum", WN);
					cv.put("DayNum", daynum);
					cv.put("ClassNum", CN);
					cv.put("MinutesForClass", classList[i].getMinutesForClass());
					cv.put("ClassName", classList[i].getClassName());
					cv.put("TeacherName", classList[i].getTeacherName());
					cv.put("ClassRoom", classList[i].getClassRoom());
					if(classList[i].isSingin()==true)cv.put("isSingIn", 1);
					else cv.put("isSingIn", 0);
					cv.put("HomeWorkIndex",classList[i].getHomeworkIndex());
					cv.put("NotesIndex",classList[i].getNotesIndex());
					cv.put("Rating", classList[i].getRate());
					cv.put("Comment", classList[i].getComment());
					Log.d(DEBUG_TAG, "writing Class"+classList[i].getClassName());
					//前面已经删除掉，所以这里不用再做添加或者更新的判断
					Log.d(DEBUG_TAG, "insert Class in writing process");
					pClassDB.insert(tableName, null, cv);
				}
			}
			pClassDB.setTransactionSuccessful(); 	
			pClassDB.endTransaction();
		}catch(Exception e)
		{
			Log.e(DEBUG_TAG,"Creating database Error while reading");
		}
		finally
		{
			if(cursor!=null)cursor.close();
			pClassDB.close();
		}		
	}
	
	//
	public void writeCassByNumListIntoTemplate(String sheetName,int CN, Class[] classList)
	{
		writeClassByNumList(sheetName,0,CN,classList);
	}
	
	//删除指定的课程，返回true表示成功,返回false表示失败
	public boolean deleteClass(String currentClassSheetName,int WN,int WD,int CN)
	{		
		int daynum=timeProcess.weekAndWeekDayToDayNum(WN,WD);
		SQLiteDatabase pClassDB=this.getWritableDatabase();
		Log.d(DEBUG_TAG, "delete Class"+String.valueOf(WN)+" "+String.valueOf(WD)+" "+String.valueOf(CN));
		try
		{
			String sel="DayNum=? and ClassNum=?";
			String[] selArgs=new String[2];
			selArgs[0]=String.valueOf(daynum);
			selArgs[1]=String.valueOf(CN);
			String[] selArgsShhetName=new String[1];
			selArgsShhetName[0]=currentClassSheetName;
			Cursor cursor=pClassDB.query(CLASS_SHEET_NAME, null,"SheetName=?", selArgsShhetName, null, null, null);
			cursor.moveToFirst();
			int sheetIDColumnIndex=cursor.getColumnIndex("SheetID");
			int classSheetID=cursor.getInt(sheetIDColumnIndex);
			String tableName="CDT"+String.valueOf(classSheetID);	
//			pClassDB=SQLiteDatabase.openDatabase(DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.CREATE_IF_NECESSARY);
			//直接删除，不做保护
			pClassDB.delete(tableName, sel, selArgs);
//			pClassDB.delete(TABLE_NAME, null, null);
		}catch(Exception e)
		{
			Log.e(DEBUG_TAG,"deleting class Error");
		}
		finally
		{
			pClassDB.close();
		}
		return true;
	}
	//删除指定的课程，返回true表示成功,返回false表示失败
	public boolean deleteClass(String currentClassSheetName,int WD,int CN)
	{	
		int WN=timeProcess.dateToDefaultWeekNum(new Date());
		int daynum=timeProcess.weekAndWeekDayToDayNum(WN,WD);
		SQLiteDatabase pClassDB=this.getWritableDatabase();
		Log.d(DEBUG_TAG, "delete Class"+String.valueOf(WN)+" "+String.valueOf(WD)+" "+String.valueOf(CN));
		try
		{
			String sel="DayNum=? and ClassNum=?";
			String[] selArgs=new String[2];
			selArgs[0]=String.valueOf(daynum);
			selArgs[1]=String.valueOf(CN);
			String[] selArgsShhetName=new String[1];
			selArgsShhetName[0]=currentClassSheetName;
			Cursor cursor=pClassDB.query(CLASS_SHEET_NAME, null,"SheetName=?", selArgsShhetName, null, null, null);
			cursor.moveToFirst();
			int sheetIDColumnIndex=cursor.getColumnIndex("SheetID");
			int classSheetID=cursor.getInt(sheetIDColumnIndex);
			String tableName="CDT"+String.valueOf(classSheetID);	
//			pClassDB=SQLiteDatabase.openDatabase(DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.CREATE_IF_NECESSARY);
			//直接删除，不做保护
			pClassDB.delete(tableName, sel, selArgs);
//			pClassDB.delete(TABLE_NAME, null, null);
		}catch(Exception e)
		{
			Log.e(DEBUG_TAG,"deleting class Error");
		}
		finally
		{
			pClassDB.close();
		}
		return true;
	}	
	
	/**
	 * 在class列表中，以第0周为模板，后面的的课程内容均从模板中读取，编辑课表时直接编码模板的内容
	 * @param CS:要操作的课表名称
	 * @param cc:要操作的课程，直接将weeknum置为0表示是模板
	 */
	public void writeClassTemplate(String CS, Class cc)
	{
		cc.setWeekNum(0);
		writeClass(CS,cc);
	}
	/**
	 * 按天读取模板的内容
	 * @param CS
	 * @param WD
	 * @return 返回Class列表句柄
	 */
	public Class[] readClassTemplateByDay(String CS, int WD)
	{
		return readClassDayList(CS, 0, WD);
	}
	
	/**
	 * 按节号读出模板中一节在一周的内容
	 * @param sheetName
	 * @param CN
	 * @return 模板列表句柄
	 */
	public Class[] readClassTemplateByNumList(String sheetName, int CN)
	{
		return readClassByNumList(sheetName,0,CN);
	}
	
	public Class readClassTemplate(int WD, int CN)
	{
		return null;
	}
	

	public void writeClassSheet(SQLiteDatabase db,ClassSheet cs)
	{
//		SQLiteDatabase db2=this.getWritableDatabase();
		ContentValues cv=new ContentValues();
		Date date=cs.getdateStart();
		Cursor cursor=null;
		cv.put("SheetName", cs.getClassSheetName());
		cv.put("YearOfStart", date.getYear());
		cv.put("MonthOfStart", date.getMonth());
		cv.put("DateOfStart", date.getDate());
		cv.put("MaxClassPerDay", cs.getMaxClassNumPerDay());
		cv.put("MinutersForPerClass",cs.getMinuteForPerClass());
		for(int i=0;i<cs.getMaxClassNumPerDay();i++)
		{	//写入上课时间
			cv.put("MinutesForClass"+String.valueOf(i+1), cs.getClassTimeMinuter(i));
		}
		try
		{
			String[] args=new String[1];
			args[0]=String.valueOf(cs.getClassSheetID());
			cursor=db.query(CLASS_SHEET_NAME, null, "SheetID =?", args, null, null, null);
			//判断是更新还是新插入
			if(cursor.getCount()>0)//表示更新
			{
				Log.d(DEBUG_TAG, "updating ClassSheet "+args[0]);
				db.update(CLASS_SHEET_NAME, cv, "SheetID =?", args);
			}
			else//表示是插入，即新建一个classSheet，此时需要新建配套的classTable，noteTable和 homeWorkTable
			{
				Log.d(DEBUG_TAG, "inserting ClassSheet "+args[0]);
				db.insert(CLASS_SHEET_NAME, null, cv);
				args[0]=cs.getClassSheetName();
				cursor=db.query(CLASS_SHEET_NAME, null, "SheetName"+"=?", args, null, null, null);
		 		cursor.moveToFirst();
				int sheetIDColumnIndex=cursor.getColumnIndex("SheetID");
				//查到该课表在table里面ID值后，转换为String类型，用于生成其他table的名字
				int classSheetID=cursor.getInt(sheetIDColumnIndex);
				cursor.close();
				//创建ClassDetailTable
				String classDetailTableName="CDT"+String.valueOf(classSheetID);
				Log.d(DEBUG_TAG, "Creating ClassDetailTable "+classDetailTableName);
				db.execSQL(CREATE_CLASS_DETAIL_TABLE_HEAD+classDetailTableName+CREATE_CLASS_DETAIL_TABLE_TAIL);
				//创建HomeworkTable
				String homeworkTableName="HWT"+String.valueOf(classSheetID);
				Log.d(DEBUG_TAG, "Creating ClassDetailTable "+homeworkTableName);
				db.execSQL(CREATE_HOMEWORK_TABLE_HEAD+homeworkTableName+CREATE_HOMEWORK_TABLE_TAIL);	
				//创建NotesTable
				String notesTableName="NT"+String.valueOf(classSheetID);
				Log.d(DEBUG_TAG, "Creating ClassDetailTable "+notesTableName);
				db.execSQL(CREATE_NOTES_TABLE_HEAD+notesTableName+CREATE_NOTES_TABLE_TAIL);	
			}
		}catch(Exception e)
		{
			Log.e(DEBUG_TAG,"writing ClassSheet Error");
		}
		finally
		{
			if(cursor!=null)cursor.close();

//			db.close();
		}
	}
	public void writeClassSheet(ClassSheet cs)
	{
		SQLiteDatabase db = null;
		ContentValues cv=new ContentValues();
		Date date=cs.getdateStart();
		Cursor cursor=null;
		cv.put("SheetName", cs.getClassSheetName());
		cv.put("YearOfStart", date.getYear());
		cv.put("MonthOfStart", date.getMonth());
		cv.put("DateOfStart", date.getDate());
		cv.put("MaxClassPerDay", cs.getMaxClassNumPerDay());
		cv.put("MinutersForPerClass",cs.getMinuteForPerClass());
		for(int i=0;i<cs.getMaxClassNumPerDay();i++)
		{	//写入上课时间
			cv.put("MinutesForClass"+String.valueOf(i+1), cs.getClassTimeMinuter(i));
		}
		try
		{
			db=this.getWritableDatabase();
			String[] args=new String[1];
			args[0]=String.valueOf(cs.getClassSheetID());
			cursor=db.query(CLASS_SHEET_NAME, null, "SheetID =?", args, null, null, null);
			//判断是更新还是新插入
			if(cursor.getCount()>0)//表示更新
			{
				Log.d(DEBUG_TAG, "updating ClassSheet "+args[0]);
				db.update(CLASS_SHEET_NAME, cv, "SheetID =?", args);
			}
			else//表示是插入
			{
				Log.d(DEBUG_TAG, "inserting ClassSheet "+args[0]);
				db.insert(CLASS_SHEET_NAME, null, cv);
				//插入后读出该sheet的ID，后面建立对应的table
				args[0]=cs.getClassSheetName();
				cursor=db.query(CLASS_SHEET_NAME, null, "SheetName =?", args, null, null, null);
		 		cursor.moveToFirst();
				int sheetIDColumnIndex=cursor.getColumnIndex("SheetID");
				//查到该课表在table里面ID值后，转换为String类型，用于生成其他table的名字
				int classSheetID=cursor.getInt(sheetIDColumnIndex);
				cursor.close();
				//创建ClassDetailTable
				String classDetailTableName="CDT"+String.valueOf(classSheetID);
				Log.d(DEBUG_TAG, "Creating ClassDetailTable "+classDetailTableName);
				db.execSQL(CREATE_CLASS_DETAIL_TABLE_HEAD+classDetailTableName+CREATE_CLASS_DETAIL_TABLE_TAIL);
				//创建HomeworkTable
				String homeworkTableName="HWT"+String.valueOf(classSheetID);
				Log.d(DEBUG_TAG, "Creating ClassDetailTable "+homeworkTableName);
				db.execSQL(CREATE_HOMEWORK_TABLE_HEAD+homeworkTableName+CREATE_HOMEWORK_TABLE_TAIL);	
				//创建NotesTable
				String notesTableName="NT"+String.valueOf(classSheetID);
				Log.d(DEBUG_TAG, "Creating ClassDetailTable "+notesTableName);
				db.execSQL(CREATE_NOTES_TABLE_HEAD+notesTableName+CREATE_NOTES_TABLE_TAIL);
			}
		}catch(Exception e)
		{
			Log.e(DEBUG_TAG,"writing ClassSheet Error");
		}
		finally
		{
			if(cursor!=null)cursor.close();
			if(db!=null)db.close();
		}
	}
	//根据名字删除课表
	public boolean deleteClassSheet(String sheetName)
	{	
		SQLiteDatabase pClassDB=this.getWritableDatabase();
		Log.d(DEBUG_TAG, "delete Class Sheet"+sheetName);
		try
		{
			String sel="SheetName=?";
			String[] selArgs=new String[1];
			selArgs[0]=sheetName;
			Cursor cursor=pClassDB.query(CLASS_SHEET_NAME, null, sel, selArgs, null, null, null);
			cursor.moveToFirst();
			int sheetIDColumnIndex=cursor.getColumnIndex("SheetID");
			//查到该课表在table里面ID值后，转换为String类型，用于生成其他table的名字
			int classSheetID=cursor.getInt(sheetIDColumnIndex);
			cursor.close();
			String tableName="CDT"+String.valueOf(classSheetID);
			Log.d(DEBUG_TAG, "deleting ClassDetailTable "+tableName);
			pClassDB.execSQL("DROP TABLE "+tableName);
			tableName="HWT"+String.valueOf(classSheetID);
			Log.d(DEBUG_TAG, "deleting ClassDetailTable "+tableName);
			pClassDB.execSQL("DROP TABLE "+tableName);	
			tableName="NT"+String.valueOf(classSheetID);
			Log.d(DEBUG_TAG, "deleting ClassDetailTable "+tableName);
			pClassDB.execSQL("DROP TABLE "+tableName);						
			//直接删除，不做保护
			pClassDB.delete(CLASS_SHEET_NAME, sel, selArgs);			
		}catch(Exception e)
		{
			Log.e(DEBUG_TAG,"deleting class Error");
		}
		finally
		{
			pClassDB.close();
		}
		return true;
	}
	
	public ClassSheet readClassSheet(String classSheetName)
	{
		SQLiteDatabase db;
		Cursor cursor=null;
		ClassSheet cs=null;
		try
		{
			db=this.getReadableDatabase();
			String[] sheetName=new String[1];
			sheetName[0]=classSheetName;
			Log.d(DEBUG_TAG, "reading ClassSheet: "+classSheetName);
			cursor=db.query(CLASS_SHEET_NAME, null, "SheetName"+"=?", sheetName, null, null, null);
			//判断是更新还是新插入
			if(cursor.getCount()!=0)
			{
				Log.d(DEBUG_TAG, "reading ClassSheet: "+classSheetName+" success");
				cs=new ClassSheet(classSheetName);
				cursor.moveToFirst();
				int index2=cursor.getColumnCount();
				int index3=cursor.getColumnIndex("SheetID");
				int index=cursor.getInt(0);
				cs.setClassSheetID(index);//获取课表ID值
				Date date=new Date();
				date.setYear(cursor.getInt(2));//year放在数据库表中第2列
				date.setMonth(cursor.getInt(3));//Month放在数据库表中第3列
				date.setDate(cursor.getInt(4));//day放在数据库表中第4列
				cs.setdateStart(date);
				cs.setMaxClassNumPerDay(cursor.getInt(5));
				cs.setMinuteForPerClass(cursor.getInt(6));
				for(int i=0;i<cs.getMaxClassNumPerDay();i++)
				{	//提取每一节课的上课时间
					cs.setClassTimeMinuter(cursor.getInt(i+7), i);
				}
			}
			else
			{
				Log.d(DEBUG_TAG, "reading ClassSheet: "+sheetName+" fail");
			}
			db.close();
		}catch(Exception e)
		{
			Log.e(DEBUG_TAG,"reading ClassSheet error");
		}
		finally
		{
			if(cursor!=null)cursor.close();
		}
		return cs;
	}
	
	//读取课表名称列表
	public String[] readClassSheetList()
	{
		Cursor cursor=null;
		String[] nameList=null;
		SQLiteDatabase db = null;
		try
		{
			db=this.getReadableDatabase();
			Log.d(DEBUG_TAG, "reading ClassSheet list ");
			String[] columns=new String[1];
			columns[0]="SheetName";
			//读取列表，只返加课表名称列
			cursor=db.query(CLASS_SHEET_NAME, columns, null, null, null, null, null);
			cursor.moveToFirst();
			int listCount=cursor.getCount();
			if(listCount!=0)
			{
				Log.d(DEBUG_TAG, "reading ClassSheet list success");
				nameList=new String[listCount];//为传入名称列表数据分配地址 
				for(int i=0;i<listCount;i++)
				{
					nameList[i]=cursor.getString(0);
					cursor.moveToNext();
				}
			}
			else
			{
				Log.d(DEBUG_TAG, "reading ClassSheet list fail");
			}
		}catch(Exception e)
		{
			Log.e(DEBUG_TAG,"reading ClassSheet list error");
		}
		finally
		{
			cursor.close();
			db.close();
		}
		return nameList;		
	}
}
