package com.cabletech.excel.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.excel.dao.PlanDao;
import com.cabletech.planinfo.beans.YearMonthPlanBean;
import com.cabletech.planstat.domainobjects.PlanStat;
public class PlanExportAction extends BaseDispatchAction{
    Logger logger = Logger.getLogger( this.getClass().getName() );
    private PlanDao plandao = new PlanDao();
    //查询年计划信息结果输出到Excel表
    public ActionForward exportYearPlanResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            //获得查询条件bean
            YearMonthPlanBean bean = ( YearMonthPlanBean )request.getSession().getAttribute( "yplanbean" );
            String contractorid = ( String )request.getSession().getAttribute( "contractorid" );
            if( contractorid != null && !contractorid.equals( "" ) ){
                bean.setDeptid( contractorid );
            }
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
            String fID = request.getParameter( "fID" ); //1, year 2, month

            //重新查询获得list
            List list = plandao.getPlanList(bean,request);
            logger.info( "得到list" );
            plandao.exportYearPlanResult(list, response, userinfo, bean);
            logger.info( "输出excel成功" );
            return null;
        }
        catch( Exception e ){
            logger.error( "导出计划信息结果表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
  
    /**
     * 导出查看计划执行进度列表
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward exportPlanprogresstextResult( ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response ){
            try{
            	// 取得PMType
            	String pmType = (String)request.getSession().getAttribute("PMType");
                
                //获得list
                List progressList = (List)request.getSession().getAttribute( "progressinfo");
                logger.info( "得到list" );
                plandao.exportPlanprogresstextResult(progressList, pmType, response);
                logger.info( "输出excel成功" );
                return null;
            }
            catch( Exception e ){
                logger.error( "导出计划信息结果表出现异常:" + e.getMessage() );
                return forwardErrorPage( mapping, request, "error" );
            }
    }
    
    /**
     * 导出查看计划执行进度列表
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward exportPlanstateResult( ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response ){
            try{
            	 PlanDao plandao = new PlanDao();
            	// 取得PMType
            	String pmType = (String)request.getSession().getAttribute("PMType");    
            	// 巡检员或巡检维护组名            
            	String executorname = (String) request.getSession().getAttribute("executornamesession");
                //获得计划执行详细信息bean
            	PlanStat planStatBean = (PlanStat)request.getSession().getAttribute("PlanStatBeanSession");
                logger.info( "得到list" );
                plandao.exportPlanstateResult(planStatBean, pmType, executorname, response);
                logger.info( "输出excel成功" );
                return null;
            }
            catch( Exception e ){
                logger.error( "导出计划信息结果表出现异常:" + e.getMessage() );
                return forwardErrorPage( mapping, request, "error" );
            }
    }
    
    //
    /**
     * 导出查看计划执行进度列表
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward exportPlanexecuteinfo( ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response ){
            try{
            	 PlanDao plandao = new PlanDao();
            	// 取得PMType
            	String pmType = (String)request.getSession().getAttribute("PMType");    
            	// 得到
            	List  planexecuteList = (List)request.getSession().getAttribute("planexecuteinfo");
                logger.info( "得到list" );
                plandao.exportPlanexecuteinfo(planexecuteList, pmType, response);
                logger.info( "输出excel成功" );
                return null;
            }
            catch( Exception e ){
                logger.error( "导出计划信息结果表出现异常:" + e.getMessage() );
                return forwardErrorPage( mapping, request, "error" );
            }
    }
    
    
    

}
