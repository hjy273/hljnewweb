<%@include file="/common/header.jsp"%>
<script language="javascript">
<!--
function isValidForm() {
    //����У��
	if(document.regionBean.regionName.value==""){
		alert("���Ʋ���Ϊ��!! ");
		return false;
	}
	if(document.regionBean.regionDes.value==""){
		alert("����˵������Ϊ��!! ");
		return false;
	}

	return true;
}

//-->
</script>
<template:titile value="����������Ϣ"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/regionAction.do?method=addRegion">
  <template:formTable>
    <template:formTr name="�������" isOdd="false">
      <html:text property="regionID" styleClass="inputtext" style="width:160" maxlength="6"/>
    </template:formTr>
    <template:formTr name="��������">
      <html:text property="regionName" styleClass="inputtext" style="width:160" maxlength="20"/>
    </template:formTr>
    <template:formTr name="�ϼ�����" isOdd="false">
      <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid"/>
      <html:select property="parentregionid" styleClass="inputtext" style="width:160">
        <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="����˵��">
      <html:text property="regionDes" styleClass="inputtext" style="width:160" maxlength="100"/>
    </template:formTr>
    <template:formTr name="״̬" isOdd="false">
      <html:select property="state" styleClass="inputtext" style="width:160">
        <html:option value="1">����</html:option>
        <html:option value="2">��ͣ</html:option>
        <html:option value="3">ֹͣ</html:option>
      </html:select>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">����</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">ȡ��</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
