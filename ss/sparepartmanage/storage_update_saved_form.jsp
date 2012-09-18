<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript">
	checkValid=function(addForm){
		if(addForm.sparePartId.value==""){
			alert("��ⱸ������Ϊ�գ�");
			addForm.sparePartId.focus();
			return false;
		}
		if(addForm.serialNumber.value==""){
			alert("�������кŲ���Ϊ�գ�");
			addForm.serialNumber.focus();
			return false;
		}
		if(addForm.storagePosition.value==""){
			alert("����λ�ò���Ϊ�գ�");
			addForm.storagePosition.focus();
			return false;
		}
		if(addForm.storageNumber.value==""){
			alert("�����������Ϊ�գ�");
			addForm.storageNumber.focus();
			return false;
		}
		if(!valiDigit(addForm.storageNumber,"�������")){
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
		var url = "${ctx}/SparepartStorageAction.do?method=listSparepartStorageForOneByMobile";
		self.location.replace(url);
	}
	</script>
	<body>
		<br>
		<template:titile value="�޸��ƶ���˾���" />
		<html:form
			action="/SparepartStorageAction.do?method=updateSavedStorage"
			styleId="addForm" onsubmit="return checkValid(this);">
			<template:formTable namewidth="150" contentwidth="350">
				<template:formTr name="��ⱸ��">
					<input name="tid"
						value="<bean:write name="one_storage"  property="tid"/>"
						type="hidden" class="inputtext" style="width:250;" maxlength="25" />
					<input name="sparePartId"
						value="<bean:write name="one_storage"  property="sparePartId"/>"
						type="hidden" class="inputtext" style="width:250;" maxlength="25" />
					<bean:write name="one_storage" property="sparePartName" />
				</template:formTr>
				<template:formTr name="�������к�">
					<input name="serialNumber"
						value="<bean:write name="one_storage"  property="serialNumber"/>"
						type="text" class="inputtext" style="width:250;" maxlength="25" />
				</template:formTr>
				<template:formTr name="����λ��">
					<input name="storagePosition"
						value="<bean:write name="one_storage"  property="storagePosition"/>"
						type="text" class="inputtext" style="width:250;" maxlength="25" />
				</template:formTr>
				<template:formTr name="�������ڲ���">
					<input name="departId"
						value="<bean:write name="one_storage"  property="departId"/>"
						type="hidden" />
					<bean:write name="one_storage" property="departName" />
				</template:formTr>
				<template:formTr name="�������">
					<input name="storageNumber"
						value="<bean:write name="one_storage"  property="storageNumber"/>"
						type="text" class="inputtext" style="width:250;" maxlength="25" />
				</template:formTr>
				<template:formTr name="������">
					<input name="storagePerson" type="text"
						value="<bean:write name="one_storage"  property="storagePerson"/>"
						class="inputtext" style="width:250;" maxlength="25" />
				</template:formTr>
				<template:formTr name="��ⱸע">
					<textarea name="storageRemark" class="inputtextarea"
						style="width:250;" rows="5"><bean:write name="one_storage" property="storageRemark" /></textarea>
				</template:formTr>

				<template:formSubmit>
					<td>
						<html:submit styleClass="button">�޸����</html:submit>
					</td>
					<td>
						<html:reset styleClass="button">ȡ��	</html:reset>
					</td>
					<td>
						<html:button property="action" styleClass="button"
							onclick="addGoBack()">����</html:button>
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
