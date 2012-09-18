<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>sendtask</title>
	</head>
	<body>
		<!--已经验证的派单详细信息-->
		<br>
		<template:titile value="任务单拒签确认详细信息" />
		<logic:notEmpty name="endorsebean">
			<table width="90%" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<tr class=trcolor>
					<td class="tdr" >
						拒签确认人：
					</td>
					<td class="tdl" width="35%">
						<bean:write name="endorsebean" property="confirmUserName" />
					</td>
					<td class="tdr">
						拒签确认结果：
					</td>
					<td class="tdl" width="35%">
						<c:if test="${endorsebean.confirmResult=='0'}">通过审核</c:if>
						<c:if test="${endorsebean.confirmResult=='1'}">未通过审核</c:if>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
						拒签确认时间：
					</td>
					<td class="tdl" colspan="3">
						<logic:notEmpty name="endorsebean">
							<bean:write name="endorsebean" property="confirmTime" />
						</logic:notEmpty>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
						拒签确认备注：
					</td>
					<td class="tdl" colspan="3">
						<div>
							<bean:write name="endorsebean" property="confirmRemark" />
						</div>
					</td>
				</tr>
			</table>
		</logic:notEmpty>
		<p align="center">
			<input type="button" onclick="javascript:closeWin1();" class="button"
				value="关闭" />
		</p>
	</body>
</html>
