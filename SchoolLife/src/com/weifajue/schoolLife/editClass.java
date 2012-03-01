package com.weifajue.schoolLife;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import android.view.*;

public class editClass extends Activity
{
	private static final String[] pWeekDay={"һ","��","��","��","��","��","��"};
	private static final int CLASSNUM=5;
	private String pClassNum[]=new String[CLASSNUM];

	private Spinner spWeekDay,spClassNum;
	private ArrayAdapter<String> adapterWD,adapterCN;
	private Button bOK,bCancel;
	private EditText etClassName,etClassLocation,etTeacherName,etContinuumClass;
	
	public String m_WeekDay,m_ClassNum;
	public String m_ClassName,m_ClassLocation,m_TeacherName,m_ContinuumClass;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editclasslist);
        Log.e("DebugLog","run in editClass Activity");
    	
        //���ݽ�����ʼ������
    	for(int i=0;i<CLASSNUM;i++)
    	{
    		pClassNum[i]=String.valueOf(i+1);
    	}
    	
        spWeekDay=(Spinner)findViewById(R.id.spinnerWeekDay);
        spClassNum=(Spinner)findViewById(R.id.spinnerClassNum);
        bOK=(Button)findViewById(R.id.buttonEditOK);
        bCancel=(Button)findViewById(R.id.buttonEditCancel);
        etClassName=(EditText)findViewById(R.id.editTextClassName);
        etClassLocation=(EditText)findViewById(R.id.editTextClassLocation);
        etTeacherName=(EditText)findViewById(R.id.editTextTeacherName);
        etContinuumClass=(EditText)findViewById(R.id.editTextContinuumClass);
        
        adapterWD=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,pWeekDay);
        adapterCN=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,pClassNum);
        
        adapterWD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterCN.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);	
        
        spWeekDay.setAdapter(adapterWD);
        spClassNum.setAdapter(adapterCN);
        
        //�������������������
        spWeekDay.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
        {
        	@Override
        	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
        	{
        		arg0.setVisibility(View.VISIBLE);
        		m_WeekDay=pWeekDay[arg2];
        	}
        	@Override
        	public void onNothingSelected(AdapterView<?>arg0)
        	{
        		m_WeekDay=pWeekDay[0];
        	}
        });
        
        spClassNum.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
        {
        	@Override
        	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
        	{
        		arg0.setVisibility(View.VISIBLE);
        		m_ClassNum=pClassNum[arg2];
        	}
        	@Override
        	public void onNothingSelected(AdapterView<?>arg0)
        	{
        		m_ClassNum=pClassNum[0];
        	}
        });
        etClassName.setHint("������γ�����");
        etClassLocation.setHint("�������Ͽν���");
        etTeacherName.setHint("��������ʦ����");
        etContinuumClass.setHint("�����������Ͽν���");
/*
        //����4���ı��༭�������
        etClassName.setHint("������γ�����");
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
        etClassLocation.setHint("�������Ͽν���");
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
        etTeacherName.setHint("��������ʦ����");
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
        etContinuumClass.setHint("�����������Ͽν���");
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
        //������������������,��ʱ��Ϊ�˳���������
        bOK.setOnClickListener(new Button.OnClickListener()
        {
        	@Override
        	public void onClick(View v)
        	{
        		m_ClassName=etClassName.getText().toString();
        		m_ClassLocation=etClassLocation.getText().toString();
        		m_TeacherName=etTeacherName.getText().toString();
        		m_ContinuumClass=etContinuumClass.getText().toString();
        		
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
    }//onCreate
}//main
