package com.cabletech.partmanage.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.ExportBO;
import com.cabletech.partmanage.dao.MonthStateDao;
import com.cabletech.statistics.dao.StatDao;


/**
 * ������ͳ��
 * @author cable
 *
 */
public class MonthStateAction extends BaseInfoBaseDispatchAction{
	MonthStateDao dao = new MonthStateDao();
	Logger logger = Logger.getLogger(this.getClass().getName());
	/**
	 * ת����ͳ��ҳ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward MonthStateForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String deptype = user.getDeptype();
		if(deptype.equals("1")){//�ƶ�
			List cons = dao.getConsByDeptId(user);
			request.setAttribute("cons",cons);
		}
		return mapping.findForward("monthstatform");
	}
	/**
	 * ת����ͳ���б�ҳ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward statMonth(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String Month = request.getParameter("Month");
		String contractorid = request.getParameter("contractorid");
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String deptype = user.getDeptype();
		if(deptype.equals("2")){
			contractorid = user.getDeptID();
		}
		List baseInfo = dao.getBaseInfo(Month, contractorid);
		List Intendance = dao.getIntendance(Month, contractorid);
		List RegionPrincipal = dao.getRegionPrincipal(Month, contractorid);
		List MaterialName = dao.getMaterialName(Month, contractorid);
		List MaterialCount = dao.getMaterialInfo(Month, contractorid);
		request.getSession().setAttribute("baseInfo", baseInfo);
		request.getSession().setAttribute("Intendance", Intendance);
		request.getSession().setAttribute("RegionPrincipal", RegionPrincipal);
		request.getSession().setAttribute("MaterialName", MaterialName);
		request.getSession().setAttribute("MaterialCount", MaterialCount);
		return mapping.findForward("monthstatlist");
	}
	/**
	 * ����������Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportMaterialMonthStat(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ExportBO export = new ExportBO();
		try {
			logger.info(" ����dao");
			List baseInfo = (List) request.getSession().getAttribute("baseInfo");
			List Intendance = (List) request.getSession().getAttribute("Intendance");
			List RegionPrincipal = (List) request.getSession().getAttribute("RegionPrincipal");
			List MaterialName = (List) request.getSession().getAttribute("MaterialName");
			List MaterialCount = (List) request.getSession().getAttribute("MaterialCount");
			logger.info("�õ�list");
			export.exportMaterialMonthStat( baseInfo,Intendance,RegionPrincipal,MaterialName,MaterialCount, response);
			logger.info("���excel�ɹ�");
			return null;
		} catch (Exception e) {
			logger.error("������Ϣ��������쳣:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}
}
