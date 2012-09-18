<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
	<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">

	<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>
	
	<script type='text/javascript'>
		function check(){
			if(document.getElementById("name").value=="null"){
				alert("分类名称不能为null");
				return false;
			}
			return true;
		}
	</script>
</head>
<body onload="changeStyle()">
	<template:titile value="添加资料分类" />
	<template:formTable>
		<html:form action="/datumAction.do?method=addType" styleId="form" onsubmit="return check();">
			<template:formTr name="名称">
				<html:text property="name" styleId="name" styleClass="inputtext required" style="width:200px" />
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