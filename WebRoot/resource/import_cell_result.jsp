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
		<template:titile value="����С�����" />
		<c:if test="${import_data_map!=null}">
			<template:formTable contentwidth="300" namewidth="220">
				<template:formTr name="����С����������">
					<c:out value="${import_data_map.total_cell_number }" />
				</template:formTr>
				<template:formTr name="�Ϸ�С������">
					<c:out value="${import_data_map.valid_cell_number }" />
				</template:formTr>
				<template:formTr name="���Ϸ�С����������">
					<c:out value="${import_data_map.invalid_cell_number }" />
				</template:formTr>
				<template:formTr name="�ϸ���">
					<c:out value="${import_data_map.valid_ratio }" />
						%
				</template:formTr>
				<tr>
					<td width="100%" style="text-align:center" colspan="2">
						<input name="button" type="button" class=button_length
							value="�������Ϸ�С��" onclick="exportFile('F01');">
					</td>
				</tr>
			</template:formTable>
			<display:table name="sessionScope.CELL_IMPORTED_LIST" id="row"
				pagesize="18" export="fasle">
				<display:column title="վַ���" property="stationCode" maxLength="15"
					sortable="true">
				</display:column>
				<display:column property="id" title="С����" sortable="true" />
				<display:column property="chineseName" title="��������" maxLength="15"
					sortable="true" />
				<display:column title="�Ƿ��Ƶ" sortable="true">
					<c:if test="${row.isFrequencyHopping == 'y'}">��</c:if>
					<c:if test="${row.isFrequencyHopping != 'y'}">��</c:if>
				</display:column>
				<display:column title="�Ƿ��´�ֱ��վ" sortable="true">
					<c:if test="${row.isOwnRepeater == 'y'}">��</c:if>
					<c:if test="${row.isOwnRepeater != 'y'}">��</c:if>
				</display:column>
				<display:column title="�Ƿ��´�����" sortable="true">
					<c:if test="${row.isOwnTma == 'y'}">��</c:if>
					<c:if test="${row.isOwnTma != 'y'}">��</c:if>
				</display:column>
				<display:column title="�Ƿ���GPRS" sortable="true">
					<c:if test="${row.isOpenGPRS == 'y'}">��</c:if>
					<c:if test="${row.isOpenGPRS != 'y'}">��</c:if>
				</display:column>
				<display:column title="�Ƿ���EGPRS" sortable="true">
					<c:if test="${row.isOpenEGPRS == 'y'}">��</c:if>
					<c:if test="${row.isOpenEGPRS != 'y'}">��</c:if>
				</display:column>
				<display:column property="carrierFrequencyNum" title="��Ƶ��"
					maxLength="15" sortable="true" />
				<display:column property="anTime" title="����ʱ��"
					format="{0,date,yyyy��MM��dd��}" sortable="true" />
			</display:table>
		</c:if>
	</body>
</html>