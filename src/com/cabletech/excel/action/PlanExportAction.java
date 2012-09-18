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
    //��ѯ��ƻ���Ϣ��������Excel��
    public ActionForward exportYearPlanResult( ActionMapping mapping, ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        try{
            //��ò�ѯ����bean
            YearMonthPlanBean bean = ( YearMonthPlanBean )request.getSession().getAttribute( "yplanbean" );
            String contractorid = ( String )request.getSession().getAttribute( "contractorid" );
            if( contractorid != null && !contractorid.equals( "" ) ){
                bean.setDeptid( contractorid );
            }
            UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
            String fID = request.getParameter( "fID" ); //1, year 2, month

            //���²�ѯ���list
            List list = plandao.getPlanList(bean,request);
            logger.info( "�õ�list" );
            plandao.exportYearPlanResult(list, response, userinfo, bean);
            logger.info( "���excel�ɹ�" );
            return null;
        }
        catch( Exception e ){
            logger.error( "�����ƻ���Ϣ���������쳣:" + e.getMessage() );
            return forwardErrorPage( mapping, request, "error" );
        }
    }
    
  
    /**
     * �����鿴�ƻ�ִ�н����б�
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
            	// ȡ��PMType
            	String pmType = (String)request.getSession().getAttribute("PMType");
                
                //���list
                List progressList = (List)request.getSession().getAttribute( "progressinfo");
                logger.info( "�õ�list" );
                plandao.exportPlanprogresstextResult(progressList, pmType, response);
                logger.info( "���excel�ɹ�" );
                return null;
            }
            catch( Exception e ){
                logger.error( "�����ƻ���Ϣ���������쳣:" + e.getMessage() );
                return forwardErrorPage( mapping, request, "error" );
            }
    }
    
    /**
     * �����鿴�ƻ�ִ�н����б�
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
            	// ȡ��PMType
            	String pmType = (String)request.getSession().getAttribute("PMType");    
            	// Ѳ��Ա��Ѳ��ά������            
            	String executorname = (String) request.getSession().getAttribute("executornamesession");
                //��üƻ�ִ����ϸ��Ϣbean
            	PlanStat planStatBean = (PlanStat)request.getSession().getAttribute("PlanStatBeanSession");
                logger.info( "�õ�list" );
                plandao.exportPlanstateResult(planStatBean, pmType, executorname, response);
                logger.info( "���excel�ɹ�" );
                return null;
            }
            catch( Exception e ){
                logger.error( "�����ƻ���Ϣ���������쳣:" + e.getMessage() );
                return forwardErrorPage( mapping, request, "error" );
            }
    }
    
    //
    /**
     * �����鿴�ƻ�ִ�н����б�
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
            	// ȡ��PMType
            	String pmType = (String)request.getSession().getAttribute("PMType");    
            	// �õ�
            	List  planexecuteList = (List)request.getSession().getAttribute("planexecuteinfo");
                logger.info( "�õ�list" );
                plandao.exportPlanexecuteinfo(planexecuteList, pmType, response);
                logger.info( "���excel�ɹ�" );
                return null;
            }
            catch( Exception e ){
                logger.error( "�����ƻ���Ϣ���������쳣:" + e.getMessage() );
                return forwardErrorPage( mapping, request, "error" );
            }
    }
    
    
    

}
