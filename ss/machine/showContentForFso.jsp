<!--  ��ʾ�����FSO����ϸ����-->
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
		<template:titile value="���������豸��ϸ��Ϣ"/>
		<template:formTable namewidth="280" contentwidth="320" th2="����">
			<template:formTr name="�豸�����Ƿ����">
				<logic:equal value="1" name="bean" property="isClean">
					���
				</logic:equal>
				<logic:equal value="0" name="bean" property="isClean">
					�����
				</logic:equal>
			</template:formTr>
			
			<template:formTr name="�豸�����¶�">
				<bean:write name="bean" property="obveTemperature"/>
			</template:formTr>
			
			<template:formTr name="�豸ָʾ��״̬">
				<logic:equal value="1" name="bean" property="isMachinePilotlamp">
						��
					</logic:equal>
					<logic:equal value="0" name="bean" property="isMachinePilotlamp">
						����
					</logic:equal>
			</template:formTr>
				
			<template:formTr name="�⹦�ʲ�ѯ">
					<bean:write name="bean" property="searchLightPower"/>
			</template:formTr>
			
			<template:formTr name="β��/��Դ�߰���">
					<bean:write name="bean" property="powerColligation"/>
			</template:formTr>
			
			<template:formTr name="��Դ��/β�˱�ǩ�˲鲹��">
					<bean:write name="bean" property="powerLabelCheck"/>
			</template:formTr>
				
			<template:formSubmit>
				<td>
					<input type="button" value="����" class="button" onclick="goback('<bean:write name="tid"/>','<bean:write name="type"/>','<bean:write name="flag"/>')">
				</td>
			</template:formSubmit>
		</template:formTable>
		
		
		
	</body>
</html>