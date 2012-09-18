<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript">
function toDelete(idValue){
    if(confirm("确定删除该纪录？")){
        var url = "${ctx}/pointAction.do?method=deletePoint&id=" + idValue;
        self.location.replace(url);
    }
}
function toEdit(idValue){
        var url = "${ctx}/pointAction.do?method=loadPoint&id=" + idValue;
        self.location.replace(url);

}
</script>

<template:titile value="查询巡检点信息结果"/>
<display:table name="sessionScope.queryresult" id="currentRowObject"  pagesize="18">

  <display:column property="pointname" title="巡检点名称"/>
  <display:column property="addressinfo" title="巡检点位置"/>
  <display:column property="pointtype" title="点类型"/>
  <display:column property="sublineid" title="所属巡检段"/>
  <display:column property="isfocus" title="是否关键点"/>
  <display:column property="regionid" title="所属区域"/>


  <apptag:checkpower thirdmould="71104" ishead="0">

  <display:column media="html" title="修改">
   <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String pointid = "";
    if(object != null)
     pointid = (String) object.get("pointid");
  %>
    <a href="javascript:toEdit('<%=pointid%>')">修改</a>
  </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="71105" ishead="0">

  </apptag:checkpower>

</display:table>
<html:link action="/pointAction.do?method=exportPointResult">导出为Excel文件</html:link>
