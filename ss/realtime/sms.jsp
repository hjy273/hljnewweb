<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@	page import	= "java.util.*" %>
<%
Hashtable ht = new Hashtable();
if (request.getAttribute("smsPro")!=null){
	ht = (Hashtable)request.getAttribute("smsPro");
}

String terminalid = (String)ht.get("terminalid");
String password = (String)ht.get("password");
String patrolid = (String)ht.get("patrolid");
String patrolname = (String)ht.get("patrolname");
String patrolmobile = (String)ht.get("patrolmobile");
String simnumber = (String)ht.get("simnumber");


%>
<script language="javascript" src="${ctx}/realtime/realtime.js"></script>

<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
<body text="#000000">

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="24" align="center" class="title2">编辑发送短消息</td>
  </tr>
  <tr>
    <td height="2" background="${ctx}/images/bg_line.gif"><img src="${ctx}/images/1px.gif" width="1" height="1"></td>
  </tr>
</table>


<input type="hidden" name="password" value ="<%=password%>">
<input type="hidden" name="patrolid" value ="<%=patrolid%>">
<input type="hidden" name="patrolmobile" value ="<%=patrolmobile%>">

<table width="98%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
  <tr>
    <th class="thlist">项目</th>
    <th class="thlist">填写</th>
  </tr>
  <tr class="trwhite">
    <td class="tdulleft">终端设备持有人</td>
    <td class="tdulright"><input type="text" name="patrolname" value ="<%=patrolname%>" class="inputtext" style="width:100;font-family: Geneva, Arial;font-size: 9pt;" disabled>
    </td>
  </tr>
  <tr class="trcolor">
    <td class="tdulleft">终端设备标识</td>
    <td class="tdulright"><input type="text" name="terminalid" value ="<%=terminalid%>"  class="inputtext" style="width:100;font-family: Geneva, Arial;font-size: 9pt;" disabled></td>
  </tr>
  <tr class="trwhite">
    <td class="tdulleft">终端设备 SIM </td>
    <td class="tdulright"><input type="text" name="simid" value ="<%=simnumber%>"  class="inputtext" style="width:100;font-family: Geneva, Arial;font-size: 9pt;" disabled></td>
  </tr>
  <tr class="trcolor">
    <td class="tdulleft">间隔时间</td>
    <td class="tdulright"><input type="text" name="interval" value ="00"  class="inputtext" style="width:100;font-family: Geneva, Arial;font-size: 9pt;"> &nbsp;(00 - 99)</td>
  </tr>
  <tr class="trwhite">
    <td class="tdulleft">返回数据次数</td>
    <td class="tdulright"><input type="text" name="returnnum" value ="1"  class="inputtext" style="width:100;font-family: Geneva, Arial;font-size: 9pt;"> &nbsp;(1 - 9)</td>
  </tr>

</table>
<html:form method="Post" action="/SendGisPmAction.do?method=sendSMS" >

<html:hidden property = "simid"/>
<html:hidden property = "content"/>

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="2" background="${ctx}/images/bg_line.gif"><img src="${ctx}/images/1px.gif" width="1" height="1"></td>
  </tr>
  <tr>
    <td align="right">
      <table  border="0" align="center" cellpadding="0" cellspacing="0" width="20%">
        <tr align="center">
          <td width="50%"> <html:button property="action" onclick="sendSms()">确认</html:button>
          </td>
		  <td width="50%"> <html:button property="action" onclick="self.close()">取消</html:button>
          </td>
      </tr>
    </table></td>
  </tr>
</html:form>
</table>
