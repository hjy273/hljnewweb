package com.cabletech.utils.GISkit;

import com.cabletech.commons.config.*;

import org.apache.log4j.Logger;

public class GISPath{
    private static Logger logger = Logger.getLogger(GISPath.class);
    public GISPath(){
    }


    /**
     * 取得gis地址
     * @param region String
     * @return String
     */
    public static String getGisPath( String regionid ) throws Exception{
        String mapURL = "";
        GisConInfo gisinfo = GisConInfo.newInstance();
        String mapSwitch = gisinfo.getGismapswitch();

        if( mapSwitch.equals( "1" ) ){ //地区分支服务器

            String wholePath = gisinfo.getWholePathFromDB( regionid );
            String branchIp = getIpFromWholePathFromDB( wholePath );

//            if( ServerStatus.getStatus( branchIp ).equals( "1" ) ){ //地区分支服务器可用
//                mapURL = wholePath;
//            }
//            else{ //地区分支服务器不可用，转向本地服务器
                mapURL = gisinfo.getWholePath();
//            }
        }
        else{ //本地统一服务器
            mapURL = gisinfo.getWholePath();
        }

        return mapURL;
    }


    /**
     * 解析ip地址
     * @param WholePathFromDB String
     * @return String
     */
    public static String getIpFromWholePathFromDB( String WholePathFromDB ){
        String ip = "";
        int i = WholePathFromDB.indexOf( ":7001" );
        ip = WholePathFromDB.substring( 7, i );
        logger.info( "IP :" + ip );
        return ip;
    }
}
