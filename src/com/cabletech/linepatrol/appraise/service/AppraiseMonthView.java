package com.cabletech.linepatrol.appraise.service;

import java.util.List;
import java.util.Map;

import com.cabletech.linepatrol.appraise.module.AppraiseContent;
import com.cabletech.linepatrol.appraise.module.AppraiseItem;
import com.cabletech.linepatrol.appraise.module.AppraiseRule;
import com.cabletech.linepatrol.appraise.module.AppraiseRuleResult;
import com.cabletech.linepatrol.appraise.module.AppraiseTable;

public class AppraiseMonthView {
	private String tableClass = "tabout";//�����ʽ
	private String trClass = "trcolor";//����ʽ
	private String tdClass = "";//����ʽ
	private String tableAlign = "center";//table����
	private String tableStyle = "";//���ñ���Style��ʽ
	
	// ��ǩ����ʾ��ʽ
	public String getTableView(AppraiseTable appraiseTable,Map<String,String> appraiseDailyNumByRule,Map<String,Object> appraiseRuleResults,String type) {
		
		StringBuilder html = new StringBuilder();
			
			//����һ�����
			html.append("<table width=\"100%\" class=\"");
			html.append(tableClass);
			html.append("\" style=\"");
			html.append(tableStyle);
			html.append("\" align=\"");
			html.append(tableAlign);
			html.append("\">");

			//�����¿��˵�ͷ����Ϣ
			html.append(exportTableHeader());
			
			List<AppraiseItem> appraiseItemList = appraiseTable
					.getAppraiseItems();
			for (int itemNum = 0; itemNum < appraiseItemList.size(); itemNum++) {
				AppraiseItem appraiseItem = appraiseItemList.get(itemNum);
				
				//�����һ����Ϣ
				html.append(exportFirstColumnInfo(appraiseItem));

				// ���Content�б�
				List<AppraiseContent> appraiseContentList = appraiseItem
						.getAppraiseContents();
				for (int contentNum = 0; contentNum < appraiseContentList
						.size(); contentNum++) {
					AppraiseContent appraiseContent = appraiseContentList
							.get(contentNum);
					//����ڶ�����Ϣ
					html.append(exportSecondColumnInfo(contentNum, appraiseContent));
					
					//����������ġ��塢����������Ϣ
					html.append(exportThirdFourInfo(appraiseContent,appraiseDailyNumByRule,appraiseRuleResults,type));
				}
			}
		if (appraiseRuleResults != null) {
			if (type.equals("edit")) {
			} else {
				html.append("<tr align=\"center\" class=\"" + trClass + "\"><td colspan=\"3\" class=\"" + tdClass
						+ "\">�ϼ�</td><td class=\"" + tdClass + "\">" + appraiseTable.getAllWeight().get("itemWeight")
						+ "</td><td class=\"" + tdClass + "\">" + appraiseRuleResults.get("result")
						+ "</td><td class=\"" + tdClass + "\"></td><td class=\"" + tdClass + "\"></td></tr>");
			}
		}
		html.append("</table>");
		return html.toString();
	}

	/**
	 * ����ڶ��е��¿���������Ϣ
	 * @param contentNum �ڼ���
	 * @param appraiseContent �¿�������ʵ��
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
		html.append(appraiseContent.getContentDescription()+"��"+appraiseContent.getWeight()+"�֣�");
		html.append("</td>");
		return html.toString();
	}

	/**
	 * �������һ�е��¿�����Ŀ��Ϣ
	 * @param appraiseItem �¿�����Ŀ����
	 * @return
	 */
	private String exportFirstColumnInfo(AppraiseItem appraiseItem) {
		StringBuffer html = new StringBuffer("");
		html.append("<tr class=\"" + trClass + "\">" + "<td class=\"" + tdClass + "\" valign=\"middle\" rowspan=\""
				+ appraiseItem.getItemSize() + "\">" + appraiseItem.getItemName()+"��"+appraiseItem.getWeight()+"�֣�" + "</td>");
		return html.toString();
	}

	/**
	 * �����¿��˵�ͷ����̬��Ϣ
	 */
	private String exportTableHeader() {
		StringBuffer html = new StringBuffer("");
		html.append("<tr class=\"");
		html.append(trClass);
		html.append("\">");
		html.append("<td style=\"font-weight: bold;text-align: center;\">������Ŀ</td>");
		html.append("<td style=\"font-weight: bold;text-align: center;\">��������</td>");
		html.append("<td style=\"font-weight: bold;text-align: center;\">����ϸ��</td>");
		html.append("<td style=\"font-weight: bold;text-align: center;\" width=\"3%\">Ȩ��</td>");
		html.append("<td style=\"font-weight: bold;text-align: center;\">�÷�</td>");
		html.append("<td style=\"font-weight: bold;text-align: center;\">��������</td>");
		html.append("<td style=\"font-weight: bold;text-align: center;\">����˵��</td>");
		html.append("</tr>");
		return html.toString();
	}




	/**
	 * �����������HTML�������С������С������С������С���������Ϣ
	 * 
	 * @param appraiseContent
	 * @return
	 */
	private String exportThirdFourInfo(AppraiseContent appraiseContent,Map<String,String> appraiseDailyNumByRule,Map<String,Object> appraiseRuleResults,String type) {
		StringBuffer html = new StringBuffer();
		// ��þ������б�
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
						+ appraiseRule.getId() + "\" size=\"2\"></input>");//Ĭ�ϵ÷�Ϊ����
				html.append("<input type=\"text\" id=\"results\" name=\"results\" styleClass=\"inputtext validate-number\" onblur=\"onchangeValue('"+appraiseRule.getWeight()+"',this)\" value=\""+appraiseRule.getWeight()+"\" size=\"2\"></input>");
				html.append("</td>");
				html.append("<td class=\"" + tdClass + "\" algin=\"center\">");
				String dailyNum = appraiseDailyNumByRule.get(appraiseRule.getId())!= null ? appraiseDailyNumByRule.get(appraiseRule.getId()):"0";
				html.append("<a href=\"javascript:viewExamBase('"+appraiseRule.getId()+"')\">�鿴("+dailyNum+")</a>");
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
				html.append("<a href=\"javascript:viewExamBase('"+appraiseRule.getId()+"')\">�鿴</a>");
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
