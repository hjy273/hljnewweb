<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>

<script language=javascript src="${ctx}/js/validation/prototype.js" type=""></script>
<script language=javascript src="${ctx}/js/validation/effects.js" type=""></script>
<script language=javascript src="${ctx}/js/validation/validation_cn.js" type=""></script>
<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">

<script language="javascript">
	function query(){
		var menuGrade = $("menuGrade").options[$("menuGrade").selectedIndex].value;
		var method = "";
		if(menuGrade == "1"){
			method = "getFirstMenu";
		}else if(menuGrade == "2"){
			method = "getSecondMenu";
		}else if(menuGrade == "3"){
			method = "getThirdMenu";
		}
		$("form").action = "${ctx}/MenuManageAction.do?method=" + method;
		$("form").submit();
	}

	function setSelection(obj){
		init();
		var val = obj.options[obj.selectedIndex].value;
		if(val == 2){
			$("firstMenuTr").style.display = "";
			getSelectionFirstMenu();
		}else if(val == 3){
			$("firstMenuTr").style.display = "";
			$("secondMenuTr").style.display = "";
			getSelectionFirstMenu();
		}
	}

	function getSelectionFirstMenu(){
		$("wait1").style.display = "";
		var url = "${ctx}/MenuManageAction.do?method=getFirstMenuSelection";
		var myAjax = new Ajax.Request( url , {
            method: 'get',
            asynchronous: 'true',
            onComplete: setSelectionFirstMenu }
        );
	}

	function setSelectionFirstMenu(response){
		$("wait1").style.display = "none";
		var str = response.responseText;
		if(str == "")
			return true;
		var optionlist = str.split("&");
		for(var i=0 ; i<optionlist.length ; i++){
			var t = optionlist[i].split(",")[0];
			var v = optionlist[i].split(",")[1];
			$("firstMenu").options[i+1] = new Option(t,v);
		}
	}

	function getSelectionSecondMenu(obj){
		$("wait2").style.display = "none";
		$("secondMenu").options.length = 1;
		var val = obj.options[obj.selectedIndex].value;
		var url = "${ctx}/MenuManageAction.do?method=getSecondMenuSelection&id="+val;
		var myAjax = new Ajax.Request( url , {
            method: 'get',
            asynchronous: 'true',
            onComplete: setSelectionSecondMenu }
        );
	}

	function setSelectionSecondMenu(response){
		$("wait2").style.display = "none";
		var str = response.responseText;
		if(str == "")
			return true;
		var optionlist = str.split("&");
		for(var i=0 ; i<optionlist.length ; i++){
			var t = optionlist[i].split(",")[0];
			var v = optionlist[i].split(",")[1];
			$("secondMenu").options[i+1] = new Option(t,v);
		}
	}

	function init(){
		$("firstMenu").options.length = 1;
		$("firstMenuTr").style.display = "none";
		$("secondMenu").options.length = 1;
		$("secondMenuTr").style.display = "none";
		$("wait1").style.display = "none";
		$("wait2").style.display = "none";
	}
</script>

<body onload="init();">
	<template:titile value="菜单管理"/>
	<form id="form" method="Post" action="">
		<template:formTable contentwidth="300" namewidth="220">
			<template:formTr name="菜单等级">
				<select name="menuGrade" style="width:220" class="inputtext" onchange="setSelection(this)">
					<option value="1">一级菜单</option>
					<option value="2">二级菜单</option>
					<option value="3">三级菜单</option>
				</select>
			</template:formTr>
			<template:formTr name="一级菜单" tagID="firstMenuTr">
				<select name="firstMenu" style="width:220" class="inputtext" onchange="getSelectionSecondMenu(this)">
					<option value="">全部</option>
				</select>
				<div id="wait1">
					<img src="${ctx}/images2/indicator.gif">请稍候……
				</div>
			</template:formTr>
			<template:formTr name="二级菜单" tagID="secondMenuTr">
				<select name="secondMenu" style="width:220" class="inputtext">
					<option value="">全部</option>
				</select>
				<div id="wait2">
					<img src="${ctx}/images2/indicator.gif">请稍候……
				</div>
			</template:formTr>
			<template:formTr name="菜单名称">
				<input type="text" name="menuName" style="width:220" class="inputtext validate-chinese">
			</template:formTr>
			<template:formSubmit>
		      		<input type="button" class="button" onclick="query()" value="查询" >
		      	  	<input type="button" class="button" onclick="history.back()" value="返回" >
		    </template:formSubmit>
		</template:formTable>
		<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
		</script>
	</form>
</body>