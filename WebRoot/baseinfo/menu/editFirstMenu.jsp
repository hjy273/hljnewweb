<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>

<script language=javascript src="${ctx}/js/validation/prototype.js" type=""></script>
<script language=javascript src="${ctx}/js/validation/effects.js" type=""></script>
<script language=javascript src="${ctx}/js/validation/validation_cn.js" type=""></script>
<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">

<body>
	<template:titile value="�޸�һ���˵�"/>
	<html:form styleId="form" method="Post" action="/MenuManageAction.do?method=editFirstMenu">
		<template:formTable contentwidth="300" namewidth="220">
			<template:formTr name="�˵�id">
				${MenuBean.id}
				<input type="hidden" name="id" value="${MenuBean.id}">
			</template:formTr>
			<template:formTr name="�˵�����">
				<html:text property="lablename" styleClass="inputtext required" style="width:220" maxlength="40"/>
			</template:formTr>
			<template:formTr name="ҵ������">
				<html:select property="businessType" styleClass="inputtext" style="width:220">
		            <html:options collection="resource" property="value" labelProperty="label"/>
		        </html:select>
			</template:formTr>
			<template:formTr name="���">
				<html:text property="showno" styleClass="inputtext required validate-integer" style="width:220" maxlength="40"/>
			</template:formTr>
			<template:formTr name="ͼƬ·��">
				<html:text property="imgurl" styleClass="inputtext" style="width:220" maxlength="40"/>
			</template:formTr>
			<template:formTr name="��ע">
				<html:text property="remark" styleClass="inputtext" style="width:220" maxlength="40"/>
			</template:formTr>
			<template:formSubmit>
		        	<html:submit styleClass="button" property="action">�޸�</html:submit>
		      	  	<input type="button" class="button" onclick="history.back()" value="����" >
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