<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>
</head>
<body onload="changeStyle()"> 
	<template:titile value="总体信息" />
	<table  width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
		<tr class=trcolor>
			<td class="tdulleft">计划名称：</td>
			<td class="tdulright">${planStat.specPlanName}</td>
			<td class="tdulleft">计划类型：</td>
			<td class="tdulright">
				<c:if test="${planStat.specPlanType eq '001'}">
					盯防计划
				</c:if>
				<c:if test="${planStat.specPlanType eq '002'}">
					保障计划
				</c:if>
			</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">计划开始时间：</td>
			<td class="tdulright"><bean:write name="plan" property="startDate" format="yyyy-MM-dd HH:mm:ss"/></td>
			<td class="tdulleft">计划结束时间：</td>
			<td class="tdulright"><bean:write name="plan" property="endDate" format="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">巡检组：</td>
			<td class="tdulright">
				<apptag:assorciateAttr table="patrolmaninfo" label="patrolname" key="patrolid" keyValue="${plan.patrolGroupId}" />
			</td>
			<td class="tdulleft">计划状态：</td>
			<td class="tdulright">
				<c:choose>
					<c:when test="${planStat.patrolStatState eq '0'}">
						计划执行中
					</c:when>
					<c:otherwise>
						计划执行完毕
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="tdulleft">计划巡检点数：</td>
			<td class="tdulright">${planStat.planPointNumber}</td>
			<td class="tdulleft">计划巡检次数：</td>
			<td class="tdulright">${planStat.planPointTimes}</td>
		</tr>
		<tr>
			<td class="tdulleft">实际巡检点数：</td>
			<td class="tdulright">${planStat.factPointNumber}</td>
			<td class="tdulleft">实际巡检次数：</td>
			<td class="tdulright">${planStat.factPointTimes}</td>
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