<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<html>
<head>
<title>addCT_roublecode</title>
<script type="">
function sub(){
  var split = /^\d\d\d$/;
  if(!split.test(document.all.troublecode.value)){
    alert("事故代码必须是数字");
    document.all.troublecode.value = "";
    document.all.troublecode.focus();
    return;
  }
  if(document.all.troublename.value==""){
    alert("必须填写事故名称!!!");
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
<template:titile value="修改事故码"/>
<html:form method="Post" action="/TroubleCodeAction.do?method=edit_TroubleCode">
    <html:text property="id" name="bean"  style="display:none" >
    </html:text>
  <template:formTable contentwidth="300" namewidth="200">
    <template:formTr name="事故代码" >
      <html:text property="troublecode"  readonly="true" name="bean" styleClass="inputtext" style="width:200" maxlength="3" title="必须是3位数字"/>
    </template:formTr>
    <template:formTr name="事故名称">
      <html:text property="troublename" name="bean" styleClass="inputtext" style="width:200" maxlength="10"/>
    </template:formTr>
    <template:formTr name="事故类型">
      <bean:define id="temp" name="bean" property="troubletype" type="java.lang.String"/>
      <select name="troubletype" class="selecttext" style="width:200">
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
		<html:text property="noticeCycle" name="bean" styleClass="inputtext" style="width:200" maxlength="45"/>
        &nbsp;(单位是小时)
    </template:formTr>
    <template:formTr name="处理周期" >
		<html:text property="handleCycle" name="bean" styleClass="inputtext" style="width:200" maxlength="45"/>
        &nbsp;(单位是小时)
    </template:formTr>
    <template:formTr name="事故级别" >
      <select name="emergencylevel" class="selecttext" style="width:200">
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
        <html:button property="" styleClass="button" onclick="sub()">修改</html:button>
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
</html>
