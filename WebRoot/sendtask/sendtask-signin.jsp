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
				alert("û��ѡ��ǩ�������");
				return false;
			}
			if(valCharLength(formElements["sendTaskSignin.signinRemark"].value)>1020){
				alert("ǩ�ձ�ע����̫�࣡���ܳ���510������");
				return false;
			}
			return true;
		}
		</script>
	</head>
	<body>
		<!--��ʾһ����ǩ���ɵ���ϸ��Ϣҳ��-->
		<br>
		<template:titile value="ǩ���ɵ�" />
		<s:form action="sendTaskSigninAction_save" name="addApplyForm"
			onsubmit="return validate();" enctype="multipart/form-data">
			<input type="hidden" name="sendTaskSignin.sendtaskid" value="${id}" />
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
						ǩ�������
					</td>
					<td class="tdl" colspan="3">
						<input name="sendTaskSignin.taskId"
							value="${sendTaskSignin.taskId}" type="hidden" />
						<input name="sendTaskSignin.signinuserid"
							value="${LOGIN_USER.userID }" type="hidden" />
						<input name="sendTaskSignin.signinResult" value="pass"
							type="radio">
						ǩ��
						<input name="sendTaskSignin.signinResult" value="reject"
							type="radio">
						��ǩ
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						ǩ�ձ�ע��
					</td>
					<td class="tdl" colspan="3">
						<textarea name="sendTaskSignin.signinRemark"
							title="�벻Ҫ����256�����ֻ���512��Ӣ���ַ������򽫽ضϡ�" style="width: 80%" rows="6"
							class="textarea"></textarea>
					</td>
				</tr>
			</table>
			<table align="center">
				<tr>
					<td>
						<input type="submit" value="ǩ��" class="button">
					</td>
					<td>
						<input type="reset" value="����" class="button">
					</td>
					<td>
						<input type="button" value="����" class="button"
							onclick="goBack()">
					</td>
				</tr>
			</table>
		</s:form>
	</body>
</html>
