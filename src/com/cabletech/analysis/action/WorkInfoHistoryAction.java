package com.cabletech.analysis.action;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cabletech.analysis.beans.HistoryTimeInfoBean;
import com.cabletech.analysis.beans.HistoryDateInfoBean;
import com.cabletech.analysis.beans.HistoryWorkInfoConditionBean;
import com.cabletech.analysis.beans.HistoryWorkInfoCreateBOParam;
import com.cabletech.analysis.services.WorkInfoHistoryContext;
import com.cabletech.analysis.services.WorkInfoHistoryContextCurve;
import com.cabletech.analysis.services.WorkInfoHistoryContextSM;
import com.cabletech.analysis.util.WorkInfoHistoryCommon;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.config.UserType;
import com.cabletech.power.CheckPower;

/**
 * ��ʷѲ����̷�����Action��
 */
public class WorkInfoHistoryAction extends BaseDispatchAction {
	private Logger logger =Logger.getLogger(this.getClass().getName()); // ����logger�������;
	private WorkInfoHistoryCommon workInfoCommon = new WorkInfoHistoryCommon();
	private HistoryWorkInfoCreateBOParam boParam = null;
    private WorkInfoHistoryContext context  = null;
	/**
	 * Ѳ�������ʷ��Ϣ��ѯҳ��
	 * 
	 * @param mapping
	 *            mapping
	 * @param form
	 *            form
	 * @param request
	 *            ����
	 * @param response
	 *            �ظ�
	 * @return ActionForward
	 */
	public ActionForward queryWorkInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// �ж��Ƿ��в�ѯѲ�������ʷ��Ϣ��Ȩ��
		if (!CheckPower.checkPower(request.getSession(), "121001")) {
			return mapping.findForward("powererror");
		}
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		try {
			// �õ���ѯҳ����������Ĳ�ѯ��Χ�б�
			List rangeList = workInfoCommon.getRangeList(userInfo);
			request.getSession().setAttribute("rangeinfo", rangeList);
			return mapping.findForward("queryWorkInfoHistory");
		} catch (Exception e) {
			logger.error("��ѯ��Χ��Ϣʱ����:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}

	/**
	 * �õ��û��������ֹ���������������ֲ�ͼ
	 * 
	 * @param mapping
	 *            mapping
	 * @param form
	 *            Ϊ�������û������������װ���formbean
	 * @param request
	 *            ����
	 * @param response
	 *            �ظ�
	 * @return ActionForward
	 */
	public ActionForward getOnlineNumberForDays(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		HistoryWorkInfoConditionBean bean = (HistoryWorkInfoConditionBean) form;
		boParam = new HistoryWorkInfoCreateBOParam(userInfo,bean,"0",null,null); //��װ����
		context = new WorkInfoHistoryContextCurve(); //����ģ�巽��ģʽ�ĳ���ģ��
        //�õ��û��������ֹ���������������ֲ�ͼ���ؽ����Map��
		Map mapOnlineNumberForDays = context.createBO(boParam).getOnlineNumberForDays(); 
		request.getSession().setAttribute("MapOnlineNumberForDays",
				mapOnlineNumberForDays);
		// �������зֱ����ڡ���ʷ���ߡ�����Ѳ��켣���������Ż��ܡ���������ͼ����
		request.getSession().setAttribute("HistoryWorkInfoConBean", bean);
		request.getSession().setAttribute("TrackRangeID", bean.getRangeID());
		request.getSession().setAttribute("SMRangeID", bean.getRangeID());
		request.getSession()
				.setAttribute("SMGraphicRangeID", bean.getRangeID());
		// ��ʼ��givenDate�����û�ѡ���˾���ĳһ��ʱ�������¸�givenDate��ֵ��
		request.getSession().setAttribute("givenDate", "0");
		// ��������ͼ���������givenDate(Ϊ�˷�ֹ��ʱ����ʾ���ص�����ʾ��Ȼ����givenDate�Ӷ����³���,
		// flagGivenDate�������ã�����鿴ĳһ�յĶ��ţ�
		request.getSession().setAttribute("flagGivenDate", "0");
		return mapping.findForward("showWorkInfoHistory");
	}

	/**
	 * ���û��ڽ�����ѡ��select���,�õ��û��������ֹ���������������ֲ�ͼ
	 * 
	 * @param mapping
	 *            mapping
	 * @param form
	 *            form
	 * @param request
	 *            ����
	 * @param response
	 *            �ظ�
	 * @return ActionForward
	 */
	public ActionForward getOnlineNumForDaysByChange(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		request.getSession().setAttribute("givenDate", "0");
		request.getSession().setAttribute("flagGivenDate", "0");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		// �õ��ڽ�����ѡ���rangeID
		String selectedRangeID = request.getParameter("selectedRangeID");
		HistoryWorkInfoConditionBean bean = (HistoryWorkInfoConditionBean) request
				.getSession().getAttribute("HistoryWorkInfoConBean");
		// ��HistoryWorkInfoConditionBean��ʼ�ձ������µ�rangeID
		bean.setRangeID(selectedRangeID);
		// �����µ�HistoryWorkInfoConditionBean����session,�Ա�����
		request.getSession().setAttribute("HistoryWorkInfoConBean", bean);
		context = new WorkInfoHistoryContextCurve(); //����ģ�巽��ģʽ�ĳ���ģ��
		boParam = new HistoryWorkInfoCreateBOParam(userInfo,bean,"0",null,null); //��װ����
        //�õ��û��������ֹ���������������ֲ�ͼ���ؽ����Map��
		Map mapOnlineNumberForDays = context.createBO(boParam).getOnlineNumberForDays();
		request.getSession().setAttribute("MapOnlineNumberForDays",
				mapOnlineNumberForDays);
		return mapping.findForward("commonHistoryCurve");
	}

	/**
	 * �õ��û�ѡ���ľ���ĳһ���еĸ���ʱ�����������ֲ�ͼ
	 * 
	 * @param mapping
	 *            mapping
	 * @param form
	 *            form
	 * @param request
	 *            ����
	 * @param response
	 *            �ظ�
	 * @return ActionForward
	 */
	public ActionForward getOnlineNumberForHours(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		// ����û��ڡ���ֹ���������������ֲ�ͼ������ѡ��ľ�������hitDate.
		String givenDate = request.getParameter("hitDate");
		// ��������ͼ���������givenDate(Ϊ�˷�ֹ��ʱ����ʾ���ص�����ʾ��Ȼ����givenDate�Ӷ����³���,
		// flagGivenDate�������ã�����鿴ĳһ�յĶ��ţ�
		request.getSession().setAttribute("flagGivenDate", givenDate);
		HistoryWorkInfoConditionBean bean = (HistoryWorkInfoConditionBean) request
				.getSession().getAttribute("HistoryWorkInfoConBean");
		Map mapOnlineNumberForHours = null;
		context = new WorkInfoHistoryContextCurve(); //����ģ�巽��ģʽ�ĳ���ģ��
        //��װ����
		boParam = new HistoryWorkInfoCreateBOParam(userInfo,bean,givenDate,null,null);
		if (UserType.CONTRACTOR.equals(userInfo.getType())
				&& (!"22".equals(bean.getRangeID()))) { //����д�ά�û�ѡ�˾���Ѳ���飬�õ�01ͼ
			mapOnlineNumberForHours = context.createBO(boParam).getFinal01GraphicMap();
		}else{
            //�õ��û�ѡ��ľ���ĳһ���и�ʱ�����������ֲ�ͼ���ؽ����Map��
			mapOnlineNumberForHours = context.createBO(boParam).getOnlineNumberForHours();
		}
		request.getSession().setAttribute("MapOnlineNumberForHours",
				mapOnlineNumberForHours);
		// ��ʱ��Ϊ��λ��ʾ����ͼ
		request.getSession().setAttribute("givenDate", givenDate);
		return mapping.findForward("showWorkInfoGivenDay");
	}

	/**
	 * �õ��û�������Ϣ����������������������ʱ�������ĵ�ǰ���ڶ�Ӧ����Ϣ��
	 * 
	 * @param mapping
	 *            mapping
	 * @param form
	 *            form
	 * @param request
	 *            ����
	 * @param response
	 *            �ظ�
	 * @return ActionForward
	 */
	public ActionForward getOnlineInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String givenDate = request.getParameter("strDate"); // �õ����������������Ӧ������
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		HistoryWorkInfoConditionBean bean = (HistoryWorkInfoConditionBean) request
				.getSession().getAttribute("HistoryWorkInfoConBean");
		// �õ�������Ϣ�б�
		context = new WorkInfoHistoryContextCurve(); //����ģ�巽��ģʽ�ĳ���ģ��
		boParam = new HistoryWorkInfoCreateBOParam(userInfo,bean,givenDate,null,null);
		HistoryDateInfoBean backBean  = context.createBO(boParam).getOnlineInfoGivenDay();
		if ( backBean == null){
			return null;
		}
		// ��֯������Ϣ
		String backString = backBean.getBackString(givenDate);
		response.setContentType("text/html; charset=GBK");
		PrintWriter out = response.getWriter();
		out.println(backString);
		out.flush();
		return null;
	}

	/**
	 * �õ��û�������Ϣ����������������������ʱ�������ĵ�ǰʱ�ζ�Ӧ����Ϣ��
	 * 
	 * @param mapping
	 *            mapping
	 * @param form
	 *            form
	 * @param request
	 *            ����
	 * @param response
	 *            �ظ�
	 * @return ActionForward
	 */
	public ActionForward getOnlineInfoTime(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// �õ����������������Ӧ��ʱ��
		String givenDateAndTime = request.getParameter("strDateAndTime");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		// �õ�������Ϣ�б�
		context = new WorkInfoHistoryContextCurve(); //����ģ�巽��ģʽ�ĳ���ģ��
		boParam = new HistoryWorkInfoCreateBOParam(userInfo,null,"0",givenDateAndTime,null);
		HistoryTimeInfoBean backBean  = context.createBO(boParam).getOnlineInfoGivenHour();
		if (backBean == null){
			return null;
		}
		// ��֯������Ϣ
		String backString = backBean.getBackString(userInfo);
		response.setContentType("text/html; charset=GBK");
		PrintWriter out = response.getWriter();
		out.println(backString);
		out.flush();
		return null;
	}

	/**
	 * �õ�������Ϣ�����Ż��ܺͶ���ͼ����ʹ��֮��
	 * 
	 * @param mapping
	 *            mapping
	 * @param form
	 *            form
	 * @param request
	 *            ����
	 * @param response
	 *            �ظ�
	 * @return ActionForward
	 */
	public ActionForward getSMInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute("flagGivenDate", "0");
		// �õ����Ƿ�ѡ���ˡ�����ͼ����Tab��"
		String flagGraphic = request.getParameter("flagGraphic");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		HistoryWorkInfoConditionBean bean = (HistoryWorkInfoConditionBean) request
				.getSession().getAttribute("HistoryWorkInfoConBean");
		String smRangeID = "";
		context = new WorkInfoHistoryContextSM(); //����ģ�巽��ģʽ�ĳ���ģ��
		if ("0".equals(flagGraphic)) { // ���ѡ����ǡ����Ż��ܡ�tab��
			// �õ���ʾ�������Ͷ����������б����ܣ�
			smRangeID = (String) request.getSession().getAttribute("SMRangeID");
			boParam = new HistoryWorkInfoCreateBOParam(userInfo,bean,"0",null,smRangeID);
			List smHistoryList  = context.createBO(boParam).getSMInfoAllType();
			request.getSession().setAttribute("smhistoryinfo", smHistoryList);
			return mapping.findForward("showHistorySMText");
		}
		// ����û�ѡ����ǡ�����ͼ����tab��
		smRangeID = (String) request.getSession().getAttribute(
				"SMGraphicRangeID");
		boParam = new HistoryWorkInfoCreateBOParam(userInfo,bean,"0",null,smRangeID);
		// �õ���ʾ�������Ͷ����������б�(ͼ����
		List smHistoryList  = context.createBO(boParam).getSMInfoAllType();
		request.getSession()
				.setAttribute("smgraphichistoryinfo", smHistoryList);
		return mapping.findForward("showHistorySMGraphic");
	}

