package com.cabletech.sparepartmanage.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.power.CheckPower;
import com.cabletech.sparepartmanage.beans.SparepartApplyBean;
import com.cabletech.sparepartmanage.beans.SparepartAuditingBean;
import com.cabletech.sparepartmanage.domainobjects.SparepartApplyF;
import com.cabletech.sparepartmanage.domainobjects.SparepartApplyS;
import com.cabletech.sparepartmanage.services.SeparepartBaseInfoService;
import com.cabletech.sparepartmanage.services.SparepartApplyBO;
import com.cabletech.sparepartmanage.services.SparepartAuditingBO;

/**
 * SparepartStorageAuditingAction ����������˵�Action ��ɱ����������˺������ʷ����ʾ
 */
public class SparepartAuditingAction extends BaseDispatchAction {
	private static Logger logger = Logger.getLogger(SparepartAuditingAction.class.getName());
	private SeparepartBaseInfoService basebo = new SeparepartBaseInfoService();
	private SparepartAuditingBO bo = new SparepartAuditingBO();
	private SparepartApplyBO applybo = new SparepartApplyBO();


	/**
	 * ��������������ʷ�б�
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
	 * @throws Exception
	 */
	public ActionForward listAuditingApplyHistory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String applyId = request.getParameter("apply_id");
		String condition = " and apply_id='" + applyId + "'";
		List list = bo.list(condition);
		request.setAttribute("auditing_list", list);
		return mapping.findForward("listAuditingApplyHistory");
	}

	/**
	 * �����������˱�
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
	 * @throws Exception
	 */
	public ActionForward auditingApplyForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (!CheckPower.checkPower(request.getSession(), "90708")) {
			return mapping.findForward("powererror");
		}	
		String applyFId = request.getParameter("apply_id");
		String viewMethod = request.getParameter("view_method");
		SparepartApplyBean bean = new SparepartApplyBean();		
		SparepartApplyF  applyF = applybo.getApplyF(applyFId);		
		List applySs = applybo.getApplySs(applyFId);

		//�������� �������к� ���벿�� ʹ�÷�ʽ �������� �������������к� ����ʹ��λ�� ������ ���뱸ע
		bean.setTid(applyFId);
		bean.setSparePartId(applyF.getSparePartId());
		bean.setApplyDate(applyF.getApplyDate());
		bean.setApplyPerson(applyF.getApplyPerson());
		bean.setApplyRemark(applyF.getApplyRemark());
		bean.setApplyUsePosition(applyF.getApplyUsePosition());
		bean.setAuditingState(applyF.getAuditingState());
		bean.setReplaceType(applyF.getReplaceType());
		bean.setUsedPosition(applyF.getUsedPosition());
		bean.setUseMode(applyF.getUseMode());
		bean.setPatrolgroupId(applyF.getPatrolgroupId());
		String sparepartname = basebo.getOneSparepart(bean.getSparePartId()).getSparePartName();
		bean.setSparePartName(sparepartname);
		bean.setUsedSparePartName(sparepartname);		
		String applyperson = applybo.getUserNameById(bean.getApplyPerson());
		bean.setApplyPerson(applyperson);
		
		String patrolgroupName = applybo.getPatrolgroupNameById(applyF.getPatrolgroupId());
		bean.setPatrolgroupName(patrolgroupName);
		request.setAttribute("one_apply", bean);
		request.setAttribute("applySs", applySs);

		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		request.setAttribute("user_name", user.getUserName());
		request.setAttribute("user_id", user.getUserID());
		return mapping.findForward("auditingApplyForm");
	}

	/**
	 * ������������
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
	 * @throws Exception
	 */
	public ActionForward auditingApply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (!CheckPower.checkPower(request.getSession(), "90708")) {
			return mapping.findForward("powererror");
		}
		SparepartAuditingBean bean = (SparepartAuditingBean) form;
		String applyfid =  bean.getApplyId();
		SparepartApplyF applyF = applybo.getApplyF(applyfid);			
		String sparepartid = applyF.getSparePartId();
		bo.addAuditngInfo(bean);
		boolean flag = bo.updateAuditngState(applyfid,bean,sparepartid);	
		if (flag) {
			Map param = new HashMap();
			param.clear();
			param.put("storage_id", request.getParameter("storage_id"));
			return super.forwardInfoPageWithParam(mapping, request, "9070301ok", param);
		} else {
			Map param = new HashMap();
			param.clear();
			param.put("apply_id", bean.getApplyId());
			return super.forwardErrorPageWithParam(mapping, request, "9070301err", param);
		}
	}




}
