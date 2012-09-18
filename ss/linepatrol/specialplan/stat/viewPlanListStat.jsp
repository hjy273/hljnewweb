<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
	<script type="text/javascript">
		function viewDetail(id, type){
			var url = "${ctx}/linepatrol/specialplan/stat/stat.jsp?type="+type+"&id="+id;
			parent.location.href = url;
		}
	</script>
</head>
<body>
	<template:titile value="�ƻ�ͳ���б�" />
	<display:table name="sessionScope.result" id="row"  pagesize="18" sort="list">
		<display:column property="specPlanName" title="�ƻ�����" sortable="true" />
		<display:column media="html" title="�ƻ�����" sortable="true">
			<c:if test="${row.specPlanType eq '001'}">
				�����ƻ�
			</c:if>
			<c:if test="${row.specPlanType eq '002'}">
				���ϼƻ�
			</c:if>
		</display:column>
		<display:column media="html" title="Ѳ��ͳ��״̬" sortable="true">
			<c:choose>
				<c:when test="${row.patrolStatState eq '1'}">
					�ƻ�ִ����
				</c:when>
				<c:otherwise>
					�ƻ�ִ�����
				</c:otherwise>
			</c:choose>
		</display:column>
		<display:column media="html" title="Ѳ����" sortable="true">
			${row.patrolRatio}%
		</display:column>
		<display:column media="html" title="���������" sortable="true">
			${row.watchRatio}%
		</display:column>
		<display:column media="html" title="����" sortable="true">
			<a href="javascript:viewDetail('${row.specPlanId}','${row.specPlanType}')">��ϸ</a>
		</display:column>
	</display:table>
</body>