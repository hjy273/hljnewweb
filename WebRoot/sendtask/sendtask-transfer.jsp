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
				alert("ת�ɱ�ע����̫�࣡���ܳ���510������");
				return false;
			}
			var acceptuseridV=jQuery("#sel_orgs").combotree("getValue");
			if(judgeEmptyString(acceptuseridV)){
				alert("��ѡ��ת�������ˣ�");
				return false;
			}
			return true;
		}
		</script>
	</head>
	<body>
		<br>
		<template:titile value="�ɵ�ת��" />
		<s:form action="sendTaskTransferAction_save" name="addApplyForm"
			onsubmit="return validate();" enctype="multipart/form-data">
			<input type="hidden" name="sendTaskTransfer.sendtaskid" value="${id}" />
			<table width="90%" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<tr class=trcolor>
					<td class="tdr" width="15%">
						�������
					</td>
					<td class="tdl" width="35%">
						<apptag:quickLoadList cssClass="" name="" listName="dispatch_task"
							keyValue="${sendTask.sendtype}" type="look" isRegion="false"></apptag:quickLoadList>
					</td>
					<td class="tdr">
						�ɵ�ʱ�䣺
					</td>
					<td class="tdl">
						${sendTask.sendtime }
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						�ɵ����ţ�
					</td>
					<td class="tdl">
						${sendTask.sendorgname }
					</td>
					<td class="tdr">
						�� �� �ˣ�
					</td>
					<td class="tdl">
						${sendTask.sendusername }
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						�����ţ�
					</td>
					<td class="tdl">
						${sendTask.acceptorgname }
					</td>
					<td class="tdr">
						�����ˣ�
					</td>
					<td class="tdl">
						${sendTask.acceptusername }
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						�������⣺
					</td>
					<td class="tdl" colspan="3">
						${sendTask.sendtopic }
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						����˵����
					</td>
					<td class="tdl" colspan="3">
						${sendTask.sendtext }
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						����ʱ�ޣ�
					</td>
					<td class="tdl" colspan="3">
						${processterm }
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						���񸽼���
					</td>
					<td class="tdl" colspan="3">
						<apptag:upload cssClass="" entityId="${sendTask.id}"
							entityType="COMMON_SENDTASK" state="look" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						ת�ɴ����ˣ�
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
						ת��˵����
					</td>
					<td class="tdl" colspan="3">
						<textarea name="sendTaskTransfer.transferRemark"
							title="ת��˵���510�����֣�" style="width: 82%" rows="5"
							class="textarea"></textarea>
					</td>
				</tr>
			</table>
			<table align="center">
				<tr>
					<td>
						<input type="submit" value="ת��" class="button">
					</td>
					<td>
						<input type="reset" value="����" class="button">
					</td>
					<td>
						<input type="button" value="����" class="button" onclick="goBack()">
					</td>
				</tr>
			</table>
		</s:form>
	</body>
</html>
