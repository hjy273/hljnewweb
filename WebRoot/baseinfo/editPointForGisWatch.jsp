<%@page import="com.cabletech.baseinfo.beans.*"%>
<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<SCRIPT LANGUAGE="JavaScript" src="/WEBGIS/js/function.js" type=""></SCRIPT>
<script language="javascript">
<!--

function toAddWatch(){

	var pointID = pointBean.pointID.value;
	var pointName = pointBean.pointName.value;
	var gpsCoordinate = pointBean.gpsCoordinate.value;
	var regionID = pointBean.regionID.value;
	var subLineID = pointBean.subLineID.value;

	var desPage = "${ctx}/GisWatchAction.do?method=toAddWatchInfo&pointName="+pointName + "&gpsCoordinate="+gpsCoordinate+"&regionID="+regionID+"&subLineID="+subLineID+"&pointID="+pointID;

	//alert(desPage);

	self.location.replace(desPage);

}

function toDelete(){

	idValue = pointBean.pointID.value;

	if(confirm("确定删除该巡检点？")){
				//setMapRefresh('RefreshAllPoint');
				//alert("000");
        var url = "${ctx}/pointAction.do?method=deletePoint4GIS&id=" + idValue;
        self.location.replace(url);

	}
}

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
<html:form onsubmit="return isValidForm()" method="Post" action="/pointAction.do?method=updatePointForGisWatch">
  <template:formTable>
    <template:formTr name="编号" >
      <html:text readonly="true" property="pointID"  styleClass="inputtext" style="width:110" maxlength="45"/>
    </template:formTr>
    <template:formTr name="名称">
      <html:text property="pointName" styleClass="inputtext" style="width:110" maxlength="45"/>
    </template:formTr>
    <template:formTr name="地址" >
      <html:text property="addressInfo" styleClass="inputtext" style="width:110" maxlength="45"/>
    </template:formTr>
    <template:formTr name="坐标">
      <html:text property="gpsCoordinate" styleClass="inputtext" style="width:110" maxlength="45"/>
    </template:formTr>
    <template:formTr name="区域" >
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
    <template:formTr name="是否关键点" >
      <html:select property="isFocus" styleClass="inputtext" style="width:110" styleId="isFId" onchange="change()">
        <html:option value="0">否</html:option>
        <html:option value="1">是</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="线序">
      <html:text property="inumber" styleClass="inputtext" style="width:110" maxlength="45"/>
    </template:formTr>

	<template:formTr name="巡检点类型"  >
      <html:select property="pointtype" styleClass="inputtext" style="width:110">
        <html:option value="1">石</html:option>
        <html:option value="2">杆</html:option>
		<html:option value="3">井</html:option>
		<html:option value="4">墙</html:option>
		<html:option value="5">基站</html:option>
		<html:option value="6">机房</html:option>
      </html:select>
    </template:formTr>

	<template:formTr name="状态" >
	  <html:select property="state" styleClass="inputtext" style="width:110" styleId="sId" onchange="checked()">
        <html:option value="1">需巡检</html:option>
        <html:option value="0">不需巡检</html:option>
      </html:select>
    </template:formTr>

    <template:formSubmit>
      <td>
        <html:submit styleClass="button" >更新</html:submit>
      </td>
      <td>
        <html:button property="action" styleClass="button" onclick="toDelete()">删除</html:button>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<script language="javascript">
<!--//
try{
	pointBean.gpsCoordinate.value = parent.GPS.value;
}catch(e){}
//-->
</script>
