<%@include file="/common/header.jsp"%>
<html>
<body>
<template:titile value="��ѯѲ���߶���Ϣ"/>
<html:form method="Post" action="/sublineAction.do?method=querySubline">
  <template:formTable>
    <template:formTr name="Ѳ���߶�����">
      <html:text property="subLineName" styleClass="inputtext" style="width:200px" maxlength="45"/>
    </template:formTr>
    <template:formTr name="������·" isOdd="false">
      <apptag:setSelectOptions valueName="lineCollection" tableName="lineinfo" columnName1="linename" region="true" columnName2="lineid"/>
      <html:select property="lineID" styleClass="inputtext" style="width:200px">
        <html:option value="">����</html:option>
        <html:options collection="lineCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="��������">
      <apptag:setSelectOptions valueName="deptCollection" tableName="deptinfo" columnName1="deptname" columnName2="deptid" region="true"/>
      <html:select property="ruleDeptID" styleClass="inputtext" style="width:200px">
        <html:option value="">����</html:option>
        <html:options collection="deptCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="��·����" isOdd="false">
     <apptag:quickLoadList cssClass="inputtext" name="lineType" listName="layingmethod"  style="width:200px" type="select"/>
      <!--html:select property="lineType" styleClass="inputtext" style="width:200px"->
        <-html:option value="">����</html:option->
        <-html:option value="00">ֱ��</html:option->
        <-html:option value="01">�ܿ�</html:option->
        <-html:option value="02">�ܵ�</html:option->
		<-html:option value="04">��ǽ</html:option->
		<-html:option value="03">���</html:option->
	  <-/html:select-->
    </template:formTr>

    <logic:equal value="group" name="PMType">
    	<template:formTr name="Ѳ��ά����">
	      <html:select property="patrolid" styleClass="inputtext" style="width:200px">
	        <html:option value="">��</html:option>
	        <html:options collection="patrolCollection" property="value" labelProperty="label"/>
	      </html:select>
	    </template:formTr>
    </logic:equal>
     <logic:notEqual value="group" name="PMType">
    	<template:formTr name="Ѳ��ά����">
	      <html:select property="patrolid" styleClass="inputtext" style="width:200px">
	        <html:option value="">��</html:option>
	        <html:options collection="patrolCollection" property="value" labelProperty="label"/>
	      </html:select>
	    </template:formTr>
    </logic:notEqual>


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
</body>
</html>