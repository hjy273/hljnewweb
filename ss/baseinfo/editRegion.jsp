<%@page import="com.cabletech.baseinfo.beans.*"%>
<jsp:useBean id="regionBean" class="com.cabletech.baseinfo.beans.RegionBean" scope="request"/>
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

function toGetBack() {
        	try{
            	location.href = "${ctx}/baseinfo/queryregionresult.jsp";
                return true;
            }
            catch(e){
            	alert(e);
            }
        }

//-->
</script>
<template:titile value="修改区域信息"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/regionAction.do?method=updateRegion">
  <template:formTable contentwidth="200" namewidth="200">
    <template:formTr name="区域编码" isOdd="false">
      <html:text property="regionID" styleClass="inputtext" style="width:200" maxlength="6"/>
    </template:formTr>
    <template:formTr name="区域名称">
      <html:text property="regionName" styleClass="inputtext" style="width:200" maxlength="10"/>
    </template:formTr>
    <template:formTr name="上级区域" isOdd="false">
      <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" columnName2="regionid" region="true" order="regionid" condition="substr(REGIONID,3,4) != '1111' "/>
      <html:select property="parentregionid" styleClass="inputtext" style="width:200">
		<html:option value="000000">无</html:option>
        <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
      </html:select>
      <!--<html:text property="parentregionid"  styleClass="inputtext" style="width:200" readonly="true">
      </html:text>-->
    </template:formTr>
    <template:formTr name="区域说明">
      <html:text property="regionDes" styleClass="inputtext" style="width:200" maxlength="100"/>
    </template:formTr><!--
    <template:formTr name="状态" isOdd="false">
      <html:select property="state" styleClass="inputtext" style="width:200">
        <html:option value="1">正常</html:option>
        <html:option value="2">暂停</html:option>
        <html:option value="3">停止</html:option>
      </html:select>
    </template:formTr>-->
    <html:hidden property="state"/>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">更新</html:submit>
      </td>
      <td>
        <input type="button" class="button" onclick="toGetBack()" value="返回" >
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
