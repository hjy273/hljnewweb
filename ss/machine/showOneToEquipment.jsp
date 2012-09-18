<%@include file="/common/header.jsp"%>
<html>
	<head>
		<script type="text/javascript">
			checkInfo=function(equipmentFormId) {	
				if(equipmentFormId.layer.value==0){
					alert("��ѡ�����ͣ�");
					equipmentFormId.layer.focus();
					return false;
				}
				if(equipmentFormId.contractorId.value==0){
					alert("��ѡ���ά��");
					equipmentFormId.contractorId.focus();
					return false;
				}
				if(equipmentFormId.equipmentName.value==""){
					alert("�豸���Ʋ���Ϊ�գ�");
					equipmentFormId.equipmentName.focus();
					return false;
				}
			}
			
			
			initSelect=function(id,value){
				var select = document.getElementById(id);
				select.value=value;
				
			}
		</script>
	</head>
	
	<body>
		<br>
		<template:titile value="�����豸��Ϣһ����"/>
		<html:form action="EquipmentInfoAction.do?method=updateForEqu" styleId="equipmentFormId" method="post" onsubmit="return checkInfo(this);">
			
			<input type="hidden" name="eid" value="<bean:write name="EquipmentInfoBean" property="eid"/>">
			<template:formTable namewidth="150" contentwidth="250">
				<template:formTr name="����">
					<select name="layer" style="width:245px;" class="inputtext" id="layerId">
						<option value="0">
							��ѡ������
						</option>
						<option value="1">
							���Ĳ�
						</option>
						<option value="2">
							�����SDH
						</option>
						<option value="3">
							�����΢��
						</option>
						<option value="4">
							�����FSO
						</option>
						<option value="5">
							�⽻ά��
						</option>
					</select>
				</template:formTr>
				
				<template:formTr name="��ά��˾">
					<select class="inputtext" name="contractorId" style="width:245px;" id="contractorId">
						<option value="0">
							��ѡ���ά
						</option>
						<logic:notEmpty name="conDeptList" scope="request">
							<logic:iterate id="conDeptListid" name="conDeptList"
								scope="request">
								<option
									value="<bean:write name="conDeptListid" property="contractorID"/>">
									<bean:write name="conDeptListid" property="contractorName" />
								</option>
							</logic:iterate>
						</logic:notEmpty>
					</select>
				</template:formTr>
				
				<template:formTr name="�豸����">
					<input type="text" name="equipmentName" style="width:245px" class="inputtext" id="equipmentName" value="<bean:write name="EquipmentInfoBean" property="equipmentName"/>">
				</template:formTr>
				
				<template:formSubmit>
					<td>
						<html:submit styleClass="button">�޸�</html:submit>
					</td>
					<td>
						<html:reset styleClass="button">ȡ��	</html:reset>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
		
		<script type="text/javascript">
			initSelect('layerId','<bean:write name="EquipmentInfoBean" property="layer"/>');
			initSelect('contractorId','<bean:write name="EquipmentInfoBean" property="contractorId"/>');
		</script>
	</body>
</html>