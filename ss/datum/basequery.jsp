<template:formTable contentwidth="200" namewidth="200">
    <template:formTr name="�ĵ�����">
      <html:text property="documentname" styleClass="inputtext" style="width:160" maxlength="45"/>
    </template:formTr>
	<template:formTr name="�ĵ�����">
		<%
			String category = (String)request.getAttribute("category");
			if(category.equals("1")){
		 %>
		<apptag:setSelectOptions columnName1="typename" columnName2="code" tableName="documenttype" valueName="documenttypecoll"  condition="category='1' "/>
		<%}
		if(category.equals("2")){
		 %>
		<apptag:setSelectOptions columnName1="typename" columnName2="code" tableName="documenttype" valueName="documenttypecoll"  condition="category='2' "/>
		<%}
		if(category.equals("3")){
		 %>
		<apptag:setSelectOptions columnName1="typename" columnName2="code" tableName="documenttype" valueName="documenttypecoll"  condition="category='3' "/>
		<%} %>
		<html:select property="documenttype" styleClass="inputtext" style="width:160">
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