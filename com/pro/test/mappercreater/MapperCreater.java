package com.pro.test.mappercreater;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.pro.test.mappercreater.util.FileCreateUtil;

public class MapperCreater {
	
	private Map<String, Object> configMap;
	
	MapperCreater(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("packageName", "com.cmx.mapper");
		map.put("suffix", "Dao");
		this.setConfigMap(map);
	}
	
	public Map<String, Object> getConfigMap() {
		return configMap;
	}

	public void setConfigMap(Map<String, Object> configMap) {
		this.configMap = configMap;
	}
	
	public void mapperCreater(Map<String, Object> tableinfo){
		Set<String> tablename = tableinfo.keySet();
		
		for(String s : tablename){
			s = s.toLowerCase();
			String newStr = s.substring(0, 1).toUpperCase()+s.substring(1);
			if(newStr.indexOf("_") != -1){
				String[] splitStr = newStr.split("_");
				newStr = "";
				for(int i = 0; i < splitStr.length; i++){
					newStr += splitStr[i].substring(0,1).toUpperCase()+splitStr[i].substring(1);
				}
			}
			
			mapperCodeCreater(newStr, s, (List<Map<String, Object>>)tableinfo.get(s));
		}
	}
	
	//创建mybatis mapper.xml模板
	public void mapperCodeCreater(String beanName, String tableName, List<Map<String, Object>> infolist){
		StringBuffer sb = new StringBuffer();
		List<String> configNameList = new ArrayList<String>();
		String upName = beanName.substring(0, 1).toUpperCase()+beanName.substring(1);
		for(int i = 0; i < infolist.size(); i++){
			configNameList.add(((Map<String, Object>)infolist.get(i)).get("columnName").toString());
		}
			
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
		sb.append("<mapper namespace=\""+ configMap.get("packageName")+ upName + configMap.get("suffix")+"\">\n");
		//selectId start
		sb.append("\t<sql id = \"selecrId\">\n");
		for(int i = 0; i < configNameList.size(); i++){
			if(i != configNameList.size()-1){
				sb.append("\t\t" + configNameList.get(i) + ",\n");
			}else{
				sb.append("\t\t" + configNameList.get(i) + "\n");
			}
		}
		sb.append("\t</sql>\n");
		//selectId end
		sb.append("\n");
		//select start
		sb.append("\t<select id = \"query\" resultMap = \""+ beanName +"\" parameterType = \"java.util.HashMap\">\n");
		sb.append("\t\tselect <include refid = \"selectId\" />\n");
		sb.append("\t\tfrom "+ tableName +" \n");
		sb.append("\t\t<where>\n");
		for(int i = 0; i < configNameList.size(); i++){
			String columnName = configNameList.get(i);
			sb.append("\t\t\t<if test = \""+ columnName +" != null\">\n");
			sb.append("\t\t\t\tAND "+ columnName +" = " + "#{"+ columnName +"}\n");
			sb.append("\t\t\t</if>\n");
		}
		sb.append("\t\t</where>\n");
		sb.append("\t</select>\n");
		//select end
		sb.append("\n");
		//insert start
		sb.append("\t<insert id = \"add\" keyProperty = \"id\" parameterType = \""+ beanName +"\" userGeneratedKey = \"true\">\n");
		sb.append("\t\tinsert into "+ tableName +"(\n");
		for(int i = 0; i < configNameList.size(); i++){
			if(i != configNameList.size()-1){
				sb.append("\t\t\t" + configNameList.get(i) + ",\n");
			}else{
				sb.append("\t\t\t" + configNameList.get(i) + ")\n");
			}
		}
		sb.append("\t\tvalues(\n");
		for(int i = 0; i < configNameList.size(); i++){
			if(i != configNameList.size()-1){
				sb.append("\t\t\t#{" + configNameList.get(i) + "},\n");
			}else{
				sb.append("\t\t\t#{" + configNameList.get(i) + "})\n");
			}
		}
		sb.append("\t</insert>\n");
		//insert end
		sb.append("\n");
		//delete start 
		sb.append("\t<delete id = \"delete\" parameterType = \"String\">\n");
		sb.append("\t\tdelete from "+ tableName + " where id = #{id}\n");
		sb.append("\t</delete>\n");
		//delete end
		sb.append("\n");
		//update start
		sb.append("\t<update id = \"modify\" parameterType = \""+ beanName +"\">\n");
		sb.append("\t\tupdate "+ tableName +"\n");
		sb.append("\t\t<set>\n");
		for(int i = 0; i < configNameList.size(); i++){
			String configName = configNameList.get(i);
			sb.append("\t\t\t<if test = \""+ configName +" != null\">\n");
			if(i != configNameList.size()){
				sb.append("\t\t\t\t"+ configName +" = #{"+ configName +"},\n");
			}else{
				sb.append("\t\t\t\t"+ configName +" = #{"+ configName +"}\n");
			}
			sb.append("\t\t\t</if>\n");
		}
		sb.append("\t\twhere id = #{id}\n");
		sb.append("\t</update>\n");
		//update end
		sb.append("\n");
		//getById start 
		sb.append("\t<select id = \"getById\" parameterType = \"String\" resultType = \""+ beanName +"\">\n");
		sb.append("\t\tselect <include refid = \"selectId\" />\n");
		sb.append("\t\tfrom "+ tableName +"\n");
		sb.append("\t\twhere\n");
		sb.append("\t\tid = #{id}\n");
		sb.append("\t</select>\n");
		//getById end
		sb.append("\n</mapper>");
		
		//System.out.println(sb.toString());
		try {
			FileCreateUtil.createFile(beanName.toLowerCase()+"-mapper.xml", "F:\\com\\cmx\\mapper\\", sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
