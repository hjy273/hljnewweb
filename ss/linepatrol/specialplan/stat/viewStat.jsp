<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>
</head>
<body onload="changeStyle()"> 
	<template:titile value="������Ϣ" />
	<table  width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
		<tr class=trcolor>
			<td class="tdulleft">�ƻ����ƣ�</td>
			<td class="tdulright">${planStat.specPlanName}</td>
			<td class="tdulleft">�ƻ����ͣ�</td>
			<td class="tdulright">
				<c:if test="${planStat.specPlanType eq '001'}">
					�����ƻ�
				</c:if>
				<c:if test="${planStat.specPlanType eq '002'}">
					���ϼƻ�
				</c:if>
			</td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">�ƻ���ʼʱ�䣺</td>
			<td class="tdulright"><bean:write name="plan" property="startDate" format="yyyy-MM-dd HH:mm:ss"/></td>
			<td class="tdulleft">�ƻ�����ʱ�䣺</td>
			<td class="tdulright"><bean:write name="plan" property="endDate" format="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr class=trcolor>
			<td class="tdulleft">Ѳ���飺</td>
			<td class="tdulright">
				<apptag:assorciateAttr table="patrolmaninfo" label="patrolname" key="patrolid" keyValue="${plan.patrolGroupId}" />
			</td>
			<td class="tdulleft">�ƻ�״̬��</td>
			<td class="tdulright">
				<c:choose>
					<c:when test="${planStat.patrolStatState eq '0'}">
						�ƻ�ִ����
					</c:when>
					<c:otherwise>
						�ƻ�ִ�����
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="tdulleft">�ƻ�Ѳ�������</td>
			<td class="tdulright">${planStat.planPointNumber}</td>
			<td class="tdulleft">�ƻ�Ѳ�������</td>
			<td class="tdulright">${planStat.planPointTimes}</td>
		</tr>
		<tr>
			<td class="tdulleft">ʵ��Ѳ�������</td>
			<td class="tdulright">${planStat.factPointNumber}</td>
			<td class="tdulleft">ʵ��Ѳ�������</td>
			<td class="tdulright">${planStat.factPointTimes}</td>
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