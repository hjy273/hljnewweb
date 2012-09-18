<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>���ɷ���</title>
		<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
		<script type="text/javascript">
			function checkAddInfo() {
				var month = document.getElementById("month").value;
				if(month==""){
					alert("ͳ���·ݲ���Ϊ��!");
					return false;
				}
				return true;
			}
			
		exportList=function(){
			var guideType = document.getElementById("guideTypehidd").value;
			var month = document.getElementById("monthhidd").value;
			var url="${ctx}/troubleQuotaAction.do?method=exportTroubleQuotaList&guideType="+guideType+"&month="+month;
			self.location.replace(url);
		}
		
		getExpense=function(yearmonth,expenseType){
			var url ="${ctx}/expensesAction.do?method=getEexpenses&yearmonth="+yearmonth+"&expensetype="+expenseType;
			window.location.href=url;
		}
		
		exportList=function(yearmonth){
			var url="${ctx}/expensesAction.do?method=exportExpense&yearmonth="+yearmonth;
			self.location.replace(url);
		}
		
		exportPipeList=function(yearmonth){
			var url="${ctx}/expensesAction.do?method=exportPipeExpense&yearmonth="+yearmonth;
			self.location.replace(url);
		}
		
		
		
			// �޸��·��ã��ۼ�����
			function editMonthExpense(id){
			 var u="${ctx}/expensesAction.do?method=editMonthExpenseForm&id="+id;
			  win = new Ext.Window({
			  layout : 'fit',
			  width: document.body.clientWidth * 0.50 , 
			  height:260, 
			  resizable:true,
			  closeAction : 'close', 
			  modal:true,
			  
			// autoLoad:{url: u,scripts:true}, 
			  html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src='+u+' />',
			  plain: true,
			  title:"�·���" 
			 });
			  win.show(Ext.getBody());
			}
			  
			 function close(){
			  	win.close();
			 }
			
			//����
			function editMoney(id,value){
				var id = document.getElementById(id);
				id.innerText = value;
			}
			
			//�ܵ�
			function editPipeMoney(id,rm,dm,remark){
				var idrm = document.getElementById(id+"rm");
				idrm.innerText = rm;
				var iddm = document.getElementById(id+"dm");
				iddm.innerText = dm;
				var idremark = document.getElementById(id+"remark");
				idremark.innerText = remark;
			}
		</script>
	</head>
	<body>
		<template:titile value="��������" />
		<html:form action="/expensesAction.do?method=createExpense"
			styleId="statExpense" onsubmit="return checkAddInfo()">
			<table width="50%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
				 <tr class=trwhite >
				  	<td class="tdulleft">�������ͣ�</td>
				  	<td class="tdulright" >
				  		<input type="radio" name="expenseType" value="0" checked="checked"/>����
				  		<input type="radio" name="expenseType" value="1"/>�ܵ� 
				  	</td>
				  </tr>
				  <tr class=trcolor>
				    <td class="tdulleft" width="20%">�����·ݣ�</td>
				    <td class="tdulright"><input name="month" id="month" class="Wdate" style="width:150px"
					onfocus="WdatePicker({dateFmt:'yyyy/MM',maxDate: '%y-#{%M-1}'})" readonly/>
					</td>
				  </tr>
			     <tr class=trwhite>
					<td colspan="6" style="text-align:center;">
						<input name="action" class="button" value="����"	 type="submit" />
					</td>
				 </tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
				<c:if test="${not empty cableMonths}">
					 <tr class=trcolor>
					 	<td>�Ѿ����ɹ��·��õ��·�
						   	<c:forEach items="${cableMonths}" var="month">&nbsp;&nbsp;
						   		<a href="javascript:getExpense('<bean:write name="month" format="yyyy/MM"/>','0')">
						   			<bean:write name="month" format="yyyy/MM"/>
						   	   </a>&nbsp;&nbsp;
						   	</c:forEach>
					   	</td>
					  </tr>
				</c:if>
				<c:if test="${not empty plMonths}">
					 <tr class=trcolor>
					 	<td>�Ѿ����ɹܵ����õ��·�
						   	<c:forEach items="${plMonths}" var="month">&nbsp;&nbsp;
						   		<a href="javascript:getExpense('<bean:write name="month" format="yyyy/MM"/>','1')">
						   			<bean:write name="month" format="yyyy/MM"/>
						   	   </a>&nbsp;&nbsp;
						   	</c:forEach>
						 </td>
					 </tr>
				</c:if>
			</table>
		</html:form>
		<c:if test="${not empty pipeExpenses}">
			<table width="100%" border="1" align="center" bgcolor="#FFFFFF"
				cellpadding="0" cellspacing="0" bordercolor="#000"
				style="border-collapse: collapse">
				<tr>
					<td colspan="8" align="center">
						<font size="2px" style="font-weight: bold"> ${yearmonth}�ܵ�ά������ </font>
					</td>
				</tr>
				<tr bgcolor="#cccccc" height="30px">
					<td>
						<font size="2px" style="font-weight: bold">��ά��˾</font>
					</td>
					<td>
						<font size="2px" style="font-weight: bold">ά������(Ԫ)</font>
					</td>
					<td>
						<font size="2px" style="font-weight: bold">ȡ�ѵ���(Ԫ)</font>
					</td>
					<td>
						<font size="2px" style="font-weight: bold">�ܵ�����</font>
					</td>
					<td>
						<font size="2px" style="font-weight: bold">�ܵ�����(km)</font>
					</td>
					<td>
						<font size="2px" style="font-weight: bold">��������(Ԫ)</font>
					</td>
					<td>
						<font size="2px" style="font-weight: bold">�ۼ�����(Ԫ)</font>
					</td>
					<td width="35%">
						<font size="2px" style="font-weight: bold">�ۼ�ԭ��</font>
					</td>
				</tr>
				<logic:iterate id="pipeExpense" name="pipeExpenses" >
					<tr>
						<td><bean:write name="pipeExpense" property="key"/></td>
						<bean:define id="pipemonth" name="pipeExpense" property="value"></bean:define>
						<bean:define id="monthprice" name="pipemonth" property="month_price"></bean:define>
						<bean:define id="monthID" name="pipemonth" property="id"></bean:define>
							<td>
							<c:if test="${monthprice gt 0}">
								<a href="javascript:editMonthExpense('${monthID}')">
									${monthprice}
								</a>
							</c:if>
							<c:if test="${monthprice eq 0}">
								${monthprice}
							</c:if>
							</td>
							<td><bean:write name="pipemonth" property="unit_price"/> </td>
							<td><bean:write name="pipemonth" property="cable_num"/> </td>
							<td><bean:write name="pipemonth" property="cable_length"/> </td>
							<td><span id="${monthID}rm"><bean:write name="pipemonth" property="rectify_money"/></span> </td>
							<td><span id="${monthID}dm"><bean:write name="pipemonth" property="deduction_money"/></span> </td>
							<td><span id="${monthID}remark"><bean:write name="pipemonth" property="remark"/></span> </td>
					</tr>
				</logic:iterate>
				<tr>
					<td colspan="8">&nbsp;&nbsp; <a href="javascript:exportPipeList('${yearmonth}')">����ΪExcel�ļ�</a></td>
				</tr>
			</table>
		
		</c:if>
		<c:if test="${not empty expenses}">
			<table width="2200px" border="1" align="center" bgcolor="#FFFFFF"
				cellpadding="0" cellspacing="0" bordercolor="#000"
				style="border-collapse: collapse">
				<tr height="35px">
					<td colspan="22" style="padding-left: 370px">
						<font size="2px" style="font-weight: bold"> ${yearmonth}����ά������ </font>
					</td>
				</tr>
				
				<!-- Map<String,Map<Double,List>> -->
				<!-- <��ά��˾���ƣ�<�ּ�ϵ���������б�4a��ʾ����144о��>> -->
				<logic:iterate id="oneContractorExpense" name="expenses">
					<logic:notEmpty name="oneContractorExpense" property="value">
						<bean:define id="oneExpenseMap" name="oneContractorExpense"
							property="value"></bean:define>
							<bean:size id="size" name="oneContractorExpense" property="value"/>
								<logic:iterate id="oneExpenseFactor" name="oneExpenseMap" indexId="i">
							<c:if test="${i==0}">
							<tr bgcolor="#cccccc" height="30px">
									<td>
										<font size="2px" style="font-weight: bold">��ά��˾</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">�ּ�ȡ��ϵ��</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">һ�ɳ���(km)</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">һ���м̶���</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">һ�ɵ���(Ԫ)</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">һ��ά������(Ԫ)</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">�Ǹɳ���(km)</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">�Ǹ��м̶���</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">�Ǹɵ���(Ԫ)</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">�Ǹ�ά������(Ԫ)</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">��۳���(km)</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">����м̶���</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">��۵���(Ԫ)</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">���ά������(Ԫ)</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">���볤��(km)</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">�����м̶���</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">���뵥��(Ԫ)</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">����ά������(Ԫ)</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">����144о����</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">����144о�����м̶���</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">����144о���뵥��(Ԫ)</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">����144о����ά������(Ԫ)</font>
									</td>
								</tr>
							</c:if>
						
							<tr>
								<c:if test="${i==0}">
									<td rowspan="${size}">
										<font size="2px" style="font-weight: bold">
											<bean:write name="oneContractorExpense" property="key" />
										</font>
									</td>
								</c:if>
								<td>
									<bean:define id="factor" name="oneExpenseFactor" property="key"></bean:define>
									<c:choose>
										<c:when test="${factor eq '�ϼ�' || factor eq '�·���'}">
											<font size="2px" style="font-weight: bold">
												${factor}
											</font>
										</c:when>
										<c:otherwise>
										   	${factor}
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<bean:define id="existMainLevel" value="false"></bean:define>
									<logic:iterate id="oneExpenseDetail" name="oneExpenseFactor" property="value">
										<logic:equal value="1" name="oneExpenseDetail" property="cable_level">
											<bean:define id="existMainLevel" value="true"></bean:define>
											<c:choose>
												<c:when test="${factor eq '�ϼ�'}">
											    	<bean:write name="oneExpenseDetail" property="len1" />
												</c:when>
												<c:when test="${factor eq '�·���'}">
													<bean:define id="monthprice" name="oneExpenseDetail" property="month_price"></bean:define>
													<bean:define id="monthid" name="oneExpenseDetail" property="id"></bean:define>
											    	<c:if test="${monthprice gt 0}">
												    	<a href="javascript:editMonthExpense('${monthid}')">
												    		<span>${monthprice}</span>
												    	</a>
											    	</c:if>
											    	<c:if test="${monthprice eq 0}">
											    			${monthprice}
											    	</c:if>
												</c:when>
												<c:otherwise>
													<bean:write name="oneExpenseDetail" property="maintenance_length" />
												</c:otherwise>
											</c:choose>
										</logic:equal>
									</logic:iterate>
										<logic:equal value="false" name="existMainLevel">
											0
										</logic:equal>
								</td>
								<td>
									<logic:iterate id="oneExpenseDetail" name="oneExpenseFactor" property="value">
										<logic:equal value="1" name="oneExpenseDetail" property="cable_level">
											<c:choose>
												<c:when test="${factor eq '�ϼ�'}">
											    	<bean:write name="oneExpenseDetail" property="num1" />
												</c:when>
												<c:when test="${factor eq '�·���'}">
													<font size="2px" style="font-weight: bold">
														��������
												    </font>
												</c:when>
												<c:otherwise>
													<bean:write name="oneExpenseDetail" property="maintenance_num" />
												</c:otherwise>
											</c:choose>
										</logic:equal>
									</logic:iterate>
										<logic:equal value="false" name="existMainLevel">
											0
										</logic:equal>
								</td>
								<td>
									<logic:iterate id="oneExpenseDetail" name="oneExpenseFactor" property="value">
										<logic:equal value="1" name="oneExpenseDetail" property="cable_level">
											<c:choose>
												<c:when test="${factor eq '�ϼ�'}">
											    	&nbsp;
												</c:when>
												<c:when test="${factor eq '�·���'}">
												    <bean:define id="rectifymoney" name="oneExpenseDetail" property="rectify_money"></bean:define>
													<bean:define id="monthid" name="oneExpenseDetail" property="id"></bean:define>
											       <span id="${monthid}">${rectifymoney}</span>
												</c:when>
												<c:otherwise>
													<bean:write name="oneExpenseDetail" property="unit_price" />
												</c:otherwise>
											</c:choose>
										</logic:equal>
									</logic:iterate>
										<logic:equal value="false" name="existMainLevel">
											0
										</logic:equal>
								</td>
								<td>
									<logic:iterate id="oneExpenseDetail" name="oneExpenseFactor" property="value">
										<logic:equal value="1" name="oneExpenseDetail" property="cable_level">
											<c:choose>
												<c:when test="${factor eq '�ϼ�'}">
											    	<bean:write name="oneExpenseDetail" property="price1" />
												</c:when>
												<c:when test="${factor eq '�·���'}">
													<font size="2px" style="font-weight: bold">
											    	   �м̶���
											    	</font>
												</c:when>
												<c:otherwise>
													<bean:write name="oneExpenseDetail" property="maintain_money" />
												</c:otherwise>
											</c:choose>
										</logic:equal>
									</logic:iterate>
										<logic:equal value="false" name="existMainLevel">
											0
										</logic:equal>
								</td>
								<td>
									<bean:define id="existSecondLevel" value="false"></bean:define>
									<logic:iterate id="oneExpenseDetail" name="oneExpenseFactor" property="value">
										<logic:equal value="2" name="oneExpenseDetail" property="cable_level">
											<bean:define id="existSecondLevel" value="true"></bean:define>
											<c:choose>
												<c:when test="${factor eq '�ϼ�'}">
											    	<bean:write name="oneExpenseDetail" property="len2" />
												</c:when>
												<c:when test="${factor eq '�·���'}">
											    	 <bean:write name="oneExpenseDetail" property="cable_num" />
												</c:when>
												<c:otherwise>
													<bean:write name="oneExpenseDetail" property="maintenance_length" />
												</c:otherwise>
											</c:choose>
										</logic:equal>
									</logic:iterate>
										<logic:equal value="false" name="existSecondLevel">
											0
										</logic:equal>
								</td>
								<td>
									<logic:iterate id="oneExpenseDetail" name="oneExpenseFactor" property="value">
										<logic:equal value="2" name="oneExpenseDetail" property="cable_level">
											<c:choose>
												<c:when test="${factor eq '�ϼ�'}">
											    	<bean:write name="oneExpenseDetail" property="num2" />
												</c:when>
												<c:when test="${factor eq '�·���'}">
													���³���(km)
												</c:when>
												<c:otherwise>
													<bean:write name="oneExpenseDetail" property="maintenance_num" />
												</c:otherwise>
											</c:choose>
										</logic:equal>
									</logic:iterate>
										<logic:equal value="false" name="existSecondLevel">
											0
										</logic:equal>
								</td>
								<td>
									<logic:iterate id="oneExpenseDetail" name="oneExpenseFactor" property="value">
										<logic:equal value="2" name="oneExpenseDetail" property="cable_level">
											<c:choose>
												<c:when test="${factor eq '�ϼ�'}">
												</c:when>
												<c:when test="${factor eq '�·���'}">
											 		<bean:write name="oneExpenseDetail" property="cable_length" />
												</c:when>
												<c:otherwise>
													<bean:write name="oneExpenseDetail" property="unit_price" />
												</c:otherwise>
											</c:choose>
										</logic:equal>
									</logic:iterate>
										<logic:equal value="false" name="existSecondLevel">
											0
										</logic:equal>
								</td>
								<td>
									<logic:iterate id="oneExpenseDetail" name="oneExpenseFactor" property="value">
										<logic:equal value="2" name="oneExpenseDetail" property="cable_level">
											<c:choose>
												<c:when test="${factor eq '�ϼ�'}">
											    	<bean:write name="oneExpenseDetail" property="price2" />
												</c:when>
												<c:when test="${factor eq '�·���'}">
												</c:when>
												<c:otherwise>
													<bean:write name="oneExpenseDetail" property="maintain_money" />
												</c:otherwise>
											</c:choose>
										</logic:equal>
									</logic:iterate>
										<logic:equal value="false" name="existSecondLevel">
											0
										</logic:equal>
								</td>
								<td>
									<bean:define id="existThirdLevel" value="false"></bean:define>
									<logic:iterate id="oneExpenseDetail" name="oneExpenseFactor" property="value">
										<logic:equal value="3" name="oneExpenseDetail" property="cable_level">
											<bean:define id="existThirdLevel" value="true"></bean:define>
											<c:choose>
												<c:when test="${factor eq '�ϼ�'}">
											    	<bean:write name="oneExpenseDetail" property="len3" />
												</c:when>
												<c:when test="${factor eq '�·���'}">
												</c:when>
												<c:otherwise>
													<bean:write name="oneExpenseDetail" property="maintenance_length" />
												</c:otherwise>
											</c:choose>
										</logic:equal>
									</logic:iterate>
										<logic:equal value="false" name="existThirdLevel">
											0
										</logic:equal>
								</td>
								<td>
									<logic:iterate id="oneExpenseDetail" name="oneExpenseFactor" property="value">
										<logic:equal value="3" name="oneExpenseDetail" property="cable_level">
											<c:choose>
												<c:when test="${factor eq '�ϼ�'}">
											    	<bean:write name="oneExpenseDetail" property="num3" />
												</c:when>
												<c:when test="${factor eq '�·���'}">
												</c:when>
												<c:otherwise>
													<bean:write name="oneExpenseDetail" property="maintenance_num" />
												</c:otherwise>
											</c:choose>
										</logic:equal>
									</logic:iterate>
										<logic:equal value="false" name="existThirdLevel">
											0
										</logic:equal>
								</td>
								<td>
									<logic:iterate id="oneExpenseDetail" name="oneExpenseFactor" property="value">
										<logic:equal value="3" name="oneExpenseDetail" property="cable_level">
											<c:choose>
												<c:when test="${factor eq '�ϼ�'}">
												</c:when>
												<c:when test="${factor eq '�·���'}">
												</c:when>
												<c:otherwise>
													<bean:write name="oneExpenseDetail" property="unit_price" />
												</c:otherwise>
											</c:choose>
										</logic:equal>
									</logic:iterate>
										<logic:equal value="false" name="existThirdLevel">
											0
										</logic:equal>
								</td>
								<td>
									<logic:iterate id="oneExpenseDetail" name="oneExpenseFactor" property="value">
										<logic:equal value="3" name="oneExpenseDetail" property="cable_level">
											<c:choose>
												<c:when test="${factor eq '�ϼ�'}">
											    	<bean:write name="oneExpenseDetail" property="price3" />
												</c:when>
												<c:when test="${factor eq '�·���'}">
												</c:when>
												<c:otherwise>
													<bean:write name="oneExpenseDetail" property="maintain_money" />
												</c:otherwise>
											</c:choose>
										</logic:equal>
									</logic:iterate>
										<logic:equal value="false" name="existThirdLevel">
											0
										</logic:equal>
								</td>
								<td>
									<bean:define id="existTourthLevel" value="false"></bean:define>
									<logic:iterate id="oneExpenseDetail" name="oneExpenseFactor" property="value">
										<logic:equal value="4" name="oneExpenseDetail" property="cable_level">
											<bean:define id="existTourthLevel" value="true"></bean:define>
											<c:choose>
												<c:when test="${factor eq '�ϼ�'}">
											    	<bean:write name="oneExpenseDetail" property="len4" />
												</c:when>
												<c:when test="${factor eq '�·���'}">
												</c:when>
												<c:otherwise>
													<bean:write name="oneExpenseDetail" property="maintenance_length" />
												</c:otherwise>
											</c:choose>
										</logic:equal>
									</logic:iterate>
										<logic:equal value="false" name="existTourthLevel">
											0
										</logic:equal>
								</td>
								<td>
									<logic:iterate id="oneExpenseDetail" name="oneExpenseFactor" property="value">
										<logic:equal value="4" name="oneExpenseDetail" property="cable_level">
											<c:choose>
												<c:when test="${factor eq '�ϼ�'}">
											    	<bean:write name="oneExpenseDetail" property="num4" />
												</c:when>
												<c:when test="${factor eq '�·���'}">
												</c:when>
												<c:otherwise>
													<bean:write name="oneExpenseDetail" property="maintenance_num" />
												</c:otherwise>
											</c:choose>
										</logic:equal>
									</logic:iterate>
										<logic:equal value="false" name="existTourthLevel">
											0
										</logic:equal>
								</td>
								<td>
									<logic:iterate id="oneExpenseDetail" name="oneExpenseFactor" property="value">
										<logic:equal value="4" name="oneExpenseDetail" property="cable_level">
											<c:choose>
												<c:when test="${factor eq '�ϼ�'}">
												</c:when>
												<c:when test="${factor eq '�·���'}">
												</c:when>
												<c:otherwise>
													<bean:write name="oneExpenseDetail" property="unit_price" />
												</c:otherwise>
											</c:choose>
										</logic:equal>
									</logic:iterate>
										<logic:equal value="false" name="existTourthLevel">
											0
										</logic:equal>
								</td>
								<td>
									<logic:iterate id="oneExpenseDetail" name="oneExpenseFactor" property="value">
										<logic:equal value="4" name="oneExpenseDetail" property="cable_level">
											<c:choose>
												<c:when test="${factor eq '�ϼ�'}">
											    	<bean:write name="oneExpenseDetail" property="price4" />
												</c:when>
												<c:when test="${factor eq '�·���'}">
												</c:when>
												<c:otherwise>
													<bean:write name="oneExpenseDetail" property="maintain_money" />
												</c:otherwise>
											</c:choose>
										</logic:equal>
									</logic:iterate>
										<logic:equal value="false" name="existTourthLevel">
											0
										</logic:equal>
								</td>
								<td>
									<bean:define id="existFifthLevel" value="false"></bean:define>
									<logic:iterate id="oneExpenseDetail" name="oneExpenseFactor" property="value">
										<logic:equal value="4A" name="oneExpenseDetail" property="cable_level">
											<bean:define id="existFifthLevel" value="true"></bean:define>
											<c:choose>
												<c:when test="${factor eq '�ϼ�'}">
											    	<bean:write name="oneExpenseDetail" property="len4a" />
												</c:when>
												<c:when test="${factor eq '�·���'}">
												</c:when>
												<c:otherwise>
													<bean:write name="oneExpenseDetail" property="maintenance_length" />
												</c:otherwise>
											</c:choose>
										</logic:equal>
									</logic:iterate>
										<logic:equal value="false" name="existFifthLevel">
											0
										</logic:equal>
								</td>
								<td>
									<logic:iterate id="oneExpenseDetail" name="oneExpenseFactor" property="value">
										<logic:equal value="4A" name="oneExpenseDetail" property="cable_level">
											<c:choose>
												<c:when test="${factor eq '�ϼ�'}">
											    	<bean:write name="oneExpenseDetail" property="num4a" />
												</c:when>
												<c:when test="${factor eq '�·���'}">
												</c:when>
												<c:otherwise>
													<bean:write name="oneExpenseDetail" property="maintenance_num" />
												</c:otherwise>
											</c:choose>
										</logic:equal>
									</logic:iterate>
										<logic:equal value="false" name="existFifthLevel">
											0
										</logic:equal>
								</td>
								<td>
									<logic:iterate id="oneExpenseDetail" name="oneExpenseFactor" property="value">
										<logic:equal value="4A" name="oneExpenseDetail" property="cable_level">
											<c:choose>
												<c:when test="${factor eq '�ϼ�'}">
												</c:when>
												<c:when test="${factor eq '�·���'}">
												</c:when>
												<c:otherwise>
													<bean:write name="oneExpenseDetail" property="unit_price" />
												</c:otherwise>
											</c:choose>
										</logic:equal>
									</logic:iterate>
										<logic:equal value="false" name="existFifthLevel">
											0
										</logic:equal>
								</td>
								<td>
									<logic:iterate id="oneExpenseDetail" name="oneExpenseFactor" property="value">
										<logic:equal value="4A" name="oneExpenseDetail" property="cable_level">
											<c:choose>
												<c:when test="${factor eq '�ϼ�'}">
											    	<bean:write name="oneExpenseDetail" property="price4a" />
												</c:when>
												<c:when test="${factor eq '�·���'}">
												</c:when>
												<c:otherwise>
													<bean:write name="oneExpenseDetail" property="maintain_money" />
												</c:otherwise>
											</c:choose>
										</logic:equal>
									</logic:iterate>
										<logic:equal value="false" name="existFifthLevel">
											0
										</logic:equal>
								</td>
							</tr>
						</logic:iterate>
					</logic:notEmpty>
				</logic:iterate>
				<tr>
					<td colspan="22">&nbsp;&nbsp; <a href="javascript:exportList('${yearmonth}')">����ΪExcel�ļ�</a></td>
				</tr>
			</table>
		</c:if>
	</body>
</html>
