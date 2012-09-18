<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="com.cabletech.menu.domainobjects.*" %>
<%@ page import="java.util.*"%>
<%@ page import="com.cabletech.sysmanage.domainobjects.*" %>
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
<title>移动传输线路巡检管理系统</title>
<link href="../css/menu_style.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	border-right: #3E4245 1px solid;
	border-left: #3E4245 1px solid;
	border-top: #2A58A6 1px solid;
}
-->
</style>
</head>

<script language="JavaScript" type="text/JavaScript">
<!--
document.oncontextmenu = function() { return false;}
function showuserinfo()
  { usershow.style.display = 'none';
    truserinfo.style.display = 'block';
}
//-->
</script>
<%
	Vector firstMenu=(Vector)session.getAttribute("MENU_FIRSTMENU");
	String fistId = "";
%>
<script type="">
function secMenu(n)
  { for( i = 1;i<=<%=firstMenu.size()%>; i++){
     var obj="menu"+ i;
     eval(obj+".className = 'menu'");
      }
    var sname="menu"+ n;
    eval(sname+".className = 'menuon'");
  }
</script>

<body bgcolor="#ffffff">
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td valign="top" background="../images/bg_menu_l.gif"><table width="98%" height="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="6"><img src="../images/1px.gif" width="1" height="1"></td>
        <td height="6"><img src="../images/1px.gif" width="1" height="1"></td>
      </tr>
      <tr>
        <td width="60" align="center" valign="top">
		<table width="100%"  border="0" cellspacing="0" cellpadding="3">
            <%
            for(int i=0;i<firstMenu.size();i++){
               FirstMenu menu=(FirstMenu)firstMenu.get(i);
			   if(i == 0){
				   fistId = menu.getId();
			   }

            %>
			<tr>
            <td align="center" class="menu" onClick="secMenu(<%=i+1%>)" id="menu<%=i+1%>">
                <a href="./submenu_tree.jsp?id=<%=menu.getId()%>&name=<%=menu.getLablename()%>" target="treemenu">
                <img src="<%=menu.getImgurl()%>" width="27" height="26" border="0" alt=""><br>
              <%=menu.getLablename()%>
              </a>
            </td>
            </tr>
            <%}%>
        </table></td>
        <td valign="top">
		<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td><DIV id=iframemenu><img src="../images/1px.gif"><br>
<%
if(fistId.equals("1")){
%>
<IFRAME name=treemenu border=0 marginWidth=0 marginHeight=0 src="${ctx}/realtime/realtime_tree.jsp" frameBorder=0 width="100%" scrolling=NO height="100%"></IFRAME>
<%
}else if(fistId.equals("6")){
%>
<IFRAME name=treemenu border=0 marginWidth=0 marginHeight=0 src="${ctx}/TroubleManage/troubleMgr_tree.jsp" frameBorder=0 width="100%" scrolling=NO height="100%"></IFRAME>
<%
}else{
%>
<IFRAME name=treemenu border=0 marginWidth=0 marginHeight=0 src="${ctx}/frames/submenu_tree.jsp" frameBorder=0 width="100%" scrolling=NO height="100%"></IFRAME>
<%
}
%>
</DIV></td>
  </tr>
  <tr id="truserinfo" style="display:block;">
    <td height="120"><DIV id=iframeuserinfo><img src="../images/1px.gif"><br>
<IFRAME name=userinfo border=0 marginWidth=0 marginHeight=0 src="userinfo.jsp" frameBorder=0 width="100%" scrolling=NO height="120">
</IFRAME>
</DIV></td>
  </tr>
  <tr id="usershow" style="display:none;">
    <td height="20" align="right"><img src="../images/icon_show_uinfo.gif" alt="显示用户信息" width="15" height="17" style="cursor:hand; " onClick="showuserinfo();"></td>
  </tr>
</table>


		</td>
      </tr>
    </table></td>
    <td width="10" valign="top"><table width="10" height="100%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10" height="9"><img src="../images/pic_menu_right_tr.gif" width="10" height="9"></td>
      </tr>
      <tr>
        <td background="../images/bg_menu_right.gif">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
<%
String display = (String) session.getAttribute("display");
if(display != null && !display.equals("")){
  String enddate = (String)session.getAttribute("enddate");
%>
<script language="JavaScript" type="text/JavaScript">
alert("您的密码 <%=enddate%>  到期,请更改账号密码!!");
</script>
<%
display = "";
}
%>
