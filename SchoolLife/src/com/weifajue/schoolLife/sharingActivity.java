/**
 * 
 */
package com.weifajue.schoolLife;

import java.util.ArrayList;
import java.util.List;

import com.weifajue.schoolLife.data.LikeDB;
import com.weifajue.schoolLife.data.UserDB;
import com.weifajue.schoolLife.model.Like;
import com.weifajue.schoolLife.model.User;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author SmartGang
 *
 */
public class sharingActivity extends Activity {
	public View topHeader;
	
	private LikeDB likeDB;
	
	private UserDB userDB;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sharing_view);
		//设置顶端标题栏，把左右两个按键去显示（保留位置），更改标题
		topHeader=(View)findViewById(R.id.sharingViewHeader);
		Button btn=(Button)topHeader.findViewById(R.id.top_btn_left);
//		btn.setVisibility(View.INVISIBLE);
		btn.setOnClickListener(saveLike);
		btn=(Button)topHeader.findViewById(R.id.top_btn_right);
		btn.setVisibility(View.INVISIBLE);
		TextView top_textView=(TextView)topHeader.findViewById(R.id.tv_toptitle);
		top_textView.setText("备忘");
		
		likeDB = new LikeDB(this);
		userDB = new UserDB(this);
	}
	
	private OnClickListener saveLike = new OnClickListener() {
		
		
		@Override
		public void onClick(View v) {
			Like like = new Like();
			like.setLike(true);
			like.setRemmoteSharingId(10);
			like.setRemoteUserId(12);
			likeDB.insert(like );
			
			Like like2 = new Like();
			like2.setLike(false);
			like2.setRemmoteSharingId(10);
			like2.setRemoteUserId(12);
			likeDB.insert(like2 );
			
			likeDB.queryAll();
			
			User user = new User();
			user.setId(100);
			user.setEmail("test@weifajue.com");
			user.setPassword("password");
			user.setCityName("hangzhou");
			user.setGender("male");
			user.setLogon(false);
			user.setMacAddress("my");
			user.setProvinceName("zhejiang");
			user.setSchoolName("zju");
			user.setWeiboToken("not exist");
			List<Long> sharingIdList = new ArrayList<Long>();
			sharingIdList.add(new Long(10));
			sharingIdList.add(new Long(20));
			sharingIdList.add(new Long(30));
			user.setSharingIdList(sharingIdList);
			
			List<Long> schoolIdList = new ArrayList<Long>();
			schoolIdList.add(new Long(100));
			schoolIdList.add(new Long(200));
			schoolIdList.add(new Long(300));
			user.setSchoolIdList(schoolIdList);
			
			List<Long> commentIdList = new ArrayList<Long>();
			commentIdList.add(new Long(1));
			commentIdList.add(new Long(2));
			commentIdList.add(new Long(3));
			user.setCommentIdList(commentIdList);
			
			userDB.insert(user);
			
			userDB.queryAll();
			
		}
	};
	
	protected void onDestroy() {
		super.onDestroy();
		likeDB.closeDB();
	};
	
}
