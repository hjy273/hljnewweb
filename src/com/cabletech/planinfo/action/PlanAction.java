package com.cabletech.planinfo.action;

import java.util.*;

import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;

import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.baseinfo.services.*;
import com.cabletech.commons.beans.*;
import com.cabletech.commons.hb.*;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.commons.util.SysConstant;
import com.cabletech.commons.web.*;
import com.cabletech.planinfo.beans.*;
import com.cabletech.planinfo.domainobjects.*;
import com.cabletech.planinfo.services.*;
import com.cabletech.utils.*;

public class PlanAction extends PlanInfoBaseDispatchAction {
	Logger logger = Logger.getLogger("PlanAction");

	/**
	 * ��ʾ�ƶ��ƻ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addWPlanShow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		//String type = request.getParameter("type");
		String sql = "";
		sql = "select p.PATROLID,p.PATROLNAME  from patrolmaninfo p ";
		if ("2".equals(userinfo.getDeptype())) {
			sql = sql + " where p.parentid='" + userinfo.getDeptID() + "'";
		} else {
			sql = sql + " where p.regionid = '" + userinfo.getRegionID() + "'";
		}
		sql += " and p.state is null ";
		try {
			QueryUtil query = new QueryUtil();
			List lgroup = query.queryBeans(sql);
			request.setAttribute("patrolgroup", lgroup);
			return mapping.findForward("addPlanForm");
		} catch (Exception e) {
			logger.warn("��ʾ�ƶ��ƻ��쳣:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}
	/**
	 * �����ƻ�,���䱣����session��,�������������ص��ƶ�ҳ��.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward createPlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo)session.getAttribute("LOGIN_USER");
		PlanBean _pb = (PlanBean) form;
		PlanBO pbo = new PlanBO();
		PlanBaseService pbs = new PlanBaseService();
		String isLoad = request.getParameter("isload");//�Ƿ�Ϊ����ƻ�
		String plantype = request.getParameter("plantype");//��üƻ����� 
		// ���session�е���������.
		session.removeAttribute("Plan");
		session.removeAttribute("taskList");
		session.removeAttribute("strsubline");
		session.removeAttribute("edittask");
		session.removeAttribute("st");//�Ƿ��޸Ĺ���
		/*��֤�Ƿ��¼ƻ����ƶ���ͨ������*/
		boolean b = pbs.isInstituteMPlan(_pb.getBegindate().substring(5,7), _pb.getBegindate().substring(0,4), userinfo.getDeptID());
		if(!b){
			return forwardInfoPage(mapping, request, "w20304");
		}
		// �жϼƻ������Ƿ����
		if(pbo.checkPlanName(_pb.getPlanname())){
			return forwardInfoPage(mapping, request, "w20302");
		}
		//�ж����������Ƿ��д��ڼƻ�
		if(pbo.checkDate(_pb.getBegindate(),_pb.getExecutorid())){
			return forwardInfoPage(mapping, request, "w20303");
		}
		if(!plantype.equals(SysConstant.PLAN_TYPE_WEEK)){
			_pb.setId(super.getDbService().getSeq("PLAN",12));
		}
		_pb.setRegionid(super.getLoginUserInfo(request).getRegionid());
		_pb.setCreator(super.getLoginUserInfo(request).getUserName());
		_pb.setCreatedate(DateUtil.getNowDateString());
		//����ƻ��Ƿ����
		if (super.getService().checkWPlanUnique(_pb.getId()) != 1) {
			return forwardInfoPage(mapping, request, "w20301");
		}
		session.setAttribute("Plan", _pb);
		session.setAttribute("EditS", "add");
		session.setAttribute("strsubline", "");
		session.setAttribute("edittask", "add");
		session.setAttribute("taskList", new ArrayList());
		if(isLoad.equals("y")){
			//��ȡģ��
			
			List stencillist = pbo.getStencilList(userinfo,_pb.getExecutorid());
			logger.info("size:"+stencillist.size());
			request.setAttribute("stencilList", stencillist);
			return mapping.findForward("loadPlanTask");
		}
		TaskBO taskbo = new TaskBO();
		String taskHtml = taskbo.getPUnitListByPatrolid(_pb.getExecutorid(), "1","");
		request.setAttribute("tasksublineHtm", taskHtml);
		
