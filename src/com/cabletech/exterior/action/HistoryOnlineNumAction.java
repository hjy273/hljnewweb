package com.cabletech.exterior.action;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cabletech.analysis.beans.HistoryDateInfoBean;
import com.cabletech.analysis.beans.HistoryWorkInfoConditionBean;
import com.cabletech.analysis.beans.HistoryWorkInfoCreateBOParam;
import com.cabletech.analysis.services.WorkInfoHistoryContext;
import com.cabletech.analysis.services.WorkInfoHistoryContextCurve;
import com.cabletech.analysis.util.WorkInfoHistoryCommon;
import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.UserInfoBO;
import com.cabletech.commons.config.UserType;

public class HistoryOnlineNumAction extends BaseInfoBaseDispatchAction {
	private HistoryWorkInfoCreateBOParam boParam = null;
    private WorkInfoHistoryContext context  = null;
	private WorkInfoHistoryCommon workInfoCommon = new WorkInfoHistoryCommon();
	private UserInfoBO ubo = new UserInfoBO();
	private Logger logger =Logger.getLogger(this.getClass().getName()); // ����logger�������;
	
	public ActionForward getOnlineNum(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		String year = request.getParameter("theyear");
		String month = request.getParameter("themonth");
        //��ȡ Ĭ���·� = ��ǰ�·�-1
		if(year == null && month == null){
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH,-1);
			year = String.valueOf(cal.get(Calendar.YEAR));
			month = String.valueOf(cal.get(Calendar.MONTH)+1);
		}
		HistoryWorkInfoConditionBean bean = new HistoryWorkInfoConditionBean();
		bean.setBeanAccordtoCondition("11", year, month);
//		logger.info(bean.getEndDate() + "," + bean.getStartDate() + "," + bean.getRangeID());
		
		//String userID = request.getParameter("uid");
		String userID = "aa";
		UserInfo userInfo = ubo.loadUserInfo(userID);
		userInfo.setType(UserType.PROVINCE);
		try {
			// �õ���ѯҳ����������Ĳ�ѯ��Χ�б�
			List rangeList = workInfoCommon.getRangeList(userInfo);
			request.getSession().setAttribute("rangeinfo", rangeList);
		} catch (Exception e) {
			logger.error("��ѯ��Χ��Ϣʱ����:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
		boParam = new HistoryWorkInfoCreateBOParam(userInfo, bean, "0", null,
				null); // ��װ����
		if (boParam == null){
			logger.info("boParam is null");
		}
		context = new WorkInfoHistoryContextCurve(); // ����ģ�巽��ģʽ�ĳ���ģ��
		// �õ��û��������ֹ���������������ֲ�ͼ���ؽ����Map��
		Map mapOnlineNumberForDays = context.createBO(boParam)
				.getOnlineNumberForDays();
		request.getSession().setAttribute("MapOnlineNumberForDays",
				mapOnlineNumberForDays);
		request.getSession().setAttribute("HistoryWorkInfoConBean", bean);
		session.setAttribute("LOGIN_USER", userInfo);
		request.setAttribute("theyear", year);
		request.setAttribute("themonth", month);
		session.setAttribute("thewidth", new Integer(750));
		session.setAttribute("theheight",new Integer(350));
//		 ��ʼ��givenDate�����û�ѡ���˾���ĳһ��ʱ�������¸�givenDate��ֵ��
		session.setAttribute("givenDate", "0");
		return mapping.findForward("showHistoryCurve");
	}
	
	public ActionForward getOnlineNumForDaysByChange(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		request.getSession().setAttribute("givenDate", "0");
		request.getSession().setAttribute("flagGivenDate", "0");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		// �õ��ڽ�����ѡ���rangeID
		String selectedRangeID = request.getParameter("selectedRangeID");
		if (selectedRangeID == null || "".equals(selectedRangeID)){
			selectedRangeID = "11";
		}
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
		return mapping.findForward("showHistoryCurveChart");
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
		logger.info("givendate:" + givenDate);
		HistoryWorkInfoConditionBean bean = (HistoryWorkInfoConditionBean) request
				.getSession().getAttribute("HistoryWorkInfoConBean");
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute("LOGIN_USER");
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
}
