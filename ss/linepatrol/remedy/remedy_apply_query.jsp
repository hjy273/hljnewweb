<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>��ѯ��������</title>
		<script type="text/javascript" src="./js/prototype.js"></script>
		<script type="text/javascript" src="./js/json2.js"></script>
		<script type="text/javascript" defer="defer"
			src="<%=request.getContextPath()%>/js/WdatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
		getSelectDateThis=function(strID) {
			document.all.item(strID).value = getPopDate(document.all.item(strID).value);
			return true;
		}
    	</script>
	</head>
	<body>
		<br>
		<template:titile value="��ѯ��������" />
		<html:form action="/remedy_apply.do?method=queryList"
			styleId="queryForm" method="post">
			<template:formTable namewidth="200" contentwidth="200">
				<html:hidden property="reset_query" value="1" />
				<html:hidden property="power" value="52004" />
				<template:formTr name="���">
					<html:text property="remedyCode" styleClass="inputtext"
						style="width:215;" maxlength="20" />
				</template:formTr>
				<template:formTr name="��Ŀ����">
					<html:text property="projectName" styleClass="inputtext"
						style="width:215;" maxlength="20" />
				</template:formTr>
				<template:formTr name="�����ܷ���">
					����
					<html:text property="totalFeeMin" styleClass="inputtext"
						style="width:65;" maxlength="20" />
					��
					<html:text property="totalFeeMax" styleClass="inputtext"
						style="width:65;" maxlength="20" />
					֮��
				</template:formTr>
				<template:formTr name="����ʱ��">
					����
					<html:text property="remedyDateMin" styleClass="inputtext"
						style="width:65;" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" />
					<!-- 
					<INPUT TYPE='BUTTON' VALUE='��' ID='btnRemedyDateMin'
						onclick="getSelectDateThis('remedyDateMin')"
						STYLE="font: 'normal small-caps 6pt serif';">
					 -->
					��
					<html:text property="remedyDataMax" styleClass="inputtext"
						style="width:65;" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" />
					<!-- 
					<INPUT TYPE='BUTTON' VALUE='��' ID='btnRemedyDateMax'
						onclick="getSelectDateThis('remedyDateMax')"
						STYLE="font: 'normal small-caps 6pt serif';">
					 -->
					֮��
				</template:formTr>
				<template:formTr name="����״̬">
					<select name="state" class="inputtext" style="width: 215;">
						<option value="">
							����
						</option>
						<logic:notEmpty name="status_list">
							<logic:iterate id="status" name="status_list">
								<option
									value="<bean:write property="status_id" name="status" />">
									<bean:write property="status_name" name="status" />
								</option>
							</logic:iterate>
						</logic:notEmpty>
					</select>
				</template:formTr>
				<template:formSubmit>
					<td>
						<html:submit styleClass="button">��ѯ</html:submit>
					</td>
					<td>
						<html:reset styleClass="button">����</html:reset>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
		<script type="text/javascript">
		</script>
	</body>
</html>
