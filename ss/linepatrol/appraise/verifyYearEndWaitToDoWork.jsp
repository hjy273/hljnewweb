<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>��άȷ�Ͻ����ѯ</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	</head>
	<script type="text/javascript">
		function view(id){
			window.location.href="${ctx}/appraiseYearEndAction.do?method=viewAppraiseMonth&flag=view&resultId="+id;
		}	
		function eidt(id) {
			window.location.href="${ctx}/appraiseYearEndAction.do?method=editAppraise&flag=edit&resultId="+id;
		}
		function send(id) {
			window.location.href="${ctx}/appraiseYearEndAction.do?method=sendAppraise&id="+id;
		}
		function verify(id) {
			window.location.href="${ctx}/appraiseYearEndAction.do?method=viewAppraiseMonth&flag=verify&resultId="+id;
		}
	</script>
	<body>
		<template:titile value="���칤��" />
			<display:table name="sessionScope.appraiseResults" id="appraiseResults" pagesize="18" export="false" defaultsort="1" defaultorder="descending" sort="list"	>
				<logic:notEmpty name="appraiseResults">
				<bean:define id="id" name="appraiseResults" property="id"></bean:define>
				<display:column media="html" title="��ά��˾" sortable="true" >
					 	<c:out value="${contractor[appraiseResults.contractorId]}"></c:out>
					 </display:column>
				<display:column property="contractNO" title="��װ�"
					 maxLength="9" sortable="true" />
				<display:column property="result" sortable="true" title="���˽��"
					 maxLength="18" />
				<display:column media="html" sortable="true"
					title="�������" >
						<bean:write name="appraiseResults" property="appraiseTime" format="yyyy"/>
					</display:column>
				<display:column property="appraiser" title="������"
					 sortable="true" />
				<display:column media="html" title="ȷ�Ͻ��" sortable="true" maxLength="36" style="width:15%;">
				<c:if test="${appraiseResults.confirmResult=='0'}">
					���·�
				</c:if>
				<c:if test="${appraiseResults.confirmResult=='1'}">
					��ȷ��
				</c:if>
				<c:if test="${appraiseResults.confirmResult=='3'}">
					���޸ģ�${appConfirmResultMap[appraiseResults.id].result }��
				</c:if>
			</display:column>
			<display:column media="html" title="����">
				<c:if test="${appraiseResults.confirmResult=='0'}">
					<a href="javascript:send('${appraiseResults.id}');">�·�</a>
					| <a href="javascript:view('${appraiseResults.id}');">�鿴</a>
				</c:if>
				<c:if test="${appraiseResults.confirmResult=='1'}">
					<a href="javascript:verify('${appraiseResults.id}');">ȷ��</a>
				</c:if>
				<c:if test="${appraiseResults.confirmResult=='3'}">
					<a href="javascript:eidt('${appraiseResults.id}');">�༭</a>
				</c:if>
			</display:column>
				</logic:notEmpty>
			</display:table>
	</body>
</html>
