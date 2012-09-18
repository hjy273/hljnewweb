<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<SCRIPT language=JavaScript src="${ctx}/common/PopupDlg.js" type=""></SCRIPT>
<SCRIPT language=javascript src="${ctx}/common/Comm.js" type=""></SCRIPT>
<html>
	<head>
		<title>sendtask</title>
		<script type="" language="javascript">
		goBack=function(){
			var url="${sessionScope.S_BACK_URL}";
			location=url;
		}
		toRefuseConfirmForm=function(signInId){
			var actionUrl="${ctx}/dispatchtask/sign_in_task.do?method=refuseConfirmTaskForm";
			var queryString="sign_in_id="+signInId+"&dispatch_id=${dispatch_id}";
			location=actionUrl+"&"+queryString+"&rnd="+Math.random();
		}
		</script>
	</head>
	<body>
		<!--��ʾһ���ɵ���ϸ��Ϣҳ��-->
		<br>
		<template:titile value="���񵥾�ǩȷ��" />
		<table width="90%" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<tr class=trcolor>
				<td class="tdr" width="15%" >
					�ɵ���ˮ�ţ�
				</td>
				<td class="tdl" width="35%">
					<bean:write name="bean" property="serialnumber" />
				</td>
				<td class="tdr" width="15%">
					�������
				</td>
				<td class="tdl" width="35%">
					<apptag:quickLoadList cssClass="" name="" listName="dispatch_task"
						keyValue="${bean.sendtype}" type="look"></apptag:quickLoadList>
					<apptag:quickLoadList cssClass="" name="" listName="dispatch_task_con"
						keyValue="${bean.sendtype}" type="look"></apptag:quickLoadList>
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr" >
					�ɵ�ʱ�䣺
				</td>
				<td class="tdl" width="18%">
					<bean:write name="bean" property="sendtime" />
				</td>
				<td class="tdr">
					�ɵ����ţ�
				</td>
				<td class="tdl" width="18%">
					<bean:write name="bean" property="senddeptname" />
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr" >
					�� �� �ˣ�
				</td>
				<td class="tdl">
					<bean:write name="bean" property="sendusername" />
				</td>
				<td class="tdr">
					�����ź������ˣ�
				</td>
				<td class="tdl">
					<logic:present name="bean" property="acceptUserList">
						<logic:iterate id="oneAcceptUser" name="bean"
							property="acceptUserList">
								���ţ�<bean:write name="oneAcceptUser" property="departname" />
								�û���<bean:write name="oneAcceptUser" property="username" />
							<br />
						</logic:iterate>
					</logic:present>
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr" >
					�������⣺
				</td>
				<td class="tdl" colspan="3">
					<bean:write name="bean" property="sendtopic" />
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr" >
					����˵����
				</td>
				<td class="tdl" colspan="3">
					<div>
						<bean:write name="bean" property="sendtext" />
					</div>
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr" >
					����Ҫ��
				</td>
				<td class="tdl" colspan="3">
					<bean:write property="replyRequest" name="bean" />
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr" >
					����ʱ�ޣ�
				</td>
				<td class="tdl" colspan="3">
					<bean:write property="processterm" name="bean" />
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr" >
					���񸽼���
				</td>
				<td class="tdl" colspan="3">
					<apptag:upload cssClass="" entityId="${bean.id}"
						entityType="LP_SENDTASK" state="look" />
				</td>
			</tr>
			<tr class=trcolor>
				<td colspan="4"  style="padding:10px;">
					<logic:notEmpty name="sign_in_list">
						<table border="1" cellpadding="0" cellspacing="0" width="100%"
							style="border-collapse: collapse;">
							<tr>
								<td align="center">
									��ǩ��
								</td>
								<td align="center">
									��ǩʱ��
								</td>
								<td align="center">
									����
								</td>
							</tr>
							<logic:iterate id="oneSignInTask" name="sign_in_list">
								<bean:define id="signInId" name="oneSignInTask"
									property="signinid" />
								<tr>
									<td>
										<bean:write name="oneSignInTask" property="signinusername" />
									</td>
									<td>
										<bean:write name="oneSignInTask" property="signintime" />
									</td>
									<td>
										<a href="javascript:toRefuseConfirmForm('${signInId }')">
											��ǩȷ�� </a>
									</td>
								</tr>
							</logic:iterate>
						</table>
					</logic:notEmpty>
				</td>
			</tr>
		</table>
		<p align="center">
			<input type="button" class="button" onclick="goBack()" value="����">
		</p>
	</body>
</html>
