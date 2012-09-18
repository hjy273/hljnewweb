<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>

<html>
	<head>
		<title>sendtask</title>
		<link rel="stylesheet" href="${ctx}/linepatrol/css/button.css" 	type="text/css" media="screen, print" />
		<script type="" language="javascript">
		toUrl=function(btn,taskName){
			var url="${ctx}/dispatchtask/dispatch_task.do?method=queryForHandleDispatchTask&&task_name="+taskName;
			parent.location=url;
		}
		</script>
	</head>
	<body>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<div align="center">
						<input type="button" class="button_not_hits"
							id="btnDispatchTask" value="�� ��" disabled="disabled" />
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
							value="��ǩ��(${wait_sign_in_task_num }��)"
							style="cursor: hand;" onclick="toUrl(this,'sign_in_task');" />
						��
						<input type="button" class="button_not_click"
							id="btnTransferSignInTask"
							value="ת��ǩ��(${wait_transfer_sign_in_task_num }��)"
							style="cursor: hand;"
							onclick="toUrl(this,'tranfer_sign_in_task');" />
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
							value="�� ��(${wait_reply_task_num }��)"
							style="cursor: hand;" onclick="toUrl(this,'reply_task');" />
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
							value="�������(${wait_check_task_num }��)"
							style="cursor: hand;" onclick="toUrl(this, 'check_task');" />
					</div>
				</td>
				<td>
					
				</td>
				<td>
					
				</td>
			</tr>
			<tr>
				<td height="10" colspan="9"></td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td colspan="2" style="text-align:center;">
					<img src="${ctx}/linepatrol/images/arrow_right_down.gif" />
				</td>
				<td style="text-align:center;">
					<input type="button" class="button_not_click" id="btnRefuseConfirmTask"
						value="��ǩȷ��(${wait_refuse_confirm_task_num }��)"
						style="cursor: hand;" onclick="toUrl(this, 'refuse_confirm_task');" />
				</td>
				<td style="text-align:center;">
					
				</td>
				<td style="text-align:center;">
					
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
			<logic:equal value="sign_in_task" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnSignInTask').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="tranfer_sign_in_task" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnTransferSignInTask').className = "button_clicked";
				</script>
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
