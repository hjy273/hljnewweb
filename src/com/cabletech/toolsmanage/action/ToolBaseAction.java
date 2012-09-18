package com.cabletech.toolsmanage.action;

import java.util.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.base.*;
import com.cabletech.power.*;
import com.cabletech.toolsmanage.beans.*;
import com.cabletech.toolsmanage.dao.*;

public class ToolBaseAction extends BaseDispatchAction{
    private static Logger logger = Logger.getLogger( ToolBaseAction.class.
                                   getName() );
    private ToolBaseInfoDao dao = new ToolBaseInfoDao();
    private ToolExportDao exportDao = new ToolExportDao();

    /**
     * �����ʾ
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     */
    public ActionForward addToolsBaseInfoshow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90802" ) ){
            return mapping.findForward( "powererror" );
        }
        request.getSession().setAttribute( "type", "2" );
        return mapping.findForward( "success" );
    }



    /**
     * ִ�����
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     */
    public ActionForward addToolBaseInfo( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        try{
            ToolsInfoBean bean = ( ToolsInfoBean )form;
            bean.setRegionid( userinfo.getRegionID() );
            ToolBaseInfoDao beanDao = new ToolBaseInfoDao( bean );
            if( true == beanDao.addToolBaseInfo() ){
                log( request, "��������", "��ӱ�������" );
                return forwardInfoPage( mapping, request, "90802" );

            }
            else{
                return forwardErrorPage( mapping, request, "error" );
            }
        }
        catch( Exception e ){
            logger.error( "��ӱ�����Ϣ�쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    /**
     * ��ʾ������������Ϣ
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     */
    public ActionForward showToolsBaseInfo( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        if( !CheckPower.checkPower( request.getSession(), "90801" ) ){
            return mapping.findForward( "powererror" );
        }
        try{
            List lInfo = dao.getAllInfo( userinfo.getRegionID() );
            request.getSession().setAttribute( "toolInfo", lInfo );
            request.getSession().setAttribute( "type", "1" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "��ʾ������������Ϣ����:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    /**
     * ��ʾ��������������Ϣ
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     */
    public ActionForward showOneToolBaseInfo( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90801" ) ){
            return mapping.findForward( "powererror" );
        }
        ToolsInfoBean bean = ( ToolsInfoBean )form;
        String id = bean.getId();
        bean = dao.getOneInfo( id );
        if( null != bean ){
            request.setAttribute( "toolInfo", bean );
            request.getSession().setAttribute( "type", "10" );
            return mapping.findForward( "success" );
        }
        else{
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    /**
     * �޸���ʾ
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     */
    public ActionForward upBaseInfoShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90804" ) ){
            return mapping.findForward( "powererror" );
        }
        ToolsInfoBean bean = ( ToolsInfoBean )form;
        bean = dao.getOneInfo( bean.getId() );
        if( null != bean ){
            request.setAttribute( "toolInfo", bean );
            request.getSession().setAttribute( "type", "4" );
            return mapping.findForward( "success" );
        }
        else{
            logger.error( "ִ���޸���ʾ����������Ϣ�г��ִ���:" );
            return forwardErrorPage( mapping, request, "error" );
        }
    }

    /**
     * ִ���޸�
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     */
    public ActionForward upToolBaseInfo( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90804" ) ){
            return mapping.findForward( "powererror" );
        }
        try{
            ToolsInfoBean bean = ( ToolsInfoBean )form;
            ToolBaseInfoDao beanDao = new ToolBaseInfoDao( bean );
            if( true == beanDao.updateToolBaseInfo() ){
                log( request, "��������", "�޸ı�����Ϣ" );
                return forwardInfoPage( mapping, request, "90804" );
            }
            else{
                return forwardErrorPage( mapping, request, "error" );
            }
        }
        catch( Exception e ){
            logger.error( "�޸ı�����Ϣ�쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    /**
     * ִ��ɾ��
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     */
    public ActionForward deleteToolBaseInfo( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90805" ) ){
            return mapping.findForward( "powererror" );
        }
        try{
            ToolsInfoBean bean = ( ToolsInfoBean )form;
            if( true == dao.deleteToolBaseInfo( bean.getId() ) ){
                log( request, "��������", "ɾ��������Ϣ" );
                return forwardInfoPage( mapping, request, "90805" );
            }
            else{
                return forwardErrorPage( mapping, request, "908055e" );
            }
        }
        catch( Exception e ){
            logger.error( "ɾ��������Ϣ�쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    /**
     * �ۺϲ�ѯ��ʾ
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     */
    public ActionForward queryShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        if( !CheckPower.checkPower( request.getSession(), "90803" ) ){
            return mapping.findForward( "powererror" );
        }
        try{

            List lName = dao.getAllName( userinfo.getRegionID() );
            List lType = dao.getAllType( userinfo.getRegionID() );
            List lFactory = dao.getAllFactory( userinfo.getRegionID() );
            List lStyle = dao.getAllStyle( userinfo.getRegionID() );
            List lSource = dao.getAllSource( userinfo.getRegionID() );
            request.setAttribute( "styleList", lStyle );
            request.setAttribute( "sourceList", lSource );
            request.setAttribute( "nameList", lName );
            request.setAttribute( "typeList", lType );
            request.setAttribute( "factoryList", lFactory );
            request.getSession().setAttribute( "type", "3" );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "��ѯ��ʾ�����쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    /**
     * ִ���ۺϲ�ѯ
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     */
    public ActionForward doQuery( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        if( !CheckPower.checkPower( request.getSession(), "90803" ) ){
            return mapping.findForward( "powererror" );
        }
        try{

            ToolsInfoBean bean = ( ToolsInfoBean )form;
            bean.setRegionid( userinfo.getRegionID() );
            List lToolInfo = dao.DoQurey( bean );

            request.getSession().setAttribute( "toolInfo", lToolInfo );
            request.getSession().setAttribute( "type", "1" );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "��ѯ��ʾ�����쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }

    /**
     * ������Ϣ����
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward exportBaseResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws Exception{
        try{

            List list = ( List )request.getSession().getAttribute( "toolInfo" );
            logger.info( "�õ�list" );
            logger.info( "���excel�ɹ�" );

            exportDao.exportBaseResult( list, response );

            return null;
        }
        catch( Exception e ){
            logger.error( "������Ϣ��������쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }

}
