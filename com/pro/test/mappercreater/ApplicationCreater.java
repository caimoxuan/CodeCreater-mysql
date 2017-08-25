package com.pro.test.mappercreater;

import java.io.IOException;
import java.util.Map;

import com.pro.test.mappercreater.util.FileCreateUtil;

public class ApplicationCreater extends Creater{
	
	
	
	private Map<String,Object> jdbcMap;
	
	public void setJdbcMap(Map<String, Object> map){
		this.jdbcMap = map;
	}
	
	public ApplicationCreater(){
		super();
		configMap.put("transactionManagerType", "pointCut");
		configMap.put("dataSourcePool", "druid");
	}
	
	public String applicationCreater(){
		String basePackageName = configMap.get("basePackageName").toString().replace("\\", ".");
		String filePath = configMap.get("filePath").toString().replace(".", "\\");
		
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<beans xmlns=\"http://www.springframework.org/schema/beans\"\n"+
	         "\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""+
	             "\txmlns:context=\"http://www.springframework.org/schema/context\"\n"+
	             "\txmlns:tx=\"http://www.springframework.org/schema/tx\"\n"+
	             "\txmlns:aop=\"http://www.springframework.org/schema/aop\"\n"+
	             "\txsi:schemaLocation=\"\n"+
	               "\t\thttp://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd\n"+
	               "\t\thttp://www.springframework.org/schema/context\n"+
	               "\t\thttp://www.springframework.org/schema/context/spring-context.xsd\n"+
	               "\t\thttp://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-2.0.xsd\n"+
	               "\t\thttp://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-2.0.xsd\n"+
	               "\t\t\"\n"+
	               "\t\t>\n\n");
		
		sb.append("\n");
		sb.append("\t<context:component-scan base-package=\""+basePackageName+".*\" />");
		sb.append("\n");
		sb.append(getJDBCProperties());
		sb.append(getDataResource());
		sb.append("\t<!-- mybtis-spring -->\n"+
				"\t<bean id = \"sqlSessionFactory\" class = \"org.mybatis.spring.SqlSessionFactoryBean\">\n"+
				"\t\t<property name = \"dataSource\" ref = \"dataSource\" />\n"+
				"\t\t<property name = \"configLocation\" value = \"classpath:mybatis.xml\" />\n"+
				"\t\t<property name = \"mapperLocations\" value = \"classpath:com/cmx/test/mapper/*.xml\" />\n"+
				"\t</bean>\n"+
				"\t<!-- 自动实现daoimpl -->\n"+
				"\t<bean id=\"zcMapperScannerConfigurer\" name=\"zcMapperScannerConfigurer\" class = \"org.mybatis.spring.mapper.MapperScannerConfigurer\">\n"+
				"\t\t<property name=\"basePackage\" value = \"com.cmx.test.dao\"></property>\n"+
				"\t\t<property name=\"sqlSessionFactoryBeanName\" value = \"sqlSessionFactory\"></property>\n"+
				"\t</bean>\n");
		sb.append(getTransactionManager());
		sb.append("\n");
		sb.append("</beans>");
		
		try{
			FileCreateUtil.createFile("applicationContext.xml", filePath, sb.toString());
			createProperties(filePath);
			createMybatisXml(filePath);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return sb.toString();
	}
	
	
	
