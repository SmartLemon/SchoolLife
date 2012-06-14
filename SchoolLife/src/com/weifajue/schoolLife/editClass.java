package com.weifajue.schoolLife;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.weifajue.schoolLife.data.ClassDB;
import com.weifajue.schoolLife.model.Class;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;

import android.util.Log;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.view.*;

public class editClass extends Activity
{
	private static final String[] pWeekDay={"һ","��","��","��","��","��","��"};
	private static final String[] pContinuumClass={"0","1","2","3","4","5","6"};
	private static final int MAX_CLASSES_PER_DAY=5;
	private static final int CLASSNUM=MAX_CLASSES_PER_DAY;

	private String pClassNum[]=new String[CLASSNUM];
	//�������
	public View topHeader;
	//�ؼ�������λ
	private Spinner spWeekDay,spClassNum;
	private ArrayAdapter<String> adapterWD,adapterCN,adapterCC;
	private Button bMultiOperate,bAdd,bDelete;
	private Button bSetClassTime;
	private EditText etClassName,etClassLocation,etTeacherName;
	//ȫ�ֱ���
	//�γ���Ϣ
	public int m_WeekDay=1,m_ClassNum=1;
	public int m_ClassTimeHour=8,m_ClassTimeMinute=0;
	public String m_ClassName,m_ClassLocation,m_TeacherName;
	public int m_ContinuumClass=0;
	//�γ�ָ�����ά����,�������浱ǰһ�ܿγ̱�ĸ���ָ��״̬
	//��������,һ����������ԭʼ��״̬��һ�����������û�ѡ����µ�״̬
	private int[][] classIndicaterTable=new int[7][MAX_CLASSES_PER_DAY];
	private int[][] classIndicaterTable_New=new int[7][MAX_CLASSES_PER_DAY];
	//��ʾ����ָʾ��ÿһ��Ԫ�ص�ռ��״̬�����ݸö�Ӧ�γ��Ƿ����裬����������״̬
	private static final int CLASS_INDICATER_EMPTY=0;	//����
	private static final int CLASS_INDICATER_OCCUPIED=1;//�Ѿ�ռ��
	private static final int CLASS_INDICATER_SELECTED=2;//��ǰ�û�ѡ��
	//�γ�ָʾ���޸ı�־�����ڱ�ʾ��ѡ��������޸���ָ���
	private boolean classTableModifyFlag=true;
	//�����ж��Ƿ��ڸı�������ʱ����ȡ���¿γ����ݣ��ֱ��Ӧweekday,classnum,
	private boolean isUpdateWDSpinner=true;
	private boolean isUpdateCNSpinner=true;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editclasslist);
        Log.e("DebugLog","run in editClass Activity");
    	
        //���ݽ�����ʼ������
    	for(int i=0;i<CLASSNUM;i++)
    	{
    		pClassNum[i]=String.valueOf(i+1);
    	}
    	//������ɿγ�ָʾ���ѡ�����󣬻�ص�onCreate������
    	//������Ҫ�����޸ĵı�ʾ���ж��������Ƿ���Ҫ��ʼ��ָ��ʾ��,classTableModifyFlagΪtrue��ʾ��Ҫ
    	if(classTableModifyFlag==true)
    	{
	    	//��ʼ���γ�ָʾ��
	    	ClassDB cDB=new ClassDB(editClass.this);
	    	for(int WD=0;WD<7;WD++)
	    	{
	    		for(int CN=0;CN<MAX_CLASSES_PER_DAY;CN++)
	    		{	
	    			if(cDB.readClass(CN+1, WD+1)==null)
	    			{
	    				classIndicaterTable[WD][CN]=CLASS_INDICATER_EMPTY;
	    				classIndicaterTable_New[WD][CN]=CLASS_INDICATER_EMPTY;
	    			}
	    			else 
	    			{
	    				classIndicaterTable[WD][CN]=CLASS_INDICATER_OCCUPIED;
	    				classIndicaterTable_New[WD][CN]=CLASS_INDICATER_OCCUPIED;
	    			}
	    		}
	    	}
	    	//��ʼ������Ϊfalse,��ʾ����Ҫ�ٳ�ʼ��
	    	classTableModifyFlag=false;
    	}
		//���ö��˱���������������������ȥ��ʾ������λ�ã������ı���
		topHeader=(View)findViewById(R.id.editClassHeader);
		//��ʾ����ļ�ͷ�����ڷ���
