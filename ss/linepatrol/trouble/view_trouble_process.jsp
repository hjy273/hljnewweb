<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>

<html>
	<head>
		<title>sendtask</title>
		<link rel="stylesheet" href="${ctx}/linepatrol/css/button.css" 	type="text/css" media="screen, print" />
		<script type="" language="javascript">
		toUrl=function(btn,taskName){
			var url="${ctx}/troubleReplyAction.do?method=getActWorkForm&&task_name="+taskName;
			parent.location=url;
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
						<input id="btnTroubleAdd" type="button" class="button_not_hits"
							disabled="disabled" value="故障派单" />
					</div>
				</td>
				<td style="text-align: center">
					<div align="center">
						<img src="${ctx}/linepatrol/images/arrow_right.gif" />
					</div>
				</td>
				<td>
					<div align="center">
						<input type="button" id="btnTroubleReply"
							class="button_not_click" style="cursor: hand;"
							value="故障反馈(${wait_reply_num }条)"
							onclick="toUrl(this,'reply_task');" />
					</div>
				</td>
				<td style="text-align: center">
					<div align="center"></div>
				</td>
				<td>
					<div align="center">
					<input type="button" id="btnEvaluate" class="button_not_click"
							style="cursor: hand;" value="考核评分(${wait_evaluate_num }条)"
							onclick="toUrl(this,'evaluate_task');" />
					</div>
				</td>
				<td style="text-align: center">
					<div align="center">
						<img src="${ctx}/linepatrol/images/arrow_right.gif" />
					</div>
				</td>
				<td>
					<div align="center">
						<input id="btnEnd2" type="button" class="button_not_hits"
							disabled="disabled" value="结 束" />
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div align="center"></div>
				</td>
				<td style="text-align: center">
					<div align="center"></div>
				</td>
				<td>
					<div align="center">
						<span style="text-align: center"><img
								src="${ctx}/linepatrol/images/arrow_down.gif" />
						</span>
					</div>
				</td>
				<td style="text-align: center">
					<div align="center"></div>
				</td>
				<td>
					<div align="center">
						<span style="text-align: center"><img
								src="${ctx}/linepatrol/images/arrow_up.gif" />
						</span>
					</div>
				</td>
				<td style="text-align: center">
					<div align="center"></div>
				</td>
				<td>
					<div align="center"></div>
				</td>
			</tr>
			<tr>
				<td>
					<div align="center"></div>
				</td>
				<td style="text-align: center">
					<div align="center"></div>
				</td>
				<td>
					<div align="center">
						<input type="button" id="btnConfirm" class="button_not_click"
							style="cursor: hand;" value="平台核准(${wait_confirm_num }条)"
							onclick="toUrl(this,'edit_dispatch_task');" />
					</div>
				</td>
				<td style="text-align: center">
					<div align="center">
						<img src="${ctx}/linepatrol/images/arrow_right.gif" />
					</div>
				</td>
				<td>
					<div align="center">
						<input type="button" id="btnApproveTroubleReply"
							class="button_not_click" style="cursor: hand;"
							value="审核反馈 (${wait_approve_num }条)"
							onclick="toUrl(this, 'approve_task');" />
						
					</div>
				</td>
				<td style="text-align: center">
					<div align="center"></div>
				</td>
				<td>
					<div align="center"></div>
				</td>
			</tr>
		</table>
		<logic:notEmpty name="task_name">
			<logic:equal value="reply_task" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnTroubleReply').className="button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="approve_task" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnApproveTroubleReply').className="button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="edit_dispatch_task" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnConfirm').className="button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="evaluate_task" name="task_name">
				<script type="text/javascript">
				document.getElementById('btnEvaluate').className="button_clicked";
				</script>
			</logic:equal>
		</logic:notEmpty>
		<script type="text/javascript">
			<c:if test="${LOGIN_USER.deptype=='1'}">
				document.getElementById('btnTroubleReply').className = "button_not_hits";
				document.getElementById('btnTroubleReply').disabled = true;
				
			</c:if>
			<c:if test="${LOGIN_USER.deptype=='2'}">
				
				document.getElementById('btnConfirm').className = "button_not_hits";
				document.getElementById('btnConfirm').disabled = true;
				document.getElementById('btnApproveTroubleReply').className = "button_not_hits";
				document.getElementById('btnApproveTroubleReply').disabled = true;
				document.getElementById('btnEvaluate').className = "button_not_hits";
				document.getElementById('btnEvaluate').disabled = true;
			</c:if>
		</script>
	</body>
</html>
