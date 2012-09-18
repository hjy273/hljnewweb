<%@include file="/common/header.jsp"%>
<html>
	<head>
		<script language="javascript" src="${ctx}/js/validation/prototype.js"
			type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js"
			type=""></script>
		<script language="javascript"
			src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css"
			rel="stylesheet" type="text/css">

		<script type='text/javascript'
			src='${ctx}/linepatrol/js/change_style.js'></script>

		<script type="text/javascript">
		function check(){
			if($('file').value == ''){
				alert("导入的附件不能为空");
				return false;
			}
			return true;
		}
	</script>
	</head>
	<body onload="changeStyle()">
		<template:titile value="材料批量导入" />
		<template:formTable>
			<html:form action="/material_apply.do?method=importMaterial"
				styleId="form" enctype="multipart/form-data"
				onsubmit="return check();">
				<template:formTr name="模板">
					<a href="材料入库申请批量导入模板.zip" target="_blank">获取模板</a>
				</template:formTr>
				<template:formTr name="附件">
					<html:file property="file" styleClass="validate-file-xls">导入</html:file>
				</template:formTr>
				<template:formSubmit>
					<td>
						<input type="submit" value="导入" />
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