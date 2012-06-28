/**
 * 
 */
package com.weifajue.schoolLife.data;

import java.util.Date;

import com.weifajue.schoolLife.model.Class;
import com.weifajue.schoolLife.model.ClassSheet;
import com.weifajue.schoolLife.Util.timeProcess;
import com.weifajue.schoolLife.data.LocalFile;
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
			  +"[WeekNum] INT,"				//1
			  +"[DayNum] INT," 				//2
			  +"[ClassNum] INT," 			//3
			  +"[MinutesForClass] INT," 	//4
			  +"[ClassName] CHAR," 			//5
			  +"[TeacherName] CHAR," 		//6
			  +"[ClassRoom] CHAR," 			//7
			  +"[isSingIn] BOOLEAN," 		//8
			  +"[HomeWorkIndex] INT," 		//9
			  +"[NotesIndex] INT," 			//10
			  +"[Rating] INT," 				//11
			  +"[Comment] CHAR)"; 			//12
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
			//新插入一个后，再查一次，以获取新插入的ClassSheet的ID值
//			cursor1=db.query(CLASS_SHEET_NAME, null, "SheetName"+"=?", string, null, null, null);
//			cursor1=db.query(CLASS_SHEET_NAME, null, null, null, null, null, null);
		}
/* 		cursor1.moveToFirst();
		int sheetIDColumnIndex=cursor1.getColumnIndex("SheetID");
		//查到该课表在table里面ID值后，转换为String类型，用于生成其他table的名字
		currentClassSheetID=cursor1.getInt(sheetIDColumnIndex);
		cursor1.close();
//		currentClassSheetID=0;
		//创建ClassDetailTable
		currentClassDetailTableName="CDT"+String.valueOf(currentClassSheetID);
		Log.d(DEBUG_TAG, "Creating ClassDetailTable "+currentClassDetailTableName);
		db.execSQL(CREATE_CLASS_DETAIL_TABLE_HEAD+currentClassDetailTableName+CREATE_CLASS_DETAIL_TABLE_TAIL);
		//创建HomeworkTable
		currentHomeworkTableName="HWT"+String.valueOf(currentClassSheetID);
		Log.d(DEBUG_TAG, "Creating ClassDetailTable "+currentHomeworkTableName);
		db.execSQL(CREATE_HOMEWORK_TABLE_HEAD+currentHomeworkTableName+CREATE_HOMEWORK_TABLE_TAIL);	
		//创建NotesTable
		currentNotesTableName="NT"+String.valueOf(currentClassSheetID);
		Log.d(DEBUG_TAG, "Creating ClassDetailTable "+currentNotesTableName);
		db.execSQL(CREATE_NOTES_TABLE_HEAD+currentNotesTableName+CREATE_NOTES_TABLE_TAIL);	
*/	}                  

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
		timeProcess tp=new timeProcess();
		Class C= null;//此处暂未做保护，需要上层调用函数进行判断该返回值是否为空;
//		int cid=CN*7+WD;
		int daynum=tp.weekAndWeekDayToDayNum(WN, WD);
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
		timeProcess tp=new timeProcess();
		Class C= null;//此处暂未做保护，需要上层调用函数进行判断该返回值是否为空;
		int WN=tp.dateToDefaultWeekNum(new Date());
		int daynum=tp.weekAndWeekDayToDayNum(WN, WD);
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
	
	public Class[] readClassDayList(String currentClassSheetName,int WN,int WD)
	{
		SQLiteDatabase pClassDB=null;
		Cursor cursor=null;
		Class[] classList=null;
		String sel="DayNum=?";
		String[] selArgs=new String[1];
		timeProcess tp=new timeProcess();
		int DN=tp.weekAndWeekDayToDayNum(WN, WD);
		selArgs[0]=String.valueOf(DN);
		Log.d(DEBUG_TAG, "reading Class Day List"+selArgs[0]);
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
			cursor=pClassDB.query(tableName, null, sel, selArgs, null, null, "ClassNum");
			int count=cursor.getCount();
			if(count>=0)
			{
				cursor.moveToFirst();
				classList=new Class[count];
				Log.e(DEBUG_TAG,"reading class success:"+selArgs[0]);
				for(int i=0;i<count;i++)
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
		timeProcess tp=new timeProcess();
		int daynum=tp.weekAndWeekDayToDayNum(C.getWeekNum(), C.getWeekDay());
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
			cursor=pClassDB.query(currentClassSheetName, null, sel, selArgs, null, null, "ID");
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
/*
	//更新指定课程的内容，返回true表示成功,返回false表示失败
	public boolean updateClass(Class C)
	{
		return true;
	}
*/
	//删除指定的课程，返回true表示成功,返回false表示失败
	public boolean deleteClass(String currentClassSheetName,int WN,int WD,int CN)
	{		
		timeProcess tp=new timeProcess();
		int daynum=tp.weekAndWeekDayToDayNum(WN,WD);
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
		timeProcess tp=new timeProcess();
		int WN=tp.dateToDefaultWeekNum(new Date());
		int daynum=tp.weekAndWeekDayToDayNum(WN,WD);
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
/*
	public Cursor getClassCursor()
	{
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cursor=db.query(TABLE_NAME, null, null, null, null, null, CLASSID);
		return cursor;
	}
*/
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
	
	//读取当前课表
//	public ClassSheet getCurrentClassSheet()
//	{
//		return readClassSheet(currentClassSheetName);
//	}
/*
	public void setTableName()
	{
		ClassSheet cs=readClassSheet(currentClassSheetName);
		currentClassSheetID=cs.getClassSheetID();
		currentClassDetailTableName="CDT"+String.valueOf(currentClassSheetID);
		currentHomeworkTableName="HWT"+String.valueOf(currentClassSheetID);
		currentNotesTableName="NT"+String.valueOf(currentClassSheetID);
	}
*/	//读取课表名称列表
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
