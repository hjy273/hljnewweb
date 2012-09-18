<%@include file="/common/header.jsp"%>
<script language="javascript" type="">
function addGoBack(){
    location.href = "${ctx}/changesurveyaction.do?method=getApplyInfoList";
    return true;
}
</script>
<br>
<template:titile value="查询勘查审定信息"/>
<html:form method="Post" action="/changesurveyaction.do?method=getSurveyInfoList">
  <template:formTable contentwidth="200" namewidth="200">
    <template:formTr name="勘查负责人">
      <html:text property="principal" styleClass="inputtext" style="width:160" maxlength="45"/>
    </template:formTr>
    <template:formTr name="工程预算" >
      <html:text property="budget" styleClass="inputtext" style="width:65" maxlength="20"/>  万元~ <html:text property="maxbudget" styleClass="inputtext" style="width:65" maxlength="20"/>万元
    </template:formTr>
    <template:formTr name="审定结果" >
    <html:select property="approveresult" styleClass="inputtext" style="width:160">
     <html:option value=""></html:option>
         <html:option value="通过审定">通过审定</html:option>
          <html:option value="未通过">未通过</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="审定开始日期" >
      <html:text property="begintime" styleClass="inputtext" style="width:160" maxlength="45"/>
    <apptag:date property="begintime" />
    </template:formTr>
    <template:formTr name="审定结束日期" >
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

