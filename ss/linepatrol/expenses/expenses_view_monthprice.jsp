<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>查看月费用</title>
	</head>

	<body>
		<html:form action="/expensesAction.do?method=editMonthExpense"
			styleId="cableGradeFactor" onsubmit="return check();">
			<input type="hidden" value="${id}" name="id"/>
			<table width="95%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			     <tr height="25px" class=trwhite>
				    <td class="tdulleft" width="20%">月费用：</td>
				    <td class="tdulright">
				    	${month.monthPrice}元
					</td>
				  </tr>
			     <tr class=trcolor >
				  	<td class="tdulleft">扣减费用：</td>
				  	<td class="tdulright" >
				  		 ${month.deductionMoney}元
				  	</td>
				  </tr>
				  <tr class=trwhite >
				  	<td class="tdulleft">矫正费用:</td>
				  	<td class="tdulright" >
				  		<span id="rectifyMoney">${month.rectifyMoney}元</span>
				  	</td>
				  </tr>
				 <tr class=trcolor >
				  	<td class="tdulleft">扣减原因:</td>
				  	<td class="tdulright" >
				  		 ${month.remark}
				  	</td>
				  </tr>
			</table>
			<div align="center" style="height:80px">
			   <input type="button" class="button" onclick="javascript:parent.close();" value="关闭"/>
			</div>
		</html:form>
	</body>
</html>
