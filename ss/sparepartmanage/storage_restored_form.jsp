<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript">
	var maxStorageNum="<bean:write name="spare_part_storage" property="storageNumber" />";
	checkValid=function(addForm){
		if(addForm.restoreNumber.value==""){
			alert("重新入库数量不能为空！");
			addForm.restoreNumber.focus();
			return false;
		}
		if(!valiDigit(addForm.restoreNumber,"重新入库数量")){
			return false;
		}
		if(parseFloat(addForm.restoreNumber.value)>parseFloat(maxStorageNum)){
			alert("重新入库数量大于送修数量，请重新填写！");
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
            alert("你填写的"+msg+"不是数字,请重新输入");
            obj.value="0";
            obj.focus();
            return false;
        }
    }
	</script>
	<body>
		<br>
		<template:titile value="移动公司重新入库" />
		<html:form action="/SparepartStorageAction.do?method=restoreToStorage"
			styleId="addForm" onsubmit="return checkValid(this);">
			<template:formTable namewidth="150" contentwidth="350">
				<template:formTr name="入库备件">
					<input name="storageId"
						value="<bean:write name="spare_part_storage" property="tid" />"
						type="hidden" />
					<bean:write name="spare_part_storage" property="sparePartName" />
				</template:formTr>
				<template:formTr name="备件序列号">
					<bean:write name="spare_part_storage" property="serialNumber" />
				</template:formTr>
				<logic:notEmpty name="init_storage_position">
					<template:formTr name="备件最初存放位置">
						<%=(String)request.getAttribute("init_storage_position") %>
					</template:formTr>
				</logic:notEmpty>
				<template:formTr name="入库数量">
					<input type="text" name="restoreNumber" class="inputtext"
						style="width:250;" maxlength="25" />
				</template:formTr>

				<template:formSubmit>
					<td>
						<html:submit styleClass="button">重新入库</html:submit>
					</td>
					<td>
						<html:reset styleClass="button">取消	</html:reset>
					</td>
					<td>
						<html:button property="action" styleClass="button"
							onclick="addGoBackMod()">返回</html:button>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
