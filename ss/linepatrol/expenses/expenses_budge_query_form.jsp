<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>���ú�ʵ</title>
		    <script type="text/javascript" src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
	<link type="text/css" rel="stylesheet"
		href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
	</head>
	<body>
		<br>
		<template:titile value="Ԥ����ò�ѯ" />
		<html:form action="/expensesAction.do?method=getEexpenses"
			styleId="statExpense" onsubmit="return checkAddInfo()">
			<table width="50%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			     <tr class=trcolor >
				  	<td class="tdulleft">��ά:</td>
				  	<td class="tdulright" >
				  		<select property="contractorid" style="width:155px" styleClass="required">
				  			<option value="">����</option>
					  		<c:forEach items="${constrators}" var="con">
					  			<option value="${con.contractorID}">${con.contractorName}</option>
					  		</c:forEach>
				  		</select>
				  	</td>
				  </tr>
				  <tr class=trcolor>
				    <td class="tdulleft" width="20%">�����·ݣ�</td>
				    <td class="tdulright"><input name="beginMonth" id="beginMonth" class="Wdate" style="width:150px"
					onfocus="WdatePicker({dateFmt:'yyyy/MM',maxDate: '%y-#{%M-1}'})" readonly/>
					��
					<input name="endMonth" id="endMonth" class="Wdate" style="width:150px"
					onfocus="WdatePicker({dateFmt:'yyyy/MM',maxDate:'#F{$dp.$D(\'beginMonth\')}'})" readonly/>
					</td>
				  </tr>
			     <tr class=trwhite>
					<td colspan="6" style="text-align:center;">
						<input name="action" class="button" value="��ѯ"	 type="submit" />
					</td>
				 </tr>
			</table>
		</html:form>
	</body>
</html>
