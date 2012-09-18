/***
 *
 * MtUsedAssessAction.java
 * @auther <a href="kww@mail.cabletech.com.cn">kww</a>
 * 2009-10-11
 **/

package com.cabletech.linepatrol.material.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linepatrol.material.beans.MtUsedAssessBean;
import com.cabletech.linepatrol.material.beans.MtUsedBean;
import com.cabletech.linepatrol.material.service.MtUsedAssessBo;
import com.cabletech.linepatrol.material.service.MtUsedBo;

public class MtUsedAssessAction extends BaseInfoBaseDispatchAction {

	private MtUsedBo getMtUsedService() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (MtUsedBo) ctx.getBean("mtUsedBo");
	}

	private MtUsedAssessBo getMtUsedAssessService() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (MtUsedAssessBo) ctx.getBean("mtUsedAssessBo");
	}

	public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public ActionForward addMtUsedAppoverForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String userids = request.getParameter("userids");
		System.out.println("userids============================== " + userids);
		MtUsedBo manager = new MtUsedBo();
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		MtUsedAssessBean bean = (MtUsedAssessBean) form;
		bean.setAssessdate(simpleDateFormat.format(Calendar.getInstance()
				.getTime()));
		bean.setAssessor(user.getUserID());
		bean.setType("0");
		MtUsedBean mtUsedBean = new MtUsedBean();
		mtUsedBean.setMid(bean.getMtusedid());
		if ("1".equals(bean.getState())) {
			mtUsedBean.setState(MtUsedBean.STATE_MOBILE_AGREE);
		} else {
			mtUsedBean.setState(MtUsedBean.STATE_MOBILE_NO_AGREE);
		}
		BasicDynaBean basic = manager.getUsedById(bean.getMtusedid());
		String createtime = (String) basic.get("createtime");
		String title = createtime + "材料盘点";
		if (getMtUsedAssessService().mtApprove(bean, mtUsedBean)) {
			if (!"1".equals(bean.getState())) {// 监理没有通过，返回盘点人
				String creator = (String) basic.get("creator");
				manager.sendMsgForUsers(bean.getMtusedid() + "", user, creator,
						title);
			} else {
				if (userids != null && !"".equals(userids)) {
					// manager.sendMsgForUsers(bean.getMtusedid() + "", user,
					// userids, title);
				}
			}
			return forwardInfoPage(mapping, request, "mtApproveSuccess");
		}
		return forwardErrorPage(mapping, request, "mtApproveError");
	}

	public ActionForward mobileMtUsedAppoverForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		MtUsedAssessBean bean = (MtUsedAssessBean) form;
		bean.setAssessdate(simpleDateFormat.format(Calendar.getInstance()
				.getTime()));
		bean.setAssessor(user.getUserID());
		bean.setType("1");
		MtUsedBean mtUsedBean = new MtUsedBean();
		mtUsedBean.setMid(bean.getMtusedid());
		if ("1".equals(bean.getState())) {
			mtUsedBean.setState(MtUsedBean.STATE_MOBILE_AGREE);
		} else {
			mtUsedBean.setState(MtUsedBean.STATE_MOBILE_NO_AGREE);
		}
		MtUsedBo mtUsedBo = getMtUsedService();
		if (getMtUsedAssessService().mtApprove(bean, mtUsedBean)) {
			if (!"1".equals(bean.getState())) {// 移动没有通过返回监理
				BasicDynaBean basic = mtUsedBo.getUsedById(bean.getMtusedid());
				// String createtime = mtUsedBean.getCreatetime();
				String title = basic.get("createtime") + "材料盘点";
				// mtUsedBo.sendOneMsg(user, assessor, titile);
				mtUsedBo.sendMsgForUsers(bean.getId(), user, "'"
						+ basic.get("creator") + "'", title);
			}
			if ("1".equals(bean.getState())) {
				log(request,"材料盘点申请审核通过（申请时间为："+bean.getAssessdate()+"）","材料管理");
				return forwardInfoPage(mapping, request,
						"mtMobileApproveSuccess");
			} else {
				log(request,"材料盘点申请审未通过（申请时间为："+bean.getAssessdate()+"）","材料管理");
				return forwardInfoPage(mapping, request,
						"mtMobileApproveSuccess1");
			}
		}
		return forwardErrorPage(mapping, request, "mtMobileApproveError");
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

		MtUsedAssessBo mtUsedAssessBo = getMtUsedAssessService();
		BasicDynaBean bean = getMtUsedService().getApplyAndApproveBeanId(idInt);
		request.setAttribute("basicDynaBean", bean);
		List list1 = mtUsedAssessBo.getMtUsedIdAndType0(Integer.parseInt(id));
		List list2 = mtUsedAssessBo.getMtUsedIdAndType1(Integer.parseInt(id));
		request.setAttribute("mtApproveList", list1);
		request.setAttribute("ydApproveList", list2);

		return mapping.findForward("goMtUsedEditForm");

	}

	/**
	 * 编辑
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	// public ActionForward doMtUsedEditForm(ActionMapping mapping, ActionForm
	// form,
	// HttpServletRequest request, HttpServletResponse response){
	// MtUsedAssessBean bean = (MtUsedAssessBean)form;
	// String backUrl = (String)
	// request.getSession().getAttribute("S_BACK_URL");
	// if(!manager.mtApplyEdit(bean)){
	// return super.forwardErrorPageWithUrl(mapping, request, "mtusedEditError",
	// backUrl);
	// }
	// return forwardInfoPageWithUrl(mapping, request, "mtusedEditSuccess",
	// backUrl);
	// }

	public ActionForward listMtUsedAppoverForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String backUrl = (String) request.getSession().getAttribute(
				"S_BACK_URL");
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List list = getMtUsedAssessService().getConditionByStateAndUserid(
				user.getUserID());
		request.getSession().setAttribute("approveList", list);
		super.setPageReset(request);
		return mapping.findForward("listMtUsedApplyForm");
	}

	public ActionForward finishMtUsed(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		super.setPageReset(request);
		List list = getMtUsedService().getConditionByFinish();
		request.getSession().setAttribute("finishList", list);
		return mapping.findForward("finishMtUsed");
	}

	public ActionForward displayFinishMtUsed(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		BasicDynaBean usedDynaBean = getMtUsedService()
				.getApplyAndApproveBeanId(Integer.parseInt(id));
		List list = getMtUsedAssessService().getUnionMtUsedId(
				Integer.parseInt(id));
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
		request.setAttribute("basicDynaBean", usedDynaBean);
		request.setAttribute("finishList", list);
		return mapping.findForward("displayFinishMtUsed");
	}

	public ActionForward goMtUsedAppoverForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String mtUsedId = request.getParameter("mtUsedId");
		int mtI = Integer.parseInt(mtUsedId);
		MtUsedBo mtUsedBo = getMtUsedService();
		BasicDynaBean usedDynaBean = mtUsedBo.getApplyAndApproveBeanId(mtI);
		List approveList = getMtUsedAssessService().getUnionMtUsedId(mtI);
		request.setAttribute("mtUsedInfo", usedDynaBean);

		List contrList = new ArrayList();
		List mobileList = new ArrayList();
		for (int i = 0; i < approveList.size(); i++) {
			BasicDynaBean listMtUsed = (BasicDynaBean) approveList.get(i);
			if (listMtUsed.get("type").equals("0")) {
				contrList.add(approveList.get(i));
			} else {
				mobileList.add(approveList.get(i));
			}
		}
		request.setAttribute("contrList", contrList);
		request.setAttribute("mobileList", mobileList);

		request.setAttribute("mtApproveList", approveList);

		Map detailStorageMap = mtUsedBo.getDetailStorageMap(
				(String) usedDynaBean.get("createtime"), (String) usedDynaBean
						.get("creator_contractor_id"));
		List list = mtUsedBo.getMtUsedStockBean(mtUsedId);
		mtUsedBo.processMap(detailStorageMap, list);
		request.setAttribute("detail_storage_map", detailStorageMap);

		return mapping.findForward("goMtUsedAppoverForm");
	}
}
