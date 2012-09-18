<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>填写联机巡检表</title>
		<style type="text/css">
		.thlist_spec {
			background-color: #FFFFFF;
			text-align: center;
			vertical-align: center;
			border: #9A9A9A solid;
			border-width: 0 0 0 0;
			border-right: #CBEDFE solid 1px;
			border-bottom: #CBEDFE solid 1px;
		}
		</style>
		<script type="text/javascript" src="${ctx }/wplanpatrol/js/table_operate.js"></script>
		<script type="text/javascript">
		function check(){
			return true;
		}
		</script>
	</head>
	<body>
		<c:set var="resourceTypeMap" value="${resource_type_map }"></c:set>
		<c:set var="patrolTableMap" value="${patrol_table_item_map }"></c:set>
		<c:set var="patrolProjectMap"
			value="${patrolTableMap['project_map'] }"></c:set>
		<c:set var="patrolItemMap" value="${patrolTableMap['item_map'] }"></c:set>
		<template:titile
			value="${patrolTableMap['region_name'] }${patrolTableMap['resource_name'] }${RESOURCE_TYPE }巡检表" />
		<s:form action="planPatrolResultAction_save" name="form" method="post"
			onsubmit="return check()">
			<input id="planPatrolResult.executeResultId"
				name="planPatrolResult.executeResultId"
				value="${patrolTableMap['patrol_result'].id }" type="hidden" />
			<input id="planPatrolTestData.executeResultId"
				name="planPatrolTestData.executeResultId"
				value="${patrolTableMap['patrol_result'].id }" type="hidden" />
			<input id="businessType" name="businessType" value="${businessType }"
				type="hidden" />
			<table width="950" border="0" align="center" cellpadding="3"
				cellspacing="0">
				<tr class="trwhite">
					<td style="width: 100%; text-align: center;" colspan="2">
						<b>站点名称：</b> ${patrolTableMap['resource_name'] }&nbsp;
						<b>巡检人员：</b> ${patrolTableMap['patrol_person_name'] }&nbsp;
						<b>电话：</b> ${patrolTableMap['patrol_person_tel'] }&nbsp;
						<b>代维单位：</b> ${patrolTableMap['contractor_name'] }&nbsp;
						<b>巡检时间：</b>
						<fmt:formatDate var="arriveTimeDis"
							value="${patrolTableMap['patrol_result'].arriveTime }"
							pattern="yyyy年MM月dd日HH时mm分" />
						${arriveTimeDis }
					</td>
				</tr>
			</table>
			<table border="0" cellpadding="0" cellspacing="0" width="100%"
				class="tabout">
				<tr>
					<th class="thlist"
						style="width: 20%; padding: 5px; text-align: left;">
						巡检内容
					</th>
					<th class="thlist" style="width: 40%; padding: 5px;">
						巡检结果
					</th>
					<th class="thlist" style="width: 40%; padding: 5px;">
						异常描述
					</th>
				</tr>
				<c:forEach var="itemsMap" items="${patrolItemMap }">
					<c:set var="projectId" value="${itemsMap.key }"></c:set>
					<tr>
						<th class="thlist_spec"
							style="width: 20%; padding: 5px; text-align: left;">
							${patrolProjectMap[projectId] }
						</th>
						<th class="thlist_spec" style="width: 40%; padding: 5px;">
						</th>
						<th class="thlist_spec" style="width: 40%; padding: 5px;">
						</th>
					</tr>
					<c:forEach var="itemMap" items="${itemsMap.value }">
						<tr>
							${itemMap.value.formInputElement }
						</tr>
					</c:forEach>
				</c:forEach>
			</table>
			<table width="100%" style="border: 0px">
				<tr class="trwhite">
					<td colspan="4" style="text-align: center; border: 0px">
						<html:submit styleClass="button">提交</html:submit>
					</td>
				</tr>
			</table>
		</s:form>
	</body>
</html>


