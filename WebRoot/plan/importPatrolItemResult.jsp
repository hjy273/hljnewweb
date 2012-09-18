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
		<template:titile value="����Ѳ������" />
		<c:if test="${sessionScope.import_data_map!=null}">
			<template:formTable contentwidth="300" namewidth="220">
				<template:formTr name="����Ѳ������������">
					<c:out value="${sessionScope.import_data_map.total_cell_number }" />
				</template:formTr>
				<template:formTr name="�Ϸ�Ѳ������������">
					<c:out value="${sessionScope.import_data_map.valid_cell_number }" />
				</template:formTr>
				<tr class="trwhite">
					<td class="tdulleft">
						<font color="red">���Ϸ�Ѳ��������������</font>
					</td>
					<td class="tdulright">
						<font color="red"><c:out
								value="${sessionScope.import_data_map.invalid_cell_number }" />
						</font>
					</td>
				</tr>
				<template:formTr name="�ϸ���">
					<c:out value="${sessionScope.import_data_map.valid_ratio }" />
						%
				</template:formTr>
				<c:if test="${sessionScope.import_data_map.valid_ratio!='100.00'}">
					<tr>
						<td width="100%" style="text-align: center" colspan="2">
							<input name="button" type="button" class=button_length
								value="�������Ϸ�Ѳ����" onclick="exportFile('F01');">
							<input name="button" type="button" class=button value="����"
								onclick="goBack();">
						</td>
					</tr>
				</c:if>
			</template:formTable>
			<display:table name="sessionScope.CELL_IMPORTED_LIST" id="row"
				pagesize="18" export="fasle">
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
				<display:column title="�쳣״̬" property="exceptionValue"
					maxLength="15" sortable="false"></display:column>
				<display:column title="Ĭ��ֵ" property="defaultValue" maxLength="15"
					sortable="false"></display:column>
			</display:table>
		</c:if>
	</body>
</html>