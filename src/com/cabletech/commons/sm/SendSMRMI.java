package com.cabletech.commons.sm;

import java.util.*;

import org.apache.log4j.*;
import com.cabletech.baseinfo.beans.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.sm.rmi.client.*;
import com.cabletech.sm.rmi.server.*;
import com.cabletech.commons.hb.*;
import java.sql.*;
import com.cabletech.commons.config.*;
import com.cabletech.baseinfo.beans.*;

public class SendSMRMI{
	
	public static final String MSG_NOTE = "[鑫干线巡检系统]";
    private static Logger logger = Logger.getLogger( SendSMRMI.class );
    private String rmiUrl = "";
    public void setRmiUrl( String rmiUrl ){
        this.rmiUrl = rmiUrl;
    }

    public String getRmiUrl(){
        return rmiUrl;
    }


    //调用短信服务上的RMI服务，IP地址为短信服务器的IP地址
    public SendSMRMI(){
        GisConInfo config = GisConInfo.newInstance();
        rmiUrl = config.getRmiUrl();
    }


    public static void main( String[] args ){
        sendNormalMessage( "0000", "13431640075", "测试 :审批", "00" );
    }


    private String sendSMI( HashMap params, String methodName, String result ){
        //判断设备的型号，如果不是CT－1200和CT－6100 则一律置成NV－2000
        String temp = ( String )params.get( "DeviceModel" );
        if( temp != null && !temp.equals( "" ) ){
            temp = temp.substring( 0, 2 ).toUpperCase().substring( 0, 2 );
            if( !temp.equals( "CT" ) ){
                params.remove( "DeviceModel" );
                params.put( "DeviceModel", "NV-2000" );
            }
        }
        else{
            params.remove( "DeviceModel" );
            params.put( "DeviceModel", "NV-2000" );
        }

        RMIRequest rmiRequest = new RMIRequest();
        rmiRequest.setName( methodName );
        rmiRequest.setParameter( params );
        RmiServerInterface rimObj = null;
        try{
            rimObj = ( RmiServerInterface )RmiClient.getRmiObject(
                     rmiUrl );
            //执行请求
            RMIResponse rmiResponse = rimObj.execute( rmiRequest );
            if( rmiResponse.isResult() ){
                HashMap hm = rmiResponse.getParameter();
                String strResult = "";
                if( result != null && !result.equals( "" ) ){
                    strResult = ( String )hm.get( result );
                }
                //TODO:发送成功
                logger.info( "信息发送成功" );
                if( strResult != null && !strResult.equals( "" ) ){
                    return strResult;
                }
                else{
                    return "";
                }
            }
            else{
                //TODO:发送失败
                logger.error( "信息发送失败" );
                return "";
            }
        }
        catch( Exception ex ){
            System.out.println( "发送短信出现异常：" + ex );
            return "";
        }
    }


    //初始化设备
    public void SendInitDeviceMsg( String UserID, String SimID, String DeviceModel,
        String DeviceID, String Password, String SpCode ){
        HashMap params = new HashMap();
        params.put( "UserID", UserID );
        params.put( "SimID", SimID );
        params.put( "DeviceModel", DeviceModel );
        params.put( "DeviceID", DeviceID );
        params.put( "Password", Password );
        params.put( "SpCode", SpCode );
        this.sendSMI( params, "SendInitDeviceMsg", "" );
        return;
    }


    //发送定位信息
    public void SendLocalizeMsg( String UserID, String SimID, String DeviceModel,
        String DeviceID, String Password, String Seconds, String Count ){
        HashMap params = new HashMap();
        params.put( "UserID", UserID );
        params.put( "SimID", SimID );
        params.put( "DeviceModel", DeviceModel );
        params.put( "DeviceID", DeviceID );
        params.put( "Password", Password );
        params.put( "Seconds", Seconds );
        params.put( "Count", Count );
        this.sendSMI( params, "SendLocalizeMsg", "" );
        return;
    }



    //删除所有隐患菜单
       public void ClearAccident( String UserID, String SimID, String DeviceModel,
           String DeviceID, String Password ){
           HashMap params = new HashMap();
           params.put( "UserID", UserID );
           params.put( "SimID", SimID );
           params.put( "DeviceModel", DeviceModel );
           params.put( "DeviceID", DeviceID );
           params.put( "Password", Password );
           this.sendSMI( params, "ClearAccident", "" );
           return;
    }

