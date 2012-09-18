<!-- 内容（FSO） -->
<%@include file="/common/header.jsp"%>

<html>
	<head>
		<script type="text/javascript">
			
			function backForCheck(tid,type) {
				var url = "PollingTaskAction.do?method=back&tid="+tid+"&type="+type;
				window.location.href=url;
			}
		</script>
	</head>
	
	<body>
		<br>
		<template:titile value="单个机房设备核查内容"/>
		<template:formTable contentwidth="320" namewidth="280" th2="内容">
			<template:formTr name="类型">
				接入层FSO
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
			
			<tr heigth="40" class=trcolor >
                        	<td class="tdr"   colspan="6"><hr/></td>
                 </tr>
                 
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
			
			<tr heigth="40" class=trcolor >
                        	<td class="tdr"   colspan="6"><hr/></td>
                 </tr>
                 
             <template:formTr name="维护工程师核查情况">
					<bean:write name="pollingTaskBean" property="auditResult"/>
				</template:formTr>
				
				<template:formTr name="核查备注">
					<bean:write name="pollingTaskBean" property="checkRemark"/>
				</template:formTr>					
			
			<template:formSubmit>
					<tr>
						<td>
							<input type="hidden" name="tid" value="<bean:write name="tid"/>">
							<input type="hidden" name="pid" value="<bean:write name="pid"/>">
							<input type="hidden" name="type" value="<bean:write name="type"/>">
							<input type="button" value="返回" class="button" onclick="backForCheck('<bean:write name="tid"/>','<bean:write name="type"/>');">
						</td>
					</tr>
			</template:formSubmit>
		</template:formTable>
	</body>
</html>