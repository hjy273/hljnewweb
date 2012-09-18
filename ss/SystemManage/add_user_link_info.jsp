<%@ include file="/common/header.jsp"%>
<html>
	<script language="javascript" type="text/javascript">
</script>
	<script type="text/javascript" src="${ctx}/js/ckeditor/ckeditor.js"></script>
	<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
	<body>
		<c:if test="${link_type=='0' }">
			<template:titile value="��д����������Ϣ" />
		</c:if>
		<c:if test="${link_type=='1' }">
			<template:titile value="��д����������Ϣ" />
		</c:if>
		<html:form action="/user_link.do?method=addUserLink" method="post"
			styleId="form">
			<template:formTable>
				<template:formTr name="��ʾ���" isOdd="false">
					<html:text property="orderNumberStr" styleClass="inputtext required validate-number max-length-8"
						style="width:300" />&nbsp;&nbsp;<font color="red">*</font>
				</template:formTr>
				<template:formTr name="��������" isOdd="true">
					<html:text property="linkName" styleClass="inputtext required"
						style="width:300" />&nbsp;&nbsp;<font color="red">*</font>
				</template:formTr>
				<template:formTr name="���ӵ�ַ" isOdd="true">
					<html:text property="linkAddress" styleClass="inputtext required validate-url"
						style="width:300" value="http://" />&nbsp;&nbsp;<font color="red">*</font>
					<input name="linkType" value="${link_type }" type="hidden" />
				</template:formTr>
				<template:formSubmit>
					<td colspan="text-align:center">
						<html:submit property="action2" styleClass="button">����</html:submit>
						<html:reset styleClass="button">����</html:reset>
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
