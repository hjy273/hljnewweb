package com.cabletech.commons.config;

import java.io.File;

import org.apache.log4j.Logger;

import com.cabletech.utils.AppConstant;

public class MessageConfig{
    private static Logger logger = Logger.getLogger(MessageConfig.class);

    public static File[] getMessageFile(){
        File[] files = null;
        String path = ConfigPathUtil.getClassPathConfigFile();
        String strTemp = path +  AppConstant.MESSAGE_CONFIG;
        File msgCofig = new File( strTemp );
        if( msgCofig.exists() && msgCofig.isDirectory() ){
            files = msgCofig.listFiles();
            logger.info(""+ msgCofig.listFiles().length );
        }
        return files;
    }
}
