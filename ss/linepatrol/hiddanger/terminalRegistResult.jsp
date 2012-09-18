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
	<template:titile value="手持设备登记列表" />
	<display:table name="sessionScope.result" id="row" pagesize="18" export="false">
		<display:column property="name" title="名称"/>
		<display:column media="html" title="分类">
			<c:out value="${types[row.type]}" />
		</display:column>
		<display:column media="html" title="编码">
			<c:out value="${codes[row.code]}" />
		</display:column>
		<display:column property="x" title="X坐标"/>
		<display:column property="y" title="Y坐标"/>
		<display:column property="reporter" title="上报人"/>
		<display:column media="html" title="上报时间">
			<bean:write name="row" property="findTime" format="yy-MM-dd HH:mm:ss"/>
		</display:column>
		<display:column property="terminal" title="设备编号"/>
		<display:column media="html" title="操作">
			<a href="javascript:regist('${row.id}','1')">登记</a>
		</display:column>
	</display:table>
</body>
</html>