<!-- 代维填写巡检内容（接入层微波） -->
<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.machine.beans.PollingConMicroBean;"%>
<html>
	<head>
		<title></title>
		
		<script type="text/javascript">
			function checkInfo() {
				var obveTemperatureEle = document.getElementById('obveTemperature');
				if(obveTemperatureEle.value.length == 0) {
					alert("请输入设备表面温度!");
					obveTemperatureEle.focus();
					return;
				}
				
				var fanStateEle = document.getElementById('fanState');
				if(fanStateEle.value.length == 0) {
					alert("请输入风扇运行状态!");
					fanStateEle.focus();
					return;
				}
				
				var outdoorFastEle = document.getElementById('outdoorFast');
				if(outdoorFastEle.value.length == 0) {
					alert("请输入室外单元检查加固!");
					outdoorFastEle.focus();
					return;
				}
				
				var machineTemperatureEle = document.getElementById('machineTemperature');
				if(machineTemperatureEle.value.length == 0) {
					alert("请输入机房温度(正常15～30℃)!");
					machineTemperatureEle.focus();
					return;
				}
				
				var machineHumidityEle = document.getElementById('machineHumidity');
				if(machineHumidityEle.value.length == 0) {
					alert("请输入机房湿度(正常40％～65％)!");
					machineHumidityEle.focus();
					return;
				}
				
				var ddfColligationEle = document.getElementById('ddfColligation');
				if(ddfColligationEle.value.length == 0) {
					alert("请输入DDF架/走线架线缆绑扎!");
					ddfColligationEle.focus();
					return;
				}
				
				var labelCheckEle = document.getElementById('labelCheck');
				if(labelCheckEle.value.length == 0) {
					alert("请输入2M线缆、馈线接头检查紧固!");
					labelCheckEle.focus();
					return;
				}
				
				var cabelCheckEle = document.getElementById('cabelCheck');
				if(cabelCheckEle.value.length == 0) {
					alert("请输入2M线缆标签核查补贴!");
					cabelCheckEle.focus();
					return;
				}
				
				addContentFormId.submit();
			}
			
			function goBack(type,tid) {
				var url = "PollingTaskAction.do?method=gobackToPrePageForRestore&type="+type+"&tid="+tid;
				window.location.href=url;
			}
		</script>
	</head>
	
	<body>
		<br>
		<template:titile value="添加单个机房设备巡检内容"/>
		<template:formTable contentwidth="320" namewidth="280" th2="内容">
			<template:formTr name="类型">
				接入层微波
			</template:formTr>
			
			<template:formTr name="主题">
				<bean:write name="mobileTaskBean" property="title" scope="request"/>
			</template:formTr>
			
			<template:formTr name="代维公司">
				<bean:write name="mobileTaskBean" property="conname" scope="request"/>
			</template:formTr>
			
			<template:formTr name="执行人">
				<bean:write name="mobileTaskBean" property="userconname" scope="request"/>
			</template:formTr>
			
			<template:formTr name="执行日期">
				<bean:write name="mobileTaskBean" property="executetime" scope="request"/>
			</template:formTr>
			
			<template:formTr name="检查人">
				<bean:write name="mobileTaskBean" property="checkusername" scope="request"/>
			</template:formTr>
			
			<template:formTr name="备注">
				<bean:write name="mobileTaskBean" property="remark" scope="request"/>
			</template:formTr>
		</template:formTable>
		<hr>
		<template:formTable contentwidth="320" namewidth="280">
			<html:form action="PollingConMicroAction.do?method=addContentForMiro"
			styleId="addContentFormId">
				<template:formTr name="设备表面清洁">
					<select  class="inputtext" style="width: 200px;" name="isClean">
						<option value="1">是</option>
						<option value="0">否</option>
					</select>
				</template:formTr>
				
				<template:formTr name="设备表面温度">
					<input type="text" class="inputtext" style="width: 200px;" name="obveTemperature" id="obveTemperature" maxlength="10">
				</template:formTr>
				
				<template:formTr name="机柜顶端指示灯状态">
					<select  class="inputtext" style="width: 200px;" name="isToppilotlamp">
						<option value="1">亮</option>
						<option value="0">灭</option>
					</select>
				</template:formTr>
				
				<template:formTr name="单板指示灯状态">
					<select  class="inputtext" style="width: 200px;" name="isVeneerpilotlamp">
						<option value="1">亮</option>
						<option value="0">灭</option>
					</select>
				</template:formTr>
				
				<template:formTr name="设备防尘网清洁">
					<select  class="inputtext" style="width: 200px;" name="isDustproofClean">
						<option value="1">是</option>
						<option value="0">否</option>
					</select>
				</template:formTr>
				
				<template:formTr name="风扇运行状态">
					<input type="text" class="inputtext" style="width: 200px;" name="fanState"  maxlength="10" id="fanState">
				</template:formTr>
				
				<template:formTr name="室外单元检查加固">
					<input type="text" class="inputtext" style="width: 200px;" name="outdoorFast"  maxlength="50" id="outdoorFast">
				</template:formTr>
				
				<template:formTr name="机房温度(正常15～30℃)">
					<input type="text" class="inputtext" style="width: 200px;" name="machineTemperature"  maxlength="10" id="machineTemperature">
				</template:formTr>
				
				<template:formTr name="机房湿度(正常40％～65％)">
					<input type="text" class="inputtext" style="width: 200px;" name="machineHumidity" maxlength="10" id="machineHumidity">
				</template:formTr>
				
				<template:formTr name="DDF架/走线架线缆绑扎">
					<input type="text" class="inputtext" style="width: 200px;" name="ddfColligation" maxlength="50" id="ddfColligation">
				</template:formTr>
				
				<template:formTr name="2M线缆、馈线接头检查紧固">
					<input type="text" class="inputtext" style="width: 200px;" name="labelCheck" maxlength="50" id="labelCheck">
				</template:formTr>
				
				<template:formTr name="2M线缆标签核查补贴">
					<input type="text" class="inputtext" style="width: 200px;" name="cabelCheck" maxlength="50" id="cabelCheck">
				</template:formTr>
				
				<template:formSubmit>
					<td>
						<input type="hidden" value="<bean:write name="pid" scope="request"/>" name="pid">
						<input type="hidden" value="<bean:write name="type" scope="request"/>" name="type">
						<input type="hidden" value="<bean:write name="tid" scope="request"/>" name="tid">
						<html:button property="action" styleClass="button"
							onclick="checkInfo()">提交</html:button>
					</td>
					
					<td>
						<input type="button" value="返回" onclick="goBack('<bean:write name="mobileTaskBean" property="machinetype"/>','<bean:write name="tid"/>')" class="button">
					</td>
					<td>
						<html:reset property="action" styleClass="button"
							onclick="">重置	</html:reset>
					</td>
				</template:formSubmit>
				
			</html:form>
			
		</template:formTable>
		
	</body>
</html>