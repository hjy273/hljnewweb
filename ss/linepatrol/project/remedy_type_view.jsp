<%@include file="/common/header.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>�鿴�������</title>
		<script type="text/javascript">
	function goBack() {
		history.back();
	}
</script>
	</head>
	<body>
		<br>
		<template:titile value="�鿴�������" />
		<template:formTable namewidth="150" contentwidth="300">
			<template:formTr name="�������">
				<bean:write name="bean" property="typeName" />
			</template:formTr>
			<template:formTr name="������Ŀ">
				<bean:write name="itemBean" property="itemname" />
			</template:formTr>
			<template:formTr name="��λ">
				<bean:write name="bean" property="unit" />
			</template:formTr>
			<template:formTr name="��ע">
				<bean:write name="bean" property="remark" />
			</template:formTr>
			<template:formSubmit>
				<td>
					<input type="button" class="button"
						onclick="location.replace('${sessionScope.S_BACK_URL}')"
						value="����">
				</td>
			</template:formSubmit>
		</template:formTable>
	</body>
</html>
