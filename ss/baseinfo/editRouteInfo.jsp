<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.fsmanager.domainobjects.*" %>
<script language="javascript">
<!--
function isValidForm() {

	if(document.routeinfoBean.routeName.value==""){
		alert("·�����Ʋ���Ϊ��!! ");
		return false;
	}
    if(trim(document.routeinfoBean.routeName.value)==""){
		alert("·�����Ʋ���Ϊ�ո�!! ");
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
<template:titile value="�޸Ĳ�����Ϣ"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/RouteInfoAction.do?method=updateRouteInfo">
  <template:formTable contentwidth="200" namewidth="200">
    <html:hidden property="id"/>
    <template:formTr name="·������">
      <html:text property="routeName" styleClass="inputtext" style="width:160" maxlength="20"/>
    </template:formTr>
      <template:formTr name="��������">
        <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" order="regionid" condition="substr(REGIONID,3,4) != '1111' "/>
        <html:select property="regionID" disabled="false" styleClass="inputtext" style="width:160">
          <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
        </html:select>
      </template:formTr>
       <template:formSubmit>
      <td>
        <html:submit styleClass="button" >����</html:submit>
      </td>
      <td>
        <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="����" >
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
      