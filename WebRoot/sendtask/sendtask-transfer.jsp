<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<script type="text/javascript"
	src="${ctx}/sendtask/js/sendtask-common.js"></script>
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
<html>
	<head>
		<title>sendtask</title>
		<script type="text/javascript"
			src="${ctx }/sendtask/js/sendtask-transfer.js"></script>
		<script type="" language="javascript">
		var jQuery = $;
		var contextPath="${ctx}";
		setRegionId("${LOGIN_USER.regionID}");
		function validate(){
			var formElements=addApplyForm.elements;
			if(valCharLength(formElements["sendTaskTransfer.transferRemark"].value)>1020){
				alert("转派备注字数太多！不能超过510个汉字");
				return false;
			}
			var acceptuseridV=jQuery("#sel_orgs").combotree("getValue");
			if(judgeEmptyString(acceptuseridV)){
				alert("请选择转派受理人！");
				return false;
			}
			return true;
		}
		</script>
	</head>
	<body>
		<br>
		<template:titile value="派单转派" />
		<s:form action="sendTaskTransferAction_save" name="addApplyForm"
			onsubmit="return validate();" enctype="multipart/form-data">
			<input type="hidden" name="sendTaskTransfer.sendtaskid" value="${id}" />
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
						转派处理人：
					</td>
					<td class="tdl" colspan="3">
						<input name="sendTaskTransfer.taskId"
							value="${sendTaskTransfer.taskId}" type="hidden" />
						<input name="sendTaskTransfer.transferuserid"
							value="${LOGIN_USER.userID }" type="hidden" />
						<select id="sel_orgs" name="sendTaskTransfer.acceptuserid"></select>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						转派说明：
					</td>
					<td class="tdl" colspan="3">
						<textarea name="sendTaskTransfer.transferRemark"
							title="转派说明最长510个汉字！" style="width: 82%" rows="5"
							class="textarea"></textarea>
					</td>
				</tr>
			</table>
			<table align="center">
				<tr>
					<td>
						<input type="submit" value="转派" class="button">
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
	</body>
</html>
