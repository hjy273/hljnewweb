<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
	<script type="text/javascript">
		function regist(id){
			window.location.href = '${ctx}/hiddangerAction.do?method=terminalRegistLink&id='+id;
		}
	</script>
</head>
<body>
	<template:titile value="�ֳ��豸�Ǽ��б�" />
	<display:table name="sessionScope.result" id="row" pagesize="18" export="false">
		<display:column property="name" title="����"/>
		<display:column media="html" title="����">
			<c:out value="${types[row.type]}" />
		</display:column>
		<display:column media="html" title="����">
			<c:out value="${codes[row.code]}" />
		</display:column>
		<display:column property="x" title="X����"/>
		<display:column property="y" title="Y����"/>
		<display:column property="reporter" title="�ϱ���"/>
		<display:column media="html" title="�ϱ�ʱ��">
			<bean:write name="row" property="findTime" format="yy-MM-dd HH:mm:ss"/>
		</display:column>
		<display:column property="terminal" title="�豸���"/>
		<display:column media="html" title="����">
			<a href="javascript:regist('${row.id}','1')">�Ǽ�</a>
		</display:column>
	</display:table>
</body>
</html>