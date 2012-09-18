package com.cabletech.linepatrol.appraise.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.ContractorBO;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.linepatrol.appraise.beans.AppraiseMonthStatBean;
import com.cabletech.linepatrol.appraise.module.AppraiseMonthStat;
import com.cabletech.linepatrol.appraise.service.AppraiseMonthStatBO;

/**
 * 代维月考核统计
 * 
 * @author liusq
 * 
 */
public class AppraiseMonthStatAction extends BaseDispatchAction {

	private static final long serialVersionUID = 7694106710160535882L;

	/**
	 * 月考核统计
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward appraiseMonthStat(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String init = request.getParameter("init");
		AppraiseMonthStatBean bean = (AppraiseMonthStatBean)form;
		
		//加载代维单位
		ContractorBO contractorBO = new ContractorBO();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List<Contractor> cons = contractorBO.getAllContractor(userInfo);
		
		WebApplicationContext ctx = getWebApplicationContext();
		AppraiseMonthStatBO appraiseMonthStatBO = (AppraiseMonthStatBO) ctx.getBean("appraiseMonthStatBO");
		List<AppraiseMonthStat> monthStatList = null;
		
		//首次加载不查询
		if(!StringUtils.equals("init", init)){
			monthStatList = appraiseMonthStatBO.monthStatAppraise(bean);
		}
		
		request.getSession().setAttribute("cons", cons);
		request.getSession().setAttribute("monthStatList", monthStatList);
		return mapping.findForward("appraiseMonthStatList");
	}

}
