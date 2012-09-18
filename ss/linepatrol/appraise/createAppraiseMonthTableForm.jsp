<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>生成月考核表</title>
		<script type="text/javascript"
			src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
		<script language="javascript" src="${ctx}/js/validation/prototype.js"
			type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js"
			type=""></script>
		<script language="javascript"
			src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css"
			rel="stylesheet" type="text/css">
		<script language="javascript" src="${ctx}/js/validate.js" type=""></script>
		<link type="text/css" rel="stylesheet"
			href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
		<script type="text/javascript">
	function getContractNO() {
		var appraiseMonth = $('appraiseTime').value;
		var contractorId = $('contractorId').value;
		$("contractNO").length = 1;
		if (appraiseMonth != "" && contractorId != "") {
			var url = "${ctx}/appraiseMonthAction.do?method=getContractNO&appraiseTime="
					+ appraiseMonth + "&contractorid=" + contractorId;
			var myAjax = new Ajax.Request(url, {
				method : 'post',
				asynchronous : true,
				onComplete : setContractNO
			});
		}
	}
	function setContractNO(response) {
		var str = response.responseText;
		if (str == "") {
			return true;
		}
		var optionlist = str.split(",");

		for ( var i = 0; i < optionlist.length; i++) {
			$("contractNO").options[i + 1] = new Option(optionlist[i],
					optionlist[i]);
		}
	}
</script>
	</head>

	<body>
		<template:titile value="生成月考核表" />
		<html:form action="/appraiseMonthAction.do?method=createTable"
			styleId="form">
			<template:formTable>
				<template:formTr name="考核月份">
					<html:text property="appraiseTime" style="Wdate;width:135px"
						styleClass="inputtext required" styleId="appraiseTime"
						onfocus="WdatePicker({dateFmt:'yyyy-MM',maxDate:'%y-%M',minDate:'${minDate}'})"
						onchange="getContractNO()"></html:text><font color="red">*</font>
				</template:formTr>
				<template:formTr name="考核代维">
					<html:select property="contractorId" styleClass="inputtext"
						styleId="contractorId" style="width:140px"
						onchange="getContractNO()">
						<html:options collection="cons" property="contractorID"
							labelProperty="contractorName"></html:options>
					</html:select><font color="red">*</font>
				</template:formTr>
				<template:formTr name="标包号">
					<html:select property="contractNO" styleId="contractNO"
						styleClass="inputtext" style="width:140px">
						<html:option value="">全部</html:option>
					</html:select><font color="red">*</font>
				</template:formTr>
			</template:formTable>
			<div align="center" style="height: 80px">
				<html:submit styleClass="button">生成</html:submit>
			</div>
		</html:form>
	</body>
	<script type="text/javascript">
	function formCallback(result, form) {
		window.status = "valiation callback for form '" + form.id
				+ "': result = " + result;
	}

	var valid = new Validation('form', {
		immediate : true,
		onFormValidate : formCallback
	});
</script>
</html>
