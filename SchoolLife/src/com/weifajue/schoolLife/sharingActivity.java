/**
 * 
 */
package com.weifajue.schoolLife;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author SmartGang
 *
 */
public class sharingActivity extends Activity {

	public View topHeader;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sharing_view);
		//设置顶端标题栏，把左右两个按键去显示（保留位置），更改标题
		topHeader=(View)findViewById(R.id.sharingViewHeader);
		Button btn=(Button)topHeader.findViewById(R.id.top_btn_left);
		btn.setVisibility(View.INVISIBLE);
		btn=(Button)topHeader.findViewById(R.id.top_btn_right);
		btn.setVisibility(View.INVISIBLE);
		TextView top_textView=(TextView)topHeader.findViewById(R.id.tv_toptitle);
		top_textView.setText("备忘");
	}
}
