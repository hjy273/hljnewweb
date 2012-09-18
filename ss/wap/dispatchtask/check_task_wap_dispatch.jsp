<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/wap/header.jsp"%>
<html>
	<head>
		<title>sendtask</title>
		<META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
		<META HTTP-EQUIV="expires" CONTENT="0">
		<script type="" language="javascript">
		goBack=function(){
			var url="${sessionScope.S_BACK_URL}";
			location=url;
		}
		toCheckTransferForm=function(replyId,sendDepartId){
			var actionUrl="${ctx}/wap/dispatchtask/check_task.do?method=checkTaskTransferForm&&env=wap";
			var queryString="reply_id="+replyId+"&dispatch_id=${dispatch_id}";
			var form1=document.forms["toApproverUrlForm"];
			form1.action_url.value=actionUrl+"&"+queryString+"&rnd="+Math.random();
			form1.depart_id.value=sendDepartId;
			form1.object_id.value=replyId;
			form1.submit();
		}
		toCheckReadForm=function(replyId){
			var actionUrl="${ctx}/wap/dispatchtask/check_task.do?method=checkTaskReadForm&&env=${env}";
			var queryString="reply_id="+replyId+"&dispatch_id=${dispatch_id}";
			location=actionUrl+"&"+queryString+"&rnd="+Math.random();
		}
		toCheckForm=function(replyId){
			var actionUrl="${ctx}/wap/dispatchtask/check_task.do?method=checkTaskForm&&env=${env}";
			var queryString="reply_id="+replyId+"&dispatch_id=${dispatch_id}";
			location=actionUrl+"&"+queryString+"&rnd="+Math.random();
		}
		</script>
	</head>
	<body>
		<!--显示一个派单详细信息页面-->
		<br>
		<template:titile value="任务单反馈审核" />
		<p>
			任务主题：
			<bean:write name="bean" property="sendtopic" />
			&nbsp;&nbsp; <br/>
			工作类别：
			<apptag:quickLoadList cssClass="" name="" listName="dispatch_task"
				keyValue="${bean.sendtype}" type="look"></apptag:quickLoadList>
			<apptag:quickLoadList cssClass="" name=""
				listName="dispatch_task_con" keyValue="${bean.sendtype}" type="look"></apptag:quickLoadList>
			&nbsp;&nbsp; <br/>
			处理时限：
			<bean:write property="processterm" name="bean" />
			&nbsp;&nbsp; <br/>
			反馈要求：
			<bean:write property="replyRequest" name="bean" />
		</p>
		<p>
			<logic:notEmpty name="reply_list">
				<logic:iterate id="oneReplyTask" name="reply_list">
					<bean:define id="replyId" name="oneReplyTask" property="replyid" />
					<bean:define id="isReader" name="oneReplyTask" property="is_reader" />
					反馈人：<bean:write name="oneReplyTask" property="replyusername" />
					<br/>
					反馈时间：<bean:write name="oneReplyTask" property="replytime" />
					<br/>
					反馈结果：<bean:write name="oneReplyTask" property="replyresultlabel" />
					&nbsp;&nbsp; 
					<c:if test="${isReader=='0'}">
						<a href="javascript:toCheckTransferForm('${replyId }','${bean.senddeptid }')"> 转审 </a>
						<a href="javascript:toCheckForm('${replyId }')"> 审核 </a>
					</c:if>
					<c:if test="${isReader=='1'}">
						<a href="javascript:toCheckReadForm('${replyId }')"> 查看 </a>
					</c:if>
					<br />
				</logic:iterate>
			</logic:notEmpty>
		</p>
		<p align="center">
			<input type="button" onclick="goBack()" value="返回待办">
		</p>
		<div style="display:none;">
			<form id="toApproverUrlForm" method="post"
				action="${ctx}/wap/load_approvers.do?method=loadWapApprovers">
				<c:if test="${sessionScope.LOGIN_USER.deptype=='1'}">
					<input name="display_type" value="mobile" type="hidden" />
				</c:if>
				<c:if test="${sessionScope.LOGIN_USER.deptype=='2'}">
					<input name="display_type" value="contractor" type="hidden" />
				</c:if>
				<input name="exist_value" value="" type="hidden" />
				<input name="object_id" value="" type="hidden" />
				<input name="object_type" value="LP_SENDTASKREPLY" type="hidden" />
				<input name="except_self" value="true" type="hidden" />
				<input name="action_url" value="" type="hidden" />
				<input name="depart_id" value="" type="hidden" />
				<input name="approver_type" value="transfer" type="hidden" />
				<input name="approver_input_name" value="approverid" type="hidden" />
				<input name="reader_input_name" value="readerid" type="hidden" />
			</form>
		</div>
	</body>
</html>
