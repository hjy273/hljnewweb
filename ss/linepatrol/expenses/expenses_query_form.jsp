<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>费用核实</title>
		    <script type="text/javascript" src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
	<link type="text/css" rel="stylesheet"
		href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
		
		<script type="text/javascript">
				function checkAddInfo() {
					var beginMonth = document.getElementById("beginMonth").value;
					var endMonth = document.getElementById("endMonth").value;
					if(beginMonth=="" || endMonth==""){
						alert("月份不能为空!");
						return false;
					}
					var beginYear=beginMonth.substring(0,beginMonth.indexOf("/"));
					var endYear=endMonth.substring(0,endMonth.indexOf("/"));
					if(beginYear!=endYear){
						alert("费用确认不能跨年！");
						return false;
					}
					return true;
				}
	  </script>
	</head>
	<body>
		<br>
		<template:titile value="费用确认" />
		<html:form action="/expensesAction.do?method=getSettlementEexpenses"
			styleId="statExpense" onsubmit="return checkAddInfo()">
			<table width="50%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			     <tr class=trcolor >
				  	<td class="tdulleft">代维:</td>
				  	<td class="tdulright" >
				  		<select name="contractorid" style="width:155px" styleClass="required">
					  		<c:forEach items="${constrators}" var="con">
					  			<option value="${con.contractorID}">${con.contractorName}</option>
					  		</c:forEach>
				  		</select>
				  	</td>
				  </tr>
			      <tr class=trcolor >
				  	<td class="tdulleft">费用类型：</td>
				  	<td class="tdulright" >
				  		<input type="radio" name="expenseType" value="0" checked="checked"/>光缆
				  		<input type="radio" name="expenseType" value="1"/>管道
				  	</td>
				  </tr>
				  <tr class=trcolor>
				    <td class="tdulleft" width="20%">月份：</td>
				    <td class="tdulright"><input name="beginMonth" id="beginMonth" class="Wdate" style="width:150px"
					onfocus="WdatePicker({dateFmt:'yyyy/MM',maxDate: '%y-#{%M-1}'})" readonly/>
					至
					<input name="endMonth" id="endMonth" class="Wdate" style="width:150px"
					onfocus="WdatePicker({dateFmt:'yyyy/MM',minDate:'#F{$dp.$D(\'beginMonth\')}',maxDate: '%y-#{%M-1}'})" readonly/>
					</td>
				  </tr>
			     <tr class=trwhite>
					<td colspan="6" style="text-align:center;">
						<input name="action" class="button" value="查询"	 type="submit" />
					</td>
				 </tr>
			</table>
		</html:form>
	</body>
</html>
