<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
	<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
	
	<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>
	
	<script type="text/javascript">
		function check(){
			if($('startTime').value == ''){
				alert('大修立项开始时间不能为空');
				$('startTime').focus();
				return false;
			}
			if($('endTime').value == ''){
				alert('大修立项结束时间不能为空');
				$('endTime').focus();
				return false;
			}
			if($('contractors').value == ''){
				alert('代维公司不能为空');
				return false;
			}
			processBar(form);
			return true;
		}
	</script>
</head>
<body onload="changeStyle()">
	<template:titile value="大修任务" />
	<template:formTable namewidth="200" contentwidth="300">
		<html:form action="/overHaulAction.do?method=addTask" styleId="form" onsubmit="return check()">
			<template:formTr name="大修项目名称">
				<html:text property="projectName" styleClass="inputtext required" style="width:140px"/>
			</template:formTr>
			<template:formTr name="立项人">
				${LOGIN_USER.userName}
				<html:hidden property="projectCreator" value="${LOGIN_USER.userID}"/>
			</template:formTr>
			<template:formTr name="费用（元）">
				<html:text property="budgetFee" styleId="budgetFee" styleClass="inputtext required validate-integer-float-double" style="width:140px"/>
			</template:formTr>
			<template:formTr name="大修立项开始时间">
				<html:text property="startTime" styleId="startTime"  styleClass="Wdate" style="width:140px" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" readonly="true"/>
			</template:formTr>
			<template:formTr name="大修立项结束时间">
				<html:text property="endTime" styleId="endTime" styleClass="Wdate" style="width:140px" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'#F{$dp.$D(\'startTime\')}'})" readonly="true"/>
			</template:formTr>
			<apptag:contractorselect inputName="contractors" />
			<template:formTr name="大修立项备注">
				<html:textarea property="projectRemark" styleId="projectRemark" styleClass="inputtextarea" style="width:200px;" />
			</template:formTr>
			<template:formSubmit>
				<td> 
					<html:submit property="action" styleClass="button">提交</html:submit>
				</td>
				<td>
					<html:reset property="action" styleClass="button">重置</html:reset>
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
