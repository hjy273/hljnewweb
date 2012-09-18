<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title></title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
			.subject{text-align:center;}
		</style>
		<script type="text/javascript">
			function getOneForm(id,type,tid) {
				var url = "PollingContentAction.do?method=getOneAllInfo&pid="+id+"&type="+type+"&tid="+tid;
				window.location.href = url;
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
		 %>
		<template:titile value="机房巡检设备信息一览表"/>
		<display:table name="sessionScope.oneTaskList" id="currentRowObject" pagesize="18">
			<%
				object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
				if(object != null) {
					id = (String) object.get("pid");
					num = (String) object.get("numerical");
				}
			 %>
			<display:column media="html" sortable="true" style="subject" title="序列号">
				<a href="javascript:getOneForm('<%=id %>','<bean:write name="type"/>','<bean:write name="tid"/>')"><%=id %></a>
			</display:column>
			<display:column property="conname" sortable="true" title="代维公司" headerClass="subject" maxLength="20" class="subject"/>
			<display:column property="exename" sortable="true" title="执行人" headerClass="subject" maxLength="20" class="subject"/>
			<display:column property="exetime" sortable="true" title="执行日期" headerClass="subject" maxLength="20" class="subject"/>
			<display:column property="ename" sortable="true" title="设备名称" headerClass="subject" maxLength="20" class="subject" />
		</display:table>
		
		<div style="text-align: center; margin-top: 10px;">
				<input type="button" value="返回" class="button" onclick="goBackForQuery()">	
		</div>
	</body>
</html>

