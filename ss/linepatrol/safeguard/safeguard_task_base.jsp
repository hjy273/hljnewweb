<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<html>
	<head>
		<title>保障任务派单基本数据</title>
	</head>
	<body>
		<table align="center" style="border-bottom:0px;"  cellpadding="1" cellspacing="0" class="tabout" width="90%">
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">任务名称：</td>
				<td class="tdulright" colspan="3">
					<c:out value="${safeguardTask.safeguardName}"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">保障时间：</td>
				<td class="tdulright" >
					<fmt:formatDate  value="${safeguardTask.startDate}" pattern="yyyy/MM/dd HH:mm:ss" var="formatStartDate"/>
					<fmt:formatDate  value="${safeguardTask.endDate}" pattern="yyyy/MM/dd HH:mm:ss" var="formatEndDate"/>
					<c:out value="${formatStartDate}"/> - <c:out value="${formatEndDate}"/>
				</td>
				<td class="tdulleft" style="width:20%;">方案提交时限：</td>
				<td class="tdulright" >
					<bean:write name="safeguardTask" property="deadline" format="yyyy/MM/dd HH:mm:ss"/>
				</td>
			</tr>
				
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">保障级别：</td>
				<td class="tdulright" style="width:30%;">
					<c:if test="${safeguardTask.level=='4'}">特级</c:if>
					<c:if test="${safeguardTask.level=='1'}">一级</c:if>
					<c:if test="${safeguardTask.level=='2'}">二级</c:if>
					<c:if test="${safeguardTask.level=='3'}">三级</c:if>
				</td>
				<td class="tdulleft" style="width:20%;">保障地点：</td>
				<td class="tdulright" style="width:30%;">
					<c:out value="${safeguardTask.region}"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">保障要求：</td>
				<td class="tdulright" colspan="3">
					<c:out value="${safeguardTask.requirement}"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">保障备注：</td>
				<td class="tdulright" colspan="3">
					<c:out value="${safeguardTask.remark }"/>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">任务附件：</td>
				<td class="tdulright" colspan="3">
					<apptag:upload cssClass="" entityId="${safeguardTask.id}" entityType="LP_SAFEGUARD_TASK" state="look"/>
				</td>
			</tr>
			<c:if test="${safeguardTask.cancelUserId!=null&&safeguardTask.cancelUserId!=''}">
				<tr class=trcolor>
					<td class="tdr">
						取消人：
					</td>
					<td class="tdl">
						${safeguardTask.cancelUserName}
					</td>
					<td class="tdr">
						取消时间：
					</td>
					<td class="tdl">
						${safeguardTask.cancelTimeDis}
					</td>
				</tr>
					
				<tr class=trcolor>
					<td class="tdr">
						取消原因：
					</td>
					<td class="tdl" colspan="3">
						${safeguardTask.cancelReason}
					</td>
				</tr>
			</c:if>
		</table>
	</body>
</html>
