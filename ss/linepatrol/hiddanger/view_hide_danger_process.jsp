<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>sendtask</title>
		<link rel="stylesheet" href="${ctx}/linepatrol/css/button.css" type="text/css" media="screen, print" />
		<script type="" language="javascript">
		toUrl=function(btn,taskName){
			var url="${ctx}/hiddangerAction.do?method=findToDoWork&&task_name="+taskName;
			parent.location=url;
		};
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
						<input type="button" id="btnRegist" class="button_not_hits"
							value="隐患登记" disabled="disabled" />
				</td>
				<td style="text-align: center; width: 25">
				</td>
				<td>
						<input type="button" id="btnReportApprove"
							class="button_not_click" style="cursor: hand;"
							value="待审隐患(${wait_add_approve_num }条)"
							onclick="toUrl(this,'add_approve_task');" />
				</td>
				<td style="text-align: center; width: 25">
						<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>
						<input type="button" id="btnMakePlan" class="button_not_click"
							style="cursor: hand;"
							value="制定计划(${wait_make_plan_num }条)"
							onclick="toUrl(this,'make_plan');" />
				</td>
				<td style="text-align: center; width: 25">&nbsp;</td>
				<td style="text-align: center; width: 25"><input type="button" id="btnEndPlanApprove"
							class="button_not_click" style="cursor: hand;"
							value="待审终止申请(${wait_end_plan_approve_num }条)"
							onClick="toUrl(this, 'end_approve');" /></td>
				<td style="text-align: center; width: 25"><span style="text-align: center"><img
								src="${ctx}/linepatrol/images/arrow_right.gif" /></span></td>
				<td>
						<input type="button" id="btnClose" class="button_not_click"
							style="cursor: hand;" value="隐患关闭(${wait_close_num }条)"
							onclick="toUrl(this,'close_task');" />
				</td>
				<td style="text-align: center; width: 25">
				</td>
				<td>
					
				</td>
				<td>&nbsp;</td>
				
				
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
				<td><img src="${ctx}/linepatrol/images/arrow_down.gif" />
				</td>
				<td>
				</td>
				<td><img src="${ctx}/linepatrol/images/arrow_up.gif" /></td>
				<td>
				</td>
				<td>&nbsp;<img src="${ctx}/linepatrol/images/arrow_down.gif" /></td>
				<td>
				</td>
				<td>
				</td>
				<td>&nbsp;</td>
				
				
			</tr>
			<tr>
				<td>
						<input type="button" id="btnConfirm" class="button_not_click"
							style="cursor: hand;"
							value="隐患评级(${wait_confirm_num }条)"
							onclick="toUrl(this,'confirm_task');" />
				</td>
				<td>
						<span style="text-align: center"><img
								src="${ctx}/linepatrol/images/arrow_right.gif" /> </span>
				</td>
				<td>
						<input type="button" id="btnReport" class="button_not_click"
							style="cursor: hand;"
							value="隐患上报(${wait_report_num }条)"
							onclick="toUrl(this,'report_task');" />
				</td>
				<td>
				</td>
				<td><span style="text-align: center; width: 25">
				  <input type="button" id="btnPlanApprove"
							class="button_not_click" style="cursor: hand;"
							value="待审计划(${wait_make_plan_approve_num }条)"
							onClick="toUrl(this,'plan_approve');" />
				</span>
				
			  </td>
				<td><span style="text-align: center"><img
								src="${ctx}/linepatrol/images/arrow_right.gif" /></span>
				</td>
				<td>
						<input type="button" id="btnEndPlan" class="button_not_click"
							style="cursor: hand;"
							value="盯防执行中(${wait_end_plan_num }条)"
							onclick="toUrl(this,'end_plan');" />
				</td>
				<td>&nbsp;</td>
				<td><input type="button" id="btnCloseApprove"
							class="button_not_click" style="cursor: hand;"
							value="待审关闭隐患(${wait_close_approve_num }条)"
							onClick="toUrl(this,'close_approve_task');" /></td>
				<td>
				</td>
				<td><span style="text-align: center"><img
								src="${ctx}/linepatrol/images/arrow_right.gif" /></span>
				</td>
				<td>
						<input type="button" id="btnEvaluate" class="button_not_click"
							style="cursor: hand;"
							value="考核评分(${wait_evaluate_num }条)"
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
		
		<script type="text/javascript">
			<c:if test="${LOGIN_USER.deptype=='1'}">
				document.getElementById('btnConfirm').className = "button_not_hits";
				document.getElementById('btnConfirm').disabled = true;
				document.getElementById('btnReport').className = "button_not_hits";
				document.getElementById('btnReport').disabled = true;
				document.getElementById('btnMakePlan').className = "button_not_hits";
				document.getElementById('btnMakePlan').disabled = true;
				document.getElementById('btnEndPlan').className = "button_not_hits";
				document.getElementById('btnEndPlan').disabled = true;
				document.getElementById('btnClose').className = "button_not_hits";
				document.getElementById('btnClose').disabled = true;
			</c:if>
			<c:if test="${LOGIN_USER.deptype=='2'}">
				document.getElementById('btnReportApprove').className = "button_not_hits";
				document.getElementById('btnReportApprove').disabled = true;
				document.getElementById('btnPlanApprove').className = "button_not_hits";
				document.getElementById('btnPlanApprove').disabled = true;
				document.getElementById('btnEndPlanApprove').className = "button_not_hits";
				document.getElementById('btnEndPlanApprove').disabled = true;
				document.getElementById('btnCloseApprove').className = "button_not_hits";
				document.getElementById('btnCloseApprove').disabled = true;
				document.getElementById('btnEvaluate').className = "button_not_hits";
				document.getElementById('btnEvaluate').disabled = true;
			</c:if>
		</script>
	</body>
</html>
