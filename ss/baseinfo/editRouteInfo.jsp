<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.fsmanager.domainobjects.*" %>
<script language="javascript">
<!--
function isValidForm() {

	if(document.routeinfoBean.routeName.value==""){
		alert("路由名称不能为空!! ");
		return false;
	}
    if(trim(document.routeinfoBean.routeName.value)==""){
		alert("路由名称不能为空格!! ");
		return false;
	}
	return true;
}


function addGoBack()
        {
        	try{
            	location.href = "${ctx}/RouteInfoAction.do?method=queryRouteInfo";
                return true;
            }
            catch(e){
            	alert(e);
            }
        }

//-->
</script>
<br>
<template:titile value="修改部门信息"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/RouteInfoAction.do?method=updateRouteInfo">
  <template:formTable contentwidth="200" namewidth="200">
    <html:hidden property="id"/>
    <template:formTr name="路由名称">
      <html:text property="routeName" styleClass="inputtext" style="width:160" maxlength="20"/>
    </template:formTr>
      <template:formTr name="所属区域">
        <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" order="regionid" condition="substr(REGIONID,3,4) != '1111' "/>
        <html:select property="regionID" disabled="false" styleClass="inputtext" style="width:160">
          <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
        </html:select>
      </template:formTr>
       <template:formSubmit>
      <td>
        <html:submit styleClass="button" >更新</html:submit>
      </td>
      <td>
        <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="返回" >
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
      