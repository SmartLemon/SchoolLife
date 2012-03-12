package com.weifajue.schoolLife;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
//import android.database.Cursor;
import android.os.Bundle;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.view.*;
import android.util.Log;
import android.widget.*;


/******SchoolLife应用主程序
 * 结构：包含3个页面:personalPage,sharingPage,managementPage
 * 对应三个方法：personalPage(),sharingPage(),managementPage()
 * 进入onCreat()后直接跳转到主界面personalPage()
 * 每个界面各对应一个layout，分别为main.xml,menu2.xml,menu3.xml
 * 每个界面都包含"个人","分享“，”管理“三个按钮，用来跳转到其他的页面 
 */

public class SchoolLifeActivity extends Activity{
	
	ListView mListView;
	
	TabHost mainTabHost;
	
	private ClassDB classDBforList=new ClassDB(SchoolLifeActivity.this);
	
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
    	Button buttonPersonal1 =(Button)findViewById(R.id.buttonPersonal1);
        Button buttonSharing1 =(Button)findViewById(R.id.buttonSharing1);
        Button buttonManagement1 =(Button)findViewById(R.id.buttonManagement1);
        
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
        //TabHost控件初始化
        mainTabHost=(TabHost)findViewById(R.id.maintabhost);
        mainTabHost.setup();
        
        mainTabHost.addTab(mainTabHost.newTabSpec("日")   
                .setIndicator("日")   
                .setContent(R.id.tab1));   
           
        mainTabHost.addTab(mainTabHost.newTabSpec("一")   
                .setIndicator("一")   
                .setContent(R.id.tab2));   
           
        mainTabHost.addTab(mainTabHost.newTabSpec("二")   
                .setIndicator("二")   
                .setContent(R.id.tab3));
        
        mainTabHost.addTab(mainTabHost.newTabSpec("三")   
                .setIndicator("三")   
                .setContent(R.id.tab4));   
           
        mainTabHost.addTab(mainTabHost.newTabSpec("四")   
                .setIndicator("四")   
                .setContent(R.id.tab5));   
           
        mainTabHost.addTab(mainTabHost.newTabSpec("五")   
                .setIndicator("五")   
                .setContent(R.id.tab6));
        
        mainTabHost.addTab(mainTabHost.newTabSpec("六")   
                .setIndicator("六")   
                .setContent(R.id.tab7));
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
        //默认开始显示周一的课程
        mainTabHost.setCurrentTab(1);
        loadClassList(1);
        //尝试为listView中的textView设置响应函数，目前未成功
        mListView.setOnItemClickListener(new OnItemClickListener()
        {
        	@Override
        	public void onItemClick(AdapterView<?> arg0, View arg1,final int arg2, long arg3)
        	{  
        	//View  The view within the AdapterView that was clicked (this will be a view provided by the adapter) 
        	//onItemClick方法的参数中,第二个参数就是子item的parent，直接用它找到子view 
//        	    TextView textView= (TextView) arg1.findViewById(R.id.textViewClassTime); 
        		TextView textView= (TextView)findViewById(R.id.textViewClassTime);
        		//子view的点击事件
        	    textView.setOnClickListener(new View.OnClickListener() {
        	       @Override
        	       public void onClick(View arg0) {
        	        Toast.makeText(getApplicationContext(),
        	          "aaa" , Toast.LENGTH_SHORT)
        	          .show();
        	       }
        	      });
        	   }
        });

//        FrameLayout tabFrame=(FrameLayout)findViewById(R.id.tab);
        
//        mLinearLayout.addView(tabFrame);
        
        Log.e("DebugLog","show personalPage View");
        
        //在个人页面中，去使能个人按钮
        buttonPersonal1.setEnabled(false);
        
        //跳转到分享页面
        buttonSharing1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Log.e("DebugLog","on click buttonSharing1");
//            	sharingPage();
        		Intent intent = new Intent();
        		intent.setClass(SchoolLifeActivity.this,editClass.class);
        		startActivity(intent);
        		SchoolLifeActivity.this.finish();
            }
        });
        
      //跳转到管理页面
        buttonManagement1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Log.e("DebugLog","on click buttonManagement1");
            	managementPage();
            }
        });

    }
/*****************************************个人页面处理方法 end********************************************/
    
