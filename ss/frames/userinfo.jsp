<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ page import="com.cabletech.baseinfo.domainobjects.*" %>
<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
<body text="#000000">
<script language="JavaScript" type="text/JavaScript">
<!--
document.oncontextmenu = function() { return false;}
function hiduserinfo()
  { parent.usershow.style.display = 'block';
    parent.truserinfo.style.display = 'none';
}
//-->
</script>

<%

UserInfo userinfo=(UserInfo)session.getAttribute("LOGIN_USER");
String regionName=(String)session.getAttribute("LOGIN_USER_REGION_NAME");
String deptName = (String)session.getAttribute("LOGIN_USER_DEPT_NAME");
%>
<body bgcolor="#F7F3F7">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="5"><img src="${ctx}/images/1px.gif" width="1" height="1"></td>
  </tr>
  <tr>
    <td>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" id=control>
        <tr>
          <td height="24" align="center" background="${ctx}/images/bg_menu_title.gif" style="border:#C2C2C2 solid;border-width:1 1 1 1;">用户信息</td>
          <td width="25" align="center" background="${ctx}/images/bg_menu_title_r.gif"><img src="../images/icon_arrow_down.gif" alt="隐藏用户信息" width="14" height="16" border="0" style="cursor:hand; " onClick="hiduserinfo();"></td>
        </tr>
    </table></td>
  </tr>
  <tr>
    <td height="65" align="center" bgcolor="#FFFFFF" style="border:#C2C2C2 solid;border-width:0 1 0 1; "><table width="100%"  border="0" cellspacing="3" cellpadding="0">
        <tr>
          <td width="25" align="center"><img src="${ctx}/images/icon_user.gif" width="20" height="21"></td>
          <td>用户：<%=userinfo.getUserName()%></td>
        </tr>
        <tr>
          <td align="center"><img src="${ctx}/images/icon_corp.gif" width="20" height="21"></td>
          <td>单位：<%=deptName%></td>
        </tr>
        <tr>
          <td width="25" align="center"><img src="${ctx}/images/icon_time.gif" width="21" height="20"></td>
          <td>区域：<%=regionName%></td>
        </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="6" height="5"><img src="${ctx}/images/pic_userinfo_jiao_bl.gif" width="6" height="5"></td>
          <td height="5" background="${ctx}/images/bg_userinfo_bot.gif"><img src="../images/1px.gif" width="1" height="1"></td>
          <td width="6" height="5"><img src="${ctx}/images/pic_userinfo_jiao_br.gif" width="6" height="5"></td>
        </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
