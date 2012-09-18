<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>增加代维单位中标合同</title>
		<script language="javascript" type="text/javascript">
			function isValidForm() {
				if(document.contractBean.contractNo.value==""){
					alert("标底包不能为空!! ");
					document.contractBean.contractNo.focus();
					return false;
				}
				return true;
			}
		</script>
	</head>
	<body>
		<template:titile value="增加代维单位中标标底包" />
		<html:form onsubmit="return isValidForm()" method="Post"
			action="/contractAction.do?method=addContract">
			<template:formTable>
				<template:formTr name="单位名称">
					<input type="hidden" name="contractorId" value="${contractorId }">
					${contractorName }
				</template:formTr>
				<template:formTr name="年份">
					<html:select property="year" styleClass="inputtext"
						style="width:160px">
						<html:options name="years"/>
					</html:select>
				</template:formTr>
				<template:formTr name="标底包">
					<html:textarea property="contractNo" styleClass="inputtextarea"
						style="width:160px" />
					<font color="red">注：多个标底包请用逗号分隔</font>
				</template:formTr>
				<template:formSubmit>
					<td><html:submit styleClass="button">增加</html:submit></td>
					<td><input class="button" type="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="返回" /></td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
