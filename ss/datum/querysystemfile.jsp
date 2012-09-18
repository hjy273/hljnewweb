<%@include file="/common/header.jsp"%>
<script language="javascript" type="">

</script>
<br>
<template:titile value="查询代维制度信息"/>
<html:form method="Post" action="/DatumSystemAction?method=queryDatumSystem">
  <template:formTable contentwidth="200" namewidth="200">
    <template:formTr name="文档名称">
      <html:text property="documentname" styleClass="inputtext" style="width:160" maxlength="45"/>
    </template:formTr>
	<template:formTr name="文档类型">
		<apptag:setSelectOptions columnName1="typename" columnName2="code" tableName="documenttype" valueName="documenttypecoll" condition="category='1' "/>
		<html:select property="documenttype" styleClass="inputtext" style="width:160">
		<html:option value="">不限</html:option>
  		<html:options collection="documenttypecoll" property="value" labelProperty="label"/>
		</html:select>
	</template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">查询</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>

