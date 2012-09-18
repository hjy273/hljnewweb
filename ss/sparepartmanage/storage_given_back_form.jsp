<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript">
	var maxStorageNum="<bean:write name="spare_part_storage" property="storageNumber" />";
	checkValid=function(addForm){
		if(addForm.givenBackNumber.value==""){
			alert("�黹��������Ϊ�գ�");
			addForm.givenBackNumber.focus();
			return false;
		}
		if(!valiDigit(addForm.givenBackNumber,"�黹����")){
			return false;
		}
		if(parseFloat(addForm.givenBackNumber.value)>parseFloat(maxStorageNum)){
			alert("�黹�������ڿ����������������д��");
			addForm.givenBackNumber.focus();
			return false;
		}
		
		return true;
	}
	addGoBack=function(){
		var url = "${ctx}/SparepartStorageAction.do?method=listSparepartStorage";
		self.location.replace(url);
	}
	addGoBackMod=function() {
		var url = "${ctx}/SparepartStorageAction.do?method=listSparepartStorageForRe";
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
		<template:titile value="��ά��λ�黹" />
		<html:form
			action="/SparepartStorageAction.do?method=giveBackToStorage"
			styleId="addForm" onsubmit="return checkValid(this);">
			<template:formTable namewidth="150" contentwidth="350">
				<template:formTr name="�黹����">
					<input name="sparePartId"
						value="<bean:write name="spare_part_storage" property="sparePartId" />"
						type="hidden" />
					<input name="fromStorageId"
						value="<%=(String) request.getAttribute("from_storage_id")%>"
						type="hidden" />
					<input name="toStorageId"
						value="<%=(String) request.getAttribute("to_storage_id")%>"
						type="hidden" />
					<input name="givenBackType"
						value="<%=(String) request.getAttribute("given_back_type")%>"
						type="hidden" />
					<bean:write name="spare_part_storage" property="sparePartName" />
				</template:formTr>
				<template:formTr name="�������к�">
					<input name="serialNumber"
						value="<bean:write name="spare_part_storage" property="serialNumber" />"
						class="inputtext" type="hidden" style="width:250;" maxlength="25" />
					<bean:write name="spare_part_storage" property="serialNumber" />
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
				<logic:notEmpty name="taken_out_position">
					<template:formTr name="������Դ">
						<%=(String)request.getAttribute("taken_out_position") %>
					</template:formTr>
				</logic:notEmpty>
				<template:formTr name="�黹����">
					<input name="givenBackNumber" class="inputtext" type="text"
						style="width:250;" maxlength="25" />
				</template:formTr>

				<template:formSubmit>
					<td>
						<html:submit styleClass="button">�黹</html:submit>
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
