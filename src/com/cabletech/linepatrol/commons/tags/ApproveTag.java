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
	// 标签输入属性
	// 标签值所占列数
	private String colSpan = "3";
	// 标签名称的样式说明
	private String labelStyle = "";
	// 标签值的样式说明
	private String valueStyle = "";
	// 标签文本域的样式说明
	private String areaStyle = "";
	// 标签名称的样式定义
	private String labelClass = "tdr";
	// 标签值的样式定义
	private String valueClass = "tdl";
	// 标签文本域的样式定义
	private String areaClass = "";
	// 标签的显示方式
	private String displayType = INPUT_DISPLAY_TYPE;
	// 审核的对象类型
	private String objectType;
	// 审核信息的关联对象编号
	private String objectId;
	// 审核信息表格的HTML样式说明
	private String tbStyle = "";
	// 审核信息表格的HTML样式定义
	private String tbClass = "";
	// 表格table的样式
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
				html.append("审核次数");
				html.append("</td>");
				html.append("<td style=\"text-align:center;\">");
				html.append("审核人");
				html.append("</td>");
				html.append("<td style=\"text-align:center;\">");
				html.append("审核时间");
				html.append("</td>");
				html.append("<td style=\"text-align:center;\">");
				html.append("审核结果");
				html.append("</td>");
				html.append("<td style=\"text-align:center;\">");
				html.append("审核意见");
				html.append("</td>");
				html.append("</tr>");
				DynaBean bean;
				String approveResult;
				for (int i = 0; i < approveList.size(); i++) {
					bean = (DynaBean) approveList.get(i);
					approveResult = (String) bean.get("approve_result");
					html.append("<tr>");
					html.append("<td style=\"text-align:center;\">");
					html.append("第" + (i + 1) + "次审核");
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
						html.append("<font color=\"blue\">审批通过</font>");
					}
					if (CommonConstant.APPROVE_RESULT_NO.equals(approveResult)) {
						html.append("<font color=\"red\">审批不通过</font>");
					}
					if (CommonConstant.APPROVE_RESULT_TRANSFER
							.equals(approveResult)) {
						html.append("<font color=\"black\">转审</font>");
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
			html.append("审核结果：");
			html.append("</td>");
			html.append("<td colspan=\"" + colSpan + "\" ");
			html.append(" style=\"" + valueStyle + "\"");
			html.append(" class=\"" + valueClass + "\">");
			html.append("<input type=\"radio\" name=\"approveResult\" ");
			html.append(" value=\"1\" checked />");
			html.append("通过");
			html.append("<input type=\"radio\" name=\"approveResult\" ");
			html.append(" value=\"0\" />");
			html.append("不通过");
			html.append("</td>");
			html.append("</tr>");
			html.append("<tr class=trcolor>");
			html.append("<td style=\"" + labelStyle + "\"");
			html.append(" class=\"" + labelClass + "\">");
			html.append("审核意见：");
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
