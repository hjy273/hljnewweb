<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
			 toEditPriceForm=function(idValue){
	            	window.location.href = "${ctx}/expensesPriceAction.do?method=editCableUnitPriceForm&tid="+idValue;
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

		<template:titile value="维护单价一览表" />
		<display:table name="sessionScope.list" id="currentRowObject" pagesize="15">
      		<display:column property="contractorname" title="代维单位" headerClass="subject"  sortable="true"/>	
      		<display:column property="expense_type" title="类型" headerClass="subject"  sortable="true"/>	
			<display:column property="explan" title="说明" headerClass="subject"  sortable="true"/>	
			<display:column property="cable_level" sortable="true" title="光缆长度" headerClass="subject" />
			<display:column property="unit_price" title="系数" headerClass="subject"  sortable="true"/>	
			 <display:column media="html" title="操作" >
			 <logic:equal value="1" property="deptype" name="LOGIN_USER">
				 <bean:define id="tid" name="currentRowObject" property="id"></bean:define>
				 <a href="javascript:toEditPriceForm('<%=tid%>')">修改</a>
			 </logic:equal>
            </display:column>
		</display:table>
	</body>
</html>
