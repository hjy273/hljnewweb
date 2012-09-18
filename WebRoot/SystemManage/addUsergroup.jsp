<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>

<html>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
<META HTTP-EQUIV="expires" CONTENT="0">
<head>
<script language="JavaScript" src="${ctx}/js/dhtmlxTree/dhtmlxcommon.js"></script>
<script language="JavaScript" src="${ctx}/js/dhtmlxTree/dhtmlxtree.js"></script>
<script language="JavaScript" src="${ctx}/js/dhtmlxTree/ext/dhtmlxtree_json.js"></script>
<script language=javascript src="${ctx}/js/validation/prototype.js" type=""></script>
<script language=javascript src="${ctx}/js/validation/effects.js" type=""></script>
<script language=javascript src="${ctx}/js/validation/validation_cn.js" type=""></script>
<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
<link rel="STYLESHEET" type="text/css" href="${ctx}/js/dhtmlxTree/dhtmlxtree.css">
<script language="javascript" type="">
var usertype="${LOGIN_USER.type}";
<!--
function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}

function isValidForm() {
    var len=0;var str="";
    if($("groupname").value.length == 0||$("groupname").value.trim()==""){
        alert("角色名称不能为空或者空格！");
        $("groupname").value="";
        $("groupname").focus();
        return false;
    }

    $("groupname").value=$("groupname").value.trim();
    if($("remark").value.length>=50){
        alert("备注不能超过50个字符！");
        $("remark").value=$("remark").value.substring(0,50);
        $("remark").focus();
        return false;
    }

    return true;
}

function saveValue(){
	$("menus").value = tree.getAllChecked();
	//if($("menus").value==""){
	//	return false;
	//}

}

var enlargeFlag = 1;

function makeSize(){
    if(enlargeFlag == 0){
		$("info").style.display="";
       	$("moduleDiv").style.display = "none";
		$("info").innerHTML='<img src="${ctx}/images2/indicator.gif">正在加载……';
        enlargeFlag = 1;
    }else{
		$("info").style.display="none";
        $("moduleDiv").style.display = "";
        enlargeFlag = 0;
    }
}
var userType=1;
function changeGroup(){
	makeSize();
	userType=$("type").value;
	init();
}

</script>
</head>
<body >
<template:titile value="增加角色"/>
<html:form method="Post"  styleId="addusergroup" onsubmit="return saveValue()" action="/UsergroupAction.do?method=addUsergroup">
  <input type="hidden" name="menus" value="">
  <template:formTable contentwidth="400" namewidth="250">
    <template:formTr name="角色名称">
      <html:text property="groupname" styleClass="required"  style="width:160" maxlength="45"/>
    </template:formTr>
    <template:formTr name="用户类型" >
    	<apptag:setSelectOptions columnName2="code" columnName1="typename" valueName="typeCollection" tableName="depttype"/>
    	<html:select property="type" styleClass="required" style="width:160" onchange="changeGroup()">
    		<html:options collection="typeCollection" property="value" labelProperty="label"/>
    	</html:select>
     
    </template:formTr>
   <template:formTr name="区域" >
      <apptag:setSelectOptions valueName="parentRegionCellection" tableName="region" columnName1="regionname" region="true" columnName2="regionid" condition="substr(REGIONID,3,4) != '1111' " order="regionid"/>
      <html:select property="regionid"  styleId="regionidId" styleClass="required"  style="width:160">
        <html:options collection="parentRegionCellection" property="value" labelProperty="label"/>
      </html:select>
    </template:formTr>

    <template:formTr name="管理模块">

        <div id="info" style="display:"><img src="${ctx}/images2/indicator.gif">正在加载……</div>
        <div id ="moduleDiv" style="width:400;height:300;display:none"></div>
		<script>
			function init(){
	        	var url = "${ctx}/UsergroupAction.do?method=getModule2Edit&userType="+userType;
	        	var myAjax = new Ajax.Request( url , {
	                    method: 'post',
	                    asynchronous: 'true',
	                    onComplete: showTree }
	            );
            }
        	function showTree(response){
        		$("add").disabled = false;
				${"moduleDiv"}.innerHTML="";//清空modulediv 防止重新加载缓存问题
				tree=new dhtmlXTreeObject("moduleDiv","100%","100%",0);
				tree.setImagePath("${ctx}/js/dhtmlxTree/imgs/csh_bluebooks/");
				tree.enableCheckBoxes(true);
				tree.enableThreeStateCheckboxes(true);
				eval("var json="+response.responseText);
				//alert(response.responseText);
				tree.loadJSONObject(json);
				makeSize();
			}
			init();
		</script>
    </template:formTr>

    <template:formTr name="备注">
        <html:textarea property="remark" styleClass="inputtext" style="width:160;height:40"/>
    </template:formTr>

    <template:formSubmit>
        <html:submit styleClass="button" styleId="add" property="action" disabled="true">增加</html:submit>
        <html:reset styleClass="button">取消</html:reset>
    </template:formSubmit>
  </template:formTable>

</html:form>
<script type="text/javascript">
		function formCallback(result, form) {
			window.status = "valiation callback for form '" + form.id + "': result = " + result;
		}

		var valid = new Validation('addusergroup', {immediate : true, onFormValidate : formCallback});
</script>
</body>

</html>