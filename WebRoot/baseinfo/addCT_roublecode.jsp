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
  for(i=0;i<document.all.code.length;i++){
          if(document.all.code[i].value == document.all.troublecode.value){
            alert("事故码已经存在,请更换!!!");
            document.all.troublecode.value = "";
            document.all.troublecode.focus();
            return;
        }
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
<template:titile value="增加事故码"/>
<html:form method="Post" action="/TroubleCodeAction.do?method=add_TroubleCode">
  <template:formTable contentwidth="300" namewidth="200">
    <template:formTr name="事故代码" >
      <html:text property="troublecode" styleClass="inputtext" style="width:200" maxlength="3" title="必须是3位数字"/>
    </template:formTr>
    <template:formTr name="事故名称">
      <html:text property="troublename" styleClass="inputtext" style="width:200" maxlength="10"/>
    </template:formTr>
    <template:formTr name="事故类型">
      <select name="troubletype" class="selecttext" style="width:200">
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
		<html:text property="noticeCycle" styleClass="inputtext" value="24" style="width:200" maxlength="45"/>
        &nbsp;(单位是小时)
    </template:formTr>
    <template:formTr name="处理周期" >
		<html:text property="handleCycle" styleClass="inputtext" value="24" style="width:200" maxlength="45"/>
        &nbsp;(单位是小时)
    </template:formTr>
    <template:formTr name="事故级别" >
      <select name="emergencylevel" class="selecttext" style="width:200">
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
        <html:button property="" styleClass="button" onclick="sub()">增加</html:button>
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
