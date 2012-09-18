<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>

<html>
	<head>
		<title>trouble</title>
		<link rel="stylesheet" href="${ctx}/linepatrol/css/button.css"	type="text/css" media="screen, print" />
		<script type="" language="javascript">
		toUrl=function(btn,taskName){
			var url="${ctx}/troubleReplyAction.do?method=getFinishHandledWork&&task_name="+taskName;
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
						<input id="btnTroubleAdd" type="button" class="button_not_click" style="cursor: hand;" 
						    value="已派单(${dispatch_num }条)"
							onclick="toUrl(this,'start');" />
					</div>
				</td>
				<td style="text-align: center">
					<div align="center">
						<img src="${ctx}/linepatrol/images/arrow_right.gif" />
					</div>
				</td>
				<td>
					<div align="center">
					<c:if test="${LOGIN_USER.deptype=='1'}">
						<input type="button" id="btnTroubleReply"
							class="button_not_click" style="cursor: hand;" disabled="disabled" 
							value="已反馈(${reply_num }条)"
							onclick="toUrl(this,'reply_task');" />
						</c:if>
						<c:if test="${LOGIN_USER.deptype=='2'}">
						<input type="button" id="btnTroubleReply"
							class="button_not_click" style="cursor: hand;"
							value="已反馈(${reply_num }条)"
							onclick="toUrl(this,'reply_task');" />
						</c:if>
					</div>
				</td>
				<td style="text-align: center">
					<div align="center"></div>
				</td>
				<td>
					<div align="center">
					<c:if test="${LOGIN_USER.deptype=='1'}">
					<input type="button" id="btnEvaluate" class="button_not_click"
							style="cursor: hand;" value="已考核(${evaluate_num }条)"
							onclick="toUrl(this,'evaluate_task');" />
					</c:if>
					<c:if test="${LOGIN_USER.deptype=='2'}">
					<input type="button" id="btnEvaluate" class="button_not_click" disabled="disabled"
							style="cursor: hand;" value="已考核(${evaluate_num }条)"
							onclick="toUrl(this,'evaluate_task');" />
					</c:if>
					</div>
				</td>
				<td style="text-align: center">
					&nbsp;
				</td>
				<td>
					&nbsp;
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
							style="cursor: hand;" value="已核准(${confirm_num }条)"
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
						<c:if test="${LOGIN_USER.deptype=='1'}">
						<input type="button" id="btnApproveTroubleReply"
							class="button_not_click" style="cursor: hand;"
							value="已审核 (${approve_num }条)"
							onclick="toUrl(this, 'approve_task');" />
						</c:if>
						<c:if test="${LOGIN_USER.deptype=='2'}">
						<input type="button" id="btnApproveTroubleReply"
							class="button_not_click" style="cursor: hand;" disabled="disabled"
							value="已审核 (${approve_num }条)"
							onclick="toUrl(this, 'approve_task');" />
						</c:if>
						
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
	</body>
</html>
