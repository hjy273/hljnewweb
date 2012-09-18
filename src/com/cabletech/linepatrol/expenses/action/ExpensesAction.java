package com.cabletech.linepatrol.expenses.action;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.ContractorBO;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.linepatrol.expenses.beans.ExpenseAffirmBean;
import com.cabletech.linepatrol.expenses.beans.ExpenseFactorBean;
import com.cabletech.linepatrol.expenses.beans.ExpensePriceBean;
import com.cabletech.linepatrol.expenses.model.ExpenseAffirm;
import com.cabletech.linepatrol.expenses.model.ExpenseBudget;
import com.cabletech.linepatrol.expenses.model.ExpenseGradeFactor;
import com.cabletech.linepatrol.expenses.model.ExpenseMonth;
import com.cabletech.linepatrol.expenses.model.ExpenseUnitPrice;
import com.cabletech.linepatrol.expenses.service.ExpenseConstant;
import com.cabletech.linepatrol.expenses.service.ExpenseExportBO;
import com.cabletech.linepatrol.expenses.service.ExpenseGradeFactorBO;
import com.cabletech.linepatrol.expenses.service.ExpensesMonthBO;
import com.cabletech.linepatrol.expenses.service.ExpensesUnitPriceBO;

/**
 * 费用核实
 * 
 * @author Administrator
 * 
 */
