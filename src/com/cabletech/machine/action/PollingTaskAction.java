package com.cabletech.machine.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.config.MsgInfo;
import com.cabletech.commons.sm.SendSMRMI;
import com.cabletech.machine.beans.MobileTaskBean;
import com.cabletech.machine.domainobjects.Property;
import com.cabletech.machine.services.MobileTaskBO;
import com.cabletech.machine.services.PollingTaskBO;
import com.cabletech.sendtask.dao.SendTaskDao;

/**
 * 巡检任务的 Action
 * @author haozi
 *
 */
public class PollingTaskAction extends BaseDispatchAction {
	private PollingTaskBO bo = new PollingTaskBO();

	/**
	 * 获得一个机房巡检任务下的设备巡检信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showOneListInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String flag = request.getParameter("flag");
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		List oneTaskList = bo.getEquTaskList(id, type, request.getSession());
		request.getSession().setAttribute("oneTaskList", oneTaskList);
		request.setAttribute("type", type);
		request.setAttribute("flag", flag);
		request.setAttribute("tid", id);
		if (Property.CORE_LAYER.equals(type) || Property.SDH_LAYER.equals(type)) {// 根据不同的类型
			// 转到不同的页面
			return mapping.findForward("showOneListForCoreAndSDH");
		} else if (Property.MICRO_LAYER.equals(type)) {
			return mapping.findForward("showOneListForMicro");
		}else if(Property.FIBER_LAYER.equals(type)){
			return mapping.findForward("showOneListForFiber");
		} else {
			return mapping.findForward("showOneListForFSO");
		}
	}

	/**
	 * 返回上一页
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward backToPrePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String sql = (String) request.getSession().getAttribute("oneTasksql");
		String type = request.getParameter("type");
		String tid = request.getParameter("tid");
		List oneTaskList = null;
		if (sql != null && sql.trim().length() != 0) {
			oneTaskList = bo.backToPrePage(sql);
		}
		request.setAttribute("type", type);
		request.setAttribute("tid", tid);
		request.setAttribute("flag", request.getParameter("flag"));
		request.getSession().setAttribute("oneTaskList", oneTaskList);
		if (Property.CORE_LAYER.equals(type) || Property.SDH_LAYER.equals(type)) {// 根据不同的类型
			// 转到不同的页面
			return mapping.findForward("showOneListForCoreAndSDH");
		} else if (Property.MICRO_LAYER.equals(type)) {
			return mapping.findForward("showOneListForMicro");
		} else if (Property.FIBER_LAYER.equals(type)) {
			return mapping.findForward("showOneListForFiber");
		} else {
			return mapping.findForward("showOneListForFSO");
		}
	}

	/**
	 * 显示签收页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showOneForSign(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// String flag = request.getParameter("flag");
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		List oneTaskList = bo.getEquTaskList(id, type, request.getSession());
		request.getSession().setAttribute("oneTaskList", oneTaskList);
		request.setAttribute("type", type);
		request.setAttribute("tid", id);
		// request.setAttribute("flag", flag);
		if (Property.CORE_LAYER.equals(type) || Property.SDH_LAYER.equals(type) || Property.FIBER_LAYER.equals(type)) {// 根据不同的类型
			// 转到不同的页面
			return mapping.findForward("showOneToSignForCoreAndSDH");
		} else if (Property.MICRO_LAYER.equals(type)) {
			return mapping.findForward("showOneToSignForMicro");
		}else {
			return mapping.findForward("showOneToSignForFSO");
		}
	}

	/**
	 * 代维签收一个任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward signATask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
		
		String tid = request.getParameter("tid");
		boolean flag = false;
		MobileTaskBO mobileTaskBO = new MobileTaskBO();
		flag = mobileTaskBO.modTaskState(Property.SIGN_FOR, tid);
		if (flag) {
			return super.forwardInfoPage(mapping, request, "160102ok");
		} else {
			
			MobileTaskBean mobileTaskBean = mobileTaskBO.getOneInfo(tid);
			String msg = Property.MECHINE_MODULE
			+ request.getSession().getAttribute(
					"LOGIN_USER_DEPT_NAME")
			+ mobileTaskBean.getTitle()+" 签收人：" + userinfo.getUserName()+SendSMRMI.MSG_NOTE;
			String sendToSim = new SendTaskDao().getSendPhone(mobileTaskBean.getUserid());
			if(sendToSim != null)
			SendSMRMI.sendNormalMessage(userinfo.getUserID(),
					sendToSim, msg, "00");
			
			return super.forwardErrorPage(mapping, request, "160102error");
		}
	}

	/**
	 * 返回上一页
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward gobackToPrePage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String sql = (String) request.getSession().getAttribute("oneTasksql");
		String type = request.getParameter("type");
		List oneTaskList = null;
		if (sql != null && sql.trim().length() != 0) {
			oneTaskList = bo.backToPrePage(sql);
		}
		request.setAttribute("type", type);
		request.getSession().setAttribute("oneTaskList", oneTaskList);
		if (Property.CORE_LAYER.equals(type) || Property.SDH_LAYER.equals(type)) {// 根据不同的类型
			// 转到不同的页面
			return mapping.findForward("showOneToSignForCoreAndSDH");
		} else if (Property.MICRO_LAYER.equals(type)) {
			return mapping.findForward("showOneToSignForMicro");
		} else {
			return mapping.findForward("showOneToSignForFSO");
		}
	}

	/**
	 * 显示机房设备回复的列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showOneForRestore(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		List oneTaskList = bo.getEquTaskListForRestore(id, type, request
				.getSession());
		request.getSession().setAttribute("oneTaskList", oneTaskList);
		request.getSession().setAttribute("type", type);
		request.getSession().setAttribute("tid", id);
		// request.setAttribute("flag", flag);
		if (Property.CORE_LAYER.equals(type) || Property.SDH_LAYER.equals(type)) {// 根据不同的类型
			// 转到不同的页面
			return mapping.findForward("showOneToRestoreForCoreAndSDH");
		} else if (Property.MICRO_LAYER.equals(type)) {
			return mapping.findForward("showOneToRestoreForMicro");
		}else if (Property.FIBER_LAYER.equals(type)) {
			return mapping.findForward("showOneToRestoreForFiber");
		} else{
			return mapping.findForward("showOneToRestoreForFSO");
		}
	}

	/**
	 * 返回上一页
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward gobackToPrePageForRestore(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String sql = (String) request.getSession().getAttribute("oneTasksql");
		String type = request.getParameter("type");
		String tid = request.getParameter("tid");
		List oneTaskList = null;
		System.out.println(sql);
		if (sql != null && sql.trim().length() != 0) {
			oneTaskList = bo.backToPrePage(sql);
		}
		request.setAttribute("type", type);
		request.setAttribute("tid", tid);
		request.getSession().setAttribute("oneTaskList", oneTaskList);
		if (Property.CORE_LAYER.equals(type) || Property.SDH_LAYER.equals(type)) {// 根据不同的类型
			// 转到不同的页面
			return mapping.findForward("showOneToRestoreForCoreAndSDH");
		} else if (Property.MICRO_LAYER.equals(type)) {
			return mapping.findForward("showOneToRestoreForMicro");
		}else if(Property.FIBER_LAYER.equals(type)){
			return mapping.findForward("showOneToRestoreForFiber");
		} else {
			return mapping.findForward("showOneToRestoreForFSO");
		}
	}

	/**
	 * 根据类型不同 显示不同的核查任务界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showOneForCheck(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String type = request.getParameter("type");
		String tid = request.getParameter("tid");
		List oneTaskList = bo.getEquTaskListForCheck(tid, type, request
				.getSession());
		request.getSession().setAttribute("oneTaskList", oneTaskList);
		request.setAttribute("type", type);
		request.setAttribute("tid", tid);
		if (Property.CORE_LAYER.equals(type) || Property.SDH_LAYER.equals(type)) {// 根据不同的类型
			// 转到不同的页面
			return mapping.findForward("showOneToCheckForCoreAndSDH");
		} else if (Property.MICRO_LAYER.equals(type)) {
			return mapping.findForward("showOneToCheckForMicro");
		} else if (Property.FIBER_LAYER.equals(type)) {
			return mapping.findForward("showOneToCheckForFiber");
		} else {
			return mapping.findForward("showOneToCheckForFSO");
		}
	}
	
	
	/**
	 * 返回上一页
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward backForCheck(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String type = request.getParameter("type");
		String tid = request.getParameter("tid");
		request.setAttribute("type", type);
		request.setAttribute("tid", tid);
		List oneTaskList = null;
		String sql = (String) request.getSession().getAttribute("oneTasksql");
		if (sql != null && sql.trim().length() != 0) {
			oneTaskList = bo.backToPrePage(sql);
		}
		request.getSession().setAttribute("oneTaskList", oneTaskList);
		request.setAttribute("type", type);
		request.setAttribute("tid", tid);
		if (Property.CORE_LAYER.equals(type) || Property.SDH_LAYER.equals(type)) {// 根据不同的类型
			// 转到不同的页面
			return mapping.findForward("showOneToCheckForCoreAndSDH");
		} else if (Property.MICRO_LAYER.equals(type)) {
			return mapping.findForward("showOneToCheckForMicro");
		} else if (Property.FIBER_LAYER.equals(type)) {
			return mapping.findForward("showOneToCheckForFiber");
		}else {
			return mapping.findForward("showOneToCheckForFSO");
		}
	}
	/**
	 * 增加核查内容
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addCheck(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
		
		MsgInfo msg = new MsgInfo();
		String pid = request.getParameter("pid");
		String tid = request.getParameter("tid");
		String type = request.getParameter("type");
		String link = "PollingTaskAction.do?method=backForCheck&tid="+tid+"&type="+type;
		msg.setLink(link);
		String auditResult = request.getParameter("auditResult");
		String checkRemark = request.getParameter("checkRemark");
		boolean bl = bo.addCheck(pid, auditResult, checkRemark);
		if(bl) {
			boolean isEnd = bo.judgeCheckIsEnd(tid);
			if(isEnd) {
				MobileTaskBO mobileTaskBO = new MobileTaskBO();
				bl = mobileTaskBO.modTaskState(Property.CHECK_OVER, tid);
			}
		} else {
			msg.setInfo("添加单个设备核查失败");
			request.setAttribute("MESSAGEINFO", msg);
			return mapping.findForward("errorMsg");
		}
		if(bl) {
			
			
			MobileTaskBean mobileTaskBean = mobileTaskBO.getOneInfo(tid);
			String sendmsg = Property.MECHINE_MODULE
			+ request.getSession().getAttribute(
					"LOGIN_USER_DEPT_NAME")
			+ mobileTaskBean.getTitle()+" 核查人：" + userinfo.getUserName()+SendSMRMI.MSG_NOTE;
			String sendToSim = new SendTaskDao().getSendPhone(mobileTaskBean.getUserid());
			if(sendToSim != null)
			SendSMRMI.sendNormalMessage(userinfo.getUserID(),
					sendToSim, sendmsg, "00");
			
			msg.setInfo("添加单个设备核查成功");
			request.setAttribute("MESSAGEINFO", msg);
			return mapping.findForward("sucessMsg");
		} else {
			msg.setInfo("添加单个设备核查失败");
			request.setAttribute("MESSAGEINFO", msg);
			return mapping.findForward("errorMsg");
		}
	}
	
	/**
	 * 显示一个任务下待核查的机房设备列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showOneListForCheck(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		List oneTaskList = bo.getEquTaskList(id, type, request.getSession());
		request.getSession().setAttribute("oneTaskList", oneTaskList);
		request.setAttribute("type", type);
		request.setAttribute("tid", id);
		if (Property.CORE_LAYER.equals(type) || Property.SDH_LAYER.equals(type)) {// 根据不同的类型
			// 转到不同的页面
			return mapping.findForward("showCoreAndSDHListForCheck");
		} else if (Property.MICRO_LAYER.equals(type)) {
			return mapping.findForward("showMicroListForCheck");
		} else if (Property.FIBER_LAYER.equals(type)) {
			return mapping.findForward("showFiberListForCheck");
		} else {
			return mapping.findForward("showFsoListForCheck");
		}
	}
	
	private MobileTaskBO mobileTaskBO = new MobileTaskBO();
	
	/**
	 * 返回上一页
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward back(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String flag = request.getParameter("flag");
		String type = request.getParameter("type");
		String tid = request.getParameter("tid");
		String sql = (String) request.getSession().getAttribute("oneTasksql");
		List oneTaskList = bo.back(sql);
		request.getSession().setAttribute("oneTaskList", oneTaskList);
		request.setAttribute("type", type);
		request.setAttribute("flag", flag);
		request.setAttribute("tid", tid);
		if (Property.CORE_LAYER.equals(type) || Property.SDH_LAYER.equals(type)) {// 根据不同的类型
			// 转到不同的页面
			return mapping.findForward("showCoreAndSDHListForCheck");
		} else if (Property.MICRO_LAYER.equals(type)) {
			return mapping.findForward("showMicroListForCheck");
		} else if (Property.FIBER_LAYER.equals(type)) {
			return mapping.findForward("showFiberListForCheck");
		}else {
			return mapping.findForward("showFsoListForCheck");
		}
	} 
}
