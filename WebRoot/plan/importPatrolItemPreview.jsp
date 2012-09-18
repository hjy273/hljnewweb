<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<script type="text/javascript" src="${ctx }/js/prototype.js"></script>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<script type="text/javascript">
<!--
	function exportFile(cableType) {
		location.href = "/bspweb/patrolManager/patrolItemImportAction_exportCheckResultData.jspx";
	}
	function cancel() {
		history.back();
	}
//-->
</script>
	</head>
	<body>
		<template:titile value="����Ѳ�������" />
		<display:table name="sessionScope.PREVIEW_IMPORT_LIST" id="row"
			pagesize="14" export="false"
			requestURI="${ctx }/patrolItemImportAction_previewImportData.jspx">
			<display:column title="ά������" property="itemName" maxLength="15"
				sortable="false"></display:column>
			<display:column title="ά�������Ŀ" property="subitemName" maxLength="15"
				sortable="false"></display:column>
			<display:column title="����" property="cycleText" maxLength="15"
				sortable="false"></display:column>
			<display:column title="������׼" property="qualityStandard"
				maxLength="15" sortable="false"></display:column>
			<display:column title="��������" property="inputTypeText" maxLength="15"
				sortable="false"></display:column>
			<display:column title="ֵ��Χ" property="valueScope" maxLength="15"
				sortable="false"></display:column>
			<display:column title="�쳣״̬" property="exceptionValue" maxLength="15"
				sortable="false"></display:column>
			<display:column title="Ĭ��ֵ" property="defaultValue" maxLength="15"
				sortable="false"></display:column>
		</display:table>

		<s:form action="patrolItemImportAction_checkImportData.jspx">
			<input type="hidden" name="filePath"
				value="${PATROLITEM_IMPORT_FILEPATH}" />
			<template:formSubmit>
				<div align="center">
					<input type="submit" class="button" value="ȷ�ϵ���">
					<input type="button" class="button" value="ȡ������" onclick="cancel()">
				</div>
			</template:formSubmit>
		</s:form>
	</body>
</html>