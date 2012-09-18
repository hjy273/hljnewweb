<%@include file="/common/header.jsp"%>
<html>
<head>

</head>
<body>
<template:titile value="上传报告" />
<html:form action="/ReportAction?method=addReport" onsubmit="return validate(this);" enctype="multipart/form-data">
<template:formTable namewidth="150" contentwidth="350">
<template:formTr name="报告名称">
<html:text property="reportname" styleClass="inputtext" style="width:250;" maxlength="25" />
</template:formTr>
<template:formTr name="报告类型">
<apptag:setSelectOptions columnName1="typename" columnName2="code" tableName="reporttype" valueName="reporttypecoll"/>
<html:select property="reporttype" styleClass="inputtext" style="width:160">
  <html:options collection="reporttypecoll" property="value" labelProperty="label"/>
</html:select>
</template:formTr>
<template:formTr name="报告描述">
<html:textarea property="remark" cols="36" onkeyup="this.value=this.value.substr(0,250)" rows="3" style="width:250" styleClass="textarea" title="描述信息最长250个汉字"/>
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
</template:formSubmit>
</template:formTable>
</html:form>
</body>
<script type="text/javascript">
function validate(theForm){
		if(theForm.reportname.value==""){
			alert("报告名称不能为空！");
			return false;
		}
		if(fileNum == 0 ){
			alert("附件不能为空！");
			return false;
		}
	}
</script>
</html>