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
<template:titile value="�����б�"/>
<table border="1" align="center" width="100%" class="tabout">
	<tr>
		<td>���˶���${cons[sessionScope.appraiseResultBean.contractorId] }</td>
		<td>����ţ�${sessionScope.appraiseResultBean.contractNO}</td>
		<td></td>
	</tr>
	<tr>
		<c:if test="${type=='1'}">
			<td>�����·�</td>
		</c:if>
		<c:if test="${type=='2'}">
			<td>ר��˱���</td>
		</c:if>
		<c:if test="${type=='4'}">
			<td>�������</td>
		</c:if>
		<td>�÷�</td>
		<td>����</td>
	</tr>
	<c:forEach var="appraiseReuslt" items="${apprasieResults}">
		<tr><c:if test="${type=='1'||type=='4'}">
			<td>${appraiseReuslt['appraiseTime'] }</td>
		</c:if>
		<c:if test="${type=='2'}">
			<td>${appraiseReuslt['tableName'] }</td>
		</c:if>
			<td>${appraiseReuslt['result'] }</td>
			<td><a href="javascript:view('${appraiseReuslt['id']}','${type}');">�鿴</a></td>
		</tr>
	</c:forEach>
	<tr>
		<td>ƽ����</td>
		<td colspan="2" style="text-align��center;">${avgResult}</td>
	</tr>
</table>
<div align="center">
<input name="btnClose" value="�ر�" class="button" type="button" onclick="closeViewBase();" />
</div>
</body>
