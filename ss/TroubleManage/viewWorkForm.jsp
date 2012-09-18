<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.troublemanage.beans.*"%>
<%@page import="com.cabletech.troublemanage.domainobjects.*"%>
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
<script language="javascript" type="">
<!--
function postIt(){
    //var url = "${ctx}/accidentAction.do?method=acceptAccidentTask&id=" + id.value + "&monitor="+monitor.value+"&commander="+commander.value;
    //self.location.replace(url);

    AccidentBean.submit();
}
function toExpotr(){
    self.location.replace("${ctx}/accidentAction.do?method=ExportAccidentNoticeform");
}
//-->
</script>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
</head>

<body>
<br>

<html:form action="/accidentAction.do?method=completeAccidentForm">
<input type="hidden" name="id" value="<%=bean.getKeyid()%>">

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="24" align="center" class="title2">障碍处理完成单</td>
  </tr>
</table>
<br>
<table width="85%"  border="0" align="center" cellpadding="5" cellspacing="0">
  <tr>
   <input type="hidden"  value="<%=bean.getContractorid()%>" name="companyname"/>
    <td>&nbsp;&nbsp;隐患报告单位：<%=bean.getContractorid()%>&nbsp;&nbsp;&nbsp;&nbsp;
      填报人：<%=bean.getBconfirmman()%>&nbsp;&nbsp;&nbsp;&nbsp;
      填报时间：<%=bean.getReprottime()%> </tr>
</table>
<table width="85%" align="center" cellpadding="5" cellspacing="1" bgcolor="#999999">
  <tr>
    <td width="15%" rowspan="4" align="center" class=trcolor >障碍描述
    </td>
    <td class=trcolor width="25%" >发生日期 </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getSendtime().substring(0,10) %></td>
  </tr>
  <tr>
    <td class=trcolor >发生时间 </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getSendtime().substring(11) %></td>
  </tr>
  <tr>
    <td class=trcolor >障碍段落 </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getLid() %></td>
  </tr>
  <tr>
    <td class=trcolor >地点标石号 </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getPid() %></td>
  </tr>
 <!-- <tr>
    <td width="15%" rowspan="10" align="center" class=trcolor >障碍处理
    </td>
    <td class=trcolor >甲方人员到达无人站时间 </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getBefortime() %></td>
  </tr>
  <tr>
    <td class=trcolor >甲方通知时间 </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getNoticetime() %></td>
  </tr>
  <tr>
    <td class=trcolor >甲方配合人 </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getCooperateman() %></td>
  </tr>
  <tr>
    <td class=trcolor >乙方到达 测判时间 </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getTesttime() %></td>
  </tr>
  <tr>
    <td class=trcolor >测判人 </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getTestman() %></td>
  </tr>
  <tr>
    <td class=trcolor >测判距离 </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getDistance() %></td>
  </tr>
  <tr>
    <td class=trcolor >实际测判距离</td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getRealdistance() %></td>
  </tr>
  <tr>
    <td class=trcolor >熔接人 </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getFixedman() %></td>
  </tr>
  <tr>
    <td class=trcolor >监测人 </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getMonitor() %></td>
  </tr>
  <tr>
    <td class=trcolor >指挥 </td>
    <td colspan="3" bgcolor="#FFFFFF"><%=bean.getCommander() %></td>
  </tr>
  <tr>
    <td width="15%" rowspan="<%=deatilVct.size()+1%>" align="center" class=trcolor >障碍历时
    </td>
    <td class=trcolor >系统 / 纤芯号 </td>
    <td width="20%" class=trcolor >临时调 通时间 </td>
    <td width="21%" class=trcolor >修复时间 </td>
    <td width="24%" class=trcolor >历时 </td>
  </tr>
  <%for(int i = 0; i < deatilVct.size() ; i++){
      AccidentDetailBean dBean = (AccidentDetailBean) deatilVct.get(i);
      %>
  <tr>
    <td bgcolor="#FFFFFF"><%=dBean.getCorecode() %></td>
    <td width="20%" bgcolor="#FFFFFF"><%=dBean.getTempfixedtime() %></td>
    <td width="21%" bgcolor="#FFFFFF"><%=dBean.getFixedtime() %></td>
    <td width="24%" bgcolor="#FFFFFF"><%=dBean.getDiachronic() %></td>
  </tr>
  <%} %>-->
  <tr>
    <td align="center" class=trcolor >故障原因</td>
    <td colspan="4" valign="top" bgcolor="#FFFFFF" class=trcolor ><%=bean.getResonandfix() %></td>
  </tr>
  <tr>
    <td align="center" class=trcolor >附件</td>
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
<table width="85%"  border="0" align="center" cellpadding="15" cellspacing="0">

</table>
<p>
<p>
<template:formSubmit>
<td>
    <html:button property="action" styleClass="lbutton" onclick="history.back()">确定</html:button>
    <html:button property="action" styleClass="lbutton" onclick="toExpotr()">导出到Excel</html:button>
</td>
</template:formSubmit>
<br>
<br>
</html:form>
</body>
</html>
<script language="javascript">
mySubmit1();
</script>
