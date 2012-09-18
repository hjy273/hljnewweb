<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
			 toEditForm=function(idValue){
	            	window.location.href = "${ctx}/budgeExpensesAction.do?method=editBudgetForm&id="+idValue;
			 }
		</script>
		<title></title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
		.subject{text-align:center;}
		</style>
	</head>
	<body>
		<br />

		<template:titile value="预付费用管理" />
		<display:table name="sessionScope.budgets" id="currentRowObject" pagesize="15">
      		<display:column property="year" title="年份" headerClass="subject"  sortable="true"/>	
			<display:column property="expense_type" title="费用类型" headerClass="subject"  sortable="true"/>	
			<display:column property="contractorname" sortable="true" title="代维单位" headerClass="subject" />
			<display:column property="budget_money" title="预付费用" headerClass="subject"  sortable="true"/>	
			<display:column property="pay_money" title="已付费用" headerClass="subject"  sortable="true"/>	
			 <display:column media="html" title="操作" >
			 <logic:equal value="1" property="deptype" name="LOGIN_USER">
				 <bean:define id="tid" name="currentRowObject" property="id"></bean:define>
				 <a href="javascript:toEditForm('<%=tid%>')">修改</a>
			 </logic:equal>
            </display:column>
		</display:table>
	</body>
</html>
