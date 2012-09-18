<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>

<script language="javascript">
function toDelete(idValue){
    if(confirm("确定删除该纪录？")){
        var url = "${ctx}/sublineAction.do?method=deleteSubline&id=" + idValue;
        self.location.replace(url);
    }
}

function toEdit(idValue){
        
        var url = "${ctx}/sublineAction.do?method=loadSubline&id=" + idValue;
        self.location.replace(url);

}
function toGetBack(){
        var url = "${ctx}/sublineAction.do?method=loadQuerySublineForm";
        self.location.replace(url);
}
</script>

<template:titile value="查询巡检线段信息结果"/>
<display:table name="sessionScope.queryresult"  id="currentRowObject" pagesize="18">

  <display:column property="sublinename" title="巡检线段名称" sortable="true"/>
  <display:column property="lineid" title="所属线路" sortable="true"/>
  <display:column property="ruledeptid" title="所属部门" sortable="true"/>
  <display:column property="linetype" title="线路类型" sortable="true"/>
  <logic:equal value="group" name="PMType">
  	    <display:column property="patrolname" title="巡检维护组" sortable="true"/>
  </logic:equal>
  <logic:notEqual value="group" name="PMType">
  	    <display:column property="patrolname" title="巡检维护人" sortable="true"/>
  </logic:notEqual>

  <display:column property="regionid" title="所属区域" sortable="true"/>

<apptag:checkpower thirdmould="71004" ishead="0">

  <display:column media="html" title="修改">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String sublineid = "";
    if(object != null)
     sublineid = (String) object.get("sublineid");
     //<display:column property="sublineid" title="巡检段编号" sortable="true"/> 由上面的column移至而来，可删除
  %>
    <a href="javascript:toEdit('<%=sublineid%>')">修改</a>
  </display:column>
  </apptag:checkpower>
    <apptag:checkpower thirdmould="71005" ishead="0">

  <display:column media="html" title="删除">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String sublineid = (String) object.get("sublineid");
  %>
    <a href="javascript:toDelete('<%=sublineid%>')">删除</a>
  </display:column>
  </apptag:checkpower>
</display:table>
<html:link action="/sublineAction.do?method=exportSubline">导出为Excel文件</html:link>

