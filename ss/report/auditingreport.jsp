<%@include file="/common/header.jsp"%>
<html>
<head>
</head>
<body>
<template:titile value="�����ϴ�����" />
<html:form action="/ReportAction?method=auditingReport"  enctype="multipart/form-data">
<template:formTable namewidth="150" contentwidth="350">
<html:hidden property="id" name="report"/>
<template:formTr name="��������">
<html:text property="reportname" name="report" styleClass="inputtext" style="border:0;background-color:transparent;width:250;" maxlength="25" />
</template:formTr>
<template:formTr name="��������">
<apptag:setSelectOptions columnName1="typename" columnName2="code" tableName="reporttype" valueName="reporttypecoll"/>
<html:select property="reporttype" name="report" styleClass="inputtext" disabled="true"  style="border:0;background-color:transparent;width:160">
  <html:options collection="reporttypecoll" property="value" labelProperty="label"/>
</html:select>
</template:formTr>
<template:formTr name="��������">
<bean:write property="remark" name="report"/>
</template:formTr>
<template:formTr name="����">
	<logic:empty name="report"  property="reporturl">
		<apptag:Attachment state="read" fileIdList=""></apptag:Attachment>
    </logic:empty>
	<logic:notEmpty name="report" property="reporturl">
		<bean:define id="temp" name="report" property="reporturl" type="java.lang.String"/>
		<apptag:Attachment state="read" fileIdList="<%=temp %>"></apptag:Attachment>
	</logic:notEmpty>
</template:formTr>
<template:formTr name="��˽��">
<html:select property="auditingResult" name="report" styleClass="inputtext" style="width:160;">
  <html:option value="ͨ��">ͨ��</html:option>
  <html:option value="δͨ��">δͨ��</html:option>
</html:select>
</template:formTr>
<template:formTr name="��ע">
<html:textarea property="auditingRemark" name="report" cols="36" onkeyup="this.value=this.value.substr(0,250)" rows="3" style="width:250" styleClass="textarea" title="������Ϣ�250������"/>
</template:formTr>
<template:formSubmit>
	
	<td>
		<html:submit styleClass="button" >���</html:submit>
	</td>
	<td>
		<input type="button" value="����" class="button"  onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')">
	</td>
</template:formSubmit>
</template:formTable>
</html:form>
</body>
</html>