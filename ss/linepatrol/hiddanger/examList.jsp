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
	<template:titile value="������������" />
	<display:table name="sessionScope.result" id="row" pagesize="18" export="false">
		<display:column property="hiddangerNumber" title="�������" sortable="true"/>
		<display:column property="createrDept" title="�Ǽǵ�λ" sortable="true"/>
		<display:column media="html" title="�Ǽ���" sortable="true">
			<c:out value="${users[row.creater]}" />
		</display:column>
		<display:column property="name" title="����" sortable="true"/>
		<display:column media="html" title="����" sortable="true">
			<c:out value="${types[row.type]}" />
		</display:column>
		<display:column media="html" title="����" sortable="true">
			<c:out value="${codes[row.code]}" />
		</display:column>
		<display:column property="reporter" title="����������Դ" sortable="true"/>
		<display:column media="html" title="����λ" sortable="true">
			<c:out value="${depts[row.treatDepartment]}" />
		</display:column>
		<display:column property="createTime" title="����ʱ��" sortable="true"/>
		<display:column media="html" title="����">
			<a href="javascript:exam('${row.id}')">����</a>	
		</display:column>
	</display:table>
</body>
</html>