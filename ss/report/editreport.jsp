<%@include file="/common/header.jsp"%>
<html>
<head>
</head>
<body>
<template:titile value="编辑上传报告" />
<html:form action="/ReportAction?method=updateReport"  enctype="multipart/form-data">
<template:formTable namewidth="150" contentwidth="350">
<html:hidden property="id" name="report"/>
<html:hidden property="reporturl" name="report"/>
<template:formTr name="报告名称">
<html:text property="reportname" name="report" styleClass="inputtext" style="width:250;" maxlength="25" />
</template:formTr>
<template:formTr name="报告类型">
<apptag:setSelectOptions columnName1="typename" columnName2="code" tableName="reporttype" valueName="reporttypecoll"/>
<html:select property="reporttype" name="report" styleClass="inputtext" style="width:160">
  <html:options collection="reporttypecoll" property="value" labelProperty="label"/>
</html:select>
</template:formTr>
<template:formTr name="报告描述">
<html:textarea property="remark" name="report" cols="36" onkeyup="this.value=this.value.substr(0,250)" rows="3" style="width:250" styleClass="textarea" title="描述信息最长250个汉字"/>
</template:formTr>
<template:formTr name="附件">
	<logic:empty name="report"  property="reporturl">
		<apptag:Attachment state="edit" fileIdList=""></apptag:Attachment>
     </logic:empty>
	<logic:notEmpty name="report" property="reporturl">
	 	<bean:define id="temp" name="report" property="reporturl" type="java.lang.String"/>
		<apptag:Attachment state="edit" fileIdList="<%=temp %>"></apptag:Attachment>
	</logic:notEmpty>

</template:formTr>

<template:formSubmit>
	<td>
		<html:button property="action" styleClass="button" onclick="addRow()">添加附件</html:button>
	</td>
	<td>
		<html:submit styleClass="button" >提交</html:submit>
	</td>
	<td>
		<input type="button" value="返回" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
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
	}
</script>
</html>