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

public class ToolToMainAction extends BaseDispatchAction{
    private ToolToMainDao mainDao = new ToolToMainDao();
    private ToolExportDao exportDao = new ToolExportDao();
    private static Logger logger = Logger.getLogger( ToolToMainAction.class.
                                   getName() );

    //��ʾ���޵�
    public ActionForward addMainShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90402" ) ){
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

        //��øõ�λ�����Ѿ���ŵĴ����ޱ�����Ϣ,�Թ��û�ѡ��

        List lBaseInfo = mainDao.getToolInfo( userinfo.getDeptID() );
        request.setAttribute( "baseinfo", lBaseInfo );

        request.getSession().setAttribute( "type", "main2" );
        return mapping.findForward( "success" );
    }


    //ִ����д���޵�
    public ActionForward addMain( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90402" ) ){
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
            bean.setContractorid( userinfo.getDeptID() );
            if( !mainDao.addMianInfo( bean, id, enumber ) ){
                return forwardInfoPage( mapping, request, "error" );
            }
            log( request, "��������", "������޵�" );
            return forwardInfoPage( mapping, request, "90402" );
        }
        catch( Exception e ){
            logger.error( "��ִ����д���޵��г���:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //��ʾ�������޵�
    public ActionForward showAllMain( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90401" ) ){
            return mapping.findForward( "powererror" );
        }

        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            List lMainInfo = mainDao.getAllMain( request );
            if( lMainInfo == null ){
                return forwardErrorPage( mapping, request, "90301e" );
            }
            request.getSession().setAttribute( "maininfo", lMainInfo );
            request.getSession().setAttribute( "type", "mian1" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "��ʾ������ʾ�������޵�����:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //��ʾһ�����޵�����ϸ��Ϣ
    public ActionForward showOneMain( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90401" ) ){
            return mapping.findForward( "powererror" );
        }
        ToolsInfoBean maininfo = new ToolsInfoBean(); //����ҳ������޵�������Ϣ
        List mainToolInfo = null;
        String mainid = request.getParameter( "mainid" );

        try{
            //��õ�ǰ�û��ĵ�λ����
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                                "LOGIN_USER" );
            if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ��ʵ�
                return forwardErrorPage( mapping, request, "partstockerror" );
            }
            //������޵�������Ϣ
            maininfo = mainDao.getOneUse( mainid ); ;
            request.setAttribute( "maininfo", maininfo );

            //������õ������޵ı�����Ϣ
            mainToolInfo = mainDao.getToolsOfOneUse( mainid );
            request.setAttribute( "maintoolinfo", mainToolInfo );
            request.getSession().setAttribute( "type", "main10" );

            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "����ʾ��ϸ�г��ִ���:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //    //�ۺϲ�ѯ��ʾ
    public ActionForward queryShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90403" ) ){
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
            List lUser = mainDao.getUserArr( contractorid );
            request.setAttribute( "mainuser", lUser );
            //���ά�޵�λ�б�
            List lRemark = mainDao.getMainunitArr( contractorid );
            request.setAttribute( "mainunit", lRemark );
            //����������б�
            List lMainName = mainDao.getMainNameArr( contractorid );
            request.setAttribute( "mainname", lMainName );
            request.getSession().setAttribute( "type", "main3" );
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
        if( !CheckPower.checkPower( request.getSession(), "90303" ) ){
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

            List lMainInfo = mainDao.getAllMainForSearch( bean );
            request.getSession().setAttribute( "maininfo", lMainInfo );
            request.getSession().setAttribute( "type", "mian1" );
            request.getSession().setAttribute( "bean", bean );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "�ۺϲ�ѯִ���쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //�����ޱ���ҳ����ʾ
    public ActionForward showMainToolAll( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90406" ) ){
            return mapping.findForward( "powererror" );
        }

        try{
            //��õ�ǰ�û��ĵ�λ����
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                                "LOGIN_USER" );
            if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ��ʵ�
                return forwardErrorPage( mapping, request, "partstockerror" );
            }
            //����������ޱ���������Ϣ
            List lMainTool = mainDao.getMainToolAll( userinfo.getDeptID() );
            request.getSession().setAttribute( "mainTool", lMainTool );
            request.getSession().setAttribute( "type", "main6" );

            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "�����ޱ���ҳ����ʾ����:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //��ʾ����һ�������������޵�
    public ActionForward showTool_Main( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90306" ) ){
            return mapping.findForward( "powererror" );
        }

        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        try{
            List lMainInfo = mainDao.getTool_Main( request );
            if( lMainInfo == null ){
                return forwardErrorPage( mapping, request, "90301e" );
            }
            request.getSession().setAttribute( "maininfo", lMainInfo );
            request.getSession().setAttribute( "type", "mian1" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "��ʾ������ʾ�������޵�����:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //���������ʾ
    public ActionForward addMainBackShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90407" ) ){
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

        //��øõ�λ�����Ѿ���ŵĴ����ޱ�����Ϣ,�Թ��û�ѡ��

        List lBaseInfo = mainDao.getToolBackInfo( userinfo.getDeptID() );
        request.setAttribute( "baseinfo", lBaseInfo );

        request.getSession().setAttribute( "type", "main7" );
        return mapping.findForward( "success" );
    }


    //ִ����д������ⵥ
    public ActionForward addMainBack( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90407" ) ){
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
            bean.setContractorid( userinfo.getDeptID() );
            if( !mainDao.addMianInfoBack( bean, id, enumber ) ){
                return forwardInfoPage( mapping, request, "error" );
            }
            log( request, "��������", "���������ⵥ" );
            return forwardInfoPage( mapping, request, "90407" );
        }
        catch( Exception e ){
            logger.error( "ִ����д������ⵥ����:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //addMainDeleShow
    //���ޱ�����ʾ
    public ActionForward addMainDeleShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90408" ) ){
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

        //��øõ�λ�����Ѿ���ŵĴ����ޱ�����Ϣ,�Թ��û�ѡ��

        List lBaseInfo = mainDao.getToolBackInfo( userinfo.getDeptID() );
        request.setAttribute( "baseinfo", lBaseInfo );

        request.getSession().setAttribute( "type", "main8" );
        return mapping.findForward( "success" );
    }


    //ִ����д���ޱ��ϵ�
    public ActionForward addMainDele( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90408" ) ){
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
            bean.setContractorid( userinfo.getDeptID() );
            if( !mainDao.addMianInfoDele( bean, id, enumber ) ){
                return forwardInfoPage( mapping, request, "error" );
            }
            log( request, "��������", "������ޱ��ϵ�" );
            return forwardInfoPage( mapping, request, "90408" );
        }
        catch( Exception e ){
            logger.error( "ִ����д���ޱ��ϵ�����:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    public ActionForward exportToMainResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws Exception{
        try{
            ToolsInfoBean bean = null;
            if( request.getSession().getAttribute( "bean" ) != null ){
                bean = ( ToolsInfoBean )request.getSession().getAttribute( "bean" );
                logger.info( "��ò�ѯ����bean������" );

                logger.info( "id��" + bean.getUserid() );
                logger.info( "ά�޵�λ��" + bean.getMainunit() );
                logger.info( "�����ˣ�" + bean.getMainname() );
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
                    logger.info( "�����ˣ�" + bean.getUsername() );
                    request.getSession().removeAttribute( "bean" );
                }
            }
            List list = ( List )request.getSession().getAttribute( "maininfo" );
            logger.info( "�õ�list" );
            logger.info( "���excel�ɹ�" );

            exportDao.exportToMainResult( list, bean, response );

            return null;
        }
        catch( Exception e ){
            logger.error( "������Ϣ��������쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    public ActionForward exportToMainList( ActionMapping mapping, ActionForm form,
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
            String mainunit = request.getParameter( "mainunit" );
            String mainname = request.getParameter( "mainname" );
            String begintime = request.getParameter( "begintime" );
            String endtime = request.getParameter( "endtime" );

            ToolsInfoBean bean = ( ToolsInfoBean )form;
            bean.setContractorid( contractorid );
            bean.setUserid(userid);
            bean.setMainunit(mainunit);
            bean.setMainname(mainname);
            bean.setBegintime( begintime );
            bean.setEndtime( endtime );
            if( userid != null ){
                String sql = "select u.USERNAME from userinfo u  where u.USERID = '" + userid + "'";
                QueryUtil query = new QueryUtil();
                ResultSet rs = null;
                rs = query.executeQuery( sql );
                while( rs.next() ){
                    bean.setUsername( rs.getString( 1 ) );
                }
                logger.info( "�����ˣ�" + bean.getUsername() );
                request.getSession().removeAttribute( "bean" );
                }
            List list = mainDao.getToMainList( bean );

            exportDao.exportToMainList(list, bean, response);

            return null;

        }
        catch( Exception e ){
            logger.error( "������Ϣ�����쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
}
