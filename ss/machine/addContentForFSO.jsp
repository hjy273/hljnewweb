<!-- ��ά��дѲ�����ݣ������FSO�� -->
<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.machine.beans.PollingConFsoBean;"%>
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
				
				var searchLightPowerEle = document.getElementById('searchLightPower');
				if(searchLightPowerEle.value.length == 0) {
					alert("������⹦�ʲ�ѯ!");
					searchLightPowerEle.focus();
					return;
				}
				
				var powerColligationEle = document.getElementById('powerColligation');
				if(powerColligationEle.value.length == 0) {
					alert("������β��/��Դ�߰���!");
					powerColligationEle.focus();
					return;
				}
				
				var powerLabelCheckEle = document.getElementById('powerLabelCheck');
				if(powerLabelCheckEle.value.length == 0) {
					alert("�������Դ��/β�˱�ǩ�˲鲹��!");
					powerLabelCheckEle.focus();
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
			<html:form action="PollingConFsoAction.do?method=addContentForFSO"
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
				
				<template:formTr name=" �豸ָʾ��״̬">
					<select  class="inputtext" style="width: 200px;" name="isMachinePilotlamp">
						<option value="1">��</option>
						<option value="0">��</option>
					</select>
				</template:formTr>
				
				<template:formTr name="�⹦�ʲ�ѯ">
					<input type="text" class="inputtext" style="width: 200px;" name="searchLightPower" id="searchLightPower" maxlength="50">
				</template:formTr>
				
				<template:formTr name="β��/��Դ�߰���">
					<input type="text" class="inputtext" style="width: 200px;" name="powerColligation" id="powerColligation" maxlength="50">
				</template:formTr>
				
				<template:formTr name="��Դ��/β�˱�ǩ�˲鲹��">
					<input type="text" class="inputtext" style="width: 200px;" name="powerLabelCheck" id="powerLabelCheck" maxlength="50">
				</template:formTr>
				
				<template:formSubmit>
					<td>
						<input type="hidden" value="<bean:write name="pid" scope="request"/>" name="pid">
						
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