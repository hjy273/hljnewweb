<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>�鿴�·���</title>
	</head>

	<body>
		<html:form action="/expensesAction.do?method=editMonthExpense"
			styleId="cableGradeFactor" onsubmit="return check();">
			<input type="hidden" value="${id}" name="id"/>
			<table width="95%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			     <tr height="25px" class=trwhite>
				    <td class="tdulleft" width="20%">�·��ã�</td>
				    <td class="tdulright">
				    	${month.monthPrice}Ԫ
					</td>
				  </tr>
			     <tr class=trcolor >
				  	<td class="tdulleft">�ۼ����ã�</td>
				  	<td class="tdulright" >
				  		 ${month.deductionMoney}Ԫ
				  	</td>
				  </tr>
				  <tr class=trwhite >
				  	<td class="tdulleft">��������:</td>
				  	<td class="tdulright" >
				  		<span id="rectifyMoney">${month.rectifyMoney}Ԫ</span>
				  	</td>
				  </tr>
				 <tr class=trcolor >
				  	<td class="tdulleft">�ۼ�ԭ��:</td>
				  	<td class="tdulright" >
				  		 ${month.remark}
				  	</td>
				  </tr>
			</table>
			<div align="center" style="height:80px">
			   <input type="button" class="button" onclick="javascript:parent.close();" value="�ر�"/>
			</div>
		</html:form>
	</body>
</html>
