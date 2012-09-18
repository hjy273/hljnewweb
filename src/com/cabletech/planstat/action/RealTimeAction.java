package com.cabletech.planstat.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.planstat.services.RealTimeBO;
import com.cabletech.planstat.beans.RealTimeConditionBean;


public class RealTimeAction extends BaseDispatchAction {
	private Logger logger = Logger.getLogger( RealTimeAction.class.getName() );
	public ActionForward queryRealTime( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ){
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
		RealTimeBO realTimeBO = new RealTimeBO();
		if( userinfo.getType().equals("11") ){
            List regionList = realTimeBO.getRegionInfoList();
            request.getSession().setAttribute( "reginfo", regionList );
        }
		List contractorList = realTimeBO.getContractorInfoList( userinfo );
		logger.info( "contractorList size: " + contractorList.size() );
        request.getSession().setAttribute( "coninfo", contractorList );
		return mapping.findForward( "queryRealTime" );
	}
	
	public ActionForward queryHistory( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ){
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
		RealTimeBO realTimeBO = new RealTimeBO();
		if( userinfo.getType().equals("11") ){
            List regionList = realTimeBO.getRegionInfoList();
            request.getSession().setAttribute( "reginfo", regionList );
        }
		List contractorList = realTimeBO.getContractorInfoList( userinfo );
		logger.info( "size: " + contractorList.size() );
        request.getSession().setAttribute( "coninfo", contractorList );
		return mapping.findForward( "queryHistory" );
	}	
	
	public ActionForward queryConHistory( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ){
		return mapping.findForward( "queryConHistory" );
	}

	public ActionForward queryRegionHistory( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ){
		return mapping.findForward( "queryRegionHistory" );
	}
	
	
	public ActionForward showRealTime( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ){
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
		RealTimeConditionBean bean = (RealTimeConditionBean)form;
		RealTimeBO realTimeBO = new RealTimeBO();
		List realTimeInfoList = realTimeBO.getRealTimeInfoList(userinfo,bean);
		if (realTimeInfoList != null && realTimeInfoList.size()!=0){
			logger.info( "realtime info's size: " + realTimeInfoList.size() );
		}
		request.getSession().setAttribute( "realtimeinfo", realTimeInfoList );
		request.getSession().setAttribute("mycontractorname", request.getParameter("conname"));
		return mapping.findForward( "showRealTime" );
	}
	
	public ActionForward showConHistory( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ){
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
		RealTimeConditionBean bean = (RealTimeConditionBean)form;
		RealTimeBO realTimeBO = new RealTimeBO();
		List conHistoryInfoList = realTimeBO.getConHistoryInfoList(userinfo,bean);
		if (conHistoryInfoList != null && conHistoryInfoList.size()!=0){
			logger.info( "conHistory sms info's size: " + conHistoryInfoList.size() );
		}
		request.getSession().setAttribute( "conhistoryinfo", conHistoryInfoList );
		request.getSession().setAttribute("mystatdate", bean.getStatDate());
		//request.getSession().setAttribute("mycontractorname", request.getParameter("conname"));
		return mapping.findForward( "showConHistory" );
	}
	
	public ActionForward showRegionHistory( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ){
		UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
		RealTimeConditionBean bean = (RealTimeConditionBean)form;
		RealTimeBO realTimeBO = new RealTimeBO();
		List regionHistoryInfoList = realTimeBO.getRegionHistoryInfoList(userinfo,bean);
		if (regionHistoryInfoList != null && regionHistoryInfoList.size()!=0){
			logger.info( "regionHistory sms info's size: " + regionHistoryInfoList.size() );
		}
		request.getSession().setAttribute( "regionhistoryinfo", regionHistoryInfoList );
		request.getSession().setAttribute("mystatdate", bean.getStatDate());
		return mapping.findForward( "showRegionHistory" );
	}
	
