<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<html>
<head>
<title>addCT_roublecode</title>
<script type="">
function sub(){
  var split = /^\d\d\d$/;
  if(!split.test(document.all.troublecode.value)){
    alert("类型代码必须是数字");
    document.all.troublecode.value = "";
    document.all.troublecode.focus();
    return;
  }
  if(document.all.troublename.value==""){
    alert("必须填写类型名称!!!");
    document.all.troublename.focus();
    return;
  }
  for(i=0;i<document.all.code.length;i++){
          if(document.all.code[i].value == document.all.troublecode.value){
            alert("类型码已经存在,请更换!!!");
            document.all.troublecode.value = "";
            document.all.troublecode.focus();
            return;
        }
  }
  document.all.TroubleCodeBean.submit();
}
function addGoBack(){
    var url="${ctx}/TroubleCodeAction.do?method=load_TroubleType";
    window.location.replace(url);
}
</script>
</head>
<body>
<template:titile value="增加事故类型"/>
<html:form method="Post" action="/TroubleCodeAction.do?method=add_TroubleType">
  <template:formTable contentwidth="300" namewidth="200">
    <template:formTr name="类型代码" >
      <html:text property="troublecode" styleClass="inputtext" style="width:200" maxlength="3" title="必须是3位数字"/>
    </template:formTr>
    <template:formTr name="类型名称">
      <html:text property="troublename" styleClass="inputtext" style="width:200" maxlength="10"/>
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
