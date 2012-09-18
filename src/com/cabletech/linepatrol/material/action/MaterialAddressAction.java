package com.cabletech.linepatrol.material.action;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.ExportBO;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.web.ClientException;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.linepatrol.material.beans.MaterialAddressBean;
import com.cabletech.linepatrol.material.domain.MaterialAddress;
import com.cabletech.linepatrol.material.service.MaterialAddressBo;
import com.cabletech.statistics.dao.StatDao;

public class MaterialAddressAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger.getLogger(MaterialAddressAction.class
			.getName());

	/**
	 * 获得MaterialAddressBo对象
	 * 
	 * @return
	 */
	private MaterialAddressBo getMaterialAddressService() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (MaterialAddressBo) ctx.getBean("materialAddressBo");
	}

	/**
	 * 添加材料存放地点跳转页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward addAddress_Form(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		StatDao statdao = new StatDao();
		List deptCollection = statdao.getSelectForTag("contractorname",
				"contractorid", "contractorinfo", statdao
						.createSqlCondtion(userinfo));
		request.setAttribute("deptCollection", deptCollection);
		return mapping.findForward("addAddrForm");
	}

	/**
	 * 添加存放地点信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward addAddress(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		MaterialAddressBean bean = (MaterialAddressBean) form;
		MaterialAddress materialAddress = new MaterialAddress();
		BeanUtil.objectCopy(bean, materialAddress);
		MaterialAddressBo materialAddressBo = getMaterialAddressService();
		if (materialAddressBo.judgeData(materialAddress, "") != null
				&& materialAddressBo.judgeData(materialAddress, "").size() > 0) {
			return forwardInfoPage(mapping, request, "ad4444");
		}
		String id = this.getDbService().getSeq("linepatrol_mt_addr", 20);
		logger.info("*****************" + id);
		materialAddress.setId(id);
		try {
			materialAddressBo.addPartAddress(materialAddress);
			log(request,"添加材料存放点信息（存放地点为："+bean.getAddress()+"）","材料管理");
			return forwardInfoPage(mapping, request, "ad111");
		} catch (ServiceException e) {
			return forwardErrorPage(mapping, request, "error");
		}
	}

	/**
	 * 载入存放地点信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward loadAddress(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		MaterialAddressBo materialAddressBo = getMaterialAddressService();
		MaterialAddressBean bean1 = (MaterialAddressBean) form;
		MaterialAddress materialAddress = new MaterialAddress();
		BeanUtil.objectCopy(bean1, materialAddress);
		try {
			String id = request.getParameter("id");
			MaterialAddress bean = materialAddressBo.getPartaddressById(id,
					materialAddress);
			UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
					"LOGIN_USER");
			StatDao statdao = new StatDao();
			List deptCollection = statdao.getSelectForTag("contractorname",
					"contractorid", "contractorinfo", statdao
							.createSqlCondtion(userinfo));
			request.setAttribute("deptCollection", deptCollection);
			request.setAttribute("bean", bean);
		} catch (Exception e) {
			logger.info("加载路由信息失败！");
			e.printStackTrace();
		}
		return mapping.findForward("updatepartaddress");
	}

	/**
	 * 删除存放地点信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward deletePartAddress(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		MaterialAddressBo materialAddressBo = getMaterialAddressService();
		String id = request.getParameter("id");
		MaterialAddress materialAddress=new MaterialAddress();
		materialAddressBo.getPartaddressById(id, materialAddress);
		String address=materialAddress.getAddress();
		try {
			materialAddressBo.deletePartaddressById(id);
			String strQueryString = getTotalQueryString(
					"method=queryAddress&address=",
					(MaterialAddressBean) request.getSession().getAttribute(
							"theQueryBean"));
			Object[] args = getURLArgs("/WebApp/MTAddressAction.do",
					strQueryString, (String) request.getSession().getAttribute(
							"S_BACK_URL"));
			log(request,"删除材料存放点信息（存放地点为："+address+"）","材料管理");
			return forwardInfoPage(mapping, request, "ad333", null, args);
		} catch (ServiceException e) {
			return forwardErrorPage(mapping, request, "error");
		}
	}

	/**
	 * 查询存放地点信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ActionForward queryAddress(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws InvocationTargetException, IllegalAccessException {
		MaterialAddressBean bean = (MaterialAddressBean) form;
		MaterialAddressBo materialAddressBo = getMaterialAddressService();
		request.getSession().setAttribute("S_BACK_URL", null); //
		request.getSession().setAttribute("theQueryBean", bean);//
		MaterialAddress materialAddress = new MaterialAddress();
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		BeanUtil.objectCopy(bean, materialAddress);
		if ("2".equals(user.getDeptype())) {
			materialAddress.setContractorid(user.getDeptID());
		}
		try {
			super.setPageReset(request);
			List list = materialAddressBo.getPartaddressBean(materialAddress);
			request.getSession().setAttribute("queryresult", list);
			System.out.println((String) request.getSession().getAttribute(
					"S_BACK_URL"));
			return mapping.findForward("queryaddressresult");
		} catch (Exception e) {
			logger.error("查询路由信息异常:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}

	/**
	 * 查询存放地点表单页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward queryAddress_Form(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		StatDao statdao = new StatDao();
		List deptCollection = statdao.getSelectForTag("contractorname",
				"contractorid", "contractorinfo", statdao
						.createSqlCondtion(userinfo));
		request.setAttribute("deptCollection", deptCollection);
		return mapping.findForward("queryAddrForm");
	}

	/**
	 * 修改材料存放地点信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward updatePartAddress(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		MaterialAddressBean bean = (MaterialAddressBean) form;
		MaterialAddress materialAddress = new MaterialAddress();
		BeanUtil.objectCopy(bean, materialAddress);
		String args1 = (String) request.getSession().getAttribute("S_BACK_URL");
		Object[] objects = new Object[1];
		objects[0] = args1;
		MaterialAddressBo materialAddressBo = getMaterialAddressService();
		if (materialAddressBo.judgeData(materialAddress, "update") != null
				&& materialAddressBo.judgeData(materialAddress, "update")
						.size() > 0) {
			return super.forwardInfoPage(mapping, request, "ad5555", null,
					objects);
		}
		try {
			materialAddressBo.updatePartaddress(materialAddress);
			String strQueryString = getTotalQueryString(
					"method=queryAddress&address=",
					(MaterialAddressBean) request.getSession().getAttribute(
							"theQueryBean"));
			Object[] args = getURLArgs("/WebApp/MTAddressAction.do",
					strQueryString, (String) request.getSession().getAttribute(
							"S_BACK_URL"));
			log(request,"修改材料存放点信息（存放地点为："+bean.getAddress()+"）","材料管理");
			return forwardInfoPage(mapping, request, "ad222", null, args);
		} catch (ServiceException e) {
			return forwardErrorPage(mapping, request, "error");
		}
	}

	/**
	 * 更新材料存放地点后，获得返回的URL
	 * 
	 * @param queryString
	 * @param bean
	 * @return
	 */
	public String getTotalQueryString(String queryString,
			MaterialAddressBean bean) {
		if (bean.getAddress() != null) {
			queryString = queryString + bean.getAddress();
		}
		if (bean.getId() != null) {
			queryString = queryString + "&id=" + bean.getId();
		}
		if (bean.getContractorid() != null) {
			queryString = queryString + "&contractorid="
					+ bean.getContractorid();
		}
		return queryString;
	}

	/**
	 * 导出材料存放地点信息列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportMaterialAddressResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ExportBO export = new ExportBO();
		try {
			logger.info(" 创建dao");
			List list = (List) request.getSession().getAttribute("queryresult");
			logger.info("得到list");
			export.exportMaterialAddressResult(list, response);
			logger.info("输出excel成功");
			return null;
		} catch (Exception e) {
			logger.error("导出信息报表出现异常:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}

}
