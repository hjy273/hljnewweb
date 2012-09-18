<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>查询修缮材料</title>
		<script type="text/javascript" src="./js/prototype.js"></script>
		<script type="text/javascript" src="./js/json2.js"></script>
	</head>
	<body>
		<br>
		<template:titile value="查询修缮材料" />
		<html:form action="/remedy_material.do?method=queryList"
			styleId="queryForm" method="post">
			<template:formTable namewidth="200" contentwidth="200">
				<html:hidden property="reset_query" value="1" />
				<html:hidden property="power" value="53001" />
				<template:formTr name="编号">
					<html:text property="remedyCode" styleClass="inputtext"
						style="width:215;" maxlength="20" />
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:submit styleClass="button">查询</html:submit>
					</td>
					<td>
						<html:reset styleClass="button">重置</html:reset>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
		<script type="text/javascript">
		</script>
	</body>
</html>
