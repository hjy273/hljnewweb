package com.cabletech.linepatrol.appraise.service;

import java.util.List;

import com.cabletech.linepatrol.appraise.module.AppraiseContent;
import com.cabletech.linepatrol.appraise.module.AppraiseItem;
import com.cabletech.linepatrol.appraise.module.AppraiseRule;
import com.cabletech.linepatrol.appraise.module.AppraiseTable;


public class AppraiseTableView {
	private String tableClass = "tabout";//表格样式
	private String trClass = "trcolor";//行样式
	private String tdClass = "";//列样式
	private String tableAlign = "center";//table排列
	private String tableStyle = "";//设置表格的Style样式
	
	// 标签的显示方式
	public String getTableView(AppraiseTable appraiseTable) {
		
		StringBuilder html = new StringBuilder();
			
			//创建一个表格
			html.append("<table class=\"");
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
				
				//输入第一、二列信息
				html.append(exportFirstColumnInfo(appraiseItem));

				// 获得Content列表
				List<AppraiseContent> appraiseContentList = appraiseItem
						.getAppraiseContents();
				for (int contentNum = 0; contentNum < appraiseContentList
						.size(); contentNum++) {
					AppraiseContent appraiseContent = appraiseContentList
							.get(contentNum);
					//输出第三、四列信息
					html.append(exportSecondColumnInfo(contentNum, appraiseContent));
					
					//输出第五、六、七、八列信息
					html.append(exportThirdFourInfo(appraiseContent));
				}
			}
			html.append("<tr class=\""+trClass+"\"><td>合计</td><td>"+appraiseTable.getAllWeight().get("tableWeight")+"</td><td></td><td>"+appraiseTable.getAllWeight().get("itemWeight")+"</td><td></td><td>"+appraiseTable.getAllWeight().get("contentWeight")+"</td><td></td><td></td></tr>");
			html.append("</table>");
		return html.toString();
	}

	/**
	 * 输出第三列、第四列的月考核内容信息
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
		html.append(appraiseContent.getContentDescription());
		html.append("</td>");
		html.append("<td class=\"");
		html.append(tdClass);
		html.append("\" valign=\"middle\" rowspan=\"");
		html.append(appraiseContent.getAppraiseRules().size());
		html.append("\">");
		html.append(appraiseContent.getWeight());
		html.append("</td>");
		return html.toString();
	}

	/**
	 * 输出表格第一列,第二列的月考核项目信息
	 * @param appraiseItem 月考核项目对象
	 * @return
	 */
	private String exportFirstColumnInfo(AppraiseItem appraiseItem) {
		StringBuffer html = new StringBuffer("");
		html.append("<tr class=\"" + trClass + "\">" + "<td class=\"" + tdClass + "\" valign=\"middle\" rowspan=\""
				+ appraiseItem.getItemSize() + "\">" + appraiseItem.getItemName() + "</td><td class=\"" + tdClass
				+ "\" valign=\"middle\" rowspan=\"" + appraiseItem.getItemSize() + "\">" + appraiseItem.getWeight() + "</td>");
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
		html.append("<td style=\"font-weight: bold;text-align: center;\">项目</td>");
		html.append("<td style=\"font-weight: bold;text-align: center;\">权重分</td>");
		html.append("<td style=\"font-weight: bold;text-align: center;\">内容</td>");
		html.append("<td style=\"font-weight: bold;text-align: center;\">权重</td>");
		html.append("<td style=\"font-weight: bold;text-align: center;\">细则</td>");
		html.append("<td style=\"font-weight: bold;text-align: center;\">满分</td>");
		html.append("<td style=\"font-weight: bold;text-align: center;\">得分</td>");
		html.append("<td style=\"font-weight: bold;text-align: center;\">备注</td>");
		html.append("</tr>");
		return html.toString();
	}




	/**
	 * 获得子项的输出HTML，第五列、第六列、第七列和第八列信息
	 * 
	 * @param appraiseContent
	 * @return
	 */
	private String exportThirdFourInfo(AppraiseContent appraiseContent) {
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
			html.append("<td class=\""+tdClass+"\">");
			html.append("</td>");
			html.append("<td class=\""+tdClass+"\">");
			html.append(appraiseRule.getGradeDescription());
			html.append("</td>");
			html.append("</tr>");
		}
		return html.toString();
	}
}
