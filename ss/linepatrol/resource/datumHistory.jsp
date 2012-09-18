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
		<template:titile value="���ϸ�����ʷ" />
		<display:table name="sessionScope.addOnes" id="row" pagesize="18"
			export="false" sort="list">
			<display:column media="html" title="����" sortable="true">
				<apptag:assorciateAttr table="datum_type" label="name" key="id"
					keyValue="${datuminfo.typeId}" />
			</display:column>
			<display:column title="����" sortable="true" value="${datuminfo.name}"></display:column>
			<display:column media="html" title="����" sortable="true">
				<a href="/WebApp/downloadAction.do?fileid=${row['FILEID']}">${row['ORIGINALNAME']}</a>
			</display:column>
			<display:column media="html" title="������" sortable="true">
				<apptag:assorciateAttr table="userinfo" label="username"
					key="userid" keyValue="${row['UPLOADER']}" />
			</display:column>
			<display:column media="html" title="����" sortable="true">
			${row['ONCREATE']}
		</display:column>
			<display:column media="html" title="״̬" sortable="true">
				<c:choose>
					<c:when test="${row['IS_USABLE'] eq '1'}">
					���
				</c:when>
					<c:otherwise>
					����
				</c:otherwise>
				</c:choose>
			</display:column>
		</display:table>
		<table style="border: 0px;">
			<tr>
				<td style="border: 0px; text-align: center;">
					<input type="button" value="����" class="button"
						onclick="history.back()">
				</td>
			</tr>
		</table>
	</body>
</html>