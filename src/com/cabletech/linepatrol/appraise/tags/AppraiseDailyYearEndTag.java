package com.cabletech.linepatrol.appraise.tags;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;
import com.cabletech.linepatrol.appraise.module.AppraiseContent;
import com.cabletech.linepatrol.appraise.module.AppraiseDailyMark;
import com.cabletech.linepatrol.appraise.module.AppraiseRule;
import com.cabletech.linepatrol.appraise.module.AppraiseTable;
import com.cabletech.linepatrol.appraise.service.AppraiseConstant;
import com.cabletech.linepatrol.appraise.service.AppraiseDailyBO;
import com.cabletech.linepatrol.appraise.service.AppraiseDailyMarkBO;

public class AppraiseDailyYearEndTag extends AppraiseDailyTag {
	private String year;
	@Override
	void addContractNo(StringBuffer html) {
		html.append("<td width=\"25%\">中标合同号 : ");
		html.append(addUnderline(contractNo));
		html.append("<input type=\"hidden\" id=\"contractNo\" name=\"contractNo\" value=\"");
		html.append(contractNo);
		html.append("\"/>");
		html.append("<input type=\"hidden\" id=\"year\" name=\"year\" value=\""+year+"\" />");
		html.append("<input type=\"hidden\" id=\"" + flagName + "markDeductions\" name=\"" + flagName + "markDeductions\" />");
		html.append("</td>");
		html.append("<td width=\"5%\"> </td>");
	}

	@Override
	public void init() {
		AppraiseDailyBean bean = initAppraiseDailyBean();
		bean.setContractNo(contractNo);
		bean.setYear(year);
		AppraiseDailyBO appraiseDailyBO = getAppraiseDailyBO("appraiseDailyYearEndBO");
		Map<String, Object> map = null;
		map = getAppraiseDailyDataMap(bean, appraiseDailyBO, map);
		appraiseTables = (List<AppraiseTable>) map.get("appraiseTables");
		contractorName = (String) map.get("contractorName");
		userInfo = (UserInfo) super.pageContext.getSession().getAttribute(
				"LOGIN_USER");
		if(StringUtils.equals("view", displayType)){
			contractNo = (String)map.get("contractNo");
		}
		
	}

	@Override
	void setAppraiseName() {
		appriaseName=AppraiseConstant.APPRAISENAME_YEAREND;
		
	}

	@Override
	void setAppraiseTypeValue() {
		appraiseType=AppraiseConstant.TYPE_YEAREND;
		
	}
	/**
	 * 输入月考核的头部静态信息
	 */
	@Override
	String exportTableHeader() {
		StringBuffer html = new StringBuffer("");
		html.append(getTrFront());
		html.append("<td style=\"font-weight: bold;text-align: center;\">项目</td>");
		html.append("<td style=\"font-weight: bold;text-align: center;\">内容</td>");
		html.append("<td style=\"font-weight: bold;text-align: center;\">细则</td>");
		html.append("<td style=\"font-weight: bold;text-align: center;\">得分</td>");
		html.append("<td style=\"font-weight: bold;text-align: center;\">扣分说明（操作请双击）</td>");
		html.append("</tr>");
		return html.toString();
	}
	@Override
	String exportThirdFourInfo(AppraiseContent appraiseContent) {
		applicationContext = WebApplicationContextUtils.getWebApplicationContext(super.pageContext.getServletContext());
		AppraiseDailyMarkBO appraiseDailyMarkBO = (AppraiseDailyMarkBO) applicationContext.getBean("appraiseDailyMarkBO");
		Map<String,Object> appraiseDailyMarks=appraiseDailyMarkBO.getAllMarkDeductionsByDailyId(id);
		StringBuffer html = new StringBuffer();
		// 获得具体想列表
		List<AppraiseRule> appraiseRuleList = appraiseContent
				.getAppraiseRules();
		for (int i = 0; i < appraiseRuleList.size(); i++) {
			AppraiseRule appraiseRule = appraiseRuleList.get(i);
			if (i != 0) {
				html.append(getTrFront());
			}
			html.append(getTdFront());
			exportThirdInfo(html, appraiseRule);
			exportOtherInfo(html, appraiseRule,appraiseDailyMarks);
			exportFourInfo(html, appraiseRule);
			html.append("</tr>");
		}
		return html.toString();
	}
	private void exportOtherInfo(StringBuffer html,AppraiseRule appraiseRule,Map<String,Object> appraiseDailyMarks){
		if (StringUtils.equals("input", displayType)) {
			html.append(getTdFront());
			html.append("<input type=\"text\" disabled=\"disabled\" size=\"5\" styleClass=\"inputtext validate-number\" id=\"");
			html.append(appraiseRule.getId());
			html.append("markDeduction\" onblur=\"saveForm(\'"+appraiseType+"\',\'"+tableId+"\')\"/>");
		} else {
			html.append("<td class=\""+tdClass+"\">");
			html.append(appraiseDailyMarks.get(appraiseRule.getId()));
		}
		html.append("</td>");
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
}
