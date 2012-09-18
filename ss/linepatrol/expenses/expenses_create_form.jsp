<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>生成费用</title>
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
					alert("统计月份不能为空!");
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
		
		
		
			// 修改月费用，扣减功能
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
			  title:"月费用" 
			 });
			  win.show(Ext.getBody());
			}
			  
			 function close(){
			  	win.close();
			 }
			
			//光缆
			function editMoney(id,value){
				var id = document.getElementById(id);
				id.innerText = value;
			}
			
			//管道
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
		<template:titile value="费用生成" />
		<html:form action="/expensesAction.do?method=createExpense"
			styleId="statExpense" onsubmit="return checkAddInfo()">
			<table width="50%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
				 <tr class=trwhite >
				  	<td class="tdulleft">费用类型：</td>
				  	<td class="tdulright" >
				  		<input type="radio" name="expenseType" value="0" checked="checked"/>光缆
				  		<input type="radio" name="expenseType" value="1"/>管道 
				  	</td>
				  </tr>
				  <tr class=trcolor>
				    <td class="tdulleft" width="20%">生成月份：</td>
				    <td class="tdulright"><input name="month" id="month" class="Wdate" style="width:150px"
					onfocus="WdatePicker({dateFmt:'yyyy/MM',maxDate: '%y-#{%M-1}'})" readonly/>
					</td>
				  </tr>
			     <tr class=trwhite>
					<td colspan="6" style="text-align:center;">
						<input name="action" class="button" value="生成"	 type="submit" />
					</td>
				 </tr>
			</table>
			<table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
				<c:if test="${not empty cableMonths}">
					 <tr class=trcolor>
					 	<td>已经生成光缆费用的月份
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
					 	<td>已经生成管道费用的月份
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
						<font size="2px" style="font-weight: bold"> ${yearmonth}管道维护费用 </font>
					</td>
				</tr>
				<tr bgcolor="#cccccc" height="30px">
					<td>
						<font size="2px" style="font-weight: bold">代维公司</font>
					</td>
					<td>
						<font size="2px" style="font-weight: bold">维护费用(元)</font>
					</td>
					<td>
						<font size="2px" style="font-weight: bold">取费单价(元)</font>
					</td>
					<td>
						<font size="2px" style="font-weight: bold">管道数量</font>
					</td>
					<td>
						<font size="2px" style="font-weight: bold">管道长度(km)</font>
					</td>
					<td>
						<font size="2px" style="font-weight: bold">矫正费用(元)</font>
					</td>
					<td>
						<font size="2px" style="font-weight: bold">扣减费用(元)</font>
					</td>
					<td width="35%">
						<font size="2px" style="font-weight: bold">扣减原因</font>
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
					<td colspan="8">&nbsp;&nbsp; <a href="javascript:exportPipeList('${yearmonth}')">导出为Excel文件</a></td>
				</tr>
			</table>
		
		</c:if>
		<c:if test="${not empty expenses}">
			<table width="2200px" border="1" align="center" bgcolor="#FFFFFF"
				cellpadding="0" cellspacing="0" bordercolor="#000"
				style="border-collapse: collapse">
				<tr height="35px">
					<td colspan="22" style="padding-left: 370px">
						<font size="2px" style="font-weight: bold"> ${yearmonth}光缆维护费用 </font>
					</td>
				</tr>
				
				<!-- Map<String,Map<Double,List>> -->
				<!-- <代维公司名称，<分级系数，费用列表（4a表示大于144芯）>> -->
				<logic:iterate id="oneContractorExpense" name="expenses">
					<logic:notEmpty name="oneContractorExpense" property="value">
						<bean:define id="oneExpenseMap" name="oneContractorExpense"
							property="value"></bean:define>
							<bean:size id="size" name="oneContractorExpense" property="value"/>
								<logic:iterate id="oneExpenseFactor" name="oneExpenseMap" indexId="i">
							<c:if test="${i==0}">
							<tr bgcolor="#cccccc" height="30px">
									<td>
										<font size="2px" style="font-weight: bold">代维公司</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">分级取费系数</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">一干长度(km)</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">一干中继段数</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">一干单价(元)</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">一干维护费用(元)</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">骨干长度(km)</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">骨干中继段数</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">骨干单价(元)</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">骨干维护费用(元)</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">汇聚长度(km)</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">汇聚中继段数</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">汇聚单价(元)</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">汇聚维护费用(元)</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">接入长度(km)</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">接入中继段数</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">接入单价(元)</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">接入维护费用(元)</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">大于144芯接入</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">大于144芯接入中继段数</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">大于144芯接入单价(元)</font>
									</td>
									<td>
										<font size="2px" style="font-weight: bold">大于144芯接入维护费用(元)</font>
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
										<c:when test="${factor eq '合计' || factor eq '月费用'}">
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
												<c:when test="${factor eq '合计'}">
											    	<bean:write name="oneExpenseDetail" property="len1" />
												</c:when>
												<c:when test="${factor eq '月费用'}">
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
												<c:when test="${factor eq '合计'}">
											    	<bean:write name="oneExpenseDetail" property="num1" />
												</c:when>
												<c:when test="${factor eq '月费用'}">
													<font size="2px" style="font-weight: bold">
														矫正费用
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
												<c:when test="${factor eq '合计'}">
											    	&nbsp;
												</c:when>
												<c:when test="${factor eq '月费用'}">
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
												<c:when test="${factor eq '合计'}">
											    	<bean:write name="oneExpenseDetail" property="price1" />
												</c:when>
												<c:when test="${factor eq '月费用'}">
													<font size="2px" style="font-weight: bold">
											    	   中继段数
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
												<c:when test="${factor eq '合计'}">
											    	<bean:write name="oneExpenseDetail" property="len2" />
												</c:when>
												<c:when test="${factor eq '月费用'}">
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
												<c:when test="${factor eq '合计'}">
											    	<bean:write name="oneExpenseDetail" property="num2" />
												</c:when>
												<c:when test="${factor eq '月费用'}">
													光缆长度(km)
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
												<c:when test="${factor eq '合计'}">
												</c:when>
												<c:when test="${factor eq '月费用'}">
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
												<c:when test="${factor eq '合计'}">
											    	<bean:write name="oneExpenseDetail" property="price2" />
												</c:when>
												<c:when test="${factor eq '月费用'}">
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
												<c:when test="${factor eq '合计'}">
											    	<bean:write name="oneExpenseDetail" property="len3" />
												</c:when>
												<c:when test="${factor eq '月费用'}">
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
												<c:when test="${factor eq '合计'}">
											    	<bean:write name="oneExpenseDetail" property="num3" />
												</c:when>
												<c:when test="${factor eq '月费用'}">
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
												<c:when test="${factor eq '合计'}">
												</c:when>
												<c:when test="${factor eq '月费用'}">
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
												<c:when test="${factor eq '合计'}">
											    	<bean:write name="oneExpenseDetail" property="price3" />
												</c:when>
												<c:when test="${factor eq '月费用'}">
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
												<c:when test="${factor eq '合计'}">
											    	<bean:write name="oneExpenseDetail" property="len4" />
												</c:when>
												<c:when test="${factor eq '月费用'}">
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
												<c:when test="${factor eq '合计'}">
											    	<bean:write name="oneExpenseDetail" property="num4" />
												</c:when>
												<c:when test="${factor eq '月费用'}">
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
												<c:when test="${factor eq '合计'}">
												</c:when>
												<c:when test="${factor eq '月费用'}">
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
												<c:when test="${factor eq '合计'}">
											    	<bean:write name="oneExpenseDetail" property="price4" />
												</c:when>
												<c:when test="${factor eq '月费用'}">
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
												<c:when test="${factor eq '合计'}">
											    	<bean:write name="oneExpenseDetail" property="len4a" />
												</c:when>
												<c:when test="${factor eq '月费用'}">
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
												<c:when test="${factor eq '合计'}">
											    	<bean:write name="oneExpenseDetail" property="num4a" />
												</c:when>
												<c:when test="${factor eq '月费用'}">
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
												<c:when test="${factor eq '合计'}">
												</c:when>
												<c:when test="${factor eq '月费用'}">
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
												<c:when test="${factor eq '合计'}">
											    	<bean:write name="oneExpenseDetail" property="price4a" />
												</c:when>
												<c:when test="${factor eq '月费用'}">
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
					<td colspan="22">&nbsp;&nbsp; <a href="javascript:exportList('${yearmonth}')">导出为Excel文件</a></td>
				</tr>
			</table>
		</c:if>
	</body>
</html>
