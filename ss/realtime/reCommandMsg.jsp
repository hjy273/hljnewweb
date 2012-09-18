<%@include file="/common/header.jsp"%>
<%@page import="java.sql.*"%>
<%@page import="com.cabletech.commons.hb.QueryUtil"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>

<script type="" language="javascript">
var showflag = -1;
function toGetForm(idValue,sendtime,message,resptime,respstate){
//  var page = "${ctx}/smsAction.do?method=showOneReSMS&id=" + idValue+"&sendtime="+sendtime+"&message="+message+"&resptime="+resptime+"&respstate="+respstate;
//  alert(page);
//  var popWin = window.open(page,'resms','width=350,height=400,left=300,top=300');
//  popWin.focus();

  if(showflag == 1){
    showflag = -1;
    basicInfoSpan.style.display = "none";

  }else{
    basicInfoSpan.style.display = "";

  }
  reform.sendtime.value = sendtime;
  reform.message.value = message;
  reform.resptime.value = resptime;
  reform.respstate.value = respstate;
}
</script>
<title>移动传输线路巡检管理系统</title>
<template:titile value="查看回复短信"/>
<display:table name="sessionScope.showsms"  id="currentRowObject" pagesize="8" >
  <display:column property="msgcontent" title="调度信息" sortable="true"/>
  <display:column property="respstate" title="回复信息" sortable="true"/>
    <display:column media="html" title="操作">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = (String) object.get("id");
    String sendtime = (String)object.get("sendtime");
    String message = (String)object.get("msgcontent");
    String resptime = (String)object.get("resptime");
    String respstate= (String)object.get("respstate");
    if(resptime == null){
      resptime = "";
    }
  %>
    <a href="javascript:toGetForm('<%=id%>','<%=sendtime%>','<%=message%>','<%=resptime%>','<%=respstate%>')">查看</a>
  </display:column>
</display:table>

<span id = "basicInfoSpan" style="display:none" >
<html:form  method="Post" action="/smsAction.do?method=sendCommandSMS" styleId="reform">
  <template:formTable th2="内   容">
    <template:formTr name="发送时间">
      <input type="text" name="sendtime" class="inputtext" style="width:200" readonly="readonly"/>
    </template:formTr>
    <template:formTr name="调度信息">
      <html:textarea property="message" styleClass="selecttext" rows="4" style="width:200" readonly="true"/>
    </template:formTr>
    <template:formTr name="回复时间">
      <input type="text" name="resptime" class="inputtext" style="width:200" readonly="readonly"/>
    </template:formTr>
    <template:formTr name="回复内容">
      <input type="text" name="respstate" class="inputtext" style="width:200"  readonly="readonly"/>
    </template:formTr>
  </template:formTable>
</html:form>
</span>
