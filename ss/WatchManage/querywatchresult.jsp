<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<html>
<template:titile value="查询外力盯防现场信息结果"/>
<script language="javascript" type="">

function loadForm(idValue){
	var url = "${ctx}/watchAction.do?method=loadWatchAsObj&id=" + idValue;
	self.location.replace(url);
}

function loadSta(idValue){
	var url = "${ctx}/WatchExeStaAction.do?method=getWatchPointSta&id=" + idValue;
	self.location.replace(url);
}


function rowQuery(idValue){
	var url = "${ctx}/watchexeAction.do?method=queryExecuteInfo&id=" + idValue;
	self.location.replace(url);
}
function rowDelete(idValue){
	if(confirm("确定删除该纪录？")){
        var url = "${ctx}/watchAction.do?method=deleteWatch&id=" + idValue;
        self.location.replace(url);
	}
}

function toEdit(idValue){
	var url = "${ctx}/watchAction.do?method=loadWatch&id=" + idValue;
	self.location.replace(url);
}

function toUploadPic(idValue){
	var url = "${ctx}/WatchPicAction.do?method=queryLinkPic&watchid=" + idValue;
	self.location.replace(url);
}

function toViewUploadPic(idValue){
	var url = "${ctx}/WatchPicAction.do?method=viewLinkPicEx&watchid=" + idValue;
	self.location.replace(url);
}

function show1(){
  alert("无施工信息，没有统计报表！")
}
function show2(){
  alert("已完成盯防，不能修改！")
}
function show3(){
  alert("已完成盯防，不能删除！")
}
function show4(){
  alert("未完成核查，没有统计报表！")
}
</script>
<style type="text/css">
.subject{text-align:center;}
</style>
<body>
<display:table name="sessionScope.queryResult" requestURI="${ctx}/watchAction.do?method=queryWatch" id="resultList" pagesize="18">
  <display:column media="html" title="盯防名称">
    <%
    BasicDynaBean objecta = (BasicDynaBean) pageContext.findAttribute("resultList");
    String rowida = "";
    String placename = "";
    String placenameTitle = "";
   	if(objecta != null){
   		rowida = (String) objecta.get("placeid");
    	placenameTitle = (String)objecta.get("placename");
    	 if(placenameTitle != null && placenameTitle.length() > 10) {
    		placename = placenameTitle.substring(0,10) + "...";
    	 } else {
    		placename = placenameTitle;
    	 }
   	}
    
    %>
    <a href="javascript:loadForm('<%=rowida%>')" title="<%=placenameTitle %>"><%=placename %></a>
    </display:column>
  <display:column property="contractorname" title="代维单位" sortable="true"/>
  <display:column property="innerregion" title="所属区域" sortable="true"/>
  <logic:equal value="group" name="PMType">
  	<display:column property="patrolname" title="巡检维护组" sortable="true"/>
  </logic:equal>
  <logic:notEqual value="group" name="PMType">
  	<display:column property="patrolname" title="负责人" sortable="true"/>
  </logic:notEqual>
  <display:column property="placetype" title="工地类型" sortable="true"/>
  <display:column property="watchplace" title="工地位置" sortable="true"/>  
  <display:column property="begindate" title="起始日期" sortable="true"/>
  <display:column property="enddate" title="终止日期" sortable="true"/>
  <display:column media="html" title="操作" headerClass="subject" >
  	 <%
    	BasicDynaBean object3 = (BasicDynaBean) pageContext.findAttribute("resultList");
    	String rowid3 = (String) object3.get("placeid");
    
      if(object3.get("dealstatus").equals("2")){

      %>
      <a href="javascript:loadSta('<%=rowid3%>')">统计报表</a>

      <%}
      if(object3.get("dealstatus").equals("0")){%>
      <a href="javascript:show1()">统计报表</a>
      <%}
      if(object3.get("dealstatus").equals("1"))%>
      <a href="javascript:show4()">统计报表</a>
  	<apptag:checkpower thirdmould="40205" ishead="0">
    	<%if(!object3.get("dealstatus").equals("2")){%>
            | <a href="javascript:toEdit('<%=rowid3%>')">修改</a>
        <% }%>
   </apptag:checkpower>
   
   <logic:notEqual value="11" name="LOGIN_USER" property="type">
  	 | <a href="javascript:toViewUploadPic('<%=rowid3%>')">附件</a>
   </logic:notEqual>  
   
   <apptag:checkpower thirdmould="40206" ishead="0">    
   	<%if(!object3.get("dealstatus").equals("2")){ %>
     | <a href="javascript:rowDelete('<%=rowid3%>')">删除</a>
    <%}%>
    </apptag:checkpower>
  </display:column>
  <input type="hidden" name="webgis" value="web">
</display:table>
<html:link action="/watchAction.do?method=exportWatchResult">导出为Excel文件</html:link>
</body></html>
