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
		<template:titile value="���Ѳ��ά�������뱸��" />
		<html:form action="/SparepartAuditingAction.do?method=auditingApply"
			styleId="addForm" onsubmit="return checkValid(this);">
			<html:hidden property="tid"></html:hidden>
			<template:formTable namewidth="150" contentwidth="350">
				<template:formTr name="����ά����">
					<bean:write name="one_apply" property="patrolgroupName" />
				</template:formTr>
				<template:formTr name="��������">
					<bean:write name="one_apply" property="sparePartName" />
				</template:formTr>
				<template:formTr name="�������к�">
					<logic:iterate id="item" name="applySs">			            
			            <bean:write name="item" property="serialNumber"/><br>
					 </logic:iterate>
				</template:formTr>
				<template:formTr name="ʹ�÷�ʽ">
					<logic:equal value="01" name="one_apply" property="useMode">
						ֱ��ʹ��
					</logic:equal>
					<logic:equal value="02" name="one_apply" property="useMode">
						����ʹ��
					</logic:equal>
				</template:formTr>
				<logic:equal value="02" name="one_apply" property="useMode">
					<template:formTr name="��������">
						<logic:equal value="01" name="one_apply" property="replaceType">
							�˻��ɱ���
						</logic:equal>
						<logic:equal value="02" name="one_apply" property="replaceType">
							���޾ɱ���
						</logic:equal>
						<logic:equal value="03" name="one_apply" property="replaceType">
							���Ͼɱ���
						</logic:equal>
					</template:formTr>
					<template:formTr name="�������������к�">
						<logic:iterate id="item" name="applySs">			            
				            <bean:write name="item" property="usedSerialNumber"/><br>
						</logic:iterate>
					</template:formTr>
				</logic:equal>
				<template:formTr name="����ʹ��λ��">
					<bean:write name="one_apply" property="applyUsePosition" />
				</template:formTr>
				<template:formTr name="������">
					<bean:write name="one_apply" property="applyPerson" />
				</template:formTr>
				<template:formTr name="���뱸ע">
					<bean:write name="one_apply" property="applyRemark" />
				</template:formTr>
				<template:formTr name="��˽��">
					<input name="storage_id" type="hidden" 
						value="<bean:write name="one_apply" property="takenOutStorage" />" />
					<html:select property="auditingResult" styleClass="inputtext" style="width:250;">
						<html:option value="01">���ͨ��</html:option>
						<html:option value="02">��˲�ͨ��</html:option>
					</html:select>
				</template:formTr>
				<template:formTr name="�����">
					<%=(String) request.getAttribute("user_name")%>
					<input name="applyId" type="hidden"
						value="<bean:write name="one_apply" property="tid" />" />
					<input name="auditingPerson" type="hidden"
						value="<%=(String) request.getAttribute("user_id")%>"/>
				</template:formTr>
				<template:formTr name="������">
					<html:textarea property="auditingRemark" styleClass="inputtextarea"
						style="width:250;" rows="5" />
				</template:formTr>

				<template:formSubmit>
					<td>
						<html:submit styleClass="button">�ύ</html:submit>
					</td>
					<td>
						<html:reset styleClass="button">����</html:reset>
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
