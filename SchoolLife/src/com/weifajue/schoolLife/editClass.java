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

	public View topHeader;	//�������
	private EditText etClassName,etClassLocation,etTeacherName;
	private ListView lvMutiEditListView;
	private ClassSheet classSheet;
	private Class editingClass;
	private boolean isEditing=false;//����ָʾ��ǰ�Ƿ����༭״̬��Ĭ��falseΪ�Ǳ༭״̬
	/** �γ�ģ�����ݻ�������������������
	 * ��һ�����Ȱ��α����������ɣ�ʵ��Ϊÿһ�ڵ��б�ָ��
	 * �ڶ������ȹ̶�Ϊ7����һ��7�������
	 * DAYS_OF_WEEK,��0��6�ֱ�����:SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, and SATURDAY
	 */
	private Class[][] detailPrimaryCacher;
	OperationVector operationRecorder;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editclasslist);
        Log.e("DebugLog","run in editClass Activity");
    	
        LocalFile lf=new LocalFile();
        String classSheetName=lf.getCurrentClassSheetName(this);
        //��ȡ��ǰ�α���Ϣ����ʼ������
        final ClassDB cDB=new ClassDB(editClass.this);
        classSheet=cDB.readClassSheet(classSheetName);
        detailPrimaryCacher=new Class[classSheet.getMaxClassNumPerDay()][];//��ʼ�������һ��
        for(int i=0;i<detailPrimaryCacher.length;i++)//��ʼ������ڶ���,�̶���СΪ7��
        {	
        	detailPrimaryCacher[i]=cDB.readClassTemplateByNumList(classSheetName, i);
        }
        //Ĭ��װ����һ��һ�ڿΣ���ʹ��Spinnerѡ��ʱ�ı�
        editingClass=cDB.readClass(classSheetName, 1, 0);      
		//���ö��˱���������������������ȥ��ʾ������λ�ã������ı���
		topHeader=(View)findViewById(R.id.editClassHeader);
		//��ʾ����ļ�ͷ�����ڷ���
		Button btnLeft=(Button)topHeader.findViewById(R.id.top_btn_left);
//		btn.setVisibility(View.INVISIBLE);
		final Button btnRight=(Button)topHeader.findViewById(R.id.top_btn_right);
