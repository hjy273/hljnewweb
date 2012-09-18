<%@page import="com.cabletech.baseinfo.beans.*"%>
<%@include file="/common/header.jsp"%>
<script language="javascript">
<!--
function prepareINumber(){
//formname pointBean
	var pointname = pointBean.pointName.value;
	pointname = pointname.substring(1,pointname.length);

	var i = parseInt(pointname,10);
	if(isNaN(i)){
		i = 0;
	}
	//alert(i);
	pointBean.inumber.value = i;
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
function toGetBack(){
        var url = "${ctx}/pointAction.do?method=queryPoint&pointName=&addressInfo=";
        self.location.replace(url);

}
function change(){
  var f = pointBean.isFId.value
  if(f==1)
     pointBean.sId.value = f;
}
function checked(){
  if(pointBean.isFId.value == 1){
    alert(" 重大事故点必须巡检！！");
    pointBean.sId.value = pointBean.isFId.value
  }
}

//-->
</script>
<br>
<template:titile value="修改巡检点信息"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/pointAction.do?method=updatePoint">
  <template:formTable >
    <template:formTr name="巡检点编号" isOdd="false">
      <html:text readonly="true" property="pointID" styleClass="inputtext" style="width:200px" maxlength="45"/>
    </template:formTr>
    <template:formTr name="巡检点名称">
      <html:text property="pointName" styleClass="inputtext" style="width:200px" maxlength="4" onchange="prepareINumber()" onblur="prepareINumber()"/>
    </template:formTr>
    <template:formTr name="巡检点地址" isOdd="false">
      <html:text property="addressInfo" styleClass="inputtext" style="width:200px" maxlength="25"/>
    </template:formTr>
    <template:formTr name="GPS坐标">
      <html:text property="gpsCoordinate" styleClass="inputtext" style="width:200px" maxlength="17"/>
    </template:formTr>
    <template:formTr name="所属区域" isOdd="false">
      <apptag:setSelectOptions valueName="regionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" condition="substr(REGIONID,3,4) != '1111' "/>
      <html:select property="regionID" styleClass="inputtext" style="width:200px">
        <html:options collection="regionCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="所属巡检段">
      <apptag:setSelectOptions valueName="sublineCollection" tableName="sublineinfo" columnName1="sublinename" columnName2="sublineid" region="true"/>
      <html:select property="subLineID" styleClass="inputtext" style="width:200px">
        <html:options collection="sublineCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="是否关键点" isOdd="false">
      <html:select property="isFocus" styleClass="inputtext" styleId="isFId" onchange="change()" style="width:200px">
        <html:option value="0">否</html:option>
        <html:option value="1">是</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="所在线段顺序">
      <html:text property="inumber" styleClass="inputtext" style="width:200px" maxlength="10" readonly="true"/>
    </template:formTr>

	<template:formTr name="巡检点类型"  isOdd="false">
      <html:select property="pointtype" styleClass="inputtext" style="width:200px">
        <html:option value="1">石</html:option>
        <html:option value="2">杆</html:option>
		<html:option value="3">井</html:option>
		<html:option value="4">墙</html:option>
		<html:option value="5">基站</html:option>
		<html:option value="6">机房</html:option>
      </html:select>
    </template:formTr>


    <template:formTr name="状态">
	  <html:select property="state" styleClass="inputtext" styleId="sId" onchange="checked()" style="width:200px">
        <html:option value="1">需巡检</html:option>
        <html:option value="0">不需巡检</html:option>
      </html:select>
    </template:formTr>



    <template:formSubmit>
      <td>
        <html:submit styleClass="button">更新</html:submit>
      </td>
      <td>
        <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="返回" >
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
