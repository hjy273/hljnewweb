<%@include file="/common/header.jsp"%>
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
	return true;
  }

}
function addGoBack(){
    var url="${ctx}/TroubleCodeAction.do?method=load_TroubleCode";
    window.location.replace(url);
}
</script>
</head>
<body>
<template:titile value="����������"/>
<html:form method="Post" action="/TroubleCodeAction.do?method=add_TroubleCode" styleId="form" onsubmit="return sub();">
  <template:formTable >
    <template:formTr name="��������" isOdd="false">
      <html:text property="troublecode" styleClass="inputtext required validate-number max-length-8" style="width:200px" maxlength="3" title="������3λ����"/>
    </template:formTr>
    <template:formTr name="��������">
      <html:text property="troublename" styleClass="inputtext required" style="width:200px" maxlength="10"/>
    </template:formTr>
    <template:formTr name="��������">
      <select name="troubletype" class="selecttext" style="width:200px">
        <logic:present name="type">
          <logic:iterate id="typeId" name="type">
            <option value="<bean:write name="typeId" property="code"/>">
              <bean:write name="typeId" property="typename"/>
            </option>
          </logic:iterate>
        </logic:present>
      </select>
    </template:formTr>
	<template:formTr name="��Ӧ����">
		<html:text property="noticeCycle" styleClass="inputtext validate-number" value="24" style="width:200px" maxlength="45"/>
        &nbsp;(��λ��Сʱ)
    </template:formTr>
    <template:formTr name="��������" isOdd="false">
		<html:text property="handleCycle" styleClass="inputtext validate-number" value="24" style="width:200px" maxlength="45"/>
        &nbsp;(��λ��Сʱ)
    </template:formTr>
    <template:formTr name="��������" isOdd="false">
      <select name="emergencylevel" class="selecttext" style="width:200px">
        <option value="1">��΢</option>
        <option value="2">�ж�</option>
        <option value="3">����</option>
      </select>
      <select name="code" style="display:none">
        <logic:iterate id="allcodeId" name="allcode">
          <option value="<bean:write name="allcodeId" property="troublecode"/>">
            <bean:write name="allcodeId" property="troublename"/>
          </option>
        </logic:iterate>
      </select>
    </template:formTr>
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