/* ****************************************共享页面处理方法*********************************************/
    public void sharingPage()
    {
        setContentView(R.layout.menu2); 
    	final Button buttonPersonal2=(Button)findViewById(R.id.buttonPersonal2);
        final Button buttonSharing2=(Button)findViewById(R.id.buttonSharing2);
        final Button buttonManagement2=(Button)findViewById(R.id.buttonManagement2);

        Log.e("DebugLog","show sharingPage View");
        
        buttonPersonal2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Log.e("DebugLog","on click OnClickListener");
            	personalPage();
            }
        });
        
        buttonSharing2.setEnabled(false);
        
        buttonManagement2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Log.e("DebugLog","on click buttonManagement2");
            	managementPage();
            }
        });
    }
/*****************************************共享页面处理方法end *********************************************/    

/*****************************************管理页面处理方法***********************************************/    
    public void managementPage()
    {
        setContentView(R.layout.menu3); 
        
    	final Button buttonPersonal3=(Button)findViewById(R.id.buttonPersonal3);
        final Button buttonSharing3=(Button)findViewById(R.id.buttonSharing3);
        final Button buttonManagement3=(Button)findViewById(R.id.buttonManagement3);

        Log.e("DebugLog","show managementPage View");
                
        buttonPersonal3.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Log.e("DebugLog","on click buttonPersonal3");
            	personalPage();
            }
        });
        
        buttonSharing3.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Log.e("DebugLog","on click buttonSharing3");
            	sharingPage();
            }
        });
        
        buttonManagement3.setEnabled(false);
    }
/*****************************************管理页面处理方法 end ***********************************************/ 
    
    //加载课程表内容到ListView中
    //传入参数WD表示要加载星期几的数据，1~7分别表示星期一至星期日
    public void loadClassList(int WD)
    {

    	Log.d("DatabaseDebug", "in loadClassList");
    	mListView=new ListView(this);

    	List<Map<String, Object>> appItems = new ArrayList<Map<String, Object>>();
    	
    	classDBforList.getClassCursor();
    	
    	for(int CN=0;CN<5;CN++)
    	{
    		Map<String, Object> appItem = new HashMap<String, Object>();
  		
    		Class classTemp;
    		classTemp=classDBforList.readClass(CN+1, WD);
    		//添加课程内容
    		if(classTemp!=null)
    		{
    			String time;
    			if((classTemp.getClassTime()%100)>9)time=String.valueOf(classTemp.getClassTime()%100);
    			else time='0'+String.valueOf(classTemp.getClassTime()%100);
    			appItem.put(classListContent[0], String.valueOf(classTemp.getClassTime()/100)+':'+time);    		
    			appItem.put(classListContent[1], classNum[classTemp.getClassNum()-1]);    	    		
    			appItem.put(classListContent[2], classTemp.getClassName());  
    			appItem.put(classListContent[3], classTemp.getTeacherName());  
    			appItem.put(classListContent[4], "上课教室");  
    		}
    		else
    		{
    			appItem.put(classListContent[0], "时间");    		
    			appItem.put(classListContent[1], classNum[CN]);    	    		
    			appItem.put(classListContent[2], "课程名程");  
    			appItem.put(classListContent[3], "老师");  
    			appItem.put(classListContent[4], "教室");  
    		}
			appItems.add(appItem);
    	}
    	Log.d("Database debug","loadClassList complete!");
/*		for (int i = 0; i < classNum.length; i++)
		{
			Map<String, Object> appItem = new HashMap<String, Object>();
			appItem.put("tvClassNum", classNum[i]);
			appItem.put("tvClassName", className[i]);
//			appItem.put("ivMore", R.drawable.ic_input_add);
			appItems.add(appItem);
		}
*/		
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, 
				appItems,
				R.layout.listviewitem, 
				classListContent,
				classListContentID);
//		setListAdapter(simpleAdapter);
		mListView.setAdapter(simpleAdapter);
		
        LinearLayout ll=new LinearLayout(this);
        switch(WD)
        {
        case 1:ll=(LinearLayout)findViewById(R.id.tab2);break;
        case 2:ll=(LinearLayout)findViewById(R.id.tab3);break;
        case 3:ll=(LinearLayout)findViewById(R.id.tab4);break;
        case 4:ll=(LinearLayout)findViewById(R.id.tab5);break;
        case 5:ll=(LinearLayout)findViewById(R.id.tab6);break;
        case 6:ll=(LinearLayout)findViewById(R.id.tab7);break;
        case 7:ll=(LinearLayout)findViewById(R.id.tab1);break;
        }
        ll.removeAllViews();
        ll.addView(mListView);    
	}
}