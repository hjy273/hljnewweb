<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<html>
<head>
<title>addCT_roublecode</title>
<script type="">
function sub(){
  var split = /^\d\d\d$/;
  if(!split.test(document.all.troublecode.value)){
    alert("�¹ʴ������������");
    document.all.troublecode.value = "";
    document.all.troublecode.focus();
    return;
  }
  if(document.all.troublename.value==""){
    alert("������д�¹�����!!!");
    document.all.troublename.focus();
    return;
  }

  document.all.TroubleCodeBean.submit();
}
function addGoBack(){
    var url="${ctx}/TroubleCodeAction.do?method=load_TroubleCode";
    window.location.replace(url);
}
</script>
</head>
<body>
<template:titile value="�޸��¹�����"/>
<html:form method="Post" action="/TroubleCodeAction.do?method=edit_TroubleType">
    <html:text property="id" name="bean"  style="display:none" >
    </html:text>
  <template:formTable contentwidth="300" namewidth="200">
    <template:formTr name="���ʹ���" >
      <html:text property="troublecode"  readonly="true" name="bean" styleClass="inputtext" style="width:200" maxlength="3" title="������3λ����"/>
    </template:formTr>
    <template:formTr name="��������">
      <html:text property="troublename" name="bean" styleClass="inputtext" style="width:200" maxlength="10"/>
    </template:formTr>

      <select name="code" style="display:none">
        <logic:iterate id="allcodeId" name="alltype">
          <option value="<bean:write name="allcodeId" property="typecode"/>">
            <bean:write name="allcodeId" property="typename"/>
          </option>
        </logic:iterate>
      </select>
    <template:formSubmit>
      <td>
        <html:button property="" styleClass="button" onclick="sub()">�޸�</html:button>
      </td>
      <td>
        <html:reset styleClass="button">ȡ��</html:reset>
      </td>
      <td>
        <input type="button" class="button" onclick="addGoBack()" value="����">
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
</body>
</html>
