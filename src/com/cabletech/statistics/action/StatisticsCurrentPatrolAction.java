package com.cabletech.statistics.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.statistics.dao.ExportDao;
import com.cabletech.statistics.dao.StatisticsCurrentPatrolDao;

public class StatisticsCurrentPatrolAction extends StatisticsBaseDispatchAction {
	
    private Logger logger = Logger.getLogger(this.getClass().getName()) ;
    
    /**
     * 取得当前正在执行的执行信息
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward getCurrentPlanInfo( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws Exception {
    	// 登录人员信息
    	UserInfo userinfo = (UserInfo)request.getSession().getAttribute("LOGIN_USER");    	
    	StatisticsCurrentPatrolDao dao = new StatisticsCurrentPatrolDao();
    	
    	List planCurrentList = dao.getCurrentPlanInfo(userinfo);
        request.getSession().setAttribute("planinfo", planCurrentList );
        request.getSession().setAttribute("type", "1");
        this.setPageReset(request);
        return mapping.findForward( "statisticscurrentpatrol" );
    }
    
    
    /**
     * 取得指定计划下的所有巡检线段信息
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward getSublinePatrolInfo( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws Exception {
    	// 取得指定的计划Id
    	String planid = request.getParameter("planid");
    	StatisticsCurrentPatrolDao dao = new StatisticsCurrentPatrolDao();
    	
    	List sublineList = dao.getSublinePatrolInfo(planid);
    	request.getSession().setAttribute("planid", planid);
        request.getSession().setAttribute("sublineinfo", sublineList );
        request.getSession().setAttribute("type", "2");
        return mapping.findForward( "statisticscurrentpatrol" );
    }
    
    
    /**
     * 取得指定线段下的巡检点信息
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward getPointPatrolInfo( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws Exception {
    	// 取得指定的计划Id
    	String planid = request.getParameter("planid");
    	String sublineid = request.getParameter("sublineid");
    	StatisticsCurrentPatrolDao dao = new StatisticsCurrentPatrolDao();
    	
    	List pointList = dao.getPointPatrolInfo(sublineid,planid);
    	request.getSession().setAttribute("planid", planid);
        request.getSession().setAttribute("pointinfo", pointList );
        request.getSession().setAttribute("type", "3");
        return mapping.findForward( "statisticscurrentpatrol" );
    }
    
    /**
     * 取得指定计划下的所有漏检线段的信息
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward getSublineLeakInfo( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws Exception {
    	// 取得指定的计划Id
    	String planid = request.getParameter("planid");
    	StatisticsCurrentPatrolDao dao = new StatisticsCurrentPatrolDao();
    	
    	List sublineList = dao.getSublineLeakInfo(planid);
    	request.getSession().setAttribute("planid", planid);
        request.getSession().setAttribute("sublineList", sublineList );
        request.getSession().setAttribute("type", "4");
        return mapping.findForward( "statisticscurrentpatrol" );
    }
    
    
    /**
     * 取得指定线段下的漏检点信息
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward getPointLeakInfo( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ) throws Exception {
    	// 取得指定的计划Id
    	String sublineid = request.getParameter("sublineid");
    	String planid = request.getParameter("planid");
    	StatisticsCurrentPatrolDao dao = new StatisticsCurrentPatrolDao();
    	
    	List pointList = dao.getPointLeakInfo(sublineid,planid);
    	request.getSession().setAttribute("planid", planid);
        request.getSession().setAttribute("pointinfo", pointList );
        request.getSession().setAttribute("type", "5");
        return mapping.findForward( "statisticscurrentpatrol" );
    }
    
    /**
     * 导出正在执行的计划信息一览表
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     */
    public ActionForward exportPlanCurrentResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            logger.info( " 创建dao" );
            List planlist = ( List )request.getSession().getAttribute( "planinfo" );
            logger.info( "得到list" );
            ExportDao exdao = new ExportDao();
            if( exdao.exportCurrentPlanInfo( planlist, response ) ){
                logger.info( "输出excel成功" );
            }
            return null;
        }
        catch( Exception e ){
            logger.error( "导出正在执行的计划信息一览表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    
    /**
     * 导出已巡检的线段信息一览表
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     */
    public ActionForward exportPlanPatrolSublineResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            logger.info( " 创建dao" );
            List sublineinfo = ( List )request.getSession().getAttribute( "sublineinfo" );
            logger.info( "得到list" );
            ExportDao exdao = new ExportDao();
            if( exdao.exportPlanPatorlSublineInfo( sublineinfo, response ) ){
                logger.info( "输出excel成功" );
            }
            return null;
        }
        catch( Exception e ){
            logger.error( "导出已巡检的线段信息一览表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    
    /**
     * 导出已巡检的巡检点信息一览表
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     */
    public ActionForward exportPlanPatrolPointResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            logger.info( " 创建dao" );
            List pointinfo = ( List )request.getSession().getAttribute( "pointinfo" );
            logger.info( "得到list" );
            ExportDao exdao = new ExportDao();
            if( exdao.exportPlanPatorlPointInfo( pointinfo, response ) ){
                logger.info( "输出excel成功" );
            }
            return null;
        }
        catch( Exception e ){
            logger.error( "导出已巡检的巡检点信息一览表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    
    /**
     * 导出漏检的线段信息一览表
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     */
    public ActionForward exportPlanLeakSublineResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            logger.info( " 创建dao" );
            List sublineList = ( List )request.getSession().getAttribute( "sublineList" );
            logger.info( "得到list" );
            ExportDao exdao = new ExportDao();
            if( exdao.exportPlanLeakSublineInfo( sublineList, response ) ){
                logger.info( "输出excel成功" );
            }
            return null;
        }
        catch( Exception e ){
            logger.error( "导出漏检的线段信息一览表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    
    /**
     * 导出漏检的巡检点信息一览表
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     */
    public ActionForward exportPlanLeakPointResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            logger.info( " 创建dao" );
            List pointinfo = ( List )request.getSession().getAttribute( "pointinfo" );
            logger.info( "得到list" );
            ExportDao exdao = new ExportDao();
            if( exdao.exportPlanLeakPointInfo( pointinfo, response ) ){
                logger.info( "输出excel成功" );
            }
            return null;
        }
        catch( Exception e ){
            logger.error( "导出漏检的巡检点信息一览表出现异常:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }

}
