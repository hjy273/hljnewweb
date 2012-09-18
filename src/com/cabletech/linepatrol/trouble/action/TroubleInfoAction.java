package com.cabletech.linepatrol.trouble.action;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.tags.services.DictionaryService;
import com.cabletech.linepatrol.trouble.beans.TroubleInfoBean;
import com.cabletech.linepatrol.trouble.module.TroubleEoms;
import com.cabletech.linepatrol.trouble.module.TroubleInfo;
import com.cabletech.linepatrol.trouble.module.TroubleReply;
import com.cabletech.linepatrol.trouble.services.TroubleConstant;
import com.cabletech.linepatrol.trouble.services.TroubleInfoBO;
import com.cabletech.linepatrol.trouble.services.TroubleReplyApproveBO;
import com.cabletech.linepatrol.trouble.services.TroubleReplyBO;

/**
 * �����ɵ�
 * 
 * @author Administrator
 * 
 */
public class TroubleInfoAction extends BaseDispatchAction {
	Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * ת���ɵ��Ľ���
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addTroubleForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		DictionaryService dictService = (DictionaryService) getWebApplicationContext()
				.getBean("dictionaryService");
		Map deadlineMap = dictService.loadDictByAssortment("trouble_deadline",
				user.getRegionid());
		request.setAttribute("deadline_map", deadlineMap);
		request.getSession().setAttribute("FILES", null);
		return mapping.findForward("addTroubleForm");
	}

	/**
	 * �õ���ʱ������ɵ���Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getTempSaveTrouble(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		super.setPageReset(request);
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleInfoBO troubleBO = (TroubleInfoBO) ctx.getBean("troubleInfoBO");
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List<TroubleInfo> troubles = troubleBO.getTempSaveTroubles(userinfo);
		request.getSession().setAttribute("tempTroubles", troubles);
		return mapping.findForward("tempSaveTroubles");
	}

	/**
	 * ת���޸��ɵ���ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editTroubleForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleInfoBO troubleBO = (TroubleInfoBO) ctx.getBean("troubleInfoBO");
		String troubleid = request.getParameter("troubleid");
		TroubleInfo trouble = troubleBO.getTroubleById(troubleid);
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		DictionaryService dictService = (DictionaryService) ctx
				.getBean("dictionaryService");
		Map deadlineMap = dictService.loadDictByAssortment("trouble_deadline",
				user.getRegionid());
		request.setAttribute("deadline_map", deadlineMap);
		request.setAttribute("trouble", trouble);
		return mapping.findForward("editTempSaveTroubleForm");
	}

	/**
	 * ��������ɵ�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */

	public ActionForward saveTroubleInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		TroubleInfoBean bean = (TroubleInfoBean) form;
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleInfoBO troubleBO = (TroubleInfoBO) ctx.getBean("troubleInfoBO");
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String beanid = bean.getId();
		try {
			String savestate = bean.getTempsave();
			String conids = request.getParameter("conids");
			String deptids = request.getParameter("deptids");
			String userids = request.getParameter("userids");
			String mobiles = request.getParameter("mobiles");
			List<FileItem> files = (List<FileItem>) request.getSession().getAttribute("FILES");
			TroubleInfo info = troubleBO.addTroubleInfo(bean, userinfo, conids,
					deptids, userids, mobiles, files);
			if (savestate != null && savestate.equals("tempsaveTrouble")) {
				log(request, "��ʱ��������ɵ�  ����������Ϊ��" + bean.getTroubleName() + "��",
						" ���Ϲ��� ");
				if (beanid != null && !beanid.equals("")) {
					return forwardInfoPage(mapping, request,
							"210101edittempSaveok");
				}
				return forwardInfoPage(mapping, request, "210101tempSaveok");
			}
			log(request, " �����ɵ� ����������Ϊ��" + bean.getTroubleName() + "��",
					" ���Ϲ��� ");
			return forwardInfoPage(mapping, request, "210101ok");
		} catch (Exception e) {
			e.printStackTrace();
			return forwardErrorPage(mapping, request, "error");
		}

	}

	/**
	 * ���ݹ��ϵ���Ų鿴���ϵ�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewTroubleInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String troubleid = request.getParameter("troubleid");
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleInfoBO troubleBO = (TroubleInfoBO) ctx.getBean("troubleInfoBO");
		TroubleInfo troubleinfo = troubleBO.getTroubleById(troubleid);
		List list = troubleBO.findTroubleUnitById(troubleid);
		request.setAttribute("unitlist", list);
		request.setAttribute("troubleinfo", troubleinfo);
		TroubleReplyApproveBO approveBO = (TroubleReplyApproveBO) ctx
				.getBean("troubleReplyApproveBO");
		TroubleReplyBO replyBO = (TroubleReplyBO) ctx.getBean("troubleReplyBO");
		TroubleReply reply = replyBO.getReplyByTroubleId(troubleid);
		List<TroubleEoms> eomsList = replyBO.getEomsByTroubleId(troubleid);
		String eoms = replyBO.emosListToString(eomsList);
		request.setAttribute("eoms", eoms);
		if (reply != null) {
			String replyid = reply.getId();
			String cf = reply.getConfirmResult();
			if (cf.equals(TroubleConstant.REPLY_ROLE_HOST)) {
				List cableList = replyBO.cableList(replyid);
				List<String> processers = replyBO.getProcessers(reply.getId());
				String responsibles = processers.get(0);
				String testmans = processers.get(1);
				String mendmans = processers.get(2);
				List process = replyBO.getTroubleProcessList(reply
						.getTroubleId());
				BasicDynaBean hidden = replyBO.getAccidentByReplyId(replyid);
				request.setAttribute("hidden", hidden);
				List approves = approveBO.getApproveHistorys(replyid);
				String troubleReasonName = replyBO.getTroubleReasonName(reply
						.getTroubleId());
				request.setAttribute("reply", reply);
				request.setAttribute("cableList", cableList);
				request.setAttribute("responsibles", responsibles);
				request.setAttribute("testmans", testmans);
				request.setAttribute("mendmans", mendmans);
				request.setAttribute("process", process);
				request.setAttribute("approves", approves);
				request.setAttribute("troubleReasonName", troubleReasonName);
			}
		}
		return mapping.findForward("viewTroubleForm");
	}

	/**
	 * ִ��ȡ�����������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelTroubleForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String troubleId = request.getParameter("troubleId");
		request.setAttribute("trouble_id", troubleId);
		return mapping.findForward("troubleCancelForm");
	}

	/**
	 * ִ��ȡ����������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward cancelTrouble(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		TroubleInfoBean bean = (TroubleInfoBean) form;
		// bean.setId(request.getParameter("troubleId"));
		TroubleInfoBO troubleBO = (TroubleInfoBO) ctx.getBean("troubleInfoBO");
		troubleBO.cancelTrouble(bean, userInfo);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		PrintWriter out = response.getWriter();
		out.print("<script type='text/javascript'>");
		out.print("window.opener.location.href='" + url + "';");
		out.print("window.close();");
		out.print("</script>");
		return null;
		// return super.forwardInfoPageWithUrl(mapping, request,
		// "CANCEL_TROUBLE_SUCCESS", url);
	}

	/**
	 * ��ʾ���й��ϵ�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getAllTroubleInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		super.setPageReset(request);
		return mapping.findForward("listTroubleForm");
	}

}
