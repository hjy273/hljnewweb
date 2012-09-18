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
		<template:titile value="导入巡检项浏览" />
		<display:table name="sessionScope.PREVIEW_IMPORT_LIST" id="row"
			pagesize="14" export="false"
			requestURI="${ctx }/patrolItemImportAction_previewImportData.jspx">
			<display:column title="维护对象" property="itemName" maxLength="15"
				sortable="false"></display:column>
			<display:column title="维护检测项目" property="subitemName" maxLength="15"
				sortable="false"></display:column>
			<display:column title="周期" property="cycleText" maxLength="15"
				sortable="false"></display:column>
			<display:column title="质量标准" property="qualityStandard"
				maxLength="15" sortable="false"></display:column>
			<display:column title="输入类型" property="inputTypeText" maxLength="15"
				sortable="false"></display:column>
			<display:column title="值域范围" property="valueScope" maxLength="15"
				sortable="false"></display:column>
			<display:column title="异常状态" property="exceptionValue" maxLength="15"
				sortable="false"></display:column>
			<display:column title="默认值" property="defaultValue" maxLength="15"
				sortable="false"></display:column>
		</display:table>

		<s:form action="patrolItemImportAction_checkImportData.jspx">
			<input type="hidden" name="filePath"
				value="${PATROLITEM_IMPORT_FILEPATH}" />
			<template:formSubmit>
				<div align="center">
					<input type="submit" class="button" value="确认导入">
					<input type="button" class="button" value="取消导入" onclick="cancel()">
				</div>
			</template:formSubmit>
		</s:form>
	</body>
</html>