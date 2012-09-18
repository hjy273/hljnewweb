<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript">
	checkValid=function(addForm){
		if(addForm.sparePartId.value==""){
			alert("入库备件不能为空！");
			addForm.sparePartId.focus();
			return false;
		}
		if(addForm.serialNumber.value==""){
			alert("备件序列号不能为空！");
			addForm.serialNumber.focus();
			return false;
		}
		if(addForm.storagePosition.value==""){
			alert("保存位置不能为空！");
			addForm.storagePosition.focus();
			return false;
		}
		if(addForm.storageNumber.value==""){
			alert("入库数量不能为空！");
			addForm.storageNumber.focus();
			return false;
		}
		if(!valiDigit(addForm.storageNumber,"入库数量")){
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
            alert("你填写的"+msg+"不是数字,请重新输入");
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
		<template:titile value="修改移动公司入库" />
		<html:form
			action="/SparepartStorageAction.do?method=updateSavedStorage"
			styleId="addForm" onsubmit="return checkValid(this);">
			<template:formTable namewidth="150" contentwidth="350">
				<template:formTr name="入库备件">
					<input name="tid"
						value="<bean:write name="one_storage"  property="tid"/>"
						type="hidden" class="inputtext" style="width:250;" maxlength="25" />
					<input name="sparePartId"
						value="<bean:write name="one_storage"  property="sparePartId"/>"
						type="hidden" class="inputtext" style="width:250;" maxlength="25" />
					<bean:write name="one_storage" property="sparePartName" />
				</template:formTr>
				<template:formTr name="备件序列号">
					<input name="serialNumber"
						value="<bean:write name="one_storage"  property="serialNumber"/>"
						type="text" class="inputtext" style="width:250;" maxlength="25" />
				</template:formTr>
				<template:formTr name="保存位置">
					<input name="storagePosition"
						value="<bean:write name="one_storage"  property="storagePosition"/>"
						type="text" class="inputtext" style="width:250;" maxlength="25" />
				</template:formTr>
				<template:formTr name="备件所在部门">
					<input name="departId"
						value="<bean:write name="one_storage"  property="departId"/>"
						type="hidden" />
					<bean:write name="one_storage" property="departName" />
				</template:formTr>
				<template:formTr name="入库数量">
					<input name="storageNumber"
						value="<bean:write name="one_storage"  property="storageNumber"/>"
						type="text" class="inputtext" style="width:250;" maxlength="25" />
				</template:formTr>
				<template:formTr name="操作人">
					<input name="storagePerson" type="text"
						value="<bean:write name="one_storage"  property="storagePerson"/>"
						class="inputtext" style="width:250;" maxlength="25" />
				</template:formTr>
				<template:formTr name="入库备注">
					<textarea name="storageRemark" class="inputtextarea"
						style="width:250;" rows="5"><bean:write name="one_storage" property="storageRemark" /></textarea>
				</template:formTr>

				<template:formSubmit>
					<td>
						<html:submit styleClass="button">修改入库</html:submit>
					</td>
					<td>
						<html:reset styleClass="button">取消	</html:reset>
					</td>
					<td>
						<html:button property="action" styleClass="button"
							onclick="addGoBack()">返回</html:button>
					</td>
					<td>
						<html:button property="action" styleClass="button"
							onclick="addGoBack()">返回</html:button>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
