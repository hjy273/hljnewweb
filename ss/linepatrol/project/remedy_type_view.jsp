<%@include file="/common/header.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>查看修缮类别</title>
		<script type="text/javascript">
	function goBack() {
		history.back();
	}
</script>
	</head>
	<body>
		<br>
		<template:titile value="查看修缮类别" />
		<template:formTable namewidth="150" contentwidth="300">
			<template:formTr name="类别名称">
				<bean:write name="bean" property="typeName" />
			</template:formTr>
			<template:formTr name="所属项目">
				<bean:write name="itemBean" property="itemname" />
			</template:formTr>
			<template:formTr name="单位">
				<bean:write name="bean" property="unit" />
			</template:formTr>
			<template:formTr name="备注">
				<bean:write name="bean" property="remark" />
			</template:formTr>
			<template:formSubmit>
				<td>
					<input type="button" class="button"
						onclick="location.replace('${sessionScope.S_BACK_URL}')"
						value="返回">
				</td>
			</template:formSubmit>
		</template:formTable>
	</body>
</html>