//		Button btn=(Button)topHeader.findViewById(R.id.top_btn_left);
//		btn.setVisibility(View.INVISIBLE);
		Button btn=(Button)topHeader.findViewById(R.id.top_btn_right);
		btn.setVisibility(View.INVISIBLE);
		TextView top_textView=(TextView)topHeader.findViewById(R.id.tv_toptitle);
		top_textView.setText("�༭�γ�");
    	//��ʼ��������Դ
    	resourceInitialize();
    }
    //onCreate
    
    void setClassSelectingGrid()
    {
    	setContentView(R.layout.classselectinggrid);
    	final int gridResIDs[]={R.drawable.gridwhite,R.drawable.gridgrey,R.drawable.gridyellow};

    	LinearLayout ll=(LinearLayout)findViewById(R.id.linearLayoutForGridCN);

    	Button bGridViewOK=(Button)findViewById(R.id.buttonGridViewOK);
    	Button bGridViewCancel=(Button)findViewById(R.id.buttonGridViewCancel);
    	
    	GridView gridView=(GridView)findViewById(R.id.gridView1);
    			
    	List<Map<String, Object>> cells = new ArrayList<Map<String, Object>>();
//����gridͼƬ��ÿһ�����һ�ڿΣ���ɫ���ʾ�գ���ɫ��ʾ�Ѿ�ѡ�񣬻�ɫ��ʾ�Ѿ��пγ�
    	int CN=0,WD=0;
		for (int i = 0; i < 7*MAX_CLASSES_PER_DAY; i++)
		{
			Map<String, Object> cell = new HashMap<String, Object>();
			CN=i/7;
			WD=i%7;
			if(WD==0)WD=6;//����WDΪ7,��Ӧ��������WD�еı����6
			else WD--;
			//����ָʾ�������ƽ��棬�������û���������ʱ�����Ա����β����Ľ��
			if(classIndicaterTable_New[WD][CN]==CLASS_INDICATER_OCCUPIED)
			{	//��ʱ���Ѿ��пγ̣��û�
				cell.put("imageview", R.drawable.gridgrey);
			}
			else if(classIndicaterTable_New[WD][CN]==CLASS_INDICATER_SELECTED)
			{
				cell.put("imageview", R.drawable.gridyellow);
			}
			else cell.put("imageview", R.drawable.gridwhite);
			cells.add(cell);
		}

		for(int i=0; i< MAX_CLASSES_PER_DAY; i++)
		{
			//����ʹ��add view�ķ�������������
	    	TextView tv=new TextView(this);
	    	tv.setText(String.valueOf(i+1));
//	    	tv.setWidth(LayoutParams.FILL_PARENT);
	    	tv.setLayoutParams(new LayoutParams(72,72));
	    	tv.setGravity(Gravity.CENTER);
	    	ll.addView(tv,i);
		}
		
		SimpleAdapter simpleAdapter = new SimpleAdapter(
				this, 
				cells,
				R.layout.gridcell, new String[]{ "imageview" }, 
				new int[]{ R.id.imageview }
		);
		gridView.setAdapter(simpleAdapter);	
		
		gridView.setOnItemClickListener(new OnItemClickListener() 
		{
			public void onItemClick(AdapterView<?> parent, View v, int position, long id)
			{
				Toast.makeText(editClass.this, "��ѡ����" + (position + 1) + " ��ͼƬ", Toast.LENGTH_SHORT).show();				
				ImageView vv=(ImageView)v;
				int CN=position/7;
				int WD=position%7;
				//�����������ڵ�һ�У����������������һ��
				if(WD==0)WD=7;
//				else if(WD==0)WD=5;
				WD--;
				if(classIndicaterTable_New[WD][CN]==CLASS_INDICATER_SELECTED)
				{	//�����λ���û��Ѿ�ѡ������򱾴�ѡ���ʾȡ�����ָ�֮ǰ��״̬
					classIndicaterTable_New[WD][CN]=classIndicaterTable[WD][CN];
					vv.setImageResource(gridResIDs[classIndicaterTable_New[WD][CN]]);
				}
				else
				{
					classIndicaterTable_New[WD][CN]=CLASS_INDICATER_SELECTED;
					vv.setImageResource(gridResIDs[CLASS_INDICATER_SELECTED]);
				}
			}
		});
		
		
		bGridViewOK.setOnClickListener(new Button.OnClickListener()
        {
        	@Override
        	public void onClick(View v)
        	{
        		//��ʾ�û�ѡ��ȷ�ϣ���onCreate�в���Ҫ�ٳ�ʼ��
    	    	classTableModifyFlag=false;        		
        		onCreate(null);
        		setViewContent();
        	}
        });
		bGridViewCancel.setOnClickListener(new Button.OnClickListener()
        {
        	@Override
        	public void onClick(View v)
        	{
        		 //�û�ѡ��ȡ������onCreate����Ҫ�ٴγ�ʼ��
    	    	classTableModifyFlag=true;  
        		onCreate(null);
        		setViewContent();
        	}
        });
    }
    //setClassSelectingGrid
	void resourceInitialize()
	{
	
	    spWeekDay=(Spinner)findViewById(R.id.spinnerWeekDay);
	    spClassNum=(Spinner)findViewById(R.id.spinnerClassNum);
	    bMultiOperate=(Button)findViewById(R.id.buttonEditCancel);
	    bAdd=(Button)findViewById(R.id.buttonAddClass);
	    bDelete=(Button)findViewById(R.id.buttonMultiOperate);
	    bSetClassTime=(Button)findViewById(R.id.buttonSetClassTime);
	    etClassName=(EditText)findViewById(R.id.editTextClassName);
	    etClassLocation=(EditText)findViewById(R.id.editTextClassLocation);
	    etTeacherName=(EditText)findViewById(R.id.editTextTeacherName);
//	    spContinuumClass = (Spinner)findViewById(R.id.spinnerContinuumClass);
	    
	    adapterWD=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,pWeekDay);
	    adapterCN=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,pClassNum);
	    adapterCC=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,pContinuumClass);
	    
	    adapterWD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    adapterCN.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);	
	    adapterCC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);	
	    
	    spWeekDay.setAdapter(adapterWD);
	    spClassNum.setAdapter(adapterCN);
