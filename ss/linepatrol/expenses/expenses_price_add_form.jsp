<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linepatrol.trouble.services.TroubleConstant" %>
<html>
	<head>
		<title>���ӹ��¼���ȡ��</title>
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
		
		<template:titile value="����ά������" />
		<html:form action="/expensesPriceAction.do?method=addUnitPrice"
			styleId="addCableTypePrice">
			<table width="80%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			      <tr class=trcolor >
				  	<td class="tdulleft">�������ͣ�</td>
				  	<td class="tdulright" >
				  		<input type="radio" name="expenseType" value="0" checked="checked" onclick="judgeExpeseType(this.value)"/>����
				  		<input type="radio" name="expenseType" value="1" onclick="judgeExpeseType(this.value)"/>�ܵ�
				  	</td>
				  </tr>
				  <tr class=trcolor >
				  	<td class="tdulleft">��ά:</td>
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
				  	<td class="tdulleft">����������</td>
				  	<td class="tdulright" >
				  	 <html:text property="explan" styleClass="required" style="width:195px"></html:text>
				  	</td>
				  </tr>
				  <tr class=trwhite >
				  	<td class="tdulleft">���¼���</td>
				  	<td class="tdulright" >
				  		<html:select property="cableLevel" style="width:155px">
				  			<html:option value="1">һ��</html:option>
				  			<html:option value="2">�Ǹ�</html:option>
				  			<html:option value="3">���</html:option>
				  			<html:option value="4">����(144о������)</html:option>
				  			<html:option value="4A">����(144о����)</html:option>
				  		</html:select>
				  	</td>
				  </tr>
				  <tr class=trcolor >
				  	<td class="tdulleft">��׼�ۣ�</td>
				  	<td class="tdulright" >
				 	 	<html:text property="unitPrice" styleClass="required validate-number"></html:text>
				 	 	(Ԫ/��/Ƥ������)
				  	</td>
				  </tr>
				  </tbody>
				  <tbody id="divpipe" style="display:none">
					  <tr class=trcolor >
					  	<td class="tdulleft">��׼�ۣ�</td>
					  	<td class="tdulright" >
					 	 	<html:text property="unitPipePrice" styleClass="required validate-number"></html:text>
					 	 	(Ԫ/�׹���)
					  	</td>
					  </tr>
				  </tbody>
			</table>
			<div align="center" style="height:80px">
		       <html:submit property="action" styleClass="button">�ύ</html:submit>  &nbsp;&nbsp;&nbsp;&nbsp;
			   <html:reset property="action" styleClass="button">����</html:reset>
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
