<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<jsp:useBean id="pointBean" class="com.cabletech.baseinfo.beans.PointBean" scope="request"/>

<%

String GPS = request.getParameter("GPS");
String tempID = request.getParameter("tempID");
String pointname = request.getParameter("pointname");


String sublineid = "";

if( session !=null && session.getAttribute("sublineidLastTime")!=null){
	sublineid = (String)session.getAttribute("sublineidLastTime");
}

pointBean.setSubLineID(sublineid);

%>

<script language="javascript" type="">
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
    if(document.pointBean.addressInfo.value==""){
        alert("巡检点地址不能为空!! ");
        document.pointBean.addressInfo.focus();
        return false;
	}
    if(document.pointBean.gpsCoordinate.value==""){
		alert("GPS坐标不能为空!! ");
        document.pointBean.gpsCoordinate.focus();
		return false;
	}
    if(document.pointBean.gpsCoordinate.value!=""){
    	var data = /^\d{1,17}$/;//GPS
        if(!data.test(document.pointBean.gpsCoordinate.value)){
	         document.pointBean.gpsCoordinate.focus();
	         alert("GPS坐标只能为数字！");
	         return false;
	    }
    }
	if(document.pointBean.subLineID.value==""){
		alert("所属巡检段不能为空!! ");
		return false;
	}
    if(document.pointBean.inumber.value==""){
		alert("所属巡检段不能为空!! ");
		return false;
	}
    if(document.pointBean.inumber.value!=""){
    	var data = /^\d{1,10}$/;//
        if(!data.test(document.pointBean.inumber.value)){
	         document.pointBean.inumber.focus();
	         alert("线顺只能为数字！");
	         return false;
	    }
    }
	return true;
}

function setPatrolAddr(){
	pointBean.addressInfo.value =  pointBean.pointName.value;
}

function setPointType(){
	var pointTypeChar = pointBean.pointName.value.substring(0,1);
	//alert(pointTypeChar);

	if(pointTypeChar == "石"){
		pointBean.pointtype.options[0].selected = true;
	}else if(pointTypeChar == "杆"){
		pointBean.pointtype.options[1].selected = true;
	}else if(pointTypeChar == "井"){
		pointBean.pointtype.options[2].selected = true;
	}else if(pointTypeChar == "墙"){
		pointBean.pointtype.options[3].selected = true;
	}else if(pointTypeChar == "基"){
		pointBean.pointtype.options[4].selected = true;
	}else if(pointTypeChar == "机"){
		pointBean.pointtype.options[5].selected = true;
	}

}

function toDelete(){

	idValue = pointBean.tempID.value;

	if(confirm("确定删除该临时点？")){

        //var url = "${ctx}/pointAction.do?method=deleteTempPoint4GIS&id=" + idValue;
		var url = "${ctx}/pointAction.do?method=setEditTempPoint4GIS&id=" + idValue;
        self.location.replace(url);
	}

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
<body onload="prepareINumber();setPatrolAddr();setPointType()">
<template:titile value="将该临时点另存为巡检点"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/pointAction.do?method=addPointForGis">
  <template:formTable>
    <template:formTr name="名称">
      <html:text property="pointName" styleClass="inputtext" style="width:110" maxlength="45" onchange="prepareINumber()" onblur="prepareINumber()" value="<%=pointname%>"/>
    </template:formTr>
    <template:formTr name="地址" >
      <html:text property="addressInfo" styleClass="inputtext" style="width:110" maxlength="25" />
    </template:formTr>
    <template:formTr name="坐标" style="display:none">
      <input type="hidden" name="tempID" value="<%=tempID%>"/>
      <html:text property="gpsCoordinate" styleClass="inputtext" style="width:110" maxlength="17" value="<%=GPS%>"/>
    </template:formTr>
	<template:formTr name="巡检段">
      <apptag:setSelectOptions valueName="sublineCollection" tableName="sublineinfo" columnName1="sublinename" columnName2="sublineid" region="true"/>
      <html:select property="subLineID" styleClass="inputtext" style="width:110">
        <html:options collection="sublineCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="是否关键点" >
      <html:select property="isFocus" styleId="isFId" styleClass="inputtext" style="width:110" onchange="change()">
        <html:option value="0">否</html:option>
        <html:option value="1">是</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="线顺">
      <html:text property="inumber" styleClass="inputtext" style="width:110" maxlength="10"/>
    </template:formTr>

	<template:formTr name="巡检点类型" >
      <html:select property="pointtype" styleClass="inputtext" style="width:110">
        <html:option value="1">石</html:option>
        <html:option value="2">杆</html:option>
		<html:option value="3">井</html:option>
		<html:option value="4">墙</html:option>
		<html:option value="5">基站</html:option>
		<html:option value="6">机房</html:option>
      </html:select>
    </template:formTr>

    <template:formTr name="状态">
	  <html:select property="state" styleId="sId" styleClass="inputtext" style="width:110" onchange="checked()">
        <html:option value="1">需巡检</html:option>
        <html:option value="0">不需巡检</html:option>
      </html:select>
    </template:formTr>

    <template:formSubmit>
      <td>
        <html:submit styleClass="lbutton">保存为巡检点</html:submit>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
</body>
