<%@include file="/common/header.jsp"%>
<script language="javascript" type="">
function addGoBack(){
    location.href = "${ctx}/changeapplyaction.do?method=getApplyInfoList";
    return true;
}
</script>
<br>
<template:titile value="查询修缮申请信息"/>
<html:form method="Post" action="/changeapplyaction?method=getApplyInfoList">
  <template:formTable contentwidth="200" namewidth="200">
    <template:formTr name="工程名称">
      <html:text property="changename" styleClass="inputtext" style="width:160" maxlength="45"/>
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
    <template:formTr name="审定结果" >
    <html:select property="approveresult" styleClass="inputtext" style="width:160">
      <html:option value="">不限</html:option>
        <html:option value="待审定">待审定</html:option>
        <html:option value="未通过">未通过</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="开始日期" >
      <html:text property="begintime" styleClass="inputtext" style="width:160" maxlength="45"/>
    <apptag:date property="begintime" />
    </template:formTr>
    <template:formTr name="结束日期" >
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