	/**
	 * ���û��ڽ�����ѡ��select���ͨ��Ajax���ò��õ�������Ϣ�����Ż��ܺͶ���ͼ����ʹ��֮��
	 * 
	 * @param mapping
	 *            mapping
	 * @param form
	 *            form
	 * @param request
	 *            ����
	 * @param response
	 *            �ظ�
	 * @return ActionForward
	 */
	public ActionForward getSMInfoByChangeRange(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// �õ����Ƿ�ѡ���ˡ�����ͼ����Tab��"
		String flagGraphic = request.getParameter("flagGraphic");
		// �õ��û��ڽ�����ѡ��ľ���RangeID
		String smRangeID = (String) request.getParameter("rangeID");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		HistoryWorkInfoConditionBean bean = (HistoryWorkInfoConditionBean) request
				.getSession().getAttribute("HistoryWorkInfoConBean");
		// �õ���ʾ�������Ͷ����������б�
		context = new WorkInfoHistoryContextSM(); //����ģ�巽��ģʽ�ĳ���ģ��
		boParam = new HistoryWorkInfoCreateBOParam(userInfo,bean,"0",null,smRangeID);
		List smHistoryList  = context.createBO(boParam).getSMInfoAllType();
		if ("0".equals(flagGraphic)) { // ���ѡ����ǡ����Ż��ܡ�tab��
			request.getSession().setAttribute("SMRangeID", smRangeID);
			request.getSession().setAttribute("smhistoryinfo", smHistoryList);
			return mapping.findForward("showHistorySMInfoText");
		}
		// ����û�ѡ����ǡ�����ͼ����tab��
		request.getSession().setAttribute("SMGraphicRangeID", smRangeID);
		request.getSession()
				.setAttribute("smgraphichistoryinfo", smHistoryList);
		return mapping.findForward("commonHistorySMGraphic");
	}

