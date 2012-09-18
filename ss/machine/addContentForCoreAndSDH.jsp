<!-- ��ά��дѲ�����ݣ����Ĳ�������SDH�� -->
<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.machine.beans.PollingContentBean;"%>

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
				
				var machineFloorCleanEle = document.getElementById('machineFloorClean');
				if(machineFloorCleanEle.value.length == 0) {
					alert("����������������!");
					machineFloorCleanEle.focus();
					return;
				}
				
				var machineTemperatureEle = document.getElementById('machineTemperature');
				if(machineTemperatureEle.value.length == 0) {
					alert("����������¶�(����15��30��)");
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
				
				var fiberProtectEle = document.getElementById('fiberProtect');
				if(fiberProtectEle.value.length == 0) {
					alert("������β�˱���!");
					fiberProtectEle.focus();
					return;
				}
				
				var ddfCableFastEle = document.getElementById('ddfCableFast');
				if(ddfCableFastEle.value.length == 0 ){
					alert("������DDF�����½�ͷ������!");
					ddfCableFastEle.focus();
					return;
				}
				
				var odfInterfacefastEle = document.getElementById('odfInterfacefast');
				if(odfInterfacefastEle.value.length == 0) {
					alert("������ODF��β�˽�ͷ������!");
					odfInterfacefastEle.focus();
					return;
				}
				
				var odfLabelCheckEle = document.getElementById('odfLabelCheck');
				if(odfLabelCheckEle.value.length == 0) {
					alert("������ODF/�豸��β�˱�ǩ�˲鲹��!");
					odfLabelCheckEle.focus();
					return;
				}
				
				var ddfCabelCheckEle = document.getElementById('ddfCabelCheck');
				if(ddfCabelCheckEle.value.length == 0) {
					alert("������DDF�����±�ǩ�˲鲹��!");
					ddfCabelCheckEle.focus();
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
		</template:formTable>
		
		<hr>
		<template:formTable contentwidth="320" namewidth="280">
			<html:form action="PollingContentAction.do?method=addContentForCoreAndSDH"
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
					<input type="text" class="inputtext" style="width: 200px;" name="fanState" id="fanState" maxlength="10">
				</template:formTr>
				
				<template:formTr name="�����������">
					<input type="text" class="inputtext" style="width: 200px;" name="machineFloorClean" id="machineFloorClean" maxlength="50">
				</template:formTr>
				
				<template:formTr name="�����¶�(����15��30��)">
					<input type="text" class="inputtext" style="width: 200px;" name="machineTemperature" id="machineTemperature" maxlength="20">
				</template:formTr>
				
				<template:formTr name="����ʪ��(����40����65��)">
					<input type="text" class="inputtext" style="width: 200px;" name="machineHumidity" id="machineHumidity" maxlength="20">
				</template:formTr>
				
				<template:formTr name="DDF��/���߼����°���">
					<input type="text" class="inputtext" style="width: 200px;" name="ddfColligation" id="ddfColligation" maxlength="50">
				</template:formTr>
				
				<template:formTr name="β�˱���">
					<input type="text" class="inputtext" style="width: 200px;" name="fiberProtect" id="fiberProtect" maxlength="50">
				</template:formTr>
				
				<template:formTr name="DDF�����½�ͷ������">
					<input type="text" class="inputtext" style="width: 200px;" name="ddfCableFast" id="ddfCableFast" maxlength="50">
				</template:formTr>
				
				<template:formTr name="ODF��β�˽�ͷ������">
					<input type="text" class="inputtext" style="width: 200px;" name="odfInterfacefast" id="odfInterfacefast" maxlength="50">
				</template:formTr>
				
				<template:formTr name="ODF/�豸��β�˱�ǩ�˲鲹��">
					<input type="text" class="inputtext" style="width: 200px;" name="odfLabelCheck" id="odfLabelCheck" maxlength="50">
				</template:formTr>
				
				<template:formTr name="DDF�����±�ǩ�˲鲹��">
					<input type="text" class="inputtext" style="width: 200px;" name="ddfCabelCheck" id="ddfCabelCheck" maxlength="50">
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