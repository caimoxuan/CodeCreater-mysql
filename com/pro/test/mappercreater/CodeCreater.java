package com.pro.test.mappercreater;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.pro.test.mappercreater.util.FileCreateUtil;


/**
 * 
 * @author cmx
 * @date 2017-8-10
 *
 */
public class CodeCreater {
	
	String[] str;
	
	String userRoot;
	
	Map<String, Object> configMap = new HashMap<String, Object>();

	CodeCreater(){
		userRoot = System.getProperty("user.dir");
	}
	

	/**
	 * 初始化传参 参数存放再一个配置文件中：properties.txt
	 */
	public void initConfigMap(String filePath){
		Properties p = new Properties();
		String classpath = Class.class.getClass().getResource("/").getPath();
		try{
			//p.load(new FileInputStream(userRoot+"\\"+filePath));
			p.load(new FileInputStream(URLDecoder.decode(classpath, "utf-8")+"com\\pro\\test\\mappercreater\\properties.txt"));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		configMap.put("basePackageName", p.get("basePackageName").toString().replace(".", "\\"));
		configMap.put("beanPath", p.get("beanPath").toString().replace(".", "\\"));
		configMap.put("mapperPath", p.get("mapperPath").toString().replace(".", "\\"));
		configMap.put("daoPath", p.get("daoPath").toString().replace(".", "\\"));
		configMap.put("servicePath", p.get("servicePath").toString().replace(".", "\\"));
		configMap.put("serviceImplPath", p.get("serviceImplPath").toString().replace(".", "\\"));
	}
	
	
	public static void main(String[] args){
		
		CodeCreater cc = new CodeCreater();
		cc.initConfigMap("src\\com\\pro\\test\\mappercreater\\properties.txt");
//		try {
//			Map<String, Object> tableinfo = cc.getTableName(MySqlConnection.getConnection());
//			
//			//System.out.println(tableinfo.toString());
//			BeanCreater bc = new BeanCreater();
//			//bc.beanCreate(tableinfo);
//			MapperCreater mc = new MapperCreater();
//			mc.mapperCreater(tableinfo);
//			MySqlConnection.closeConnection();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
	}
	
	//获得table和view的名称，再获取其中的信息，保存成一个map之后再做处理
	public Map<String, Object> getTableName(Connection connection)throws SQLException{
		Map<String, Object> tableinfo = new HashMap<String, Object>();
		
		
		DatabaseMetaData dbMetData = connection.getMetaData();
		ResultSet rs = dbMetData.getTables(null, null, null, new String[]{"TABLE", "VIEW"});
		
		while(rs.next()){
			List<Map<String, Object>> tableinfolist = new ArrayList<Map<String, Object>>();
			if("TABLE".equals(rs.getString(4))||"VIEW".equals(rs.getString(4))){
				//根据表名提取表信息：  
				String tablename = rs.getString(3);
	            ResultSet colRet = dbMetData.getColumns(null, "%", tablename, "%");  
	            while (colRet.next()) {
	                String columnName = colRet.getString("COLUMN_NAME");
	                String columnType = colRet.getString("TYPE_NAME");
	                int dataSize = colRet.getInt("COLUMN_SIZE");
	                int nullAble = colRet.getInt("NULLABLE");
	                Map<String, Object> infomap = new HashMap<String, Object>();
	                infomap.put("columnName", columnName);
	                infomap.put("columnType", columnType);
	                infomap.put("dataSize", dataSize);
	                infomap.put("nullAble", nullAble);
	                tableinfolist.add(infomap);
	            } 
	            tableinfo.put(tablename, tableinfolist);
			}
		}
		return tableinfo;	
	}
	

}
