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
	��ǰ�û�����:<%=userinfo.getUserName()%><br>
<br>
	��ǰ�û�ID��<%=userinfo.getUserID()%><br>
<br>
	��ǰ�û��Ĳ��ţ�<%=userinfo.getDeptype()%><br>
<br>
	��ǰ�û���ӵ�е�ģ�飺<%=strPower%><br>
<br>
	��ǰ�û�SessionID��<%=session.getId()%><br>
<br>

<input type="button" value="��ʾ��ҳ" onclick="showallOnClick()"/>
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
