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
    //����У��
	if(document.pointBean.pointName.value==""){
		alert("���Ʋ���Ϊ��!! ");
        document.pointBean.pointName.focus();
		return false;
	}
    if(document.pointBean.gpsCoordinate.value!=""){
    	var data = /^\d{1,17}$/;//�绰
        if(!data.test(document.pointBean.gpsCoordinate.value)){
	         document.pointBean.gpsCoordinate.focus();
	         alert("GPS����ֻ��Ϊ���֣�");
	         return false;
	    }
    }
	if(document.pointBean.addressInfo.value==""){
		alert("Ѳ����ַ����Ϊ��!! ");
		return false;
	}
	if(document.pointBean.subLineID.value==""){
		alert("����Ѳ��β���Ϊ��!! ");
		return false;
	}
    if(document.pointBean.subLineID.value==""){
		alert("����Ѳ��β���Ϊ��!! ");
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
    alert(" �ش��¹ʵ����Ѳ�죡��");
    pointBean.sId.value = pointBean.isFId.value
  }
}
function toGetBack(){
 	var url="${ctx}/pointAction.do?method=queryPoint&pointName=&addressInfo=";
	self.location.replace(url);
}
//-->
</script>
<template:titile value="����Ѳ�����Ϣ"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/pointAction.do?method=addPoint">
  <template:formTable contentwidth="300" namewidth="200">
    <template:formTr name="Ѳ�������">
      <html:text property="pointName" styleClass="inputtext" style="width:200" maxlength="4" onchange="prepareINumber()" onblur="prepareINumber()"/>
    </template:formTr>
    <template:formTr name="Ѳ����ַ" >
      <html:text property="addressInfo" styleClass="inputtext" style="width:200" maxlength="25"/>
    </template:formTr>
    <template:formTr name="GPS����">
      <html:text property="gpsCoordinate" styleClass="inputtext" style="width:200" title="GPS����Ϊ17λ����" maxlength="17"/>
    </template:formTr>
    <template:formTr name="����Ѳ���" >
      <apptag:setSelectOptions valueName="sublineCollection" tableName="sublineinfo" columnName1="sublinename" columnName2="sublineid" region="true"/>
      <html:select property="subLineID" styleClass="inputtext" style="width:200">
        <html:options collection="sublineCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="�Ƿ�ؼ���">
      <html:select property="isFocus" styleId="isFId" styleClass="inputtext" style="width:200" onchange="change(this)">
        <html:option value="0">��</html:option>
        <html:option value="1">��</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="�����߶�˳��" >
      <html:text property="inumber" styleClass="inputtext" style="width:200" maxlength="10"/>
    </template:formTr>

	<template:formTr name="Ѳ�������">
      <html:select property="pointtype" styleClass="inputtext" style="width:200">
        <html:option value="1">ʯ</html:option>
        <html:option value="2">��</html:option>
		<html:option value="3">��</html:option>
		<html:option value="4">ǽ</html:option>
		<html:option value="5">��վ</html:option>
		<html:option value="6">����</html:option>
      </html:select>
    </template:formTr>

    <template:formTr name="״̬"  >
	  <html:select property="state" styleId="sId" styleClass="inputtext" style="width:200" onchange="checked()">
        <html:option value="1">��Ѳ��</html:option>
        <html:option value="0">����Ѳ��</html:option>
      </html:select>
    </template:formTr>

	<template:formSubmit>
      <td>
        <html:submit styleClass="button">����</html:submit>
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
