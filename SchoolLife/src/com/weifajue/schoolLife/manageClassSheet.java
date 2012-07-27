/**
 * 
 */
package com.weifajue.schoolLife;

import com.weifajue.schoolLife.data.ClassDB;
import com.weifajue.schoolLife.data.LocalFile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
	//��������������ʾ��ǰ�Ƿ���༭״̬����adapter��getView�и���״̬���в�ͬ����
	final static private boolean EDIT_FLAG_NORMAL=true;
	final static private boolean EDIT_FLAG_EDITING=false;
	private boolean EDIT_FLAG=EDIT_FLAG_NORMAL;
	private String currentClassSheetName;
	private SheetListAdapter sheetListAdapter;
	//����α��б�
	private String[] sheetList=null;
	ClassDB cDB;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_classsheet);
		//���ö��˱���������������������ȥ��ʾ������λ�ã������ı���
		cDB=new ClassDB(manageClassSheet.this);
		topHeader=(View)findViewById(R.id.managingClassSheetHeader);
		Button btn1=(Button)topHeader.findViewById(R.id.top_btn_left);
		btn1.setVisibility(View.INVISIBLE);
		final Button btn=(Button)topHeader.findViewById(R.id.top_btn_right);
		btn.setText("�༭");
		LayoutParams params = (LayoutParams) btn.getLayoutParams();
		params.setMargins(0, 0, 10, 0); //substitute parameters for left, top, right, bottom
		btn.setLayoutParams(params);
		btn.setBackgroundColor(R.drawable.button_bkg_normal);
		btn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(manageClassSheet.this, "�༭�α�", Toast.LENGTH_SHORT).show();		    						
				if(EDIT_FLAG==EDIT_FLAG_NORMAL)//������״̬���£���ʾҪ����༭״̬
				{
					EDIT_FLAG=EDIT_FLAG_EDITING;
					btn.setText("���");
					sheetListAdapter.notifyDataSetChanged();
				}
				else
				{
					EDIT_FLAG=EDIT_FLAG_NORMAL;
					btn.setText("�༭");
					sheetListAdapter.notifyDataSetChanged();
				}
			}
			
		});
		TextView top_textView=(TextView)topHeader.findViewById(R.id.tv_toptitle);
		top_textView.setText("����α�");

        LocalFile lf=new LocalFile();
        currentClassSheetName=lf.getCurrentClassSheetName(this);
        
		sheetList=cDB.readClassSheetList();
		
		classSheetList=(ListView)findViewById(R.id.managingClassSheetList);
		listFooter=(LinearLayout)LayoutInflater.from(manageClassSheet.this).inflate(R.layout.listview_footer_add, null);
		classSheetList.addFooterView(listFooter);
		footerImage=(ImageView)listFooter.findViewById(R.id.listview_footer_add);
//		ArrayAdapter<String> classSheetListAdapter = new ArrayAdapter<String>(this,R.layout.manage_listview_item, sheetList);
		sheetListAdapter=new SheetListAdapter(manageClassSheet.this);
		classSheetList.setAdapter(sheetListAdapter);
		classSheetList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		classSheetList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
        	@Override
        	public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3)
        	{
				Intent intent=new Intent(manageClassSheet.this,editClassSheet.class);
				intent.putExtra("edit_type", Constant.CLASS_SHEET_EDIT_TYPE_EDIT);
				intent.putExtra("sheet_name", sheetList[arg2]);
				startActivity(intent); 
				manageClassSheet.this.finish();//����ǰʵ���������Ա���һ������ʱ�������µ���OnCreateˢ��
        	}
        });
		
		footerImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(manageClassSheet.this, "��ӿα�", Toast.LENGTH_SHORT).show();
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
			CheckBox cbIsCurrentSheet =(CheckBox)rl.findViewById(R.id.cbIsCurrentClassSheet);
			
			tvSheetName.setText(sheetList[position]);
			if(EDIT_FLAG==EDIT_FLAG_NORMAL)//����״̬�£�ɾ��������ʾ
			{
				bDelete.setVisibility(View.INVISIBLE);
				if(sheetList[position].equals(currentClassSheetName))
				{
					cbIsCurrentSheet.setVisibility(View.VISIBLE);
					cbIsCurrentSheet.setChecked(true);
					cbIsCurrentSheet.setFocusable(false);
					cbIsCurrentSheet.setClickable(false);
				}
				else
				{
					cbIsCurrentSheet.setVisibility(View.INVISIBLE);
				}
			}
			else
			{
				bDelete.setVisibility(View.VISIBLE);
				cbIsCurrentSheet.setVisibility(View.VISIBLE);
				if(sheetList[position].equals(currentClassSheetName))cbIsCurrentSheet.setChecked(true);
				else cbIsCurrentSheet.setChecked(false);					
				final int p=position;
				bDelete.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(sheetList[p].equals(currentClassSheetName)==false)
						{	//���ڲ��ǵ�ǰ�α�ѯ���û��Ƿ����ɾ������
							new AlertDialog.Builder(manageClassSheet.this)
							.setTitle("ȷ��ɾ��"+sheetList[p]+"?")
							.setPositiveButton("ȷ��", new DialogInterface.OnClickListener(){
								public void onClick(DialogInterface dialog, int whichButton)
								{
									cDB.deleteClassSheet(sheetList[p]);
									sheetList=cDB.readClassSheetList();
									notifyDataSetChanged();
								}
							})
							.setNegativeButton("ȡ��",new DialogInterface.OnClickListener(){
								public void onClick(DialogInterface dialog, int whichButton)
								{
								}
							})
							.show();
						}
						else
						{
							Toast.makeText(manageClassSheet.this, "�ÿα�Ϊ��ǰʹ�ÿα��޷�ɾ��", Toast.LENGTH_SHORT).show();		    									
						}
					}
					
				});
				cbIsCurrentSheet.setOnCheckedChangeListener(new OnCheckedChangeListener()
				{

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if(isChecked)
						{
							currentClassSheetName=sheetList[p];
					        LocalFile lf=new LocalFile();
					        lf.setCurrentClassSheetName(currentClassSheetName,manageClassSheet.this);
							notifyDataSetChanged();
						}
					}
					
				});
			}			
			return rl;
		}
		 
	 }
}
