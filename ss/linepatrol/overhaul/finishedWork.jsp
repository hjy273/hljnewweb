<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
		
	<script type="text/javascript">
		function view(id){
			var url = '${ctx}/overHaulAction.do?method=view&type=history&id='+id;
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
	<template:titile value="大修列表" />
	<logic:notEmpty name="result">
	<display:table name="sessionScope.result" id="row" pagesize="18" export="false" defaultsort="1" defaultorder="descending" sort="list">
		<bean:define id="sendUserId" name="row" property="creator" />
		<bean:define id="id" name="row" property="id"></bean:define>
		<bean:define id="applyState" name="row" property="state"></bean:define>
		<display:setProperty name="sort.amount" value="list"/>
		<display:column property="projectName" title="项目名称" sortable="true"/>
		<display:column property="projectCreator" title="立项人" sortable="true"/>
		<display:column property="budgetFee" title="预算费用（元）" sortable="true"/>
		<display:column property="useFee" title="目前项目使用费用（元）" sortable="true"/>
		<display:column property="startTime" title="开始时间" sortable="true" format="{0,date,yyyy-MM-dd}"/>
		<display:column property="endTime" title="结束时间" sortable="true" format="{0,date,yyyy-MM-dd}"/>
		<display:column media="html" title="操作">
			<a href="javascript:view('${row.id}')">查看</a>
	         <!--         <c:if test="${sessionScope.LOGIN_USER.userID==sendUserId&&applyState!='999'}">
						| <a href="javascript:toCancelForm('${row.id}')">取消</a>
					</c:if>
					-->
		</display:column>
	</display:table>
	</logic:notEmpty>
</body>