<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript">
	var maxStorageNum="<bean:write name="spare_part_storage" property="storageNumber" />";
	checkValid=function(addForm){
		if(addForm.restoreNumber.value==""){
			alert("���������������Ϊ�գ�");
			addForm.restoreNumber.focus();
			return false;
		}
		if(!valiDigit(addForm.restoreNumber,"�����������")){
			return false;
		}
		if(parseFloat(addForm.restoreNumber.value)>parseFloat(maxStorageNum)){
			alert("���������������������������������д��");
			addForm.restoreNumber.focus();
			return false;
		}
		
		return true;
	}
	addGoBack=function(){
		var url = "${ctx}/SparepartStorageAction.do?method=listSparepartStorage";
		self.location.replace(url);
	}
	addGoBackMod=function(){
		var url = "${ctx}/SparepartStorageAction.do?method=listSparepartStorageForAgain";
		self.location.replace(url);
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
	</script>
	<body>
		<br>
		<template:titile value="�ƶ���˾�������" />
		<html:form action="/SparepartStorageAction.do?method=restoreToStorage"
			styleId="addForm" onsubmit="return checkValid(this);">
			<template:formTable namewidth="150" contentwidth="350">
				<template:formTr name="��ⱸ��">
					<input name="storageId"
						value="<bean:write name="spare_part_storage" property="tid" />"
						type="hidden" />
					<bean:write name="spare_part_storage" property="sparePartName" />
				</template:formTr>
				<template:formTr name="�������к�">
					<bean:write name="spare_part_storage" property="serialNumber" />
				</template:formTr>
				<logic:notEmpty name="init_storage_position">
					<template:formTr name="����������λ��">
						<%=(String)request.getAttribute("init_storage_position") %>
					</template:formTr>
				</logic:notEmpty>
				<template:formTr name="�������">
					<input type="text" name="restoreNumber" class="inputtext"
						style="width:250;" maxlength="25" />
				</template:formTr>

				<template:formSubmit>
					<td>
						<html:submit styleClass="button">�������</html:submit>
					</td>
					<td>
						<html:reset styleClass="button">ȡ��	</html:reset>
					</td>
					<td>
						<html:button property="action" styleClass="button"
							onclick="addGoBackMod()">����</html:button>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
