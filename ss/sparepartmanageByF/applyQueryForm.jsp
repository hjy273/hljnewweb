<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript">
		 function GetSelectDateTHIS(strID) {
	    	document.all.item(strID).value = getPopDate(document.all.item(strID).value);
	    	return ;
		 }
	</script>
	<body>
		<br>
		<template:titile value="��������ѯ����������Ϣ" />
		<html:form action="/SparepartApplyAction.do?method=doQueryForApply"
			styleId="queryForm">
			<template:formTable namewidth="200" contentwidth="200">
				<html:hidden property="reset_query" value="1" />

				<template:formTr name="�������к�">
					<input name="serial_number" class="inputtext" type="text"
						style="width: 250;" maxlength="25" />
				</template:formTr>
				
				<template:formTr name="��˽��:">
					<select name="auditing_result" class="inputtext"
						style="width: 250;">
						<option value="">
							ȫ��
						</option>
						<option value="03">
							�����
						</option>
						<option value="01">
							���ͨ��
						</option>
						<option value="02">
							��˲�ͨ��
						</option>
					</select>
				</template:formTr>

				<template:formTr name="����ʹ��λ��">
					<input name="storage_position" class="inputtext" type="text"
						style="width: 250;" maxlength="25" />
				</template:formTr>

				<template:formTr name="���뿪ʼʱ��">
					<input id="begin" type="text" readonly="readonly" name="begintime"
						class="inputtext" style="width: 220" />
					<INPUT TYPE='BUTTON' VALUE='����' readonly="readonly" ID='btn'
						onclick="GetSelectDateTHIS('begin')"
						STYLE="font: 'normal small-caps 6pt serif';">
				</template:formTr>
				<template:formTr name="�������ʱ��">
					<input id="end" type="text" name="endtime" readonly="readonly"
						class="inputtext" style="width: 220" />
					<INPUT TYPE='BUTTON' VALUE='����' ID='btn'
						onclick="GetSelectDateTHIS('end')"
						STYLE="font: 'normal small-caps 6pt serif';">
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:submit property="action" styleClass="button">��ѯ</html:submit>
					</td>
					<td>
						<html:reset property="action" styleClass="button">ȡ��</html:reset>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
