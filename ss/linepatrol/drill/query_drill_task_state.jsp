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
	</head>
	<body>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>

				<td style="text-align: center;">
					<input type="button" name="btnCreateDrillProj"
						class="button_not_click" style="cursor: hand;" value="制定演练方案"
						onclick="toUrl(this,'create_drill_proj_task');" />
				</td>
				<td style="text-align: center;">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td style="text-align: center;">
					<input type="button" name="btnApproveDrillProj"
						class="button_not_click" style="cursor: hand;" value="审核演练方案"
						onclick="toUrl(this,'approve_drill_proj_task');" />
				</td>
				<td style="text-align: center;">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td style="text-align: center;">
					<input type="button" name="btnCreateDrillSummary"
						class="button_not_click" style="cursor: hand;" value="提交演练总结"
						onclick="toUrl(this,'create_drill_summary_task');" />
				</td>
				<td style="text-align: center;">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td style="text-align: center;">
					<input type="button" name="btnApproveDrillSummary"
						class="button_not_click" style="cursor: hand;" value="审核演练总结"
						onclick="toUrl(this,'approve_drill_summary_task');" />
				</td>
			</tr>
			<tr>

				<td style="text-align: center;">
					<img src="${ctx}/linepatrol/images/arrow_up.gif" />
				</td>
				<td style="text-align: center;">
					&nbsp;

				</td>
				<td style="text-align: center;">
					<img src="${ctx}/linepatrol/images/arrow_down.gif" />
				</td>
				<td style="text-align: center;">
					&nbsp;

				</td>
				<td style="text-align: center;">
					<img src="${ctx}/linepatrol/images/arrow_up.gif" />
				</td>
				<td style="text-align: center;">
					&nbsp;
				</td>
				<td style="text-align: center;">
					<img src="${ctx}/linepatrol/images/arrow_down.gif" />
				</td>
			</tr>
			<tr>
				<td style="text-align: center;">
					<input type="button" name="btnCreateDrillTask"
						class="button_not_hits" disabled="disabled" value="制定演练任务" />
				</td>
				<td style="text-align: center;">
					&nbsp;
				</td>
				<td style="text-align: center;">
					<input type="button" name="btnChangeDrillProj"
						class="button_not_click" style="cursor: hand;" value="变更演练方案"
						onclick="toUrl(this,'change_drill_proj_task');" />
				</td>
				<td style="text-align: center;">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td style="text-align: center;">
					<input type="button" name="btnApproveChangeDrillProj"
						class="button_not_click" style="cursor: hand;" value="审核变更方案"
						onclick="toUrl(this, 'approve_change_drill_proj_task');" />
				</td>
				<td style="text-align: center;">
					&nbsp;

				</td>
				<td style="text-align: center;">
					<input type="button" name="btnEvaluate" class="button_not_click"
						style="cursor: hand;" value="考核评分"
						onClick="toUrl(this, 'evaluate_task');"/>
				</td>
			</tr>
		</table>
		<logic:notEmpty name="task_name">
			<logic:equal value="create_drill_proj_task" name="task_name">
				<script type="text/javascript">
				btnCreateDrillProj.className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="approve_drill_proj_task" name="task_name">
				<script type="text/javascript">
				btnApproveDrillProj.className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="change_drill_proj_task" name="task_name">
				<script type="text/javascript">
				btnChangeDrillProj.className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="approve_change_drill_proj_task" name="task_name">
				<script type="text/javascript">
				btnApproveChangeDrillProj.className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="create_drill_summary_task" name="task_name">
				<script type="text/javascript">
				btnCreateDrillSummary.className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="approve_drill_summary_task" name="task_name">
				<script type="text/javascript">
				btnApproveDrillSummary.className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="evaluate_task" name="task_name">
				<script type="text/javascript">
				btnEvaluate.className = "button_clicked";
				</script>
			</logic:equal>
		</logic:notEmpty>
		<logic:notEmpty name="task_names">
			<logic:iterate id="task_name" name="task_names" >
			<logic:equal value="create_drill_proj_task" name="task_name">
				<script type="text/javascript">
				btnCreateDrillProj.className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="approve_drill_proj_task" name="task_name">
				<script type="text/javascript">
				btnApproveDrillProj.className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="change_drill_proj_task" name="task_name">
				<script type="text/javascript">
				btnChangeDrillProj.className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="approve_change_drill_proj_task" name="task_name">
				<script type="text/javascript">
				btnApproveChangeDrillProj.className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="create_drill_summary_task" name="task_name">
				<script type="text/javascript">
				btnCreateDrillSummary.className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="approve_drill_summary_task" name="task_name">
				<script type="text/javascript">
				btnApproveDrillSummary.className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="evaluate_task" name="task_name">
				<script type="text/javascript">
				btnEvaluate.className = "button_clicked";
				</script>
			</logic:equal>
			</logic:iterate>
		</logic:notEmpty>
	</body>
</html>
