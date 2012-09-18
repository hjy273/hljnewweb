package com.cabletech.linepatrol.commons.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.beanutils.DynaBean;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.UserInfoDAOImpl;
import com.cabletech.linepatrol.commons.module.ReplyApprover;

public class TransferApproveTag extends BodyTagSupport {

	private static final long serialVersionUID = 8518374478605199466L;
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
	// 默认审核人的对应实体编号
	private String objectId = "";
	// 默认审核人的对应实体所属模块类型
	private String objectType = "";
	private WebApplicationContext applicationContext;
	private String approverIds;
	private String approverNames;

	public void getDefaultApprover() {
		applicationContext = WebApplicationContextUtils
				.getWebApplicationContext(super.pageContext.getServletContext());
		ReplyApproverDAO approverDao = (ReplyApproverDAO) applicationContext
				.getBean("replyApproverDAO");
		String condition = "";
		condition += " and object_id='" + objectId + "' ";
		condition += " and object_type='" + objectType + "' ";
		condition += " and approver_type='"
				+ CommonConstant.APPROVE_TRANSFER_MAN + "' ";
		List tmpList = approverDao.queryList(condition);
		approverIds = "";
		approverNames = "";
		DynaBean bean;
		for (int i = 0; tmpList != null && i < tmpList.size(); i++) {
			bean = (DynaBean) tmpList.get(i);
			if (bean != null) {
				approverIds += (String) bean.get("approver_id");
				approverNames += (String) bean.get("username");
				if (i != tmpList.size() - 1) {
					approverIds += ",";
					approverNames += ",";
				}
			}
		}
	}

	public int doEndTag() throws JspException {
		getDefaultApprover();
		StringBuilder html = new StringBuilder();
		String contextPath = super.pageContext.getServletContext()
				.getContextPath();
		// html.append("<script type=\"text/javascript\" ");
		// html.append(" src=\"" + contextPath + "/js/jquery/jquery.js\">");
		// html.append("</script>");
		html.append("<script type=\"text/javascript\" ");
		html
				.append(" src=\"" + contextPath
						+ "/js/jquery/jquery.jwindow.js\">");
		html.append("</script>");
		html.append("<script type=\"text/javascript\" ");
		html.append(" src=\"" + contextPath
				+ "/js/jquery/jquery.interface.js\">");
		html.append("</script>");
		html.append("<script type=\"text/javascript\" ");
		html.append(" src=\"" + contextPath
				+ "/linepatrol/js/select_approvers.js\">");
		html.append("</script>");
		// html.append("<script type=\"text/javascript\">var jQuery=$;</script>");
		html.append("<link rel=\"stylesheet\" ");
		html.append(" href=\"" + contextPath
				+ "/css/jwindow.css\" media=\"all\" />");
		html.append("<tr class=trcolor>");
		html.append("<td style=\"" + labelStyle + "\"");
		html.append(" class=\"" + labelClass + "\">");
		html.append("转审人");
		html.append("：</td>");
		html.append("<td style=\"" + valueStyle + "\"");
		html.append(" class=\"" + valueClass + "\"");
		html.append(" colspan=\"" + colSpan + "\">");
		html.append("<span id=\"");
		html.append("approverSpan");
		html.append("\">");
		html.append(approverNames);
		html.append("</span>");

		html.append("<input name=\"");
		html.append("approver");
		html.append("\" type=\"text\" id=\"");
		html.append("approver");
		html.append("\" value=\"");
		html.append(approverIds);
		html.append("\" />");

		html.append("<input name=\"");
		html.append("approverTel");
		html.append("\" type=\"text\" id=\"");
		html.append("approverTel");
		html.append("\" />");

		html.append("<input name=\"btnAdd1\" type=\"button\" ");
		html.append("id=\"btnAdd1\" value=\"添加");
		html.append("转审人");
		html.append("\" onclick=\"showApproverDlg('");
		html.append("approver,approverTel");
		html.append("','");
		html.append("approverSpan");
		html.append("','");
		html.append("approver");
		html.append("');\" /></td>");
		html.append("</tr>");

		html.append("<tr><td colspan='");
		html.append((Integer.parseInt(colSpan) + 1) + "'>");
		html.append("<div class=\"window\" id=\"panelWindowapproverSpan\">");
		// html.append("  <div class=\"title\">选择转审人");
		// html.append("    <span class=\"buttons\">");
		// html.append("      <span class=\"min\"></span>");
		// html.append("      <span id=\"hideDlg\" class=\"close\"></span>");
		// html.append("    </span>");
		// html.append("  </div>");
		html.append("  <table border='0' cellpadding='0'");
		html.append(" cellspacing='0' width='100%'>");
		html
				.append("    <tr><td style=\"background-color:#A2D0FF;text-align:left;\">");
		html.append("      选择转审人");
		html
				.append("    </td><td style='background-color:#A2D0FF;text-align:right;'>");
		html.append("      <img src='/WebApp/images/close.jpg'");
		html.append(" style='cursor:hand;' id='approverSpanHideDlg'>");
		html.append("    </td></tr>");
		html.append("  </table>");
		html.append("  <div class=\"content\" style=\"height:400px;\">");
		html.append("  <iframe id=\"approverSpanFrame\" frameborder=\"0\"");
		html.append("   scrolling=\"yes\" width=\"400\" height=\"375\">");
		html.append("  </iframe>");
		html.append("  </div>");
		html.append("  <div class=\"status\">");
		html.append("    <span class=\"resize\"></span>");
		html.append("  </div>");
		html.append("</div>");
		html.append("</td></tr>");

		html.append("<tr>");
		html.append("<td style=\"" + labelStyle + "\"");
		html.append(" class=\"" + labelClass + "\">");
		html.append("转审说明：");
		html.append("</td>");
		html.append("<td colspan=\"" + colSpan + "\"");
		html.append(" style=\"" + valueStyle + "\"");
		html.append(" class=\"" + valueClass + "\">");
		html.append("<textarea name=\"approveRemark\" rows=\"4\"");
		html.append(" style=\"" + areaStyle + "\"");
		html.append(" class=\"" + areaClass + "\"></textarea>");
		html.append("</td>");
		html.append("</tr>");
		try {
			super.pageContext.getOut().print(html.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.EVAL_PAGE;
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

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

}
