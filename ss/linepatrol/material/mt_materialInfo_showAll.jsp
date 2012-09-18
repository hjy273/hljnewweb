<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript">
function toDelete(idValue){
    if(confirm("确定删除该纪录？")){
       var url = "${ctx}/materialInfoAction.do?method=deletePartBase&id=" + idValue;
        self.location.replace(url);
    }
     //confirm("不能删除该纪录!");

}


function toEdit(idValue){
        var url = "${ctx}/materialInfoAction.do?method=loadPartBase&id=" + idValue;
        self.location.replace(url);

}
</script>
<template:titile value="查询材料信息结果"/>
<display:table name="sessionScope.queryresult" requestURI="${ctx}/materialInfoAction.do?method=queryPartBase" id="currentRowObject" pagesize="18">
  <display:column property="name" style="width:20%" title="材料名称"/>
   <display:column property="typename" style="width:15%" title="材料类型" />
  <display:column property="modelname" style="width:15%" title="材料规格"/>
   <display:column property="factory" style="width:20%" title="生产厂家"/>
    <display:column property="remark" style="width:20%" title="备注"/>
<display:column media="html" style="width:20%" title="操作">
   <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    Object id =null;
    if(object != null)
     id = object.get("id");
  %>
    <a href="javascript:toEdit('<%=id%>')">修改</a>
    <a href="javascript:toDelete('<%=id%>')">删除</a>
  </display:column>
</display:table>
<logic:empty name="queryresult">
</logic:empty>
<logic:notEmpty name="queryresult">
<html:link action="/materialInfoAction.do?method=exportMaterialInfoResult">导出为Excel文件</html:link>
<br/>
<div align="center">
<input type="button" class="button" onclick="history.back()" value="返回"/>
</div>
</logic:notEmpty>
