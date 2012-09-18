<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.troublemanage.beans.*"%>
<%@page import="com.cabletech.troublemanage.domainobjects.*"%>
<%@ page import="com.cabletech.baseinfo.domainobjects.*" %>
<%@ page import="com.cabletech.commons.hb.*" %>
<%@ page import="java.sql.*" %>
<%UserInfo userinfo=(UserInfo)session.getAttribute("LOGIN_USER");%>
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
function postIt(flag){
    var url = "${ctx}/accidentAction.do?method=postAccidentTask&id=" + id.value +
    	"&breportman="+breportman.value+"&bconfirman=" + bconfirman.value + "&flag="+ flag;
    self.location.replace(url);
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

<input type="hidden" name="id" value="<%=bean.getKeyid()%>">

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="24" align="center" class="title2">填写障碍处理通知单</td>
  </tr>
</table>
<br>
<table width="85%"  border="0" align="center" cellpadding="5" cellspacing="0">
  <tr>
   <input type="hidden" name="breportman" class="hiInput" value="<%=userinfo.getUserName()%>">
  <input type="hidden" name="bconfirman" class="hiInput" value=" ">
  <input type="hidden" name="companyname" class="commonHiInput" style="width:120;" value="<%=bean.getContractorid()%>">
    <td><br>&nbsp;&nbsp;隐患报告单位：<%=bean.getContractorid()%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

    派 单 人：<%=userinfo.getUserName()%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    填报时间：<input type="text" name="year" class="commonHiInput" style="width:50;" readonly>年 <input type="text" name="month" class="commonHiInput" style="width:50;" readonly>月 </td>
  </tr>
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
  <!--
  <tr>
    <td width="15%" rowspan="10" align="center" class=trcolor >障碍处理
    </td>
    <td class=trcolor >甲方人员到达无人站时间 </td>
    <td colspan="3" bgcolor="#FFFFFF">&nbsp; </td>
  </tr>
  <tr>
    <td class=trcolor >甲方通知时间 </td>
    <td colspan="3" bgcolor="#FFFFFF">&nbsp; </td>
  </tr>
  <tr>
    <td class=trcolor >甲方配合人 </td>
    <td colspan="3" bgcolor="#FFFFFF"><input type="text" name="cooperateman" class="hiInput"></td>
  </tr>
  <tr>
    <td class=trcolor >乙方到达 测判时间 </td>
    <td colspan="3" bgcolor="#FFFFFF">&nbsp; </td>
  </tr>
  <tr>
    <td class=trcolor >测判人 </td>
    <td colspan="3" bgcolor="#FFFFFF">&nbsp; </td>
  </tr>
  <tr>
    <td class=trcolor >测判距离距测判端 </td>
    <td colspan="3" bgcolor="#FFFFFF">&nbsp; </td>
  </tr>
  <tr>
    <td class=trcolor >实际 测判距离距测判端 </td>
    <td colspan="3" bgcolor="#FFFFFF">&nbsp; </td>
  </tr>
  <tr>
    <td class=trcolor >熔接人 </td>
    <td colspan="3" bgcolor="#FFFFFF">&nbsp; </td>
  </tr>
  <tr>
    <td class=trcolor >监测人 </td>
    <td colspan="3" bgcolor="#FFFFFF">&nbsp; </td>
  </tr>
  <tr>
    <td class=trcolor >指挥 </td>
    <td colspan="3" bgcolor="#FFFFFF">&nbsp; </td>
  </tr>
  <tr>
    <td width="15%" rowspan="6" align="center" class=trcolor >障碍历时
    </td>
    <td class=trcolor >系统 / 纤芯号 </td>
    <td width="20%" class=trcolor >临时调 通时间 </td>
    <td width="21%" class=trcolor >修复时间 </td>
    <td width="24%" class=trcolor >历时 </td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp; </td>
    <td width="20%" bgcolor="#FFFFFF">&nbsp; </td>
    <td width="21%" bgcolor="#FFFFFF">&nbsp; </td>
    <td width="24%" bgcolor="#FFFFFF">&nbsp; </td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp; </td>
    <td width="20%" bgcolor="#FFFFFF">&nbsp; </td>
    <td width="21%" bgcolor="#FFFFFF">&nbsp; </td>
    <td width="24%" bgcolor="#FFFFFF">&nbsp; </td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp; </td>
    <td width="20%" bgcolor="#FFFFFF">&nbsp; </td>
    <td width="21%" bgcolor="#FFFFFF">&nbsp; </td>
    <td width="24%" bgcolor="#FFFFFF">&nbsp; </td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp; </td>
    <td width="20%" bgcolor="#FFFFFF">&nbsp; </td>
    <td width="21%" bgcolor="#FFFFFF">&nbsp; </td>
    <td width="24%" bgcolor="#FFFFFF">&nbsp; </td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp; </td>
    <td width="20%" bgcolor="#FFFFFF">&nbsp; </td>
    <td width="21%" bgcolor="#FFFFFF">&nbsp; </td>
    <td width="24%" bgcolor="#FFFFFF">&nbsp; </td>
  </tr>-->
  <tr>
    <td align="center" class=trcolor >故障原因
    <td colspan="4" valign="top" bgcolor="#FFFFFF" class=trcolor ><%=bean.getResonandfix() %></td>
  </tr>
</table><!--
<table width="85%"  border="0" align="center" cellpadding="15" cellspacing="0">
  <tr>
    <td>乙方填报人：<br><input type="text" name="breportman" class="hiInput" value="<%=userinfo.getUserName()%>"></td>
  </tr>
  <tr>
    <td>甲方确认人：<br /><input type="text" name="bconfirman" class="hiInput" value="   ">
      </td>
  </tr>
</table>-->
<p>
<p>
<template:formSubmit>
<td>
    <html:button property="action" styleClass="lbutton" onclick="postIt('1')">下发通知单</html:button>
 &nbsp;&nbsp;&nbsp;&nbsp;
    <html:button property="action" styleClass="lbutton" onclick="postIt('2')">忽略该隐患</html:button>

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
