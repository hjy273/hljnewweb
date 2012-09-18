<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/validate.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/prototype.js"></script>
	<script type="text/javascript">
	var nowDate=new Date(<%=new java.util.Date().getYear() + 1900%>,<%=new java.util.Date().getMonth()%>,<%=new java.util.Date().getDate()%>);
	checkValid=function(addForm){
		flag=judgeEmptyValue(addForm.patrolDate,"Ѳ������");
		flag=flag&&judgeOnlyEmptyValue(addForm.simId,"Ѳ���ն˺���");
		flag=flag&&judgeEmptyValue(addForm.patrolmanName,"Ѳ��������");
		var beginDateValue=convertStrToDate("<bean:write name='plan_bean' property='beginDate' />"," ","/");
		var endDateValue=convertStrToDate("<bean:write name='plan_bean' property='endDate' />"," ","/");
		var patrolDateValue=convertStrToDate(addForm.patrolDate.value," ","/");
		flag=flag&&beforeDate(beginDateValue,patrolDateValue,"Ѳ�����ڲ���С�ڿ�ʼ���ڣ�");
		flag=flag&&beforeDate(patrolDateValue,endDateValue,"Ѳ�����ڲ��ܴ��ڽ������ڣ�");
		flag=flag&&beforeDate(patrolDateValue,nowDate,"Ѳ�����ڲ��ܴ����������ڣ�");
		return flag;
	}
	addGoBackMod=function(){
		var url = "<%=request.getContextPath()%>/plan_patrol_result.do?method=viewWaitWrite&plan_id=<bean:write name='plan_bean' property='tid' />";
		self.location.replace(url);
	}
	getSelectDateThis=function(strID) {
		var addForm=document.addForm;
		document.all.item(strID).value = getPopDate(document.all.item(strID).value);
		if(document.all.item(strID).value !="" && document.all.item(strID).value !=null){
			//if(checkBDate()){
				//preGetWeeklyTaskList();
				//setPlanName();
			//}
		}
		return true;
	}
	loadTerminal=function(patrolId){
		var actionUrl="<%=request.getContextPath()%>/plan_patrol_result.do?method=loadTerminalByPatrolMan&patrolman_id="+patrolId+"&"+Math.random();
		new Ajax.Request(actionUrl, {method:"post", onSuccess:function (transport) {
			terminalSpan.innerHTML=transport.responseText;
		}, onFailure:function () {
			terminalSpan.innerHTML="û��Ѳ���ն˺��룡";
		}, evalScript:true });
	}
	loadTerminalSync=function(patrolId){
		var actionUrl="<%=request.getContextPath()%>/plan_patrol_result.do?method=loadTerminalByPatrolMan&patrolman_id="+patrolId+"&"+Math.random();
		new Ajax.Request(actionUrl, {method:"post", onSuccess:function (transport) {
			terminalSpan.innerHTML=transport.responseText;
		}, onFailure:function () {
			terminalSpan.innerHTML="û��Ѳ���ն˺��룡";
		}, evalScript:true, asynchronous:false });
	}
	</script>
	<body>
		<template:titile value="��д�м�վѲ��ƻ�" />
		<html:form action="/plan_patrol_result.do?method=write"
			styleId="writeForm" method="post" onsubmit="return checkValid(this);">
			<template:formTable namewidth="150" contentwidth="350">
				<template:formTr name="�ƻ�����">
					<bean:write name="plan_bean" property="planName" />
				</template:formTr>
				<template:formTr name="��������">
					<%=(String) request.getAttribute("region_name")%>
				</template:formTr>
				<template:formTr name="��ʼ����">
					<bean:write name="plan_bean" property="beginDate" />
				</template:formTr>
				<template:formTr name="��������">
					<bean:write name="plan_bean" property="endDate" />
				</template:formTr>
				<template:formTr name="�ƻ�����">
					<logic:equal name="plan_bean" property="planType" value="01">
					�����ռƻ�
				</logic:equal>
					<logic:equal name="plan_bean" property="planType" value="02">
					�����ܼƻ�
				</logic:equal>
					<logic:equal name="plan_bean" property="planType" value="03">
					�����¼ƻ�
				</logic:equal>
					<logic:equal name="plan_bean" property="planType" value="04">
					���ְ���ƻ�
				</logic:equal>
				</template:formTr>
				<template:formTr name="�м�վ����">
					<%=request.getAttribute("station_name")%>
				</template:formTr>
				<template:formTr name="������">
					<%=request.getAttribute("validate_user_name")%>
				</template:formTr>
				<template:formTr name="������ע">
					<bean:write name="plan_bean" property="validateRemark" />
				</template:formTr>
				<template:formTr name="Ѳ������">
					<input name="tid" value="" type="hidden" />
					<input name="planId" value="" type="hidden" />
					<input name="repeaterStationId" value="" type="hidden" />
					<input name="plan_type" value="" type="hidden" />
					<html:text property="patrolDate" styleClass="inputtext"
						style="width:225;" readonly="true" />
					<INPUT TYPE='BUTTON' VALUE='��' ID='btn'
						onclick="getSelectDateThis('patrolDate')"
						STYLE="font:'normal small-caps 6pt serif';">
				</template:formTr>
				<template:formTr name="Ѳ��������">
					<select name="patrol_group" class="inputtext" style="width:250;"
						onchange="loadTerminal(this.value);">
						<option value="">
							��ѡ��Ѳ��������
						</option>
						<logic:present name="patrolman_list" scope="request">
							<logic:iterate id="item" name="patrolman_list" scope="request">
								<option value="<bean:write name="item" property="patrolID"/>">
									<bean:write name="item" property="patrolName" />
								</option>
							</logic:iterate>
						</logic:present>
					</select>
				</template:formTr>
				<template:formTr name="Ѳ��������">
					<input name="patrolmanName" value="" class="inputtext" type="text"
						style="width:250;" maxlength="25" />
				</template:formTr>
				<template:formTr name="Ѳ���ն˺���">
					<span id="terminalSpan"><select name="simId"
							class="inputtext" style="width:250;">
							<option value="">
								��ѡ��Ѳ���ն˺���
							</option>
						</select>
					</span>
				</template:formTr>
				<logic:equal value="01" name="plan_bean" property="planType">
					<template:formTr name="�豸�¶�(��)">
						<input name="terminalTemperature" type="text" value=""
							style="width:250;" class="inputtext" />
					</template:formTr>
					<template:formTr name="�豸ʪ��(%)">
						<input name="terminalHumidity" type="text" value=""
							style="width:250;" class="inputtext" />
					</template:formTr>
					<template:formTr name="��������">
						<html:select property="machineClean" styleClass="inputtext"
							style="width:250;">
							<html:option value="0">��</html:option>
							<html:option value="1">��</html:option>
						</html:select>
					</template:formTr>
					<template:formTr name="���񶥶�ָʾ��״̬">
						<html:select property="topPilotLamp" styleClass="inputtext"
							style="width:250;">
							<html:option value="0">��</html:option>
							<html:option value="1">��</html:option>
							<html:option value="2">��</html:option>
						</html:select>
					</template:formTr>
					<template:formTr name="����ָʾ��״̬">
						<html:select property="veneerPilotLamp" styleClass="inputtext"
							style="width:250;">
							<html:option value="0">��</html:option>
							<html:option value="1">��</html:option>
						</html:select>
					</template:formTr>
					<template:formTr name="�豸�����¶�">
						<html:select property="termialSurfaceTemperature"
							style="width:250;" styleClass="inputtext">
							<html:option value="0">����</html:option>
							<html:option value="1">��</html:option>
						</html:select>
					</template:formTr>
					<template:formTr name="��ص�ѹ(V)">
						<input name="batterylVoltage" type="text" class="inputtext"
							value="" style="width:250;" />
					</template:formTr>
				</logic:equal>
				<logic:equal value="02" name="plan_bean" property="planType">
					<template:formTr name="DDF�ܽ�ͷ�Ƿ��ɶ�">
						<html:select property="isDdfLoosen" styleClass="inputtext"
							style="width:250;">
							<html:option value="0">��</html:option>
							<html:option value="1">��</html:option>
						</html:select>
					</template:formTr>
					<template:formTr name="ODF�ܽ�ͷ�Ƿ��ɶ�">
						<html:select property="isOdfLoosen" styleClass="inputtext"
							style="width:250;">
							<html:option value="0">��</html:option>
							<html:option value="1">��</html:option>
						</html:select>
					</template:formTr>
					<template:formTr name="�豸����״̬��������">
						<input name="deviceFanStateCheckClean" class="inputtext"
							type="text" value="" style="width:250;" />
					</template:formTr>
					<template:formTr name="��ͷ���Դ��˿���澯���">
						<input name="cabinetFuseAlarmCheck" class="inputtext" type="text"
							value="" style="width:250;" />
					</template:formTr>
				</logic:equal>
				<logic:equal value="03" name="plan_bean" property="planType">
					<template:formTr name="�豸��������">
						<html:select property="terminalSurfaceClean"
							styleClass="inputtext" style="width:250;">
							<html:option value="0">��</html:option>
							<html:option value="1">��</html:option>
						</html:select>
					</template:formTr>
					<template:formTr name="��������">
						<html:select property="rackClean" styleClass="inputtext"
							style="width:250;">
							<html:option value="0">��</html:option>
							<html:option value="1">��</html:option>
						</html:select>
					</template:formTr>
					<template:formTr name="���߼�����">
						<html:select property="distributionFrameClean"
							styleClass="inputtext" style="width:250;">
							<html:option value="0">��</html:option>
							<html:option value="1">��</html:option>
						</html:select>
					</template:formTr>
					<template:formTr name="̫���ܼ�������">
						<html:select property="solarEnergyClean" styleClass="inputtext"
							style="width:250;">
							<html:option value="0">��</html:option>
							<html:option value="1">��</html:option>
						</html:select>
					</template:formTr>
				</logic:equal>
				<logic:equal value="04" name="plan_bean" property="planType">
					<template:formTr name="�������">
						<input name="batteryCapacitance" class="inputtext" type="text"
							value="" style="width:250;" />
					</template:formTr>
					<template:formTr name="�������">
						<input name="resistancetestTest" class="inputtext" type="text"
							value="" style="width:250;" />
					</template:formTr>
					<template:formTr name="�����������">
						<input name="machineResistancetestTest" class="inputtext"
							type="text" value="" style="width:250;" />
					</template:formTr>
					<template:formTr name="��Դ�����Ӽ��">
						<input name="powerLineTest" type="text" class="inputtext" value=""
							style="width:250;" />
					</template:formTr>
					<template:formTr name="�������Ӽ��">
						<input name="earthLineTest" type="text" class="inputtext" value=""
							style="width:250;" />
					</template:formTr>
				</logic:equal>
				<template:formTr name="��ע">
					<html:textarea property="patrolRemark" styleClass="inputtextarea"
						style="width:250;" rows="5" />
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:submit styleClass="button">�ύ</html:submit>
					</td>
					<td>
						<html:reset styleClass="button">ȡ��	</html:reset>
					</td>
					<td>
						<html:button property="action" styleClass="button"
							onclick="addGoBackMod();">����</html:button>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
		<script type="text/javascript">
		var writeInfoForm=document.writeForm;
		writeInfoForm.planId.value="<bean:write name='plan_bean' property='tid' />";
		writeInfoForm.repeaterStationId.value="<%=(String) request.getAttribute("station_id")%>";
		writeInfoForm.plan_type.value="<bean:write name='plan_bean' property='planType' />";
		</script>
		<logic:present name="patrol_bean">
			<script type="text/javascript">
			writeInfoForm.tid.value="<bean:write name='patrol_bean' property='tid' />";
			writeInfoForm.patrolDate.value="<bean:write name='patrol_bean' property='patrolDate' />";
			writeInfoForm.patrol_group.value="<%=(String)request.getAttribute("patrol_id")%>";
			loadTerminalSync(writeInfoForm.patrol_group.value);
			writeInfoForm.simId.value="<bean:write name='patrol_bean' property='simId' />";
			writeInfoForm.patrolmanName.value="<bean:write name='patrol_bean' property='patrolmanName' />";
			writeInfoForm.patrolRemark.value="<bean:write name='patrol_bean' property='patrolRemark' />";
			</script>
			<logic:equal value="01" name="plan_bean" property="planType">
				<script type="text/javascript">
				writeInfoForm.terminalTemperature.value="<bean:write name='patrol_bean' property='terminalTemperature' />";
				writeInfoForm.terminalHumidity.value="<bean:write name='patrol_bean' property='terminalHumidity' />";
				writeInfoForm.machineClean.value="<bean:write name='patrol_bean' property='machineClean' />";
				writeInfoForm.topPilotLamp.value="<bean:write name='patrol_bean' property='topPilotLamp' />";
				writeInfoForm.veneerPilotLamp.value="<bean:write name='patrol_bean' property='veneerPilotLamp' />";
				writeInfoForm.termialSurfaceTemperature.value="<bean:write name='patrol_bean' property='termialSurfaceTemperature' />";
				writeInfoForm.batterylVoltage.value="<bean:write name='patrol_bean' property='batterylVoltage' />";
				</script>
			</logic:equal>
			<logic:equal value="02" name="plan_bean" property="planType">
				<script type="text/javascript">
				writeInfoForm.isDdfLoosen.value="<bean:write name='patrol_bean' property='isDdfLoosen' />";
				writeInfoForm.isOdfLoosen.value="<bean:write name='patrol_bean' property='isOdfLoosen' />";
				writeInfoForm.deviceFanStateCheckClean.value="<bean:write name='patrol_bean' property='deviceFanStateCheckClean' />";
				writeInfoForm.cabinetFuseAlarmCheck.value="<bean:write name='patrol_bean' property='cabinetFuseAlarmCheck' />";
				</script>
			</logic:equal>
			<logic:equal value="03" name="plan_bean" property="planType">
				<script type="text/javascript">
				writeInfoForm.terminalSurfaceClean.value="<bean:write name='patrol_bean' property='terminalSurfaceClean' />";
				writeInfoForm.rackClean.value="<bean:write name='patrol_bean' property='rackClean' />";
				writeInfoForm.distributionFrameClean.value="<bean:write name='patrol_bean' property='distributionFrameClean' />";
				writeInfoForm.solarEnergyClean.value="<bean:write name='patrol_bean' property='solarEnergyClean' />";
				</script>
			</logic:equal>
			<logic:equal value="04" name="plan_bean" property="planType">
				<script type="text/javascript">
				writeInfoForm.batteryCapacitance.value="<bean:write name='patrol_bean' property='batteryCapacitance' />";
				writeInfoForm.resistancetestTest.value="<bean:write name='patrol_bean' property='resistancetestTest' />";
				writeInfoForm.machineResistancetestTest.value="<bean:write name='patrol_bean' property='machineResistancetestTest' />";
				writeInfoForm.powerLineTest.value="<bean:write name='patrol_bean' property='powerLineTest' />";
				writeInfoForm.earthLineTest.value="<bean:write name='patrol_bean' property='earthLineTest' />";
				</script>
			</logic:equal>
		</logic:present>
	</body>
</html>
