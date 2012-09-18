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
			if($('planAcceptanceTime').value == ''){
				alert('�ƻ��������ڲ���Ϊ��');
				return false;
			}
			return true;
		}
	</script>
</head>
<body onload="changeStyle()">
	<template:titile value="�ܵ�¼��" />
	<template:formTable>
		<html:form action="/acceptancePipesAction.do?method=recordPipe" styleId="form" onsubmit="return check();">
			<template:formTr name="��������">
				<html:text property="projectName" styleClass="inputtext required" style="width:200px"/>
			</template:formTr>
			<template:formTr name="�ƻ���������">
				<html:text property="planAcceptanceTime" styleClass="Wdate" style="width:200px" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'%y-%M-%d'})"/>
			</template:formTr>
			<template:formTr name="�ܵ��ص�">
				<html:text property="pipeAddress" styleClass="inputtext required" style="width:200px"/>
			</template:formTr>
			<template:formTr name="��ϸ·��">
				<html:text property="pipeRoute" styleClass="inputtext required" style="width:200px"/>
			</template:formTr>
			<template:formTr name="�ܵ�����">
				<apptag:quickLoadList cssClass="input" style="width:200px" name="pipeType" listName="pipe_type" type="select"/>
			</template:formTr>
			<template:formTr name="��Ȩ����">
				<apptag:quickLoadList cssClass="input" style="width:200px" name="pipeProperty" listName="property_right" type="select"/>
			</template:formTr>
			<template:formTr name="�ܵ�����">
				�������<html:text property="pipeLength0" styleClass="inputtext required validate-number" style="width:140"/><br/>
				�ף����<html:text property="pipeLength1" styleClass="inputtext required validate-number" style="width:140"/>
			</template:formTr>
			<template:formTr name="�ƶ�����">
				�������<html:text property="moveScale0" styleClass="inputtext required validate-number" style="width:140"/><br/>
				�ף����<html:text property="moveScale1" styleClass="inputtext required validate-number" style="width:140"/>
			</template:formTr>
			<template:formTr name="����ͼֽ">
				<html:text property="workingDrawing" styleClass="inputtext required validate-number" style="width:200px"/>
			</template:formTr>
			<template:formTr name="ʩ����λ">
				<html:text property="builder" styleClass="inputtext required" style="width:200px"/>
			</template:formTr>
			<template:formTr name="ʩ����λ��ϵ��ʽ">
				<html:text property="builderPhone" styleClass="inputtext required" style="width:200px"/>
			</template:formTr>
			<template:formTr name="�ܵ�������Ŀ����">
				<html:text property="pcpm" styleClass="inputtext required" style="width:200px"/>
			</template:formTr>
			<template:formTr name="��ע1">
				<html:textarea rows="4" cols="35" property="remark" styleClass="inputtext"  style="width:300px;height:50px"/>
			</template:formTr>
			<template:formTr name="��ע2">
				<html:textarea rows="4" cols="35" property="remark2" styleClass="inputtext"  style="width:300px;height:50px"/>
			</template:formTr>
			<template:formTr name="��ע3">
				<html:textarea rows="4" cols="35" property="remark3" styleClass="inputtext"  style="width:300px;height:50px"/>
			</template:formTr>
			<template:formSubmit>
				<td>
					<input type="submit" class="button" value="���" />
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