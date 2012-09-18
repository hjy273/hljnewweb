<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
	<script type="text/javascript">
		function view(id){
			window.location.href = '${ctx}/hiddangerQueryAction.do?method=view&param=finished&id='+id;
		}
		//取消流程
		toCancelForm=function(hideDangerId){
			var url;
			if(confirm("确定要取消该隐患流程吗？")){
				url="${ctx}/hiddangerAction.do?method=cancelHideDangerForm";
				var queryString="hide_danger_id="+hideDangerId;
				//location=url+"&"+queryString+"&rnd="+Math.random();
				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
			}
		}
	</script>
</head>
<body>
	<template:titile value="隐患已办工作列表(<font color='red'>共${number}条已办</font>)" />
	<display:table name="sessionScope.result" id="row" pagesize="18" export="false" defaultsort="9" defaultorder="descending" sort="list">
		<logic:notEmpty name="row">
			<bean:define id="id" name="row" property="id"></bean:define>
			<bean:define id="applyState" name="row" property="hiddangerState"></bean:define>
		</logic:notEmpty>
		
		<display:setProperty name="sort.amount" value="list"/>
		<display:column property="hiddangerNumber" title="隐患编号" sortable="true"/>
		<display:column property="name" title="名称" sortable="true"/>
		<display:column media="html" title="分类" sortable="true">
			<c:out value="${types[row.type]}" />
		</display:column>
		<display:column media="html" title="编码" sortable="true">
			<c:out value="${codes[row.code]}" />
		</display:column>
		<display:column media="html" title="发现时间" sortable="true">
			<bean:write name="row" property="findTime" format="yy-MM-dd HH:mm:ss"/>
		</display:column>
		<display:column property="reporter" title="报告人" sortable="true"/>
		<display:column media="html" title="处理单位" maxWords="8" sortable="true">
			<c:out value="${depts[row.treatDepartment]}" />
		</display:column>
		<display:column media="html" title="登记时间" sortable="true">
			<bean:write name="row" property="createTime" format="yy-MM-dd HH:mm:ss"/>
		</display:column>
		<display:column media="html" title="操作" sortable="true">
			<a href="javascript:view('${row.id}')">查看</a>
			<c:if test="${sessionScope.LOGIN_USER.deptype=='1'}">
						<c:if test="${row.hiddangerState eq '10'}">
						  	<a href="javascript:toCancelForm('${row.id}')">|取消</a>
						</c:if>
						<c:if test="${row.hiddangerState eq '20'}">
							<a href="javascript:toCancelForm('${row.id}')">|取消</a>
						</c:if>
						<c:if test="${row.hiddangerState eq '30'}">
							<a href="javascript:toCancelForm('${row.id}')">|取消</a>
						</c:if>
						<c:if test="${row.hiddangerState eq '40'}">
							<a href="javascript:toCancelForm('${row.id}')">|取消</a>
						</c:if>
					</c:if>		
		</display:column>
	</display:table>
</body>