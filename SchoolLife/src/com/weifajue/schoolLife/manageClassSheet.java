/**
 * 
 */
package com.weifajue.schoolLife;

import com.weifajue.schoolLife.data.ClassDB;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.RelativeLayout.LayoutParams;
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
	//两个常量用来表示当前是否处理编辑状态，在adapter的getView中根据状态进行不同操作
	final static private boolean EDIT_FLAG_NORMAL=true;
	final static private boolean EDIT_FLAG_EDITING=false;
	private boolean EDIT_FLAG=EDIT_FLAG_NORMAL;
	
	private SheetListAdapter sheetListAdapter;
	//保存课表列表
	private String[] sheetList=null;
	ClassDB cDB;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_classsheet);
		//设置顶端标题栏，把左右两个按键去显示（保留位置），更改标题
		cDB=new ClassDB(manageClassSheet.this);
		topHeader=(View)findViewById(R.id.managingClassSheetHeader);
		Button btn1=(Button)topHeader.findViewById(R.id.top_btn_left);
		btn1.setVisibility(View.INVISIBLE);
		final Button btn=(Button)topHeader.findViewById(R.id.top_btn_right);
//		btn.setVisibilit/y(View.INVISIBLE);
		btn.setText("编辑");
//		btn.setPadding(0, 0, 10, 0);
		LayoutParams params = (LayoutParams) btn.getLayoutParams();
		params.setMargins(0, 0, 10, 0); //substitute parameters for left, top, right, bottom
		btn.setLayoutParams(params);
		btn.setBackgroundColor(R.drawable.button_bkg_normal);
		btn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(manageClassSheet.this, "编辑课表", Toast.LENGTH_SHORT).show();		    						
				if(EDIT_FLAG==EDIT_FLAG_NORMAL)//在正常状态按下，表示要进入编辑状态
				{
					EDIT_FLAG=EDIT_FLAG_EDITING;
					btn.setText("完成");
					sheetListAdapter.notifyDataSetChanged();
				}
				else
				{
					EDIT_FLAG=EDIT_FLAG_NORMAL;
					btn.setText("编辑");
					sheetListAdapter.notifyDataSetChanged();
				}
			}
			
		});
		TextView top_textView=(TextView)topHeader.findViewById(R.id.tv_toptitle);
		top_textView.setText("管理课表");
		
		sheetList=cDB.readClassSheetList();
		
		classSheetList=(ListView)findViewById(R.id.managingClassSheetList);
		listFooter=(LinearLayout)LayoutInflater.from(manageClassSheet.this).inflate(R.layout.listview_footer_add, null);
		classSheetList.addFooterView(listFooter);
		footerImage=(ImageView)listFooter.findViewById(R.id.listview_footer_add);
//		ArrayAdapter<String> classSheetListAdapter = new ArrayAdapter<String>(this,R.layout.manage_listview_item, sheetList);
		sheetListAdapter=new SheetListAdapter(manageClassSheet.this);
		classSheetList.setAdapter(sheetListAdapter);
		classSheetList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
        	@Override
        	public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3)
        	{
				Intent intent=new Intent(manageClassSheet.this,editClassSheet.class);
				intent.putExtra("edit_type", Constant.CLASS_SHEET_EDIT_TYPE_EDIT);
				intent.putExtra("sheet_name", sheetList[arg2]);
				startActivity(intent); 
				manageClassSheet.this.finish();//将当前实例结束，以便下一个返回时，会重新调用OnCreate刷新
        	}
        });
		
		footerImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(manageClassSheet.this, "添加课表", Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(manageClassSheet.this,editClassSheet.class);
				intent.putExtra("edit_type", Constant.CLASS_SHEET_EDIT_TYPE_NEW);
				startActivity(intent);
				manageClassSheet.this.finish();
			}
		});
	}
	
	 private class SheetListAdapter extends BaseAdapter
	 {
		private LayoutInflater layoutInflater;
		Context context;
			
		public SheetListAdapter(Context context)
		{
			this.context = context;
			layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return sheetList.length;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return sheetList[arg0];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			RelativeLayout rl 	= (RelativeLayout)layoutInflater.inflate(R.layout.manage_classsheet_list_item, null);
			TextView tvSheetName		= (TextView) rl.findViewById(R.id.tvSheetName);
			Button bDelete			= (Button)rl.findViewById(R.id.bDeleteSheet);

			tvSheetName.setText(sheetList[position]);
			if(EDIT_FLAG==EDIT_FLAG_NORMAL)//正常状态下，删除键不显示
			{
				bDelete.setVisibility(View.INVISIBLE);
			}
			else
			{
				bDelete.setVisibility(View.VISIBLE);
				final int p=position;
				bDelete.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub						
						cDB.deleteClassSheet(sheetList[p]);
						sheetList=cDB.readClassSheetList();
						notifyDataSetChanged();
					}
					
				});
			}			
			return rl;
		}
		 
	 }
}
