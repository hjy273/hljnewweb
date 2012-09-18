<!-- 代维填写巡检内容（接入层FSO） -->
<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.machine.beans.PollingConFsoBean;"%>
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
				
				var searchLightPowerEle = document.getElementById('searchLightPower');
				if(searchLightPowerEle.value.length == 0) {
					alert("请输入光功率查询!");
					searchLightPowerEle.focus();
					return;
				}
				
				var powerColligationEle = document.getElementById('powerColligation');
				if(powerColligationEle.value.length == 0) {
					alert("请输入尾纤/电源线绑扎!");
					powerColligationEle.focus();
					return;
				}
				
				var powerLabelCheckEle = document.getElementById('powerLabelCheck');
				if(powerLabelCheckEle.value.length == 0) {
					alert("请输入电源线/尾纤标签核查补贴!");
					powerLabelCheckEle.focus();
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
			<html:form action="PollingConFsoAction.do?method=addContentForFSO"
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
				
				<template:formTr name=" 设备指示灯状态">
					<select  class="inputtext" style="width: 200px;" name="isMachinePilotlamp">
						<option value="1">亮</option>
						<option value="0">灭</option>
					</select>
				</template:formTr>
				
				<template:formTr name="光功率查询">
					<input type="text" class="inputtext" style="width: 200px;" name="searchLightPower" id="searchLightPower" maxlength="50">
				</template:formTr>
				
				<template:formTr name="尾纤/电源线绑扎">
					<input type="text" class="inputtext" style="width: 200px;" name="powerColligation" id="powerColligation" maxlength="50">
				</template:formTr>
				
				<template:formTr name="电源线/尾纤标签核查补贴">
					<input type="text" class="inputtext" style="width: 200px;" name="powerLabelCheck" id="powerLabelCheck" maxlength="50">
				</template:formTr>
				
				<template:formSubmit>
					<td>
						<input type="hidden" value="<bean:write name="pid" scope="request"/>" name="pid">
						
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