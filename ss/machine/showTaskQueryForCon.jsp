<%@include file="/common/header.jsp"%>

<html>
	<head>
		<title></title>
		<script type="text/javascript">
			function GetSelectDateTHIS(strID) {
			   	document.all.item(strID).value = getPopDate(document.all.item(strID).value);
			   	return ;
			}
			
		</script>
	</head>

	<body>
		<br>
		<template:titile value="�����ѯ����" />
		<html:form action="MobileTaskAction.do?method=doQuery" styleId="queryForm">
			<template:formTable th1="��Ŀ" th2="��д" namewidth="150"
				contentwidth="300">
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
	
	
				<template:formTr name="ִ����">
					<select class="inputtext" name="userid" style="width:245px;"
						id="userid">
						<option value="0">
							��ѡ��ִ����
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
	
				<template:formTr name="�ɵ�״̬">
					<select class="inputtext" name="state" style="width:245px;"
						id="state">
						<option value="0">
							��ѡ���ɵ�״̬
						</option>
						<option value="1">
							��ǩ��
						</option>
						<option value="2">
							��ǩ��
						</option>
						<%--<option value="3">
							�ѻظ�
						</option>
						<option value="4">�Ѻ˲�</option>
					--%>
					</select>
				</template:formTr>
	
				<template:formTr name="�˲���">
					<select class="inputtext" name="checkuser" style="width:245px;"
						id="checkuser" onchange="setCheckUsernaem(this)">
						<option value="0">
							��ѡ��˲���
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
	
				<template:formTr name="ִ�п�ʼ����">
					<input id="begtime" type="text" readonly="readonly" name="begtime"
						class="inputtext" style="width: 217" />
					<INPUT TYPE='BUTTON' VALUE='����' readonly="readonly" ID='btn'
						onclick="GetSelectDateTHIS('begtime')"
						STYLE="font: 'normal small-caps 6pt serif';">
				</template:formTr>
	
				<template:formTr name="ִ�н�������">
					<input id="endtime" type="text" readonly="readonly" name="endtime"
						class="inputtext" style="width: 217" />
					<INPUT TYPE='BUTTON' VALUE='����' readonly="readonly" ID='btn'
						onclick="GetSelectDateTHIS('endtime')"
						STYLE="font: 'normal small-caps 6pt serif';">
				</template:formTr>
	
				<template:formSubmit>
					<td>
						<input type="hidden" value="1" name="query">
						<input type="submit" value="��ѯ" class="button" onclick="" />
						<input type="reset" value="����" class="button" />
					</td>
				</template:formSubmit>
	
	
			</template:formTable>
		</html:form>
	</body>

</html>
