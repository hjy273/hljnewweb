<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
function toDelete(idValue){
    if(confirm("确定删除该纪录？")){
       var url = "${ctx}/departAction.do?method=deleteDepart&id=" + idValue;
        self.location.replace(url);
    }
     //confirm("不能删除该纪录!");

}


function toEdit(idValue){
        var url = "${ctx}/departAction.do?method=loadDepart&id=" + idValue;
        self.location.replace(url);

}

</script>
<template:titile value="查询部门信息结果"/>
<display:table name="sessionScope.RESULTLIST" id="currentRowObject" pagesize="18">


  <display:column property="deptname"  title="部门名称"/>
  <display:column property="linkmaninfo"title="部门联系人"/>
  <display:column property="parentid"  title="上级部门"/>

  <apptag:checkpower thirdmould="70204" ishead="0">

  <display:column media="html" title="修改操作">
   <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String deptid = "";
    String id = "";
    if(object != null)
     deptid = (String) object.get("deptid");
  %>
    <a href="javascript:toEdit('<%=deptid%>')">修改</a>
  </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="70205" ishead="0">

  <display:column media="html"  title="删除操作">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String deptid = (String) object.get("deptid");
  %>
    <a href="javascript:toDelete('<%=deptid%>')">删除</a>
  </display:column>
  </apptag:checkpower>

</display:table>
<html:link action="/departAction.do?method=exportDepartResult">导出为Excel文件</html:link>
