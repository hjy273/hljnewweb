<%@page import="com.cabletech.lineinfo.beans.*"%>
<jsp:useBean id="GISCrossPointBean" class="com.cabletech.lineinfo.beans.GISCrossPointBean" scope="request"/>
<%@include file="/common/header.jsp"%>
<script language="javascript" type="">
<!--
function isValidForm() {
    //部门校验
	if(document.GISCrossPointBean.crosspointname.value==""){
		alert("名称不能为空!! ");
		return false;
	}
	return true;
}

function deleteCrossPoint() {

	if(confirm("确定删除该标示点？")){
		document.location.replace("${ctx}/GISCrossPointAction.do?method=deleteGISCrossPoint&id="+ GISCrossPointBean.id.value);
	}


}

//-->
</script>
<%
String regionID = GISCrossPointBean.getRegionid();
%>
<template:titile value="修改标识点信息"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/GISCrossPointAction.do?method=updateGISCrossPoint">
  <template:formTable>

    <template:formTr name="名称">

	  <html:hidden property="id"/>
	  <html:hidden property="gpscoordinate"/>
	  <html:hidden property="status"/>

      <html:text property="crosspointname" styleClass="inputtext" style="width:110"/>
    </template:formTr>

    <template:formTr name="类型" isOdd="false">
	  <html:select property="type" styleClass="inputtext" style="width:110">
        <!--<html:option value="1">中继点</html:option>
		<html:option value="2">基站</html:option>
		<html:option value="6">机房</html:option>-->
        <html:option value="3">特殊标示点</html:option>
      </html:select>
	</template:formTr>

    <template:formTr name="区域">
      <apptag:setSelectOptions valueName="regionCellection" tableName="region" columnName1="regionname" currentRegion="true" columnName2="regionid" regionID="<%=regionID%>"/>
      <html:select property="regionid" styleClass="inputtext" style="width:110">
        <html:options collection="regionCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>

    <template:formTr name="备注"  isOdd="false">
      <html:text property="remark" styleClass="inputtext" style="width:110"/>
    </template:formTr>

    <template:formSubmit>
      <td>
        <html:submit styleClass="button">更新</html:submit>
      </td>
      <td>
        <html:button styleClass="button" property="action" onclick="deleteCrossPoint()">删除</html:button>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>

