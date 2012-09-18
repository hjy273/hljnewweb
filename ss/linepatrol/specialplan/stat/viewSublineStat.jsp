<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
	<script type="text/javascript">
	</script>
</head>
<body>
	<template:titile value="Ѳ���߶�" />
	<display:table name="sessionScope.result" id="row"  pagesize="18" sort="list">
		<display:column media="html" title="��Ѳ�ƻ�" sortable="true">
			<apptag:assorciateAttr table="lp_special_plan" label="plan_name" key="id" keyValue="${row.specPlanId}" />
		</display:column>
		<display:column media="html" title="Ѳ�������" sortable="true">
			<apptag:assorciateAttr table="sublineinfo" label="sublinename" key="sublineid" keyValue="${row.sublineId}" />
		</display:column>
		<display:column media="html" title="�߶μ���" sortable="true">
			<apptag:quickLoadList cssClass="input" keyValue="${row.sublineLevel}" name="cabletype" listName="cabletype" type="look"/>
		</display:column>
		<display:column media="html" title="Ѳ����" sortable="true">
			<apptag:assorciateAttr table="patrolmaninfo" label="patrolname" key="patrolid" keyValue="${row.patrolGroupId}" />
		</display:column>
		<display:column media="html" title="�ƻ�Ѳ�����/���" sortable="true">
			${row.planPointhNumber}��/${row.planPointhTimes}��
		</display:column>
		<display:column media="html" title="ʵ��Ѳ�����/���" sortable="true">
			${row.factPointhNumber}��/${row.factPointhTimes}��
		</display:column>
		<display:column media="html" title="�ƻ�/ʵ��Ѳ�����" sortable="true">
			${row.planPatrolMileage}����/${row.factPatrolMileage}����
		</display:column>
		<display:column media="html" title="Ѳ����" sortable="true">
			${row.patrolRadio}%
		</display:column>
	</display:table>
</body>