package com.weifajue.schoolLife;

import java.util.Vector;

import com.weifajue.schoolLife.data.ClassDB;
import com.weifajue.schoolLife.data.LocalFile;
import com.weifajue.schoolLife.model.Class;
import com.weifajue.schoolLife.model.ClassSheet;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.widget.*;
import android.widget.RelativeLayout.LayoutParams;
import android.view.*;
import android.view.View.OnClickListener;

public class editClass extends Activity
{
	private static final int[] ImageViewIDsForList={
		R.id.ivMutiEditSunday,
		R.id.ivMutiEditMonday,
		R.id.ivMutiEditTuesday,
		R.id.ivMutiEditWednesday,
		R.id.ivMutiEditThursday,
		R.id.ivMutiEditFriday,
		R.id.ivMutiEditSaturday
	};

	public View topHeader;	//界面标题
	private EditText etClassName,etClassLocation,etTeacherName;
	private ListView lvMutiEditListView;
	private ClassSheet classSheet;
	private Class editingClass;
	private boolean isEditing=false;//用于指示当前是否进入编辑状态，默认false为非编辑状态
	/** 课程模板内容缓存器，二级对象数据
	 * 第一级长度按课表最大节数生成，实际为每一节的列表指针
	 * 第二级长度固定为7，即一周7天的内容
	 * DAYS_OF_WEEK,从0到6分别如下:SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, and SATURDAY
	 */
	private Class[][] detailPrimaryCacher;
	OperationVector operationRecorder;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editclasslist);
        Log.e("DebugLog","run in editClass Activity");
    	
        LocalFile lf=new LocalFile();
        String classSheetName=lf.getCurrentClassSheetName(this);
        //读取当前课表信息并初始化数组
        final ClassDB cDB=new ClassDB(editClass.this);
        classSheet=cDB.readClassSheet(classSheetName);
        detailPrimaryCacher=new Class[classSheet.getMaxClassNumPerDay()][];//初始化数组第一级
        for(int i=0;i<detailPrimaryCacher.length;i++)//初始化数组第二级,固定大小为7个
        {	
        	detailPrimaryCacher[i]=cDB.readClassTemplateByNumList(classSheetName, i);
        }
        //默认装载周一第一节课，在使用Spinner选择时改变
        editingClass=cDB.readClass(classSheetName, 1, 0);      
		//设置顶端标题栏，把左右两个按键去显示（保留位置），更改标题
		topHeader=(View)findViewById(R.id.editClassHeader);
		//显示向左的箭头，用于返回
		Button btnLeft=(Button)topHeader.findViewById(R.id.top_btn_left);
//		btn.setVisibility(View.INVISIBLE);
		final Button btnRight=(Button)topHeader.findViewById(R.id.top_btn_right);
