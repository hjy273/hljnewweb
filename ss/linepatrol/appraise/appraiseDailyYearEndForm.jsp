<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>年终检查打分</title>
		<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
		<link type="text/css" rel="stylesheet" href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	</head>
	<script type="text/javascript">
		function getContractNO(){
			if(document.getElementById("contractorId")!=null&&document.getElementById("year")!=null){
			var year=$('year').value;
			var contractorId=$('contractorId').value;
			$("contractNO").length=1;
			var url="${ctx}/appraiseMonthAction.do?method=getContractNO&appraiseTime="+year+"&contractorid="+contractorId;
			var myAjax = new Ajax.Request( url , {
        	    method: 'post',
        	    asynchronous: true,
        	    onComplete: setContractNO });
        	}
		}
		
		function setContractNO(response){
			var str=response.responseText;
			if(str==""){
				return true;
			}
			var optionlist=str.split(",");
			for(var i=0;i<optionlist.length;i++){
				$("contractNO").options[i+1]=new Option(optionlist[i],optionlist[i]);
			}
		}	
		function check(){
			var contractorId=document.getElementById("contractorId").value;
			if(contractorId==""){
				alert("请选择代维单位！");
				return false;
			}
			var contractNo=document.getElementById("contractNO").value;
			if(contractNo==""){
				alert("请选择标包！");
				return false;
			}
			return true;
		}
	</script>
	<body>
		<template:titile value="年终检查打分" />
		<html:form action="/appraiseDailyYearEndAction.do?method=appraiseDailyYearEnd" styleId="form" onsubmit="return check();">
			<template:formTable>
				<template:formTr name="考核年份">
					<html:text property="year" style="Wdate;width:140px;" styleId="year" styleClass="inputtext required" onfocus="WdatePicker({dateFmt:'yyyy',maxDate:'${lastYear}'})" onchange="getContractNO();"></html:text><font color="red">*</font>
				</template:formTr>
				<template:formTr name="考核代维">
					<html:select property="contractorId" styleClass="inputtext" styleId="contractorId" style="width:145px" onchange="getContractNO();">
						<html:option value="">请选择</html:option>
						<html:options collection="cons" property="contractorID" labelProperty="contractorName"></html:options>
					</html:select><font color="red">*</font>
				</template:formTr>
				<template:formTr name="标包号">
					<html:select property="contractNo" styleId="contractNO" style="width:145px" styleClass="inputtext">
						<html:option value="">请选择</html:option>
					</html:select><font color="red">*</font>
				</template:formTr>
				</template:formTable>
			<div align="center"><input type="submit" value="生成" class="button"/></div>
		</html:form>
		<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
	</script>
	</body>
</html>
