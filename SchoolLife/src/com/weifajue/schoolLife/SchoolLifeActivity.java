package com.weifajue.schoolLife;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import com.weifajue.schoolLife.*;

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