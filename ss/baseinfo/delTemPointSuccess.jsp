<%@page contentType="text/html; charset=GBK"%>

<%
  //String msg = (String)request.getAttribute("errmsg");
  String msg = "�Ըõ�����ɹ���";
%>
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<title>�ƶ�������·Ѳ�����ϵͳ</title>
<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<table width="75px" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
<table width="59%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
  <tr class="trcolor">
    <td class="tdulleft" width="19%">��ʾ��Ϣ��</td>
    <td class="tdulright" width="81%"><%=msg%>    </td>
  </tr>
</table>
<table border="0" align="center" cellpadding="0" cellspacing="0" width="225px">
  <tr align="center">
    <td>
      <!-- <input name="Button2" type="reset" class="button" value="ȷ��" onclick="history.back()"> -->
    </td>
  </tr>
</table>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript" src="/WEBGIS/js/function.js" type=""></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">	
	setMapRefresh('RefreshTempAndPatrolPoint')
</SCRIPT>