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
<title>�ƶ�������·Ѳ�����ϵͳ</title>
<template:titile value="�鿴�ظ�����"/>
<display:table name="sessionScope.showsms"  id="currentRowObject" pagesize="8" >
  <display:column property="msgcontent" title="������Ϣ" sortable="true"/>
  <display:column property="respstate" title="�ظ���Ϣ" sortable="true"/>
    <display:column media="html" title="����">
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
    <a href="javascript:toGetForm('<%=id%>','<%=sendtime%>','<%=message%>','<%=resptime%>','<%=respstate%>')">�鿴</a>
  </display:column>
</display:table>

<span id = "basicInfoSpan" style="display:none" >
<html:form  method="Post" action="/smsAction.do?method=sendCommandSMS" styleId="reform">
  <template:formTable th2="��   ��">
    <template:formTr name="����ʱ��">
      <input type="text" name="sendtime" class="inputtext" style="width:200" readonly="readonly"/>
    </template:formTr>
    <template:formTr name="������Ϣ">
      <html:textarea property="message" styleClass="selecttext" rows="4" style="width:200" readonly="true"/>
    </template:formTr>
    <template:formTr name="�ظ�ʱ��">
      <input type="text" name="resptime" class="inputtext" style="width:200" readonly="readonly"/>
    </template:formTr>
    <template:formTr name="�ظ�����">
      <input type="text" name="respstate" class="inputtext" style="width:200"  readonly="readonly"/>
    </template:formTr>
  </template:formTable>
</html:form>
</span>
