<%@include file="/common/header.jsp"%>
<html>
<head>

</head>
<body>
<template:titile value="��ӹ�������" />
<html:form action="/DatumCriterionAction?method=addDatumCriterion"  onsubmit="return validate(this);" enctype="multipart/form-data">
<template:formTable namewidth="150" contentwidth="350">
<template:formTr name="������������">
<html:text property="documentname" styleClass="inputtext" style="width:250;" maxlength="25" />
</template:formTr>
<template:formTr name="������������">
<apptag:setSelectOptions columnName1="typename" columnName2="code" tableName="documenttype" valueName="documenttypecoll"  condition="category='2' "/>
<html:select property="documenttype" styleClass="inputtext" style="width:160">
  <html:options collection="documenttypecoll" property="value" labelProperty="label"/>
</html:select>
</template:formTr>
<template:formTr name="������������">
<html:textarea property="description" cols="36" onkeyup="this.value=this.value.substr(0,250)" rows="3" style="width:250" styleClass="inputTextSmall" title="������Ϣ�250������"/>
</template:formTr>
<template:formTr name="��Ч��">
<html:text property="validatetime" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" style="width:250;" maxlength="26" />
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
	<td>
		<html:button property="action" styleClass="button" onclick="addGoBack()">����</html:button>
	</td>
</template:formSubmit>
</template:formTable>
</html:form>
</body>
<script type="text/javascript">
function validate(theForm){
		if(theForm.documentname.value==""){
			alert("�����������Ʋ���Ϊ�գ�");
			return false;
		}
		if(fileNum == 0 ){
			alert("��������Ϊ�գ�");
			return false;
		}
	}
</script>
</html>