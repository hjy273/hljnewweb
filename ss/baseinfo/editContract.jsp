<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>���Ӵ�ά��λ�б��װ�</title>
		<script language="javascript" type="text/javascript">
			function isValidForm() {
				if(document.contractBean.contractNo.value==""){
					alert("��ͬ�Ų���Ϊ��!! ");
					document.contractBean.contractNo.focus();
					return false;
				}
				return true;
			}
		</script>
	</head>
	<body>
		<template:titile value="���´�ά��λ�б��װ�" />
		<html:form onsubmit="return isValidForm()" method="Post"
			action="/contractAction.do?method=editContract">
			<template:formTable>
				<template:formTr name="��λ����">
					<input type="hidden" name="id" value="${contract.id }">
					<input type="hidden" name="contractorId" value="${contract.contractorId }">
					<input type="hidden" name="year" value="${contract.year }">
					${contractorName }
				</template:formTr>
				<template:formTr name="���">
					${contract.year }
				</template:formTr>
				<template:formTr name="��װ�">
					<html:textarea property="contractNo" styleClass="inputtextarea"
						style="width:160px" value="${contract.contractNo }"></html:textarea>
					<font color="red">ע�������װ����ö��ŷָ�</font>
				</template:formTr>
				<template:formSubmit>
					<td><html:submit styleClass="button">����</html:submit></td>
					<td><input class="button" type="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="����" /></td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
