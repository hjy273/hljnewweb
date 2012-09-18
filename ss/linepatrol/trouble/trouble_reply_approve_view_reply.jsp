<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>故障反馈单查看</title>
		   
	</head>

	<body>
		<br>
		<template:titile value="故障反馈单信息" />
					 <table width="50%"  align="center">
					 	<tr>
					 		<td>处理人角色：</td>
					 		<td>
					 			<c:if test="${reply.confirmResult=='1'}">
						    		主办
						    	</c:if>
						    	<c:if test="${reply.confirmResult=='0'}">
						    		协办
						    	</c:if>
					 		</td>
					 		<td>反馈人：</td>
					 		<td><c:out value="${replyman}"></c:out></td>
					 	</tr>
					 	<tr>
					 		<td>反馈时间：</td>
					 		<td colspan="3">
					 			<bean:write name="reply" property="replySubmitTime" format="yyyy/MM/dd HH:mm:ss"/>
					 		</td>
					 	</tr>
					 	<tr>
					 		<td>备注：</td>
					 		<td colspan="3">
					 			<c:out value="${reply.replyRemark}"></c:out>
					 		</td>
					 	</tr>
					 	<tr>
					 		<td colspan="4" align="center">
					 			<input type="button" value="返回"  onclick="javascript:history.back();"/>
					 		</td>
					 	</tr>
			       </table>
	</body>
</html>
