package com.weifajue.schoolLife;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import com.weifajue.schoolLife.*;

/******SchoolLife应用主程序
 * 结构：包含3个页面:personalPage,sharingPage,managementPage
 * 对应三个方法：personalPage(),sharingPage(),managementPage()
 * 进入onCreat()后直接跳转到主界面personalPage()
 * 每个界面各对应一个layout，分别为main.xml,menu2.xml,menu3.xml
 * 每个界面都包含"个人","分享“，”管理“三个按钮，用来跳转到其他的页面 
 */

public class SchoolLifeActivity extends TabActivity{

	private TabHost tabHost;

	private static final String VIEWCLASS = "课程";
	private static final String SHARE = "分享";
	private static final String MANAGE = "管理";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabframe);
		
		tabHost = this.getTabHost();
		
		TabHost.TabSpec spec1 = tabHost.newTabSpec(VIEWCLASS)
		.setIndicator(VIEWCLASS)
		.setContent(new Intent(this, viewClass.class));
		tabHost.addTab(spec1);
		
		TabHost.TabSpec spec2 = tabHost.newTabSpec(SHARE)
		.setIndicator(SHARE)
		.setContent(new Intent(this, sharingActivity.class));
		tabHost.addTab(spec2);

		TabHost.TabSpec spec3 = tabHost.newTabSpec(MANAGE)
		.setIndicator(MANAGE)
		.setContent(new Intent(this, editClass.class));
		tabHost.addTab(spec3);
		
	}
	
}