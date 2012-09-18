package com.cabletech.baseinfo.action;

import java.util.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;
import com.cabletech.baseinfo.beans.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.beans.*;
import com.cabletech.commons.sqlbuild.*;
import com.cabletech.commons.web.*;
import com.cabletech.power.*;

/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Cable tech</p>
 * @author not attributable
 * @version 1.0
 */
public class TaskOperationAction extends BaseInfoBaseDispatchAction{

    private static Logger logger =
        Logger.getLogger( TaskOperationAction.class.getName() );
    /**
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ClientException
     * @throws Exception
     * @return ActionForward
     */
    public ActionForward addTaskOperation( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
       /* if( !CheckPower.checkPower( request.getSession(), "71402" ) ){
            return mapping.findForward( "powererror" );
        }*/

        TaskOperationBean bean = ( TaskOperationBean )form;
        TaskOperation data = new TaskOperation();
        BeanUtil.objectCopy( bean, data );

        data.setID( super.getDbService().getSeq( "TaskOperation", 8 ) );

        super.getService().createTaskOperation( data );
        //Log
        log( request, " 增加巡检操作信息（巡检操作名称为："+bean.getOperationName()+"） ", " 系统管理 " );

        return forwardInfoPage( mapping, request, "0901" );
    }


    /**
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ClientException
     * @throws Exception
     * @return ActionForward
     */
    public ActionForward loadTaskOperation( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
      /*  if( !CheckPower.checkPower( request.getSession(), "71404" ) ){
            return mapping.findForward( "powererror" );
        }*/

        TaskOperation data = super.getService().loadTaskOperation( request.
                             getParameter( "id" ) );
        TaskOperationBean bean = new TaskOperationBean();
        BeanUtil.objectCopy( data, bean );
        request.setAttribute( "TaskOperationBean", bean );
        return mapping.findForward( "updateTaskOperation" );
    }


    /**
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ClientException
     * @throws Exception
     * @return ActionForward
     */
    public ActionForward deleteTaskOperation( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
      /*  if( !CheckPower.checkPower( request.getSession(), "71405" ) ){
            return mapping.findForward( "powererror" );
        }*/

        TaskOperation data = super.getService().loadTaskOperation( request.
                             getParameter( "id" ) );
        super.getService().removeTaskOperation( data );
        //Log
        log( request, " 删除巡检操作信息 ", " 系统管理 " );
        String strQueryString = getTotalQueryString("method=queryTaskOperation&operationName=",(TaskOperationBean)request.getSession().getAttribute("theQueryBean"));
		 Object[] args = getURLArgs("/WebApp/TaskOperationAction.do",strQueryString,(String)request.getSession().getAttribute( "S_BACK_URL" ));
		 return forwardInfoPage( mapping, request, "0903",null,args);
    }


    /**
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ClientException
     * @throws Exception
     * @return ActionForward
     */
    public ActionForward updateTaskOperation( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{

        //bean.v
        TaskOperation data = new TaskOperation();
        TaskOperationBean bean=(TaskOperationBean)form;
        BeanUtil.objectCopy( bean, data );
        //data.setID(super.getDbService().getSeq("taskoperation", 10));
        super.getService().updateTaskOperation( data );
        //Log
        log( request, " 更新巡检操作信息（巡检操作名称为："+bean.getOperationName()+"） ", " 系统管理 " );
        String strQueryString = getTotalQueryString("method=queryTaskOperation&operationName=",(TaskOperationBean)request.getSession().getAttribute("theQueryBean"));
		 Object[] args = getURLArgs("/WebApp/TaskOperationAction.do",strQueryString,(String)request.getSession().getAttribute( "S_BACK_URL" ));
		 return forwardInfoPage( mapping, request, "0902",null,args);
    }


    public ActionForward queryTaskOperation( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws
        ClientException, Exception{
    	TaskOperationBean bean = (TaskOperationBean)form;
    	request.getSession().setAttribute("S_BACK_URL",null); //
        request.getSession().setAttribute("theQueryBean", bean);//
        String sql =
            "select id,operationname,operationdes,regionid from taskoperation";

        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );

        sqlBuild.addConditionAnd( "operationname like {0}",
            "%" +
            ( ( TaskOperationBean )form ).getOperationName() +
            "%" );
        //sqlBuild.addRegion(super.getLoginUserInfo(request).getRegionid());
        List list = super.getDbService().queryBeans( sqlBuild.toSql() );

        request.getSession().setAttribute( "queryresult", list );
        return mapping.findForward( "queryTaskOperation" );
    }
    
    public String getTotalQueryString(String queryString,TaskOperationBean bean){
    	if (bean.getOperationName()!= null){
    		queryString = queryString + bean.getOperationName();
    	}
    	if (bean.getId()!= null){
    		queryString = queryString + "&id=" + bean.getId();
    	}
    	if (bean.getRegionID() != null){
    		queryString = queryString + "&regionID=" + bean.getRegionID();
    	}
    	if (bean.getOperationDes() != null){
    		queryString = queryString + "&operationDes=" + bean.getOperationDes();
    	}
    	return queryString;
    }

    public TaskOperationAction(){
    }


    public ActionForward exportTaskOperationResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            logger.info( " 创建dao" );
            List list = ( List )request.getSession().getAttribute( "queryresult" );
            logger.info( "得到list" );
            super.getService().exportTaskOperationResult( list, response );
            logger.info( "输出excel成功" );
            return null;
        }
        catch( Exception e ){
            logger.error( "导出信息报表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
}
