<%@page import="com.cabletech.baseinfo.beans.*"%>
<%@include file="/common/header.jsp"%>
<script language="javascript">
<!--
function isValidForm() {
    //部门校验
	if(document.pointBean.pointName.value==""){
		alert("名称不能为空!! ");
		return false;
	}
	if(document.pointBean.addressInfo.value==""){
		alert("巡检点地址不能为空!! ");
		return false;
	}
	if(document.pointBean.subLineID.value==""){
		alert("所属巡检段不能为空!! ");
		return false;
	}

	return true;
}
function change(){
  var f = pointBean.isFId.value
  if(f==1)
     pointBean.sId.value = f;
}
function checked(){
  if(pointBean.isFId.value == 1){
    alert(" 关键点必须巡检！！");
    pointBean.sId.value = pointBean.isFId.value
  }
}


//-->
</script><template:titile value="修改巡检点信息"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/pointAction.do?method=updatePointForGis">
  <template:formTable>
    <template:formTr name="编号" isOdd="false">
      <html:text readonly="true" property="pointID" styleClass="inputtext" style="width:110" maxlength="45"/>
    </template:formTr>
    <template:formTr name="名称">
      <html:text property="pointName" styleClass="inputtext" style="width:110" maxlength="45"/>
    </template:formTr>
    <template:formTr name="地址" isOdd="false">
      <html:text property="addressInfo" styleClass="inputtext" style="width:110" maxlength="45"/>
    </template:formTr>
    <template:formTr name="坐标">
      <html:text property="gpsCoordinate" styleClass="inputtext" style="width:110" maxlength="45"/>
    </template:formTr>
    <template:formTr name="区域" isOdd="false">
      <apptag:setSelectOptions valueName="regionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid"/>
      <html:select property="regionID" styleClass="inputtext" style="width:110">
        <html:options collection="regionCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="巡检段">
      <apptag:setSelectOptions valueName="sublineCollection" tableName="sublineinfo" columnName1="sublinename" columnName2="sublineid" region="true"/>
      <html:select property="subLineID" styleClass="inputtext" style="width:110">
        <html:options collection="sublineCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="是否关键点" isOdd="false">
      <html:select property="isFocus" styleClass="inputtext" styleId="isFId" onchange="change()" style="width:110">
        <html:option value="0">否</html:option>
        <html:option value="1">是</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="线序">
      <html:text property="inumber" styleClass="inputtext" style="width:110" maxlength="45"/>
    </template:formTr>

	<template:formTr name="巡检点类型"  isOdd="false">
      <html:select property="pointtype" styleClass="inputtext" style="width:110">
        <html:option value="1">石</html:option>
        <html:option value="2">杆</html:option>
		<html:option value="3">井</html:option>
		<html:option value="4">墙</html:option>
		<html:option value="5">基站</html:option>
		<html:option value="6">机房</html:option>
      </html:select>
    </template:formTr>

    <template:formTr name="状态"  isOdd="false">
	  <html:select property="state" styleClass="inputtext" style="width:110" styleId="sId" onchange="checked()">
        <html:option value="1">需巡检</html:option>
        <html:option value="0">不需巡检</html:option>
      </html:select>
    </template:formTr>

    <template:formSubmit>
      <td>
        <html:submit styleClass="button">更新</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<script language="javascript">
<!--
try{
	pointBean.gpsCoordinate.value = parent.GPS.value;
}catch(e){}
//-->
</script>
