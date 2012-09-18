<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script language="javascript" type="">
function toGetForm(idValue,contractornameValue,executornameValue){
   var url = "${ctx}/PlanExeResultAction.do?method=showPlanExeDetail&id=" + idValue  + "&contractorname=" + contractornameValue + "&executorname=" + executornameValue;
   self.location.replace(url);
}
function toGetBack(){
        var url = "${ctx}/PlanExeResultAction.do?method=queryPlanExeResult";
        self.location.replace(url);
}
</script>
<body style="width: 95%">
	<template:titile value="计划执行信息显示" />
	<display:table name="sessionScope.planexecuteinfo"
		requestURI="${ctx}/PlanExeResultAction.do?method=showPlanExeResult"
		id="currentRowObject" pagesize="18">
		<display:column property="startdate" title="开始时间" sortable="true" />
		<display:column property="enddate" title="结束时间" sortable="true" />
		<display:column property="planname" title="计划名称" sortable="true" />
		<logic:equal value="group" name="PMType">
			<display:column property="patrolname" title="巡检维护组" sortable="true" />
		</logic:equal>
		<logic:notEqual value="group" name="PMType">
			<display:column property="patrolname" title="巡检员" sortable="true" />
		</logic:notEqual>
		<display:column media="html" title="操作">
			<%
			    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
			    String id = (String) object.get("planid");
			    String contractorname = (String) object.get("contractorname");
			    String executorname = (String) object.get("patrolname");
			%>
			<a
				href="javascript:toGetForm('<%=id%>','<%=contractorname%>','<%=executorname%>')">查看</a>
		</display:column>
	</display:table>
	<html:link action="/planExportAction.do?method=exportPlanexecuteinfo">导出为Excel文件</html:link>
	<p>
	<center>
		<input type="button" class="button" onclick="toGetBack()" value="返回">
	</center>
	<p>
	</p>
</body>
