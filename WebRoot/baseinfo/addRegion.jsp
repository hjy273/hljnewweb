<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
<script language="javascript" type="">
<!--
function addGoBack(){
	try{
       location.href = "${ctx}/baseinfo/queryregionresult.jsp";
        return true;
     }catch(e){
         alert(e);
     }
}
function isValidForm() {

    var chinses = /^\d\d\d\d\d\d$/;///^[\u0391-\uFFE5]+$/;
    if(document.regionBean.regionID.value==""){
		alert("����д�������!! ");
		return false;
	}
    if(!chinses.test(document.regionBean.regionID.value)){
        document.regionBean.regionID.value = "";
        document.regionBean.regionID.focus();
        alert("������벻�����ֻ򳤶Ȳ���6λ����");
        return false;
    }
	if(document.regionBean.regionName.value==""){
		alert("����д����!! ");
		return false;
	}
    //��������һ�δ��룬���Կ����������Ʋ���Ϊ�ո�.Add by Steven Mar 16,2007
    if(document.regionBean.regionName.value.trim()==""){
      alert("�������Ʋ���Ϊ�ո�!! ");
      document.regionBean.regionName.focus();
      return false;
    }
	if(document.regionBean.regionDes.value==""){
		alert("����д����˵��!! ");
		return false;
	}
     if(document.regionBean.regionDes.value.trim()==""){
      alert("����˵������Ϊ�ո�!! ");
      document.regionBean.regionDes.focus();
      return false;
    }
   // return false;
}
function toGetBack(){
       window.history.go(-1);
}

//-->
</script>
<apptag:checkpower thirdmould="70102" ishead="1">
	<jsp:forward page="../globinfo/powererror.jsp"/>
</apptag:checkpower>
<template:titile value="����������Ϣ"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/regionAction.do?method=addRegion">
  <template:formTable contentwidth="300" namewidth="200">
    <template:formTr name="�������" >
      <html:text property="regionID" styleClass="inputtext" style="width:200" maxlength="6"  title="������6λ����"/>
    </template:formTr>
    <template:formTr name="��������">
      <html:text property="regionName" styleClass="inputtext" style="width:200" maxlength="10"/>
    </template:formTr>
    <template:formTr name="�ϼ�����" >
      <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname"  columnName2="regionid" region="true" order="regionid" condition="substr(REGIONID,3,4) != '1111' "/>
      <html:select property="parentregionid" styleClass="inputtext" style="width:200">
      	<html:option value="">��</html:option>
        <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="����˵��">
      <html:text property="regionDes" styleClass="inputtext" style="width:200" maxlength="50"/>
    </template:formTr>
    <!--
    <template:formTr name="״̬" >
      <html:select property="state" styleClass="inputtext" style="width:200">
        <html:option value="1">����</html:option>
        <html:option value="2">��ͣ</html:option>
        <html:option value="3">ֹͣ</html:option>
      </html:select>
    </template:formTr>-->
    <template:formSubmit>
      <td>
        <html:submit styleClass="button" >����</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">ȡ��</html:reset>
      </td>
      <td>
        <input type="button" class="button" onclick="addGoBack()" value="����" >
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
