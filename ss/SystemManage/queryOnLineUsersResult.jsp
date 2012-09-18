<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>

<template:titile value="当前在线人数信息"/>

<display:table name="sessionScope.queryresult"  id="resultList" pagesize="15">
	<display:column property="userid" title="用户ID" />
	<display:column property="username" title="用户姓名" />
	<display:column property="dept" title="所属部门" />
	<display:column property="region" title="所属区域" />
	<display:column property="ip" title="登陆IP" />
	<display:column property="logintime" title="登陆时间" />
</display:table>
</h1>
</body>
</html>

