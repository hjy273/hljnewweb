<%@ include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script language="javascript">
	function toUpdate(idValue) {
		var url = "${ctx}/user_mail.do?method=updateUserMailForm&id="
				+ idValue;
		self.location.replace(url);
	}
	function toDelete(idValue) {
		if (confirm("确定删除该纪录？")) {
			var url = "${ctx}/user_mail.do?method=deleteUserMail&id="
					+ idValue;
			self.location.replace(url);
		}
	}
</script>
<template:titile value="查询邮箱信息列表" />
<logic:notEmpty name="USER_MAIL_LIST" scope="session">
	<display:table name="sessionScope.USER_MAIL_LIST" id="oneRowData"
		pagesize="18">
		<bean:define id="rowId" name="oneRowData" property="id"></bean:define>
		<display:column property="order_number" title="显示序号"
			style="width:150;" />
		<display:column property="mail_name" title="邮箱名称" style="width:250;" />
		<display:column property="email_address" title="邮箱地址" />
		<display:column media="html" title="操作">
			<a href="javascript:toUpdate('${rowId }')">修改</a>
			<a href="javascript:toDelete('${rowId }')">删除</a>
		</display:column>
	</display:table>
</logic:notEmpty>
</h1>
</body>
</html>

