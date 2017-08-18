package com.pro.test.mappercreater.util;

public class NameUtil {
	
	
	
	public static String getBeanName(String tableName){
		
		tableName = tableName.toLowerCase();
		String newStr = tableName.substring(0, 1).toUpperCase()+tableName.substring(1);
		if(newStr.indexOf("_") != -1){
			String[] splitStr = newStr.split("_");
			newStr = "";
			for(int i = 0; i < splitStr.length; i++){
				newStr += splitStr[i].substring(0,1).toUpperCase()+splitStr[i].substring(1);
			}
		}
		
		return newStr;
	}
	
}
