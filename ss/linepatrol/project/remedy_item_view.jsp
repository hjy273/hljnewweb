<%@include file="/common/header.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>查看修缮项目</title>
		<script type="text/javascript">
	function goBack() {
		history.back();
	}
</script>
	</head>
		<br>
		<template:titile value="查看修缮项" />
		<template:formTable namewidth="150" contentwidth="300">
			<template:formTr name="项目名称">
				<bean:write name="bean" property="itemName" />
			</template:formTr>
			<template:formTr name="所属区域">
				<bean:write name="regionName" />
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
