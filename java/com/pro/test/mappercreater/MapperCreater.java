package pro.test.mappercreater;

import pro.test.mappercreater.util.FileCreateUtil;
import pro.test.mappercreater.util.NameUtil;
import pro.test.mappercreater.util.SqlTypeUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class MapperCreater extends Creater{
	
	MapperCreater(){
		super();
		configMap.put("basePackageName", "com.cmx");
		configMap.put("mapperPath", "mapper");
		configMap.put("suffix", "Dao");
	}
	
	
	@SuppressWarnings("unchecked")
	public void mapperCreater(Map<String, Object> tableinfo){
		Set<String> tablename = tableinfo.keySet();

		for(String s : tablename){
			String str = s.toLowerCase().replace("t_", "");
			String newStr = str.substring(0, 1).toUpperCase()+str.substring(1);
			if(newStr.indexOf("_") != -1){
				String[] splitStr = newStr.split("_");
				newStr = "";
				for(int i = 0; i < splitStr.length; i++){
					newStr += splitStr[i].substring(0,1).toUpperCase()+splitStr[i].substring(1);
				}
			}
			if(s.endsWith("-key")){//这里取到每个表的主键，但是一般情况下，表都使用id当做操作条件，所以暂时不使用主键。
				continue;
			}else{
				mapperCodeCreater(newStr, s, (List<Map<String, Object>>)tableinfo.get(s), ((List<String>)tableinfo.get(s+"-key")).get(0));
			}
		}
	}
	
	//创建mybatis mapper.xml模板
	public void mapperCodeCreater(String beanName, String tableName, List<Map<String, Object>> infolist, String primaryKey){
		StringBuffer sb = new StringBuffer();
		List<String> configNameList = new ArrayList<String>();
		String upName = beanName.substring(0, 1).toUpperCase()+beanName.substring(1);
		for(int i = 0; i < infolist.size(); i++){
			configNameList.add(((Map<String, Object>)infolist.get(i)).get("columnName").toString());
		}
		
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
		sb.append("<mapper namespace=\""+ configMap.get("basePackageName").toString().replace("\\", ".") + "." + configMap.get("daoPath")+ "." + upName + configMap.get("suffix")+"\">\n");
		//resultMap
		sb.append("\t<resultMap id=\"BaseResultMap\" type=\"" + beanName + "\" >\n");
		infolist.forEach((value) -> {
			sb.append("\t\t<result column=\""+ value.get("columnName") +"\" property=\""+ NameUtil.lineToHump(value.get("columnName"))+"\" />\n");
		});
		sb.append("\t</resultMap>\n\n");

		sb.append("\t<sql id = \"table\">\n\t\t"+tableName+"\n\t</sql>\n");
		//selectId start
		sb.append("\n\t<sql id = \"columns\">\n\t\t");
		for(int i = 0; i < configNameList.size(); i++){
			if(i != configNameList.size()-1){
				sb.append(configNameList.get(i) + ", ");
				if((i+1) % 5 == 0){
					sb.append("\n\t\t");
				}
			}else{
				sb.append(configNameList.get(i) + "\n");
			}
		}
		sb.append("\t</sql>\n");

		sb.append("\n\t<sql id = \"values\">\n\t\t");
		for(int i = 0; i < configNameList.size(); i++){
			if(i != configNameList.size()-1){
				sb.append("#{" + NameUtil.lineToHump(configNameList.get(i)) + "}, ");
				if((i+1) % 5 == 0){
					sb.append("\n\t\t");
				}
			}else{
				sb.append("#{" + NameUtil.lineToHump(configNameList.get(i)) + "}\n");
			}
		}
		sb.append("\t</sql>\n");
		//selectId end
		sb.append("\n");
		//select start
		sb.append("\t<select id = \"select\" parameterType = \""+ beanName +"\" resultMap = \"BaseResultMap\">\n");
		sb.append("\t\tselect <include refid = \"columns\" />\n");
		sb.append("\t\tfrom <include refid=\"table\"/> \n");
		sb.append("\t\t<where>\n");
		for(int i = 0; i < configNameList.size(); i++){
			String columnName = configNameList.get(i);
			sb.append("\t\t\t<if test = \""+ NameUtil.lineToHump(columnName) +" != null\">");
			sb.append("AND "+ columnName +" = " + "#{"+ NameUtil.lineToHump(columnName) +"}");
			sb.append("</if>\n");
		}
		sb.append("\t\t</where>\n");
		sb.append("\t</select>\n");
		//select end
		sb.append("\n");
		//insert start
		sb.append("\t<insert id = \"insert\" keyProperty = \"id\" parameterType = \""+ beanName +"\" useGeneratedKeys = \"true\">\n");
		sb.append("\t\tinsert into <include refid=\"table\"/>(<include refid=\"columns\"/>)\n");
//		for(int i = 0; i < configNameList.size(); i++){
//			if(i != configNameList.size()-1){
//				sb.append("\t\t\t" + configNameList.get(i) + ",\n");
//			}else{
//				sb.append("\t\t\t" + configNameList.get(i) + ")\n");
//			}
//		}
		sb.append("\t\tvalues (<include refid=\"values\"/>)\n");
//		for(int i = 0; i < configNameList.size(); i++){
//			if(i != configNameList.size()-1){
//				sb.append("\t\t\t#{" + configNameList.get(i) + "},\n");
//			}else{
//				sb.append("\t\t\t#{" + configNameList.get(i) + "})\n");
//			}
//		}
		sb.append("\t</insert>\n");
		//insert end
		sb.append("\n");
		//delete start 
		sb.append("\t<delete id = \"delete\" parameterType = \"String\">\n");
		sb.append("\t\tdelete from <include refid=\"table\"/>\n\t\twhere "+ primaryKey +" = #{"+ NameUtil.lineToHump(primaryKey) +"}\n");
		sb.append("\t</delete>\n");
		//delete end
		sb.append("\n");
		//update start
		sb.append("\t<update id = \"update\" parameterType = \""+ beanName +"\">\n");
		sb.append("\t\tupdate <include refid=\"table\"/>\n");
		sb.append("\t\t<trim prefix=\"SET\" suffixOverrides=\",\">\n");
		for(int i = 0; i < configNameList.size(); i++){
			String configName = configNameList.get(i);
			sb.append("\t\t\t<if test = \""+ NameUtil.lineToHump(configName) +" != null\">");
			sb.append(configName +" = #{"+ NameUtil.lineToHump(configName )+"},");
			sb.append("</if>\n");
		}
		sb.append("\t\t</trim>\n");
		sb.append("\t\twhere "+ primaryKey +" = #{"+ NameUtil.lineToHump(primaryKey) +"}\n");
		sb.append("\t</update>\n");
		//update end
		sb.append("\n");
		//getById start 
		sb.append("\t<select id = \"getById\" resultMap = \"BaseResultMap\">\n");
		sb.append("\t\tselect <include refid = \"columns\" />\n");
		sb.append("\t\tfrom <include refid = \"table\" />\n");
		sb.append("\t\twhere\n");
		sb.append("\t\t"+ primaryKey +" = #{"+ NameUtil.lineToHump(primaryKey) +"}\n");
		sb.append("\t</select>\n");
		//getById end
		sb.append("\n</mapper>");
		
		//System.out.println(sb.toString());
		try {
			String mapperPath = configMap.get("filePath")+"\\"+configMap.get("basePackageName").toString()+"\\"+configMap.get("mapperPath");
			FileCreateUtil.createFile(NameUtil.lineToHump(beanName.toLowerCase())+"Mapper.xml", mapperPath, sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
