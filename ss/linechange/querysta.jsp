<%@include file="/common/header.jsp"%>
<script language="javascript" type="">
function addGoBack(){
    location.href = "${ctx}/stataction.do?method=loadQueryForm";
    return true;
}
</script>
<br>
<template:titile value="��ѯͳ����Ϣ"/>
<html:form method="Post" action="/stataction.do?method=getStatInfo">
  <template:formTable contentwidth="200" namewidth="200">
    <template:formTr name="��������">
      <html:text property="changename" styleClass="inputtext" style="width:160" maxlength="45"/>
    </template:formTr>
    <template:formTr name="��������">
      <html:text property="changepro" styleClass="inputtext" style="width:160" maxlength="45"/>
    </template:formTr>
    <template:formTr name="����״̬" >
    <html:select property="step" styleClass="inputtext" style="width:160">
      <html:option value="">����</html:option>
        <html:option value="A">����</html:option>
         <html:option value="B1">ͨ��������</html:option>
         <html:option value="B2">ͨ���ƶ���</html:option>
          <html:option value="C">ʩ��׼��</html:option>
          <html:option value="D">��ʼʩ��</html:option>
          <html:option value="E">ʩ�����</html:option>
          <html:option value="F">�������</html:option>
          <html:option value="G">�Ѿ��鵵</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="��������">
      <apptag:setSelectOptions columnName1="name" columnName2="code" tableName="lineclassdic" valueName="linetypeColl"/>
      <html:select property="lineclass" styleClass="inputtext" style="width:160">
        <html:option value="">����</html:option>
        <html:options collection="linetypeColl" property="value" labelProperty="label"/>
      </html:select>
      </template:formTr>
      <template:formTr name="��д��ʼ����">
        <html:text property="begintime" styleClass="inputtext" style="width:160" maxlength="45"/>
        <apptag:date property="begintime"/>
      </template:formTr>
      <template:formTr name="��д��������">
        <html:text property="endtime" styleClass="inputtext" style="width:160" maxlength="45"/>
        <apptag:date property="endtime"/>
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
