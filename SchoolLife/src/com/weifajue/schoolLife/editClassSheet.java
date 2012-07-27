package com.weifajue.schoolLife;

import java.util.Calendar;
import java.util.Date;

import com.weifajue.schoolLife.data.ClassDB;
import com.weifajue.schoolLife.model.ClassSheet;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class editClassSheet extends Activity{

	private int editType;
	private String editSheetName;
	private ClassSheet classSheet;
	
	private View topHeader;
	
	private EditText etClassSheetName,etMaxClassNum,etClassMinuters;
	private Button bSetClassTime,bOK,bCancel;
	private Button bSetDate;
//	private int timeBarTime=20;
//	private DatePicker dpDatePicker;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_class_sheet);

		//读取intent传入参数，判断是编辑还是新建课表
		Bundle bundle=getIntent().getExtras();
		editType=bundle.getInt("edit_type");
		classSheet=new ClassSheet();
		if(Constant.CLASS_SHEET_EDIT_TYPE_EDIT==editType)
		{
			editSheetName=bundle.getString("sheet_name");
			ClassDB cDB=new ClassDB(editClassSheet.this);
			classSheet=cDB.readClassSheet(editSheetName);
		}
		else classSheet=new ClassSheet();
		
		//初始化页面标题，左右箭头不显示，根据新建或者编辑设定标题
		topHeader=(View)findViewById(R.id.editClassSheetHeader);
		Button btnLeft=(Button)topHeader.findViewById(R.id.top_btn_left);
		btnLeft.setVisibility(View.INVISIBLE);
		Button btnRight=(Button)topHeader.findViewById(R.id.top_btn_right);
		btnRight.setVisibility(View.INVISIBLE);
		TextView top_textView=(TextView)topHeader.findViewById(R.id.tv_toptitle);
		if(Constant.CLASS_SHEET_EDIT_TYPE_EDIT==editType)
		{
			top_textView.setText("编辑课表");
		}
		else top_textView.setText("新建课表");
		
		//控件初始化
		widgetInitialize();
		
	}
	
	 private void widgetInitialize()
	 {
			//控制初始化
			etClassSheetName=(EditText)findViewById(R.id.etClassSheetName);
			etMaxClassNum=(EditText)findViewById(R.id.etMaxClassNum);
			etClassMinuters=(EditText)findViewById(R.id.etClassMinuters);
			bSetClassTime=(Button)findViewById(R.id.bSetClassTime);
			bOK=(Button)findViewById(R.id.bOK);
			bCancel=(Button)findViewById(R.id.bCancel);
			bSetDate=(Button)findViewById(R.id.bSetDate);
//			dpDatePicker=(DatePicker)findViewById(R.id.dpSheetEffectDate);
			
//			if(Constant.CLASS_SHEET_EDIT_TYPE_EDIT==editType)
//			{
				etClassSheetName.setText(classSheet.getClassSheetName());
				etMaxClassNum.setText(String.valueOf(classSheet.getMaxClassNumPerDay()));
				etClassMinuters.setText(String.valueOf(classSheet.getMinuteForPerClass()));
/*			}
			else
			{
				etClassSheetName.setHint("请输入课表名称");
				etMaxClassNum.setHint("请输入每天课节数");
				etClassMinuters.setHint("请输入课时长");
			}
*/			
			//设置时间
			bSetClassTime.setOnClickListener(new Button.OnClickListener()
		    {
		    	@Override
		    	public void onClick(View v)
		    	{
		    		final int classNum=classSheet.getMaxClassNumPerDay();
		    		final int[] timeTemp=new int[classNum];
		    		//暂时保存之前的时间值，界面上修改的是classSheeet的内容，如果用户修改后选择取消，但该暂存的值重新写回classSheet中即可
		    		for(int i=0;i<classNum;i++)
		    		{
		    			timeTemp[i]=classSheet.getClassTimeMinuter(i);
		    		}
		    		classSheet.setMaxClassNumPerDay(Integer.valueOf(etMaxClassNum.getText().toString()));
		    		classSheet.setMinuteForPerClass(Integer.valueOf(etClassMinuters.getText().toString()));
		    		
					Toast.makeText(editClassSheet.this, "设置上课时间", Toast.LENGTH_SHORT).show();		    		
										
					RelativeLayout setClasstimeLL=new RelativeLayout(editClassSheet.this);
					ListView setClassTimeLV=new ListView(editClassSheet.this);
					SetTimeAdapter timeAdapter=new SetTimeAdapter(editClassSheet.this);
					setClassTimeLV.setAdapter(timeAdapter);
					setClasstimeLL.addView(setClassTimeLV,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.FILL_PARENT));
					new AlertDialog.Builder(editClassSheet.this)
					.setTitle("设置上课时间")
					.setView(setClasstimeLL)
					.setPositiveButton("完成", new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface dialog, int whichButton)
						{
							//保存时间结果
							
						}
					})
					.setNegativeButton("取消",new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface dialog, int whichButton)
						{
							//用户选择取消，则将暂存的值重新写回去
				    		for(int i=0;i<classNum;i++)
				    		{
				    			classSheet.setClassTimeMinuter(timeTemp[i],i);
				    		}
						}
					})
					.show();

		    	}
		    });
			
		    bSetDate.setOnClickListener(new Button.OnClickListener()
		    {
		    	@Override
		    	public void onClick(View v)
		    	{
		    		Calendar c=Calendar.getInstance();
		    		Date d=classSheet.getdateStart();
		    		int year=d.getYear();
		    		int month=d.getMonth();
		    		int day=d.getDate();
		    		int year2=c.get(Calendar.YEAR);
		    		int month2=c.get(Calendar.MONTH);
		    		int day2=c.get(Calendar.DAY_OF_MONTH);
		    		
		    		
		    		new DatePickerDialog(editClassSheet.this,
		    				new DatePickerDialog.OnDateSetListener() 
		    				{								
								@Override
								public void onDateSet(DatePicker view, int year, int monthOfYear,
										int dayOfMonth) {
									// TODO Auto-generated method stub
									Toast.makeText(editClassSheet.this, "设置时期", Toast.LENGTH_SHORT).show();		    											
									//因为Date的时间是从1900年开始算，所以要对应进行转化
									classSheet.setdateStart(new Date(year-1900,monthOfYear,dayOfMonth));
								}
		    				},d.getYear()+1900,d.getMonth(),d.getDate()).show();
//							},c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).show();		    		
		    	}
		    });	
		    bOK.setOnClickListener(new Button.OnClickListener()
		    {
		    	@Override
		    	public void onClick(View v)
		    	{
					String sheetName=etClassSheetName.getText().toString();
					classSheet.setMaxClassNumPerDay(Integer.valueOf(etMaxClassNum.getText().toString()));
		    		classSheet.setMinuteForPerClass(Integer.valueOf(etClassMinuters.getText().toString()));
		    		classSheet.setClassSheetName(sheetName);
//					if(Constant.CLASS_SHEET_EDIT_TYPE_NEW==editType)
//					{
						ClassDB cDB=new ClassDB(editClassSheet.this);
						cDB.writeClassSheet(classSheet);
						Intent intent=new Intent(editClassSheet.this,manageClassSheet.class);
						intent.putExtra("result", true);
						startActivity(intent); 
						editClassSheet.this.finish();
//					}
				}
		    });
		    
		    bCancel.setOnClickListener(new Button.OnClickListener()
		    {
		    	@Override
		    	public void onClick(View v)
		    	{
					Intent intent=new Intent(editClassSheet.this,manageClassSheet.class);
					intent.putExtra("result", false);
					startActivity(intent); 
					editClassSheet.this.finish();		    		
		    	}
		    });		    
		    
	 }
	 
	 private class SetTimeAdapter extends BaseAdapter
	 {
		private LayoutInflater layoutInflater;
		Context context;
		
		public SetTimeAdapter(Context context)
		{
			this.context = context;
			layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		//上课时间从6点开始，一直到晚上22点，共16个小时，seekBar的每一格代表10分钟，则总共需要16*6=96格
		private int progressToMinuter(int progress)
		{
			return progress*10;
		}
		private String minutersToProgressText(int minuters)
		{
			return String.valueOf(minuters/60+6)+":"+String.valueOf(minuters%60);
		}
		//时间到seekBar值的转换，时间取自ClassSheet，从6点开始算
		private int minutersToProgress(int minuters)
		{
			return minuters/10;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return classSheet.getMaxClassNumPerDay();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return classSheet.getClassTimeMinuter(arg0);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LinearLayout linearLayout 	= (LinearLayout)layoutInflater.inflate(R.layout.set_class_time_list_item, null);
			TextView tvClassNum		 	= (TextView) linearLayout.findViewById(R.id.tvClassNum);
			SeekBar seekBar		 		= (SeekBar) linearLayout.findViewById(R.id.sbClassTimeBar);
			TextView tvClassTime		= (TextView)linearLayout.findViewById(R.id.tvSetClassTime);
			Button bIncrease			= (Button)linearLayout.findViewById(R.id.bIncreaseTime);
			Button bDecrease			= (Button)linearLayout.findViewById(R.id.bDecreaseTime);
			
			final int p=position;
			
			tvClassNum.setText("第"+String.valueOf(position+1)+"节");
			tvClassTime.setText(minutersToProgressText(classSheet.getClassTimeMinuter(position)));
			seekBar.setProgress(minutersToProgress(classSheet.getClassTimeMinuter(position)));
//    		seekBar.setSecondaryProgress(minutersToProgress(classSheet.getClassTimeMinuter(position))+4);
			seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
				
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
	
				}
				
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					// TODO Auto-generated method stub
					classSheet.setClassTimeMinuter(progressToMinuter(progress), p);
					notifyDataSetChanged();
				}
			});
			bIncrease.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					int timeMinuter=classSheet.getClassTimeMinuter(p);
					if(timeMinuter<Constant.SET_TIME_MINUTER_SEEKBAR_MAX*10)
					{
						timeMinuter+=10;
						classSheet.setClassTimeMinuter(timeMinuter, p);
						notifyDataSetChanged();
					}
				}				
			});
			bDecrease.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int timeMinuter=classSheet.getClassTimeMinuter(p);
					if(timeMinuter>10)
					{
						timeMinuter-=10;
						classSheet.setClassTimeMinuter(timeMinuter, p);
						notifyDataSetChanged();
					}				
				}
				
			});
   		return linearLayout;
		}		 
	 }
}
