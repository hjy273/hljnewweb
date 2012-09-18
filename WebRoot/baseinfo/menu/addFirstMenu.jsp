<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>

<script language=javascript src="${ctx}/js/validation/prototype.js" type=""></script>
<script language=javascript src="${ctx}/js/validation/effects.js" type=""></script>
<script language=javascript src="${ctx}/js/validation/validation_cn.js" type=""></script>
<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
<body>
	<template:titile value="添加一级菜单"/>
	<html:form styleId="form" method="Post" action="/MenuManageAction.do?method=addFirstMenu">
		<template:formTable contentwidth="300" namewidth="220">
			<template:formTr name="菜单名称">
				<html:text property="lablename" styleClass="inputtext required" style="width:220" maxlength="40"/>
			</template:formTr>
			<template:formTr name="业务类型">
				<html:select property="businessType" styleClass="inputtext" style="width:220">
		            <html:options  collection="businessTypes" property="value" labelProperty="label"/>
		        </html:select>
			</template:formTr>
			<template:formTr name="显示编号">
				<html:text property="showno" styleClass="inputtext" style="width:220" maxlength="40"/>
			</template:formTr>
			<template:formTr name="图标地址">
				<html:text property="imgurl" styleClass="inputtext" style="width:220" maxlength="40"/>
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