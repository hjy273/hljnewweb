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
	<template:titile value="�ƻ�ִ����Ϣ��ʾ" />
	<display:table name="sessionScope.planexecuteinfo"
		requestURI="${ctx}/PlanExeResultAction.do?method=showPlanExeResult"
		id="currentRowObject" pagesize="18">
		<display:column property="startdate" title="��ʼʱ��" sortable="true" />
		<display:column property="enddate" title="����ʱ��" sortable="true" />
		<display:column property="planname" title="�ƻ�����" sortable="true" />
		<logic:equal value="group" name="PMType">
			<display:column property="patrolname" title="Ѳ��ά����" sortable="true" />
		</logic:equal>
		<logic:notEqual value="group" name="PMType">
			<display:column property="patrolname" title="Ѳ��Ա" sortable="true" />
		</logic:notEqual>
		<display:column media="html" title="����">
			<%
			    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
			    String id = (String) object.get("planid");
			    String contractorname = (String) object.get("contractorname");
			    String executorname = (String) object.get("patrolname");
			%>
			<a
				href="javascript:toGetForm('<%=id%>','<%=contractorname%>','<%=executorname%>')">�鿴</a>
		</display:column>
	</display:table>
	<html:link action="/planExportAction.do?method=exportPlanexecuteinfo">����ΪExcel�ļ�</html:link>
	<p>
	<center>
		<input type="button" class="button" onclick="toGetBack()" value="����">
	</center>
	<p>
	</p>
</body>
