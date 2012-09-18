<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linepatrol.trouble.services.TroubleConstant" %>
<html>
	<head>
		<title>���ӹ��³���ȡ��</title>
			<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
			<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
			<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
			<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
	</head>

	<body>
		<template:titile value="�޸�ȡ��ϵ��" />
		<html:form action="/expensesFactorAction.do?method=editFactor" 
			styleId="editGradeFactor">
			<input name="id" id="id" type="hidden" value="${expenseFactorBean.id}"/>
			<table width="80%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			     <tr class=trcolor >
				  	<td class="tdulleft">�������ͣ�</td>
				  	<td class="tdulright" >
				  	<input type="radio" name="expenseType" value="0" checked="checked"/>����
				  		<!--<c:if test="${expenseFactorBean.expenseType==0}">
					  		<input type="radio" name="expenseType" value="0" checked="checked"/>����
					  		<input type="radio" name="expenseType" value="1"/>�ܵ�
				  		</c:if>
				  		<c:if test="${expenseFactorBean.expenseType==1}">
					  		<input type="radio" name="expenseType" value="0" />����
					  		<input type="radio" name="expenseType" value="1" checked="checked"/>�ܵ�
				  		</c:if>-->
				  	</td>
				  </tr>
				  <tr class=trwhite >
				  	<td class="tdulleft">��ά:</td>
				  	<td class="tdulright" >
				  		<html:select property="contractorid" name="expenseFactorBean" style="width:155px" styleClass="required">
					  		<c:forEach items="${constrators}" var="con">
					  			<html:option value="${con.contractorID}">${con.contractorName}</html:option>
					  		</c:forEach>
				  		</html:select>
				  	</td>
				  </tr>
				   <tr class=trcolor >
				  	<td class="tdulleft">����������</td>
				  	<td class="tdulright" >
				  	 <html:text property="explain" styleClass="required" style="width:195px"></html:text>
				  	</td>
				  </tr>
				  <tr class=trwhite >
				  	<td class="tdulleft">���ȼ���(����)��</td>
				  	<td class="tdulright" >
					  ����<html:text property="grade1" styleClass="required validate-integer" style="width:80px"></html:text>&nbsp;&nbsp;
				  	С�ڵ���<html:text property="grade2" styleClass="required validate-integer" style="width:80px"></html:text>
				  	&nbsp;<font color="red">(���������Ϊ0,��ʾ���޴�)</font>
				  	</td>
				  </tr>
				  
				  <tr class=trcolor >
				  	<td class="tdulleft">�ּ�ȡ��ϵ����</td>
				  	<td class="tdulright" >
				 	 	<html:text property="factor" styleClass="required validate-number"></html:text>
				  	</td>
				  </tr>
			</table>
			<div align="center" style="height:80px">
		       <html:submit property="action" styleClass="button">�ύ</html:submit> 
			   <html:reset property="action" styleClass="button" onclick="javascript:history.back();">����</html:reset>
			</div>
		</html:form>
		<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}
			var valid = new Validation('editGradeFactor', {immediate : true, onFormValidate : formCallback});
	   </script>
	</body>
	<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery("#editGradeFactor").bind("submit", function() {
			processBar(editGradeFactor);
		});
	})
</script>
</html>
