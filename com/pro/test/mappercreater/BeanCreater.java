package com.pro.test.mappercreater;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.pro.test.mappercreater.util.FileCreateUtil;

public class BeanCreater {
	
	BeanCreater(){
		Map<String, Object> config = new HashMap<String, Object>();
		config.put("packageName", "com.cmx.entity");
		this.setConfigMap(config);
	}
	
	private String filepath = System.getProperty("user.dir");
	
	private Map<String, Object> configMap;
	
	public Map<String, Object> getConfigMap() {
		return configMap;
	}

	public void setConfigMap(Map<String, Object> configMap) {
		this.configMap = configMap;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	@SuppressWarnings("unchecked")
	public void beanCreate(Map<String, Object> tableinfo){
		
		Set<String> tablename = tableinfo.keySet();
		
		for(String s : tablename){
			//System.out.println(s);
			s = s.toLowerCase();
			String newStr = s.substring(0, 1).toUpperCase()+s.substring(1);
			if(newStr.indexOf("_") != -1){
				String[] splitStr = newStr.split("_");
				newStr = "";
				for(int i = 0; i < splitStr.length; i++){
					newStr += splitStr[i].substring(0,1).toUpperCase()+splitStr[i].substring(1);
				}
			}
			
			createBeanCode(newStr, (List<Map<String, Object>>)tableinfo.get(s));
			break;
		}
		
	}
	
	//创建bean模板
	public String createBeanCode(String beanName, List<Map<String, Object>> infolist){
		StringBuffer sb = new StringBuffer();
		Map<String, String> columninfo = new HashMap<String, String>();
		sb.append("package " + configMap.get("packageName")+";\n");
		sb.append("\nimport java.io.Serializable;\n");
		sb.append("\npublic class " + beanName + " implements Serializable {\n");
			sb.append("\tprivate static final long serialVersionUID = 1L;\n");
			//生成属性
			for(int i = 0; i < infolist.size(); i++){
				Map<String, Object> map = infolist.get(i);
				String type = getType(map.get("columnType").toString());
				String columnName = map.get("columnName").toString();
				columninfo.put(columnName, type);
				sb.append("\tprivate "+ type + " " + columnName + ";\n");
			}
			sb.append("\n");
			//生成属性的getter和setter
			for(String s : columninfo.keySet()){
				String type = columninfo.get(s);
				String upname = s.substring(0,1).toUpperCase()+s.substring(1);
				sb.append("\tpublic void set" + upname + "("+ type +" "+s+"){\n");
					sb.append("\t\tthis."+ s + " = " + s + ";\n");
				sb.append("\t}\n");
				sb.append("\tpublic " + type + " get" + upname + "(){\n");
					sb.append("\t\treturn " + s +";\n");
				sb.append("\t}\n\n");
			}
			//生成toString
			sb.append("\tpublic String toString(){\n");
				sb.append("\t\treturn \"" + beanName + "[\"\n");
				int configCount = columninfo.keySet().size();
				for(String s : columninfo.keySet()){
					configCount--;
					if(configCount <= 0){
						sb.append("\t\t+\"" + s + "=\" + " + s + " +\n");
					}else{
						sb.append("\t\t+\"" + s + "=\" + " + s + " + \",\"\n");
					}
				}
				sb.append("\t\t+\"]\";\n");
			sb.append("\t}");
		sb.append("\n}");
		//System.out.println(sb.toString());
		return sb.toString();
		
	}
	
	//根据数据库字段类型获取属性的java类型
	public String getType(String columnType){
		if("INT".equals(columnType)){
			return "Integer";
		}else if("BIGINT".equals(columnType)){
			return "Long";
		}else if(columnType.indexOf("INT") != -1){
			return "Integer";
		}else if("FLOAT".equals(columnType)){
			return "Float";
		}else if("DOUBLE".equals(columnType)){
			return "Double";
		}else{
			return "String";
		}
	}

}
