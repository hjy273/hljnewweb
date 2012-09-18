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
     * 新增操作码
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

            String errmsg = "该操作编码已经存在，请调整后重试！";
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
            log( request, " 增加巡检操作信息 ", " 系统管理 " );

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
     * 查询
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
        String sql = "select CONCAT('故',SUBSTR(operationcode,2,3)) operationcode, decode(substr(operationcode,2,1), '1','标石','2','人井','3','电杆','4','其他事故','其他事故') optype, ";
        sql +=
            " operationdes operationdes, decode(emergencylevel, '1','轻微','2','中度','3','严重','其他') emlevel from patroloperation";

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
     * 载入
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
        //System.out.println("操作类型 ："+bean.getOptype());
        bean.setSubcode( bean.getOperationcode().substring( 2 ) );
        //System.out.println("编码 ：" + bean.getSubcode());

        request.setAttribute( "PatrolOpBean", bean );
        return mapping.findForward( "editPatrolOpresult" );
    }


    /**
     * 删除
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
        log( request, " 删除巡检操作信息 ", " 系统管理 " );
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
     * 更新
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
        log( request, " 更新巡检操作信息 ", " 系统管理 " );

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
     * 调用缓存接口
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
            System.out.println( "刷新缓存异常 ：" + e.toString() );
        }

    }


    public ActionForward exportPatrolOpResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            logger.info( " 创建dao" );
            List list = ( List )request.getSession().getAttribute( "queryresult" );
            logger.info( "得到list:" + list.size() );
            super.getService().exportPatrolOpResult( list, response );
            logger.info( "输出excel成功" );
            return null;
        }
        catch( Exception e ){
            logger.error( "导出信息报表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    public ActionForward exportSysLog( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            logger.info( " 创建dao" );
            List list = ( List )request.getSession().getAttribute( "queryresult" );
            logger.info( "得到list" );
            super.getService().exportSysLog( list, response );
            logger.info( "输出excel成功" );
            return null;
        }
        catch( Exception e ){
            logger.error( "导出信息报表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    public ActionForward exportSmsReceiveLog( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            logger.info( " 创建dao" );
            List list = ( List )request.getSession().getAttribute( "queryresult" );
            logger.info( "得到list" );
            super.getService().exportSmsReceiveLog( list, response );
            logger.info( "输出excel成功" );
            return null;
        }
        catch( Exception e ){
            logger.error( "导出信息报表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }


    public ActionForward exportSmsSendLog( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            logger.info( " 创建dao" );
            List list = ( List )request.getSession().getAttribute( "queryresult" );
            logger.info( "得到list" );
            super.getService().exportSmsSendLog( list, response );
            logger.info( "输出excel成功" );
            return null;
        }
        catch( Exception e ){
            logger.error( "导出信息报表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
}
