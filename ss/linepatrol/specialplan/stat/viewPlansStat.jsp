<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>
</head>
<body onload="changeStyle()"> 
	<template:titile value="������Ϣ" />
	<table  width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
		<tr>
			<td class="tdulleft">�ƻ�Ѳ�������</td>
			<td class="tdulright">${planStat.allPlanPointNumbers}</td>
			<td class="tdulleft">ʵ��Ѳ�������</td>
			<td class="tdulright">${planStat.allFactPointNumbers}</td>
		</tr>
		<tr>
			<td class="tdulleft">Ѳ������ʣ�</td>
			<td class="tdulright">${planStat.patrolRatio} %</td>
			<td class="tdulleft">©���ʣ�</td>
			<td class="tdulright">${planStat.leakRatio} %</td>
		</tr>
		<tr>
			<td class="tdulleft">�ƻ�Ѳ���߶�������</td>
			<td class="tdulright">${planStat.planSublineNumber}</td>
			<td class="tdulleft">δѲ���߶�������</td>
			<td class="tdulright">${planStat.noPatrolSublineNumber}</td>
		</tr>
		<tr>
			<td class="tdulleft">δ���Ѳ���߶�������</td>
			<td class="tdulright">${planStat.noCompleteSublineNumber}</td>
			<td class="tdulleft">���Ѳ���߶�������</td>
			<td class="tdulright">${planStat.completeSublineNumber}</td>
		</tr>
		<tr>
			<td class="tdulleft">�ƻ�Ѳ����̣�</td>
			<td class="tdulright">${planStat.planPatrolMileage} ����</td>
			<td class="tdulleft">ʵ��Ѳ����̣�</td>
			<td class="tdulright">${planStat.factPatrolMileage} ����</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">�ƻ���������</td>
			<td class="tdulright">${planStat.planWatchNumber}</td>
			<td class="tdulleft">ʵ�ʶ�������</td>
			<td class="tdulright">${planStat.factWatchNumber}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">��������ʣ�</td>
			<td class="tdulright" colspan=3>${planStat.watchRatio} %</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">�ƻ���������������</td>
			<td class="tdulright">${planStat.planWatchAreaNumber}</td>
			<td class="tdulleft">δ��������������</td>
			<td class="tdulright">${planStat.noWatchAreaNumber}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">δ��ɶ�������������</td>
			<td class="tdulright">${planStat.noCompleteAreaNumber}</td>
			<td class="tdulleft">��ɶ�������������</td>
			<td class="tdulright">${planStat.completeAreaNumber}</td>
		</tr>
	</table>
</body>