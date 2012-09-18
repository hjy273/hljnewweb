<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<script language="javascript" src="/WebApp/js/validation/prototype.js" type=""></script>
	<script language="javascript" src="/WebApp/js/validation/effects.js" type=""></script>
	<script language="javascript" src="/WebApp/js/validation/validation_cn.js" type=""></script>
	<link href="/WebApp/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
	
	<script type='text/javascript' src='/WebApp/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='/WebApp/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='/WebApp/js/extjs/resources/css/ext-all.css' />
	
	<script type='text/javascript' src='/WebApp/linepatrol/js/change_style.js'></script>
	
	<script type='text/javascript'>
		var win;
		
		function close(){
			initOptions();
			win.close();
		}
				    
		function more(){
			window.location.href = '/WebApp/linepatrol/resource/fileDetail.jsp';
		}
	</script>
</head>
<body onload="changeStyle();">
	<template:titile value="更新资料类型" />
	<template:formTable>
		<html:form action="/datumAction.do?method=updateType&id=${datumtype.id}" styleId="form">
			<html:hidden property="id" value="${datumtype.id}"/>
			<template:formTr name="名称">
				<html:text property="name" styleClass="inputtext required" value="${datumtype.name}"/>
			</template:formTr>
			
			<template:formSubmit>
				<td>
					<html:submit property="action" styleClass="button">提交</html:submit>
				</td>
				<td>
					<input type="button" class="button" value="返回" onclick="history.back()" />
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