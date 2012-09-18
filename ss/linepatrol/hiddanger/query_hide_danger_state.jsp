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
		};
		</script>
		<style type="text/css">
table td {
	text-align: center;
}
</style>
	</head>
	<body>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<input type="button" id="btnRegist" class="button_not_hits"
						value="Òþ»¼µÇ¼Ç" disabled="disabled" />
				</td>
				<td style="text-align: center; width: 25">
				</td>
				<td>
					<input type="button" id="btnReportApprove"
						class="button_not_click" style="cursor: hand;" value="´ýÉóÒþ»¼"
						onclick="toUrl(this,'add_approve_task');" />
				</td>
				<td style="text-align: center; width: 25">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>
					<input type="button" id="btnMakePlan" class="button_not_click"
						style="cursor: hand;" value="ÖÆ¶¨¼Æ»®"
						onclick="toUrl(this,'make_plan');" />
				</td>
				<td style="text-align: center; width: 25">
					&nbsp;
				</td>
				<td style="text-align: center; width: 25">
					<input type="button" id="btnEndPlanApprove"
						class="button_not_click" style="cursor: hand;" value="´ýÉóÖÕÖ¹ÉêÇë"
						onClick="toUrl(this, 'end_approve');" />
				</td>
				<td style="text-align: center; width: 25">
					<span style="text-align: center"><img
							src="${ctx}/linepatrol/images/arrow_right.gif" />
					</span>
				</td>
				<td>
					<input type="button" id="btnClose" class="button_not_click"
						style="cursor: hand;" value="Òþ»¼¹Ø±Õ"
						onclick="toUrl(this,'close_task');" />
				</td>
				<td style="text-align: center; width: 25">
				</td>
				<td>
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td>
					<img src="${ctx}/linepatrol/images/arrow_down.gif" />
				</td>
				<td>
				</td>
				<td>
					<img src="${ctx}/linepatrol/images/arrow_up.gif" />
				</td>
				<td>
				</td>
				<td>
					<img src="${ctx}/linepatrol/images/arrow_down.gif" />
				</td>
				<td>
				</td>
				<td>
					<img src="${ctx}/linepatrol/images/arrow_up.gif" />
				</td>
				<td>
				</td>
				<td>
					&nbsp;
					<img src="${ctx}/linepatrol/images/arrow_down.gif" />
				</td>
				<td>
				</td>
				<td>
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td>
					<input type="button" id="btnConfirm" class="button_not_click"
						style="cursor: hand;" value="Òþ»¼ÆÀ¼¶"
						onclick="toUrl(this,'confirm_task');" />
				</td>
				<td>
					<span style="text-align: center"><img
							src="${ctx}/linepatrol/images/arrow_right.gif" /> </span>
				</td>
				<td>
					<input type="button" id="btnReport" class="button_not_click"
						style="cursor: hand;" value="Òþ»¼ÉÏ±¨"
						onclick="toUrl(this,'report_task');" />
				</td>
				<td>
				</td>
				<td>
					<span style="text-align: center; width: 25"> <input
							type="button" id="btnPlanApprove" class="button_not_click"
							style="cursor: hand;" value="´ýÉó¼Æ»®"
							onClick="toUrl(this,'plan_approve');" /> </span>

				</td>
				<td>
					<span style="text-align: center"><img
							src="${ctx}/linepatrol/images/arrow_right.gif" />
					</span>
				</td>
				<td>
					<input type="button" id="btnEndPlan" class="button_not_click"
						style="cursor: hand;" value="¶¢·ÀÖ´ÐÐÖÐ"
						onclick="toUrl(this,'end_plan');" />
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					<input type="button" id="btnCloseApprove"
						class="button_not_click" style="cursor: hand;" value="´ýÉó¹Ø±ÕÒþ»¼"
						onClick="toUrl(this,'close_approve_task');" />
				</td>
				<td>
				</td>
				<td>
					<span style="text-align: center"><img
							src="${ctx}/linepatrol/images/arrow_right.gif" />
					</span>
				</td>
				<td>
					<input type="button" id="btnEvaluate" class="button_not_click"
						style="cursor: hand;" value="¿¼ºËÆÀ·Ö"
						onclick="toUrl(this, 'evaluate_task');" />
				</td>
			</tr>
		</table>
		<logic:notEmpty name="task_name">
			<logic:equal value="confirm_task" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnConfirm').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="report_task" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnReport').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="add_approve_task" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnReportApprove').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="make_plan" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnMakePlan').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="plan_approve" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnPlanApprove').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="end_plan" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnEndPlan').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="end_approve" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnEndPlanApprove').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="close_task" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnClose').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="close_approve_task" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnCloseApprove').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="evaluate_task" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnEvaluate').className = "button_clicked";
				</script>
			</logic:equal>
		</logic:notEmpty>
		<logic:notEmpty name="task_names">
			<logic:iterate id="task_name" name="task_names" >
			<logic:equal value="confirm_task" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnConfirm').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="report_task" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnReport').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="add_approve_task" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnReportApprove').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="make_plan" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnMakePlan').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="plan_approve" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnPlanApprove').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="end_plan" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnEndPlan').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="end_approve" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnEndPlanApprove').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="close_task" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnClose').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="close_approve_task" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnCloseApprove').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="evaluate_task" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnEvaluate').className = "button_clicked";
				</script>
			</logic:equal>
			</logic:iterate>
		</logic:notEmpty>
	</body>
</html>
