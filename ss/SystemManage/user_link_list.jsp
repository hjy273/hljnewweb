<%@ include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script language="javascript">
	function toUpdate(idValue) {
		var url = "${ctx}/user_link.do?method=updateUserLinkForm&link_type=${link_type}&id="
				+ idValue;
		self.location.replace(url);
	}
	function toDelete(idValue) {
		if (confirm("ȷ��ɾ���ü�¼��")) {
			var url = "${ctx}/user_link.do?method=deleteUserLink&link_type=${link_type}&id="
					+ idValue;
			self.location.replace(url);
		}
	}
</script>
<c:if test="${link_type=='1'}">
	<template:titile value="��ѯ����������Ϣ�б�" />
	<logic:notEmpty name="USER_LINK_LIST" scope="session">
		<display:table name="sessionScope.USER_LINK_LIST" id="oneRowData"
			pagesize="18">
			<bean:define id="rowId" name="oneRowData" property="id"></bean:define>
			<bean:define id="linkType" name="oneRowData" property="link_type"></bean:define>
			<display:column property="order_number" title="��ʾ���"
				style="width:150;" />
			<display:column property="link_name" title="������������"
				style="width:250;" />
			<display:column property="link_address" title="�������ӵ�ַ" />
			<display:column media="html" title="����">
				<a href="javascript:toUpdate('${rowId }')">�޸�</a>
				<a href="javascript:toDelete('${rowId }')">ɾ��</a>
			</display:column>
		</display:table>
	</logic:notEmpty>
</c:if>
<c:if test="${link_type=='0'}">
	<template:titile value="��ѯ����������Ϣ�б�" />
	<logic:notEmpty name="USER_LINK_LIST" scope="session">
		<display:table name="sessionScope.USER_LINK_LIST" id="oneRowData"
			pagesize="18">
			<bean:define id="rowId" name="oneRowData" property="id"></bean:define>
			<bean:define id="linkType" name="oneRowData" property="link_type"></bean:define>
			<display:column property="order_number" title="��ʾ���"
				style="width:150;" />
			<display:column property="link_name" title="������������"
				style="width:250;" />
			<display:column property="link_address" title="�������ӵ�ַ" />
			<display:column media="html" title="����">
				<a href="javascript:toUpdate('${rowId }')">�޸�</a>
				<a href="javascript:toDelete('${rowId }')">ɾ��</a>
			</display:column>
		</display:table>
	</logic:notEmpty>
</c:if>
</h1>
</body>
</html>

