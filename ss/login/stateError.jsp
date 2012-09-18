<%@page contentType="text/html; charset=GBK"%>
<%
  String errmsgFlag = "2";
  String errmsg = "";
  if (request.getAttribute("errmsg") != null) {
    errmsgFlag = (String) request.getAttribute("errmsg");
  }

  if(errmsgFlag.equals("2")){
	errmsg = "可能出于目前系统维护的需要，您的账号目前状态为\"暂停\"。请稍后重试或者联系系统管理员。";
  }

  if(errmsgFlag.equals("3")){
	errmsg = "您的账号目前状态为\"停止\"，如需使用该系统，请联系系统管理员。";
  }

%>
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<title>...::::欢迎使用移动传输线路巡检管理系统::::...</title>
<link href="css/login_style.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.style1 {
	color: #FF0000;
	font-weight: bold;
}
-->
</style>
</head>
<script language="javascript" src="js/no_right.js" type="text/javascript"></script>
<script language="JavaScript">
<!--
function openwin() {
	newwin=window.open ("", "newwindow", "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");

	//userInfoBean.target = "newwindow";
	//userInfoBean.submit();

	newwin.moveTo(0,0);
	newwin.resizeTo(screen.width,screen.height);

	//zsh 2004-10-22
	newwin.focus();
	//closes.Click();
}

//-->
</script>

<body onload="userInfoBean.userid.focus()">

<table width="100%" height="85%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="234" align="center"><table width="535"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="214" align="center" valign="bottom" background="${ctx}/images/bg_login_top.gif">&nbsp;</td>
      </tr>
      <tr>
        <td height="123" valign="top" background="${ctx}/images/bg_login_mid.gif">
       <form name="userInfoBean" method="Post" action="${ctx}/login.do?method=login" target = "newwindow" onsubmit="openwin()">
         <table width="80%"  border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td width="60" align="center">&nbsp;</td>
            <td>&nbsp;</td>
            <td width="60" align="center">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td width="60" align="center">用户名：</td>
            <td><input name="userid" type="text" class="inputtext" size="20"></td>
            <td width="60" align="center">口 令：</td>
            <td><input name="password" type="password" class="inputtext" size="20"></td>
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
                 <input name="imageField" type="image" src="${ctx}/images/button_login.gif" width="74" height="21" border="0">
                  
                 <!-- <a href="javascript:openwin()"><img src="images/button_login.gif" width="74" height="21" border="0"></a>--></td></tr>
         </table>
		  </form>
		 <table width="90%" height="62"  border="0" align="center" cellpadding="0" cellspacing="0">
           <tr>
             <td height="62" align="left" valign="bottom"><span class="style1">登录错误提示信息：<%=errmsg%></span></td>
           </tr>
         </table>

		</td>
      </tr>
      <tr>
        <td height="40" valign="top" background="${ctx}/images/bg_login_bot.gif"><table width="100%"  border="0" cellspacing="3" cellpadding="0">
          <tr>
            <td align="right">Copy Right 2004 by 鑫干线网络科技有限责任公司 版权所有 </td>
            <td width="20" align="right">&nbsp;</td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>

