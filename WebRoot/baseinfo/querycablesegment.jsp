<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<script language="javascript" type="">
function addGoBack(){
    location.href = "${ctx}/CableSegmentAction.do?method=queryCableSegment";
    return true;
}
</script>

<template:titile value="��ѯ������Ϣ"/>
<html:form method="Post" action="/CableSegmentAction.do?method=queryCableSegment">
  <template:formTable contentwidth="200" namewidth="200">
    <template:formTr name="���¶κ�">
      <html:text property="segmentid" styleClass="inputtext" style="width:160" maxlength="45"/>
    </template:formTr>
    <template:formTr name="������" >
      <html:text property="segmentname" styleClass="inputtext" style="width:160" maxlength="45"/>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">��ѯ</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">ȡ��</html:reset>
      </td>
      <td>
        <input type="button" class="button" onclick="toGetBack()" value="����" >
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
