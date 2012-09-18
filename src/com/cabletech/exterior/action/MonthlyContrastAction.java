package com.cabletech.exterior.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.commons.exception.SubtrahendException;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.commons.util.StrReformSplit;
import com.cabletech.exterior.service.StatWholeBO;
/**
 * MonthlyContrastAction
 * �ṩ���ۺ�ƽ̨���Ľӿ�
 * @author Administrator
 *
 */
public class MonthlyContrastAction extends BaseInfoBaseDispatchAction  {
	private StatWholeBO statBo = new StatWholeBO();
	/**
	 * ȫʡ������������Ա�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getmMonthlyContrastForWhole(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		System.out.println("MMmonth == "+month);
		//��ȡ Ĭ���·� = ��ǰ�·�-1
		if(year == null && month == null){
			String dateStr = statBo.dateSubtract(new Date(),-1);
			List dateList  = StrReformSplit.getStrSplit(dateStr, "-");
			year = (String)dateList.get(0);
			month = (String)dateList.get(1);
			System.out.println("For _MMmonth == "+ month);
		}
		Date date = DateUtil.parseDate(year+"/"+month+"/01");
		String theYearMonth =year+"-" + month + "��ǰ6���µĶԱ�";
		session.setAttribute("endYear", year);
		session.setAttribute("endMonth", month);
		session.setAttribute("width", new Integer(750));
		session.setAttribute("height",new Integer(350));
		try {
			String temp = statBo.dateSubtract(date);
			theYearMonth = temp.substring(0,temp.length()-3)+"�� �� "+year+"-" + month + "�µĶԱ�";
			session.setAttribute("beginYear", temp.substring(0,temp.indexOf("-")));
			session.setAttribute("beginMonth", temp.substring(temp.indexOf("-")+1,temp.length()-3));
		} catch (SubtrahendException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		session.setAttribute("YMForComComp", theYearMonth);
		List monthList = statBo.getMonthlyContrast(year, month);
		session.setAttribute("comCompVInfo", monthList);
		return mapping.findForward("monthContrast");
	}
}
