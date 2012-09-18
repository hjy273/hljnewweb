<%@include file="/common/header.jsp"%>
<jsp:useBean id="statdao" class="com.cabletech.statistics.dao.StatDao" scope="request"/>

<script type="text/javascript"
			src="${ctx}/js/validate.js"></script>
<script language="javascript">
function downloadFile() {
  location.href = "${ctx}/CableAction.do?method=downloadTemplet";
}

function showupload() {
    var objpart = document.getElementById("groupDivId");
    var objup = document.getElementById("upDivId");
    objpart.style.display="none";
    objup.style.display="block";
}

function noupload()
{
    var objpart = document.getElementById("groupDivId");
    var objup = document.getElementById("upDivId");

    objpart.style.display="block";
    objup.style.display="none";
}

function isValidForm() {
    var myform = document.forms[0];
    var length = myform.remark.value.getGBLength();
    if(myform.address.value==''){
       alert('材料存放地点不能为空！');
     myform.address.focus();
        return false;
    }
if(trim(myform.address.value)==""){
		alert("材料存放地点不能为空格!! ");
        myform.address.focus();
		return false;
	}
if(length>1024){
		alert("备注最多输入512个汉字或者1024个字符！");
		myform.remark.focus();
		return false;
}
	return true;
}
</script>

<body>
 <div id="groupDivId">
<template:titile value="增加材料存放地点信息"/>

<html:form onsubmit="return isValidForm()" method="Post" action="/materialAddressAction.do?method=addAddress">
  <template:formTable contentwidth="260" namewidth="260">
    <template:formTr name="存放地点">
      <html:text property="address" styleClass="inputtext" style="width:205" maxlength="200"/>
    </template:formTr>
      <template:formTr name="代维单位" isOdd="false">
        <html:select property="contractorid" styleClass="inputtext" style="width:205">
          <html:options collection="deptCollection" property="value" labelProperty="label"/>
        </html:select>
      </template:formTr>
   
    <template:formTr name="备注">
      <html:textarea property="remark" styleClass="inputtext" style="width:255;height:100;" cols="10" rows="10"/>
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">增加</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">重置</html:reset>
      </td>
      </template:formSubmit>
      </template:formTable>
      </html:form>

