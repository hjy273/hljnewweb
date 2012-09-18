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
					
				</td>
				<td style="text-align: center; width: 25;">
				</td>
				<td>
					<input type="button" id="btnWork" class="button_not_click"
						style="cursor: hand;" value="´ý·´À¡¸î½Ó"
						onclick="toUrl(this,'work_task');" />
				</td>
				<td style="text-align: center; width: 25;">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>
					<input type="button" id="btnWorkApprove"
						class="button_not_click" style="cursor: hand;"
						value="´ýÉó·´À¡"
						onclick="toUrl(this,'work_approve_task');" />
				</td>
				<td style="text-align: center; width: 25;">
				</td>
				<td>
					<input type="button" id="btnEvaluate" class="button_not_click"
						style="cursor: hand;" value="¿¼ºËÆÀ·Ö"
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
						style="cursor: hand;" value="¸î½ÓÉêÇë"
						onclick="toUrl(this,'apply_task');" />
				</td>
				<td style="text-align: center;">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>
					<input type="button" id="btnApplyApprove"
						class="button_not_click" style="cursor: hand;"
						value="´ýÉóÉêÇë"
						onclick="toUrl(this,'apply_approve_task');" />
				</td>
				<td style="text-align: center;">
				</td>
				<td>
					<input type="button" id="btnCheck" class="button_not_click"
						style="cursor: hand;" value="´ýÑéÊÕ"
						onclick="toUrl(this,'check_task');" />
				</td>
				<td style="text-align: center;">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>
					<input type="button" id="btnCheckApprove"
						class="button_not_click" style="cursor: hand;"
						value="´ýÉóÑéÊÕ"
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
		<logic:notEmpty name="task_names">
			<logic:iterate id="task_name" name="task_names" >
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
			</logic:iterate>
		</logic:notEmpty>
	</body>
</html>
