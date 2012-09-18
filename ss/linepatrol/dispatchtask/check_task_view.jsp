<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>sendtask</title>
	</head>
	<body>
		<!--已经验证的派单详细信息-->
		<br>
		<template:titile value="任务单反馈审核详细信息" />
		<logic:notEmpty name="checkbean">
			<table width="90%" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<tr class=trcolor>
					<td class="tdr" >
						审核人：
					</td>
					<td class="tdl" width="30%">
						<bean:write name="checkbean" property="validateusername" />
					</td>
					<td class="tdr">
						审核结果：
					</td>
					<td class="tdl" width="35%">
						<c:if test="${checkbean.validateresult=='0'}">通过审核</c:if>
						<c:if test="${checkbean.validateresult=='1'}">未通过审核</c:if>
						<c:if test="${checkbean.validateresult=='2'}">转审</c:if>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
						审核时间：
					</td>
					<td class="tdl" colspan="3">
						<logic:notEmpty name="checkbean">
							<bean:write name="checkbean" property="validatetime" />
						</logic:notEmpty>
					</td>
				</tr>
				<c:if test="${checkbean.validateresult=='2'}">
					<tr class=trcolor>
						<td class="tdr" >
							转审部门和转审人：
						</td>
						<td class="tdl" colspan="3">
							<logic:present name="checkbean" property="approverUserList">
								<logic:iterate id="oneApprover" name="checkbean"
									property="approverUserList">
								部门：<bean:write name="oneApprover" property="departname" />
								用户：<bean:write name="oneApprover" property="username" />
									<br />
								</logic:iterate>
							</logic:present>
						</td>
					</tr>
				</c:if>
				<tr class=trcolor>
					<td class="tdr" >
						审核备注：
					</td>
					<td class="tdl" colspan="3">
						<div>
							<bean:write name="checkbean" property="validateremark" />
						</div>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
						审核附件：
					</td>
					<td class="tdl" colspan="3">
						<apptag:upload state="look" entityId="${checkbean.id}"
							entityType="LP_VALIDATEREPLY"></apptag:upload>
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
