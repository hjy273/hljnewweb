<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	
	<script type='text/javascript'>
		function edit(id){
			window.location.href = '${ctx}/datumAction.do?method=edit&id='+id;
		}
		function history(id){
			window.location.href = '${ctx}/datumAction.do?method=history&id='+id;
		}
		function approve(id){
			window.location.href = '${ctx}/datumAction.do?method=approveLink&id='+id;
		}
	</script>
</head>
<body>
	<template:titile value="维护资料库" />
	<display:table name="sessionScope.result" id="row"  pagesize="18" export="false" sort="list">
	<logic:notEmpty name="result">
		<display:column media="html" title="分类" sortable="true">
			<apptag:assorciateAttr table="datum_type" label="name" key="id" keyValue="${row.typeId}" />
		</display:column>
		<display:column media="html" title="名称" sortable="true">
			<a href="javascript:history('${row.id}')">${row.name}</a>
		</display:column>
		<display:column media="html" title="代维名称" sortable="true">
			<apptag:assorciateAttr table="contractorinfo" label="contractorname" key="contractorid" keyValue="${row.contractorId}" />
		</display:column>
		<display:column media="html" title="状态">
			<c:if test="${row.datumState=='0'}">待审核</c:if>
			<c:if test="${row.datumState=='1'}">审核不通过</c:if>
			<c:if test="${row.datumState=='2'}">审核通过</c:if>
		</display:column>
		<display:column media="html" title="操作">
			<c:choose>
				<c:when test="${LOGIN_USER.deptype eq '1'}">
					<c:if test="${row.datumState=='0'}">
						<a href="javascript:approve('${row.id}');">审核</a>
					</c:if>
				</c:when>
				<c:otherwise>
					<c:if test="${row.datumState=='1'}">
						<a href="javascript:edit('${row.id}');">更新</a>
					</c:if>
				</c:otherwise>
			</c:choose>
		</display:column>
		</logic:notEmpty>
	</display:table>
</body>
</html>