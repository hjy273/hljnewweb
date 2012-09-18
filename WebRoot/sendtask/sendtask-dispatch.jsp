<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${ctx }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${ctx }/js/easyui/themes/icon.css">
<!-- ajax combox -->
<link rel="stylesheet"
	href="${ctx }/js/acbox/css/jquery.ajaxComboBox.css">
<script type="text/javascript" src="${ctx}/js/easyui/jquery-1.6.1.js"></script>
<script type="text/javascript"
	src="${ctx }/js/easyui/jquery.easyui.min.js"></script>
<script src="${ctx }/js/easyui/easyloader.js"></script>
<script type="text/javascript">
	var jQuery = $;
	var contextPath="${ctx}";
</script>
<script type="text/javascript"
	src="${ctx}/sendtask/js/sendtask-common.js"></script>
<script type="text/javascript"
	src="${ctx}/sendtask/js/sendtask-dispatch.js"></script>
<script type="text/javascript">
setRegionId("${LOGIN_USER.regionID}");
</script>
<html>
	<head>
		<title>sendtask</title>
		<script type="" language="javascript">
		function checkFormData(){
			var formElements=addApplyForm.elements;
			if(judgeEmptyString(formElements["sendTask.sendtype"].value)){
				alert("请输入工作类别！");
				formElements["sendTask.sendtype"].focus();
				return;
			}
			if(judgeEmptyString(formElements["sendTask.sendtopic"].value)){
				alert("请输入任务主题！");
				formElements["sendTask.sendtopic"].focus();
				return;
			}
			var acceptuseridV=jQuery("#sel_orgs").combotree("getValue");
			if(judgeEmptyString(acceptuseridV)){
				alert("请选择受理人！");
				return;
			}
			if(judgeEmptyString(formElements["sendTask.sendtext"].value)){
				alert("请输入任务说明！");
				formElements["sendTask.sendtext"].focus();
				return;
			}
			if(valCharLength(formElements["sendTask.sendtext"].value)>1020){
				alert("任务说明字数太多！不能超过510个汉字");
				formElements["sendTask.sendtext"].focus();
				return;
			}
			var allCheck = document.getElementsByName('ifCheck');
			var length = allCheck.length;
			for(var i = 0; i < length; i++) {		
				var index = allCheck[i].value;
				var pathText = document.getElementById('uploadFile[' + index + '].file');
				var path = pathText.value;
				if(path.length == 0) {
					alert("请选择要上传的附件的路径，\n如果没有要上传的附件请删除!");
					pathText.focus();
					return false;
				}
			}
			return true;
		}
		function tempSave() {
			if(checkFormData()){
				addApplyForm.elements["sendTask.isSubmit"].value="0";
				addApplyForm.submit();
			}
		}
		function checkSub() {
			if(checkFormData()){
				addApplyForm.submit();
			}
		}
		</script>
	</head>
	<body>
		<br>
		<template:titile value="任务派单" />
		<s:form name="addApplyForm" action="sendTaskAction_save"
			enctype="multipart/form-data" method="post">
			<input name="sendTask.isSubmit" value="1" type="hidden" />
			<table id="tableID" width="90%" border="0" align="center"
				cellpadding="3" cellspacing="0" class="tabout">
				<tr class=trcolor>
					<td class="tdr" width="15%">
						工作类别：
					</td>
					<td class="tdl" colspan="3">
						<apptag:quickLoadList cssClass="inputtext"
							name="sendTask.sendtype" listName="dispatch_task" type="select"
							isRegion="false" keyValue="${sendTask.sendtype}" />
						<font color="#FF0000">*</font>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" width="15%">
						派单人：
					</td>
					<td class="tdl" width="35%">
						<!-- 
						<input name="taskId" value="${taskId}" type="hidden" />
						 -->
						<input name="sendTask.taskId" value="${sendTask.taskId}"
							type="hidden" />
						<input name="sendTask.transition" value="pass" type="hidden" />
						<input name="sendTask.id" value="${sendTask.id}" type="hidden" />
						<input name="sendTask.sendorgid" type="hidden"
							value="${LOGIN_USER.deptID}">
						<input name="sendTask.senduserid" type="hidden"
							value="${LOGIN_USER.userID}">
						<s:property value="%{#session.LOGIN_USER.userName}" />
					</td>
					<td class="tdr" width="15%">
						受理人：
					</td>
					<td class="tdl" width="35%">
						<input name="sendTask.acceptorgid" value="${sendTask.acceptorgid}"
							type="hidden">
						<select id="sel_orgs" name="sendTask.acceptuserid"></select>
						<font color="#FF0000">*</font>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						任务主题：
					</td>
					<td class="tdl" colspan="3">
						<input name="sendTask.sendtopic" style="width: 82%"
							class="inputtext" value="${sendTask.sendtopic}" maxlength="100" />
						<font color="#FF0000">*</font>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						任务说明：
					</td>
					<td class="tdl" colspan="3">
						<textarea name="sendTask.sendtext" title=" 任务说明最长510个汉字！"
							style="width: 82%" rows="5" class="textarea">${sendTask.sendtext}</textarea>
						<font color="#FF0000">*</font>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						处理时限：
					</td>
					<td class="tdl" colspan="3">
						<input type="text" name="processterm" value="${processterm}"
							readonly="readonly" class="Wdate"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d'})"
							style="width: 200px" />
						<font color="#FF0000">*</font>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						任务附件：
					</td>
					<td class="tdl" colspan="3">
						<c:if test="${sendTask.id==''}">
							<apptag:upload cssClass="" entityId=""
								entityType="COMMON_SENDTASK" state="add" />
						</c:if>
						<c:if test="${sendTask.id!=''}">
							<apptag:upload cssClass="" entityId="${sendTask.id}"
								entityType="COMMON_SENDTASK" state="edit" />
						</c:if>
					</td>
				</tr>
			</table>
			<table align="center" style="border: 0px;">
				<tr height="50">
					<td class="tdc" style="border: 0px;">
						<input type="button" value="临时保存" class="button"
							onclick="tempSave();">
						<input type="button" value="提交" class="button"
							onclick="checkSub();">
						<input type="reset" value="重置" class="button">
					</td>
				</tr>
			</table>
		</s:form>
		<c:if test="${sendTask.id!=''}">
			<script type="text/javascript">
			setDefaultValue("${sendTask.acceptUserId}");
			</script>
		</c:if>
	</body>
</html>
