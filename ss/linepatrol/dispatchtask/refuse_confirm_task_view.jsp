<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>sendtask</title>
	</head>
	<body>
		<!--�Ѿ���֤���ɵ���ϸ��Ϣ-->
		<br>
		<template:titile value="���񵥾�ǩȷ����ϸ��Ϣ" />
		<logic:notEmpty name="endorsebean">
			<table width="90%" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<tr class=trcolor>
					<td class="tdr" >
						��ǩȷ���ˣ�
					</td>
					<td class="tdl" width="35%">
						<bean:write name="endorsebean" property="confirmUserName" />
					</td>
					<td class="tdr">
						��ǩȷ�Ͻ����
					</td>
					<td class="tdl" width="35%">
						<c:if test="${endorsebean.confirmResult=='0'}">ͨ�����</c:if>
						<c:if test="${endorsebean.confirmResult=='1'}">δͨ�����</c:if>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
						��ǩȷ��ʱ�䣺
					</td>
					<td class="tdl" colspan="3">
						<logic:notEmpty name="endorsebean">
							<bean:write name="endorsebean" property="confirmTime" />
						</logic:notEmpty>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
						��ǩȷ�ϱ�ע��
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
				value="�ر�" />
		</p>
	</body>
</html>
