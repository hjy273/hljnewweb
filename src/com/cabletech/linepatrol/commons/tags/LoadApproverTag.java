package com.cabletech.linepatrol.commons.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.beanutils.DynaBean;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.util.StringUtils;
import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.UserInfoDAOImpl;
import com.cabletech.linepatrol.commons.module.ReplyApprover;

/**
 * 载入选择审核人或者抄送人的标签
 * 
 * @author Administrator
 * 
 */
public class LoadApproverTag extends BodyTagSupport {
	// 审核人
	public static final String APPROVE_MAN = "approver";
	// 转审人
	public static final String APPROVE_TRANSFER_MAN = "transfer";
	// 抄送人
	public static final String APPROVE_READ = "reader";
	// 输入域的标签名称
	private String label;
	// 显示占位符的HTML页面id
	private String spanId = "approverSpan";
	// 输入域的名称
	private String inputName;
	// 输入域表格的所占表格列数
	private String colSpan = "3";
	// 输入域表格的输入域模式
	private String inputType = "checkbox";
	// 不允许出现值的输入域名称
	private String notAllowName = "approverSpan";
	// 已经选择的默认值
	private String existValue = "";
	// 显示审核人员的类型
	private String displayType = "mobile";
	// 是否排除当前用户自身
	private String exceptSelf = "true";
	// 限定审核人员的部门编号
	private String departId = "";
	// 默认审核人的对应实体编号
	private String objectId = "";
	// 默认审核人的对应实体所属模块类型
	private String objectType = "";
	// 审核人类型（approver表示审核人，transfer表示转审人，reader表示抄送人）
	private String approverType = "approver";
	private WebApplicationContext applicationContext;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

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

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getNotAllowName() {
		return notAllowName;
	}

	public void setNotAllowName(String notAllowName) {
		this.notAllowName = notAllowName;
	}

	public String getExistValue() {
		return existValue;
	}

	public void setExistValue(String existValue) {
		this.existValue = existValue;
	}

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public String getExceptSelf() {
		return exceptSelf;
	}

	public void setExceptSelf(String exceptSelf) {
		this.exceptSelf = exceptSelf;
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

	public String getApproverType() {
		return approverType;
	}

	public void setApproverType(String approverType) {
		this.approverType = approverType;
	}

	public void getDefaultApprover() {
		if (APPROVE_MAN.equals(approverType)) {
			approverType = CommonConstant.APPROVE_MAN;
		}
		if (APPROVE_TRANSFER_MAN.equals(approverType)) {
			approverType = CommonConstant.APPROVE_TRANSFER_MAN;
		}
		if (APPROVE_READ.equals(approverType)) {
			approverType = CommonConstant.APPROVE_READ;
		}
		applicationContext = WebApplicationContextUtils
				.getWebApplicationContext(super.pageContext.getServletContext());
		UserInfo userInfo = (UserInfo) super.pageContext.getSession()
				.getAttribute("LOGIN_USER");
		ReplyApproverDAO approverDao = (ReplyApproverDAO) applicationContext
				.getBean("replyApproverDAO");
		String condition = "";
		condition += " and object_id='" + objectId + "' ";
		condition += " and object_type='" + objectType + "' ";
		condition += " and approver_type='" + approverType + "' ";
		List tmpList = approverDao.queryList(condition);
		String approverIds = "";
		String approverNames = "";
		String approverTels = "";
		DynaBean bean;
		boolean flag = false;
		if (CommonConstant.APPROVE_TRANSFER_MAN.equals(approverType)) {
			for (int i = 0; tmpList != null && i < tmpList.size(); i++) {
				bean = (DynaBean) tmpList.get(i);
				if (bean != null) {
					if (!userInfo.getUserID().equals(bean.get("approver_id"))) {
						approverIds += (String) bean.get("approver_id");
						approverNames += (String) bean.get("username");
						approverTels += (String) bean.get("phone");
						flag = true;
						break;
					}
				}
			}
		} else {
			List list = new ArrayList();
			for (int i = 0; tmpList != null && i < tmpList.size(); i++) {
				bean = (DynaBean) tmpList.get(i);
				if (bean != null) {
					if (list.contains(bean.get("approver_id"))) {
						continue;
					}
					approverIds += (String) bean.get("approver_id");
					approverNames += (String) bean.get("username");
					approverTels += (String) bean.get("phone");
					if (i != tmpList.size() - 1) {
						approverIds += ",";
						approverNames += ",";
						approverTels += ",";
					}
					flag = true;
					list.add(bean.get("approver_id"));
				}
			}
		}
		if (tmpList != null && !tmpList.isEmpty()) {
			if (flag) {
				if (existValue == null || "".equals(existValue)) {
					existValue = approverIds + "--" + approverNames + "--"
							+ approverTels;
				}
			}
		}
	}

	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		getDefaultApprover();
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
				+ "/linepatrol/js/select_approvers.js\">");
		buf.append("</script>");
		// buf.append("<script type=\"text/javascript\">var jQuery=$;</script>");
		buf.append("<link rel=\"stylesheet\" ");
		buf.append(" href=\"" + contextPath
				+ "/css/jwindow.css\" media=\"all\" />");
		// buf.append("<tr class=trcolor>");
		buf.append("<td class=\"tdr\" style=\"text-align:right;\">");
		buf.append(label);
		buf.append("：</td>");
		buf.append("<td class=\"tdl\" style=\"text-align:left;\" colspan=\""
				+ colSpan + "\">");
		buf.append("<span id=\"");
		buf.append(spanId);
		buf.append("\">");
		if (existValue != null && existValue.indexOf("--") != -1) {
			buf.append(existValue.split("--")[1]);
		}
		buf.append("</span>");

