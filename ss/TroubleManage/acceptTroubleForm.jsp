<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.troublemanage.beans.*"%>
<%@page import="com.cabletech.troublemanage.domainobjects.*"%>
<%AccidentBean bean = (AccidentBean) request.getAttribute("AccidentBean");%>
<style type="text/css">

<!--
.hiInput {
    width: 100%;
    background-color:transparent;
    border-bottom-width: 1px;
    border-top-style: none;
    border-right-style: none;
    border-bottom-style: solid;
    border-left-style: none;
    border-bottom-color: #000000;
}

.commonHiInput {
    background-color:transparent;
    border-bottom-width: 1px;
    border-top-style: none;
    border-right-style: none;
    border-bottom-style: solid;
    border-left-style: none;
    border-bottom-color: #000000;
}
-->
</style>
<script language="javascript">
function mySubmit1(){
    var a=document.getElementsByTagName("td");

    for(var i=0;i<a.length;i++){
        if (a[i].innerText=="null"){
            a[i].innerText="";
        }
    }
     var b=document.getElementsByTagName("input");
    for(var i=0;i<b.length;i++){
        if (b[i].value=="null"){
            b[i].value="";
        }
    }
}
</script>
<script language="javascript" type="">
<!--
function postIt(){
    var url = "${ctx}/accidentAction.do?method=acceptTroubleTask&id=" + id.value;
    self.location.replace(url);
}
//-->
</script>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>�ޱ����ĵ�</title>
</head>

<body>
<br>
<input type="hidden" name="id" value="<%=bean.getKeyid()%>">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="24" align="center" class="title2">��д��������֪ͨ��</td>
  </tr>
</table>
<br>
<table width="85%"  border="0" align="center" cellpadding="1" cellspacing="0">
  <tr>
    <td>�ҷ� ( ���� ) ��<input type="text" name="companyname" class="commonHiInput" style="width:120;" value="<%=bean.getContractorid()%>">��˾&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ʱ�䣺 <input type="text" name="year" class="commonHiInput" style="width:50;" readonly>�� <input type="text" name="month" class="commonHiInput" style="width:50;" readonly>�� </td>
  </tr>
</table>
<table width="85%" align="center" cellpadding="5" cellspacing="1" bgcolor="#999999">
  <tr>
    <td width="15%" rowspan="4" align="center" class=trcolor >�ϰ�����
    </td>
    <td class=trcolor width="25%" >�������� </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getSendtime().substring(0,10) %></td>
  </tr>
  <tr>
    <td class=trcolor >����ʱ�� </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getSendtime().substring(11) %></td>
  </tr>
  <tr>
    <td class=trcolor >�ϰ����� </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getLid() %></td>
  </tr>
  <tr>
    <td class=trcolor >�ص��ʯ�� </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getPid() %></td>
  </tr>
  <tr>
    <td width="15%" rowspan="3" align="center" class=trcolor >�ϰ�����
    </td>
    <td class=trcolor >�ɷ��� </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getWhosend()%></td>
</tr>
    <tr>
    <td class=trcolor >������
    </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getSendto()%></td>
    </tr>
  <tr>
    <td class=trcolor >�ɷ�ʱ��</td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getNoticetime()%></td>
  </tr>

  <tr>
    <td align="center" class=trcolor >����ԭ��<br>      <br>
      ��<br>      <br>
    ���޹���    </td>
    <td  colspan="4" valign="top" bgcolor="#FFFFFF" class=trcolor ><%=bean.getResonandfix() %></td>
  </tr>
</table>
<table width="85%"  border="0" align="center" cellpadding="15" cellspacing="0">
  <tr>
    <td width="80">�ҷ���ˣ�</td>
    <td align="left"><%=bean.getBreportman()%></td>
  </tr>
  <tr>
    <td width="80">�׷�ȷ���ˣ�</td>
    <td align="left"><%=bean.getBconfirmman()%></td>
  </tr>
</table>
<p>
<p>
<template:formSubmit>
<td>
    <html:button property="action" styleClass="lbutton" onclick="postIt()">��Ӧ֪ͨ��</html:button>
</td>
</template:formSubmit>
<br>
<br>
</body>
</html>
<script language="javascript">
 var d=new Date();
 document.all.year.value=d.getFullYear();
 document.all.month.value=d.getMonth()+1;
 mySubmit1();
</script>