		session.setAttribute("sublinelist", taskbo.getPSublineByPatrol(_pb.getExecutorid(), "1"));
		return mapping.findForward("addPlanTask");

	}
	/**
	 * �����ƶ��ƻ�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward savePlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		long startTime = System.currentTimeMillis();
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
		PlanBean _planb = (PlanBean)session.getAttribute("Plan");
		_planb.setIfinnercheck("1");
		List taskList = (List)session.getAttribute("taskList");
		if(taskList.size()<1){
			session.setAttribute("strsubline", "");
			return forwardInfoPage(mapping, request, "f20304");
		}
		
		for (int i = 0; i < taskList.size(); i++) {
			TaskBean taskbean = (TaskBean) taskList.get(i);
			if(taskbean.getTaskSubline().size()<1){
				return forwardInfoPage(mapping, request, "f20301");
			}
		}
		logger.info("size:"+taskList.size());
		PlanBO _po = new PlanBO();
		String strFor = "f20301";
		Plan _plan = new Plan();
		BeanUtil.objectCopy(_planb, _plan);
		
		boolean b = _po.savePlan(_plan,taskList);
		if(b){
			strFor = "s20301";
		}
		//���session
		session.removeAttribute("Plan");
		session.removeAttribute("taskList");
		session.removeAttribute("EditS");
		session.removeAttribute("strsubline");
		session.removeAttribute("edittask");
		session.removeAttribute("st");
		long endTime = System.currentTimeMillis();
		long total=(endTime - startTime);
		logger.info("���task��ʱ��"+total+"����") ;
		log(request,"���Ѳ��ƻ����ƻ�����Ϊ��"+_planb.getPlanname()+"��","Ѳ�����");
		return forwardInfoPage(mapping, request, strFor);
	}
	/**
	 * ���ؼƻ���Ϣ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward loadPlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		long starttime = System.currentTimeMillis();
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");//��ȡ�û���Ϣ
		
		PlanBO pbo = new PlanBO();
		PlanBean bean = new PlanBean();
		Plan data = pbo.loadPlan(request.getParameter("id"));//��ȡ�ƻ�.
		BeanUtil.objectCopy(data, bean);
		List taskList = pbo.getTaskList(bean.getId());
		long endtime = System.currentTimeMillis();
		long total=(endtime - starttime)/1000;
		logger.info("ִ��ʱ�䣺"+total/60+"��"+total%60+"��");
		session.setAttribute("strsubline", "");
		session.setAttribute("Plan", bean);
		session.setAttribute("delTaskList", new ArrayList());
		session.setAttribute("taskList", taskList);
		session.setAttribute("EditS", "edit");
		String forwardPage = "editPlanResult";
		return mapping.findForward(forwardPage);
	}
	
	/**
	 * �������¼ƻ�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward updatePlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
		PlanBean bean = (PlanBean)session.getAttribute("Plan");
		Plan plan = new Plan();
		BeanUtil.objectCopy(bean,plan);
		String st = (String)session.getAttribute("st");
		List delTaskList = (List) session.getAttribute("delTaskList");
		logger.info("st"+st);
		String strFor = "f20302";
		if(st != null && st.equals("y")){
			List taskList = (List)session.getAttribute("taskList");
			if(taskList.size()<1){
				session.setAttribute("strsubline", "");
				return forwardInfoPage(mapping, request, "f20304");
			}
			logger.info("Llist "+taskList.size());
			
			PlanBO pbo = new PlanBO();
			boolean b = pbo.saveOrUpdatePlan(plan,taskList,delTaskList);
			
			if(b){
				strFor = "s20302";
			}
		}else{
			strFor = "s20302";
		}
		//���session
		session.removeAttribute("Plan");
		session.removeAttribute("taskList");
		session.removeAttribute("EditS");
		session.removeAttribute("strsubline");
		session.removeAttribute("edittask");
		session.removeAttribute("delTaskList");
		session.removeAttribute("st");
		log(request,"����Ѳ��ƻ����ƻ�����Ϊ��"+bean.getPlanname()+"��","Ѳ�����");
		return forwardInfoPage(mapping, request, strFor);
	}
	/**
	 * ɾ���ƻ�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward deletePlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		
		String planid = request.getParameter("id");
		Plan data = super.getService().loadPlan(planid);
		String planName=data.getPlanname();
		PlanBO pbo = new PlanBO();
		pbo.removePlan(data);
		log(request, " ɾ��Ѳ��ƻ����ƻ�����Ϊ��"+planName+"�� ", " Ѳ��ƻ����� ");
		return forwardInfoPage(mapping, request, "s20303");
		
	}
	/**
	 * ��ѯѲ��ƻ���Ϣ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryPlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
		PlanBean bean = (PlanBean) form;
		String _regionid = request.getParameter("regionid");
		String _workID = request.getParameter("workID");
		String createDate = request.getParameter("createdate");
		PlanBO pbo = new PlanBO();
		List list = pbo.queryPlan(userinfo, bean, _regionid, _workID,createDate);
		logger.info("Regionid=" + _regionid);
		logger.info("workID=" + _workID);
		session.setAttribute("queryresult", list);
		this.setPageReset(request);
		return mapping.findForward("queryPlanResult");
	}

	
	/**
	 * ������/�Զ���ƻ���ʾ
	 * 
	 * @throws Exception
	 */
	public ActionForward searchWPlanShow(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String stype = request.getParameter("searchtype");
		/* ************ simonzhang ��д*********** */
		List lRegion = null;
		List lContractor = null;
		List lgroup = null;

		// ��ѯ��������----ʡ���û�
		String sql1 = "select  r.REGIONNAME, r.REGIONID "
				+ " from  region r   where r.STATE is null and substr(r.REGIONID,3,4) != '1111' ";
		// ��ά
		String sql2 = "select  c.CONTRACTORID, c.CONTRACTORNAME, c.REGIONID "
				+ " from  contractorinfo c " + " where c.STATE is null ";
		// Ѳ����
		String sql3 = "select p.PATROLID, p.PATROLNAME, p.REGIONID, p.PARENTID "
				+ " from patrolmaninfo p " + " where p.STATE is null ";
		try {
			QueryUtil _qu = new QueryUtil();
			// ���ƶ�
			if (userinfo.getType().equals("12")) {
				sql1 += " and r.regionid in ('" + userinfo.getRegionid() + "')";
				sql2 += " and c.regionid IN ('" + userinfo.getRegionID()
						+ "') ";
				sql3 += " and p.regionid IN ('" + userinfo.getRegionID()
						+ "') order by p.PARENTID,p.PATROLNAME ";
				lRegion = _qu.queryBeans(sql1);
				lContractor = _qu.queryBeans(sql2);
				lgroup = _qu.queryBeans(sql3);
			}
			// �д�ά
			if (userinfo.getType().equals("22")) {
				// sql1 += " and r.regionid='"+ userinfo.getRegionid() +"'";
				sql2 += " and c.CONTRACTORID = '" + userinfo.getDeptID() + "'";
				sql3 += " and p.regionid IN ('" + userinfo.getRegionID()
						+ "') and p.PARENTID = '" + userinfo.getDeptID()
						+ "' order by p.PARENTID,p.PATROLNAME ";
				lgroup = _qu.queryBeans(sql3);
			}
			// ʡ�ƶ�
			if (userinfo.getType().equals("11")) {
				sql3 += " order by p.PARENTID,p.PATROLNAME ";
				lRegion = _qu.queryBeans(sql1);
				lContractor = _qu.queryBeans(sql2);
				lgroup = _qu.queryBeans(sql3);
			}
			// ʡ��ά
			if (userinfo.getType().equals("21")) {
				sql2 += " and c.parentcontractorid='" + userinfo.getDeptID()
						+ "'";
				sql3 += " order by p.PARENTID,p.PATROLNAME ";
				lRegion = _qu.queryBeans(sql1);
				lContractor = _qu.queryBeans(sql2);
				lgroup = _qu.queryBeans(sql3);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("sql" + sql3);
		request.setAttribute("lRegion", lRegion);
		request.setAttribute("lContractor", lContractor);
		request.setAttribute("patrolgroup", lgroup);
		String forward = "";
		if (stype != null && stype.equals("custom")) {
			forward = "searchCustomPlanShow";
		} else {
			forward = "searchWeekplanshow";
		}
		logger.info("forward t o " + forward);
		return mapping.findForward(forward);
		
	}

	/**
	 * ȡ��һ���ƻ��ı����ʾ
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
	public ActionForward loadPlanForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		Plan data = super.getService().loadPlan(request.getParameter("id"));
		PlanBean bean = new PlanBean();
		BeanUtil.objectCopy(data, bean);

		BaseInfoService baseinfoservice = new BaseInfoService();
		PatrolMan patrolman = baseinfoservice.loadPatrolMan(bean
				.getExecutorid());

		Vector taskListVct = super.getService().getTasklistByPlanID(
				bean.getId(),"PLAN");
		request.getSession().setAttribute("tasklist", taskListVct);

		bean.setExecutorid(patrolman.getPatrolName());

		if (bean.getIfinnercheck().equals("2")) {
			bean.setIfinnercheck("������");
		} else {
			if (bean.getIfinnercheck().equals("3")) {
				bean.setIfinnercheck("δͨ������");
			} else {
				bean.setIfinnercheck("ͨ������");
				String approvecontent = "";
				String sql = "select approvecontent from planapprovemaster where planid = '"
						+ request.getParameter("id") + "'";
				java.sql.ResultSet rs = null;
				com.cabletech.commons.hb.QueryUtil util = new com.cabletech.commons.hb.QueryUtil();
				rs = util.executeQuery(sql);
				while (rs.next()) {
					approvecontent = rs.getString(1);
				}
				bean.setApprovecontent(approvecontent);

			}
		}

		bean.setApplyformdate(DateUtil.getNowDateString());

		request.getSession().setAttribute("planBean", bean);

		return mapping.findForward("weekplanform");
	}



	/**
	 * �����ܼƻ���Ϣ
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
	public ActionForward ExportWeekPlanform(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {

		PlanBean planbean = (PlanBean) request.getSession().getAttribute(
				"planBean");
		Vector taskVct = (Vector) request.getSession().getAttribute("tasklist");

		super.getService().ExportWeekPlanform(planbean, taskVct, response);

		return null;
	}

	/**
	 * �����üƻ�ִ���߶�ӦѲ����б�
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
	public ActionForward ExportPlanPointsList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {

		String patrolName = request.getParameter("patrolName");
		String patrolId = request.getParameter("patrolId");
		String id = request.getParameter("id");

		Vector pointList = super.getService().newGetPUnitListByPatrolid(id);

		super.getService().ExportPatrolPointsList(patrolName, patrolId,
				pointList, response);

		return null;
	}


	// ��ѯ��ƻ���Ϣ��������Excel��
	public ActionForward exportYearPlanResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {

			List list = (List) request.getSession().getAttribute("queryresult");
			logger.info("�õ�list");
			logger.info("���excel�ɹ�");
			super.getService().ExportYearPlanResult(list, response);
			return null;
		} catch (Exception e) {
			logger.error("�����ƻ���Ϣ���������쳣:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}

	public ActionForward exportMonthPlanResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {

			List list = (List) request.getSession().getAttribute("queryresult");
			logger.info("�õ�list");
			logger.info("���excel�ɹ�");
			super.getService().ExportMonthPlanResult(list, response);
			return null;
		} catch (Exception e) {
			logger.error("�����ƻ���Ϣ���������쳣:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}

	public ActionForward exportWeekPlanResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {

			List list = (List) request.getSession().getAttribute("queryresult");
			logger.info("�ɹ��������");
			super.getService().ExportWeekPlanResult(list, response);
			logger.info("���excel�ɹ�");
			return null;
		} catch (Exception e) {
			logger.error("�����ƻ���Ϣ���������쳣:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}

}
