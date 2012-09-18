<%@include file="/common/header.jsp"%>
<br>
<template:titile value="管道重新分配确认页面" />
<script language="javascript">
	function check(){
		var patrolID = document.all.targetMaintenanceId.value;
		var maintenanceId = document.getElementById("maintenanceId").value;
		if(patrolID != ""){
			if(patrolID==maintenanceId){
				alert("请选择其他代维单位！");
				return false;
			}
			return true;
		}else{
			alert("请选择代维单位");
			return false;
		}
	}
	function affirm(){
		document.all.btt.disabled=false;
	}
	function goAssign2(){
		var sublineid = document.getElementById("id").value;
		var maintenanceId = document.getElementById("maintenanceId").value;
		var url="${ctx}/pipeAction.do?method=pipeAssignTwo&sublineid="+sublineid+"&maintenanceId="+maintenanceId;
		window.location.href=url;
	}
</script>
<body>
	<html:form method="Post"
		action="/pipeAction.do?method=assignPipe">
		<div align="center">
			<input type="hidden" name="maintenanceId" id="maintenanceId" value="${maintenanceId }">
			<input type="hidden" name="id" id="id" value="${kid }">
			<br>
			从
			<apptag:setSelectOptions valueName="connCollection"
				tableName="contractorinfo" columnName1="contractorname"
				region="true" columnName2="contractorid" order="contractorname" />
			<html:select property="maintenanceId" disabled="true"
				styleClass="inputtext" styleId="rId" style="width:100px">
				<html:options collection="connCollection" property="value"
					labelProperty="label" />
			</html:select>
			至
			<html:select property="targetMaintenanceId" styleClass="inputtext"
				styleId="rId" style="width:200px">
				<html:options collection="connCollection" property="value"
					labelProperty="label" />
			</html:select>

		</div>
		<br>
		<div align="center">
			<input type="submit" class="button" value="分配" disabled id="btt"
				onclick="return check()">
			<input type="button" value="上一步" class="button"
				onclick="goAssign2()" />
		</div>
	</html:form>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
		media="screen, print" />
	<div align="left" style="color: red">
		* 以下为待分配设备，请
		<a href="javascript:affirm()"> 确认</a>
	</div>
	<display:table name="requestScope.list" id="currentRowObject">
		<display:column property="work_name" title="工程名称" />
		<display:column property="pipe_address" title="管道地点" />
		<display:column property="pipe_length_channel" title="管道规模(沟)" />
		<display:column property="pipe_length_hole" title="管道规模(孔)" />
		<display:column property="mobile_scare_channel" title="移动规模(沟)" />
		<display:column property="mobile_scare_hole" title="移动规模(孔)" />
		<display:column property="finishtime" title="交维日期" />
	</display:table>
</body>