    //增加隐患类型
    public void AddAccidentType( String UserID, String SimID, String DeviceModel,
        String DeviceID, String Password, String AccidentTypeID,
        String AccidentTypeName ){
        HashMap params = new HashMap();
        params.put( "UserID", UserID );
        params.put( "SimID", SimID );
        params.put( "DeviceModel", DeviceModel );
        params.put( "DeviceID", DeviceID );
        params.put( "Password", Password );
        params.put( "AccidentTypeID", AccidentTypeID );
        params.put( "AccidentTypeName", AccidentTypeName );
        this.sendSMI( params, "AddAccidentType", "" );
        return;
    }


    //增加某个类型的隐患
    public void AddAccident( String UserID, String SimID, String DeviceModel,
        String DeviceID, String Password, String AccidentTypeID,
        String AccidentID, String AccidentName ){
        HashMap params = new HashMap();
        params.put( "UserID", UserID );
        params.put( "SimID", SimID );
        params.put( "DeviceModel", DeviceModel );
        params.put( "DeviceID", DeviceID );
        params.put( "Password", Password );
        params.put( "AccidentTypeID", AccidentTypeID );
        params.put( "AccidentID", AccidentID );
        params.put( "AccidentName", AccidentName );
        this.sendSMI( params, "AddAccident", "" );
        return;
    }


    //发出让手持设备反馈电压的指令
    public void QueryDeviceVoltage( String UserID, String SimID, String DeviceModel,
        String DeviceID, String Password ){
        HashMap params = new HashMap();
        params.put( "UserID", UserID );
        params.put( "SimID", SimID );
        params.put( "DeviceModel", DeviceModel );
        params.put( "DeviceID", DeviceID );
        params.put( "Password", Password );
        this.sendSMI( params, "QueryDeviceVoltage", "" );
        return;

    }


    /**
     * setDevicePhoneBook
     *
     * @param UserID String
     * @param simID String
     * @param deviceModel String
     * @param deviceID String
     * @param password String
     * @param phoneList String
     * @param writeMode String
     */
    public String setDevicePhoneBook( String UserID,
        String simID,
        String deviceModel,
        String deviceID,
        String password,
        String phoneList,
        String writeMode ){
        SendSMRMI srmi = new SendSMRMI();
        String srmiUrl = srmi.getRmiUrl();

        HashMap params = new HashMap();
        params.put( "UserID", UserID );
        params.put( "SimID", simID );
        params.put( "DeviceModel", deviceModel );
        params.put( "DeviceID", deviceID );
        params.put( "Password", password );
        params.put( "PhoneList", phoneList );
        params.put( "WriteMode", writeMode );
        return this.sendSMI( params, "SetDevicePhonebook", "WrietCount" );
    }


    //往手持设备发送调度信息
    public void SendAttemperMsg( String UserID, String SimID, String DeviceModel,
        String DeviceID, String Password, String NeedReply, String Message,
        String BusinessCode ){
        HashMap params = new HashMap();
        params.put( "UserID", UserID );
        params.put( "SimID", SimID );
        params.put( "DeviceModel", DeviceModel );
        params.put( "DeviceID", DeviceID );
        params.put( "Password", Password );
        params.put( "NeedReply", NeedReply );
        params.put( "BusinessCode", BusinessCode );
        params.put( "Message", Message );
        this.sendSMI( params, "SendAttemperMsg", "" );
        return;
    }


