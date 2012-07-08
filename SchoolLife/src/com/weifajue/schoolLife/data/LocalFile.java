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
 *�Ա����ļ����в���
 *ʹ��SharedPreferences�ķ�ʽ���浱ǰ���ڲ�����ClassSheet����
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
		// ȡ�ñ༭����
		SharedPreferences.Editor editor = sp.edit();
		// ���ֵ
		editor.putString("CurrentClassSheet", sheetName);		
		// �ύ����
		editor.commit();
	}	
}
