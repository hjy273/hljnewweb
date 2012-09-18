package com.cabletech.linepatrol.appraise.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;

public class AppraiseDailySpecialAction extends BaseDispatchAction {
	@SuppressWarnings("unchecked")
	public ActionForward saveForm(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) {
		AppraiseDailyBean appraiseDailyBean=(AppraiseDailyBean)form;
		List<AppraiseDailyBean> specialBeans=(List<AppraiseDailyBean>)request.getSession().getAttribute("appraiseDailySpecialBean");
		if(specialBeans==null){
			specialBeans=new ArrayList<AppraiseDailyBean>();
		}
		List<AppraiseDailyBean> specialBeanList=new ArrayList<AppraiseDailyBean>();
		for(AppraiseDailyBean appraiseDailyBean2:specialBeans){
			if(!appraiseDailyBean2.getTableId().equals(appraiseDailyBean.getTableId())){
				specialBeanList.add(appraiseDailyBean2);
			}
		}
		specialBeanList.add(appraiseDailyBean);
		request.getSession().setAttribute("appraiseDailySpecialBean", specialBeanList);
		return null;
	} 
}
