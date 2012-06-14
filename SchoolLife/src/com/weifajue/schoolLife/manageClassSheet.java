/**
 * 
 */
package com.weifajue.schoolLife;

import com.weifajue.schoolLife.data.ClassDB;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author SmartGang
 *
 */
public class manageClassSheet extends Activity {
	public ListView classSheetList;
	public View topHeader;
	private LinearLayout listFooter;
	private ImageView footerImage;
	//保存课表列表
	private String[] sheetList=null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_classsheet);
		//设置顶端标题栏，把左右两个按键去显示（保留位置），更改标题
		topHeader=(View)findViewById(R.id.managingClassSheetHeader);
		Button btn=(Button)topHeader.findViewById(R.id.top_btn_left);
		btn.setVisibility(View.INVISIBLE);
		btn=(Button)topHeader.findViewById(R.id.top_btn_right);
		btn.setVisibility(View.INVISIBLE);
		TextView top_textView=(TextView)topHeader.findViewById(R.id.tv_toptitle);
		top_textView.setText("管理课表");
		
		ClassDB cDB=new ClassDB(manageClassSheet.this);
		sheetList=cDB.readClassSheetList();
		
		classSheetList=(ListView)findViewById(R.id.managingClassSheetList);
		listFooter=(LinearLayout)LayoutInflater.from(manageClassSheet.this).inflate(R.layout.listview_footer_add, null);
		classSheetList.addFooterView(listFooter);
		footerImage=(ImageView)listFooter.findViewById(R.id.listview_footer_add);
		ArrayAdapter<String> classSheetListAdapter = new ArrayAdapter<String>(this,R.layout.manage_listview_item, sheetList);
		classSheetList.setAdapter(classSheetListAdapter);
		classSheetList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
        	@Override
        	public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3)
        	{
				Intent intent=new Intent(manageClassSheet.this,editClassSheet.class);
				intent.putExtra("edit_type", Constant.CLASS_SHEET_EDIT_TYPE_EDIT);
				intent.putExtra("sheet_name", sheetList[arg2]);
				startActivity(intent);        		
        	}
        });
		
		footerImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(manageClassSheet.this, "添加课表", Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(manageClassSheet.this,editClassSheet.class);
				intent.putExtra("edit_type", Constant.CLASS_SHEET_EDIT_TYPE_NEW);
				startActivity(intent);
			}
		});
	}	
}
