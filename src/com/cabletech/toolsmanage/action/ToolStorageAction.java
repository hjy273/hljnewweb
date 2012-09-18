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
import com.cabletech.commons.hb.QueryUtil;
import java.sql.ResultSet;

public class ToolStorageAction extends BaseDispatchAction{
    private ToolStorageDao dao = new ToolStorageDao();
    private ToolExportDao exportDao = new ToolExportDao();
    private static Logger logger = Logger.getLogger( ToolStorageAction.class.
                                   getName() );

    public ToolStorageAction(){
    }


    //�鿴���
    public ActionForward showAllStorage( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90701" ) ){
            return mapping.findForward( "powererror" );
        }
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        List lStorageInfo = null;
        try{
            if( userinfo.getDeptype().equals( "2" ) ){ //��ά��λ
                lStorageInfo = dao.getAllStorageForCon( request );
            }
            else{ //�ƶ���˾
                lStorageInfo = dao.getAllStorageForDept( userinfo.getRegionID() );
            }
            request.getSession().setAttribute( "storageinfo", lStorageInfo );
            request.getSession().setAttribute( "type", "st1" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "��ʾ���п����Ϣ����:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //�ۺϲ�ѯ��ʾ
    public ActionForward queryShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90703" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        request.setAttribute( "unittype", userinfo.getDeptype() );
        try{
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            //���b���������б��id�б�
            List partName = dao.getToolNameArr();
            request.setAttribute( "toolname", partName );
            //��ñ��������б�
            List partType = dao.getToolTypeArr();
            request.setAttribute( "tooltype", partType );
            //��ô�ά��λ���ƺ�id
            List contractorname = dao.getContractorNameArr( userinfo.getRegionID() );
            request.setAttribute( "contractorname", contractorname );

            request.getSession().setAttribute( "type", "st3" );
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
        if( !CheckPower.checkPower( request.getSession(), "90703" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        try{
            ToolsInfoBean bean = ( ToolsInfoBean )form;
            List lStorageInfo = null;
            if( userinfo.getDeptype().equals( "1" ) ){ //���ƶ���˾
                bean.setRegionid( userinfo.getRegionID() );
                lStorageInfo = dao.getStorageForDept( bean );
            }
            else{ //�Ǵ�ά��λ
                String contractorid = ( userinfo.getDeptID() );
                bean.setContractorid( contractorid );
                lStorageInfo = dao.getStorageForContractor( bean );
            }

            request.getSession().setAttribute( "storageinfo", lStorageInfo );
            request.getSession().setAttribute( "type", "st1" );
            request.getSession().setAttribute( "bean", bean );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "�ۺϲ�ѯִ���쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //��ʼ�������ʾ
    public ActionForward initStorageShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90702" ) ){
            return mapping.findForward( "powererror" );
        }

        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }
        String contractorname = ( String )request.getSession().getAttribute(
                                "LOGIN_USER_DEPT_NAME" );
        request.setAttribute( "deptname", contractorname );
        request.setAttribute( "deptid", userinfo.getDeptID() );
        request.setAttribute( "username", userinfo.getUserName() );
        request.setAttribute( "userid", userinfo.getUserID() );

        //��÷������ĵ�ǰʱ��
        Date nowDate = new Date();
        DateFormat df = DateFormat.getDateInstance( DateFormat.DATE_FIELD );
        String data = df.format( nowDate );
        request.setAttribute( "date", data );

        //��ñ�����Ϣ,�Թ��û�ѡ��
        List lBaseInfo = dao.getAllInfo();
        request.setAttribute( "baseinfo", lBaseInfo );

        request.getSession().setAttribute( "type", "st2" );
        return mapping.findForward( "success" );
    }


    //ִ�г�ʼ��
    public ActionForward initStorage( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90702" ) ){
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
            String[] essenumber = request.getParameterValues( "essenumber" );
            String[] portmainnumber = request.getParameterValues( "portmainnumber" );
            String[] mainnumber = request.getParameterValues( "mainnumber" );
            bean.setRegionid( userinfo.getRegionID() );

            if( !dao.initStorage( bean, id, essenumber, portmainnumber, mainnumber ) ){
                return forwardInfoPage( mapping, request, "error" );
            }
            log( request, "���Ϲ���", "��ʼ�����" );
            return forwardInfoPage( mapping, request, "80602" );
        }
        catch( Exception e ){
            logger.error( "��ִ�г��ⵥ�г���:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    public ActionForward exportStorageResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws Exception{
        try{
            ToolsInfoBean bean = null;
            if( request.getSession().getAttribute( "bean" ) != null ){
                bean = ( ToolsInfoBean )request.getSession().getAttribute( "bean" );
                //��õ�ǰ�û��ĵ�λ����
                UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
                if( userinfo.getDeptype().equals( "1" ) ){ //���ƶ���˾
                    String sql =
                        "select c.CONTRACTORNAME from contractorinfo c where c.CONTRACTORID = '"
                        + bean.getContractorid() + "'";
                    logger.info( sql );
                    QueryUtil query = new QueryUtil();
                    ResultSet rs = null;
                    rs = query.executeQuery( sql );
                    while( rs.next() ){
                        bean.setContractorname( rs.getString( 1 ) );
                    }
                    logger.info( "cid:" + bean.getContractorid() );
                    logger.info( "��λ���ƣ�" + bean.getContractorname() );
                }
                logger.info( "��ò�ѯ����bean������" );

                logger.info( "id:" + bean.getId() );
                String sql =
                    "select b.name from tool_base b where b.id = '"
                    + bean.getId() + "'";
                logger.info( sql );
                QueryUtil query = new QueryUtil();
                ResultSet rs = null;
                rs = query.executeQuery( sql );
                while( rs.next() ){
                    bean.setName( rs.getString( 1 ) );
                }

                logger.info( "��������:" + bean.getName() );
                logger.info( "�������ͣ�" + bean.getType() );
                logger.info( "��������ڣ�" + bean.getEsselownumber() + " С�� " + bean.getEssehighnumber() );
                logger.info( "���������ڣ�" + bean.getPortlownumber() + " С�� " + bean.getPorthighnumber() );
            }
            List list = ( List )request.getSession().getAttribute( "storageinfo" );
            logger.info( "�õ�list" );
            logger.info( "���excel�ɹ�" );

            exportDao.exportStorageResult( list, bean, response );

            return null;
        }
        catch( Exception e ){
            logger.error( "������Ϣ��������쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
}
