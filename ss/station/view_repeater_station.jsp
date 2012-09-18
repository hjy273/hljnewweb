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
		<template:titile value="中继站信息" />
		<template:formTable namewidth="150" contentwidth="350">
			<template:formTr name="中继站名字">
				<bean:write name="station_bean" property="stationName" />
			</template:formTr>
			<template:formTr name="所属地州">
				<%=(String) request.getAttribute("region_name")%>
			</template:formTr>
			<template:formTr name="地下室中继站">
				<logic:equal value="0" name="station_bean"
					property="isGroundStation">
				是
				</logic:equal>
				<logic:equal value="1" name="station_bean"
					property="isGroundStation">
				否
				</logic:equal>
			</template:formTr>
			<template:formTr name="市电">
				<logic:equal value="0" name="station_bean" property="hasElectricity">
				是
				</logic:equal>
				<logic:equal value="1" name="station_bean" property="hasElectricity">
				否
				</logic:equal>
			</template:formTr>
			<template:formTr name="能否接市电">
				<logic:equal value="0" name="station_bean"
					property="connectElectricity">
				是
				</logic:equal>
				<logic:equal value="1" name="station_bean"
					property="connectElectricity">
				否
				</logic:equal>
			</template:formTr>
			<template:formTr name="空调">
				<logic:equal value="0" name="station_bean"
					property="hasAirCondition">
				是
				</logic:equal>
				<logic:equal value="1" name="station_bean"
					property="hasAirCondition">
				否
				</logic:equal>
			</template:formTr>
			<template:formTr name="能否接空调">
				<logic:equal value="0" name="station_bean"
					property="connectAirCondition">
				是
				</logic:equal>
				<logic:equal value="1" name="station_bean"
					property="connectAirCondition">
				否
				</logic:equal>
			</template:formTr>
			<template:formTr name="可否接风力发电">
				<logic:equal value="0" name="station_bean"
					property="connectWindPowerGenerate">
				是
				</logic:equal>
				<logic:equal value="1" name="station_bean"
					property="connectWindPowerGenerate">
				否
				</logic:equal>
			</template:formTr>
			<template:formTr name="备注">
				<bean:write name="station_bean" property="stationRemark" />
			</template:formTr>
			<template:formSubmit>
				<td>
					<input type="button" value="返回" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
				</td>
				<td style="text-align:center;">
					<input name="action" class="button" value="导出为Excel"
						onclick="exportOne()" type="button" />
				</td>
			</template:formSubmit>
		</template:formTable>
	</body>
</html>
