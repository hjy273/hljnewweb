<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<script type="text/javascript">
<!--
	function exportFile(cableType) {
		location.href = "${ctx}/patrolManager/patrolItemImportAction_exportCheckResultData.jspx";
	}
	function goBack() {
		location.href = "${ctx}/patrolManager/patrolItemImportAction_input.jspx?businessType=${businessType}";
	}
//-->
</script>
	</head>
	<body>
		<template:titile value="导入巡检项结果" />
		<c:if test="${sessionScope.import_data_map!=null}">
			<template:formTable contentwidth="300" namewidth="220">
				<template:formTr name="导入巡检项数据总数">
					<c:out value="${sessionScope.import_data_map.total_cell_number }" />
				</template:formTr>
				<template:formTr name="合法巡检项数据总数">
					<c:out value="${sessionScope.import_data_map.valid_cell_number }" />
				</template:formTr>
				<tr class="trwhite">
					<td class="tdulleft">
						<font color="red">不合法巡检项数据总数：</font>
					</td>
					<td class="tdulright">
						<font color="red"><c:out
								value="${sessionScope.import_data_map.invalid_cell_number }" />
						</font>
					</td>
				</tr>
				<template:formTr name="合格率">
					<c:out value="${sessionScope.import_data_map.valid_ratio }" />
						%
				</template:formTr>
				<c:if test="${sessionScope.import_data_map.valid_ratio!='100.00'}">
					<tr>
						<td width="100%" style="text-align: center" colspan="2">
							<input name="button" type="button" class=button_length
								value="导出不合法巡检项" onclick="exportFile('F01');">
							<input name="button" type="button" class=button value="返回"
								onclick="goBack();">
						</td>
					</tr>
				</c:if>
			</template:formTable>
			<display:table name="sessionScope.CELL_IMPORTED_LIST" id="row"
				pagesize="18" export="fasle">
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
				<display:column title="异常状态" property="exceptionValue"
					maxLength="15" sortable="false"></display:column>
				<display:column title="默认值" property="defaultValue" maxLength="15"
					sortable="false"></display:column>
			</display:table>
		</c:if>
	</body>
</html>