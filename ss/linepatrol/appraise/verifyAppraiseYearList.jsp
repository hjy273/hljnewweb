<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>���˽��ȷ��</title>
	</head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
	<script type="text/javascript">
		function verify(id) {
		window.location.href="${ctx}/appraiseYearAction.do?method=viewAppraiseYear&flag=verify&id="+id;
	}	
	</script>
	<body>
		<template:titile value="���˽��ȷ��" />
			<display:table name="sessionScope.appraiseYearResults" id="appraiseYearResults" pagesize="18" export="false" defaultsort="1" defaultorder="descending" sort="list"	>
				<logic:notEmpty name="appraiseYearResults">
				<bean:define id="id" name="appraiseYearResults" property="id"></bean:define>
				<display:column media="html" title="��ά��˾" sortable="true" >
					 	<c:out value="${contractor[appraiseYearResults.contractorId]}"></c:out>
					 </display:column>
				<display:column property="contractNO" title="��װ�"
					 maxLength="9" sortable="true" />
				<display:column property="result" sortable="true" title="���˽��"
					 maxLength="18" />
				<display:column media="html" sortable="true" property="year"
					title="�������" >
					</display:column>
				<display:column property="appraiser" title="������"
					 sortable="true" />
				<display:column media="html" title="����">
					<a href="javascript:verify('${appraiseYearResults.id}');">ȷ��</a>
				</display:column>
				</logic:notEmpty>
			</display:table>
			<table border="0" cellpadding="0" cellspacing="0" width="100%"
				style="border: 0px">
				<tr>
					<td style="text-align: center;">
						<input name="action" class="button" value="����" onclick="history.back();"
							type="button" />
					</td>
				</tr>
			</table>
	</body>
</html>
