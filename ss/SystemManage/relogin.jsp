<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ page import="com.cabletech.baseinfo.domainobjects.*"%>

<%
	UserInfo uiBean=(UserInfo)session.getAttribute("LOGIN_USER");
	String userid = uiBean.getUserID();

	String relogin = "${ctx}/login.do?id=" + userid + "&method=relogin";
	out.print("<a href=");
	out.print(relogin);
	out.print(" target=_top>");
	out.print("ÖØĞÂµÇÂ½");
	out.print("</a>");

%>
<br>
