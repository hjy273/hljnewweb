<%@include file="/common/header.jsp"%>
<html>
<head>
<title>addCT_roublecode</title>
<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
<script type="">
function addGoBack(){
    var url="${ctx}/TroubleCodeAction.do?method=load_TroubleCode";
    window.location.replace(url);
}
</script>
</head>
<body>
<template:titile value="修改隐患码"/>
<html:form method="Post" action="/TroubleCodeAction.do?method=edit_TroubleCode" styleId="form"> 
    <html:text property="id" name="bean"  style="display:none" >
    </html:text>
  <template:formTable>
    <template:formTr name="隐患代码" isOdd="false">
      <html:text property="troublecode"  readonly="true" name="bean" styleClass="inputtext required validate-number max-length-8" style="width:200px" maxlength="3" title="必须是3位数字"/>
    </template:formTr>
    <template:formTr name="隐患名称">
      <html:text property="troublename" name="bean" styleClass="inputtext required" style="width:200px" maxlength="10"/>
    </template:formTr>
    <template:formTr name="隐患类型">
      <bean:define id="temp" name="bean" property="troubletype" type="java.lang.String"/>
      <select name="troubletype" class="selecttext" style="width:200px">
        <logic:present name="type">
          <logic:iterate id="typeId" name="type">
               <logic:equal value="<%=temp%>" name="typeId" property="code" >
                     <option value="<bean:write name="typeId" property="code"/>" selected="selected"><bean:write name="typeId" property="typename"/> </option>
               </logic:equal>
             <logic:notEqual value="<%=temp%>" name="typeId" property="code" >
                     <option value="<bean:write name="typeId" property="code"/>"><bean:write name="typeId" property="typename"/> </option>
             </logic:notEqual>
          </logic:iterate>
        </logic:present>
      </select>
    </template:formTr>
	<template:formTr name="响应周期">
		<html:text property="noticeCycle" name="bean" styleClass="inputtext validate-number" style="width:200px" maxlength="45"/>
        &nbsp;(单位是小时)
    </template:formTr>
    <template:formTr name="处理周期" isOdd="false">
		<html:text property="handleCycle" name="bean" styleClass="inputtext validate-number" style="width:200px" maxlength="45"/>
        &nbsp;(单位是小时)
    </template:formTr>
    <template:formTr name="隐患级别" isOdd="false">
      <select name="emergencylevel" class="selecttext" style="width:200px">
          <logic:equal value="1" name="bean" property="emergencylevel">
             <option value="1" selected="selected">轻微</option>
            <option value="2">中度</option>
            <option value="3">严重</option>
          </logic:equal>
            <logic:equal value="2" name="bean" property="emergencylevel">
             <option value="1" >轻微</option>
            <option value="2" selected="selected">中度</option>
            <option value="3">严重</option>
          </logic:equal>
        <logic:equal value="3" name="bean" property="emergencylevel">
             <option value="1">轻微</option>
            <option value="2">中度</option>
            <option value="3" selected="selected">严重</option>
          </logic:equal>

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
        <html:submit styleClass="button">修改</html:submit>
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
