<%@include file="/common/header.jsp"%>
<script language="javascript" type="">

</script>
<br>
<template:titile value="��ѯ��ά������Ϣ"/>
<html:form method="Post" action="/DatumExperienceAction?method=queryDatumExperience">
<template:formTable contentwidth="200" namewidth="200">
    <template:formTr name="�ĵ�����">
      <html:text property="documentname" styleClass="inputtext" style="width:160" maxlength="45"/>
    </template:formTr>
	<template:formTr name="�ĵ�����">
		<apptag:setSelectOptions columnName1="typename" columnName2="code" tableName="documenttype" valueName="documenttypecoll"  condition="category='3' "/>
	
		<html:select property="documenttype" styleClass="inputtext" style="width:160">
		<html:option value="">����</html:option>
  		<html:options collection="documenttypecoll" property="value" labelProperty="label"/>
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