<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
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
        document.pointBean.pointName.focus();
		return false;
	}
    if(document.pointBean.gpsCoordinate.value!=""){
    	var data = /^\d{1,17}$/;//电话
        if(!data.test(document.pointBean.gpsCoordinate.value)){
	         document.pointBean.gpsCoordinate.focus();
	         alert("GPS坐标只能为数字！");
	         return false;
	    }
    }
	if(document.pointBean.addressInfo.value==""){
		alert("巡检点地址不能为空!! ");
		return false;
	}
	if(document.pointBean.subLineID.value==""){
		alert("所属巡检段不能为空!! ");
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
    alert(" 重大事故点必须巡检！！");
    pointBean.sId.value = pointBean.isFId.value
  }
}
function toGetBack(){
 	var url="${ctx}/pointAction.do?method=queryPoint&pointName=&addressInfo=";
	self.location.replace(url);
}
//-->
</script>
<template:titile value="增加巡检点信息"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/pointAction.do?method=addPoint">
  <template:formTable contentwidth="300" namewidth="200">
    <template:formTr name="巡检点名称">
      <html:text property="pointName" styleClass="inputtext" style="width:200" maxlength="4" onchange="prepareINumber()" onblur="prepareINumber()"/>
    </template:formTr>
    <template:formTr name="巡检点地址" >
      <html:text property="addressInfo" styleClass="inputtext" style="width:200" maxlength="25"/>
    </template:formTr>
    <template:formTr name="GPS坐标">
      <html:text property="gpsCoordinate" styleClass="inputtext" style="width:200" title="GPS坐标为17位数字" maxlength="17"/>
    </template:formTr>
    <template:formTr name="所属巡检段" >
      <apptag:setSelectOptions valueName="sublineCollection" tableName="sublineinfo" columnName1="sublinename" columnName2="sublineid" region="true"/>
      <html:select property="subLineID" styleClass="inputtext" style="width:200">
        <html:options collection="sublineCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="是否关键点">
      <html:select property="isFocus" styleId="isFId" styleClass="inputtext" style="width:200" onchange="change(this)">
        <html:option value="0">否</html:option>
        <html:option value="1">是</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="所在线段顺序" >
      <html:text property="inumber" styleClass="inputtext" style="width:200" maxlength="10"/>
    </template:formTr>

	<template:formTr name="巡检点类型">
      <html:select property="pointtype" styleClass="inputtext" style="width:200">
        <html:option value="1">石</html:option>
        <html:option value="2">杆</html:option>
		<html:option value="3">井</html:option>
		<html:option value="4">墙</html:option>
		<html:option value="5">基站</html:option>
		<html:option value="6">机房</html:option>
      </html:select>
    </template:formTr>

    <template:formTr name="状态"  >
	  <html:select property="state" styleId="sId" styleClass="inputtext" style="width:200" onchange="checked()">
        <html:option value="1">需巡检</html:option>
        <html:option value="0">不需巡检</html:option>
      </html:select>
    </template:formTr>

	<template:formSubmit>
      <td>
        <html:submit styleClass="button">增加</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
      <td>
        <input type="button" class="button" onclick="toGetBack()" value="返回" >
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
