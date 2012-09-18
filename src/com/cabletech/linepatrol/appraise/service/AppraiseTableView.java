package com.cabletech.linepatrol.appraise.service;

import java.util.List;

import com.cabletech.linepatrol.appraise.module.AppraiseContent;
import com.cabletech.linepatrol.appraise.module.AppraiseItem;
import com.cabletech.linepatrol.appraise.module.AppraiseRule;
import com.cabletech.linepatrol.appraise.module.AppraiseTable;


public class AppraiseTableView {
	private String tableClass = "tabout";//������ʽ
	private String trClass = "trcolor";//����ʽ
	private String tdClass = "";//����ʽ
	private String tableAlign = "center";//table����
	private String tableStyle = "";//���ñ����Style��ʽ
	
	// ��ǩ����ʾ��ʽ
	public String getTableView(AppraiseTable appraiseTable) {
		
		StringBuilder html = new StringBuilder();
			
			//����һ������
			html.append("<table class=\"");
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
				
				//�����һ��������Ϣ
				html.append(exportFirstColumnInfo(appraiseItem));

				// ���Content�б�
				List<AppraiseContent> appraiseContentList = appraiseItem
						.getAppraiseContents();
				for (int contentNum = 0; contentNum < appraiseContentList
						.size(); contentNum++) {
					AppraiseContent appraiseContent = appraiseContentList
							.get(contentNum);
					//���������������Ϣ
					html.append(exportSecondColumnInfo(contentNum, appraiseContent));
					
					//������塢�����ߡ�������Ϣ
					html.append(exportThirdFourInfo(appraiseContent));
				}
			}
			html.append("<tr class=\""+trClass+"\"><td>�ϼ�</td><td>"+appraiseTable.getAllWeight().get("tableWeight")+"</td><td></td><td>"+appraiseTable.getAllWeight().get("itemWeight")+"</td><td></td><td>"+appraiseTable.getAllWeight().get("contentWeight")+"</td><td></td><td></td></tr>");
			html.append("</table>");
		return html.toString();
	}

	/**
	 * ��������С������е��¿���������Ϣ
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
	 * ��������һ��,�ڶ��е��¿�����Ŀ��Ϣ
	 * @param appraiseItem �¿�����Ŀ����
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
	 * �����¿��˵�ͷ����̬��Ϣ
	 */
	private String exportTableHeader() {
		StringBuffer html = new StringBuffer("");
		html.append("<tr class=\"");
		html.append(trClass);
		html.append("\">");
		html.append("<td style=\"font-weight: bold;text-align: center;\">��Ŀ</td>");
		html.append("<td style=\"font-weight: bold;text-align: center;\">Ȩ�ط�</td>");
		html.append("<td style=\"font-weight: bold;text-align: center;\">����</td>");
		html.append("<td style=\"font-weight: bold;text-align: center;\">Ȩ��</td>");
		html.append("<td style=\"font-weight: bold;text-align: center;\">ϸ��</td>");
		html.append("<td style=\"font-weight: bold;text-align: center;\">����</td>");
		html.append("<td style=\"font-weight: bold;text-align: center;\">�÷�</td>");
		html.append("<td style=\"font-weight: bold;text-align: center;\">��ע</td>");
		html.append("</tr>");
		return html.toString();
	}




	/**
	 * �����������HTML�������С������С������к͵ڰ�����Ϣ
	 * 
	 * @param appraiseContent
	 * @return
	 */
	private String exportThirdFourInfo(AppraiseContent appraiseContent) {
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