		// buf.append("<input name=\"");
		// buf.append(inputName);
		// buf.append("\" type=\"hidden\" id=\"");
		// buf.append(inputName);
		// buf.append("\" />");

		String existName = "";
		if (inputName != null) {
			List<String> list = StringUtils.string2List(inputName, ",");
			String name = "";
			for (int i = 0; list != null && i < list.size(); i++) {
				name = list.get(i);
				buf.append("<input name=\"");
				buf.append(name);
				buf.append("\" type=\"hidden\" id=\"");
				buf.append(name);
				buf.append("\"");
				if (i == 0) {
					existName = list.get(0);
					if (existValue != null && existValue.indexOf("--") != -1) {
						buf.append(" value=\"");
						buf.append(existValue.split("--")[0]);
						buf.append("\" ");
					}
				}
				if (i == 1) {
					// existName = list.get(1);
					if (existValue != null && existValue.indexOf("--") != -1) {
						if (existValue.split("--").length >= 3) {
							buf.append(" value=\"");
							buf.append(existValue.split("--")[2]);
							buf.append("\" ");
						}
					}
				}
				buf.append(" />");
			}
		}
		buf.append("<input name=\"btnAdd1\" type=\"button\" ");
		buf.append("id=\"btnAdd1\" class=\"lbutton\" value=\"添加" );
		buf.append(label);
		buf.append("\" onclick=\"showApproverDlg('");
		buf.append(inputName);
		buf.append("','");
		buf.append(spanId);
		buf.append("','");
		buf.append(existName);
		buf.append("','");
		buf.append(inputType);
		buf.append("','");
		buf.append(notAllowName);
		buf.append("','");
		buf.append(displayType);
		buf.append("','");
		buf.append(departId);
		buf.append("','");
		buf.append(exceptSelf);
		buf.append("');\" /></td>");
		// buf.append("</tr>");

		buf.append("<tr><td colspan='");
		buf.append((Integer.parseInt(colSpan) + 1) + "'>");
		buf.append("<div class=\"window\"");
		buf.append(" style=\"bgcolor:#FF0000;\"");
		buf.append(" id=\"panelWindow" + spanId + "\">");
		// buf.append("  <div class=\"title\">选择");
		// buf.append(label);
		// buf.append("    <span class=\"buttons\">");
		// buf.append("      <span class=\"min\"></span>");
		// buf.append("      <span id=\"hideDlg\" class=\"close\"></span>");
		// buf.append("    </span>");
		// buf.append("  </div>");
		buf.append("  <table border='0' cellpadding='0'");
		buf.append(" cellspacing='0' width='100%'>");
		buf
				.append("    <tr><td style=\"background-color:#A2D0FF;text-align:left;\">");
		buf.append("      选择" + label);
		buf
				.append("    </td><td style='background-color:#A2D0FF;text-align:right;'>");
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
		buf.append("</td>");
		buf.append("</tr>");

		try {
			super.pageContext.getOut().print(buf.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.EVAL_PAGE;
	}

}
