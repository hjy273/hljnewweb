<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>

<BR/>

<script language="javascript" type="">

</script>

<template:titile value="��ά��˾��ʷ��ѯ"/>
<html:form method="Post" action="/RealTimeAction?method=showConHistory">
  <template:formTable contentwidth="200" namewidth="300">
    <template:formTr name="��&nbsp;&nbsp;&nbsp;&nbsp;��" isOdd="false">
      <html:text readonly="true" property="statDate" styleClass="Wdate" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" style="width:200" maxlength="12"/>
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

