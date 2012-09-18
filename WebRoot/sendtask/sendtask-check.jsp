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
			var checkResult=formElements["sendTaskCheck.checkResult"];
			if(!checkResult[0].checked&&!checkResult[1].checked){
				alert("没有选择验证意见！");
				return false;
			}
			if(valCharLength(formElements["sendTaskCheck.checkRemark"].value)>1020){
				alert("验证备注字数太多！不能超过510个汉字");
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
		<template:titile value="派单验证" />
		<s:form action="sendTaskCheckAction_save" name="addApplyForm"
			onsubmit="return validate();" enctype="multipart/form-data">
			<input name="sendTaskCheck.taskId" value="${sendTaskCheck.taskId}"
				type="hidden" />
			<input type="hidden" name="sendTaskCheck.sendtaskid" value="${id}" />
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
						验证意见：
					</td>
					<td class="tdl" colspan="3">
						<input name="sendTaskCheck.checkuserid"
							value="${LOGIN_USER.userID }" type="hidden" />
						<input name="sendTaskCheck.checkResult" value="pass" type="radio">
						通过
						<input name="sendTaskCheck.checkResult" value="reject"
							type="radio">
						不通过
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						验证备注：
					</td>
					<td class="tdl" colspan="3">
						<textarea name="sendTaskCheck.checkRemark"
							title="请不要超过256个汉字或者512个英文字符，否则将截断。" style="width: 80%" rows="6"
							class="textarea"></textarea>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						验证附件：
					</td>
					<td class="tdl" colspan="3">
						<apptag:upload cssClass="" entityId=""
							entityType="COMMON_SENDTASKCHECK" state="add" />
					</td>
				</tr>
			</table>
			<table align="center">
				<tr>
					<td>
						<input type="submit" value="验证" class="button">
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
