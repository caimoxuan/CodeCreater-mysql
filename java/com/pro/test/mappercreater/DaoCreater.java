package pro.test.mappercreater;

import pro.test.mappercreater.util.FileCreateUtil;
import pro.test.mappercreater.util.NameUtil;

import java.util.Map;
import java.util.Set;


public class DaoCreater extends Creater{
	
	
	DaoCreater(){
		super();
		configMap.put("basePackageName", "com.cmx");
		configMap.put("daoPath", "dao");
	}
	
	
	public void createDao(Map<String, Object> tableinfo){
		
		Set<String> keySet = tableinfo.keySet();
		for(String s : keySet){
			if(s.endsWith("-key")){
				
			}else{
				s = s.replace("t_", "");
				daoCodeCreater(NameUtil.getBeanName(s));
			}
		}
		
		
		
	}
	
	
	public String daoCodeCreater(String beanName){
		StringBuffer sb = new StringBuffer();
		String filePath = configMap.get("filePath").toString().replace(".", "\\")+"\\" + configMap.get("basePackageName").toString()+"\\"+configMap.get("daoPath");
		String packageName = configMap.get("basePackageName").toString().replace("\\", ".") + "." + configMap.get("daoPath");
		String baseDaoPackage = configMap.get("basePackageName").toString().replace("\\", ".") + "." + configMap.get("basePath");
		String beanPath = configMap.get("basePackageName").toString().replace("\\", ".") + "." + configMap.get("beanPath");
		String suffix = configMap.get("suffix").toString();
		sb.append("package " + packageName + ";\n\n");
		sb.append("import java.util.List;\n\n");
		//sb.append("import " + baseDaoPackage+".BaseDao;\n");
		sb.append("import " + beanPath + "." + beanName + ";\n\n");
		
		sb.append("public interface " + beanName+suffix + /**" extends BaseDao<" + beanName + ">*/ "{\n\n");

		sb.append("\tList<" + beanName + "> select("+ beanName + " "+ NameUtil.getLowCaseName(beanName) +");\n\n");
		sb.append("\tint update("+ beanName + " "+ NameUtil.getLowCaseName(beanName) + ");\n\n");
		sb.append("\tint insert("+ beanName + " "+ NameUtil.getLowCaseName(beanName) +");\n\n");
		sb.append("\tint delete("+ beanName + " "+ NameUtil.getLowCaseName(beanName) +");\n\n");
		sb.append("\t" + beanName + " getById(String id);\n");

		sb.append("}");
		try{
			FileCreateUtil.createFile(beanName+suffix+".java", filePath, sb.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
	
}
