package com.weifajue.schoolLife;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;

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
		View view1 = View.inflate(SchoolLifeActivity.this, R.layout.class_tab_ind, null);
		((LinearLayout) view1.findViewById(R.id.ll_tab_ind)).setBackgroundDrawable(null);
		((ImageView) view1.findViewById(R.id.tab_imageview_icon)).setImageResource(R.drawable.classview_tab_selector);		
		TabHost.TabSpec spec1 = tabHost.newTabSpec(VIEWCLASS)
		.setIndicator(view1)
		.setContent(new Intent(this, viewClass.class));
		tabHost.addTab(spec1);
		
		View view2 = View.inflate(SchoolLifeActivity.this, R.layout.class_tab_ind, null);
		((LinearLayout) view2.findViewById(R.id.ll_tab_ind)).setBackgroundDrawable(null);
		((ImageView) view2.findViewById(R.id.tab_imageview_icon)).setImageResource(R.drawable.remind_tab_selector);		
		TabHost.TabSpec spec2 = tabHost.newTabSpec(SHARE)
		.setIndicator(view2)
		.setContent(new Intent(this, sharingActivity.class));
		tabHost.addTab(spec2);

		View view3 = View.inflate(SchoolLifeActivity.this, R.layout.class_tab_ind, null);
		((LinearLayout) view3.findViewById(R.id.ll_tab_ind)).setBackgroundDrawable(null);
		((ImageView) view3.findViewById(R.id.tab_imageview_icon)).setImageResource(R.drawable.manage_tab_selector);		
		TabHost.TabSpec spec3 = tabHost.newTabSpec(MANAGE)
		.setIndicator(view3)
		.setContent(new Intent(this, managingActivity.class));
		tabHost.addTab(spec3);
		
	}
	
}