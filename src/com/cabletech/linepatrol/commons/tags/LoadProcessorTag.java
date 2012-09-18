package com.cabletech.linepatrol.commons.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * 载入选择代维公司信息的标签
 * 
 * @author Administrator
 * 
 */
public class LoadProcessorTag extends BodyTagSupport {

	// 显示占位符的HTML页面id
	private String spanId = "processorSpan";
	// 输入域的名称
	private String inputName;
	// 输入域表格的所占表格列数
	private String colSpan = "3";
	// 受理部门和受理人信息的默认选择值
	private String existValue = "";
	// 输入域表格的输入域模式
	private String inputType = "checkbox";
	// 显示受理人员的类型
	private String displayType = "contractor";
	// 显示选择过的人员姓名
	private String spanValue = "";
	// 是否排除当前用户自身
	private String exceptSelf = "true";
	// 标签名称
	private String labelName = "受理人";

	public String getSpanId() {
		return spanId;
	}

	public void setSpanId(String spanId) {
		this.spanId = spanId;
	}

	public String getInputName() {
		return inputName;
	}

	public void setInputName(String inputName) {
		this.inputName = inputName;
	}

	public String getColSpan() {
		return colSpan;
	}

	public void setColSpan(String colSpan) {
		this.colSpan = colSpan;
	}

	public String getExistValue() {
		return existValue;
	}

	public void setExistValue(String existValue) {
		this.existValue = existValue;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getExceptSelf() {
		return exceptSelf;
	}

	public void setExceptSelf(String exceptSelf) {
		this.exceptSelf = exceptSelf;
	}

	public String getSpanValue() {
		return spanValue;
	}

	public void setSpanValue(String spanValue) {
		this.spanValue = spanValue;
	}

	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		StringBuffer buf = new StringBuffer();
		String contextPath = super.pageContext.getServletContext()
				.getContextPath();
		// buf.append("<script type=\"text/javascript\" ");
		// buf.append(" src=\"" + contextPath + "/js/jquery/jquery.js\">");
		// buf.append("</script>");
		buf.append("<script type=\"text/javascript\" ");
		buf.append(" src=\"" + contextPath + "/js/jquery/jquery.jwindow.js\">");
		buf.append("</script>");
		buf.append("<script type=\"text/javascript\" ");
		buf.append(" src=\"" + contextPath
				+ "/js/jquery/jquery.interface.js\">");
		buf.append("</script>");
		buf.append("<script type=\"text/javascript\" ");
		buf.append(" src=\"" + contextPath
				+ "/linepatrol/js/select_processors.js\">");
		buf.append("</script>");
		// buf.append("<script type=\"text/javascript\">var jQuery=$;</script>");
		buf.append("<link rel=\"stylesheet\" ");
		buf.append(" href=\"" + contextPath
				+ "/css/jwindow.css\" media=\"all\" />");
		buf.append("<tr class=trcolor>");
		buf.append("<td style=\"text-align:right;\" class=\"tdr\">");
		buf.append(labelName);
		buf.append("：</td>");
		buf.append("<td style=\"text-align:left;\" colspan=\"" + colSpan
				+ "\" class=\"tdl\">");
		buf.append("<span id=\"");
		buf.append(spanId);
		buf.append("\">");
		buf.append(spanValue);
		buf.append("</span>");
		String existName = "";
		if (inputName != null && inputName.indexOf(",") == -1) {
			buf.append("<input name=\"");
			buf.append(inputName);
			buf.append("\" type=\"hidden\" id=\"");
			buf.append(inputName);
			buf.append("\" />");
			existName = inputName;
		}
		if (inputName != null && inputName.indexOf(",") != -1) {
			String[] inputNames = inputName.split(",");
			for (int i = 0; inputNames != null && i < inputNames.length; i++) {
				buf.append("<input name=\"");
				buf.append(inputNames[i]);
				buf.append("\" type=\"hidden\" id=\"");
				buf.append(inputNames[i]);
				buf.append("\" ");
				if (i == 1) {
					existName = inputNames[1];
					if (existValue != null && existValue.indexOf("--") != -1) {
						if (existValue.split("--").length >= 1) {
							buf.append(" value=\"" + existValue.split("--")[0]
									+ "\" ");
						}
					} else if (existValue != null) {
						buf.append(" value=\"" + existValue + "\" ");
					}
				}
				if (i == 2) {
					if (existValue != null && existValue.indexOf("--") != -1) {
						if (existValue.split("--").length >= 2) {
							buf.append(" value=\"" + existValue.split("--")[1]
									+ "\" ");
						}
					}
				}
				buf.append(" />");
			}
		}
		buf.append("<input name=\"btnAdd1\" type=\"button\" ");
		buf.append("id=\"btnAdd1\" value=\"添加");
		buf.append(labelName);
		buf.append("\" onclick=\"showProcessorDlg('");
		buf.append(inputName);
		buf.append("','");
		buf.append(spanId);
		buf.append("','");
		buf.append(existName);
		buf.append("','");
		buf.append(inputType);
		buf.append("','");
		buf.append(displayType);
		buf.append("','");
		buf.append(exceptSelf);
		buf.append("','");
		buf.append(labelName);
		buf.append("');\" /></td>");
		buf.append("</tr>");

		buf.append("<tr><td colspan='");
		buf.append((Integer.parseInt(colSpan) + 1) + "'>");
		buf.append("<div class=\"window\"");
		buf.append(" style=\"bgcolor:#FF0000;\"");
		buf.append(" id=\"panelWindow" + spanId + "\">");//
		// buf.append("  <div class=\"title\">选择代维公司");
		// buf.append("    <span class=\"buttons\">");
		// buf.append("      <span class=\"min\"></span>");
		// buf.append("      <span id=\"hideDlg\" class=\"close\"></span>");
		// buf.append("    </span>");
		// buf.append("  </div>");
		buf.append("  <table border='0' cellpadding='0'");
		buf.append(" cellspacing='0' width='100%'>");
		buf.append("    <tr><td");
		buf.append(" style=\"background-color:#A2D0FF;text-align:left;\">");
		buf.append("      选择部门和");
		buf.append(labelName);
		buf.append("    </td><td");
		buf.append(" style='background-color:#A2D0FF;text-align:right;'>");
		buf.append("      <img src='/WebApp/images/close.jpg'");
		buf.append(" style='cursor:hand;' id='" + spanId + "HideDlg'>");
		buf.append("    </td></tr>");
		buf.append("  </table>");
		buf.append("  <div class=\"content\"");
		buf.append(" style=\"height:400px; width:100%\">");
		buf.append("  <table border='0' cellspacing='0'");
		buf.append(" cellpadding='0' width='98%'>");
		buf.append("  <tr><td>");
		buf.append("  <iframe id=\"" + spanId + "Frame\" frameborder=\"0\"");
		buf.append("   scrolling=\"yes\" width=\"390\" height=\"390\">");
		buf.append("  </iframe>");
		buf.append("  </td></tr>");
		buf.append("  </table>");
		buf.append("  </div>");
		buf.append("  <div class=\"status\">");
		buf.append("    <span class=\"resize\"></span>");
		buf.append("  </div>");
		buf.append("</div>");
		buf.append("</td></tr>");

		try {
			super.pageContext.getOut().print(buf.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.EVAL_PAGE;
	}

}
