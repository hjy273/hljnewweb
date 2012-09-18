package com.cabletech.utils.GISkit;

import com.cabletech.commons.config.*;

import org.apache.log4j.Logger;

public class GISPath{
    private static Logger logger = Logger.getLogger(GISPath.class);
    public GISPath(){
    }


    /**
     * ȡ��gis��ַ
     * @param region String
     * @return String
     */
    public static String getGisPath( String regionid ) throws Exception{
        String mapURL = "";
        GisConInfo gisinfo = GisConInfo.newInstance();
        String mapSwitch = gisinfo.getGismapswitch();

        if( mapSwitch.equals( "1" ) ){ //������֧������

            String wholePath = gisinfo.getWholePathFromDB( regionid );
            String branchIp = getIpFromWholePathFromDB( wholePath );

//            if( ServerStatus.getStatus( branchIp ).equals( "1" ) ){ //������֧����������
//                mapURL = wholePath;
//            }
//            else{ //������֧�����������ã�ת�򱾵ط�����
                mapURL = gisinfo.getWholePath();
//            }
        }
        else{ //����ͳһ������
            mapURL = gisinfo.getWholePath();
        }

        return mapURL;
    }


    /**
     * ����ip��ַ
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
