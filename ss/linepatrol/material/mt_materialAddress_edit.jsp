<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<jsp:useBean id="statdao" class="com.cabletech.statistics.dao.StatDao" scope="request"/>
<script type="text/javascript"
			src="${ctx}/js/validate.js"></script>
<script language="javascript">
function isValidForm() {
    var myform = document.forms[0];
    var length = myform.remark.value.getGBLength();
    if(myform.address.value==''){
       alert('���ϴ�ŵص㲻��Ϊ�գ�');
     myform.address.focus();
        return false;
    }
if(jQuery.trim(myform.address.value)==""){
		alert("���ϴ�ŵص㲻��Ϊ�ո�!! ");
        myform.address.focus();
		return false;
	}
	if(length>1024){
		alert("��ע�������512�����ֻ���1024���ַ���");
		myform.remark.focus();
		return false;
}
	processBar(updatePartAddress);
	return true;
}


</script>
<br>
<template:titile value="�޸Ĳ��ϴ����Ϣ"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/MTAddressAction.do?method=updatePartAddress" styleId="updatePartAddress">
  <template:formTable contentwidth="400" namewidth="100">
    <html:hidden property="id"/>
    <template:formTr name="���ϴ������">
      <html:text property="address" styleClass="inputtext" name="bean" style="width:205px" maxlength="200"/>
    </template:formTr>
      <template:formTr name="��ά��λ" isOdd="false">
        <html:select property="contractorid" styleClass="inputtext" style="width:205px">
          <html:options collection="deptCollection" property="value" labelProperty="label"/>
        </html:select>
      </template:formTr>
     <template:formTr name="��ע">
      <html:textarea property="remark" styleClass="inputtext" name="bean" style="width:205px;height:100px;" cols="10" rows="10"/>
    </template:formTr>
       <template:formSubmit>
      <td>
        <html:submit styleClass="button" >����</html:submit>
      </td>
      <td>
       <!--  <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="����" >
      -->
      <input type="button" class="button" onclick="history.back()" value="����"/>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
