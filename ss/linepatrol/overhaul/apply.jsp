<%@include file="/common/header.jsp"%>
<html>
<head>
	<title></title>
	
	<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
	
	<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
	<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
	<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
	

	<script type='text/javascript' src='${ctx}/linepatrol/js/change_style.js'></script>
	
	<script type="text/javascript">
		var win;
		function showWin(url){
			win = new Ext.Window({
				layout : 'fit',
				width:600,
				height:400,
				resizable:true,
				closeAction : 'close',
				modal:true,
				autoScroll:true,
				html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="'+url+'" />',
				plain: true
			});
			win.show(Ext.getBody());
		}
		function close(){
			win.close();
		}
		function chooseCut(){
			var p = getChecked('cutid');
			var url = '${ctx}/linepatrol/overhaul/chooseCut.jsp?p='+p;
			showWin(url);
		}
		function chooseProject(){
			var p = getChecked('projectid');
			var url = '${ctx}/linepatrol/overhaul/chooseProject.jsp?p='+p;
			showWin(url);
		}
		function getChecked(name){
			var checked = document.getElementsByName(name);
			var chs = new Array();
			for(var i = 0 ; i < checked.length ; i++){
				if(checked[i].checked)
					chs.push(checked[i].value);
			}
			return chs.join();
		}
		function getNumber(name){
			var checked = document.getElementsByName(name);
			var num = 0;
			for(var i = 0 ; i < checked.length ; i++){
				//if(checked[i].checked)
					num++;
			}
			return num;
		}
		function check(){
			caculate();
			var inps = document.getElementsByTagName("input");
			var cut = false;
			var project = false;
			for(var i=0 ; i<inps.length ; i++){
				if(inps[i].cut == 'true'){
					cut = true;
				}
				if(inps[i].project == 'true'){
					project = true;
				}
			}
			if(!cut){
				alert('û����Ӹ����Ϣ');
				return false;
			}
			if(!project){
				alert('û����ӹ�����Ϣ');
				return false;
			}
			if($('approver').value == ''){
				alert('����˲���Ϊ��');
				return false;
			}
			var number = getNumber('cutid') + getNumber('projectid');
			var msg = sendAjax(number);
			if(msg == ''){
				return true;
				}
			else{
				alert(msg);
				return false;
			}
		}
		function caculate(){
			var inps = document.getElementsByTagName("input");
			var nul = /^\s+$/;
			var num = /^\d*\.?\d*$/;
			var sum = parseFloat(0);
			for(var i=0 ; i<inps.length ; i++){
				if(inps[i].cut == 'true' || inps[i].project == 'true'){
					var val = inps[i].value;
					if(val!='' && !nul.test(val) && num.test(val)){
						sum += parseFloat(val);
					}
				}
			}
			$('applyfee').innerHTML = sum + "Ԫ";
			$('fee').value = sum;
		}
		function viewCut(id){
			var url = "${ctx}/cutQueryStatAction.do?method=viewQueryCut&type=win&cutId="+id;
			showWin(url);
		}
		function viewProject(id){
			var url = "${ctx}/project/remedy_apply.do?method=view&type=win&apply_id="+id;
			showWin(url);
		}
		function sendAjax(number){
			var response = '';
			jQuery.ajax({
			   type: "POST",
			   url: "overHaulApplyAction.do",
			   data: "method=validateFile&number="+number,
			   async: false,
			   success: function(msg){
				   response = msg;
			   }
			});
			return response;
		}

function dosubmit(){
	if(check()){
		document.getElementById("form").submit();
	}
}
		
	</script>
</head>
<body onload="changeStyle()">
	<template:titile value="��������" />
	<template:formTable namewidth="170" contentwidth="400">
		<html:form action="/overHaulApplyAction.do?method=addApply" styleId="form" >
			<input type="hidden" name="type" value="add" />
			<html:hidden property="taskId" value="${overHaul.id}" />
			<template:formTr name="������Ŀ����">
				${overHaul.projectName}
			</template:formTr>
			<template:formTr name="������">
				${overHaul.projectCreator}
			</template:formTr>
			<template:formTr name="Ԥ�����">
				${overHaul.budgetFee}Ԫ
			</template:formTr>
			<template:formTr name="�������ʼʱ��">
				<bean:write name="overHaul" property="startTime" format="yyyy-MM-dd" />
			</template:formTr>
			<template:formTr name="�����������ʱ��">
				<bean:write name="overHaul" property="endTime" format="yyyy-MM-dd" />
			</template:formTr>
			<template:formTr name="�������ע">
				${overHaul.projectRemark}
			</template:formTr>
			<template:formTr name="�����Ϣ">
				<input type="button" value="ѡ��" onclick="chooseCut();"/><div id="cut" />
			</template:formTr>
			<template:formTr name="������Ϣ">
				<input type="button" value="ѡ��" onclick="chooseProject();"/><div id="project" />
			</template:formTr>
			<template:formTr name="�����ܷ���">
				<div id="applyfee">${overHaulApplyBean.fee}Ԫ</div>
				<html:hidden property="fee" styleId="fee" styleClass="inputtext required" style="width:140px"/>
			</template:formTr>
			<template:formTr name="����">
				<apptag:upload state="add" cssClass="" />
			</template:formTr>
			<tr class=trcolor >
				<apptag:approverselect label="�����" inputName="approver" spanId="approverSpan" inputType="radio" notAllowName="reader"/>
			</tr>
			<tr class=trcolor >
				<apptag:approverselect label="������" inputName="reader" spanId="readerSpan" notAllowName="approver" />
			</tr>
			<template:formSubmit>
				<td>
					<input type="button" class="button" value="�ύ" onclick="dosubmit()" />
					<!--<html:submit property="action" styleClass="button">�ύ</html:submit>-->
				</td>
				<td>
					<input type="button" class="button" value="����" onclick="history.back()" />
				</td>
			</template:formSubmit>
		</html:form>
		<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
				if(result){
					processBar(form);
				}
			}
	
			var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
		</script>
	</template:formTable>
</body>
