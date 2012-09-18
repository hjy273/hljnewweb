<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>

<html>
	<head>
		<title>acceptance proces</title>
		<link rel="stylesheet" href="${ctx}/linepatrol/css/button.css" type="text/css" media="screen, print" />
		<script type="" language="javascript">
		toUrl=function(btn,taskName,processName){
			var url="${ctx}/acceptanceAction.do?method=findToDoWork&&task_name="+taskName+"&&process_name="+processName;
			parent.location=url;
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
						value="核准交维申请(${wait_allot_task_num }条)"
						class="button_not_click" style="cursor: hand;"
						onclick="toUrl(this,'allotTasks','acceptance');" />
				</td>
				<td style="text-align: center;">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td style="text-align: center;">
					<input type="button" id="btnRecordData"
						value="录入交维数据(${wait_record_data_num }条)"
						class="button_not_click" style="cursor: hand;"
						onclick="toUrl(this,'record','acceptancesubflow');" />
				</td>
				<td style="text-align: center;" >
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td style="text-align: center;">
					<input type="button" id="btnApproveRecord"
						value="审核交维数据(${wait_record_approve_num }条)"
						class="button_not_click" style="cursor: hand;"
						onclick="toUrl(this,'approve','acceptancesubflow');" />
				</td>
				<td style="text-align: center;" >
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td style="text-align: center;">
					<input type="button" id="btnEvaluate"
						value="考核信息(${wait_evaluate_num }条)"
						class="button_not_click" style="cursor: hand;"
						onclick="toUrl(this,'exam','acceptancesubflow');" />
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
						value="认领交维任务(${wait_claim_task_num }条)"
						class="button_not_click" style="cursor: hand;"
						onclick="toUrl(this,'claimTask','acceptance');" />
				</td>
				<td style="text-align: center;" >
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td style="text-align: center;">
					<input type="button" id="btnApproveClaimTask"
						value="核准交维任务(${wait_task_approve_num }条)"
						class="button_not_click" style="cursor: hand;"
						onclick="toUrl(this, 'approve', 'acceptance');" />
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
			<logic:equal value="allotTasks" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnAllotTask').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="claimTask" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnClaimTask').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="acceptance" name="process_name">
				<logic:equal value="approve" name="task_name">
					<script type="text/javascript">
					document.getElementById('btnApproveClaimTask').className = "button_clicked";
					</script>
				</logic:equal>
			</logic:equal>
			<logic:equal value="record" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnRecordData').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="choose" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnRecordDataSubmit').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="acceptancesubflow" name="process_name">
				<logic:equal value="approve" name="task_name">
					<script type="text/javascript">
					document.getElementById('btnApproveRecord').className = "button_clicked";
					</script>
				</logic:equal>
			</logic:equal>
			<logic:equal value="exam" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnEvaluate').className = "button_clicked";
				</script>
			</logic:equal>
		</logic:notEmpty>
		
		<script type="text/javascript">
			<c:if test="${LOGIN_USER.deptype=='1'}">
				document.getElementById('btnRecordData').className = "button_not_hits";
				document.getElementById('btnRecordData').disabled = true;
				document.getElementById('btnClaimTask').className = "button_not_hits";
				document.getElementById('btnClaimTask').disabled = true;
			</c:if>
			<c:if test="${LOGIN_USER.deptype=='2'}">
				document.getElementById('btnAllotTask').className = "button_not_hits";
				document.getElementById('btnAllotTask').disabled = true;
				document.getElementById('btnApproveClaimTask').className = "button_not_hits";
				document.getElementById('btnApproveClaimTask').disabled = true;
				document.getElementById('btnApproveRecord').className = "button_not_hits";
				document.getElementById('btnApproveRecord').disabled = true;
				document.getElementById('btnEvaluate').className = "button_not_hits";
				document.getElementById('btnEvaluate').disabled = true;
			</c:if>
		</script>
	</body>
</html>
