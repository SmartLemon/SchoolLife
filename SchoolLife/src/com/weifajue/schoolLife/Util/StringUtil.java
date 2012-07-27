package com.weifajue.schoolLife.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 字符串辅助
 * 
 * @author Lemon
 *
 */
public class StringUtil {

	private static final String COMMA = ",";
	
	/**
	 * list -> String
	 * list:100,200,300 - >  string:100,200,300
	 * 
	 * @param inputList
	 * @return
	 */
	public static String listStrToString(List<String> inputList) {
		
		if(null == inputList || inputList.isEmpty()) {
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		
		for(String input : inputList) {
			sb.append(input);
			sb.append(COMMA);
		}
		sb.setLength(sb.length()-1);
		
		return sb.toString(); 
	}
	
	/**
	 * list -> String
	 * list:100,200,300 - >  string:100,200,300
	 * 
	 * @param inputList
	 * @return
	 */
	public static String listLongToString(List<Long> inputList) {
		
		if(null == inputList || inputList.isEmpty()) {
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		
		for(Long input : inputList) {
			sb.append(input);
			sb.append(COMMA);
		}
		sb.setLength(sb.length()-1);
		
		return sb.toString(); 
	}
	
	/**
	 * 从字符串转化到列表
	 * 
	 * @param input
	 * @return
	 */
	public static List<String> stringToListStr(String input) {
		
		List<String> outList = new ArrayList<String>();
		if(null == input || input.trim().isEmpty()) {
			return outList;
		}
		String[] out = input.split(COMMA);
		
		return Arrays.asList(out);
	}
	
	public static List<Long> stringToListLong(String input) {
		
		List<Long> outList = new ArrayList<Long>();
		if(null == input || input.trim().isEmpty()) {
			return outList;
		}
		String[] out = input.split(COMMA);
		for(String temp : out) {
			outList.add(Long.parseLong(temp));
		}
		
		return outList;
		
	}
	
}
