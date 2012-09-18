<%@include file="/common/header.jsp"%>
<script language="javascript" type="">
function addGoBack(){
    location.href = "${ctx}/changesurveyaction.do?method=getApplyInfoList";
    return true;
}
</script>
<br>
<template:titile value="查询施工信息"/>
<html:form method="Post" action="/buildaction.do?method=getBuildInfo">
  <template:formTable contentwidth="200" namewidth="200">
  <template:formTr name="工程名称">
      <html:text property="changename" styleClass="inputtext" style="width:160" maxlength="45"/>
    </template:formTr>
    <template:formTr name="施工单位">
      <html:text property="buildunit" styleClass="inputtext" style="width:160" maxlength="45"/>
    </template:formTr>
    <template:formTr name="工程性质" >
      <html:text property="changepro" styleClass="inputtext" style="width:160" maxlength="45"/>
    </template:formTr>
    <template:formTr name="网络性质">
      <apptag:setSelectOptions columnName1="name" columnName2="code" tableName="lineclassdic" valueName="linetypeColl"/>
      <html:select property="lineclass" styleClass="inputtext" style="width:160">
       <html:option value="">不限</html:option>
        <html:options collection="linetypeColl" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="施工开始日期" >
      <html:text property="starttime" styleClass="inputtext" style="width:160" maxlength="45"/>
    <apptag:date property="starttime" />
    </template:formTr>
    <template:formTr name="施工结束日期" >
      <html:text property="endtime" styleClass="inputtext" style="width:160" maxlength="45"/>
    <apptag:date property="endtime" />
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
