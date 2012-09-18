package com.cabletech.linepatrol.appraise.service;

import java.util.List;
import java.util.Map;

import com.cabletech.linepatrol.appraise.module.AppraiseContent;
import com.cabletech.linepatrol.appraise.module.AppraiseItem;
import com.cabletech.linepatrol.appraise.module.AppraiseRule;
import com.cabletech.linepatrol.appraise.module.AppraiseRuleResult;
import com.cabletech.linepatrol.appraise.module.AppraiseTable;

public class AppraiseMonthView {
	private String tableClass = "tabout";//表格样式
	private String trClass = "trcolor";//行样式
	private String tdClass = "";//列样式
	private String tableAlign = "center";//table排列
	private String tableStyle = "";//设置表格的Style样式
	
	// 标签的显示方式
	public String getTableView(AppraiseTable appraiseTable,Map<String,String> appraiseDailyNumByRule,Map<String,Object> appraiseRuleResults,String type) {
		
		StringBuilder html = new StringBuilder();
			
			//创建一个表格
			html.append("<table width=\"100%\" class=\"");
			html.append(tableClass);
			html.append("\" style=\"");
			html.append(tableStyle);
			html.append("\" align=\"");
			html.append(tableAlign);
			html.append("\">");

			//输入月考核的头部信息
			html.append(exportTableHeader());
			
			List<AppraiseItem> appraiseItemList = appraiseTable
					.getAppraiseItems();
			for (int itemNum = 0; itemNum < appraiseItemList.size(); itemNum++) {
				AppraiseItem appraiseItem = appraiseItemList.get(itemNum);
				
				//输入第一列信息
				html.append(exportFirstColumnInfo(appraiseItem));

				// 获得Content列表
				List<AppraiseContent> appraiseContentList = appraiseItem
						.getAppraiseContents();
				for (int contentNum = 0; contentNum < appraiseContentList
						.size(); contentNum++) {
					AppraiseContent appraiseContent = appraiseContentList
							.get(contentNum);
					//输出第二列信息
					html.append(exportSecondColumnInfo(contentNum, appraiseContent));
					
					//输出第三、四、五、六、七列信息
					html.append(exportThirdFourInfo(appraiseContent,appraiseDailyNumByRule,appraiseRuleResults,type));
				}
			}
		if (appraiseRuleResults != null) {
			if (type.equals("edit")) {
			} else {
				html.append("<tr align=\"center\" class=\"" + trClass + "\"><td colspan=\"3\" class=\"" + tdClass
						+ "\">合计</td><td class=\"" + tdClass + "\">" + appraiseTable.getAllWeight().get("itemWeight")
						+ "</td><td class=\"" + tdClass + "\">" + appraiseRuleResults.get("result")
						+ "</td><td class=\"" + tdClass + "\"></td><td class=\"" + tdClass + "\"></td></tr>");
			}
		}
		html.append("</table>");
		return html.toString();
	}

	/**
	 * 输出第二列的月考核内容信息
	 * @param contentNum 第几条
	 * @param appraiseContent 月考核内容实体
	 * @return
	 */
	private String exportSecondColumnInfo(int contentNum,
			AppraiseContent appraiseContent) {
		StringBuffer html = new StringBuffer("");
		if (contentNum != 0) {
			html.append("<tr class=\"");
			html.append(trClass);
			html.append("\">");
		}
		html.append("<td class=\"");
		html.append(tdClass);
		html.append("\" valign=\"middle\" rowspan=\"");
		html.append(appraiseContent.getAppraiseRules().size());
		html.append("\">");
		html.append(appraiseContent.getContentDescription()+"（"+appraiseContent.getWeight()+"分）");
		html.append("</td>");
		return html.toString();
	}

	/**
	 * 输出表格第一列的月考核项目信息
	 * @param appraiseItem 月考核项目对象
	 * @return
	 */
	private String exportFirstColumnInfo(AppraiseItem appraiseItem) {
		StringBuffer html = new StringBuffer("");
		html.append("<tr class=\"" + trClass + "\">" + "<td class=\"" + tdClass + "\" valign=\"middle\" rowspan=\""
				+ appraiseItem.getItemSize() + "\">" + appraiseItem.getItemName()+"（"+appraiseItem.getWeight()+"分）" + "</td>");
		return html.toString();
	}

