package com.cabletech.exterior.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.analysis.services.StatForWholeBO;
import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.commons.util.StrReformSplit;
import com.cabletech.exterior.service.StatWholeBO;
/**
 * AreaContrastAction 
 * 提供了综合平台调的接口
 * @author Administrator
 *
 */
public class AreaContrastAction extends BaseInfoBaseDispatchAction  {
	private StatForWholeBO statBo = new StatForWholeBO();
	private  StatWholeBO sbo = new StatWholeBO();
	/**
	 * 全身各区域间的对比
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getAreaContrastDataForWhole(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		//获取 默认月份 = 当前月份-1
		if(year == null && month == null){
			String dateStr = sbo.dateSubtract(new Date(),-1);
			List dateList  = StrReformSplit.getStrSplit(dateStr, "-");
			year = (String)dateList.get(0);
			month = (String)dateList.get(1);
		}
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		session.setAttribute("width", new Integer(750));
		session.setAttribute("height",new Integer(350));
		List areaList = statBo.getAreaContrast(year, month);
		session.setAttribute("areaList", areaList);
		return mapping.findForward("areaContrast");
	}

}
