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
				<td>
					<input type="button" name="btnCreateTestPlan"
						class="button_not_hits" disabled="disabled" value="制定测试计划" />
				</td>
				<td style="text-align: center">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>
					<input type="button" name="btnApproveTestPlan"
						class="button_not_click" style="cursor: hand;" value="审核测试计划"
						onclick="toUrl(this,'approve_test_plan_task');" />
				</td>
				<td style="text-align: center">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>
					<input type="button" name="btnRecordData" class="button_not_click"
						style="cursor: hand;" value="录入测试数据"
						onclick="toUrl(this,'record_test_data_task');" />
				</td>
				<td style="text-align: center">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>
					<input type="button" name="btnApproveRecordData"
						class="button_not_click" style="cursor: hand;" value="审核测试数据"
						onclick="toUrl(this,'approve_test_data_task');" />
				</td>
				<td style="text-align: center">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>
					<input type="button" name="btnEvaluate" class="button_not_click"
						style="cursor: hand;" value="考核评分"
						onclick="toUrl(this, 'evaluate_task');" />
				</td>
			</tr>
		</table>
		<logic:notEmpty name="task_name">
			<logic:equal value="approve_test_plan_task" name="task_name">
				<script type="text/javascript">
				btnApproveTestPlan.className="button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="record_test_data_task" name="task_name">
				<script type="text/javascript">
				btnRecordData.className="button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="approve_test_data_task" name="task_name">
				<script type="text/javascript">
				btnApproveRecordData.className="button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="evaluate_task" name="task_name">
				<script type="text/javascript">
				btnEvaluate.className="button_clicked";
				</script>
			</logic:equal>
		</logic:notEmpty>
		<logic:notEmpty name="task_names">
			<logic:iterate id="task_name" name="task_names" >
			<logic:equal value="approve_test_plan_task" name="task_name">
				<script type="text/javascript">
				btnApproveTestPlan.className="button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="record_test_data_task" name="task_name">
				<script type="text/javascript">
				btnRecordData.className="button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="approve_test_data_task" name="task_name">
				<script type="text/javascript">
				btnApproveRecordData.className="button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="evaluate_task" name="task_name">
				<script type="text/javascript">
				btnEvaluate.className="button_clicked";
				</script>
			</logic:equal>
			</logic:iterate>
		</logic:notEmpty>
	</body>
</html>
