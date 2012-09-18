<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<SCRIPT language=JavaScript src="${ctx}/common/PopupDlg.js" type=""></SCRIPT>
<SCRIPT language=javascript src="${ctx}/common/Comm.js" type=""></SCRIPT>
<html>
	<head>
		<title>sendtask</title>
		<script type="" language="javascript">
		toViewForm=function(dispatchId){
			var url="${ctx}/dispatchtask/dispatch_task.do?method=viewDispatchTask";
			var queryString="dispatch_id="+dispatchId;
			location=url+"&"+queryString+"&rnd="+Math.random();
		}
		toUpdateForm=function(dispatchId){
			var url="${ctx}/dispatchtask/dispatch_task.do?method=updateDispatchTaskForm";
			var queryString="dispatch_id="+dispatchId;
			location=url+"&"+queryString+"&rnd="+Math.random();
		}
		toDeleteForm=function(dispatchId){
			var url;
			if(confirm("ȷ��Ҫɾ���ɵ���")){
				url="${ctx}/dispatchtask/dispatch_task.do?method=deleteDispatchTask";
				var queryString="dispatch_id="+dispatchId;
				location=url+"&"+queryString+"&rnd="+Math.random();
			}
		}
		toSignInForm=function(dispatchId,subTaskId){
			var url="${ctx}/dispatchtask/sign_in_task.do?method=signInTaskForm";
			var queryString="dispatch_id="+dispatchId+"&send_accept_dept_id="+subTaskId;
			location=url+"&"+queryString+"&rnd="+Math.random();
		}
		toRefuseConfirmForm=function(dispatchId,subTaskId){
			var url="${ctx}/dispatchtask/sign_in_task.do?method=refuseDispatchTaskForm";
			var queryString="dispatch_id="+dispatchId+"&send_accept_dept_id="+subTaskId;
			location=url+"&"+queryString+"&rnd="+Math.random();
		}
		toTransferDispatchForm=function(dispatchId,subTaskId){
			var url="${ctx}/dispatchtask/sign_in_task.do?method=transferDispatchTaskForm";
			var queryString="dispatch_id="+dispatchId+"&send_accept_dept_id="+subTaskId;
			location=url+"&"+queryString+"&rnd="+Math.random();
		}
		toReplyForm=function(dispatchId,subTaskId){
			var url="${ctx}/dispatchtask/reply_task.do?method=replyTaskForm";
			var queryString="dispatch_id="+dispatchId+"&send_accept_dept_id="+subTaskId;
			location=url+"&"+queryString+"&rnd="+Math.random();
		}
		toUpdateReplyForm=function(dispatchId,replyId){
			var url="${ctx}/dispatchtask/reply_task.do?method=updateReplyTaskForm";
			var queryString="dispatch_id="+dispatchId+"&reply_id="+replyId;
			location=url+"&"+queryString+"&rnd="+Math.random();
		}
		toDeleteReplyForm=function(replyId){
			var url;
			var queryString="reply_id="+replyId;
			if(confirm("ȷ��Ҫɾ���ɵ�������")){
				url="${ctx}/dispatchtask/reply_task.do?method=deleteReplyTask";
				location=url+"&"+queryString+"&rnd="+Math.random();
			}
		}
		toCheckForm=function(dispatchId){
			var url="${ctx}/dispatchtask/check_task.do?method=checkDispatchTaskForm";
			var queryString="dispatch_id="+dispatchId;
			location=url+"&"+queryString+"&rnd="+Math.random();
		}
		toCancelForm=function(dispatchId){
			var url;
			if(confirm("ȷ��Ҫȡ���ɵ���")){
				url="${ctx}/dispatchtask/dispatch_task.do?method=cancelDispatchTaskForm";
				var queryString="dispatch_id="+dispatchId;
				//location=url+"&"+queryString+"&rnd="+Math.random();
				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
			}
		}
		</script>
	</head>
	<body>
		<!--��ʾ�����ɵ�ҳ��-->
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<br>
		<logic:notEmpty name="task_name">
			<logic:equal value="sign_in_task" name="task_name">
				<template:titile
					value="��ǩ�������ɵ��б� (<font color='red'>${sessionScope.DISPATCH_TASK_LIST_NUM}������</font>)" />
			</logic:equal>
			<logic:equal value="tranfer_sign_in_task" name="task_name">
				<template:titile
					value="��ת��ǩ�������ɵ��б� (<font color='red'>${sessionScope.DISPATCH_TASK_LIST_NUM}������</font>)" />
			</logic:equal>
			<logic:equal value="refuse_confirm_task" name="task_name">
				<template:titile
					value="����ǩȷ�������ɵ��б� (<font color='red'>${sessionScope.DISPATCH_TASK_LIST_NUM}������</font>)" />
			</logic:equal>
			<logic:equal value="reply_task" name="task_name">
				<template:titile
					value="�����������ɵ��б� (<font color='red'>${sessionScope.DISPATCH_TASK_LIST_NUM}������</font>)" />
			</logic:equal>
			<logic:equal value="check_task" name="task_name">
				<template:titile
					value="����֤�����ɵ��б� (<font color='red'>${sessionScope.DISPATCH_TASK_LIST_NUM}������</font>)" />
			</logic:equal>
		</logic:notEmpty>
		<logic:empty name="task_name">
			<template:titile
				value="���������ɵ��б� (<font color='red'>${sessionScope.DISPATCH_TASK_LIST_NUM}������</font>)" />
		</logic:empty>
		<div style="text-align: center;">
			<iframe
				src="${ctx}/dispatchtask/dispatch_task.do?method=viewDispatchTaskProcess&&task_name=${param.task_name }"
				frameborder="0" id="processGraphFrame" height="70" scrolling="no"
				width="95%"></iframe>
		</div>
		<logic:notEmpty name="DISPATCH_TASK_LIST">
			<display:table name="sessionScope.DISPATCH_TASK_LIST"
				requestURI="${ctx}/dispatchtask/dispatch_task.do?method=queryForHandleDispatchTask"
				id="currentRowObject" pagesize="20" style="width:100%">
				<bean:define id="dispatchId" name="currentRowObject" property="id" />
				<bean:define id="serialNumber" name="currentRowObject"
					property="serialnumber" />
				<bean:define id="sendTopic" name="currentRowObject"
					property="sendtopic" />
				<bean:define id="isOutTime" name="currentRowObject"
					property="is_out_time" />
				<bean:define id="processTerm" name="currentRowObject"
					property="processterm" />
				<bean:define id="flowState" name="currentRowObject"
					property="flow_state" />
				<bean:define id="sendUserId" name="currentRowObject"
					property="senduserid" />
				<bean:define id="subTaskId" name="currentRowObject" property="subid" />
				<bean:define id="existReply" name="currentRowObject"
					property="exist_reply" />
				<bean:define id="replyId" name="currentRowObject"
					property="reply_id" />
				<bean:define id="workstate" name="currentRowObject" property="workstate" />
				<display:column media="html" title="��ˮ��" sortable="true"
					style="width:90px">
					<a href="javascript:toViewForm('${dispatchId }')">
						${serialNumber } </a>
				</display:column>
				<display:column media="html" title="����" sortable="true" maxLength="40">
					<a href="javascript:toViewForm('${dispatchId }')">${sendTopic }</a>
				</display:column>
				<display:column property="sendtypelabel" title="����"
					style="width:80px" maxLength="5" sortable="true" />
				<display:column property="senddeptname" title="�ɵ���λ"
					style="width:80px" maxLength="5" sortable="true" />
				<display:column property="processterm" title="��������"
					style="width:140px" sortable="true" />
				<display:column property="sendusername" title="�ɷ���"
					style="width:60px" maxLength="4" sortable="true" />
				<display:column media="html" title="����" style="width:100px">
					<c:if test="${sessionScope.LOGIN_USER.userID==sendUserId}">
						<!-- 
						<a href="javascript:toUpdateForm('${dispatchId }')">�޸�</a>
						 -->
					</c:if>
					<c:if
						test="${flowState=='sign_in_task'||flowState=='tranfer_sign_in_task'}">
						<a
							href="javascript:toSignInForm('${dispatchId }','${subTaskId }')">ǩ��</a>
					</c:if>
					<c:if test="${flowState=='sign_in_task'}">
						<a
							href="javascript:toTransferDispatchForm('${dispatchId }','${subTaskId }')">ת��</a>
					</c:if>
					<c:if test="${flowState=='refuse_confirm_task'}">
						<a
							href="javascript:toRefuseConfirmForm('${dispatchId }','${subTaskId }')">��ǩȷ��</a>
					</c:if>
					<c:if test="${flowState=='reply_task'}">
						<c:if test="${existReply=='0'}">
							<a
								href="javascript:toReplyForm('${dispatchId }','${subTaskId }')">����</a>
						</c:if>
						<c:if test="${existReply=='1'}">
							<a
								href="javascript:toUpdateReplyForm('${dispatchId }','${replyId }')">�޸ķ���</a>
							<a href="javascript:toDeleteReplyForm('${replyId }')">ɾ������</a>
						</c:if>
					</c:if>
					<c:if test="${flowState=='check_task'}">
						<a href="javascript:toCheckForm('${dispatchId }')">���</a>
					</c:if>
					<c:if test="${sessionScope.LOGIN_USER.deptype=='1' && workstate!= '999'}">
						<a href="javascript:toCancelForm('${dispatchId }')">ȡ��</a>
					</c:if>
				</display:column>
			</display:table>
		</logic:notEmpty>
		<logic:notEmpty name="DISPATCH_TASK_LIST">
			<html:link
				action="/dispatchtask/dispatch_task.do?method=exportDispatchTaskResult">����ΪExcel�ļ�</html:link>
		</logic:notEmpty>
	</body>
</html>