    //获得指定区域的所有新设备的信息
    public List getSimIfo( UserInfo userinfo ){
        ResultSet rst;
        String sql = " SELECT   t.simnumber, t.terminalmodel, t.phonebook, t.PASSWORD, "
                     + "          SUBSTR (t.terminalid, "
                     + "                  LENGTH (t.terminalid) - 7, "
                     + "                  LENGTH (t.terminalid) "
                     + "                 ) terminalid "
                     + "     FROM terminalinfo t "
                     + "    WHERE t.regionid ='" + userinfo.getRegionID() + "'"
                     + "      AND (t.state != '1' or t.STATE is null)"
                     + "      AND UPPER (SUBSTR (t.terminalmodel, 0, 2)) = 'CT'";
        List list = new Vector();
        try{
            QueryUtil qu = new QueryUtil();
            rst = qu.executeQuery( sql );
            while( rst != null && rst.next() ){
                TerminalBean bean = new TerminalBean();
                bean.setSimNumber( rst.getString( "simnumber" ) );
                bean.setTerminalModel(rst.getString("terminalmodel" ));
                bean.setTerminalID( rst.getString( "terminalid" ) );
                bean.setPassword( rst.getString( "password" ) );
                list.add( bean );
            }
            if( rst != null ){
                rst.close();
            }
            return list;
        }
        catch( Exception ex ){
            logger.warn( "获得指定区域的所有新设备的信息:" + ex.getMessage() );
            return null;
        }
    }


    //获得指定设备的信息
    public List getSimIfo( String[] simnumber ){
        ResultSet rst;
        StringBuffer sb = new StringBuffer();
        for( int i = 0; i < simnumber.length; i++ ){
            if( i != simnumber.length - 1 ){
                sb.append("'"+ simnumber[i] + "'," );
            }
            else{
                sb.append("'" + simnumber[i] + "'" );
            }
        }

        String sql = " SELECT   t.simnumber, t.terminalmodel, t.phonebook, t.PASSWORD, "
                     + "          SUBSTR (t.terminalid, "
                     + "                  LENGTH (t.terminalid) - 7, "
                     + "                  LENGTH (t.terminalid) "
                     + "                 ) terminalid "
                     + "     FROM terminalinfo t "
                     + "    WHERE t.simnumber in(" + sb.toString() + ")";
        logger.info("SQL:" + sql);
        List list = new Vector();
        try{
            QueryUtil qu = new QueryUtil();
            rst = qu.executeQuery( sql );
            while( rst != null && rst.next() ){
                TerminalBean bean = new TerminalBean();
                bean.setSimNumber( rst.getString( "simnumber" ) );
                bean.setTerminalModel( "terminalmodel" );
                bean.setTerminalID( rst.getString( "terminalid" ) );
                bean.setPassword( rst.getString( "password" ) );
                list.add( bean );
            }
            if( rst != null ){
                rst.close();
            }
            return list;
        }
        catch( Exception ex ){
            logger.warn( "获得指定区域的所有新设备的信息:" + ex.getMessage() );
            return null;
        }
    }

    //删除一个区域内所有设备的隐患菜单
    public void deleTRoubleMenu( UserInfo userinfo ){
        List list = this.getSimIfo( userinfo );
        if( list == null ){
            return;
        }
        for( int i = 0; i < list.size(); i++ ){
            TerminalBean bean = ( TerminalBean )list.get( i );
            ClearAccident( userinfo.getUserID(), bean.getSimNumber(), bean.getTerminalModel(), bean.getTerminalID(),
                bean.getPassword() );
        }
        return;
    }


    ////初始化设备
    public static boolean SendInitDeviceMsg( UserInfo userinfo, String terminalid ){
        SendSMRMI sm = new SendSMRMI();
        String sql = "select t.terminalid, t.terminaltype, t.terminalmodel, t.simnumber, t.password "
                     + " from terminalinfo t "
                     + " where t.terminalid='" + terminalid + "' ";
        logger.info( "SQL:" + sql );
        try{
            QueryUtil qu = new QueryUtil();
            ResultSet rst = qu.executeQuery( sql );
            String temp = "";
            if( rst != null && rst.next() ){
                temp = rst.getString( "terminalid" );
                if( rst.getString( "terminalmodel" ).substring( 0, 2 ).toUpperCase().equals( "CT" ) ){
                    temp = temp.substring( temp.length() - 8, temp.length() );
                }
                else{
                    temp = temp.substring( temp.length() - 4, temp.length() );
                }

                sm.SendInitDeviceMsg( userinfo.getUserID(),
                    rst.getString( "simnumber" ),
                    rst.getString( "terminalmodel" ),
                    temp,
                    rst.getString( "password" ),
                    "" );
            }
            if( rst != null ){
                rst.close();
            }
            return true;
        }
        catch( Exception ex ){
            logger.warn( "初始化设备异常:" + ex.getMessage() );
            ex.printStackTrace();
            return false;
        }
    }