	public String getDataResource(){
		String poolName = configMap.get("dataSourcePool").toString(); 
		
		StringBuffer sb = new StringBuffer();
		if("druid".equals(poolName)){
			sb.append("\n");
			sb.append("\t<bean id = \"dataSource\" class = \"com.alibaba.druid.pool.DruidDataSource\" init-method=\"init\" destroy-method=\"close\">\n"+
			"\t\t<!-- 基本连接配置 -->\n"+
			"\t\t<property name = \"url\" value = \"${url}\" />\n"+
			"\t\t<property name = \"username\" value = \"${username}\" />\n"+
			"\t\t<property name = \"password\" value = \"${password}\" />\n"+
			
			"\t\t<!-- 连接配置 -->\n"+
			"\t\t<property name = \"initialSize\" value = \"${druid.initialSize}\" />\n"+
			"\t\t<!-- 最小空闲连接数 -->\n"+
			"\t\t<property name = \"minIdle\" value = \"${druid.minIdle}\" />\n"+
			"\t\t<property name = \"maxActive\" value = \"${druid.maxActive}\" />\n"+
			
			"\t\t<!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->\n"+ 
			"\t\t<property name = \"timeBetweenEvictionRunsMillis\" value =\"${druid.timeBetweenEvictionRunsMillis}\" />\n"+ 
			"\t\t<!-- 配置一个连接在池中最小生存的时间，单位是毫秒 -->\n"+  
			"\t\t<property name = \"minEvictableIdleTimeMillis\" value =\"${druid.minEvictableIdleTimeMillis}\" />\n"+    
			"\t\t<property name = \"validationQuery\" value = \"${druid.validationQuery}\" />\n"+    
			"\t\t<property name = \"testWhileIdle\" value = \"${druid.testWhileIdle}\" />\n"+    
			"\t\t<property name = \"testOnBorrow\" value = \"${druid.testOnBorrow}\" />\n"+    
			"\t\t<property name = \"testOnReturn\" value = \"${druid.testOnReturn}\" />\n"+    
			"\t\t<property name = \"maxOpenPreparedStatements\" value =\"${druid.maxOpenPreparedStatements}\" />\n"+  
			"\t\t<!-- 打开 removeAbandoned 功能 -->\n"+  
			"\t\t<property name = \"removeAbandoned\" value = \"${druid.removeAbandoned}\" />\n"+  
			"\t\t<!-- 1800 秒，也就是 30 分钟 -->\n"+  
			"\t\t<property name = \"removeAbandonedTimeout\" value =\"${druid.removeAbandonedTimeout}\" />\n"+  
			"\t\t<!-- 关闭 abanded 连接时输出错误日志 -->\n"+     
			"\t\t<property name = \"logAbandoned\" value = \"${druid.logAbandoned}\" />\n"+ 
			"\t\t<!-- 默认使用wall和stat filter (默认关闭batch操作)  开启时 配置wall 的 multiStatementAllow为true-->\n"+
			"\t\t<property name = \"filters\" value = \"${druid.filters}\" />\n"+
			"\t</bean>\n");
			sb.append("\n");
		}else if("jdbc".equals(poolName)){
			
		}
		
		
		return sb.toString();
	}
	
	
	public String getJDBCProperties(){
		
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb.append("\t<bean class = \"org.springframework.beans.factory.config.PropertyPlaceholderConfigurer\">\n"+
   		"\t\t<property name = \"locations\">\n"+
			"\t\t\t<list>\n"+
				"\t\t\t\t<value>\n"+
					"\t\t\t\t\tclasspath:jdbc.properties\n"+
				"\t\t\t\t</value>\n"+
			"\t\t\t</list>\n"+
		"\t\t</property>\n"+
		"\t</bean>\n");
		sb.append("\n");
		
		return sb.toString();
	}
	
	
	public String getTransactionManager(){
		String transactionManagerType = configMap.get("transactionManagerType").toString();
		String pointCut = configMap.get("basePackageName").toString().replace("\\", ".") + "." + configMap.get("servicePath").toString().replace("\\", ".")+".impl";
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb.append("\t<bean id = \"transactionManager\" class = \"org.springframework.jdbc.datasource.DataSourceTransactionManager\">\n"+
   		"\t\t<property name = \"dataSource\" ref = \"dataSource\" />\n"+
        "\t</bean>\n");
		sb.append("\n");
		
		if("annotation".equals(transactionManagerType)){
			sb.append("\n");
			sb.append("\t<tx:annotation-driven transaction-manager = \"transactionManager\" />\n");
			sb.append("\n");
		}else if("pointcut".equals(transactionManagerType)){
			sb.append("\n");
			sb.append("\t<aop:config>\n"+
           		"\t\t<aop:pointcut expression=\"execution(public * "+ pointCut +".*Impl.*(..))\" id = \"pointcut\"/>\n"+
           		"\t\t<aop:advisor advice-ref=\"txAdvice\" pointcut-ref=\"pointcut\"/>\n"+
           "\t</aop:config>\n"+
           "\t<tx:advice id = \"txAdvice\" transaction-manager = \"transactionManager\" >\n"+
           		"\t\t<tx:attributes>\n"+
           			"\t\t\t<tx:method name=\"delete*\" propagation = \"REQUIRED\" rollback-for = \"java.lan.Exception\"/>\n"+
           			"\t\t\t<tx:method name=\"update*\" propagation = \"REQUIRED\" rollback-for = \"java.lan.Exception\"/>\n"+
           			"\t\t\t<tx:method name=\"insert*\" propagation = \"REQUIRED\" rollback-for = \"java.lan.Exception\"/>\n"+
           			"\t\t\t<tx:method name=\"select*\" propagation = \"SUPPORTS\"  read-only = \"true\"/>\n"+
           			"\t\t\t<tx:method name=\"get*\" propagation = \"SUPPORTS\" read-only = \"true\"/>\n"+
           		"\t\t</tx:attributes>\n"+
           "\t</tx:advice>\n");
			sb.append("\n");
		}
		
		
		return sb.toString();
	}
	
