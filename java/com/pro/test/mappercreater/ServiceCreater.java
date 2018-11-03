package pro.test.mappercreater;

import pro.test.mappercreater.util.FileCreateUtil;
import pro.test.mappercreater.util.NameUtil;

import java.util.Map;
import java.util.Set;


public class ServiceCreater extends Creater{
	
	
	public ServiceCreater(){
		super();
		configMap.put("basePackageName", "com.cmx");
		configMap.put("servicePath", "service");
	}
	
	
	public void createService(Map<String, Object> tableinfo){
		
		Set<String> keySet = tableinfo.keySet();
		for(String s : keySet){
			if(s.endsWith("-key")){
				
			}else{
				createServiceInterface(NameUtil.getBeanName(s));
				createServiceImpl(NameUtil.getBeanName(s));
			}
		}
	}
	
	
	public String createServiceInterface(String beanName){
		String packageName = configMap.get("basePackageName").toString().replace("\\", ".") + "." + configMap.get("servicePath");
		String beanPath = configMap.get("basePackageName").toString().replace("\\", ".") + "." + configMap.get("beanPath").toString();
		String basePath = configMap.get("basePackageName").toString().replace("\\", ".") + "." + configMap.get("basePath").toString();
 		StringBuffer sb = new StringBuffer();
 		sb.append("package " + packageName + ";\n\n");
 		sb.append("import " + beanPath + "." + beanName + ";\n");
 		sb.append("import " + basePath + ".BaseService;\n\n" );
 		sb.append("public interface " + beanName + "Service extends BaseService<" + beanName + "> {\n\n");
 		
 		sb.append("\n}");
 		String filePath = configMap.get("filePath").toString() + "\\" + packageName.replace(".", "\\");
 		try{
 			FileCreateUtil.createFile(beanName+"Service.java", filePath, sb.toString());
 		}catch(Exception e){
 			e.printStackTrace();
 		}
 		return sb.toString();
	}
	
	//使用spring的Service声明
	public String createServiceImpl(String beanName){
		String packageName = configMap.get("basePackageName").toString().replace("\\", ".") + "." + configMap.get("servicePath");
		String beanPath = configMap.get("basePackageName").toString().replace("\\", ".") + "." + configMap.get("beanPath").toString();
		String basePath = configMap.get("basePackageName").toString().replace("\\", ".") + "." + configMap.get("basePath").toString();
		
		StringBuffer sb = new StringBuffer();
		sb.append("package " + packageName + ".impl;\n\n");
		
		sb.append("import org.springframework.stereotype.Service;\n");
		sb.append("import " + packageName + "." + beanName + "Service;\n");
		sb.append("import " + beanPath + "." + beanName + ";\n");
 		sb.append("import " + basePath + ".BaseServiceImpl;\n\n" );
 		
 		sb.append("@Service(\""+beanName.substring(0,1).toLowerCase()+beanName.substring(1) + "Service\")\n");
 		sb.append("public class " + beanName + "ServiceImpl extends BaseServiceImpl<" + beanName + "> implements " + beanName + "Service {\n\n");
 		sb.append("\n}");
		
 		String filePath = configMap.get("filePath").toString() + "\\" + packageName.replace(".", "\\") + "\\impl";
 		try{
 			FileCreateUtil.createFile(beanName+"ServiceImpl.java", filePath, sb.toString());
 		}catch(Exception e){
 			e.printStackTrace();
 		}
		return sb.toString();
	}
	
	
}
