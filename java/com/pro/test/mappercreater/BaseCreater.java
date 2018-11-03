package pro.test.mappercreater;


import pro.test.mappercreater.util.FileCreateUtil;

public class BaseCreater extends Creater{
	
	
	BaseCreater(){
		configMap.put("basePackageName", "com.cmx");
		configMap.put("basePath", "base");
		configMap.put("createOverride", true);
	}
	

	public void createBaseDao(){
		
		try{
			String filePath = configMap.get("filePath")+"\\"+configMap.get("basePackageName").toString().replace(".", "\\")+"\\"+configMap.get("basePath");
			FileCreateUtil.createFile("BaseDao.java", filePath, baseDaoCodeCreater());
			FileCreateUtil.createFile("BaseService.java", filePath, baseServiceCodeCreater());
			FileCreateUtil.createFile("BaseServiceImpl.java", filePath, baseServiceImplCodeCreater());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	//生成Pageview 考虑是否生成mybtais拦截器
	public String createPageView(){
		StringBuffer sb = new StringBuffer();
		
		
		return sb.toString();
	}
	
	public String baseDaoCodeCreater(){
		String packageName = configMap.get("basePackageName").toString().replace("\\", ".")+"."+configMap.get("basePath");
		StringBuffer sb = new StringBuffer();
		sb.append("package "+ packageName + ";\n\n");
		sb.append("import java.util.List;\n\n");
		sb.append("public interface BaseDao<T> {\n\n");
		sb.append("\tpublic List<T> query(T t);\n\n");
		sb.append("\tpublic void delete(String id);\n\n");
		sb.append("\tpublic void modify(T t);\n\n");
		sb.append("\tpublic Integer add(T t);\n\n");
		sb.append("}");
		
		return sb.toString();
	}
	
	//创建baseService
	public String baseServiceCodeCreater(){
		String packageName = configMap.get("basePackageName").toString().replace("\\", ".")+"."+configMap.get("basePath");
		StringBuffer sb = new StringBuffer();
		sb.append("package " + packageName+";\n\n");
		sb.append("import java.util.List;\n\n");
		sb.append("public interface BaseService<T> {\n\n");
		sb.append("\tpublic List<T> query(T t) throws Exception;\n\n");
		sb.append("\tpublic void delete(String id) throws Exception;\n\n");
		sb.append("\tpublic void modify(T t) throws Exception;\n\n");
		sb.append("\tpublic Integer add(T t) throws Exception;\n\n");
		sb.append("}");
		
		return sb.toString();
	}
	
	//创建baseServiceImpl
	public String baseServiceImplCodeCreater(){
		String packageName = configMap.get("basePackageName").toString().replace("\\", ".")+"."+configMap.get("basePath");
		boolean createOverride = Boolean.parseBoolean(configMap.get("createOverride").toString());
		StringBuffer sb = new StringBuffer();
		sb.append("package " + packageName + ";\n\n");
		sb.append("import java.util.List;\n");
		sb.append("import org.springframework.beans.factory.annotation.Autowired;\n\n");
		sb.append("public class BaseServiceImpl<T> implements BaseService<T> {\n\n");
		sb.append("\t@Autowired\n");
		sb.append("\tprotected BaseDao<T> dao;\n\n");
		if(createOverride){
			sb.append("\t@Override\n");
		}
		sb.append("\tpublic Integer add(T t) throws Exception {\n\n");
		sb.append("\t\tInteger id = dao.add(t);\n");
		sb.append("\t\treturn id;\n");
		sb.append("\t}\n\n");
		
		if(createOverride){
			sb.append("\t@Override\n");
		}
		sb.append("\tpublic void delete(String id) throws Exception { \n\n");
		sb.append("\t\tdao.delete(id);\n");
		sb.append("\t}\n\n");
		
		if(createOverride){
			sb.append("\t@Override\n");
		}
		sb.append("\tpublic void modify(T t) throws Exception {\n\n");
		sb.append("\t\tdao.modify(t);\n");
		sb.append("\t}\n\n");
		
		if(createOverride){
			sb.append("\t@Override\n");
		}
		sb.append("\tpublic List<T> query(T t) throws Exception {\n\n");
		sb.append("\t\tList<T> rowRecord = dao.query(t);\n");
		sb.append("\t\treturn rowRecord;\n");
		sb.append("\t}\n");
		
		sb.append("\n}");
		
		
		
		return sb.toString();
	}
	/*
	public static void main(String[] args){
		BaseCreater  bc = new BaseCreater();
		String baseServiceImplCodeCreater = bc.baseServiceImplCodeCreater();
		System.out.println(baseServiceImplCodeCreater);
	}
	*/

}
