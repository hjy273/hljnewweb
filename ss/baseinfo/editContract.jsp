<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>增加代维单位中标标底包</title>
		<script language="javascript" type="text/javascript">
			function isValidForm() {
				if(document.contractBean.contractNo.value==""){
					alert("合同号不能为空!! ");
					document.contractBean.contractNo.focus();
					return false;
				}
				return true;
			}
		</script>
	</head>
	<body>
		<template:titile value="更新代维单位中标标底包" />
		<html:form onsubmit="return isValidForm()" method="Post"
			action="/contractAction.do?method=editContract">
			<template:formTable>
				<template:formTr name="单位名称">
					<input type="hidden" name="id" value="${contract.id }">
					<input type="hidden" name="contractorId" value="${contract.contractorId }">
					<input type="hidden" name="year" value="${contract.year }">
					${contractorName }
				</template:formTr>
				<template:formTr name="年份">
					${contract.year }
				</template:formTr>
				<template:formTr name="标底包">
					<html:textarea property="contractNo" styleClass="inputtextarea"
						style="width:160px" value="${contract.contractNo }"></html:textarea>
					<font color="red">注：多个标底包请用逗号分隔</font>
				</template:formTr>
				<template:formSubmit>
					<td><html:submit styleClass="button">更新</html:submit></td>
					<td><input class="button" type="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="返回" /></td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
