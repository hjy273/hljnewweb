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
            alert("隐患码已经存在,请更换!!!");
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
<template:titile value="增加隐患码"/>
<html:form method="Post" action="/TroubleCodeAction.do?method=add_TroubleCode" styleId="form" onsubmit="return sub();">
  <template:formTable >
    <template:formTr name="隐患代码" isOdd="false">
      <html:text property="troublecode" styleClass="inputtext required validate-number max-length-8" style="width:200px" maxlength="3" title="必须是3位数字"/>
    </template:formTr>
    <template:formTr name="隐患名称">
      <html:text property="troublename" styleClass="inputtext required" style="width:200px" maxlength="10"/>
    </template:formTr>
    <template:formTr name="隐患类型">
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
	<template:formTr name="响应周期">
		<html:text property="noticeCycle" styleClass="inputtext validate-number" value="24" style="width:200px" maxlength="45"/>
        &nbsp;(单位是小时)
    </template:formTr>
    <template:formTr name="处理周期" isOdd="false">
		<html:text property="handleCycle" styleClass="inputtext validate-number" value="24" style="width:200px" maxlength="45"/>
        &nbsp;(单位是小时)
    </template:formTr>
    <template:formTr name="隐患级别" isOdd="false">
      <select name="emergencylevel" class="selecttext" style="width:200px">
        <option value="1">轻微</option>
        <option value="2">中度</option>
        <option value="3">严重</option>
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
       <html:submit styleClass="button">增加</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
      <td>
        <input type="button" class="button" onclick="addGoBack()" value="返回">
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
