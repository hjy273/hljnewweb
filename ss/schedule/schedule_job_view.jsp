<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>sendtask</title>
		<script type="text/javascript">
		goBack=function(){
			var url="${sessionScope.S_BACK_URL}";
			location=url;
		}
		</script>
	</head>
	<body>
		<!--已经验证的派单详细信息-->
		<br>
		<template:titile value="定时任务详细信息" />
		<logic:notEmpty name="job_detail">
			<table width="90%" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<tr class=trcolor>
					<td class="tdr" >
						定时任务名称：
					</td>
					<td class="tdl" colspan="3">
						<bean:write name="job_detail" property="sendObjectName" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						发送短信号码：
					</td>
					<td class="tdl" colspan="3">
						<bean:write name="job_detail" property="simId" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
						发送短信内容：
					</td>
					<td class="tdl" colspan="3">
						<bean:write name="job_detail" property="sendContent" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
						指定发送时间：
					</td>
					<td class="tdl" colspan="3">
						<logic:notEmpty name="job_detail" property="firstSendTime">
						开始时间：
						<bean:write name="job_detail" property="firstSendTime" />
						</logic:notEmpty>
						<logic:notEmpty name="job_detail" property="lastSendTime">
						结束时间：
						<bean:write name="job_detail" property="lastSendTime" />
						</logic:notEmpty>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
						发送类型：
					</td>
					<td class="tdl">
						<c:if test="${job_detail.sendType=='-1'}">定时单次发送</c:if>
						<c:if test="${job_detail.sendType=='1'}">定时重复发送</c:if>
						<c:if test="${job_detail.sendType=='2'}">固定周期发送</c:if>
					</td>
					<td class="tdr" >
						发送间隔：
					</td>
					<td class="tdl">
						<c:if test="${job_detail.sendType=='1'}">
							<bean:write name="job_detail" property="sendTimeSpace" />
							<c:if test="${job_detail.sendTimeType=='0'}">秒</c:if>
							<c:if test="${job_detail.sendTimeType=='1'}">分</c:if>
							<c:if test="${job_detail.sendTimeType=='2'}">小时</c:if>
							<c:if test="${job_detail.sendTimeType=='3'}">天</c:if>
							<c:if test="${job_detail.sendTimeType=='4'}">周</c:if>
						</c:if>
						<c:if test="${job_detail.sendType=='2'}">
							<bean:write name="job_detail" property="sendCycleRule" />
						</c:if>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
						上次发送时间：
					</td>
					<td class="tdl">
						<bean:write name="job_detail" property="prevSentTime" />
					</td>
					<td class="tdr" >
						下次发送时间：
					</td>
					<td class="tdl">
						<bean:write name="job_detail" property="nextSentTime" />
					</td>
				</tr>
			</table>
		</logic:notEmpty>
		<p align="center">
			<input type="button" onclick="goBack();" class="button"
				value="返回" />
		</p>
	</body>
</html>
