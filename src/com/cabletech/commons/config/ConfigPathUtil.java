package com.cabletech.commons.config;

import java.io.*;
import java.net.URL;
import java.util.*;
import org.apache.log4j.Logger;

public class ConfigPathUtil{
    private static Logger logger = Logger.getLogger(ConfigPathUtil.class);
    /**
     * 从classpath找到属性文件
     * @param configfileName String
     * @return String
     */
    public static String getClassPathConfigFile(){
        String path = ConfigPathUtil.class.getResource("").getPath();
		if (path.indexOf("WEB-INF") > 0) {
		    path = path.substring(0, path.indexOf("com/cabletech"));
		   } else {
			   logger.error("路径获取错误");
		   }
		logger.info("path:"+path);
        
        return path;
    }
    /**
     * 从classpath找到属性文件
     * @param configfileName String
     * @return String
     */
    public static String getClassPathConfigFile( String configfileName ){
        String configfile = null;
        String path = ConfigPathUtil.class.getResource("").getPath();
		if (path.indexOf("WEB-INF") > 0) {
		    path = path.substring(0, path.indexOf("com/cabletech"));
		   } else {
			   logger.error("路径获取错误");
		   }
        String filePath = path +  configfileName;
        logger.info("PATH:"+filePath);
        File msgCofig = new File( filePath );
        if( msgCofig.exists() && msgCofig.isFile() ){
            configfile = msgCofig.getPath();
        }
        return configfile;
    }


    /**
     * 从classpath找到Log4j属性文件
     * @param configfileName String
     * @return String
     */
//    public static String getLog4jPathConfigFile(){
//        String configfile = null;
//        try{
//            StringTokenizer strToken = new StringTokenizer( System.getProperty(
//                                       "java.class.path" ), File.pathSeparator );
//            while( strToken.hasMoreTokens() ){
//                String strFilepath = strToken.nextToken();
//                //logger.info(strFilepath);
//                File file = new File( strFilepath );
//                if( file.isDirectory() ){
//                    String strTemp = file.getPath() + file.separator +
//                                     "cabletech_web_log4j.properties";
//                    File msgCofig = new File( strTemp );
//                    if( msgCofig.exists() && msgCofig.isFile() ){
//                        configfile = msgCofig.getPath();
//                        break;
//                    }
//                }
//            }
//        }
//        catch( Exception ex ){
//            ex.printStackTrace();
//        }
//        return configfile;
//
//    }

}
