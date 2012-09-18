<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.cabletech.power.*"%>
<%@page import="com.cabletech.baseinfo.domainobjects.UserInfo"%>
<%@taglib uri="cabletechtag" prefix="apptag"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
<body>
<%
  String strPower = "";
  UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
  strPower = (String) session.getAttribute("USERCURRENTPOWER");
%>
<br>
<br>
<br>
<br>
	当前用户名称:<%=userinfo.getUserName()%><br>
<br>
	当前用户ID：<%=userinfo.getUserID()%><br>
<br>
	当前用户的部门：<%=userinfo.getDeptype()%><br>
<br>
	当前用户所拥有的模块：<%=strPower%><br>
<br>
	当前用户SessionID：<%=session.getId()%><br>
<br>

<input type="button" value="显示新页" onclick="showallOnClick()"/>
</body>
<script  language="javascript" type="" >
function showallOnClick()
{
	try
	{
		url = "${ctx}/demo/jsp2.jsp";
		alert(url);
		showModalDialog(url,"","status:false;dialogWidth:670px;dialogHeight:620px;help:no;status:no;scroll=no");
	}
	catch(e)
	{
		alert(e);
	}
}
</script>
