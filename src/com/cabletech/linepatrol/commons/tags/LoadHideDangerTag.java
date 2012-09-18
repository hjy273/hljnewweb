package com.cabletech.linepatrol.commons.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.beanutils.DynaBean;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cabletech.linepatrol.commons.dao.HideDangerDAO;

/**
 * 载入选择隐患信息的标签
 * 
 * @author Administrator
 * 
 */
public class LoadHideDangerTag extends BodyTagSupport {

	// 显示占位符的HTML页面id
	private String spanId = "accidentSpan";
	// 输入域的名称
	private String inputName;
	// 输入域表格的所占表格列数
	private String colSpan = "3";
	// 带入的隐患数值
	private String accidents;
	private ApplicationContext applicationContext;

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

	public String getAccidents() {
		return accidents;
	}

	public void setAccidents(String accidents) {
		this.accidents = accidents;
	}

	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		applicationContext = WebApplicationContextUtils
		.getWebApplicationContext(super.pageContext.getServletContext());
		HideDangerDAO dao = (HideDangerDAO) applicationContext
		.getBean("hideDangerDAO");
		String accidentName = "";
		if (accidents != null) {
			String[] accident = accidents.split(",");
			List list;
			DynaBean bean;
			for (int i = 0; accident != null && i < accident.length; i++) {
				list = dao.queryList(" and id='" + accident[i] + "'");
				if(list !=null && list.size()>0){
					bean = (DynaBean) list.get(0);
					accidentName = accidentName + (String) bean.get("name");
					if (i != accident.length - 1) {
						accidentName = accidentName + ",";
					}
				}
			}
		}
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
				+ "/linepatrol/js/select_hide_dangers.js\">");
		buf.append("</script>");
		// buf.append("<script type=\"text/javascript\">var jQuery=$;</script>");
		buf.append("<link rel=\"stylesheet\" ");
		buf.append(" href=\"" + contextPath
				+ "/css/jwindow.css\" media=\"all\" />");
		buf.append("<tr class=trcolor>");
		buf.append("<td style=\"text-align:right;\" class=\"tdr\">");
		buf.append("隐患信息");
		buf.append("：</td>");
		buf.append("<td style=\"text-align:left;\" colspan=\"" + colSpan
				+ "\" class=\"tdl\">");
		buf.append("<span id=\"");
		buf.append(spanId);
		buf.append("\">");
		buf.append(accidentName);
		buf.append("</span>");
		buf.append("<input name=\"");
		buf.append(inputName);
		buf.append("\" type=\"hidden\" id=\"");
		buf.append(inputName);
		buf.append("\" />");
		buf.append("<input name=\"btnAdd1\" type=\"button\" ");
		buf.append("id=\"btnAdd1\" value=\"添加隐患信息");
		buf.append("\" onclick=\"showHideDangerDlg('");
		buf.append(inputName);
		buf.append("','");
		buf.append(spanId);
		buf.append("','");
		buf.append(accidents);
		buf.append("');\" /></td>");
		buf.append("</tr>");

		buf.append("<tr><td colspan='");
		buf.append((Integer.parseInt(colSpan) + 1) + "'>");
		buf.append("<div class=\"window\"");
		buf.append(" style=\"bgcolor:#FF0000;\"");
		buf.append(" id=\"panelWindow" + spanId + "\">");
		// buf.append("  <div class=\"title\">选择隐患信息");
		// buf.append("    <span class=\"buttons\">");
		// buf.append("      <span class=\"min\"></span>");
		// buf.append("      <span id=\"hideDlg\" class=\"close\"></span>");
		// buf.append("    </span>");
		// buf.append("  </div>");
		buf.append("  <table border='0' cellpadding='0'");
		buf.append(" cellspacing='0' width='100%'>");
		buf.append("    <tr><td ");
		buf.append(" style=\"background-color:#A2D0FF;text-align:left;\">");
		buf.append("      选择隐患信息");
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
