<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/linepatrol/css/button.css"
	type="text/css" media="screen, print" />
<html>
	<head>
		<title>sendtask</title>
		<script type="" language="javascript">
		toUrl=function(btn,taskName){
			var url="${ctx}/project/remedy_apply.do?method=toDoWork&&task_name="+taskName;
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
						<input type="button" class="button_not_hits"
							name="btnStart" value="开 始" disabled="disabled" />
					</div>
				</td>
				<td style="text-align: center; width: 25;">
				</td>
				<td>
					<input type="button" name="btnApply" class="button_not_click" id="btnApply"
						style="cursor: hand;" value="提交工程申请(${wait_apply_task_num }条)"
						onclick="toUrl(this,'remedy_apply_task');" />
				</td>
				<td style="text-align: center; width: 25;">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>
					<input type="button" name="btnApplyApprove" id="btnApplyApprove"
						class="button_not_click" style="cursor: hand;"
						value="待审核工程申请(${wait_approve_task_num }条)"
						onclick="toUrl(this,'remedy_approve_task');" />
				</td>
				<td style="text-align: center; width: 25;">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>
					<div align="center">
						<input type="button" class="button_not_hits"
							name="btnEnd" value="结 束" disabled="disabled" />
					</div>
				</td>
			</tr>
		</table>
		<logic:notEmpty name="task_name">
			<logic:equal value="remedy_apply_task" name="task_name">
				<script type="text/javascript">
				$('btnApply').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="remedy_approve_task" name="task_name">
				<script type="text/javascript">
				$('btnApplyApprove').className = "button_clicked";
				</script>
			</logic:equal>
		</logic:notEmpty>
	</body>
	<script type="text/javascript">
		<c:if test="${LOGIN_USER.deptype=='2'}">
			$('btnApplyApprove').className = "button_not_hits";
			$('btnApplyApprove').disabled = true;
		</c:if>
		<c:if test="${LOGIN_USER.deptype=='1'}">
			$('btnApply').className = "button_not_hits";
			$('btnApply').disabled = true;
		</c:if>
	</script>
</html>
