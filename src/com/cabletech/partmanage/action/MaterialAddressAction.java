package com.cabletech.partmanage.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.ExportBO;
import com.cabletech.commons.web.ClientException;
import com.cabletech.partmanage.beans.MaterialAddressBean;
import com.cabletech.partmanage.dao.MaterialAddressDao;
import com.cabletech.statistics.dao.StatDao;

public class MaterialAddressAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger.getLogger(MaterialAddressAction.class
			.getName());

	/**
	 * ��Ӳ��ϴ�ŵص���תҳ��
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
	 * ��Ӵ�ŵص���Ϣ
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
		MaterialAddressDao dao = new MaterialAddressDao();
		if (dao.judgeData(bean,"") != null && dao.judgeData(bean,"").size() > 0) {
			return forwardInfoPage(mapping, request, "ad4444");
		}
		bean.setId(this.getDbService().getSeq("linepatrol_mt_addr", 20));
		boolean sucess = dao.addPartAddress(bean);
		if (sucess) {
			return forwardInfoPage(mapping, request, "ad111");
		} else {
			log(request, " ���Ӳ��ϴ����Ϣ ", " ���Ϲ��� ");
			return forwardErrorPage(mapping, request, "error");
		}
	}

	/**
	 * �����ŵص���Ϣ
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
		MaterialAddressDao dao = new MaterialAddressDao();
		MaterialAddressBean bean = (MaterialAddressBean) form;
		try {
			String id = request.getParameter("id");
			bean = dao.getPartaddressById(id, bean);
			UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
					"LOGIN_USER");
			StatDao statdao = new StatDao();
			List deptCollection = statdao.getSelectForTag("contractorname",
					"contractorid", "contractorinfo", statdao
							.createSqlCondtion(userinfo));
			request.setAttribute("deptCollection", deptCollection);
			request.setAttribute("Part_addressBean", bean);
		} catch (Exception e) {
			logger.info("����·����Ϣʧ�ܣ�");
			e.printStackTrace();
		}
		return mapping.findForward("updatepartaddress");
	}

	/**
	 * ɾ����ŵص���Ϣ
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
		MaterialAddressDao dao = new MaterialAddressDao();
		String id = request.getParameter("id");
		boolean sucess = dao.deletePartaddressById(id);
		if (sucess) {
			log(request, " ɾ��·����Ϣ ", " �������Ϲ��� ");
			String strQueryString = getTotalQueryString(
					"method=queryAddress&address=",
					(MaterialAddressBean) request.getSession().getAttribute(
							"theQueryBean"));
			Object[] args = getURLArgs("/WebApp/materialAddressAction.do",
					strQueryString, (String) request.getSession().getAttribute(
							"S_BACK_URL"));
			return forwardInfoPage(mapping, request, "ad333", null, args);
		} else {
			return forwardErrorPage(mapping, request, "error");
		}
	}

	/**
	 * ��ѯ��ŵص���Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryAddress(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialAddressBean bean = (MaterialAddressBean) form;
		MaterialAddressDao dao = new MaterialAddressDao();
		request.getSession().setAttribute("S_BACK_URL", null); //
		request.getSession().setAttribute("theQueryBean", bean);//
		try {
			List list = dao.getPartaddressBean(bean);
			request.getSession().setAttribute("queryresult", list);
			super.setPageReset(request);
			System.out.println((String) request.getSession().getAttribute(
					"S_BACK_URL"));
			return mapping.findForward("queryaddressresult");
		} catch (Exception e) {
			logger.error("��ѯ·����Ϣ�쳣:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}

	/**
	 * ��ѯ��ŵص��ҳ��
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
	 * �޸Ĳ��ϴ�ŵص���Ϣ
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
		String args1 = (String)request.getSession().getAttribute("S_BACK_URL");
		Object[] objects = new Object[1];
		objects[0] = args1;
		MaterialAddressDao dao = new MaterialAddressDao();
		if(dao.judgeData(bean,"update") != null && dao.judgeData(bean,"update").size() > 0){
			return super.forwardInfoPage(mapping, request, "ad5555",null,objects);
		}
		boolean sucess = dao.updatePartaddress(bean);
		if (sucess) {
			log(request, " ���²��ϴ����Ϣ ", " ���Ϲ��� ");
			String strQueryString = getTotalQueryString(
					"method=queryAddress&address=",
					(MaterialAddressBean) request.getSession().getAttribute(
					"theQueryBean"));
			Object[] args = getURLArgs("/WebApp/materialAddressAction.do",
					strQueryString, (String) request.getSession().getAttribute(
					"S_BACK_URL"));
			return forwardInfoPage(mapping, request, "ad222", null, args);
		} else {
			return forwardErrorPage(mapping, request, "error");
		}
	}

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

	// �������ϴ�ŵص���Ϣ�б�
	public ActionForward exportMaterialAddressResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ExportBO export = new ExportBO();
		try {
			logger.info(" ����dao");
			List list = (List) request.getSession().getAttribute("queryresult");
			logger.info("�õ�list");
			export.exportMaterialAddressResult(list, response);
			logger.info("���excel�ɹ�");
			return null;
		} catch (Exception e) {
			logger.error("������Ϣ��������쳣:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}

}
