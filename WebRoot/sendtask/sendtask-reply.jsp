<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<script type="text/javascript"
	src="${ctx}/sendtask/js/sendtask-common.js"></script>
<html>
	<head>
		<title>sendtask</title>
		<script type="text/javascript" src="/WebApp/js/prototype.js"></script>
		<script type="" language="javascript">
		var contextPath="${ctx}";
		function validate(){
			var formElements=addApplyForm.elements;
			if(judgeEmptyString(formElements["sendTaskReply.replyresult"].value)){
				alert("回复内容不能为空！");
				return false;
			}
			if(valCharLength(formElements["sendTaskReply.replyresult"].value)>1020){
				alert("回复内容字数太多！不能超过510个汉字");
				return false;
			}
			var allCheck = document.getElementsByName('ifCheck');
			var length = allCheck.length;
			for(var i = 0; i < length; i++) {
				//var index = i + 1;
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
		</script>
	</head>
	<body>
		<!--反馈派单-->
		<br>
		<template:titile value="派单回复" />
		<s:form action="sendTaskReplyAction_save" name="addApplyForm"
			onsubmit="return validate();" enctype="multipart/form-data">
			<input name="sendTaskReply.taskId" value="${sendTaskReply.taskId}"
				type="hidden" />
			<input type="hidden" name="sendTaskReply.sendtaskid" value="${id}" />
			<input type="hidden" name="sendTaskReply.transition" value="pass" />
			<input type="hidden" name="sendTaskReply.id"
				value="${sendTaskReply.id }" />
			<input type="hidden" name="sendTaskReply.actiontype" value="REPLY" />
			<table width="90%" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<tr class=trcolor>
					<td class="tdr" width="15%">
						工作类别：
					</td>
					<td class="tdl" width="35%">
						<apptag:quickLoadList cssClass="" name="" listName="dispatch_task"
							keyValue="${sendTask.sendtype}" type="look" isRegion="false"></apptag:quickLoadList>
					</td>
					<td class="tdr">
						派单时间：
					</td>
					<td class="tdl">
						${sendTask.sendtime }
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						派单部门：
					</td>
					<td class="tdl">
						${sendTask.sendorgname }
					</td>
					<td class="tdr">
						派 单 人：
					</td>
					<td class="tdl">
						${sendTask.sendusername }
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						受理部门：
					</td>
					<td class="tdl">
						${sendTask.acceptorgname }
					</td>
					<td class="tdr">
						受理人：
					</td>
					<td class="tdl">
						${sendTask.acceptusername }
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						任务主题：
					</td>
					<td class="tdl" colspan="3">
						${sendTask.sendtopic }
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						任务说明：
					</td>
					<td class="tdl" colspan="3">
						${sendTask.sendtext }
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						处理时限：
					</td>
					<td class="tdl" colspan="3">
						${processterm }
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						任务附件：
					</td>
					<td class="tdl" colspan="3">
						<apptag:upload cssClass="" entityId="${sendTask.id}"
							entityType="COMMON_SENDTASK" state="look" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdc" colspan="4" id="processHistoryTd"
						style="padding: 5px;">
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						回复内容：
					</td>
					<td class="tdl" colspan="3">
						<input name="sendTaskReply.replyuserid"
							value="${LOGIN_USER.userID }" type="hidden" />
						<textarea name="sendTaskReply.replyresult" title="回复内容最长510个汉字！"
							style="width: 82%" rows="5" class="textarea"></textarea>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						回复附件：
					</td>
					<td class="tdl" colspan="3">
						<apptag:upload cssClass="" entityId=""
							entityType="COMMON_SENDTASKREPLY" state="add" />
					</td>
				</tr>
			</table>
			<table align="center">
				<tr>
					<td>
						<input type="submit" value="回复" class="button">
					</td>
					<td>
						<input type="reset" value="重置" class="button">
					</td>
					<td>
						<input type="button" value="返回" class="button" onclick="goBack()">
					</td>
				</tr>
			</table>
		</s:form>
		<script type="text/javascript">
		showProcessHistory('${sendTask.id}');
		</script>
	</body>
</html>
