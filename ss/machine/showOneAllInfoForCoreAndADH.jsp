<!-- 全部内容（核心层与接入层SDH） -->
<%@include file="/common/header.jsp"%>

<html>
	<head>
		<title></title>
		
		<script type="text/javascript">
			function goBack(tid,type) {
				var url = "PollingTaskAction.do?method=back&tid="+tid+"&type="+type;
				window.location.href=url;
			}
		</script>
	</head>
	
	<body>
		<br>
		<template:titile value="单个机房设备巡检核查内容"/>
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
			
			<tr heigth="40" class=trcolor >
                        	<td class="tdr"   colspan="6"><hr/></td>
                 </tr>
			
			<template:formTr name="设备表面是否清洁">
						<logic:equal value="1" name="bean" property="isClean" scope="request">
							清洁
						</logic:equal>
						<logic:equal value="0" name="bean" property="isClean" scope="request">
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
				
				<template:formTr name="机房地面清洁">
					<bean:write name="bean" property="machineFloorClean"/>
				</template:formTr>
				
				<template:formTr name="机房温度">
					<bean:write name="bean" property="machineTemperature"/>
				</template:formTr>
				
				<template:formTr name="机房湿度">
					<bean:write name="bean" property="machineHumidity"/>
				</template:formTr>
				
				<template:formTr name="DDF架/走线架线缆绑扎">
					<bean:write name="bean" property="ddfColligation"/>
				</template:formTr>
				
				<template:formTr name="尾纤保护">
					<bean:write name="bean" property="fiberProtect"/>
				</template:formTr>
				
				<template:formTr name="DDF架线缆接头检查紧固">
					<bean:write name="bean" property="ddfCableFast"/>
				</template:formTr>
				
				<template:formTr name="ODF架尾纤接头检查紧固">
					<bean:write name="bean" property="odfInterfacefast"/>
				</template:formTr>
				
				<template:formTr name="ODF/设备侧尾纤标签核查补贴">
					<bean:write name="bean" property="odfLabelCheck"/>
				</template:formTr>
				
				<template:formTr name="DDF架线缆标签核查补贴">
					<bean:write name="bean" property="ddfCabelCheck"/>
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
							<input type="button" value="返回" class="button" onclick="goBack('<bean:write name="tid"/>','<bean:write name="type"/>');">
						</td>
					</tr>
				</template:formSubmit>
		</template:formTable>
	</body>
</html>