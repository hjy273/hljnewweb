<%@page import="com.cabletech.baseinfo.beans.*"%>
<%@include file="/common/header.jsp"%>
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

	if(confirm("ȷ��ɾ����Ѳ��㣿")){
				//setMapRefresh('RefreshAllPoint');
				//alert("000");
        var url = "${ctx}/pointAction.do?method=deletePoint4GIS&id=" + idValue;
        self.location.replace(url);

	}
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
function change(){
  var f = pointBean.isFId.value
  if(f==1)
     pointBean.sId.value = f;
}
function checked(){
  if(pointBean.isFId.value == 1){
    alert(" �ؼ������Ѳ�죡��");
    pointBean.sId.value = pointBean.isFId.value
  }
}

//-->
</script><template:titile value="�޸�Ѳ�����Ϣ"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/pointAction.do?method=updatePointForGisWatch">
  <template:formTable>
    <template:formTr name="���" isOdd="false">
      <html:text readonly="true" property="pointID"  styleClass="inputtext" style="width:110" maxlength="45"/>
    </template:formTr>
    <template:formTr name="����">
      <html:text property="pointName" styleClass="inputtext" style="width:110" maxlength="45"/>
    </template:formTr>
    <template:formTr name="��ַ" isOdd="false">
      <html:text property="addressInfo" styleClass="inputtext" style="width:110" maxlength="45"/>
    </template:formTr>
    <template:formTr name="����">
      <html:text property="gpsCoordinate" styleClass="inputtext" style="width:110" maxlength="45"/>
    </template:formTr>
    <template:formTr name="����" isOdd="false">
      <apptag:setSelectOptions valueName="regionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid"/>
      <html:select property="regionID" styleClass="inputtext" style="width:110">
        <html:options collection="regionCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="Ѳ���">
      <apptag:setSelectOptions valueName="sublineCollection" tableName="sublineinfo" columnName1="sublinename" columnName2="sublineid" region="true"/>
      <html:select property="subLineID" styleClass="inputtext" style="width:110">
        <html:options collection="sublineCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="�Ƿ�ؼ���" isOdd="false">
      <html:select property="isFocus" styleClass="inputtext" style="width:110" styleId="isFId" onchange="change()">
        <html:option value="0">��</html:option>
        <html:option value="1">��</html:option>
      </html:select>
    </template:formTr>
    <template:formTr name="����">
      <html:text property="inumber" styleClass="inputtext" style="width:110" maxlength="45"/>
    </template:formTr>

	<template:formTr name="Ѳ�������"  isOdd="false">
      <html:select property="pointtype" styleClass="inputtext" style="width:110">
        <html:option value="1">ʯ</html:option>
        <html:option value="2">��</html:option>
		<html:option value="3">��</html:option>
		<html:option value="4">ǽ</html:option>
		<html:option value="5">��վ</html:option>
		<html:option value="6">����</html:option>
      </html:select>
    </template:formTr>

	<template:formTr name="״̬" >
	  <html:select property="state" styleClass="inputtext" style="width:110" styleId="sId" onchange="checked()">
        <html:option value="1">��Ѳ��</html:option>
        <html:option value="0">����Ѳ��</html:option>
      </html:select>
    </template:formTr>

    <template:formSubmit>
      <td>
        <html:submit styleClass="button" >����</html:submit>
      </td>
      <td>
        <html:button property="action" styleClass="button" onclick="toDelete()">ɾ��</html:button>
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
