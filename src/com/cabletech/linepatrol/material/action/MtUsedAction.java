/***
 *
 * MtUsedAction.java
 * @auther <a href="kww@mail.cabletech.com.cn">kww</a>
 * 2009-10-10
 **/

package com.cabletech.linepatrol.material.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.sql.DATE;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linepatrol.material.beans.MtUsedBean;
import com.cabletech.linepatrol.material.service.MtUsedAssessBo;
import com.cabletech.linepatrol.material.service.MtUsedBo;

public class MtUsedAction extends BaseInfoBaseDispatchAction {

	private MtUsedBo getMtUsedService() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (MtUsedBo) ctx.getBean("mtUsedBo");
	}

	private MtUsedAssessBo getMtUsedAssessService() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (MtUsedAssessBo) ctx.getBean("mtUsedAssessBo");
	}

	public ActionForward goMtUsedApplyForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return mapping.findForward("goMtUsedApplyForm");
	}

	public ActionForward detailMtUsedApplyForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MtUsedBo mtUsedBo = getMtUsedService();
		MtUsedBean bean = (MtUsedBean) form;
		String month = bean.getCreatetime();
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String contractorId = user.getDeptID();

		String backUrl = request.getContextPath()
				+ "/mtUsedAction.do?method=goMtUsedApplyForm";
		if (mtUsedBo.judgeExistMtUsed(month, contractorId, "0")) {
			return super.forwardErrorPageWithUrl(mapping, request,
					"existMtUsedError", backUrl);
		}

		Map detailStorageMap = mtUsedBo
				.getDetailStorageMap(month, contractorId);
		request.setAttribute("detail_storage_map", detailStorageMap);
		request.setAttribute("month", month);

		// List list = mtUsedBo.getContractorByTpe3();
		// request.setAttribute("contractorList", list);
		return mapping.findForward("detailMtUsedApplyForm");
	}

	public ActionForward addMtUsedApplyForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {
		MtUsedBean bean = (MtUsedBean) form;
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");

		MtUsedBo mtUsedBo = getMtUsedService();
		bean.setState(MtUsedBean.STATE_APPLY);
		bean.setCreator(user.getUserID());
		String userIds = request.getParameter("userids");
		bean.setApproverId(userIds);
		if (!mtUsedBo.mtApply(bean, request)) {
			return forwardErrorPage(mapping, request, "mtusedError");
		}
		String time = bean.getCreatetime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = sdf.parse(time);
		String title = sdf.format(date) + "材料盘点";
		// mtUsedBo.sendConMsg(user, bean.getContractorid(),titile);
		String userids = "";
		if (userIds != null) {
			String[] userId = userIds.split(",");
			for (int i = 0; userId != null && i < userId.length; i++) {
				if (userId != null) {
					userids = userids + "'" + userId[i] + "'";
				}
				if (userId.length - 1 != i) {
					userids = userids + ",";
				}
			}
		}
		log(request,"添加材料盘点申请（申请时间为："+bean.getCreatetime()+"）","材料管理");
		mtUsedBo.sendMsgForUsers(bean.getMid()+"",user, userids, title);
		return forwardInfoPage(mapping, request, "mtusedSuccess");
	}

	public ActionForward getMtUsedApplyForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String mtUsedId = request.getParameter("mtUsedId");

		MtUsedBo mtUsedBo = getMtUsedService();
		MtUsedAssessBo mtUsedAssessBo = getMtUsedAssessService();

		BasicDynaBean domainObject = mtUsedBo.getApplyAndApproveBeanId(Integer
				.parseInt(mtUsedId));
		List list = mtUsedAssessBo.getUnionMtUsedId(Integer.parseInt(mtUsedId));

		List contrList = new ArrayList();
		List mobileList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			BasicDynaBean listMtUsed = (BasicDynaBean) list.get(i);
			if (listMtUsed.get("type").equals("0")) {
				contrList.add(list.get(i));
			} else {
				mobileList.add(list.get(i));
			}
		}
		request.setAttribute("contrList", contrList);
		request.setAttribute("mobileList", mobileList);

		request.setAttribute("basicDynaBean", domainObject);
		request.setAttribute("mtApproveList", list);

		Map detailStorageMap = mtUsedBo.getDetailStorageMap(
				(String) domainObject.get("createtime"), (String) domainObject
						.get("contractorid"));

		list = mtUsedBo.getMtUsedStockBean(mtUsedId);
		mtUsedBo.processMap(detailStorageMap, list);
		request.setAttribute("detail_storage_map", detailStorageMap);
		List users = mtUsedBo.getUserInfos();
		request.setAttribute("users", users);
		return mapping.findForward("getMtUsedApplyForm");
	}

	// public ActionForward getMtUsedApplyForm1(ActionMapping mapping,
	// ActionForm form,
	// HttpServletRequest request, HttpServletResponse response){
	// String mtUsedId = request.getParameter("mtUsedId");
	//		
	// BasicDynaBean domainObject =
	// manager.getApplyAndApproveBeanId(Integer.parseInt(mtUsedId));
	// List list = assessManger.getUnionMtUsedId(Integer.parseInt(mtUsedId));
	// request.setAttribute("basicDynaBean", domainObject);
	// request.setAttribute("mtApproveList", list);
	// request.setAttribute("ydApproveList", list);
	// return mapping.findForward("getMtUsedApplyForm1");
	// }

	public ActionForward displayMtUsedApplyForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		MtUsedBo mtUsedBo = getMtUsedService();
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String deptid = user.getDeptID();
		List list = mtUsedBo.getConditionByState0(deptid);
		request.getSession().setAttribute("applyList", list);
		return mapping.findForward("displayMtUsedApplyForm");
	}

	public ActionForward listMtUsedApplyForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		MtUsedBo mtUsedBo = getMtUsedService();
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List list = mtUsedBo.getApplyBy0And1(user.getUserID());
		request.getSession().setAttribute("applyList", list);
		super.setPageReset(request);
		return mapping.findForward("listMtUsedApplyForm");
	}

	/**
	 * 显示编辑页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward goMtUsedEditForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		int idInt = 0;
		String backUrl = (String) request.getSession().getAttribute(
				"S_BACK_URL");
		if (id != null) {
			idInt = Integer.parseInt(id);
		} else {
			return super.forwardErrorPageWithUrl(mapping, request,
					"nullmtusedError", backUrl);
		}

		MtUsedBo mtUsedBo = getMtUsedService();
		// List list = mtUsedBo.getContractorByTpe3();
		// request.setAttribute("contractorList", list);

		BasicDynaBean bean = mtUsedBo.getMtUsedBean(idInt);

		request.setAttribute("editBean", bean);
		return mapping.findForward("goMtUsedApplyEditForm");

	}

	/**
	 * 显示详细页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward goMtUsedDisForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		String backUrl = (String) request.getSession().getAttribute(
				"S_BACK_URL");
		int idInt = 0;
		if (id != null) {
			idInt = Integer.parseInt(id);
		} else {
			return super.forwardErrorPageWithUrl(mapping, request,
					"nullmtusedError", backUrl);
		}

		MtUsedBo mtUsedBo = getMtUsedService();
		BasicDynaBean bean = mtUsedBo.getMtUsedBean(idInt);

		request.setAttribute("disBean", bean);

		Map detailStorageMap = mtUsedBo.getDetailStorageMap((String) bean
				.get("createtime"), (String) bean.get("creator_contractor_id"));

		List list = mtUsedBo.getMtUsedStockBean(id);
		mtUsedBo.processMap(detailStorageMap, list);
		request.setAttribute("detail_storage_map", detailStorageMap);

		return mapping.findForward("goMtUsedApplydisForm");

	}

	/**
	 * 编辑
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	public ActionForward doMtUsedEditForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		MtUsedBean bean = (MtUsedBean) form;
		String backUrl = (String) request.getSession().getAttribute(
				"S_BACK_URL");
		int id = Integer.parseInt(bean.getId());
		String userIds = request.getParameter("userids");
		
		MtUsedBo mtUsedBo = getMtUsedService();
		BasicDynaBean basic = mtUsedBo.getUsedById(id);
		bean.setApproverId(userIds);
		if (!mtUsedBo.mtApplyEdit(bean, request)) {
			return super.forwardErrorPageWithUrl(mapping, request,
					"mtusedEditError", backUrl);
		}
		if (basic != null) {
			String state = (String) basic.get("state");
			if (state.equals("1")) {
				String time = bean.getCreatetime();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
				Date date = sdf.parse(time);
				String title = sdf.format(date) + "材料盘点";
				String userids = "";
				if (userIds != null) {
					String[] userId = userIds.split(",");
					for (int i = 0; userId != null && i < userId.length; i++) {
						if (userId != null) {
							userids = userids + "'" + userId[i] + "'";
						}
						if (userId.length - 1 != i) {
							userids = userids + ",";
						}
					}
				}
				// mtUsedBo.sendConMsg(user, bean.getContractorid(), titile);
				mtUsedBo.sendMsgForUsers(bean.getId(),user, userids, title);
			}
		}
		log(request,"编辑材料盘点申请（申请年月为："+bean.getCreatetime()+"）","材料管理");
		return forwardInfoPageWithUrl(mapping, request, "mtusedEditSuccess",
				backUrl);
	}

	public ActionForward detailMtUsedApplyEditedForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MtUsedBean bean = (MtUsedBean) form;
		String month = bean.getCreatetime();
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String contractorId = user.getDeptID();

		MtUsedBo mtUsedBo = getMtUsedService();
		String backUrl = (String) request.getSession().getAttribute(
				"S_BACK_URL");

		if (mtUsedBo.judgeExistMtUsed(bean.getCreatetime(), contractorId, bean
				.getId())) {
			return super.forwardErrorPageWithUrl(mapping, request,
					"existMtUsedError", backUrl);
		}

		Map detailStorageMap = mtUsedBo
				.getDetailStorageMap(month, contractorId);
		request.setAttribute("month", month);

		// List list = mtUsedBo.getContractorByTpe3();
		// request.setAttribute("contractorList", list);

		String id = request.getParameter("id");
		int idInt = 0;
		if (id != null) {
			idInt = Integer.parseInt(id);
		} else {
			return super.forwardErrorPageWithUrl(mapping, request,
					"nullmtusedError", backUrl);
		}

		BasicDynaBean oneBean = mtUsedBo.getMtUsedBean(idInt);
		request.setAttribute("editBean", oneBean);

		List list = mtUsedBo.getMtUsedStockBean(id);
		mtUsedBo.processMap(detailStorageMap, list);
		request.setAttribute("detail_storage_map", detailStorageMap);

		return mapping.findForward("detailMtUsedApplyEditedForm");
	}

	/**
	 * 删除
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doMtUsedDeleteForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MtUsedBo mtUsedBo = getMtUsedService();
		String id = request.getParameter("id");
		String backUrl = (String) request.getSession().getAttribute(
				"S_BACK_URL");
		int idInt = 0;
		String time=mtUsedBo.get(id).getCreatetime();
		if (id != null) {
			idInt = Integer.parseInt(id);
		} else {
			return super.forwardErrorPageWithUrl(mapping, request,
					"nullmtusedError", backUrl);
		}
		if (!mtUsedBo.mtApplyDel(idInt)) {
			return super.forwardErrorPageWithUrl(mapping, request,
					"mtusedDelError", backUrl);
		}
		log(request,"删除材料盘点申请（申请时间为："+time+"）","材料管理");
		return forwardInfoPageWithUrl(mapping, request, "mtusedDelSuccess",
				backUrl);
	}

	/**
	 * 跳转查询页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward mobileMtUsedQuery(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return mapping.findForward("mobileMtUsedQuery");
	}

	/**
	 * 移动条件查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward mobileMtUsedQueryList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MtUsedBo mtUsedBo = getMtUsedService();
		MtUsedBean mtUsedBean = (MtUsedBean) form;
		List list = mtUsedBo.getConditionByMobile(mtUsedBean.getContractorid(),
				mtUsedBean.getCreatetime());
		request.getSession().setAttribute("finishList", list);
		super.setPageReset(request);
		return mapping.findForward("finishMtUsed");
	}
}
