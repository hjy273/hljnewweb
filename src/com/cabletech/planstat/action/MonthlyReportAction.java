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

    //��ѯѲ��Ա/���¹�������
    public ActionForward queryPatrolmanMonthlyReport( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        return mapping.findForward("queryPatrolmanMonthlyReport");
    }
    
    //��ѯ��ά��˾�¹�������
    public ActionForward queryConMonthlyReport( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        return mapping.findForward("queryConMonthlyReport");
    }
    
    //��ѯ���ƶ���˾�¹�������
    public ActionForward queryMobileMonthlyReport( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        return mapping.findForward("queryMobileMonthlyReport");
    }

    //��ѯʡ�ƶ���˾�¹�������
    public ActionForward queryPMobileMonthlyReport( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        return mapping.findForward("queryPMobileMonthlyReport");
    }
    
    //��ʾѲ��Ա/���¹�������
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
    
    //��ʾ��ά��˾�¹�������
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
    
    //��ʾ�ƶ���˾�¹�������
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

    //��ʾʡ�ƶ���˾�¹�������
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
    
    //��ʾ�¹������������(PDF��EXCEL��ʽ)
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
