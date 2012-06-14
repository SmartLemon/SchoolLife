/**
 * 
 */
package com.weifajue.schoolLife.data;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * @author SmartGang
 *�Ա����ļ����в���
 *ʹ��SharedPreferences�ķ�ʽ���浱ǰ���ڲ�����ClassSheet����
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
		// ȡ�ñ༭����
		SharedPreferences.Editor editor = uiState.edit();
		// ���ֵ
		editor.putString("CurrentClassSheet", sheetName);		
		// �ύ����
		editor.commit();
	}	
}
