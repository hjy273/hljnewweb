<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript">
	checkValid=function(addForm){
		return true;
	}
	addGoBackMod=function() {
		//var url = "${ctx}/SparepartApplyAction.do?method=listWaitAuditingApplyForAu";
		//self.location.replace(url);
		history.back();
	}
	</script>
	<body>
		<br>
		<template:titile value="审核巡检维护组申请备件" />
		<html:form action="/SparepartAuditingAction.do?method=auditingApply"
			styleId="addForm" onsubmit="return checkValid(this);">
			<html:hidden property="tid"></html:hidden>
			<template:formTable namewidth="150" contentwidth="350">
				<template:formTr name="申请维护组">
					<bean:write name="one_apply" property="patrolgroupName" />
				</template:formTr>
				<template:formTr name="备件名称">
					<bean:write name="one_apply" property="sparePartName" />
				</template:formTr>
				<template:formTr name="备件序列号">
					<logic:iterate id="item" name="applySs">			            
			            <bean:write name="item" property="serialNumber"/><br>
					 </logic:iterate>
				</template:formTr>
				<template:formTr name="使用方式">
					<logic:equal value="01" name="one_apply" property="useMode">
						直接使用
					</logic:equal>
					<logic:equal value="02" name="one_apply" property="useMode">
						更换使用
					</logic:equal>
				</template:formTr>
				<logic:equal value="02" name="one_apply" property="useMode">
					<template:formTr name="更换类型">
						<logic:equal value="01" name="one_apply" property="replaceType">
							退还旧备件
						</logic:equal>
						<logic:equal value="02" name="one_apply" property="replaceType">
							送修旧备件
						</logic:equal>
						<logic:equal value="03" name="one_apply" property="replaceType">
							报废旧备件
						</logic:equal>
					</template:formTr>
					<template:formTr name="被更换备件序列号">
						<logic:iterate id="item" name="applySs">			            
				            <bean:write name="item" property="usedSerialNumber"/><br>
						</logic:iterate>
					</template:formTr>
				</logic:equal>
				<template:formTr name="申请使用位置">
					<bean:write name="one_apply" property="applyUsePosition" />
				</template:formTr>
				<template:formTr name="申请人">
					<bean:write name="one_apply" property="applyPerson" />
				</template:formTr>
				<template:formTr name="申请备注">
					<bean:write name="one_apply" property="applyRemark" />
				</template:formTr>
				<template:formTr name="审核结果">
					<input name="storage_id" type="hidden" 
						value="<bean:write name="one_apply" property="takenOutStorage" />" />
					<html:select property="auditingResult" styleClass="inputtext" style="width:250;">
						<html:option value="01">审核通过</html:option>
						<html:option value="02">审核不通过</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="审核人">
					<%=(String) request.getAttribute("user_name")%>
					<input name="applyId" type="hidden"
						value="<bean:write name="one_apply" property="tid" />" />
					<input name="auditingPerson" type="hidden"
						value="<%=(String) request.getAttribute("user_id")%>"/>
				</template:formTr>
				<template:formTr name="审核意见">
					<html:textarea property="auditingRemark" styleClass="inputtextarea"
						style="width:250;" rows="5" />
				</template:formTr>

				<template:formSubmit>
					<td>
						<html:submit styleClass="button">提交</html:submit>
					</td>
					<td>
						<html:reset styleClass="button">重置</html:reset>
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
