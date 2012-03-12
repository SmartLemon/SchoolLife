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


/******SchoolLifeӦ��������
 * �ṹ������3��ҳ��:personalPage,sharingPage,managementPage
 * ��Ӧ����������personalPage(),sharingPage(),managementPage()
 * ����onCreat()��ֱ����ת��������personalPage()
 * ÿ���������Ӧһ��layout���ֱ�Ϊmain.xml,menu2.xml,menu3.xml
 * ÿ�����涼����"����","������������������ť��������ת��������ҳ�� 
 */

public class SchoolLifeActivity extends Activity{
	
	ListView mListView;
	
	TabHost mainTabHost;
	
	private ClassDB classDBforList=new ClassDB(SchoolLifeActivity.this);
	
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
    	Button buttonPersonal1 =(Button)findViewById(R.id.buttonPersonal1);
        Button buttonSharing1 =(Button)findViewById(R.id.buttonSharing1);
        Button buttonManagement1 =(Button)findViewById(R.id.buttonManagement1);
        
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
        //TabHost�ؼ���ʼ��
        mainTabHost=(TabHost)findViewById(R.id.maintabhost);
        mainTabHost.setup();
        
        mainTabHost.addTab(mainTabHost.newTabSpec("��")   
                .setIndicator("��")   
                .setContent(R.id.tab1));   
           
        mainTabHost.addTab(mainTabHost.newTabSpec("һ")   
                .setIndicator("һ")   
                .setContent(R.id.tab2));   
           
        mainTabHost.addTab(mainTabHost.newTabSpec("��")   
                .setIndicator("��")   
                .setContent(R.id.tab3));
        
        mainTabHost.addTab(mainTabHost.newTabSpec("��")   
                .setIndicator("��")   
                .setContent(R.id.tab4));   
           
        mainTabHost.addTab(mainTabHost.newTabSpec("��")   
                .setIndicator("��")   
                .setContent(R.id.tab5));   
           
        mainTabHost.addTab(mainTabHost.newTabSpec("��")   
                .setIndicator("��")   
                .setContent(R.id.tab6));
        
        mainTabHost.addTab(mainTabHost.newTabSpec("��")   
                .setIndicator("��")   
                .setContent(R.id.tab7));
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
        //Ĭ�Ͽ�ʼ��ʾ��һ�Ŀγ�
        mainTabHost.setCurrentTab(1);
        loadClassList(1);
        //����ΪlistView�е�textView������Ӧ������Ŀǰδ�ɹ�
        mListView.setOnItemClickListener(new OnItemClickListener()
        {
        	@Override
        	public void onItemClick(AdapterView<?> arg0, View arg1,final int arg2, long arg3)
        	{  
        	//View  The view within the AdapterView that was clicked (this will be a view provided by the adapter) 
        	//onItemClick�����Ĳ�����,�ڶ�������������item��parent��ֱ�������ҵ���view 
//        	    TextView textView= (TextView) arg1.findViewById(R.id.textViewClassTime); 
        		TextView textView= (TextView)findViewById(R.id.textViewClassTime);
        		//��view�ĵ���¼�
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
        
        //�ڸ���ҳ���У�ȥʹ�ܸ��˰�ť
        buttonPersonal1.setEnabled(false);
        
        //��ת������ҳ��
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
        
      //��ת������ҳ��
        buttonManagement1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Log.e("DebugLog","on click buttonManagement1");
            	managementPage();
            }
        });

    }
/*****************************************����ҳ�洦���� end********************************************/
    
/* ****************************************����ҳ�洦����*********************************************/
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
/*****************************************����ҳ�洦����end *********************************************/    

/*****************************************����ҳ�洦����***********************************************/    
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
/*****************************************����ҳ�洦���� end ***********************************************/ 
    
    //���ؿγ̱����ݵ�ListView��
    //�������WD��ʾҪ�������ڼ������ݣ�1~7�ֱ��ʾ����һ��������
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
    		//��ӿγ�����
    		if(classTemp!=null)
    		{
    			String time;
    			if((classTemp.getClassTime()%100)>9)time=String.valueOf(classTemp.getClassTime()%100);
    			else time='0'+String.valueOf(classTemp.getClassTime()%100);
    			appItem.put(classListContent[0], String.valueOf(classTemp.getClassTime()/100)+':'+time);    		
    			appItem.put(classListContent[1], classNum[classTemp.getClassNum()-1]);    	    		
    			appItem.put(classListContent[2], classTemp.getClassName());  
    			appItem.put(classListContent[3], classTemp.getTeacherName());  
    			appItem.put(classListContent[4], "�Ͽν���");  
    		}
    		else
    		{
    			appItem.put(classListContent[0], "ʱ��");    		
    			appItem.put(classListContent[1], classNum[CN]);    	    		
    			appItem.put(classListContent[2], "�γ�����");  
    			appItem.put(classListContent[3], "��ʦ");  
    			appItem.put(classListContent[4], "����");  
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