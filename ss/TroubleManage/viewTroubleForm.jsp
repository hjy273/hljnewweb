<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.troublemanage.beans.*"%>
<%@page import="com.cabletech.troublemanage.domainobjects.*"%>
<%

AccidentBean bean = (AccidentBean) request.getAttribute("AccidentBean");
Vector deatilVct = bean.getDetailvct();

%>
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
    //var url = "${ctx}/accidentAction.do?method=acceptAccidentTask&id=" + id.value + "&monitor="+monitor.value+"&commander="+commander.value;
    //self.location.replace(url);

    AccidentBean.submit();
}
function toExpotr(){
    self.location.replace("${ctx}/accidentAction.do?method=ExportTroubleNoticeform");
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

<html:form action="/accidentAction.do?method=completeTroubleForm">
<input type="hidden" name="id" value="<%=bean.getKeyid()%>">

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="24" align="center" class="title2">���������浥</td>
  </tr>
</table>
<br>
<table width="85%"  border="0" align="center" cellpadding="5" cellspacing="0">
  <tr>
    <input type="hidden"  value="<%=bean.getContractorid()%>" name="companyname"/>
    <td>&nbsp;&nbsp;�������浥λ��<%=bean.getContractorid()%>&nbsp;&nbsp;&nbsp;&nbsp;
      ��ˣ�<%=bean.getBconfirmman()%>&nbsp;&nbsp;&nbsp;&nbsp;
      �ʱ�䣺<%=bean.getReprottime()%>
  </tr>
</table>
<table width="85%" align="center" cellpadding="5" cellspacing="1" bgcolor="#999999">
  <tr>
    <td width="15%" rowspan="4" align="center" class=trcolor >��������
    </td>
    <td class=trcolor width="25%" >�������� </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getSendtime().substring(0,10) %></td>
  </tr>
  <tr>
    <td class=trcolor >����ʱ�� </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getSendtime().substring(11) %></td>
  </tr>
  <tr>
    <td class=trcolor >�������� </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getLid() %></td>
  </tr>
  <tr>
    <td class=trcolor >�ص��ʯ�� </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getPid() %></td>
  </tr>
  <tr>
    <td width="15%" rowspan="3" align="center" class=trcolor >��������
    </td>
    <td class=trcolor >�ɷ��� </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getWhosend()%></td>
</tr>
    <tr>
    <td class=trcolor >������ </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getSendto()%></td>
    </tr>
    <tr>
    <td class=trcolor >�ɷ�ʱ��</td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getNoticetime()%></td>
  </tr>

  <tr>
    <td align="center" class=trcolor >����ԭ��</td>
    <td  valign="top" colspan="4" bgcolor="#FFFFFF" class=trcolor >
    <input type="text" name="reson" style="background-color:transparent;border:0;width:100%" value="<%=bean.getResonandfix() %>">
  </tr>
  <tr>
    <td align="center" class=trcolor >����</td>
    <td  valign="top" colspan="4" bgcolor="#FFFFFF" class=trcolor >
    <%
    	String datumid="";
        AccidentBean abean = (AccidentBean)request.getAttribute("AccidentBean");
        datumid = abean.getDatumid();
        if(datumid==null){
        	datumid="";
        }
 	%>
   <apptag:listAttachmentLink fileIdList="<%=datumid%>"/>
  </tr>
</table>

<template:formSubmit>
<td>
    <html:button property="action" styleClass="lbutton" onclick="history.back()">ȷ��</html:button>
    <html:button property="action" styleClass="lbutton" onclick="toExpotr()">������Excel</html:button>
</td>
</template:formSubmit>
<br>
<br>
</html:form>
</body>
</html>
<script language="javascript" type="">


 var d=new Date();
 document.all.year.value=d.getFullYear();
 document.all.month.value=d.getMonth()+1;
 mySubmit1();
</script>
</script>