	public ActionForward showHistoryDaysList( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ){
		int days;
		RealTimeConditionBean bean = (RealTimeConditionBean)form;
		if (((String)bean.getEndMonth()).equals("02")){
			if ((Integer.parseInt(bean.getEndYear())%400 ==0) || (Integer.parseInt(bean.getEndYear())%4 ==0 && Integer.parseInt(bean.getEndYear())%100 != 0)){
				days = 29;
			}else{
				days = 28;
			}
		}else{
			if (bean.getEndMonth().equals("04") || bean.getEndMonth().equals("06") ||bean.getEndMonth().equals("09") ||bean.getEndMonth().equals("11")){
				days = 30;
			}else{
				days = 31;
			}
		}
		HashMap map;
		ArrayList daysList = new ArrayList();
		for (int i =1; i <= days; i++){
			map = new HashMap();
		    map.put("day", i + "ºÅ");
		    daysList.add(map);
		}
        request.getSession().setAttribute("realtimeconditionbean", bean);
        request.getSession().setAttribute("mycontractorname", request.getParameter("conname"));
        request.getSession().setAttribute( "dayslist", daysList );
        //request.getSession().setAttribute("theEndYearMonth", (String)bean.getEndYear() + "Äê" + (String)bean.getEndMonth() + "ÔÂ");
		return mapping.findForward( "showHistoryDaysList" );
	}
	
	public ActionForward showHistoryPerDay( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ){
		String strConid = request.getParameter("conid");
		String strYear = request.getParameter("theyear");
		String strMonth = request.getParameter("themonth");
		String strDay = request.getParameter("theday");
		strDay = strDay.substring(0,strDay.length()-1);
        //logger.info("theday:" + strDay );
    	if (strDay.length() == 1){
    		strDay = "0" + strDay;
    	}
    	String strDate = strYear + "-" + strMonth + "-" + strDay;
		RealTimeBO realTimeBO = new RealTimeBO();
		List historyInfoList = realTimeBO.getHistoryInfoList(strConid,strDate);
		//logger.info( "size: " + historyInfoList.size() );
		if( historyInfoList.size() == 0 || historyInfoList == null){
            return forwardInfoPage( mapping, request, "120202" );
        }
		request.getSession().setAttribute( "historyperdayinfo", historyInfoList );
		request.getSession().setAttribute( "historydate", strDate );
		return mapping.findForward( "showHistoryPerDay" );
	}
	
	public ActionForward showRealTimePerCard( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ){
		String strSim = request.getParameter("simnumber");
        String strHandleState = request.getParameter("handlestate");
        RealTimeBO realTimeBO = new RealTimeBO();
        List realTimePerCardInfoList = realTimeBO.getRealTimePerCardInfoList(strSim,strHandleState);
        request.getSession().setAttribute( "realtimepercardinfo", realTimePerCardInfoList );
        return mapping.findForward( "showRealTimePerCard" );
	}
	
	public ActionForward showHistoryPerCard( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ){
		String strSim = request.getParameter("simid");
        String strHandleState = request.getParameter("handlestate");
        String strDate = (String)request.getSession().getAttribute("historydate");
        RealTimeBO realTimeBO = new RealTimeBO();
        List historyPerCardInfoList = realTimeBO.getHistoryPerCardInfoList(strSim,strHandleState,strDate);
        request.getSession().setAttribute( "historypercardinfo", historyPerCardInfoList );
        return mapping.findForward( "showHistoryPerCard" );
	}

	public ActionForward showConHistoryPerCon( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ){
		String strContractorid = request.getParameter("contractorid");
        String strHandleState = request.getParameter("handlestate");
        String strDate = (String)request.getSession().getAttribute("mystatdate");
        RealTimeBO realTimeBO = new RealTimeBO();
        List conHistoryPerConInfoList = realTimeBO.getConHistoryPerConInfoList(strContractorid,strHandleState,strDate);
        request.getSession().setAttribute( "conHistoryPerConInfoList", conHistoryPerConInfoList );
        return mapping.findForward( "showConHistoryPerCon" );
	}

	public ActionForward showRegionHistoryPerReg( ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response ){
		String strRegionid = request.getParameter("regionid");
        String strHandleState = request.getParameter("handlestate");
        String strDate = (String)request.getSession().getAttribute("mystatdate");
        RealTimeBO realTimeBO = new RealTimeBO();
        List regionHistoryPerRegInfoList = realTimeBO.getRegionHistoryPerRegInfoList(strRegionid,strHandleState,strDate);
        request.getSession().setAttribute( "regionHistoryPerRegInfoList", regionHistoryPerRegInfoList );
        return mapping.findForward( "showRegionHistoryPerReg" );
	}
	
	
}
