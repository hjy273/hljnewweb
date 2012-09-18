<%@include file="/common/header.jsp"%>
<html>
	<head>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />

		<style type="text/css">
			.subject{text-align:center;}
		</style>

		<script type="text/javascript">
			function getOneForm(id,type,tid) {
				var url = "PollingConFsoAction.do?method=getOneAllinfo&pid="+id+"&type="+type+"&tid="+tid;
				window.location.href=url;
			}
		
			function goBackForQuery() {
				var url = "MobileTaskAction.do?method=doQueryForCheck";
				window.location.href=url;
			}
		</script>
	</head>

	<body>
		<br>
		<%
			BasicDynaBean object = null;
			String num = null;
			String id = null;
			String state = null;
		%>
		<template:titile value="机房巡检设备信息一览表" />

		<display:table name="sessionScope.oneTaskList" id="currentRowObject"
			pagesize="18">
			<%
							object = (BasicDynaBean) pageContext
							.findAttribute("currentRowObject");
					if (object != null) {
						num = (String) object.get("numerical");
						id = (String) object.get("pid");
						state = (String) object.get("state");
					}
			%>
			<display:column title="序列号" sortable="true" class="subject"
				headerClass="subject" media="html">
					<a href="javascript:getOneForm('<%=id%>','<bean:write name="type"/>','<bean:write name="tid"/>')"><%=id%></a>
			</display:column>
			<display:column property="conname" sortable="true" title="代维公司"
				headerClass="subject" maxLength="20" class="subject" />
			<display:column property="exename" sortable="true" title="执行人"
				headerClass="subject" maxLength="20" class="subject" />
			<display:column property="exetime" sortable="true" title="执行日期"
				headerClass="subject" maxLength="20" class="subject" />
			<display:column property="eaname" sortable="true" title="A端设备名称"
				headerClass="subject" maxLength="20" class="subject" />
			<display:column property="ebname" sortable="true" title="B端设备名称"
				headerClass="subject" maxLength="20" class="subject" />
			<display:column property="equipment_model" sortable="true"
				title="设备型号" headerClass="subject" maxLength="20"
				class="subject" />
			<display:column property="machine_no" sortable="true" title="机身号"
				headerClass="subject" maxLength="20" class="subject" />
			<display:column property="power_type" sortable="true" title="设备/电源类型"
				headerClass="subject" maxLength="20" class="subject" />
		</display:table>


		<div style="text-align: center; margin-top: 10px;">
				<input type="button" value="返回" class="button"
					onclick="goBackForQuery()">
		</div>
	</body>
</html>
