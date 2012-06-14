/**
 * 
 */
package com.weifajue.schoolLife.data;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * @author SmartGang
 *对本地文件进行操作
 *使用SharedPreferences的方式保存当前正在操作的ClassSheet名字
 */
public class LocalFile extends Activity {

	public String getCurrentClassSheetName()
	{
		SharedPreferences sp= getPreferences(Activity.MODE_PRIVATE);
		String sheetName=sp.getString("CurrentClassSheet", "default");
		return sheetName;
	}
	
	public void setCurrentClassSheetName(String sheetName)
	{
		SharedPreferences uiState = getPreferences(Activity.MODE_PRIVATE);
		// 取得编辑对象
		SharedPreferences.Editor editor = uiState.edit();
		// 添加值
		editor.putString("CurrentClassSheet", sheetName);		
		// 提交保存
		editor.commit();
	}	
}
