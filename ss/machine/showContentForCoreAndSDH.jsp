
<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.machine.beans.PollingContentBean;"%>
<html>
	<head>
		<title></title>
	
		<script type="text/javascript">
			function goback(id,type,flag) {
				var url = "PollingTaskAction.do?method=backToPrePage&id="+id+"&type="+type+"&flag="+flag;
				window.location.href=url;
			}		
		</script>	
	</head>
	<body>
		<br>
		<logic:present name="bean" scope="request">
		<template:titile value="���������豸��ϸ��Ϣ"/>
			<template:formTable namewidth="280" contentwidth="320" th2="����">
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
				
				<template:formSubmit>
					<td>
						<input type="button" value="����" class="button" onclick="goback('<bean:write name="tid"/>','<bean:write name="type"/>','<bean:write name="flag"/>')">
					</td>
				</template:formSubmit>
			</template:formTable>
			</logic:present>
	</body>
</html>