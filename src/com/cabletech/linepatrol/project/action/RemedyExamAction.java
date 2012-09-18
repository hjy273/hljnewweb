package com.cabletech.linepatrol.project.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;
import com.cabletech.linepatrol.project.beans.RemedyExamBean;
import com.cabletech.linepatrol.project.service.RemedyApplyManager;
import com.cabletech.linepatrol.project.service.RemedyExamManager;
import com.cabletech.linepatrol.project.service.RemedyItemManager;
import com.cabletech.linepatrol.project.service.RemedyTypeManager;

/**
 * 工程管理日常考核
 * 
 * @author liusq
 * 
 */
public class RemedyExamAction extends BaseDispatchAction {

	private static final long serialVersionUID = 1031071811220097845L;

	/**
	 * 工程管理待考核列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward examList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String init = request.getParameter("init");
		RemedyExamBean bean = (RemedyExamBean)form;
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		RemedyExamManager rem = (RemedyExamManager) ctx.getBean("remedyExamManager");
		List<DynaBean> examList = null;
		if(!StringUtils.equals("init", init)){
			examList = rem.getExamListByCondition(bean, userInfo);
		}
		session.setAttribute("examList", examList);
		return mapping.findForward("examList");
	}
	
	/**
	 * 转向考核大修项目界面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward examRemedyForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		RemedyApplyManager applyBo = (RemedyApplyManager) ctx.getBean("remedyApplyManager");
		RemedyItemManager itemBo = (RemedyItemManager) ctx
				.getBean("remedyItemManager");
		RemedyTypeManager itemTypeBo = (RemedyTypeManager) ctx
				.getBean("remedyTypeManager");
		String applyId = request.getParameter("apply_id");
		String type = request.getParameter("type");
		Map applyMap = applyBo.viewApply(applyId);
		if (applyMap == null) {
			String backUrl = (String) request.getSession().getAttribute(
					"S_BACK_URL");
			return super.forwardErrorPageWithUrl(mapping, request,
					"NotFindDataError", backUrl);
		}
		request.setAttribute("apply_map", applyMap);
		request.setAttribute("type", type);
		List<DynaBean> itemList = itemBo.getRemedyItemList(userInfo);
		request.setAttribute("item_list", itemList);
		List<DynaBean> itemTypeList = itemTypeBo.getRemedyItemTypeList(userInfo);
		request.setAttribute("item_type_list", itemTypeList);
		return mapping.findForward("remedyExamForm");
	}
	
	/**
	 * 考核大修项目
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward examRemedy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//获得日常考核对象
		AppraiseDailyBean appraiseDailyBean = (AppraiseDailyBean)request.getSession().getAttribute("appraiseDailyDailyBean");
		List<AppraiseDailyBean> speicalBeans=(List<AppraiseDailyBean>)request.getSession().getAttribute("appraiseDailySpecialBean");
		WebApplicationContext ctx = getWebApplicationContext();
		RemedyExamManager rem = (RemedyExamManager) ctx.getBean("remedyExamManager");
		rem.examRemedy(appraiseDailyBean,speicalBeans);
		return forwardInfoPage(mapping, request, "examRemedySuccess");
	}
}
