package com.cabletech.commons.config;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.core.io.support.PropertiesLoaderUtils;
/**
 * 属性文件读取基类
 * @author 张会军
 *
 */
public class PropertiesUtil implements java.io.Serializable{
	private static final long serialVersionUID = -565619404360749261L;
	private static Logger logger = Logger.getLogger(PropertiesUtil.class);
	
	public static Properties loadProperties(String fileName) throws IOException{
		return PropertiesLoaderUtils.loadAllProperties(fileName);
	}
	/**
	 * 判断属性文件是否被修改。
	 * @param fileName
	 * @return
	 */
	public static boolean isModFile(String fileName) {
		String path = ConfigPathUtil.getClassPathConfigFile();
		logger.info("--path:"+path);
		File file = new File(path+fileName);
        if(file.isFile()){
            long lastUpdateTime = file.lastModified();
            if(lastUpdateTime > GisConInfo.lastModified){
                logger.info("这个属性文件已经被修改.");
                GisConInfo.lastModified = lastUpdateTime;
                return true;
            }else{
            	 logger.info("这个属性文件没有被修改.");
                return false;
            }
        }else{
        	logger.info("在应用程序路径下没有找到该文件.");
            return false;
        }
	}
	
	public static void main(String []args){
		URL url = new PropertiesUtil().getClass().getResource("");
		String path1 = url.getPath();
		System.out.println("url:"+url +" ,path:"+path1);
		url = new PropertiesUtil().getClass().getResource("/");
		path1 = url.getPath();
		System.out.println("url:"+url +" ,path:"+path1);
		url = new PropertiesUtil().getClass() .getClassLoader().getResource("");
		path1 = url.getPath();
		System.out.println("url:"+url +" ,path:"+path1);
		url = ClassLoader.getSystemResource("");
		path1 = url.getPath();
		System.out.println("url:"+url +" ,path:"+path1);
		path1 =PropertiesUtil.class.getResource("").getPath();
		System.out.println("path:"+path1);
		
	}

	
	
}


