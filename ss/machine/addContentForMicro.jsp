<!-- ��ά��дѲ�����ݣ������΢���� -->
<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.machine.beans.PollingConMicroBean;"%>
<html>
	<head>
		<title></title>
		
		<script type="text/javascript">
			function checkInfo() {
				var obveTemperatureEle = document.getElementById('obveTemperature');
				if(obveTemperatureEle.value.length == 0) {
					alert("�������豸�����¶�!");
					obveTemperatureEle.focus();
					return;
				}
				
				var fanStateEle = document.getElementById('fanState');
				if(fanStateEle.value.length == 0) {
					alert("�������������״̬!");
					fanStateEle.focus();
					return;
				}
				
				var outdoorFastEle = document.getElementById('outdoorFast');
				if(outdoorFastEle.value.length == 0) {
					alert("���������ⵥԪ���ӹ�!");
					outdoorFastEle.focus();
					return;
				}
				
				var machineTemperatureEle = document.getElementById('machineTemperature');
				if(machineTemperatureEle.value.length == 0) {
					alert("����������¶�(����15��30��)!");
					machineTemperatureEle.focus();
					return;
				}
				
				var machineHumidityEle = document.getElementById('machineHumidity');
				if(machineHumidityEle.value.length == 0) {
					alert("���������ʪ��(����40����65��)!");
					machineHumidityEle.focus();
					return;
				}
				
				var ddfColligationEle = document.getElementById('ddfColligation');
				if(ddfColligationEle.value.length == 0) {
					alert("������DDF��/���߼����°���!");
					ddfColligationEle.focus();
					return;
				}
				
				var labelCheckEle = document.getElementById('labelCheck');
				if(labelCheckEle.value.length == 0) {
					alert("������2M���¡����߽�ͷ������!");
					labelCheckEle.focus();
					return;
				}
				
				var cabelCheckEle = document.getElementById('cabelCheck');
				if(cabelCheckEle.value.length == 0) {
					alert("������2M���±�ǩ�˲鲹��!");
					cabelCheckEle.focus();
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
		<template:titile value="��ӵ��������豸Ѳ������"/>
		<template:formTable contentwidth="320" namewidth="280" th2="����">
			<template:formTr name="����">
				�����΢��
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
		</template:formTable>
		<hr>
		<template:formTable contentwidth="320" namewidth="280">
			<html:form action="PollingConMicroAction.do?method=addContentForMiro"
			styleId="addContentFormId">
				<template:formTr name="�豸�������">
					<select  class="inputtext" style="width: 200px;" name="isClean">
						<option value="1">��</option>
						<option value="0">��</option>
					</select>
				</template:formTr>
				
				<template:formTr name="�豸�����¶�">
					<input type="text" class="inputtext" style="width: 200px;" name="obveTemperature" id="obveTemperature" maxlength="10">
				</template:formTr>
				
				<template:formTr name="���񶥶�ָʾ��״̬">
					<select  class="inputtext" style="width: 200px;" name="isToppilotlamp">
						<option value="1">��</option>
						<option value="0">��</option>
					</select>
				</template:formTr>
				
				<template:formTr name="����ָʾ��״̬">
					<select  class="inputtext" style="width: 200px;" name="isVeneerpilotlamp">
						<option value="1">��</option>
						<option value="0">��</option>
					</select>
				</template:formTr>
				
				<template:formTr name="�豸���������">
					<select  class="inputtext" style="width: 200px;" name="isDustproofClean">
						<option value="1">��</option>
						<option value="0">��</option>
					</select>
				</template:formTr>
				
				<template:formTr name="��������״̬">
					<input type="text" class="inputtext" style="width: 200px;" name="fanState"  maxlength="10" id="fanState">
				</template:formTr>
				
				<template:formTr name="���ⵥԪ���ӹ�">
					<input type="text" class="inputtext" style="width: 200px;" name="outdoorFast"  maxlength="50" id="outdoorFast">
				</template:formTr>
				
				<template:formTr name="�����¶�(����15��30��)">
					<input type="text" class="inputtext" style="width: 200px;" name="machineTemperature"  maxlength="10" id="machineTemperature">
				</template:formTr>
				
				<template:formTr name="����ʪ��(����40����65��)">
					<input type="text" class="inputtext" style="width: 200px;" name="machineHumidity" maxlength="10" id="machineHumidity">
				</template:formTr>
				
				<template:formTr name="DDF��/���߼����°���">
					<input type="text" class="inputtext" style="width: 200px;" name="ddfColligation" maxlength="50" id="ddfColligation">
				</template:formTr>
				
				<template:formTr name="2M���¡����߽�ͷ������">
					<input type="text" class="inputtext" style="width: 200px;" name="labelCheck" maxlength="50" id="labelCheck">
				</template:formTr>
				
				<template:formTr name="2M���±�ǩ�˲鲹��">
					<input type="text" class="inputtext" style="width: 200px;" name="cabelCheck" maxlength="50" id="cabelCheck">
				</template:formTr>
				
				<template:formSubmit>
					<td>
						<input type="hidden" value="<bean:write name="pid" scope="request"/>" name="pid">
						<input type="hidden" value="<bean:write name="type" scope="request"/>" name="type">
						<input type="hidden" value="<bean:write name="tid" scope="request"/>" name="tid">
						<html:button property="action" styleClass="button"
							onclick="checkInfo()">�ύ</html:button>
					</td>
					
					<td>
						<input type="button" value="����" onclick="goBack('<bean:write name="mobileTaskBean" property="machinetype"/>','<bean:write name="tid"/>')" class="button">
					</td>
					<td>
						<html:reset property="action" styleClass="button"
							onclick="">����	</html:reset>
					</td>
				</template:formSubmit>
				
			</html:form>
			
		</template:formTable>
		
	</body>
</html>