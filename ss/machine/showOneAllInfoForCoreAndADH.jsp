<!-- ȫ�����ݣ����Ĳ�������SDH�� -->
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
		<template:titile value="���������豸Ѳ��˲�����"/>
		<template:formTable contentwidth="320" namewidth="280" th2="����">
			<template:formTr name="����">
				<logic:equal value="1" name="mobileTaskBean" property="machinetype" scope="request">
					���Ĳ�
				</logic:equal>
				
				<logic:equal value="2" name="mobileTaskBean" property="machinetype">
					�����SDH
				</logic:equal>
			</template:formTr>
			
			<template:formTr name="����">
				<bean:write name="mobileTaskBean" property="title" scope="request"/>
			</template:formTr>
			
			<template:formTr name="��ά��˾">
				<bean:write name="mobileTaskBean" property="conname" scope="request"/>
			</template:formTr>
			
			<template:formTr name="ִ����">
				<bean:write name="mobileTaskBean" property="userconname" scope="request"/>
			</template:formTr>
			
			<template:formTr name="ִ������">
				<bean:write name="mobileTaskBean" property="executetime" scope="request"/>
			</template:formTr>
			
			<template:formTr name="�����">
				<bean:write name="mobileTaskBean" property="checkusername" scope="request"/>
			</template:formTr>
			
			<template:formTr name="��ע">
				<bean:write name="mobileTaskBean" property="remark" scope="request"/>
			</template:formTr>
			
			<tr heigth="40" class=trcolor >
                        	<td class="tdr"   colspan="6"><hr/></td>
                 </tr>
			
			<template:formTr name="�豸�����Ƿ����">
						<logic:equal value="1" name="bean" property="isClean" scope="request">
							���
						</logic:equal>
						<logic:equal value="0" name="bean" property="isClean" scope="request">
							�����
						</logic:equal>
				</template:formTr>
				
				<template:formTr name="�豸�����¶�">
					<bean:write name="bean" property="obveTemperature"/>
				</template:formTr>
				
				<template:formTr name="���񶥶�ָʾ��״̬">
					<logic:equal value="1" name="bean" property="isToppilotlamp">
						��
					</logic:equal>
					<logic:equal value="0" name="bean" property="isToppilotlamp">
						����
					</logic:equal>
				</template:formTr>
				
				<template:formTr name="����ָʾ��״̬">
					<logic:equal value="1" name="bean" property="isVeneerpilotlamp">
						��
					</logic:equal>
					<logic:equal value="0" name="bean" property="isVeneerpilotlamp">
						����
					</logic:equal>
				</template:formTr>
				
				<template:formTr name="�豸�������Ƿ����">
					<logic:equal value="1" name="bean" property="isDustproofClean" scope="request">
						���
					</logic:equal>
					<logic:equal value="0" name="bean" property="isDustproofClean" scope="request">
						�����
					</logic:equal>
				</template:formTr>
				
				<template:formTr name="��������״̬">
					<bean:write name="bean" property="fanState"/>
				</template:formTr>
				
				<template:formTr name="�����������">
					<bean:write name="bean" property="machineFloorClean"/>
				</template:formTr>
				
				<template:formTr name="�����¶�">
					<bean:write name="bean" property="machineTemperature"/>
				</template:formTr>
				
				<template:formTr name="����ʪ��">
					<bean:write name="bean" property="machineHumidity"/>
				</template:formTr>
				
				<template:formTr name="DDF��/���߼����°���">
					<bean:write name="bean" property="ddfColligation"/>
				</template:formTr>
				
				<template:formTr name="β�˱���">
					<bean:write name="bean" property="fiberProtect"/>
				</template:formTr>
				
				<template:formTr name="DDF�����½�ͷ������">
					<bean:write name="bean" property="ddfCableFast"/>
				</template:formTr>
				
				<template:formTr name="ODF��β�˽�ͷ������">
					<bean:write name="bean" property="odfInterfacefast"/>
				</template:formTr>
				
				<template:formTr name="ODF/�豸��β�˱�ǩ�˲鲹��">
					<bean:write name="bean" property="odfLabelCheck"/>
				</template:formTr>
				
				<template:formTr name="DDF�����±�ǩ�˲鲹��">
					<bean:write name="bean" property="ddfCabelCheck"/>
				</template:formTr>
				
				<tr heigth="40" class=trcolor >
                        	<td class="tdr"   colspan="6"><hr/></td>
                 </tr>
				
				<template:formTr name="ά������ʦ�˲����">
					<bean:write name="pollingTaskBean" property="auditResult"/>
				</template:formTr>
				
				<template:formTr name="�˲鱸ע">
					<bean:write name="pollingTaskBean" property="checkRemark"/>
				</template:formTr>					
				
				<template:formSubmit>
					<tr>
						<td>
							<input type="hidden" name="tid" value="<bean:write name="tid"/>">
							<input type="hidden" name="pid" value="<bean:write name="pid"/>">
							<input type="hidden" name="type" value="<bean:write name="type"/>">
							<input type="button" value="����" class="button" onclick="goBack('<bean:write name="tid"/>','<bean:write name="type"/>');">
						</td>
					</tr>
				</template:formSubmit>
		</template:formTable>
	</body>
</html>