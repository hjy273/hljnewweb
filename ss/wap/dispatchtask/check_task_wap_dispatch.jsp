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
		<!--��ʾһ���ɵ���ϸ��Ϣҳ��-->
		<br>
		<template:titile value="���񵥷������" />
		<p>
			�������⣺
			<bean:write name="bean" property="sendtopic" />
			&nbsp;&nbsp; <br/>
			�������
			<apptag:quickLoadList cssClass="" name="" listName="dispatch_task"
				keyValue="${bean.sendtype}" type="look"></apptag:quickLoadList>
			<apptag:quickLoadList cssClass="" name=""
				listName="dispatch_task_con" keyValue="${bean.sendtype}" type="look"></apptag:quickLoadList>
			&nbsp;&nbsp; <br/>
			����ʱ�ޣ�
			<bean:write property="processterm" name="bean" />
			&nbsp;&nbsp; <br/>
			����Ҫ��
			<bean:write property="replyRequest" name="bean" />
		</p>
		<p>
			<logic:notEmpty name="reply_list">
				<logic:iterate id="oneReplyTask" name="reply_list">
					<bean:define id="replyId" name="oneReplyTask" property="replyid" />
					<bean:define id="isReader" name="oneReplyTask" property="is_reader" />
					�����ˣ�<bean:write name="oneReplyTask" property="replyusername" />
					<br/>
					����ʱ�䣺<bean:write name="oneReplyTask" property="replytime" />
					<br/>
					���������<bean:write name="oneReplyTask" property="replyresultlabel" />
					&nbsp;&nbsp; 
					<c:if test="${isReader=='0'}">
						<a href="javascript:toCheckTransferForm('${replyId }','${bean.senddeptid }')"> ת�� </a>
						<a href="javascript:toCheckForm('${replyId }')"> ��� </a>
					</c:if>
					<c:if test="${isReader=='1'}">
						<a href="javascript:toCheckReadForm('${replyId }')"> �鿴 </a>
					</c:if>
					<br />
				</logic:iterate>
			</logic:notEmpty>
		</p>
		<p align="center">
			<input type="button" onclick="goBack()" value="���ش���">
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
