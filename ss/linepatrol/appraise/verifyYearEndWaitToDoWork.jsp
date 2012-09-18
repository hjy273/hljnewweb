<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>代维确认结果查询</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	</head>
	<script type="text/javascript">
		function view(id){
			window.location.href="${ctx}/appraiseYearEndAction.do?method=viewAppraiseMonth&flag=view&resultId="+id;
		}	
		function eidt(id) {
			window.location.href="${ctx}/appraiseYearEndAction.do?method=editAppraise&flag=edit&resultId="+id;
		}
		function send(id) {
			window.location.href="${ctx}/appraiseYearEndAction.do?method=sendAppraise&id="+id;
		}
		function verify(id) {
			window.location.href="${ctx}/appraiseYearEndAction.do?method=viewAppraiseMonth&flag=verify&resultId="+id;
		}
	</script>
	<body>
		<template:titile value="待办工作" />
			<display:table name="sessionScope.appraiseResults" id="appraiseResults" pagesize="18" export="false" defaultsort="1" defaultorder="descending" sort="list"	>
				<logic:notEmpty name="appraiseResults">
				<bean:define id="id" name="appraiseResults" property="id"></bean:define>
				<display:column media="html" title="代维公司" sortable="true" >
					 	<c:out value="${contractor[appraiseResults.contractorId]}"></c:out>
					 </display:column>
				<display:column property="contractNO" title="标底包"
					 maxLength="9" sortable="true" />
				<display:column property="result" sortable="true" title="考核结果"
					 maxLength="18" />
				<display:column media="html" sortable="true"
					title="考核年份" >
						<bean:write name="appraiseResults" property="appraiseTime" format="yyyy"/>
					</display:column>
				<display:column property="appraiser" title="考核人"
					 sortable="true" />
				<display:column media="html" title="确认结果" sortable="true" maxLength="36" style="width:15%;">
				<c:if test="${appraiseResults.confirmResult=='0'}">
					待下发
				</c:if>
				<c:if test="${appraiseResults.confirmResult=='1'}">
					待确认
				</c:if>
				<c:if test="${appraiseResults.confirmResult=='3'}">
					待修改（${appConfirmResultMap[appraiseResults.id].result }）
				</c:if>
			</display:column>
			<display:column media="html" title="操作">
				<c:if test="${appraiseResults.confirmResult=='0'}">
					<a href="javascript:send('${appraiseResults.id}');">下发</a>
					| <a href="javascript:view('${appraiseResults.id}');">查看</a>
				</c:if>
				<c:if test="${appraiseResults.confirmResult=='1'}">
					<a href="javascript:verify('${appraiseResults.id}');">确认</a>
				</c:if>
				<c:if test="${appraiseResults.confirmResult=='3'}">
					<a href="javascript:eidt('${appraiseResults.id}');">编辑</a>
				</c:if>
			</display:column>
				</logic:notEmpty>
			</display:table>
	</body>
</html>
