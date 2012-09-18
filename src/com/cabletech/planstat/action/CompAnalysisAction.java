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

import com.cabletech.planstat.beans.CompAnalysisBean;
import com.cabletech.planstat.services.CompAnalysisBO;



public class CompAnalysisAction extends BaseDispatchAction {
	Logger logger = Logger.getLogger( this.getClass().getName() );
    //查询巡检员/维护组对比分析(包括横纵向)
    public ActionForward queryPmCompAnalysis( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
    	CompAnalysisBO compAnalysisBO = new CompAnalysisBO();
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        List contractorList = compAnalysisBO.getContractorInfoList(userinfo);
        List patrolmanList = compAnalysisBO.getPatrolmanInfoList(userinfo);
        if( userinfo.getType().equals("11") ){
            List regionList = compAnalysisBO.getRegionInfoList();
            request.getSession().setAttribute( "reginfo", regionList );
        }
        request.getSession().setAttribute( "coninfo", contractorList);
        request.getSession().setAttribute( "uinfo", patrolmanList);
        return mapping.findForward("queryPmCompAnalysis");
    }
    
    
//  查询代维公司对比分析(包括横纵向)
    public ActionForward queryConCompAnalysis( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
    	CompAnalysisBO compAnalysisBO = new CompAnalysisBO();
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        List contractorList = compAnalysisBO.getContractorInfoList(userinfo);
        if( userinfo.getType().equals("11") ){
            List regionList = compAnalysisBO.getRegionInfoList();
            request.getSession().setAttribute( "reginfo", regionList );
        }
        request.getSession().setAttribute( "coninfo", contractorList);
        return mapping.findForward("queryComCompAnalysis");
    }
    
    //显示巡检员/维护组对比分析结果
    public ActionForward showPmCompAnalysis( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        CompAnalysisBean bean = (CompAnalysisBean)form;
        CompAnalysisBO compAnalysisBO = new CompAnalysisBO();
        String compType = (String)request.getParameter("thecomptype");
        
        //compType为1表示纵向对比分析,2为横向对比分析
        if ("1".equals(compType)){
        	List pmCompVList = compAnalysisBO.getPmVResult(bean);
        	if( pmCompVList.size() < 1 ){
                return forwardInfoPage( mapping, request, "120301" );
            }
        	request.getSession().setAttribute( "pmCompVInfo", pmCompVList);
        	String theYearMonth = bean.getEndYear() + "年" + bean.getStartMonth() + "月-" + bean.getEndMonth() + "月";
        	request.getSession().setAttribute("YMForPmComp", theYearMonth);
        	request.getSession().setAttribute("CompType", "V");
        }else{
        	List pmCompHList = compAnalysisBO.getPmHResult(bean);
        	if( pmCompHList.size() == 0 ){
                return forwardInfoPage( mapping, request, "120301" );
            }
        	request.getSession().setAttribute( "pmCompHInfo", pmCompHList);
        	String theYearMonth = bean.getEndYear() + "年" + bean.getTheMonth() + "月";
        	request.getSession().setAttribute("YMForPmComp", theYearMonth);
        	request.getSession().setAttribute("CompType", "H");
        }
        return mapping.findForward("showpmCompAnalysis");
    }
    
//  显示代维公司对比分析结果
    public ActionForward showComCompAnalysis( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
        CompAnalysisBean bean = (CompAnalysisBean)form;
        CompAnalysisBO compAnalysisBO = new CompAnalysisBO();
        String compType = (String)request.getParameter("thecomptype");
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        if ("12".equals(userinfo.getType())){
        	bean.setRegionId(userinfo.getRegionid());
        }
        //compType为1表示纵向对比分析,2为横向对比分析
        if ("1".equals(compType)){
        	List comCompVList = compAnalysisBO.getComVResult(bean);
        	if( comCompVList.size() <= 1 ){
                return forwardInfoPage( mapping, request, "120302" );
            }
        	request.getSession().setAttribute( "comCompVInfo", comCompVList);
        	String theYearMonth = bean.getEndYear() + "年" + bean.getStartMonth() + "月-" + bean.getEndMonth() + "月";
        	request.getSession().setAttribute("YMForComComp", theYearMonth);
        	request.getSession().setAttribute("CompType", "V");
        }else{
        	List comCompHList = compAnalysisBO.getComHResult(bean,userinfo);
        	if( comCompHList.size() == 0 ){
                return forwardInfoPage( mapping, request, "120302" );
            }
        	request.getSession().setAttribute( "comCompHInfo", comCompHList);
        	String theYearMonth = bean.getEndYear() + "年" + bean.getTheMonth() + "月";
        	request.getSession().setAttribute("YMForComComp", theYearMonth);
        	request.getSession().setAttribute("CompType", "H");
        }
        return mapping.findForward("showComCompAnalysis");
    }
    
}
