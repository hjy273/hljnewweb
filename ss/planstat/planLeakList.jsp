<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
	type="text/css" media="screen, print" />
<body style="width: 95%">
	<template:titile value="©����ϸ��" />
	<display:table name="sessionScope.leaklist" id="currentRowObject"
		pagesize="18">
		<display:column property="sublinename" title="����Ѳ���" sortable="true" />
		<display:column property="pointname" title="Ѳ�������" sortable="true" />
		<display:column property="addressinfo" title="Ѳ���λ��" sortable="true" />
		<display:column property="executetimes" title="�ƻ�Ѳ�����" sortable="true" />
		<display:column property="leaktime" title="©�����" sortable="true" />
		<display:column property="isfocus" title="�Ƿ�ؼ���" sortable="true" />
	</display:table>
</body>

