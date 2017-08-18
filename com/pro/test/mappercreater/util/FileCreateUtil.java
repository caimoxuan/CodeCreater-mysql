package com.pro.test.mappercreater.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileCreateUtil {
	
	private static File f;
		
	private static FileWriter fw;
	
	public static void createFile(String fileName, String filePath, String content)throws IOException{
		
		f =new File(filePath);
		if(f.exists()){
			//System.out.println("路径存在");
		}else{
			if(f.mkdirs())
				System.out.println("创建路径成功");
			else
				System.out.println("创建失败");
		}
		
		
		f = new File(filePath+"\\"+fileName);
		fw = new FileWriter(f);
		
		fw.write(content);
		fw.flush();
		fw.close();
	}

}
