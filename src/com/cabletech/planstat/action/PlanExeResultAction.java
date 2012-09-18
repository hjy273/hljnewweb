package com.cabletech.planstat.action;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.planstat.beans.PlanExeResultBean;
import com.cabletech.planstat.beans.PlanStatBean;
import com.cabletech.planstat.domainobjects.PlanStat;
import com.cabletech.planstat.services.PlanExeResultBO;
import com.cabletech.planstat.services.PlanExeResultBOImpl;


public class PlanExeResultAction extends BaseDispatchAction{

    private Logger logger = Logger.getLogger(PlanExeResultAction.class.getName());
    public PlanExeResultAction(){
    }

    //��ѯ�ƻ�ִ����Ϣ
    public ActionForward queryPlanExeResult( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        PlanExeResultBO planExeResultBO = new PlanExeResultBOImpl();
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        List contractorList = planExeResultBO.getContractorInfoList(userinfo);
        if( contractorList.isEmpty()){
            contractorList = null;
        }
        List patrolmanList = planExeResultBO.getPatrolmanInfoList(userinfo);
        if( patrolmanList.isEmpty()){
            patrolmanList = null;
        }
        request.getSession().setAttribute( "coninfo", contractorList);
        request.getSession().setAttribute( "uinfo", patrolmanList);
        return mapping.findForward("queryPlanExeResult");
    }

    //��ʾ�ƻ�ִ�н��
    public ActionForward showPlanExeResult( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
    	if(request.getParameter("flag")==null&&request.getSession().getAttribute("planExeResultBean")!=null){
    		form=(ActionForm)request.getSession().getAttribute("planExeResultBean");
    	}
        PlanExeResultBean bean = (PlanExeResultBean)form;
        PlanExeResultBO planExeResultBO = new PlanExeResultBOImpl();
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        List planExeResultList = planExeResultBO.getPlanExeResult(userinfo,bean);
        if( planExeResultList.isEmpty()){
            planExeResultList = null;
        }
        request.getSession().setAttribute( "planexecuteinfo", planExeResultList);
    
        this.setPageReset(request);
        request.getSession().setAttribute("planExeResultBean", bean);
        return mapping.findForward("showPlanExeResult");
    }

    //��ʾ�ƻ�ִ�������ϸ��Ϣ������������Ϣ��Ѳ����ϸ��©����ϸ�ȣ�
    public ActionForward showPlanExeDetail( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        String strPlanID = request.getParameter("id");
        if(strPlanID == null) {
        	strPlanID = String.valueOf(request.getSession().getAttribute("planid"));
        }
        String strContractorName = request.getParameter("contractorname");
        String strExecutorName = request.getParameter("executorname");
        if(strExecutorName == null) {
        	strExecutorName = String.valueOf(request.getSession().getAttribute("executorname"));
        }
        PlanExeResultBO planExeResultBO = new PlanExeResultBOImpl();
        PlanStatBean bean = new PlanStatBean();
        try{
            PlanStat data = planExeResultBO.getPlanStat( strPlanID );
            logger.info("Planstat :"+data.toString());
            BeanUtil.objectCopy(data, bean);
            request.setAttribute("PlanStatBean", data);
            request.getSession().setAttribute("PlanStatBeanSession", data);
            
            // add by guixy 2008-12-30 �ж�ҳ�����Ƿ�Ҫ����"���Ӽƻ�δ���ԭ��"��ť
            if(data.getNofinishreason() == null) {
            	Date endDate = data.getEnddate();
            	
            	Calendar calendar = Calendar.getInstance();
            	calendar.setTime(endDate);
            	
            	if(calendar.get(Calendar.DAY_OF_WEEK) == 6  ) {
            		// ����
            		calendar.add(Calendar.DAY_OF_MONTH, 3);
            	} else if( calendar.get(Calendar.DAY_OF_WEEK) == 7 ){
            		// ����
            		calendar.add(Calendar.DAY_OF_MONTH, 2);
            	} else {
            		calendar.add(Calendar.DAY_OF_MONTH, 1);
            	}
            	
            	// ȡ��ϵͳ����
            	Calendar sysDate = Calendar.getInstance();
            	
            	// �жϽ���ʱ�ڵ�һ���������ǲ��ǵ�ǰ��ϵͳ����
            	 if (calendar.get(Calendar.YEAR) == sysDate   
 		                .get(Calendar.YEAR)   
 		                && calendar.get(Calendar.MONTH) == sysDate   
 		                        .get(Calendar.MONTH)   
 		                && calendar.get(Calendar.DAY_OF_MONTH) == sysDate   
 		                        .get(Calendar.DAY_OF_MONTH)) {  
            		 request.setAttribute("writeflg", "0");
 		        }
            }
            // add by guixy 2008-12-30 �ж�ҳ�����Ƿ�Ҫ����"���Ӽƻ�δ���ԭ��"��ť
            
            logger.info("Bean"+bean.toString());
        }
        catch( Exception ex ){
            logger.info( ex.toString() );
        }
        
        List workdayList = planExeResultBO.getWorkdayList(strPlanID);
        List SubLineInfoList = planExeResultBO.getSubLineInfo(strPlanID);
        List patrolList = planExeResultBO.getPatrolList(strPlanID);
        List leakList = planExeResultBO.getLeakList(strPlanID);
        request.getSession().setAttribute( "sublinenamelist", SubLineInfoList);
        request.getSession().setAttribute( "patrollist", patrolList);
        request.getSession().setAttribute( "leaklist", leakList);
        request.getSession().setAttribute( "workdayList", workdayList);
        request.setAttribute("contractorname",strContractorName);
        request.setAttribute("executorname",strExecutorName);
        request.getSession().setAttribute("executornamesession", strExecutorName);
        request.getSession().setAttribute("theplanid", strPlanID);
        return mapping.findForward("showPlanExeDetail");

    }

    //  ��ʾ�ƻ�ִ�н��
    public ActionForward showWorkdayText( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
    	String strpatroldate = request.getParameter("patroldate");
    	String strplanid = request.getParameter("planid");
        PlanExeResultBO planExeResultBO = new PlanExeResultBOImpl();
        List workDayTextList = planExeResultBO.getWorkdayText(strplanid,strpatroldate);
        if( workDayTextList.isEmpty()){
        	workDayTextList = null;
        }
        request.getSession().setAttribute( "workdaytextinfo", workDayTextList);

        return mapping.findForward("showWorkdayText");
    }
    
    //  ��ʾ�ƻ�ִ�н��
    public ActionForward saveNofinishReason( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){ 
    	// �ƻ�id
    	String strPlanId = request.getParameter("planid");
    	request.getSession().setAttribute("planid", strPlanId );
    	String executorname = request.getParameter("executorname");
    	request.getSession().setAttribute("executorname", executorname );
    	String nofinishreason = request.getParameter("nofinishreason");
    	
    	PlanExeResultBO planExeResultBO = new PlanExeResultBOImpl();
    	
    	boolean updateFlg = planExeResultBO.saveReason(strPlanId, nofinishreason );
    	
    	if (updateFlg) {
    		// ����ɹ�
    		return forwardInfoPage( mapping, request, "214reason" );
		} else {
			return forwardErrorPage( mapping, request, "error" );
		}
    	
    	
    }
    
    
    

}
