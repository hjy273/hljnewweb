<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linepatrol.trouble.services.TroubleConstant" %>
<html>
	<head>
		<title>增加光缆级别取费</title>
			<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
			<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
			<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
			<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
			<script type="text/javascript">
				function judgeExpeseType(obj){
					if(obj=="1"){
						$('divpipe').style.display="";
						$('divcable').style.display="none";
					}
					if(obj=="0"){
						$('divpipe').style.display="none";
						$('divcable').style.display="";
					}
				}
			</script>
	</head>

	<body>
		
		<template:titile value="增加维护单价" />
		<html:form action="/expensesPriceAction.do?method=addUnitPrice"
			styleId="addCableTypePrice">
			<table width="80%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			      <tr class=trcolor >
				  	<td class="tdulleft">费用类型：</td>
				  	<td class="tdulright" >
				  		<input type="radio" name="expenseType" value="0" checked="checked" onclick="judgeExpeseType(this.value)"/>光缆
				  		<input type="radio" name="expenseType" value="1" onclick="judgeExpeseType(this.value)"/>管道
				  	</td>
				  </tr>
				  <tr class=trcolor >
				  	<td class="tdulleft">代维:</td>
				  	<td class="tdulright" >
				  		<html:select property="contractorid" style="width:155px" styleClass="required">
					  		<c:forEach items="${constrators}" var="con">
					  			<html:option value="${con.contractorID}">${con.contractorName}</html:option>
					  		</c:forEach>
				  		</html:select>
				  	</td>
				  </tr>
				  <tbody id="divcable">
				  <tr class=trcolor >
				  	<td class="tdulleft">级别描述：</td>
				  	<td class="tdulright" >
				  	 <html:text property="explan" styleClass="required" style="width:195px"></html:text>
				  	</td>
				  </tr>
				  <tr class=trwhite >
				  	<td class="tdulleft">光缆级别：</td>
				  	<td class="tdulright" >
				  		<html:select property="cableLevel" style="width:155px">
				  			<html:option value="1">一干</html:option>
				  			<html:option value="2">骨干</html:option>
				  			<html:option value="3">汇聚</html:option>
				  			<html:option value="4">接入(144芯及以下)</html:option>
				  			<html:option value="4A">接入(144芯以上)</html:option>
				  		</html:select>
				  	</td>
				  </tr>
				  <tr class=trcolor >
				  	<td class="tdulleft">基准价：</td>
				  	<td class="tdulright" >
				 	 	<html:text property="unitPrice" styleClass="required validate-number"></html:text>
				 	 	(元/月/皮长公里)
				  	</td>
				  </tr>
				  </tbody>
				  <tbody id="divpipe" style="display:none">
					  <tr class=trcolor >
					  	<td class="tdulleft">基准价：</td>
					  	<td class="tdulright" >
					 	 	<html:text property="unitPipePrice" styleClass="required validate-number"></html:text>
					 	 	(元/孔公里)
					  	</td>
					  </tr>
				  </tbody>
			</table>
			<div align="center" style="height:80px">
		       <html:submit property="action" styleClass="button">提交</html:submit>  &nbsp;&nbsp;&nbsp;&nbsp;
			   <html:reset property="action" styleClass="button">重置</html:reset>
			</div>
		</html:form>
		<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('addCableTypePrice', {immediate : true, onFormValidate : formCallback});
		</script>
	</body>
	<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery("#addCableTypePrice").bind("submit", function() {
			processBar(addCableTypePrice);
		});
	})
</script>
</html>
