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
	{ "��һ��", "�ڶ���", "������", "���Ľ�", "�����" };
	
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
	{ "����", "��ѧ", "Ӣ��", "����", "����" };	
*/
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.e("DebugLog","run in main");
        personalPage();
    }
/* *****************************************����ҳ�洦����*********************************************/
/*
*  ע�⣺setContentView(R.layout.main)�����������ǰ
*/
    public void personalPage()
    {
//    	LinearLayout mLinearLayout=new LinearLayout(this);
        setContentView(R.layout.main);
        
//        setContentView(R.layout.listviewitem);
//        mListView=new ListView(this);
       
//        setContentView(R.layout.main);
//        mLinearLayout=(LinearLayout)findViewById(R.id.mainLinearLayout);
/*      2012-3-10�ոĶ�������ֱ�Ӽ���ListView��ʾ�γ����ݣ��������Ϊʹ��tabHost������
 * 		�ں���loadTabView��������ʵ�֣���ListView�ӵ�TabView�� 
 * 		LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(
        		LinearLayout.LayoutParams.FILL_PARENT,
        		LinearLayout.LayoutParams.WRAP_CONTENT);
        loadClassList();
        mLinearLayout.addView(mListView,param);
*/
        //listview�ؼ���ʼ��,������TabHost֮ǰ����Ϊtabhost���õ�
//    	mListView=new ListView(this);
    	mListView=(ListView)findViewById(R.id.listViewClassList);
    	    	
        //TabHost�ؼ���ʼ��
        mainTabHost=(TabHost)findViewById(R.id.maintabhost);
        mainTabHost.setup();
		View view1 = View.inflate(viewClass.this, R.layout.class_tab_ind, null);
		((ImageView) view1.findViewById(R.id.tab_imageview_icon)).setImageResource(R.drawable.sun_tab_selector);
        mainTabHost.addTab(mainTabHost.newTabSpec("��")   
                .setIndicator(view1)
                .setContent(R.id.listViewClassList));   

		View view2 = View.inflate(viewClass.this, R.layout.class_tab_ind, null);
		((ImageView) view2.findViewById(R.id.tab_imageview_icon)).setImageResource(R.drawable.mon_tab_selector);
        mainTabHost.addTab(mainTabHost.newTabSpec("һ")   
                .setIndicator(view2)   
                .setContent(R.id.listViewClassList));
        
		View view3 = View.inflate(viewClass.this, R.layout.class_tab_ind, null);
		((ImageView) view3.findViewById(R.id.tab_imageview_icon)).setImageResource(R.drawable.tue_tab_selector);        
        mainTabHost.addTab(mainTabHost.newTabSpec("��")   
                .setIndicator(view3)   
                .setContent(R.id.listViewClassList));
        
		View view4 = View.inflate(viewClass.this, R.layout.class_tab_ind, null);
		((ImageView) view4.findViewById(R.id.tab_imageview_icon)).setImageResource(R.drawable.wed_tab_selector);        
        mainTabHost.addTab(mainTabHost.newTabSpec("��")   
                .setIndicator(view4)   
                .setContent(R.id.listViewClassList));   
           
		View view5 = View.inflate(viewClass.this, R.layout.class_tab_ind, null);
		((ImageView) view5.findViewById(R.id.tab_imageview_icon)).setImageResource(R.drawable.thu_tab_selector);        
        mainTabHost.addTab(mainTabHost.newTabSpec("��")   
                .setIndicator(view5)   
                .setContent(R.id.listViewClassList));   
           
		View view6 = View.inflate(viewClass.this, R.layout.class_tab_ind, null);
		((ImageView) view6.findViewById(R.id.tab_imageview_icon)).setImageResource(R.drawable.fri_tab_selector);        
        mainTabHost.addTab(mainTabHost.newTabSpec("��")   
                .setIndicator(view6)   
                .setContent(R.id.listViewClassList));
        
		View view7 = View.inflate(viewClass.this, R.layout.class_tab_ind, null);
		((ImageView) view7.findViewById(R.id.tab_imageview_icon)).setImageResource(R.drawable.sat_tab_selector);        
        mainTabHost.addTab(mainTabHost.newTabSpec("��")   
                .setIndicator(view7)   
                .setContent(R.id.listViewClassList));
       //ΪTabHost�ؼ�������Ӧ���ֱ���ص���Ŀγ̱�listView��
        mainTabHost.setOnTabChangedListener(new OnTabChangeListener()
        {
        	@Override
        	public void onTabChanged(String tabId)
        	{
        		if(tabId=="��")
        		{
        			loadClassList(7);
        		}
        		else if(tabId=="һ")
        		{
        			loadClassList(1);
        		}
        		else if(tabId=="��")
        		{
        			loadClassList(2);
        		}
        		else if(tabId=="��")
        		{
        			loadClassList(3);
        		}
        		else if(tabId=="��")
        		{
        			loadClassList(4);
        		}
        		else if(tabId=="��")
        		{
        			loadClassList(5);
        		
        		}else if(tabId=="��")
        		{
        			loadClassList(6);
        		}
        	}
        });
        //Ĭ�Ͽ�ʼ��ʾ��һ�Ŀγ�,���Զ�����setOnTabChangedListener
        mainTabHost.setCurrentTab(1);

        //����ΪlistView������Ӧ������Ŀǰδ�ɹ�

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
        	@Override
        	public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3)
        	{  
        	//View  The view within the AdapterView that was clicked (this will be a view provided by the adapter) 
        	//onItemClick�����Ĳ�����,�ڶ�������������item��parent��ֱ�������ҵ���view 
    	        Toast.makeText(getApplicationContext(),
          	          "aaa" , Toast.LENGTH_SHORT)
          	          .show();
/*
    	        TextView textView= (TextView)findViewById(R.id.textViewClassTime);
        		//��view�ĵ���¼�
        	    textView.setOnClickListener(new View.OnClickListener() {
        	       @Override
        	       public void onClick(View arg0) {

        	       }
        	      });*/
        	   }
        });
        
        Log.e("DebugLog","show personalPage View");
    }
/*****************************************����ҳ�洦���� end********************************************/
 
    //���ؿγ̱����ݵ�ListView��
    //�������WD��ʾҪ�������ڼ������ݣ�1~7�ֱ��ʾ����һ��������
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
	    		//��ӿγ�����
    			//ͨ������ѭ���������γ�����ÿһ�ڿε�����
	    		if(classTemp[j].getClassNum()==CN)
	    		{
	    			appItem.put(classListContent[0], String.valueOf(classTemp[j].getMinutesForClass()));    		
	    			appItem.put(classListContent[1], classNum[classTemp[j].getClassNum()-1]);    	    		
	    			appItem.put(classListContent[2], classTemp[j].getClassName());  
	    			appItem.put(classListContent[3], classTemp[j].getTeacherName());  
	    			appItem.put(classListContent[4], "�Ͽν���"); 
	    			break;
	    		}
	    	}
    		//��������Ŀγ̱���û�ж�Ӧ�ýڿε����ݣ���Ĭ����д
	    	if(j==classnumbers)
	    	{
	    		appItem.put(classListContent[0], "ʱ��");    		
	   			appItem.put(classListContent[1], "��"+(CN+1)+"��");    	    		
	   			appItem.put(classListContent[2], "�γ�����");  
	   			appItem.put(classListContent[3], "��ʦ");  
	   			appItem.put(classListContent[4], "����");  
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
