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

		<template:titile value="Ԥ�����ù���" />
		<display:table name="sessionScope.budgets" id="currentRowObject" pagesize="15">
      		<display:column property="year" title="���" headerClass="subject"  sortable="true"/>	
			<display:column property="expense_type" title="��������" headerClass="subject"  sortable="true"/>	
			<display:column property="contractorname" sortable="true" title="��ά��λ" headerClass="subject" />
			<display:column property="budget_money" title="Ԥ������" headerClass="subject"  sortable="true"/>	
			<display:column property="pay_money" title="�Ѹ�����" headerClass="subject"  sortable="true"/>	
			 <display:column media="html" title="����" >
			 <logic:equal value="1" property="deptype" name="LOGIN_USER">
				 <bean:define id="tid" name="currentRowObject" property="id"></bean:define>
				 <a href="javascript:toEditForm('<%=tid%>')">�޸�</a>
			 </logic:equal>
            </display:column>
		</display:table>
	</body>
</html>
