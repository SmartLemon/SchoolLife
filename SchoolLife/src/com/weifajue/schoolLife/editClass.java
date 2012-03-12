package com.weifajue.schoolLife;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.widget.*;
import android.view.*;

public class editClass extends Activity
{
	private static final String[] pWeekDay={"一","二","三","四","五","六","日"};
	private static final String[] pContinuumClass={"0","1","2","3","4","5","6"};
	private static final int CLASSNUM=5;
	private String pClassNum[]=new String[CLASSNUM];

	private Spinner spWeekDay,spClassNum,spContinuumClass ;
	private ArrayAdapter<String> adapterWD,adapterCN,adapterCC;
	private Button bOK,bCancel,bAdd,bDelete;
	private Button bSetClassTime;
	private EditText etClassName,etClassLocation,etTeacherName;
	
//	public String m_WeekDay,m_ClassNum;
	public int m_WeekDay=0,m_ClassNum=0;
	public int m_ClassTimeHour=8,m_ClassTimeMinute=0;
	public String m_ClassName,m_ClassLocation,m_TeacherName;
	public int m_ContinuumClass=0;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editclasslist);
        Log.e("DebugLog","run in editClass Activity");
    	
        //根据节数初始化数组
    	for(int i=0;i<CLASSNUM;i++)
    	{
    		pClassNum[i]=String.valueOf(i+1);
    	}

        spWeekDay=(Spinner)findViewById(R.id.spinnerWeekDay);
        spClassNum=(Spinner)findViewById(R.id.spinnerClassNum);
        bOK=(Button)findViewById(R.id.buttonEditOK);
        bCancel=(Button)findViewById(R.id.buttonEditCancel);
        bAdd=(Button)findViewById(R.id.buttonAddClass);
        bDelete=(Button)findViewById(R.id.buttonDeleteClass);
        bSetClassTime=(Button)findViewById(R.id.buttonSetClassTime);
        etClassName=(EditText)findViewById(R.id.editTextClassName);
        etClassLocation=(EditText)findViewById(R.id.editTextClassLocation);
        etTeacherName=(EditText)findViewById(R.id.editTextTeacherName);
//        etContinuumClass=(EditText)findViewById(R.id.editTextContinuumClass);
        spContinuumClass = (Spinner)findViewById(R.id.spinnerContinuumClass);
        
        adapterWD=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,pWeekDay);
        adapterCN=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,pClassNum);
        adapterCC=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,pContinuumClass);
        
        adapterWD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterCN.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);	
        adapterCC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);	
        
        spWeekDay.setAdapter(adapterWD);
        spClassNum.setAdapter(adapterCN);
        spContinuumClass.setAdapter(adapterCC);
        
        //设置两个下拉框的内容
        spWeekDay.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
        {
        	@Override
        	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
        	{
        		arg0.setVisibility(View.VISIBLE);
//        		m_WeekDay=pWeekDay[arg2];
        		m_WeekDay=arg2+1;
        	}
        	@Override
        	public void onNothingSelected(AdapterView<?>arg0)
        	{
//        		m_WeekDay=pWeekDay[0];
        		m_WeekDay=1;
        	}
        });
        
        spClassNum.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
        {
        	@Override
        	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
        	{
        		arg0.setVisibility(View.VISIBLE);
//        		m_ClassNum=pClassNum[arg2];
        		m_ClassNum=arg2+1;
        	}
        	@Override
        	public void onNothingSelected(AdapterView<?>arg0)
        	{
//        		m_ClassNum=pClassNum[0];
        		m_ClassNum=1;
        	}
        });
        //设置连接上课节数下拉菜单
        spContinuumClass.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
        {
        	@Override
        	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
        	{
        		arg0.setVisibility(View.VISIBLE);
        		m_ContinuumClass=arg2;
        	}
        	@Override
        	public void onNothingSelected(AdapterView<?>arg0)
        	{
        		m_ContinuumClass=0;
        	}
        });
        etClassName.setHint("请输入课程名称");
        etClassLocation.setHint("请输入上课教室");
        etTeacherName.setHint("请输入老师名字");
