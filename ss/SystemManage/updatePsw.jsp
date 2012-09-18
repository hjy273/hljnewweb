<%@ include file="/common/header.jsp"%>
<%@ page import="com.cabletech.baseinfo.domainobjects.*"%>

<%
	UserInfo uiBean=(UserInfo)session.getAttribute("LOGIN_USER");
	String userid = uiBean.getUserID();
	//String ps = uiBean.getPassword();
    if(uiBean.getPassword() == null){
      response.sendRedirect("${ctx}/globinfo/warninfo.jsp");
    }
    //System.out.println("---"+ps);
	String userurl = "/login.do?id=" + userid + "&method=loadUserpsw";
	out.print("<a href=${ctx}");
	out.print(userurl);
	out.print(">");
	out.print("修改当前用户口令");
	out.print("</a>");
	out.print("<br>");

	if (userid.equals(String.valueOf("00000002"))) {
		String allurl = "../baseinfo/queryUserInfo.jsp";
		out.print("<a href=");
		out.print(allurl);
		out.print(">");
		out.print("修改所有用户信息（高级用户可用）");
		out.print("</a>");
		out.print("<br>");
	}
    else
    {
		out.print("修改所有用户信息（当前用户不可用）");
    }
%>
<br>
<jsp:forward page="<%=userurl%>">
</jsp:forward>
