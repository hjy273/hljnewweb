<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>

<html>
	<head>
		<title>sendtask</title>
		<link rel="stylesheet" href="${ctx}/linepatrol/css/button.css" type="text/css" media="screen, print" />
		<script type="" language="javascript">
		toUrl=function(btn,taskName){
			var url="${ctx}/cutAction.do?method=queryFinishHandledCutApplyList&&task_name="+taskName;
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
					
				</td>
				<td style="text-align: center; width: 25;">
				</td>
				<td>
					<input type="button" id="btnWork" class="button_not_click"
						style="cursor: hand;" value="待反馈割接(${work_task_num }条)"
						onclick="toUrl(this,'work_task');" />
				</td>
				<td style="text-align: center; width: 25;">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>
					<input type="button" id="btnWorkApprove"
						class="button_not_click" style="cursor: hand;"
						value="待审反馈(${work_approve_task_num }条)"
						onclick="toUrl(this,'work_approve_task');" />
				</td>
				<td style="text-align: center; width: 25;">
				</td>
				<td>
					<input type="button" id="btnEvaluate" class="button_not_click"
						style="cursor: hand;" value="考核评分(${evaluate_task_num }条)"
						onclick="toUrl(this,'evaluate_task');" />
				</td>
				
			</tr>
			<tr>
				<td>
					
				</td>
				<td style="text-align: center;">
				</td>
				<td>
					<img src="${ctx}/linepatrol/images/arrow_up.gif" />
				</td>
				<td style="text-align: center;">
				</td>
				<td>
					<img src="${ctx}/linepatrol/images/arrow_down.gif" />
				</td>
				<td style="text-align: center;">
				</td>
				<td>
					<img src="${ctx}/linepatrol/images/arrow_up.gif" />
				</td>
				
			</tr>
			<tr>
				<td>
					<input type="button" id="btnApply" class="button_not_click"
						style="cursor: hand;" value="割接申请(${apply_task_num }条)"
						onclick="toUrl(this,'apply_task');" />
				</td>
				<td style="text-align: center;">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>
					<input type="button" id="btnApplyApprove"
						class="button_not_click" style="cursor: hand;"
						value="待审申请(${apply_approve_task_num }条)"
						onclick="toUrl(this,'apply_approve_task');" />
				</td>
				<td style="text-align: center;">
				</td>
				<td>
					<input type="button" id="btnCheck" class="button_not_click"
						style="cursor: hand;" value="待验收(${check_task_num }条)"
						onclick="toUrl(this,'check_task');" />
				</td>
				<td style="text-align: center;">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>
					<input type="button" id="btnCheckApprove"
						class="button_not_click" style="cursor: hand;"
						value="待审验收(${check_approve_task_num }条)"
						onclick="toUrl(this, 'check_approve_task');" />
				</td>
				
			</tr>
		</table>
		<logic:notEmpty name="task_name">
			<logic:equal value="apply_task" name="task_name">
				<script type="text/javascript">
	document.getElementById('btnApply').className = "button_clicked";
</script>
			</logic:equal>
			<logic:equal value="apply_approve_task" name="task_name">
				<script type="text/javascript">
	document.getElementById('btnApplyApprove').className = "button_clicked";
</script>
			</logic:equal>
			<logic:equal value="work_task" name="task_name">
				<script type="text/javascript">
	document.getElementById('btnWork').className = "button_clicked";
</script>
			</logic:equal>
			<logic:equal value="work_approve_task" name="task_name">
				<script type="text/javascript">
	document.getElementById('btnWorkApprove').className = "button_clicked";
</script>
			</logic:equal>
			<logic:equal value="check_task" name="task_name">
				<script type="text/javascript">
	document.getElementById('btnCheck').className = "button_clicked";
</script>
			</logic:equal>
			<logic:equal value="check_approve_task" name="task_name">
				<script type="text/javascript">
	document.getElementById('btnCheckApprove').className = "button_clicked";
</script>
			</logic:equal>
			<logic:equal value="evaluate_task" name="task_name">
				<script type="text/javascript">
	document.getElementById('btnEvaluate').className = "button_clicked";
</script>
			</logic:equal>
		</logic:notEmpty>
	</body>
	<script type="text/javascript">
			<c:if test="${LOGIN_USER.deptype=='2'}">
			
			document.getElementById('btnApplyApprove').className = "button_not_hits";
			document.getElementById('btnApplyApprove').disabled = true;
			document.getElementById('btnWorkApprove').className = "button_not_hits";
			document.getElementById('btnWorkApprove').disabled = true;
			document.getElementById('btnCheckApprove').className = "button_not_hits";
			document.getElementById('btnCheckApprove').disabled = true;
			document.getElementById('btnEvaluate').className = "button_not_hits";
			document.getElementById('btnEvaluate').disabled = true;
			</c:if>
			<c:if test="${LOGIN_USER.deptype=='1'}">
			document.getElementById('btnApply').className = "button_not_hits";
			document.getElementById('btnApply').disabled = true;
			document.getElementById('btnWork').className = "button_not_hits";
			document.getElementById('btnWork').disabled = true;
			document.getElementById('btnCheck').className = "button_not_hits";
			document.getElementById('btnCheck').disabled = true;
			</c:if>
		</script>
</html>
