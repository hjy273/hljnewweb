<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript">
	checkValid=function(addForm){
		return true;
	}
	addGoBack=function(){
		var url = "${ctx}/SparepartStorageAction.do?method=doQueryForRestored";
		self.location.replace(url);
	}
	</script>
	<body>
		<br/>
		<template:titile value="查看单个库存信息" />
			<template:formTable namewidth="150" contentwidth="350" th1="项目" th1="内容">
				<template:formTr name="入库备件">
					<bean:write name="one_storage" property="sparePartName" />
				</template:formTr>
				<template:formTr name="备件序列号">
					<bean:write name="one_storage" property="serialNumber" />
				</template:formTr>
				<template:formTr name="保存位置">
					<bean:write name="one_storage" property="storagePosition" />
				</template:formTr>
				<template:formTr name="备件所在部门">
					<bean:write name="one_storage" property="departName" />
				</template:formTr>
				<template:formTr name="备件当前所在位置">
					<bean:write name="one_storage" property="storagePosition" />
				</template:formTr>
				<logic:notEmpty name="taken_out_position">
					<template:formTr name="备件来源">
						<%=(String)request.getAttribute("taken_out_position") %>
					</template:formTr>
				</logic:notEmpty>
				<logic:notEmpty name="init_storage_position">
					<template:formTr name="备件最初存放位置">
						<%=(String)request.getAttribute("init_storage_position") %>
					</template:formTr>
				</logic:notEmpty>
				<template:formTr name="库存数量">
					<bean:write name="one_storage" property="storageNumber" />
				</template:formTr>
				<template:formTr name="备件状态">
					<logic:equal value="01" name="one_storage" property="sparePartState">
						<img src="${ctx}/images/sparepartstate/01.gif" alt="移动备用">
					</logic:equal>
					<logic:equal value="02" name="one_storage" property="sparePartState">
						<img src="${ctx}/images/sparepartstate/02.gif" alt="代维备用">
					</logic:equal>
					<logic:equal value="03" name="one_storage" property="sparePartState">
						<img src="${ctx}/images/sparepartstate/03.gif" alt="运行">
					</logic:equal>
					<logic:equal value="04" name="one_storage" property="sparePartState">
						<img src="${ctx}/images/sparepartstate/04.gif" alt="送修">
					</logic:equal>
					<logic:equal value="05" name="one_storage" property="sparePartState">
						<img src="${ctx}/images/sparepartstate/05.gif" alt="报废">
					</logic:equal>
				</template:formTr>
				<template:formTr name="操作人">
					<bean:write name="one_storage" property="storagePerson" />
				</template:formTr>
				<template:formTr name="备注">
					<bean:write name="one_storage" property="storageRemark" />
				</template:formTr>
				
				<template:formSubmit>
					<td>
						<html:button property="action" styleClass="button"
							onclick="addGoBack()">返回</html:button>
					</td>
				</template:formSubmit>
			</template:formTable>
			
			<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px;text-align: center;" >
				<tr>
					<td >说明：
						<img src="${ctx}/images/sparepartstate/01.gif">表示“移动备用”状态，
						<img src="${ctx}/images/sparepartstate/02.gif">表示“代维备用”状态，
						<img src="${ctx}/images/sparepartstate/03.gif">表示“运行”状态，
						<img src="${ctx}/images/sparepartstate/04.gif">表示“送修”状态，
						<img src="${ctx}/images/sparepartstate/05.gif">表示“报废”状态。
					</td>
				</tr>
			</table>
	</body>
</html>
