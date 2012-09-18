package com.cabletech.baseinfo.action;

import java.util.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;
import com.cabletech.baseinfo.beans.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.beans.*;
import com.cabletech.commons.hb.*;
import com.cabletech.commons.sqlbuild.*;
import com.cabletech.commons.web.*;
import com.cabletech.utils.SMSkit.*;
import org.hibernate.*;

public class PatrolOpAction extends BaseInfoBaseDispatchAction{

    private static Logger logger =
        Logger.getLogger( PatrolOpAction.class.getName() );

    public PatrolOpAction(){
    }


    /**
     * ����������
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward addPatrolOp( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        PatrolOpBean bean = ( PatrolOpBean )form;
        //bean.setOperationcode("A"+bean.getOperationcode());

        int flag = super.getService().checkPatrolOpPk( bean.getOperationcode() );

        if( flag == -1 ){

            String errmsg = "�ò��������Ѿ����ڣ�����������ԣ�";
            request.setAttribute( "errmsg", errmsg );

            //System.out.println(errmsg);
            return mapping.findForward( "commonerror" );

        }
        else{
            PatrolOp data = new PatrolOp();
            BeanUtil.objectCopy( bean, data );

            data.setRegionid( super.getLoginUserInfo( request ).getRegionid() );
            super.getService().addPatrolOp( data );

            //Log
            log( request, " ����Ѳ�������Ϣ ", " ϵͳ���� " );

            // commit transaction and close hibernate session
            try{
                HibernateSession.commitTransaction();
            }
            catch( HibernateException e ){
                try{
                    HibernateSession.rollbackTransaction();
                }
                catch( HibernateException e1 ){}
                log.error( e );
            }

            //2005 / 08 / 30
            refreshSMSCache( request );

            return forwardInfoPage( mapping, request, "0301" );
        }
    }


    /**
     * ��ѯ
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward queryPatrolOp( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        PatrolOpBean bean = ( PatrolOpBean )form;
        request.getSession().setAttribute("S_BACK_URL",null); //
        request.getSession().setAttribute("theQueryBean", bean);//
        String opType = request.getParameter( "optype" );
        String subCode = request.getParameter( "opcode" );
        String emergencylevel = request.getParameter( "emergencylevel" );
        String operationdes = request.getParameter( "operationdes" );
        if( emergencylevel == null || emergencylevel.equals( "" ) ){
            emergencylevel = "";
        }
        String sql = "select CONCAT('��',SUBSTR(operationcode,2,3)) operationcode, decode(substr(operationcode,2,1), '1','��ʯ','2','�˾�','3','���','4','�����¹�','�����¹�') optype, ";
        sql +=
            " operationdes operationdes, decode(emergencylevel, '1','��΢','2','�ж�','3','����','����') emlevel from patroloperation";

        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );

        sqlBuild.addConditionAnd( "substr(operationcode,2,1) = {0}", opType );
        sqlBuild.addConditionAnd( "substr(operationcode,3,2) = {0}", subCode );
        sqlBuild.addConditionAnd( "operationdes like {0}",
            "%" + bean.getOperationdes() + "%" );
        sqlBuild.addConditionAnd( "emergencylevel like {0}", "%" + emergencylevel + "%" );
        sqlBuild.addConditionAnd( "operationdes like {0}", "%" + operationdes + "%" );
        //sqlBuild.addRegion(super.getLoginUserInfo(request).getRegionid());

        sqlBuild.addConstant( " order by operationcode" );

        //System.out.println( "SQL: " + sqlBuild.toSql() );

        List list = super.getDbService().queryBeans( sqlBuild.toSql() );

        request.getSession().setAttribute( "queryresult", list );
        return mapping.findForward( "queryPatrolOpresult" );
    }


    /**
     * ����
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward loadPatrolOp( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        String id = request.getParameter("id");
        id = "A" + id.substring(1,id.length());
        PatrolOp data = super.getService().loadPatrolOp( id);
        PatrolOpBean bean = new PatrolOpBean();
        BeanUtil.objectCopy( data, bean );

        bean.setOptype( bean.getOperationcode().substring( 1, 2 ) );
        //System.out.println("�������� ��"+bean.getOptype());
        bean.setSubcode( bean.getOperationcode().substring( 2 ) );
        //System.out.println("���� ��" + bean.getSubcode());

        request.setAttribute( "PatrolOpBean", bean );
        return mapping.findForward( "editPatrolOpresult" );
    }


    /**
     * ɾ��
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward deletePatrolOp( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        String id = request.getParameter("id");
        id = "A" + id.substring(1,id.length());
        PatrolOp data = super.getService().loadPatrolOp( id);
        super.getService().removePatrolOp( data );
        //Log
        log( request, " ɾ��Ѳ�������Ϣ ", " ϵͳ���� " );
		 String strQueryString = getTotalQueryString("method=queryPatrolOp&operationdes=",(PatrolOpBean)request.getSession().getAttribute("theQueryBean"));
		 Object[] args = getURLArgs("/WebApp/PatrolOpAction.do",strQueryString,(String)request.getSession().getAttribute( "S_BACK_URL" ));
		 return forwardInfoPage( mapping, request, "0303",null,args);
    }
    
    public String getTotalQueryString(String queryString,PatrolOpBean bean){
    	if (bean.getOperationdes() != null){
    		queryString = queryString + bean.getOperationdes();
    	}
    	if (bean.getId()!= null){
    		queryString = queryString + "&id=" + bean.getId();
    	}
    	if (bean.getOperationcode() != null){
    		queryString = queryString + "&operationcode=" + bean.getOperationcode();
    	}
    	if (bean.getEmergencylevel() != null){
    		queryString = queryString + "&emergencylevel=" + bean.getEmergencylevel();
    	}
    	return queryString;
    }

    /**
     * ����
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws ClientException
     * @throws Exception
     */
    public ActionForward updatePatrolOp( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        //bean.v
        PatrolOp data = new PatrolOp();
        BeanUtil.objectCopy( ( PatrolOpBean )form, data );

        super.getService().updatePatrolOp( data );
        //Log
        log( request, " ����Ѳ�������Ϣ ", " ϵͳ���� " );

        // commit transaction and close hibernate session
        try{
            HibernateSession.commitTransaction();
        }
        catch( HibernateException e ){
            try{
                HibernateSession.rollbackTransaction();
            }
            catch( HibernateException e1 ){}
            log.error( e );
        }

        //2005 / 08 / 30
        refreshSMSCache( request );
        String strQueryString = getTotalQueryString("method=queryPatrolOp&operationdes=",(PatrolOpBean)request.getSession().getAttribute("theQueryBean"));
		 Object[] args = getURLArgs("/WebApp/PatrolOpAction.do",strQueryString,(String)request.getSession().getAttribute( "S_BACK_URL" ));
		 return forwardInfoPage( mapping, request, "0302",null,args);
    }


