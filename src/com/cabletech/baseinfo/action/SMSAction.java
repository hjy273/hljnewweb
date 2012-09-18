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
     * 发送初始化信息
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

        String errMsg = "请求超时或者连接短信服务器失败，请重试！";
        String sucMsg = "设备初始化成功！";

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
                log( request, " 初始化巡检设备 ", " 基础资料管理 " );
                request.setAttribute( "resultMsg", sucMsg );
            }
            else{
                request.setAttribute( "resultMsg", errMsg );
            }
            return mapping.findForward( "initDevice" );
        }
        catch( Exception e ){
            System.out.println( "初始化手持设备异常：" + e.getMessage() );
            request.setAttribute( "resultMsg", errMsg );
            return mapping.findForward( "initDevice" );
        }
    }


    /**
     * 发送定位信息
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

        String errMsg = "请求超时或者连接短信服务器失败，请重试！";
        String sucMsg = "发送定位消息成功！";

        SMSBean bean = ( SMSBean )form;

        String[] infoArr = new String[7];

        UserInfo currentUser = super.getLoginUserInfo( request );
        if( currentUser == null ){
            //会话超时
            String errmsg = "由于您当前会话已过期，请重新登录后重试！";
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
        logger.warn("=======开始调用短信=======");
        // if( smsutil.sendHomingSMS( infoArr ) ){
        if( SendSMRMI.SendLocalizeMsg( currentUser, infoArr[2], infoArr[4], infoArr[5] ) ){
            //Log
            log( request, " 发送定位信息 ", " 实时监控 " );
            request.setAttribute( "resultMsg", sucMsg );
        }
        else{
            request.setAttribute( "resultMsg", errMsg );

        }
        return mapping.findForward( "sendHomingSMS" );
    }


    /**
     * 发送调度信息
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

        String errMsg = "请求超时或者连接短信服务器失败，请重试！";
        String sucMsg = "发送调度消息成功！";

        SMSBean bean = ( SMSBean )form;

        if( bean.getNeedreply() == null ){
            bean.setNeedreply( "N" );
        }
           UserInfo userinfo = (UserInfo)request.getSession().getAttribute("LOGIN_USER");

        if(SendSMRMI.SendAttemperMsg_s(userinfo,bean.getDeviceid(),bean.getNeedreply(),bean.getMessage())){
            log( request, " 发送调度信息 ", " 实时监控" );
            request.setAttribute( "resultMsg", sucMsg );
        }else{
             request.setAttribute( "resultMsg", errMsg );
        }
        return mapping.findForward( "sendCommandSMS" );
    }


    /**
     * 查看回复信息
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
            logger.info( "查看回复信息出现异常" + e.toString() );
        }
        return mapping.findForward( "showReSMS" );
    }
}
