<%@page contentType="text/html; charset=GBK"%>
<%
  String errmsg = "����ʱ�������Ӷ��ŷ�����ʧ�ܣ������ԡ�";
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
<title>�ƶ�������·Ѳ�����ϵͳ</title>
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
    <td class="tdulleft" width="19%">��ʾ��Ϣ��</td>
    <td class="tdulright" width="81%"><%=errmsg%></td>
  </tr>
</table>
<br>
<table border="0" align="center" cellpadding="0" cellspacing="0" width="225">
  <tr align="center">
    <td>
      <input name="Button2" type="reset" class="button" value="ȷ��" onclick="toClose()">
    </td>
  </tr>
</table>
</body>
</html>
