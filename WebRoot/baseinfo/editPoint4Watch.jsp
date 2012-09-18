<%@page import="com.cabletech.baseinfo.beans.*"%>
<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
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

function setParent(){

	parent.watchBean.placeName.value = pointBean.pointName.value;
	parent.watchBean.gpsCoordinate.value = pointBean.gpsCoordinate.value;

	setRegion(pointBean.regionID.value);
	setLid(pointBean.subLineID.value);
	setGerneralP("00");

	parent.toSetVis("");


}

function setRegion(iniV){

	for(var i = 0; i < parent.watchBean.regionID.options.length; i ++){
		if(parent.watchBean.regionID.options[i].value == iniV){
			parent.watchBean.regionID.options[i].selected = "true";
			break;
		}
	}
}

function setLid(iniV){

	for(var i = 0; i < parent.watchBean.lid.options.length; i ++){
		if(parent.watchBean.lid.options[i].value == iniV){
			parent.watchBean.lid.options[i].selected = "true";
			break;
		}
	}
}

function setGerneralP(iniV){

	for(var i = 0; i < parent.watchBean.isGeneralPoint.options.length; i ++){
		if(parent.watchBean.isGeneralPoint.options[i].value == iniV){
			parent.watchBean.isGeneralPoint.options[i].selected = "true";
			break;
		}
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
</script><template:titile value="修改巡检点信息"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/pointAction.do?method=updatePoint">
  <template:formTable>
    <template:formTr name="编号" >
      <html:text readonly="true" property="pointID" styleClass="inputtext" size="25" maxlength="45"/>
    </template:formTr>
    <template:formTr name="名称">
      <html:text property="pointName" styleClass="inputtext" size="25" maxlength="45"/>
    </template:formTr>
    <template:formTr name="地址" >
      <html:text property="addressInfo" styleClass="inputtext" size="25" maxlength="45"/>
    </template:formTr>
    <template:formTr name="坐标">
      <html:text property="gpsCoordinate" styleClass="inputtext" size="25" maxlength="45"/>
    </template:formTr>
    <template:formTr name="区域" >
      <apptag:setSelectOptions valueName="regionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" condition="substr(REGIONID,3,4) != '1111' "/>
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
      <html:text property="inumber" styleClass="inputtext" size="25" maxlength="45"/>
    </template:formTr>

    <template:formTr name="状态"  >
	  <html:select property="state" styleClass="inputtext" styleId="sId" onchange="checked()" style="width:110">
        <html:option value="1">需巡检</html:option>
        <html:option value="0">不需巡检</html:option>
      </html:select>
    </template:formTr>

    <template:formSubmit>
      <td>
        <html:submit styleClass="button">修改</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<script language="javascript">
<!--

//回设
setParent();

//-->
</script>
