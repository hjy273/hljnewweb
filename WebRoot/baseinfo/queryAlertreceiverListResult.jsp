<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script  type="text/javascript">
function toDelete(idValue){
    if(confirm("确定删除该纪录？")){
        var url = "${ctx}/AlertreceiverListAction.do?method=deleteAlertreceiverList&id=" + idValue;
        self.location.replace(url);
    }
}

function toEdit(idValue){
        var url = "${ctx}/AlertreceiverListAction.do?method=loadAlertreceiverList&id=" + idValue;
        self.location.replace(url);

}


</script>
<body>
<template:titile value="查询报警号码结果"/>
<display:table name="sessionScope.queryresult" id="currentRowObject" pagesize="18">
  <display:column property="simcode" title="接收报警号码" sortable="true"/>
  <display:column property="emergencylevel" title="接收事故级别"  sortable="true"/>
  <display:column property="userid" title="接收者"  sortable="true"/>
  <display:column property="contractorid" title="事故所属代维单位"  sortable="true"/>

  <apptag:checkpower thirdmould="71504" ishead="0">
	  <display:column media="html" title="修改">
       <%
	    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
	    String id = (String) object.get("id");
	  %>
	    <a href="javascript:toEdit('<%=id%>')">修改</a>
	  </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="71505" ishead="0">
	  <display:column media="html" title="删除">
       <%
	    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
	    String id = (String) object.get("id");
	  %>
	     <a href="javascript:toDelete('<%=id%>')">删除</a>
	  </display:column>
  </apptag:checkpower>

</display:table>
<html:link action="/AlertreceiverListAction.do?method=exportAlertreceiverListResult">导出为Excel文件</html:link>
</body>
