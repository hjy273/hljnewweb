<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>增加光缆长度取费</title>
			<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
			<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
			<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
			<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
	</head>

	<body>
		<template:titile value="增加取费系数" />
		<html:form action="/expensesFactorAction.do?method=saveFactor"
			styleId="cableGradeFactor">
			<table width="80%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			     <tr class=trcolor >
				  	<td class="tdulleft">费用类型：</td>
				  	<td class="tdulright" >
				  		<input type="radio" name="expenseType" value="0" checked="checked"/>光缆
				  	<!-- 	<input type="radio" name="expenseType" value="1"/>管道 -->
				  	</td>
				  </tr>
				  <tr class=trwhite >
				  	<td class="tdulleft">代维:</td>
				  	<td class="tdulright" >
				  		<html:select property="contractorid" style="width:155px" styleClass="required">
					  		<c:forEach items="${constrators}" var="con">
					  			<html:option value="${con.contractorID}">${con.contractorName}</html:option>
					  		</c:forEach>
				  		</html:select>
				  	</td>
				  </tr>
				   <tr class=trcolor >
				  	<td class="tdulleft">级别描述：</td>
				  	<td class="tdulright" >
				  	 <html:text property="explain" styleClass="required" style="width:195px"></html:text>
				  	</td>
				  </tr>
				  <tr class=trwhite >
				  	<td class="tdulleft">长度级别(公里)：</td>
				  	<td class="tdulright" >
					  	大于<html:text property="grade1" styleClass="required validate-integer" style="width:80px"></html:text>&nbsp;&nbsp; 
					  	小于等于<html:text property="grade2" styleClass="required validate-integer" style="width:80px"></html:text>
					  	&nbsp;<font color="red">(后面输入框为0,表示无限大)</font>
				  	</td>
				  </tr>
				  
				  <tr class=trcolor >
				  	<td class="tdulleft">分级取费系数：</td>
				  	<td class="tdulright" >
				 	 	<html:text property="factor" styleClass="required validate-number"></html:text>
				  	</td>
				  </tr>
			</table>
			<div align="center" style="height:80px">
		       <html:submit property="action" styleClass="button">提交</html:submit> 
			   <html:reset property="action" styleClass="button">重置</html:reset>
			</div>
		</html:form>
		<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}
			var valid = new Validation('cableGradeFactor', {immediate : true, onFormValidate : formCallback});
	   </script>
	</body>
	<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery("#cableGradeFactor").bind("submit", function() {
			processBar(cableGradeFactor);
		});
	})
</script>
</html>
