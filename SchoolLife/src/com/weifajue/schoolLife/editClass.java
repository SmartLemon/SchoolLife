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
	private static final String[] pWeekDay={"一","二","三","四","五","六","日"};
	private static final String[] pContinuumClass={"0","1","2","3","4","5","6"};
	private static final int MAX_CLASSES_PER_DAY=5;
	private static final int CLASSNUM=MAX_CLASSES_PER_DAY;

	private String pClassNum[]=new String[CLASSNUM];
	//界面标题
	public View topHeader;
	//控件变量定位
	private Spinner spWeekDay,spClassNum;
	private ArrayAdapter<String> adapterWD,adapterCN,adapterCC;
	private Button bMultiOperate,bAdd,bDelete;
	private Button bSetClassTime;
	private EditText etClassName,etClassLocation,etTeacherName;
	//全局变量
	//课程信息
	public int m_WeekDay=1,m_ClassNum=1;
	public int m_ClassTimeHour=8,m_ClassTimeMinute=0;
	public String m_ClassName,m_ClassLocation,m_TeacherName;
	public int m_ContinuumClass=0;
	//课程指标表，二维数组,用来保存当前一周课程表的各节指标状态
	//两个数组,一个用来保存原始的状态，一个用来保存用户选择后新的状态
	private int[][] classIndicaterTable=new int[7][MAX_CLASSES_PER_DAY];
	private int[][] classIndicaterTable_New=new int[7][MAX_CLASSES_PER_DAY];
	//表示上面指示表每一个元素的占用状态，根据该对应课程是否已设，分下面三种状态
	private static final int CLASS_INDICATER_EMPTY=0;	//空闲
	private static final int CLASS_INDICATER_OCCUPIED=1;//已经占用
	private static final int CLASS_INDICATER_SELECTED=2;//当前用户选中
	//课程指示表修改标志，用于表示在选择界面中修改了指标表
	private boolean classTableModifyFlag=true;
	//用来判断是否在改变下拉表时，读取更新课程内容，分别对应weekday,classnum,
	private boolean isUpdateWDSpinner=true;
	private boolean isUpdateCNSpinner=true;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editclasslist);
        Log.e("DebugLog","run in editClass Activity");
    	
        //根据节数初始化数组
    	for(int i=0;i<CLASSNUM;i++)
    	{
    		pClassNum[i]=String.valueOf(i+1);
    	}
    	//由于完成课程指示表的选择界面后，会回调onCreate函数，
    	//所以需要根据修改的标示，判断在这里是否需要初始化指标示表,classTableModifyFlag为true表示需要
    	if(classTableModifyFlag==true)
    	{
	    	//初始化课程指示表
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
	    	//初始化后置为false,表示不需要再初始化
	    	classTableModifyFlag=false;
    	}
		//设置顶端标题栏，把左右两个按键去显示（保留位置），更改标题
		topHeader=(View)findViewById(R.id.editClassHeader);
		//显示向左的箭头，用于返回
