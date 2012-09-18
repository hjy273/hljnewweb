<%@include file="/common/header.jsp"%>

<html>
	<head>
		<title></title>
			<script type="text/javascript" src="./js/prototype.js"></script>
			<script type="text/javascript">
				function GetSelectDateTHIS(strID) {
				   	document.all.item(strID).value = getPopDate(document.all.item(strID).value);
				   	return ;
				}
			
				//ajax
				function onConChange(obj) {
					var params = obj.value;
					var useridSelEle = document.getElementById('userid').options;//获取执行人的select
					var index = obj.options.selectedIndex;
					var conname = obj.options[index].text;
					useridSelEle.length = 0;//清空下拉列表
					useridSelEle.add(new Option('===请选择执行人===','0'));
					if(params == 0) {
						return;
					}
					var url = 'MobileTaskAction.do?method=getUserByConId&conId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callback, asynchronous:true});
				}
				
				function callback(originalRequest) {
					var useridSelEle = document.getElementById('userid').options;//获取执行人的select
					var rst = originalRequest.responseText;
					var userarr = eval('('+rst+')');
					for(var i=0; i<userarr.length; i++) {
						useridSelEle.add(new Option(userarr[i].username,userarr[i].userid));
					}
				}
		</script>
	</head>
	
	<body>
		<br>
		<template:titile value="机房巡检任务查询"/>
		<html:form action="MobileTaskAction.do?method=doQueryForCheck" styleId="queryForm">
		<template:formTable th1="项目" th2="填写" namewidth="150" contentwidth="300">
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
			
		<logic:equal value="1" name="depType">
			<template:formTr name="代维公司">
				<select class="inputtext" name="contractorid" style="width:245px;" id="contractorid" onchange="onConChange(this)">
						<option value="0">
							请选择代维公司
						</option>
						<logic:notEmpty name="conDeptList" scope="request">
							<logic:iterate id="conDeptListid" name="conDeptList"
								scope="request">
								<option
									value="<bean:write name="conDeptListid" property="contractorID"/>">
									<bean:write name="conDeptListid" property="contractorName" />
								</option>
							</logic:iterate>
						</logic:notEmpty>
				</select>
			</template:formTr>
			
			<template:formTr name="执行人">
					<select class="inputtext" name="userid" style="width:245px;" id="userid">
						<option value="0"></option>
					</select>
			</template:formTr>
		</logic:equal>
		
		<logic:equal value="2" name="depType">
			<template:formTr name="执行人">
					<select class="inputtext" name="userid" style="width:245px;" id="userid">
						<option value="0">请选择执行人</option>
						<logic:notEmpty name="userList" scope="request">
							<logic:iterate id="userListId" name="userList">
								<option
									value="<bean:write name="userListId" property="userid"/>">
									<bean:write name="userListId" property="username" />
								</option>
							</logic:iterate>
						</logic:notEmpty>
					</select>
			</template:formTr>
		</logic:equal>
			
			<template:formTr name="核查人">
					<select class="inputtext" name="checkuser" style="width:245px;" id="checkuser" onchange="setCheckUsernaem(this)">
						<option value="0">
							请选择核查人
						</option>
						<logic:notEmpty name="mobileList" scope="request">
							<logic:iterate id="mobileListId" name="mobileList"
								scope="request">
								<option
									value="<bean:write name="mobileListId" property="userid"/>">
									<bean:write name="mobileListId" property="username" />
								</option>
							</logic:iterate>
						</logic:notEmpty>
					</select>
			</template:formTr>
			
			<template:formTr name="开始日期">
				<input id="begtime" type="text" readonly="readonly"
						name="begtime" class="inputtext" style="width: 217" />
				<INPUT TYPE='BUTTON' VALUE='日期' readonly="readonly" ID='btn'
						onclick="GetSelectDateTHIS('begtime')"
						STYLE="font: 'normal small-caps 6pt serif';">
			</template:formTr>
			
			<template:formTr name="结束日期">
				<input id="endtime" type="text" readonly="readonly"
						name="endtime" class="inputtext" style="width: 217" />
				<INPUT TYPE='BUTTON' VALUE='日期' readonly="readonly" ID='btn'
						onclick="GetSelectDateTHIS('endtime')"
						STYLE="font: 'normal small-caps 6pt serif';">
			</template:formTr>
			
			<template:formSubmit>
				<td>
					<input type="hidden" value="4" name="state">
					<input type="submit" value="查询" class="button"/>
					<input type="reset" value="重置" class="button"/>
				</td>
			</template:formSubmit>
		</template:formTable>
		</html:form>
	</body>
</html>