//	    spContinuumClass.setAdapter(adapterCC);
	    
	    //�������������������
	    spWeekDay.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
	    {
	    	@Override
	    	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	    	{
	    		arg0.setVisibility(View.VISIBLE);
	    		//�ж�����֮ǰѡ�еĿ�ʱ�Ƿ��Ѿ���ʶΪ��ѡ�����Ϊ��ѡ����ָ�ԭ��״̬
	    		if(classIndicaterTable_New[m_WeekDay-1][m_ClassNum-1]==CLASS_INDICATER_SELECTED)
	    		{
	    			classIndicaterTable_New[m_WeekDay-1][m_ClassNum-1]=classIndicaterTable[m_WeekDay-1][m_ClassNum-1];
	    		}
	    		m_WeekDay=arg2+1;
	    		//���±�������
	    		classIndicaterTable_New[m_WeekDay-1][m_ClassNum-1]=CLASS_INDICATER_SELECTED;
	    		//����isUpdateWDSpinner�ж��Ƿ�Ҫ���¿��Ƶ�ֵ
	    		if(isUpdateWDSpinner==true)
	    		{
		    		//ѡ��󽫶�Ӧ�Ŀγ���Ϣˢ�µ�edit text�ؼ���
		    		ClassDB cDB=new ClassDB(editClass.this);
		    		Class cc=cDB.readClass(m_ClassNum, m_WeekDay);
		    		if(cc!=null)
		    		{
		    			etClassName.setText(cc.getClassName());
		    			etTeacherName.setText(cc.getTeacherName());
		//   			etClassLocation.setText(cc.getClassLocation);      			
		    		}
		    		else
		    		{
		    			etClassName.setText(null);
		    			etTeacherName.setText(null);
		    		}
	    		}
	    		else isUpdateWDSpinner=true;
	    	}
	    	@Override
	    	public void onNothingSelected(AdapterView<?>arg0)
	    	{
	//    		m_WeekDay=pWeekDay[0];
	    		m_WeekDay=1;
	    	}
	    });
	    
	    
	    
	    spClassNum.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
	    {
	    	@Override
	    	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	    	{
	    		arg0.setVisibility(View.VISIBLE);
	    		if(classIndicaterTable_New[m_WeekDay-1][m_ClassNum-1]==CLASS_INDICATER_SELECTED)
	    		{
	    			classIndicaterTable_New[m_WeekDay-1][m_ClassNum-1]=classIndicaterTable[m_WeekDay-1][m_ClassNum-1];
	    		}
	    		m_ClassNum=arg2+1;
	    		classIndicaterTable_New[m_WeekDay-1][m_ClassNum-1]=CLASS_INDICATER_SELECTED;
	    		if(isUpdateCNSpinner==true)
	    		{
		    		//ѡ��󽫶�Ӧ�Ŀγ���Ϣˢ�µ�edit text�ؼ���
		    		ClassDB cDB=new ClassDB(editClass.this);
		    		Class cc=cDB.readClass(m_ClassNum, m_WeekDay);
		    		if(cc!=null)
		    		{
		    			etClassName.setText(cc.getClassName());
		    			etTeacherName.setText(cc.getTeacherName());
		    		}
		    		else
		    		{
		    			etClassName.setText(null);
		    			etTeacherName.setText(null);
		    		}
	    		}
	    		else isUpdateCNSpinner=true;
	    	}
	    	@Override
	    	public void onNothingSelected(AdapterView<?>arg0)
	    	{
	    		m_ClassNum=1;
	    	}
	    });
	    /*
	    //���������Ͽν��������˵�
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
	    */
	    etClassName.setHint("������γ�����");
	    etClassLocation.setHint("�������Ͽν���");
	    etTeacherName.setHint("��������ʦ����");
		if(m_ClassName!=null)
		{
			etClassName.setText(m_ClassName);
		}
		if(m_TeacherName!=null)
		{
			etTeacherName.setText(m_TeacherName);
		}
		if(m_ClassLocation!=null)
		{
			etClassLocation.setText(m_ClassLocation);
		}
		
	    //��Ӱ�����Ӧ����
	    bAdd.setOnClickListener(new Button.OnClickListener()
	    {
	    	@Override
	    	public void onClick(View v)
	    	{
	    		//�����Ӻ󣬶�ȡָʾ���е�����״̬�����γ���Ϣд�����ݿ�
	    		ClassDB cDB=new ClassDB(editClass.this);
	    		m_ClassName=etClassName.getText().toString();
	    		m_ClassLocation=etClassLocation.getText().toString();
	    		m_TeacherName=etTeacherName.getText().toString();
//	    		Class C=new Class(m_ClassNum,m_WeekDay,m_ClassName,m_TeacherName,m_ContinuumClass,m_ClassTimeHour,m_ClassTimeMinute);
	    		Class C=new Class();
	    		//�Ƚ����༭����Ŀγ���Ϣд�����ݿ⣬֮���ٶ�ȡָʾ���е�����
	    		cDB.writeClass(C);
	    		int WD=0,CN=0;
	    		for(WD=0;WD<7;WD++)
	    		{
	    			for(CN=0;CN<MAX_CLASSES_PER_DAY;CN++)
	    			{
	    				if(classIndicaterTable_New[WD][CN]==CLASS_INDICATER_SELECTED)
	    				{
	    					C.setWeekDay(WD+1);
	    					C.setClassNum(CN+1);
	                		cDB.writeClass(C);
	                		//��������ָʾ�������
	                		classIndicaterTable[WD][CN]=CLASS_INDICATER_OCCUPIED;
	                		classIndicaterTable_New[WD][CN]=CLASS_INDICATER_OCCUPIED;
	    				}
	    			}
	    		}
	    	}
	    });
	    //��Ӻ�ɾ������Ҫ���ڲ�ͬ�Ľ����ϲ���
	    bDelete.setOnClickListener(new Button.OnClickListener()
	    {
	    	@Override
	    	public void onClick(View v)
	    	{
	    		ClassDB cDB=new ClassDB(editClass.this);
	    		cDB.deleteClass(m_ClassNum, m_WeekDay);
	    		etClassName.setText(null);
	    		etTeacherName.setText(null);
	    		//�ṩ����ɾ������
	    		int WD=0,CN=0;
	    		for(WD=0;WD<7;WD++)
	    		{
	    			for(CN=0;CN<MAX_CLASSES_PER_DAY;CN++)
	    			{
	    				if(classIndicaterTable_New[WD][CN]==CLASS_INDICATER_SELECTED)
	    				{
	    					cDB.deleteClass(CN+1,WD+1);
	                		classIndicaterTable[WD][CN]=CLASS_INDICATER_EMPTY;
	                		classIndicaterTable_New[WD][CN]=CLASS_INDICATER_EMPTY;
	    				}
	    			}
	    		}
	    	}
	    });
	    //���������޸İ���
	    bMultiOperate.setOnClickListener(new Button.OnClickListener()
	    {
	    	@Override
	    	public void onClick(View v)
	    	{
	/*
	    		Intent intent = new Intent();
	    		intent.setClass(editClass.this,SchoolLifeActivity.class);
	    		startActivity(intent);
	    		editClass.this.finish();
	*/        		
	    		//�й�ȥ֮ǰ���Ƚ����ݱ�������
	    		m_ClassName=etClassName.getText().toString();
	    		m_ClassLocation=etClassLocation.getText().toString();
	    		m_TeacherName=etTeacherName.getText().toString();
	    		//����ǰ������ѡ���п�ʱ����Ϊ��ѡ
	    		if(classIndicaterTable_New[m_WeekDay-1][m_ClassNum-1]!=CLASS_INDICATER_SELECTED)
	    		{
	    			classIndicaterTable_New[m_WeekDay-1][m_ClassNum-1]=CLASS_INDICATER_SELECTED;
	    		}
	    		setClassSelectingGrid();
	    	}
	    });
	    //����ʱ��
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
	}
	//resourceInitialize()
	//��������ҳ��ؼ������������е�setClassSelectingGrid�����󣬿�����ȷ��ʾ���õ�ֵ
	void setViewContent()
	{
		isUpdateWDSpinner=false;
		spWeekDay.setSelection(m_WeekDay-1);
		isUpdateCNSpinner=false;
		spClassNum.setSelection(m_ClassNum-1);
//		spContinuumClass.setSelection(m_ContinuumClass);
		if(m_ClassName!=null)
		{
			etClassName.setText(m_ClassName);
		}
		if(m_TeacherName!=null)
		{
			etTeacherName.setText(m_TeacherName);
		}
		if(m_ClassLocation!=null)
		{
			etClassLocation.setText(m_ClassLocation);
		}
	}
	//setViewContent()
}//main
