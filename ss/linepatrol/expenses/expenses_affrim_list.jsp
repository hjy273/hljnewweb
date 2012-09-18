<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
			 toViewForm=function(idValue){
	            	window.location.href = "${ctx}/expensesAction.do?method=viewAffrimEexpense&id="+idValue;
			 }
			
			function  goBack(){
				var url="${ctx}/expensesAction.do?method=addCableTypePrice";
				self.location.replace(url);
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

		<template:titile value="已确认费用一览表" />
		<display:table name="sessionScope.affrims" id="currentRowObject" pagesize="15">
      		<display:column property="contractorname" title="代维单位" headerClass="subject"  sortable="true"/>	
			<display:column property="expense_type" title="费用类型" headerClass="subject"  sortable="true"/>	
			<display:column property="start_month" sortable="true" title="付费开始月份" headerClass="subject" />
			<display:column property="end_month" title="付费结束月份" headerClass="subject"  sortable="true"/>	
			 <display:column media="html" title="操作" >
				 <bean:define id="tid" name="currentRowObject" property="id"></bean:define>
				 <a href="javascript:toViewForm('<%=tid%>')">查看</a>
            </display:column>
		</display:table>
	</body>
</html>