    /**
     * ���û���ӿ�
     * @param request HttpServletRequest
     * @throws Exception
     */
    public void refreshSMSCache( HttpServletRequest request ) throws Exception{

        try{
            String[] infoArr = new String[1];
            infoArr[0] = super.getLoginUserInfo( request ).getUserID();
            SmsUtil smsutil = SmsUtil.getInstance();
            smsutil.refreshCache( infoArr );
        }
        catch( Exception e ){
            System.out.println( "ˢ�»����쳣 ��" + e.toString() );
        }

    }


    public ActionForward exportPatrolOpResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            logger.info( " ����dao" );
            List list = ( List )request.getSession().getAttribute( "queryresult" );
            logger.info( "�õ�list:" + list.size() );
            super.getService().exportPatrolOpResult( list, response );
            logger.info( "���excel�ɹ�" );
            return null;
        }
        catch( Exception e ){
            logger.error( "������Ϣ��������쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    public ActionForward exportSysLog( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            logger.info( " ����dao" );
            List list = ( List )request.getSession().getAttribute( "queryresult" );
            logger.info( "�õ�list" );
            super.getService().exportSysLog( list, response );
            logger.info( "���excel�ɹ�" );
            return null;
        }
        catch( Exception e ){
            logger.error( "������Ϣ��������쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    public ActionForward exportSmsReceiveLog( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            logger.info( " ����dao" );
            List list = ( List )request.getSession().getAttribute( "queryresult" );
            logger.info( "�õ�list" );
            super.getService().exportSmsReceiveLog( list, response );
            logger.info( "���excel�ɹ�" );
            return null;
        }
        catch( Exception e ){
            logger.error( "������Ϣ��������쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    public ActionForward exportSmsSendLog( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            logger.info( " ����dao" );
            List list = ( List )request.getSession().getAttribute( "queryresult" );
            logger.info( "�õ�list" );
            super.getService().exportSmsSendLog( list, response );
            logger.info( "���excel�ɹ�" );
            return null;
        }
        catch( Exception e ){
            logger.error( "������Ϣ��������쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
}
