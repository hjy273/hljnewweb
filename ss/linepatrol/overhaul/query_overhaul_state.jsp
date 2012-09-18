<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/linepatrol/css/button.css"
	type="text/css" media="screen, print" />
<html>
	<head>
		<title>query for task state</title>
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
					<input type="button" class="button_not_hits"
						value="制定任务" disabled="disabled" />
				</td>
				<td style="text-align: center; width: 25">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>
					<input type="button" name="apply"
						class="button_not_click" style="cursor: hand;"
						value="大修申请"
						onclick="toUrl(this,'apply');" />
				</td>
				<td style="text-align: center; width: 25">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>
					<input type="button" name="approve"
						class="button_not_click" style="cursor: hand;" id="treat"
						value="大修审核"
						onclick="toUrl(this,'approve');" />
				</td>
				<td style="text-align: center; width: 25">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>
					<input type="button" name="treat" class="button_not_click"
						style="cursor: hand;" id="treat"
						value="大修处理"
						onclick="toUrl(this,'treat');" />
				</td>
			</tr>
		</table>
		<script type="text/javascript">
			<c:if test="${task_name eq 'treat'}">
				<c:choose>
					<c:when test="${LOGIN_USER.deptype=='1'}">
						$('treat').className = "button_clicked";
					</c:when>
					<c:otherwise>
						$('apply').className = "button_clicked";
					</c:otherwise>
				</c:choose>
			</c:if>
			<c:if test="${task_name eq 'approve'}">
				$('approve').className = "button_clicked";
			</c:if>
			<c:if test="${task_name eq 'apply'}">
				$('apply').className = "button_clicked";
			</c:if>
		</script>
		<logic:notEmpty name="task_names">
			<logic:iterate id="task_name" name="task_names" >
			<script type="text/javascript">
				<c:if test="${task_name eq 'treat'}">
					<c:choose>
						<c:when test="${LOGIN_USER.deptype=='1'}">
							$('treat').className = "button_clicked";
						</c:when>
						<c:otherwise>
							$('apply').className = "button_clicked";
						</c:otherwise>
					</c:choose>
				</c:if>
				<c:if test="${task_name eq 'approve'}">
					$('approve').className = "button_clicked";
				</c:if>
			</script>
			</logic:iterate>
		</logic:notEmpty>
	</body>
</html>