//		Button btn=(Button)topHeader.findViewById(R.id.top_btn_left);
//		btn.setVisibility(View.INVISIBLE);
		Button btn=(Button)topHeader.findViewById(R.id.top_btn_right);
		btn.setVisibility(View.INVISIBLE);
		TextView top_textView=(TextView)topHeader.findViewById(R.id.tv_toptitle);
		top_textView.setText("编辑课程");
    	//初始化控制资源
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
//加载grid图片，每一格代表一节课，白色格表示空，黄色表示已经选择，灰色表示已经有课程
    	int CN=0,WD=0;
		for (int i = 0; i < 7*MAX_CLASSES_PER_DAY; i++)
		{
			Map<String, Object> cell = new HashMap<String, Object>();
			CN=i/7;
			WD=i%7;
			if(WD==0)WD=6;//周日WD为7,对应表格里面的WD列的编号是6
			else WD--;
			//按新指示表来绘制界面，这样在用户反复操作时，可以保存多次操作的结果
			if(classIndicaterTable_New[WD][CN]==CLASS_INDICATER_OCCUPIED)
			{	//该时间已经有课程，置灰
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
			//尝试使用add view的方法来增加行名
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
				Toast.makeText(editClass.this, "你选择了" + (position + 1) + " 号图片", Toast.LENGTH_SHORT).show();				
				ImageView vv=(ImageView)v;
				int CN=position/7;
				int WD=position%7;
				//修正，周日在第一列，但在数组中是最后一列
				if(WD==0)WD=7;
//				else if(WD==0)WD=5;
				WD--;
				if(classIndicaterTable_New[WD][CN]==CLASS_INDICATER_SELECTED)
				{	//如果该位置用户已经选择过，则本次选择表示取消，恢复之前的状态
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
        		//表示用户选择确认，在onCreate中不需要再初始化
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
        		 //用户选择取消，在onCreate中需要再次初始化
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
	    
	    //设置两个下拉框的内容
	    spWeekDay.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
	    {
	    	@Override
	    	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	    	{
	    		arg0.setVisibility(View.VISIBLE);
	    		//判断下拉之前选中的课时是否已经标识为已选，如果为已选，则恢复原来状态
	    		if(classIndicaterTable_New[m_WeekDay-1][m_ClassNum-1]==CLASS_INDICATER_SELECTED)
	    		{
	    			classIndicaterTable_New[m_WeekDay-1][m_ClassNum-1]=classIndicaterTable[m_WeekDay-1][m_ClassNum-1];
	    		}
	    		m_WeekDay=arg2+1;
	    		//更新表格的内容
	    		classIndicaterTable_New[m_WeekDay-1][m_ClassNum-1]=CLASS_INDICATER_SELECTED;
	    		//根据isUpdateWDSpinner判断是否要更新控制的值
	    		if(isUpdateWDSpinner==true)
	    		{
		    		//选择后将对应的课程信息刷新到edit text控件中
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
		    		//选择后将对应的课程信息刷新到edit text控件中
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
	    */
	    etClassName.setHint("请输入课程名称");
	    etClassLocation.setHint("请输入上课教室");
	    etTeacherName.setHint("请输入老师名字");
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
		
	    //添加按键向应函数
	    bAdd.setOnClickListener(new Button.OnClickListener()
	    {
	    	@Override
	    	public void onClick(View v)
	    	{
	    		//点击添加后，读取指示表中的设置状态，将课程信息写入数据库
	    		ClassDB cDB=new ClassDB(editClass.this);
	    		m_ClassName=etClassName.getText().toString();
	    		m_ClassLocation=etClassLocation.getText().toString();
	    		m_TeacherName=etTeacherName.getText().toString();
//	    		Class C=new Class(m_ClassNum,m_WeekDay,m_ClassName,m_TeacherName,m_ContinuumClass,m_ClassTimeHour,m_ClassTimeMinute);
	    		Class C=new Class();
	    		//先将主编辑界面的课程信息写入数据库，之后再读取指示表中的内容
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
	                		//更新两个指示表的内容
	                		classIndicaterTable[WD][CN]=CLASS_INDICATER_OCCUPIED;
	                		classIndicaterTable_New[WD][CN]=CLASS_INDICATER_OCCUPIED;
	    				}
	    			}
	    		}
	    	}
	    });
	    //添加和删除考虑要放在不同的界面上操作
	    bDelete.setOnClickListener(new Button.OnClickListener()
	    {
	    	@Override
	    	public void onClick(View v)
	    	{
	    		ClassDB cDB=new ClassDB(editClass.this);
	    		cDB.deleteClass(m_ClassNum, m_WeekDay);
	    		etClassName.setText(null);
	    		etTeacherName.setText(null);
	    		//提供批量删除功能
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
	    //设置批量修改按键
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
	    		//切过去之前，先将内容保存下来
	    		m_ClassName=etClassName.getText().toString();
	    		m_ClassLocation=etClassLocation.getText().toString();
	    		m_TeacherName=etTeacherName.getText().toString();
	    		//将当前下拉框选择中课时设置为已选
	    		if(classIndicaterTable_New[m_WeekDay-1][m_ClassNum-1]!=CLASS_INDICATER_SELECTED)
	    		{
	    			classIndicaterTable_New[m_WeekDay-1][m_ClassNum-1]=CLASS_INDICATER_SELECTED;
	    		}
	    		setClassSelectingGrid();
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
	}
	//resourceInitialize()
	//用来设置页面控件内容这样在切到setClassSelectingGrid回来后，可以正确显示设置的值
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
