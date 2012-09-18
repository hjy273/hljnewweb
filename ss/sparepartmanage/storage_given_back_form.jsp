<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript">
	var maxStorageNum="<bean:write name="spare_part_storage" property="storageNumber" />";
	checkValid=function(addForm){
		if(addForm.givenBackNumber.value==""){
			alert("归还数量不能为空！");
			addForm.givenBackNumber.focus();
			return false;
		}
		if(!valiDigit(addForm.givenBackNumber,"归还数量")){
			return false;
		}
		if(parseFloat(addForm.givenBackNumber.value)>parseFloat(maxStorageNum)){
			alert("归还数量大于库存数量，请重新填写！");
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
            alert("你填写的"+msg+"不是数字,请重新输入");
            obj.value="0";
            obj.focus();
            return false;
        }
    }
	</script>
	<body>
		<template:titile value="代维单位归还" />
		<html:form
			action="/SparepartStorageAction.do?method=giveBackToStorage"
			styleId="addForm" onsubmit="return checkValid(this);">
			<template:formTable namewidth="150" contentwidth="350">
				<template:formTr name="归还备件">
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
				<template:formTr name="备件序列号">
					<input name="serialNumber"
						value="<bean:write name="spare_part_storage" property="serialNumber" />"
						class="inputtext" type="hidden" style="width:250;" maxlength="25" />
					<bean:write name="spare_part_storage" property="serialNumber" />
				</template:formTr>
				<template:formTr name="备件所在部门">
					<input name="departId"
						value="<%=(String) request.getAttribute("dept_id")%>"
						type="hidden" class="inputtext" style="width:250;" />
					<%=(String) request.getAttribute("dept_name")%>
				</template:formTr>
				<template:formTr name="备件当前所在位置">
					<bean:write name="spare_part_storage" property="storagePosition" />
				</template:formTr>
				<logic:notEmpty name="taken_out_position">
					<template:formTr name="备件来源">
						<%=(String)request.getAttribute("taken_out_position") %>
					</template:formTr>
				</logic:notEmpty>
				<template:formTr name="归还数量">
					<input name="givenBackNumber" class="inputtext" type="text"
						style="width:250;" maxlength="25" />
				</template:formTr>

				<template:formSubmit>
					<td>
						<html:submit styleClass="button">归还</html:submit>
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
