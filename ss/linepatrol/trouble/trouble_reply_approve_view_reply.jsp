<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>���Ϸ������鿴</title>
		   
	</head>

	<body>
		<br>
		<template:titile value="���Ϸ�������Ϣ" />
					 <table width="50%"  align="center">
					 	<tr>
					 		<td>�����˽�ɫ��</td>
					 		<td>
					 			<c:if test="${reply.confirmResult=='1'}">
						    		����
						    	</c:if>
						    	<c:if test="${reply.confirmResult=='0'}">
						    		Э��
						    	</c:if>
					 		</td>
					 		<td>�����ˣ�</td>
					 		<td><c:out value="${replyman}"></c:out></td>
					 	</tr>
					 	<tr>
					 		<td>����ʱ�䣺</td>
					 		<td colspan="3">
					 			<bean:write name="reply" property="replySubmitTime" format="yyyy/MM/dd HH:mm:ss"/>
					 		</td>
					 	</tr>
					 	<tr>
					 		<td>��ע��</td>
					 		<td colspan="3">
					 			<c:out value="${reply.replyRemark}"></c:out>
					 		</td>
					 	</tr>
					 	<tr>
					 		<td colspan="4" align="center">
					 			<input type="button" value="����"  onclick="javascript:history.back();"/>
					 		</td>
					 	</tr>
			       </table>
	</body>
</html>
