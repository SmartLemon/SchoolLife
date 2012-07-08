/**
 * 
 */
package com.weifajue.schoolLife.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

/**
 * @author SmartGang
 *对本地文件进行操作
 *使用SharedPreferences的方式保存当前正在操作的ClassSheet名字
 */
public class LocalFile extends Activity {

	public String getCurrentClassSheetName(Context ctx)
	{
		SharedPreferences sp=ctx.getSharedPreferences("sheetName", 0);
		String sheetName=sp.getString("CurrentClassSheet", "default");
		return sheetName;
	}
	
	public void setCurrentClassSheetName(String sheetName,Context ctx)
	{
		SharedPreferences sp=ctx.getSharedPreferences("sheetName", 0);
		// 取得编辑对象
		SharedPreferences.Editor editor = sp.edit();
		// 添加值
		editor.putString("CurrentClassSheet", sheetName);		
		// 提交保存
		editor.commit();
	}	
}
