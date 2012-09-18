package com.cabletech.linepatrol.commons.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.beanutils.DynaBean;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.commons.dao.ApproveDAO;

public class ApproveTag extends BodyTagSupport {
	public String INPUT_DISPLAY_TYPE = "input";
	public String VIEW_DISPLAY_TYPE = "view";
	private static final long serialVersionUID = 8518374478605199466L;
	private ApplicationContext applicationContext;
	private List approveList;
	// ��ǩ��������
	// ��ǩֵ��ռ����
	private String colSpan = "3";
	// ��ǩ���Ƶ���ʽ˵��
	private String labelStyle = "";
	// ��ǩֵ����ʽ˵��
	private String valueStyle = "";
	// ��ǩ�ı������ʽ˵��
	private String areaStyle = "";
	// ��ǩ���Ƶ���ʽ����
	private String labelClass = "tdr";
	// ��ǩֵ����ʽ����
	private String valueClass = "tdl";
	// ��ǩ�ı������ʽ����
	private String areaClass = "";
	// ��ǩ����ʾ��ʽ
	private String displayType = INPUT_DISPLAY_TYPE;
	// ��˵Ķ�������
	private String objectType;
	// �����Ϣ�Ĺ���������
	private String objectId;
	// �����Ϣ����HTML��ʽ˵��
	private String tbStyle = "";
	// �����Ϣ����HTML��ʽ����
	private String tbClass = "";
	// ���table����ʽ
	private String tableClass = "";

	public int doEndTag() throws JspException {
		StringBuilder html = new StringBuilder();
		if (VIEW_DISPLAY_TYPE.equals(displayType)) {
			init();
			html.append("<td colspan=\"" + colSpan
					+ "\" style=\"padding:10px;\">");
			if (approveList != null && !approveList.isEmpty()) {
				html.append("<table border=\"1\" cellpadding=\"0\" ");
				html.append(" cellspacing=\"0\" width=\"100%\" ");
				html.append(" class=\"" + tableClass + "\" ");
				html.append(" style=\"border-collapse: collapse;\">");
				html.append("<tr>");
				html.append("<td style=\"text-align:center;\">");
				html.append("��˴���");
				html.append("</td>");
				html.append("<td style=\"text-align:center;\">");
				html.append("�����");
				html.append("</td>");
				html.append("<td style=\"text-align:center;\">");
				html.append("���ʱ��");
				html.append("</td>");
				html.append("<td style=\"text-align:center;\">");
				html.append("��˽��");
				html.append("</td>");
				html.append("<td style=\"text-align:center;\">");
				html.append("������");
				html.append("</td>");
				html.append("</tr>");
				DynaBean bean;
				String approveResult;
				for (int i = 0; i < approveList.size(); i++) {
					bean = (DynaBean) approveList.get(i);
					approveResult = (String) bean.get("approve_result");
					html.append("<tr>");
					html.append("<td style=\"text-align:center;\">");
					html.append("��" + (i + 1) + "�����");
					html.append("</td>");
					html.append("<td style=\"text-align:center;\">");
					html.append(bean.get("username"));
					html.append("</td>");
					html.append("<td style=\"text-align:center;\">");
					html.append(bean.get("approve_time"));
					html.append("</td>");
					html.append("<td style=\"text-align:center;\">");
					if (CommonConstant.APPROVE_RESULT_PASS
							.equals(approveResult)) {
						html.append("<font color=\"blue\">����ͨ��</font>");
					}
					if (CommonConstant.APPROVE_RESULT_NO.equals(approveResult)) {
						html.append("<font color=\"red\">������ͨ��</font>");
					}
					if (CommonConstant.APPROVE_RESULT_TRANSFER
							.equals(approveResult)) {
						html.append("<font color=\"black\">ת��</font>");
					}
					html.append("</td>");
					html.append("<td style=\"text-align:center;\">");
					if (bean.get("approve_remark") != null
							&& !bean.get("approve_remark").equals("")) {
						html.append(bean.get("approve_remark"));
					} else {
						html.append("");
					}
					html.append("</td>");
					html.append("</tr>");
				}
				html.append("</table>");
			}
			html.append("</td>");
		} else {
			// html.append("<tr class=trcolor>");
			html.append("<td style=\"" + labelStyle + "\"");
			html.append(" class=\"" + labelClass + "\">");
			html.append("��˽����");
			html.append("</td>");
			html.append("<td colspan=\"" + colSpan + "\" ");
			html.append(" style=\"" + valueStyle + "\"");
			html.append(" class=\"" + valueClass + "\">");
			html.append("<input type=\"radio\" name=\"approveResult\" ");
			html.append(" value=\"1\" checked />");
			html.append("ͨ��");
			html.append("<input type=\"radio\" name=\"approveResult\" ");
			html.append(" value=\"0\" />");
			html.append("��ͨ��");
			html.append("</td>");
			html.append("</tr>");
			html.append("<tr class=trcolor>");
			html.append("<td style=\"" + labelStyle + "\"");
			html.append(" class=\"" + labelClass + "\">");
			html.append("��������");
			html.append("</td>");
			html.append("<td colspan=\"" + colSpan + "\" ");
			html.append(" style=\"" + valueStyle + "\"");
			html.append(" class=\"" + valueClass + "\">");
			html.append("<textarea name=\"approveRemark\" rows=\"6\" ");
			html.append(" style=\"" + areaStyle + "\"");
			html.append(" class=\"" + areaClass + "\"></textarea>");
			html.append("</td>");
			// html.append("</tr>");
		}
		try {
			super.pageContext.getOut().print(html.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.EVAL_PAGE;
	}

	public void init() {
		applicationContext = WebApplicationContextUtils
				.getWebApplicationContext(super.pageContext.getServletContext());
		String condition = " and approve.object_id='";
		condition = condition + objectId + "' ";
		condition = condition + " and approve.object_type='";
		condition = condition + objectType + "' ";
		ApproveDAO dao = (ApproveDAO) applicationContext.getBean("approveDAO");
		approveList = dao.queryList(condition);
	}

	public String getTbStyle() {
		return tbStyle;
	}

	public void setTbStyle(String tbStyle) {
		this.tbStyle = tbStyle;
	}

	public String getTbClass() {
		return tbClass;
	}

	public void setTbClass(String tbClass) {
		this.tbClass = tbClass;
	}

	public String getLabelClass() {
		return labelClass;
	}

	public void setLabelClass(String labelClass) {
		this.labelClass = labelClass;
	}

	public String getValueClass() {
		return valueClass;
	}

	public void setValueClass(String valueClass) {
		this.valueClass = valueClass;
	}

	public String getAreaClass() {
		return areaClass;
	}

	public void setAreaClass(String areaClass) {
		this.areaClass = areaClass;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	public String getColSpan() {
		return colSpan;
	}

	public void setColSpan(String colSpan) {
		this.colSpan = colSpan;
	}

	public String getLabelStyle() {
		return labelStyle;
	}

	public void setLabelStyle(String labelStyle) {
		this.labelStyle = labelStyle;
	}

	public String getValueStyle() {
		return valueStyle;
	}

	public void setValueStyle(String valueStyle) {
		this.valueStyle = valueStyle;
	}

	public String getAreaStyle() {
		return areaStyle;
	}

	public void setAreaStyle(String areaStyle) {
		this.areaStyle = areaStyle;
	}

	public String getTableClass() {
		return tableClass;
	}

	public void setTableClass(String tableClass) {
		this.tableClass = tableClass;
	}

}
