<%@ page language="java" contentType="text/html; charset=GBK"%><%@include
	file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
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
function toView(idValue){
        var url = "${ctx}/patrolAction.do?method=viewPatrol&id=" + idValue;
        self.location.replace(url);

}
</script>
<template:titile value="巡检维护组信息一览表" />
<display:table name="sessionScope.queryresult" id="currentRowObject"
	pagesize="18">
	<%
	    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
	    String patrolid = "";
	    if (object != null)
	        patrolid = (String) object.get("patrolid");
	%>
	<display:column property="patrolname" title="巡检维护组" />
	<display:column property="parentid" title="代维单位" />
	<display:column property="maintenance_area" title="维护区域" />
	<display:column property="principal" title="负责人" />
	<display:column property="phone" title="联系电话" />
	<display:column property="work_phone" title="驻地电话" />
	<display:column media="html" title="操作">
		<a href="javascript:toView('<%=patrolid%>')">查看</a>
	</display:column>
	<apptag:checkpower thirdmould="70604" ishead="0">
		<display:column media="html" title="操作">
			<a href="javascript:toEdit('<%=patrolid%>')">修改</a>
		</display:column>
	</apptag:checkpower>
	<apptag:checkpower thirdmould="70605" ishead="0">
		<display:column media="html" title="操作">
			<a href="javascript:toDelete('<%=patrolid%>')">删除</a>
		</display:column>
	</apptag:checkpower>
</display:table>
<html:link action="/patrolAction.do?method=exportPatrolMan">导出为Excel文件</html:link>