//        etContinuumClass.setHint("请输入连续上课节数");
/*
        //设置4个文本编辑框的内容
        etClassName.setHint("请输入课程名称");
        etClassName.setOnKeyListener(new EditText.OnKeyListener()
        {
        	@Override
        	public boolean onKey(View arg0,int arg1, KeyEvent arg2)
        	{
        		m_ClassName=etClassName.getText().toString();
        		etClassName.setText(m_ClassName);    
        		return false;
        	}
        });
        etClassLocation.setHint("请输入上课教室");
        etClassLocation.setOnKeyListener(new EditText.OnKeyListener()
        {
        	@Override
        	public boolean onKey(View arg0,int arg1, KeyEvent arg2)
        	{
        		m_ClassLocation=etClassLocation.getText().toString();
        		etClassLocation.setText(m_ClassLocation);    
        		return false;
        	}
        });
        etTeacherName.setHint("请输入老师名字");
        etTeacherName.setOnKeyListener(new EditText.OnKeyListener()
        {
        	@Override
        	public boolean onKey(View arg0,int arg1, KeyEvent arg2)
        	{
        		m_TeacherName=etTeacherName.getText().toString();
        		etTeacherName.setText(m_TeacherName);    
        		return false;
        	}
        });
        etContinuumClass.setHint("请输入连续上课节数");
        etContinuumClass.setOnKeyListener(new EditText.OnKeyListener()
        {
        	@Override
        	public boolean onKey(View arg0,int arg1, KeyEvent arg2)
        	{
        		m_ContinuumClass=etContinuumClass.getText().toString();
        		etContinuumClass.setText(m_ContinuumClass);    
        		return false;
        	}
        }); */ 
        
        bAdd.setOnClickListener(new Button.OnClickListener()
        {
        	@Override
        	public void onClick(View v)
        	{
        		ClassDB cDB=new ClassDB(editClass.this);
//        		int CN=Integer.parseInt(m_ClassNum);
//        		int WD=Integer.parseInt(m_WeekDay);
        		m_ClassName=etClassName.getText().toString();
        		m_ClassLocation=etClassLocation.getText().toString();
        		m_TeacherName=etTeacherName.getText().toString();
//        		m_ContinuumClass=etContinuumClass.getText().toString();

        		Class C=new Class(m_ClassNum,m_WeekDay,m_ClassName,m_TeacherName,m_ContinuumClass,m_ClassTimeHour,m_ClassTimeMinute);
        		cDB.writeClass(C);
        	}
        });
        bDelete.setOnClickListener(new Button.OnClickListener()
        {
        	@Override
        	public void onClick(View v)
        	{
        		ClassDB cDB=new ClassDB(editClass.this);
//        		int CN=Integer.parseInt(m_ClassNum);
//        		int WD=Integer.parseInt(m_WeekDay);
        		cDB.deleteClass(m_ClassNum, m_WeekDay);
        	}
        });
        //设置两个按键的内容,暂时均为退出到主类中
        bOK.setOnClickListener(new Button.OnClickListener()
        {
        	@Override
        	public void onClick(View v)
        	{
        		Intent intent = new Intent();
        		intent.setClass(editClass.this,SchoolLifeActivity.class);
        		startActivity(intent);
        		editClass.this.finish();
        	}
        });
        bCancel.setOnClickListener(new Button.OnClickListener()
        {
        	@Override
        	public void onClick(View v)
        	{
        		Intent intent = new Intent();
        		intent.setClass(editClass.this,SchoolLifeActivity.class);
        		startActivity(intent);
        		editClass.this.finish();
        	}
        });
        //设置时间
        bSetClassTime.setOnClickListener(new Button.OnClickListener()
        {
        	@Override
        	public void onClick(View v)
        	{
        		new TimePickerDialog(editClass.this,
        				new TimePickerDialog.OnTimeSetListener()
        		{						
							@Override
							public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
								// TODO Auto-generated method stub
								m_ClassTimeHour=hourOfDay;
								m_ClassTimeMinute=minute;
							}
						},8,0,true).show();
        	}
        });
    }//onCreate
}//main
