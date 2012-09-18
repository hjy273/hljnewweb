<%@include file="/common/header.jsp"%>

<html>
	<head>
		<script type="text/javascript">
			function GetSelectDateTHIS(strID) {
				   	document.all.item(strID).value = getPopDate(document.all.item(strID).value);
				   	return ;
				}
		</script>
	</head>
	
	<body>
		<br>
		<template:titile value="任务回复查询"/>
		<html:form action="MobileTaskAction.do?method=doQuery" styleId="queryForm">
			<template:formTable th1="项目" th2="填写" namewidth="150"
					contentwidth="300">
				<template:formTr name="类型">
						<input type="hidden" value="1" name="reset_query">
						<select name="machinetype" style="width:245px;" class="inputtext">
							<option value="0">
								请选择类型
							</option>
							<option value="1">
								核心层
							</option>
							<option value="2">
								接入层SDH
							</option>
							<option value="3">
								接入层微波
							</option>
							<option value="4">
								接入层FSO
							</option>
							<option value="5">
								光交维护
							</option>
						</select>
					</template:formTr>
		
		
					<template:formTr name="执行人">
						<select class="inputtext" name="userid" style="width:245px;"
							id="userid">
							<option value="0">
								请选择执行人
							</option>
							<logic:notEmpty name="executeList" scope="request">
								<logic:iterate id="executeListId" name="executeList"
									scope="request">
									<option
										value="<bean:write name="executeListId" property="userid" />">
										<bean:write name="executeListId" property="username" />
									</option>
								</logic:iterate>
							</logic:notEmpty>
						</select>
					</template:formTr>
		
					<%--<template:formTr name="派单状态">
						<select class="inputtext" name="state" style="width:245px;"
							id="state">
							<option value="0">
								请选择派单状态
							</option>
							<option value="1">
								待签收
							</option>
							
							<option value="2">
								已签收
							</option>
							<option value="3">
								已回复
							</option>
							<option value="4">已核查</option>
						
						</select>
					</template:formTr>--%>
		
					<template:formTr name="核查人">
						<select class="inputtext" name="checkuser" style="width:245px;"
							id="checkuser" onchange="setCheckUsernaem(this)">
							<option value="0">
								请选择核查人
							</option>
							<logic:notEmpty name="mobileList" scope="request">
								<logic:iterate id="mobileListId" name="mobileList" scope="request">
									<option
										value="<bean:write name="mobileListId" property="userid"/>">
										<bean:write name="mobileListId" property="username" />
									</option>
								</logic:iterate>
							</logic:notEmpty>
						</select>
					</template:formTr>
		
					<template:formTr name="开始日期">
						<input id="begtime" type="text" readonly="readonly" name="begtime"
							class="inputtext" style="width: 217" />
						<INPUT TYPE='BUTTON' VALUE='日期' readonly="readonly" ID='btn'
							onclick="GetSelectDateTHIS('begtime')"
							STYLE="font: 'normal small-caps 6pt serif';">
					</template:formTr>
		
					<template:formTr name="结束日期">
						<input id="endtime" type="text" readonly="readonly" name="endtime"
							class="inputtext" style="width: 217" />
						<INPUT TYPE='BUTTON' VALUE='日期' readonly="readonly" ID='btn'
							onclick="GetSelectDateTHIS('endtime')"
							STYLE="font: 'normal small-caps 6pt serif';">
					</template:formTr>
		
					<template:formSubmit>
						<td>
							<input type="hidden" value="2" name="query">
							<input type="submit" value="查询" class="button" onclick="" />
							<input type="reset" value="重置" class="button" />
						</td>
					</template:formSubmit>
				
				
			</template:formTable>
		</html:form>
	</body>
	
</html>