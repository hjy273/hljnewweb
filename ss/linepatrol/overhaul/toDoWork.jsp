<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
		
	<script type="text/javascript">
		function apply(id){
			var url = '${ctx}/overHaulAction.do?method=applyForm&id='+id;
			window.location.href = url;
		}
		function approve(id, type){
			var url = '${ctx}/overHaulApplyAction.do?method=approveForm&id='+id+'&type='+type;
			window.location.href = url;
		}
		function editApply(id){
			var url = '${ctx}/overHaulAction.do?method=editApplyForm&id='+id;
			window.location.href = url;
		}
		function treat(id){
			var url = '${ctx}/overHaulAction.do?method=treatForm&id='+id;
			window.location.href = url;
		}
		//取消流程
//		toCancelForm=function(overHaulId){
//			var url;
//			if(confirm("确定要取消该大修项目流程吗？")){
//				url="${ctx}/overHaulAction.do?method=cancelOverHaulForm";
//				var queryString="overhaul_id="+overHaulId;
//				//location=url+"&"+queryString+"&rnd="+Math.random();
//				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
//			}
//		}
	</script>
</head>
<body>
	<template:titile value="大修列表(<font color='red'>共${number}条待办</font>)" />
	<div style="text-align:center;">
		<iframe src="${ctx}/overHaulAction.do?method=processMap&task_name=${param.task_name}"
			frameborder="0" id="processGraphFrame" height="70" scrolling="no" width="99%" ></iframe>
	</div>
	<logic:notEmpty name="result">
	<display:table name="sessionScope.result" id="row" pagesize="18" export="false" defaultsort="1" defaultorder="descending" sort="list">
		<bean:define id="sendUserId" name="row" property="creator" />
		<bean:define id="applyState" name="row" property="state"></bean:define>
		<display:setProperty name="sort.amount" value="list"/>
		<display:column property="projectName" title="项目名称" sortable="true"/>
		<display:column property="projectCreator" title="立项人" sortable="true"/>
		<display:column property="budgetFee" title="预算费用（元）" sortable="true"/>
		<display:column property="useFee" title="目前项目使用费用（元）" sortable="true"/>
		<display:column property="startTime" title="开始时间" sortable="true" format="{0,date,yyyy-MM-dd}"/>
		<display:column property="endTime" title="结束时间" sortable="true" format="{0,date,yyyy-MM-dd}"/>
		<display:column media="html" title="操作">
			<c:if test="${row.state eq '10'}">
				<c:choose>
					<c:when test="${LOGIN_USER.deptype eq '1'}">
						<a href="javascript:treat('${row.id}')">处理</a>
					</c:when>
					<c:otherwise>
						<a href="javascript:apply('${row.id}')">申请</a>
					</c:otherwise>
				</c:choose>
			</c:if>
			<c:if test="${row.state eq '20'}">
				<c:choose>
					<c:when test="${row.readOnly}">
						<a href="javascript:approve('${row.subflowId}')">查看</a>
					</c:when>
					<c:otherwise>
						<a href="javascript:approve('${row.subflowId}','1')">审核</a> | 
						<a href="javascript:approve('${row.subflowId}','0')">转审</a>
					</c:otherwise>
				</c:choose>
			</c:if>
			<c:if test="${row.state eq '30'}">
				<a href="javascript:editApply('${row.subflowId}')">修改申请</a>
			</c:if>
	         <!--        <c:if test="${sessionScope.LOGIN_USER.deptype=='1'&&applyState!='999'}">
						| <a href="javascript:toCancelForm('${row.id}')">取消</a>
					</c:if>
					--> 
		</display:column>
	</display:table>
	</logic:notEmpty>
</body>
</html>