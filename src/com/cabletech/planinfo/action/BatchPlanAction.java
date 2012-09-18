package com.cabletech.planinfo.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.planinfo.beans.BatchBean;
import com.cabletech.planinfo.domainobjects.StencilTask;
import com.cabletech.planinfo.services.BatchPlanBO;
import com.cabletech.planinfo.services.BatchPlanThread;
import com.cabletech.planinfo.services.PlanBaseService;

public class BatchPlanAction extends PlanInfoBaseDispatchAction {
	Logger logger = Logger.getLogger("BatchPlanAction");

	private BatchPlanBO batchBO = new BatchPlanBO();

	/**
	 * ���������ƻ���Ϣ��session��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addBatchPlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
		session.removeAttribute("batch");
		session.removeAttribute("patrol");
		BatchBean batchplan = (BatchBean) form;
		PlanBaseService pbs = new PlanBaseService();
		/* ��֤�Ƿ��¼ƻ����ƶ���ͨ������ */
		boolean b = pbs.isInstituteMPlan(batchplan.getStartdate().substring(5,7), batchplan.getStartdate().substring(0, 4), userinfo.getDeptID());
		if (!b) {
			return forwardInfoPage(mapping, request, "w20304_batch");
		}
		/* ��֤��ʱ������Ƿ���Ѳ��ƻ� */

		// if(pbs.checkDate(batchplan.getStartdate(),userinfo.getDeptID())){
		// return forwardInfoPage(mapping, request, "w21601");
		// }
		session.setAttribute("batch", batchplan);
		// List patrol = batchBO.getPatrolList(userinfo.getDeptID()); /*��Ѳ��Ա�ƶ�*/
		List patrol = batchBO.getPatrolStencilList(userinfo.getDeptID());
		session.setAttribute("patrol", patrol);
		request.setAttribute("patrol", patrol);

		return mapping.findForward("selectPatrol");
	}

	/**
	 * �������ɼƻ�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward createBatchPlan(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
		String deptname = (String) session.getAttribute("LOGIN_USER_DEPT_NAME");
		List patrollist = (List) session.getAttribute("patrol");
		BatchBean batch = (BatchBean) session.getAttribute("batch");
		List stencilList = new ArrayList();
		
		//2009��6��10�� ����ά����ĸ�������ѡ�����ַ�ʽ���
		
		String count = request.getParameter("patrolCount");
		int len = 0;
		logger.info("Count: "+count);
		if (count.equals("1")) {
			len = 1;
			String patrolId = request.getParameter("patrol");
			String stencilid = request.getParameter(patrolId);
			boolean suess = batchBO.checkDatePatrol(batch.getStartdate(),
					patrolId);
			if (null != stencilid && !suess) {
				logger.info("stencil " + stencilid);
				StencilTask stencil = new StencilTask();
				stencil.setPatrolid(patrolId);
				stencil.setID(stencilid);
				stencil.setStencilname(batchBO.searchPatrol(patrollist,
						patrolId));// Ѳ���������
				stencilList.add(stencil);
			}
		} else {
			String[] patrolAr = request.getParameterValues("patrol");
			len = patrolAr.length;
			for (int i = 0; i < patrolAr.length; i++) {
				String stencilid = request.getParameter(patrolAr[i]);
				boolean suess = batchBO.checkDatePatrol(batch.getStartdate(),
						patrolAr[i]);
				if (null != stencilid && !suess) {
					logger.info("stencil " + stencilid);
					StencilTask stencil = new StencilTask();
					stencil.setPatrolid(patrolAr[i]);
					stencil.setID(stencilid);
					stencil.setStencilname(batchBO.searchPatrol(patrollist,
							patrolAr[i]));// Ѳ���������
					stencilList.add(stencil);
				}
			}
		}
		if (stencilList.size() == 0) {
			return forwardInfoPage(mapping, request, "f21602");
		}

		batch.setContractorid(userinfo.getDeptID());
		Thread thread = new Thread(new BatchPlanThread(deptname, userinfo,
				batch, stencilList, len));
		thread.start();

		// boolean b=
		// batchBO.createBatchPlan4Stencil(deptname,userinfo.getRegionid(),batch,stencilList);
		// String strForward = "s21601";
		// if(!b){
		// strForward = "f21601";
		// }
		log(request,"�����ƻ��ƶ����ƻ�����Ϊ��"+batch.getBatchname()+"��","Ѳ�����");
		return forwardInfoPage(mapping, request, "batch01");
	}

	/**
	 * c��ѯ�����ƻ�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryBatchPlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");

		String batchname = (String) request.getParameter("batchname");
		List batch = batchBO.queryBatch(userinfo, batchname);
		session.setAttribute("batch", batch);
		return mapping.findForward("queryBatch");
	}

	/**
	 * ���������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchBatchPlan(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
		return mapping.findForward("searchBatch");
	}

	/**
	 * ɾ�������ƻ�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delBatchPlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
		String id = request.getParameter("id");
		String strForward = "s21605";
		boolean b = batchBO.removeBatch(id);
		;
		if (!b) {
			strForward = "f21605";
		}
		String url=request.getContextPath()+"/batchPlan.do?method=queryBatchPlan";
		log(request,"ɾ�������ƻ�","Ѳ�����");
		return forwardInfoPageWithUrl(mapping, request, strForward,url);
	}

}
