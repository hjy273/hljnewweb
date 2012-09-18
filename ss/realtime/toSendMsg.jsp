<%@include file="/common/header.jsp"%>
<script language="javascript">
<!--
function isValidForm() {
    //部门校验
	if(document.regionBean.regionName.value==""){
		alert("名称不能为空!! ");
		return false;
	}
	if(document.regionBean.regionDes.value==""){
		alert("区域说明不能为空!! ");
		return false;
	}

	return true;
}

//-->
</script>
<template:titile value="增加区域信息"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/regionAction.do?method=addRegion">
  <template:formTable>
    <template:formTr name="区域编码" isOdd="false">
      <html:text property="regionID" styleClass="inputtext" style="width:160" maxlength="6"/>
    </template:formTr>
    <template:formTr name="区域名称">
      <html:text property="regionName" styleClass="inputtext" style="width:160" maxlength="20"/>
    </template:formTr>
    <template:formTr name="上级区域" isOdd="false">
      <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid"/>
      <html:select property="parentregionid" styleClass="inputtext" style="width:160">
        <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="区域说明">
      <html:text property="regionDes" styleClass="inputtext" style="width:160" maxlength="100"/>
    </template:formTr>
    <template:formTr name="状态" isOdd="false">
      <html:select property="state" styleClass="inputtext" style="width:160">
        <html:option value="1">正常</html:option>
        <html:option value="2">暂停</html:option>
        <html:option value="3">停止</html:option>
      </html:select>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">增加</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