    //定位信息
    public static boolean SendLocalizeMsg( UserInfo userinfo, String terminalid, String Seconds, String Count ){
        SendSMRMI sm = new SendSMRMI();
        String sql = "select t.TERMINALID,t.TERMINALTYPE,t.TERMINALMODEL,t.SIMNUMBER,t.PASSWORD "
                     + " from terminalinfo t "
                     + " where t.TERMINALID='" + terminalid + "' ";
        logger.info( "=======定位信息的SQL:" + sql );
        try{
            QueryUtil qu = new QueryUtil();
            ResultSet rst = qu.executeQuery( sql );
            String temp = "";
            if( rst != null && rst.next() ){
                temp = rst.getString( "terminalid" );
                if( rst.getString( "terminalmodel" ).substring( 0, 2 ).toUpperCase().equals( "CT" ) ){
                    temp = temp.substring( temp.length() - 8, temp.length() );
                }
                else{
                    temp = temp.substring( temp.length() - 4, temp.length() );
                }
                sm.SendLocalizeMsg( userinfo.getUserID(),
                    rst.getString( "simnumber" ),
                    rst.getString( "terminalmodel" ),
                    temp,
                    rst.getString( "password" ),
                    Seconds,
                    Count );
            }
            if( rst != null ){
                rst.close();
            }
            return true;
        }
        catch( Exception ex ){
            logger.warn( "定位信息异常:" + ex.getMessage() );
            return false;
        }
    }

    //更新设备的隐患菜单
    public static void updateTroubleMenu( TroubleTypeBean[] bean, UserInfo userInfo ){
        SendSMRMI sm = new SendSMRMI();

        ArrayList acciddentType = new ArrayList();
        ArrayList acciddent = new ArrayList();
        StringBuffer simBuf = new StringBuffer();
        StringBuffer modBuf = new StringBuffer();
        StringBuffer passBuf = new StringBuffer();
        StringBuffer noBuf = new StringBuffer();

        List list = sm.getSimIfo( userInfo ); //设备
        for(int i=0;i<bean.length;i++){
            String[] temp = new String[2];
            temp[0] = bean[i].getCode();
            temp[1] = bean[i].getTypename();
            acciddentType.add(temp);
            for(int j=0;j<bean[i].getTroublecode().size();j++){
                String[] atemp = new String[3];
                atemp[0] = bean[i].getCode();
                atemp[1] = ((TroubleCodeBean)bean[i].getTroublecode().get(j)).getTroublecode();
                atemp[2] = ((TroubleCodeBean)bean[i].getTroublecode().get(j)).getTroublename();
                acciddent.add(atemp);
            }
        }
        for(int i=0;i<list.size();i++){
            simBuf.append(((TerminalBean)list.get(i)).getSimNumber());
            modBuf.append(((TerminalBean)list.get(i)).getTerminalModel());
            passBuf.append(((TerminalBean)list.get(i)).getPassword());
            noBuf.append(((TerminalBean)list.get(i)).getTerminalID());
            if(i != list.size()-1){
                simBuf.append( "," );
                modBuf.append( "," );
                passBuf.append( "," );
                noBuf.append( "," );
            }
        }
        sm.clearAndAddAccidents(userInfo.getUserID(),simBuf.toString(),modBuf.toString(),
                            noBuf.toString(),passBuf.toString(),acciddentType,acciddent);

        return;
    }
    //更新设备的隐患菜单--一部分的手机
 public static void updateTroubleMenuForSome( TroubleTypeBean[] bean, UserInfo userInfo,String[] simnumber ){
     SendSMRMI sm = new SendSMRMI();
     ArrayList acciddentType = new ArrayList();
     ArrayList acciddent = new ArrayList();
     StringBuffer simBuf = new StringBuffer();
     StringBuffer modBuf = new StringBuffer();
     StringBuffer passBuf = new StringBuffer();
     StringBuffer noBuf = new StringBuffer();
     List list = sm.getSimIfo( simnumber ); //设备
     for(int i=0;i<bean.length;i++){
         String[] temp = new String[2];
         temp[0] = bean[i].getCode();
         temp[1] = bean[i].getTypename();
         acciddentType.add(temp);
         for(int j=0;j<bean[i].getTroublecode().size();j++){
             String[] atemp = new String[3];
             atemp[0] = bean[i].getCode();
             atemp[1] = ((TroubleCodeBean)bean[i].getTroublecode().get(j)).getTroublecode();
             atemp[2] = ((TroubleCodeBean)bean[i].getTroublecode().get(j)).getTroublename();
             acciddent.add(atemp);
         }
     }
     for(int i=0;i<list.size();i++){
         simBuf.append(((TerminalBean)list.get(i)).getSimNumber());
         modBuf.append(((TerminalBean)list.get(i)).getTerminalModel());
         passBuf.append(((TerminalBean)list.get(i)).getPassword());
         noBuf.append(((TerminalBean)list.get(i)).getTerminalID());
         if(i != list.size()-1){
             simBuf.append( "," );
             modBuf.append( "," );
             passBuf.append( "," );
             noBuf.append( "," );
         }
     }
     sm.clearAndAddAccidents(userInfo.getUserID(),simBuf.toString(),modBuf.toString(),
                         noBuf.toString(),passBuf.toString(),acciddentType,acciddent);

     return;
 }



