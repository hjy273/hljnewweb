package com.cabletech.linepatrol.appraise.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;

/**
 * »’≥£øº∫À
 * 
 * @author liusq
 * 
 */
public class AppraiseDailyDailyAction extends BaseDispatchAction {

	private static final long serialVersionUID = 7522968325374910135L;

	public ActionForward saveForm(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) {
		AppraiseDailyBean appraiseDailyBean = (AppraiseDailyBean)form;
		request.getSession().setAttribute("appraiseDailyDailyBean", appraiseDailyBean);
		return null;
	}
}
