package com.cabletech.baseinfo.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.beans.ContractBean;
import com.cabletech.baseinfo.domainobjects.Contract;
import com.cabletech.baseinfo.services.ContractBO;
import com.cabletech.commons.base.BaseDispatchAction;

/**
 * ��ά��λ�б��ͬAction
 * 
 * @author liusq
 * 
 */
public class ContractAction extends BaseDispatchAction {

	private static final long serialVersionUID = -3286517980610962730L;
	Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * ת����Ӵ�ά��λǩԼ��ͬҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addContractForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ContractBO contractBO = (ContractBO) ctx.getBean("contractBO");
		String contractorId = request.getParameter("contractorId");
		Map<String, Object> map = contractBO.addContractForm(contractorId);
		String contractorName = (String) map.get("contractorName");
		String[] years = (String[]) map.get("years");
		request.setAttribute("contractorId", contractorId);
		request.setAttribute("contractorName", contractorName);
		request.setAttribute("years", years);
		return mapping.findForward("addContract");
	}

	/**
	 * ��Ӵ�ά��λǩԼ��ͬ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addContract(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		ContractBO contractBO = (ContractBO) ctx.getBean("contractBO");
		ContractBean bean = (ContractBean) form;
		// �Ƿ�����ͬ��ǩԼ��ͬ��
		boolean hasSameContractNo = contractBO.hasSameContractNo(bean);
		if (hasSameContractNo) {
			return forwardErrorPage(mapping, request, "hasSameContractNo");
		}
		// �Ƿ��Ѵ��ڸ����ǩԼ��ͬ
		boolean isDoubleContract = contractBO.hasBeenAddContract(bean);
		if (isDoubleContract) {
			return forwardErrorPage(mapping, request, "doubleContract");
		}
		contractBO.saveContract(bean);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		return forwardInfoPageWithUrl(mapping, request, "addContractSuccess",
				url);
	}

	/**
	 * ת�������ά��λǩԼ��ͬҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editContractForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ContractBO contractBO = (ContractBO) ctx.getBean("contractBO");
		String contractorId = request.getParameter("contractorId");
		Map<String, Object> map = contractBO.editContractForm(contractorId);
		String contractorName = (String) map.get("contractorName");
		Contract contract = (Contract) map.get("contract");
		request.setAttribute("contractorName", contractorName);
		request.setAttribute("contract", contract);
		return mapping.findForward("editContract");
	}

	/**
	 * ���´�ά��λǩԼ��ͬ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editContract(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		ContractBO contractBO = (ContractBO) ctx.getBean("contractBO");
		ContractBean bean = (ContractBean) form;
		// �Ƿ�����ͬ��ǩԼ��ͬ��
		boolean hasSameContractNo = contractBO.hasSameContractNo(bean);
		if (hasSameContractNo) {
			return forwardErrorPage(mapping, request, "hasSameContractNo");
		}
		contractBO.saveContract(bean);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		return forwardInfoPageWithUrl(mapping, request, "editContractSuccess",
				url);
	}

	/**
	 * �鿴��ά��λǩԼ��ͬҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewContract(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		ContractBO contractBO = (ContractBO) ctx.getBean("contractBO");
		String contractorId = request.getParameter("contractorId");
		Map<String, Object> map = contractBO.viewContract(contractorId);
		Contract thisYearContract = (Contract) map.get("thisYearContract");
		@SuppressWarnings("unchecked")
		List<Contract> contractList = (List<Contract>) map.get("contractList");
		String contractorName = (String) map.get("contractorName");
		request.setAttribute("thisYearContract", thisYearContract);
		request.setAttribute("contractList", contractList);
		request.setAttribute("contractorName", contractorName);
		return mapping.findForward("viewContract");
	}
}
