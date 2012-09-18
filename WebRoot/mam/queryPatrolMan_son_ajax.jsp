<%@ page language="java" contentType="text/html; charset=GBK"%><%@ include file="/common/header.jsp"%>
<SCRIPT language=javascript src="${ctx}/js/prototype.js" type=""></SCRIPT>
<SCRIPT language=javascript src="${ctx}/js/parsexml.js" type=""></SCRIPT>

<body>
<template:titile value="查询巡检员信息" />
	<html:form method="Post"
		action="/patrolSonAction.do?method=queryPatrolSon">
		<template:formTable contentwidth="300" namewidth="150">
            <%@include file="/common/linkpatrol.jsp" %>
			<template:formTr name="性&nbsp;&nbsp;&nbsp;&nbsp;别">
				<html:select property="sex" styleClass="inputtext" style="width:225">
					<html:option value="">不限</html:option>
					<html:option value="1">男</html:option>
					<html:option value="2">女</html:option>
				</html:select>
			</template:formTr>
			<template:formTr name="工作状态" >
				<html:select property="jobState" styleClass="inputtext"
					style="width:225">
					<html:option value="">不限</html:option>
					<html:option value="1">在岗</html:option>
					<html:option value="2">休假</html:option>
					<html:option value="3">离职</html:option>
				</html:select>
			</template:formTr>


			<template:formSubmit>
				<td>
					<html:submit styleClass="button">查询</html:submit>
				</td>
				<td>
					<html:reset styleClass="button">取消</html:reset>
				</td>
			</template:formSubmit>
		</template:formTable>
	</html:form>
</body>
