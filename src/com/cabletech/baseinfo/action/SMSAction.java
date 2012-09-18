package com.cabletech.baseinfo.action;

import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;
import com.cabletech.baseinfo.beans.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.commons.web.*;
import com.cabletech.utils.SMSkit.*;
import java.util.List;
import java.sql.*;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.sm.SendSMRMI;

public class SMSAction extends BaseInfoBaseDispatchAction{

    private static Logger logger =
        Logger.getLogger( SMSAction.class.getName() );

    /**
     * ���ͳ�ʼ����Ϣ
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward sendInitialSMS( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        String errMsg = "����ʱ�������Ӷ��ŷ�����ʧ�ܣ������ԣ�";
        String sucMsg = "�豸��ʼ���ɹ���";

        SMSBean bean = ( SMSBean )form;

        String[] infoArr = new String[5];

        infoArr[0] = super.getLoginUserInfo( request ).getUserID();
        infoArr[1] = bean.getSimid();
        infoArr[2] = bean.getDeviceid();
        infoArr[3] = bean.getPassword();
        infoArr[4] = bean.getSpid();
        try{

            //SmsUtil smsutil = SmsUtil.getInstance();
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
            // if( smsutil.sendInitialSMS( infoArr ) ){
            if( SendSMRMI.SendInitDeviceMsg( userinfo, infoArr[2] ) ){
                //Log
                log( request, " ��ʼ��Ѳ���豸 ", " �������Ϲ��� " );
                request.setAttribute( "resultMsg", sucMsg );
            }
            else{
                request.setAttribute( "resultMsg", errMsg );
            }
            return mapping.findForward( "initDevice" );
        }
        catch( Exception e ){
            System.out.println( "��ʼ���ֳ��豸�쳣��" + e.getMessage() );
            request.setAttribute( "resultMsg", errMsg );
            return mapping.findForward( "initDevice" );
        }
    }


    /**
     * ���Ͷ�λ��Ϣ
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward sendHomingSMS( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        String errMsg = "����ʱ�������Ӷ��ŷ�����ʧ�ܣ������ԣ�";
        String sucMsg = "���Ͷ�λ��Ϣ�ɹ���";

        SMSBean bean = ( SMSBean )form;

        String[] infoArr = new String[7];

        UserInfo currentUser = super.getLoginUserInfo( request );
        if( currentUser == null ){
            //�Ự��ʱ
            String errmsg = "��������ǰ�Ự�ѹ��ڣ������µ�¼�����ԣ�";
            request.setAttribute( "errmsg", errmsg );

            return mapping.findForward( "commonerror" );
        }

        infoArr[0] = currentUser.getUserID();
        infoArr[1] = bean.getSimid();
        infoArr[2] = bean.getDeviceid();
        infoArr[3] = bean.getPassword();
        infoArr[4] = bean.getSeconds();
        infoArr[5] = bean.getCount();
        infoArr[6] = bean.getMode();

        //SmsUtil smsutil = SmsUtil.getInstance();
        //smsutil.sendHomingSMS( infoArr );
        logger.warn("=======��ʼ���ö���=======");
        // if( smsutil.sendHomingSMS( infoArr ) ){
        if( SendSMRMI.SendLocalizeMsg( currentUser, infoArr[2], infoArr[4], infoArr[5] ) ){
            //Log
            log( request, " ���Ͷ�λ��Ϣ ", " ʵʱ��� " );
            request.setAttribute( "resultMsg", sucMsg );
        }
        else{
            request.setAttribute( "resultMsg", errMsg );

        }
        return mapping.findForward( "sendHomingSMS" );
    }


    /**
     * ���͵�����Ϣ
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward sendCommandSMS( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        String errMsg = "����ʱ�������Ӷ��ŷ�����ʧ�ܣ������ԣ�";
        String sucMsg = "���͵�����Ϣ�ɹ���";

        SMSBean bean = ( SMSBean )form;

        if( bean.getNeedreply() == null ){
            bean.setNeedreply( "N" );
        }
           UserInfo userinfo = (UserInfo)request.getSession().getAttribute("LOGIN_USER");

        if(SendSMRMI.SendAttemperMsg_s(userinfo,bean.getDeviceid(),bean.getNeedreply(),bean.getMessage())){
            log( request, " ���͵�����Ϣ ", " ʵʱ���" );
            request.setAttribute( "resultMsg", sucMsg );
        }else{
             request.setAttribute( "resultMsg", errMsg );
        }
        return mapping.findForward( "sendCommandSMS" );
    }


    /**
     * �鿴�ظ���Ϣ
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward showReSMS( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
        try{
            String id = request.getParameter( "id" );
            String sim = request.getParameter( "sim" );
            String pw = request.getParameter( "pw" );
            String sql = "select KEYID id, MSGCONTENT, RESPSTATE, TO_CHAR (SENDTIME, 'YYYY-MM-DD') SENDTIME, TO_CHAR (RESPTIME, 'YYYY-MM-DD') RESPTIME  from ATTEMPER_MSG where simid = '"
                         + sim + "'";
            List list = super.getDbService().queryBeans( sql );
            logger.info( sql );
            request.getSession().setAttribute( "showsms", list );
        }
        catch( Exception e ){
            logger.info( "�鿴�ظ���Ϣ�����쳣" + e.toString() );
        }
        return mapping.findForward( "showReSMS" );
    }
}
