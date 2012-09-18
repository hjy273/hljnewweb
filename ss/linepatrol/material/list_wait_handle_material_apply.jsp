<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
	toViewApplyForm = function(idValue) {
		location.href = "${ctx}/material_apply.do?method=viewMaterialApply&&apply_id="
				+ idValue;
	}
	toEditApplyForm = function(idValue) {
		location.href = "${ctx}/material_apply.do?method=modMaterialApplyForm&&apply_id="
				+ idValue;
	}
	toDeleteApplyForm = function(idValue) {
		if (confirm("ȷʵҪɾ����������Ϣ��")) {
			location.href = "${ctx}/material_apply.do?method=delMaterialApply&&apply_id="
					+ idValue;
		}
	}
	toApproveApplyForm = function(idValue) {
		location.href = "${ctx}/material_apply.do?method=approveMaterialApplyForm&&apply_id="
				+ idValue;
	}
	toTransferApproveApplyForm = function(idValue) {
		location.href = "${ctx}/material_apply.do?method=transferApproveMaterialApplyForm&&apply_id="
				+ idValue;
	}
	toReadApplyForm = function(idValue) {
		location.href = "${ctx}/material_apply.do?method=readApproveMaterialApplyForm&&apply_id="
				+ idValue;
	}
	toPutStorageForm = function(idValue) {
		location.href = "${ctx}/material_put_storage.do?method=addMaterialPutStorageForm&&apply_id="
				+ idValue;
	}
	toEditPutStorageForm = function(idValue, subIdValue) {
		location.href = "${ctx}/material_put_storage.do?method=modMaterialPutStorageForm&&apply_id="
				+ idValue + "&&put_storage_id=" + subIdValue;
	}
	toDeletePutStorageForm = function(idValue) {
		if(confirm("ȷʵҪɾ���������Ϣ��")){
			location.href = "${ctx}/material_put_storage.do?method=delMaterialPutStorage&&put_storage_id="
				+ idValue;
		}
	}
	toApprovePutStorageForm = function(idValue, subIdValue) {
		location.href = "${ctx}/material_put_storage.do?method=approveMaterialPutStorageForm&&apply_id="
				+ idValue + "&&put_storage_id=" + subIdValue;
	}
	toTransferApprovePutStorageForm = function(idValue, subIdValue) {
		location.href = "${ctx}/material_put_storage.do?method=transferApproveMaterialPutStorageForm&&apply_id="
				+ idValue + "&&put_storage_id=" + subIdValue;
	}
	toReadPutStorageForm = function(idValue, subIdValue) {
		location.href = "${ctx}/material_put_storage.do?method=readApproveMaterialPutStorageForm&&apply_id="
				+ idValue + "&&put_storage_id=" + subIdValue;
	}
		//ȡ������
//		toCancelForm=function(applyId){
//			var url;
//			if(confirm("ȷ��Ҫȡ���ò�������������")){
//				url="${ctx}/material_apply.do?method=cancelMaterialApplyForm";
//				var queryString="apply_id="+applyId;
//				//location=url+"&"+queryString+"&rnd="+Math.random();
//				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
//			}
//		}
</script>
		<title>��������</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
