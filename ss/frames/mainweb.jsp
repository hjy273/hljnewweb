<%@include file="/common/header.jsp"%>

<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<title>...::::欢迎使用移动传输线路巡检管理系统::::...</title>
<link href="css/login_style.css" rel="stylesheet" type="text/css">
</head>
<script language="javascript" src="js/no_right.js" type="text/javascript"></script>
<script language="JavaScript" type="">

function openwin() {
    newwin=window.open ("", "newwindow", "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
    newwin.moveTo(0,0);
    newwin.resizeTo(screen.width,screen.height);
    newwin.focus();
}
function vilaSub(){
    var iobj = document.getElementById("iId");
    var pobj = document.getElementById("pId");

      if(iobj.value ==null || iobj.value ==""){
        alert("请输入登陆用户名!!!");
        iobj.focus();
        return false;
    }
    if(pobj.value ==null || pobj.value ==""){
        alert("请输入登陆口令!!!");
        pobj.focus()
        return false;
    }
    openwin();
}
</script>

<body onload="userInfoBean.userid.focus()">

<table width="100%" height="85%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="234" align="center"><table width="535"  border="0" cellspacing="0" cellpadding="0">
      <tr>
          <logic:present name="version">
              <td height="214" align="right" valign="bottom"  background="${ctx}/images/bg_login_top1.gif"><font color="#1313EDs"   size="2" >版本：<bean:write name="version"/>V2.0&nbsp;&nbsp;&nbsp;&nbsp;</font></td>
          </logic:present>
        <logic:notPresent name="version">
            <td height="214" align="center" valign="bottom" background="${ctx}/images/bg_login_top1.gif">&nbsp;</td>
        </logic:notPresent>
      </tr>
      <tr>
        <td height="123" valign="top" background="${ctx}/images/bg_login_mid.gif">
       <form name="userInfoBean" method="Post" action="${ctx}/login.do?method=login" target = "newwindow" >
         <table width="80%"  border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td width="60" align="center">&nbsp;</td>
            <td>&nbsp;</td>
            <td width="60" align="center">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td width="60" align="center">用户名：</td>
            <td><input name="userid"  id="iId" type="text" class="inputtext" size="20"></td>
            <td width="60" align="center">口 令：</td>
            <td><input name="password" id="pId" type="password" class="inputtext" size="20"></td>
          </tr>
          <tr>
            <td width="60" align="center">&nbsp;</td>
            <td>&nbsp;</td>
            <td width="60" align="center">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
        </table>
         <table width="80%"  border="0" align="center" cellpadding="0" cellspacing="0">
           <tr>
             <td>&nbsp;</td>
             <td align="right">
                 <input name="imageField" onclick="return vilaSub()" type="image" src="${ctx}/images/button_login.gif" width="74" height="21" border="0" >

                 <!-- <a href="javascript:openwin()"><img src="images/button_login.gif" width="74" height="21" border="0"></a>--></td></tr>
         </table>
         <input type="hidden" accept="" name="sessionManage" value="nosession" />
          </form>
         <table width="90%"  border="0" align="center" cellpadding="0" cellspacing="0">
           <tr>
             <td align="center">登录提示信息：请正确填写用户名和口令！</td>
           </tr>
         </table>

        </td>
      </tr>
      <tr>
        <td height="40" valign="top" background="${ctx}/images/bg_login_bot.gif"><table width="100%"  border="0" cellspacing="3" cellpadding="0">
          <tr>
            <td align="right">Copy Right 2004-2007 by 鑫干线网络科技发展有限公司 版权所有 </td>
            <td width="20" align="right">&nbsp;</td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
