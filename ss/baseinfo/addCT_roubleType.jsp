<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>addCT_roublecode</title>
<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
<script type="">
function sub(){
  for(i=0;i<document.all.code.length;i++){
          if(document.all.code[i].value == document.all.troublecode.value){
            alert("�������Ѿ�����,�����!!!");
            document.all.troublecode.value = "";
            document.all.troublecode.focus();
            return false;
        }
  }
  return true;
}
function addGoBack(){
    var url="${ctx}/TroubleCodeAction.do?method=load_TroubleType";
    window.location.replace(url);
}
</script>
</head>
<body>
<template:titile value="������������"/>
<html:form method="Post" action="/TroubleCodeAction.do?method=add_TroubleType" styleId="form" onsubmit="return sub();">
  <template:formTable >
    <template:formTr name="���ʹ���" isOdd="false">
      <html:text property="troublecode" styleClass="inputtext required validate-number min-length-3" style="width:200px" maxlength="3" title="������3λ����"/> <font color="red">*</font>
    </template:formTr>
    <template:formTr name="��������">
      <html:text property="troublename" styleClass="inputtext required" style="width:200px" maxlength="10"/> <font color="red">*</font>
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
        <html:submit styleClass="button">����</html:submit>
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
<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
	</script>
</html>
