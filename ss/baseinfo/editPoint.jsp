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
    //����У��
	if(document.pointBean.pointName.value==""){
		alert("���Ʋ���Ϊ��!! ");
		return false;
	}
	if(document.pointBean.addressInfo.value==""){
		alert("Ѳ����ַ����Ϊ��!! ");
		return false;
	}
	if(document.pointBean.subLineID.value==""){
		alert("����Ѳ��β���Ϊ��!! ");
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
    alert(" �ش��¹ʵ����Ѳ�죡��");
    pointBean.sId.value = pointBean.isFId.value
  }
}

//-->
</script>
<br>
<template:titile value="�޸�Ѳ�����Ϣ"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/pointAction.do?method=updatePoint">
  <template:formTable >
    <template:formTr name="Ѳ�����" isOdd="false">
      <html:text readonly="true" property="pointID" styleClass="inputtext" style="width:200px" maxlength="45"/>
    </template:formTr>
    <template:formTr name="Ѳ�������">
      <html:text property="pointName" styleClass="inputtext" style="width:200px" maxlength="4" onchange="prepareINumber()" onblur="prepareINumber()"/>
    </template:formTr>
    <template:formTr name="Ѳ����ַ" isOdd="false">
      <html:text property="addressInfo" styleClass="inputtext" style="width:200px" maxlength="25"/>
    </template:formTr>
    <template:formTr name="GPS����">
      <html:text property="gpsCoordinate" styleClass="inputtext" style="width:200px" maxlength="17"/>
    </template:formTr>
    <template:formTr name="��������" isOdd="false">
      <apptag:setSelectOptions valueName="regionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" condition="substr(REGIONID,3,4) != '1111' "/>
      <html:select property="regionID" styleClass="inputtext" style="width:200px">
        <html:options collection="regionCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="����Ѳ���">
      <apptag:setSelectOptions valueName="sublineCollection" tableName="sublineinfo" columnName1="sublinename" columnName2="sublineid" region="true"/>
      <html:select property="subLineID" styleClass="inputtext" style="width:200px">
        <html:options collection="sublineCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="�Ƿ�ؼ���" isOdd="false">
      <html:select property="isFocus" styleClass="inputtext" styleId="isFId" onchange="change()" style="width:200px">
        <html:option value="0">��</html:option>
        <html:option value="1">��</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="�����߶�˳��">
      <html:text property="inumber" styleClass="inputtext" style="width:200px" maxlength="10" readonly="true"/>
    </template:formTr>

	<template:formTr name="Ѳ�������"  isOdd="false">
      <html:select property="pointtype" styleClass="inputtext" style="width:200px">
        <html:option value="1">ʯ</html:option>
        <html:option value="2">��</html:option>
		<html:option value="3">��</html:option>
		<html:option value="4">ǽ</html:option>
		<html:option value="5">��վ</html:option>
		<html:option value="6">����</html:option>
      </html:select>
    </template:formTr>


    <template:formTr name="״̬">
	  <html:select property="state" styleClass="inputtext" styleId="sId" onchange="checked()" style="width:200px">
        <html:option value="1">��Ѳ��</html:option>
        <html:option value="0">����Ѳ��</html:option>
      </html:select>
    </template:formTr>



    <template:formSubmit>
      <td>
        <html:submit styleClass="button">����</html:submit>
      </td>
      <td>
        <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="����" >
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
