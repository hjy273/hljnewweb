<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript" src="js/prototype.js"></script>
	<script type="text/javascript">
	addGoBackMod=function() {
		var url = "<%=request.getContextPath()%>/station_info.do?method=list";
		self.location.replace(url);
	}
	exportOne=function(){
		var url="<%=request.getContextPath()%>/station_info.do?method=exportOne";
		self.location.replace(url);
	}
	</script>
	<body>
		<br>
		<template:titile value="�м�վ��Ϣ" />
		<template:formTable namewidth="150" contentwidth="350">
			<template:formTr name="�м�վ����">
				<bean:write name="station_bean" property="stationName" />
			</template:formTr>
			<template:formTr name="��������">
				<%=(String) request.getAttribute("region_name")%>
			</template:formTr>
			<template:formTr name="�������м�վ">
				<logic:equal value="0" name="station_bean"
					property="isGroundStation">
				��
				</logic:equal>
				<logic:equal value="1" name="station_bean"
					property="isGroundStation">
				��
				</logic:equal>
			</template:formTr>
			<template:formTr name="�е�">
				<logic:equal value="0" name="station_bean" property="hasElectricity">
				��
				</logic:equal>
				<logic:equal value="1" name="station_bean" property="hasElectricity">
				��
				</logic:equal>
			</template:formTr>
			<template:formTr name="�ܷ���е�">
				<logic:equal value="0" name="station_bean"
					property="connectElectricity">
				��
				</logic:equal>
				<logic:equal value="1" name="station_bean"
					property="connectElectricity">
				��
				</logic:equal>
			</template:formTr>
			<template:formTr name="�յ�">
				<logic:equal value="0" name="station_bean"
					property="hasAirCondition">
				��
				</logic:equal>
				<logic:equal value="1" name="station_bean"
					property="hasAirCondition">
				��
				</logic:equal>
			</template:formTr>
			<template:formTr name="�ܷ�ӿյ�">
				<logic:equal value="0" name="station_bean"
					property="connectAirCondition">
				��
				</logic:equal>
				<logic:equal value="1" name="station_bean"
					property="connectAirCondition">
				��
				</logic:equal>
			</template:formTr>
			<template:formTr name="�ɷ�ӷ�������">
				<logic:equal value="0" name="station_bean"
					property="connectWindPowerGenerate">
				��
				</logic:equal>
				<logic:equal value="1" name="station_bean"
					property="connectWindPowerGenerate">
				��
				</logic:equal>
			</template:formTr>
			<template:formTr name="��ע">
				<bean:write name="station_bean" property="stationRemark" />
			</template:formTr>
			<template:formSubmit>
				<td>
					<input type="button" value="����" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
				</td>
				<td style="text-align:center;">
					<input name="action" class="button" value="����ΪExcel"
						onclick="exportOne()" type="button" />
				</td>
			</template:formSubmit>
		</template:formTable>
	</body>
</html>
