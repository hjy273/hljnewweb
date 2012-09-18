package com.cabletech.exterior.action;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.web.ClientException;
import com.cabletech.exterior.beans.WatchExeStaCondtionBean;
import com.cabletech.exterior.beans.WatchExeStaResultBean;
import com.cabletech.planstat.action.LogPathAction;
import com.cabletech.watchinfo.services.WatchInfoService;

/**
 * WatchExeAnalysisAction 
 * 提供了综合平台盯防管理的调用接口
 * @author Administrator
 *
 */
public class WatchExeAnalysisAction extends BaseDispatchAction  {
	private Logger logger = Logger.getLogger(WatchExeAnalysisAction.class.getName());
	/**
	 * 显示盯防执行情况统计信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getWatchExeStaResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws
	        ClientException, Exception{
		HttpSession session = request.getSession();
		String year = request.getParameter("theyear");
		String month = request.getParameter("themonth");
		//获取 默认月份 = 当前月份-1
		if(year == null && month == null){
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH,-1);
			year = String.valueOf(cal.get(Calendar.YEAR));
			month = String.valueOf(cal.get(Calendar.MONTH)+1);
		}
		WatchExeStaCondtionBean conditionBean = new WatchExeStaCondtionBean();
		conditionBean.setYear(year);
		conditionBean.setMonth(month);
		WatchInfoService service = new WatchInfoService();
		List watchExeStaResultList = service.getStaResultBeanForAA(conditionBean);
		request.setAttribute("theyear", year);
		request.setAttribute("themonth", month);
		session.setAttribute("thewidth", new Integer(750));
		session.setAttribute("theheight",new Integer(350));
		session.setAttribute("watchExeStaResultList", watchExeStaResultList);
		return mapping.findForward("showWatchExeStaResult");
	}
}
