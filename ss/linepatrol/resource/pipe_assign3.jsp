<%@include file="/common/header.jsp"%>
<br>
<template:titile value="�ܵ����·���ȷ��ҳ��" />
<script language="javascript">
	function check(){
		var patrolID = document.all.targetMaintenanceId.value;
		var maintenanceId = document.getElementById("maintenanceId").value;
		if(patrolID != ""){
			if(patrolID==maintenanceId){
				alert("��ѡ��������ά��λ��");
				return false;
			}
			return true;
		}else{
			alert("��ѡ���ά��λ");
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
			��
			<apptag:setSelectOptions valueName="connCollection"
				tableName="contractorinfo" columnName1="contractorname"
				region="true" columnName2="contractorid" order="contractorname" />
			<html:select property="maintenanceId" disabled="true"
				styleClass="inputtext" styleId="rId" style="width:100px">
				<html:options collection="connCollection" property="value"
					labelProperty="label" />
			</html:select>
			��
			<html:select property="targetMaintenanceId" styleClass="inputtext"
				styleId="rId" style="width:200px">
				<html:options collection="connCollection" property="value"
					labelProperty="label" />
			</html:select>

		</div>
		<br>
		<div align="center">
			<input type="submit" class="button" value="����" disabled id="btt"
				onclick="return check()">
			<input type="button" value="��һ��" class="button"
				onclick="goAssign2()" />
		</div>
	</html:form>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
		media="screen, print" />
	<div align="left" style="color: red">
		* ����Ϊ�������豸����
		<a href="javascript:affirm()"> ȷ��</a>
	</div>
	<display:table name="requestScope.list" id="currentRowObject">
		<display:column property="work_name" title="��������" />
		<display:column property="pipe_address" title="�ܵ��ص�" />
		<display:column property="pipe_length_channel" title="�ܵ���ģ(��)" />
		<display:column property="pipe_length_hole" title="�ܵ���ģ(��)" />
		<display:column property="mobile_scare_channel" title="�ƶ���ģ(��)" />
		<display:column property="mobile_scare_hole" title="�ƶ���ģ(��)" />
		<display:column property="finishtime" title="��ά����" />
	</display:table>
</body>