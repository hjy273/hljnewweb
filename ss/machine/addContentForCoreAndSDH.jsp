<!-- 代维填写巡检内容（核心层与接入层SDH） -->
<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.machine.beans.PollingContentBean;"%>

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
				
				var machineFloorCleanEle = document.getElementById('machineFloorClean');
				if(machineFloorCleanEle.value.length == 0) {
					alert("请输入机房地面清洁!");
					machineFloorCleanEle.focus();
					return;
				}
				
				var machineTemperatureEle = document.getElementById('machineTemperature');
				if(machineTemperatureEle.value.length == 0) {
					alert("请输入机房温度(正常15～30℃)");
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
				
				var fiberProtectEle = document.getElementById('fiberProtect');
				if(fiberProtectEle.value.length == 0) {
					alert("请输入尾纤保护!");
					fiberProtectEle.focus();
					return;
				}
				
				var ddfCableFastEle = document.getElementById('ddfCableFast');
				if(ddfCableFastEle.value.length == 0 ){
					alert("请输入DDF架线缆接头检查紧固!");
					ddfCableFastEle.focus();
					return;
				}
				
				var odfInterfacefastEle = document.getElementById('odfInterfacefast');
				if(odfInterfacefastEle.value.length == 0) {
					alert("请输入ODF架尾纤接头检查紧固!");
					odfInterfacefastEle.focus();
					return;
				}
				
				var odfLabelCheckEle = document.getElementById('odfLabelCheck');
				if(odfLabelCheckEle.value.length == 0) {
					alert("请输入ODF/设备侧尾纤标签核查补贴!");
					odfLabelCheckEle.focus();
					return;
				}
				
				var ddfCabelCheckEle = document.getElementById('ddfCabelCheck');
				if(ddfCabelCheckEle.value.length == 0) {
					alert("请输入DDF架线缆标签核查补贴!");
					ddfCabelCheckEle.focus();
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
				<logic:equal value="1" name="mobileTaskBean" property="machinetype" scope="request">
					核心层
				</logic:equal>
				
				<logic:equal value="2" name="mobileTaskBean" property="machinetype">
					接入层SDH
				</logic:equal>
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
			<html:form action="PollingContentAction.do?method=addContentForCoreAndSDH"
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
					<input type="text" class="inputtext" style="width: 200px;" name="fanState" id="fanState" maxlength="10">
				</template:formTr>
				
				<template:formTr name="机房地面清洁">
					<input type="text" class="inputtext" style="width: 200px;" name="machineFloorClean" id="machineFloorClean" maxlength="50">
				</template:formTr>
				
				<template:formTr name="机房温度(正常15～30℃)">
					<input type="text" class="inputtext" style="width: 200px;" name="machineTemperature" id="machineTemperature" maxlength="20">
				</template:formTr>
				
				<template:formTr name="机房湿度(正常40％～65％)">
					<input type="text" class="inputtext" style="width: 200px;" name="machineHumidity" id="machineHumidity" maxlength="20">
				</template:formTr>
				
				<template:formTr name="DDF架/走线架线缆绑扎">
					<input type="text" class="inputtext" style="width: 200px;" name="ddfColligation" id="ddfColligation" maxlength="50">
				</template:formTr>
				
				<template:formTr name="尾纤保护">
					<input type="text" class="inputtext" style="width: 200px;" name="fiberProtect" id="fiberProtect" maxlength="50">
				</template:formTr>
				
				<template:formTr name="DDF架线缆接头检查紧固">
					<input type="text" class="inputtext" style="width: 200px;" name="ddfCableFast" id="ddfCableFast" maxlength="50">
				</template:formTr>
				
				<template:formTr name="ODF架尾纤接头检查紧固">
					<input type="text" class="inputtext" style="width: 200px;" name="odfInterfacefast" id="odfInterfacefast" maxlength="50">
				</template:formTr>
				
				<template:formTr name="ODF/设备侧尾纤标签核查补贴">
					<input type="text" class="inputtext" style="width: 200px;" name="odfLabelCheck" id="odfLabelCheck" maxlength="50">
				</template:formTr>
				
				<template:formTr name="DDF架线缆标签核查补贴">
					<input type="text" class="inputtext" style="width: 200px;" name="ddfCabelCheck" id="ddfCabelCheck" maxlength="50">
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