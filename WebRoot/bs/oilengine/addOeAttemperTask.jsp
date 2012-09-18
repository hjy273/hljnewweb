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
	function getStation() {
		var url = "${ctx}/oilEngineAction_getBaseStation.jspx?district="+ $('district').value;
		var myAjax = new Ajax.Request(url, {
			method : 'get',
			asynchronous : 'false',
			onComplete : setStationCode
		});
	}
	function setStationCode(response) {
		$("oeAttemperTask.blackoutStation").options.length = 1;
		var str = response.responseText;
		if (str == "")
			return true;
		var optionlist = str.split(";");
		for ( var i = 0; i < optionlist.length - 1; i++) {
			var t = optionlist[i].split("=")[0];
			var v = optionlist[i].split("=")[1];
			$("oeAttemperTask.blackoutStation").options[i + 1] = new Option(v, t);
		}
	}
	function check(){
		if($('oeAttemperTask.blackoutStation').value==""){
			alert("��ѡ���վ��");
			return false;
		}return true;
	}
</script>
</head>
<body>
	<template:titile value="��Ӹ澯��Ϣ"/>

	<s:form action="oeAttemperTaskAction_save" name="addOeAttemperTaskFrom" method="post" onsubmit="return check()">
		<template:formTable>
		<template:formTr name="����">
				<s:select list="%{#session.DISTRICTS}" name="district" listKey="key" listValue="value" cssClass="inputtext required"
					cssStyle="width:220px"  headerKey="" onchange="getStation()" headerValue="=��ѡ��=" />
		</template:formTr>
		<template:formTr name="�ϵ��վ">
			<select name="oeAttemperTask.blackoutStation" id="oeAttemperTask.stationId" class="inputtext" style="width: 220px">
						<option value="">��ѡ��</option>
		</template:formTr>
		<template:formTr name="�ϵ�ʱ��">
			<input  name="oeAttemperTask.blackoutTime" class="inputtext required" style="width: 220px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly /><span style="color:red">*</span>
		</template:formTr>
		<template:formTr name="�ϵ�ԭ��">
			<input type="text" name="oeAttemperTask.blackoutReason" class="inputtext required" style="width:220px" maxlength="400" ><span style="color:red">*</span>
		</template:formTr>
		</template:formTable>
		<template:formSubmit>
			<td>
				<html:submit styleClass="button">���</html:submit>
			</td>
			<td>
				<input type="button" class="button" onclick=history.back();
					value="����">
			</td>
		</template:formSubmit>
		<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('addOeAttemperTaskFrom', {immediate : true, onFormValidate : formCallback});
		</script>
	</s:form>
</body>
