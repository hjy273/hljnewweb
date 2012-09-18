<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/linepatrol/css/button.css"
	type="text/css" media="screen, print" />
<html>
	<head>
		<title>query for task state</title>
		<script type="" language="javascript">
		toUrl=function(btn,taskName){
			var parentForm=parent.document.forms[0];
			if(btn.className=="button_not_click"){
				btn.className="button_clicked";
				var inputElement=parent.document.getElementById("taskStates_"+taskName);
				if(typeof(inputElement)=="undefined"||inputElement==null){
					inputElement=parent.document.createElement("input");
				}
				inputElement.id="taskStates_"+taskName;
				inputElement.name="taskStates";
				inputElement.type="hidden";
				inputElement.value=taskName;
				parentForm.appendChild(inputElement);
			}else{
				btn.className="button_not_click";
				var inputElement=parent.document.getElementById("taskStates_"+taskName);
				if(typeof(inputElement)!="undefined"&&inputElement!=null){
					parentForm.removeChild(inputElement);
				}
			}
			parentForm.submit();
		}
		</script>
		<style type="text/css">
			table td{
				text-align:center;
			}
		</style>
	</head>
	<body>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<div align="center">
						<input type="button" class="button_not_hits"
							name="btnStart" value="开 始" disabled="disabled" />
					</div>
				</td>
				<td style="text-align: center; width: 25;">
				</td>
				<td>
					<input type="button" name="btnApply" class="button_not_click"
						style="cursor: hand;" value="提交工程申请"
						onclick="toUrl(this,'remedy_apply_task');" />
				</td>
				<td style="text-align: center; width: 25;">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>
					<input type="button" name="btnApplyApprove"
						class="button_not_click" style="cursor: hand;"
						value="待审核工程申请"
						onclick="toUrl(this,'remedy_approve_task');" />
				</td>
				<td style="text-align: center; width: 25;">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>
					<div align="center">
						<input type="button" class="button_not_hits"
							name="btnEnd" value="结 束" disabled="disabled" />
					</div>
				</td>
			</tr>
		</table>
		<logic:notEmpty name="task_name">
			<logic:equal value="remedy_apply_task" name="task_name">
				<script type="text/javascript">
				btnApply.className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="remedy_approve_task" name="task_name">
				<script type="text/javascript">
				btnApplyApprove.className = "button_clicked";
				</script>
			</logic:equal>
		</logic:notEmpty>
		<logic:notEmpty name="task_names">
			<logic:iterate id="task_name" name="task_names" >
			<logic:equal value="remedy_apply_task" name="task_name">
				<script type="text/javascript">
				btnApply.className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="remedy_approve_task" name="task_name">
				<script type="text/javascript">
				btnApplyApprove.className = "button_clicked";
				</script>
			</logic:equal>
			</logic:iterate>
		</logic:notEmpty>
	</body>
</html>
