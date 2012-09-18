<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<template:titile value="设备重新分配"/>
<html:form method="Post" action="/ResourceAssignAction.do?method=showAssignTerminal">
<template:formTable contentwidth="300" namewidth="200">
<template:formTr name="代维单位" >
     <apptag:setSelectOptions valueName="connCollection" tableName="contractorinfo" columnName1="contractorname" region="true" columnName2="contractorid" order="contractorname" />
     <html:select property="contractor" styleClass="inputtext" styleId="rId" style="width:200">
        <html:options collection="connCollection" property="value" labelProperty="label"/>
     </html:select>
</template:formTr>
<template:formSubmit>
      <td>
        <html:submit styleClass="button">下一步</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
</template:formSubmit>
</template:formTable>
</html:form>
<table width="500" border="0" align="center" cellpadding="3" cellspacing="0" >
  <tr>
    <td><div align="left" style="color:red">
    注意：<br>
    * 请选择原设备所属维护部门<br>
    </div></td>
  </tr>
</table>