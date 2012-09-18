<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.beans.SMSBean"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="text/javascript">
function toDelete(idValue){
    if(confirm("确定删除该纪录？")){
        var url = "${ctx}/terminalAction.do?method=deleteTerminal&id=" + idValue;
        self.location.replace(url);
    }
//confirm("不能删除该纪录!");
}

function toInit(id, pw, sim, spid){

    var url = "${ctx}/linepatrol/realtime/initMsg.jsp?id="+id+"&pw="+pw+"&sim="+sim+"&spid="+spid;

    var smsPop = window.open(url,'smsPop','width=300,height=100,scrollbars=yes');
    smsPop.focus();
}
/*
function toInit(id, sim, pw, path){

    var terL = id.length;

    id = id.substring(terL-4, terL);

    var url = path + "&SimIDList="+sim;

    var smsPop = window.open(url,'smsPop','width=300,height=100,scrollbars=yes');
    smsPop.focus();
}*/

function toEdit(idValue){
        var url = "${ctx}/terminalAction.do?method=loadTerminal&id=" + idValue;
        self.location.replace(url);
}
function toRelease (deviceid,id){
	if(confirm("确定解除资源号码"+deviceid+"与设备绑定关系吗？")){
		var url = "${ctx}/terminalAction.do?method=release&id=" + id;
        self.location.replace(url);
	}
}

</script>
<body>
<template:titile value="查询手持终端设备信息结果"/>
<display:table name="sessionScope.queryresult" pagesize="18" id="currentRowObject">
  <display:column property="sim" title="SIM卡号" sortable="true" headerClass="sortable"/>
  <display:column property="deviceid" title="设备编号"/>
  <display:column property="produceman" title="生产厂商"/>
  <logic:equal value="group" name="PMType">
    <display:column property="ownerid" title="巡检维护组"/>
  </logic:equal>
  <logic:notEqual value="group" name="PMType">
    <display:column property="ownerid" title="巡检人"/>
  </logic:notEqual>
  <display:column media="html" title="是否绑定设备">
  	<%
  	 BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
  		String imei = (String)object.get("imei");
  		if(imei != null && !"".equals(imei)){
  	%>
  		是(<%=imei %>)
  	<%
  		}else{
  			out.print("否");
  		}
  		
  	%>
  </display:column>
  <display:column property="contractorid" title="代维单位"/>
<%
  BasicDynaBean object1 = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
  String terminalid = "";
  if (object1 != null)
    terminalid = (String) object1.get("terminalid");
%>
  <apptag:checkpower thirdmould="70804" ishead="0">
    <display:column media="html" title="修改">
      <a href="javascript:toEdit('<%=terminalid%>')">修改</a>
    </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="70805" ishead="0">
    <display:column media="html" title="删除">
    <%
      BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
      String id = (String) object.get("terminalid");
    %>
      <a href="javascript:toDelete('<%=id%>')">删除</a>
    </display:column>
  </apptag:checkpower>
</display:table>
<html:link action="/terminalAction.do?method=exportTerminalResult">导出为Excel文件</html:link>

</body>
