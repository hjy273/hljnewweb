<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
	<script type="text/javascript">
		function view(id){
			window.location.href = '${ctx}/acceptanceQueryAction.do?method=view&param=finished&id='+id;
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
	<template:titile value="验收交维已办列表(<font color='red'>共${number}条待办</font>)" />
	<display:table name="sessionScope.result" id="row" pagesize="18" export="false" defaultsort="4" defaultorder="descending" sort="list">
		<logic:notEmpty name="row">
			<bean:define id="id" name="row" property="id"></bean:define>
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
		<display:column media="html" title="操作">
			<a href="javascript:view('${row.id}')">查看</a>
			<c:if test="${sessionScope.LOGIN_USER.deptype=='1'&&applyState=='10'}">
				<a href="javascript:toCancelForm('${row.id}')">取消</a>
			</c:if>
		</display:column>
		</logic:notEmpty>
	</display:table>
</body>
</html>