<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<title>考核结果确认</title>
	</head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<script type="text/javascript">
		function verify(id) {
		window.location.href="${ctx}/appraiseSpecialAction.do?method=viewAppraiseMonth&flag=verify&resultId="+id;
	}
	</script>
	<body>
		<template:titile value="考核结果确认" />
			<display:table name="sessionScope.appraiseResults" id="appraiseResults" pagesize="18" export="false" defaultsort="1" defaultorder="descending" sort="list"	>
				<logic:notEmpty name="appraiseResults">
				<display:column media="html" title="代维公司" sortable="true" >
					 	<c:out value="${contractor[appraiseResults.contractorId]}"></c:out>
					 </display:column>
				<display:column property="startDate" sortable="true" format="{0,date,yyyy-MM-dd}"
					title="考核开始时间" />
				<display:column property="endDate" sortable="true" format="{0,date,yyyy-MM-dd}"
					title="考核结束时间" />
				<display:column property="result" sortable="true" title="考核结果"
					 maxLength="18" />
				<display:column property="appraiser" title="考核人"
					 sortable="true" />
				<display:column media="html" title="操作">
					<a href="javascript:verify('${appraiseResults.id}');">确认</a>
				</display:column>
				</logic:notEmpty>
			</display:table>
			<table border="0" cellpadding="0" cellspacing="0" width="100%"
				style="border: 0px">
				<tr>
					<td style="text-align: center;">
						<input name="action" class="button" value="返回" onclick="history.back();"
							type="button" />
					</td>
				</tr>
			</table>
	</body>
</html>
