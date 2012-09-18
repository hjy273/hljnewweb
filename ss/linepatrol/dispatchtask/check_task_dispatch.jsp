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
		<!--显示一个派单详细信息页面-->
		<br>
		<template:titile value="任务单反馈审核" />
		<table width="90%" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<tr class=trcolor>
				<td class="tdr" width="15%" >
					派单流水号：
				</td>
				<td class="tdl" width="35%">
					<bean:write name="bean" property="serialnumber" />
				</td>
				<td class="tdr" width="15%">
					工作类别：
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
					派单时间：
				</td>
				<td class="tdl">
					<bean:write name="bean" property="sendtime" />
				</td>
				<td class="tdr">
					派单部门：
				</td>
				<td class="tdl">
					<bean:write name="bean" property="senddeptname" />
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr" >
					派 单 人：
				</td>
				<td class="tdl">
					<bean:write name="bean" property="sendusername" />
				</td>
				<td class="tdr">
					受理部门和受理人：
				</td>
				<td class="tdl">
					<logic:present name="bean" property="acceptUserList">
						<logic:iterate id="oneAcceptUser" name="bean"
							property="acceptUserList">
								部门：<bean:write name="oneAcceptUser" property="departname" />
								用户：<bean:write name="oneAcceptUser" property="username" />
							<br />
						</logic:iterate>
					</logic:present>
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr" >
					任务主题：
				</td>
				<td class="tdl" colspan="3">
					<bean:write name="bean" property="sendtopic" />
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr" >
					任务说明：
				</td>
				<td class="tdl" colspan="3">
					<div>
						<bean:write name="bean" property="sendtext" />
					</div>
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr" >
					反馈要求：
				</td>
				<td class="tdl" colspan="3">
					<bean:write property="replyRequest" name="bean" />
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr" >
					处理时限：
				</td>
				<td class="tdl" colspan="3">
					<bean:write property="processterm" name="bean" />
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr" >
					任务附件：
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
									反馈人
								</td>
								<td>
									反馈时间
								</td>
								<td>
									反馈结果
								</td>
								<td>
									操作
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
												转审 </a>
											<a href="javascript:toCheckForm('${replyId }')"> 审核 </a>
										</c:if>
										<c:if test="${isReader=='1'}">
											<a href="javascript:toCheckReadForm('${replyId }')"> 查看 </a>
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
			<input type="button" class="button" onclick="goBack()" value="返回">
		</p>
	</body>
</html>
