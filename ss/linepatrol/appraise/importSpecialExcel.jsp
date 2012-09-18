<%@include file="/common/header.jsp"%>
<html>
<head>
	<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
	<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
	
	<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>
	
	<script type="text/javascript">
		function check(){
			if($('file').value == ''){
				alert("导入的附件不能为空");
				return false;
			}
			return true;
		}
		jQuery(document).ready(function() {
			jQuery("#form").bind("submit",function(){
			});
		});
	</script>
</head>
<body onload="changeStyle()">
	<template:titile value="专项考核表导入" />
	<template:formTable>
		<html:form action="/appraiseTableSpecialAction.do?method=importExcel" styleId="form" enctype="multipart/form-data" onsubmit="return check();">
			<template:formTr name="考核期限">
				<html:text property="startDate" styleId="startDate" styleClass="Wdate required" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'%y-%M-%d'})"></html:text>--<html:text property="endDate" styleClass="Wdate required" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'#F{$dp.$D(\'startDate\')}'})"></html:text><font color="red">*</font>
			</template:formTr>			
			<template:formTr name="考核表">
				<html:file property="file" styleId="file" styleClass="validate-file-xls">导入</html:file><font color="red">*</font><html:link action="appraiseTableMonthAction.do?method=exportTableTemplate&templateName=appraiseTableMonth">获得模板</html:link>
			</template:formTr>
			<template:formSubmit>
				<td>
					<input type="submit" class="button" value="导入" />
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
