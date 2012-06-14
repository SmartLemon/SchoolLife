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
import android.app.Activity;
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
	
	//���ݿ�������
	/*���ݿ�����*/
	private final static String DATABASE_NAME="ClassDataBase";
	/*������*/	
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

	private String currentClassSheetName;
	private int currentClassSheetID;
	private String currentClassDetailTableName;
	private String currentHomeworkTableName;
	private String currentNotesTableName;
/*	
	//������
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
//		LocalFile loclfile=new LocalFile();
//		currentClassSheetName=loclfile.getCurrentClassSheetName();
		currentClassSheetName="default";
		setTableName();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.d(DEBUG_TAG, "Creating Table");
		db.execSQL(CREATE_CLASS_SHEET_TABLE_HEAD+CLASS_SHEET_NAME+CREATE_CLASS_SHEET_TABLE_TAIL);
		String[] string=new String[1];
		string[0]=currentClassSheetName;
//		Cursor cursor1=db.query(CLASS_SHEET_NAME, null, "SheetName"+"=?", string, null, null, null);
 		Cursor cursor1=db.query(CLASS_SHEET_NAME, null, null, null, null, null, null);
 		if(cursor1.getCount()==0)//û���ҵ�����ʾ�ǵ�һ�δ���
		{	//��ClassSheet�б����½�һ���б�
			ClassSheet cs=new ClassSheet(currentClassSheetName);
			writeClassSheet(db,cs);
			//�²���һ�����ٲ�һ�Σ��Ի�ȡ�²����ClassSheet��IDֵ
//			cursor1=db.query(CLASS_SHEET_NAME, null, "SheetName"+"=?", string, null, null, null);
			cursor1=db.query(CLASS_SHEET_NAME, null, null, null, null, null, null);
		}
 		cursor1.moveToFirst();
		int sheetIDColumnIndex=cursor1.getColumnIndex("SheetID");
		//�鵽�ÿα���table����IDֵ��ת��ΪString���ͣ�������������table������
		currentClassSheetID=cursor1.getInt(sheetIDColumnIndex);
		cursor1.close();
//		currentClassSheetID=0;
		//����ClassDetailTable
		currentClassDetailTableName="CDT"+String.valueOf(currentClassSheetID);
		Log.d(DEBUG_TAG, "Creating ClassDetailTable "+currentClassDetailTableName);
		db.execSQL(CREATE_CLASS_DETAIL_TABLE_HEAD+currentClassDetailTableName+CREATE_CLASS_DETAIL_TABLE_TAIL);
		//����HomeworkTable
		currentHomeworkTableName="HWT"+String.valueOf(currentClassSheetID);
		Log.d(DEBUG_TAG, "Creating ClassDetailTable "+currentHomeworkTableName);
		db.execSQL(CREATE_HOMEWORK_TABLE_HEAD+currentHomeworkTableName+CREATE_HOMEWORK_TABLE_TAIL);	
		//����NotesTable
		currentNotesTableName="NT"+String.valueOf(currentClassSheetID);
		Log.d(DEBUG_TAG, "Creating ClassDetailTable "+currentNotesTableName);
		db.execSQL(CREATE_NOTES_TABLE_HEAD+currentNotesTableName+CREATE_NOTES_TABLE_TAIL);	
	}                  

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String drop="DROP TABLE IF EXISTS "+TABLE_NAME;
		db.execSQL(drop);
		onCreate(db);
	}
	
	//�ṩ���������ڶ�ȡ���ܣ���ȡĳ��ĳ�ڿ�
	public Class readClass(int WN,int WD,int CN)
	{
		SQLiteDatabase pClassDB=null;
		Cursor cursor=null;
		timeProcess tp=new timeProcess();
		Class C= null;//�˴���δ����������Ҫ�ϲ���ú��������жϸ÷���ֵ�Ƿ�Ϊ��;
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
			cursor=pClassDB.query(currentClassDetailTableName, null, sel, selArgs, null, null, "ID");
			if(cursor.getCount()!=0)
			{
//				cursor.moveToFirst();
//				int columnIndex;
//				do//cursor��ʼӦ����ָ���ͷ������Ҫ��do
//				{
//					columnIndex=cursor.getColumnIndex(CLASSID);
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
				
//					if(cursor.getInt(columnIndex)==cid)
//					{
//						C=new Class(cursor.getInt(1)/7,cursor.getInt(1)%7,cursor.getString(2),cursor.getString(3));
//						break;//�ҵ�������ѭ��
//					}
//				}while(cursor.moveToNext());
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
	
	public Class readClass(int WD,int CN)
	{
		SQLiteDatabase pClassDB=null;
		Cursor cursor=null;
		timeProcess tp=new timeProcess();
		Class C= null;//�˴���δ����������Ҫ�ϲ���ú��������жϸ÷���ֵ�Ƿ�Ϊ��;
		int WN=getCurrentWeekNum();
		int daynum=tp.weekAndWeekDayToDayNum(WN, WD);
		String sel="DayNum=? and ClassNum=?";
		String[] selArgs=new String[2];
		selArgs[0]=String.valueOf(daynum);
		selArgs[1]=String.valueOf(CN);
		Log.d(DEBUG_TAG, "reading Class");
		try
		{
			pClassDB=this.getReadableDatabase();
			cursor=pClassDB.query(currentClassDetailTableName, null, sel, selArgs, null, null, "ID");
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
	
	public Class[] readClassDayList(int WN,int WD)
	{
		SQLiteDatabase pClassDB=null;
		Cursor cursor=null;
		Class[] classList=null;
		String sel="DayNum=?";
		String[] selArgs=new String[1];
		timeProcess tp=new timeProcess();
		int DN=tp.weekAndWeekDayToDayNum(WN, WD);
		selArgs[0]=String.valueOf(DN);
		Log.d(DEBUG_TAG, "reading Class Day List"+selArgs);
		try
		{
			pClassDB=this.getReadableDatabase();
			cursor=pClassDB.query(currentClassDetailTableName, null, sel, selArgs, null, null, "ClassNum");
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
	//���γ�����д�뵽���ݿ���,����true��ʾ�ɹ�,����false��ʾʧ��
	public boolean writeClass(Class C)
	{
		SQLiteDatabase pClassDB=this.getWritableDatabase();
		Cursor cursor = null;
		timeProcess tp=new timeProcess();
		int daynum=tp.weekAndWeekDayToDayNum(C.getWeekNum(), C.getWeekDay());
		//ʹ��contentValues�ķ���������͸������ݿ�
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
			cursor=pClassDB.query(currentClassDetailTableName, null, sel, selArgs, null, null, "ID");
			//�ж��Ǹ��»����²���
			if(cursor.getCount()!=0)
			{
				Log.d(DEBUG_TAG , "update Class in writing process");
				pClassDB.update(currentClassDetailTableName, cv, sel, selArgs);
			}
			else
			{
				Log.d(DEBUG_TAG, "insert Class in writing process");
				pClassDB.insert(currentClassDetailTableName, null, cv);
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
	//����ָ���γ̵����ݣ�����true��ʾ�ɹ�,����false��ʾʧ��
	public boolean updateClass(Class C)
	{
		return true;
	}
*/
	//ɾ��ָ���Ŀγ̣�����true��ʾ�ɹ�,����false��ʾʧ��
	public boolean deleteClass(int WN,int WD,int CN)
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
//			pClassDB=SQLiteDatabase.openDatabase(DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.CREATE_IF_NECESSARY);
			//ֱ��ɾ������������
			pClassDB.delete(currentClassDetailTableName, sel, selArgs);
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
	//ɾ��ָ���Ŀγ̣�����true��ʾ�ɹ�,����false��ʾʧ��
	public boolean deleteClass(int WD,int CN)
	{	
		int WN=getCurrentWeekNum();
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
//			pClassDB=SQLiteDatabase.openDatabase(DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.CREATE_IF_NECESSARY);
			//ֱ��ɾ������������
			pClassDB.delete(currentClassDetailTableName, sel, selArgs);
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
		{	//д���Ͽ�ʱ��
			cv.put("MinutesForClass"+String.valueOf(i+1), cs.getClassTimeMinuter(i));
		}
		try
		{
			String[] sheetName=new String[1];
			sheetName[0]=cs.getClassSheetName();
			cursor=db.query(CLASS_SHEET_NAME, null, "SheetName "+"=?", sheetName, null, null, null);
			//�ж��Ǹ��»����²���
			if(cursor.getCount()>0)//��ʾ����
			{
				Log.d(DEBUG_TAG, "updating ClassSheet "+sheetName);
				int updateResult=db.update(CLASS_SHEET_NAME, cv, "SheetName"+"=?", sheetName);
			}
			else//��ʾ�ǲ���
			{
				Log.d(DEBUG_TAG, "inserting ClassSheet "+sheetName);
				long insertResult=db.insert(CLASS_SHEET_NAME, null, cv);
				if(insertResult==0)insertResult=2;
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
			Log.d(DEBUG_TAG, "reading ClassSheet: "+sheetName);
			cursor=db.query(CLASS_SHEET_NAME, null, "SheetName"+"=?", sheetName, null, null, null);
			//�ж��Ǹ��»����²���
			if(cursor.getCount()!=0)
			{
				Log.d(DEBUG_TAG, "reading ClassSheet: "+sheetName+" success");
				cs=new ClassSheet(classSheetName);
				cursor.moveToFirst();
				int index2=cursor.getColumnCount();
				int index3=cursor.getColumnIndex("SheetID");
				int index=cursor.getInt(0);
				cs.setClassSheetID(index);//��ȡ�α�IDֵ
				Date date=new Date();
				date.setYear(cursor.getInt(2));//year�������ݿ���е�2��
				date.setMonth(cursor.getInt(3));//Month�������ݿ���е�3��
				date.setDate(cursor.getInt(4));//day�������ݿ���е�4��
				cs.setdateStart(date);
				cs.setMaxClassNumPerDay(cursor.getInt(5));
				cs.setMinuteForPerClass(cursor.getInt(6));
				for(int i=0;i<cs.getMaxClassNumPerDay();i++)
				{	//��ȡÿһ�ڿε��Ͽ�ʱ��
					cs.setClassTimeMinuter(cursor.getInt(i+7), i);
				}
			}
			else//��ʾ�ǲ���
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
			cursor.close();
		}
		return cs;
	}
	//��ȡ��ǰ�α�
	public ClassSheet getCurrentClassSheet()
	{
		return readClassSheet(currentClassSheetName);
	}
	public void setTableName()
	{
		ClassSheet cs=readClassSheet(currentClassSheetName);
		currentClassSheetID=cs.getClassSheetID();
		currentClassDetailTableName="CDT"+String.valueOf(currentClassSheetID);
		currentHomeworkTableName="HWT"+String.valueOf(currentClassSheetID);
		currentNotesTableName="NT"+String.valueOf(currentClassSheetID);
	}
	//��ȡ�α������б�
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
			//��ȡ�б�ֻ���ӿα�������
			cursor=db.query(CLASS_SHEET_NAME, columns, null, null, null, null, null);
			cursor.moveToFirst();
			int listCount=cursor.getCount();
			//�ж��Ǹ��»����²���
			if(listCount!=0)
			{
				Log.d(DEBUG_TAG, "reading ClassSheet list success");
				nameList=new String[listCount];//Ϊ���������б����ݷ����ַ 
				for(int i=0;i<listCount;i++)
				{
					nameList[i]=cursor.getString(0);
				}
			}
			else//��ʾ�ǲ���
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
	public int getCurrentWeekNum()
	{
		timeProcess tp=new timeProcess();
		ClassSheet cs=getCurrentClassSheet();
		return tp.weekOffset(cs.getdateStart(), new Date());
	}

}
