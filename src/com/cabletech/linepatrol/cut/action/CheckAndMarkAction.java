package com.cabletech.linepatrol.cut.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;
import com.cabletech.linepatrol.cut.services.CheckAndMarkManager;
import com.cabletech.linepatrol.cut.services.CutManager;
import com.cabletech.ctf.exception.ServiceException;

/**
 * 
 * 
 * @author liusq
 * 
 */
public class CheckAndMarkAction extends BaseInfoBaseDispatchAction {

	private static Logger logger = Logger.getLogger(CheckAndMarkAction.class.getName());

	/**
	 * ���CutManager����
	 */
	private CheckAndMarkManager getCheckAndMarkService() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (CheckAndMarkManager) ctx.getBean("checkAndMarkManager");
	}
	
	/**
	 * ��ø�Ӵ������б�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getCheckAndMarkList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		List list = getCheckAndMarkService().getCheckAndMarkList();
		request.getSession().setAttribute("list", list);
		return mapping.findForward("getCheckAndMarkList");
	}
	
	/**
	 * �鿴�����Ϣ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewApply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String cutId = request.getParameter("cutId");
		Map map = getCheckAndMarkService().viewApplyData(cutId);
		request.setAttribute("map", map);
		return mapping.findForward("viewApply");
	}
	
	/**
	 * �������ǰ����������Ϣ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward checkAndMarkFrom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String cutId = request.getParameter("cutId");
		Map map = getCheckAndMarkService().viewApplyData(cutId);
		request.setAttribute("map", map);
		return mapping.findForward("checkAndMarkFrom");
	}
	
	/**
	 * �������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward checkAndMark(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute("LOGIN_USER");
		CutManager cut=(CutManager)ctx.getBean("cutManager");
		String cutId = request.getParameter("cutId");
		
		//����ճ����˶���
		AppraiseDailyBean appraiseDailyBean = (AppraiseDailyBean)request.getSession().getAttribute("appraiseDailyDailyBean");
		List<AppraiseDailyBean> speicalBeans=(List<AppraiseDailyBean>)request.getSession().getAttribute("appraiseDailySpecialBean");
		try{
			getCheckAndMarkService().checkAndMark(userInfo, cutId,appraiseDailyBean,speicalBeans);
			log(request, " ��������  ���������Ϊ��"+cut.get(cutId).getCutName()+"��", " ��ӹ��� ");
			return forwardInfoPage(mapping, request,"checkAndMarkSuccess");
		}catch(ServiceException e){
			e.printStackTrace();
			logger.error("������ֳ���������ϢΪ��" + e.getMessage());
			return forwardErrorPage(mapping, request, "checkAndMarkError");
		}
	}
}
