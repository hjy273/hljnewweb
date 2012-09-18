<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
	<script type="text/javascript">
		function viewCableData(id){
			window.location.href = '${ctx}/acceptanceAction.do?method=viewCableData&id=${apply.id}&cableId='+id;
		}
		function deleteCableData(id){
			var con = confirm("是否删除该条记录？");
			if(con){
				window.location.href = '${ctx}/acceptancePlanQueryAction.do?method=deleteCableData&cableId='+id;
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
	<template:titile value="光缆列表" />
	<display:table name="sessionScope.apply.cables" id="row"  pagesize="8" export="false" defaultsort="1" defaultorder="ascending">
		<display:column property="cableNo" title="光缆编号"/>
		<display:column property="trunk" title="光缆中继段"/>
		<display:column property="fibercoreNo" title="纤芯数"/>
		<display:column property="issueNumber" title="工程名称"/>
		<display:column media="html" title="光缆级别">
			${cabletype[row.cableLevel] }
		</display:column>
		<display:column media="html" title="光缆长度">
			${row.cableLength}千米
		</display:column>
		<c:if test="${apply.subProcessState eq '43' || apply.processState eq '00'}">
			<display:column media="html" title="操作">
				<a href="javascript:viewCableData('${row.id}')">查看数据</a>
			</display:column>
		</c:if>
		<c:if test="${canDelete=='can'}">
			<display:column media="html" title="操作">
				<a href="javascript:deleteCableData('${row.id}')">删除</a>
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
				<input type="button" value="退出" class="button" onclick="javascript:parent.close();">
			</c:if>
		</td></tr>
	</table>
</body>