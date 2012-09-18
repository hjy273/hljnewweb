package com.cabletech.linepatrol.appraise.tags;


import java.util.List;
import java.util.Map;


import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;
import com.cabletech.linepatrol.appraise.module.AppraiseTable;
import com.cabletech.linepatrol.appraise.service.AppraiseConstant;
import com.cabletech.linepatrol.appraise.service.AppraiseDailyBO;
public class AppraiseDailySpecialTag extends AppraiseDailyTag {
	@Override
	public void init() {
		AppraiseDailyBean bean=initAppraiseDailyBean();
		AppraiseDailyBO appraiseDailyBO = getAppraiseDailyBO("appraiseDailySpecialBO");
		Map<String, Object> map = null;
		map = getAppraiseDailyDataMap(bean, appraiseDailyBO, map);
		appraiseTables=(List<AppraiseTable>) map.get("appraiseTables");
		contractorName = (String) map.get("contractorName");
		userInfo = (UserInfo) super.pageContext.getSession().getAttribute(
		"LOGIN_USER");
	}
	@Override
	void addContractNo(StringBuffer html){
		html.append("<td width=\"25%\"> </td>");
	}
	@Override
	void setAppraiseTypeValue() {
		appraiseType=AppraiseConstant.TYPE_SPECIAL;
	}
	@Override
	void setAppraiseName() {
		appriaseName=AppraiseConstant.APPRAISENAME_SPECIAL;
		
	}
}
