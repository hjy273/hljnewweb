<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
	<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
	
	<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
	
	<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>
	
	<script type='text/javascript'>
		var win;
		function showWin(url){
			win = new Ext.Window({
				layout : 'fit',
				width:500,
				height:300, 
				resizable:true,
				closeAction : 'close',
				modal:true,
				html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="'+url+'" />',
				plain: true
			});
			win.show(Ext.getBody());
		}
		function close(){
			initOptions();
			win.close();
		}
		function addType(){
			showWin('${ctx}/linepatrol/resource/datumTypeAdd.jsp');
		}		    
		function initOptions(){
			$("typeId").length = 0;
			var url = "${ctx}/datumAction.do?method=getType";
			var myAjax = new Ajax.Request( url , {
        	    method: 'get',
        	    asynchronous: 'true',
        	    onComplete: setType }
        	);
		}
		function setType(response){
			var str = response.responseText;
			if(str == "")
				return true;
			var optionlist = str.split("&");
			for(var i=0 ; i<optionlist.length ; i++){
				var t = optionlist[i].split(",")[0];
				var v = optionlist[i].split(",")[1];
				$("typeId").options[i] = new Option(v,t);
			}
		}
		function check(){
			var msg=sendAjax();
			if(msg == ''){
				return true;
				}
			else{
				alert(msg);
				return false;
			}
		}
		function sendAjax(){
			var response = '';
			jQuery.ajax({
			   type: "POST",
			   url: "${ctx}/datumAction.do?method=validateFile",
			   async: false,
			   success: function(msg){
				   response = msg;
			   }
			});
			return response;
		}
	</script>
</head>
<body onload="changeStyle();initOptions()">
	<template:titile value="添加维护资料" />
	<template:formTable>
		<html:form action="/datumAction.do?method=add" styleId="form" onsubmit="return check();">
			<template:formTr name="分类">
				<html:select property="typeId" styleId="typeId" styleClass="inputtext required" style="width:140px" />
				<input type="button" value="添加" onclick="addType()" />
			</template:formTr>
			<template:formTr name="名称">
				<html:text property="name" styleClass="inputtext required" style="width:140px"/>
			</template:formTr>
			<template:formTr name="说明">
				<html:textarea property="info" styleClass="inputtext required" style="width:250px;height:50px" />
			</template:formTr>
			<template:formTr name="资料">
				<apptag:upload state="add" cssClass=""/>
			</template:formTr>
			<template:formSubmit>
				<td>
					<html:submit property="action" styleClass="button">提交</html:submit>
				</td>
			</template:formSubmit>
		</html:form>
	</template:formTable>
	<script type="text/javascript">
		function formCallback(result, form) {
			window.status = "valiation callback for form '" + form.id + "': result = " + result;
		}

		var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
	</script>
</body>
</html>