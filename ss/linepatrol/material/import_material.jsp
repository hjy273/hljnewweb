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
				alert("����ĸ�������Ϊ��");
				return false;
			}
			return true;
		}
	</script>
	</head>
	<body onload="changeStyle()">
		<template:titile value="������������" />
		<template:formTable>
			<html:form action="/material_apply.do?method=importMaterial"
				styleId="form" enctype="multipart/form-data"
				onsubmit="return check();">
				<template:formTr name="ģ��">
					<a href="�������������������ģ��.zip" target="_blank">��ȡģ��</a>
				</template:formTr>
				<template:formTr name="����">
					<html:file property="file" styleClass="validate-file-xls">����</html:file>
				</template:formTr>
				<template:formSubmit>
					<td>
						<input type="submit" value="����" />
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