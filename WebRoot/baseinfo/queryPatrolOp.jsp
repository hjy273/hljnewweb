<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<script language="JavaScript">
function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}
function isValidForm() {
    //部门校验
	if(document.all.opcode.value.length == 0){
		alert("事故码不能为空!! ");
		document.all.opcode.focus();
		return false;
	}
	if(document.all.operationdes.value.length != 0&&document.all.operationdes.value.trim().length==0){
		alert("事故名称不能为空或者空格!! ");
        document.all.operationdes.value="";
		document.all.operationdes.focus();
		return false;
	}
    document.all.operationdes.value=document.all.operationdes.value.trim();
	document.all.operationcode.value = "A" + document.all.optype.value + document.all.opcode.value;
	//alert(PatrolOpBean.operationcode.value);

	return true;
}
 //检验是否数字
    function valiD(id){
    	var mysplit = /\d\d/;
        var obj = document.getElementById(id);
        if(mysplit.test(obj.value)){
          return true;
        }
        else{
        	alert("你填写的不是数字,请重新输入");
             obj.focus();
             obj.value = "";
        }
    }
</script>

<template:titile value="查询巡检事故码信息"/>

<html:form onsubmit="return isValidForm()" method="Post" action="/PatrolOpAction.do?method=queryPatrolOp">
  <template:formTable contentwidth="300" namewidth="200">
	<template:formTr name="事故类型" >
      <select name="optype" class="inputtext" style="width:200">
		<option value="">不限</option>
        <option value="1">标石</option>
        <option value="2">人井</option>
		<option value="3">电杆</option>
		<option value="4">其他事故</option>
      <select>
    </template:formTr>

    <template:formTr name="事故码">
      <input type="text" name="opcode" id="id" onchange="valiD(id)" class="inputtext" style="width:200" maxlength="2"/>
		&nbsp;( 不含类别信息 )
    </template:formTr>

    <template:formTr name="事故名称" >
		<html:hidden property="operationcode"/>
		<html:text property="operationdes" styleClass="inputtext" style="width:200" maxlength="45"/>
    </template:formTr>

		<template:formTr name="事故级别">
      <html:select property="emergencylevel" styleClass="inputtext" style="width:200">
	    <html:option value="">不限</html:option>
        <html:option value="1">轻微</html:option>
        <html:option value="2">中度</html:option>
        <html:option value="3">严重</html:option>
      </html:select>
	</template:formTr>

    <template:formSubmit>
      <td>
        <html:submit styleClass="button">查询</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
    </template:formSubmit>

  </template:formTable>
</html:form>
