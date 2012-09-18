<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script src="${ctx}/js/validation/prototype.js" type="text/javascript"></script>
<script src="${ctx}/js/validation/effects.js" type="text/javascript"></script>
<script src="${ctx}/js/validation/validation_cn.js" type="text/javascript"></script>
<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${ctx}/js/wdatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/js/extjs/resources/css/ext-all.css" />
<script type="text/javascript" src="${ctx}/js/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${ctx}/js/extjs/ext-all.js"></script>
<style type="text/css">
.input_line {
	BORDER-TOP-STYLE: none;
	BORDER-BOTTOM: #cccccc 1px solid;
	BORDER-RIGHT-STYLE: none;
	BORDER-LEFT-STYLE: none;
	TEXT-ALIGN: center;
	COLOR: RED
}
</style>

<head>
	<script language="javascript">
</script>
</head>
<body>
	<template:titile value="查询断电基站统计" />
	<s:form action="oeAttemperTaskAction_queryOeAttemperTask"  id="form" method="post">
		<template:formTable contentwidth="400" namewidth="220">
				<template:formTr name="所在区县">
					<s:select list="%{#session.DISTRICTS}" name="district" listKey="key" listValue="value" cssClass="inputtext"
					cssStyle="width:196px"  headerKey="" headerValue="=请选择=" />
				</template:formTr>
				<template:formTr name="查询月份">
					<input name="time" class="inputtext required" style="width: 196px"
						onfocus="WdatePicker({dateFmt:'yyyy-MM'})" readonly />
				</template:formTr>
		<template:formSubmit>
			<td>
				<html:submit styleClass="button">查询</html:submit>
			</td>
			<td>
				<input type="button" class="button" onclick=history.back();
					value="返回">
			</td>
		</template:formSubmit>
	</template:formTable>
	</s:form>
	<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
		</script>
</body>