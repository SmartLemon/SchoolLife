/**
 * 
 */
package com.weifajue.schoolLife;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.weifajue.schoolLife.Util.timeProcess;
import com.weifajue.schoolLife.data.ClassDB;
import com.weifajue.schoolLife.data.LocalFile;
import com.weifajue.schoolLife.model.Class;
import com.weifajue.schoolLife.model.ClassSheet;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
//import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TabHost.OnTabChangeListener;
import android.view.*;
import android.view.View.OnClickListener;
import android.util.Log;
import android.widget.*;


/**
 * @author SmartGang
 *
 */
public class viewClass extends Activity{
	
	ListView mListView;
	public TabHost mainTabHost;
	private String currentClassSheetName;
	private ClassSheet currentClassSheet;
	private int currentShowedWN;
	private int currentShowedWD;
	private ClassDB classDB;
	private ClassDetailListAdapter classDetailListAdapter;

	
	public View topHeader;//标题头
	
	private static String[] classListContent=new String[]
	{
		"tvClassTime","tvClassNum",
		"tvClassName",
		"tvTeacherName",
		"tvClassLocation"
	};
	
	private static int[] classListContentID=new int[]
	{
		R.id.textViewClassTime,R.id.textViewClassNum,
		R.id.textViewClassName,
		R.id.textViewTeacherName,R.id.textViewClassLocation
	};

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.e("DebugLog","run in main");
        LocalFile localFile=new LocalFile();
        //获取当前的默认ClassSheetName
        currentClassSheetName=localFile.getCurrentClassSheetName(this); 
        //以下代码做异常保护，防止从localFile中读到的sheetName在DB中没有保存
        classDB=new ClassDB(this);
        String[] sheetList=classDB.readClassSheetList();
        int sheetNum=sheetList.length;
        int i=0;
        for(i=0;i<sheetNum;i++)
        {
        	if(currentClassSheetName.equals(sheetList[i]))break;
        }
        if(i==sheetNum)
        {	//如果没有保存，则数据存中有值，则将第一个设置为默认
        	if(sheetNum>0)currentClassSheetName=sheetList[0];
        	else currentClassSheetName="default";
        }
        currentClassSheet=classDB.readClassSheet(currentClassSheetName);
        currentShowedWN=currentClassSheet.getCurrentWeekNum();
        classDetailListAdapter=new ClassDetailListAdapter(viewClass.this);
        setupTopHeader();
        