    //删除并且增加隐患菜单
    public void clearAndAddAccidents(String UserID,String SimID,String DeviceModel,
           String DeviceID,String Password,ArrayList AccidentTypeList,ArrayList AccidentList){
        HashMap params = new HashMap();
           params.put( "UserID", UserID );
           params.put( "SimID", SimID );
           params.put( "DeviceModel", DeviceModel);
           params.put( "DeviceID", DeviceID );
           params.put( "Password", Password );
           params.put( "AccidentTypeList", AccidentTypeList );
           params.put( "AccidentList", AccidentList );

           this.sendSMI( params, "ClearAndAddAccidents", "" );
           return;

    }

    public static void searchV( UserInfo userinfo, String[] simnunber ){
        SendSMRMI sm = new SendSMRMI();
        List list = sm.getSimIfo( simnunber );
        if( list == null ){
            return;
        }
        for( int i = 0; i < list.size(); i++ ){
            TerminalBean bean = ( TerminalBean )list.get( i );
            sm.QueryDeviceVoltage( userinfo.getUserID(),
                bean.getSimNumber(),
                bean.getTerminalModel(),
                bean.getTerminalID(),
                bean.getPassword() );
        }
        return;
    }


    //往手持设备发送调度信息__静态方法
    public static boolean SendAttemperMsg_s( UserInfo userinfo, String terminalid, String needReplay, String message ){
        SendSMRMI sm = new SendSMRMI();
        String sql = "select t.TERMINALID,t.TERMINALTYPE,t.TERMINALMODEL,t.SIMNUMBER,t.PASSWORD "
                     + " from terminalinfo t "
                     + " where t.TERMINALID='" + terminalid + "' ";
        logger.info( "SQL:" + sql );
        try{
            QueryUtil qu = new QueryUtil();
            ResultSet rst = qu.executeQuery( sql );
            if( rst != null && rst.next() ){
                String temp = rst.getString( "terminalid" );
                if( rst.getString( "terminalmodel" ).substring( 0, 2 ).toUpperCase().equals( "CT" ) ){
                    temp = temp.substring( temp.length() - 8, temp.length() );
                }
                else{
                    temp = temp.substring( temp.length() - 4, temp.length() );
                }

                sm.SendAttemperMsg( userinfo.getUserID(),
                    rst.getString( "simnumber" ),
                    rst.getString( "terminalmodel" ),
                    temp,
                    rst.getString( "password" ),
                    needReplay,
                    message,
                    "00" );
            }
            if( rst != null ){
                rst.close();
            }
            return true;
        }
        catch( Exception ex ){
            logger.warn( "发送调度信息异常:" + ex.getMessage() );
            return false;
        }
    }


