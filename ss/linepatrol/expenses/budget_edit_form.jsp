<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>�޸�Ԥ��</title>
			<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
			<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
			<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
			<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
			
			<script type="text/javascript">
				function checkAddInfo() {
					var month = document.getElementById("year").value;
					if(month==""){
						alert("Ԥ����ݲ���Ϊ��!");
						return false;
					}
					processBar(cableGradeFactor);
					return true;
				}
			</script>
	</head>

	<body>
		<template:titile value="�޸�Ԥ��" />
		<html:form action="/budgeExpensesAction.do?method=editBudget"
			styleId="cableGradeFactor" onsubmit="return checkAddInfo();">
			<input type="hidden" name="id" value="${budgetFactorBean.id}"/>
			<input type="hidden" name="payMoney" value="${budgetFactorBean.payMoney}"/>
			<table width="80%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			     <tr class=trwhite>
				    <td class="tdulleft" width="20%">Ԥ����ݣ�</td>
				    <td class="tdulright"><input name="year" id="year" class="Wdate" style="width:150px"
				    value="${budgetFactorBean.year}"
					onfocus="WdatePicker({dateFmt:'yyyy',minDate: '%y'})" readonly/>
					</td>
				  </tr>
			     <tr class=trcolor >
				  	<td class="tdulleft">�������ͣ�</td>
				  	<td class="tdulright" >
				  		<c:if test="${budgetFactorBean.expenseType==0}">
					  		<input type="radio" name="expenseType" value="0" checked="checked"/>����
					  		<input type="radio" name="expenseType" value="1"/>�ܵ�
				  		</c:if>
				  		<c:if test="${budgetFactorBean.expenseType==1}">
					  		<input type="radio" name="expenseType" value="0" />����
					  		<input type="radio" name="expenseType" value="1" checked="checked"/>�ܵ�
				  		</c:if>
				  	</td>
				  </tr>
				  <tr class=trwhite >
				  	<td class="tdulleft">��ά:</td>
				  	<td class="tdulright" >
				  		<html:select property="contractorId" style="width:155px" styleClass="required">
					  		<c:forEach items="${constrators}" var="con">
					  			<html:option value="${con.contractorID}">${con.contractorName}</html:option>
					  		</c:forEach>
				  		</html:select>
				  	</td>
				  </tr>
				   <tr class=trcolor >
				  	<td class="tdulleft">Ԥ�����(Ԫ)��</td>
				  	<td class="tdulright" >
				  	 <html:text property="budgetMoney" styleClass="required validate-number max-length-8" style="width:195px"></html:text>
				  	</td>
				  </tr>
			</table>
			<div align="center" style="height:80px">
		       <html:submit property="action" styleClass="button">�ύ</html:submit> 
			   <html:button property="action" onclick="javascript:history.back();" styleClass="button">����</html:button>
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
