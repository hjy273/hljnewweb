<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<br>
<script type="text/javascript">
	function view(id,type){
	var typeName;
		if(type=='1'){
			typeName="Month";
		}
		if(type=="2"){
			typeName="Special";
		}
		if(type=="4"){
			typeName="YearEnd";
		}
		window.location.href="${ctx}/appraise"+typeName+"Action.do?method=viewAppraiseMonth&flag=view&resultId="+id;
	}
</script>
<body background="#FFFFFF">
<template:titile value="考核列表"/>
<table border="1" align="center" width="100%" class="tabout">
	<tr>
		<td>考核对象：${cons[sessionScope.appraiseResultBean.contractorId] }</td>
		<td>标包号：${sessionScope.appraiseResultBean.contractNO}</td>
		<td></td>
	</tr>
	<tr>
		<c:if test="${type=='1'}">
			<td>考核月份</td>
		</c:if>
		<c:if test="${type=='2'}">
			<td>专项考核表名</td>
		</c:if>
		<c:if test="${type=='4'}">
			<td>考核年份</td>
		</c:if>
		<td>得分</td>
		<td>操作</td>
	</tr>
	<c:forEach var="appraiseReuslt" items="${apprasieResults}">
		<tr><c:if test="${type=='1'||type=='4'}">
			<td>${appraiseReuslt['appraiseTime'] }</td>
		</c:if>
		<c:if test="${type=='2'}">
			<td>${appraiseReuslt['tableName'] }</td>
		</c:if>
			<td>${appraiseReuslt['result'] }</td>
			<td><a href="javascript:view('${appraiseReuslt['id']}','${type}');">查看</a></td>
		</tr>
	</c:forEach>
	<tr>
		<td>平均分</td>
		<td colspan="2" style="text-align：center;">${avgResult}</td>
	</tr>
</table>
<div align="center">
<input name="btnClose" value="关闭" class="button" type="button" onclick="closeViewBase();" />
</div>
</body>
