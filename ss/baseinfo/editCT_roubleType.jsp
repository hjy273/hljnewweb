<%@include file="/common/header.jsp"%>
<html>
<head>
<title>addCT_roublecode</title>
<script type="">
function sub(){
  var split = /^\d\d\d$/;
  if(!split.test(document.all.troublecode.value)){
    alert("隐患代码必须是数字");
    document.all.troublecode.value = "";
    document.all.troublecode.focus();
    return;
  }
  if(document.all.troublename.value==""){
    alert("必须填写隐患名称!!!");
    document.all.troublename.focus();
    return;
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
<template:titile value="修改隐患类型"/>
<html:form method="Post" action="/TroubleCodeAction.do?method=edit_TroubleType">
    <html:text property="id" name="bean"  style="display:none" >
    </html:text>
  <template:formTable >
    <template:formTr name="类型代码" isOdd="false">
      <html:text property="troublecode"  readonly="true" name="bean" styleClass="inputtext" style="width:200px" maxlength="3" title="必须是3位数字"/>
    </template:formTr>
    <template:formTr name="类型名称">
      <html:text property="troublename" name="bean" styleClass="inputtext" style="width:200px" maxlength="10"/>
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
