<%@include file="/common/header.jsp"%>
<html>
<head>

</head>
<body>
<template:titile value="�ϴ�����" />
<html:form action="/ReportAction?method=addReport" onsubmit="return validate(this);" enctype="multipart/form-data">
<template:formTable namewidth="150" contentwidth="350">
<template:formTr name="��������">
<html:text property="reportname" styleClass="inputtext" style="width:250;" maxlength="25" />
</template:formTr>
<template:formTr name="��������">
<apptag:setSelectOptions columnName1="typename" columnName2="code" tableName="reporttype" valueName="reporttypecoll"/>
<html:select property="reporttype" styleClass="inputtext" style="width:160">
  <html:options collection="reporttypecoll" property="value" labelProperty="label"/>
</html:select>
</template:formTr>
<template:formTr name="��������">
<html:textarea property="remark" cols="36" onkeyup="this.value=this.value.substr(0,250)" rows="3" style="width:250" styleClass="textarea" title="������Ϣ�250������"/>
</template:formTr>
<template:formTr name="����">
<apptag:Attachment state="add" fileIdList=""></apptag:Attachment>
</template:formTr>

<template:formSubmit>
	<td>
		<html:button property="action" styleClass="button" onclick="addRow()">��Ӹ���</html:button>
	</td>
	<td>
		<html:submit styleClass="button" >�ύ</html:submit>
	</td>
	<td>
		<html:reset styleClass="button">ȡ��	</html:reset>
	</td>
</template:formSubmit>
</template:formTable>
</html:form>
</body>
<script type="text/javascript">
function validate(theForm){
		if(theForm.reportname.value==""){
			alert("�������Ʋ���Ϊ�գ�");
			return false;
		}
		if(fileNum == 0 ){
			alert("��������Ϊ�գ�");
			return false;
		}
	}
</script>
</html>