package com.cabletech.linepatrol.project.action;

import java.util.*;

import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.beans.*;
import com.cabletech.commons.web.*;
import com.cabletech.linepatrol.project.beans.CountyInfoBean;
import com.cabletech.linepatrol.project.domain.ProjectCountyInfo;
import com.cabletech.linepatrol.project.service.CountyInfoBO;

public class CountyInfoAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger.getLogger(CountyInfoAction.class
			.getName());
	private UserInfo userInfo;
	private WebApplicationContext ctx;
	private CountyInfoBO bo;

	public void initialize(HttpServletRequest request) {
		ctx = getWebApplicationContext();
		userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		bo = (CountyInfoBO) ctx.getBean("countyInfoBO");
	}

	/**
	 * 新增一个县
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @throws ClientException
	 * @throws Exception
	 * @return ActionForward
	 */
	public ActionForward addCounty(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initialize(request);
		CountyInfoBean bean = (CountyInfoBean) form;
		ProjectCountyInfo data = new ProjectCountyInfo();
		BeanUtil.objectCopy(bean, data);
		data.setId("-1");
		String backUrl = request.getContextPath()
				+ "/linepatrol/remedy/addCounty.jsp";
		if (bo.judgeCountyExist(data)) {
			return super.forwardErrorPageWithUrl(mapping, request,
					"0011quxianerror", backUrl);
		}
		bo.addCounty(data);
		log(request, " 增加修缮区域信息 ", " 基础资料管理 ");
		return forwardInfoPage(mapping, request, "0011quxian");
	}

	/**
	 * 载入信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward loadCounty(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		initialize(request);
		ProjectCountyInfo data = bo.loadCounty(request.getParameter("id"));
		CountyInfoBean bean = new CountyInfoBean();
		BeanUtil.objectCopy(data, bean);
		request.setAttribute("countyinfoBean", bean);
		request.getSession().setAttribute("countyinfoBean", bean);
		return mapping.findForward("updateCounty");
	}

	/**
	 * 查询县级区域信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward queryCounty(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		initialize(request);
		CountyInfoBean bean = (CountyInfoBean) form;
		List list = bo.queryForList(bean, userInfo);
		request.getSession().setAttribute("queryresult", list);
		super.setPageReset(request);
		return mapping.findForward("querycountyresult");
	}

	/**
	 * 修改县级信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward updateCounty(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		initialize(request);
		ProjectCountyInfo data = new ProjectCountyInfo();
		BeanUtil.objectCopy((CountyInfoBean) form, data);
		String backUrl = (String) request.getSession().getAttribute(
				"S_BACK_URL");
		if (bo.judgeCountyExist(data)) {
			return super.forwardErrorPageWithUrl(mapping, request,
					"0011quxianerror", backUrl);
		}
		bo.updateCounty(data);
		log(request, " 更新修缮区域信息 ", " 基础资料管理 ");
		return forwardInfoPageWithUrl(mapping, request, "0022quxian", backUrl);
	}

	/**
	 * 删除县区域
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward deleteCounty(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		initialize(request);
		String id = (String) request.getParameter("id");
		String backUrl = (String) request.getSession().getAttribute(
				"S_BACK_URL");
		bo.deleteCounty(id);
		return forwardInfoPageWithUrl(mapping, request, "0023quxian", backUrl);
	}

	/**
	 * 
	 * @param queryString
	 * @param bean
	 * @return
	 */
	public String getTotalQueryString(String queryString, CountyInfoBean bean) {
		if (bean.getTown() != null) {
			queryString = queryString + bean.getTown();
		}
		if (bean.getId() != null) {
			queryString = queryString + "&id=" + bean.getId();
		}
		if (bean.getRegionid() != null) {
			queryString = queryString + "&regionid=" + bean.getRegionid();
		}
		return queryString;
	}

	public void initRemedyBaseBo() {
		// TODO Auto-generated method stub
	}
}
