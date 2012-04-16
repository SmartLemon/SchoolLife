package com.weifajue.schoolLife;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
/**
 * 
 */
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author SmartGang
 *
 */
public class managingActivity extends Activity {

	public ListView managingList;
	public View topHeader;
	//主listview的标题内容
	private final static String[]managingListContent={"编辑课表内容","设置课程参数","软件更新","关于"};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu3);
		//设置顶端标题栏，把左右两个按键去显示（保留位置），更改标题
		topHeader=(View)findViewById(R.id.managingViewHeader);
		Button btn=(Button)topHeader.findViewById(R.id.top_btn_left);
		btn.setVisibility(View.INVISIBLE);
		btn=(Button)topHeader.findViewById(R.id.top_btn_right);
		btn.setVisibility(View.INVISIBLE);
		TextView top_textView=(TextView)topHeader.findViewById(R.id.tv_toptitle);
		top_textView.setText("管理设置");

		managingList=(ListView)findViewById(R.id.managingList);
		ArrayAdapter<String> managingListAdapter = new ArrayAdapter<String>(this,R.layout.manage_listview_item, managingListContent);
		managingList.setAdapter(managingListAdapter);
		managingList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
        	@Override
        	public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3)
        	{
        		Intent intent;
        		//根据点击的位置判断是点了哪一项
        		switch(arg2)
        		{
        		case 0:	intent=new Intent(managingActivity.this,editClass.class);
        				startActivity(intent);
        				break;
        		case 1: 
        				Toast.makeText(getApplicationContext(),
            	         "设置课程参数" , Toast.LENGTH_SHORT).show();
        				break;
        		case 2:
	    				Toast.makeText(getApplicationContext(),
	              	    "设置课程参数" , Toast.LENGTH_SHORT).show();
	          			break;
	          	default:break;
        		}
        	}
        });
	}	
}
