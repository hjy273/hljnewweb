<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>�鿴�ʾ���ϸ</title>
		<script type="text/javascript">
		</script>
	</head>
	<body>
		<template:titile value="�鿴�ʾ���ϸ"/>
		<table align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">�ʾ����ƣ�</td>
				<td class="tdulright"><bean:write name="bean" property="name"/></td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">�ʾ����ͣ�</td>
				<td class="tdulright"><bean:write name="bean" property="type"/></td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">�����</td>
				<td class="tdulright">
					<table width="100%">
						<tr>
							<td width="20%">ָ�����</td>
							<td width="20%">ָ�����</td>
							<td width="60%">ָ��ϸ��</td>
						</tr>
						<c:forEach items="${list}" var="itemBean">
							<tr>
								<td width="20%"><bean:write name="itemBean" property="qclass"/></td>
								<td width="20%"><bean:write name="itemBean" property="sort"/></td>
								<td width="60%"><bean:write name="itemBean" property="item"/></td>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">��ά��˾��</td>
				<td class="tdulright">${conName }</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">��������</td>
				<td class="tdulright">${compNames }</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">�ʾ�˵����</td>
				<td class="tdulright"><bean:write name="bean" property="remark"/></td>
			</tr>
		</table>
		<div align="center" style="height:40px">
			<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">����</html:button>
		</div>
	</body>
</html>
