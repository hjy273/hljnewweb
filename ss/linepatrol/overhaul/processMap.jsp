<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/linepatrol/css/button.css"
	type="text/css" media="screen, print" />
<html>
	<head>
		<title>sendtask</title>
		<script type="" language="javascript">
		toUrl=function(btn,taskName){
			var url="${ctx}/overHaulAction.do?method=toDoWork&task_name="+taskName;
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
					<input type="button" class="button_not_hits"
						value="制定任务" disabled="disabled" />
				</td>
				<td style="text-align: center; width: 25">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>
					<input type="button" name="apply" id="apply"
						class="button_not_click" style="cursor: hand;"
						value="大修申请(${process.applyNo}条)"
						onclick="toUrl(this,'treat,apply');" />
				</td>
				<td style="text-align: center; width: 25">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>
					<input type="button" name="approve"
						class="button_not_click" style="cursor: hand;" id="approve"
						value="大修审核(${process.approveNo}条)"
						onclick="toUrl(this,'approve');" />
				</td>
				<td style="text-align: center; width: 25">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>
					<input type="button" name="treat" class="button_not_click"
						style="cursor: hand;" id="treat"
						value="大修处理(${process.treatNo}条)"
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
		</script>
		<script type="text/javascript">
			<c:if test="${LOGIN_USER.deptype=='1'}">
				$('apply').className = "button_not_hits";
				$('apply').disabled = true; 
			</c:if>
			<c:if test="${LOGIN_USER.deptype=='2'}">
				$('approve').className = "button_not_hits";
				$('approve').disabled = true;
				$('treat').className = "button_not_hits";
				$('treat').disabled = true;
			</c:if>
		</script>
	</body>
</html>
