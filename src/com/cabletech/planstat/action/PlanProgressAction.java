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
import com.cabletech.planstat.beans.PatrolStatConditionBean;
import com.cabletech.planstat.services.PlanProgressBO;

public class PlanProgressAction extends BaseDispatchAction{
	private Logger logger = Logger.getLogger( SMNoticePlanStartAction.class.getName() );
	public ActionForward queryProgress( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ){
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
		PatrolStatConditionBean bean = ( PatrolStatConditionBean )form;
		PlanProgressBO progressBO = new PlanProgressBO();
		if( userinfo.getType().equals( "11" ) || userinfo.getType().equals( "21" )){
            List regionList = progressBO.getRegionInfoList();
            request.getSession().setAttribute( "reginfo", regionList );
		}
		//if(!( userinfo.getType().equals( "22" ))){
		    List contractorList = progressBO.getContractorInfoList(userinfo);
		    request.getSession().setAttribute( "coninfo", contractorList );
		//}
		request.getSession().setAttribute( "isshowpanel", "0" );
        return mapping.findForward( "showPlanProgress" );
	}
	
	public ActionForward showProgressText( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ){
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
		PatrolStatConditionBean bean = ( PatrolStatConditionBean )form;
		PlanProgressBO progressBO = new PlanProgressBO();
		List progressList = progressBO.getPlanProgressList(userinfo, bean);
		if( progressList.size() == 0 ){
			request.getSession().setAttribute( "isshowpanel", "0" );
            return forwardInfoPage( mapping, request, "21901" );
        }
		request.getSession().setAttribute( "isshowpanel", "1" );
		request.getSession().setAttribute("mycontractorname", request.getParameter("conname"));
		request.getSession().setAttribute( "progressinfo", progressList );
        return mapping.findForward( "showPlanProgressText" );
	}
	
	public ActionForward showSublineDetailInfo( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ){
		String planid = request.getParameter("planid");
		PlanProgressBO progressBO = new PlanProgressBO();
		List sublineUnfinished = progressBO.getSublineDetailUnfinished(planid);
		List sublineNeverStart = progressBO.getSublineDetailNeverStart(planid);
		List sublineFinished = progressBO.getSublineDetailFinished(planid);
		request.getSession().setAttribute( "sublineUnfinished", sublineUnfinished );
        request.getSession().setAttribute( "sublineNeverStart", sublineNeverStart );
        request.getSession().setAttribute( "sublineFinished", sublineFinished );
		return mapping.findForward( "showSublineForProgressPlan" );
	}	
	
	
}
