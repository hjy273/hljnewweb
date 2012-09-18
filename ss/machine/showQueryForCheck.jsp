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
					var useridSelEle = document.getElementById('userid').options;//��ȡִ���˵�select
					var index = obj.options.selectedIndex;
					var conname = obj.options[index].text;
					useridSelEle.length = 0;//��������б�
					useridSelEle.add(new Option('===��ѡ��ִ����===','0'));
					if(params == 0) {
						return;
					}
					var url = 'MobileTaskAction.do?method=getUserByConId&conId=' + params;
					var myAjax = new Ajax.Request(url, {method:"post", onComplete:callback, asynchronous:true});
				}
				
				function callback(originalRequest) {
					var useridSelEle = document.getElementById('userid').options;//��ȡִ���˵�select
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
		<template:titile value="����Ѳ�������ѯ"/>
		<html:form action="MobileTaskAction.do?method=doQueryForCheck" styleId="queryForm">
		<template:formTable th1="��Ŀ" th2="��д" namewidth="150" contentwidth="300">
			<template:formTr name="����">
				<input type="hidden" value="1" name="reset_query">
				<select name="machinetype" style="width:245px;" class="inputtext">
						<option value="0">
							��ѡ������
						</option>
						<option value="1">
							���Ĳ�
						</option>
						<option value="2">
							�����SDH
						</option>
						<option value="3">
							�����΢��
						</option>
						<option value="4">
							�����FSO
						</option>
						<option value="5">
							�⽻ά��
						</option>
				</select>
			</template:formTr>
			
		<logic:equal value="1" name="depType">
			<template:formTr name="��ά��˾">
				<select class="inputtext" name="contractorid" style="width:245px;" id="contractorid" onchange="onConChange(this)">
						<option value="0">
							��ѡ���ά��˾
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
			
			<template:formTr name="ִ����">
					<select class="inputtext" name="userid" style="width:245px;" id="userid">
						<option value="0"></option>
					</select>
			</template:formTr>
		</logic:equal>
		
		<logic:equal value="2" name="depType">
			<template:formTr name="ִ����">
					<select class="inputtext" name="userid" style="width:245px;" id="userid">
						<option value="0">��ѡ��ִ����</option>
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
			
			<template:formTr name="�˲���">
					<select class="inputtext" name="checkuser" style="width:245px;" id="checkuser" onchange="setCheckUsernaem(this)">
						<option value="0">
							��ѡ��˲���
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
			
			<template:formTr name="��ʼ����">
				<input id="begtime" type="text" readonly="readonly"
						name="begtime" class="inputtext" style="width: 217" />
				<INPUT TYPE='BUTTON' VALUE='����' readonly="readonly" ID='btn'
						onclick="GetSelectDateTHIS('begtime')"
						STYLE="font: 'normal small-caps 6pt serif';">
			</template:formTr>
			
			<template:formTr name="��������">
				<input id="endtime" type="text" readonly="readonly"
						name="endtime" class="inputtext" style="width: 217" />
				<INPUT TYPE='BUTTON' VALUE='����' readonly="readonly" ID='btn'
						onclick="GetSelectDateTHIS('endtime')"
						STYLE="font: 'normal small-caps 6pt serif';">
			</template:formTr>
			
			<template:formSubmit>
				<td>
					<input type="hidden" value="4" name="state">
					<input type="submit" value="��ѯ" class="button"/>
					<input type="reset" value="����" class="button"/>
				</td>
			</template:formSubmit>
		</template:formTable>
		</html:form>
	</body>
</html>