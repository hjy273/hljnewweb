<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<html>
<template:titile value="盯防点信息"/>
<script language="javascript">

function loadForm(idValue){
	var url = "${ctx}/watchAction.do?method=loadWatchAsObj&id=" + idValue;
	self.location.replace(url);
}

function rowQuery(idValue){
	var url = "${ctx}/watchexeAction.do?method=queryExecuteInfo&id=" + idValue;
	self.location.replace(url);
}
function rowDelete(idValue){
	if(confirm("确定忽略该点？")){
        var url = "${ctx}/watchAction.do?method=deleteTempWatch&id=" + idValue;
        self.location.replace(url);
	}
}

function toEdit(idValue){
	var url = "${ctx}/watchAction.do?method=loadWatch&id=" + idValue;
	self.location.replace(url);
}

function toAdd(idValue){
	var url = "${ctx}/watchAction.do?method=getTempWatch&id=" + idValue;
	self.location.replace(url);
}

function toUploadPic(idValue){
	var url = "${ctx}/WatchPicAction.do?method=queryUploadPic&watchid=" + idValue;
	self.location.replace(url);
}



</script>
<style type="text/css">
.subject{text-align:center;}
</style>
<body>
<display:table name="sessionScope.querytempResult" requestURI="${ctx}/WatchExecuteQueryAction.do?method=queryAllTempWatchPoint" id="querytempResult" pagesize="18">

  <display:column property="x" title="经度" sortable="true"/>
  <display:column property="y" title="纬度" sortable="true"/>
  <display:column property="regionname" title="所属区域" sortable="true"/>
  <display:column property="simid" title="SIM卡号" sortable="true"/>
  <display:column property="patrolname" title="巡检维护组" sortable="true"/>
  <display:column property="receivetime" title="采集时间" sortable="true"/>
 

 
  <display:column media="html" title="操作" headerClass="subject" class="subject">  	
  	<%
    	BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("querytempResult");
    	String bedited = "";
    	String rowid = (String) object.get("gpscoordinate");
    	bedited = (String) object.get("bedited");
    	if(bedited.equals("已制定")){
    %>
    -- 
	<%}else{%>
		<apptag:checkpower thirdmould="40103" ishead="0">
    		<a href="javascript:toAdd('<%=rowid%>')">制定</a> |
    	</apptag:checkpower>
    	<apptag:checkpower thirdmould="40104" ishead="0">
    	 	<a href="javascript:rowDelete('<%=rowid%>')">忽略</a>
    	</apptag:checkpower>
    <%}%> 
  </display:column>


</display:table>
<html:link action="/watchAction.do?method=exportTempWatchResult">导出为Excel文件</html:link>
</body></html>
