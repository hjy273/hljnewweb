<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/linepatrol/css/button.css"
	type="text/css" media="screen, print" />
<html>
	<head>
		<title>sendtask</title>
		<script type="" language="javascript">
		toUrl=function(btn,taskName){
			var url="${ctx}/drillTaskAction.do?method=getAgentList&&task_name="+taskName;
			parent.location=url;
		}
		</script>
	</head>
	<body>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				
				<td style="text-align: center;">
					<input type="button" name="btnCreateDrillProj" id="btnCreateDrillProj"
						class="button_not_click" style="cursor: hand;"
						value="制定演练方案(${wait_create_drill_proj_num }条)"
						onclick="toUrl(this,'create_drill_proj_task');" />
				</td>
				<td style="text-align: center;">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td style="text-align: center;">
					<input type="button" name="btnApproveDrillProj" id="btnApproveDrillProj"
						class="button_not_click" style="cursor: hand;"
						value="审核演练方案(${wait_approve_drill_proj_num }条)"
						onclick="toUrl(this,'approve_drill_proj_task');" />
				</td>
				<td style="text-align: center;">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td style="text-align: center;">
					<input type="button" name="btnCreateDrillSummary" id="btnCreateDrillSummary"
						class="button_not_click" style="cursor: hand;"
						value="提交演练总结(${wait_create_drill_summary_num }条)"
						onclick="toUrl(this,'create_drill_summary_task');" />
				</td>
				<td style="text-align: center;" >
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td style="text-align: center;">
					<input type="button" name="btnApproveDrillSummary" id="btnApproveDrillSummary"
						class="button_not_click" style="cursor: hand;"
						value="审核演练总结(${wait_approve_drill_summary_num }条)"
						onclick="toUrl(this,'approve_drill_summary_task');" />
				</td>
				
				
			</tr>
			<tr>
				
				<td style="text-align: center;">
					<img src="${ctx}/linepatrol/images/arrow_up.gif" />
				</td>
				<td style="text-align: center;">&nbsp;
					
				</td>
				<td style="text-align: center;">
					<img src="${ctx}/linepatrol/images/arrow_down.gif" />
				</td>
				<td style="text-align: center;">&nbsp;
					
				</td>
				<td style="text-align: center;">
					<img src="${ctx}/linepatrol/images/arrow_up.gif" />
				</td>
				<td style="text-align: center;">&nbsp;
					
				</td>
				<td style="text-align: center;"><img src="${ctx}/linepatrol/images/arrow_down.gif" />
					
			  </td>
				
			</tr>
			<tr>
               
              <td style="text-align: center;"><input type="button" name="btnCreateDrillTask" id="btnCreateDrillTask"
						class="button_not_hits" disabled="disabled" value="制定演练任务" />
					
			  </td>
				<td style="text-align: center;">&nbsp;
					
		    </td>
				<td style="text-align: center;">
					<input type="button"
							name="btnChangeDrillProj" class="button_not_click" id="btnChangeDrillProj"
							style="cursor: hand;"
							value="变更演练方案(${wait_change_drill_proj_num }条)"
							onclick="toUrl(this,'change_drill_proj_task');" /> 
				</td>
				<td style="text-align: center;">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td style="text-align: center;">
					<input type="button" name="btnApproveChangeDrillProj" id="btnApproveChangeDrillProj"
						class="button_not_click" style="cursor: hand;"
						value="审核变更方案(${wait_approve_change_drill_proj_num }条)"
						onclick="toUrl(this, 'approve_change_drill_proj_task');" />
				</td>
				<td style="text-align: center;">&nbsp;
					
				</td>
				<td style="text-align: center;"><input type="button" name="btnEvaluate" class="button_not_click" id="btnEvaluate"
						style="cursor: hand;"
						value="考核评分(${wait_evaluate_num }条)"
						onClick="toUrl(this,'evaluate_task');" />
					
			  </td>
			</tr>
		</table>
		<logic:notEmpty name="task_name">
<logic:equal value="create_drill_proj_task" name="task_name">
				<script type="text/javascript">
				$('btnCreateDrillProj').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="approve_drill_proj_task" name="task_name">
				<script type="text/javascript">
				$('btnApproveDrillProj').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="change_drill_proj_task" name="task_name">
				<script type="text/javascript">
				$('btnChangeDrillProj').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="approve_change_drill_proj_task" name="task_name">
				<script type="text/javascript">
				$('btnApproveChangeDrillProj').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="create_drill_summary_task" name="task_name">
				<script type="text/javascript">
				$('btnCreateDrillSummary').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="approve_drill_summary_task" name="task_name">
				<script type="text/javascript">
				$('btnApproveDrillSummary').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="evaluate_task" name="task_name">
				<script type="text/javascript">
				$('btnEvaluate').className = "button_clicked";
				</script>
			</logic:equal>
		</logic:notEmpty>
	</body>
	<script type="text/javascript">
	<c:if test="${LOGIN_USER.deptype=='2'}">
	
	
	
	$('btnApproveDrillProj').className = "button_not_hits";
	$('btnApproveDrillProj').disabled = true;
	
	$('btnApproveDrillSummary').className = "button_not_hits";
	$('btnApproveDrillSummary').disabled = true;
	
	$('btnApproveChangeDrillProj').className = "button_not_hits";
	$('btnApproveChangeDrillProj').disabled = true;
	$('btnEvaluate').className = "button_not_hits";
	$('btnEvaluate').disabled = true;
	</c:if>
	<c:if test="${LOGIN_USER.deptype=='1'}">
	$('btnCreateDrillSummary').className = "button_not_hits";
	$('btnCreateDrillSummary').disabled = true;
	$('btnCreateDrillProj').className = "button_not_hits";
	$('btnCreateDrillProj').disabled = true;
	$('btnChangeDrillProj').className = "button_not_hits";
	$('btnChangeDrillProj').disabled = true;
	
	</c:if>
		</script>
</html>