public class ExpensesAction extends BaseDispatchAction {
	Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * 转到分级取费系数列表页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward cableGradeFactorList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		ExpenseGradeFactorBO factorBO = (ExpenseGradeFactorBO) ctx
		.getBean("expenseGradeFactorBO");
		List list = factorBO.getFacotorList((UserInfo) user);
		request.getSession().setAttribute("list", list);
		return mapping.findForward("cableGradeFactorList");
	}

	/**
	 * 转到新增分级取费系数页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward cableGradeFactor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		ExpensesMonthBO expBO = (ExpensesMonthBO) ctx
		.getBean("expensesMonthBO");
		String deptype = user.getDeptype();
		String regionid = user.getRegionid();
		if (deptype.equals("1")) {
			List list = expBO.getConstractorByDeptId(regionid);
			request.setAttribute("constrators", list);
		}
		return mapping.findForward("addFactorForm");
	}

	/**
	 * 保存分级取费系数
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveFactor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			ExpenseFactorBean bean = (ExpenseFactorBean) form;
			ExpenseGradeFactor factor = new ExpenseGradeFactor();
			BeanUtil.objectCopy(bean, factor);
			String conid = bean.getContractorid();
			WebApplicationContext ctx = getWebApplicationContext();
			ExpenseGradeFactorBO factorBO = (ExpenseGradeFactorBO) ctx
			.getBean("expenseGradeFactorBO");
			ContractorBO contractorBo=new ContractorBO();
			String name=contractorBo.loadContractor(bean.getContractorid()).getContractorName();
			boolean isHave = factorBO.judgeIsHaveFactor(conid, factor);
			if (isHave) {
				return forwardInfoPage(mapping, request, "30addFactorRepeat");
			}
			factorBO.saveFactor(factor);
			log(request, " 保存分级取费系数（对象为："+name+"  "+bean.getExplain()+"）", " 费用核实 ");
			return forwardInfoPage(mapping, request, "30addFactorOK");
		} catch (Exception e) {
			logger.error("保存分级取费系数出现异常:" + e.getMessage());
			e.printStackTrace();
		}
		return forwardErrorPage(mapping, request, "error");
	}

	/**
	 * 转到修改分级取费系数页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editFactorForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("LOGIN_USER");
		String tid = request.getParameter("tid");
		WebApplicationContext ctx = getWebApplicationContext();
		ExpenseGradeFactorBO factorBO = (ExpenseGradeFactorBO) ctx
		.getBean("expenseGradeFactorBO");
		ExpensesMonthBO expBO = (ExpensesMonthBO) ctx
		.getBean("expensesMonthBO");
		ExpenseGradeFactor factor = factorBO.getFactorById(tid);
		String deptype = user.getDeptype();
		String regionid = user.getRegionid();
		if (deptype.equals("1")) {
			List list = expBO.getConstractorByDeptId(regionid);
			request.setAttribute("constrators", list);
		}
		request.setAttribute("expenseFactorBean", factor);
		return mapping.findForward("editFactorForm");
	}

	/**
	 * 修改分级取费系数
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editFactor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			ExpenseFactorBean bean = (ExpenseFactorBean) form;
			ExpenseGradeFactor factor = new ExpenseGradeFactor();
			String contractorid = bean.getContractorid();
			WebApplicationContext ctx = getWebApplicationContext();
			ExpenseGradeFactorBO factorBO = (ExpenseGradeFactorBO) ctx
			.getBean("expenseGradeFactorBO");
			BeanUtil.objectCopy(bean, factor);
			boolean isHave = factorBO.judgeIsHaveFactor(contractorid, factor);
			if (isHave) {
				return forwardInfoPage(mapping, request, "30eidtFactorRepeat");
			}
			factorBO.updateFactor(factor);
			ContractorBO contractorBo=new ContractorBO();
			String name=contractorBo.loadContractor(bean.getContractorid()).getContractorName();
			log(request, " 修改分级取费系数（对象为："+name+"  "+bean.getExplain()+"） ", " 费用核实");
			return forwardInfoPage(mapping, request, "30editFactorOK");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return forwardErrorPage(mapping, request, "error");
	}

	/**
	 * 取费单价列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward cableUnitPriceList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		ExpensesUnitPriceBO bo = (ExpensesUnitPriceBO) ctx
		.getBean("expensesUnitPriceBO");
		List list = bo.getUnitPriceList(user);
		request.getSession().setAttribute("list", list);
		return mapping.findForward("unitPriceList");
	}

	/**
	 * 转到光缆维护级别取费单价列表页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward cableUnitPrice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		ExpensesMonthBO expBO = (ExpensesMonthBO) ctx
		.getBean("expensesMonthBO");
		String regionid = user.getRegionid();
		String type = user.getDeptype();
		if (type.equals("1")) {
			List list = expBO.getConstractorByDeptId(regionid);
			request.setAttribute("constrators", list);
		}
		return mapping.findForward("addUnitPriceForm");
	}

	/**
	 * 保存维护单价
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addUnitPrice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		ExpensesUnitPriceBO bo = (ExpensesUnitPriceBO) ctx
		.getBean("expensesUnitPriceBO");
		ExpensePriceBean bean = (ExpensePriceBean) form;
		ExpenseUnitPrice price = new ExpenseUnitPrice();
		try {
			BeanUtil.objectCopy(bean, price);
			boolean isHave = bo.judgeIsHaveUnitPrice(price);
			if (isHave) {
				return forwardInfoPage(mapping, request, "30addPriceRepeat");
			}

			bo.saveUnitPrice(price,bean);
			ContractorBO contractorBo=new ContractorBO();
			String name=contractorBo.loadContractor(bean.getContractorid()).getContractorName();
			log(request, " 保存维护单价（对象为："+name+"）", " 费用核实 ");
			return forwardInfoPage(mapping, request, "30addPriceOK");
		} catch (Exception e) {
			logger.info("保存维护单价出现问题:" + e.getMessage());
			e.printStackTrace();
		}
		return forwardErrorPage(mapping, request, "error");
	}

	/**
	 * 转到维护单价的页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editCableUnitPriceForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("LOGIN_USER");
		String id = request.getParameter("tid");
		WebApplicationContext ctx = getWebApplicationContext();
		ExpensesUnitPriceBO bo = (ExpensesUnitPriceBO) ctx
		.getBean("expensesUnitPriceBO");
		ExpensesMonthBO expBO = (ExpensesMonthBO) ctx
		.getBean("expensesMonthBO");
		ExpenseUnitPrice price = bo.getTypePriceById(id);
		String regionid = user.getRegionid();
		List list = expBO.getConstractorByDeptId(regionid);
		request.setAttribute("constrators", list);
		request.setAttribute("expensePriceBean", price);
		return mapping.findForward("editCableUnitPriceForm");
	}

	/**
	 * 保存光缆维护级别取费单价
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editCableUnitPrice(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		ExpensesUnitPriceBO bo = (ExpensesUnitPriceBO) ctx
		.getBean("expensesUnitPriceBO");
		ExpensePriceBean bean = (ExpensePriceBean) form;
		ExpenseUnitPrice price = new ExpenseUnitPrice();
		try {
			BeanUtil.objectCopy(bean, price);
			boolean isHave = bo.judgeIsHaveUnitPrice(price);
			if (isHave) {
				return forwardInfoPage(mapping, request, "30editPriceRepeat");
			}
			bo.saveUnitPrice(price,bean);
			return forwardInfoPage(mapping, request, "30editCablePriceOK");
		} catch (Exception e) {
			logger.info("修改维护单价出现问题:" + e.getMessage());
			e.getStackTrace();
		}
		return forwardErrorPage(mapping, request, "error");
	}

	/**
	 * 转到费用生成页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward createExpenseForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		request.getSession().setAttribute("cableMonths", null);
		request.getSession().setAttribute("plMonths", null);
	//	request.getSession().setAttribute("expenseType", null);
		request.getSession().setAttribute("expenses", null);
		request.getSession().setAttribute("pipeExpenses", null);
		WebApplicationContext ctx = getWebApplicationContext();
		ExpensesMonthBO bo = (ExpensesMonthBO) ctx.getBean("expensesMonthBO");
		Calendar calen = Calendar.getInstance();
		int year = calen.get(Calendar.YEAR);
		String expenseType = ExpenseConstant.EXPENSE_TYPE_CABLE;
		String expenseTypePL = ExpenseConstant.EXPENSE_TYPE_PL;
		List cableMonths = bo.getExpenseMonthByYear(year+"/01",expenseType);
		List plMonths = bo.getExpenseMonthByYear(year+"/01",expenseTypePL);
		request.getSession().setAttribute("cableMonths", cableMonths);
		request.getSession().setAttribute("plMonths", plMonths);
		//request.getSession().setAttribute("expenseType", expenseType);
		return mapping.findForward("createExpensesForm");
	}

	/**
	 * 生成费用
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward createExpense(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("LOGIN_USER");
		String month = request.getParameter("month");
		String expenseType = request.getParameter("expenseType");
		WebApplicationContext ctx = getWebApplicationContext();
		ExpensesMonthBO bo = (ExpensesMonthBO) ctx.getBean("expensesMonthBO");
		String regionid = user.getRegionid();
		List<Contractor> contractors = bo.getConstractorByDeptId(regionid);
		for (Contractor c : contractors) {// 判断每个代维的单价与系数是否已经填写，未填写则不能进行费用计算,管道只跟单价关联
			String contractorid = c.getContractorID();
			if(expenseType.equals(ExpenseConstant.EXPENSE_TYPE_CABLE)){
				List<ExpenseGradeFactor> factors = bo
				.getGradeFactors(contractorid);
				if (factors == null || factors.size() == 0) {
					return forwardInfoPage(mapping, request, "30noGradeFactor");
				}
			}
			List<ExpenseUnitPrice> prices = bo.getPrices(contractorid,expenseType);
			if (prices == null || prices.size() == 0) {
				return forwardInfoPage(mapping, request, "30noUnitPrice");
			}
		}
		List list = bo.getMonthExpenses(month,expenseType);
		if (list == null || list.size() == 0) {
			if(expenseType.equals(ExpenseConstant.EXPENSE_TYPE_CABLE)){
				bo.createExpense(user, month);
			}else{
				bo.createPipeExpense(user,month,expenseType);
			}
		}
	//	List months = bo.getExpenseMonthByYear(month,expenseType);
		//request.getSession().setAttribute("months", months);
		List months = null;
		if(expenseType.equals(ExpenseConstant.EXPENSE_TYPE_CABLE)){
			months = bo.getExpenseMonthByYear(month,expenseType);
			request.getSession().setAttribute("cableMonths", months);
		}else{
			months  = bo.getExpenseMonthByYear(month,expenseType);
			request.getSession().setAttribute("plMonths", months);
		}
		request.setAttribute("yearmonth", month);
		request.getSession().setAttribute("expenseType", expenseType);
		if(expenseType.equals(ExpenseConstant.EXPENSE_TYPE_CABLE)){
			Map<String, Map> expenses = bo.getExpenses(user, "",month);
			request.getSession().setAttribute("expenses", expenses);
			request.getSession().setAttribute("pipeExpenses", null);
		}else{
			Map<String,Object> expenses = bo.getPipeExpenses(user,"",month);
			request.getSession().setAttribute("pipeExpenses", expenses);
			request.getSession().setAttribute("expenses", null);
		}
		log(request, " 生成费用（生成月份为："+months+"）", " 费用核实 ");
		return mapping.findForward("createExpensesForm");
	}

	/**
	 * 根据查询条件制定月份查询费用
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getEexpenses(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		ExpensesMonthBO bo = (ExpensesMonthBO) ctx.getBean("expensesMonthBO");
		String yearmonth = request.getParameter("yearmonth");
		String expenseType = request.getParameter("expensetype");
		request.setAttribute("yearmonth", yearmonth);
		if(expenseType.equals(ExpenseConstant.EXPENSE_TYPE_CABLE)){
			Map<String, Map> expenses = bo.getExpenses(user, "",yearmonth);
			request.getSession().setAttribute("expenses", expenses);
			request.getSession().setAttribute("pipeExpenses", null);
		}else{
			Map<String,Object> expenses = bo.getPipeExpenses(user,"",yearmonth);
			request.getSession().setAttribute("pipeExpenses", expenses);
			request.getSession().setAttribute("expenses", null);
		}
		return mapping.findForward("createExpensesForm");
	}

	/**
	 * 转到 扣减月费用的页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editMonthExpenseForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		WebApplicationContext ctx = getWebApplicationContext();
		ExpensesMonthBO bo = (ExpensesMonthBO) ctx.getBean("expensesMonthBO");
		ExpenseMonth month = bo.getExpenseMonthById(id);
		String contractorid = month.getContractorId();
		Date date = month.getYearmonth();
		String yearmonth = DateUtil.DateToString(date,"yyyy/MM");
		boolean ishave = bo.judgeIsExistAffirm(contractorid,yearmonth);
		request.setAttribute("ishave",ishave);
		request.setAttribute("id", id);
		request.setAttribute("month", month);
		return mapping.findForward("editMonthExpenseForm");
	}
	
	/**
	 * 转到查看扣减月费用的页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewMonthExpenseForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		WebApplicationContext ctx = getWebApplicationContext();
		ExpensesMonthBO bo = (ExpensesMonthBO) ctx.getBean("expensesMonthBO");
		ExpenseMonth month = bo.getExpenseMonthById(id);
		request.setAttribute("month", month);
		return mapping.findForward("viewMonthExpenseForm");
	}

	/**
	 * 扣减月费用
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public void editMonthExpense(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException {
		String id = request.getParameter("id");
		String remark = request.getParameter("remark");
		String deductionMoney = request.getParameter("deductionMoney");
		WebApplicationContext ctx = getWebApplicationContext();
		ExpensesMonthBO bo = (ExpensesMonthBO) ctx.getBean("expensesMonthBO");
		bo.editMonthExpense(id, deductionMoney,remark);
		ExpenseMonth month = bo.getExpenseMonthById(id);
		if(remark==null){
			remark="";
		}
		String type = month.getExpenseType();
		response.setContentType("text/html; charset=GBK");
		StringBuffer javascript = new StringBuffer();
		javascript.append("<script type=\"text/javascript\">");
		if(type.equals(ExpenseConstant.EXPENSE_TYPE_PL)){
			javascript.append("parent.editPipeMoney('" + id + "','"
					+ month.getRectifyMoney() + "','"+month.getDeductionMoney()+"','");
			javascript.append(remark+"');");
		}else{
			javascript.append("parent.editMoney('" + id + "','"
					+ month.getRectifyMoney() + "');");
		}
		javascript.append("parent.close();");
		javascript.append("</script>");
		response.getWriter().write(javascript.toString());
	}

	/**
	 * 转到查询费用页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryExpenseForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		request.getSession().setAttribute("expenses",null);
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		ExpensesMonthBO bo = (ExpensesMonthBO) ctx.getBean("expensesMonthBO");
		String regionid = user.getRegionid();
		String type = user.getDeptype();
		if (type.equals("1")) {
			List list = bo.getConstractorByDeptId(regionid);
			request.setAttribute("constrators", list);
		}
		return mapping.findForward("queryExpensesForm");
	}

	/**
	 *维护费用结算
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getSettlementEexpenses(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		ExpensesMonthBO bo = (ExpensesMonthBO) ctx.getBean("expensesMonthBO");
		String contractorid = request.getParameter("contractorid");
		String beginMonth = request.getParameter("beginMonth");
		String endMonth = request.getParameter("endMonth");
		String expenseType = request.getParameter("expenseType");
		String operationCode = bo.judgeIsSettled(contractorid, beginMonth,
				endMonth, expenseType);
		if (operationCode != null && !"E_S".equals(operationCode)) {
			String url = request.getContextPath()
			+ "/expensesAction.do?method=queryExpenseForm";
			return super.forwardErrorPageWithUrl(mapping, request,
					operationCode, url);
		}
		String budgetId = bo.getBudgetId(contractorid, beginMonth, expenseType);
		Double moneySum = bo.getExpensesSum(contractorid, beginMonth, endMonth,
				expenseType);
		if(expenseType.equals(ExpenseConstant.EXPENSE_TYPE_CABLE)){
			Map<String, Map> expenses = bo.getSettlementEexpenses(user,
					contractorid, beginMonth, endMonth);
			request.getSession().setAttribute("cableExpenses", expenses);
			request.getSession().setAttribute("pipeExpenses", null);
		}else{
			Map<String, Map> expenses = bo.getPipeSettlementEexpenses(user,
					contractorid, beginMonth, endMonth);
			request.getSession().setAttribute("pipeExpenses", expenses);
			request.getSession().setAttribute("cableExpenses", null);
		}
		Contractor c = bo.getContractorById(contractorid);
		request.setAttribute("contractor", c);
		request.setAttribute("budgetId", budgetId);
		request.setAttribute("beginMonth", beginMonth);
		request.setAttribute("endMonth", endMonth);
		request.setAttribute("moneySum", moneySum);
		//request.setAttribute("expenseType", expenseType);
		//request.getSession().setAttribute("expenses", expenses);
		log(request, "维护费用结算", "费用核实");
		return mapping.findForward("expensesList");
	}

	/**
	 * 保存费用核实信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveSettlementEexpenses(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String url = request.getContextPath()
		+ "/expensesAction.do?method=queryExpenseForm";
		ExpenseAffirmBean bean = (ExpenseAffirmBean) form;
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		ExpensesMonthBO bo = (ExpensesMonthBO) ctx.getBean("expensesMonthBO");
		bo.saveExpenseAffirm(bean, user);
		return super.forwardInfoPageWithUrl(mapping, request, "E_S001", url);
	}

	/**
	 * 转到已经确认的费用列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward affirmExepenseList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		ExpensesMonthBO bo = (ExpensesMonthBO) ctx.getBean("expensesMonthBO");
		//String expenseType =ExpenseConstant.EXPENSE_TYPE_CABLE;
		List list = bo.getExpenseAffrims();
		request.getSession().setAttribute("affrims", list);
		return mapping.findForward("affirmExepenseList");
	}

	/**
	 *查看已经结算的费用
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewAffrimEexpense(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		ExpensesMonthBO bo = (ExpensesMonthBO) ctx.getBean("expensesMonthBO");
		String id = request.getParameter("id");
		ExpenseAffirm affrim = bo.getAffirm(id);
		ExpenseBudget budget = bo.getExpenseBudget(affrim.getBudgetId());
		String expenseType = budget.getExpenseType();
		String contractorid = affrim.getContractorId();
		Date begin = affrim.getStartMonth();
		Date end = affrim.getEndMonth();
		String beginMonth = DateUtil.DateToString(begin,"yyyy/MM");
		String endMonth = DateUtil.DateToString(end,"yyyy/MM");
	//	Map<String, Map> expenses = bo.getSettlementEexpenses(user,
				//contractorid, beginMonth, endMonth);
		Double deductionPrice = affrim.getDeductionPrice();
		Double balancePrice = affrim.getBalancePrice();
		double monthMoney = balancePrice+deductionPrice;	
		Contractor c = bo.getContractorById(contractorid);
		request.setAttribute("contractor", c);
		request.setAttribute("id", id);
		request.setAttribute("contractorId", contractorid);
		request.setAttribute("beginMonth", beginMonth);
		request.setAttribute("endMonth", endMonth);
		request.setAttribute("affrim", affrim);
		request.setAttribute("monthMoney", monthMoney);
		if(expenseType.equals(ExpenseConstant.EXPENSE_TYPE_CABLE)){
			Map<String, Map> expenses = bo.getSettlementEexpenses(user,
					contractorid, beginMonth, endMonth);
			request.getSession().setAttribute("affrimExpense",expenses);
			request.getSession().setAttribute("affrimPipeExpense",null);
		}else{
			Map<String, Object> expenses = bo.getPipeSettlementEexpenses(user,
					contractorid, beginMonth, endMonth);
			request.getSession().setAttribute("affrimPipeExpense",expenses);
			request.getSession().setAttribute("affrimExpense",null);
		}
		return mapping.findForward("viewAffrimEexpense");
	}

	/**
	 * 导出光缆费用
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportExpense(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String yearmonth = request.getParameter("yearmonth");
		ExpenseExportBO exportBO = new ExpenseExportBO();
		Map<String, Map> map = (Map<String, Map>) request.getSession()
		.getAttribute("expenses");
		exportBO.exportExpense(map, yearmonth, response);
		return null;
	}

	
	/**
	 * 导出管道费用
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportPipeExpense(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String yearmonth = request.getParameter("yearmonth");
		ExpenseExportBO exportBO = new ExpenseExportBO();
		Map<String,Object> map = (Map<String, Object>) request.getSession()
		.getAttribute("pipeExpenses");
		exportBO.exportPipeExpense(map, yearmonth, response);
		return null;
	}
	
	/**
	 * 导出光缆费用确认
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportSettlementExpense(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String contractorid = request.getParameter("contractorid");
		String beginMonth = request.getParameter("beginMonth");
		String endMonth = request.getParameter("endMonth");
		ExpenseExportBO exportBO = new ExpenseExportBO();
		WebApplicationContext ctx = getWebApplicationContext();
		ExpensesMonthBO bo = (ExpensesMonthBO) ctx.getBean("expensesMonthBO");
		String id = request.getParameter("id");
		ExpenseAffirm affrim = bo.getAffirm(id);
		Contractor c = bo.getContractorById(contractorid);
		Map<String,Map> map = (Map<String,Map>) request.getSession().getAttribute("affrimExpense");
		exportBO.exportSettlementExpense(map,affrim,c, beginMonth,endMonth,response);
		return null;
	}
	
	
	/**
	 * 导出管道费用确认
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportPipeSettlementExpense(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String contractorid = request.getParameter("contractorid");
		String beginMonth = request.getParameter("beginMonth");
		String endMonth = request.getParameter("endMonth");
		ExpenseExportBO exportBO = new ExpenseExportBO();
		WebApplicationContext ctx = getWebApplicationContext();
		ExpensesMonthBO bo = (ExpensesMonthBO) ctx.getBean("expensesMonthBO");
		String id = request.getParameter("id");
		ExpenseAffirm affrim = bo.getAffirm(id);
		Contractor c = bo.getContractorById(contractorid);
		Map<String,Object> map = (Map<String,Object>) request.getSession().getAttribute("affrimPipeExpense");
		exportBO.exportPipeSettlementExpense(map, affrim, 
				c, beginMonth, endMonth, response);
		return null;
	}


}