<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript">
	var maxStorageNum="<bean:write name="spare_part_storage" property="storageNumber" />";
	checkValid=function(addForm){
		if(addForm.storagePosition.value==""){
			alert("����λ�ò���Ϊ�գ�");
			addForm.storagePosition.focus();
			return false;
		}
		if(addForm.storageNumber.value==""){
			alert("������������Ϊ�գ�");
			addForm.storageNumber.focus();
			return false;
		}
		if(!valiDigit(addForm.storageNumber,"��������")){
			return false;
		}
		if(parseFloat(addForm.storageNumber.value)>parseFloat(maxStorageNum)){
			alert("�����������ڿ����������������д��");
			addForm.storageNumber.focus();
			addForm.storageNumber.value=1;
			return false;
		}
		
		return true;
	}
	valiDigit=function(obj,msg){
        var mysplit = /^\d{1,6}$/;
        if(mysplit.test(obj.value)){
          return true;
        }
        else{
            alert("����д��"+msg+"��������,����������");
            obj.value="0";
            obj.focus();
            return false;
        }
    }
	addGoBack=function(){
		var url = "${ctx}/SparepartStorageAction.do?method=listSparepartStorageForDraw";
		self.location.replace(url);
	}
	</script>
	<body>
		<br>
		<template:titile value="��ά��λ����" />
		<html:form
			action="/SparepartStorageAction.do?method=takeOutFromStorage"
			styleId="addForm" onsubmit="return checkValid(this);">
			<template:formTable namewidth="150" contentwidth="350">
				<template:formTr name="��ⱸ��">
					<input name="sparePartId"
						value="<bean:write name="spare_part_storage" property="sparePartId" />"
						type="hidden" />
					<input name="mobile_storage_id"
						value="<%=(String) request.getAttribute("mobile_storage_id")%>"
						type="hidden" />
					<bean:write name="spare_part_storage" property="sparePartName" />
				</template:formTr>
				<template:formTr name="�������к�">
					<input name="serialNumber"
						value="<bean:write name="spare_part_storage" property="serialNumber" />"
						class="inputtext" type="hidden" style="width:250;" maxlength="25" />
					<bean:write name="spare_part_storage" property="serialNumber" />
				</template:formTr>
				<template:formTr name="����λ��">
					<html:text property="storagePosition" styleClass="inputtext"
						style="width:250;" maxlength="25" />
				</template:formTr>
				<template:formTr name="�������ڲ���">
					<input name="departId"
						value="<%=(String) request.getAttribute("dept_id")%>"
						type="hidden" class="inputtext" style="width:250;" />
					<%=(String) request.getAttribute("dept_name")%>
				</template:formTr>
				<template:formTr name="������ǰ����λ��">
					<bean:write name="spare_part_storage" property="storagePosition" />
				</template:formTr>
				<template:formTr name="��������">
					<html:text property="storageNumber" styleClass="inputtext"
						style="width:250;" maxlength="25" value="1" />
				</template:formTr>
				<template:formTr name="������">
					<input name="storagePerson" type="text"
						value="<%=(String) request.getAttribute("user_name")%>"
						class="inputtext" style="width:250;" maxlength="25" />
				</template:formTr>
				<template:formTr name="���ñ�ע">
					<html:textarea property="storageRemark" styleClass="inputtextarea"
						style="width:250;" rows="5" />
				</template:formTr>

				<template:formSubmit>
					<td>
						<html:submit styleClass="button">����</html:submit>
					</td>
					<td>
						<html:reset styleClass="button">ȡ��	</html:reset>
					</td>
					<td>
						<html:button property="action" styleClass="button"
							onclick="addGoBack()">����</html:button>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
