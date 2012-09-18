<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>

<html>
	<head>
		<title>query for task state</title>
		<link rel="stylesheet" href="${ctx}/linepatrol/css/button.css" type="text/css" media="screen, print" />
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
	</head>
	<body>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td style="text-align: center;">
					<input id="btnApply" type="button" value="提交交维申请"
						class="button_not_hits" disabled="disabled" />
				</td>
				<td style="text-align: center;">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td style="text-align: center;">
					<input type="button" id="btnAllotTask"
						value="核准交维申请"
						class="button_not_click" style="cursor: hand;"
						onclick="toUrl(this,'acceptance__allotTasks');" />
				</td>
				<td style="text-align: center;">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td style="text-align: center;">
					<input type="button" id="btnRecordData"
						value="录入交维数据"
						class="button_not_click" style="cursor: hand;"
						onclick="toUrl(this,'acceptancesubflow__record');" />
				</td>
				<td style="text-align: center;" >
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td style="text-align: center;">
					<input type="button" id="btnApproveRecord"
						value="审核交维数据"
						class="button_not_click" style="cursor: hand;"
						onclick="toUrl(this,'acceptancesubflow__approve');" />
				</td>
				<td style="text-align: center;" >
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td style="text-align: center;">
					<input type="button" id="btnEvaluate"
						value="考核信息"
						class="button_not_click" style="cursor: hand;"
						onclick="toUrl(this,'acceptancesubflow__exam');" />
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td style="text-align: center;">
					<img src="${ctx}/linepatrol/images/arrow_down.gif" />
				</td>
				<td>
					&nbsp;
				</td>
				<td style="text-align: center;" >
					<img src="${ctx}/linepatrol/images/arrow_up.gif" />
				</td>
				<td style="text-align: center;">
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td style="text-align: center;">
					<input type="button" id="btnClaimTask"
						value="认领交维任务"
						class="button_not_click" style="cursor: hand;"
						onclick="toUrl(this,'acceptance__claimTask');" />
				</td>
				<td style="text-align: center;" >
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td style="text-align: center;">
					<input type="button" id="btnApproveClaimTask"
						value="核准交维任务"
						class="button_not_click" style="cursor: hand;"
						onclick="toUrl(this, 'acceptance__approve');" />
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
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
			<logic:equal value="acceptance__allotTasks" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnAllotTask').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="acceptance__claimTask" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnClaimTask').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="acceptance__approve" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnApproveClaimTask').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="acceptancesubflow__record" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnRecordData').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="acceptancesubflow__approve" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnApproveRecord').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="acceptancesubflow__exam" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnEvaluate').className = "button_clicked";
				</script>
			</logic:equal>
		</logic:notEmpty>
		<logic:notEmpty name="task_names">
			<logic:iterate id="task_name" name="task_names" >
			<logic:equal value="acceptance__allotTasks" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnAllotTask').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="acceptance__claimTask" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnClaimTask').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="acceptance__approve" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnApproveClaimTask').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="acceptancesubflow__record" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnRecordData').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="acceptancesubflow__approve" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnApproveRecord').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="acceptancesubflow__exam" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnEvaluate').className = "button_clicked";
				</script>
			</logic:equal>
			</logic:iterate>
		</logic:notEmpty>
	</body>
</html>
