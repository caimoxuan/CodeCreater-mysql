package com.pro.test.mappercreater;

import com.pro.test.mappercreater.util.FileCreateUtil;


public class BaseCreater extends Creater{
	
	
	BaseCreater(){
		configMap.put("basePackageName", "com.cmx");
		configMap.put("basePath", "base");
	}
	

	public void createBaseDao(){
		
		try{
			String filePath = configMap.get("filePath")+"\\"+configMap.get("basePackageName").toString().replace(".", "\\")+"\\"+configMap.get("basePath");
			FileCreateUtil.createFile("BaseDao.java", filePath, baseDaoCodeCreater());
			FileCreateUtil.createFile("BaseService.java", filePath, baseServiceCodeCreater());
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
		sb.append("\tpublic void mofiy(T t) throws Exception;\n\n");
		sb.append("\tpublic Integer add(T t) throws Exception;\n\n");
		sb.append("}");
		
		return sb.toString();
	}
	
	//创建baseServiceImpl
	public String baseServiceImplCodeCreater(){
		StringBuffer sb = new StringBuffer();
		
		return sb.toString();
	}

}
