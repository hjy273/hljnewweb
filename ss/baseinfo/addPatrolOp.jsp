<%@include file="/common/header.jsp"%>

<script language="JavaScript">
function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}
function isValidForm() {
    //部门校验
	if(document.PatrolOpBean.opcode.value.length == 0){
		alert("事故码不能为空!! ");
		PatrolOpBean.opcode.focus();
		return false;
	}
	if(document.PatrolOpBean.operationdes.value.length == 0||document.PatrolOpBean.operationdes.value.trim().length==0){
		alert("事故名称不能为空或者空格!! ");
        PatrolOpBean.operationdes.value="";
		PatrolOpBean.operationdes.focus();
		return false;
	}
    document.PatrolOpBean.operationdes.value=document.PatrolOpBean.operationdes.value.trim();
	PatrolOpBean.operationcode.value = "A" + PatrolOpBean.optype.value + PatrolOpBean.opcode.value;
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
<br>
<template:titile value="增加巡检事故代码"/>
<html:form onsubmit="return isValidForm()" method="Post" action="/PatrolOpAction.do?method=addPatrolOp">
  <template:formTable >

    <template:formTr name="事故类型" isOdd="false">
      <select name="optype" class="inputtext" style="width:200px">
        <option value="1">标石</option>
        <option value="2">人井</option>
		<option value="3">电杆</option>
		<option value="4">其他事故</option>
      <select>
    </template:formTr>

    <template:formTr name="事故码">
      <input type="text" name="opcode" id="Id" class="inputtext"  onchange="valiD(id)" style="width:200px" maxlength="2"/>
		&nbsp;( 不含类别信息 )
    </template:formTr>
    <template:formTr name="事故名称" isOdd="false">
		<html:hidden property="operationcode"/>
		<html:text property="operationdes" styleClass="inputtext" style="width:200px" maxlength="45"/>
    </template:formTr>
	<template:formTr name="响应周期">
		<html:text property="noticecycle" styleClass="inputtext" value="24" style="width:200px" maxlength="45"/>
        &nbsp;(单位是小时)
    </template:formTr>
    <template:formTr name="处理周期" isOdd="false">
		<html:text property="handlecycle" styleClass="inputtext" value="24" style="width:200px" maxlength="45"/>
        &nbsp;(单位是小时)
    </template:formTr>
    <template:formTr name="事故级别">
      <html:select property="emergencylevel" styleClass="inputtext" style="width:200px">
        <html:option value="1">轻微</html:option>
        <html:option value="2">中度</html:option>
        <html:option value="3">严重</html:option>
      </html:select>
	</template:formTr>

    <template:formTr name="备注" isOdd="false">
      <html:text property="remark" styleClass="inputtext" style="width:200px" maxlength="145"/>
    </template:formTr>

    <template:formSubmit>
      <td>
        <html:submit styleClass="button">增加</html:submit>
      </td>
      <td>
        <html:reset styleClass="button">取消</html:reset>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
