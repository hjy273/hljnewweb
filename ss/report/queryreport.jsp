<%@include file="/common/header.jsp"%>
<script language="javascript" type="">

</script>
<br>
<template:titile value="��ѯ�ϴ�������Ϣ"/>
<html:form method="Post" action="/ReportAction?method=queryReport">
  <template:formTable contentwidth="200" namewidth="200">
    <template:formTr name="��������">
      <html:text property="reportname" styleClass="inputtext" style="width:160" maxlength="45"/>
    </template:formTr>
	<template:formTr name="��������">
		<apptag:setSelectOptions columnName1="typename" columnName2="code" tableName="reporttype" valueName="reporttypecoll"/>
		<html:select property="reporttype" styleClass="inputtext" style="width:160">
		<html:option value="">����</html:option>
  		<html:options collection="reporttypecoll" property="value" labelProperty="label"/>
		</html:select>
	</template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">��ѯ</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">ȡ��</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>

