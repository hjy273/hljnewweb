<%@include file="/common/header.jsp"%>
<script type="text/javascript">
	addGoBackMod=function(){
		var url = "<%=request.getContextPath()%>/plan_patrol_result.do?method=viewWaitWrite&plan_id=<bean:write name='plan_bean' property='tid' />";
		self.location.replace(url);
	}
</script>
<logic:present name="patrol_bean">
	<template:formTable namewidth="150" contentwidth="350">
		<template:formTr name="中继站名称">
			<%=request.getAttribute("station_name")%>
		</template:formTr>
		<template:formTr name="巡检日期">
			<bean:write name='patrol_bean' property='patrolDate' />
		</template:formTr>
		<template:formTr name="巡检终端号码">
			<bean:write name='patrol_bean' property='simId' />
		</template:formTr>
		<template:formTr name="巡检人姓名">
			<bean:write name='patrol_bean' property='patrolmanName' />
		</template:formTr>
		<logic:equal value="01" name="plan_type">
			<template:formTr name="设备温度(℃)">
				<bean:write name='patrol_bean' property='terminalTemperature' />
			</template:formTr>
			<template:formTr name="设备湿度(%)">
				<bean:write name='patrol_bean' property='terminalHumidity' />
			</template:formTr>
			<template:formTr name="机房清洁度">
				<logic:equal value="0" name='patrol_bean' property='machineClean'>
					好
				</logic:equal>
				<logic:equal value="1" name='patrol_bean' property='machineClean'>
					差
				</logic:equal>
			</template:formTr>
			<template:formTr name="机柜顶端指示灯状态">
				<logic:equal value="0" name='patrol_bean' property='topPilotLamp'>
					红
				</logic:equal>
				<logic:equal value="1" name='patrol_bean' property='topPilotLamp'>
					绿
				</logic:equal>
				<logic:equal value="2" name='patrol_bean' property='topPilotLamp'>
					黄
				</logic:equal>
			</template:formTr>
			<template:formTr name="单板指示灯状态">
				<logic:equal value="0" name='patrol_bean' property='veneerPilotLamp'>
					红
				</logic:equal>
				<logic:equal value="1" name='patrol_bean' property='veneerPilotLamp'>
					绿
				</logic:equal>
			</template:formTr>
			<template:formTr name="设备表面温度">
				<logic:equal value="0" name='patrol_bean'
					property='termialSurfaceTemperature'>
					正常
				</logic:equal>
				<logic:equal value="1" name='patrol_bean'
					property='termialSurfaceTemperature'>
					高
				</logic:equal>
			</template:formTr>
			<template:formTr name="电池电压(V)">
				<bean:write name='patrol_bean' property='batterylVoltage' />
			</template:formTr>
		</logic:equal>
		<logic:equal value="02" name="plan_type">
			<template:formTr name="DDF架接头是否松动">
				<logic:equal value="0" name='patrol_bean' property='isDdfLoosen'>
					是
				</logic:equal>
				<logic:equal value="1" name='patrol_bean' property='isDdfLoosen'>
					否
				</logic:equal>
			</template:formTr>
			<template:formTr name="ODF架接头是否松动">
				<logic:equal value="0" name='patrol_bean' property='isOdfLoosen'>
					是
				</logic:equal>
				<logic:equal value="1" name='patrol_bean' property='isOdfLoosen'>
					否
				</logic:equal>
			</template:formTr>
			<template:formTr name="设备风扇状态检查与清洁">
				<bean:write name='patrol_bean' property='deviceFanStateCheckClean' />
			</template:formTr>
			<template:formTr name="列头柜电源熔丝及告警检查">
				<bean:write name='patrol_bean' property='cabinetFuseAlarmCheck' />
			</template:formTr>
		</logic:equal>
		<logic:equal value="03" name="plan_type">
			<template:formTr name="设备表面清洁度">
				<logic:equal value="0" name='patrol_bean'
					property='terminalSurfaceClean'>
					是
				</logic:equal>
				<logic:equal value="1" name='patrol_bean'
					property='terminalSurfaceClean'>
					否
				</logic:equal>
			</template:formTr>
			<template:formTr name="机架清洁度">
				<logic:equal value="0" name='patrol_bean' property='rackClean'>
					是
				</logic:equal>
				<logic:equal value="1" name='patrol_bean' property='rackClean'>
					否
				</logic:equal>
			</template:formTr>
			<template:formTr name="配线架清洁度">
				<logic:equal value="0" name='patrol_bean'
					property='distributionFrameClean'>
					是
				</logic:equal>
				<logic:equal value="1" name='patrol_bean'
					property='distributionFrameClean'>
					否
				</logic:equal>
			</template:formTr>
			<template:formTr name="太阳能极板清洁度">
				<logic:equal value="0" name='patrol_bean'
					property='solarEnergyClean'>
					是
				</logic:equal>
				<logic:equal value="1" name='patrol_bean'
					property='solarEnergyClean'>
					否
				</logic:equal>
			</template:formTr>
		</logic:equal>
		<logic:equal value="04" name="plan_type">
			<template:formTr name="电池容量">
				<bean:write name='patrol_bean' property='batteryCapacitance' />
			</template:formTr>
			<template:formTr name="内阻测试">
				<bean:write name='patrol_bean' property='resistancetestTest' />
			</template:formTr>
			<template:formTr name="机房地阻测试">
				<bean:write name='patrol_bean' property='machineResistancetestTest' />
			</template:formTr>
			<template:formTr name="电源线连接检查">
				<bean:write name='patrol_bean' property='powerLineTest' />
			</template:formTr>
			<template:formTr name="地线连接检查">
				<bean:write name='patrol_bean' property='earthLineTest' />
			</template:formTr>
		</logic:equal>
		<template:formTr name="备注">
			<bean:write name='patrol_bean' property='patrolRemark' />
		</template:formTr>
	</template:formTable>
</logic:present>
<logic:notPresent name="patrol_bean">
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr class="trcolor">
			<td>
				没有填写巡检信息
			</td>
		</tr>
	</table>
</logic:notPresent>
