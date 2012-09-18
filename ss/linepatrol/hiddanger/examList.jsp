<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
	<script type="text/javascript">
		function exam(id){
			window.location.href = '${ctx}/hiddangerExamAction.do?method=examLink&id='+id;
		}
	</script>
</head>
<body>
	<template:titile value="隐患考核评估" />
	<display:table name="sessionScope.result" id="row" pagesize="18" export="false">
		<display:column property="hiddangerNumber" title="隐患编号" sortable="true"/>
		<display:column property="createrDept" title="登记单位" sortable="true"/>
		<display:column media="html" title="登记人" sortable="true">
			<c:out value="${users[row.creater]}" />
		</display:column>
		<display:column property="name" title="名称" sortable="true"/>
		<display:column media="html" title="分类" sortable="true">
			<c:out value="${types[row.type]}" />
		</display:column>
		<display:column media="html" title="编码" sortable="true">
			<c:out value="${codes[row.code]}" />
		</display:column>
		<display:column property="reporter" title="发现隐患来源" sortable="true"/>
		<display:column media="html" title="处理单位" sortable="true">
			<c:out value="${depts[row.treatDepartment]}" />
		</display:column>
		<display:column property="createTime" title="创建时间" sortable="true"/>
		<display:column media="html" title="操作">
			<a href="javascript:exam('${row.id}')">考核</a>	
		</display:column>
	</display:table>
</body>
</html>