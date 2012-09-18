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
		toCheckTransferForm=function(replyId){
			var actionUrl="${ctx}/dispatchtask/check_task.do?method=checkTaskTransferForm";
			var queryString="reply_id="+replyId+"&dispatch_id=${dispatch_id}";
			location=actionUrl+"&"+queryString+"&rnd="+Math.random();
		}
		toCheckReadForm=function(replyId){
			var actionUrl="${ctx}/dispatchtask/check_task.do?method=checkTaskReadForm";
			var queryString="reply_id="+replyId+"&dispatch_id=${dispatch_id}";
			location=actionUrl+"&"+queryString+"&rnd="+Math.random();
		}
		toCheckForm=function(replyId){
			var actionUrl="${ctx}/dispatchtask/check_task.do?method=checkTaskForm";
			var queryString="reply_id="+replyId+"&dispatch_id=${dispatch_id}";
			location=actionUrl+"&"+queryString+"&rnd="+Math.random();
		}
		</script>
	</head>
	<body>
		<!--��ʾһ���ɵ���ϸ��Ϣҳ��-->
		<br>
		<template:titile value="���񵥷������" />
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
				<td class="tdl">
					<bean:write name="bean" property="sendtime" />
				</td>
				<td class="tdr">
					�ɵ����ţ�
				</td>
				<td class="tdl">
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
				<td colspan="4" style="padding:10px;">
					<logic:notEmpty name="reply_list">
						<table border="1" cellpadding="0" cellspacing="0" width="100%"
							style="border-collapse: collapse;">
							<tr>
								<td>
									������
								</td>
								<td>
									����ʱ��
								</td>
								<td>
									�������
								</td>
								<td>
									����
								</td>
							</tr>
							<logic:iterate id="oneReplyTask" name="reply_list">
								<bean:define id="replyId" name="oneReplyTask" property="replyid" />
								<bean:define id="isReader" name="oneReplyTask"
									property="is_reader" />
								<tr>
									<td>
										<bean:write name="oneReplyTask" property="replyusername" />
									</td>
									<td>
										<bean:write name="oneReplyTask" property="replytime" />
									</td>
									<td>
										<bean:write name="oneReplyTask" property="replyresultlabel" />
									</td>
									<td>
										<c:if test="${isReader=='0'}">
											<a href="javascript:toCheckTransferForm('${replyId }')">
												ת�� </a>
											<a href="javascript:toCheckForm('${replyId }')"> ��� </a>
										</c:if>
										<c:if test="${isReader=='1'}">
											<a href="javascript:toCheckReadForm('${replyId }')"> �鿴 </a>
										</c:if>
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
