<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>查看问卷详细</title>
		<script type="text/javascript">
		</script>
	</head>
	<body>
		<template:titile value="查看问卷详细"/>
		<table align="center" cellpadding="1" cellspacing="0" class="tabout" width="90%">
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">问卷名称：</td>
				<td class="tdulright"><bean:write name="bean" property="name"/></td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">问卷类型：</td>
				<td class="tdulright"><bean:write name="bean" property="type"/></td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">参评项：</td>
				<td class="tdulright">
					<table width="100%">
						<tr>
							<td width="20%">指标类别</td>
							<td width="20%">指标分类</td>
							<td width="60%">指标细项</td>
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
				<td class="tdulleft" style="width:20%;">代维公司：</td>
				<td class="tdulright">${conName }</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">参评对象：</td>
				<td class="tdulright">${compNames }</td>
			</tr>
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">问卷说明：</td>
				<td class="tdulright"><bean:write name="bean" property="remark"/></td>
			</tr>
		</table>
		<div align="center" style="height:40px">
			<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">返回</html:button>
		</div>
	</body>
</html>
