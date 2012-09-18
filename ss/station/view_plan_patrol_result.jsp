<%@include file="/common/header.jsp"%>
<script type="text/javascript">
	addGoBackMod=function(){
		var url = "<%=request.getContextPath()%>/plan_patrol_result.do?method=viewWaitWrite&plan_id=<bean:write name='plan_bean' property='tid' />";
		self.location.replace(url);
	}
</script>
<logic:present name="patrol_bean">
	<template:formTable namewidth="150" contentwidth="350">
		<template:formTr name="�м�վ����">
			<%=request.getAttribute("station_name")%>
		</template:formTr>
		<template:formTr name="Ѳ������">
			<bean:write name='patrol_bean' property='patrolDate' />
		</template:formTr>
		<template:formTr name="Ѳ���ն˺���">
			<bean:write name='patrol_bean' property='simId' />
		</template:formTr>
		<template:formTr name="Ѳ��������">
			<bean:write name='patrol_bean' property='patrolmanName' />
		</template:formTr>
		<logic:equal value="01" name="plan_type">
			<template:formTr name="�豸�¶�(��)">
				<bean:write name='patrol_bean' property='terminalTemperature' />
			</template:formTr>
			<template:formTr name="�豸ʪ��(%)">
				<bean:write name='patrol_bean' property='terminalHumidity' />
			</template:formTr>
			<template:formTr name="��������">
				<logic:equal value="0" name='patrol_bean' property='machineClean'>
					��
				</logic:equal>
				<logic:equal value="1" name='patrol_bean' property='machineClean'>
					��
				</logic:equal>
			</template:formTr>
			<template:formTr name="���񶥶�ָʾ��״̬">
				<logic:equal value="0" name='patrol_bean' property='topPilotLamp'>
					��
				</logic:equal>
				<logic:equal value="1" name='patrol_bean' property='topPilotLamp'>
					��
				</logic:equal>
				<logic:equal value="2" name='patrol_bean' property='topPilotLamp'>
					��
				</logic:equal>
			</template:formTr>
			<template:formTr name="����ָʾ��״̬">
				<logic:equal value="0" name='patrol_bean' property='veneerPilotLamp'>
					��
				</logic:equal>
				<logic:equal value="1" name='patrol_bean' property='veneerPilotLamp'>
					��
				</logic:equal>
			</template:formTr>
			<template:formTr name="�豸�����¶�">
				<logic:equal value="0" name='patrol_bean'
					property='termialSurfaceTemperature'>
					����
				</logic:equal>
				<logic:equal value="1" name='patrol_bean'
					property='termialSurfaceTemperature'>
					��
				</logic:equal>
			</template:formTr>
			<template:formTr name="��ص�ѹ(V)">
				<bean:write name='patrol_bean' property='batterylVoltage' />
			</template:formTr>
		</logic:equal>
		<logic:equal value="02" name="plan_type">
			<template:formTr name="DDF�ܽ�ͷ�Ƿ��ɶ�">
				<logic:equal value="0" name='patrol_bean' property='isDdfLoosen'>
					��
				</logic:equal>
				<logic:equal value="1" name='patrol_bean' property='isDdfLoosen'>
					��
				</logic:equal>
			</template:formTr>
			<template:formTr name="ODF�ܽ�ͷ�Ƿ��ɶ�">
				<logic:equal value="0" name='patrol_bean' property='isOdfLoosen'>
					��
				</logic:equal>
				<logic:equal value="1" name='patrol_bean' property='isOdfLoosen'>
					��
				</logic:equal>
			</template:formTr>
			<template:formTr name="�豸����״̬��������">
				<bean:write name='patrol_bean' property='deviceFanStateCheckClean' />
			</template:formTr>
			<template:formTr name="��ͷ���Դ��˿���澯���">
				<bean:write name='patrol_bean' property='cabinetFuseAlarmCheck' />
			</template:formTr>
		</logic:equal>
		<logic:equal value="03" name="plan_type">
			<template:formTr name="�豸��������">
				<logic:equal value="0" name='patrol_bean'
					property='terminalSurfaceClean'>
					��
				</logic:equal>
				<logic:equal value="1" name='patrol_bean'
					property='terminalSurfaceClean'>
					��
				</logic:equal>
			</template:formTr>
			<template:formTr name="��������">
				<logic:equal value="0" name='patrol_bean' property='rackClean'>
					��
				</logic:equal>
				<logic:equal value="1" name='patrol_bean' property='rackClean'>
					��
				</logic:equal>
			</template:formTr>
			<template:formTr name="���߼�����">
				<logic:equal value="0" name='patrol_bean'
					property='distributionFrameClean'>
					��
				</logic:equal>
				<logic:equal value="1" name='patrol_bean'
					property='distributionFrameClean'>
					��
				</logic:equal>
			</template:formTr>
			<template:formTr name="̫���ܼ�������">
				<logic:equal value="0" name='patrol_bean'
					property='solarEnergyClean'>
					��
				</logic:equal>
				<logic:equal value="1" name='patrol_bean'
					property='solarEnergyClean'>
					��
				</logic:equal>
			</template:formTr>
		</logic:equal>
		<logic:equal value="04" name="plan_type">
			<template:formTr name="�������">
				<bean:write name='patrol_bean' property='batteryCapacitance' />
			</template:formTr>
			<template:formTr name="�������">
				<bean:write name='patrol_bean' property='resistancetestTest' />
			</template:formTr>
			<template:formTr name="�����������">
				<bean:write name='patrol_bean' property='machineResistancetestTest' />
			</template:formTr>
			<template:formTr name="��Դ�����Ӽ��">
				<bean:write name='patrol_bean' property='powerLineTest' />
			</template:formTr>
			<template:formTr name="�������Ӽ��">
				<bean:write name='patrol_bean' property='earthLineTest' />
			</template:formTr>
		</logic:equal>
		<template:formTr name="��ע">
			<bean:write name='patrol_bean' property='patrolRemark' />
		</template:formTr>
	</template:formTable>
</logic:present>
<logic:notPresent name="patrol_bean">
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr class="trcolor">
			<td>
				û����дѲ����Ϣ
			</td>
		</tr>
	</table>
</logic:notPresent>
