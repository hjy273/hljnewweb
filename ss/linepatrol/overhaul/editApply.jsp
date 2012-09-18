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
			var p = getChecked('projectId');
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
				alert('没有添加割接信息');
				return false;
			}
			if(!project){
				alert('没有添加工程信息');
				return false;
			}
			if($('approver').value == ''){
				alert('审核人不能为空');
				return false;
			}
			var number = getNumber('cutid') + getNumber('projectid');
			var msg = sendAjax(number);
			if(msg == '')
				return true;
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
			$('applyfee').innerHTML = sum + "元";
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
		function getNumber(name){
			var checked = document.getElementsByName(name);
			var num = 0;
			for(var i = 0 ; i < checked.length ; i++){
				if(checked[i].checked)
					num++;
			}
			return num;
		}
	</script>
</head>
<body onload="changeStyle()">
	<template:titile value="修改大修申请" />
	<template:formTable namewidth="170" contentwidth="400">
		<html:form action="/overHaulApplyAction.do?method=addApply" styleId="form" onsubmit="return check()">
			<input type="hidden" name="type" value="edit" />
			<html:hidden property="taskId" value="${overHaul.id}" />
			<html:hidden property="id" value="${OverHaulApply.id}" />
			<template:formTr name="大修项目名称">
				${OverHaul.projectName}
			</template:formTr>
			<template:formTr name="立项人">
				${OverHaul.projectCreator}
			</template:formTr>
			<template:formTr name="预算费用">
				${OverHaul.budgetFee}元
			</template:formTr>
			<template:formTr name="大修立项开始时间">
				<bean:write name="OverHaul" property="startTime" format="yyyy-MM-dd" />
			</template:formTr>
			<template:formTr name="大修立项结束时间">
				<bean:write name="OverHaul" property="endTime" format="yyyy-MM-dd" />
			</template:formTr>
			<template:formTr name="大修立项备注">
				${OverHaul.projectRemark}
			</template:formTr>
			<template:formTr name="割接信息">
				<input type="button" value="更改" onclick="chooseCut();"/><div id="cut" />
				<table width=100% border=1>
					<tr>
						<td align='center' width='10%'>选择</td>
						<td align='center' width='50%'>割接名称</td>
						<td align='center' width='15%'>参考费用</td>
						<td align='center' width='25%'>割接费用</td>
					</tr>
					<c:forEach var="cut" items="${OverHaulApply.overHaulCuts}">
						<tr>
							<td align="center"><input type="checkbox" onclick="return false" name="cutid" value="${cut.cutId}" checked /></td>
							<td><a href="javascript:viewCut('${cut.cutId}')">${cut.cutName}</a></td>
							<td>${cut.cutRefFee}元<input type='hidden' name='cutref,${cut.cutId}' id='cutref,${cut.cutId}' value='${cut.cutRefFee}' /></td>
							<td align='center'><input type='text' name='cut,${cut.cutId}' cut='true' id='cut,${cut.cutId}' value='${cut.cutFee}' style='width:70px' onchange='caculate()' class='inputtext required validate-integer-float-double' /></td>
						</tr>
					</c:forEach>
				</table>
			</template:formTr>
			<template:formTr name="工程信息">
				<input type="button" value="更改" onclick="chooseProject();"/><div id="project" />
				<table width=100% border=1>
					<tr>
						<td align="center" width="10%">选择</td>
						<td align="center" width="50%">工程名称</td>
						<td align="center" width="15%">参考费用</td>
						<td align='center' width='25%'>工程费用</td>
					</tr>
					<c:forEach var="project" items="${OverHaulApply.overHaulProjects}">
						<tr>
							<td align="center"><input type="checkbox" onclick="return false" name="projectid" value="${project.projectId}" checked /></td>
							<td><a href="javascript:viewProject('${project.projectId}')">${project.projectName}</a></td>
							<td>${project.projectRefFee}元<input type='hidden' name='projectref,${project.projectId}' id='projectref,${project.projectId}' value="${project.projectRefFee}"/></td>
							<td align='center'><input type='text' name='project,${project.projectId}' id='project,${project.projectId}' value='${project.projectFee}' style='width:70px' project='true' onchange='caculate()' class='inputtext required validate-integer-float-double' /></td>
						</tr> 
					</c:forEach>
				</table>
			</template:formTr>
			<template:formTr name="申请总费用">
				<div id="applyfee">${OverHaulApply.fee}元</div>
				<html:hidden property="fee" styleClass="inputtext required" style="width:140px"/>
			</template:formTr>
			<template:formTr name="附件">
				<apptag:upload state="edit" cssClass="" entityId="${OverHaulApply.id}" entityType="LP_OVERHAUL"/>
			</template:formTr>
			<tr class=trcolor>
				<apptag:approverselect label="审核人" inputName="approver"
				  approverType="approver" objectType="LP_OVERHAUL" objectId="${OverHaulApply.id}" 
				  spanId="approverSpan" inputType="radio" notAllowName="reader"/>
			</tr>
			<tr class=trcolor>
				<apptag:approverselect label="抄送人" inputName="reader" 
				  approverType="reader" objectType="LP_OVERHAUL" objectId="${OverHaulApply.id}" 
				  spanId="readerSpan" notAllowName="approver" />
			</tr>
			<tr>
				<apptag:approve labelClass="tdulleft" displayType="view" objectId="${OverHaulApply.id}" objectType="LP_OVERHAUL" />
			</tr>
			<template:formSubmit>
				<td>
					<html:submit property="action" styleClass="button">提交</html:submit>
				</td>
				<td>
					<input type="button" class="button" value="返回" onclick="history.back()" />
				</td>
			</template:formSubmit>
		</html:form>
		<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}
	
			var valid = new Validation('form', {immediate : true, onFormValidate : formCallback});
		</script>
	</template:formTable>
</body>