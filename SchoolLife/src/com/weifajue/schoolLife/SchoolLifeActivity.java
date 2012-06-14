package com.weifajue.schoolLife;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;

/******SchoolLifeӦ��������
 * �ṹ������3��ҳ��:personalPage,sharingPage,managementPage
 * ��Ӧ����������personalPage(),sharingPage(),managementPage()
 * ����onCreat()��ֱ����ת��������personalPage()
 * ÿ���������Ӧһ��layout���ֱ�Ϊmain.xml,menu2.xml,menu3.xml
 * ÿ�����涼����"����","������������������ť��������ת��������ҳ�� 
 */

public class SchoolLifeActivity extends TabActivity{

	private TabHost tabHost;

	private static final String VIEWCLASS = "�γ�";
	private static final String SHARE = "����";
	private static final String MANAGE = "����";
	
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