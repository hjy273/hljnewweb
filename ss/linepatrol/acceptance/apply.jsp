<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
	<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
	
	<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
	
	<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>
	
	<script type="text/javascript">
		var win;
		function showWin(url){
			win = new Ext.Window({
				layout : 'fit',
				width:700,
				height:400,
				resizable:true,
				closeAction : 'close',
				modal:true,
				html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="'+url+'" />',
				plain: true
			});
			win.show(Ext.getBody());
		}
		function record(method){
			var url = getUrl(method);
			showWin(url);
		}
		function getUrl(method){
			var type = getType();
			var url = '${ctx}/linepatrol/acceptance/';
			if(type == '1'){
				if(method == '1'){
					url += 'importCable.jsp?type=1';
				}else{
					url += 'recordCable.jsp';
				}
			}else{
				if(method == '1'){
					url += 'importPipe.jsp?type=1';
				}else{
					url += 'recordPipe.jsp';
				}
			}
			return url;
		}
		function getResultUrl(method){
			var url = '${ctx}/linepatrol/acceptance/';
			if(method == '1'){
				url += 'cableList.jsp';
			}else{
				url += 'pipeList.jsp';
			}
			return url;
		}
		function getType(){
			var types = document.getElementsByName("resourceType");
			for(var i=0;i<types.length;i++){
				if(types[i].checked){
					return types[i].value;
				}
			}
		}
		function view(){
			var method = getType();
			var url = getResultUrl(method);
			showWin(url);
		}
		function close(){
			win.close();
		}
		function check(){
			if($('approver').value == ''){
				alert('��׼�˲���Ϊ��');
				return false;
			}
			return true;
		}
		
	</script>
</head>
<body onload="changeStyle()">
	<template:titile value="�ƶ����ս�ά�ƻ�" />
	<html:form action="/acceptanceAction.do?method=apply" styleId="form" onsubmit="return check()">
		<template:formTable >
			<template:formTr name="��Ŀ����">
				<html:text property="applicant" styleClass="view" style="width:200px" readonly="true" />
			</template:formTr>
			<template:formTr name="��������">
				<html:text property="name" styleClass="inputtext required" style="width:200px"/><font color="red">*</font>
			</template:formTr>
			<template:formTr name="������Դ����">
				<html:radio property="resourceType" value="1" />����
				<html:radio property="resourceType" value="2" />�ܵ�
			</template:formTr>
			<template:formTr name="���뷽ʽ">
				<input type="button" class="button" value="��������" onclick="record(1);" />
				<input type="button" class="button" value="�ֹ�¼��" onclick="record(2);" />
			</template:formTr>
			<template:formTr name="��¼������">
				<input type="button" class="button" value="�鿴" onclick="view();" />
			</template:formTr>
			<apptag:approverselect label="��׼��" inputName="approver" spanId="approverSpan" inputType="radio" />
			<template:formSubmit>
				<td>
					<html:submit property="action" styleClass="button">�ύ</html:submit>
				</td>
			</template:formSubmit>
		</template:formTable>
	</html:form>
	<script type="text/javascript">
		function formCallback(result, form) {
			window.status = "valiation callback for form '" + form.id + "': result = " + result;
		}

		var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
	</script>
</body>
</html>