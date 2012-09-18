package com.cabletech.analysis.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cabletech.analysis.services.LeakSublineBO;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.power.CheckPower;

public class LeakSublineAction extends BaseDispatchAction {
	Logger logger = Logger.getLogger( this.getClass().getName() );
    /**
     * 
     * 为市移动用户显示市内各代维公司漏做巡检任务结果
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward showLeakSubline412( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
		// 判断是否有户显示市内各代维公司漏做巡检任务结果的权限
		if (!CheckPower.checkPower(request.getSession(), "121101")) {
			return mapping.findForward("powererror");
		}
    	LeakSublineBO bo = new LeakSublineBO();
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        String regionName = bo.getRegionName(userinfo.getRegionID());
        List leakSublineList412 = bo.getLeakSublineList412(userinfo.getRegionid());
        request.getSession().setAttribute( "regionName412", regionName);
        request.getSession().setAttribute( "leakSublineList412", leakSublineList412);
        this.setPageReset(request);
        return mapping.findForward("showLeakSublineList412");
    }
    
    /**
     * 为市代维用户显示其下各巡检组漏做巡检任务结果
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward showLeakSubline422( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
		// 为市代维用户显示其下各巡检组漏做巡检任务结果的权限
		if (!CheckPower.checkPower(request.getSession(), "121102")) {
			return mapping.findForward("powererror");
		}
    	LeakSublineBO bo = new LeakSublineBO();
        UserInfo userinfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
        String conName = bo.getConName(userinfo.getDeptID());
        List leakSublineList422 = bo.getLeakSublineList422(userinfo.getRegionid(),userinfo.getDeptID());
        request.getSession().setAttribute( "conName422", conName);
        request.getSession().setAttribute( "leakSublineList422", leakSublineList422);
        return mapping.findForward("showLeakSublineList422");
    }
    
    /**
     * 为市代维用户显示其下某一具体巡检组漏做巡检任务线段详细信息
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward showLeakSublineDetail422( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
    	String patrolID = request.getParameter("id");
    	String patrolName = request.getParameter("patrolname");
    	LeakSublineBO bo = new LeakSublineBO();
        List leakSublineDetail422 = bo.getLeakSublineDetail422(patrolID);
        request.getSession().setAttribute( "leakSublineDetail422", leakSublineDetail422);
        request.setAttribute("patrolName422", patrolName);
        return mapping.findForward("showLeakSublineDetail422");
    }
    
    /**
     * 为市移动用户显示市内具体某一代维公司漏做巡检任务线段详细信息
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward showLeakSublineDetail412( ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response ){
    	String conID = request.getParameter("id");
    	String conName = request.getParameter("conname");
    	LeakSublineBO bo = new LeakSublineBO();
        List leakSublineDetail412 = bo.getLeakSublineDetail412(conID);
        request.getSession().setAttribute( "leakSublineDetail412", leakSublineDetail412);
        request.setAttribute("conName412", conName);
        return mapping.findForward("showLeakSublineDetail412");
    }
}
