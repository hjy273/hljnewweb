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
	<template:titile value="ά�����Ͽ�" />
	<display:table name="sessionScope.result" id="row"  pagesize="18" export="false" sort="list">
	<logic:notEmpty name="result">
		<display:column media="html" title="����" sortable="true">
			<apptag:assorciateAttr table="datum_type" label="name" key="id" keyValue="${row.typeId}" />
		</display:column>
		<display:column media="html" title="����" sortable="true">
			<a href="javascript:history('${row.id}')">${row.name}</a>
		</display:column>
		<display:column media="html" title="��ά����" sortable="true">
			<apptag:assorciateAttr table="contractorinfo" label="contractorname" key="contractorid" keyValue="${row.contractorId}" />
		</display:column>
		<display:column media="html" title="״̬">
			<c:if test="${row.datumState=='0'}">�����</c:if>
			<c:if test="${row.datumState=='1'}">��˲�ͨ��</c:if>
			<c:if test="${row.datumState=='2'}">���ͨ��</c:if>
		</display:column>
		<display:column media="html" title="����">
			<c:choose>
				<c:when test="${LOGIN_USER.deptype eq '1'}">
					<c:if test="${row.datumState=='0'}">
						<a href="javascript:approve('${row.id}');">���</a>
					</c:if>
				</c:when>
				<c:otherwise>
					<c:if test="${row.datumState=='1'}">
						<a href="javascript:edit('${row.id}');">����</a>
					</c:if>
				</c:otherwise>
			</c:choose>
		</display:column>
		</logic:notEmpty>
	</display:table>
</body>
</html>