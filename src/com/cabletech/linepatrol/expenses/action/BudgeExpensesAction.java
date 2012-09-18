package com.cabletech.linepatrol.expenses.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.ContractorBO;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.linepatrol.expenses.beans.BudgetFactorBean;
import com.cabletech.linepatrol.expenses.model.ExpenseBudget;
import com.cabletech.linepatrol.expenses.service.BudgetBO;
import com.cabletech.linepatrol.expenses.service.ExpensesMonthBO;

/**
 * 预算管理
 * 
 * @author Administrator
 * 
 */
public class BudgeExpensesAction extends BaseDispatchAction {
	Logger logger = Logger.getLogger(this.getClass().getName());
	
	
	/**
	 * 转到增加预算费用页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addBudgetForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		ExpensesMonthBO bo = (ExpensesMonthBO) ctx.getBean("expensesMonthBO");
		String regionid = user.getRegionid();
		String type = user.getDeptype();
		if(type.equals("1")){
			List list = bo.getConstractorByDeptId(regionid);
			request.setAttribute("constrators",list);
		}
		return mapping.findForward("addBudgetExpenseForm");
	}
	
	/**
	 * 保存预算
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addBudget(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		BudgetFactorBean bean = (BudgetFactorBean)form;
		WebApplicationContext ctx = getWebApplicationContext();
		BudgetBO bo = (BudgetBO) ctx.getBean("budgetBO");
		boolean ishave = bo.judgeIsHaveBudget(bean);
		if(ishave){
			return forwardInfoPage(mapping, request, "30addBudgetRepeat");	
		}
		bo.saveBudget(bean);
		try {
			ContractorBO contractorBo=new ContractorBO();
			String name=contractorBo.loadContractor(bean.getContractorId()).getContractorName();
		log(request, " 保存预算（预算对象为："+bean.getYear()+"   "+name+"）", " 费用核实");
		}catch(Exception e){}
		return forwardInfoPage(mapping, request, "30addBudgetOK");	
	}
	
	/**
	 * 转到修改预算页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editBudgetForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("LOGIN_USER");
		String id = request.getParameter("id");
		WebApplicationContext ctx = getWebApplicationContext();
		ExpensesMonthBO bo = (ExpensesMonthBO) ctx.getBean("expensesMonthBO");
		String regionid = user.getRegionid();
		String type = user.getDeptype();
		if(type.equals("1")){
			List list = bo.getConstractorByDeptId(regionid);
			request.setAttribute("constrators",list);
		}
		BudgetBO budgeBO = (BudgetBO) ctx.getBean("budgetBO");
		ExpenseBudget budget = budgeBO.getBudgetById(id);
		request.setAttribute("budgetFactorBean", budget);
		return mapping.findForward("editBudgetForm");
	}
	
	/**
	 * 修改预算
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editBudget(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		BudgetFactorBean bean = (BudgetFactorBean)form;
		WebApplicationContext ctx = getWebApplicationContext();
		BudgetBO bo = (BudgetBO) ctx.getBean("budgetBO");
		boolean ishave = bo.judgeIsHaveBudget(bean);
		if(ishave){
			return forwardInfoPage(mapping, request, "30editBudgetRepeat");	
		}
		bo.updateBudget(bean);
		ContractorBO contractorBo=new ContractorBO();
		String name;
		try {
			name = contractorBo.loadContractor(bean.getContractorId()).getContractorName();
			log(request, " 修改预算（预算对象为："+bean.getYear()+"   "+name+"）", " 费用核实");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return forwardInfoPage(mapping, request, "30editBudgetOK");	
	}

	/**
	 * 查询预算
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getBudgetExpense(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		BudgetBO bo = (BudgetBO) ctx.getBean("budgetBO");
		List list = bo.getBudgets();
		request.getSession().setAttribute("budgets",list);
		return mapping.findForward("budgetExpenses");
	}
	
}