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
import com.weifajue.schoolLife.model.Class;
import com.weifajue.schoolLife.model.ClassSheet;

import android.app.Activity;
//import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.view.*;
import android.util.Log;
import android.widget.*;


/**
 * @author SmartGang
 *
 */
public class viewClass extends Activity{
	
//	private static final int MAX_CLASSES_PER_DAY = 5;

	ListView mListView;
	
	public TabHost mainTabHost;
	

	private static String[] classNum = new String[]
	{ "第一节", "第二节", "第三节", "第四节", "第五节" };
	
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
/*	                                                  	
	private static String[] className = new String[]
	{ "语文", "数学", "英语", "地理", "体育" };	
*/
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.e("DebugLog","run in main");
        personalPage();
    }
/* *****************************************个人页面处理方法*********************************************/
/*
*  注意：setContentView(R.layout.main)语句必须放在最前
*/
    public void personalPage()
    {
//    	LinearLayout mLinearLayout=new LinearLayout(this);
        setContentView(R.layout.main);
        
//        setContentView(R.layout.listviewitem);
//        mListView=new ListView(this);
       
//        setContentView(R.layout.main);
//        mLinearLayout=(LinearLayout)findViewById(R.id.mainLinearLayout);
/*      2012-3-10日改动，本来直接加载ListView显示课程内容，重新设计为使用tabHost来承载
 * 		在后面loadTabView函数中来实现，将ListView加到TabView中 
 * 		LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(
        		LinearLayout.LayoutParams.FILL_PARENT,
        		LinearLayout.LayoutParams.WRAP_CONTENT);
        loadClassList();
        mLinearLayout.addView(mListView,param);
*/
        //listview控件初始化,必须在TabHost之前，因为tabhost会用到
//    	mListView=new ListView(this);
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
        			loadClassList(7);
        		}
        		else if(tabId=="一")
        		{
        			loadClassList(1);
        		}
        		else if(tabId=="二")
        		{
        			loadClassList(2);
        		}
        		else if(tabId=="三")
        		{
        			loadClassList(3);
        		}
        		else if(tabId=="四")
        		{
        			loadClassList(4);
        		}
        		else if(tabId=="五")
        		{
        			loadClassList(5);
        		
        		}else if(tabId=="六")
        		{
        			loadClassList(6);
        		}
        	}
        });
        //默认开始显示周一的课程,会自动调用setOnTabChangedListener
        mainTabHost.setCurrentTab(1);

        //尝试为listView设置响应函数，目前未成功

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
/*
    	        TextView textView= (TextView)findViewById(R.id.textViewClassTime);
        		//子view的点击事件
        	    textView.setOnClickListener(new View.OnClickListener() {
        	       @Override
        	       public void onClick(View arg0) {

        	       }
        	      });*/
        	   }
        });
        
        Log.e("DebugLog","show personalPage View");
    }
/*****************************************个人页面处理方法 end********************************************/
 
    //加载课程表内容到ListView中
    //传入参数WD表示要加载星期几的数据，1~7分别表示星期一至星期日
    public void loadClassList(int WD)
    {
    	ClassDB classDBforList=new ClassDB(viewClass.this);
    	Log.d("DatabaseDebug", "in loadClassList");
    	List<Map<String, Object>> appItems = new ArrayList<Map<String, Object>>();
		ClassSheet cCS=classDBforList.getCurrentClassSheet();
		timeProcess tp=new timeProcess();
		int WN=tp.weekOffset(cCS.getdateStart(), new Date());		
		Class[] classTemp=classDBforList.readClassDayList(WN,WD); 
		int classnumbers=classTemp.length;
		int maxClass=cCS.getMaxClassNumPerDay();
    	for(int CN=0;CN<maxClass;CN++)    		
		{
    		Map<String, Object> appItem = new HashMap<String, Object>();
    		int j=0;
    		for(j=0;j<classnumbers;j++)
	    	{
	    		//添加课程内容
    			//通过两次循环，按最大课程数找每一节课的内容
	    		if(classTemp[j].getClassNum()==CN)
	    		{
	    			appItem.put(classListContent[0], String.valueOf(classTemp[j].getMinutesForClass()));    		
	    			appItem.put(classListContent[1], classNum[classTemp[j].getClassNum()-1]);    	    		
	    			appItem.put(classListContent[2], classTemp[j].getClassName());  
	    			appItem.put(classListContent[3], classTemp[j].getTeacherName());  
	    			appItem.put(classListContent[4], "上课教室"); 
	    			break;
	    		}
	    	}
    		//如果读到的课程表中没有对应该节课的内容，则按默认填写
	    	if(j==classnumbers)
	    	{
	    		appItem.put(classListContent[0], "时间");    		
	   			appItem.put(classListContent[1], "第"+(CN+1)+"节");    	    		
	   			appItem.put(classListContent[2], "课程名程");  
	   			appItem.put(classListContent[3], "老师");  
	   			appItem.put(classListContent[4], "教室");  
    		}
	    	appItems.add(appItem);
		}
    	Log.d("Database debug","loadClassList complete!");
	
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, 
				appItems,
				R.layout.listviewitem, 
				classListContent,
				classListContentID);
		mListView.setAdapter(simpleAdapter);
	}
}
