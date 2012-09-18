<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>

<html>
	<head>
		<title>query for task state</title>
		<link rel="stylesheet" href="${ctx}/linepatrol/css/button.css" 	type="text/css" media="screen, print" />
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
							disabled="disabled" value="¹ÊÕÏÅÉµ¥" />
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
							value="¹ÊÕÏ·´À¡"
							onclick="toUrl(this,'reply_task');" />
					</div>
				</td>
				<td style="text-align: center">
					<div align="center"></div>
				</td>
				<td>
					<div align="center">
						<input type="button" id="btnEvaluate" class="button_not_click"
							style="cursor: hand;" value="¿¼ºËÆÀ·Ö"
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
							disabled="disabled" value="½á Êø" />
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
							style="cursor: hand;" value="Æ½Ì¨ºË×¼"
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
							value="ÉóºË·´À¡ "
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
		<logic:notEmpty name="task_names">
			<logic:iterate id="task_name" name="task_names" >
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
			</logic:iterate>
		</logic:notEmpty>
	</body>
</html>
