package pro.test.mappercreater;

import pro.test.mappercreater.util.FileCreateUtil;
import pro.test.mappercreater.util.NameUtil;
import pro.test.mappercreater.util.SqlTypeUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class BeanCreater extends Creater{
	
	BeanCreater(){
		configMap.put("basePackageName", "com.cmx");
		configMap.put("beanPath", "entity");
	}
	

	@SuppressWarnings("unchecked")
	public void beanCreate(Map<String, Object> tableinfo){
		
		Set<String> tablename = tableinfo.keySet();
		
		for(String s : tablename){
			//System.out.println(s);
			String str = s.toLowerCase();
			str = str.replace("t_", "");
			String newStr = str.substring(0, 1).toUpperCase()+str.substring(1);
			if(newStr.indexOf("_") != -1){
				String[] splitStr = newStr.split("_");
				newStr = "";
				for(int i = 0; i < splitStr.length; i++){
					newStr += splitStr[i].substring(0,1).toUpperCase()+splitStr[i].substring(1);
				}
			}
			if(s.endsWith("-key")){//主键
				
			}else{
				createBeanCode(newStr, (List<Map<String, Object>>)tableinfo.get(s));
			}
		}
		
	}
	
	//创建bean模板
	public String createBeanCode(String beanName, List<Map<String, Object>> infolist){
		StringBuffer sb = new StringBuffer();
		Map<String, String> columninfo = new HashMap<String, String>();
		String beanPackage = configMap.get("basePackageName").toString().replace("\\", ".")+"."+configMap.get("beanPath");
		sb.append("package " + beanPackage+";\n");
		sb.append("\nimport java.io.Serializable;\n");
		sb.append("import lombok.Data;\n");
		sb.append("\n@Data\npublic class " + beanName + " implements Serializable {\n");
			sb.append("\tprivate static final long serialVersionUID = 1L;\n");
			//生成属性
			for(int i = 0; i < infolist.size(); i++){
				Map<String, Object> map = infolist.get(i);
				String type = SqlTypeUtil.getJavaType(map.get("columnType").toString());
				String columnName = map.get("columnName").toString();
				columninfo.put(columnName, type);
				sb.append("\t/** " + map.get("remarks").toString() + "*/\n");
				sb.append("\tprivate "+ type + " " + NameUtil.lineToHump(columnName )+ ";\n");
			}
			sb.append("\n");
//			//生成属性的getter和setter
//			for(String s : columninfo.keySet()){
//				String type = columninfo.get(s);
//				String upname = s.substring(0,1).toUpperCase()+s.substring(1);
//				sb.append("\tpublic void set" + upname + "("+ type +" "+s+"){\n");
//					sb.append("\t\tthis."+ s + " = " + s + ";\n");
//				sb.append("\t}\n");
//				sb.append("\tpublic " + type + " get" + upname + "(){\n");
//					sb.append("\t\treturn " + s +";\n");
//				sb.append("\t}\n\n");
//			}
//			//生成toString
//			sb.append("\tpublic String toString(){\n");
//				sb.append("\t\treturn \"" + beanName + "[\"\n");
//				int configCount = columninfo.keySet().size();
//				for(String s : columninfo.keySet()){
//					configCount--;
//					if(configCount <= 0){
//						sb.append("\t\t+\"" + s + "=\" + " + s + " \n");
//					}else{
//						sb.append("\t\t+\"" + s + "=\" + " + s + " + \",\"\n");
//					}
//				}
//				sb.append("\t\t+\"]\";\n");
//			sb.append("\t}");
		sb.append("\n}");
		
		try{
			String beanPath = configMap.get("filePath")+"\\"+configMap.get("basePackageName").toString()+"\\"+configMap.get("beanPath");
			FileCreateUtil.createFile(beanName+".java",	beanPath, sb.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		//System.out.println(sb.toString());
		return sb.toString();
		
	}

}
