<%@include file="/common/header.jsp"%>
<script language="javascript" type="">
function addGoBack(){
    location.href = "${ctx}/CableSegmentAction.do?method=queryCableSegment";
    return true;
}
</script>
<br>
<template:titile value="查询光缆信息"/>
<html:form method="Post" action="/CableSegmentAction.do?method=queryCableSegment">
  <template:formTable contentwidth="200" namewidth="200">
    <template:formTr name="光缆段号">
      <html:text property="segmentid" styleClass="inputtext" style="width:160" maxlength="45"/>
    </template:formTr>
    <template:formTr name="光缆名" >
      <html:text property="segmentname" styleClass="inputtext" style="width:160" maxlength="45"/>
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
