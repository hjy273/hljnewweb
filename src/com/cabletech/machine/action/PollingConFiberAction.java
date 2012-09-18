/***
 *
 * PollingConFiberAction.java
 * @auther <a href="kww@mail.cabletech.com.cn">kww</a>
 * 2009-2-13
 **/

package com.cabletech.machine.action;

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
import com.cabletech.machine.beans.PollingConFiberBean;
import com.cabletech.machine.beans.PollingContentBean;
import com.cabletech.machine.beans.PollingTaskBean;
import com.cabletech.machine.domainobjects.Property;
import com.cabletech.machine.services.MobileTaskBO;
import com.cabletech.machine.services.PollingConFiberBo;
import com.cabletech.machine.services.PollingTaskBO;
import com.cabletech.sendtask.dao.SendTaskDao;

public class PollingConFiberAction extends BaseDispatchAction {

	private PollingConFiberBo bo = new PollingConFiberBo();
	private MobileTaskBO mobileTaskBO = new MobileTaskBO();
	/**
	 * 获取代维填写巡检内容后的全部内容
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getOneForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		PollingConFiberBean bean = bo.getOneForm(id);
		request.setAttribute("bean", bean);
		request.setAttribute("tid", id);
		request.setAttribute("type", type);
		request.setAttribute("flag", request.getParameter("flag"));
		return mapping.findForward("showOneInfo");
	}
	
	/**
	 * 获取核查过后的全部信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getOneAllInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String pid = request.getParameter("pid");
		String tid = request.getParameter("tid");
		MobileTaskBO mobileTaskBO = new MobileTaskBO();
		PollingTaskBO pollingTaskBO = new PollingTaskBO();
		PollingTaskBean pollingTaskBean = pollingTaskBO.getOneTaskInfo(pid);
		MobileTaskBean bean = mobileTaskBO.getOneInfo(tid);
		PollingConFiberBean pollingConFiberBean = bo.getOneForm(pid);
		request.setAttribute("mobileTaskBean", bean);
		request.setAttribute("bean", pollingConFiberBean);
		request.setAttribute("pollingTaskBean", pollingTaskBean);
		request.setAttribute("pid", pid);
		request.setAttribute("tid", tid);
		request.setAttribute("type", request.getParameter("type"));
		return mapping.findForward("showOneAllInfoForFiber");
	}
	
	/**
	 * 增加一个广交维护信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addPollingConFiber(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
		
		MsgInfo msg = new MsgInfo();
		String type = request.getParameter("type");
		String tid = request.getParameter("tid");
		String link = "PollingTaskAction.do?method=gobackToPrePageForRestore&type="
				+ type + "&tid=" + tid;
		
		
		PollingConFiberBean  bean = (PollingConFiberBean)form;
		boolean flag = false;
		boolean isEnd = false;
		String pid = bean.getPid();
		
		flag = bo.addPollingConFiberBean(bean);
		if(flag){
			PollingTaskBO pollingTaskBO = new PollingTaskBO();
			flag = pollingTaskBO.modEquState(pid);
			isEnd = pollingTaskBO.judgeIsEnd(tid);
			if (isEnd) {
				MobileTaskBO mobileTaskBO = new MobileTaskBO();
				flag = mobileTaskBO.modTaskState(Property.END_TASK, tid);
			}
		}else {
			msg.setInfo("添加单个巡检设备回复内容失败");
			msg.setLink(link);
			request.setAttribute("MESSAGEINFO", msg);
			return mapping.findForward("errorMsg");
		}
		
		if (flag) {
			
			MobileTaskBean mobileTaskBean = mobileTaskBO.getOneInfo(tid);
			String sendmsg = Property.MECHINE_MODULE
			+ request.getSession().getAttribute(
					"LOGIN_USER_DEPT_NAME")
			+ mobileTaskBean.getTitle()+" 签收人：" + userinfo.getUserName()+SendSMRMI.MSG_NOTE;
			String sendToSim = new SendTaskDao().getSendPhone(mobileTaskBean.getUserid());
			if(sendToSim != null)
			SendSMRMI.sendNormalMessage(userinfo.getUserID(),
					sendToSim, sendmsg, "00");
			
			msg.setInfo("添加单个巡检设备回复内容成功");
			msg.setLink(link);
			request.setAttribute("MESSAGEINFO", msg);
			return mapping.findForward("sucessMsg");
		} else {
			msg.setInfo("添加单个巡检设备回复内容失败");
			msg.setLink(link);
			request.setAttribute("MESSAGEINFO", msg);
			return mapping.findForward("errorMsg");
		}
	}
	
	/**
	 * 显示微波层巡检内容 添加界面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward restoreAEqu(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String pid = request.getParameter("pid");
		String tid = request.getParameter("tid");
		MobileTaskBO mobileTaskBO = new MobileTaskBO();
		MobileTaskBean bean = mobileTaskBO.getOneInfo(tid);
		request.setAttribute("mobileTaskBean", bean);
		request.setAttribute("pid", pid);
		request.setAttribute("tid", tid);
		request.setAttribute("type", request.getParameter("type"));
		return mapping.findForward("addContentForFiber");
	}
	
	/**
	 * 显示光交维护界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward checkAEqu(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String pid = request.getParameter("pid");
		String tid = request.getParameter("tid");
		MobileTaskBO mobileTaskBO = new MobileTaskBO();
		MobileTaskBean bean = mobileTaskBO.getOneInfo(tid);
		PollingConFiberBean pollingConFiberBean = bo.getOneForm(pid);
		request.setAttribute("mobileTaskBean", bean);
		request.setAttribute("bean", pollingConFiberBean);
		request.setAttribute("pid", pid);
		request.setAttribute("tid", tid);
		request.setAttribute("type", request.getParameter("type"));
		return mapping.findForward("addCheckForFiber");
	}
}
