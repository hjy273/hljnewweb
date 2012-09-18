<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript">
function toDelete(idValue){
    if(confirm("确定删除该纪录？")){
       var url = "${ctx}/RouteInfoAction.do?method=deleteRouteInfo&id=" + idValue;
        self.location.replace(url);
    }
     //confirm("不能删除该纪录!");

}


function toEdit(idValue){
        var url = "${ctx}/RouteInfoAction.do?method=loadRouteInfo&id=" + idValue;
        self.location.replace(url);

}
</script>
<template:titile value="查询路由信息结果"/>
<display:table name="sessionScope.queryresult" requestURI="${ctx}/RouteInfoAction.do?method=queryRouteInfo" id="currentRowObject" pagesize="18">
  <display:column property="routename" style="width:30%" title="路由名称"/>
  <display:column property="regionid" style="width:30%" title="所属区域"/>
   <apptag:checkpower thirdmould="70204" ishead="0">
<display:column media="html" style="width:10%" title="操作">
   <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = "";
    if(object != null)
     id = (String) object.get("id");
  %>
    <a href="javascript:toEdit('<%=id%>')">修改</a>
     <apptag:checkpower thirdmould="70205" ishead="0">
    <a href="javascript:toDelete('<%=id%>')">删除</a>
    </apptag:checkpower>
  </display:column>
   </apptag:checkpower>
</display:table>
<logic:empty name="queryresult">
</logic:empty>
<logic:notEmpty name="queryresult">
<html:link action="/RouteInfoAction.do?method=exportRouteInfoResult">导出为Excel文件</html:link>
</logic:notEmpty>
