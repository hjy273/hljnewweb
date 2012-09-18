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
       alert('材料存放地点不能为空！');
     myform.address.focus();
        return false;
    }
if(jQuery.trim(myform.address.value)==""){
		alert("材料存放地点不能为空格!! ");
        myform.address.focus();
		return false;
	}
	if(length>1024){
		alert("备注最多输入512个汉字或者1024个字符！");
		myform.remark.focus();
		return false;
}
	processBar(updatePartAddress);
	return true;
}


</script>
<br>
<template:titile value="修改材料存放信息"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/MTAddressAction.do?method=updatePartAddress" styleId="updatePartAddress">
  <template:formTable contentwidth="400" namewidth="100">
    <html:hidden property="id"/>
    <template:formTr name="材料存放名称">
      <html:text property="address" styleClass="inputtext" name="bean" style="width:205px" maxlength="200"/>
    </template:formTr>
      <template:formTr name="代维单位" isOdd="false">
        <html:select property="contractorid" styleClass="inputtext" style="width:205px">
          <html:options collection="deptCollection" property="value" labelProperty="label"/>
        </html:select>
      </template:formTr>
     <template:formTr name="备注">
      <html:textarea property="remark" styleClass="inputtext" name="bean" style="width:205px;height:100px;" cols="10" rows="10"/>
    </template:formTr>
       <template:formSubmit>
      <td>
        <html:submit styleClass="button" >更新</html:submit>
      </td>
      <td>
       <!--  <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="返回" >
      -->
      <input type="button" class="button" onclick="history.back()" value="返回"/>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
