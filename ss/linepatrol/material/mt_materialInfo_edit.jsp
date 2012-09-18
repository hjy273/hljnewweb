<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<script type="text/javascript" src="${ctx}/js/prototype.js"></script>
<script type="text/javascript"
			src="${ctx}/js/validate.js"></script>
<script language="javascript">
function isValidForm() {
    var myform = document.forms[0];
    var length = myform.remark.value.getGBLength();
    var modelid = document.getElementById("modelid");
    if(myform.name.value==''){
       alert('材料名称不能为空！');
     myform.name.focus();
        return false;
    }
     if(myform.modelid.value==""){
    alert("该类型下没有相应的规格! ");
	        myform.modelid.focus();
			return false;
    }
if(jQuery.trim(myform.name.value)==""){
		alert("材料名称不能为空格!! ");
        myform.name.focus();
		return false;
	}
	 if(myform.factory.value==''){
       alert('生产厂家不能为空！');
     myform.factory.focus();
        return false;
    }
if(jQuery.trim(myform.factory.value)==""){
		alert("生产厂家不能为空格!! ");
        myform.factory.focus();
		return false;
	}
if(length>512){
		alert("备注最多输入256个汉字或者512个字符！");
		myform.remark.focus();
		return false;
}
	processBar(updatePartBase);
	return true;
}

/*根据材料ID，获取相应的类型信息*/
function changeModel(){
var myform = document.forms[0];
   var mid = myform.typeid.value;
    var urls = '${ctx}/MTInfoAction.do?method=queryTypeList&id='+mid+'&rnd='+Math.random();
    var pars = 'id='+mid+'&rnd='+Math.random();
    $("modelDiv").innerHTML = "<img src='${ctx}/images/indicator.gif'>";
     var myAjax = new Ajax.Updater('modelDiv',urls,
     {
      method: 'post',
      parapmeters: pars,
      onSuccess:showRs,
      onComplete: showRs
     });
}

function showRs(rs){
}
</script>

<body>
 <div id="groupDivId">
<template:titile value="修改材料信息"/>

<html:form onsubmit="return isValidForm()" method="Post" action="/materialInfoAction.do?method=updatePartBase" styleId="updatePartBase">
  <template:formTable contentwidth="260" namewidth="260">
  <html:hidden property="id"/>
    <template:formTr name="材料名称">
      <html:text property="name" name="MaterialInfoBean" styleClass="inputtext" style="width:205px" maxlength="50"/>
    </template:formTr>
    <template:formTr name="材料类型" isOdd="false">
      <html:select property="typeid" styleClass="inputtext" style="width:205px" onchange="changeModel()">
        <html:options collection="typelist" property="id" labelProperty="typename"/>
      </html:select>
    </template:formTr>
   <template:formTr name="材料规格" isOdd="false">
   <div id="modelDiv" name="modelDiv">
      <html:select property="modelid" styleClass="inputtext" style="width:205px">
        <html:options collection="modellist" property="id" labelProperty="modelname"/>
      </html:select>
      </div>
    </template:formTr>
    <template:formTr name="生产厂家">
      <html:text property="factory" name="MaterialInfoBean" styleClass="inputtext" style="width:205px" maxlength="200"/>
    </template:formTr>
    <template:formTr name="备注">
      <html:textarea property="remark" name="MaterialInfoBean" styleClass="inputtext" style="width:260px;height:100px;" cols="10" rows="10"/>
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
