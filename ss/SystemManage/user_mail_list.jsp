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
		if (confirm("ȷ��ɾ���ü�¼��")) {
			var url = "${ctx}/user_mail.do?method=deleteUserMail&id="
					+ idValue;
			self.location.replace(url);
		}
	}
</script>
<template:titile value="��ѯ������Ϣ�б�" />
<logic:notEmpty name="USER_MAIL_LIST" scope="session">
	<display:table name="sessionScope.USER_MAIL_LIST" id="oneRowData"
		pagesize="18">
		<bean:define id="rowId" name="oneRowData" property="id"></bean:define>
		<display:column property="order_number" title="��ʾ���"
			style="width:150;" />
		<display:column property="mail_name" title="��������" style="width:250;" />
		<display:column property="email_address" title="�����ַ" />
		<display:column media="html" title="����">
			<a href="javascript:toUpdate('${rowId }')">�޸�</a>
			<a href="javascript:toDelete('${rowId }')">ɾ��</a>
		</display:column>
	</display:table>
</logic:notEmpty>
</h1>
</body>
</html>

