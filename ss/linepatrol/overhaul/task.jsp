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
				alert('�������ʼʱ�䲻��Ϊ��');
				$('startTime').focus();
				return false;
			}
			if($('endTime').value == ''){
				alert('�����������ʱ�䲻��Ϊ��');
				$('endTime').focus();
				return false;
			}
			if($('contractors').value == ''){
				alert('��ά��˾����Ϊ��');
				return false;
			}
			processBar(form);
			return true;
		}
	</script>
</head>
<body onload="changeStyle()">
	<template:titile value="��������" />
	<template:formTable namewidth="200" contentwidth="300">
		<html:form action="/overHaulAction.do?method=addTask" styleId="form" onsubmit="return check()">
			<template:formTr name="������Ŀ����">
				<html:text property="projectName" styleClass="inputtext required" style="width:140px"/>
			</template:formTr>
			<template:formTr name="������">
				${LOGIN_USER.userName}
				<html:hidden property="projectCreator" value="${LOGIN_USER.userID}"/>
			</template:formTr>
			<template:formTr name="���ã�Ԫ��">
				<html:text property="budgetFee" styleId="budgetFee" styleClass="inputtext required validate-integer-float-double" style="width:140px"/>
			</template:formTr>
			<template:formTr name="�������ʼʱ��">
				<html:text property="startTime" styleId="startTime"  styleClass="Wdate" style="width:140px" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" readonly="true"/>
			</template:formTr>
			<template:formTr name="�����������ʱ��">
				<html:text property="endTime" styleId="endTime" styleClass="Wdate" style="width:140px" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'#F{$dp.$D(\'startTime\')}'})" readonly="true"/>
			</template:formTr>
			<apptag:contractorselect inputName="contractors" />
			<template:formTr name="�������ע">
				<html:textarea property="projectRemark" styleId="projectRemark" styleClass="inputtextarea" style="width:200px;" />
			</template:formTr>
			<template:formSubmit>
				<td> 
					<html:submit property="action" styleClass="button">�ύ</html:submit>
				</td>
				<td>
					<html:reset property="action" styleClass="button">����</html:reset>
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
