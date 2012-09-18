<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
		<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
		<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
		<title>维护费用结算确认函</title>
		<script type="text/javascript">
		calculateSum=function(value){
			var form1=document.forms[0];
			var moneySum=0;
			var decMoney=0;
			var finalMoney=0;
			if(form1.moneySum.value!=""){
				moneySum=parseFloat(form1.moneySum.value);
			}
			if(value!=""){
				decMoney=parseFloat(value);
			}
			if(decMoney>=moneySum){
				alert("扣减金额过大，请重新输入！");
				form1.deductionPrice.focus();
				return;
			}
			finalMoney=moneySum-decMoney;
			finalMoney=parseInt(finalMoney*100+0.5)/100;
			form1.balancePrice.value=finalMoney;
		}
		
		   //查看月费用，扣减费用以及说明
			function viewMonthExpense(id){
			 var u="${ctx}/expensesAction.do?method=viewMonthExpenseForm&id="+id;
			  win = new Ext.Window({
			  layout : 'fit',
			  width: document.body.clientWidth * 0.45 , 
			  height:260, 
			  resizable:true,
			  closeAction : 'close', 
			  modal:true,
			// autoLoad:{url: u,scripts:true}, 
			  html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src='+u+' />',
			  plain: true,
			  title:"月费用扣减" 
			 });
			  win.show(Ext.getBody());
			}
			  
			 function close(){
			  	win.close();
			 }
		
		
			exportList=function(beginMonth,endMonth,id){
				var contractorid = document.getElementById("contractorId").value;
				var url="${ctx}/expensesAction.do?method=exportSettlementExpense&beginMonth="
				+beginMonth+"&endMonth="+endMonth+"&contractorid="+contractorid+"&id="+id;
				self.location.replace(url);
			}
			
			exportPipeExpense=function(beginMonth,endMonth,id){
				var contractorid = document.getElementById("contractorId").value;
				var url="${ctx}/expensesAction.do?method=exportPipeSettlementExpense&beginMonth="
				+beginMonth+"&endMonth="+endMonth+"&contractorid="+contractorid+"&id="+id;
				self.location.replace(url);
			}
		</script>
	</head>

	<body>
	
	<c:if test="${not empty affrimPipeExpense}">
	<input name="contractorId" value="${contractorId }" type="hidden" />
			<table width="95%" border="1" align="center" bgcolor="#FFFFFF"
					cellpadding="0" cellspacing="0" bordercolor="#000"
					style="border-collapse: collapse">
					<tr>
						<td colspan="8" align="center">
							<font size="2px" style="font-weight: bold"> 
						${beginMonth}到${endMonth }${contractor.contractorName}已付费用
							</font>
						</td>
					</tr>
				<tr bgcolor="#cccccc" height="30px">
					<td>
						<font size="2px" style="font-weight: bold">时间</font>
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
						<font size="2px" style="font-weight: bold">管道长度(KM)</font>
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
				<logic:iterate id="pipeExpense" name="affrimPipeExpense" >
					<tr>
						<td><bean:write name="pipeExpense" property="key"/></td>
						<bean:define id="pipemonth" name="pipeExpense" property="value"></bean:define>
						<bean:define id="monthprice" name="pipemonth" property="month_price"></bean:define>
						<bean:define id="monthID" name="pipemonth" property="id"></bean:define>
							<td>
							${monthprice}
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
					<td align="right" colspan="2">
						维护费用（元）：
					</td>
					<td colspan="6">
						${monthMoney}
					</td>
				</tr>
				<tr>
					<td align="right" colspan="2">
							扣减（元）：
					</td>
					<td colspan="6">
							${affrim.deductionPrice}
					</td>
				</tr>
				<tr>
					<td align="right" colspan="2">
							结算费用（元）：
					</td>
					<td colspan="6">
							${affrim.balancePrice}
					</td>
				</tr>
				<tr>
					<td align="right" colspan="2">
							扣减原因：
					</td>
					<td colspan="6" style="word-break:break-all;width:500px;" >
							${affrim.remark}
					</td>
				</tr>
				<tr>
					<td colspan="8">&nbsp;&nbsp; 
						<a href="javascript:exportPipeExpense('${beginMonth}','${endMonth}','${id}')">导出为Excel文件</a>
					</td>
				</tr>
				<tr>
					<td colspan="8" style="text-align:center;">
								<html:button styleClass="button" property="action" onclick="javascript:history.back();">返回</html:button>
					</td>
				</tr>
			</table>
		
		</c:if>
	
	
	
		<c:if test="${not empty affrimExpense}">
			<logic:iterate id="oneContractorExpensesMap" name="affrimExpense">
				<table width="95%" border="1" align="center" bgcolor="#FFFFFF"
					cellpadding="0" cellspacing="0" bordercolor="#000"
					style="border-collapse: collapse">
					<tr>
						<td colspan="7" align="center">
							<font size="2px" style="font-weight: bold"> 
							${beginMonth}到${endMonth }已付费用
							</font>
						</td>
					</tr>
					<!-- Map<String,Map<String,List>> -->
					<!-- <代维公司名称，<年月，费用列表>> -->
					<tr>
						<td rowspan="2" align="center">
							时间
						</td>
						<td>
							公司名称
						</td>
						<td colspan="5">
							${oneContractorExpensesMap.key }
							<input type="hidden" value="${oneContractorExpensesMap.key}" name="contraName" id="contraName"/>
						</td>
					</tr>
					<tr>
						<td>
							光缆级别
						</td>
						<td>
							光缆长度（km）
						</td>
						<td>
							光缆数量（条）
						</td>
						<td>
							长度合计（km）
						</td>
						<td>
							数量合计（条）
						</td>
						<td>
							月费用合计（元）
						</td>
					</tr>
					<logic:notEmpty name="oneContractorExpensesMap" property="value">
						<logic:iterate id="oneMonthExpensesMap"
							name="oneContractorExpensesMap" property="value" indexId="index">
							<logic:notEmpty name="oneMonthExpensesMap" property="value">
								<bean:size id="size" name="oneMonthExpensesMap" property="value" />
							</logic:notEmpty>
							<logic:empty name="oneMonthExpensesMap" property="value">
								<bean:define id="size" value="1"></bean:define>
							</logic:empty>
							<tr>
								<td rowspan="${size }"  align="center">
									<bean:write name="oneMonthExpensesMap" property="key" />
								</td>
								<logic:notEmpty name="oneMonthExpensesMap" property="value">
									<logic:iterate id="oneExpensesDetail"
										name="oneMonthExpensesMap" property="value" length="1">
										<td>
											<bean:write name="oneExpensesDetail" property="cable_type"/>
										</td>
										<td>
											<logic:equal value="1" name="oneExpensesDetail" property="cable_level">
												<bean:write name="oneExpensesDetail" property="len1"/>
											</logic:equal>
											<logic:equal value="2" name="oneExpensesDetail" property="cable_level">
												<bean:write name="oneExpensesDetail" property="len2"/>
											</logic:equal>
											<logic:equal value="3" name="oneExpensesDetail" property="cable_level">
												<bean:write name="oneExpensesDetail" property="len3"/>
											</logic:equal>
											<logic:equal value="4" name="oneExpensesDetail" property="cable_level">
												<bean:write name="oneExpensesDetail" property="len4"/>
											</logic:equal>
											<logic:equal value="4A" name="oneExpensesDetail" property="cable_level">
												<bean:write name="oneExpensesDetail" property="len4a"/>
											</logic:equal>
										</td>
										<td>
											<logic:equal value="1" name="oneExpensesDetail" property="cable_level">
												<bean:write name="oneExpensesDetail" property="num1"/>
											</logic:equal>
											<logic:equal value="2" name="oneExpensesDetail" property="cable_level">
												<bean:write name="oneExpensesDetail" property="num2"/>
											</logic:equal>
											<logic:equal value="3" name="oneExpensesDetail" property="cable_level">
												<bean:write name="oneExpensesDetail" property="num3"/>
											</logic:equal>
											<logic:equal value="4" name="oneExpensesDetail" property="cable_level">
												<bean:write name="oneExpensesDetail" property="num4"/>
											</logic:equal>
											<logic:equal value="4A" name="oneExpensesDetail" property="cable_level">
												<bean:write name="oneExpensesDetail" property="num4a"/>
											</logic:equal>
										</td>
									</logic:iterate>
								</logic:notEmpty>
								<logic:empty name="oneMonthExpensesMap" property="value">
									<td>
										无
									</td>
									<td>
										无
									</td>
									<td>
										无
									</td>
								</logic:empty>
								<logic:notEmpty name="oneMonthExpensesMap" property="value">
									<logic:iterate id="oneExpenses" name="oneMonthExpensesMap"
										property="value" length="1">
										<td rowspan="${size }">
											<bean:write name="oneExpenses" property="cable_length" />
										</td>
										<td rowspan="${size }">
											<bean:write name="oneExpenses" property="cable_num" />
										</td>
										<td rowspan="${size }">
											<bean:write name="oneExpenses" property="rectify_money" />&nbsp;&nbsp;
											<bean:define id="monthid" name="oneExpenses" property="id" ></bean:define>
											<a href="javascript:viewMonthExpense('${monthid}')">
												扣减
											</a>
										</td>
									</logic:iterate>
								</logic:notEmpty>
								<logic:empty name="oneMonthExpensesMap" property="value">
									<td rowspan="${size }">
										0
									</td>
									<td rowspan="${size }">
										0
									</td>
									<td rowspan="${size }">
										0
									</td>
								</logic:empty>
							</tr>
							<logic:notEmpty name="oneMonthExpensesMap" property="value">
								<logic:iterate id="oneExpensesDetail" name="oneMonthExpensesMap"
									property="value" offset="1">
									<tr>
										<td>
											<bean:write name="oneExpensesDetail" property="cable_type"/>
										</td>
										<td>
											<logic:equal value="1" name="oneExpensesDetail" property="cable_level">
												<bean:write name="oneExpensesDetail" property="len1"/>
											</logic:equal>
											<logic:equal value="2" name="oneExpensesDetail" property="cable_level">
												<bean:write name="oneExpensesDetail" property="len2"/>
											</logic:equal>
											<logic:equal value="3" name="oneExpensesDetail" property="cable_level">
												<bean:write name="oneExpensesDetail" property="len3"/>
											</logic:equal>
											<logic:equal value="4" name="oneExpensesDetail" property="cable_level">
												<bean:write name="oneExpensesDetail" property="len4"/>
											</logic:equal>
											<logic:equal value="4A" name="oneExpensesDetail" property="cable_level">
												<bean:write name="oneExpensesDetail" property="len4a"/>
											</logic:equal>
										</td>
										<td>
											<logic:equal value="1" name="oneExpensesDetail" property="cable_level">
												<bean:write name="oneExpensesDetail" property="num1"/>
											</logic:equal>
											<logic:equal value="2" name="oneExpensesDetail" property="cable_level">
												<bean:write name="oneExpensesDetail" property="num2"/>
											</logic:equal>
											<logic:equal value="3" name="oneExpensesDetail" property="cable_level">
												<bean:write name="oneExpensesDetail" property="num3"/>
											</logic:equal>
											<logic:equal value="4" name="oneExpensesDetail" property="cable_level">
												<bean:write name="oneExpensesDetail" property="num4"/>
											</logic:equal>
											<logic:equal value="4A" name="oneExpensesDetail" property="cable_level">
												<bean:write name="oneExpensesDetail" property="num4a"/>
											</logic:equal>
										</td>
									</tr>
								</logic:iterate>
							</logic:notEmpty>
						</logic:iterate>
					</logic:notEmpty>
					<html:form
						action="/expensesAffirmAction.do?method=saveSettlementEexpenses"
						method="post" onsubmit="" styleId="expenseform">
						<input name="contractorId" value="${contractorId }" type="hidden" />
						<input name="budgetId" value="${budgetId }" type="hidden" />
						<input name="startMonth" value="${beginMonth }" type="hidden" />
						<input name="endMonth" value="${endMonth }" type="hidden" />
						<input name="moneySum" value="${moneySum }" type="hidden" />
						<tr>
							<td align="right" colspan="2">
								维护费用（元）：
							</td>
							<td colspan="5">
								${monthMoney}
							</td>
						</tr>
						<tr>
							<td align="right" colspan="2">
								扣减（元）：
							</td>
							<td colspan="5">
								${affrim.deductionPrice}
							</td>
						</tr>
						<tr>
							<td align="right" colspan="2">
								结算费用（元）：
							</td>
							<td colspan="5">
								${affrim.balancePrice}
							</td>
						</tr>
						<tr>
							<td align="right" colspan="2">
								扣减原因：
							</td>
							<td colspan="5" style="word-break:break-all;width:500px;" >
								${affrim.remark}
							</td>
						</tr>
						<tr>
							<td colspan="7">&nbsp;&nbsp;
							 	<a href="javascript:exportList('${beginMonth}','${endMonth}','${id}')">导出为Excel文件</a>
							</td>
						</tr>
						<tr>
							<td colspan="7" style="text-align:center;">
								<html:button styleClass="button" property="action" onclick="javascript:history.back();">返回</html:button>
							</td>
						</tr>
					</html:form>
					<tr>
				</tr>
				</table>
			</logic:iterate>
		</c:if>
	</body>
</html>
