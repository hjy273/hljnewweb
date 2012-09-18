package com.cabletech.baseinfo.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.beans.CableSegmentBean;
import com.cabletech.baseinfo.domainobjects.CableSegment;
import com.cabletech.baseinfo.services.CableSegmentBO;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.web.ClientException;
import com.cabletech.power.CheckPower;

public class CableSegmentAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger.getLogger("CableSegmentAction");

	public ActionForward queryCableSegment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		CableSegmentBean bean = (CableSegmentBean) form;
		request.getSession().setAttribute("S_BACK_URL", null); //
		request.getSession().setAttribute("theQueryBean", bean);//
		List cablelist = null;
		CableSegmentBO csbo = new CableSegmentBO();
		// CableSegmentBean csbean = (CableSegmentBean) form;
		CableSegment csdata = new CableSegment();
		String segmentid = ((CableSegmentBean) form).getSegmentid();
		String segmentname = ((CableSegmentBean) form).getSegmentname();
		logger.info("查询关键字：" + segmentid + " -- " + segmentname);
		if (segmentid != null)
			csdata.setSegmentid(segmentid);
		if (segmentname != null)
			csdata.setSegmentname(segmentname);
		// BeanUtil.objectCopy( csbean, csdata );
		logger.info("查询关键字：" + csdata.getSegmentid() + " -- "
				+ csdata.getSegmentname());
		cablelist = csbo.queryCableSegment(csdata);
		request.getSession().setAttribute("cablelist", cablelist);
		super.setPageReset(request);
		return mapping.findForward("querycablesegmentresult");
	}

	public ActionForward addCableSegment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		CableSegmentBO bo = new CableSegmentBO();
		CableSegmentBean csbean = (CableSegmentBean) form;
		CableSegment csdata = new CableSegment();
		BeanUtil.objectCopy(csbean, csdata);
		logger.info("data " + csbean.getPointz());
		logger.info("builder " + csbean.getBuilder());
		csdata.setKid(super.getDbService().getSeq("cablesegment", 10));
		boolean b = bo.addCableSegment(csdata);
		logger.info("zengjiachenggong" + b);
		log(request, " 增加光缆信息 ", " 基础资料管理 ");
		if (b) {
			return forwardInfoPage(mapping, request, "m72102");
		} else {
			return forwardInfoPage(mapping, request, "e72102");
		}
	}

	public ActionForward delCableSegment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		CableSegmentBO bo = new CableSegmentBO();
		String kid = request.getParameter("kid");
		boolean b = false;
		if (kid != null) {
			b = bo.delCableSegment(kid);
		}
		if (b) {
			String strQueryString = getTotalQueryString(
					"method=queryCableSegment&segmentname=",
					(CableSegmentBean) request.getSession().getAttribute(
							"theQueryBean"));
			Object[] args = getURLArgs("/WebApp/CableSegmentAction.do",
					strQueryString, (String) request.getSession().getAttribute(
							"S_BACK_URL"));
			return forwardInfoPage(mapping, request, "m72105", null, args);
		} else {
			return forwardInfoPage(mapping, request, "e72105");
		}

	}

	public ActionForward updateCableSegment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		CableSegmentBO bo = new CableSegmentBO();
		CableSegmentBean csbean = (CableSegmentBean) form;
		CableSegment csdata = new CableSegment();
		BeanUtil.objectCopy(csbean, csdata);
		boolean b = bo.updateCableSegment(csdata);
		if (b) {
			String strQueryString = getTotalQueryString(
					"method=queryCableSegment&segmentname=",
					(CableSegmentBean) request.getSession().getAttribute(
							"theQueryBean"));
			Object[] args = getURLArgs("/WebApp/CableSegmentAction.do",
					strQueryString, (String) request.getSession().getAttribute(
							"S_BACK_URL"));
			return forwardInfoPage(mapping, request, "m72104", null, args);
		} else {
			return forwardInfoPage(mapping, request, "e72104");
		}

	}

	public ActionForward loadEditForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		String type = request.getParameter("t");
		if ("e".equals(type)) {
			if (!CheckPower.checkPower(request.getSession(), "72104")) {
				return mapping.findForward("powererror");
			}
		}
		String kid = request.getParameter("kid");

		CableSegment csdata = new CableSegment();
		CableSegmentBean csbean = new CableSegmentBean();
		CableSegmentBO bo = new CableSegmentBO();
		if (kid != null) {
			csdata = bo.getCableSegment(kid);
			BeanUtil.objectCopy(csdata, csbean);
			request.setAttribute("cablesegmentBean", csbean);
			request.setAttribute("TYPE", type);
		}
		return mapping.findForward("loadeditform");
	}

	public ActionForward loadAddForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		if (!CheckPower.checkPower(request.getSession(), "72102")) {
			return mapping.findForward("powererror");
		}

		return mapping.findForward("loadaddform");
	}

	public ActionForward loadQueryForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {

		return mapping.findForward("loadqueryform");
	}

	public String getTotalQueryString(String queryString, CableSegmentBean bean) {
		if (bean.getSegmentname() != null) {
			queryString = queryString + bean.getSegmentname();
		}
		if (bean.getId() != null) {
			queryString = queryString + "&id=" + bean.getId();
		}
		if (bean.getKid() != null) {
			queryString = queryString + "&kid=" + bean.getKid();
		}
		if (bean.getCabletype() != null) {
			queryString = queryString + "&cabletype=" + bean.getCabletype();
		}
		return queryString;
	}

	public ActionForward exportCableSegment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			logger.info(" 创建dao");
			List list = (List) request.getSession().getAttribute("cablelist");
			logger.info("得到list");
			super.getService().exportCableSegment(list, response);
			logger.info("输出excel成功");
			return null;
		} catch (Exception e) {
			logger.error("导出信息报表出现异常:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}
}
