<%@ page contentType="text/html; charset=GBK" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="cabletechtag" prefix="apptag" %>

<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">

<script language="javascript">
<!--
function setCloseP(){
    try{
        opener.closes.Click();
    }catch(e){
        //alert(e);
    }
}
//-->
</script>

<%@ page import="com.cabletech.baseinfo.domainobjects.*"%>

<html>

<body onload="userInfoBean.userid.focus()">

<table width="100%" height="85%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="234" align="center"><table width="535"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="214" align="center" valign="bottom" background="${ctx}/images/bg_login_top.gif">&nbsp;</td>
      </tr>
      <tr>
        <td height="123" valign="top" background="${ctx}/images/bg_login_mid.gif">
       <form name="userInfoBean" method="Post" action="${ctx}/login.do?method=login" >
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
                 </td>
           </tr>
         </table>
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
            <td align="right">Copy Right 2007 by 鑫干线网络科技有限责任公司 版权所有 </td>
            <td width="20" align="right">&nbsp;</td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>

<script language="javascript" type="">
<!--
//setCloseP();
//-->
</script>
