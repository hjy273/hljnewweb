<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>sendtask</title>
	</head>
	<body>
		<!--已经签收的派单详细信息-->
		<br>
		<template:titile value="派单签收详细信息" />
		<logic:notEmpty name="endorsebean">
			<table width="90%" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<tr class=trcolor>
					<td class="tdr" width="20%" >
						签收人：
					</td>
					<td class="tdl" width="30%">
						<bean:write name="endorsebean" property="endorseusername" />
					</td>
					<td class="tdr" width="15%">
						签收结果：
					</td>
					<td class="tdl" width="35%">
						<c:if test="${endorsebean.result=='00'}">签收</c:if>
						<c:if test="${endorsebean.result=='01'}">拒签</c:if>
						<c:if test="${endorsebean.result=='02'}">转派</c:if>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
						签收时间：
					</td>
					<td class="tdl" colspan="3">
						<logic:notEmpty name="endorsebean">
							<bean:write name="endorsebean" property="time" />
						</logic:notEmpty>
					</td>
				</tr>
				<c:if test="${endorsebean.result=='02'}">
					<tr class=trcolor>
						<td class="tdr" >
							转派部门和转派人：
						</td>
						<td class="tdl" colspan="3">
							<logic:present name="endorsebean" property="acceptUserList">
								<logic:iterate id="oneAcceptUser" name="endorsebean"
									property="acceptUserList">
								部门：<bean:write name="oneAcceptUser" property="departname" />
								用户：<bean:write name="oneAcceptUser" property="username" />
									<br />
								</logic:iterate>
							</logic:present>
						</td>
					</tr>
				</c:if>
				<tr class=trcolor>
					<td class="tdr" >
						签收备注：
					</td>
					<td class="tdl" colspan="3">
						<div>
							<bean:write name="endorsebean" property="remark" />
						</div>
					</td>
				</tr>
				<c:if test="${endorsebean.result=='01'}">
					<logic:notEmpty name="refuseConfirmBean">
						<tr class=trcolor>
							<td class="tdr">
								拒签确认人：
							</td>
							<td class="tdl" width="35%">
								<bean:write name="refuseConfirmBean" property="confirmUserName" />
							</td>
							<td class="tdr">
								拒签确认结果：
							</td>
							<td class="tdl" width="35%">
								<c:if test="${refuseConfirmBean.confirmResult=='0'}">通过审核</c:if>
								<c:if test="${refuseConfirmBean.confirmResult=='1'}">未通过审核</c:if>
							</td>
						</tr>
						<tr class=trcolor>
							<td class="tdr">
								拒签确认时间：
							</td>
							<td class="tdl" colspan="3">
								<bean:write name="refuseConfirmBean" property="confirmTime" />
							</td>
						</tr>
						<tr class=trcolor>
							<td class="tdr">
								拒签确认备注：
							</td>
							<td class="tdl" colspan="3">
								<div>
									<bean:write name="refuseConfirmBean" property="confirmRemark" />
								</div>
							</td>
						</tr>
					</logic:notEmpty>
				</c:if>
			</table>
		</logic:notEmpty>
		<p align="center">
			<input type="button" onclick="javascript:closeWin();" class="button"
				value="关闭" />
		</p>
	</body>
</html>
