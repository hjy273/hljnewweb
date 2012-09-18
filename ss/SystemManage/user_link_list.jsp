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
		if (confirm("确定删除该纪录？")) {
			var url = "${ctx}/user_link.do?method=deleteUserLink&link_type=${link_type}&id="
					+ idValue;
			self.location.replace(url);
		}
	}
</script>
<c:if test="${link_type=='1'}">
	<template:titile value="查询常用链接信息列表" />
	<logic:notEmpty name="USER_LINK_LIST" scope="session">
		<display:table name="sessionScope.USER_LINK_LIST" id="oneRowData"
			pagesize="18">
			<bean:define id="rowId" name="oneRowData" property="id"></bean:define>
			<bean:define id="linkType" name="oneRowData" property="link_type"></bean:define>
			<display:column property="order_number" title="显示序号"
				style="width:150;" />
			<display:column property="link_name" title="常用链接名称"
				style="width:250;" />
			<display:column property="link_address" title="常用链接地址" />
			<display:column media="html" title="操作">
				<a href="javascript:toUpdate('${rowId }')">修改</a>
				<a href="javascript:toDelete('${rowId }')">删除</a>
			</display:column>
		</display:table>
	</logic:notEmpty>
</c:if>
<c:if test="${link_type=='0'}">
	<template:titile value="查询公用链接信息列表" />
	<logic:notEmpty name="USER_LINK_LIST" scope="session">
		<display:table name="sessionScope.USER_LINK_LIST" id="oneRowData"
			pagesize="18">
			<bean:define id="rowId" name="oneRowData" property="id"></bean:define>
			<bean:define id="linkType" name="oneRowData" property="link_type"></bean:define>
			<display:column property="order_number" title="显示序号"
				style="width:150;" />
			<display:column property="link_name" title="公用链接名称"
				style="width:250;" />
			<display:column property="link_address" title="公用链接地址" />
			<display:column media="html" title="操作">
				<a href="javascript:toUpdate('${rowId }')">修改</a>
				<a href="javascript:toDelete('${rowId }')">删除</a>
			</display:column>
		</display:table>
	</logic:notEmpty>
</c:if>
</h1>
</body>
</html>

