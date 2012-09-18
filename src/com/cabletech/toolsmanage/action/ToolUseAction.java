package com.cabletech.toolsmanage.action;

import java.text.*;
import java.util.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.base.*;
import com.cabletech.power.*;
import com.cabletech.toolsmanage.beans.*;
import com.cabletech.toolsmanage.dao.*;
import java.sql.ResultSet;
import com.cabletech.commons.hb.QueryUtil;

public class ToolUseAction extends BaseDispatchAction{
    private static Logger logger = Logger.getLogger( ToolUseAction.class.
                                   getName() );
    private ToolUseDao useDao = new ToolUseDao();
    private ToolExportDao exportDao = new ToolExportDao();
    //��ʾ���õ�
    public ActionForward addUseShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90102" ) ){
            return mapping.findForward( "powererror" );
        }
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ��ʵ�
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        String deptname = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_NAME" ); ;
        request.setAttribute( "deptname", deptname );
        request.setAttribute( "deptid", userinfo.getDeptID() );
        request.setAttribute( "username", userinfo.getUserName() );
        request.setAttribute( "userid", userinfo.getUserID() );

        //��÷������ĵ�ǰʱ��
        Date nowDate = new Date();
        DateFormat df = DateFormat.getDateInstance( DateFormat.DATE_FIELD );
        String data = df.format( nowDate );
        request.setAttribute( "date", data );

        //��øõ�λ�����Ѿ���ŵı�����Ϣ,�Թ��û�ѡ��
        List lBaseInfo = useDao.getToolInfo( userinfo.getDeptID() );
        request.setAttribute( "baseinfo", lBaseInfo );

        request.getSession().setAttribute( "type", "use2" );
        return mapping.findForward( "success" );
    }


    //ִ����д���õ�
    public ActionForward addUse( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90102" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            ToolsInfoBean bean = ( ToolsInfoBean )form;
            String[] id = request.getParameterValues( "id" );
            String[] enumber = request.getParameterValues( "enumber" );
            if( !useDao.addUseInfo( bean, id, enumber ) ){
                return forwardInfoPage( mapping, request, "error" );
            }
            log( request, "��������", "������õ�" );
            return forwardInfoPage( mapping, request, "90102" );
        }
        catch( Exception e ){
            logger.error( "��ִ����д���õ��г���:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //��ʾ�������õ�
    public ActionForward showAllUse( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90101" ) ){
            return mapping.findForward( "powererror" );
        }

        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            List lReqInfo = useDao.getAllUse( request );

            request.getSession().setAttribute( "useinfo", lReqInfo );
            request.getSession().setAttribute( "type", "use1" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "��ʾ���г��ⵥ��Ϣ����:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //��ʾһ����ⵥ����ϸ��Ϣ
    public ActionForward showOneUse( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90101" ) ){
            return mapping.findForward( "powererror" );
        }
        ToolsInfoBean useinfo = new ToolsInfoBean(); //����ҳ������õ�������Ϣ
        List useToolInfo = null;
        String useid = request.getParameter( "useid" );

        try{
            //��õ�ǰ�û��ĵ�λ����
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                                "LOGIN_USER" );
            if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ��ʵ�
                return forwardErrorPage( mapping, request, "partstockerror" );
            }
            //������õ�������Ϣ
            useinfo = useDao.getOneUse( useid ); ;
            request.setAttribute( "useinfo", useinfo );

            //������õ������õı�����Ϣ
            useToolInfo = useDao.getToolsOfOneUse( useid );
            request.setAttribute( "usepartinfo", useToolInfo );
            request.getSession().setAttribute( "type", "use10" );

            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "����ʾ��ϸ�г��ִ���:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //�ۺϲ�ѯ��ʾ
    public ActionForward queryShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90103" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            //��ò���������
            List lUser = useDao.getUserArr( contractorid );
            request.setAttribute( "useuser", lUser );
            //�������������
            List lUsename = useDao.getUseNameArr( contractorid );
            request.setAttribute( "lusename", lUsename );
            //������õ�λ�б�
            List lUseunit = useDao.getUseUnitArr( contractorid );
            request.setAttribute( "luseunit", lUseunit );
            //�������ԭ���б�
            List lRemark = useDao.getUseRemarkArr( contractorid );
            request.setAttribute( "useremark", lRemark );
            request.getSession().setAttribute( "type", "use3" );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "�ۺϲ�ѯ��ʾ�쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //�ۺϲ�ѯִ��
    public ActionForward queryExec( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90103" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            ToolsInfoBean bean = ( ToolsInfoBean )form;
            bean.setContractorid( contractorid );

            List lUseInfo = useDao.getAllUseForSearch( bean );
            request.getSession().setAttribute( "useinfo", lUseInfo );
            request.getSession().setAttribute( "type", "use1" );
            request.getSession().setAttribute( "bean", bean );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "�ۺϲ�ѯִ���쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


//////////////////////////////////////////////////////////////////////////
    //����ʱ��ѯ���õ���ʾ
    public ActionForward backShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90106" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            //��ò���������
            List lUser = useDao.getUserArr( contractorid );
            request.setAttribute( "useuser", lUser );
            //�������������
            List lUsename = useDao.getUseNameArr( contractorid );
            request.setAttribute( "lusename", lUsename );
            //������õ�λ�б�
            List lUseunit = useDao.getUseUnitArr( contractorid );
            request.setAttribute( "luseunit", lUseunit );
            //�������ԭ���б�
            List lRemark = useDao.getUseRemarkArr( contractorid );
            request.setAttribute( "useremark", lRemark );
            request.getSession().setAttribute( "type", "back3" );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "����ʱ��ѯ���õ���ʾ�쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //����ʱ��ѯ���õ�ִ��
    public ActionForward backQueryExec( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90106" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            ToolsInfoBean bean = ( ToolsInfoBean )form;
            bean.setContractorid( contractorid );

            List lUseInfo = useDao.getAllUseForBack( bean );
            request.getSession().setAttribute( "useinfo", lUseInfo );
            request.getSession().setAttribute( "type", "back1" );
            request.getSession().setAttribute( "bean", bean );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "����ʱ��ѯ���õ�ִ���쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //����ҳ����ʾ
    public ActionForward showOneUseForBack( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90106" ) ){
            return mapping.findForward( "powererror" );
        }
        ToolsInfoBean useinfo = new ToolsInfoBean(); //����ҳ������õ�������Ϣ
        List useToolInfo = null;
        String useid = request.getParameter( "useid" );

        try{
            //��õ�ǰ�û��ĵ�λ����
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                                "LOGIN_USER" );
            if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ��ʵ�
                return forwardErrorPage( mapping, request, "partstockerror" );
            }
            //��÷������ĵ�ǰʱ��
            Date nowDate = new Date();
            DateFormat df = DateFormat.getDateInstance( DateFormat.DATE_FIELD );
            String data = df.format( nowDate );
            request.setAttribute( "date", data );
            //������õ�������Ϣ
            useinfo = useDao.getOneUse( useid ); ;
            request.setAttribute( "useinfo", useinfo );

            //������õ������õı�����Ϣ
            useToolInfo = useDao.getToolsOfOneUse( useid );
            request.setAttribute( "usepartinfo", useToolInfo );
            request.getSession().setAttribute( "type", "back10" );

            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "�ڷ���ҳ����ʾ�г��ִ���:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //ִ����д������
    public ActionForward addBack( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90106" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            ToolsInfoBean bean = ( ToolsInfoBean )form;
            bean.setUserid( userinfo.getUserID() );
            bean.setContractorid( userinfo.getDeptID() );
            String[] id = request.getParameterValues( "id" );
            String[] bnumber = request.getParameterValues( "bnumber" );
            if( !useDao.addBackInfo( bean, id, bnumber ) ){
                return forwardInfoPage( mapping, request, "error" );
            }
            log( request, "��������", "��������" );
            return forwardInfoPage( mapping, request, "90106" );
        }
        catch( Exception e ){
            logger.error( "��ִ����д�������г���:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //����������ҳ����ʾ
    public ActionForward shouldBackShow( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90107" ) ){
            return mapping.findForward( "powererror" );
        }

        try{
            //��õ�ǰ�û��ĵ�λ����
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                                "LOGIN_USER" );
            if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ��ʵ�
                return forwardErrorPage( mapping, request, "partstockerror" );
            }
            //������õ������õı�����Ϣ
            List shouldBackTool = useDao.getToolForShouldBack( userinfo.getDeptID() );
            request.getSession().setAttribute( "shouldBackTool", shouldBackTool );
            request.getSession().setAttribute( "type", "back07" );

            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "�ڷ���ҳ����ʾ�г��ִ���:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //��ѯӦ�����������õ�ִ��
    public ActionForward showShouldBackUse( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90106" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            String id = request.getParameter( "id" );
            ToolsInfoBean bean = ( ToolsInfoBean )form;
            bean.setContractorid( contractorid );
            bean.setId( id );

            List lUseInfo = useDao.getAllShouldBackUse( bean );
            request.getSession().setAttribute( "useinfo", lUseInfo );
            request.getSession().setAttribute( "type", "back1" );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "��ѯӦ�����������õ�ִ���쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    public ActionForward exportUseResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws Exception{
        try{
            ToolsInfoBean bean = null;
            if( request.getSession().getAttribute( "bean" ) != null ){
                bean = ( ToolsInfoBean )request.getSession().getAttribute( "bean" );
                logger.info( "��ò�ѯ����bean������" );

                logger.info( "id��" + bean.getUserid() );
                logger.info( "������������" + bean.getUsename());
                logger.info( "���õ�λ��" + bean.getUseunit() );
                logger.info( "����ԭ��" + bean.getUseremark() );
                logger.info( "�黹�����" + bean.getBack() );
                logger.info( "��ʼʱ�䣺" + bean.getBegintime() );
                logger.info( "����ʱ�䣺" + bean.getEndtime() );

                if( !bean.getUserid().equals( "" ) ){
                    String sql = "select u.USERNAME from userinfo u  where u.USERID = '" + bean.getUserid() + "'";
                    QueryUtil query = new QueryUtil();
                    ResultSet rs = null;
                    rs = query.executeQuery( sql );
                    while( rs.next() ){
                        bean.setUsername( rs.getString( 1 ) );
                    }
                    logger.info( "����ˣ�" + bean.getUsername() );
                    request.getSession().removeAttribute( "bean" );
                }
            }
            List list = ( List )request.getSession().getAttribute( "useinfo" );
            logger.info( "�õ�list" );
            logger.info( "���excel�ɹ�" );

            exportDao.exportUseResult( list, bean, response );

            return null;
        }
        catch( Exception e ){
            logger.error( "������Ϣ��������쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    public ActionForward exportBackResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws Exception{
        try{
            ToolsInfoBean bean = null;
            if( request.getSession().getAttribute( "bean" ) != null ){
                bean = ( ToolsInfoBean )request.getSession().getAttribute( "bean" );
                logger.info( "��ò�ѯ����bean������" );

                logger.info( "id��" + bean.getUserid() );
                logger.info( "������������" + bean.getUsename());
                logger.info( "���õ�λ��" + bean.getUseunit() );
                logger.info( "����ԭ��" + bean.getUseremark() );
                logger.info( "��ʼʱ�䣺" + bean.getBegintime() );
                logger.info( "����ʱ�䣺" + bean.getEndtime() );

                if( !bean.getUserid().equals( "" ) ){
                    String sql = "select u.USERNAME from userinfo u  where u.USERID = '" + bean.getUserid() + "'";
                    QueryUtil query = new QueryUtil();
                    ResultSet rs = null;
                    rs = query.executeQuery( sql );
                    while( rs.next() ){
                        bean.setUsername( rs.getString( 1 ) );
                    }
                    logger.info( "����ˣ�" + bean.getUsername() );
                    request.getSession().removeAttribute( "bean" );
                }
            }
            List list = ( List )request.getSession().getAttribute( "useinfo" );
            logger.info( "�õ�list" );
            logger.info( "���excel�ɹ�" );

            exportDao.exportBackResult( list, bean, response );

            return null;
        }
        catch( Exception e ){
            logger.error( "������Ϣ��������쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    public ActionForward exportUseList( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
//        if( !CheckPower.checkPower( request.getSession(), "80403" ) ){
//            return mapping.findForward( "powererror" );
//        }
//        //��õ�ǰ�û��ĵ�λ����
//        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
//                            "LOGIN_USER" );
//        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
//            return forwardErrorPage( mapping, request, "partstockerror" );
//        }

        String contractorid = ( String )request.getSession().getAttribute( "LOGIN_USER_DEPT_ID" );

        try{

            String userid = request.getParameter( "userid" );
            String usename = request.getParameter( "usename" );
            String useunit = request.getParameter( "useunit" );
            String useremark = request.getParameter( "useremark" );
            String back = request.getParameter( "back" );
            String begintime = request.getParameter( "begintime" );
            String endtime = request.getParameter( "endtime" );

            ToolsInfoBean bean = ( ToolsInfoBean )form;
            bean.setContractorid( contractorid );
            bean.setUsername( userid );
            bean.setUsename( usename );
            bean.setUseunit( useunit );
            bean.setUseremark( useremark );
            bean.setBack( back );
            bean.setBegintime( begintime );
            bean.setEndtime( endtime );
            if( userid != null){
                String sql = "select u.USERNAME from userinfo u  where u.USERID = '" + userid + "'";
                QueryUtil query = new QueryUtil();
                ResultSet rs = null;
                rs = query.executeQuery( sql );
                while( rs.next() ){
                    bean.setUsername( rs.getString( 1 ) );
                }
                logger.info( "�����ˣ�" + bean.getUsername() );
                }
            List list = useDao.getUseList( bean );

            exportDao.exportUseList(list, bean, response);

            return null;

        }
        catch( Exception e ){
            logger.error( "������Ϣ�����쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
}
