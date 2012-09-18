package com.cabletech.planstat.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.planstat.beans.MonthlyReportBean;
import com.cabletech.planstat.services.MonthlyReportBO;

public class MonthlyReportAction  extends BaseDispatchAction{

    private Logger logger = Logger.getLogger(MonthlyReportAction.class.getName());
    public MonthlyReportAction(){
    }

    //查询巡检员/组月工作报表
    public ActionForward queryPatrolmanMonthlyReport( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        return mapping.findForward("queryPatrolmanMonthlyReport");
    }
    
    //查询代维公司月工作报表
    public ActionForward queryConMonthlyReport( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        return mapping.findForward("queryConMonthlyReport");
    }
    
    //查询市移动公司月工作报表
    public ActionForward queryMobileMonthlyReport( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        return mapping.findForward("queryMobileMonthlyReport");
    }

    //查询省移动公司月工作报表
    public ActionForward queryPMobileMonthlyReport( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        return mapping.findForward("queryPMobileMonthlyReport");
    }
    
    //显示巡检员/组月工作报表
    public ActionForward showPatrolmanMonthlyReport( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
    	UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
    	MonthlyReportBean bean = (MonthlyReportBean)form;
    	MonthlyReportBO bo = new MonthlyReportBO();
    	List patrolmanMonthlyReport = bo.getPatrolmanMonthlyReport(bean,userinfo);
        if( patrolmanMonthlyReport.size() == 0 ){
            return forwardInfoPage( mapping, request, "120601" );
        }
        request.getSession().setAttribute( "patrolmanMonthlyReport", patrolmanMonthlyReport);
        return mapping.findForward("showPatrolmanMonthlyReport");
    }
    
    //显示代维公司月工作报表
    public ActionForward showConMonthlyReport( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
    	UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
    	MonthlyReportBean bean = (MonthlyReportBean)form;
    	MonthlyReportBO bo = new MonthlyReportBO();
    	List conMonthlyReport = bo.getConMonthlyReport(bean,userinfo);
        if( conMonthlyReport.size() == 0 ){
            return forwardInfoPage( mapping, request, "120601" );
        }
        request.getSession().setAttribute( "conMonthlyReport", conMonthlyReport);
        return mapping.findForward("showConMonthlyReport");
    }
    
    //显示移动公司月工作报表
    public ActionForward showMobileMonthlyReport( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
    	UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
    	MonthlyReportBean bean = (MonthlyReportBean)form;
    	MonthlyReportBO bo = new MonthlyReportBO();
    	List mobileMonthlyReport = bo.getMobileMonthlyReport(bean,userinfo);
        if( mobileMonthlyReport.size() == 0 ){
            return forwardInfoPage( mapping, request, "120601" );
        }
        request.getSession().setAttribute( "mobileMonthlyReport", mobileMonthlyReport);
        return mapping.findForward("showMobileMonthlyReport");
    }

    //显示省移动公司月工作报表
    public ActionForward showPMobileMonthlyReport( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
    	UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
    	MonthlyReportBean bean = (MonthlyReportBean)form;
    	MonthlyReportBO bo = new MonthlyReportBO();
    	List pMobileMonthlyReport = bo.getPMobileMonthlyReport(bean,userinfo);
        if( pMobileMonthlyReport.size() == 0 ){
            return forwardInfoPage( mapping, request, "120601" );
        }
        request.getSession().setAttribute( "pMobileMonthlyReport", pMobileMonthlyReport);
        return mapping.findForward("showPMobileMonthlyReport");
    }
    
    //显示月工作报表的内容(PDF或EXCEL格式)
    public ActionForward showReportContent( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
    	//response.setCharacterEncoding("UTF-8");
    	//response.setContentType("text/html; charset=UTF-8"); 
    	String theurl = request.getParameter( "theurl" );
        logger.info("the url is:" + theurl);
        
        return null;
    }

    
}
