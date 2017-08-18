package com.pro.test.mappercreater;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DaoCreate {
	
	private Map<String, Object> configMap;
	
	DaoCreate(){
		Map<String, Object> map = new HashMap<String, Object>();
		//可配置的参数：包名.（interfase 和 impl）是否使用basedao
		
		
	}
	

	public Map<String, Object> getConfigMap() {
		return configMap;
	}

	public void setConfigMap(Map<String, Object> configMap) {
		this.configMap = configMap;
	}
	
	
	public void daoCreate(Map<String, Object> tableinfo){
		Set<String> tablename = tableinfo.keySet();
		
		for(String s : tablename){
			s = s.toLowerCase();
			String newStr = s.substring(0, 1).toUpperCase()+s.substring(1);
			if(newStr.indexOf("_") != -1){
				String[] splitStr = newStr.split("_");
				newStr = "";
				for(int i = 0; i < splitStr.length; i++){
					newStr += splitStr[i].substring(0,1).toUpperCase()+splitStr[i].substring(1);
				}
			}
			
			
		}
	}
	
	
	public void daoCodeCreate(String beanName){
		
	}

}
