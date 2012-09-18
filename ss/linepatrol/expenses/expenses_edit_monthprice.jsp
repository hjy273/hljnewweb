<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>修改月费用</title>
			<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
			<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
			<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
			<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
			<script type="text/javascript">
				function getMoney(){
					var monthprice = document.getElementById("monthprice").value;
					var deductionMoney = document.getElementById("deductionMoney").value;
					var rectifyMoney =  document.getElementById("rectifyMoney");
					var money = Number(monthprice) -  Number(deductionMoney);
					rectifyMoney.innerText=money;
				}
				function check(){
					var monthprice = document.getElementById("monthprice").value;
					var deductionMoney = document.getElementById("deductionMoney").value;
					var money = Number(monthprice) -  Number(deductionMoney);  
					if(money<0){
						alert("扣减费用不能大于月费用!");
						return false;
					}
					return true;
				}
			</script>
	</head>

	<body>
		<html:form action="/expensesAction.do?method=editMonthExpense"
			styleId="cableGradeFactor" onsubmit="return check();">
			<input type="hidden" value="${id}" name="id"/>
			<input type="hidden" value="${month.expenseType}" name="expenseType"/>
			<table width="95%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			     <tr height="25px" class=trwhite>
				    <td class="tdulleft" width="20%">月费用：</td>
				    <td class="tdulright">
				    	${month.monthPrice}元
				    	<input id="monthprice" value="${month.monthPrice}" type="hidden"/>
					</td>
				  </tr>
			     <tr class=trcolor >
				  	<td class="tdulleft">扣减费用：</td>
				  	<td class="tdulright" >
				  		<c:if test="${!ishave}">
				  			 <input name="deductionMoney" value="${month.deductionMoney}" id="deductionMoney" onkeyup="getMoney();"  class="required validate-number"/>元
				  		</c:if>
				  		<c:if test="${ishave}">
				  		   ${month.deductionMoney}元
				  		</c:if>
				  	</td>
				  </tr>
				  <tr class=trwhite >
				  	<td class="tdulleft">矫正费用:</td>
				  	<td class="tdulright" >
				  		<span id="rectifyMoney">${month.rectifyMoney}</span>元
				  	</td>
				  </tr>
				 <tr class=trcolor >
				  	<td class="tdulleft">扣减原因:</td>
				  	<td class="tdulright" >
				  		<c:if test="${!ishave}">
				  		  <textarea  name="remark" rows="3"  style="width:275px">${month.remark}</textarea>
				  		</c:if>
				  		<c:if test="${ishave}">
				  		   ${month.remark}
				  		</c:if>
				  	</td>
				  </tr>
			</table>
			<div align="center" style="height:80px">
				<c:if test="${!ishave}">
			       <html:submit property="action" styleClass="button">提交</html:submit> 
		       </c:if>
			   <input type="button" class="button" onclick="javascript:parent.close();" value="关闭"/>
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
