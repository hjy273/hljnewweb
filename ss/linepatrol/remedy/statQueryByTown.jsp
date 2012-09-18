<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript" src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
	<link type="text/css" rel="stylesheet"
		href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
	<head>
		<script language="javascript" type="">
       			function addGoBack(){
   			location.href = "${ctx}/stataction.do?method=loadQueryForm";
    			return true;
			}
			function checkForm(){
				var month = document.getElementById("month");
				if(month.value == ""){
					alert("��ѡ���·�!");
					month.focus();
					return false;
				}
				return true;
			}
		</script>
	</head>
<body>
<template:titile value="������ͳ��" />
<html:form method="Post" action="/statRemedyAction.do?method=statRemedyByTown" onsubmit="return checkForm();">
	<template:formTable contentwidth="200" namewidth="200">
		<template:formTr name="����">
			<select name="townId" id="townId" class="inputtext"
				style="width: 200px">
				<option value="0">
					����
				</option>
				<logic:present name="towns">
					<logic:iterate id="town" name="towns">
						<option value="<bean:write name='town' property='id'/>">
							<bean:write name="town" property="town" />
						</option>
					</logic:iterate>
				</logic:present>
			</select>
		</template:formTr>
		<template:formTr name="�·�<font color='red'>*</font>">
			<input name="month" class="inputtext" style="width: 200"
				onfocus="WdatePicker({dateFmt:'yyyy-MM'})" readonly/>
		</template:formTr>

		<template:formSubmit>
			<td>
				<input type="submit" class="button" name="sub" value="��ѯ">
			<td>
				<html:reset styleClass="button">����</html:reset>
			</td>
		</template:formSubmit>

	</template:formTable>
</html:form>
</body>