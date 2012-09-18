<%@page import="com.cabletech.baseinfo.beans.*"%>
<jsp:useBean id="taskoperationBean" class="com.cabletech.baseinfo.beans.TaskOperationBean" scope="request"/>
<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>

<script language="javascript">
function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}

function isValidForm(form) {
	if(form.operationName.value.length == 0||form.operationName.value.trim().length==0){
		alert("����������Ʋ���Ϊ�ջ��߿ո�!! ");
        form.operationName.value="";
		form.operationName.focus();
		return false;
	}
    form.operationName.value=form.operationName.value.trim();
    if(valCharLength(form.operationName.value)>50){
      alert("����������Ʋ��ܴ���25�����֣���������д��");
      form.operationName.value=cutString(form.operationName.value,150);
      return false;
    }
    if(valCharLength(form.operationDes.value)>150){
      alert("��ע���ܴ���75�����֣���������д��");
      form.operationDes.value=cutString(form.operationDes.value,150);
      return false;
    }
}
function valCharLength(Value){
  var j=0;
  var s = Value;
  for   (var   i=0;   i<s.length;   i++)
  {
    if   (s.substr(i,1).charCodeAt(0)>255)   j   =   j   +   2;
    else   j++;
  }
  return j;
}
function cutString(value,len){
  var s="";var j=0;
  for(i=0;i<value.length;i++){
    if(value.substr(i,1).charCodeAt(0)>255) j=j+2;
    else j++;
    if(j>len) break;
    s=s+value.substr(i,1).charAt(0);
  }
  return s;
}

function getDep(){

    var depV = departBean.regionid.value;
    var URL = "getSelect.jsp?depType=1&selectname=parentID&regionid=" + depV;

    hiddenInfoFrame.location.replace(URL);
}


function toGetBack(){
        var url = "${ctx}/TaskOperationAction.do?method=queryTaskOperation";
        self.location.replace(url);

}


</script>
<apptag:checkpower thirdmould="71404" ishead="5">
<jsp:forward page="/globinfo/powererror.jsp"/>
</apptag:checkpower>
<template:titile value="�޸����������Ϣ"/>
<html:form onsubmit="return isValidForm(this)" method="Post" action="/TaskOperationAction.do?method=updateTaskOperation">
  <template:formTable contentwidth="300" namewidth="200">
    <template:formTr name="�����������">
      <html:hidden property="ID" />
      <html:text property="operationName" styleClass="inputtext" style="width:200"/>
    </template:formTr>
    <template:formTr name="��������"  style="display:none">
      <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" condition="substr(REGIONID,3,4) != '1111' "/>
      <html:select property="regionID" styleClass="inputtext" style="width:200">
        <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>
    <template:formTr name="��ע" >
<!--      <html:text property="operationDes" styleClass="inputtext" style="width:200"  maxlength="145"/>-->
      <html:textarea property="operationDes" styleClass="inputtext" style="width:200;height:50" />
    </template:formTr>

  </template:formTable>
   <template:formSubmit>
      <td>
        <html:submit styleClass="button">����</html:submit>
      </td>
      <td>
        <input type="button" class="button" onclick="toGetBack()" value="����" >
      </td>
    </template:formSubmit>
</html:form>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
