<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>���˽��ȷ��</title>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
</head>
<script type="text/javascript">
	function verify(id) {
		window.location.href="${ctx}/appraiseMonthAction.do?method=viewAppraiseMonth&flag=verify&resultId="+id;
	}
</script>
<body>
	<template:titile value="���˽��ȷ��" />
		<display:table name="sessionScope.appraiseResults"
			id="appraiseResults" pagesize="18" export="false" defaultsort="1"
			defaultorder="descending" sort="list">
			<display:column property="appraiseTime" title="�����·�" format="{0,date,yyyy-MM}"
				sortable="true" />
			<display:column media="html" title="��ά��˾" sortable="true">
				<c:out value="${contractor[appraiseResults.contractorId]}"></c:out>
			</display:column>
			<display:column property="contractNO" title="��װ�" maxLength="9"
				sortable="true" />
				<display:column property="result" sortable="true" title="���˽��" />
			<display:column property="appraiseDate" sortable="true"
				format="{0,date,yyyy-MM-dd}" title="����ʱ��" />
			<display:column property="appraiser" title="������" sortable="true" ></display:column>
			<display:column media="html" title="����">
				<a href="javascript:verify('${appraiseResults.id}');">ȷ��</a>
			</display:column>
		</display:table>
</body>
</html>
