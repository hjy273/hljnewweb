<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>

<BR/>

<script language="javascript" type="">

</script>

<template:titile value="代维公司历史查询"/>
<html:form method="Post" action="/RealTimeAction?method=showConHistory">
  <template:formTable contentwidth="200" namewidth="300">
    <template:formTr name="日&nbsp;&nbsp;&nbsp;&nbsp;期" isOdd="false">
      <html:text readonly="true" property="statDate" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" style="width:200" maxlength="12"/>
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

