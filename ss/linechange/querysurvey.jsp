<%@include file="/common/header.jsp"%>
<script language="javascript" type="">
function addGoBack(){
    location.href = "${ctx}/changesurveyaction.do?method=getApplyInfoList";
    return true;
}
</script>
<br>
<template:titile value="��ѯ��������Ϣ"/>
<html:form method="Post" action="/changesurveyaction.do?method=getSurveyInfoList">
  <template:formTable contentwidth="200" namewidth="200">
    <template:formTr name="���鸺����">
      <html:text property="principal" styleClass="inputtext" style="width:160" maxlength="45"/>
    </template:formTr>
    <template:formTr name="����Ԥ��" >
      <html:text property="budget" styleClass="inputtext" style="width:65" maxlength="20"/>  ��Ԫ~ <html:text property="maxbudget" styleClass="inputtext" style="width:65" maxlength="20"/>��Ԫ
    </template:formTr>
    <template:formTr name="�󶨽��" >
    <html:select property="approveresult" styleClass="inputtext" style="width:160">
     <html:option value=""></html:option>
         <html:option value="ͨ����">ͨ����</html:option>
          <html:option value="δͨ��">δͨ��</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="�󶨿�ʼ����" >
      <html:text property="begintime" styleClass="inputtext" style="width:160" maxlength="45"/>
    <apptag:date property="begintime" />
    </template:formTr>
    <template:formTr name="�󶨽�������" >
      <html:text property="endtime" styleClass="inputtext" style="width:160" maxlength="45"/>
    <apptag:date property="endtime" />
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