.subject {
	text-align: center;
}
</style>
	</head>
	<body>
		<br />
		<logic:notEmpty name="task_name">
			<logic:equal value="apply_task" name="task_name">
				<template:titile
					value="���ύ��������(<font color='red'>${sessionScope.WAIT_HANDLE_NUM}������</font>)" />
			</logic:equal>
			<logic:equal value="apply_approve_task" name="task_name">
				<template:titile
					value="����˲�������(<font color='red'>${sessionScope.WAIT_HANDLE_NUM}������</font>)" />
			</logic:equal>
			<logic:equal value="put_storage_task" name="task_name">
				<template:titile
					value="���ύ�������(<font color='red'>${sessionScope.WAIT_HANDLE_NUM}������</font>)" />
			</logic:equal>
			<logic:equal value="put_storage_confirm_task" name="task_name">
				<template:titile
					value="����˲������(<font color='red'>${sessionScope.WAIT_HANDLE_NUM}������</font>)" />
			</logic:equal>
		</logic:notEmpty>
		<logic:empty name="task_name">
			<template:titile
				value="���칤��(<font color='red'>${sessionScope.WAIT_HANDLE_NUM}������</font>)" />
		</logic:empty>
		<div style="text-align: center;">
			<iframe
				src="${ctx}/material_apply.do?method=viewMaterialApplyProcess&&task_name=${param.task_name }"
				frameborder="0" id="processGraphFrame" height="100" scrolling="no"
				width="95%"></iframe>
		</div>
		<logic:notEmpty name="DATA_LIST">
			<display:table name="sessionScope.DATA_LIST" id="currentRowObject"
				pagesize="18">
				<bean:define name="currentRowObject" property="creator" id="sendUserId" />
				<bean:define id="id" name="currentRowObject" property="id"></bean:define>
				<bean:define id="applyState" name="currentRowObject" property="state"></bean:define>
				<bean:define id="flowState" name="currentRowObject"
					property="flow_state"></bean:define>
				<bean:define id="isReader" name="currentRowObject"
					property="is_reader"></bean:define>
				<bean:define id="storageId" name="currentRowObject"
					property="storage_id"></bean:define>
				<display:column property="title" sortable="true" title="����"
					headerClass="subject" />
				<display:column property="createtime" title="����ʱ��"
					headerClass="subject" sortable="true" />
				<display:column property="contractorname" sortable="true"
					title="��ά��˾" headerClass="subject" maxLength="10" />
				<display:column property="username" sortable="true" title="������"
					headerClass="subject" maxLength="10" />
				<display:column media="html" sortable="true" title="״̬"
					headerClass="subject">
					<c:if test="${flowState=='apply_task'}">δ�ύ</c:if>
					<c:if test="${flowState=='apply_approve_task'}">�����</c:if>
					<c:if test="${flowState=='put_storage_task'}">�ȴ����</c:if>
					<c:if test="${flowState=='put_storage_confirm_task'}">��ȷ��</c:if>
				</display:column>
				<display:column media="html" title="����">
					<a href="javascript:toViewApplyForm('${id}');">�鿴</a>
					<c:if test="${flowState=='apply_task'}">
						<a href="javascript:toEditApplyForm('${id}');">�޸�����</a>
						<a href="javascript:toDeleteApplyForm('${id}');">ɾ������</a>
					</c:if>
					<c:if test="${flowState=='apply_approve_task'}">
						<c:if test="${isReader=='1'}">
							<a href="javascript:toReadApplyForm('${id}');">��������</a>
						</c:if>
						<c:if test="${isReader=='0'}">
							<a href="javascript:toTransferApproveApplyForm('${id}');">ת������</a>
							<a href="javascript:toApproveApplyForm('${id}');">�������</a>
						</c:if>
					</c:if>
					<c:if test="${flowState=='put_storage_task'}">
						<c:if test="${storageId==-1}">
							<a href="javascript:toPutStorageForm('${id}');">��д���</a>
						</c:if>
						<c:if test="${storageId!=-1}">
							<a
								href="javascript:toEditPutStorageForm('${id}','${storageId }');">�޸����</a>
							<a href="javascript:toDeletePutStorageForm('${storageId}');">ɾ�����</a>
						</c:if>
					</c:if>
					<c:if test="${flowState=='put_storage_confirm_task'}">
						<c:if test="${isReader=='1'}">
							<a href="javascript:toReadPutStorageForm('${id}','${storageId }');">�������</a>
						</c:if>
						<c:if test="${isReader=='0'}">
							<a
								href="javascript:toTransferApprovePutStorageForm('${id}','${storageId }');">���ȷ��ת��</a>
							<a
								href="javascript:toApprovePutStorageForm('${id}','${storageId }');">���ȷ��</a>
						</c:if>
					</c:if>
				</display:column>
			</display:table>
		</logic:notEmpty>
	</body>
</html>
