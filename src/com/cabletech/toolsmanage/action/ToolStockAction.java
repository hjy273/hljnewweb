package com.cabletech.toolsmanage.action;

import java.text.*;
import java.util.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.base.*;
import com.cabletech.partmanage.dao.*;
import com.cabletech.power.*;
import com.cabletech.toolsmanage.beans.*;
import com.cabletech.toolsmanage.dao.*;
import com.cabletech.commons.hb.QueryUtil;
import java.sql.ResultSet;

public class ToolStockAction extends BaseDispatchAction{
    private static Logger logger = Logger.getLogger( ToolStockAction.class.
                                   getName() );
    private ToolStockDao stockDao = new ToolStockDao();
    private ToolExportDao exportDao = new ToolExportDao();
    //�����ʾ
    public ActionForward stockShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90502" ) ){
            return mapping.findForward( "powererror" );
        }
        PartRequisitionDao dao = new PartRequisitionDao();
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }
        String deptname = dao.getUserDeptName( userinfo );
        request.setAttribute( "deptname", deptname );
        request.setAttribute( "deptid", userinfo.getDeptID() );
        request.setAttribute( "username", userinfo.getUserName() );
        request.setAttribute( "userid", userinfo.getUserID() );

        //��÷������ĵ�ǰʱ��
        Date nowDate = new Date();
        DateFormat df = DateFormat.getDateInstance( DateFormat.DATE_FIELD );
        String data = df.format( nowDate );
        request.setAttribute( "date", data );

        //��ñ�����Ϣ,�Թ��û�ѡ��
        List lBaseInfo = stockDao.getAllInfo( userinfo.getRegionID() );
        //��ñ�������Ϣ,���û�ѡ��
        List lPatrolid = stockDao.getAllpatrolid( userinfo.getDeptID() );
        request.setAttribute( "patrol", lPatrolid );
        request.setAttribute( "baseinfo", lBaseInfo );
        request.getSession().setAttribute( "type", "2" );
        return mapping.findForward( "success" );
    }


    //ִ�������ⵥ
    public ActionForward addStock( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90502" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }
        try{
            ToolsInfoBean bean = ( ToolsInfoBean )form;
            String[] id = request.getParameterValues( "id" );
            String[] enumber = request.getParameterValues( "enumber" );
            //�ж��Ƿ�û����ⱸ�������������һ��������������û�У������
            for( int i = 0; i < enumber.length; i++ ){
                if( enumber[i].equals( "0" ) || id[i].equals( "" ) ){
                    return forwardErrorPage( mapping, request, "90502e" );
                }
            }

            if( !stockDao.doAddStock( bean, id, enumber, userinfo.getRegionID() ) ){
                return forwardErrorPage( mapping, request, "error" );
            }
            log( request, "��������", "��ӱ�����ⵥ" );
            return forwardInfoPage( mapping, request, "90502" );
        }
        catch( Exception e ){
            logger.error( "�������ⵥ�г���:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //��ʾ������ⵥ
    public ActionForward showAllStock( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90501" ) ){
            return mapping.findForward( "powererror" );
        }

        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }
        try{
            List lReqInfo = stockDao.getAllStock( request );
            request.getSession().setAttribute( "stockinfo", lReqInfo );
            request.getSession().setAttribute( "type", "1" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "��ʾ������ⵥ����:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //��ʾһ����ⵥ����ϸ��Ϣ
    public ActionForward showOneInfo( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90501" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        String stockid = request.getParameter( "stockid" );
        ToolsInfoBean stockinfo = new ToolsInfoBean(); //����ҳ�����ⵥ������Ϣ
        List toolsinfo = null; //�����ϸ��Ϣ

        try{
            //�����ⵥ������Ϣ
            stockinfo = stockDao.getOneStock( stockid );
            request.setAttribute( "stockinfo", stockinfo );

            //�����ⵥ��ⱸ����Ϣ
            toolsinfo = stockDao.getStockTools( stockid );
            request.setAttribute( "toolsinfo", toolsinfo );
            request.getSession().setAttribute( "type", "10" );

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
        if( !CheckPower.checkPower( request.getSession(), "90503" ) ){
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
            //������������
            List lUser = stockDao.getAllUsers( contractorid );
            request.setAttribute( "users", lUser );
            //��ô�ŵص��б�
            List lAdress = stockDao.getAllAdress( contractorid );
            request.setAttribute( "adress", lAdress );
            //��ñ������б�
            List lPatrolid = stockDao.getAllPatrid( contractorid );
            request.setAttribute( "patrolid", lPatrolid );
            request.getSession().setAttribute( "type", "3" );
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
        if( !CheckPower.checkPower( request.getSession(), "90503" ) ){
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
            String contractorid = userinfo.getDeptID();
            bean.setContractorid( contractorid );
            List lStockInfo = this.stockDao.doSearchStock( bean );
            request.getSession().setAttribute( "stockinfo", lStockInfo );
            request.getSession().setAttribute( "type", "1" );
            request.getSession().setAttribute("bean",bean);
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "�ۺϲ�ѯִ���쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


//////////////////////���ϲ���////////////////////////////////
    //������ʾ
    public ActionForward revokeShow( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90602" ) ){
            return mapping.findForward( "powererror" );
        }
        PartRequisitionDao dao = new PartRequisitionDao();
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }
        String deptname = dao.getUserDeptName( userinfo );
        request.setAttribute( "deptname", deptname );
        request.setAttribute( "deptid", userinfo.getDeptID() );
        request.setAttribute( "username", userinfo.getUserName() );
        request.setAttribute( "userid", userinfo.getUserID() );

        //��÷������ĵ�ǰʱ��
        Date nowDate = new Date();
        DateFormat df = DateFormat.getDateInstance( DateFormat.DATE_FIELD );
        String data = df.format( nowDate );
        request.setAttribute( "date", data );

        //��ñ�����Ϣ,�Թ��û�ѡ��
        List lBaseInfo = stockDao.getAllInfoByCon( userinfo.getDeptID() );
        request.setAttribute( "baseinfo", lBaseInfo );
        request.getSession().setAttribute( "type", "revoke2" );
        return mapping.findForward( "success" );
    }


    //ִ����ӱ��ϵ�
    public ActionForward addRevoke( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90602" ) ){
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
            //�ж��Ƿ�û�б��ϱ��������������һ��������������û�У������
            for( int i = 0; i < enumber.length; i++ ){
                if( enumber[i].equals( "0" ) || id[i].equals( "" ) ){
                    return forwardErrorPage( mapping, request, "90502e" );
                }
            }

            if( !stockDao.doAddRevoke( bean, id, enumber ) ){
                return forwardErrorPage( mapping, request, "error" );
            }
            log( request, "��������", "��ӱ������ϵ�" );
            return forwardInfoPage( mapping, request, "90602" );
        }
        catch( Exception e ){
            logger.error( "����ӱ��ϵ��г���:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //��ʾ���б��ϵ�
    public ActionForward showAllRevoke( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90601" ) ){
            return mapping.findForward( "powererror" );
        }

        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }
        try{
            List lReqInfo = stockDao.getAllRevoke( request );
            request.getSession().setAttribute( "stockinfo", lReqInfo );
            request.getSession().setAttribute( "type", "revoke1" );
            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "��ʾ���б��ϵ�����:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //��ʾһ�����ϵ�����ϸ��Ϣ
    public ActionForward showOneRevoke( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90601" ) ){
            return mapping.findForward( "powererror" );
        }
        //��õ�ǰ�û��ĵ�λ����
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute(
                            "LOGIN_USER" );
        if( userinfo.getDeptype().equals( "1" ) ){ //������ƶ���˾�ǲ������
            return forwardErrorPage( mapping, request, "partstockerror" );
        }

        String stockid = request.getParameter( "stockid" );
        ToolsInfoBean revokeinfo = new ToolsInfoBean(); //����ҳ��� ���ϵ�������Ϣ
        List toolsinfo = null; //������ϸ��Ϣ

        try{
            //�����ⵥ������Ϣ
            revokeinfo = stockDao.getOneRevoke( stockid );
            request.setAttribute( "stockinfo", revokeinfo );

            //��ñ��ϵ����ϱ�����Ϣ
            toolsinfo = stockDao.getStockTools( stockid );
            request.setAttribute( "toolsinfo", toolsinfo );
            request.getSession().setAttribute( "type", "revoke10" );

            return mapping.findForward( "success" );
        }
        catch( Exception e ){
            logger.error( "����ʾ������ϸ�г��ִ���:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //�ۺϲ�ѯ��ʾ
    public ActionForward queryShowForRevoke( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90603" ) ){
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
            //��ñ���������
            List lUser = stockDao.getAllUsersForRevoke( contractorid );
            request.setAttribute( "users", lUser );
            //��ñ���ԭ���б�
            List lRemark = stockDao.getAllRemarkForRevoke( contractorid );
            request.setAttribute( "remark", lRemark );
            request.getSession().setAttribute( "type", "revoke3" );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "�ۺϲ�ѯ��ʾ�쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    //�ۺϲ�ѯִ��
    public ActionForward queryExecForRevoke( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        if( !CheckPower.checkPower( request.getSession(), "90603" ) ){
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
            String contractorid = ( String )request.getSession().getAttribute(
                                  "LOGIN_USER_DEPT_ID" );
            bean.setContractorid( contractorid );
            List lStockInfo = this.stockDao.doSearchStockForRevoke( bean );
            request.getSession().setAttribute( "stockinfo", lStockInfo );
            request.getSession().setAttribute( "type", "revoke1" );
            request.getSession().setAttribute( "bean", bean );
            return mapping.findForward( "success" );

        }
        catch( Exception e ){
            logger.error( "�ۺϲ�ѯִ���쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    public ActionForward exportStockResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws Exception{
        try{

            ToolsInfoBean bean = null;
            if( request.getSession().getAttribute( "bean" ) != null ){
                bean = ( ToolsInfoBean )request.getSession().getAttribute( "bean" );
                logger.info( "��ò�ѯ����bean������" );

                logger.info( "id��" + bean.getUserid() );
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

            List list = ( List )request.getSession().getAttribute( "stockinfo" );
            logger.info( "�õ�list" );
            logger.info( "���excel�ɹ�" );

            exportDao.exportStockResult( list, bean, response );

            return null;
        }
        catch( Exception e ){
            logger.error( "������Ϣ��������쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    public ActionForward exportRevokeResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws Exception{
        try{
            ToolsInfoBean bean = null;
            if( request.getSession().getAttribute( "bean" ) != null ){
                bean = ( ToolsInfoBean )request.getSession().getAttribute( "bean" );
                logger.info( "��ò�ѯ����bean������" );

                logger.info( "id��" + bean.getUserid() );
                logger.info( "����ԭ��" + bean.getRemark() );
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
            List list = ( List )request.getSession().getAttribute( "stockinfo" );
            logger.info( "�õ�list" );
            logger.info( "���excel�ɹ�" );

            exportDao.exportRevokeResult( list, bean, response );

            return null;
        }
        catch( Exception e ){
            logger.error( "������Ϣ��������쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }

    //�������ɲ�����ⵥ��ϸ��Ϣһ����
    public ActionForward exportStockList( ActionMapping mapping, ActionForm form,
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
            String begintime = request.getParameter( "begintime" );
            String endtime = request.getParameter( "endtime" );

            ToolsInfoBean bean = ( ToolsInfoBean )form;
            bean.setContractorid( contractorid );
            bean.setUserid(userid);
            bean.setBegintime(begintime);
            bean.setEndtime(endtime);
            if( userid != null ){
                String sql = "select u.USERNAME from userinfo u  where u.USERID = '" + userid + "'";
                QueryUtil query = new QueryUtil();
                ResultSet rs = null;
                rs = query.executeQuery( sql );
                while( rs.next() ){
                    bean.setUsername( rs.getString( 1 ) );
                }
                logger.info( "����ˣ�" + userid );
            }
            List list = stockDao.getStockList( bean );

            exportDao.exportStockList(list, bean, response);

            return null;

        }
        catch( Exception e ){
            logger.error( "������Ϣ�����쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    public ActionForward exportRevokeList( ActionMapping mapping, ActionForm form,
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
            String remark = request.getParameter( "remark" );
            String begintime = request.getParameter( "begintime" );
            String endtime = request.getParameter( "endtime" );

            ToolsInfoBean bean = ( ToolsInfoBean )form;
            bean.setContractorid( contractorid );
            bean.setRemark( remark );
            bean.setUserid( userid );
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
                logger.info( "�����ˣ�" + userid );
            }
            List list = stockDao.getRevokeList( bean );

            exportDao.exportRevokeList(list, bean, response);

            return null;

        }
        catch( Exception e ){
            logger.error( "������Ϣ�����쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
}
