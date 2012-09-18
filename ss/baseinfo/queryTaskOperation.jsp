<%@include file="/common/header.jsp"%>
<script language="javascript">
</script>
<br>
<template:titile value="查询任务操作信息"/>
<html:form method="Post" action="/TaskOperationAction.do?method=queryTaskOperation">
  <template:formTable>
    <template:formTr name="任务操作名称">
      <html:text property="operationName" styleClass="inputtext" style="width:200px"/>
    </template:formTr>
    <template:formTr name="所属区域" isOdd="false" style="display:none">
      <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid"/>
      <html:select property="regionID" styleClass="inputtext" style="width:200px">
        <html:option value="">不限</html:option>
        <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
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
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
