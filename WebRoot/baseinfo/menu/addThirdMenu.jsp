<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>

<script language=javascript src="${ctx}/js/validation/prototype.js" type=""></script>
<script language=javascript src="${ctx}/js/validation/effects.js" type=""></script>
<script language=javascript src="${ctx}/js/validation/validation_cn.js" type=""></script>
<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
<head>
<script language="javascript" type="">
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
			$("firstMenu").options[i] = new Option(t,v);
		}
		getSelectionSecondMenu();
	}

	function getSelectionSecondMenu(){
		$("wait2").style.display = "";
		$("parentid").options.length = 0;
		var val = $("firstMenu").options[$("firstMenu").selectedIndex].value;
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
			$("parentid").options[i] = new Option(t,v);
		}
	}
	</script>
</head>
<body onload="getSelectionFirstMenu()">
	<template:titile value="添加三级菜单"/>
	<html:form styleId="form" method="Post" action="/MenuManageAction.do?method=addThirdMenu">
		<template:formTable contentwidth="300" namewidth="220">
			<template:formTr name="菜单名称">
				<html:text property="lablename" styleClass="inputtext required" style="width:220" maxlength="40"/>
			</template:formTr>
			<template:formTr name="一级菜单">
				<select name="firstMenu" id="firstMenu" style="width:220" class="inputtext" onchange="getSelectionSecondMenu()"></select>
				<div id="wait1" style="display:none">
					<img src="${ctx}/images2/indicator.gif">请稍候……
				</div>
			</template:formTr>
			<template:formTr name="二级菜单">
				<html:select property="parentid" styleClass="inputtext required" style="width:220"></html:select>
				<div id="wait2" style="display:none">
					<img src="${ctx}/images2/indicator.gif">请稍候……
				</div>
			</template:formTr>
			<template:formTr name="连接地址">
				<html:text property="hrefurl" styleClass="inputtext required" style="width:220" maxlength="120"/>
			</template:formTr>
			<template:formTr name="显示编号">
				<html:text property="showno" styleClass="inputtext" style="width:220" maxlength="40"/>
			</template:formTr>
			<template:formTr name="是否可用">
				<html:select property="type" styleClass="inputtext" style="width:220">
					<option value=''>可用</option>
					<option value='9'>禁用</option>
				</html:select>
			</template:formTr>
			<template:formTr name="是否为菜单">
				<html:radio property="isMenu"  value="0">功能点</html:radio>
				<html:radio property="isMenu" value="1">菜单</html:radio>
			</template:formTr>
			<template:formTr name="备注">
				<html:text property="remark" styleClass="inputtext" style="width:220" maxlength="40"/>
			</template:formTr>
			<template:formSubmit>
		        	<html:submit styleClass="button" property="action">添加</html:submit>
		    </template:formSubmit>
		</template:formTable>
		<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
		</script>
	</html:form>
</body>