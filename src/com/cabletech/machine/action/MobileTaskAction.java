package com.cabletech.machine.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.sm.SendSMRMI;
import com.cabletech.linecut.services.LineCutReService;
import com.cabletech.machine.beans.MobileTaskBean;
import com.cabletech.machine.beans.PollingTaskBean;
import com.cabletech.machine.domainobjects.Property;
import com.cabletech.machine.services.MobileExcelBo;
import com.cabletech.machine.services.MobileTaskBO;
import com.cabletech.machine.services.PollingTaskBO;
import com.cabletech.sendtask.dao.SendTaskDao;

/**
 * �ƶ��ƶ������action���Լ���ʾ��ǩ�յ� ���ظ��� ���˲������
 * @author haozi
 *
 */
public class MobileTaskAction extends BaseDispatchAction {
	private MobileTaskBO bo = new MobileTaskBO();
	Logger logger = Logger.getLogger( this.getClass().getName() );

	/**
	 * ��ʾ��������Ľ���
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showAddTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LineCutReService service = new LineCutReService();
		List conDeptList = service.getAllCon();
		List mobileList = bo.getMobileUser();
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		request.setAttribute("makePeopleName", userinfo.getUserName());
		request.setAttribute("makePeopleId", userinfo.getUserID());
		request.setAttribute("mobileList", mobileList);
		request.setAttribute("conDeptList", conDeptList);
		//super.setPageReset(request);
		return mapping.findForward("addTaskStepOne");
	}

	/**
	 * ��������ĵ�һ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addTaskStepOne(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String flag = "";
		MobileTaskBean bean = (MobileTaskBean) form;
		List equList = null;
		String machineType = bean.getMachinetype();
		System.out.println(machineType);
		if (Property.CORE_LAYER.equals(machineType)
				|| Property.SDH_LAYER.equals(machineType)) {// �����Ǻ��Ĳ� �����ǽ����SDH
			flag = Property.FLAG_FOR_CORE_AND_SDH;
			request.setAttribute("flag", flag);
		} else if (Property.MICRO_LAYER.equals(machineType)) {// ����ǽ����΢��
			flag = Property.FLAG_FOR_MICRO;
			request.setAttribute("flag", flag);
		} else if (Property.FSO_LAYER.equals(machineType)) { // ����ǽ����FSO
			flag = Property.FLAG_FOR_FSO;
			request.setAttribute("flag", flag);
		}else if (Property.FIBER_LAYER.equals(machineType)) { // ����ǹ⽻ά��
			flag = Property.FLAG_FOR_FIBER;
			request.setAttribute("flag", flag);
		}
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		request.setAttribute("makePeopleName", userinfo.getUserName());
		request.setAttribute("taskbean", bean);
		equList = bo.getEqu(bean.getMachinetype(), bean.getContractorid());
		request.setAttribute("equList", equList);
		return mapping.findForward("addTaskStepTwo");
	}

	/**
	 * ���ӻ����豸Ѳ��ƻ�(�����SDH�ͺ��Ĳ������) ��������ĵڶ���
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addTaskStepForSDHAndCore(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
		
		MobileTaskBean mobileTaskBean = (MobileTaskBean)form;
		PollingTaskBO taskBO = new PollingTaskBO();
		MobileTaskBean bean = this.popubean(request);
		boolean flag = true;
		String tid = bo.addMobileTask(bean);
		if (tid == null) {
			flag = false;
		}
		String[] subCheck = request.getParameterValues("ifCheck");
		PollingTaskBean taskBean = null;
		
		List list = new ArrayList();
		for (int i = 0; i < subCheck.length; i++) {
			taskBean = new PollingTaskBean();
			taskBean.setEid(request.getParameter("selEqu_" + subCheck[i]));
			taskBean.setTid(tid);
			taskBean.setLayer(bean.getMachinetype());
//			flag = taskBO.addPollingTaskForCoreAndSDH(taskBean);
			list.add(taskBean);
		}
		
		flag = taskBO.addPollingTaskForCoreAndSDH(list);
		
		if (flag == false) {
			return super.forwardErrorPage(mapping, request, "160101error");
		} else {
			
			String msg = Property.MECHINE_MODULE
			+ request.getSession().getAttribute(
					"LOGIN_USER_DEPT_NAME")
			+ mobileTaskBean.getTitle()+" �ƶ��ˣ�" + userinfo.getUserName()+SendSMRMI.MSG_NOTE;
			String sendToSim = new SendTaskDao().getSendPhone(mobileTaskBean.getUserid());
			if(sendToSim != null)
			SendSMRMI.sendNormalMessage(userinfo.getUserID(),
					sendToSim, msg, "00");
			logger.info(msg);
			return super.forwardInfoPage(mapping, request, "160101ok");
		}
	}

	/**
	 * ���ӻ����豸Ѳ��ƻ�(����΢���������) ��������ĵڶ���
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addTaskStepForMicro(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		MobileTaskBean mobileTaskBean = (MobileTaskBean)form;
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
		
		PollingTaskBO taskBO = new PollingTaskBO();
		MobileTaskBean bean = this.popubean(request);
		boolean flag = true;
		String tid = bo.addMobileTask(bean);
		if (tid == null) {
			flag = false;
		}
		String[] subCheck = request.getParameterValues("ifCheck");
		PollingTaskBean taskBean = null;
		List list = new ArrayList();
		for (int i = 0; i < subCheck.length; i++) {
			taskBean = new PollingTaskBean();
			taskBean.setTid(tid);
			taskBean.setAeid(request.getParameter("selEquA_" + subCheck[i]));
			taskBean.setBeid(request.getParameter("selEquB_" + subCheck[i]));
			taskBean.setLayer(bean.getMachinetype());
			list.add(taskBean);
			//flag = taskBO.addPollingTaskForMicro(taskBean);
		}
		flag = taskBO.addPollingTaskForMicro(list);
		if (flag == false) {
			return super.forwardErrorPage(mapping, request, "160101error");
		} else {
			
			String msg = Property.MECHINE_MODULE
			+ request.getSession().getAttribute(
					"LOGIN_USER_DEPT_NAME")
			+ mobileTaskBean.getTitle()+" �ƶ��ˣ�" + userinfo.getUserName()+SendSMRMI.MSG_NOTE;
			String sendToSim = new SendTaskDao().getSendPhone(mobileTaskBean.getUserid());
			if(sendToSim != null)
			SendSMRMI.sendNormalMessage(userinfo.getUserID(),
					sendToSim, msg, "00");
			logger.info(msg);
			return super.forwardInfoPage(mapping, request, "160101ok");
		}
	}

	/**
	 * ���ӽ����FSO������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addPollingTaskForFSO(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		MobileTaskBean mobileTaskBean = (MobileTaskBean)form;
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
		
		PollingTaskBO taskBO = new PollingTaskBO();
		MobileTaskBean bean = this.popubean(request);
		boolean flag = true;
		String tid = bo.addMobileTask(bean);
		if (tid == null) {
			flag = false;
		}
		String[] subCheck = request.getParameterValues("ifCheck");
		
		List list = new ArrayList();
		
		PollingTaskBean taskBean = null;
		for (int i = 0; i < subCheck.length; i++) {
			taskBean = new PollingTaskBean();
			taskBean.setTid(tid);
			taskBean.setAeid(request.getParameter("selEquA_" + subCheck[i]));
			taskBean.setBeid(request.getParameter("selEquB_" + subCheck[i]));
			taskBean.setEquipmentModel(request.getParameter("model_"
					+ subCheck[i]));
			taskBean.setMachineNo(request.getParameter("no_" + subCheck[i]));
			taskBean.setPowerType(request.getParameter("power_" + subCheck[i]));
			taskBean.setLayer(bean.getMachinetype());
			list.add(taskBean);
		}
		flag = taskBO.addPollingTaskForFSO(list);
		if (flag == false) {
			return super.forwardErrorPage(mapping, request, "160101error");
		} else {
			String msg = Property.MECHINE_MODULE
			+ request.getSession().getAttribute(
					"LOGIN_USER_DEPT_NAME")
			+ mobileTaskBean.getTitle()+" �ƶ��ˣ�" + userinfo.getUserName()+SendSMRMI.MSG_NOTE;
			String sendToSim = new SendTaskDao().getSendPhone(mobileTaskBean.getUserid());
			if(sendToSim != null)
			SendSMRMI.sendNormalMessage(userinfo.getUserID(),
					sendToSim, msg, "00");
			logger.info(msg);
			return super.forwardInfoPage(mapping, request, "160101ok");
		}
	}

	/**
	 * ��ʾ����Ѳ�������ѯҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showQueryForTask(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LineCutReService service = new LineCutReService();
		List conDeptList = service.getAllCon();
		List mobileList = bo.getMobileUser();
		request.setAttribute("mobileList", mobileList);
		request.setAttribute("conDeptList", conDeptList);
		super.setPageReset(request);
		return mapping.findForward("showQueryForTask");
	}

	/**
	 * ִ�в�ѯ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String condition = "";
		List queryList = null;
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String reset_query = request.getParameter("reset_query");
		if ("1".equals(reset_query)) {//����ǲ�ѯ ���Ƿ���
			if("1".equals(request.getParameter("query"))) {//������ƶ�ָ�������еĲ�ѯ �����Ǵ�άǩ�������еĲ�ѯ
				if (userinfo.getDeptype().equals("1")) {// ������ƶ��û�
					String type = request.getParameter("machinetype");
					String contractorid = request.getParameter("contractorid");
					String userid = request.getParameter("userid");
					String checkuser = request.getParameter("checkuser");
					String begtime = request.getParameter("begtime");
					String endtime = request.getParameter("endtime");
					String state = request.getParameter("state");
					if (!"0".equals(type)) {
						condition += " and mt.machinetype='" + type + "'";
					}
					if (!"0".equals(contractorid)) {
						condition += " and mt.contractorid='" + contractorid + "'";
					}
					if (!"0".equals(userid)) {
						condition += " and mt.userid='" + userid + "'";
					}
					if (!"0".equals(checkuser)) {
						condition += " and mt.checkuser='" + checkuser + "'";
					}
					if (!"0".equals(state)) {
						condition += " and mt.state='" + state + "'";
					} else {
						condition += " and (mt.state='1' or mt.state='2')";
					}
					if (begtime != null && !begtime.equals("")) {
						condition += " and mt.executetime >= TO_DATE('" + begtime
								+ "','YYYY-MM-DD')";
					}
					if (endtime != null && !endtime.equals("")) {
						condition += " and mt.executetime <= to_date('" + endtime
								+ " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
					}
				} else {// ��ά�û�
					String type = request.getParameter("machinetype");
					String userid = request.getParameter("userid");
					String state = request.getParameter("state");
					String checkuser = request.getParameter("checkuser");
					String begtime = request.getParameter("begtime");
					String endtime = request.getParameter("endtime");
					if (!"0".equals(type)) {
						condition += " and mt.machinetype='" + type + "'";
					}
					if (!"0".equals(userid)) {
						condition += " and mt.userid='" + userid + "'";
					}
					if (!"0".equals(checkuser)) {
						condition += " and mt.checkuser='" + checkuser + "'";
					}
					if (!"0".equals(state)) {
						condition += " and mt.state='" + state + "'";
					} else {
						condition += " and (mt.state='1' or mt.state='2')";
					}
					if (begtime != null && !begtime.equals("")) {
						condition += " and mt.executetime >= TO_DATE('" + begtime
								+ "','YYYY-MM-DD')";
					}
					if (endtime != null && !endtime.equals("")) {
						condition += " and mt.executetime <= to_date('" + endtime
								+ " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
					}
				}
			} else {//����ǻظ������еĲ�ѯ
				if (userinfo.getDeptype().equals("1")) {// ������ƶ��û�
					String type = request.getParameter("machinetype");
					String contractorid = request.getParameter("contractorid");
					String userid = request.getParameter("userid");
					String checkuser = request.getParameter("checkuser");
					String begtime = request.getParameter("begtime");
					String endtime = request.getParameter("endtime");
					String state = request.getParameter("state");
					if (!"0".equals(type)) {
						condition += " and mt.machinetype='" + type + "'";
					}
					if (!"0".equals(contractorid)) {
						condition += " and mt.contractorid='" + contractorid + "'";
					}
					if (!"0".equals(userid)) {
						condition += " and mt.userid='" + userid + "'";
					}
					if (!"0".equals(checkuser)) {
						condition += " and mt.checkuser='" + checkuser + "'";
					}
					condition += " and mt.state='3' ";
					if (begtime != null && !begtime.equals("")) {
						condition += " and mt.maketime >= TO_DATE('" + begtime
								+ "','YYYY-MM-DD')";
					}
					if (endtime != null && !endtime.equals("")) {
						condition += " and mt.maketime <= to_date('" + endtime
								+ " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
					}
				} else {// ��ά�û�
					String type = request.getParameter("machinetype");
					String userid = request.getParameter("userid");
					String state = request.getParameter("state");
					String checkuser = request.getParameter("checkuser");
					String begtime = request.getParameter("begtime");
					String endtime = request.getParameter("endtime");
					if (!"0".equals(type)) {
						condition += " and mt.machinetype='" + type + "'";
					}
					if (!"0".equals(userid)) {
						condition += " and mt.userid='" + userid + "'";
					}
					if (!"0".equals(checkuser)) {
						condition += " and mt.checkuser='" + checkuser + "'";
					}
					condition += " and mt.state='3' ";
					if (begtime != null && !begtime.equals("")) {
						condition += " and mt.executetime >= TO_DATE('" + begtime
								+ "','YYYY-MM-DD')";
					}
					if (endtime != null && !endtime.equals("")) {
						condition += " and mt.executetime <= to_date('" + endtime
								+ " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
					}
				}
			}
			queryList = bo.doQuery(condition, userinfo);
			request.getSession().setAttribute("sqlCon", condition);
		} else {
			condition = (String) request.getSession().getAttribute("sqlCon");
			queryList = bo.doQuery(condition, userinfo);
		}
		request.getSession().setAttribute("queryList", queryList);
		super.setPageReset(request);
		return mapping.findForward("showQueryList");
	}

	/**
	 * ��ʾ��ά��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showQueryForRestore(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		LineCutReService service = new LineCutReService();
		List conDeptList = service.getAllCon();
		List mobileList = bo.getMobileUser();
		request.setAttribute("mobileList", mobileList);
		request.setAttribute("conDeptList", conDeptList);
		return mapping.findForward("showQueryForRestore");
	}

	/**
	 * ��ʾ��ά�������ѯ����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showTaskQueryForCon(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List executeList = bo.getUserByConId(userinfo.getDeptID());
		List mobileList = bo.getMobileUser();
		request.setAttribute("mobileList", mobileList);// �˲���
		request.setAttribute("executeList", executeList);// ִ����
		return mapping.findForward("showTaskQueryForCon");
	}

	/**
	 * ��ʾ��½�û����ڴ�ά��˾��ǩ�յ�����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getTaskForCon(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		if("1".equals(userinfo.getDeptype())) {//������ƶ��û�
			return null;
		}
		List taskList = bo.getAllTaskForSign(userinfo);
		request.getSession().setAttribute("taskList", taskList);
		super.setPageReset(request);
		return mapping.findForward("getTaskForCon");
	}

	/**
	 * ��ʾ���д��ظ���
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showTaskForRestore(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		if("1".equals(userinfo.getDeptype())) {//������ƶ��û�
			return null;
		}
		List taskList = bo.showTaskForRestore(userinfo);
		request.getSession().setAttribute("taskList", taskList);
		super.setPageReset(request);
		return mapping.findForward("showTaskListForRestore");
	}

	/**
	 * ��ʾ���д��˲�������б�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showTaskForCheck(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
		if(userinfo.getDeptype().equals("2")) {//����Ǵ�ά�û�
			return null;
		}
		List taskList = bo.getTastForCheck();
		request.getSession().setAttribute("taskList", taskList);
		super.setPageReset(request);
		return mapping.findForward("showTaskListForCheck");
	}
	
	/**
	 * ���ݴ�ά��ID���Ҹô�ά�µ��û�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getUserByConId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json; charset=GBK");
		String conid = request.getParameter("conId");
		List userList = bo.getUserByConId(conid);
		JSONArray ja = JSONArray.fromObject(userList);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(ja.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
		System.out.println(ja.toString());
		return null;
	}
	
	/**
	 * ��ʾ�˲�Ĳ����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showQueryForCheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
		if("1".equals(userinfo.getDeptype())) {//������ƶ��û�
			LineCutReService service = new LineCutReService();
			List conDeptList = service.getAllCon();
			List mobileList = bo.getMobileUser();
			request.setAttribute("mobileList", mobileList);
			request.setAttribute("conDeptList", conDeptList);
		} else {
			List userList = bo.getUserByConId(userinfo.getDeptID());
			List mobileList = bo.getMobileUser();
			request.setAttribute("mobileList", mobileList);
			request.setAttribute("userList", userList);
		}
		request.setAttribute("depType", userinfo.getDeptype());
		return mapping.findForward("showQueryForCheck");
	}
	
	public ActionForward doQueryForCheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String condition = "";
		List queryList = null;
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String reset_query = request.getParameter("reset_query");
		
		// add by guixy 2009-1-13 Ϊ�˱����ѯ����
		HashMap beanMap = new HashMap();
//		 add by guixy 2009-1-13 Ϊ�˱����ѯ����
		
		if ("1".equals(reset_query)) {// ������ƶ��û�
			if (userinfo.getDeptype().equals("1")) {
				String type = request.getParameter("machinetype");
				String contractorid = request.getParameter("contractorid");
				String userid = request.getParameter("userid");
				String checkuser = request.getParameter("checkuser");
				String begtime = request.getParameter("begtime");
				String endtime = request.getParameter("endtime");
				String state = request.getParameter("state");
				beanMap.put("type", type);
				beanMap.put("contractorid", contractorid);
				beanMap.put("userid", userid);
				beanMap.put("checkuser", checkuser);
				beanMap.put("begtime", begtime);
				beanMap.put("endtime", endtime);
				beanMap.put("state", state);
				
				if (!"0".equals(type)) {
					condition += " and mt.machinetype='" + type + "'";
				}
				if (!"0".equals(contractorid)) {
					condition += " and mt.contractorid='" + contractorid + "'";
				}
				if (!"0".equals(userid)) {
					condition += " and mt.userid='" + userid + "'";
				}
				if (!"0".equals(checkuser)) {
					condition += " and mt.checkuser='" + checkuser + "'";
				}
				if (!"0".equals(state)) {
					condition += " and mt.state='" + state + "'";
				}
				if (begtime != null && !begtime.equals("")) {
					condition += " and mt.maketime >= TO_DATE('" + begtime
							+ "','YYYY-MM-DD')";
				}
				if (endtime != null && !endtime.equals("")) {
					condition += " and mt.maketime <= to_date('" + endtime
							+ " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
				}
			} else {// ��ά�û�
				String type = request.getParameter("machinetype");
				String userid = request.getParameter("userid");
				String state = request.getParameter("state");
				String checkuser = request.getParameter("checkuser");
				String begtime = request.getParameter("begtime");
				String endtime = request.getParameter("endtime");
				beanMap.put("type", type);
				beanMap.put("userid", userid);
				beanMap.put("checkuser", checkuser);
				beanMap.put("begtime", begtime);
				beanMap.put("endtime", endtime);
				beanMap.put("state", state);
				
				if (!"0".equals(type)) {
					condition += " and mt.machinetype='" + type + "'";
				}
				if (!"0".equals(userid)) {
					condition += " and mt.userid='" + userid + "'";
				}
				if (!"0".equals(checkuser)) {
					condition += " and mt.checkuser='" + checkuser + "'";
				}
				if (!"0".equals(state)) {
					condition += " and mt.state='" + state + "'";
				}
				if (begtime != null && !begtime.equals("")) {
					condition += " and mt.maketime >= TO_DATE('" + begtime
							+ "','YYYY-MM-DD')";
				}
				if (endtime != null && !endtime.equals("")) {
					condition += " and mt.maketime <= to_date('" + endtime
							+ " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
				}
			}
			queryList = bo.doQuery(condition, userinfo);
			request.getSession().setAttribute("sqlCon", condition);
			request.getSession().setAttribute("querymap", beanMap);
		} else {
			condition = (String) request.getSession().getAttribute("sqlCon");
			queryList = bo.doQuery(condition, userinfo);
		}
		request.getSession().setAttribute("queryList", queryList);
		super.setPageReset(request);
		return mapping.findForward("showQueryListForCheck");
		
	}
	
	/**
	 * ��װ��Ϣ
	 * 
	 * @param request
	 * @return
	 */
	private MobileTaskBean popubean(HttpServletRequest request) {
		String machinetype = request.getParameter("machinetype");
		String title = request.getParameter("title");
		String contractorid = request.getParameter("contractorid");
		String userid = request.getParameter("userid");
		String executetime = request.getParameter("executetime").replaceAll(
				"/", "-");
		String checkuser = request.getParameter("checkuser");
		String makepeopleid = request.getParameter("makepeopleid");
		String remark = request.getParameter("remark");
		MobileTaskBean bean = new MobileTaskBean();
		bean.setMachinetype(machinetype);
		bean.setTitle(title);
		bean.setContractorid(contractorid);
		bean.setUserid(userid);
		bean.setExecutetime(executetime);
		bean.setCheckuser(checkuser);
		bean.setMakepeopleid(makepeopleid);
		bean.setRemark(remark);
		bean.setState(Property.WAIT_SIGN_FOR);
		bean.setMaketime(new Date());
		return bean;
	}
	
    /**
     * �����鿴�ƻ�ִ�н����б�
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward exportCheckedTaskInfo( ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response ){
            try{
            	MobileExcelBo excelBo = new MobileExcelBo();
            	// �õ���ѯ����Map
            	HashMap queryMap = (HashMap)request.getSession().getAttribute("querymap"); 

                logger.info( "�õ�list" );
                excelBo.exportCheckedTaskinfo(queryMap, response);
                logger.info( "���excel�ɹ�" );
                return null;
            }
            catch( Exception e ){
                logger.error( "�����ƻ���Ϣ���������쳣:" + e.getMessage() );
                return forwardErrorPage( mapping, request, "error" );
            }
    }

}
