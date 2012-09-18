package com.cabletech.utils.SMSkit;

import java.util.*;

import com.cabletech.commons.config.*;
import com.cabletech.sm.rmi.client.*;
import com.cabletech.sm.rmi.server.*;

public class SmsUtil{

    private static SmsUtil smsutil;
    public static RMIRequest rmiRequest;

    SmsConInfo smsconfig = new SmsConInfo();
    public String rmiUrl = "t3://" + smsconfig.getSmsServerip() + ":" +
                           smsconfig.getSmsServerport();


    public SmsUtil(){
        if( rmiRequest == null ){
            rmiRequest = new RMIRequest();
        }
    }


    public static SmsUtil getInstance(){
        if( smsutil == null ){
            smsutil = new SmsUtil();
        }
        return smsutil;
    }


    /**
     * ˢ�»���
     * @param infoArr String[]
     * @return boolean
     * @throws Exception
     */
    public boolean refreshCache( String[] infoArr ) throws Exception{
        //UserID

        rmiRequest.setName( "RefreshCache" );

        HashMap inputParams = new HashMap();
        inputParams.put( "UserID", infoArr[0] );
        rmiRequest.setParameter( inputParams );

        RmiServerInterface rimObj = ( RmiServerInterface )RmiClient.getRmiObject(
                                    rmiUrl );

        //ִ������
        RMIResponse rmiResponse = rimObj.execute( rmiRequest );

        //���ִ�гɹ���rmiResponse.isResult()����true�����򷵻�false
        System.out.println( "ˢ�»��� : " + rmiResponse.isResult() );

        return rmiResponse.isResult();
    }


    /**
     * ���Ͷ�λ��Ϣ
     * @param infoArr String[]
     * @return boolean
     * @throws Exception
     */
    public boolean sendHomingSMS( String[] infoArr ) throws Exception{
        //UserID, SimID, DeviceID, Password, Seconds, Count, Mode

        boolean resultFlag = false;

        rmiRequest.setName( "SendLocalizeMsg" );

        HashMap inputParams = new HashMap();

        inputParams.put( "UserID", infoArr[0] );
        inputParams.put( "SimID", infoArr[1] );
        inputParams.put( "DeviceID", infoArr[2] );
        inputParams.put( "Password", infoArr[3] );
        inputParams.put( "Seconds", infoArr[4] );
        inputParams.put( "Count", infoArr[5] );
        inputParams.put( "Mode", infoArr[6] );

        rmiRequest.setParameter( inputParams );
        try{
            RmiServerInterface rimObj = ( RmiServerInterface )RmiClient.
                                        getRmiObject( rmiUrl );

            //ִ������
            RMIResponse rmiResponse = rimObj.execute( rmiRequest );
            resultFlag = rmiResponse.isResult();

        }
        catch( Exception e ){
            System.out.println( "���Ͷ�λ��Ϣʧ�� : " + e.toString() );
        }

        return resultFlag;

    }


    /**
     * ���͵�����Ϣ
     * @param infoArr String[]
     * @return boolean
     * @throws Exception
     */
    public boolean sendCommandSMS( String[] infoArr ) throws Exception{
        //UserID, SimID, Message

        boolean resultFlag = false;

        rmiRequest.setName( "SendAttemperMsg" );

        String UserID = infoArr[0];
        String SimID = infoArr[1];
        String Message = infoArr[2];
        String DeviceID = infoArr[3];
        String Password = infoArr[4];
        String NeedReply = infoArr[5];
        String DeviceModel = infoArr[6];
        String BusinessCode = infoArr[7];

        HashMap inputParams = new HashMap();

        inputParams.put( "UserID", UserID );
        inputParams.put( "SimID", SimID );
        inputParams.put( "Message", Message );
        inputParams.put( "DeviceID", DeviceID );
        inputParams.put( "Password", Password );
        inputParams.put( "NeedReply", NeedReply );
        inputParams.put( "DeviceModel", DeviceModel );
        inputParams.put( "BusinessCode", BusinessCode );

        rmiRequest.setParameter( inputParams );

        try{
            RmiServerInterface rimObj = ( RmiServerInterface )RmiClient.
                                        getRmiObject( rmiUrl );

            //ִ������
            RMIResponse rmiResponse = rimObj.execute( rmiRequest );
            resultFlag = rmiResponse.isResult();
        }
        catch( Exception e ){
            System.out.println( "���͵�����Ϣʧ�� : " + e.toString() );
        }

        return resultFlag;

    }


    /**
     * ���ͳ�ʼ���豸��Ϣ
     * @param infoArr String[]
     * @return boolean
     * @throws Exception
     */
    public boolean sendInitialSMS( String[] infoArr ) throws Exception{
        //UserID, SimID, DeviceID, Password, SPID
        boolean resultFlag = false;

        rmiRequest.setName( "SendInitDeviceMsg" );

        HashMap inputParams = new HashMap();

        inputParams.put( "UserID", infoArr[0] );
        inputParams.put( "SimID", infoArr[1] );
        inputParams.put( "DeviceID", infoArr[2] );
        inputParams.put( "Password", infoArr[3] );
        inputParams.put( "SPID", infoArr[4] );

        rmiRequest.setParameter( inputParams );

        try{

            RmiServerInterface rimObj = ( RmiServerInterface )RmiClient.
                                        getRmiObject( rmiUrl );
            //ִ������
            RMIResponse rmiResponse = rimObj.execute( rmiRequest );
            resultFlag = rmiResponse.isResult();
        }
        catch( Exception e ){
            System.out.println( "���ͳ�ʼ���豸��Ϣʧ�� : " + e.toString() );
        }

        return resultFlag;
    }


    /**
     * ȡ��ʱ��
     * @return Vector
     * @throws Exception
     */
    public Vector getSettingTime() throws Exception{

        Vector vct = new Vector();
        rmiRequest.setName( "DisplayConfig" );

        try{

            RmiServerInterface rimObj = ( RmiServerInterface )RmiClient.
                                        getRmiObject( rmiUrl );
            //ִ������
            RMIResponse rmiResponse = rimObj.execute( rmiRequest );

            HashMap outputParams = ( HashMap )rmiResponse.getParameter();

            String workBegin = ( String )outputParams.get(
                               "sm.patrol.worktime.begin" );
            String workEnd = ( String )outputParams.get( "sm.patrol.worktime.end" );

            vct.add( workBegin );
            vct.add( workEnd );

        }
        catch( Exception e ){
            System.out.println( "ȡ��ϵͳ����ʱ��ʧ�� : " + e.toString() );
        }

        return vct;
    }


    /**
     * ����ʱ��
     * @param infoArr String[]
     * @return boolean
     * @throws Exception
     */
    public void setTime( String begintime, String endtime ) throws Exception{
        //begin, end

        rmiRequest.setName( "UpdateConfig" );

        HashMap inputParams = new HashMap();

        inputParams.put( "sm.patrol.worktime.begin", begintime );
        inputParams.put( "sm.patrol.worktime.end", endtime );

        rmiRequest.setParameter( inputParams );

        try{

            RmiServerInterface rimObj = ( RmiServerInterface )RmiClient.
                                        getRmiObject( rmiUrl );
            //ִ������
            RMIResponse rmiResponse = rimObj.execute( rmiRequest );
            HashMap outputParams = ( HashMap )rmiResponse.getParameter();

        }
        catch( Exception e ){
            System.out.println( "����ʱ��ʧ�� : " + e.toString() );
        }

    }

}