    //向手机发送短信
    /**
     *
     * @param userId String 用户的登录Id
     * @param simId String  接收短信的手机号
     * @param message String 消息内容
     * @param businessCode String 业务代码 固定用"00"
     */
    public static void sendNormalMessage( String userId, String simId,
        String message, String businessCode ){
        SendSMRMI srmi = new SendSMRMI();
        String srmiUrl = srmi.getRmiUrl();

        HashMap params = new HashMap();
        //用户登录Id
        params.put( "UserID", userId );
        //手机号
        params.put( "SimID", simId );
        //消息内容
        params.put( "Message", message );
        //业务代码 一般用"00"
        params.put( "BusinessCode", businessCode );
        RMIRequest rmiRequest = new RMIRequest();
        rmiRequest.setName( "SendNormalMsg" );
        rmiRequest.setParameter( params );
        RmiServerInterface rimObj = null;
        try{
            rimObj = ( RmiServerInterface )RmiClient.getRmiObject(
                     srmiUrl );
            //System.out.println( rimObj );
            //执行请求
            RMIResponse rmiResponse = rimObj.execute( rmiRequest );
            //如果执行成功，rmiResponse.isResult()返回true，否则返回false
            if( rmiResponse.isResult() ){
                //TODO:发送成功
                System.out.println( "发送成功" );
                logger.error( "信息发送成功" );
            }
            else{
                //TODO:发送失败
                logger.error( "信息发送失败" );
                System.out.println( "发送失败" );
            }
        }
        catch( Exception ex ){
            System.out.println( "发送短信出现异常：" + ex );
        }
    }


    /**
     * setPhoneBook
     * @param bean TerminalBean
     * @param userInfo UserInfo
     * @param simNumber String[]
     */
    public static void setPhoneBook( TerminalBean bean, UserInfo userInfo, String[] simNumber ){
        String sql = "";
        ResultSet rst;
        SendSMRMI srmi = new SendSMRMI();
        List list = new Vector();

        if( bean.getSetnumber().equals( "all" ) ){
            sql = " SELECT   t.simnumber, t.terminalmodel, t.phonebook, t.PASSWORD, "
                  + "          SUBSTR (t.terminalid, "
                  + "                  LENGTH (t.terminalid) - 7, "
                  + "                  LENGTH (t.terminalid) "
                  + "                 ) terminalid "
                  + "     FROM terminalinfo t, patrolmaninfo p "
                  + "    WHERE t.ownerid = p.patrolid "
                  + "      AND (t.state != '1' or t.STATE is null) "
                  + "      AND UPPER (SUBSTR (t.terminalmodel, 0, 2)) = 'CT'"
                  + "      AND p.parentid = '" + userInfo.getDeptID() + "'";

        }
        else{
            StringBuffer sb = new StringBuffer();
            for( int i = 0; i < simNumber.length; i++ ){
                if( i != simNumber.length - 1 ){
                    sb.append( simNumber[i] + "," );
                }
                else{
                    sb.append( simNumber[i] );
                }
            }
            sql = " SELECT   t.simnumber, t.terminalmodel, t.phonebook, t.PASSWORD, "
                  + "          SUBSTR (t.terminalid, "
                  + "                  LENGTH (t.terminalid) - 7, "
                  + "                  LENGTH (t.terminalid) "
                  + "                 ) terminalid "
                  + "     FROM terminalinfo t "
                  + "    WHERE t.simnumber in( " + sb.toString() + ")";
        }
        logger.info( "setPhoneBook SQL:" + sql );
        try{
            QueryUtil qu = new QueryUtil();
            rst = qu.executeQuery( sql );
            while( rst != null && rst.next() ){
                TerminalBean getBean = new TerminalBean();
                getBean.setSimNumber( rst.getString( "simnumber" ) );
                getBean.setTerminalModel( rst.getString( "terminalmodel" ) );
                getBean.setTerminalID( rst.getString( "terminalid" ) );
                getBean.setPassword( rst.getString( "password" ) );
                getBean.setPhonebook( rst.getString( "phonebook" ) );
                list.add( getBean );
            }
            rst.close();
            for( int i = 0; i < list.size(); i++ ){
                TerminalBean getBean = ( TerminalBean )list.get( i );
                srmi.setDevicePhoneBook( userInfo.getUserID(),
                    getBean.getSimNumber(),
                    getBean.getTerminalModel(),
                    getBean.getTerminalID(),
                    getBean.getPassword(),
                    getBean.getPhonebook(), "Rewrite" );
            }
            return;
        }
        catch( Exception ex ){
            logger.warn( "调用短信接口发送通讯录时异常:" + ex.getMessage() );
            return;
        }
    }

}
