package com.weifajue.schoolLife;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.view.*;
import android.util.Log;

/******SchoolLife应用主程序
 * 结构：包含3个页面:personalPage,sharingPage,managementPage
 * 对应三个方法：personalPage(),sharingPage(),managementPage()
 * 进入onCreat()后直接跳转到主界面personalPage()
 * 每个界面各对应一个layout，分别为main.xml,menu2.xml,menu3.xml
 * 每个界面都包含"个人","分享“，”管理“三个按钮，用来跳转到其他的页面 
 */

public class SchoolLifeActivity extends Activity {
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
    	
        setContentView(R.layout.main);
        
    	Button buttonPersonal1 =(Button)findViewById(R.id.buttonPersonal1);
        Button buttonSharing1 =(Button)findViewById(R.id.buttonSharing1);
        Button buttonManagement1 =(Button)findViewById(R.id.buttonManagement1);
   

        Log.e("DebugLog","show personalPage View");
        
        //在个人页面中，去使能个人按钮
        buttonPersonal1.setEnabled(false);
        
        //跳转到分享页面
        buttonSharing1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Log.e("DebugLog","on click buttonSharing1");
            	sharingPage();
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
}