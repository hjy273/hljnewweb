<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script src="${ctx}/js/validation/prototype.js" type="text/javascript"></script>
<script src="${ctx}/js/validation/effects.js" type="text/javascript"></script>
<script src="${ctx}/js/validation/validation_cn.js" type="text/javascript"></script>
<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${ctx}/js/wdatePicker/WdatePicker.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/js/wdatePicker/skin/WdatePicker.css">
<head>
<script language="javascript" type="text/javascript">
	function check(){
		if($('oeAttemperTask.blackoutStation').value==""){
			alert("请选择基站！");
			return false;
		}return true;
	}
</script>
</head>
<body>
	<template:titile value="修改告警信息"/>

	<s:form action="oeAttemperTaskAction_update" name="editOeAttemperTaskFrom" method="post" onsubmit="return check()">
		<template:formTable>
		<template:formTr name="断电基站">
				<s:select list="%{#session.BASESTATIONS}" name="oeAttemperTask.blackoutStation" listKey="key" listValue="value" cssClass="inputtext required"
					cssStyle="width:220px"  headerKey="" headerValue="=请选择=" />
		</template:formTr>
		<template:formTr name="断电时间">
			<input name="oeAttemperTask.blackoutTime" value="${oeAttemperTask.blackoutTime}" class="inputtext required" style="width: 220px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly /><span style="color:red">*</span>
		</template:formTr>
		<template:formTr name="断电原因">
			<input type="text" name="oeAttemperTask.blackoutReason" value="${oeAttemperTask.blackoutReason}" class="inputtext required" style="width:220px" maxlength="200" /><span style="color:red">*</span>
		</template:formTr>
		</template:formTable>
		<template:formSubmit>
			<td>
				<html:submit styleClass="button">修改</html:submit>
			</td>
			<td>
				<input type="button" class="button" onclick=history.back();
					value="返回">
			</td>
		</template:formSubmit>
		<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('editOeAttemperTaskFrom', {immediate : true, onFormValidate : formCallback});
		</script>
	</s:form>
</body>
