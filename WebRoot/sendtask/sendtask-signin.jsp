<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<script type="text/javascript"
	src="${ctx}/sendtask/js/sendtask-common.js"></script>
<html>
	<head>
		<title>sendtask</title>
		<script type="" language="javascript">
		var contextPath="${ctx}";
		function validate(){
			var formElements=addApplyForm.elements;
			var signinResult=formElements["sendTaskSignin.signinResult"];
			if(!signinResult[0].checked&&!signinResult[1].checked){
				alert("没有选择签收意见！");
				return false;
			}
			if(valCharLength(formElements["sendTaskSignin.signinRemark"].value)>1020){
				alert("签收备注字数太多！不能超过510个汉字");
				return false;
			}
			return true;
		}
		</script>
	</head>
	<body>
		<!--显示一个待签收派单详细信息页面-->
		<br>
		<template:titile value="签收派单" />
		<s:form action="sendTaskSigninAction_save" name="addApplyForm"
			onsubmit="return validate();" enctype="multipart/form-data">
			<input type="hidden" name="sendTaskSignin.sendtaskid" value="${id}" />
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
					<td class="tdr">
						签收意见：
					</td>
					<td class="tdl" colspan="3">
						<input name="sendTaskSignin.taskId"
							value="${sendTaskSignin.taskId}" type="hidden" />
						<input name="sendTaskSignin.signinuserid"
							value="${LOGIN_USER.userID }" type="hidden" />
						<input name="sendTaskSignin.signinResult" value="pass"
							type="radio">
						签收
						<input name="sendTaskSignin.signinResult" value="reject"
							type="radio">
						拒签
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						签收备注：
					</td>
					<td class="tdl" colspan="3">
						<textarea name="sendTaskSignin.signinRemark"
							title="请不要超过256个汉字或者512个英文字符，否则将截断。" style="width: 80%" rows="6"
							class="textarea"></textarea>
					</td>
				</tr>
			</table>
			<table align="center">
				<tr>
					<td>
						<input type="submit" value="签收" class="button">
					</td>
					<td>
						<input type="reset" value="重置" class="button">
					</td>
					<td>
						<input type="button" value="返回" class="button"
							onclick="goBack()">
					</td>
				</tr>
			</table>
		</s:form>
	</body>
</html>
