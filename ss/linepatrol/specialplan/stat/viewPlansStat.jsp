<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>
</head>
<body onload="changeStyle()"> 
	<template:titile value="总体信息" />
	<table  width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
		<tr>
			<td class="tdulleft">计划巡检点数：</td>
			<td class="tdulright">${planStat.allPlanPointNumbers}</td>
			<td class="tdulleft">实际巡检次数：</td>
			<td class="tdulright">${planStat.allFactPointNumbers}</td>
		</tr>
		<tr>
			<td class="tdulleft">巡检完成率：</td>
			<td class="tdulright">${planStat.patrolRatio} %</td>
			<td class="tdulleft">漏检率：</td>
			<td class="tdulright">${planStat.leakRatio} %</td>
		</tr>
		<tr>
			<td class="tdulleft">计划巡检线段数量：</td>
			<td class="tdulright">${planStat.planSublineNumber}</td>
			<td class="tdulleft">未巡检线段数量：</td>
			<td class="tdulright">${planStat.noPatrolSublineNumber}</td>
		</tr>
		<tr>
			<td class="tdulleft">未完成巡检线段数量：</td>
			<td class="tdulright">${planStat.noCompleteSublineNumber}</td>
			<td class="tdulleft">完成巡检线段数量：</td>
			<td class="tdulright">${planStat.completeSublineNumber}</td>
		</tr>
		<tr>
			<td class="tdulleft">计划巡检里程：</td>
			<td class="tdulright">${planStat.planPatrolMileage} 公里</td>
			<td class="tdulleft">实际巡检里程：</td>
			<td class="tdulright">${planStat.factPatrolMileage} 公里</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">计划盯防数：</td>
			<td class="tdulright">${planStat.planWatchNumber}</td>
			<td class="tdulleft">实际盯防数：</td>
			<td class="tdulright">${planStat.factWatchNumber}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">盯防完成率：</td>
			<td class="tdulright" colspan=3>${planStat.watchRatio} %</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">计划盯防区域数量：</td>
			<td class="tdulright">${planStat.planWatchAreaNumber}</td>
			<td class="tdulleft">未盯防区域数量：</td>
			<td class="tdulright">${planStat.noWatchAreaNumber}</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">未完成盯防区域数量：</td>
			<td class="tdulright">${planStat.noCompleteAreaNumber}</td>
			<td class="tdulleft">完成盯防区域数量：</td>
			<td class="tdulright">${planStat.completeAreaNumber}</td>
		</tr>
	</table>
</body>