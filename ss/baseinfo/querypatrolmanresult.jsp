<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript">
function toDelete(idValue){
    if(confirm("确定删除该纪录？")){
        var url = "${ctx}/patrolAction.do?method=deletePatrol&id=" + idValue;
        self.location.replace(url);
    }
}

function toEdit(idValue){
        var url = "${ctx}/patrolAction.do?method=loadPatrol&id=" + idValue;
        self.location.replace(url);

}

</script>

<template:titile value="巡检维护组信息一览表"/>
<display:table name="sessionScope.queryresult"  id="currentRowObject"  pagesize="18">

  <display:column property="patrolname" title="巡检维护组"/>
  <display:column property="parentid" title="代维单位"/>
  <display:column property="jobinfo" title="工作信息"/>



  <apptag:checkpower thirdmould="70604" ishead="0">
  <display:column media="html" title="修改">
   <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String patrolid =  "";
    if(object != null)
     patrolid = (String) object.get("patrolid");
    
  %>
    <a href="javascript:toEdit('<%=patrolid%>')">修改</a>

  </display:column>
  </apptag:checkpower>
   <apptag:checkpower thirdmould="70605" ishead="0">
 <display:column media="html" title="删除">
   <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String patrolid =  "";
    if(object != null)
     patrolid = (String) object.get("patrolid");
    
  %>
    <a href="javascript:toDelete('<%=patrolid%>')">删除</a>
  </display:column>
  </apptag:checkpower>
</display:table>
<logic:empty name="queryresult">
</logic:empty>
<logic:notEmpty name="queryresult">
<html:link action="/patrolAction.do?method=exportPatrolMan">导出为Excel文件</html:link>
</logic:notEmpty>


