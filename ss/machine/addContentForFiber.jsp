<!-- ��ά��дѲ�����ݣ��⽻ά���� -->
<%@include file="/common/header.jsp"%>
<%@ page import="com.cabletech.machine.beans.PollingContentBean;"%>

<html>
	<head>
		<title></title>
		
		<script type="text/javascript">
			function checkInfo() {
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
		<template:titile value="��ӵ����⽻�豸Ѳ������"/>
		<template:formTable contentwidth="320" namewidth="280" th2="����">
			<template:formTr name="����">
				<logic:equal value="1" name="mobileTaskBean" property="machinetype" scope="request">
					���Ĳ�
				</logic:equal>
				
				<logic:equal value="2" name="mobileTaskBean" property="machinetype">
					�����SDH
				</logic:equal>
				<logic:equal value="5" name="mobileTaskBean" property="machinetype">
					�⽻
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
			<html:form action="PollingConFiberAction.do?method=addPollingConFiber"
			styleId="addContentFormId">
				<template:formTr name="ODF���ͼ�Ƿ����">
					<select  class="inputtext" style="width: 200px;" name="isUpdate">
						<option value="1">��</option>
						<option value="0">��</option>
					</select>
				</template:formTr>
				
				<template:formTr name="�������̶�">
					<select  class="inputtext" style="width: 200px;" name="isClean">
						<option value="2">����</option>
						<option value="1">����</option>
						<option value="0">һ��</option>
					</select>
				</template:formTr>
				
				<template:formTr name="�⽻���⻷�����̶�">
					<select  class="inputtext" style="width: 200px;" name="isFiberBoxClean">
						<option value="2">����</option>
						<option value="1">����</option>
						<option value="0">һ��</option>
					</select>
				</template:formTr>
				
				<template:formTr name="β���Ƿ��������">
					<select  class="inputtext" style="width: 200px;" name="isColligation">
						<option value="1">��</option>
						<option value="0">��</option>
					</select>
				</template:formTr>
				
				<template:formTr name="���±�ʶ�ƺ˲鲹��">
					<select  class="inputtext" style="width: 200px;" name="isFiberCheck">
						<option value="1">�Ѻ˲鲹��</option>
						<option value="0">δ�˲鲹��</option>
					</select>
				</template:formTr>
				
				<template:formTr name="β�˱�ʶ�ƺ˲鲹��">
					<select  class="inputtext" style="width: 200px;" name="isTailFiberCheck">
						<option value="1">�Ѻ˲鲹��</option>
						<option value="0">δ�˲鲹��</option>
					</select>
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
						<input type="button" value="����" onClick="goBack('<bean:write name="mobileTaskBean" property="machinetype"/>','<bean:write name="tid"/>')" class="button">
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