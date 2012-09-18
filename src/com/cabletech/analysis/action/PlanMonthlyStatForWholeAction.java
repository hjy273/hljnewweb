package com.cabletech.analysis.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.analysis.services.StatForWholeBO;
import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.commons.exception.SubtrahendException;

/**
 * ȫʡѲ��ƻ���ͳ��
 */
public class PlanMonthlyStatForWholeAction extends BaseInfoBaseDispatchAction {
	private Logger logger = Logger.getLogger("PlanMonthlyStatForWholeAction");
	private StatForWholeBO statBo = new StatForWholeBO();
	/**
	 * ��ѯȫʡ����ļƻ�����ִ��������ӱ�ȡ�ò�ѯ���·� 
	 * 1����ȡ�������ƶ���˾�Ŀ��˽�� 
	 * 2�����ȫʡ�ϸ����ͼ����Ϣ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward getWhole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String year = request.getParameter("endYear");
		String month = request.getParameter("endMonth");
		session.setAttribute("year", year);
		session.setAttribute("month", month);
		List whole = statBo.getCollectivityInfo(year, month);
		Map wholepie = statBo.getChartData(year, month);
		if(whole != null && whole.size() != 0){
			session.setAttribute("whole", whole);
			session.setAttribute("wholepie", wholepie);
			logger.info("whole");
			return mapping.findForward("provinceStatframe");
		}else{
			logger.info("s120201fs120201fs120201f");
			return forwardInfoPage( mapping, request, "s120201f" );
		}
	}

	/**
	 * ��ȡ�����е�Ѳ��ƻ�ִ�н����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward getExecuteResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		String year = (String)session.getAttribute("year");
		String month = (String)session.getAttribute("month");
		List exeList = statBo.getExecuteForAllArea(year, month);
		session.setAttribute("exeList", exeList);
		return mapping.findForward("executeplan");
	}

	/**
	 * ��ð��¼��������ĶԱȷ����������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws SubtrahendException 
	 */
	public ActionForward getContrastDataForWhole(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws SubtrahendException {
		HttpSession session = request.getSession();
		String year = (String)session.getAttribute("year");
		String month = (String)session.getAttribute("month");
		List monthList = statBo.getMonthlyContrast(year, month);
		List areaList = statBo.getAreaContrast(year, month);
		session.setAttribute("monthList", monthList);
		session.setAttribute("areaList", areaList);
		return mapping.findForward("contraststat");
	}

	/**
	 * ���Ѳ���߶�Ѳ���������ͼ����,�����ϸ��,δ�ϸ��,δ�ƻ���
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward getSublinePatrolForChart(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		String year = (String)session.getAttribute("year");
		String month = (String)session.getAttribute("month");
		Map map = statBo.getSublinePatrolForChart(year, month);
		session.setAttribute("sublinemap", map);
		return mapping.findForward("sublinestat");
	}
}
