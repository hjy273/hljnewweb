<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<script language="javascript">
</script>

<template:titile value="��ѯ���������Ϣ"/>
<html:form method="Post" action="/TaskOperationAction.do?method=queryTaskOperation">
  <template:formTable contentwidth="300" namewidth="200">
    <template:formTr name="�����������">
      <html:text property="operationName" styleClass="inputtext" style="width:200"/>
    </template:formTr>
    <template:formTr name="��������"  style="display:none">
      <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid"/>
      <html:select property="regionID" styleClass="inputtext" style="width:200">
        <html:option value="">����</html:option>
        <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
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
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
