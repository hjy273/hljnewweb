<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>增加预算</title>
			<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
			<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
			<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
			<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
			
			<script type="text/javascript">
			function checkAddInfo() {
				var month = document.getElementById("year").value;
				if(month==""){
					alert("预算年份不能为空!");
					return false;
				}
				processBar(cableGradeFactor);
				return true;
			}
			</script>
			
	</head>

	<body>
		<template:titile value="增加预算" />
		<html:form action="/budgeExpensesAction.do?method=addBudget"
			styleId="cableGradeFactor" onsubmit="return checkAddInfo();">
			<table width="80%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			     <tr class=trwhite>
				    <td class="tdulleft" width="20%">预算年份：</td>
				    <td class="tdulright"><input name="year" id="year" class="Wdate" style="width:150px"
					onfocus="WdatePicker({dateFmt:'yyyy',minDate: '%y'})" readonly/>
					</td>
				  </tr>
			     <tr class=trcolor >
				  	<td class="tdulleft">费用类型：</td>
				  	<td class="tdulright" >
				  		<input type="radio" name="expenseType" value="0" checked="checked"/>光缆
				  		<input type="radio" name="expenseType" value="1"/>管道
				  	</td>
				  </tr>
				  <tr class=trwhite >
				  	<td class="tdulleft">代维:</td>
				  	<td class="tdulright" >
				  		<html:select property="contractorId" style="width:155px" styleClass="required">
					  		<c:forEach items="${constrators}" var="con">
					  			<html:option value="${con.contractorID}">${con.contractorName}</html:option>
					  		</c:forEach>
				  		</html:select>
				  	</td>
				  </tr>
				   <tr class=trcolor >
				  	<td class="tdulleft">预算费用(元)：</td>
				  	<td class="tdulright" >
				  	 <html:text property="budgetMoney" styleClass="required validate-number max-length-8" style="width:195px"></html:text>
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
</html>
