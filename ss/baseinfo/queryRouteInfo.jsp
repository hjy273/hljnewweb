<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.fsmanager.domainobjects.*" %>
<script language="javascript">
<!--
function toGetBack(){
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
<template:titile value="��ѯ·����Ϣ"/>
<html:form method="Post" action="/RouteInfoAction.do?method=queryRouteInfo">
  <template:formTable>
    <template:formTr name="·������">
      <html:text property="routeName" styleClass="inputtext" style="width:200px" maxlength="45"/>
    </template:formTr>
    <template:formTr name="��������" isOdd="false">
      <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname"  columnName2="regionid" region="true" order="regionid" condition="substr(REGIONID,3,4) != '1111' "/>
      <html:select property="regionID" styleClass="inputtext" style="width:200px">
      	<html:option value="">��</html:option>
        <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
     <template:formSubmit>
      <td>
        <html:submit styleClass="button">��ѯ</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">ȡ��</html:reset>
      </td>
      <td>
        <input type="button" class="button" onclick="toGetBack()" value="����" >
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
    