	/**
	 * 输入月考核的头部静态信息
	 */
	private String exportTableHeader() {
		StringBuffer html = new StringBuffer("");
		html.append("<tr class=\"");
		html.append(trClass);
		html.append("\">");
		html.append("<td style=\"font-weight: bold;text-align: center;\">考核项目</td>");
		html.append("<td style=\"font-weight: bold;text-align: center;\">考核内容</td>");
		html.append("<td style=\"font-weight: bold;text-align: center;\">评分细则</td>");
		html.append("<td style=\"font-weight: bold;text-align: center;\" width=\"3%\">权重</td>");
		html.append("<td style=\"font-weight: bold;text-align: center;\">得分</td>");
		html.append("<td style=\"font-weight: bold;text-align: center;\">考核依据</td>");
		html.append("<td style=\"font-weight: bold;text-align: center;\">评分说明</td>");
		html.append("</tr>");
		return html.toString();
	}




	/**
	 * 获得子项的输出HTML，第三列、第四列、第五列、第六列、第七列信息
	 * 
	 * @param appraiseContent
	 * @return
	 */
	private String exportThirdFourInfo(AppraiseContent appraiseContent,Map<String,String> appraiseDailyNumByRule,Map<String,Object> appraiseRuleResults,String type) {
		StringBuffer html = new StringBuffer();
		// 获得具体想列表
		List<AppraiseRule> appraiseRuleList = appraiseContent
				.getAppraiseRules();
		for (int i = 0; i < appraiseRuleList.size(); i++) {
			AppraiseRule appraiseRule = appraiseRuleList.get(i);
			if (i != 0) {
				html.append("<tr class=\"");
				html.append(trClass);
				html.append("\">");
			}
			html.append("<td class=\""+tdClass+"\">");
			html.append(appraiseRule.getRuleDescription());
			html.append("</td>");
			html.append("<td class=\""+tdClass+"\">");
			html.append(appraiseRule.getWeight());
			html.append("</td>");
			if (appraiseRuleResults == null) {
				html.append("<td class=\"" + tdClass + "\">");
				html.append("<input type=\"hidden\" id=\"rules\" name=\"rules\" styleClass=\"inputtext\" value=\""
						+ appraiseRule.getId() + "\" size=\"2\"></input>");//默认得分为满分
				html.append("<input type=\"text\" id=\"results\" name=\"results\" styleClass=\"inputtext validate-number\" onblur=\"onchangeValue('"+appraiseRule.getWeight()+"',this)\" value=\""+appraiseRule.getWeight()+"\" size=\"2\"></input>");
				html.append("</td>");
				html.append("<td class=\"" + tdClass + "\" algin=\"center\">");
				String dailyNum = appraiseDailyNumByRule.get(appraiseRule.getId())!= null ? appraiseDailyNumByRule.get(appraiseRule.getId()):"0";
				html.append("<a href=\"javascript:viewExamBase('"+appraiseRule.getId()+"')\">查看("+dailyNum+")</a>");
				html.append("</td>");
				html.append("<td class=\"" + tdClass + "\">");
				html.append("<input type=\"text\" id=\"remarks\" name=\"remarks\" value=\" \" styleClass=\"inputtext\"></input>");
				html.append("</td>");
			} else {
				String disabled="";
				if(!type.equals("edit")){
					disabled="disabled=\"disabled\"";
				}
				AppraiseRuleResult appraiseRuleResult=(AppraiseRuleResult)appraiseRuleResults.get(appraiseRule.getId());
				html.append("<td class=\""+tdClass+"\">");
				html.append("<input type=\"hidden\" id=\"rules\" name=\"rules\" styleClass=\"inputtext\" value=\""
						+ appraiseRule.getId() + "\" size=\"2\"></input>");
				html.append("<input type=\"text\" id=\"results\" name=\"results\" styleClass=\"inputtext validate-number\" "+disabled+" value=\""+appraiseRuleResult.getResult()+"\" size=\"2\"></input>");
				html.append("</td>");
				html.append("<td class=\""+tdClass+"\" algin=\"center\">");
				html.append("<a href=\"javascript:viewExamBase('"+appraiseRule.getId()+"')\">查看</a>");
				html.append("</td>");
				html.append("<td class=\""+tdClass+"\">");
				html.append("<input type=\"text\" id=\"remarks\" name=\"remarks\" "+disabled+" value=\""+appraiseRuleResult.getRemark()+"\" styleClass=\"inputtext\"></input>");
				html.append("</td>");
			}
			html.append("</tr>");
		}
		return html.toString();
	}
}
