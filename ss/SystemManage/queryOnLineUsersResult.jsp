<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>

<template:titile value="��ǰ����������Ϣ"/>

<display:table name="sessionScope.queryresult"  id="resultList" pagesize="15">
	<display:column property="userid" title="�û�ID" />
	<display:column property="username" title="�û�����" />
	<display:column property="dept" title="��������" />
	<display:column property="region" title="��������" />
	<display:column property="ip" title="��½IP" />
	<display:column property="logintime" title="��½ʱ��" />
</display:table>
</h1>
</body>
</html>