        setupListFrame();
    }
    
    public void setupTopHeader()
    {
    	topHeader=(View)findViewById(R.id.main_header);
		Button leftButton=(Button)topHeader.findViewById(R.id.top_btn_left);
		Button rightButton=(Button)topHeader.findViewById(R.id.top_btn_right);
		final TextView top_textView=(TextView)topHeader.findViewById(R.id.tv_toptitle);
		top_textView.setText("第"+String.valueOf(currentShowedWN)+"周");
		//左键响应函数，显示前一周的课表
		leftButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(currentShowedWN>1)currentShowedWN--;
				classDetailListAdapter.notifyDataSetChanged();
				top_textView.setText("第"+String.valueOf(currentShowedWN)+"周");
			}
			
		});
		//右键响应函数，显示下一周的课表
		rightButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/**
				 * 暂时没有设上限
				 */
				currentShowedWN++;
				classDetailListAdapter.notifyDataSetChanged();
				top_textView.setText("第"+String.valueOf(currentShowedWN)+"周");
			}
			
		});
    }
    
    public void setupListFrame()
    {
    	mListView=(ListView)findViewById(R.id.listViewClassList);
    	    	
        //TabHost控件初始化
        mainTabHost=(TabHost)findViewById(R.id.maintabhost);
        mainTabHost.setup();
		View view1 = View.inflate(viewClass.this, R.layout.class_tab_ind, null);
		((ImageView) view1.findViewById(R.id.tab_imageview_icon)).setImageResource(R.drawable.sun_tab_selector);
        mainTabHost.addTab(mainTabHost.newTabSpec("日")   
                .setIndicator(view1)
                .setContent(R.id.listViewClassList));   

		View view2 = View.inflate(viewClass.this, R.layout.class_tab_ind, null);
		((ImageView) view2.findViewById(R.id.tab_imageview_icon)).setImageResource(R.drawable.mon_tab_selector);
        mainTabHost.addTab(mainTabHost.newTabSpec("一")   
                .setIndicator(view2)   
                .setContent(R.id.listViewClassList));
        
		View view3 = View.inflate(viewClass.this, R.layout.class_tab_ind, null);
		((ImageView) view3.findViewById(R.id.tab_imageview_icon)).setImageResource(R.drawable.tue_tab_selector);        
        mainTabHost.addTab(mainTabHost.newTabSpec("二")   
                .setIndicator(view3)   
                .setContent(R.id.listViewClassList));
        
		View view4 = View.inflate(viewClass.this, R.layout.class_tab_ind, null);
		((ImageView) view4.findViewById(R.id.tab_imageview_icon)).setImageResource(R.drawable.wed_tab_selector);        
        mainTabHost.addTab(mainTabHost.newTabSpec("三")   
                .setIndicator(view4)   
                .setContent(R.id.listViewClassList));   
           
		View view5 = View.inflate(viewClass.this, R.layout.class_tab_ind, null);
		((ImageView) view5.findViewById(R.id.tab_imageview_icon)).setImageResource(R.drawable.thu_tab_selector);        
        mainTabHost.addTab(mainTabHost.newTabSpec("四")   
                .setIndicator(view5)   
                .setContent(R.id.listViewClassList));   
           
		View view6 = View.inflate(viewClass.this, R.layout.class_tab_ind, null);
		((ImageView) view6.findViewById(R.id.tab_imageview_icon)).setImageResource(R.drawable.fri_tab_selector);        
        mainTabHost.addTab(mainTabHost.newTabSpec("五")   
                .setIndicator(view6)   
                .setContent(R.id.listViewClassList));
        
		View view7 = View.inflate(viewClass.this, R.layout.class_tab_ind, null);
		((ImageView) view7.findViewById(R.id.tab_imageview_icon)).setImageResource(R.drawable.sat_tab_selector);        
        mainTabHost.addTab(mainTabHost.newTabSpec("六")   
                .setIndicator(view7)   
                .setContent(R.id.listViewClassList));
       //为TabHost控件设置响应，分别加载当天的课程表到listView中
        mainTabHost.setOnTabChangedListener(new OnTabChangeListener()
        {
        	@Override
        	public void onTabChanged(String tabId)
        	{
        		if(tabId=="日")
        		{
        			currentShowedWD=0;
        		}
        		else if(tabId=="一")
        		{
        			currentShowedWD=1;
        		}
        		else if(tabId=="二")
        		{
        			currentShowedWD=2;
        		}
        		else if(tabId=="三")
        		{
        			currentShowedWD=3;
        		}
        		else if(tabId=="四")
        		{
        			currentShowedWD=4;
        		}
        		else if(tabId=="五")
        		{
        			currentShowedWD=5;
        		
        		}else if(tabId=="六")
        		{
        			currentShowedWD=6;
        		}
        		classDetailListAdapter.notifyDataSetChanged();
        	}
        });
        //默认开始显示周一的课程,会自动调用setOnTabChangedListener
        mainTabHost.setCurrentTab(1);
        
        mListView.setAdapter(classDetailListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
        	@Override
        	public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3)
        	{  
        	//View  The view within the AdapterView that was clicked (this will be a view provided by the adapter) 
        	//onItemClick方法的参数中,第二个参数就是子item的parent，直接用它找到子view 
    	        Toast.makeText(getApplicationContext(),
          	          "aaa" , Toast.LENGTH_SHORT)
          	          .show();
        	   }
        });
        
        Log.e("DebugLog","show personalPage View");
    }

    /**
     * @author SmartGang
     * 显示页面ListView控件的内容适配器
     * 在getView中同时读取模板查询classTemplateList和信息查询结果classDetailList
     * 对两个结果同一节课的内容进行对比，如果课程相同，则表示classDetailList的内容为正常课表内容
     * 如果不同且classDetailList内容不为空，表示课程已被修改，classTemplateList显示为历史信息
     */
    private class ClassDetailListAdapter extends BaseAdapter
    {
		private LayoutInflater layoutInflater;
		Context context;
		Class[] classTemplateList, classDetailList;
			
		public ClassDetailListAdapter(Context context)
		{	//传入classList的指针做为该adapter显示的内容
			this.context = context;
			layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			classTemplateList=classDB.readClassTemplateByDay(currentClassSheetName, currentShowedWD);			
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return currentClassSheet.getMaxClassNumPerDay();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			//该位置有课，则返该课程，否则返回为空
			int i=classTemplateList.length;
			for(int j=0;j<i;j++)
			{
				if(arg0==classTemplateList[j].getClassNum())
					return classTemplateList[j];
			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			RelativeLayout rl 	= (RelativeLayout)layoutInflater.inflate(R.layout.listviewitem, null);
			TextView tvClassName	= (TextView) rl.findViewById(R.id.textViewClassName);
			TextView tvClassTime	= (TextView) rl.findViewById(R.id.textViewClassTime);
			TextView tvClassNum		= (TextView) rl.findViewById(R.id.textViewClassNum);
			TextView tvTeacherName	= (TextView) rl.findViewById(R.id.textViewTeacherName);
			TextView tvClassRoom	= (TextView) rl.findViewById(R.id.textViewClassLocation);
			
			classTemplateList=classDB.readClassTemplateByDay(currentClassSheetName, currentShowedWD);
			classDetailList=classDB.readClassDayList(currentClassSheetName, currentShowedWN, currentShowedWD);
			
			int timeMuninute=currentClassSheet.getClassTimeMinuter(position);
			String classTime=String.valueOf(timeMuninute/60+6)+":"+String.valueOf(timeMuninute%60);
			if(null==classTemplateList[position])
			{
				tvClassName.setText("没课真好！！");
				tvClassTime.setText(classTime);
				tvClassNum.setText("第"+(position+1)+"节");
				tvTeacherName.setText("教师");
				tvClassRoom.setText("教室");
				}
			else
			{
				tvClassName.setText(classTemplateList[position].getClassName());
				tvClassTime.setText(classTime);
				tvClassNum.setText("第"+(position+1)+"节");
				tvTeacherName.setText(classTemplateList[position].getTeacherName());
				tvClassRoom.setText("教室");				
			}
			
			return rl;
		}
    	
    }
    
    
}