	public String createProperties(String filePath)throws Exception{
		String dataSourcePool = configMap.get("dataSourcePool").toString();
		String username = jdbcMap.get("username").toString();
		String password = jdbcMap.get("password").toString();
		String jdbcDriver = jdbcMap.get("jdbcDriver").toString();
		String url = jdbcMap.get("url").toString();
		
		StringBuffer sb = new StringBuffer();
		sb.append("username="+username+"\n");
		sb.append("password="+password+"\n");
		sb.append("jdbcDriver="+jdbcDriver+"\n");
		sb.append("url="+url+"\n");
		sb.append("\n");
		
		if("druid".equals(dataSourcePool)){
			sb.append("#druid-config\n");
			sb.append("druid.initialSize=20\n"+
						"druid.minIdle=20\n"+
						"druid.maxActive=100\n"+
						"druid.maxWait=60000\n"+
						"druid.timeBetweenEvictionRunsMillis=60000\n"+
						"druid.minEvictableIdleTimeMillis=300000\n"+
						"druid.validationQuery=SELECT 'x'\n"+
						"druid.testWhileIdle=true\n"+
						"druid.testOnBorrow=false\n"+
						"druid.testOnReturn=false\n"+
						"druid.poolPreparedStatements=false\n"+
						"druid.maxPoolPreparedStatementPerConnectionSize=20\n"+
						"druid.filters=wall,stat\n"+
						"druid.maxOpenPreparedStatements=20\n"+  
						"druid.removeAbandoned=true\n"+
						"druid.removeAbandonedTimeout=1800\n"+  
						"druid.logAbandoned=true");
		}else if("jdbc".equals(dataSourcePool)){
			
		}
		FileCreateUtil.createFile("jdbc.properties", filePath, sb.toString());
		return sb.toString();
	}
	
	public void createMybatisXml(String filePath)throws Exception{
		String packageName = configMap.get("basePackageName").toString().replace("\\", ".") + "." + configMap.get("beanPath");
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
					"<!DOCTYPE configuration PUBLIC \"-//mybatis.org//DTD Config 3.0//EN\"\n"+
						"\t\"http://www.mybatis.org/dtd/mybatis-3-config.dtd\">\n"+
					"<configuration>\n"+
					  "\t<typeAliases>\n"+
							"\t\t<package name=\"" + packageName + "\" />\n"+
					  "\t</typeAliases>\n"+
					"</configuration>\n");
		FileCreateUtil.createFile("mybatis.xml", filePath, sb.toString());
	}

}
