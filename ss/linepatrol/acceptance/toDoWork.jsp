<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
	<script type="text/javascript">
		function allotTasks(id){
			window.location.href = '${ctx}/acceptanceAction.do?method=allotTasks&id='+id;
		}
		function claimTasks(id){
			window.location.href = '${ctx}/acceptanceAction.do?method=claimTask&id='+id;
		}
		function approveTasks(id){
			window.location.href = '${ctx}/acceptanceAction.do?method=approveTasks&id='+id;
		}
		function chooseTask(id, type){
				window.location.href = '${ctx}/acceptanceAction.do?method=chooseTask&id='+id+'&type='+type;
		}
		function push(id){
			window.location.href = '${ctx}/acceptanceAction.do?method=pushLink&id='+id;
		}
		function approve(id){
			window.location.href = '${ctx}/acceptanceAction.do?method=apporveLink&id='+id;
		}
		function exam(id){
			window.location.href = '${ctx}/acceptanceExamAction.do?method=examLink&id='+id;
		}
		function recheck(id){
			window.location.href = '${ctx}/acceptanceAction.do?method=recheckLink&id='+id;
		}
		//取消流程
		toCancelForm=function(applyId){
			var url;
			if(confirm("确定要取消该验收交维流程吗？")){
				url="${ctx}/acceptanceAction.do?method=cancelAcceptanceForm";
				var queryString="apply_id="+applyId;
				//location=url+"&"+queryString+"&rnd="+Math.random();
				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
			};
		};
	</script>
</head>
<body>
	<template:titile value="验收交维列表(<font color='red'>共${number}条待办</font>)" />
	<div style="text-align:center;">
		<iframe
			src="${ctx}/acceptanceAction.do?method=viewAcceptanceProcess&&task_name=${param.task_name }&&process_name=${param.process_name }"
			frameborder="0" id="processGraphFrame" height="100" scrolling="no"
			width="95%"></iframe>
	</div>
	<display:table name="sessionScope.result" id="row" pagesize="18" export="false" defaultsort="4" defaultorder="descending" sort="list">
		<logic:notEmpty name="row">
				<bean:define id="sendUserId" name="row" property="applicant" />
				<bean:define id="id" name="row" property="id"></bean:define>
				<bean:define id="applyState" name="row" property="processState"></bean:define>
		</logic:notEmpty>
		<display:setProperty name="sort.amount" value="list"/>
		<display:column property="name" title="工程名称"/>
		<display:column property="code" title="申请编号"/>
		<display:column media="html" title="项目经理">
			<c:out value="${users[row.applicant]}" />
		</display:column>
		<display:column media="html" title="申请时间">
			<bean:write name="row" property="applyDate" format="yy/MM/dd"/>
		</display:column>
		<display:column media="html" title="资源类型">
			<c:choose>
				<c:when test="${row.resourceType eq '1'}">光缆</c:when>
				<c:otherwise>管道</c:otherwise>
			</c:choose>
		</display:column>
		<display:column media="html" title="状态">
			<c:if test="${row.processState eq '10'}">需要核准申请</c:if>
			<c:if test="${row.processState eq '20'}">需要认领任务</c:if>
			<c:if test="${row.processState eq '30'}">需要核准任务</c:if>
			<c:if test="${row.processState eq '40'}">
				<c:if test="${row.subProcessState eq '40'}">需要录入数据</c:if>
				<c:if test="${row.subProcessState eq '42'}">需要审核录入</c:if>
				<c:if test="${row.subProcessState eq '43'}">需要考核评估</c:if>
				<c:if test="${row.subProcessState eq '44'}">需要录入数据</c:if>
			</c:if>
			<c:if test="${row.processState eq '46'}">需要复验核准</c:if>
		</display:column>
		<display:column media="html" title="操作">
			<c:if test="${row.processState eq '10'}">
				<a href="javascript:allotTasks('${row.id}')">核准申请</a>
			</c:if>
			<c:if test="${row.processState eq '20'}">
				<a href="javascript:claimTasks('${row.id}')">认领任务</a>
			</c:if>
			<c:if test="${row.processState eq '30'}">
				<a href="javascript:approveTasks('${row.id}')">核准任务</a>
			</c:if>
			<c:if test="${row.processState eq '40'}">
				<c:if test="${row.subProcessState eq '40'}">
					<a href="javascript:chooseTask('${row.subflowId}','true')">录入数据</a>
				</c:if>
				<c:if test="${row.subProcessState eq '42'}">
					<a href="javascript:approve('${row.subflowId}')">审核录入</a>
				</c:if>
				<c:if test="${row.subProcessState eq '43'}">
					<a href="javascript:exam('${row.subflowId}')">考核评估</a>
				</c:if>
				<c:if test="${row.subProcessState eq '44'}">
					<a href="javascript:chooseTask('${row.subflowId}','false')">录入数据</a>
				</c:if>
			</c:if>
			<c:if test="${row.processState eq '46'}">
				<a href="javascript:recheck('${row.id}')">复验核准</a>
			</c:if>
	        <c:if test="${sessionScope.LOGIN_USER.deptype=='1'&&applyState!='00'&&applyState!='42'}">
				<a href="javascript:toCancelForm('${row.id}')">取消</a>
			</c:if>
		</display:column>
	</display:table>
</body>
</html>