<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>生成专项考核表</title>
		<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
	<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
	<script type="text/javascript">
		function getTableOption(){
			var startDate=$('startDate').value;
			var endDate=$('endDate').value;
			if(endDate==''||startDate==''){
				return false;
			}
			$("tableId").length=0;
			var url="${ctx}/appraiseSpecialAction.do?method=getTableOption&startDate="+startDate+"&endDate="+endDate;
			var myAjax = new Ajax.Request( url , {
        	    method: 'post',
        	    asynchronous: true,
        	    onComplete: setTableOption });
		}
		
		function setTableOption(response){
			var str=response.responseText;
			if(str==""){
				return true;
			}
			var optionlist=str.split(";");
			for(var i=0;i<optionlist.length-1;i++){
				$("tableId").options[i]=new Option(optionlist[i].split(":")[1],optionlist[i].split(":")[0]);
			}
		}
		function check(){
			if($('tableId').value==''){
				alert("考核表不能为空！");
				return false;
			}
			return true;
		}
	</script>
	</head>

	<body>
		<template:titile value="生成专项考核表" />
			<html:form action="/appraiseSpecialAction?method=createTable" styleId="form" onsubmit="return check();">
			<template:formTable>
				<template:formTr name="考核日期">
					<html:text property="startDate" style="Wdate;width:135px" styleClass="inputtext required" styleId="startDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" onchange="getTableOption()"></html:text>
					--
					<html:text property="endDate" style="Wdate;width:135px" styleClass="inputtext required" styleId="endDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M',minDate:'#F{$dp.$D(\'startDate\')}'})" onchange="getTableOption()"></html:text><font color="red">*</font>
				</template:formTr>
				<template:formTr name="考核表">
					<html:select property="tableId" styleId="tableId" styleClass="inputtext" style="width:290px">
				</html:select><font color="red">*</font>
				</template:formTr>
				<template:formTr name="考核代维">
					<html:select property="contractorId" styleClass="inputtext" styleId="contractorId" style="width:290px" >
						<html:options collection="cons" property="contractorID" labelProperty="contractorName"></html:options>
					</html:select><font color="red">*</font>
				</template:formTr>
				</template:formTable>
				<div align="center" style="height:80px">
					<html:submit styleClass="button">生成</html:submit>
				</div>
			</html:form>
	</body>
	<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
	</script>
</html>
