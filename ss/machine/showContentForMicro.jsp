<!--  ��ʾ�����΢������ϸ����-->
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
		<template:titile value="���������豸��ϸ��Ϣ"/>
		<template:formTable namewidth="280" contentwidth="320" th2="����">
			<template:formTr name="�豸�����Ƿ����">
				<logic:equal value="1" name="bean" property="isClean">
					���
				</logic:equal>
				<logic:equal value="2" name="bean" property="isClean">
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
			
			<template:formTr name="���ⵥԪ���ӹ�">
					<bean:write name="bean" property="outdoorFast"/>
			</template:formTr>
			
			<template:formTr name="�����¶�(����15��30��)">
					<bean:write name="bean" property="machineTemperature"/>
			</template:formTr>
				
			<template:formTr name="����ʪ��">
					<bean:write name="bean" property="machineHumidity"/>
			</template:formTr>
			
			<template:formTr name="DDF��/���߼����°���">
					<bean:write name="bean" property="ddfColligation"/>
			</template:formTr>
			
			<template:formTr name="2M���¡����߽�ͷ������">
				<bean:write name="bean" property="labelCheck"/>
			</template:formTr>
			
			<template:formTr name="2M���±�ǩ�˲鲹��">
				<bean:write name="bean" property="cabelCheck"/>
			</template:formTr>
			
			<template:formSubmit>
				<td>
					<input type="button" value="����" class="button" onclick="goback('<bean:write name="tid"/>','<bean:write name="type"/>','<bean:write name="flag"/>')">
				</td>
			</template:formSubmit>
		</template:formTable>
		
		
		
	</body>
</html>