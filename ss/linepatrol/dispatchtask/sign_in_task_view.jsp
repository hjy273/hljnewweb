<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>sendtask</title>
	</head>
	<body>
		<!--�Ѿ�ǩ�յ��ɵ���ϸ��Ϣ-->
		<br>
		<template:titile value="�ɵ�ǩ����ϸ��Ϣ" />
		<logic:notEmpty name="endorsebean">
			<table width="90%" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<tr class=trcolor>
					<td class="tdr" width="20%" >
						ǩ���ˣ�
					</td>
					<td class="tdl" width="30%">
						<bean:write name="endorsebean" property="endorseusername" />
					</td>
					<td class="tdr" width="15%">
						ǩ�ս����
					</td>
					<td class="tdl" width="35%">
						<c:if test="${endorsebean.result=='00'}">ǩ��</c:if>
						<c:if test="${endorsebean.result=='01'}">��ǩ</c:if>
						<c:if test="${endorsebean.result=='02'}">ת��</c:if>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
						ǩ��ʱ�䣺
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
							ת�ɲ��ź�ת���ˣ�
						</td>
						<td class="tdl" colspan="3">
							<logic:present name="endorsebean" property="acceptUserList">
								<logic:iterate id="oneAcceptUser" name="endorsebean"
									property="acceptUserList">
								���ţ�<bean:write name="oneAcceptUser" property="departname" />
								�û���<bean:write name="oneAcceptUser" property="username" />
									<br />
								</logic:iterate>
							</logic:present>
						</td>
					</tr>
				</c:if>
				<tr class=trcolor>
					<td class="tdr" >
						ǩ�ձ�ע��
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
								��ǩȷ���ˣ�
							</td>
							<td class="tdl" width="35%">
								<bean:write name="refuseConfirmBean" property="confirmUserName" />
							</td>
							<td class="tdr">
								��ǩȷ�Ͻ����
							</td>
							<td class="tdl" width="35%">
								<c:if test="${refuseConfirmBean.confirmResult=='0'}">ͨ�����</c:if>
								<c:if test="${refuseConfirmBean.confirmResult=='1'}">δͨ�����</c:if>
							</td>
						</tr>
						<tr class=trcolor>
							<td class="tdr">
								��ǩȷ��ʱ�䣺
							</td>
							<td class="tdl" colspan="3">
								<bean:write name="refuseConfirmBean" property="confirmTime" />
							</td>
						</tr>
						<tr class=trcolor>
							<td class="tdr">
								��ǩȷ�ϱ�ע��
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
				value="�ر�" />
		</p>
	</body>
</html>
