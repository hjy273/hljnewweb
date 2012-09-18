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
 * Ѳ������� Action
 * @author haozi
 *
 */
public class PollingTaskAction extends BaseDispatchAction {
	private PollingTaskBO bo = new PollingTaskBO();

	/**
	 * ���һ������Ѳ�������µ��豸Ѳ����Ϣ
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
		if (Property.CORE_LAYER.equals(type) || Property.SDH_LAYER.equals(type)) {// ���ݲ�ͬ������
			// ת����ͬ��ҳ��
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
	 * ������һҳ
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
		if (Property.CORE_LAYER.equals(type) || Property.SDH_LAYER.equals(type)) {// ���ݲ�ͬ������
			// ת����ͬ��ҳ��
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
	 * ��ʾǩ��ҳ��
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
		if (Property.CORE_LAYER.equals(type) || Property.SDH_LAYER.equals(type) || Property.FIBER_LAYER.equals(type)) {// ���ݲ�ͬ������
			// ת����ͬ��ҳ��
			return mapping.findForward("showOneToSignForCoreAndSDH");
		} else if (Property.MICRO_LAYER.equals(type)) {
			return mapping.findForward("showOneToSignForMicro");
		}else {
			return mapping.findForward("showOneToSignForFSO");
		}
	}

	/**
	 * ��άǩ��һ������
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
			+ mobileTaskBean.getTitle()+" ǩ���ˣ�" + userinfo.getUserName()+SendSMRMI.MSG_NOTE;
			String sendToSim = new SendTaskDao().getSendPhone(mobileTaskBean.getUserid());
			if(sendToSim != null)
			SendSMRMI.sendNormalMessage(userinfo.getUserID(),
					sendToSim, msg, "00");
			
			return super.forwardErrorPage(mapping, request, "160102error");
		}
	}

	/**
	 * ������һҳ
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
		if (Property.CORE_LAYER.equals(type) || Property.SDH_LAYER.equals(type)) {// ���ݲ�ͬ������
			// ת����ͬ��ҳ��
			return mapping.findForward("showOneToSignForCoreAndSDH");
		} else if (Property.MICRO_LAYER.equals(type)) {
			return mapping.findForward("showOneToSignForMicro");
		} else {
			return mapping.findForward("showOneToSignForFSO");
		}
	}

	/**
	 * ��ʾ�����豸�ظ����б�
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
		if (Property.CORE_LAYER.equals(type) || Property.SDH_LAYER.equals(type)) {// ���ݲ�ͬ������
			// ת����ͬ��ҳ��
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
	 * ������һҳ
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
		if (Property.CORE_LAYER.equals(type) || Property.SDH_LAYER.equals(type)) {// ���ݲ�ͬ������
			// ת����ͬ��ҳ��
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
	 * �������Ͳ�ͬ ��ʾ��ͬ�ĺ˲��������
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
		if (Property.CORE_LAYER.equals(type) || Property.SDH_LAYER.equals(type)) {// ���ݲ�ͬ������
			// ת����ͬ��ҳ��
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
	 * ������һҳ
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
		if (Property.CORE_LAYER.equals(type) || Property.SDH_LAYER.equals(type)) {// ���ݲ�ͬ������
			// ת����ͬ��ҳ��
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
	 * ���Ӻ˲�����
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
			msg.setInfo("��ӵ����豸�˲�ʧ��");
			request.setAttribute("MESSAGEINFO", msg);
			return mapping.findForward("errorMsg");
		}
		if(bl) {
			
			
			MobileTaskBean mobileTaskBean = mobileTaskBO.getOneInfo(tid);
			String sendmsg = Property.MECHINE_MODULE
			+ request.getSession().getAttribute(
					"LOGIN_USER_DEPT_NAME")
			+ mobileTaskBean.getTitle()+" �˲��ˣ�" + userinfo.getUserName()+SendSMRMI.MSG_NOTE;
			String sendToSim = new SendTaskDao().getSendPhone(mobileTaskBean.getUserid());
			if(sendToSim != null)
			SendSMRMI.sendNormalMessage(userinfo.getUserID(),
					sendToSim, sendmsg, "00");
			
			msg.setInfo("��ӵ����豸�˲�ɹ�");
			request.setAttribute("MESSAGEINFO", msg);
			return mapping.findForward("sucessMsg");
		} else {
			msg.setInfo("��ӵ����豸�˲�ʧ��");
			request.setAttribute("MESSAGEINFO", msg);
			return mapping.findForward("errorMsg");
		}
	}
	
	/**
	 * ��ʾһ�������´��˲�Ļ����豸�б�
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
		if (Property.CORE_LAYER.equals(type) || Property.SDH_LAYER.equals(type)) {// ���ݲ�ͬ������
			// ת����ͬ��ҳ��
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
	 * ������һҳ
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
		if (Property.CORE_LAYER.equals(type) || Property.SDH_LAYER.equals(type)) {// ���ݲ�ͬ������
			// ת����ͬ��ҳ��
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