	/**
	 * �õ�������ѡĳһ��Ķ��ţ����Ż��ܺͶ���ͼ����ʹ��֮��
	 * 
	 * @param mapping
	 *            mapping
	 * @param form
	 *            form
	 * @param request
	 *            ����
	 * @param response
	 *            �ظ�
	 * @return ActionForward
	 */
	public ActionForward getSMInfoGivenDay(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// �õ��û��Ƿ�ѡ���ˡ�����ͼ�������ߡ����Ż��ܡ��ı�־
		String flagGraphic = request.getParameter("flagGraphic");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		HistoryWorkInfoConditionBean bean = (HistoryWorkInfoConditionBean) request
				.getSession().getAttribute("HistoryWorkInfoConBean");
		// ����ĳһ��Ķ��ŵ�����Χֻ�ܸ�����ֹ�����ڵ�����ͼ����Χ���仯��
		String smRangeID = bean.getRangeID();
		// ��������ͼ���������givenDate(Ϊ�˷�ֹ��ʱ����ʾ���ص�����ʾ��Ȼ����givenDate�Ӷ����³���),
		// flagGivenDate�������ã�����鿴ĳһ�յĶ��ţ�
		String givenDate = (String) request.getSession().getAttribute(
				"flagGivenDate");
		context = new WorkInfoHistoryContextSM(); //����ģ�巽��ģʽ�ĳ���ģ��
		boParam = new HistoryWorkInfoCreateBOParam(userInfo,null,givenDate,null,smRangeID);
		// �õ�������ѡĳһ��Ķ����б���ʱgivenDate��ֵ������һ������HistoryWorkInfoConditionBean��Ϊ��
		List smHistoryListGivenDay  = context.createBO(boParam).getSMInfoAllType();
		if ("0".equals(flagGraphic)) { // ���ѡ����ǡ����Ż��ܡ�tab��
			request.getSession().setAttribute("SMRangeID", smRangeID);
			request.getSession().setAttribute("smhistoryinfogivenday",
					smHistoryListGivenDay);
			return mapping.findForward("showHistorySMTextGivenDay");
		}
		// ����û�ѡ����ǡ�����ͼ����tab��
		request.getSession().setAttribute("smgraphichistoryinfogivenday",
				smHistoryListGivenDay);
		return mapping.findForward("commonHistorySMGraphic");
	}
}
