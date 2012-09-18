<%@include file="/common/header.jsp"%>
<html>
<head>

</head>
<body>
<template:titile value="添加工程资料" />
<html:form action="/DatumCriterionAction?method=addDatumCriterion"  onsubmit="return validate(this);" enctype="multipart/form-data">
<template:formTable namewidth="150" contentwidth="350">
<template:formTr name="工程资料名称">
<html:text property="documentname" styleClass="inputtext" style="width:250;" maxlength="25" />
</template:formTr>
<template:formTr name="工程资料类型">
<apptag:setSelectOptions columnName1="typename" columnName2="code" tableName="documenttype" valueName="documenttypecoll"  condition="category='2' "/>
<html:select property="documenttype" styleClass="inputtext" style="width:160">
  <html:options collection="documenttypecoll" property="value" labelProperty="label"/>
</html:select>
</template:formTr>
<template:formTr name="工程资料描述">
<html:textarea property="description" cols="36" onkeyup="this.value=this.value.substr(0,250)" rows="3" style="width:250" styleClass="inputTextSmall" title="描述信息最长250个汉字"/>
</template:formTr>
<template:formTr name="有效期">
<html:text property="validatetime" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" style="width:250;" maxlength="26" />
</template:formTr>
<template:formTr name="附件">
<apptag:Attachment state="add" fileIdList=""></apptag:Attachment>
</template:formTr>

<template:formSubmit>
	<td>
		<html:button property="action" styleClass="button" onclick="addRow()">添加附件</html:button>
	</td>
	<td>
		<html:submit styleClass="button" >提交</html:submit>
	</td>
	<td>
		<html:reset styleClass="button">取消	</html:reset>
	</td>
	<td>
		<html:button property="action" styleClass="button" onclick="addGoBack()">返回</html:button>
	</td>
</template:formSubmit>
</template:formTable>
</html:form>
</body>
<script type="text/javascript">
function validate(theForm){
		if(theForm.documentname.value==""){
			alert("工程资料名称不能为空！");
			return false;
		}
		if(fileNum == 0 ){
			alert("附件不能为空！");
			return false;
		}
	}
</script>
</html>