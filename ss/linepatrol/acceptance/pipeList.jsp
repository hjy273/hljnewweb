<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
	
	<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
	
	<script type="text/javascript">
		function viewPipeData(id){
			var url = '${ctx}/acceptanceAction.do?method=viewPipeData&id=${apply.id}&pipeId='+id;
			parent.showWin(url);
		}
		//删除管道信息
		function deletePipeData(id){
			var con = confirm("是否删除该条记录？");
			if(con){
				window.location.href = '${ctx}/acceptancePlanQueryAction.do?method=deletePipeData&pipeId='+id;
			}
		}
		//返回
		goBack = function() {
			var url = '${sessionScope.S_BACK_URL}';
			self.location.replace(url);
		}
	</script>
</head>
<body>
	<template:titile value="管道列表" />
	<display:table name="sessionScope.apply.pipes" id="row"  pagesize="8" export="false" defaultsort="1" defaultorder="ascending">
		<display:column property="projectName" title="工程名称"/>
		<display:column property="pipeAddress" title="管道地点"/>
		<display:column property="pipeRoute" title="详细路由"/>
		<display:column property="builder" title="施工单位"/>
		<display:column property="pipeLength0" title="管道长度(KM)"/>
		<display:column property="planAcceptanceTime" title="验收日期"/>
		<display:column media="html" title="产权属性">
			${pipeProperty[row.pipeProperty]}
		</display:column>
		<display:column media="html" title="管道属性">
			${pipeType[row.pipeType]}
		</display:column>
		<c:if test="${apply.subProcessState eq '43' || apply.processState eq '00'}">
			<display:column media="html" title="操作">
				<a href="javascript:viewPipeData('${row.id}')">查看数据</a>
			</display:column>
		</c:if>
		<c:if test="${canDelete=='can'}">
			<display:column media="html" title="操作">
				<a href="javascript:deletePipeData('${row.id}')">删除</a>
			</display:column>
		</c:if>
	</display:table>
	<table style="border:0px;">
		<tr><td style="border:0px;text-align:center;">	
			<font color="red">${error}</font>
		</td></tr>
		<tr><td style="border:0px;text-align:center;">	
			<c:if test="${canDelete=='can'}">
				<html:button property="action" styleClass="button" onclick="goBack();">返回</html:button>
			</c:if>
			<c:if test="${canDelete!='can'}">
				<!-- 
				<input type="button" value="继续输入" class="button" onclick="javascript:resumeInput();">
				 -->
				<input type="button" value="退出" class="button" onclick="javascript:parent.close()">
			</c:if>
		</td></tr>
	</table>
</body>