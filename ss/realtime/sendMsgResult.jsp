<%@page contentType="text/html; charset=GBK"%>
<%
  String errmsg = "请求超时或者连接短信服务器失败，请重试。";
  if (request.getAttribute("resultMsg") != null) {
    errmsg = (String) request.getAttribute("resultMsg");
  }
%>
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<script language="javascript">
<!--

function toClose(){
	try{
		top.close();
	}catch(e){}

	try{
		self.close();
	}catch(e){}
}
//-->
</script>
<title>移动传输线路巡检管理系统</title>
<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<table width="75" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
<table width="80%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
  <tr class="trcolor">
    <td class="tdulleft" width="19%">提示信息：</td>
    <td class="tdulright" width="81%"><%=errmsg%></td>
  </tr>
</table>
<br>
<table border="0" align="center" cellpadding="0" cellspacing="0" width="225">
  <tr align="center">
    <td>
      <input name="Button2" type="reset" class="button" value="确定" onclick="toClose()">
    </td>
  </tr>
</table>
</body>
</html>
