<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<script type="">
function toGetBack(){
 	var url="${ctx}/pointAction.do?method=queryPoint&pointName=&addressInfo=";
	self.location.replace(url);
}
function check(){
  if(document.pointBean.inumber.value!=""){
    var data = /^\d{1,10}$/;//
    if(!data.test(document.pointBean.inumber.value)){
      document.pointBean.inumber.focus();
      alert("��˳ֻ��Ϊ���֣�");
      document.pointBean.inumber.value="";
      return false;
    }
  }
}
</script>
<template:titile value="��ѯѲ�����Ϣ"/>
<html:form method="Post" action="/pointAction.do?method=queryPoint">
  <template:formTable contentwidth="300" namewidth="200">
    <template:formTr name="Ѳ�������">
      <html:text property="pointName" styleClass="inputtext" style="width:200" maxlength="20"/>
    </template:formTr>
    <template:formTr name="Ѳ���λ��" >
      <html:text property="addressInfo" styleClass="inputtext" style="width:200" maxlength="25"/>
    </template:formTr>
    <template:formTr name="GPS����">
      <html:text property="gpsCoordinate" styleClass="inputtext" style="width:200" maxlength="17"/>
    </template:formTr>
    <template:formTr name="����Ѳ���" >
      <apptag:setSelectOptions valueName="sublineCollection" tableName="sublineinfo" columnName1="sublinename" columnName2="sublineid" region="true"/>
      <html:select property="subLineID" styleClass="inputtext" style="width:200">

        <html:options collection="sublineCollection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="�Ƿ�ؼ���">
      <html:select property="isFocus" styleClass="inputtext" style="width:200">
        <html:option value="">����</html:option>
        <html:option value="0">��</html:option>
        <html:option value="1">��</html:option>
      </html:select>
    </template:formTr>

    <template:formTr name="�����߶�˳��" >
      <html:text property="inumber" styleClass="inputtext" style="width:200" maxlength="10" onblur="check()"/>
    </template:formTr>


    <template:formSubmit>
      <td>
        <html:submit styleClass="button">��ѯ</html:submit>
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
