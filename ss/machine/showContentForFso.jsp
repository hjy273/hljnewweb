<!--  显示接入层FSO的详细内容-->
<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.machine.beans.PollingConFsoBean;"%>

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
				<logic:equal value="0" name="bean" property="isClean">
					不清洁
				</logic:equal>
			</template:formTr>
			
			<template:formTr name="设备表面温度">
				<bean:write name="bean" property="obveTemperature"/>
			</template:formTr>
			
			<template:formTr name="设备指示灯状态">
				<logic:equal value="1" name="bean" property="isMachinePilotlamp">
						亮
					</logic:equal>
					<logic:equal value="0" name="bean" property="isMachinePilotlamp">
						不亮
					</logic:equal>
			</template:formTr>
				
			<template:formTr name="光功率查询">
					<bean:write name="bean" property="searchLightPower"/>
			</template:formTr>
			
			<template:formTr name="尾纤/电源线绑扎">
					<bean:write name="bean" property="powerColligation"/>
			</template:formTr>
			
			<template:formTr name="电源线/尾纤标签核查补贴">
					<bean:write name="bean" property="powerLabelCheck"/>
			</template:formTr>
				
			<template:formSubmit>
				<td>
					<input type="button" value="返回" class="button" onclick="goback('<bean:write name="tid"/>','<bean:write name="type"/>','<bean:write name="flag"/>')">
				</td>
			</template:formSubmit>
		</template:formTable>
		
		
		
	</body>
</html>