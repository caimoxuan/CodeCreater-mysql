package com.pro.test.mappercreater;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;

import com.pro.test.mappercreater.util.FileCreateUtil;

public class MvcCreater extends Creater{
	
	MvcCreater(){
		super();
		configMap.put("basePackageName", "com.cmx");
	}
	
	
	public void createWEB_INFRoot(){
		String folderpath = configMap.get("filePath").toString().replace(".", "\\")+"\\"+"WEB-INF";
		
		try {
			FileCreateUtil.createFile("springmvc-servlet.xml", folderpath, createSpring_servlet());
			FileCreateUtil.createFile("web.xml", folderpath, createWeb_xml());
		} catch (IOException e) {
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public String createSpring_servlet(){
		String basePackageName = configMap.get("basePackageName").toString().replace("\\", ".");
		
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
			"<beans xmlns=\"http://www.springframework.org/schema/beans\"\n"+
			"\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:context=\"http://www.springframework.org/schema/context\"\n"+
			"\txmlns:tx=\"http://www.springframework.org/schema/tx\" xmlns:task=\"http://www.springframework.org/schema/task\"\n"+
			"\txmlns:aop=\"http://www.springframework.org/schema/aop\" xmlns:dubbo=\"http://code.alibabatech.com/schema/dubbo\"\n"+
			"\txmlns:mvc=\"http://www.springframework.org/schema/mvc\" xmlns:p=\"http://www.springframework.org/schema/p\"\n"+
			"\txsi:schemaLocation=\"http://www.springframework.org/schema/beans\n"+
		    "\thttp://www.springframework.org/schema/beans/spring-beans-4.0.xsd\n"+ 
		    "\thttp://www.springframework.org/schema/context\n"+  
		    "\thttp://www.springframework.org/schema/context/spring-context-4.0.xsd\n"+  
		    "\thttp://www.springframework.org/schema/tx\n"+
		    "\thttp://www.springframework.org/schema/tx/spring-tx-4.0.xsd\n"+
		    "\thttp://www.springframework.org/schema/task\n"+ 
		    "\thttp://www.springframework.org/schema/task/spring-task-4.0.xsd\n"+     
		    "\thttp://www.springframework.org/schema/aop\n"+ 
		    "\thttp://www.springframework.org/schema/aop/spring-aop-4.0.xsd\n"+
		    "\thttp://www.springframework.org/schema/mvc\n"+
		    "\thttp://www.springframework.org/schema/mvc/spring-mvc-4.0.xsd\n"+
		   "\t\">\n");
		sb.append("\n");
		sb.append("\t<!-- 自动扫描注解 -->\n"+
				  "\t<context:component-scan base-package = \""+ basePackageName +".controller\" />\n");
		sb.append("\n");
		sb.append("\t<mvc:annotation-driven></mvc:annotation-driven>");
		sb.append("\n");
		sb.append("\t<!--jsp 视图解析器  请在WEB-INF下创建jsp文件夹 否则请修改 perfix 配置 -->\n");
		sb.append("\t<bean id=\"jspViewResolver\" class=\"org.springframework.web.servlet.view.InternalResourceViewResolver\" p:order=\"2\" >\n");
		sb.append("\t\t<property name=\"prefix\" value=\"/WEB-INF/jsp/\" />\n"+
				  "\t\t<property name=\"suffix\" value=\".jsp\" />\n");
		sb.append("\t</bean>\n");
		sb.append("\n");
		
		sb.append("</beans>");
		
		
		return sb.toString();
	}
	
	public String createWeb_xml() throws Exception{
		String path = this.getClass().getResource("").getFile();
		System.out.println(path);
		StringBuffer sb = new StringBuffer();
		File f = new File(URLDecoder.decode(path, "utf-8")+"\\"+"web_xml.txt");
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String s;
		while((s = br.readLine())!= null){
			sb.append(s);
			sb.append("\n");
		}
		return sb.toString();
	}
}
