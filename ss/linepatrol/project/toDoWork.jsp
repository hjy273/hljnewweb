<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<html>
	<head>
		<title></title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
		<script type="text/javascript" language="javascript">
			function needCompleteApply(id){
				var url = "${ctx}/project/remedy_apply.do?method=updateApplyForm&apply_id="+id;
				window.location.href = url;
			}
			function needApprove(id, type){
				var url = "${ctx}/project/remedy_apply_approve.do?method=approveApplyForm&apply_id="+id+'&type='+type;
				window.location.href = url;
			}
		//取消割接流程
		toCancelForm=function(applyId){
			var url;
			if(confirm("确定要取消该工程申请流程吗？")){
				url="${ctx}/project/remedy_apply.do?method=cancelRemedyForm";
				var queryString="apply_id="+applyId;
				//location=url+"&"+queryString+"&rnd="+Math.random();
				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
			}
		}
		</script>
	</head>
	<body>
		<template:titile value="工程列表" />
		<div style="text-align:center;">
			<iframe
				src="${ctx}/project/remedy_apply.do?method=viewRemedyProcess&&task_name=${param.task_name }"
				frameborder="0" id="processGraphFrame" height="70" scrolling="no"
				width="95%"></iframe>
		</div>
		<%
			session.setAttribute("S_BACK_URL",request.getContextPath()+"/project/remedy_apply.do?method=toDoWork");
		%>
		<logic:notEmpty name="result">
		<display:table name="sessionScope.result" id="row" pagesize="18">
			<logic:notEmpty name="row">
			<bean:define id="sendUserId" name="row" property="creator" />
			<bean:define id="applyState" name="row" property="state" />
			</logic:notEmpty>
			<display:column property="remedyCode" sortable="true" title="编号" />
			<display:column property="projectName" sortable="true" title="项目名称"  />
			<display:column property="remedyDate" sortable="true" title="申请时间"  format="{0,date,yyyy-MM-dd}"/>
			<display:column property="totalFee" title="定额项合计金额" sortable="true"/>
			<display:column property="mtotalFee" title="使用材料合计金额" sortable="true" />
			<display:column media="html" title="状态" sortable="true">
				<c:if test="${row.state eq '10'}">
					需要填写申请
				</c:if>
				<c:if test="${row.state eq '20'}">
					需要审核
				</c:if>
			</display:column>
			<display:column media="html" title="操作">
				<c:if test="${row.state eq '10'}">
					<a href="javascript:needCompleteApply('${row.id}')">填写</a>
				</c:if>
				<c:if test="${row.state eq '20'}">
					<c:choose>
						<c:when test="${!row.flag}">
							<a href="javascript:needApprove('${row.id}', '')">审核</a> | 
							<a href="javascript:needApprove('${row.id}', '2')">转审</a>
						</c:when>
						<c:otherwise>
							<a href="javascript:needApprove('${row.id}', '3')">查看</a>
						</c:otherwise>
					</c:choose>
				</c:if>
			</display:column>
		</display:table>
		</logic:notEmpty>
		<table border="0" cellpadding="0" cellspacing="0" width="100%"
			style="border: 0px">
			<tr>
				<td>
					<a href="#"></a>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;">
				</td>
			</tr>
		</table>
	</body>
</html>