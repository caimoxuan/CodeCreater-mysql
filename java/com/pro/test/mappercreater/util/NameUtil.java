package pro.test.mappercreater.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameUtil {

	private static Pattern linePattern = Pattern.compile("_(\\w)");
	
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

	public static String lineToHump(Object str){
		str = str.toString().toLowerCase();
		Matcher matcher = linePattern.matcher((String)str);
		StringBuffer sb = new StringBuffer();
		while(matcher.find()){
			matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
	
}
