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
	location.href = "/bspweb/cell/cellImportAction_exportCheckResultData.jspx";
}
//-->
</script>
	</head>
	<body>
		<template:titile value="导入小区结果" />
		<c:if test="${import_data_map!=null}">
			<template:formTable contentwidth="300" namewidth="220">
				<template:formTr name="导入小区数据总数">
					<c:out value="${import_data_map.total_cell_number }" />
				</template:formTr>
				<template:formTr name="合法小区总数">
					<c:out value="${import_data_map.valid_cell_number }" />
				</template:formTr>
				<template:formTr name="不合法小区数据总数">
					<c:out value="${import_data_map.invalid_cell_number }" />
				</template:formTr>
				<template:formTr name="合格率">
					<c:out value="${import_data_map.valid_ratio }" />
						%
				</template:formTr>
				<tr>
					<td width="100%" style="text-align:center" colspan="2">
						<input name="button" type="button" class=button_length
							value="导出不合法小区" onclick="exportFile('F01');">
					</td>
				</tr>
			</template:formTable>
			<display:table name="sessionScope.CELL_IMPORTED_LIST" id="row"
				pagesize="18" export="fasle">
				<display:column title="站址编号" property="stationCode" maxLength="15"
					sortable="true">
				</display:column>
				<display:column property="id" title="小区号" sortable="true" />
				<display:column property="chineseName" title="中文名称" maxLength="15"
					sortable="true" />
				<display:column title="是否调频" sortable="true">
					<c:if test="${row.isFrequencyHopping == 'y'}">是</c:if>
					<c:if test="${row.isFrequencyHopping != 'y'}">否</c:if>
				</display:column>
				<display:column title="是否下带直放站" sortable="true">
					<c:if test="${row.isOwnRepeater == 'y'}">是</c:if>
					<c:if test="${row.isOwnRepeater != 'y'}">否</c:if>
				</display:column>
				<display:column title="是否下带塔放" sortable="true">
					<c:if test="${row.isOwnTma == 'y'}">是</c:if>
					<c:if test="${row.isOwnTma != 'y'}">否</c:if>
				</display:column>
				<display:column title="是否开启GPRS" sortable="true">
					<c:if test="${row.isOpenGPRS == 'y'}">是</c:if>
					<c:if test="${row.isOpenGPRS != 'y'}">否</c:if>
				</display:column>
				<display:column title="是否开启EGPRS" sortable="true">
					<c:if test="${row.isOpenEGPRS == 'y'}">是</c:if>
					<c:if test="${row.isOpenEGPRS != 'y'}">否</c:if>
				</display:column>
				<display:column property="carrierFrequencyNum" title="载频数"
					maxLength="15" sortable="true" />
				<display:column property="anTime" title="入网时间"
					format="{0,date,yyyy年MM月dd日}" sortable="true" />
			</display:table>
		</c:if>
	</body>
</html>