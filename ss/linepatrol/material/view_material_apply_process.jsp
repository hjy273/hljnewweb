<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/linepatrol/css/button.css"
	type="text/css" media="screen, print" />
<html>
	<head>
		<title>sendtask</title>
		<script type="" language="javascript">
		toUrl=function(btn,taskName){
			var url="${ctx}/material_apply.do?method=queryWaitHandleMaterialApplyList&&task_name="+taskName;
			parent.location=url;
		}
		</script>
	</head>
	<body>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<div align="center">
						<input name="btnStart" type="button" disabled="disabled"
							class="button_not_hits" id="btnStart" value="开 始" />
					</div>
				</td>
				<td>
					<div align="center">
						<img src="${ctx}/linepatrol/images/arrow_right.gif" />
					</div>
				</td>
				<td>

					<div align="center">
						<input name="btnApplyTask" type="button" class="button_not_click"
							id="btnApplyTask" style="cursor: hand;"
							onclick="toUrl(this,'apply_task');"
							value="提交申请(${wait_apply_task_num }条)" />
					</div>
				</td>
				<td>
					<div align="center"></div>
				</td>
				<td>
					<div align="center"></div>
				</td>
				<td>

					<div align="center">
						<input name="btnPutStorageApproveTask" type="button"
							class="button_not_click" id="btnPutStorageApproveTask"
							style="cursor: hand;" onclick="toUrl(this, 'put_storage_confirm_task');"
							value="确认入库(${wait_put_storage_confirm_task_num }条)" />
					</div>
				</td>
				<td>
					<div align="center">
						<img src="${ctx}/linepatrol/images/arrow_right.gif" />
					</div>
				</td>
				<td>
					<div align="center">
						<input type="button" class="button_not_hits" name="btnEndTask"
							value="结 束" disabled="disabled" />
					</div>
				</td>
			</tr>
			<tr>
				<td height="10">
					<div align="center"></div>
				</td>
				<td height="10">
					<div align="center"></div>
				</td>
				<td height="10">
					<div align="center">
						<img src="${ctx}/linepatrol/images/arrow_down.gif" />
					</div>
				</td>
				<td height="10">
					<div align="center"></div>
				</td>
				<td height="10">
					<div align="center"></div>
				</td>
				<td height="10">
					<div align="center">
						<img src="${ctx}/linepatrol/images/arrow_up.gif" />
					</div>
				</td>
				<td height="10">
					<div align="center"></div>
				</td>
				<td height="10">
					<div align="center"></div>
				</td>
			</tr>
			<tr>
				<td>
					<div align="center"></div>
				</td>
				<td>
					<div align="center"></div>
				</td>
				<td colspan="2" style="text-align: center;">
					<div align="center">
						<input name="btnApplyApproveTask" type="button"
							class="button_not_click" id="btnApplyApproveTask"
							style="cursor: hand;" onclick="toUrl(this,'apply_approve_task');"
							value="审核申请(${wait_apply_approve_task_num }条)" />
					</div>
				</td>
				<td style="text-align: center;">
					<div align="center">
						<img src="${ctx}/linepatrol/images/arrow_right.gif" />
					</div>
				</td>
				<td style="text-align: center;">
					<div align="center">
						<input name="btnPutStorageTask" type="button"
							class="button_not_click" id="btnPutStorageTask"
							value="提交入库(${wait_put_storage_task_num }条)" style="cursor: hand;"
							onclick="toUrl(this, 'put_storage_task');" />
					</div>
				</td>
				<td style="text-align: center;">
					<div align="center"></div>
				</td>
				<td>
					<div align="center"></div>
				</td>
			</tr>
		</table>
		<logic:notEmpty name="task_name">
			<logic:equal value="apply_task" name="task_name">
				<script type="text/javascript">
	$('btnApplyTask').className = "button_clicked";
</script>
			</logic:equal>
			<logic:equal value="apply_approve_task" name="task_name">
				<script type="text/javascript">
	$('btnApplyApproveTask').className = "button_clicked";
</script>
			</logic:equal>
			<logic:equal value="put_storage_task" name="task_name">
				<script type="text/javascript">
	$('btnPutStorageTask').className = "button_clicked";
</script>
			</logic:equal>
			<logic:equal value="put_storage_confirm_task" name="task_name">
				<script type="text/javascript">
	$('btnPutStorageApproveTask').className = "button_clicked";
</script>
			</logic:equal>
		</logic:notEmpty>
		<c:if test="${LOGIN_USER.deptype=='1'}">
			<script type="text/javascript">
	$('btnApplyTask').className = "button_not_hits";
	$('btnPutStorageTask').className = "button_not_hits";
	$('btnApplyTask').disabled = true;
	$('btnPutStorageTask').disabled = true;
</script>
		</c:if>
		<c:if test="${LOGIN_USER.deptype=='2'}">
			<script type="text/javascript">
	$('btnApplyApproveTask').className = "button_not_hits";
	$('btnPutStorageApproveTask').className = "button_not_hits";
	$('btnApplyApproveTask').disabled = true;
	$('btnPutStorageApproveTask').disabled = true;
</script>
		</c:if>
	</body>
</html>
