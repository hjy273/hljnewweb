<%@include file="/common/header.jsp"%>
<script language="javascript" type="">
function addGoBack(){
    location.href = "${ctx}/changesurveyaction.do?method=getApplyInfoList";
    return true;
}
</script>
<br>
<template:titile value="��ѯʩ����Ϣ"/>
<html:form method="Post" action="/buildaction.do?method=getBuildInfo">
  <template:formTable contentwidth="200" namewidth="200">
  <template:formTr name="��������">
      <html:text property="changename" styleClass="inputtext" style="width:160" maxlength="45"/>
    </template:formTr>
    <template:formTr name="ʩ����λ">
      <html:text property="buildunit" styleClass="inputtext" style="width:160" maxlength="45"/>
    </template:formTr>
    <template:formTr name="��������" >
      <html:text property="changepro" styleClass="inputtext" style="width:160" maxlength="45"/>
    </template:formTr>
    <template:formTr name="��������">
      <apptag:setSelectOptions columnName1="name" columnName2="code" tableName="lineclassdic" valueName="linetypeColl"/>
      <html:select property="lineclass" styleClass="inputtext" style="width:160">
       <html:option value="">����</html:option>
        <html:options collection="linetypeColl" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="ʩ����ʼ����" >
      <html:text property="starttime" styleClass="inputtext" style="width:160" maxlength="45"/>
    <apptag:date property="starttime" />
    </template:formTr>
    <template:formTr name="ʩ����������" >
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