//		btn.setVisibility(View.INVISIBLE);
		TextView top_textView=(TextView)topHeader.findViewById(R.id.tv_toptitle);
		top_textView.setText("编辑课程");
		
		btnRight.setText("编辑");
		LayoutParams params = (LayoutParams) btnRight.getLayoutParams();
		params.setMargins(0, 0, 10, 0); //substitute parameters for left, top, right, bottom
		btnRight.setLayoutParams(params);
		btnRight.setBackgroundColor(R.drawable.button_bkg_normal);
		
		//初始始化edit控件
	    etClassName=(EditText)findViewById(R.id.etShowDetailClassName);
	    etClassLocation=(EditText)findViewById(R.id.etShowDetailClassRoom);
	    etTeacherName=(EditText)findViewById(R.id.etShowDetailTeacherName);	    
	    initialEditTexts();
	    //初始化ListView
	    lvMutiEditListView=(ListView)findViewById(R.id.lvMutiEditClassDetial);
	    LinearLayout headerViewForList;
	    headerViewForList=(LinearLayout)LayoutInflater.from(editClass.this).inflate(R.layout.mutil_edit_class_detail_listview_header, null);		
	    lvMutiEditListView.addHeaderView(headerViewForList);
	    final DetailListItemAdapter detailListAdapter=new DetailListItemAdapter(this);
	    lvMutiEditListView.setAdapter(detailListAdapter);
	    
	    /**
	     * 返回键响应函数
	     * detailPrimaryCacher的内容重新写回数据库表中
	     * 完成后调用返回键功能
	     */
	    btnLeft.setOnClickListener(new OnClickListener()
	    {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for(int i=0;i<classSheet.getMaxClassNumPerDay();i++)
				{
					cDB.writeCassByNumListIntoTemplate(classSheet.getClassSheetName(),i,detailPrimaryCacher[i]);
				}				
				editClass.this.finish();
			}
	    	
	    });
	    
	    btnRight.setOnClickListener(new OnClickListener()
	    {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isEditing)//编辑状态下按下此键，进入非编辑状态
				{
					btnRight.setText("编辑");
					//读取编辑信息
					String className;
					if(etClassName.getText()!=null)className=etClassName.getText().toString();
					else
					{
						Toast.makeText(editClass.this, "请输入课程名称", Toast.LENGTH_SHORT).show();		    		
						return;
					}
					String teacherName;
					if(etTeacherName.getText()!=null)teacherName=etTeacherName.getText().toString();
					else
					{
						Toast.makeText(editClass.this, "请输入老师名字", Toast.LENGTH_SHORT).show();		    		
						return;
					}
					String classRoom;
					if(etClassLocation.getText()!=null)classRoom=etClassLocation.getText().toString();
					else
					{
						Toast.makeText(editClass.this, "请输入上课地点", Toast.LENGTH_SHORT).show();		    		
						return;
					}
					
					//读取控制信息并生成class写入detailPrimaryCacher中
					UserChoic uc;
					for(int i=0;i<operationRecorder.getSize();i++)
					{
						uc=(UserChoic) operationRecorder.getOperation(i);
						//判断是添加操作还是删除操作
						if(uc.getOperation()==UserChoic.OP_ADD)
						{
							Class classTemp=new Class();
							classTemp.setClassName(className);
							classTemp.setTeacherName(teacherName);
							classTemp.setClassRoom(classRoom);
							classTemp.setWeekDay(uc.WD);
							classTemp.setClassNum(uc.CN);
							detailPrimaryCacher[uc.CN][uc.WD]=classTemp;
						}
						else
						{
							detailPrimaryCacher[uc.CN][uc.WD]=null;
						}
					}
					//进入非编辑状态
					editingClass=detailPrimaryCacher[0][0];
					isEditing=false;
					initialEditTexts();
					detailListAdapter.notifyDataSetChanged();
				}
				else//非编辑状态下进入编辑状态
				{
					btnRight.setText("完成");
					//创建新的操作记录器
					operationRecorder=new OperationVector();
					isEditing=true;
					initialEditTexts();	
					detailListAdapter.notifyDataSetChanged();
				}
			}	    	
	    });
    }

	void initialEditTexts()
	{
		//根据编辑状态设置控件是否可用
		etClassName.setEnabled(isEditing);
		etClassLocation.setEnabled(isEditing);
		etTeacherName.setEnabled(isEditing);			
		if(editingClass!=null)//前选中的class为非空
		{
			etClassName.setText(editingClass.getClassName());
			etClassLocation.setText(editingClass.getClassRoom());
			etTeacherName.setText(editingClass.getTeacherName());				
		}
		else//当前选中的class为空,则不显示内容
		{
			etClassName.setText(" ");
			etClassLocation.setText(" ");
			etTeacherName.setText(" ");					
		}   
	}

	//
	
	/**
	 * @author SmartGang
	 *定义内部类，一个操作对象，用来保存用户的操作信息
	 *再通过一个对象容器，就可以保存所有的用户操作信息
	 */
	private class UserChoic
	{
		//定义operation的两个操作类型，添加和删除
		final static int OP_ADD=1;
		final static int OP_DELETE=2;
		int WD;
		int CN;
		private int operation=0;
		UserChoic(int WD, int CN)
		{
			this.WD=WD;
			this.CN=CN;
		}
		public void setAdd()
		{
			operation=OP_ADD;
		}
		public void setDelete()
		{
			operation=OP_DELETE;
		}
		public int getOperation()
		{
			return operation;
		}
	}
		
	/**
	 * @author SmartGang
	 *封装一个Vector容器，以满足使用需求
	 */
	private class OperationVector
	{
		Vector<UserChoic> recorder;
		
		OperationVector()
		{
			recorder=new Vector<UserChoic>();
		}
		
		void addOperation(UserChoic uc)
		{
			recorder.addElement(uc);
		}
		
		UserChoic isContain(int WD,int CN)
		{
			int n=recorder.size();
			for(int i=0;i<n;i++)
			{
				UserChoic uc=recorder.get(i);
				if(CN==uc.CN&&WD==uc.WD)return uc;
			}
			return null;
		}
		
		boolean removeOperation(UserChoic UC)
		{
			int n=recorder.size();
			for(int i=0;i<n;i++)
			{
				UserChoic uc=recorder.get(i);
				if(UC.CN==uc.CN&&UC.WD==uc.WD)
				{
					recorder.remove(i);
					return true;
				}
			}			
			return false;
		}
		
		int getSize()
		{
			return recorder.size();
		}
		
		UserChoic getOperation(int index)
		{
			return recorder.get(index);
		}
	}
	
	private class DetailListItemAdapter extends BaseAdapter
	{
		private LayoutInflater layoutInflater;
		Context context;
			
		public DetailListItemAdapter(Context context)
		{
			this.context = context;
			layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return classSheet.getMaxClassNumPerDay();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return detailPrimaryCacher[arg0];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		/**
		 * getView用来绘制每一列
		 * 按下时首先要判断当前的编辑状态
		 * 编辑状态：
		 * 		如果editingClass为空，则将空闲位置设为空白，其余为灰色，且不响应按键事件
		 * 		如果editingClass不为空，则将空闲位置设为空白，相同设为选中，其余设为灰且不响应按键事件
		 * 		在按键响应函数中做如下操作：
		 * 		查找operationRecorder，判断该位置之前是否已经操作过
		 * 		如果已经操作过，且之前的操作为增加，则将图片设为空白,将操作记录设为删除
		 * 		如果没有操作过，判断当前位置是否有课表，为空则操作记录为增加，将图片设为选中
		 * 		否则增加操作记录为删除，将图片设为删除。
		 *
		 * 非编辑状态：
		 * 		如果editingClass为空，则将空闲位置设为空白，其余为灰色
		 * 		如果editingClass不为空，则将空闲位置设为空白，相同设为选中，其余设为灰
		 * 		在按键响应函数中做如下操作：
		 * 		将当前选中的位置设为editingClass
		 * 		刷新显示
		 */	
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LinearLayout ll 	= (LinearLayout)layoutInflater.inflate(R.layout.mutil_edit_classs_detail_list_item, null);
			TextView tvClassNum =(TextView)ll.findViewById(R.id.tvMutiEditClassNum);		
			tvClassNum.setText("第"+position+"节");
			
			if(isEditing)//编辑状态下的处理
			{	//注意，detailPrimaryCacher保存的顺序为周一到周日，但显示时需要显示为周日到周六
				for(int i=0;i<7;i++)
				{
					final ImageView imageView=(ImageView)ll.findViewById(ImageViewIDsForList[i]);
					final Class classTemp=detailPrimaryCacher[position][i];
					//遍历7天课程，如果找到当天该节有课，如果该节课与要编辑的课名不同
					//表示已经被占用，设为灰且不可操作
					if(classTemp!=null&&editingClass!=null&&!editingClass.getClassName().equals(classTemp.getClassName()))
					{
						imageView.setClickable(false);
						imageView.setImageResource(R.drawable.gridgrey);
					}//editingClass为空时，则所有不为空的点都设为灰，且不响应按键
					else if(editingClass==null&&classTemp!=null)
					{
						imageView.setClickable(false);
						imageView.setImageResource(R.drawable.gridgrey);						
					}
					else//剩下有3种格:editingClass为空时的空格，和editing不为同时相同的格以及空格
					{
						final int WD=i;
						final int CN=position;
						//设为可点击
						imageView.setClickable(true);
						if(classTemp!=null)//有值的情况
						{
							imageView.setImageResource(R.drawable.gridyellow);	
						}
						else//无值的情况
						{
							imageView.setImageResource(R.drawable.gridwhite);							
						}					
						View.OnClickListener ocl=new OnClickListener()
						{
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Toast.makeText(editClass.this, "选中课程"+WD+CN, Toast.LENGTH_SHORT).show();		    		
								UserChoic uc=operationRecorder.isContain(WD,CN);
								if(uc!=null)//之前操作过
								{	//之前的操作为添加，则此次操作为删掉
									if(uc.getOperation()==UserChoic.OP_ADD)
									{
										imageView.setImageResource(R.drawable.gridwhite);
										uc.setDelete();
									}
									else//之前的操作为删除，则此次为添加 
									{
										imageView.setImageResource(R.drawable.gridyellow);
										uc.setAdd();
									}
								}
								else
								{	//第一次操作且该位置不为空，表示是删除
									if(classTemp!=null)
									{
										imageView.setImageResource(R.drawable.gridwhite);
										UserChoic UC=new UserChoic(WD,CN);
										UC.setDelete();
										operationRecorder.addOperation(UC);
									}
									else//第一次操作且该位置为空，表示增加
									{
										imageView.setImageResource(R.drawable.gridyellow);
										UserChoic UC=new UserChoic(WD,CN);
										UC.setAdd();
										operationRecorder.addOperation(UC);										
									}
								}
							}						
						};
						imageView.setOnClickListener(ocl);
					}
				}
			}
			else//非编辑状态下的处理
			{
				for(int i=0;i<7;i++)
				{
					final ImageView imageView=(ImageView)ll.findViewById(ImageViewIDsForList[i]);
					final Class classTemp=detailPrimaryCacher[position][i];
					final int WD=i;
					final int CN=position;
					//设为可点击
					imageView.setClickable(true);
					if(editingClass==null&&classTemp!=null)
					{	//选中的格为空时，对所有的非空格设为灰，且不响应按键
						imageView.setImageResource(R.drawable.gridgrey);
					}
					else if(editingClass!=null&&classTemp!=null&&!editingClass.getClassName().equals(classTemp.getClassName()))
					{	//选中的格为不空，对所有的不同的非空格设为灰，且不响应按键
						imageView.setImageResource(R.drawable.gridgrey);
					}
					else
					{
						if(classTemp!=null)//有值的情况
						{
							imageView.setImageResource(R.drawable.gridyellow);						
						}
						else//无值的情况
						{
							imageView.setBackgroundResource(R.drawable.gridwhite);							
						}
					}					
					View.OnClickListener ocl=new OnClickListener()
					{
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Toast.makeText(editClass.this, "选中课程"+WD+CN, Toast.LENGTH_SHORT).show();		    		
							editingClass=classTemp;
							initialEditTexts();
							notifyDataSetChanged();
						}						
					};
					imageView.setOnClickListener(ocl);
				}				
			}
			return ll;
		}		
	}	
}//main
