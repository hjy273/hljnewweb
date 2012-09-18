<%@include file="/common/header.jsp"%>
<html>
<head>
</head>
<body>
<template:titile value="审批上传报告" />
<html:form action="/ReportAction?method=auditingReport"  enctype="multipart/form-data">
<template:formTable namewidth="150" contentwidth="350">
<html:hidden property="id" name="report"/>
<template:formTr name="报告名称">
<html:text property="reportname" name="report" styleClass="inputtext" style="border:0;background-color:transparent;width:250;" maxlength="25" />
</template:formTr>
<template:formTr name="报告类型">
<apptag:setSelectOptions columnName1="typename" columnName2="code" tableName="reporttype" valueName="reporttypecoll"/>
<html:select property="reporttype" name="report" styleClass="inputtext" disabled="true"  style="border:0;background-color:transparent;width:160">
  <html:options collection="reporttypecoll" property="value" labelProperty="label"/>
</html:select>
</template:formTr>
<template:formTr name="报告描述">
<bean:write property="remark" name="report"/>
</template:formTr>
<template:formTr name="附件">
	<logic:empty name="report"  property="reporturl">
		<apptag:Attachment state="read" fileIdList=""></apptag:Attachment>
    </logic:empty>
	<logic:notEmpty name="report" property="reporturl">
		<bean:define id="temp" name="report" property="reporturl" type="java.lang.String"/>
		<apptag:Attachment state="read" fileIdList="<%=temp %>"></apptag:Attachment>
	</logic:notEmpty>
</template:formTr>
<template:formTr name="审核结果">
<html:select property="auditingResult" name="report" styleClass="inputtext" style="width:160;">
  <html:option value="通过">通过</html:option>
  <html:option value="未通过">未通过</html:option>
</html:select>
</template:formTr>
<template:formTr name="批注">
<html:textarea property="auditingRemark" name="report" cols="36" onkeyup="this.value=this.value.substr(0,250)" rows="3" style="width:250" styleClass="textarea" title="描述信息最长250个汉字"/>
</template:formTr>
<template:formSubmit>
	
	<td>
		<html:submit styleClass="button" >审核</html:submit>
	</td>
	<td>
		<input type="button" value="返回" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
	</td>
</template:formSubmit>
</template:formTable>
</html:form>
</body>
</html>