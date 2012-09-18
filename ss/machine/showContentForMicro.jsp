<!--  显示接入层微波的详细内容-->
<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.machine.beans.PollingConMicroBean;"%>

<html>
	<head>
		<script type="text/javascript">
			function goback(id,type,flag) {
				var url = "PollingTaskAction.do?method=backToPrePage&id="+id+"&type="+type+"&flag="+flag;
				window.location.href=url;
			}
		</script>
	</head>
	
	<body>
		<br>
		<template:titile value="单个机房设备详细信息"/>
		<template:formTable namewidth="280" contentwidth="320" th2="内容">
			<template:formTr name="设备表面是否清洁">
				<logic:equal value="1" name="bean" property="isClean">
					清洁
				</logic:equal>
				<logic:equal value="2" name="bean" property="isClean">
					不清洁
				</logic:equal>
			</template:formTr>
			
			<template:formTr name="设备表面温度">
				<bean:write name="bean" property="obveTemperature"/>
			</template:formTr>
			
			<template:formTr name="机柜顶端指示灯状态">
				<logic:equal value="1" name="bean" property="isToppilotlamp">
						亮
				</logic:equal>
				<logic:equal value="0" name="bean" property="isToppilotlamp">
						不亮
				</logic:equal>
			</template:formTr>
			
			<template:formTr name="单板指示灯状态">
				<logic:equal value="1" name="bean" property="isVeneerpilotlamp">
						亮
					</logic:equal>
					<logic:equal value="0" name="bean" property="isVeneerpilotlamp">
						不亮
					</logic:equal>
			</template:formTr>
				
			<template:formTr name="设备防尘网是否清洁">
					<logic:equal value="1" name="bean" property="isDustproofClean" scope="request">
						清洁
					</logic:equal>
					<logic:equal value="0" name="bean" property="isDustproofClean" scope="request">
						不清洁
					</logic:equal>
			</template:formTr>
				
			<template:formTr name="风扇运行状态">
					<bean:write name="bean" property="fanState"/>
			</template:formTr>
			
			<template:formTr name="室外单元检查加固">
					<bean:write name="bean" property="outdoorFast"/>
			</template:formTr>
			
			<template:formTr name="机房温度(正常15～30℃)">
					<bean:write name="bean" property="machineTemperature"/>
			</template:formTr>
				
			<template:formTr name="机房湿度">
					<bean:write name="bean" property="machineHumidity"/>
			</template:formTr>
			
			<template:formTr name="DDF架/走线架线缆绑扎">
					<bean:write name="bean" property="ddfColligation"/>
			</template:formTr>
			
			<template:formTr name="2M线缆、馈线接头检查紧固">
				<bean:write name="bean" property="labelCheck"/>
			</template:formTr>
			
			<template:formTr name="2M线缆标签核查补贴">
				<bean:write name="bean" property="cabelCheck"/>
			</template:formTr>
			
			<template:formSubmit>
				<td>
					<input type="button" value="返回" class="button" onclick="goback('<bean:write name="tid"/>','<bean:write name="type"/>','<bean:write name="flag"/>')">
				</td>
			</template:formSubmit>
		</template:formTable>
		
		
		
	</body>
</html>