//		btn.setVisibility(View.INVISIBLE);
		TextView top_textView=(TextView)topHeader.findViewById(R.id.tv_toptitle);
		top_textView.setText("�༭�γ�");
		
		btnRight.setText("�༭");
		LayoutParams params = (LayoutParams) btnRight.getLayoutParams();
		params.setMargins(0, 0, 10, 0); //substitute parameters for left, top, right, bottom
		btnRight.setLayoutParams(params);
		btnRight.setBackgroundColor(R.drawable.button_bkg_normal);
		
		//��ʼʼ��edit�ؼ�
	    etClassName=(EditText)findViewById(R.id.etShowDetailClassName);
	    etClassLocation=(EditText)findViewById(R.id.etShowDetailClassRoom);
	    etTeacherName=(EditText)findViewById(R.id.etShowDetailTeacherName);	    
	    initialEditTexts();
	    //��ʼ��ListView
	    lvMutiEditListView=(ListView)findViewById(R.id.lvMutiEditClassDetial);
	    LinearLayout headerViewForList;
	    headerViewForList=(LinearLayout)LayoutInflater.from(editClass.this).inflate(R.layout.mutil_edit_class_detail_listview_header, null);		
	    lvMutiEditListView.addHeaderView(headerViewForList);
	    final DetailListItemAdapter detailListAdapter=new DetailListItemAdapter(this);
	    lvMutiEditListView.setAdapter(detailListAdapter);
	    
	    /**
	     * ���ؼ���Ӧ����
	     * detailPrimaryCacher����������д�����ݿ����
	     * ��ɺ���÷��ؼ�����
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
				if(isEditing)//�༭״̬�°��´˼�������Ǳ༭״̬
				{
					btnRight.setText("�༭");
					//��ȡ�༭��Ϣ
					String className;
					if(etClassName.getText()!=null)className=etClassName.getText().toString();
					else
					{
						Toast.makeText(editClass.this, "������γ�����", Toast.LENGTH_SHORT).show();		    		
						return;
					}
					String teacherName;
					if(etTeacherName.getText()!=null)teacherName=etTeacherName.getText().toString();
					else
					{
						Toast.makeText(editClass.this, "��������ʦ����", Toast.LENGTH_SHORT).show();		    		
						return;
					}
					String classRoom;
					if(etClassLocation.getText()!=null)classRoom=etClassLocation.getText().toString();
					else
					{
						Toast.makeText(editClass.this, "�������Ͽεص�", Toast.LENGTH_SHORT).show();		    		
						return;
					}
					
					//��ȡ������Ϣ������classд��detailPrimaryCacher��
					UserChoic uc;
					for(int i=0;i<operationRecorder.getSize();i++)
					{
						uc=(UserChoic) operationRecorder.getOperation(i);
						//�ж�����Ӳ�������ɾ������
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
					//����Ǳ༭״̬
					editingClass=detailPrimaryCacher[0][0];
					isEditing=false;
					initialEditTexts();
					detailListAdapter.notifyDataSetChanged();
				}
				else//�Ǳ༭״̬�½���༭״̬
				{
					btnRight.setText("���");
					//�����µĲ�����¼��
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
		//���ݱ༭״̬���ÿؼ��Ƿ����
		etClassName.setEnabled(isEditing);
		etClassLocation.setEnabled(isEditing);
		etTeacherName.setEnabled(isEditing);			
		if(editingClass!=null)//ǰѡ�е�classΪ�ǿ�
		{
			etClassName.setText(editingClass.getClassName());
			etClassLocation.setText(editingClass.getClassRoom());
			etTeacherName.setText(editingClass.getTeacherName());				
		}
		else//��ǰѡ�е�classΪ��,����ʾ����
		{
			etClassName.setText(" ");
			etClassLocation.setText(" ");
			etTeacherName.setText(" ");					
		}   
	}

	//
	
	/**
	 * @author SmartGang
	 *�����ڲ��࣬һ�������������������û��Ĳ�����Ϣ
	 *��ͨ��һ�������������Ϳ��Ա������е��û�������Ϣ
	 */
	private class UserChoic
	{
		//����operation�������������ͣ���Ӻ�ɾ��
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
	 *��װһ��Vector������������ʹ������
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
		 * getView��������ÿһ��
		 * ����ʱ����Ҫ�жϵ�ǰ�ı༭״̬
		 * �༭״̬��
		 * 		���editingClassΪ�գ��򽫿���λ����Ϊ�հף�����Ϊ��ɫ���Ҳ���Ӧ�����¼�
		 * 		���editingClass��Ϊ�գ��򽫿���λ����Ϊ�հף���ͬ��Ϊѡ�У�������Ϊ���Ҳ���Ӧ�����¼�
		 * 		�ڰ�����Ӧ�����������²�����
		 * 		����operationRecorder���жϸ�λ��֮ǰ�Ƿ��Ѿ�������
		 * 		����Ѿ�����������֮ǰ�Ĳ���Ϊ���ӣ���ͼƬ��Ϊ�հ�,��������¼��Ϊɾ��
		 * 		���û�в��������жϵ�ǰλ���Ƿ��пα�Ϊ���������¼Ϊ���ӣ���ͼƬ��Ϊѡ��
		 * 		�������Ӳ�����¼Ϊɾ������ͼƬ��Ϊɾ����
		 *
		 * �Ǳ༭״̬��
		 * 		���editingClassΪ�գ��򽫿���λ����Ϊ�հף�����Ϊ��ɫ
		 * 		���editingClass��Ϊ�գ��򽫿���λ����Ϊ�հף���ͬ��Ϊѡ�У�������Ϊ��
		 * 		�ڰ�����Ӧ�����������²�����
		 * 		����ǰѡ�е�λ����ΪeditingClass
		 * 		ˢ����ʾ
		 */	
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LinearLayout ll 	= (LinearLayout)layoutInflater.inflate(R.layout.mutil_edit_classs_detail_list_item, null);
			TextView tvClassNum =(TextView)ll.findViewById(R.id.tvMutiEditClassNum);		
			tvClassNum.setText("��"+position+"��");
			
			if(isEditing)//�༭״̬�µĴ���
			{	//ע�⣬detailPrimaryCacher�����˳��Ϊ��һ�����գ�����ʾʱ��Ҫ��ʾΪ���յ�����
				for(int i=0;i<7;i++)
				{
					final ImageView imageView=(ImageView)ll.findViewById(ImageViewIDsForList[i]);
					final Class classTemp=detailPrimaryCacher[position][i];
					//����7��γ̣�����ҵ�����ý��пΣ�����ýڿ���Ҫ�༭�Ŀ�����ͬ
					//��ʾ�Ѿ���ռ�ã���Ϊ���Ҳ��ɲ���
					if(classTemp!=null&&editingClass!=null&&!editingClass.getClassName().equals(classTemp.getClassName()))
					{
						imageView.setClickable(false);
						imageView.setImageResource(R.drawable.gridgrey);
					}//editingClassΪ��ʱ�������в�Ϊ�յĵ㶼��Ϊ�ң��Ҳ���Ӧ����
					else if(editingClass==null&&classTemp!=null)
					{
						imageView.setClickable(false);
						imageView.setImageResource(R.drawable.gridgrey);						
					}
					else//ʣ����3�ָ�:editingClassΪ��ʱ�Ŀո񣬺�editing��Ϊͬʱ��ͬ�ĸ��Լ��ո�
					{
						final int WD=i;
						final int CN=position;
						//��Ϊ�ɵ��
						imageView.setClickable(true);
						if(classTemp!=null)//��ֵ�����
						{
							imageView.setImageResource(R.drawable.gridyellow);	
						}
						else//��ֵ�����
						{
							imageView.setImageResource(R.drawable.gridwhite);							
						}					
						View.OnClickListener ocl=new OnClickListener()
						{
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Toast.makeText(editClass.this, "ѡ�пγ�"+WD+CN, Toast.LENGTH_SHORT).show();		    		
								UserChoic uc=operationRecorder.isContain(WD,CN);
								if(uc!=null)//֮ǰ������
								{	//֮ǰ�Ĳ���Ϊ��ӣ���˴β���Ϊɾ��
									if(uc.getOperation()==UserChoic.OP_ADD)
									{
										imageView.setImageResource(R.drawable.gridwhite);
										uc.setDelete();
									}
									else//֮ǰ�Ĳ���Ϊɾ������˴�Ϊ��� 
									{
										imageView.setImageResource(R.drawable.gridyellow);
										uc.setAdd();
									}
								}
								else
								{	//��һ�β����Ҹ�λ�ò�Ϊ�գ���ʾ��ɾ��
									if(classTemp!=null)
									{
										imageView.setImageResource(R.drawable.gridwhite);
										UserChoic UC=new UserChoic(WD,CN);
										UC.setDelete();
										operationRecorder.addOperation(UC);
									}
									else//��һ�β����Ҹ�λ��Ϊ�գ���ʾ����
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
			else//�Ǳ༭״̬�µĴ���
			{
				for(int i=0;i<7;i++)
				{
					final ImageView imageView=(ImageView)ll.findViewById(ImageViewIDsForList[i]);
					final Class classTemp=detailPrimaryCacher[position][i];
					final int WD=i;
					final int CN=position;
					//��Ϊ�ɵ��
					imageView.setClickable(true);
					if(editingClass==null&&classTemp!=null)
					{	//ѡ�еĸ�Ϊ��ʱ�������еķǿո���Ϊ�ң��Ҳ���Ӧ����
						imageView.setImageResource(R.drawable.gridgrey);
					}
					else if(editingClass!=null&&classTemp!=null&&!editingClass.getClassName().equals(classTemp.getClassName()))
					{	//ѡ�еĸ�Ϊ���գ������еĲ�ͬ�ķǿո���Ϊ�ң��Ҳ���Ӧ����
						imageView.setImageResource(R.drawable.gridgrey);
					}
					else
					{
						if(classTemp!=null)//��ֵ�����
						{
							imageView.setImageResource(R.drawable.gridyellow);						
						}
						else//��ֵ�����
						{
							imageView.setBackgroundResource(R.drawable.gridwhite);							
						}
					}					
					View.OnClickListener ocl=new OnClickListener()
					{
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Toast.makeText(editClass.this, "ѡ�пγ�"+WD+CN, Toast.LENGTH_SHORT).show();		    		
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
