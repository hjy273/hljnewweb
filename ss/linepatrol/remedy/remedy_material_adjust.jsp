<%@include file="/common/header.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>调整修缮材料</title>
		<link href="${ctx}/css/linepatrol_css.css" rel="stylesheet"
			type="text/css">
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/validate.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/prototype.js"></script>
		<script type="text/javascript">
		submitAdjust=function(){
			var addForm=document.getElementById("addForm")
			if(checkFormData(addForm)){
				addForm.submit();
			}
		}
		checkFormData=function(addForm){
			var flag=judgeEmptyValue(addForm.adjustNewNum,"修改使用数量");
			flag=flag&&judgeValueIsFloat(addForm.adjustNewNum,"修改使用数量必须为数字！");
			
			var storageNumber=parseFloat("<bean:write property="materialStorageNumber" name="remedy_material" />");
			var oldNumber=parseFloat(addForm.adjustOldNum.value);
			var newNumber=parseFloat(addForm.adjustNewNum.value);
			if(oldNumber==newNumber){
				alert("没有进行使用数量的调整！");
				flag=flag&&false;
			}
			
			if(newNumber>storageNumber+oldNumber){
				alert("修缮申请中的修缮材料库存不足!");
				flag=flag&&false;
			}
			return flag;
		}
		goBack=function(){
			var url = '<%=(String) request.getSession().getAttribute("S_BACK_URL")%>';
			self.location.replace(url);
		}
		</script>
	</head>
	<body>
		<br>
		<template:titile value="调整修缮材料" />
		<html:form action="/remedy_material.do?method=adjustMaterial"
			styleId="addForm">
			<template:formTable namewidth="150" contentwidth="600">
				<template:formTr name="编号" style="background-color:#FFFFFF;">
					<bean:write property="remedyCode" name="remedy_material" />
					<input name="id"
						value="<bean:write property="id" name="remedy_material" />"
						type="hidden" />
					<input name="remedyId"
						value="<bean:write property="remedyId" name="remedy_material" />"
						type="hidden" />
					<input name="remedyCode"
						value="<bean:write property="remedyCode" name="remedy_material" />"
						type="hidden" />
					<input name="materialId"
						value="<bean:write property="materialId" name="remedy_material" />"
						type="hidden" />
					<input name="materialStorageAddress"
						value="<bean:write property="materialStorageAddress" name="remedy_material" />"
						type="hidden" />
					<input name="materialStorageAddressId"
						value="<bean:write property="materialStorageAddressId" name="remedy_material" />"
						type="hidden" />
					<input name="materialStorageType"
						value="<bean:write property="materialStorageType" name="remedy_material" />"
						type="hidden" />
					<input name="adjustOldNum"
						value="<bean:write property="adjustOldNum" name="remedy_material" />"
						type="hidden" />
				</template:formTr>
				<template:formTr name="材料名称" style="background-color:#FFFFFF;">
					<bean:write property="materialName" name="remedy_material" />
				</template:formTr>
				<template:formTr name="修缮项目名称" style="background-color:#FFFFFF;">
					<bean:write property="remedyProjectName" name="remedy_material" />
				</template:formTr>
				<template:formTr name="存放地点" style="background-color:#FFFFFF;">
					<bean:write property="materialStorageAddress"
						name="remedy_material" />
				</template:formTr>
				<template:formTr name="存放材料类型" style="background-color:#FFFFFF;">
					<logic:equal value="1" property="materialStorageType"
						name="remedy_material">利旧材料</logic:equal>
					<logic:equal value="0" property="materialStorageType"
						name="remedy_material">新增材料</logic:equal>
				</template:formTr>
				<template:formTr name="现有库存数量" style="background-color:#FFFFFF;">
					<bean:write property="materialStorageNumber" name="remedy_material" />
				</template:formTr>
				<template:formTr name="已经使用数量" style="background-color:#FFFFFF;">
					<bean:write property="adjustOldNum" name="remedy_material" />
				</template:formTr>
				<template:formTr name="修改使用数量" style="background-color:#FFFFFF;">
					<html:text property="adjustNewNum" styleClass="inputtext"
						style="width:215;" maxlength="20" />
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:button property="action" styleClass="button"
							onclick="submitAdjust();">提交</html:button>
					</td>
					<td>
						<html:reset property="action" styleClass="button">重置</html:reset>
					</td>
					<td>
						<html:button property="action" styleClass="button"
							onclick="goBack();">返回</html:button>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
