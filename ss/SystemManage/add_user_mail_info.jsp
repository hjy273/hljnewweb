<%@ include file="/common/header.jsp"%>
<html>
	<script language="javascript" type="text/javascript">
</script>
<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${ctx}/js/ckeditor/ckeditor.js"></script>
	<body>
		<template:titile value="ÃÓ–¥” œ‰–≈œ¢" />
		<html:form action="/user_mail.do?method=addUserMail" method="post"
			styleId="form">
			<template:formTable>
				<template:formTr name="œ‘ æ–Ú∫≈" isOdd="false">
					<html:text property="orderNumberStr" styleClass="inputtext required validate-number max-length-8"
						style="width:300" />&nbsp;&nbsp;<font color="red">*</font>
				</template:formTr>
				<template:formTr name="” œ‰√˚≥∆" isOdd="true">
					<html:text property="mailName" styleClass="inputtext required"
						style="width:300" />&nbsp;&nbsp;<font color="red">*</font>
				</template:formTr>
				<template:formTr name="” œ‰µÿ÷∑" isOdd="true">
					<html:text property="emailAddress" styleClass="inputtext required validate-email"
						style="width:300" />&nbsp;&nbsp;<font color="red">*</font>
				</template:formTr>
				<template:formSubmit>
					<td colspan="text-align:center">
						<html:submit property="action2" styleClass="button">±£¥Ê</html:submit>
						<html:reset styleClass="button">÷ÿÃÓ</html:reset>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
	<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
	</script>
</html>
