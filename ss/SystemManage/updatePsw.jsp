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
	out.print("�޸ĵ�ǰ�û�����");
	out.print("</a>");
	out.print("<br>");

	if (userid.equals(String.valueOf("00000002"))) {
		String allurl = "../baseinfo/queryUserInfo.jsp";
		out.print("<a href=");
		out.print(allurl);
		out.print(">");
		out.print("�޸������û���Ϣ���߼��û����ã�");
		out.print("</a>");
		out.print("<br>");
	}
    else
    {
		out.print("�޸������û���Ϣ����ǰ�û������ã�");
    }
%>
<br>
<jsp:forward page="<%=userurl%>">
</jsp:forward>
