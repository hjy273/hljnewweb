<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript">
function toDelete(idValue){
    if(confirm("确定删除该纪录？")){
        var url = "${ctx}/EquipInfoAction.do?method=deleteEquipInfo&id=" + idValue;
        self.location.replace(url);
    }
}
</script>
<template:titile value="查询装备配置信息结果"/>
<display:table name="sessionScope.queryresult" id="currentRowObject" pagesize="18">
  <display:column property="id" title="设备编号" href="${ctx}/EquipInfoAction.do?method=loadEquipInfo" paramId="id"/>
  <display:column property="name" title="设备名称"/>
  <display:column property="type" title="类别"/>
  <display:column property="model" title="设备型号"/>

  <display:column media="html" title="删除">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = "";
   if(object != null)
     id = (String) object.get("id");
  %>
    <a href="javascript:toDelete('<%=id%>')">删除</a>
  </display:column>

</display:table>
