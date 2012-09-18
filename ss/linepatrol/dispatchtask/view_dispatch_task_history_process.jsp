<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>

<html>
	<head>
		<title>send task</title>
		<link rel="stylesheet" href="${ctx}/linepatrol/css/button.css" 	type="text/css" media="screen, print" />
		<script type="" language="javascript">
		toUrl=function(btn,taskName,taskOutCome){
			var url="${ctx}/dispatchtask/dispatch_task.do?method=queryForFinishHandledDispatchTask&&task_name="+taskName+"&&task_out_come="+taskOutCome;
			parent.location=url;
		}
		</script>
	</head>
	<body>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<div align="center">
						<input type="button" class="button_not_click"
							id="btnDispatchTask" value="���ɷ�(${dispatched_task_num }��)"
							style="cursor: hand;" onclick="toUrl(this,'start','dispatch');" />
					</div>
				</td>
				<td>
					<div align="center">
						<img src="${ctx}/linepatrol/images/arrow_right.gif" />
					</div>
				</td>
				<td>
					<div align="center">
						<input type="button" class="button_not_click" id="btnSignInTask"
							value="��ǩ��(${signed_in_task_num }��)" style="cursor: hand;"
							onclick="toUrl(this,'sign_in_task,tranfer_sign_in_task','reply');" />
						��
						<input type="button" class="button_not_click"
							id="btnTransferTask"
							value="��ת��(${transfered_sign_in_task_num }��)"
							style="cursor: hand;"
							onclick="toUrl(this,'sign_in_task','transfer_dispatch');" />
					</div>
				</td>
				<td>
					<div align="center">
						<img src="${ctx}/linepatrol/images/arrow_right.gif" />
					</div>
				</td>
				<td>
					<div align="center">
						<input type="button" class="button_not_click" id="btnReplyTask"
							value="�ѷ���(${replyed_task_num }��)" style="cursor: hand;"
							onclick="toUrl(this,'reply_task','check');" />
					</div>
				</td>
				<td>
					<div align="center">
						<img src="${ctx}/linepatrol/images/arrow_right.gif" />
					</div>
				</td>
				<td>
					<div align="center">
						<input type="button" class="button_not_click" id="btnCheckTask"
							value="�����(${checked_task_num }��)" style="cursor: hand;"
							onclick="toUrl(this, 'check_task','not_passed,end,transfer,read');" />
					</div>
				</td>
				<td>
				</td>
				<td>
				</td>
			</tr>
			<tr>
				<td>
					<div align="center">
						<!-- <img src="${ctx}/linepatrol/images/arrow_down.gif" /> -->
					</div>
				</td>
				<td height="10" colspan="9"></td>
			</tr>
			<tr>
				<td>
					<div align="center">
						<!-- 
						<input type="button" class="button_not_click" id="btnCancelTask"
							value="��ȡ��(��)" style="cursor: hand;"
							onclick="toUrl(this,'any','cancel');" />
						 -->
					</div>
				</td>
				<td>
					<div align="center">
						<img src="${ctx}/linepatrol/images/arrow_right_down.gif" />
					</div>
				</td>
				<td>
					<div align="center">
						<input type="button" class="button_not_click" id="btnRefuseTask"
							value="�Ѿ�ǩ(${refused_task_num }��)" style="cursor: hand;"
							onclick="toUrl(this, 'sign_in_task,tranfer_sign_in_task','refuse');" />
					</div>
				</td>
				<td>
					<div align="center">
						<img src="${ctx}/linepatrol/images/arrow_right.gif" />
					</div>
				</td>
				<td>
					<div align="center">
						<input type="button" class="button_not_click"
							id="btnRefuseConfirmTask" value="��ȷ��(${confirmed_task_num }��)"
							style="cursor: hand;"
							onclick="toUrl(this, 'refuse_confirm_task', 'not_passed,end');" />
					</div>
				</td>
				<td style="text-align: center;">
				</td>
				<td style="text-align: center;">
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
		</table>
		<logic:notEmpty name="task_name">
			<logic:equal value="start" name="task_name">
				<script type="text/javascript">
	document.getElementById('btnDispatchTask').className = "button_clicked";
</script>
			</logic:equal>
			<logic:equal value="any" name="task_name">
				<script type="text/javascript">
	document.getElementById('btnCancelTask').className = "button_clicked";
</script>
			</logic:equal>
			<logic:equal value="sign_in_task,tranfer_sign_in_task"
				name="task_name">
				<logic:equal value="reply" name="task_out_come">
					<script type="text/javascript">
	document.getElementById('btnSignInTask').className = "button_clicked";
</script>
				</logic:equal>
			</logic:equal>
			<logic:equal value="sign_in_task" name="task_name">
				<script type="text/javascript">
	document.getElementById('btnTransferTask').className = "button_clicked";
</script>
			</logic:equal>
			<logic:equal value="sign_in_task,tranfer_sign_in_task"
				name="task_name">
				<logic:equal value="refuse" name="task_out_come">
					<script type="text/javascript">
	document.getElementById('btnRefuseTask').className = "button_clicked";
</script>
				</logic:equal>
			</logic:equal>
			<logic:equal value="refuse_confirm_task" name="task_name">
				<script type="text/javascript">
	document.getElementById('btnRefuseConfirmTask').className = "button_clicked";
</script>
			</logic:equal>
			<logic:equal value="reply_task" name="task_name">
				<script type="text/javascript">
	document.getElementById('btnReplyTask').className = "button_clicked";
</script>
			</logic:equal>
			<logic:equal value="check_task" name="task_name">
				<script type="text/javascript">
	document.getElementById('btnCheckTask').className = "button_clicked";
</script>
			</logic:equal>
		</logic:notEmpty>
	</body>
</html>
