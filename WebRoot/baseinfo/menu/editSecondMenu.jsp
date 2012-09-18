<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>

<script language=javascript src="${ctx}/js/validation/prototype.js" type=""></script>
<script language=javascript src="${ctx}/js/validation/effects.js" type=""></script>
<script language=javascript src="${ctx}/js/validation/validation_cn.js" type=""></script>
<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">

<body>
	<template:titile value="修改二级菜单"/>
	<html:form styleId="form" method="Post" action="/MenuManageAction.do?method=editSecondMenu">
		<template:formTable contentwidth="300" namewidth="220">
			<template:formTr name="菜单id">
				${MenuBean.id}
				<input type="hidden" name="id" value="${MenuBean.id}">
			</template:formTr>
			<template:formTr name="菜单名称">
				<html:text property="lablename" styleClass="inputtext required" style="width:220" maxlength="40"/>
			</template:formTr>
			<template:formTr name="上一级菜单">
				<html:select property="parentid" styleClass="inputtext" style="width:220">
		            <html:options collection="parentList" property="value" labelProperty="label"/>
		        </html:select>
			</template:formTr>
			<template:formTr name="序号">
				<html:text property="showno" styleClass="inputtext required validate-integer" style="width:220" maxlength="40"/>
			</template:formTr>
			<template:formTr name="是否可用">
				<html:select property="type" styleClass="inputtext" style="width:220">
		            <html:options collection="typeList" property="value" labelProperty="label"/>
		        </html:select>
			</template:formTr>
			<template:formTr name="备注">
				<html:text property="remark" styleClass="inputtext" style="width:220" maxlength="40"/>
			</template:formTr>
			<template:formSubmit>
		        	<html:submit styleClass="button" property="action">修改</html:submit>
		      	  	<input type="button" class="button" onclick="history.back()" value="返回" >
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