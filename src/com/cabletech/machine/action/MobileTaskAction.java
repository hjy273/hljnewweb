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
 * 移动制定任务的action，以及显示待签收的 待回复的 待核查的任务
 * @author haozi
 *
 */
public class MobileTaskAction extends BaseDispatchAction {
	private MobileTaskBO bo = new MobileTaskBO();
	Logger logger = Logger.getLogger( this.getClass().getName() );

	/**
	 * 显示增加任务的界面
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
	 * 增加任务的第一步
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
				|| Property.SDH_LAYER.equals(machineType)) {// 若干是核心层 或者是接入层SDH
			flag = Property.FLAG_FOR_CORE_AND_SDH;
			request.setAttribute("flag", flag);
		} else if (Property.MICRO_LAYER.equals(machineType)) {// 如果是接入层微波
			flag = Property.FLAG_FOR_MICRO;
			request.setAttribute("flag", flag);
		} else if (Property.FSO_LAYER.equals(machineType)) { // 如果是接入层FSO
			flag = Property.FLAG_FOR_FSO;
			request.setAttribute("flag", flag);
		}else if (Property.FIBER_LAYER.equals(machineType)) { // 如果是光交维护
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
	 * 增加机房设备巡检计划(接入层SDH和核心层的任务) 增加任务的第二步
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
			+ mobileTaskBean.getTitle()+" 制定人：" + userinfo.getUserName()+SendSMRMI.MSG_NOTE;
			String sendToSim = new SendTaskDao().getSendPhone(mobileTaskBean.getUserid());
			if(sendToSim != null)
			SendSMRMI.sendNormalMessage(userinfo.getUserID(),
					sendToSim, msg, "00");
			logger.info(msg);
			return super.forwardInfoPage(mapping, request, "160101ok");
		}
	}

	/**
	 * 增加机房设备巡检计划(接入微波层的任务) 增加任务的第二步
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
			+ mobileTaskBean.getTitle()+" 制定人：" + userinfo.getUserName()+SendSMRMI.MSG_NOTE;
			String sendToSim = new SendTaskDao().getSendPhone(mobileTaskBean.getUserid());
			if(sendToSim != null)
			SendSMRMI.sendNormalMessage(userinfo.getUserID(),
					sendToSim, msg, "00");
			logger.info(msg);
			return super.forwardInfoPage(mapping, request, "160101ok");
		}
	}

	/**
	 * 增加接入层FSO的任务
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
			+ mobileTaskBean.getTitle()+" 制定人：" + userinfo.getUserName()+SendSMRMI.MSG_NOTE;
			String sendToSim = new SendTaskDao().getSendPhone(mobileTaskBean.getUserid());
			if(sendToSim != null)
			SendSMRMI.sendNormalMessage(userinfo.getUserID(),
					sendToSim, msg, "00");
			logger.info(msg);
			return super.forwardInfoPage(mapping, request, "160101ok");
		}
	}

	/**
	 * 显示机房巡检任务查询页面
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
	 * 执行查询
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
		if ("1".equals(reset_query)) {//如果是查询 不是返回
			if("1".equals(request.getParameter("query"))) {//如果是移动指派任务中的查询 或者是代维签收任务中的查询
				if (userinfo.getDeptype().equals("1")) {// 如果是移动用户
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
				} else {// 代维用户
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
			} else {//如果是回复任务中的查询
				if (userinfo.getDeptype().equals("1")) {// 如果是移动用户
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
				} else {// 代维用户
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
	 * 显示代维的
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
	 * 显示代维的任务查询界面
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
		request.setAttribute("mobileList", mobileList);// 核查人
		request.setAttribute("executeList", executeList);// 执行人
		return mapping.findForward("showTaskQueryForCon");
	}

	/**
	 * 显示登陆用户所在代维公司待签收的任务
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
		if("1".equals(userinfo.getDeptype())) {//如果是移动用户
			return null;
		}
		List taskList = bo.getAllTaskForSign(userinfo);
		request.getSession().setAttribute("taskList", taskList);
		super.setPageReset(request);
		return mapping.findForward("getTaskForCon");
	}

	/**
	 * 显示所有待回复的
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
		if("1".equals(userinfo.getDeptype())) {//如果是移动用户
			return null;
		}
		List taskList = bo.showTaskForRestore(userinfo);
		request.getSession().setAttribute("taskList", taskList);
		super.setPageReset(request);
		return mapping.findForward("showTaskListForRestore");
	}

	/**
	 * 显示所有待核查的任务列表
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
		if(userinfo.getDeptype().equals("2")) {//如果是代维用户
			return null;
		}
		List taskList = bo.getTastForCheck();
		request.getSession().setAttribute("taskList", taskList);
		super.setPageReset(request);
		return mapping.findForward("showTaskListForCheck");
	}
	
	/**
	 * 根据代维的ID查找该代维下的用户
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
	 * 显示核查的查界面
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
		if("1".equals(userinfo.getDeptype())) {//如果是移动用户
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
		
		// add by guixy 2009-1-13 为了保存查询条件
		HashMap beanMap = new HashMap();
//		 add by guixy 2009-1-13 为了保存查询条件
		
		if ("1".equals(reset_query)) {// 如果是移动用户
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
			} else {// 代维用户
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
	 * 组装信息
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
     * 导出查看计划执行进度列表
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
            	// 得到查询条件Map
            	HashMap queryMap = (HashMap)request.getSession().getAttribute("querymap"); 

                logger.info( "得到list" );
                excelBo.exportCheckedTaskinfo(queryMap, response);
                logger.info( "输出excel成功" );
                return null;
            }
            catch( Exception e ){
                logger.error( "导出计划信息结果表出现异常:" + e.getMessage() );
                return forwardErrorPage( mapping, request, "error" );
            }
    }

}
