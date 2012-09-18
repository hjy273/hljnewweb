<%@include file="/common/header.jsp"%>
<html>
	<head>
		<script type="text/javascript">
			checkInfo=function(equipmentFormId) {	
				if(equipmentFormId.layer.value==0){
					alert("请选择类型！");
					equipmentFormId.layer.focus();
					return false;
				}
				if(equipmentFormId.contractorId.value==0){
					alert("请选择代维！");
					equipmentFormId.contractorId.focus();
					return false;
				}
				if(equipmentFormId.equipmentName.value==""){
					alert("设备名称不能为空！");
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
		<template:titile value="机房设备信息一览表"/>
		<html:form action="EquipmentInfoAction.do?method=updateForEqu" styleId="equipmentFormId" method="post" onsubmit="return checkInfo(this);">
			
			<input type="hidden" name="eid" value="<bean:write name="EquipmentInfoBean" property="eid"/>">
			<template:formTable namewidth="150" contentwidth="250">
				<template:formTr name="类型">
					<select name="layer" style="width:245px;" class="inputtext" id="layerId">
						<option value="0">
							请选择类型
						</option>
						<option value="1">
							核心层
						</option>
						<option value="2">
							接入层SDH
						</option>
						<option value="3">
							接入层微波
						</option>
						<option value="4">
							接入层FSO
						</option>
						<option value="5">
							光交维护
						</option>
					</select>
				</template:formTr>
				
				<template:formTr name="代维公司">
					<select class="inputtext" name="contractorId" style="width:245px;" id="contractorId">
						<option value="0">
							请选择代维
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
				
				<template:formTr name="设备名称">
					<input type="text" name="equipmentName" style="width:245px" class="inputtext" id="equipmentName" value="<bean:write name="EquipmentInfoBean" property="equipmentName"/>">
				</template:formTr>
				
				<template:formSubmit>
					<td>
						<html:submit styleClass="button">修改</html:submit>
					</td>
					<td>
						<html:reset styleClass="button">取消	</html:reset>
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