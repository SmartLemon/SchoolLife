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
import android.content.SharedPreferences;
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
	
	ListView mListView;
	public TabHost mainTabHost;
	private String currentClassSheetName;
	
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
        LocalFile localFile=new LocalFile();
        //获取当前的默认ClassSheetName
        currentClassSheetName=localFile.getCurrentClassSheetName(this); 
        //从下代码做异常保护，防止从localFile中读到的sheetName在DB中没有保存
        ClassDB pDB=new ClassDB(this);
        String[] sheetList=pDB.readClassSheetList();
        int sheetNum=sheetList.length;
        int i=0;
        for(i=0;i<sheetNum;i++)
        {
        	if(currentClassSheetName==sheetList[i])break;
        }
        if(i==sheetNum)
        {	//如果没有保存，则数据存中有值，则将第一个设置为默认
        	if(sheetNum>0)currentClassSheetName=sheetList[0];
        	else currentClassSheetName="default";
        }
        personalPage();
    }
    public void personalPage()
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

    //加载课程表内容到ListView中
    //传入参数WD表示要加载星期几的数据，1~7分别表示星期一至星期日
    public void loadClassList(int WD)
    {
    	ClassDB classDBforList=new ClassDB(viewClass.this);
    	Log.d("DatabaseDebug", "in loadClassList");
    	List<Map<String, Object>> appItems = new ArrayList<Map<String, Object>>();
		ClassSheet cCS=classDBforList.readClassSheet(currentClassSheetName);
		timeProcess tp=new timeProcess();
		int WN=tp.dateToDefaultWeekNum(new Date());		
		Class[] classTemp=classDBforList.readClassDayList(currentClassSheetName,WN,WD); 
		int classnumbers=0;
		if(classTemp!=null)classnumbers=classTemp.length;//做null指针保护，读不到课程时，防止classTemp.length引发异常
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
	    			appItem.put(classListContent[1], "第"+(CN+1)+"节");    	    		
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
