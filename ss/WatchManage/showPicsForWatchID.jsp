<%@include file="/common/header.jsp"%>
<%@page import="java.util.List" %>
<%@page import="org.apache.commons.beanutils.BasicDynaBean" %>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<%@page import="com.cabletech.commons.config.GisConInfo" %>

<%
//List picListForWatch = (List)session.getAttribute("picListForWatch");
GisConInfo gisip = GisConInfo.newInstance();
String watchPicURL = "http://" + gisip.getWatchPicIP() + ":" + gisip.getWatchPicPort() + "/"  + gisip.getWatchPicDir();
String totalPicURL = "";
%>



<script language="javascript">
var finalstring="";
function makesure(pic){
    if(pic.checked == true){
      if (finalstring.indexOf(pic.value)== -1){
        finalstring = finalstring + pic.value + "|";
      }
    }else{
      finalstring = finalstring.replace(pic.value + "|","");
    }
   document.all.picnamestring.value = finalstring;
}

function addGoBack(){
	var url = "${ctx}/WatchManage/querywatchresult.jsp";
	self.location.replace(url);
}


</script>
<template:titile value="����ͼƬѡ�����"/>
<html:form action="/WatchPicAction.do?method=doLinkUploadPic">
<display:table name="sessionScope.picListForWatch" id="picListForWatch" pagesize="18">
  <display:column media="html" title="��ѡ" width="30">
    <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("picListForWatch");
    String attachname = (String) object.get("attach"); 
    String id = (String) object.get("keyid"); 
    String remark = (String) object.get("remark"); 
    %>
     <input name="choice_id" type="checkbox"  value="<%=attachname %>*<%=id %>*<%=remark %>" onclick="makesure(this)"/>
  </display:column>
  <display:column property="attach" title="������" sortable="true"/>
  <display:column property="subject" title="����" sortable="true"/>
  <display:column property="remark" title="����" sortable="true"/>
  <display:column property="sendtime" title="����ʱ��" sortable="true"/>
  <display:column property="sender" title="SIM����" sortable="true"/>
  <display:column media="html" title="����">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("picListForWatch");
    String attachname = (String) object.get("attach"); 
    totalPicURL = watchPicURL + "/" + attachname;
    System.out.println("totalPicURL:" + totalPicURL);
  %>
  <img src="<%=totalPicURL%>" border="0" height="45">
  </display:column>
</display:table>
    <input  id="picnamestring"  name="picnamestring" type="hidden" value=""/>
    <br/>
    <br/>
    <center>
        <html:submit styleClass="button">ȷ��</html:submit>
        <html:button property="action" styleClass="button" onclick="addGoBack()" >����</html:button>
    </center>
</html:form>
