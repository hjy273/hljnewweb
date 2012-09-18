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
</script>
		<title>材料类型</title>
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
		<template:titile value="材料申请单查询一览表" />
		<logic:notEmpty name="DATA_LIST">
			<display:table name="sessionScope.DATA_LIST" id="currentRowObject"
				pagesize="18">
				<bean:define id="id" name="currentRowObject" property="id"></bean:define>
				<bean:define id="storageId" name="currentRowObject"
					property="storage_id"></bean:define>
				<bean:define id="applyState" name="currentRowObject" property="state"></bean:define>
				<display:column property="title" sortable="true" title="标题"
					headerClass="subject" />
				<display:column property="createtime" title="申请时间"
					headerClass="subject" sortable="true" />
				<display:column property="contractorname" sortable="true"
					title="代维公司" headerClass="subject" maxLength="10" />
				<display:column property="username" sortable="true" title="申请人"
					headerClass="subject" maxLength="10" />
				<display:column media="html" title="操作">
					<a href="javascript:toViewApplyForm('${id}');">查看</a>
					<c:if test="${sessionScope.LOGIN_USER.deptype=='1'&&applyState!='999'&&applyState!='08'}">
						| <a href="javascript:toCancelForm('<%=id%>')">取消</a>
					</c:if>
				</display:column>
			</display:table>
		</logic:notEmpty>
	</body>
</html>
