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
     * ȡ�õ�ǰ����ִ�е�ִ����Ϣ
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
    	// ��¼��Ա��Ϣ
    	UserInfo userinfo = (UserInfo)request.getSession().getAttribute("LOGIN_USER");    	
    	StatisticsCurrentPatrolDao dao = new StatisticsCurrentPatrolDao();
    	
    	List planCurrentList = dao.getCurrentPlanInfo(userinfo);
        request.getSession().setAttribute("planinfo", planCurrentList );
        request.getSession().setAttribute("type", "1");
        this.setPageReset(request);
        return mapping.findForward( "statisticscurrentpatrol" );
    }
    
    
    /**
     * ȡ��ָ���ƻ��µ�����Ѳ���߶���Ϣ
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
    	// ȡ��ָ���ļƻ�Id
    	String planid = request.getParameter("planid");
    	StatisticsCurrentPatrolDao dao = new StatisticsCurrentPatrolDao();
    	
    	List sublineList = dao.getSublinePatrolInfo(planid);
    	request.getSession().setAttribute("planid", planid);
        request.getSession().setAttribute("sublineinfo", sublineList );
        request.getSession().setAttribute("type", "2");
        return mapping.findForward( "statisticscurrentpatrol" );
    }
    
    
    /**
     * ȡ��ָ���߶��µ�Ѳ�����Ϣ
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
    	// ȡ��ָ���ļƻ�Id
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
     * ȡ��ָ���ƻ��µ�����©���߶ε���Ϣ
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
    	// ȡ��ָ���ļƻ�Id
    	String planid = request.getParameter("planid");
    	StatisticsCurrentPatrolDao dao = new StatisticsCurrentPatrolDao();
    	
    	List sublineList = dao.getSublineLeakInfo(planid);
    	request.getSession().setAttribute("planid", planid);
        request.getSession().setAttribute("sublineList", sublineList );
        request.getSession().setAttribute("type", "4");
        return mapping.findForward( "statisticscurrentpatrol" );
    }
    
    
    /**
     * ȡ��ָ���߶��µ�©�����Ϣ
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
    	// ȡ��ָ���ļƻ�Id
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
     * ��������ִ�еļƻ���Ϣһ����
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
            logger.info( " ����dao" );
            List planlist = ( List )request.getSession().getAttribute( "planinfo" );
            logger.info( "�õ�list" );
            ExportDao exdao = new ExportDao();
            if( exdao.exportCurrentPlanInfo( planlist, response ) ){
                logger.info( "���excel�ɹ�" );
            }
            return null;
        }
        catch( Exception e ){
            logger.error( "��������ִ�еļƻ���Ϣһ��������쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    
    /**
     * ������Ѳ����߶���Ϣһ����
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
            logger.info( " ����dao" );
            List sublineinfo = ( List )request.getSession().getAttribute( "sublineinfo" );
            logger.info( "�õ�list" );
            ExportDao exdao = new ExportDao();
            if( exdao.exportPlanPatorlSublineInfo( sublineinfo, response ) ){
                logger.info( "���excel�ɹ�" );
            }
            return null;
        }
        catch( Exception e ){
            logger.error( "������Ѳ����߶���Ϣһ��������쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    
    /**
     * ������Ѳ���Ѳ�����Ϣһ����
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
            logger.info( " ����dao" );
            List pointinfo = ( List )request.getSession().getAttribute( "pointinfo" );
            logger.info( "�õ�list" );
            ExportDao exdao = new ExportDao();
            if( exdao.exportPlanPatorlPointInfo( pointinfo, response ) ){
                logger.info( "���excel�ɹ�" );
            }
            return null;
        }
        catch( Exception e ){
            logger.error( "������Ѳ���Ѳ�����Ϣһ��������쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    
    /**
     * ����©����߶���Ϣһ����
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
            logger.info( " ����dao" );
            List sublineList = ( List )request.getSession().getAttribute( "sublineList" );
            logger.info( "�õ�list" );
            ExportDao exdao = new ExportDao();
            if( exdao.exportPlanLeakSublineInfo( sublineList, response ) ){
                logger.info( "���excel�ɹ�" );
            }
            return null;
        }
        catch( Exception e ){
            logger.error( "����©����߶���Ϣһ��������쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
    
    /**
     * ����©���Ѳ�����Ϣһ����
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
            logger.info( " ����dao" );
            List pointinfo = ( List )request.getSession().getAttribute( "pointinfo" );
            logger.info( "�õ�list" );
            ExportDao exdao = new ExportDao();
            if( exdao.exportPlanLeakPointInfo( pointinfo, response ) ){
                logger.info( "���excel�ɹ�" );
            }
            return null;
        }
        catch( Exception e ){
            logger.error( "����©���Ѳ�����Ϣһ��������쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }

}
