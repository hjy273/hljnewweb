<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
	<script type="text/javascript">
		function view(id){
			window.location.href = '${ctx}/acceptanceQueryAction.do?method=view&param=finished&id='+id;
		}
		//ȡ������
		toCancelForm=function(applyId){
			var url;
			if(confirm("ȷ��Ҫȡ�������ս�ά������")){
				url="${ctx}/acceptanceAction.do?method=cancelAcceptanceForm";
				var queryString="apply_id="+applyId;
				//location=url+"&"+queryString+"&rnd="+Math.random();
				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
			};
		};
	</script>
</head>
<body>
	<template:titile value="���ս�ά�Ѱ��б�(<font color='red'>��${number}������</font>)" />
	<display:table name="sessionScope.result" id="row" pagesize="18" export="false" defaultsort="4" defaultorder="descending" sort="list">
		<logic:notEmpty name="row">
			<bean:define id="id" name="row" property="id"></bean:define>
		<display:setProperty name="sort.amount" value="list"/>
		<display:column property="name" title="��������"/>
		<display:column property="code" title="������"/>
		<display:column media="html" title="��Ŀ����">
			<c:out value="${users[row.applicant]}" />
		</display:column>
		<display:column media="html" title="����ʱ��">
			<bean:write name="row" property="applyDate" format="yy/MM/dd"/>
		</display:column>
		<display:column media="html" title="��Դ����">
			<c:choose>
				<c:when test="${row.resourceType eq '1'}">����</c:when>
				<c:otherwise>�ܵ�</c:otherwise>
			</c:choose>
		</display:column>
		<display:column media="html" title="����">
			<a href="javascript:view('${row.id}')">�鿴</a>
			<c:if test="${sessionScope.LOGIN_USER.deptype=='1'&&applyState=='10'}">
				<a href="javascript:toCancelForm('${row.id}')">ȡ��</a>
			</c:if>
		</display:column>
		</logic:notEmpty>
	</display:table>
</body>
</html>