<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>sendtask</title>
	</head>
	<body>
		<!--�Ѿ���֤���ɵ���ϸ��Ϣ-->
		<br>
		<template:titile value="���񵥷��������ϸ��Ϣ" />
		<logic:notEmpty name="checkbean">
			<table width="90%" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<tr class=trcolor>
					<td class="tdr" >
						����ˣ�
					</td>
					<td class="tdl" width="30%">
						<bean:write name="checkbean" property="validateusername" />
					</td>
					<td class="tdr">
						��˽����
					</td>
					<td class="tdl" width="35%">
						<c:if test="${checkbean.validateresult=='0'}">ͨ�����</c:if>
						<c:if test="${checkbean.validateresult=='1'}">δͨ�����</c:if>
						<c:if test="${checkbean.validateresult=='2'}">ת��</c:if>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
						���ʱ�䣺
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
							ת���ź�ת���ˣ�
						</td>
						<td class="tdl" colspan="3">
							<logic:present name="checkbean" property="approverUserList">
								<logic:iterate id="oneApprover" name="checkbean"
									property="approverUserList">
								���ţ�<bean:write name="oneApprover" property="departname" />
								�û���<bean:write name="oneApprover" property="username" />
									<br />
								</logic:iterate>
							</logic:present>
						</td>
					</tr>
				</c:if>
				<tr class=trcolor>
					<td class="tdr" >
						��˱�ע��
					</td>
					<td class="tdl" colspan="3">
						<div>
							<bean:write name="checkbean" property="validateremark" />
						</div>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
						��˸�����
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
				value="�ر�" />
		</p>
	</body>
</html>
