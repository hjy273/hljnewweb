<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title></title>

		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />

		<script type='text/javascript'>

	</script>
	</head>
	<body>
		<template:titile value="资料更新历史" />
		<display:table name="sessionScope.addOnes" id="row" pagesize="18"
			export="false" sort="list">
			<display:column media="html" title="分类" sortable="true">
				<apptag:assorciateAttr table="datum_type" label="name" key="id"
					keyValue="${datuminfo.typeId}" />
			</display:column>
			<display:column title="名称" sortable="true" value="${datuminfo.name}"></display:column>
			<display:column media="html" title="资料" sortable="true">
				<a href="/WebApp/downloadAction.do?fileid=${row['FILEID']}">${row['ORIGINALNAME']}</a>
			</display:column>
			<display:column media="html" title="更新人" sortable="true">
				<apptag:assorciateAttr table="userinfo" label="username"
					key="userid" keyValue="${row['UPLOADER']}" />
			</display:column>
			<display:column media="html" title="日期" sortable="true">
			${row['ONCREATE']}
		</display:column>
			<display:column media="html" title="状态" sortable="true">
				<c:choose>
					<c:when test="${row['IS_USABLE'] eq '1'}">
					入库
				</c:when>
					<c:otherwise>
					新增
				</c:otherwise>
				</c:choose>
			</display:column>
		</display:table>
		<table style="border: 0px;">
			<tr>
				<td style="border: 0px; text-align: center;">
					<input type="button" value="返回" class="button"
						onclick="history.back()">
				</td>
			</tr>
		</table>
	</body>
</html>